package com.starpilot.starview;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.starpilot.starview.FileKinds;
import com.starpilot.starview.FilesActivity;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.sequences.Sequence;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import org.json.JSONArray;
import org.json.JSONObject;
import android.text.TextUtils.TruncateAt;
import android.widget.LinearLayout.LayoutParams;
import kotlin.jvm.internal.Ref.ObjectRef;

/* compiled from: FilesActivity.kt */
public final class FilesActivity extends AppCompatActivity {
    private Button allFilesBtn;
    private volatile boolean busy;
    private Button cancelBtn;
    private Pane comma;
    private Button copyBtn;
    private TextView copyHint;
    private Button pauseBtn;
    private ProgressBar progBar;
    private LinearLayout progBox;
    private TextView progTv;
    private LinearLayout queueList;
    private Pane tablet;
    private final Handler ui = new Handler(Looper.getMainLooper());
    private final ExecutorService io = Executors.newSingleThreadExecutor();
    private final OkHttpClient http = new OkHttpClient.Builder().addInterceptor(Link.INSTANCE.getAuthInterceptor()).connectTimeout(5, TimeUnit.SECONDS).readTimeout(0, TimeUnit.MILLISECONDS).writeTimeout(0, TimeUnit.MILLISECONDS).build();
    private final int dim = Color.rgb(143, 163, 199);
    private float d = 1.0f;
    private final AtomicBoolean cancel = new AtomicBoolean(false);
    private final ArrayList<QRow> queueRows = new ArrayList<>();
    private final AtomicBoolean paused = new AtomicBoolean(false);
    private final SimpleDateFormat dateFmt = new SimpleDateFormat("MM-dd HH:mm", Locale.US);

    /* compiled from: FilesActivity.kt */
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[Clash.values().length];
            try {
                iArr[Clash.CANCEL.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[Clash.SKIP.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[Clash.KEEP_BOTH.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                iArr[Clash.REPLACE.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[QState.values().length];
            try {
                iArr2[QState.WAITING.ordinal()] = 1;
            } catch (NoSuchFieldError e5) {
            }
            try {
                iArr2[QState.COPYING.ordinal()] = 2;
            } catch (NoSuchFieldError e6) {
            }
            try {
                iArr2[QState.DONE.ordinal()] = 3;
            } catch (NoSuchFieldError e7) {
            }
            try {
                iArr2[QState.FAILED.ordinal()] = 4;
            } catch (NoSuchFieldError e8) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: FilesActivity.kt */
    static final /* data */ class Entry {
        private final boolean dir;
        private final long mtime;
        private final String name;
        private final long size;

        public static /* synthetic */ Entry copy$default(Entry entry, String str, boolean z, long j, long j2, int i, Object obj) {
            if ((i & 1) != 0) {
                str = entry.name;
            }
            if ((i & 2) != 0) {
                z = entry.dir;
            }
            boolean z2 = z;
            if ((i & 4) != 0) {
                j = entry.size;
            }
            long j3 = j;
            if ((i & 8) != 0) {
                j2 = entry.mtime;
            }
            return entry.copy(str, z2, j3, j2);
        }

        /* renamed from: component1, reason: from getter */
        public final String getName() {
            return this.name;
        }

        /* renamed from: component2, reason: from getter */
        public final boolean getDir() {
            return this.dir;
        }

        /* renamed from: component3, reason: from getter */
        public final long getSize() {
            return this.size;
        }

        /* renamed from: component4, reason: from getter */
        public final long getMtime() {
            return this.mtime;
        }

        public final Entry copy(String name, boolean dir, long size, long mtime) {
            Intrinsics.checkNotNullParameter(name, "name");
            return new Entry(name, dir, size, mtime);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Entry)) {
                return false;
            }
            Entry entry = (Entry) other;
            return Intrinsics.areEqual(this.name, entry.name) && this.dir == entry.dir && this.size == entry.size && this.mtime == entry.mtime;
        }

        public int hashCode() {
            return (((((this.name.hashCode() * 31) + Boolean.hashCode(this.dir)) * 31) + Long.hashCode(this.size)) * 31) + Long.hashCode(this.mtime);
        }

        public String toString() {
            return "Entry(name=" + this.name + ", dir=" + this.dir + ", size=" + this.size + ", mtime=" + this.mtime + ")";
        }

        public Entry(String name, boolean dir, long size, long mtime) {
            Intrinsics.checkNotNullParameter(name, "name");
            this.name = name;
            this.dir = dir;
            this.size = size;
            this.mtime = mtime;
        }

        public final boolean getDir() {
            return this.dir;
        }

        public final long getMtime() {
            return this.mtime;
        }

        public final String getName() {
            return this.name;
        }

        public final long getSize() {
            return this.size;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: FilesActivity.kt */
    final class Pane {
        private List<Entry> entries;
        public TextView info;
        private String lastError;
        public LinearLayout list;
        private String path;
        public TextView pathTv;
        private final boolean remote;
        public LinearLayout root;
        private final LinkedHashSet<String> selected;
        private boolean sortAsc;
        private final LinkedHashMap<String, TextView> sortBtns;
        private String sortKey;
        final /* synthetic */ FilesActivity this$0;
        private final String title;

        public Pane(FilesActivity this$0, boolean remote, String title) {
            String absolutePath;
            Intrinsics.checkNotNullParameter(title, "title");
            this.this$0 = this$0;
            this.remote = remote;
            this.title = title;
            if (this.remote) {
                absolutePath = "/data";
            } else {
                absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
                Intrinsics.checkNotNullExpressionValue(absolutePath, "getAbsolutePath(...)");
            }
            this.path = absolutePath;
            this.entries = CollectionsKt.emptyList();
            this.selected = new LinkedHashSet<>();
            this.sortKey = "name";
            this.sortAsc = true;
            this.sortBtns = new LinkedHashMap<>();
        }

        public final boolean getRemote() {
            return this.remote;
        }

        public final String getTitle() {
            return this.title;
        }

        public final String getPath() {
            return this.path;
        }

        public final void setPath(String str) {
            Intrinsics.checkNotNullParameter(str, "<set-?>");
            this.path = str;
        }

        public final List<Entry> getEntries() {
            return this.entries;
        }

        public final void setEntries(List<Entry> list) {
            Intrinsics.checkNotNullParameter(list, "<set-?>");
            this.entries = list;
        }

        public final LinkedHashSet<String> getSelected() {
            return this.selected;
        }

        public final TextView getInfo() {
            TextView textView = this.info;
            if (textView != null) {
                return textView;
            }
            Intrinsics.throwUninitializedPropertyAccessException("info");
            return null;
        }

        public final LinearLayout getList() {
            LinearLayout linearLayout = this.list;
            if (linearLayout != null) {
                return linearLayout;
            }
            Intrinsics.throwUninitializedPropertyAccessException("list");
            return null;
        }

        public final TextView getPathTv() {
            TextView textView = this.pathTv;
            if (textView != null) {
                return textView;
            }
            Intrinsics.throwUninitializedPropertyAccessException("pathTv");
            return null;
        }

        public final LinearLayout getRoot() {
            LinearLayout linearLayout = this.root;
            if (linearLayout != null) {
                return linearLayout;
            }
            Intrinsics.throwUninitializedPropertyAccessException("root");
            return null;
        }

        public final void setInfo(TextView textView) {
            Intrinsics.checkNotNullParameter(textView, "<set-?>");
            this.info = textView;
        }

        public final void setList(LinearLayout linearLayout) {
            Intrinsics.checkNotNullParameter(linearLayout, "<set-?>");
            this.list = linearLayout;
        }

        public final void setPathTv(TextView textView) {
            Intrinsics.checkNotNullParameter(textView, "<set-?>");
            this.pathTv = textView;
        }

        public final void setRoot(LinearLayout linearLayout) {
            Intrinsics.checkNotNullParameter(linearLayout, "<set-?>");
            this.root = linearLayout;
        }

        public final String getLastError() {
            return this.lastError;
        }

        public final void setLastError(String str) {
            this.lastError = str;
        }

        public final boolean getSortAsc() {
            return this.sortAsc;
        }

        public final String getSortKey() {
            return this.sortKey;
        }

        public final void setSortAsc(boolean z) {
            this.sortAsc = z;
        }

        public final void setSortKey(String str) {
            Intrinsics.checkNotNullParameter(str, "<set-?>");
            this.sortKey = str;
        }

        /* VF: from Vineflower (jadx: ) */
        private final List<FilesActivity.Entry> sorted(List<FilesActivity.Entry> var1) {
            Comparator var5;
            label28: {
                String var2 = this.sortKey;
                switch (var2.hashCode()) {
                    case 3076014:
                        if (var2.equals("date")) {
                            var5 = new Comparator() {
                                @Override
                                public final int compare(T var1, T var2x) {
                                    return ComparisonsKt.compareValues(((FilesActivity.Entry)var1).getMtime(), ((FilesActivity.Entry)var2x).getMtime());
                                }
                            };
                            break label28;
                        }
                        break;
                    case 3530753:
                        if (var2.equals("size")) {
                            var5 = new Comparator() {
                                @Override
                                public final int compare(T var1, T var2) {
                                    return ComparisonsKt.compareValues(((FilesActivity.Entry)var1).getSize(), ((FilesActivity.Entry)var2).getSize());
                                }
                            };
                            break label28;
                        }
                        break;
                    case 3575610:
                        if (var2.equals("type")) {
                            var5 = new Comparator() {
                                final Comparator $this_thenBy = new Comparator() {
                                @Override
                                public final int compare(T var1, T var2) {
                                    var1 = StringsKt.substringAfterLast(((FilesActivity.Entry)var1).getName(), '.', "").toLowerCase(Locale.ROOT);
                                    Intrinsics.checkNotNullExpressionValue(var1, "toLowerCase(...)");
                                    Comparable var4 = var1;
                                    var2 = StringsKt.substringAfterLast(((FilesActivity.Entry)var2).getName(), '.', "").toLowerCase(Locale.ROOT);
                                    Intrinsics.checkNotNullExpressionValue(var2, "toLowerCase(...)");
                                    return ComparisonsKt.compareValues(var4, var2);
                                }
                            };

                                @Override
                                public final int compare(T var1, T var2) {
                                    int var3 = this.$this_thenBy.compare(var1, var2);
                                    if (var3 == 0) {
                                        var1 = ((FilesActivity.Entry)var1).getName().toLowerCase(Locale.ROOT);
                                        Intrinsics.checkNotNullExpressionValue(var1, "toLowerCase(...)");
                                        Comparable var5 = var1;
                                        var2 = ((FilesActivity.Entry)var2).getName().toLowerCase(Locale.ROOT);
                                        Intrinsics.checkNotNullExpressionValue(var2, "toLowerCase(...)");
                                        var3 = ComparisonsKt.compareValues(var5, var2);
                                    }

                                    return var3;
                                }
                            };
                            break label28;
                        }
                }

                var5 = new Comparator() {
                    @Override
                    public final int compare(T var1, T var2) {
                        var1 = ((FilesActivity.Entry)var1).getName().toLowerCase(Locale.ROOT);
                        Intrinsics.checkNotNullExpressionValue(var1, "toLowerCase(...)");
                        Comparable var4 = var1;
                        var2 = ((FilesActivity.Entry)var2).getName().toLowerCase(Locale.ROOT);
                        Intrinsics.checkNotNullExpressionValue(var2, "toLowerCase(...)");
                        return ComparisonsKt.compareValues(var4, var2);
                    }
                };
            }

            Comparator var3 = new Comparator() {
                @Override
                public final int compare(T var1, T var2) {
                    return ComparisonsKt.compareValues(((FilesActivity.Entry)var1).getDir() ^ true, ((FilesActivity.Entry)var2).getDir() ^ true);
                }
            };
            Iterable var4 = var1;
            if (!this.sortAsc) {
                var5 = var5.reversed();
            }

            Intrinsics.checkNotNull(var5);
            return CollectionsKt.sortedWith(var4, ComparisonsKt.then(var3, var5));
        }

        public final void setSort(String key) {
            Intrinsics.checkNotNullParameter(key, "key");
            if (Intrinsics.areEqual(key, this.sortKey)) {
                this.sortAsc = !this.sortAsc;
            } else {
                this.sortKey = key;
                this.sortAsc = Intrinsics.areEqual(key, "name");
            }
            this.entries = sorted(this.entries);
            render();
            updateSortBar();
        }

        /* VF: from Vineflower (jadx: ) */
        private final void updateSortBar() {
            for (Map.Entry var4 : this.sortBtns.entrySet()) {
                String var5 = (String)var4.getKey();
                TextView var7 = (TextView)var4.getValue();
                byte var3 = ((Intrinsics.areEqual(var5, this.sortKey)) ? 1 : 0);
                int var2;
                if (var5.length() > 0) {
                    var2 = 1;
                } else {
                    var2 = 0;
                }

                if ((var2 != 0)) {
                    StringBuilder var10 = new StringBuilder();
                    char var1 = var5.charAt(0);
                    String var8 = String.valueOf(var1);
                    Intrinsics.checkNotNull(var8, "null cannot be cast to non-null type java.lang.String");
                    var8 = var8.toUpperCase(Locale.ROOT);
                    Intrinsics.checkNotNullExpressionValue(var8, "toUpperCase(...)");
                    StringBuilder var11 = var10.append(var8);
                    var5 = var5.substring(1);
                    Intrinsics.checkNotNullExpressionValue(var5, "substring(...)");
                    var5 = var11.append(var5).toString();
                }

                String var12;
                if (var3 != 0) {
                    if (this.sortAsc) {
                        var12 = " ▲";
                    } else {
                        var12 = " ▼";
                    }
                } else {
                    var12 = "";
                }

                var7.setText(var5 + var12);
                if (var3 != 0) {
                    var2 = Color.rgb(111, 227, 161);
                } else {
                    var2 = this.this$0.dim;
                }

                var7.setTextColor(var2);
                var7.setTypeface(null, var3);
            }
        }

        public final View build() {
            LinearLayout $this$build_u24lambda_u247 = new LinearLayout(this.this$0);
            FilesActivity filesActivity = this.this$0;
            $this$build_u24lambda_u247.setOrientation(1);
            $this$build_u24lambda_u247.setPadding(filesActivity.dp(8), 0, filesActivity.dp(8), 0);
            setRoot($this$build_u24lambda_u247);
            LinearLayout head = new LinearLayout(this.this$0);
            head.setOrientation(0);
            head.setGravity(16);
            TextView $this$build_u24lambda_u249 = new TextView(this.this$0);
            $this$build_u24lambda_u249.setText(this.title);
            $this$build_u24lambda_u249.setTextColor(-1);
            $this$build_u24lambda_u249.setTextSize(16.0f);
            $this$build_u24lambda_u249.setTypeface(null, 1);
            head.addView($this$build_u24lambda_u249);
            head.addView(new View(this.this$0), new LinearLayout.LayoutParams(0, 1, 1.0f));
            head.addView(build$small(this.this$0, "↑ Up", new Function0() { // from class: com.starpilot.starview.FilesActivity$Pane$$ExternalSyntheticLambda19
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit build$lambda$15;
                    build$lambda$15 = FilesActivity.Pane.build$lambda$15(FilesActivity.Pane.this);
                    return build$lambda$15;
                }
            }));
            head.addView(build$small(this.this$0, "⟳", new Function0() { // from class: com.starpilot.starview.FilesActivity$Pane$$ExternalSyntheticLambda20
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit build$lambda$16;
                    build$lambda$16 = FilesActivity.Pane.build$lambda$16(FilesActivity.Pane.this);
                    return build$lambda$16;
                }
            }));
            head.addView(build$small(this.this$0, "+ Folder", new Function0() { // from class: com.starpilot.starview.FilesActivity$Pane$$ExternalSyntheticLambda21
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit build$lambda$17;
                    build$lambda$17 = FilesActivity.Pane.build$lambda$17(FilesActivity.Pane.this);
                    return build$lambda$17;
                }
            }));
            head.addView(build$small(this.this$0, "Open", new Function0() { // from class: com.starpilot.starview.FilesActivity$Pane$$ExternalSyntheticLambda22
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit build$lambda$18;
                    build$lambda$18 = FilesActivity.Pane.build$lambda$18(FilesActivity.Pane.this);
                    return build$lambda$18;
                }
            }));
            head.addView(build$small(this.this$0, "Delete", new Function0() { // from class: com.starpilot.starview.FilesActivity$Pane$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit build$lambda$19;
                    build$lambda$19 = FilesActivity.Pane.build$lambda$19(FilesActivity.Pane.this);
                    return build$lambda$19;
                }
            }));
            getRoot().addView(head);
            TextView $this$build_u24lambda_u2421 = new TextView(this.this$0);
            FilesActivity filesActivity2 = this.this$0;
            $this$build_u24lambda_u2421.setTextColor(Color.rgb(111, 227, 161));
            float f = 13.0f;
            $this$build_u24lambda_u2421.setTextSize(13.0f);
            $this$build_u24lambda_u2421.setTypeface(Typeface.MONOSPACE);
            $this$build_u24lambda_u2421.setPadding(filesActivity2.dp(4), filesActivity2.dp(2), filesActivity2.dp(4), filesActivity2.dp(4));
            $this$build_u24lambda_u2421.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.FilesActivity$Pane$$ExternalSyntheticLambda2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    FilesActivity.Pane.this.goToPathDialog();
                }
            });
            setPathTv($this$build_u24lambda_u2421);
            getRoot().addView(getPathTv());
            LinearLayout sortBar = new LinearLayout(this.this$0);
            FilesActivity filesActivity3 = this.this$0;
            sortBar.setOrientation(0);
            sortBar.setGravity(16);
            sortBar.setPadding(filesActivity3.dp(4), 0, filesActivity3.dp(4), filesActivity3.dp(4));
            TextView $this$build_u24lambda_u2423 = new TextView(this.this$0);
            FilesActivity filesActivity4 = this.this$0;
            $this$build_u24lambda_u2423.setText("Sort:");
            $this$build_u24lambda_u2423.setTextColor(filesActivity4.dim);
            $this$build_u24lambda_u2423.setTextSize(12.0f);
            $this$build_u24lambda_u2423.setPadding(0, 0, filesActivity4.dp(8), 0);
            sortBar.addView($this$build_u24lambda_u2423);
            for (final String k : CollectionsKt.listOf((Object[]) new String[]{"name", "size", "date", "type"})) {
                TextView $this$build_u24lambda_u2425 = new TextView(this.this$0);
                FilesActivity filesActivity5 = this.this$0;
                $this$build_u24lambda_u2425.setTextSize(f);
                $this$build_u24lambda_u2425.setPadding(filesActivity5.dp(8), filesActivity5.dp(6), filesActivity5.dp(8), filesActivity5.dp(6));
                $this$build_u24lambda_u2425.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.FilesActivity$Pane$$ExternalSyntheticLambda3
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        FilesActivity.Pane.this.setSort(k);
                    }
                });
                this.sortBtns.put(k, $this$build_u24lambda_u2425);
                sortBar.addView($this$build_u24lambda_u2425);
                f = 13.0f;
            }
            updateSortBar();
            getRoot().addView(sortBar);
            LinearLayout $this$build_u24lambda_u2426 = new LinearLayout(this.this$0);
            $this$build_u24lambda_u2426.setOrientation(1);
            setList($this$build_u24lambda_u2426);
            LinearLayout root = getRoot();
            ScrollView $this$build_u24lambda_u2427 = new ScrollView(this.this$0);
            $this$build_u24lambda_u2427.addView(getList());
            root.addView($this$build_u24lambda_u2427, new LinearLayout.LayoutParams(-1, 0, 1.0f));
            TextView $this$build_u24lambda_u2428 = new TextView(this.this$0);
            FilesActivity filesActivity6 = this.this$0;
            $this$build_u24lambda_u2428.setTextColor(filesActivity6.dim);
            $this$build_u24lambda_u2428.setTextSize(12.0f);
            $this$build_u24lambda_u2428.setPadding(filesActivity6.dp(4), filesActivity6.dp(4), filesActivity6.dp(4), filesActivity6.dp(6));
            setInfo($this$build_u24lambda_u2428);
            getRoot().addView(getInfo());
            return getRoot();
        }

        private static final Button build$small(FilesActivity this$0, String label, final Function0<Unit> function0) {
            Button $this$build_u24small_u24lambda_u2412 = new Button(this$0);
            $this$build_u24small_u24lambda_u2412.setText(label);
            $this$build_u24small_u24lambda_u2412.setTextSize(13.0f);
            $this$build_u24small_u24lambda_u2412.setAllCaps(false);
            $this$build_u24small_u24lambda_u2412.setMinWidth(0);
            $this$build_u24small_u24lambda_u2412.setMinimumWidth(0);
            $this$build_u24small_u24lambda_u2412.setMinHeight(this$0.dp(44));
            $this$build_u24small_u24lambda_u2412.setMinimumHeight(this$0.dp(44));
            $this$build_u24small_u24lambda_u2412.setTextColor(-1);
            GradientDrawable $this$build_u24small_u24lambda_u2412_u24lambda_u2410 = new GradientDrawable();
            $this$build_u24small_u24lambda_u2412_u24lambda_u2410.setCornerRadius(this$0.dp(10));
            $this$build_u24small_u24lambda_u2412_u24lambda_u2410.setColor(Color.rgb(30, 38, 47));
            $this$build_u24small_u24lambda_u2412_u24lambda_u2410.setStroke(this$0.dp(1), Color.rgb(60, 66, 78));
            $this$build_u24small_u24lambda_u2412.setBackground($this$build_u24small_u24lambda_u2412_u24lambda_u2410);
            $this$build_u24small_u24lambda_u2412.setPadding(this$0.dp(12), 0, this$0.dp(12), 0);
            $this$build_u24small_u24lambda_u2412.setStateListAnimator(null);
            $this$build_u24small_u24lambda_u2412.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.FilesActivity$Pane$$ExternalSyntheticLambda6
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    function0.invoke();
                }
            });
            LinearLayout.LayoutParams $this$build_u24small_u24lambda_u2414_u24lambda_u2413 = new LinearLayout.LayoutParams(-2, this$0.dp(44));
            $this$build_u24small_u24lambda_u2414_u24lambda_u2413.leftMargin = this$0.dp(6);
            $this$build_u24small_u24lambda_u2412.setLayoutParams($this$build_u24small_u24lambda_u2414_u24lambda_u2413);
            return $this$build_u24small_u24lambda_u2412;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit build$lambda$15(Pane this$0) {
            this$0.up();
            return Unit.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit build$lambda$16(Pane this$0) {
            this$0.refresh();
            return Unit.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit build$lambda$17(Pane this$0) {
            this$0.mkdir();
            return Unit.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit build$lambda$18(Pane this$0) {
            this$0.openSelected();
            return Unit.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit build$lambda$19(Pane this$0) {
            this$0.deleteSelected();
            return Unit.INSTANCE;
        }

        public final void up() {
            if (Intrinsics.areEqual(this.path, "/")) {
                return;
            }
            String parent = new File(this.path).getParent();
            this.path = parent != null ? parent : "/";
            this.selected.clear();
            refresh();
        }

        public final void enter(String name) {
            StringBuilder append;
            Intrinsics.checkNotNullParameter(name, "name");
            if (Intrinsics.areEqual(this.path, "/")) {
                append = new StringBuilder();
            } else {
                append = new StringBuilder().append(this.path);
            }
            this.path = append.append("/").append(name).toString();
            this.selected.clear();
            refresh();
        }

        public final void refresh() {
            String str;
            getPathTv().setText(this.path);
            if (this.remote) {
                final String h = this.this$0.getHost();
                if (h != null) {
                    ExecutorService executorService = this.this$0.io;
                    final FilesActivity filesActivity = this.this$0;
                    executorService.execute(new Runnable() { // from class: com.starpilot.starview.FilesActivity$Pane$$ExternalSyntheticLambda16
                        @Override // java.lang.Runnable
                        public final void run() {
                            FilesActivity.Pane.refresh$lambda$32(FilesActivity.this, h, this);
                        }
                    });
                    return;
                } else {
                    Pane $this$refresh_u24lambda_u2429 = this;
                    $this$refresh_u24lambda_u2429.getInfo().setText("not connected");
                    return;
                }
            }
            File f = new File(this.path);
            File[] kids = f.listFiles();
            if (kids == null) {
                this.entries = CollectionsKt.emptyList();
                render();
                TextView info = getInfo();
                if (this.this$0.hasAllFiles()) {
                    str = "cannot read " + this.path;
                } else {
                    str = "tap 'Allow all files' to browse tablet storage";
                }
                info.setText(str);
                return;
            }
            File[] fileArr = kids;
            int $i$f$map = 0;
            Collection destination$iv$iv = new ArrayList(fileArr.length);
            int length = fileArr.length;
            int i = 0;
            while (i < length) {
                File file = fileArr[i];
                String name = file.getName();
                Intrinsics.checkNotNullExpressionValue(name, "getName(...)");
                destination$iv$iv.add(new Entry(name, file.isDirectory(), file.length(), file.lastModified() / 1000));
                i++;
                $i$f$map = $i$f$map;
                kids = kids;
                fileArr = fileArr;
            }
            this.entries = sorted((List) destination$iv$iv);
            render();
            getInfo().setText(this.entries.size() + " items · free " + this.this$0.fmtSize(f.getUsableSpace()));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void refresh$lambda$32(final FilesActivity this$0, String $h, final Pane this$1) {
            try {
                try {
                } catch (Exception e) {
                    e = e;
                }
                try {
                    JSONObject j = this$0.getJson("http://" + $h + ":8090/fs/list?path=" + Uri.encode(this$1.path));
                    JSONArray arr = j.getJSONArray("entries");
                    final ArrayList es = new ArrayList();
                    int length = arr.length();
                    for (int i = 0; i < length; i++) {
                        JSONObject e2 = arr.getJSONObject(i);
                        String string = e2.getString("name");
                        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
                        es.add(new Entry(string, e2.getBoolean("dir"), e2.optLong("size"), e2.optLong("mtime")));
                    }
                    final long free = j.optLong("free");
                    final long total = j.optLong("total");
                    this$0.ui.post(new Runnable() { // from class: com.starpilot.starview.FilesActivity$Pane$$ExternalSyntheticLambda8
                        @Override // java.lang.Runnable
                        public final void run() {
                            FilesActivity.Pane.refresh$lambda$32$lambda$30(this$1, es, this$0, free, total);
                        }
                    });
                } catch (Exception e3) {
                    e = e3;
                    this$0.ui.post(new Runnable() { // from class: com.starpilot.starview.FilesActivity$Pane$$ExternalSyntheticLambda9
                        @Override // java.lang.Runnable
                        public final void run() {
                            FilesActivity.Pane.refresh$lambda$32$lambda$31(this$1, e);
                        }
                    });
                }
            } catch (Exception e4) {
                e = e4;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void refresh$lambda$32$lambda$30(Pane this$0, ArrayList $es, FilesActivity this$1, long $free, long $total) {
            this$0.entries = this$0.sorted($es);
            this$0.lastError = null;
            this$0.render();
            this$0.getInfo().setText($es.size() + " items · free " + this$1.fmtSize($free) + " of " + this$1.fmtSize($total));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void refresh$lambda$32$lambda$31(Pane this$0, Exception $e) {
            this$0.lastError = $e.getMessage();
            this$0.entries = CollectionsKt.emptyList();
            this$0.render();
            this$0.getInfo().setText("error: " + $e.getMessage());
        }

        public final void render() {
            getList().removeAllViews();
            for (final Entry e : this.entries) {
                LinearLayout row = new LinearLayout(this.this$0);
                FilesActivity filesActivity = this.this$0;
                row.setOrientation(0);
                row.setGravity(16);
                row.setPadding(filesActivity.dp(10), filesActivity.dp(9), filesActivity.dp(10), filesActivity.dp(9));
                row.setBackgroundResource(R.drawable.card_bg);
                boolean sel = this.selected.contains(e.getName());
                row.setAlpha(1.0f);
                if (sel) {
                    row.setBackgroundColor(Color.rgb(22, 80, 50));
                }
                FileKinds.Kind kind = FileKinds.INSTANCE.of(e.getName(), e.getDir());
                View $this$render_u24lambda_u2435 = new View(this.this$0);
                $this$render_u24lambda_u2435.setBackground(SvIcon.drawable$default(SvIcon.INSTANCE, kind.getIcon(), kind.getColor(), 0, 4, null));
                LinearLayout.LayoutParams $this$render_u24lambda_u2436 = new LinearLayout.LayoutParams(this.this$0.dp(22), this.this$0.dp(22));
                $this$render_u24lambda_u2436.rightMargin = this.this$0.dp(10);
                Unit unit = Unit.INSTANCE;
                row.addView($this$render_u24lambda_u2435, $this$render_u24lambda_u2436);
                TextView $this$render_u24lambda_u2437 = new TextView(this.this$0);
                $this$render_u24lambda_u2437.setText(e.getName());
                $this$render_u24lambda_u2437.setTextColor(-1);
                $this$render_u24lambda_u2437.setTextSize(15.0f);
                $this$render_u24lambda_u2437.setMaxLines(1);
                $this$render_u24lambda_u2437.setEllipsize(TextUtils.TruncateAt.MIDDLE);
                row.addView($this$render_u24lambda_u2437, new LinearLayout.LayoutParams(0, -2, 1.0f));
                TextView $this$render_u24lambda_u2438 = new TextView(this.this$0);
                FilesActivity filesActivity2 = this.this$0;
                $this$render_u24lambda_u2438.setText(filesActivity2.fmtDate(e.getMtime()));
                $this$render_u24lambda_u2438.setTextColor(filesActivity2.dim);
                $this$render_u24lambda_u2438.setTextSize(11.0f);
                $this$render_u24lambda_u2438.setPadding(filesActivity2.dp(8), 0, 0, 0);
                row.addView($this$render_u24lambda_u2438);
                TextView $this$render_u24lambda_u2439 = new TextView(this.this$0);
                FilesActivity filesActivity3 = this.this$0;
                $this$render_u24lambda_u2439.setText(e.getDir() ? "" : filesActivity3.fmtSize(e.getSize()));
                $this$render_u24lambda_u2439.setTextColor(filesActivity3.dim);
                $this$render_u24lambda_u2439.setTextSize(12.0f);
                $this$render_u24lambda_u2439.setPadding(filesActivity3.dp(10), 0, 0, 0);
                $this$render_u24lambda_u2439.setMinWidth(filesActivity3.dp(64));
                $this$render_u24lambda_u2439.setGravity(8388613);
                row.addView($this$render_u24lambda_u2439);
                row.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.FilesActivity$Pane$$ExternalSyntheticLambda10
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        FilesActivity.Pane.render$lambda$40(e, FilesActivity.Pane.this, view);
                    }
                });
                row.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.starpilot.starview.FilesActivity$Pane$$ExternalSyntheticLambda12
                    @Override // android.view.View.OnLongClickListener
                    public final boolean onLongClick(View view) {
                        boolean render$lambda$41;
                        render$lambda$41 = FilesActivity.Pane.render$lambda$41(FilesActivity.Pane.this, e, view);
                        return render$lambda$41;
                    }
                });
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-1, -2);
                lp.bottomMargin = this.this$0.dp(3);
                getList().addView(row, lp);
            }
            if (this.entries.isEmpty()) {
                LinearLayout list = getList();
                TextView $this$render_u24lambda_u2442 = new TextView(this.this$0);
                FilesActivity filesActivity4 = this.this$0;
                String str = this.lastError;
                if (str == null) {
                    str = "(empty)";
                }
                $this$render_u24lambda_u2442.setText(str);
                $this$render_u24lambda_u2442.setTextColor(filesActivity4.dim);
                $this$render_u24lambda_u2442.setPadding(filesActivity4.dp(10), filesActivity4.dp(10), filesActivity4.dp(10), filesActivity4.dp(10));
                list.addView($this$render_u24lambda_u2442);
            }
            this.this$0.updateButtons();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void render$lambda$40(Entry $e, Pane this$0, View it) {
            if ($e.getDir() && this$0.selected.isEmpty()) {
                this$0.enter($e.getName());
            } else {
                this$0.toggle($e.getName());
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final boolean render$lambda$41(Pane this$0, Entry $e, View it) {
            this$0.toggle($e.getName());
            return true;
        }

        public final void toggle(String name) {
            Intrinsics.checkNotNullParameter(name, "name");
            if (!this.selected.remove(name)) {
                this.selected.add(name);
            }
            render();
        }

        public final void mkdir() {
            FilesActivity filesActivity = this.this$0;
            String str = "New folder in " + this.path;
            final FilesActivity filesActivity2 = this.this$0;
            FilesActivity.input$default(filesActivity, str, "name", null, new Function1() { // from class: com.starpilot.starview.FilesActivity$Pane$$ExternalSyntheticLambda4
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    Unit mkdir$lambda$44;
                    mkdir$lambda$44 = FilesActivity.Pane.mkdir$lambda$44(FilesActivity.Pane.this, filesActivity2, (String) obj);
                    return mkdir$lambda$44;
                }
            }, 4, null);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit mkdir$lambda$44(final Pane this$0, FilesActivity this$1, String name) {
            Intrinsics.checkNotNullParameter(name, "name");
            if (this$0.remote) {
                JSONObject put = new JSONObject().put("op", "mkdir").put("path", this$1.join(this$0.path, name));
                Intrinsics.checkNotNullExpressionValue(put, "put(...)");
                this$1.op(put, new Function0() { // from class: com.starpilot.starview.FilesActivity$Pane$$ExternalSyntheticLambda5
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        Unit mkdir$lambda$44$lambda$43;
                        mkdir$lambda$44$lambda$43 = FilesActivity.Pane.mkdir$lambda$44$lambda$43(this$0);
                        return mkdir$lambda$44$lambda$43;
                    }
                });
            } else {
                new File(this$0.path, name).mkdirs();
                this$0.refresh();
            }
            return Unit.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit mkdir$lambda$44$lambda$43(Pane this$0) {
            this$0.refresh();
            return Unit.INSTANCE;
        }

        public final void deleteSelected() {
            if (this.selected.isEmpty()) {
                this.this$0.toast("Select files or folders first (tap to select, tap a folder with nothing selected to open it)");
                return;
            }
            final List names = CollectionsKt.toList(this.selected);
            AlertDialog.Builder builder = new AlertDialog.Builder(this.this$0);
            int size = names.size();
            String str = this.remote ? "the comma" : "the tablet";
            final FilesActivity filesActivity = this.this$0;
            builder.setTitle("Delete " + size + " item(s) from " + str + "?").setMessage(CollectionsKt.joinToString(names, "\n", "", "", -1, "...", null)).setPositiveButton("Delete", new DialogInterface.OnClickListener() { // from class: com.starpilot.starview.FilesActivity$Pane$$ExternalSyntheticLambda17
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    FilesActivity.Pane.deleteSelected$lambda$47(FilesActivity.Pane.this, filesActivity, names, dialogInterface, i);
                }
            }).setNegativeButton("Cancel", (DialogInterface.OnClickListener) null).show();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void deleteSelected$lambda$47(final Pane this$0, final FilesActivity this$1, final List $names, DialogInterface dialogInterface, int i) {
            if (this$0.remote) {
                this$1.io.execute(new Runnable() { // from class: com.starpilot.starview.FilesActivity$Pane$$ExternalSyntheticLambda7
                    @Override // java.lang.Runnable
                    public final void run() {
                        FilesActivity.Pane.deleteSelected$lambda$47$lambda$46($names, this$1, this$0);
                    }
                });
                return;
            }
            Iterator it = $names.iterator();
            while (it.hasNext()) {
                String n = (String) it.next();
                FilesKt.deleteRecursively(new File(this$0.path, n));
            }
            this$0.selected.clear();
            this$0.refresh();
        }

        /* VF: from Vineflower (jadx: Type inference failed) */
        public static final void deleteSelected$lambda$47$lambda$46(List var0, FilesActivity var1, FilesActivity.Pane var2) {
            ObjectRef var3 = new ObjectRef();

            for (String var4 : var0) {
                try {
                    JSONObject var5 = new JSONObject();
                    JSONObject var8 = var5.put("op", "delete").put("path", var1.join(var2.path, var4));
                    Intrinsics.checkNotNullExpressionValue(var8, "put(...)");
                    var1.opSync(var8);
                } catch (Exception var6) {
                    var3.element = (T)var6.getMessage();
                }
            }

            var1.ui.post(new FilesActivity$Pane$$ExternalSyntheticLambda13(var2, var3, var1));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void deleteSelected$lambda$47$lambda$46$lambda$45(Pane this$0, Ref.ObjectRef $err, FilesActivity this$1) {
            this$0.selected.clear();
            this$0.refresh();
            if ($err.element) {
                this$1.toast("delete: " + $err.element);
            }
        }

        public final void openSelected() {
            String name = (String) CollectionsKt.singleOrNull(this.selected);
            Object obj = null;
            if (name == null) {
                Iterable $this$singleOrNull$iv = this.entries;
                Object single$iv = null;
                boolean found$iv = false;
                Iterator it = $this$singleOrNull$iv.iterator();
                while (true) {
                    if (it.hasNext()) {
                        Object element$iv = it.next();
                        if (!((Entry) element$iv).getDir()) {
                            if (found$iv) {
                                single$iv = null;
                                break;
                            } else {
                                single$iv = element$iv;
                                found$iv = true;
                            }
                        }
                    } else if (!found$iv) {
                        single$iv = null;
                    }
                }
                Entry entry = (Entry) single$iv;
                name = entry != null ? entry.getName() : null;
            }
            Iterable $this$firstOrNull$iv = this.entries;
            Iterator it2 = $this$firstOrNull$iv.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    break;
                }
                Object element$iv2 = it2.next();
                Entry it3 = (Entry) element$iv2;
                if (Intrinsics.areEqual(it3.getName(), name) && !it3.getDir()) {
                    obj = element$iv2;
                    break;
                }
            }
            final Entry e = (Entry) obj;
            if (e == null) {
                this.this$0.toast("Select one file to open");
                return;
            }
            final FileKinds.Kind kind = FileKinds.INSTANCE.of(e.getName(), false);
            if (kind.getPreview() == FileKinds.Preview.NONE) {
                this.this$0.toast(e.getName() + ": can't be shown here - copy it to the tablet to open it");
                return;
            }
            final String full = this.this$0.join(this.path, e.getName());
            ExecutorService executorService = this.this$0.io;
            final FilesActivity filesActivity = this.this$0;
            executorService.execute(new Runnable() { // from class: com.starpilot.starview.FilesActivity$Pane$$ExternalSyntheticLambda18
                @Override // java.lang.Runnable
                public final void run() {
                    FilesActivity.Pane.openSelected$lambda$58(kind, this, filesActivity, full, e);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Code restructure failed: missing block: B:26:0x009c, code lost:
        
            if (r4 == null) goto L27;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public static final void openSelected$lambda$58(final FileKinds.Kind $kind, final Pane this$0, final FilesActivity this$1, final String $full, final Entry $e) {
            long limit;
            FileInputStream fileInputStream;
            byte[] byteArray;
            String str;
            String string;
            try {
                limit = $kind.getPreview() == FileKinds.Preview.IMAGE ? 12582912L : 1048576L;
            } catch (Exception e) {
                x = e;
            }
            try {
                boolean z = true;
                if (this$0.remote) {
                    String h = this$1.getHost();
                    if (h == null) {
                        throw new RuntimeException("not connected");
                    }
                    fileInputStream = this$1.http.newCall(new Request.Builder().url("http://" + h + ":8090/fs/get?path=" + Uri.encode($full)).build()).execute();
                    try {
                        Response r = fileInputStream;
                        if (!r.isSuccessful()) {
                            ResponseBody body = r.body();
                            if (body != null && (string = body.string()) != null) {
                                String str2 = string;
                                if (str2.length() != 0) {
                                    z = false;
                                }
                                if (z) {
                                    str2 = "HTTP " + r.code();
                                }
                                str = str2;
                            }
                            str = "HTTP " + r.code();
                            throw new RuntimeException(str);
                        }
                        ResponseBody body2 = r.body();
                        Intrinsics.checkNotNull(body2);
                        InputStream ins = body2.byteStream();
                        ByteArrayOutputStream out = new ByteArrayOutputStream();
                        byte[] buf = new byte[65536];
                        while (true) {
                            int it = ins.read(buf);
                            if (it <= 0 || out.size() >= limit) {
                                break;
                            } else {
                                out.write(buf, 0, it);
                            }
                        }
                        byteArray = out.toByteArray();
                        CloseableKt.closeFinally(fileInputStream, null);
                    } finally {
                        try {
                            throw th;
                        } finally {
                        }
                    }
                } else {
                    try {
                        fileInputStream = new FileInputStream(new File($full));
                        try {
                            FileInputStream ins2 = fileInputStream;
                            ByteArrayOutputStream out2 = new ByteArrayOutputStream();
                            byte[] buf2 = new byte[65536];
                            while (true) {
                                int it2 = ins2.read(buf2);
                                if (it2 <= 0 || out2.size() >= limit) {
                                    break;
                                } else {
                                    out2.write(buf2, 0, it2);
                                }
                            }
                            byteArray = out2.toByteArray();
                            CloseableKt.closeFinally(fileInputStream, null);
                        } finally {
                        }
                    } catch (Exception e2) {
                        x = e2;
                        this$1.ui.post(new Runnable() { // from class: com.starpilot.starview.FilesActivity$Pane$$ExternalSyntheticLambda15
                            @Override // java.lang.Runnable
                            public final void run() {
                                FilesActivity.Pane.openSelected$lambda$58$lambda$57(this$1, x);
                            }
                        });
                    }
                }
                final byte[] bytes = byteArray;
                final boolean truncated = $e.getSize() > ((long) bytes.length);
                this$1.ui.post(new Runnable() { // from class: com.starpilot.starview.FilesActivity$Pane$$ExternalSyntheticLambda11
                    @Override // java.lang.Runnable
                    public final void run() {
                        FilesActivity.Pane.openSelected$lambda$58$lambda$56(this$1, this$0, $e, $full, $kind, bytes, truncated);
                    }
                });
            } catch (Exception e3) {
                x = e3;
                this$1.ui.post(new Runnable() { // from class: com.starpilot.starview.FilesActivity$Pane$$ExternalSyntheticLambda15
                    @Override // java.lang.Runnable
                    public final void run() {
                        FilesActivity.Pane.openSelected$lambda$58$lambda$57(this$1, x);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void openSelected$lambda$58$lambda$56(FilesActivity this$0, Pane this$1, Entry $e, String $full, FileKinds.Kind $kind, byte[] $bytes, boolean $truncated) {
            Intrinsics.checkNotNull($bytes);
            this$0.showPreview(this$1, $e, $full, $kind, $bytes, $truncated);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void openSelected$lambda$58$lambda$57(FilesActivity this$0, Exception $x) {
            this$0.toast("open: " + $x.getMessage());
        }

        public final void goToPathDialog() {
            final List shortcuts;
            String absolutePath;
            String str = "/";
            if (this.remote) {
                shortcuts = CollectionsKt.listOf((Object[]) new String[]{"/data", "/data/openpilot", "/data/media/0/realdata", "/data/params/d", "/data/extstorage", "/tmp", "/"});
            } else {
                String[] strArr = new String[4];
                strArr[0] = Environment.getExternalStorageDirectory().getAbsolutePath();
                strArr[1] = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
                strArr[2] = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath();
                File externalFilesDir = this.this$0.getExternalFilesDir(null);
                if (externalFilesDir != null && (absolutePath = externalFilesDir.getAbsolutePath()) != null) {
                    str = absolutePath;
                }
                strArr[3] = str;
                shortcuts = CollectionsKt.listOf((Object[]) strArr);
            }
            AlertDialog.Builder title = new AlertDialog.Builder(this.this$0).setTitle("Go to");
            Collection $this$toTypedArray$iv = CollectionsKt.plus((Collection<? extends String>) shortcuts, "Type a path…");
            CharSequence[] charSequenceArr = (CharSequence[]) $this$toTypedArray$iv.toArray(new String[0]);
            final FilesActivity filesActivity = this.this$0;
            title.setItems(charSequenceArr, new DialogInterface.OnClickListener() { // from class: com.starpilot.starview.FilesActivity$Pane$$ExternalSyntheticLambda14
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    FilesActivity.Pane.goToPathDialog$lambda$61(shortcuts, this, filesActivity, dialogInterface, i);
                }
            }).show();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void goToPathDialog$lambda$61(List $shortcuts, final Pane this$0, FilesActivity this$1, DialogInterface dialogInterface, int w) {
            if (w >= $shortcuts.size()) {
                this$1.input("Path", "/…", this$0.path, new Function1() { // from class: com.starpilot.starview.FilesActivity$Pane$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        Unit goToPathDialog$lambda$61$lambda$60;
                        goToPathDialog$lambda$61$lambda$60 = FilesActivity.Pane.goToPathDialog$lambda$61$lambda$60(this$0, (String) obj);
                        return goToPathDialog$lambda$61$lambda$60;
                    }
                });
                return;
            }
            this$0.path = (String) $shortcuts.get(w);
            this$0.selected.clear();
            this$0.refresh();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit goToPathDialog$lambda$61$lambda$60(Pane this$0, String p) {
            Intrinsics.checkNotNullParameter(p, "p");
            String trimEnd = StringsKt.trimEnd(p, '/');
            if (trimEnd.length() == 0) {
                trimEnd = "/";
            }
            this$0.path = trimEnd;
            this$0.selected.clear();
            this$0.refresh();
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String getHost() {
        return Link.INSTANCE.getHost();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final int dp(int v) {
        return (int) (v * this.d);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.ContextThemeWrapper, android.content.ContextWrapper
    protected void attachBaseContext(Context base) {
        Intrinsics.checkNotNullParameter(base, "base");
        super.attachBaseContext(NarrowDensity.INSTANCE.wrap(base, 1000, 0.62f));
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        Pane pane;
        super.onCreate(savedInstanceState);
        Link.INSTANCE.loadKey(this);
        this.d = getResources().getDisplayMetrics().density;
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(1);
        root.setBackgroundColor(Color.rgb(11, 13, 18));
        LinearLayout header = new LinearLayout(this);
        header.setOrientation(0);
        header.setGravity(16);
        header.setPadding(dp(20), dp(10), dp(20), dp(4));
        TextView $this$onCreate_u24lambda_u242 = new TextView(this);
        $this$onCreate_u24lambda_u242.setText("Files");
        $this$onCreate_u24lambda_u242.setTextColor(-1);
        $this$onCreate_u24lambda_u242.setTextSize(20.0f);
        $this$onCreate_u24lambda_u242.setTypeface(null, 1);
        header.addView($this$onCreate_u24lambda_u242);
        header.addView(new View(this), new LinearLayout.LayoutParams(0, 1, 1.0f));
        Button $this$onCreate_u24lambda_u244 = new Button(this);
        $this$onCreate_u24lambda_u244.setText("Allow all files");
        $this$onCreate_u24lambda_u244.setTextSize(12.0f);
        $this$onCreate_u24lambda_u244.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.FilesActivity$$ExternalSyntheticLambda9
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                FilesActivity.this.requestAllFiles();
            }
        });
        this.allFilesBtn = $this$onCreate_u24lambda_u244;
        Button button = this.allFilesBtn;
        if (button == null) {
            Intrinsics.throwUninitializedPropertyAccessException("allFilesBtn");
            button = null;
        }
        header.addView(button);
        TextView $this$onCreate_u24lambda_u246 = new TextView(this);
        $this$onCreate_u24lambda_u246.setText("Done");
        $this$onCreate_u24lambda_u246.setTextColor(Color.rgb(111, 227, 161));
        $this$onCreate_u24lambda_u246.setTextSize(20.0f);
        $this$onCreate_u24lambda_u246.setPadding(dp(20), dp(10), dp(4), dp(10));
        $this$onCreate_u24lambda_u246.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.FilesActivity$$ExternalSyntheticLambda10
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                FilesActivity.this.finish();
            }
        });
        header.addView($this$onCreate_u24lambda_u246);
        root.addView(header);
        LinearLayout panes = new LinearLayout(this);
        panes.setOrientation(0);
        this.comma = new Pane(this, true, "comma");
        this.tablet = new Pane(this, false, "tablet");
        Pane pane2 = this.comma;
        if (pane2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("comma");
            pane2 = null;
        }
        panes.addView(pane2.build(), new LinearLayout.LayoutParams(0, -1, 1.0f));
        LinearLayout mid = new LinearLayout(this);
        mid.setOrientation(1);
        mid.setGravity(17);
        mid.setPadding(dp(4), 0, dp(4), 0);
        Button $this$onCreate_u24lambda_u2411 = new Button(this);
        $this$onCreate_u24lambda_u2411.setText("⇄");
        $this$onCreate_u24lambda_u2411.setTextSize(30.0f);
        $this$onCreate_u24lambda_u2411.setTypeface(null, 1);
        $this$onCreate_u24lambda_u2411.setTextColor(Color.rgb(11, 13, 18));
        $this$onCreate_u24lambda_u2411.setStateListAnimator(null);
        GradientDrawable $this$onCreate_u24lambda_u2411_u24lambda_u249 = new GradientDrawable();
        $this$onCreate_u24lambda_u2411_u24lambda_u249.setShape(1);
        $this$onCreate_u24lambda_u2411_u24lambda_u249.setColor(Color.rgb(111, 227, 161));
        $this$onCreate_u24lambda_u2411.setBackground($this$onCreate_u24lambda_u2411_u24lambda_u249);
        $this$onCreate_u24lambda_u2411.setLayoutParams(new LinearLayout.LayoutParams(dp(96), dp(96)));
        $this$onCreate_u24lambda_u2411.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.FilesActivity$$ExternalSyntheticLambda12
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                FilesActivity.this.askCopy();
            }
        });
        this.copyBtn = $this$onCreate_u24lambda_u2411;
        TextView $this$onCreate_u24lambda_u2412 = new TextView(this);
        $this$onCreate_u24lambda_u2412.setText("");
        $this$onCreate_u24lambda_u2412.setTextColor(this.dim);
        $this$onCreate_u24lambda_u2412.setTextSize(11.0f);
        $this$onCreate_u24lambda_u2412.setGravity(17);
        $this$onCreate_u24lambda_u2412.setPadding(0, dp(6), 0, 0);
        $this$onCreate_u24lambda_u2412.setMaxWidth(dp(110));
        this.copyHint = $this$onCreate_u24lambda_u2412;
        Button button2 = this.copyBtn;
        if (button2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("copyBtn");
            button2 = null;
        }
        mid.addView(button2);
        TextView textView = this.copyHint;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("copyHint");
            textView = null;
        }
        mid.addView(textView);
        panes.addView(mid, new LinearLayout.LayoutParams(-2, -1));
        Pane pane3 = this.tablet;
        if (pane3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tablet");
            pane3 = null;
        }
        panes.addView(pane3.build(), new LinearLayout.LayoutParams(0, -1, 1.0f));
        root.addView(panes, new LinearLayout.LayoutParams(-1, 0, 1.0f));
        LinearLayout $this$onCreate_u24lambda_u2413 = new LinearLayout(this);
        $this$onCreate_u24lambda_u2413.setOrientation(1);
        $this$onCreate_u24lambda_u2413.setPadding(dp(16), dp(6), dp(16), dp(10));
        $this$onCreate_u24lambda_u2413.setVisibility(8);
        $this$onCreate_u24lambda_u2413.setBackgroundColor(Color.rgb(17, 20, 25));
        this.progBox = $this$onCreate_u24lambda_u2413;
        LinearLayout top = new LinearLayout(this);
        top.setOrientation(0);
        top.setGravity(16);
        LinearLayout pcol = new LinearLayout(this);
        pcol.setOrientation(1);
        TextView $this$onCreate_u24lambda_u2416 = new TextView(this);
        $this$onCreate_u24lambda_u2416.setTextColor(-1);
        $this$onCreate_u24lambda_u2416.setTextSize(13.0f);
        $this$onCreate_u24lambda_u2416.setMaxLines(1);
        $this$onCreate_u24lambda_u2416.setEllipsize(TextUtils.TruncateAt.MIDDLE);
        this.progTv = $this$onCreate_u24lambda_u2416;
        ProgressBar $this$onCreate_u24lambda_u2417 = new ProgressBar(this, null, 16842872);
        $this$onCreate_u24lambda_u2417.setMax(1000);
        this.progBar = $this$onCreate_u24lambda_u2417;
        TextView textView2 = this.progTv;
        if (textView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("progTv");
            textView2 = null;
        }
        pcol.addView(textView2);
        ProgressBar progressBar = this.progBar;
        if (progressBar == null) {
            Intrinsics.throwUninitializedPropertyAccessException("progBar");
            progressBar = null;
        }
        pcol.addView(progressBar);
        top.addView(pcol, new LinearLayout.LayoutParams(0, -2, 1.0f));
        final Button $this$onCreate_u24lambda_u2419 = new Button(this);
        $this$onCreate_u24lambda_u2419.setText("Pause");
        $this$onCreate_u24lambda_u2419.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.FilesActivity$$ExternalSyntheticLambda13
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                FilesActivity.onCreate$lambda$19$lambda$18(FilesActivity.this, $this$onCreate_u24lambda_u2419, view);
            }
        });
        this.pauseBtn = $this$onCreate_u24lambda_u2419;
        Button button3 = this.pauseBtn;
        if (button3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pauseBtn");
            button3 = null;
        }
        top.addView(button3);
        Button $this$onCreate_u24lambda_u2421 = new Button(this);
        $this$onCreate_u24lambda_u2421.setText("Cancel");
        $this$onCreate_u24lambda_u2421.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.FilesActivity$$ExternalSyntheticLambda14
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                FilesActivity.onCreate$lambda$21$lambda$20(FilesActivity.this, view);
            }
        });
        this.cancelBtn = $this$onCreate_u24lambda_u2421;
        Button button4 = this.cancelBtn;
        if (button4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("cancelBtn");
            button4 = null;
        }
        top.addView(button4);
        LinearLayout linearLayout = this.progBox;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("progBox");
            linearLayout = null;
        }
        linearLayout.addView(top);
        LinearLayout $this$onCreate_u24lambda_u2422 = new LinearLayout(this);
        $this$onCreate_u24lambda_u2422.setOrientation(1);
        this.queueList = $this$onCreate_u24lambda_u2422;
        LinearLayout linearLayout2 = this.progBox;
        if (linearLayout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("progBox");
            linearLayout2 = null;
        }
        ScrollView $this$onCreate_u24lambda_u2423 = new ScrollView(this);
        LinearLayout linearLayout3 = this.queueList;
        if (linearLayout3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("queueList");
            linearLayout3 = null;
        }
        $this$onCreate_u24lambda_u2423.addView(linearLayout3);
        linearLayout2.addView($this$onCreate_u24lambda_u2423, new LinearLayout.LayoutParams(-1, dp(170)));
        LinearLayout linearLayout4 = this.progBox;
        if (linearLayout4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("progBox");
            linearLayout4 = null;
        }
        root.addView(linearLayout4);
        setContentView(root);
        Pane pane4 = this.tablet;
        if (pane4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tablet");
            pane4 = null;
        }
        pane4.setPath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath());
        Pane pane5 = this.comma;
        if (pane5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("comma");
            pane5 = null;
        }
        pane5.refresh();
        Pane pane6 = this.tablet;
        if (pane6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tablet");
            pane = null;
        } else {
            pane = pane6;
        }
        pane.refresh();
        updateButtons();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$19$lambda$18(FilesActivity this$0, Button $this_apply, View it) {
        boolean p = !this$0.paused.get();
        this$0.paused.set(p);
        $this_apply.setText(p ? "Resume" : "Pause");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$21$lambda$20(FilesActivity this$0, View it) {
        this$0.cancel.set(true);
        this$0.paused.set(false);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        Button button = this.allFilesBtn;
        Pane pane = null;
        if (button == null) {
            Intrinsics.throwUninitializedPropertyAccessException("allFilesBtn");
            button = null;
        }
        button.setVisibility(hasAllFiles() ? 8 : 0);
        if (this.tablet != null) {
            Pane pane2 = this.tablet;
            if (pane2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tablet");
            } else {
                pane = pane2;
            }
            pane.refresh();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean hasAllFiles() {
        return Build.VERSION.SDK_INT < 30 || Environment.isExternalStorageManager();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void requestAllFiles() {
        if (Build.VERSION.SDK_INT >= 30) {
            try {
                startActivity(new Intent("android.settings.MANAGE_APP_ALL_FILES_ACCESS_PERMISSION", Uri.parse("package:" + getPackageName())));
            } catch (Exception e) {
                startActivity(new Intent("android.settings.MANAGE_ALL_FILES_ACCESS_PERMISSION"));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateButtons() {
        String str;
        if (this.copyBtn == null) {
            return;
        }
        Pane pane = this.comma;
        TextView textView = null;
        if (pane == null) {
            Intrinsics.throwUninitializedPropertyAccessException("comma");
            pane = null;
        }
        int c = pane.getSelected().size();
        Pane pane2 = this.tablet;
        if (pane2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tablet");
            pane2 = null;
        }
        int t = pane2.getSelected().size();
        Button button = this.copyBtn;
        if (button == null) {
            Intrinsics.throwUninitializedPropertyAccessException("copyBtn");
            button = null;
        }
        button.setEnabled(!this.busy && (c > 0 || t > 0));
        Button button2 = this.copyBtn;
        if (button2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("copyBtn");
            button2 = null;
        }
        Button button3 = this.copyBtn;
        if (button3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("copyBtn");
            button3 = null;
        }
        button2.setAlpha(button3.isEnabled() ? 1.0f : 0.4f);
        Button button4 = this.copyBtn;
        if (button4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("copyBtn");
            button4 = null;
        }
        button4.setText((c <= 0 || t != 0) ? (t <= 0 || c != 0) ? "⇄" : "←" : "→");
        TextView textView2 = this.copyHint;
        if (textView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("copyHint");
        } else {
            textView = textView2;
        }
        if (!this.busy) {
            if (c > 0 && t == 0) {
                str = c + " selected on comma → tablet folder";
            } else if (t <= 0 || c != 0) {
                str = (c <= 0 || t <= 0) ? "select files or folders on either side" : "selected on both sides — tap to choose";
            } else {
                str = t + " selected on tablet → comma folder";
            }
        }
        textView.setText(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final JSONObject getJson(String url) {
        String body;
        Response execute = this.http.newCall(new Request.Builder().url(url).build()).execute();
        try {
            Response r = execute;
            ResponseBody body2 = r.body();
            if (body2 == null || (body = body2.string()) == null) {
                body = "";
            }
            if (r.isSuccessful()) {
                JSONObject jSONObject = new JSONObject(body);
                CloseableKt.closeFinally(execute, null);
                return jSONObject;
            }
            String str = body;
            if (str.length() == 0) {
                str = "HTTP " + r.code();
            }
            throw new RuntimeException(str);
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                CloseableKt.closeFinally(execute, th);
                throw th2;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final JSONObject opSync(JSONObject j) {
        String body;
        JSONObject res;
        String h = getHost();
        if (h == null) {
            throw new RuntimeException("not connected");
        }
        OkHttpClient okHttpClient = this.http;
        Request.Builder url = new Request.Builder().url("http://" + h + ":8090/fs/op");
        RequestBody.Companion companion = RequestBody.Companion;
        MediaType mediaType = MediaType.Companion.get("application/json");
        String jSONObject = j.toString();
        Intrinsics.checkNotNullExpressionValue(jSONObject, "toString(...)");
        Response execute = okHttpClient.newCall(url.post(companion.create(mediaType, jSONObject)).build()).execute();
        try {
            Response r = execute;
            ResponseBody body2 = r.body();
            if (body2 == null || (body = body2.string()) == null) {
                body = "";
            }
            try {
                res = new JSONObject(body);
            } catch (Exception e) {
                JSONObject put = new JSONObject().put("ok", false);
                String str = body;
                if (str.length() == 0) {
                    str = "HTTP " + r.code();
                }
                res = put.put("error", str);
            }
            if (!res.optBoolean("ok")) {
                throw new RuntimeException(res.optString("error", "HTTP " + r.code()));
            }
            Intrinsics.checkNotNull(res);
            CloseableKt.closeFinally(execute, null);
            return res;
        } finally {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void op(final JSONObject j, final Function0<Unit> then) {
        this.io.execute(new Runnable() { // from class: com.starpilot.starview.FilesActivity$$ExternalSyntheticLambda21
            @Override // java.lang.Runnable
            public final void run() {
                FilesActivity.op$lambda$30(FilesActivity.this, j, then);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void op$lambda$30(final FilesActivity this$0, final JSONObject $j, final Function0 $then) {
        try {
            this$0.opSync($j);
            this$0.ui.post(new Runnable() { // from class: com.starpilot.starview.FilesActivity$$ExternalSyntheticLambda27
                @Override // java.lang.Runnable
                public final void run() {
                    $then.invoke();
                }
            });
        } catch (Exception e) {
            this$0.ui.post(new Runnable() { // from class: com.starpilot.starview.FilesActivity$$ExternalSyntheticLambda28
                @Override // java.lang.Runnable
                public final void run() {
                    FilesActivity.op$lambda$30$lambda$29(this$0, $j, e);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void op$lambda$30$lambda$29(FilesActivity this$0, JSONObject $j, Exception $e) {
        this$0.toast($j.optString("op") + ": " + $e.getMessage());
    }

    /* compiled from: FilesActivity.kt */
    private static final /* data */ class Job {
        private final String dstPath;
        private final long mtime;
        private final long size;
        private final String srcPath;

        public static /* synthetic */ Job copy$default(Job job, String str, String str2, long j, long j2, int i, Object obj) {
            if ((i & 1) != 0) {
                str = job.srcPath;
            }
            if ((i & 2) != 0) {
                str2 = job.dstPath;
            }
            String str3 = str2;
            if ((i & 4) != 0) {
                j = job.size;
            }
            long j3 = j;
            if ((i & 8) != 0) {
                j2 = job.mtime;
            }
            return job.copy(str, str3, j3, j2);
        }

        /* renamed from: component1, reason: from getter */
        public final String getSrcPath() {
            return this.srcPath;
        }

        /* renamed from: component2, reason: from getter */
        public final String getDstPath() {
            return this.dstPath;
        }

        /* renamed from: component3, reason: from getter */
        public final long getSize() {
            return this.size;
        }

        /* renamed from: component4, reason: from getter */
        public final long getMtime() {
            return this.mtime;
        }

        public final Job copy(String srcPath, String dstPath, long size, long mtime) {
            Intrinsics.checkNotNullParameter(srcPath, "srcPath");
            Intrinsics.checkNotNullParameter(dstPath, "dstPath");
            return new Job(srcPath, dstPath, size, mtime);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Job)) {
                return false;
            }
            Job job = (Job) other;
            return Intrinsics.areEqual(this.srcPath, job.srcPath) && Intrinsics.areEqual(this.dstPath, job.dstPath) && this.size == job.size && this.mtime == job.mtime;
        }

        public int hashCode() {
            return (((((this.srcPath.hashCode() * 31) + this.dstPath.hashCode()) * 31) + Long.hashCode(this.size)) * 31) + Long.hashCode(this.mtime);
        }

        public String toString() {
            return "Job(srcPath=" + this.srcPath + ", dstPath=" + this.dstPath + ", size=" + this.size + ", mtime=" + this.mtime + ")";
        }

        public Job(String srcPath, String dstPath, long size, long mtime) {
            Intrinsics.checkNotNullParameter(srcPath, "srcPath");
            Intrinsics.checkNotNullParameter(dstPath, "dstPath");
            this.srcPath = srcPath;
            this.dstPath = dstPath;
            this.size = size;
            this.mtime = mtime;
        }

        public final String getDstPath() {
            return this.dstPath;
        }

        public final long getMtime() {
            return this.mtime;
        }

        public final long getSize() {
            return this.size;
        }

        public final String getSrcPath() {
            return this.srcPath;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void askCopy() {
        if (this.busy) {
            return;
        }
        Pane pane = this.comma;
        Pane pane2 = null;
        if (pane == null) {
            Intrinsics.throwUninitializedPropertyAccessException("comma");
            pane = null;
        }
        int c = pane.getSelected().size();
        Pane pane3 = this.tablet;
        if (pane3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tablet");
            pane3 = null;
        }
        int t = pane3.getSelected().size();
        if (c <= 0 || t != 0) {
            if (t <= 0 || c != 0) {
                if (c > 0 && t > 0) {
                    AlertDialog.Builder title = new AlertDialog.Builder(this).setTitle("Copy which selection?");
                    String[] strArr = new String[2];
                    Pane pane4 = this.tablet;
                    if (pane4 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("tablet");
                        pane4 = null;
                    }
                    strArr[0] = c + " item(s) from comma → tablet:" + pane4.getPath();
                    Pane pane5 = this.comma;
                    if (pane5 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("comma");
                        pane5 = null;
                    }
                    strArr[1] = t + " item(s) from tablet → comma:" + pane5.getPath();
                    title.setItems(strArr, new DialogInterface.OnClickListener() { // from class: com.starpilot.starview.FilesActivity$$ExternalSyntheticLambda26
                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i) {
                            FilesActivity.askCopy$lambda$31(FilesActivity.this, dialogInterface, i);
                        }
                    }).setNegativeButton("Cancel", (DialogInterface.OnClickListener) null).show();
                    return;
                }
                toast("Select files or folders first");
                return;
            }
            Pane pane6 = this.tablet;
            if (pane6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tablet");
                pane6 = null;
            }
            Pane pane7 = this.comma;
            if (pane7 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("comma");
            } else {
                pane2 = pane7;
            }
            confirmCopy(pane6, pane2);
            return;
        }
        Pane pane8 = this.comma;
        if (pane8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("comma");
            pane8 = null;
        }
        Pane pane9 = this.tablet;
        if (pane9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tablet");
        } else {
            pane2 = pane9;
        }
        confirmCopy(pane8, pane2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void askCopy$lambda$31(FilesActivity this$0, DialogInterface dialogInterface, int w) {
        Pane pane;
        Pane pane2 = null;
        if (w == 0) {
            pane = this$0.comma;
            if (pane == null) {
                Intrinsics.throwUninitializedPropertyAccessException("comma");
                pane = null;
            }
            Pane pane3 = this$0.tablet;
            if (pane3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tablet");
            } else {
                pane2 = pane3;
            }
        } else {
            pane = this$0.tablet;
            if (pane == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tablet");
                pane = null;
            }
            Pane pane4 = this$0.comma;
            if (pane4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("comma");
            } else {
                pane2 = pane4;
            }
        }
        this$0.confirmCopy(pane, pane2);
    }

    private final void confirmCopy(final Pane src, final Pane dst) {
        List names = CollectionsKt.toList(src.getSelected());
        String srcName = src.getRemote() ? "comma" : "tablet";
        String dstName = dst.getRemote() ? "comma" : "tablet";
        String listing = CollectionsKt.joinToString(CollectionsKt.take(names, 8), "\n", "", "", -1, "...", null) + (names.size() > 8 ? "\n… and " + (names.size() - 8) + " more" : "");
        new AlertDialog.Builder(this).setTitle("Copy " + names.size() + " item(s) " + srcName + " → " + dstName + "?").setMessage("From:  " + srcName + ":" + src.getPath() + "\nTo:      " + dstName + ":" + dst.getPath() + "\n\n" + listing).setPositiveButton("Copy", new DialogInterface.OnClickListener() { // from class: com.starpilot.starview.FilesActivity$$ExternalSyntheticLambda24
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                FilesActivity.this.startCopy(src, dst);
            }
        }).setNegativeButton("Cancel", (DialogInterface.OnClickListener) null).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void startCopy(final Pane src, final Pane dst) {
        if (this.busy) {
            return;
        }
        final String h = getHost();
        if (h == null) {
            FilesActivity $this$startCopy_u24lambda_u2433 = this;
            $this$startCopy_u24lambda_u2433.toast("Not connected");
            return;
        }
        final List names = CollectionsKt.toList(src.getSelected());
        if (names.isEmpty()) {
            return;
        }
        this.busy = true;
        this.cancel.set(false);
        this.paused.set(false);
        ProgressBar progressBar = null;
        if (this.pauseBtn != null) {
            Button button = this.pauseBtn;
            if (button == null) {
                Intrinsics.throwUninitializedPropertyAccessException("pauseBtn");
                button = null;
            }
            button.setText("Pause");
        }
        updateButtons();
        LinearLayout linearLayout = this.progBox;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("progBox");
            linearLayout = null;
        }
        linearLayout.setVisibility(0);
        TextView textView = this.progTv;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("progTv");
            textView = null;
        }
        textView.setText("Preparing…");
        ProgressBar progressBar2 = this.progBar;
        if (progressBar2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("progBar");
        } else {
            progressBar = progressBar2;
        }
        progressBar.setProgress(0);
        this.io.execute(new Runnable() { // from class: com.starpilot.starview.FilesActivity$$ExternalSyntheticLambda25
            @Override // java.lang.Runnable
            public final void run() {
                FilesActivity.startCopy$lambda$51(FilesActivity.Pane.this, this, h, names, src);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:103:0x025c A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0284 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void startCopy$lambda$51(final Pane $dst, final FilesActivity this$0, String $h, List $names, Pane $src) {
        final Pane pane;
        final FilesActivity filesActivity;
        Throwable th;
        final Exception e;
        Handler handler;
        Runnable runnable;
        final Ref.IntRef failed;
        final int ok;
        final long j;
        Object element$iv;
        String n;
        Ref.ObjectRef applyAll;
        ArrayList dirs;
        String str;
        String str2;
        String target;
        String s;
        int $i$f$forEach;
        String str3;
        Iterator<File> it;
        String n2;
        String str4;
        String str5;
        String str6;
        String str7;
        final Pane pane2 = $dst;
        String t = $h;
        Pane pane3 = $src;
        String str8 = "rel";
        try {
            try {
                HashMap existing = new HashMap();
                String str9 = "http://";
                try {
                    if ($dst.getRemote()) {
                        JSONArray arr = this$0.getJson("http://" + t + ":8090/fs/list?path=" + Uri.encode($dst.getPath())).getJSONArray("entries");
                        int length = arr.length();
                        for (int i = 0; i < length; i++) {
                            JSONObject e2 = arr.getJSONObject(i);
                            existing.put(e2.getString("name"), Boolean.valueOf(e2.getBoolean("dir")));
                        }
                    } else {
                        File[] listFiles = new File($dst.getPath()).listFiles();
                        if (listFiles != null) {
                            int $i$f$forEach2 = 0;
                            int length2 = listFiles.length;
                            int i2 = 0;
                            while (i2 < length2) {
                                File file = listFiles[i2];
                                File[] fileArr = listFiles;
                                int $i$f$forEach3 = $i$f$forEach2;
                                existing.put(file.getName(), Boolean.valueOf(file.isDirectory()));
                                i2++;
                                listFiles = fileArr;
                                $i$f$forEach2 = $i$f$forEach3;
                            }
                        }
                    }
                    final ArrayList<Job> jobs = new ArrayList();
                    ArrayList dirs2 = new ArrayList();
                    final Ref.ObjectRef applyAll2 = new Ref.ObjectRef();
                    Iterator it2 = $names.iterator();
                    while (it2.hasNext()) {
                        try {
                            String n3 = (String) it2.next();
                            Iterable $this$firstOrNull$iv = $src.getEntries();
                            Iterator it3 = $this$firstOrNull$iv.iterator();
                            while (true) {
                                if (it3.hasNext()) {
                                    try {
                                        element$iv = it3.next();
                                        Entry it4 = (Entry) element$iv;
                                        if (Intrinsics.areEqual(it4.getName(), n3)) {
                                        }
                                    } catch (InterruptedException e3) {
                                        pane = pane2;
                                        filesActivity = this$0;
                                        pane2 = $src;
                                        filesActivity.ui.post(new Runnable() { // from class: com.starpilot.starview.FilesActivity$$ExternalSyntheticLambda7
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                this$0.toast("Cancelled");
                                            }
                                        });
                                        handler = filesActivity.ui;
                                        runnable = new Runnable() { // from class: com.starpilot.starview.FilesActivity$$ExternalSyntheticLambda35
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                FilesActivity.startCopy$lambda$51$lambda$50(this$0, pane2, pane);
                                            }
                                        };
                                        handler.post(runnable);
                                    } catch (Exception e4) {
                                        e = e4;
                                        pane = pane2;
                                        filesActivity = this$0;
                                        pane2 = $src;
                                        filesActivity.ui.post(new Runnable() { // from class: com.starpilot.starview.FilesActivity$$ExternalSyntheticLambda34
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                FilesActivity.startCopy$lambda$51$lambda$47(this$0, e);
                                            }
                                        });
                                        handler = filesActivity.ui;
                                        runnable = new Runnable() { // from class: com.starpilot.starview.FilesActivity$$ExternalSyntheticLambda35
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                FilesActivity.startCopy$lambda$51$lambda$50(this$0, pane2, pane);
                                            }
                                        };
                                        handler.post(runnable);
                                    } catch (Throwable th2) {
                                        th = th2;
                                        pane = pane2;
                                        filesActivity = this$0;
                                        pane2 = $src;
                                        filesActivity.ui.post(new Runnable() { // from class: com.starpilot.starview.FilesActivity$$ExternalSyntheticLambda35
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                FilesActivity.startCopy$lambda$51$lambda$50(this$0, pane2, pane);
                                            }
                                        });
                                        throw th;
                                    }
                                } else {
                                    element$iv = null;
                                }
                            }
                            Entry e5 = (Entry) element$iv;
                            if (e5 == null) {
                                pane3 = $src;
                            } else {
                                try {
                                    if (existing.containsKey(n3)) {
                                        try {
                                            try {
                                                Clash clash = (Clash) applyAll2.element;
                                                if (clash == null) {
                                                    boolean dir = e5.getDir();
                                                    n2 = n3;
                                                    str5 = "cancelled";
                                                    str = str8;
                                                    str6 = "op";
                                                    str4 = "path";
                                                    str2 = str9;
                                                    str7 = "put(...)";
                                                    applyAll = applyAll2;
                                                    dirs = dirs2;
                                                    clash = this$0.askClash(n2, dir, Intrinsics.areEqual(existing.get(n3), (Object) true), $dst.getRemote() ? "comma" : "tablet", $dst.getPath(), new Function1() { // from class: com.starpilot.starview.FilesActivity$$ExternalSyntheticLambda33
                                                        @Override // kotlin.jvm.functions.Function1
                                                        public final Object invoke(Object obj) {
                                                            Unit startCopy$lambda$51$lambda$36;
                                                            startCopy$lambda$51$lambda$36 = FilesActivity.startCopy$lambda$51$lambda$36(applyAll, (FilesActivity.Clash) obj);
                                                            return startCopy$lambda$51$lambda$36;
                                                        }
                                                    });
                                                } else {
                                                    n2 = n3;
                                                    str4 = "path";
                                                    applyAll = applyAll2;
                                                    dirs = dirs2;
                                                    str = str8;
                                                    str2 = str9;
                                                    str5 = "cancelled";
                                                    str6 = "op";
                                                    str7 = "put(...)";
                                                }
                                                Clash choice = clash;
                                                switch (WhenMappings.$EnumSwitchMapping$0[choice.ordinal()]) {
                                                    case 1:
                                                        throw new InterruptedException(str5);
                                                    case 2:
                                                        pane2 = $dst;
                                                        t = $h;
                                                        pane3 = $src;
                                                        str9 = str2;
                                                        applyAll2 = applyAll;
                                                        str8 = str;
                                                        dirs2 = dirs;
                                                        break;
                                                    case 3:
                                                        n = n2;
                                                        boolean dir2 = e5.getDir();
                                                        Set<String> keySet = existing.keySet();
                                                        Intrinsics.checkNotNullExpressionValue(keySet, "<get-keys>(...)");
                                                        target = this$0.freeName(n, dir2, keySet);
                                                        existing.put(target, Boolean.valueOf(e5.getDir()));
                                                        String s2 = this$0.join($src.getPath(), n);
                                                        String t2 = this$0.join($dst.getPath(), target);
                                                        if (e5.getDir()) {
                                                            jobs.add(new Job(s2, t2, e5.getSize(), e5.getMtime()));
                                                            pane2 = $dst;
                                                            t = $h;
                                                            pane3 = $src;
                                                            str9 = str2;
                                                            applyAll2 = applyAll;
                                                            str8 = str;
                                                            dirs2 = dirs;
                                                            break;
                                                        } else {
                                                            ArrayList dirs3 = dirs;
                                                            dirs3.add(t2);
                                                            if ($src.getRemote()) {
                                                                String str10 = str2;
                                                                JSONObject w = this$0.getJson(str10 + $h + ":8090/fs/walk?path=" + Uri.encode(s2));
                                                                JSONArray da = w.getJSONArray("dirs");
                                                                int i3 = 0;
                                                                int length3 = da.length();
                                                                while (i3 < length3) {
                                                                    String n4 = n;
                                                                    String n5 = da.getString(i3);
                                                                    Intrinsics.checkNotNullExpressionValue(n5, "getString(...)");
                                                                    dirs3.add(this$0.join(t2, n5));
                                                                    i3++;
                                                                    n = n4;
                                                                }
                                                                JSONArray fa = w.getJSONArray("files");
                                                                int i4 = 0;
                                                                int length4 = fa.length();
                                                                while (i4 < length4) {
                                                                    JSONObject f = fa.getJSONObject(i4);
                                                                    JSONArray da2 = da;
                                                                    String str11 = str;
                                                                    String str12 = str10;
                                                                    String string = f.getString(str11);
                                                                    Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
                                                                    String join = this$0.join(s2, string);
                                                                    String string2 = f.getString(str11);
                                                                    Intrinsics.checkNotNullExpressionValue(string2, "getString(...)");
                                                                    jobs.add(new Job(join, this$0.join(t2, string2), f.getLong("size"), 0L));
                                                                    i4++;
                                                                    w = w;
                                                                    fa = fa;
                                                                    str10 = str12;
                                                                    str = str11;
                                                                    da = da2;
                                                                }
                                                                pane2 = $dst;
                                                                pane3 = $src;
                                                                str8 = str;
                                                                dirs2 = dirs3;
                                                                t = $h;
                                                                applyAll2 = applyAll;
                                                                str9 = str10;
                                                                break;
                                                            } else {
                                                                String str13 = str;
                                                                String str14 = str2;
                                                                Sequence $this$forEach$iv = FilesKt.walkTopDown(new File(s2));
                                                                int $i$f$forEach4 = 0;
                                                                Iterator<File> it5 = $this$forEach$iv.iterator();
                                                                while (it5.hasNext()) {
                                                                    Object element$iv2 = it5.next();
                                                                    File f2 = (File) element$iv2;
                                                                    Sequence $this$forEach$iv2 = $this$forEach$iv;
                                                                    String rel = FilesKt.relativeTo(f2, new File(s2)).getPath();
                                                                    Intrinsics.checkNotNull(rel);
                                                                    if (rel.length() == 0) {
                                                                        s = s2;
                                                                        $i$f$forEach = $i$f$forEach4;
                                                                        str3 = str13;
                                                                        it = it5;
                                                                    } else if (f2.isDirectory()) {
                                                                        s = s2;
                                                                        dirs3.add(this$0.join(t2, rel));
                                                                        $i$f$forEach = $i$f$forEach4;
                                                                        str3 = str13;
                                                                        it = it5;
                                                                    } else {
                                                                        s = s2;
                                                                        $i$f$forEach = $i$f$forEach4;
                                                                        String absolutePath = f2.getAbsolutePath();
                                                                        str3 = str13;
                                                                        Intrinsics.checkNotNullExpressionValue(absolutePath, "getAbsolutePath(...)");
                                                                        it = it5;
                                                                        jobs.add(new Job(absolutePath, this$0.join(t2, rel), f2.length(), f2.lastModified() / 1000));
                                                                    }
                                                                    $this$forEach$iv = $this$forEach$iv2;
                                                                    s2 = s;
                                                                    $i$f$forEach4 = $i$f$forEach;
                                                                    str13 = str3;
                                                                    it5 = it;
                                                                }
                                                                pane2 = $dst;
                                                                pane3 = $src;
                                                                dirs2 = dirs3;
                                                                t = $h;
                                                                applyAll2 = applyAll;
                                                                str9 = str14;
                                                                str8 = str13;
                                                                break;
                                                            }
                                                        }
                                                    case 4:
                                                        n = n2;
                                                        if (!Intrinsics.areEqual(existing.get(n), Boolean.valueOf(e5.getDir()))) {
                                                            if ($dst.getRemote()) {
                                                                JSONObject put = new JSONObject().put(str6, "delete").put(str4, this$0.join($dst.getPath(), n));
                                                                Intrinsics.checkNotNullExpressionValue(put, str7);
                                                                this$0.opSync(put);
                                                            } else {
                                                                Boolean.valueOf(FilesKt.deleteRecursively(new File(this$0.join($dst.getPath(), n))));
                                                            }
                                                            break;
                                                        }
                                                        break;
                                                    default:
                                                        throw new NoWhenBranchMatchedException();
                                                }
                                            } catch (Throwable th3) {
                                                th = th3;
                                                pane = $dst;
                                                pane2 = $src;
                                                th = th;
                                                filesActivity = this$0;
                                                filesActivity.ui.post(new Runnable() { // from class: com.starpilot.starview.FilesActivity$$ExternalSyntheticLambda35
                                                    @Override // java.lang.Runnable
                                                    public final void run() {
                                                        FilesActivity.startCopy$lambda$51$lambda$50(this$0, pane2, pane);
                                                    }
                                                });
                                                throw th;
                                            }
                                        } catch (InterruptedException e6) {
                                            e = e6;
                                            pane = $dst;
                                            pane2 = $src;
                                            filesActivity = this$0;
                                            filesActivity.ui.post(new Runnable() { // from class: com.starpilot.starview.FilesActivity$$ExternalSyntheticLambda7
                                                @Override // java.lang.Runnable
                                                public final void run() {
                                                    this$0.toast("Cancelled");
                                                }
                                            });
                                            handler = filesActivity.ui;
                                            runnable = new Runnable() { // from class: com.starpilot.starview.FilesActivity$$ExternalSyntheticLambda35
                                                @Override // java.lang.Runnable
                                                public final void run() {
                                                    FilesActivity.startCopy$lambda$51$lambda$50(this$0, pane2, pane);
                                                }
                                            };
                                            handler.post(runnable);
                                        } catch (Exception e7) {
                                            e = e7;
                                            pane = $dst;
                                            pane2 = $src;
                                            e = e;
                                            filesActivity = this$0;
                                            filesActivity.ui.post(new Runnable() { // from class: com.starpilot.starview.FilesActivity$$ExternalSyntheticLambda34
                                                @Override // java.lang.Runnable
                                                public final void run() {
                                                    FilesActivity.startCopy$lambda$51$lambda$47(this$0, e);
                                                }
                                            });
                                            handler = filesActivity.ui;
                                            runnable = new Runnable() { // from class: com.starpilot.starview.FilesActivity$$ExternalSyntheticLambda35
                                                @Override // java.lang.Runnable
                                                public final void run() {
                                                    FilesActivity.startCopy$lambda$51$lambda$50(this$0, pane2, pane);
                                                }
                                            };
                                            handler.post(runnable);
                                        }
                                    } else {
                                        n = n3;
                                        applyAll = applyAll2;
                                        dirs = dirs2;
                                        str = str8;
                                        str2 = str9;
                                    }
                                    existing.put(target, Boolean.valueOf(e5.getDir()));
                                    String s22 = this$0.join($src.getPath(), n);
                                    String t22 = this$0.join($dst.getPath(), target);
                                    if (e5.getDir()) {
                                    }
                                } catch (InterruptedException e8) {
                                    e = e8;
                                    pane = $dst;
                                    pane2 = $src;
                                    filesActivity = this$0;
                                    filesActivity.ui.post(new Runnable() { // from class: com.starpilot.starview.FilesActivity$$ExternalSyntheticLambda7
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            this$0.toast("Cancelled");
                                        }
                                    });
                                    handler = filesActivity.ui;
                                    runnable = new Runnable() { // from class: com.starpilot.starview.FilesActivity$$ExternalSyntheticLambda35
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            FilesActivity.startCopy$lambda$51$lambda$50(this$0, pane2, pane);
                                        }
                                    };
                                    handler.post(runnable);
                                } catch (Exception e9) {
                                    e = e9;
                                    pane = $dst;
                                    pane2 = $src;
                                    e = e;
                                    filesActivity = this$0;
                                    filesActivity.ui.post(new Runnable() { // from class: com.starpilot.starview.FilesActivity$$ExternalSyntheticLambda34
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            FilesActivity.startCopy$lambda$51$lambda$47(this$0, e);
                                        }
                                    });
                                    handler = filesActivity.ui;
                                    runnable = new Runnable() { // from class: com.starpilot.starview.FilesActivity$$ExternalSyntheticLambda35
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            FilesActivity.startCopy$lambda$51$lambda$50(this$0, pane2, pane);
                                        }
                                    };
                                    handler.post(runnable);
                                } catch (Throwable th4) {
                                    th = th4;
                                    pane = $dst;
                                    pane2 = $src;
                                    th = th;
                                    filesActivity = this$0;
                                    filesActivity.ui.post(new Runnable() { // from class: com.starpilot.starview.FilesActivity$$ExternalSyntheticLambda35
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            FilesActivity.startCopy$lambda$51$lambda$50(this$0, pane2, pane);
                                        }
                                    });
                                    throw th;
                                }
                                target = n3;
                            }
                        } catch (InterruptedException e10) {
                            e = e10;
                        } catch (Exception e11) {
                            e = e11;
                        } catch (Throwable th5) {
                            th = th5;
                        }
                    }
                    ArrayList dirs4 = dirs2;
                    String label = t;
                    try {
                        Iterator it6 = dirs4.iterator();
                        Intrinsics.checkNotNullExpressionValue(it6, "iterator(...)");
                        while (it6.hasNext()) {
                            Object next = it6.next();
                            Intrinsics.checkNotNullExpressionValue(next, "next(...)");
                            String dd = (String) next;
                            if ($dst.getRemote()) {
                                JSONObject put2 = new JSONObject().put("op", "mkdir").put("path", dd);
                                Intrinsics.checkNotNullExpressionValue(put2, "put(...)");
                                this$0.opSync(put2);
                            } else {
                                Boolean.valueOf(new File(dd).mkdirs());
                            }
                        }
                        final long total = 0;
                        for (Job it7 : jobs) {
                            total += it7.getSize();
                        }
                        final Ref.LongRef doneBytes = new Ref.LongRef();
                        final long t0 = System.nanoTime();
                        final CountDownLatch latch = new CountDownLatch(1);
                        this$0.ui.post(new Runnable() { // from class: com.starpilot.starview.FilesActivity$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                FilesActivity.startCopy$lambda$51$lambda$39(this$0, jobs, latch);
                            }
                        });
                        latch.await();
                        Ref.IntRef failed2 = new Ref.IntRef();
                        int i5 = 0;
                        for (Job j2 : jobs) {
                            final int i6 = i5;
                            i5++;
                            try {
                                this$0.waitIfPaused();
                                ArrayList dirs5 = dirs4;
                                if (this$0.cancel.get()) {
                                    throw new InterruptedException("cancelled");
                                }
                                try {
                                    Ref.IntRef failed3 = failed2;
                                    final String label2 = (i6 + 1) + "/" + jobs.size() + "  " + new File(j2.getSrcPath()).getName();
                                    try {
                                        HashMap existing2 = existing;
                                        CountDownLatch latch2 = latch;
                                        ArrayList jobs2 = jobs;
                                        Function1 progress = new Function1() { // from class: com.starpilot.starview.FilesActivity$$ExternalSyntheticLambda2
                                            @Override // kotlin.jvm.functions.Function1
                                            public final Object invoke(Object obj) {
                                                Unit startCopy$lambda$51$lambda$41;
                                                startCopy$lambda$51$lambda$41 = FilesActivity.startCopy$lambda$51$lambda$41(doneBytes, t0, this$0, label2, total, i6, ((Long) obj).longValue());
                                                return startCopy$lambda$51$lambda$41;
                                            }
                                        };
                                        this$0.ui.post(new Runnable() { // from class: com.starpilot.starview.FilesActivity$$ExternalSyntheticLambda3
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                FilesActivity.startCopy$lambda$51$lambda$42(this$0, i6);
                                            }
                                        });
                                        try {
                                            try {
                                            } catch (InterruptedException x) {
                                                throw x;
                                            }
                                        } catch (Exception x2) {
                                            if (this$0.cancel.get()) {
                                                throw new InterruptedException("cancelled");
                                            }
                                            failed3.element++;
                                            final String msg = x2.getMessage();
                                            this$0.ui.post(new Runnable() { // from class: com.starpilot.starview.FilesActivity$$ExternalSyntheticLambda5
                                                @Override // java.lang.Runnable
                                                public final void run() {
                                                    FilesActivity.startCopy$lambda$51$lambda$44(this$0, i6, msg);
                                                }
                                            });
                                        }
                                        if ($src.getRemote()) {
                                            try {
                                                if (!$dst.getRemote()) {
                                                    this$0.download($h, j2, progress);
                                                    this$0.ui.post(new Runnable() { // from class: com.starpilot.starview.FilesActivity$$ExternalSyntheticLambda4
                                                        @Override // java.lang.Runnable
                                                        public final void run() {
                                                            FilesActivity.startCopy$lambda$51$lambda$43(this$0, i6);
                                                        }
                                                    });
                                                    doneBytes.element += j2.getSize();
                                                    failed2 = failed3;
                                                    label = $h;
                                                    dirs4 = dirs5;
                                                    jobs = jobs2;
                                                    existing = existing2;
                                                    latch = latch2;
                                                }
                                            } catch (InterruptedException x3) {
                                                throw x3;
                                            }
                                        }
                                        if ($src.getRemote() || !$dst.getRemote()) {
                                            throw new RuntimeException("same side");
                                        }
                                        this$0.upload($h, j2, progress);
                                        this$0.ui.post(new Runnable() { // from class: com.starpilot.starview.FilesActivity$$ExternalSyntheticLambda4
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                FilesActivity.startCopy$lambda$51$lambda$43(this$0, i6);
                                            }
                                        });
                                        doneBytes.element += j2.getSize();
                                        failed2 = failed3;
                                        label = $h;
                                        dirs4 = dirs5;
                                        jobs = jobs2;
                                        existing = existing2;
                                        latch = latch2;
                                    } catch (InterruptedException e12) {
                                        e = e12;
                                        pane = $dst;
                                        pane2 = $src;
                                        filesActivity = this$0;
                                        filesActivity.ui.post(new Runnable() { // from class: com.starpilot.starview.FilesActivity$$ExternalSyntheticLambda7
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                this$0.toast("Cancelled");
                                            }
                                        });
                                        handler = filesActivity.ui;
                                        runnable = new Runnable() { // from class: com.starpilot.starview.FilesActivity$$ExternalSyntheticLambda35
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                FilesActivity.startCopy$lambda$51$lambda$50(this$0, pane2, pane);
                                            }
                                        };
                                        handler.post(runnable);
                                    } catch (Exception e13) {
                                        e = e13;
                                        pane = $dst;
                                        pane2 = $src;
                                        e = e;
                                        filesActivity = this$0;
                                        filesActivity.ui.post(new Runnable() { // from class: com.starpilot.starview.FilesActivity$$ExternalSyntheticLambda34
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                FilesActivity.startCopy$lambda$51$lambda$47(this$0, e);
                                            }
                                        });
                                        handler = filesActivity.ui;
                                        runnable = new Runnable() { // from class: com.starpilot.starview.FilesActivity$$ExternalSyntheticLambda35
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                FilesActivity.startCopy$lambda$51$lambda$50(this$0, pane2, pane);
                                            }
                                        };
                                        handler.post(runnable);
                                    } catch (Throwable th6) {
                                        th = th6;
                                        pane = $dst;
                                        pane2 = $src;
                                        th = th;
                                        filesActivity = this$0;
                                        filesActivity.ui.post(new Runnable() { // from class: com.starpilot.starview.FilesActivity$$ExternalSyntheticLambda35
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                FilesActivity.startCopy$lambda$51$lambda$50(this$0, pane2, pane);
                                            }
                                        });
                                        throw th;
                                    }
                                } catch (InterruptedException e14) {
                                    e = e14;
                                } catch (Exception e15) {
                                    e = e15;
                                } catch (Throwable th7) {
                                    th = th7;
                                }
                            } catch (InterruptedException e16) {
                                e = e16;
                            } catch (Exception e17) {
                                e = e17;
                            } catch (Throwable th8) {
                                th = th8;
                            }
                        }
                        ArrayList jobs3 = jobs;
                        failed = failed2;
                        ok = jobs3.size() - failed.element;
                        pane2 = $src;
                        j = total;
                        filesActivity = this$0;
                    } catch (InterruptedException e18) {
                        e = e18;
                        pane = $dst;
                        pane2 = $src;
                        filesActivity = this$0;
                        filesActivity.ui.post(new Runnable() { // from class: com.starpilot.starview.FilesActivity$$ExternalSyntheticLambda7
                            @Override // java.lang.Runnable
                            public final void run() {
                                this$0.toast("Cancelled");
                            }
                        });
                        handler = filesActivity.ui;
                        runnable = new Runnable() { // from class: com.starpilot.starview.FilesActivity$$ExternalSyntheticLambda35
                            @Override // java.lang.Runnable
                            public final void run() {
                                FilesActivity.startCopy$lambda$51$lambda$50(this$0, pane2, pane);
                            }
                        };
                        handler.post(runnable);
                    } catch (Exception e19) {
                        e = e19;
                        pane = $dst;
                        pane2 = $src;
                        filesActivity = this$0;
                        e = e;
                        filesActivity.ui.post(new Runnable() { // from class: com.starpilot.starview.FilesActivity$$ExternalSyntheticLambda34
                            @Override // java.lang.Runnable
                            public final void run() {
                                FilesActivity.startCopy$lambda$51$lambda$47(this$0, e);
                            }
                        });
                        handler = filesActivity.ui;
                        runnable = new Runnable() { // from class: com.starpilot.starview.FilesActivity$$ExternalSyntheticLambda35
                            @Override // java.lang.Runnable
                            public final void run() {
                                FilesActivity.startCopy$lambda$51$lambda$50(this$0, pane2, pane);
                            }
                        };
                        handler.post(runnable);
                    } catch (Throwable th9) {
                        th = th9;
                        pane = $dst;
                        pane2 = $src;
                        filesActivity = this$0;
                        th = th;
                        filesActivity.ui.post(new Runnable() { // from class: com.starpilot.starview.FilesActivity$$ExternalSyntheticLambda35
                            @Override // java.lang.Runnable
                            public final void run() {
                                FilesActivity.startCopy$lambda$51$lambda$50(this$0, pane2, pane);
                            }
                        });
                        throw th;
                    }
                    try {
                        this$0.ui.post(new Runnable() { // from class: com.starpilot.starview.FilesActivity$$ExternalSyntheticLambda6
                            @Override // java.lang.Runnable
                            public final void run() {
                                FilesActivity.startCopy$lambda$51$lambda$45(this$0, ok, j, failed);
                            }
                        });
                        handler = filesActivity.ui;
                        runnable = new Runnable() { // from class: com.starpilot.starview.FilesActivity$$ExternalSyntheticLambda35
                            @Override // java.lang.Runnable
                            public final void run() {
                                FilesActivity.startCopy$lambda$51$lambda$50(this$0, pane2, $dst);
                            }
                        };
                    } catch (InterruptedException e20) {
                        e = e20;
                        pane = $dst;
                        filesActivity.ui.post(new Runnable() { // from class: com.starpilot.starview.FilesActivity$$ExternalSyntheticLambda7
                            @Override // java.lang.Runnable
                            public final void run() {
                                this$0.toast("Cancelled");
                            }
                        });
                        handler = filesActivity.ui;
                        runnable = new Runnable() { // from class: com.starpilot.starview.FilesActivity$$ExternalSyntheticLambda35
                            @Override // java.lang.Runnable
                            public final void run() {
                                FilesActivity.startCopy$lambda$51$lambda$50(this$0, pane2, pane);
                            }
                        };
                        handler.post(runnable);
                    } catch (Exception e21) {
                        e = e21;
                        pane = $dst;
                        e = e;
                        filesActivity.ui.post(new Runnable() { // from class: com.starpilot.starview.FilesActivity$$ExternalSyntheticLambda34
                            @Override // java.lang.Runnable
                            public final void run() {
                                FilesActivity.startCopy$lambda$51$lambda$47(this$0, e);
                            }
                        });
                        handler = filesActivity.ui;
                        runnable = new Runnable() { // from class: com.starpilot.starview.FilesActivity$$ExternalSyntheticLambda35
                            @Override // java.lang.Runnable
                            public final void run() {
                                FilesActivity.startCopy$lambda$51$lambda$50(this$0, pane2, pane);
                            }
                        };
                        handler.post(runnable);
                    } catch (Throwable th10) {
                        th = th10;
                        pane = $dst;
                        th = th;
                        filesActivity.ui.post(new Runnable() { // from class: com.starpilot.starview.FilesActivity$$ExternalSyntheticLambda35
                            @Override // java.lang.Runnable
                            public final void run() {
                                FilesActivity.startCopy$lambda$51$lambda$50(this$0, pane2, pane);
                            }
                        });
                        throw th;
                    }
                } catch (InterruptedException e22) {
                    pane = pane2;
                    pane2 = pane3;
                } catch (Exception e23) {
                    e = e23;
                    pane = pane2;
                    pane2 = pane3;
                } catch (Throwable th11) {
                    th = th11;
                    pane = pane2;
                    pane2 = pane3;
                }
            } catch (Throwable th12) {
                th = th12;
            }
        } catch (InterruptedException e24) {
            e = e24;
            pane = pane2;
            pane2 = pane3;
        } catch (Exception e25) {
            e = e25;
            pane = pane2;
            pane2 = pane3;
        } catch (Throwable th13) {
            th = th13;
            pane = pane2;
            pane2 = pane3;
        }
        handler.post(runnable);
    }

    /* VF: from Vineflower (jadx: ) */
    public static final Unit startCopy$lambda$51$lambda$36(ObjectRef var0, FilesActivity.Clash var1) {
        Intrinsics.checkNotNullParameter(var1, "it");
        var0.element = (T)var1;
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void startCopy$lambda$51$lambda$39(FilesActivity this$0, ArrayList $jobs, CountDownLatch $latch) {
        this$0.buildQueue($jobs);
        $latch.countDown();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit startCopy$lambda$51$lambda$41(Ref.LongRef $doneBytes, long $t0, final FilesActivity this$0, final String $label, final long $total, final int $i, final long fileDone) {
        final long all = $doneBytes.element + fileDone;
        double secs = (System.nanoTime() - $t0) / 1.0E9d;
        final double rate = secs > 0.5d ? all / secs : 0.0d;
        this$0.ui.post(new Runnable() { // from class: com.starpilot.starview.FilesActivity$$ExternalSyntheticLambda15
            @Override // java.lang.Runnable
            public final void run() {
                FilesActivity.startCopy$lambda$51$lambda$41$lambda$40(this$0, $label, all, $total, rate, $i, fileDone);
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void startCopy$lambda$51$lambda$41$lambda$40(FilesActivity this$0, String $label, long $all, long $total, double $rate, int $i, long $fileDone) {
        TextView textView = this$0.progTv;
        ProgressBar progressBar = null;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("progTv");
            textView = null;
        }
        String str = this$0.paused.get() ? "PAUSED  ·  " : "";
        textView.setText(str + $label + "  ·  " + this$0.fmtSize($all) + " / " + this$0.fmtSize($total) + "  ·  " + this$0.fmtSize((long) $rate) + "/s");
        ProgressBar progressBar2 = this$0.progBar;
        if (progressBar2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("progBar");
        } else {
            progressBar = progressBar2;
        }
        progressBar.setProgress($total > 0 ? (int) ((1000 * $all) / $total) : 0);
        markRow$default(this$0, $i, QState.COPYING, $fileDone, null, 8, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void startCopy$lambda$51$lambda$42(FilesActivity this$0, int $i) {
        markRow$default(this$0, $i, QState.COPYING, 0L, null, 8, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void startCopy$lambda$51$lambda$43(FilesActivity this$0, int $i) {
        markRow$default(this$0, $i, QState.DONE, 0L, null, 12, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void startCopy$lambda$51$lambda$44(FilesActivity this$0, int $i, String $msg) {
        markRow$default(this$0, $i, QState.FAILED, 0L, $msg, 4, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void startCopy$lambda$51$lambda$45(FilesActivity this$0, int $ok, long $total, Ref.IntRef $failed) {
        String str;
        String fmtSize = this$0.fmtSize($total);
        if ($failed.element > 0) {
            str = " - " + $failed.element + " failed (marked in the list)";
        } else {
            str = "";
        }
        this$0.toast("Copied " + $ok + " file(s), " + fmtSize + str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void startCopy$lambda$51$lambda$47(FilesActivity this$0, Exception $e) {
        this$0.toast("Copy failed: " + $e.getMessage());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void startCopy$lambda$51$lambda$50(final FilesActivity this$0, Pane $src, Pane $dst) {
        boolean z = false;
        this$0.busy = false;
        $src.getSelected().clear();
        $src.render();
        $dst.refresh();
        this$0.updateButtons();
        TextView textView = this$0.progTv;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("progTv");
            textView = null;
        }
        textView.setText("Finished");
        Handler handler = this$0.ui;
        Runnable runnable = new Runnable() { // from class: com.starpilot.starview.FilesActivity$$ExternalSyntheticLambda16
            @Override // java.lang.Runnable
            public final void run() {
                FilesActivity.startCopy$lambda$51$lambda$50$lambda$48(this$0);
            }
        };
        Iterable $this$any$iv = this$0.queueRows;
        if (!($this$any$iv instanceof Collection) || !((Collection) $this$any$iv).isEmpty()) {
            Iterator it = $this$any$iv.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Object element$iv = it.next();
                QRow it2 = (QRow) element$iv;
                CharSequence text = it2.getState().getText();
                Intrinsics.checkNotNullExpressionValue(text, "getText(...)");
                if (StringsKt.startsWith(text, (CharSequence) "✕", false)) {
                    z = true;
                    break;
                }
            }
        }
        handler.postDelayed(runnable, z ? 15000L : 3000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void startCopy$lambda$51$lambda$50$lambda$48(FilesActivity this$0) {
        if (this$0.busy) {
            return;
        }
        LinearLayout linearLayout = this$0.progBox;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("progBox");
            linearLayout = null;
        }
        linearLayout.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: FilesActivity.kt */
    enum Clash {
        REPLACE,
        KEEP_BOTH,
        SKIP,
        CANCEL;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries($VALUES);

        public static EnumEntries<Clash> getEntries() {
            return $ENTRIES;
        }
    }

    /* VF: from Vineflower (jadx: Type inference failed) */
    private final FilesActivity.Clash askClash(
        String var1, boolean var2, boolean var3, String var4, String var5, Function1<? super FilesActivity.Clash, Unit> var6
    ) {
        CountDownLatch var7 = new CountDownLatch(1);
        ObjectRef var8 = new ObjectRef();
        var8.element = (T)FilesActivity.Clash.CANCEL;
        this.ui.post(new FilesActivity$$ExternalSyntheticLambda18(this, var3, var2, var1, var4, var5, var8, var6, var7));
        var7.await();
        return (FilesActivity.Clash)var8.element;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void askClash$lambda$60(FilesActivity this$0, boolean $dstDir, boolean $srcDir, String $name, String $side, String $folder, final Ref.ObjectRef $result, final Function1 $setAll, final CountDownLatch $latch) {
        final CheckBox $this$askClash_u24lambda_u2460_u24lambda_u2452 = new CheckBox(this$0);
        $this$askClash_u24lambda_u2460_u24lambda_u2452.setText("Do this for all remaining conflicts");
        $this$askClash_u24lambda_u2460_u24lambda_u2452.setPadding(this$0.dp(8), this$0.dp(8), 0, 0);
        String what = $dstDir ? "A folder" : "A file";
        boolean merge = $srcDir && $dstDir;
        String msg = what + " named \"" + $name + "\" already exists in " + $side + ":" + $folder + "." + (merge ? "\n\nReplace merges the folders: files with the same name are overwritten, other files stay." : "");
        LinearLayout $this$askClash_u24lambda_u2460_u24lambda_u2453 = new LinearLayout(this$0);
        $this$askClash_u24lambda_u2460_u24lambda_u2453.setOrientation(1);
        $this$askClash_u24lambda_u2460_u24lambda_u2453.setPadding(this$0.dp(20), this$0.dp(8), this$0.dp(20), 0);
        $this$askClash_u24lambda_u2460_u24lambda_u2453.addView($this$askClash_u24lambda_u2460_u24lambda_u2452);
        String[] labels = new String[4];
        labels[0] = merge ? "Replace (merge)" : "Replace";
        labels[1] = "Keep both";
        labels[2] = "Skip";
        labels[3] = "Cancel copy";
        final AlertDialog dlg = new AlertDialog.Builder(this$0).setTitle("\"" + $name + "\" already exists").setMessage(msg).setView($this$askClash_u24lambda_u2460_u24lambda_u2453).setCancelable(false).setPositiveButton(labels[0], new DialogInterface.OnClickListener() { // from class: com.starpilot.starview.FilesActivity$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                FilesActivity.askClash$lambda$60$lambda$54($result, $this$askClash_u24lambda_u2460_u24lambda_u2452, $setAll, $latch, dialogInterface, i);
            }
        }).setNeutralButton(labels[1], new DialogInterface.OnClickListener() { // from class: com.starpilot.starview.FilesActivity$$ExternalSyntheticLambda11
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                FilesActivity.askClash$lambda$60$lambda$55($result, $this$askClash_u24lambda_u2460_u24lambda_u2452, $setAll, $latch, dialogInterface, i);
            }
        }).setNegativeButton(labels[2], new DialogInterface.OnClickListener() { // from class: com.starpilot.starview.FilesActivity$$ExternalSyntheticLambda22
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                FilesActivity.askClash$lambda$60$lambda$56($result, $this$askClash_u24lambda_u2460_u24lambda_u2452, $setAll, $latch, dialogInterface, i);
            }
        }).show();
        Button $this$askClash_u24lambda_u2460_u24lambda_u2459_u24lambda_u2458 = new Button(this$0);
        $this$askClash_u24lambda_u2460_u24lambda_u2459_u24lambda_u2458.setText(labels[3]);
        $this$askClash_u24lambda_u2460_u24lambda_u2459_u24lambda_u2458.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.FilesActivity$$ExternalSyntheticLambda29
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                FilesActivity.askClash$lambda$60$lambda$59$lambda$58$lambda$57($result, dlg, $latch, view);
            }
        });
        $this$askClash_u24lambda_u2460_u24lambda_u2453.addView($this$askClash_u24lambda_u2460_u24lambda_u2459_u24lambda_u2458);
    }

    /* VF: from Vineflower (jadx: Type inference failed) */
    public static final void askClash$lambda$60$lambda$54(ObjectRef var0, CheckBox var1, Function1 var2, CountDownLatch var3, DialogInterface var4, int var5) {
        var0.element = (T)FilesActivity.Clash.REPLACE;
        if (var1.isChecked()) {
            var2.invoke(var0.element);
        }

        var3.countDown();
    }

    /* VF: from Vineflower (jadx: Type inference failed) */
    public static final void askClash$lambda$60$lambda$55(ObjectRef var0, CheckBox var1, Function1 var2, CountDownLatch var3, DialogInterface var4, int var5) {
        var0.element = (T)FilesActivity.Clash.KEEP_BOTH;
        if (var1.isChecked()) {
            var2.invoke(var0.element);
        }

        var3.countDown();
    }

    /* VF: from Vineflower (jadx: Type inference failed) */
    public static final void askClash$lambda$60$lambda$56(ObjectRef var0, CheckBox var1, Function1 var2, CountDownLatch var3, DialogInterface var4, int var5) {
        var0.element = (T)FilesActivity.Clash.SKIP;
        if (var1.isChecked()) {
            var2.invoke(var0.element);
        }

        var3.countDown();
    }

    /* VF: from Vineflower (jadx: Type inference failed) */
    public static final void askClash$lambda$60$lambda$59$lambda$58$lambda$57(ObjectRef var0, AlertDialog var1, CountDownLatch var2, View var3) {
        var0.element = (T)FilesActivity.Clash.CANCEL;
        var1.dismiss();
        var2.countDown();
    }

    private final String freeName(String name, boolean dir, Set<String> taken) {
        String base;
        String str;
        int dot = -1;
        if (!dir) {
            Integer valueOf = Integer.valueOf(StringsKt.lastIndexOf((CharSequence) name, '.', kotlin.text.StringsKt.getLastIndex(((CharSequence) name)), false));
            int it = valueOf.intValue();
            if (!(it > 0)) {
                valueOf = null;
            }
            if (valueOf != null) {
                dot = valueOf.intValue();
            }
        }
        if (dot > 0) {
            base = name.substring(0, dot);
            Intrinsics.checkNotNullExpressionValue(base, "substring(...)");
        } else {
            base = name;
        }
        if (dot > 0) {
            str = name.substring(dot);
            Intrinsics.checkNotNullExpressionValue(str, "substring(...)");
        } else {
            str = "";
        }
        String ext = str;
        int i = 1;
        while (taken.contains(base + " (" + i + ")" + ext)) {
            i++;
        }
        return base + " (" + i + ")" + ext;
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x00bc, code lost:
    
        if (r0 == null) goto L21;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final void download(String h, Job j, Function1<? super Long, Unit> progress) {
        Throwable th;
        String str;
        String string;
        File out = new File(j.getDstPath());
        File parentFile = out.getParentFile();
        if (parentFile != null) {
            parentFile.mkdirs();
        }
        File tmp = new File(out.getParentFile(), "." + out.getName() + ".part");
        Response execute = this.http.newCall(new Request.Builder().url("http://" + h + ":8090/fs/get?path=" + Uri.encode(j.getSrcPath())).build()).execute();
        try {
            Response r = execute;
            if (!r.isSuccessful()) {
                ResponseBody body = r.body();
                if (body != null && (string = body.string()) != null) {
                    String str2 = string;
                    if (str2.length() == 0) {
                        str2 = "HTTP " + r.code();
                    }
                    str = str2;
                }
                str = "HTTP " + r.code();
                throw new RuntimeException(str);
            }
            try {
                ResponseBody body2 = r.body();
                Intrinsics.checkNotNull(body2);
                InputStream src = body2.byteStream();
                FileOutputStream fileOutputStream = new FileOutputStream(tmp);
                try {
                    FileOutputStream os = fileOutputStream;
                    byte[] buf = new byte[262144];
                    long done = 0;
                    long lastUi = 0;
                    while (true) {
                        int it = src.read(buf);
                        if (it <= 0) {
                            Unit unit = Unit.INSTANCE;
                            CloseableKt.closeFinally(fileOutputStream, null);
                            Unit unit2 = Unit.INSTANCE;
                            CloseableKt.closeFinally(execute, null);
                            if (!tmp.renameTo(out)) {
                                out.delete();
                                if (!tmp.renameTo(out)) {
                                    throw new RuntimeException("rename failed: " + out.getPath());
                                }
                            }
                            if (j.getMtime() > 0) {
                                out.setLastModified(j.getMtime() * 1000);
                                return;
                            }
                            return;
                        }
                        if (this.cancel.get()) {
                            throw new InterruptedException("cancelled");
                        }
                        waitIfPaused();
                        os.write(buf, 0, it);
                        InputStream src2 = src;
                        done += it;
                        try {
                            if (System.nanoTime() - lastUi > 100000000) {
                                lastUi = System.nanoTime();
                                try {
                                    progress.invoke(Long.valueOf(done));
                                } catch (Throwable th2) {
                                    th = th2;
                                    th = th;
                                    try {
                                        throw th;
                                    } catch (Throwable th3) {
                                        CloseableKt.closeFinally(fileOutputStream, th);
                                        throw th3;
                                    }
                                }
                            }
                            src = src2;
                        } catch (Throwable th4) {
                            th = th4;
                        }
                    }
                } catch (Throwable th5) {
                    th = th5;
                }
            } catch (Throwable th6) {
                th = th6;
                Throwable th7 = th;
                try {
                    throw th7;
                } catch (Throwable th8) {
                    CloseableKt.closeFinally(execute, th7);
                    throw th8;
                }
            }
        } catch (Throwable th9) {
            th = th9;
        }
    }

    private final void upload(String h, Job j, final Function1<? super Long, Unit> progress) {
        String txt;
        final File f = new File(j.getSrcPath());
        RequestBody requestBody = new RequestBody() { // from class: com.starpilot.starview.FilesActivity$upload$body$1
            @Override // okhttp3.RequestBody
            /* renamed from: contentType */
            public MediaType getContentType() {
                return MediaType.Companion.get("application/octet-stream");
            }

            @Override // okhttp3.RequestBody
            public long contentLength() {
                return f.length();
            }

            @Override // okhttp3.RequestBody
            public boolean isOneShot() {
                return true;
            }

            @Override // okhttp3.RequestBody
            public void writeTo(BufferedSink sink) {
                AtomicBoolean atomicBoolean;
                Intrinsics.checkNotNullParameter(sink, "sink");
                FileInputStream fileInputStream = new FileInputStream(f);
                FilesActivity filesActivity = this;
                Function1<Long, Unit> function1 = progress;
                try {
                    FileInputStream ins = fileInputStream;
                    byte[] buf = new byte[262144];
                    long done = 0;
                    long lastUi = 0;
                    while (true) {
                        int it = ins.read(buf);
                        if (it > 0) {
                            atomicBoolean = filesActivity.cancel;
                            if (!atomicBoolean.get()) {
                                filesActivity.waitIfPaused();
                                sink.write(buf, 0, it);
                                done += it;
                                if (System.nanoTime() - lastUi > 100000000) {
                                    lastUi = System.nanoTime();
                                    function1.invoke(Long.valueOf(done));
                                }
                            } else {
                                throw new InterruptedException("cancelled");
                            }
                        } else {
                            Unit unit = Unit.INSTANCE;
                            CloseableKt.closeFinally(fileInputStream, null);
                            return;
                        }
                    }
                } catch (Throwable th) {
                    try {
                        throw th;
                    } catch (Throwable th2) {
                        CloseableKt.closeFinally(fileInputStream, th);
                        throw th2;
                    }
                }
            }
        };
        String url = "http://" + h + ":8090/fs/put?path=" + Uri.encode(j.getDstPath()) + "&mtime=" + j.getMtime();
        Response execute = this.http.newCall(new Request.Builder().url(url).put(requestBody).build()).execute();
        try {
            Response r = execute;
            ResponseBody body = r.body();
            if (body == null || (txt = body.string()) == null) {
                txt = "";
            }
            if (r.isSuccessful()) {
                Unit unit = Unit.INSTANCE;
                CloseableKt.closeFinally(execute, null);
            } else {
                String str = txt;
                if (str.length() == 0) {
                    str = "HTTP " + r.code();
                }
                throw new RuntimeException(str);
            }
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                CloseableKt.closeFinally(execute, th);
                throw th2;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showPreview(final Pane pane, final Entry e, final String full, FileKinds.Kind kind, byte[] bytes, boolean truncated) {
        final String side = pane.getRemote() ? "comma" : "tablet";
        if (kind.getPreview() == FileKinds.Preview.IMAGE) {
            Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            if (bmp == null) {
                FilesActivity $this$showPreview_u24lambda_u2468 = this;
                $this$showPreview_u24lambda_u2468.toast(e.getName() + ": not an image this tablet can read");
                return;
            }
            ImageView $this$showPreview_u24lambda_u2469 = new ImageView(this);
            $this$showPreview_u24lambda_u2469.setImageBitmap(bmp);
            $this$showPreview_u24lambda_u2469.setAdjustViewBounds(true);
            $this$showPreview_u24lambda_u2469.setBackgroundColor(-16777216);
            new AlertDialog.Builder(this).setTitle(e.getName() + "  ·  " + bmp.getWidth() + "×" + bmp.getHeight() + "  ·  " + side).setView($this$showPreview_u24lambda_u2469).setPositiveButton("Close", (DialogInterface.OnClickListener) null).show();
            return;
        }
        if (!FileKinds.INSTANCE.showable(bytes)) {
            toast(e.getName() + ": binary file - copy it to open it");
            return;
        }
        String str = new String(bytes, Charsets.UTF_8);
        Object text = kind.getPreview();
        if (text == FileKinds.Preview.JSON) {
            try {
                Result.Companion companion = Result.Companion;
                FilesActivity filesActivity = this;
                str = StringsKt.startsWith(StringsKt.trimStart((CharSequence) str).toString(), "[", false) ? new JSONArray(str).toString(2) : new JSONObject(str).toString(2);
                KtCompat.resultOf(Unit.INSTANCE);
            } catch (Throwable th) {
                Result.Companion companion2 = Result.Companion;
                KtCompat.resultOf(ResultKt.createFailure(th));
            }
        }
        Object text2 = str;
        boolean editable = !truncated && bytes.length <= 262144;
        final EditText $this$showPreview_u24lambda_u2471 = new EditText(this);
        $this$showPreview_u24lambda_u2471.setText((CharSequence) text2);
        $this$showPreview_u24lambda_u2471.setTypeface(Typeface.MONOSPACE);
        $this$showPreview_u24lambda_u2471.setTextSize(13.0f);
        $this$showPreview_u24lambda_u2471.setTextColor(-1);
        $this$showPreview_u24lambda_u2471.setBackgroundColor(Color.rgb(11, 13, 18));
        $this$showPreview_u24lambda_u2471.setGravity(8388659);
        $this$showPreview_u24lambda_u2471.setPadding(dp(14), dp(10), dp(14), dp(10));
        $this$showPreview_u24lambda_u2471.setFocusable(false);
        $this$showPreview_u24lambda_u2471.setFocusableInTouchMode(false);
        $this$showPreview_u24lambda_u2471.setHorizontallyScrolling(true);
        $this$showPreview_u24lambda_u2471.setInputType(655361);
        FrameLayout $this$showPreview_u24lambda_u2473 = new FrameLayout(this);
        ScrollView $this$showPreview_u24lambda_u2473_u24lambda_u2472 = new ScrollView(this);
        $this$showPreview_u24lambda_u2473_u24lambda_u2472.addView($this$showPreview_u24lambda_u2471);
        $this$showPreview_u24lambda_u2473.addView($this$showPreview_u24lambda_u2473_u24lambda_u2472, new FrameLayout.LayoutParams(-1, dp(560)));
        String title = e.getName() + "  ·  " + fmtSize(e.getSize()) + "  ·  " + side + (truncated ? "  ·  first " + fmtSize(bytes.length) : "");
        AlertDialog.Builder $this$showPreview_u24lambda_u2475 = new AlertDialog.Builder(this).setTitle(title).setView($this$showPreview_u24lambda_u2473).setNegativeButton("Close", (DialogInterface.OnClickListener) null).setNeutralButton("Copy text", new DialogInterface.OnClickListener() { // from class: com.starpilot.starview.FilesActivity$$ExternalSyntheticLambda30
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                FilesActivity.showPreview$lambda$74(FilesActivity.this, e, $this$showPreview_u24lambda_u2471, dialogInterface, i);
            }
        });
        if (editable) {
            $this$showPreview_u24lambda_u2475.setPositiveButton("Edit", (DialogInterface.OnClickListener) null);
        }
        final AlertDialog dlg = $this$showPreview_u24lambda_u2475.show();
        if (editable) {
            dlg.getButton(-1).setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.FilesActivity$$ExternalSyntheticLambda31
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    FilesActivity.showPreview$lambda$78($this$showPreview_u24lambda_u2471, this, e, side, full, pane, dlg, view);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showPreview$lambda$74(FilesActivity this$0, Entry $e, EditText $et, DialogInterface dialogInterface, int i) {
        Object systemService = this$0.getSystemService("clipboard");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.content.ClipboardManager");
        ((ClipboardManager) systemService).setPrimaryClip(ClipData.newPlainText($e.getName(), $et.getText().toString()));
        this$0.toast("Copied");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showPreview$lambda$78(final EditText $et, final FilesActivity this$0, Entry $e, String $side, final String $full, final Pane $pane, final AlertDialog $dlg, View b) {
        if ($et.isFocusable()) {
            new AlertDialog.Builder(this$0).setTitle("Save " + $e.getName() + " on the " + $side + "?").setMessage("The file is replaced with your edit. " + $full).setPositiveButton("Save", new DialogInterface.OnClickListener() { // from class: com.starpilot.starview.FilesActivity$$ExternalSyntheticLambda17
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    FilesActivity.showPreview$lambda$78$lambda$77(this$0, $pane, $full, $et, $dlg, dialogInterface, i);
                }
            }).setNegativeButton("Cancel", (DialogInterface.OnClickListener) null).show();
        } else {
            $et.setFocusable(true);
            $et.setFocusableInTouchMode(true);
            $et.requestFocus();
            Intrinsics.checkNotNull(b, "null cannot be cast to non-null type android.widget.Button");
            ((Button) b).setText("Save");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showPreview$lambda$78$lambda$77(FilesActivity this$0, Pane $pane, String $full, EditText $et, final AlertDialog $dlg, DialogInterface dialogInterface, int i) {
        this$0.saveText($pane, $full, $et.getText().toString(), new Function0() { // from class: com.starpilot.starview.FilesActivity$$ExternalSyntheticLambda32
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit showPreview$lambda$78$lambda$77$lambda$76;
                showPreview$lambda$78$lambda$77$lambda$76 = FilesActivity.showPreview$lambda$78$lambda$77$lambda$76($dlg);
                return showPreview$lambda$78$lambda$77$lambda$76;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit showPreview$lambda$78$lambda$77$lambda$76(AlertDialog $dlg) {
        $dlg.dismiss();
        return Unit.INSTANCE;
    }

    private final void saveText(final Pane pane, final String full, final String text, final Function0<Unit> done) {
        this.io.execute(new Runnable() { // from class: com.starpilot.starview.FilesActivity$$ExternalSyntheticLambda23
            @Override // java.lang.Runnable
            public final void run() {
                FilesActivity.saveText$lambda$83(FilesActivity.Pane.this, this, full, text, done);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x00ae, code lost:
    
        if (r1 == null) goto L22;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void saveText$lambda$83(final Pane $pane, final FilesActivity this$0, String $full, String $text, final Function0 $done) {
        String str;
        String string;
        try {
            if ($pane.getRemote()) {
                String h = this$0.getHost();
                if (h == null) {
                    throw new RuntimeException("not connected");
                }
                OkHttpClient okHttpClient = this$0.http;
                Request.Builder url = new Request.Builder().url("http://" + h + ":8090/fs/put?path=" + Uri.encode($full) + "&mtime=0");
                RequestBody.Companion companion = RequestBody.Companion;
                MediaType mediaType = MediaType.Companion.get("application/octet-stream");
                byte[] bytes = $text.getBytes(Charsets.UTF_8);
                Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
                Response execute = okHttpClient.newCall(url.put(RequestBody.Companion.create(mediaType, bytes, 0, bytes.length)).build()).execute();
                try {
                    Response r = execute;
                    if (!r.isSuccessful()) {
                        ResponseBody body = r.body();
                        if (body != null && (string = body.string()) != null) {
                            String str2 = string;
                            if (str2.length() == 0) {
                                str2 = "HTTP " + r.code();
                            }
                            str = str2;
                        }
                        str = "HTTP " + r.code();
                        throw new RuntimeException(str);
                    }
                    Unit unit = Unit.INSTANCE;
                    CloseableKt.closeFinally(execute, null);
                } finally {
                }
            } else {
                FilesKt.writeText(new File($full), $text, kotlin.text.Charsets.UTF_8);
            }
            this$0.ui.post(new Runnable() { // from class: com.starpilot.starview.FilesActivity$$ExternalSyntheticLambda19
                @Override // java.lang.Runnable
                public final void run() {
                    FilesActivity.saveText$lambda$83$lambda$81(this$0, $done, $pane);
                }
            });
        } catch (Exception x) {
            this$0.ui.post(new Runnable() { // from class: com.starpilot.starview.FilesActivity$$ExternalSyntheticLambda20
                @Override // java.lang.Runnable
                public final void run() {
                    FilesActivity.saveText$lambda$83$lambda$82(this$0, x);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void saveText$lambda$83$lambda$81(FilesActivity this$0, Function0 $done, Pane $pane) {
        this$0.toast("Saved");
        $done.invoke();
        $pane.refresh();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void saveText$lambda$83$lambda$82(FilesActivity this$0, Exception $x) {
        this$0.toast("save: " + $x.getMessage());
    }

    /* compiled from: FilesActivity.kt */
    private enum QState {
        WAITING,
        COPYING,
        DONE,
        FAILED;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries($VALUES);

        public static EnumEntries<QState> getEntries() {
            return $ENTRIES;
        }
    }

    /* compiled from: FilesActivity.kt */
    private final class QRow {
        private final ProgressBar bar;
        private final Job job;
        private final TextView state;
        final /* synthetic */ FilesActivity this$0;
        private final LinearLayout view;

        public QRow(FilesActivity this$0, Job job, LinearLayout view, TextView state, ProgressBar bar) {
            Intrinsics.checkNotNullParameter(job, "job");
            Intrinsics.checkNotNullParameter(view, "view");
            Intrinsics.checkNotNullParameter(state, "state");
            Intrinsics.checkNotNullParameter(bar, "bar");
            this.this$0 = this$0;
            this.job = job;
            this.view = view;
            this.state = state;
            this.bar = bar;
        }

        public final ProgressBar getBar() {
            return this.bar;
        }

        public final Job getJob() {
            return this.job;
        }

        public final TextView getState() {
            return this.state;
        }

        public final LinearLayout getView() {
            return this.view;
        }
    }

    /* VF: from Vineflower (jadx: Type inference failed) */
    private final void buildQueue(List<FilesActivity.Job> var1) {
        LinearLayout var4 = this.queueList;
        LinearLayout var3 = var4;
        if (var4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("queueList");
            var3 = null;
        }

        var3.removeAllViews();
        this.queueRows.clear();
        Iterator var5 = var1.iterator();

        for (int var2 = 0; var5.hasNext(); var2++) {
            FilesActivity.Job var6 = (FilesActivity.Job)var5.next();
            if (var2 >= 300) {
                var3 = this.queueList;
                if (var3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("queueList");
                    var3 = null;
                }

                TextView var23 = new TextView((Context)this);
                var2 = var1.size();
                var23.setText("… and " + (var2 - 300) + " more files");
                var23.setTextColor(this.dim);
                var23.setTextSize(12.0F);
                var23.setPadding(this.dp(6), this.dp(4), 0, this.dp(4));
                var3.addView((View)var23);
                break;
            }

            FileKinds var18 = FileKinds.INSTANCE;
            String var11 = new File(var6.getSrcPath()).getName();
            Intrinsics.checkNotNullExpressionValue(var11, "getName(...)");
            FileKinds.Kind var19 = var18.of(var11, false);
            LinearLayout var7 = new LinearLayout((Context)this);
            var7.setOrientation(0);
            var7.setGravity(16);
            var7.setPadding(this.dp(6), this.dp(4), this.dp(6), this.dp(4));
            View var12 = new View((Context)this);
            var12.setBackground(SvIcon.drawable$default(SvIcon.INSTANCE, var19.getIcon(), var19.getColor(), 0, 4, null));
            android.widget.LinearLayout.LayoutParams var20 = new LayoutParams(this.dp(18), this.dp(18));
            var20.rightMargin = this.dp(8);
            Unit var8 = Unit.INSTANCE;
            var7.addView(var12, (android.view.ViewGroup.LayoutParams)var20);
            TextView var13 = new TextView((Context)this);
            var13.setText(new File(var6.getSrcPath()).getName());
            var13.setTextColor(-1);
            var13.setTextSize(13.0F);
            var13.setMaxLines(1);
            var13.setEllipsize(TruncateAt.MIDDLE);
            var7.addView((View)var13, (android.view.ViewGroup.LayoutParams)(new LayoutParams(0, -2, 1.0F)));
            ProgressBar var24 = new ProgressBar((Context)this, null, 16842872);
            var24.setMax(1000);
            var24.setProgress(0);
            View var21 = (View)var24;
            android.widget.LinearLayout.LayoutParams var14 = new LayoutParams(this.dp(140), -2);
            var14.leftMargin = this.dp(10);
            var14.rightMargin = this.dp(10);
            Unit var9 = Unit.INSTANCE;
            var7.addView(var21, (android.view.ViewGroup.LayoutParams)var14);
            TextView var25 = new TextView((Context)this);
            String var15 = this.fmtSize(var6.getSize());
            var25.setText("waiting · " + var15);
            var25.setTextColor(this.dim);
            var25.setTextSize(12.0F);
            var25.setMinWidth(this.dp(120));
            var7.addView((View)var25);
            var4 = this.queueList;
            var3 = var4;
            if (var4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("queueList");
                var3 = null;
            }

            var3.addView((View)var7);
            this.queueRows.add(new FilesActivity.QRow(this, var6, var7, var25, var24));
        }
    }

    static /* synthetic */ void markRow$default(FilesActivity filesActivity, int i, QState qState, long j, String str, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            j = 0;
        }
        long j2 = j;
        if ((i2 & 8) != 0) {
            str = null;
        }
        filesActivity.markRow(i, qState, j2, str);
    }

    private final void markRow(int i, QState st, long done, String err) {
        QRow r = (QRow) CollectionsKt.getOrNull(this.queueRows, i);
        if (r == null) {
            return;
        }
        switch (WhenMappings.$EnumSwitchMapping$1[st.ordinal()]) {
            case 1:
                r.getState().setText("waiting · " + fmtSize(r.getJob().getSize()));
                return;
            case 2:
                r.getBar().setProgress(r.getJob().getSize() > 0 ? (int) ((1000 * done) / r.getJob().getSize()) : 0);
                r.getState().setText(fmtSize(done) + " / " + fmtSize(r.getJob().getSize()));
                r.getState().setTextColor(-1);
                return;
            case 3:
                r.getBar().setProgress(1000);
                r.getState().setText("✓ done");
                r.getState().setTextColor(Color.rgb(111, 227, 161));
                return;
            case 4:
                r.getState().setText("✕ " + (err == null ? "failed" : err));
                r.getState().setTextColor(Color.rgb(255, 138, 128));
                return;
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void waitIfPaused() {
        while (this.paused.get() && !this.cancel.get()) {
            Thread.sleep(150L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String join(String a, String b) {
        return (StringsKt.endsWith(a, "/", false) ? new StringBuilder().append(a) : new StringBuilder().append(a).append("/")).append(b).toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String fmtSize(long b) {
        String format;
        if (b < 1024) {
            return b + " B";
        }
        if (b < 1048576) {
            format = String.format("%.1f KB", Arrays.copyOf(new Object[]{Double.valueOf(b / 1024.0d)}, 1));
        } else {
            double d = b;
            format = b < 1073741824 ? String.format("%.1f MB", Arrays.copyOf(new Object[]{Double.valueOf(d / 1048576.0d)}, 1)) : String.format("%.2f GB", Arrays.copyOf(new Object[]{Double.valueOf(d / 1.073741824E9d)}, 1));
        }
        Intrinsics.checkNotNullExpressionValue(format, "format(...)");
        return format;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String fmtDate(long mtime) {
        if (mtime <= 0) {
            return "";
        }
        String format = this.dateFmt.format(new Date(1000 * mtime));
        Intrinsics.checkNotNullExpressionValue(format, "format(...)");
        return format;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void toast(String s) {
        Toast.makeText(this, s, 1).show();
    }

    static /* synthetic */ void input$default(FilesActivity filesActivity, String str, String str2, String str3, Function1 function1, int i, Object obj) {
        if ((i & 4) != 0) {
            str3 = "";
        }
        filesActivity.input(str, str2, str3, function1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void input(String title, String hint, String initial, final Function1<? super String, Unit> onDone) {
        final EditText $this$input_u24lambda_u2492 = new EditText(this);
        $this$input_u24lambda_u2492.setText(initial);
        $this$input_u24lambda_u2492.setHint(hint);
        $this$input_u24lambda_u2492.setInputType(1);
        $this$input_u24lambda_u2492.setPadding(dp(20), dp(16), dp(20), dp(16));
        new AlertDialog.Builder(this).setTitle(title).setView($this$input_u24lambda_u2492).setPositiveButton("OK", new DialogInterface.OnClickListener() { // from class: com.starpilot.starview.FilesActivity$$ExternalSyntheticLambda8
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                FilesActivity.input$lambda$93($this$input_u24lambda_u2492, onDone, dialogInterface, i);
            }
        }).setNegativeButton("Cancel", (DialogInterface.OnClickListener) null).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void input$lambda$93(EditText $e, Function1 $onDone, DialogInterface dialogInterface, int i) {
        String s = StringsKt.trim((CharSequence) $e.getText().toString()).toString();
        if (s.length() > 0) {
            $onDone.invoke(s);
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        this.cancel.set(true);
        this.io.shutdownNow();
        super.onDestroy();
    }
}
