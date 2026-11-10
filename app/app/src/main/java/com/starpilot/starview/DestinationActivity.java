package com.starpilot.starview;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.starpilot.starview.DestinationActivity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.collections.MapsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import org.json.JSONArray;
import org.json.JSONObject;
import android.widget.LinearLayout.LayoutParams;

/* compiled from: DestinationActivity.kt */
public final class DestinationActivity extends AppCompatActivity {
    private static final String CLIENT_ID;
    public static final Companion Companion = new Companion(null);
    public static final String EXTRA_LAT = "lat";
    public static final String EXTRA_LON = "lon";
    public static final String EXTRA_QUERY = "query";
    public static final String EXTRA_ROUTE = "route";
    private static final String KEY_RECENTS = "recents";
    private static final int MAX_RECENTS = 12;
    private static final String PREFS = "starview_nav";
    private static final List<Pair<String, String>> QUICK;
    public static final String R_ADDRESS = "address";
    public static final String R_LAT = "latitude";
    public static final String R_LON = "longitude";
    public static final String R_MAPBOX_ID = "mapbox_id";
    public static final String R_NAME = "name";
    public static final String R_SEARCH_ID = "search_id";
    private TextView alongBtn;
    private boolean commaSearch;
    private final Runnable doSearch;
    private EditText edit;
    private Pair<Double, Double> here;
    private LinearLayout list;
    private List<Place> results;
    private String route;
    private String searchId;
    private int searchSeq;
    private TextView status;
    private final Handler ui = new Handler(Looper.getMainLooper());
    private final ExecutorService bg = Executors.newSingleThreadExecutor();
    private boolean along = true;
    private List<Place> favorites = CollectionsKt.emptyList();
    private List<Place> spRecents = CollectionsKt.emptyList();
    private String resultsTitle = "";

    /* compiled from: DestinationActivity.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final List<JSONObject> recents(Context ctx) {
            Intrinsics.checkNotNullParameter(ctx, "ctx");
            try {
                JSONArray a = new JSONArray(ctx.getSharedPreferences("starview_nav", 0).getString("recents", "[]"));
                Iterable $this$map$iv = RangesKt.until(0, a.length());
                Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                Iterator<Integer> it = $this$map$iv.iterator();
                while (it.hasNext()) {
                    int item$iv$iv = ((IntIterator) it).nextInt();
                    destination$iv$iv.add(a.getJSONObject(item$iv$iv));
                }
                return (List) destination$iv$iv;
            } catch (Exception e) {
                return CollectionsKt.emptyList();
            }
        }

        public final void remember(Context ctx, String name, String address, double lat, double lon) {
            Intrinsics.checkNotNullParameter(ctx, "ctx");
            Intrinsics.checkNotNullParameter(name, "name");
            Intrinsics.checkNotNullParameter(address, "address");
            Iterable $this$filterNot$iv = recents(ctx);
            int $i$f$filterNot = 0;
            Collection destination$iv$iv = new ArrayList();
            Iterable $this$filterNotTo$iv$iv = $this$filterNot$iv;
            Iterator it = $this$filterNotTo$iv$iv.iterator();
            while (true) {
                Iterable $this$filterNot$iv2 = $this$filterNot$iv;
                int $i$f$filterNot2 = $i$f$filterNot;
                if (!it.hasNext()) {
                    break;
                }
                Object element$iv$iv = it.next();
                Iterable $this$filterNotTo$iv$iv2 = $this$filterNotTo$iv$iv;
                JSONObject it2 = (JSONObject) element$iv$iv;
                if (!(Math.abs(it2.optDouble("latitude") - lat) < 1.0E-5d && Math.abs(it2.optDouble("longitude") - lon) < 1.0E-5d)) {
                    destination$iv$iv.add(element$iv$iv);
                }
                $this$filterNot$iv = $this$filterNot$iv2;
                $i$f$filterNot = $i$f$filterNot2;
                $this$filterNotTo$iv$iv = $this$filterNotTo$iv$iv2;
            }
            List list = (List) destination$iv$iv;
            JSONArray out = new JSONArray().put(new JSONObject().put("name", name).put("address", address).put("latitude", lat).put("longitude", lon));
            for (JSONObject o : (Iterable<JSONObject>) CollectionsKt.take(list, 11)) {
                out.put(o);
            }
            ctx.getSharedPreferences("starview_nav", 0).edit().putString("recents", out.toString()).apply();
        }
    }

    public DestinationActivity() {
        String uuid = UUID.randomUUID().toString();
        Intrinsics.checkNotNullExpressionValue(uuid, "toString(...)");
        this.searchId = uuid;
        this.commaSearch = true;
        this.doSearch = new Runnable() { // from class: com.starpilot.starview.DestinationActivity$$ExternalSyntheticLambda20
            @Override // java.lang.Runnable
            public final void run() {
                DestinationActivity.doSearch$lambda$28(DestinationActivity.this);
            }
        };
    }

    static {
        String uuid = UUID.randomUUID().toString();
        Intrinsics.checkNotNullExpressionValue(uuid, "toString(...)");
        CLIENT_ID = uuid;
        QUICK = CollectionsKt.listOf(new Pair[]{TuplesKt.to("gas", "⛽  Gas"), TuplesKt.to("ev", "⚡  EV charging"), TuplesKt.to("coffee", "☕  Coffee"), TuplesKt.to("food", "🍽  Food"), TuplesKt.to("fast_food", "🍔  Fast food"), TuplesKt.to("parking", "🅿  Parking"), TuplesKt.to("grocery", "🛒  Grocery"), TuplesKt.to("pharmacy", "💊  Pharmacy"), TuplesKt.to("hotel", "🛏  Hotels"), TuplesKt.to("rest_area", "🚻  Rest areas")});
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: DestinationActivity.kt */
    static final class Place {
        private final String address;
        private final double distM;
        private final boolean keep;
        private final String kind;
        private final double lat;
        private final double lon;
        private final String mapboxId;
        private final String name;

        public Place(String name, String address, double lat, double lon, String mapboxId, double distM, String kind, boolean keep) {
            Intrinsics.checkNotNullParameter(name, "name");
            Intrinsics.checkNotNullParameter(address, "address");
            Intrinsics.checkNotNullParameter(mapboxId, "mapboxId");
            Intrinsics.checkNotNullParameter(kind, "kind");
            this.name = name;
            this.address = address;
            this.lat = lat;
            this.lon = lon;
            this.mapboxId = mapboxId;
            this.distM = distM;
            this.kind = kind;
            this.keep = keep;
        }

        public /* synthetic */ Place(String str, String str2, double d, double d2, String str3, double d3, String str4, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, str2, d, d2, (i & 16) != 0 ? "" : str3, (i & 32) != 0 ? Double.NaN : d3, (i & 64) != 0 ? "" : str4, (i & 128) != 0 ? true : z);
        }

        public final String getAddress() {
            return this.address;
        }

        public final double getLat() {
            return this.lat;
        }

        public final double getLon() {
            return this.lon;
        }

        public final String getName() {
            return this.name;
        }

        public final double getDistM() {
            return this.distM;
        }

        public final boolean getKeep() {
            return this.keep;
        }

        public final String getKind() {
            return this.kind;
        }

        public final String getMapboxId() {
            return this.mapboxId;
        }
    }

    private final int dp(int v) {
        return MathKt.roundToInt(v * getResources().getDisplayMetrics().density);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.ContextThemeWrapper, android.content.ContextWrapper
    protected void attachBaseContext(Context base) {
        Intrinsics.checkNotNullParameter(base, "base");
        super.attachBaseContext(NarrowDensity.INSTANCE.wrap(base, 1000, 0.62f));
    }

    /* VF: from Vineflower (jadx: Code restructure failed;incorrect) */
    @Override
    protected void onCreate(Bundle var1) {
        String var11 = null; // declarations Vineflower left out
        super.onCreate(var1);
        double var4 = this.getIntent().getDoubleExtra("lat", Double.NaN);
        double var2 = this.getIntent().getDoubleExtra("lon", Double.NaN);
        boolean var6;
        if (!Double.isInfinite(var4) && !Double.isNaN(var4)) {
            var6 = true;
        } else {
            var6 = false;
        }

        if (var6) {
            if (!Double.isInfinite(var2) && !Double.isNaN(var2)) {
                var6 = true;
            } else {
                var6 = false;
            }

            label102:
            if (var6) {
                if (var4 == 0.0) {
                    var6 = true;
                } else {
                    var6 = false;
                }

                if (var6) {
                    if (var2 == 0.0) {
                        var6 = true;
                    } else {
                        var6 = false;
                    }

                    if (var6) {
                        break label102;
                    }
                }

                this.here = TuplesKt.to(var4, var2);
            }
        }

        label94: {
            var11 = this.getIntent().getStringExtra("route");
            if (var11 != null) {
                if (var11.length() >= 10) {
                    var6 = true;
                } else {
                    var6 = false;
                }

                if (var6) {
                    break label94;
                }
            }

            var11 = null;
        }

        this.route = ((String) var11);
        LinearLayout var8 = new LinearLayout((Context)this);
        var8.setOrientation(1);
        var8.setBackgroundColor(Color.rgb(11, 13, 18));
        var8.setPadding(this.dp(20), this.dp(16), this.dp(20), this.dp(8));
        LinearLayout var9 = new LinearLayout((Context)this);
        var9.setOrientation(0);
        var9.setGravity(16);
        TextView var12 = new TextView((Context)this);
        var12.setText("‹");
        var12.setTextSize(34.0F);
        var12.setTextColor(-1);
        var12.setPadding(this.dp(4), 0, this.dp(16), this.dp(4));
        var12.setOnClickListener(new DestinationActivity$$ExternalSyntheticLambda22(this));
        var9.addView((View)var12);
        EditText var13 = new EditText((Context)this);
        var13.setHint("Search places or addresses");
        var13.setHintTextColor(Color.rgb(128, 134, 144));
        var13.setTextColor(-1);
        var13.setTextSize(20.0F);
        var13.setInputType(8193);
        var13.setImeOptions(3);
        var13.setSingleLine(true);
        GradientDrawable var7 = new GradientDrawable();
        var7.setCornerRadius(this.dp(14));
        var7.setColor(Color.rgb(27, 31, 39));
        var13.setBackground((Drawable)var7);
        var13.setPadding(this.dp(16), this.dp(12), this.dp(16), this.dp(12));
        var13.setOnEditorActionListener(new DestinationActivity$$ExternalSyntheticLambda23(this, var13));
        var13.addTextChangedListener(new TextWatcher() {
            final DestinationActivity this$0 = DestinationActivity.this;

            public void afterTextChanged(Editable var1) {
                DestinationActivity var3 = this.this$0;
                String var4x;
                if (var1 != null) {
                    var4x = var1.toString();
                } else {
                    var4x = null;
                }

                String var2x = var4x;
                if (var4x == null) {
                    var2x = "";
                }

                var3.search(var2x, false);
            }

            public void beforeTextChanged(CharSequence var1, int var2x, int var3, int var4x) {
            }

            public void onTextChanged(CharSequence var1, int var2x, int var3, int var4x) {
            }
        });
        this.edit = var13;
        EditText var30 = this.edit;
        EditText var14 = var30;
        if (var30 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("edit");
            var14 = null;
        }

        var9.addView((View)var14, (android.view.ViewGroup.LayoutParams)(new LayoutParams(0, -2, 1.0F)));
        TextView var15 = new TextView((Context)this);
        var15.setText("✕");
        var15.setTextSize(22.0F);
        var15.setTextColor(Color.rgb(154, 161, 172));
        var15.setPadding(this.dp(14), 0, this.dp(4), 0);
        var15.setOnClickListener(new DestinationActivity$$ExternalSyntheticLambda1(this));
        var9.addView((View)var15);
        var8.addView((View)var9);
        var9 = new LinearLayout((Context)this);
        var9.setOrientation(0);
        var9.setPadding(0, this.dp(12), 0, this.dp(2));
        this.alongBtn = this.chip("", new DestinationActivity$$ExternalSyntheticLambda2(this));
        if (this.route != null) {
            TextView var31 = this.alongBtn;
            TextView var16 = var31;
            if (var31 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("alongBtn");
                var16 = null;
            }

            var9.addView((View)var16, (android.view.ViewGroup.LayoutParams)this.chipLp());
            this.paintAlong();
        }

        for (Pair var10 : QUICK) {
            String var32 = (String)var10.component1();
            String var41 = (String)var10.component2();
            var9.addView(
                (View)this.chip(var41, new DestinationActivity$$ExternalSyntheticLambda3(this, var32, var41)), (android.view.ViewGroup.LayoutParams)this.chipLp()
            );
        }

        HorizontalScrollView var18 = new HorizontalScrollView((Context)this);
        var18.setHorizontalScrollBarEnabled(false);
        var18.addView((View)var9);
        var8.addView((View)var18);
        TextView var19 = new TextView((Context)this);
        var19.setTextColor(Color.rgb(143, 163, 199));
        var19.setTextSize(14.0F);
        var19.setPadding(this.dp(4), this.dp(10), this.dp(4), this.dp(4));
        this.status = var19;
        TextView var33 = this.status;
        TextView var20 = var33;
        if (var33 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("status");
            var20 = null;
        }

        var8.addView((View)var20);
        LinearLayout var21 = new LinearLayout((Context)this);
        var21.setOrientation(1);
        this.list = var21;
        ScrollView var40 = new ScrollView((Context)this);
        LinearLayout var34 = this.list;
        LinearLayout var22 = var34;
        if (var34 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("list");
            var22 = null;
        }

        var40.addView((View)var22);
        var8.addView((View)var40, (android.view.ViewGroup.LayoutParams)(new LayoutParams(-1, 0, 1.0F)));
        this.setContentView((View)var8);
        String var23 = this.getIntent().getStringExtra("query");
        if (var23 != null) {
            if (StringsKt.isBlank(var23)) {
                var23 = null;
            }

            if (var23 != null) {
                EditText var37 = this.edit;
                EditText var35 = var37;
                if (var37 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("edit");
                    var35 = null;
                }

                var35.setText(var23);
                EditText var38 = this.edit;
                EditText var36 = var38;
                if (var38 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("edit");
                    var36 = null;
                }

                var36.setSelection(var23.length());
                this.search(var23, true);
            }
        }

        this.favorites = this.places(new JSONArray(Favorites.INSTANCE.getList()), "name");
        DataClient var24 = Link.INSTANCE.getData();
        if (var24 != null) {
            var24.request("nav_list", kotlin.collections.MapsKt.emptyMap(), new DestinationActivity$$ExternalSyntheticLambda4(this));
        }

        this.render();
        EditText var25 = this.edit;
        if (var25 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("edit");
            var25 = null;
        }

        var25.requestFocus();
        this.ui.postDelayed(new DestinationActivity$$ExternalSyntheticLambda5(this), 250L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean onCreate$lambda$7$lambda$6(DestinationActivity this$0, EditText $this_apply, TextView textView, int id, KeyEvent ev) {
        if (id != 3) {
            if (!(ev != null && ev.getKeyCode() == 66)) {
                return false;
            }
        }
        this$0.search($this_apply.getText().toString(), true);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$9$lambda$8(DestinationActivity this$0, View it) {
        EditText editText = this$0.edit;
        if (editText == null) {
            Intrinsics.throwUninitializedPropertyAccessException("edit");
            editText = null;
        }
        editText.setText("");
        this$0.results = null;
        this$0.render();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onCreate$lambda$11(DestinationActivity this$0) {
        this$0.along = !this$0.along;
        this$0.paintAlong();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onCreate$lambda$12(DestinationActivity this$0, String $key, String $label) {
        this$0.quick($key, $label);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onCreate$lambda$19(DestinationActivity this$0, JSONObject r) {
        Intrinsics.checkNotNullParameter(r, "r");
        if (r.optBoolean("ok")) {
            Favorites.load$default(Favorites.INSTANCE, Link.INSTANCE.getData(), null, 2, null);
            this$0.favorites = this$0.places(r.optJSONArray("favorites"), "name");
            this$0.spRecents = this$0.places(r.optJSONArray("recents"), "place_name");
            if (this$0.results == null) {
                this$0.render();
            }
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$20(DestinationActivity this$0) {
        Object systemService = this$0.getSystemService("input_method");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.view.inputmethod.InputMethodManager");
        InputMethodManager inputMethodManager = (InputMethodManager) systemService;
        EditText editText = this$0.edit;
        if (editText == null) {
            Intrinsics.throwUninitializedPropertyAccessException("edit");
            editText = null;
        }
        inputMethodManager.showSoftInput(editText, 1);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        this.bg.shutdownNow();
        super.onDestroy();
    }

    private final TextView chip(String text, final Function0<Unit> onClick) {
        TextView $this$chip_u24lambda_u2423 = new TextView(this);
        $this$chip_u24lambda_u2423.setText(text);
        $this$chip_u24lambda_u2423.setTextSize(15.0f);
        $this$chip_u24lambda_u2423.setTextColor(-1);
        $this$chip_u24lambda_u2423.setTypeface($this$chip_u24lambda_u2423.getTypeface(), 1);
        $this$chip_u24lambda_u2423.setGravity(17);
        $this$chip_u24lambda_u2423.setPadding(dp(16), dp(9), dp(16), dp(9));
        GradientDrawable $this$chip_u24lambda_u2423_u24lambda_u2421 = new GradientDrawable();
        $this$chip_u24lambda_u2423_u24lambda_u2421.setCornerRadius(dp(20));
        $this$chip_u24lambda_u2423_u24lambda_u2421.setColor(Color.rgb(30, 35, 44));
        $this$chip_u24lambda_u2423.setBackground($this$chip_u24lambda_u2423_u24lambda_u2421);
        $this$chip_u24lambda_u2423.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.DestinationActivity$$ExternalSyntheticLambda21
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                onClick.invoke();
            }
        });
        return $this$chip_u24lambda_u2423;
    }

    private final LinearLayout.LayoutParams chipLp() {
        LinearLayout.LayoutParams $this$chipLp_u24lambda_u2424 = new LinearLayout.LayoutParams(-2, -2);
        $this$chipLp_u24lambda_u2424.setMarginEnd(dp(8));
        return $this$chipLp_u24lambda_u2424;
    }

    private final void paintAlong() {
        int i;
        int i2;
        int i3;
        TextView textView = this.alongBtn;
        TextView textView2 = null;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("alongBtn");
            textView = null;
        }
        textView.setText(this.along ? "↗  Along the route" : "◎  Near me");
        TextView textView3 = this.alongBtn;
        if (textView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("alongBtn");
        } else {
            textView2 = textView3;
        }
        Drawable background = textView2.getBackground();
        Intrinsics.checkNotNull(background, "null cannot be cast to non-null type android.graphics.drawable.GradientDrawable");
        GradientDrawable gradientDrawable = (GradientDrawable) background;
        if (this.along) {
            i = 128;
            i2 = 237;
            i3 = 47;
        } else {
            i = 35;
            i2 = 44;
            i3 = 30;
        }
        gradientDrawable.setColor(Color.rgb(i3, i, i2));
    }

    private final List<Place> places(JSONArray a, String nameKey) {
        String name;
        String str;
        JSONArray jSONArray = a;
        if (jSONArray == null) {
            return CollectionsKt.emptyList();
        }
        ArrayList out = new ArrayList();
        int i = 0;
        int length = a.length();
        while (i < length) {
            JSONObject o = jSONArray.optJSONObject(i);
            if (o != null) {
                double lat = o.optDouble("latitude", Double.NaN);
                double lon = o.optDouble("longitude", Double.NaN);
                if ((Double.isInfinite(lat) || Double.isNaN(lat)) ? false : true) {
                    if ((Double.isInfinite(lon) || Double.isNaN(lon)) ? false : true) {
                        String optString = o.optString(nameKey);
                        if (optString.length() == 0) {
                            String optString2 = o.optString("name");
                            if (optString2.length() == 0) {
                                optString2 = o.optString("place_name");
                            }
                            optString = optString2;
                        }
                        String n = optString;
                        Intrinsics.checkNotNull(n);
                        List it = StringsKt.split((CharSequence) n, new String[]{": "}, false, 2);
                        Pair pair = it.size() == 2 ? TuplesKt.to(it.get(0), it.get(1)) : TuplesKt.to(n, o.optString("address"));
                        String name0 = (String) pair.component1();
                        String addr = (String) pair.component2();
                        String optString3 = o.optString("label");
                        if (Intrinsics.areEqual(optString3, "home")) {
                            name = "Home";
                        } else if (Intrinsics.areEqual(optString3, "work")) {
                            name = "Work";
                        } else {
                            name = name0;
                        }
                        out.add(new Place(name, addr, lat, lon, null, 0.0d, null, false, 240, null));
                    }
                }
            }
            i++;
            jSONArray = a;
        }
        return out;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void doSearch$lambda$28(DestinationActivity this$0) {
        EditText editText = this$0.edit;
        if (editText == null) {
            Intrinsics.throwUninitializedPropertyAccessException("edit");
            editText = null;
        }
        this$0.runSearch(editText.getText().toString(), false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void search(String q, boolean now) {
        this.ui.removeCallbacks(this.doSearch);
        if (StringsKt.trim((CharSequence) q).toString().length() < 2) {
            this.results = null;
            render();
        } else if (now) {
            runSearch(q, true);
        } else {
            this.ui.postDelayed(this.doSearch, 300L);
        }
    }

    private final void runSearch(String q0, final boolean enter) {
        final String q = StringsKt.trim((CharSequence) q0).toString();
        if (q.length() < 2) {
            return;
        }
        this.searchSeq++;
        final int seq = this.searchSeq;
        DataClient data = Link.INSTANCE.getData();
        if (!this.commaSearch || data == null || !data.getConnected()) {
            if (q.length() >= 3) {
                androidSearch(q, seq);
                return;
            }
            return;
        }
        TextView textView = this.status;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("status");
            textView = null;
        }
        textView.setText("Searching…");
        HashMap args = new HashMap(MapsKt.mapOf(TuplesKt.to("query", q), TuplesKt.to("searchId", this.searchId), TuplesKt.to("clientId", CLIENT_ID)));
        Pair it = this.here;
        if (it != null) {
            args.put("latitude", it.getFirst());
            args.put("longitude", it.getSecond());
        }
        data.request("nav_search", args, new Function1() { // from class: com.starpilot.starview.DestinationActivity$$ExternalSyntheticLambda13
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit runSearch$lambda$34;
                runSearch$lambda$34 = DestinationActivity.runSearch$lambda$34(seq, DestinationActivity.this, q, enter, (JSONObject) obj);
                return runSearch$lambda$34;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit runSearch$lambda$34(int $seq, DestinationActivity this$0, String $q, boolean $enter, JSONObject r) {
        Intrinsics.checkNotNullParameter(r, "r");
        if ($seq != this$0.searchSeq || this$0.isFinishing()) {
            return Unit.INSTANCE;
        }
        if (!r.optBoolean("ok")) {
            String e = r.optString("error");
            Intrinsics.checkNotNull(e);
            TextView textView = null;
            if (StringsKt.contains((CharSequence) e, (CharSequence) "new destination search", false)) {
                this$0.searchId = UUID.randomUUID().toString();
            } else {
                this$0.commaSearch = false;
                TextView textView2 = this$0.status;
                if (textView2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("status");
                } else {
                    textView = textView2;
                }
                String str = e;
                if (str.length() == 0) {
                    str = "no answer";
                }
                textView.setText("Mapbox search isn't available (" + ((Object) str) + ") - Android's address search instead");
            }
            if ($q.length() >= 3) {
                this$0.androidSearch($q, $seq);
            }
            return Unit.INSTANCE;
        }
        JSONArray a = r.optJSONArray("results");
        if (a == null) {
            a = new JSONArray();
        }
        Iterable $this$mapNotNull$iv = RangesKt.until(0, a.length());
        Collection destination$iv$iv = new ArrayList();
        Iterator<Integer> it = $this$mapNotNull$iv.iterator();
        while (it.hasNext()) {
            int element$iv$iv$iv = ((IntIterator) it).nextInt();
            JSONObject optJSONObject = a.optJSONObject(element$iv$iv$iv);
            if (optJSONObject != null) {
                destination$iv$iv.add(optJSONObject);
            }
        }
        Iterable $this$map$iv = (List) destination$iv$iv;
        Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        Iterable $this$mapTo$iv$iv = $this$map$iv;
        int $i$f$mapTo = 0;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            JSONObject o = (JSONObject) item$iv$iv;
            String optString = o.optString("name");
            Intrinsics.checkNotNullExpressionValue(optString, "optString(...)");
            String optString2 = o.optString("description");
            Intrinsics.checkNotNullExpressionValue(optString2, "optString(...)");
            JSONArray a2 = a;
            String optString3 = o.optString("id");
            Intrinsics.checkNotNullExpressionValue(optString3, "optString(...)");
            Iterable $this$mapTo$iv$iv2 = $this$mapTo$iv$iv;
            int $i$f$mapTo2 = $i$f$mapTo;
            double optDouble = o.optDouble("distanceMeters", Double.NaN);
            String optString4 = o.optString("maki");
            if (optString4.length() == 0) {
                optString4 = o.optString("category");
            }
            Intrinsics.checkNotNullExpressionValue(optString4, "ifEmpty(...)");
            destination$iv$iv2.add(new Place(optString, optString2, Double.NaN, Double.NaN, optString3, optDouble, optString4, false, 128, null));
            a = a2;
            $this$mapTo$iv$iv = $this$mapTo$iv$iv2;
            $i$f$mapTo = $i$f$mapTo2;
        }
        List found = (List) destination$iv$iv2;
        if (found.isEmpty() && ($enter || $q.length() >= 4)) {
            this$0.androidSearch($q, $seq);
            return Unit.INSTANCE;
        }
        this$0.results = found;
        this$0.resultsTitle = "";
        this$0.render();
        return Unit.INSTANCE;
    }

    private final void quick(String key, final String label) {
        DataClient data = Link.INSTANCE.getData();
        Pair pos = this.here;
        TextView textView = null;
        if (data == null || !data.getConnected()) {
            TextView textView2 = this.status;
            if (textView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("status");
            } else {
                textView = textView2;
            }
            textView.setText("Not connected to the comma");
            return;
        }
        if (pos == null) {
            TextView textView3 = this.status;
            if (textView3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("status");
            } else {
                textView = textView3;
            }
            textView.setText("The car's position isn't known yet");
            return;
        }
        this.searchSeq++;
        final int seq = this.searchSeq;
        boolean alongRoute = this.route != null && this.along;
        TextView textView4 = this.status;
        if (textView4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("status");
            textView4 = null;
        }
        String substringAfter$default = StringsKt.substringAfter(label, "  ", label);
        Locale US = Locale.US;
        Intrinsics.checkNotNullExpressionValue(US, "US");
        String lowerCase = substringAfter$default.toLowerCase(US);
        Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
        textView4.setText("Looking for " + lowerCase + (alongRoute ? " along the route" : " nearby") + "…");
        HashMap args = new HashMap(MapsKt.mapOf(TuplesKt.to("category", key), TuplesKt.to("latitude", pos.getFirst()), TuplesKt.to("longitude", pos.getSecond())));
        if (alongRoute) {
            args.put("route", this.route);
        }
        data.request("nav_category", args, new Function1() { // from class: com.starpilot.starview.DestinationActivity$$ExternalSyntheticLambda19
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit quick$lambda$42;
                quick$lambda$42 = DestinationActivity.quick$lambda$42(seq, DestinationActivity.this, label, (JSONObject) obj);
                return quick$lambda$42;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit quick$lambda$42(int $seq, final DestinationActivity this$0, String $label, JSONObject r) {
        List sortedWith;
        String str;
        Intrinsics.checkNotNullParameter(r, "r");
        if ($seq == this$0.searchSeq && !this$0.isFinishing()) {
            if (!r.optBoolean("ok")) {
                String e = r.optString("error");
                TextView textView = this$0.status;
                if (textView == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("status");
                    textView = null;
                }
                Intrinsics.checkNotNull(e);
                if (StringsKt.contains((CharSequence) e, (CharSequence) "unknown action", false)) {
                    str = "The comma needs the StarView navigation update for this";
                } else {
                    String str2 = e;
                    if (str2.length() == 0) {
                        str2 = "No answer from the comma";
                    }
                    str = str2;
                }
                textView.setText(str);
                return Unit.INSTANCE;
            }
            JSONArray a = r.optJSONArray("results");
            if (a == null) {
                a = new JSONArray();
            }
            Iterable $this$mapNotNull$iv = RangesKt.until(0, a.length());
            Collection destination$iv$iv = new ArrayList();
            Iterator<Integer> it = $this$mapNotNull$iv.iterator();
            while (it.hasNext()) {
                int element$iv$iv$iv = ((IntIterator) it).nextInt();
                JSONObject optJSONObject = a.optJSONObject(element$iv$iv$iv);
                if (optJSONObject != null) {
                    destination$iv$iv.add(optJSONObject);
                }
            }
            Iterable $this$map$iv = (List) destination$iv$iv;
            Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            for (Object item$iv$iv : $this$map$iv) {
                JSONObject o = (JSONObject) item$iv$iv;
                String optString = o.optString("name");
                Intrinsics.checkNotNullExpressionValue(optString, "optString(...)");
                String optString2 = o.optString("address");
                Intrinsics.checkNotNullExpressionValue(optString2, "optString(...)");
                double optDouble = o.optDouble("latitude");
                double optDouble2 = o.optDouble("longitude");
                JSONArray a2 = a;
                double optDouble3 = o.optDouble("distanceMeters", Double.NaN);
                String optString3 = o.optString("maki");
                if (optString3.length() == 0) {
                    optString3 = o.optString("category");
                }
                Intrinsics.checkNotNullExpressionValue(optString3, "ifEmpty(...)");
                destination$iv$iv2.add(new Place(optString, optString2, optDouble, optDouble2, null, optDouble3, optString3, false, 16, null));
                a = a2;
            }
            List it2 = (List) destination$iv$iv2;
            if (r.optBoolean("alongRoute")) {
                sortedWith = it2;
            } else {
                List $this$sortedBy$iv = it2;
                sortedWith = CollectionsKt.sortedWith($this$sortedBy$iv, new Comparator() { // from class: com.starpilot.starview.DestinationActivity$quick$lambda$42$lambda$41$$inlined$sortedBy$1
                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // java.util.Comparator
                    public final int compare(Object t, Object t2) {
                        Double dist;
                        double doubleValue;
                        Double dist2;
                        double doubleValue2;
                        DestinationActivity.Place p = (DestinationActivity.Place) t;
                        Double valueOf = Double.valueOf(p.getDistM());
                        double d = valueOf.doubleValue();
                        if (!((Double.isInfinite(d) || Double.isNaN(d)) ? false : true)) {
                            valueOf = null;
                        }
                        if (valueOf != null) {
                            doubleValue = valueOf.doubleValue();
                        } else {
                            dist = this$0.dist(p);
                            doubleValue = (dist != null ? dist.doubleValue() : 99.0d) * 1609.344d;
                        }
                        Double valueOf2 = Double.valueOf(doubleValue);
                        DestinationActivity.Place p2 = (DestinationActivity.Place) t2;
                        Double valueOf3 = Double.valueOf(p2.getDistM());
                        double d2 = valueOf3.doubleValue();
                        Double d3 = (!Double.isInfinite(d2) && !Double.isNaN(d2)) ? valueOf3 : null;
                        if (d3 != null) {
                            doubleValue2 = d3.doubleValue();
                        } else {
                            dist2 = this$0.dist(p2);
                            doubleValue2 = (dist2 != null ? dist2.doubleValue() : 99.0d) * 1609.344d;
                        }
                        return ComparisonsKt.compareValues(valueOf2, Double.valueOf(doubleValue2));
                    }
                });
            }
            this$0.results = sortedWith;
            this$0.resultsTitle = StringsKt.substringAfter($label, "  ", $label) + (r.optBoolean("alongRoute") ? " along the route" : " nearby");
            this$0.render();
            return Unit.INSTANCE;
        }
        return Unit.INSTANCE;
    }

    private final void androidSearch(final String q, final int seq) {
        TextView textView = null;
        if (!Geocoder.isPresent()) {
            TextView textView2 = this.status;
            if (textView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("status");
            } else {
                textView = textView2;
            }
            textView.setText("Address search isn't available on this tablet (needs Google Play services)");
            return;
        }
        TextView textView3 = this.status;
        if (textView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("status");
        } else {
            textView = textView3;
        }
        textView.setText("Searching addresses…");
        final Pair pos = this.here;
        this.bg.execute(new Runnable() { // from class: com.starpilot.starview.DestinationActivity$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                DestinationActivity.androidSearch$lambda$48(DestinationActivity.this, pos, q, seq);
            }
        });
    }

    /* VF: from Vineflower (jadx: Removed duplicated region;incorrect) */
    public static final void androidSearch$lambda$48(DestinationActivity var0, Pair var1, String var2, int var3) {
        List out;
        try {
            Geocoder g = new Geocoder((Context) var0, Locale.getDefault());
            List<Address> found = null;
            if (var1 != null) {
                double dLon = 0.45 / RangesKt.coerceAtLeast(Math.cos(Math.toRadians(((Number) var1.getFirst()).doubleValue())), 0.2);
                found = g.getFromLocationName(var2, 8, ((Number) var1.getFirst()).doubleValue() - 0.45, ((Number) var1.getSecond()).doubleValue() - dLon, ((Number) var1.getFirst()).doubleValue() + 0.45, ((Number) var1.getSecond()).doubleValue() + dLon);
            }
            if (found == null || found.isEmpty()) {
                found = g.getFromLocationName(var2, 8);
            }
            List<Address> all = found != null ? found : CollectionsKt.emptyList();
            List<Address> located = new ArrayList();
            for (Address a : all) {
                if (a.hasLatitude() && a.hasLongitude()) {
                    located.add(a);
                }
            }
            ArrayList places = new ArrayList(CollectionsKt.collectionSizeOrDefault(located, 10));
            for (Address a2 : located) {
                String line = a2.getAddressLine(0);
                if (line == null) {
                    line = CollectionsKt.joinToString(CollectionsKt.listOfNotNull(new String[]{a2.getThoroughfare(), a2.getLocality()}), ", ", "", "", -1, "...", null);
                }
                String feat = a2.getFeatureName();
                if (feat == null) {
                    feat = "";
                }
                boolean letter = false;
                if (feat.length() > 0) {
                    for (int i = 0; i < feat.length(); i++) {
                        if (Character.isLetter(feat.charAt(i))) {
                            letter = true;
                            break;
                        }
                    }
                }
                String name = (letter && !StringsKt.startsWith(line, feat, false)) ? feat : StringsKt.substringBefore(line, ",", line);
                places.add(new DestinationActivity.Place(name, line, a2.getLatitude(), a2.getLongitude(), null, 0.0, null, false, 240, null));
            }
            out = places;
        } catch (Exception e) {
            out = null;
        }
        var0.ui.post(new DestinationActivity$$ExternalSyntheticLambda9(var3, var0, out));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void androidSearch$lambda$48$lambda$47(int $seq, final DestinationActivity this$0, List $found) {
        if ($seq != this$0.searchSeq || this$0.isFinishing()) {
            return;
        }
        if ($found != null) {
            List $this$sortedBy$iv = $found;
            this$0.results = CollectionsKt.sortedWith($this$sortedBy$iv, new Comparator() { // from class: com.starpilot.starview.DestinationActivity$androidSearch$lambda$48$lambda$47$$inlined$sortedBy$1
                /* JADX WARN: Multi-variable type inference failed */
                @Override // java.util.Comparator
                public final int compare(Object t, Object t2) {
                    Double dist;
                    Double dist2;
                    DestinationActivity.Place it = (DestinationActivity.Place) t;
                    dist = this$0.dist(it);
                    Double valueOf = Double.valueOf(dist != null ? dist.doubleValue() : 0.0d);
                    DestinationActivity.Place it2 = (DestinationActivity.Place) t2;
                    dist2 = this$0.dist(it2);
                    return ComparisonsKt.compareValues(valueOf, Double.valueOf(dist2 != null ? dist2.doubleValue() : 0.0d));
                }
            });
            this$0.resultsTitle = "";
            this$0.render();
            return;
        }
        TextView textView = this$0.status;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("status");
            textView = null;
        }
        textView.setText("Search failed - is the tablet online?");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Double dist(Place p) {
        Pair h = this.here;
        if (h == null) {
            return null;
        }
        double lat = p.getLat();
        if (!((Double.isInfinite(lat) || Double.isNaN(lat)) ? false : true)) {
            return null;
        }
        double dx = (p.getLon() - ((Number) h.getSecond()).doubleValue()) * 111320.0d * Math.cos(Math.toRadians(((Number) h.getFirst()).doubleValue()));
        double dy = (p.getLat() - ((Number) h.getFirst()).doubleValue()) * 110540.0d;
        return Double.valueOf(Math.hypot(dx, dy) / 1609.344d);
    }

    private final void render() {
        boolean z;
        String str;
        LinearLayout linearLayout = this.list;
        TextView textView = null;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("list");
            linearLayout = null;
        }
        linearLayout.removeAllViews();
        List res = this.results;
        if (res != null) {
            TextView textView2 = this.status;
            if (textView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("status");
            } else {
                textView = textView2;
            }
            if (res.isEmpty()) {
                str = "Nothing found - try adding the town";
            } else {
                str = this.resultsTitle;
                if (str.length() == 0) {
                    str = res.size() + " found";
                }
            }
            textView.setText(str);
            for (Place p : (Iterable<Place>) res) {
                row(p);
            }
            return;
        }
        TextView textView3 = this.status;
        if (textView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("status");
        } else {
            textView = textView3;
        }
        textView.setText(this.here == null ? "No GPS position yet - results won't be sorted by distance" : "");
        if (!this.favorites.isEmpty()) {
            section("Saved places");
            Iterable $this$forEach$iv = this.favorites;
            for (Object element$iv : $this$forEach$iv) {
                row((Place) element$iv);
            }
        }
        Iterable $this$map$iv = Companion.recents(this);
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        for (Object item$iv$iv : $this$map$iv) {
            JSONObject it = (JSONObject) item$iv$iv;
            String optString = it.optString("name");
            Intrinsics.checkNotNullExpressionValue(optString, "optString(...)");
            String optString2 = it.optString("address");
            Intrinsics.checkNotNullExpressionValue(optString2, "optString(...)");
            destination$iv$iv.add(new Place(optString, optString2, it.optDouble("latitude"), it.optDouble("longitude"), null, 0.0d, null, false, 240, null));
        }
        List mine = (List) destination$iv$iv;
        if (!mine.isEmpty()) {
            section("Recent on this tablet");
            List $this$forEach$iv2 = mine;
            for (Object element$iv2 : $this$forEach$iv2) {
                row((Place) element$iv2);
            }
        }
        Iterable $this$forEach$iv3 = this.spRecents;
        Iterable $this$filter$iv = $this$forEach$iv3;
        Collection destination$iv$iv2 = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            Place s = (Place) element$iv$iv;
            List $this$none$iv = mine;
            if (!($this$none$iv instanceof Collection) || !$this$none$iv.isEmpty()) {
                Iterator it2 = $this$none$iv.iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        z = true;
                        break;
                    }
                    Object element$iv3 = it2.next();
                    Place it3 = (Place) element$iv3;
                    if (Math.abs(it3.getLat() - s.getLat()) < 1.0E-4d && Math.abs(it3.getLon() - s.getLon()) < 1.0E-4d) {
                        z = false;
                        break;
                    }
                }
            } else {
                z = true;
            }
            if (z) {
                destination$iv$iv2.add(element$iv$iv);
            }
        }
        List sp = (List) destination$iv$iv2;
        if (!sp.isEmpty()) {
            section("Recent in StarPilot");
            List $this$forEach$iv4 = sp;
            for (Object element$iv4 : $this$forEach$iv4) {
                row((Place) element$iv4);
            }
        }
        if (this.favorites.isEmpty() && mine.isEmpty() && sp.isEmpty()) {
            section("Type a place or an address, or pick a kind of place above");
        }
    }

    private final void section(String title) {
        LinearLayout linearLayout = this.list;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("list");
            linearLayout = null;
        }
        TextView $this$section_u24lambda_u2456 = new TextView(this);
        Locale US = Locale.US;
        Intrinsics.checkNotNullExpressionValue(US, "US");
        String upperCase = title.toUpperCase(US);
        Intrinsics.checkNotNullExpressionValue(upperCase, "toUpperCase(...)");
        $this$section_u24lambda_u2456.setText(upperCase);
        $this$section_u24lambda_u2456.setTextColor(Color.rgb(110, 118, 130));
        $this$section_u24lambda_u2456.setTextSize(13.0f);
        $this$section_u24lambda_u2456.setLetterSpacing(0.08f);
        $this$section_u24lambda_u2456.setTypeface($this$section_u24lambda_u2456.getTypeface(), 1);
        $this$section_u24lambda_u2456.setPadding(dp(4), dp(18), dp(4), dp(6));
        linearLayout.addView($this$section_u24lambda_u2456);
    }

    private final String icon(String kind) {
        if (StringsKt.contains((CharSequence) kind, (CharSequence) "fuel", false) || StringsKt.contains((CharSequence) kind, (CharSequence) "gas", false)) {
            return "⛽";
        }
        if (StringsKt.contains((CharSequence) kind, (CharSequence) "charging", false)) {
            return "⚡";
        }
        if (StringsKt.contains((CharSequence) kind, (CharSequence) "cafe", false) || StringsKt.contains((CharSequence) kind, (CharSequence) "coffee", false)) {
            return "☕";
        }
        if (StringsKt.contains((CharSequence) kind, (CharSequence) "fast", false)) {
            return "🍔";
        }
        if (StringsKt.contains((CharSequence) kind, (CharSequence) "restaurant", false) || StringsKt.contains((CharSequence) kind, (CharSequence) "food", false)) {
            return "🍽";
        }
        if (StringsKt.contains((CharSequence) kind, (CharSequence) "parking", false)) {
            return "🅿";
        }
        if (StringsKt.contains((CharSequence) kind, (CharSequence) "grocery", false) || StringsKt.contains((CharSequence) kind, (CharSequence) "shop", false) || StringsKt.contains((CharSequence) kind, (CharSequence) "super", false)) {
            return "🛒";
        }
        if (StringsKt.contains((CharSequence) kind, (CharSequence) "pharmacy", false) || StringsKt.contains((CharSequence) kind, (CharSequence) "hospital", false) || StringsKt.contains((CharSequence) kind, (CharSequence) "doctor", false)) {
            return "💊";
        }
        if (StringsKt.contains((CharSequence) kind, (CharSequence) "lodging", false) || StringsKt.contains((CharSequence) kind, (CharSequence) "hotel", false)) {
            return "🛏";
        }
        if (kind.length() == 0) {
        }
        return "📍";
    }

    private final void row(final Place p) {
        String str;
        LinearLayout r = new LinearLayout(this);
        r.setOrientation(0);
        r.setGravity(16);
        r.setPadding(dp(14), dp(12), dp(14), dp(12));
        GradientDrawable $this$row_u24lambda_u2459_u24lambda_u2457 = new GradientDrawable();
        $this$row_u24lambda_u2459_u24lambda_u2457.setCornerRadius(dp(12));
        $this$row_u24lambda_u2459_u24lambda_u2457.setColor(Color.rgb(22, 25, 32));
        r.setBackground($this$row_u24lambda_u2459_u24lambda_u2457);
        r.setClickable(true);
        TypedValue tv = new TypedValue();
        r.getContext().getTheme().resolveAttribute(16843534, tv, true);
        r.setForeground(r.getContext().getDrawable(tv.resourceId));
        r.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.DestinationActivity$$ExternalSyntheticLambda6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DestinationActivity.this.pick(p);
            }
        });
        double lat = p.getLat();
        final JSONObject fav = !Double.isInfinite(lat) && !Double.isNaN(lat) ? Favorites.INSTANCE.find(p.getLat(), p.getLon()) : null;
        r.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.starpilot.starview.DestinationActivity$$ExternalSyntheticLambda7
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                boolean row$lambda$60;
                row$lambda$60 = DestinationActivity.row$lambda$60(DestinationActivity.this, p, fav, view);
                return row$lambda$60;
            }
        });
        TextView $this$row_u24lambda_u2461 = new TextView(this);
        $this$row_u24lambda_u2461.setText(fav != null ? Favorites.INSTANCE.icon(fav) : icon(p.getKind()));
        $this$row_u24lambda_u2461.setTextSize(22.0f);
        $this$row_u24lambda_u2461.setPadding(0, 0, dp(14), 0);
        r.addView($this$row_u24lambda_u2461);
        LinearLayout texts = new LinearLayout(this);
        texts.setOrientation(1);
        TextView $this$row_u24lambda_u2464 = new TextView(this);
        String name = p.getName();
        if (name.length() == 0) {
            name = p.getAddress();
        }
        $this$row_u24lambda_u2464.setText(name);
        $this$row_u24lambda_u2464.setTextColor(-1);
        $this$row_u24lambda_u2464.setTextSize(18.0f);
        $this$row_u24lambda_u2464.setTypeface($this$row_u24lambda_u2464.getTypeface(), 1);
        texts.addView($this$row_u24lambda_u2464);
        if ((p.getAddress().length() > 0) && !Intrinsics.areEqual(p.getAddress(), p.getName())) {
            TextView $this$row_u24lambda_u2465 = new TextView(this);
            $this$row_u24lambda_u2465.setText(p.getAddress());
            $this$row_u24lambda_u2465.setTextColor(Color.rgb(154, 161, 172));
            $this$row_u24lambda_u2465.setTextSize(14.0f);
            texts.addView($this$row_u24lambda_u2465);
        }
        r.addView(texts, new LinearLayout.LayoutParams(0, -2, 1.0f));
        double distM = p.getDistM();
        Double mi = !Double.isInfinite(distM) && !Double.isNaN(distM) ? Double.valueOf(p.getDistM() / 1609.344d) : dist(p);
        if (mi != null) {
            double d = mi.doubleValue();
            TextView $this$row_u24lambda_u2467_u24lambda_u2466 = new TextView(this);
            if (d < 0.1d) {
                str = "here";
            } else {
                String format = d < 10.0d ? String.format("%.1f mi", Arrays.copyOf(new Object[]{Double.valueOf(d)}, 1)) : String.format("%.0f mi", Arrays.copyOf(new Object[]{Double.valueOf(d)}, 1));
                Intrinsics.checkNotNullExpressionValue(format, "format(...)");
                str = format;
            }
            $this$row_u24lambda_u2467_u24lambda_u2466.setText(str);
            $this$row_u24lambda_u2467_u24lambda_u2466.setTextColor(Color.rgb(154, 161, 172));
            $this$row_u24lambda_u2467_u24lambda_u2466.setTextSize(15.0f);
            $this$row_u24lambda_u2467_u24lambda_u2466.setPadding(dp(12), 0, 0, 0);
            r.addView($this$row_u24lambda_u2467_u24lambda_u2466);
        }
        boolean pinned = fav != null && Favorites.INSTANCE.pinned(new UiSettings(this), p.getLat(), p.getLon());
        TextView $this$row_u24lambda_u2469 = new TextView(this);
        $this$row_u24lambda_u2469.setText(fav == null ? "☆" : pinned ? "★📌" : "★");
        $this$row_u24lambda_u2469.setTextSize(24.0f);
        $this$row_u24lambda_u2469.setTextColor(fav == null ? Color.rgb(154, 161, 172) : Color.rgb(255, 200, 87));
        $this$row_u24lambda_u2469.setPadding(dp(16), 0, dp(4), 0);
        $this$row_u24lambda_u2469.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.DestinationActivity$$ExternalSyntheticLambda8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DestinationActivity.this.placeMenu(p, fav);
            }
        });
        r.addView($this$row_u24lambda_u2469);
        LinearLayout linearLayout = this.list;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("list");
            linearLayout = null;
        }
        LinearLayout.LayoutParams $this$row_u24lambda_u2470 = new LinearLayout.LayoutParams(-1, -2);
        $this$row_u24lambda_u2470.bottomMargin = dp(8);
        Unit unit = Unit.INSTANCE;
        linearLayout.addView(r, $this$row_u24lambda_u2470);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean row$lambda$60(DestinationActivity this$0, Place $p, JSONObject $fav, View it) {
        this$0.placeMenu($p, $fav);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void placeMenu(final Place p, final JSONObject fav) {
        final DataClient data = Link.INSTANCE.getData();
        final UiSettings s = new UiSettings(this);
        if (fav == null) {
            final String[] opts = {"☆  Save", "🏠  Save as Home", "💼  Save as Work"};
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            String name = p.getName();
            if (name.length() == 0) {
                name = p.getAddress();
            }
            builder.setTitle(name).setItems(opts, new DialogInterface.OnClickListener() { // from class: com.starpilot.starview.DestinationActivity$$ExternalSyntheticLambda14
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    DestinationActivity.placeMenu$lambda$74(opts, p, data, DestinationActivity.this, dialogInterface, i);
                }
            }).setNegativeButton("Cancel", (DialogInterface.OnClickListener) null).show();
            return;
        }
        final boolean pinned = Favorites.INSTANCE.pinned(s, p.getLat(), p.getLon());
        String[] strArr = new String[5];
        strArr[0] = pinned ? "📌  Take off the main screen" : "📌  Show on the main screen (one tap to go)";
        strArr[1] = "🏠  Make it Home";
        strArr[2] = "💼  Make it Work";
        String optString = fav.optString("label");
        Intrinsics.checkNotNullExpressionValue(optString, "optString(...)");
        strArr[3] = optString.length() > 0 ? "★  Just a saved place (not Home / Work)" : null;
        strArr[4] = "🗑  Remove from saved places";
        Collection $this$toTypedArray$iv = ArraysKt.filterNotNull(strArr);
        final String[] opts2 = (String[]) $this$toTypedArray$iv.toArray(new String[0]);
        new AlertDialog.Builder(this).setTitle(Favorites.INSTANCE.title(fav)).setItems(opts2, new DialogInterface.OnClickListener() { // from class: com.starpilot.starview.DestinationActivity$$ExternalSyntheticLambda15
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                DestinationActivity.placeMenu$lambda$79(opts2, s, fav, pinned, data, DestinationActivity.this, dialogInterface, i);
            }
        }).setNegativeButton("Cancel", (DialogInterface.OnClickListener) null).show();
    }

    private static final void placeMenu$done(DestinationActivity this$0, String e, String ok) {
        Toast.makeText(this$0, e == null ? ok : e, 0).show();
        this$0.favorites = this$0.places(new JSONArray((Collection) Favorites.INSTANCE.getList()), "name");
        this$0.render();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void placeMenu$lambda$74(String[] $opts, Place $p, DataClient $data, final DestinationActivity this$0, DialogInterface dialogInterface, int i) {
        String label;
        switch (i) {
            case 1:
                label = "home";
                break;
            case 2:
                label = "work";
                break;
            default:
                label = null;
                break;
        }
        final String ok = "Saved" + (label != null ? " as " + StringsKt.substringAfter($opts[i], "as ", ($opts[i])) : "");
        double lat = $p.getLat();
        if ((Double.isInfinite(lat) || Double.isNaN(lat)) ? false : true) {
            Favorites.INSTANCE.save($data, $p.getName(), $p.getAddress(), $p.getLat(), $p.getLon(), label, new Function1() { // from class: com.starpilot.starview.DestinationActivity$$ExternalSyntheticLambda10
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    Unit placeMenu$lambda$74$lambda$72;
                    placeMenu$lambda$74$lambda$72 = DestinationActivity.placeMenu$lambda$74$lambda$72(ok, this$0, (String) obj);
                    return placeMenu$lambda$74$lambda$72;
                }
            });
        } else {
            Favorites.INSTANCE.saveFound(this$0, $data, $p.getName(), $p.getAddress(), label, new Function1() { // from class: com.starpilot.starview.DestinationActivity$$ExternalSyntheticLambda12
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    Unit placeMenu$lambda$74$lambda$73;
                    placeMenu$lambda$74$lambda$73 = DestinationActivity.placeMenu$lambda$74$lambda$73(ok, this$0, (String) obj);
                    return placeMenu$lambda$74$lambda$73;
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit placeMenu$lambda$74$lambda$72(String $ok, DestinationActivity this$0, String e) {
        placeMenu$done(this$0, e, $ok);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit placeMenu$lambda$74$lambda$73(String $ok, DestinationActivity this$0, String e) {
        placeMenu$done(this$0, e, $ok);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void placeMenu$lambda$79(String[] $opts, UiSettings $s, JSONObject $fav, boolean $pinned, DataClient $data, final DestinationActivity this$0, DialogInterface dialogInterface, int i) {
        if (!StringsKt.startsWith($opts[i], "📌", false)) {
            if (!StringsKt.startsWith($opts[i], "🏠", false)) {
                if (!StringsKt.startsWith($opts[i], "💼", false)) {
                    if (!StringsKt.startsWith($opts[i], "★", false)) {
                        Favorites.INSTANCE.remove(this$0, $data, $fav, new Function1() { // from class: com.starpilot.starview.DestinationActivity$$ExternalSyntheticLambda18
                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj) {
                                Unit placeMenu$lambda$79$lambda$78;
                                placeMenu$lambda$79$lambda$78 = DestinationActivity.placeMenu$lambda$79$lambda$78(this$0, (String) obj);
                                return placeMenu$lambda$79$lambda$78;
                            }
                        });
                        return;
                    } else {
                        Favorites.INSTANCE.label($data, $fav, null, new Function1() { // from class: com.starpilot.starview.DestinationActivity$$ExternalSyntheticLambda17
                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj) {
                                Unit placeMenu$lambda$79$lambda$77;
                                placeMenu$lambda$79$lambda$77 = DestinationActivity.placeMenu$lambda$79$lambda$77(this$0, (String) obj);
                                return placeMenu$lambda$79$lambda$77;
                            }
                        });
                        return;
                    }
                }
                Favorites.INSTANCE.label($data, $fav, "work", new Function1() { // from class: com.starpilot.starview.DestinationActivity$$ExternalSyntheticLambda16
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        Unit placeMenu$lambda$79$lambda$76;
                        placeMenu$lambda$79$lambda$76 = DestinationActivity.placeMenu$lambda$79$lambda$76(this$0, (String) obj);
                        return placeMenu$lambda$79$lambda$76;
                    }
                });
                return;
            }
            Favorites.INSTANCE.label($data, $fav, "home", new Function1() { // from class: com.starpilot.starview.DestinationActivity$$ExternalSyntheticLambda11
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    Unit placeMenu$lambda$79$lambda$75;
                    placeMenu$lambda$79$lambda$75 = DestinationActivity.placeMenu$lambda$79$lambda$75(this$0, (String) obj);
                    return placeMenu$lambda$79$lambda$75;
                }
            });
            return;
        }
        Favorites.INSTANCE.setPinned($s, $fav, !$pinned);
        placeMenu$done(this$0, null, $pinned ? "Taken off the main screen" : "On the main screen");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit placeMenu$lambda$79$lambda$75(DestinationActivity this$0, String e) {
        placeMenu$done(this$0, e, "Home set");
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit placeMenu$lambda$79$lambda$76(DestinationActivity this$0, String e) {
        placeMenu$done(this$0, e, "Work set");
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit placeMenu$lambda$79$lambda$77(DestinationActivity this$0, String e) {
        placeMenu$done(this$0, e, "Done");
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit placeMenu$lambda$79$lambda$78(DestinationActivity this$0, String e) {
        placeMenu$done(this$0, e, "Removed");
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void pick(Place p) {
        Intent out = new Intent().putExtra("name", p.getName()).putExtra("address", p.getAddress());
        Intrinsics.checkNotNullExpressionValue(out, "putExtra(...)");
        if (p.getMapboxId().length() > 0) {
            out.putExtra("mapbox_id", p.getMapboxId()).putExtra("search_id", this.searchId);
        } else {
            if (p.getKeep()) {
                Companion.remember(this, p.getName(), p.getAddress(), p.getLat(), p.getLon());
            }
            out.putExtra("latitude", p.getLat()).putExtra("longitude", p.getLon());
        }
        setResult(-1, out);
        finish();
    }

    // recovered from Vineflower (R8 lambda body jadx inlined)
    static final void onCreate$lambda$4$lambda$3(DestinationActivity var0, View var1) {
       var0.finish();
    }
}
