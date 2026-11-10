package com.starpilot.starview;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import com.starpilot.starview.LaneBrain;
import com.starpilot.starview.LaneFixDialog;
import com.starpilot.starview.LaneRamps;
import com.starpilot.starview.LaneShots;
import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.json.JSONObject;
import android.graphics.drawable.Drawable;
import android.widget.LinearLayout.LayoutParams;

/* compiled from: LanePicturesActivity.kt */
public final class LanePicturesActivity extends AppCompatActivity {
    private TextView caption;
    private LinearLayout drives;
    private boolean exporting;
    private int i;
    private ZoomImageView img;
    private TextView info;

    /* renamed from: lastPrefs$delegate, reason: from kotlin metadata */
    private final Lazy lastPrefs$delegate;
    private final ActivityResultLauncher<String[]> pickModel;
    private TextView score;
    private LaneShots shots;
    private List<LaneShots.Shot> all = CollectionsKt.emptyList();
    private final HashMap<String, Integer> marks = new HashMap<>();
    private final HashMap<String, String> fixed = new HashMap<>();
    private final HashMap<String, String> skipWhy = new HashMap<>();
    private String drive = "";
    private List<LaneShots.Shot> cur = CollectionsKt.emptyList();

    public LanePicturesActivity() {
        ActivityResultLauncher<String[]> registerForActivityResult = registerForActivityResult(new ActivityResultContracts.OpenDocument(), new ActivityResultCallback() { // from class: com.starpilot.starview.LanePicturesActivity$$ExternalSyntheticLambda3
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                LanePicturesActivity.pickModel$lambda$23(LanePicturesActivity.this, (Uri) obj);
            }
        });
        Intrinsics.checkNotNullExpressionValue(registerForActivityResult, "registerForActivityResult(...)");
        this.pickModel = registerForActivityResult;
        this.lastPrefs$delegate = LazyKt.lazy(new Function0() { // from class: com.starpilot.starview.LanePicturesActivity$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                SharedPreferences lastPrefs_delegate$lambda$52;
                lastPrefs_delegate$lambda$52 = LanePicturesActivity.lastPrefs_delegate$lambda$52(LanePicturesActivity.this);
                return lastPrefs_delegate$lambda$52;
            }
        });
    }

    private final float getD() {
        return getResources().getDisplayMetrics().density;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context applicationContext = getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
        this.shots = new LaneShots(applicationContext);
        LaneShots laneShots = this.shots;
        if (laneShots == null) {
            Intrinsics.throwUninitializedPropertyAccessException("shots");
            laneShots = null;
        }
        this.all = laneShots.list();
        HashMap<String, Integer> hashMap = this.marks;
        LaneShots laneShots2 = this.shots;
        if (laneShots2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("shots");
            laneShots2 = null;
        }
        hashMap.putAll(laneShots2.verdicts());
        HashMap<String, String> hashMap2 = this.fixed;
        LaneShots laneShots3 = this.shots;
        if (laneShots3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("shots");
            laneShots3 = null;
        }
        hashMap2.putAll(laneShots3.fixes());
        HashMap<String, String> hashMap3 = this.skipWhy;
        LaneShots laneShots4 = this.shots;
        if (laneShots4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("shots");
            laneShots4 = null;
        }
        hashMap3.putAll(laneShots4.skipReasons());
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(1);
        root.setBackgroundColor(Color.rgb(11, 13, 18));
        LinearLayout bar = new LinearLayout(this);
        bar.setGravity(16);
        bar.setPadding(dp(16), dp(10), dp(16), dp(6));
        TextView $this$onCreate_u24lambda_u242 = new TextView(this);
        $this$onCreate_u24lambda_u242.setText("Lane pictures");
        $this$onCreate_u24lambda_u242.setTextColor(-1);
        $this$onCreate_u24lambda_u242.setTextSize(20.0f);
        $this$onCreate_u24lambda_u242.setTypeface(null, 1);
        bar.addView($this$onCreate_u24lambda_u242, new LinearLayout.LayoutParams(0, -2, 1.0f));
        TextView $this$onCreate_u24lambda_u243 = new TextView(this);
        $this$onCreate_u24lambda_u243.setTextSize(15.0f);
        $this$onCreate_u24lambda_u243.setTextColor(Color.rgb(200, 208, 222));
        $this$onCreate_u24lambda_u243.setPadding(0, 0, dp(16), 0);
        this.score = $this$onCreate_u24lambda_u243;
        TextView textView = this.score;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("score");
            textView = null;
        }
        bar.addView(textView);
        TextView btn = btn("Lane model", Color.rgb(48, 54, 66), new Function0() { // from class: com.starpilot.starview.LanePicturesActivity$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit onCreate$lambda$4;
                onCreate$lambda$4 = LanePicturesActivity.onCreate$lambda$4(LanePicturesActivity.this);
                return onCreate$lambda$4;
            }
        });
        LinearLayout.LayoutParams $this$onCreate_u24lambda_u245 = new LinearLayout.LayoutParams(dp(150), dp(44));
        $this$onCreate_u24lambda_u245.setMarginEnd(dp(10));
        Unit unit = Unit.INSTANCE;
        bar.addView(btn, $this$onCreate_u24lambda_u245);
        TextView btn2 = btn("Export", Color.rgb(52, 92, 170), new Function0() { // from class: com.starpilot.starview.LanePicturesActivity$$ExternalSyntheticLambda11
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit onCreate$lambda$6;
                onCreate$lambda$6 = LanePicturesActivity.onCreate$lambda$6(LanePicturesActivity.this);
                return onCreate$lambda$6;
            }
        });
        LinearLayout.LayoutParams $this$onCreate_u24lambda_u247 = new LinearLayout.LayoutParams(dp(130), dp(44));
        $this$onCreate_u24lambda_u247.setMarginEnd(dp(10));
        Unit unit2 = Unit.INSTANCE;
        bar.addView(btn2, $this$onCreate_u24lambda_u247);
        bar.addView(btn("Close", Color.rgb(48, 54, 66), new Function0() { // from class: com.starpilot.starview.LanePicturesActivity$$ExternalSyntheticLambda20
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit onCreate$lambda$8;
                onCreate$lambda$8 = LanePicturesActivity.onCreate$lambda$8(LanePicturesActivity.this);
                return onCreate$lambda$8;
            }
        }), new LinearLayout.LayoutParams(dp(110), dp(44)));
        root.addView(bar);
        LinearLayout body = new LinearLayout(this);
        LinearLayout $this$onCreate_u24lambda_u249 = new LinearLayout(this);
        $this$onCreate_u24lambda_u249.setOrientation(1);
        $this$onCreate_u24lambda_u249.setPadding(dp(12), 0, dp(8), dp(12));
        this.drives = $this$onCreate_u24lambda_u249;
        ScrollView $this$onCreate_u24lambda_u2410 = new ScrollView(this);
        LinearLayout linearLayout = this.drives;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("drives");
            linearLayout = null;
        }
        $this$onCreate_u24lambda_u2410.addView(linearLayout);
        body.addView($this$onCreate_u24lambda_u2410, new LinearLayout.LayoutParams(dp(250), -1));
        LinearLayout right = new LinearLayout(this);
        right.setOrientation(1);
        right.setPadding(dp(8), 0, dp(16), dp(12));
        this.img = new ZoomImageView(this);
        ZoomImageView zoomImageView = this.img;
        if (zoomImageView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("img");
            zoomImageView = null;
        }
        right.addView(zoomImageView, new LinearLayout.LayoutParams(-1, 0, 1.0f));
        TextView $this$onCreate_u24lambda_u2412 = new TextView(this);
        $this$onCreate_u24lambda_u2412.setTextSize(16.0f);
        $this$onCreate_u24lambda_u2412.setTextColor(-1);
        $this$onCreate_u24lambda_u2412.setPadding(0, dp(6), 0, 0);
        this.caption = $this$onCreate_u24lambda_u2412;
        TextView $this$onCreate_u24lambda_u2413 = new TextView(this);
        $this$onCreate_u24lambda_u2413.setTextSize(14.0f);
        $this$onCreate_u24lambda_u2413.setTextColor(Color.rgb(160, 168, 184));
        $this$onCreate_u24lambda_u2413.setPadding(0, dp(2), 0, dp(8));
        this.info = $this$onCreate_u24lambda_u2413;
        TextView textView2 = this.caption;
        if (textView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("caption");
            textView2 = null;
        }
        right.addView(textView2);
        TextView textView3 = this.info;
        if (textView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("info");
            textView3 = null;
        }
        right.addView(textView3);
        LinearLayout row = new LinearLayout(this);
        row.addView(btn("◀", Color.rgb(48, 54, 66), new Function0() { // from class: com.starpilot.starview.LanePicturesActivity$$ExternalSyntheticLambda21
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit onCreate$lambda$15;
                onCreate$lambda$15 = LanePicturesActivity.onCreate$lambda$15(LanePicturesActivity.this);
                return onCreate$lambda$15;
            }
        }), onCreate$lp(this));
        row.addView(btn("✓  Right", Color.rgb(34, 150, 74), new Function0() { // from class: com.starpilot.starview.LanePicturesActivity$$ExternalSyntheticLambda22
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit onCreate$lambda$16;
                onCreate$lambda$16 = LanePicturesActivity.onCreate$lambda$16(LanePicturesActivity.this);
                return onCreate$lambda$16;
            }
        }), onCreate$lp(this));
        row.addView(btn("✗  Wrong", Color.rgb(200, 52, 52), new Function0() { // from class: com.starpilot.starview.LanePicturesActivity$$ExternalSyntheticLambda23
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit onCreate$lambda$17;
                onCreate$lambda$17 = LanePicturesActivity.onCreate$lambda$17(LanePicturesActivity.this);
                return onCreate$lambda$17;
            }
        }), onCreate$lp(this));
        row.addView(btn("Fix", Color.rgb(52, 92, 170), new Function0() { // from class: com.starpilot.starview.LanePicturesActivity$$ExternalSyntheticLambda24
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit onCreate$lambda$18;
                onCreate$lambda$18 = LanePicturesActivity.onCreate$lambda$18(LanePicturesActivity.this);
                return onCreate$lambda$18;
            }
        }), onCreate$lp(this));
        row.addView(btn("Skip", Color.rgb(90, 96, 110), new Function0() { // from class: com.starpilot.starview.LanePicturesActivity$$ExternalSyntheticLambda25
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit onCreate$lambda$19;
                onCreate$lambda$19 = LanePicturesActivity.onCreate$lambda$19(LanePicturesActivity.this);
                return onCreate$lambda$19;
            }
        }), onCreate$lp(this));
        row.addView(btn("▶", Color.rgb(48, 54, 66), new Function0() { // from class: com.starpilot.starview.LanePicturesActivity$$ExternalSyntheticLambda26
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit onCreate$lambda$20;
                onCreate$lambda$20 = LanePicturesActivity.onCreate$lambda$20(LanePicturesActivity.this);
                return onCreate$lambda$20;
            }
        }), onCreate$lp(this));
        right.addView(row);
        body.addView(right, new LinearLayout.LayoutParams(0, -1, 1.0f));
        root.addView(body, new LinearLayout.LayoutParams(-1, 0, 1.0f));
        setContentView(root);
        List names = driveNames();
        if (!names.isEmpty()) {
            pick((String) CollectionsKt.first(names));
            return;
        }
        TextView textView4 = this.caption;
        if (textView4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("caption");
            textView4 = null;
        }
        textView4.setText("No lane pictures yet. They're taken while driving with the lane card on.");
        listDrives();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onCreate$lambda$4(LanePicturesActivity this$0) {
        this$0.modelDialog();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onCreate$lambda$6(LanePicturesActivity this$0) {
        this$0.exportNow();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onCreate$lambda$8(LanePicturesActivity this$0) {
        this$0.finish();
        return Unit.INSTANCE;
    }

    private static final LinearLayout.LayoutParams onCreate$lp(LanePicturesActivity this$0) {
        LinearLayout.LayoutParams $this$onCreate_u24lp_u24lambda_u2414 = new LinearLayout.LayoutParams(0, this$0.dp(60), 1.0f);
        $this$onCreate_u24lp_u24lambda_u2414.setMarginEnd(this$0.dp(10));
        return $this$onCreate_u24lp_u24lambda_u2414;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onCreate$lambda$15(LanePicturesActivity this$0) {
        if (this$0.i > 0) {
            this$0.i--;
            this$0.show();
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onCreate$lambda$16(LanePicturesActivity this$0) {
        mark$default(this$0, 1, null, 2, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onCreate$lambda$17(LanePicturesActivity this$0) {
        mark$default(this$0, -1, null, 2, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onCreate$lambda$18(LanePicturesActivity this$0) {
        this$0.askFix();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onCreate$lambda$19(LanePicturesActivity this$0) {
        mark$default(this$0, 0, null, 2, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onCreate$lambda$20(LanePicturesActivity this$0) {
        if (this$0.i < this$0.cur.size() - 1) {
            this$0.i++;
            this$0.show();
        }
        return Unit.INSTANCE;
    }

    private final int dp(int v) {
        return (int) (v * getD());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void pickModel$lambda$23(final LanePicturesActivity this$0, final Uri uri) {
        if (uri != null) {
            new Thread(new Runnable() { // from class: com.starpilot.starview.LanePicturesActivity$$ExternalSyntheticLambda19
                @Override // java.lang.Runnable
                public final void run() {
                    LanePicturesActivity.pickModel$lambda$23$lambda$22(this$0, uri);
                }
            }).start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void pickModel$lambda$23$lambda$22(final LanePicturesActivity this$0, Uri $uri) {
        Context applicationContext = this$0.getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
        final String err = new LaneModel(applicationContext).importModel($uri);
        this$0.runOnUiThread(new Runnable() { // from class: com.starpilot.starview.LanePicturesActivity$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                LanePicturesActivity.pickModel$lambda$23$lambda$22$lambda$21(this$0, err);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void pickModel$lambda$23$lambda$22$lambda$21(LanePicturesActivity this$0, String $err) {
        new AlertDialog.Builder(this$0).setTitle("Lane model").setMessage($err == null ? "Installed. The lane card uses it on city streets from the next drive." : $err).setPositiveButton("OK", (DialogInterface.OnClickListener) null).show();
    }

    private final void modelDialog() {
        String now;
        Context applicationContext = getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
        LaneModel lm = new LaneModel(applicationContext);
        if (lm.getHasModel()) {
            String format = String.format("%.1f", Arrays.copyOf(new Object[]{Double.valueOf(lm.modelFile().length() / 1000000.0d)}, 1));
            Intrinsics.checkNotNullExpressionValue(format, "format(...)");
            now = "Installed: " + format + " MB, from " + new SimpleDateFormat("MMM d, HH:mm", Locale.US).format(new Date(lm.modelFile().lastModified())) + ".";
        } else {
            now = "No lane model installed yet.";
        }
        new AlertDialog.Builder(this).setTitle("Lane model").setMessage(now + "\n\nThe model trained on your lane pictures. It looks at the road camera every 2 s and tells the lane card how many lanes are left and right of you - on city streets only for now (on highways it needs pictures from more drives).\n\nA new model comes as a .lite file: download it on this tablet, then Import.").setPositiveButton("Import…", new DialogInterface.OnClickListener() { // from class: com.starpilot.starview.LanePicturesActivity$$ExternalSyntheticLambda8
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                LanePicturesActivity.modelDialog$lambda$24(LanePicturesActivity.this, dialogInterface, i);
            }
        }).setNegativeButton("Close", (DialogInterface.OnClickListener) null).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void modelDialog$lambda$24(LanePicturesActivity this$0, DialogInterface dialogInterface, int i) {
        this$0.pickModel.launch(new String[]{"*/*"});
    }

    private final TextView btn(String t, int bg, final Function0<Unit> click) {
        TextView $this$btn_u24lambda_u2427 = new TextView(this);
        $this$btn_u24lambda_u2427.setText(t);
        $this$btn_u24lambda_u2427.setTextSize(20.0f);
        $this$btn_u24lambda_u2427.setGravity(17);
        $this$btn_u24lambda_u2427.setTextColor(-1);
        $this$btn_u24lambda_u2427.setTypeface(null, 1);
        GradientDrawable $this$btn_u24lambda_u2427_u24lambda_u2425 = new GradientDrawable();
        $this$btn_u24lambda_u2427_u24lambda_u2425.setCornerRadius(12 * getD());
        $this$btn_u24lambda_u2427_u24lambda_u2425.setColor(bg);
        $this$btn_u24lambda_u2427.setBackground($this$btn_u24lambda_u2427_u24lambda_u2425);
        $this$btn_u24lambda_u2427.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.LanePicturesActivity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                click.invoke();
            }
        });
        return $this$btn_u24lambda_u2427;
    }

    private final List<String> driveNames() {
        Iterable $this$map$iv = this.all;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        for (Object item$iv$iv : $this$map$iv) {
            LaneShots.Shot it = (LaneShots.Shot) item$iv$iv;
            String drive = it.getDrive();
            if (drive.length() == 0) {
                drive = "earlier";
            }
            destination$iv$iv.add(drive);
        }
        return CollectionsKt.sortedDescending(CollectionsKt.distinct((List) destination$iv$iv));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void pick(String name) {
        this.drive = name;
        Iterable $this$filter$iv = this.all;
        Collection destination$iv$iv = new ArrayList();
        Iterator it = $this$filter$iv.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Object element$iv$iv = it.next();
            String drive = ((LaneShots.Shot) element$iv$iv).getDrive();
            if ((drive.length() == 0 ? 1 : 0) != 0) {
                drive = "earlier";
            }
            if (Intrinsics.areEqual(drive, name)) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        this.cur = (List) destination$iv$iv;
        List $this$indexOfFirst$iv = this.cur;
        int index$iv = 0;
        Iterator<LaneShots.Shot> it2 = $this$indexOfFirst$iv.iterator();
        while (true) {
            if (!it2.hasNext()) {
                index$iv = -1;
                break;
            }
            Object item$iv = it2.next();
            if (!this.marks.containsKey(((LaneShots.Shot) item$iv).getName())) {
                break;
            } else {
                index$iv++;
            }
        }
        int it3 = index$iv;
        this.i = it3 >= 0 ? it3 : 0;
        listDrives();
        show();
    }

    /* VF: from Vineflower (jadx: Removed duplicated region;incorrect) */
    private final void listDrives() {
        boolean var22 = false; // declarations Vineflower left out
        LinearLayout var7 = this.drives;
        String var8 = "drives";
        LinearLayout var6 = var7;
        if (var7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("drives");
            var6 = null;
        }

        var6.removeAllViews();
        Iterator var42 = this.driveNames().iterator();

        while (true) {
            boolean var5 = var42.hasNext();
            String var46 = "";
            if (!var5) {
                Iterable var55 = this.all;
                var var43 = new ArrayList();

                for (Object var71 : var55) {
                    LaneShots.Shot var84 = (LaneShots.Shot)var71;
                    Integer var85 = this.marks.get(var84.getName());
                    int var25;
                    if (var85 != null) {
                        var25 = var85;
                    } else {
                        var25 = 0;
                    }

                    boolean var26;
                    if (var25 != 0) {
                        var26 = true;
                    } else {
                        var26 = false;
                    }

                    if (var26) {
                        var43.add(var71);
                    }
                }

                List var86 = var43;
                Iterable var57 = var86;
                var var44 = new ArrayList();

                for (Object var72 : var57) {
                    if (Intrinsics.areEqual(((LaneShots.Shot)var72).getWhy(), "EVERY_30S")) {
                        var44.add(var72);
                    }
                }

                List var73 = var44;
                TextView var59 = this.score;
                TextView var45 = var59;
                if (var59 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("score");
                    var45 = null;
                }

                CharSequence var52;
                if (var86.isEmpty()) {
                    var52 = "nothing marked yet";
                } else {
                    var var60 = var86;
                    int var35;
                    if (var60 instanceof Collection && ((Collection)var60).isEmpty()) {
                        var35 = 0;
                    } else {
                        int var27 = 0;

                        for (LaneShots.Shot var88 : (Iterable<LaneShots.Shot>) var60) {
                            var88 = this.marks.get(var88.getName());
                            Intrinsics.checkNotNull(var88);
                            int var33;
                            if (((Number)(Object)var88).intValue() > 0) {
                                var33 = 1;
                            } else {
                                var33 = 0;
                            }

                            if ((var33 != 0)) {
                                var33 = var27 + 1;
                                var27 = var33;
                                if (var33 < 0) {
                                    CollectionsKt.throwCountOverflow();
                                    var27 = var33;
                                }
                            }
                        }

                        var35 = var27;
                    }

                    int var41 = var86.size();
                    if (!var73.isEmpty()) {
                        var var53 = var73;
                        int var28;
                        if (var53 instanceof Collection && ((Collection)var53).isEmpty()) {
                            var28 = 0;
                        } else {
                            var28 = 0;

                            for (LaneShots.Shot var62 : (Iterable<LaneShots.Shot>) var53) {
                                var8 = this.marks.get(var62.getName());
                                Intrinsics.checkNotNull(var8);
                                int var38;
                                if (((Number)(Object)var8).intValue() > 0) {
                                    var38 = 1;
                                } else {
                                    var38 = 0;
                                }

                                if ((var38 != 0)) {
                                    var38 = var28 + 1;
                                    var28 = var38;
                                    if (var38 < 0) {
                                        CollectionsKt.throwCountOverflow();
                                        var28 = var38;
                                    }
                                }
                            }
                        }

                        int var40 = 100 * var28 / var73.size();
                        var28 = var73.size();
                        var46 = "  ·  every-30-s: " + var40 + "% of " + var28;
                    }

                    var52 = "all drives: " + var35 + " of " + var41 + " right" + var46;
                }

                var45.setText(var52);
                return;
            }

            String var11 = (String)var42.next();
            Object var9 = this.all;
            var var10 = new ArrayList();

            for (Object var12 : (Iterable) var9) {
                var9 = ((LaneShots.Shot)var12).getDrive();
                boolean var1;
                if (((String) var9).length() == 0) {
                    var1 = true;
                } else {
                    var1 = false;
                }

                if (var1) {
                    var9 = "earlier";
                }

                if (Intrinsics.areEqual(var9, var11)) {
                    var10.add(var12);
                }
            }

            List var90 = var10;
            Iterable var74 = var90;
            var var65 = new ArrayList();

            for (Object var75 : var74) {
                LaneShots.Shot var14 = (LaneShots.Shot)var75;
                Integer var93 = this.marks.get(var14.getName());
                int var15;
                if (var93 != null) {
                    var15 = var93;
                } else {
                    var15 = 0;
                }

                boolean var16;
                if (var15 != 0) {
                    var16 = true;
                } else {
                    var16 = false;
                }

                if (var16) {
                    var65.add(var75);
                }
            }

            List var92 = var65;
            var9 = var90;
            int var31;
            if (var9 instanceof Collection && ((Collection)var9).isEmpty()) {
                var31 = 0;
            } else {
                int var17 = 0;

                for (LaneShots.Shot var76 : (Iterable<LaneShots.Shot>) var9) {
                    Integer var77 = this.marks.get(var76.getName());
                    int var2;
                    if (var77 != null && var77 == 0) {
                        var2 = 1;
                    } else {
                        var2 = 0;
                    }

                    if ((var2 != 0)) {
                        var2 = var17 + 1;
                        var17 = var2;
                        if (var2 < 0) {
                            CollectionsKt.throwCountOverflow();
                            var17 = var2;
                        }
                    }
                }

                var31 = var17;
            }

            var9 = var92;
            int var18;
            if (var9 instanceof Collection && ((Collection)var9).isEmpty()) {
                var18 = 0;
            } else {
                var18 = 0;

                for (LaneShots.Shot var78 : (Iterable<LaneShots.Shot>) var9) {
                    Object var79 = this.marks.get(var78.getName());
                    Intrinsics.checkNotNull(var79);
                    boolean var3;
                    if (((Number)var79).intValue() > 0) {
                        var3 = true;
                    } else {
                        var3 = false;
                    }

                    if (var3) {
                        if (++var18 < 0) {
                            CollectionsKt.throwCountOverflow();
                        }
                    }
                }
            }

            LinearLayout var80 = this.drives;
            LinearLayout var70 = var80;
            if (var80 == null) {
                Intrinsics.throwUninitializedPropertyAccessException(var8);
                var70 = null;
            }

            TextView var94 = new TextView((Context)this);
            int var36 = var90.size();
            int var4 = var92.size();
            String var81;
            if (var31 > 0) {
                var81 = " · " + var31 + " skipped";
            } else {
                var81 = "";
            }

            if (!var92.isEmpty()) {
                var18 = var18 * 100 / var92.size();
                var46 = " · " + var18 + "% right";
            }

            var94.setText(var11 + "\n" + var36 + " pictures · " + var4 + " marked" + var81 + var46);
            var94.setTextSize(15.0F);
            if (Intrinsics.areEqual(var11, this.drive)) {
                var18 = -16777216;
            } else {
                var18 = -1;
            }

            label272: {
                var94.setTextColor(var18);
                var94.setPadding(this.dp(12), this.dp(10), this.dp(12), this.dp(10));
                if (!var90.isEmpty()) {
                    var var47 = var90;
                    boolean var21;
                    if (var47 instanceof Collection && ((Collection)var47).isEmpty()) {
                        var21 = true;
                    } else {
                        Iterator var82 = var47.iterator();

                        while (true) {
                            if (!var82.hasNext()) {
                                var21 = true;
                                break;
                            }

                            LaneShots.Shot var48 = (LaneShots.Shot)var82.next();
                            if (!this.marks.containsKey(var48.getName())) {
                                var21 = false;
                                break;
                            }
                        }
                    }

                    if (var21) {
                        var22 = true;
                        break label272;
                    }
                }

                var22 = false;
            }

            if (var22) {
                CharSequence var49 = var94.getText();
                var94.setText("✓ " + var49);
            }

            GradientDrawable var50 = new GradientDrawable();
            var50.setCornerRadius(10 * this.getD());
            if (Intrinsics.areEqual(var11, this.drive)) {
                var18 = Color.rgb(111, 227, 161);
            } else {
                byte var24;
                byte var32;
                byte var37;
                if (var22) {
                    var32 = 64;
                    var37 = 42;
                    var24 = 22;
                } else {
                    var32 = 34;
                    var37 = 44;
                    var24 = 30;
                }

                var18 = Color.rgb(var24, var32, var37);
            }

            var50.setColor(var18);
            var94.setBackground((Drawable)var50);
            var94.setOnClickListener(new LanePicturesActivity$$ExternalSyntheticLambda9(this, var11));
            var94.setOnLongClickListener(new LanePicturesActivity$$ExternalSyntheticLambda10(this, var11, var90));
            View var87 = (View)var94;
            android.widget.LinearLayout.LayoutParams var83 = new LayoutParams(-1, -2);
            var83.bottomMargin = this.dp(8);
            Unit var51 = Unit.INSTANCE;
            var70.addView(var87, (android.view.ViewGroup.LayoutParams)var83);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean listDrives$lambda$43$lambda$42(LanePicturesActivity this$0, String $n, List $list, View it) {
        this$0.askDelete($n, $list);
        return true;
    }

    /* VF: from Vineflower (jadx: ) */
    private final void show() {
        String var37 = null; String var47 = null; // declarations Vineflower left out
        if (!this.cur.isEmpty()) {
            LaneShots.Shot var9 = this.cur.get(this.i);

            Bitmap var5;
            label257: {
                InputStream var4;
                try {
                    var4 = this.getContentResolver().openInputStream(Uri.parse(var9.getUri()));
                } catch (Exception var29) {
                    var5 = null;
                    break label257;
                }

                label248:
                if (var4 != null) {
                    try {
                        var4 = var4;
                    } catch (Exception var28) {
                        var5 = null;
                        break label248;
                    }

                    try {
                        var5 = BitmapFactory.decodeStream(var4);
                    } catch (Throwable var27) {
                        Throwable var39 = var27;

                        try {
                            throw var39;
                        } finally {
                            try {
                                CloseableKt.closeFinally(var4, var39);
                            } catch (Exception var25) {
                                var5 = null;
                                break label248;
                            }
                        }
                    }

                    try {
                        CloseableKt.closeFinally(var4, null);
                    } catch (Exception var24) {
                        var5 = null;
                    }
                } else {
                    var5 = null;
                }
            }

            ZoomImageView var31 = this.img;
            if (var5 != null) {
                ZoomImageView var6 = var31;
                if (var31 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("img");
                    var6 = null;
                }

                var6.setImageBitmap(var5);
            } else {
                ZoomImageView var41 = var31;
                if (var31 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("img");
                    var41 = null;
                }

                var41.setImageDrawable(null);
            }

            TextView var42 = this.caption;
            TextView var32 = var42;
            if (var42 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("caption");
                var32 = null;
            }

            var32.setText(var9.getCaption());
            Integer var8 = this.marks.get(var9.getName());
            TextView var33 = this.info;
            TextView var7 = var33;
            if (var33 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("info");
                var7 = null;
            }

            int var2 = this.i;
            boolean var1 = true;
            int var3 = this.cur.size();
            String var34 = var9.getWhy();
            String var43;
            if (Intrinsics.areEqual(var34, "USER")) {
                var43 = "you tapped";
            } else if (Intrinsics.areEqual(var34, "EVERY_30S")) {
                var43 = "every 30 s";
            } else {
                var43 = "lane event";
            }

            String var10 = "";
            String var35;
            if (var8 == null) {
                var35 = "  ·  not marked";
            } else if (var8 > 0) {
                var35 = "  ·  marked RIGHT";
            } else if (var8 == 0) {
                label229: {
                    String var36 = this.skipWhy.get(var9.getName());
                    if (var36 != null) {
                        switch (var36.hashCode()) {
                            case 169749129:
                                if (var36.equals("intersection")) {
                                    var37 = " (intersection)";
                                    break label229;
                                }
                                break;
                            case 1641332067:
                                if (var36.equals("lane_change")) {
                                    var37 = " (changing lanes)";
                                    break label229;
                                }
                        }
                    }

                    var37 = "";
                }

                var35 = "  ·  skipped" + var37;
            } else {
                var35 = "  ·  marked WRONG";
            }

            if (var9.getRamps().length() <= 0) {
                var1 = false;
            }

            String var45;
            if (var1) {
                String var44 = CollectionsKt.joinToString(LaneRamps.INSTANCE.parse(var9.getRamps()), ", ", "", "", -1, "...", new LanePicturesActivity$$ExternalSyntheticLambda18());
                var45 = "  ·  map: " + var44;
            } else {
                var45 = "";
            }

            label218: {
                String var46 = this.fixed.get(var9.getName());
                if (var46 != null) {
                    String var11 = "  ·  you fixed it: " + var46;
                    var47 = var11;
                    if (var11 != null) {
                        break label218;
                    }
                }

                var47 = "";
            }

            if (var5 == null) {
                var10 = "  ·  picture deleted";
            }

            var7.setText(var2 + 1 + " of " + var3 + "  ·  " + var43 + var35 + var45 + var47 + var10);
            TextView var40 = this.info;
            TextView var38 = var40;
            if (var40 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("info");
                var38 = null;
            }

            var38.setVisibility(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CharSequence show$lambda$50(LaneRamps.Ramp it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return it.text();
    }

    private final SharedPreferences getLastPrefs() {
        return (SharedPreferences) this.lastPrefs$delegate.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final SharedPreferences lastPrefs_delegate$lambda$52(LanePicturesActivity this$0) {
        return this$0.getSharedPreferences("lane_pictures", 0);
    }

    private final LaneFixDialog.Result lastFix() {
        String str;
        try {
            String string = getLastPrefs().getString("last_fix", null);
            if (string == null) {
                return null;
            }
            JSONObject j = new JSONObject(string);
            int i = j.getInt("through");
            int optInt = j.optInt("extraL");
            int optInt2 = j.optInt("extraR");
            String optString = j.optString("kind");
            Intrinsics.checkNotNullExpressionValue(optString, "optString(...)");
            int optInt3 = j.optInt("lane");
            boolean optBoolean = j.optBoolean("remember");
            String optString2 = j.optString("way");
            if (optString2.length() == 0) {
                if (j.has("twoWay")) {
                    str = j.getBoolean("twoWay") ? "two" : "one";
                } else {
                    str = "";
                }
                optString2 = str;
            }
            Intrinsics.checkNotNullExpressionValue(optString2, "ifEmpty(...)");
            int optInt4 = j.optInt("otherWay");
            String optString3 = j.optString("kindR");
            Intrinsics.checkNotNullExpressionValue(optString3, "optString(...)");
            return new LaneFixDialog.Result(i, optInt, optInt2, optString, optInt3, optBoolean, optString2, optInt4, optString3);
        } catch (Exception e) {
            return null;
        }
    }

    private final void keepLast(LaneFixDialog.Result r) {
        JSONObject j = new JSONObject().put("through", r.getThrough()).put("extraL", r.getExtraL()).put("extraR", r.getExtraR()).put("kind", r.getKind()).put("lane", r.getLane()).put("remember", r.getRemember()).put("otherWay", r.getOtherWay()).put("way", r.getWay()).put("kindR", r.getKindR());
        getLastPrefs().edit().putString("last_fix", j.toString()).apply();
    }

    private final void askFix() {
        int saved;
        Bitmap bitmap;
        Bitmap bitmap2;
        Bitmap pic;
        String str;
        if (this.cur.isEmpty()) {
            return;
        }
        final LaneShots.Shot s = this.cur.get(this.i);
        if (s.getWayId() != 0) {
            LaneBrain.Live live = LaneBrain.Live.INSTANCE;
            Context applicationContext = getApplicationContext();
            Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
            saved = live.savedCount(applicationContext, s.getWayId());
        } else {
            saved = 0;
        }
        try {
            LaneShots laneShots = this.shots;
            if (laneShots == null) {
                Intrinsics.throwUninitializedPropertyAccessException("shots");
                laneShots = null;
            }
            File it = new File(laneShots.getCleanDir(), s.getRoadFile());
            if ((((s.getRoadFile().length() > 0) && it.exists()) ? 1 : null) == null) {
                it = null;
            }
            bitmap = it != null ? BitmapFactory.decodeFile(it.getPath()) : null;
        } catch (Exception e) {
            bitmap = null;
        }
        if (bitmap == null) {
            try {
                InputStream openInputStream = getContentResolver().openInputStream(Uri.parse(s.getUri()));
                if (openInputStream != null) {
                    InputStream it2 = openInputStream;
                    try {
                        bitmap2 = BitmapFactory.decodeStream(it2);
                        CloseableKt.closeFinally(it2, null);
                    } finally {
                    }
                } else {
                    bitmap2 = null;
                }
            } catch (Exception e2) {
                bitmap2 = null;
            }
            pic = bitmap2;
        } else {
            pic = bitmap;
        }
        LaneFixDialog laneFixDialog = LaneFixDialog.INSTANCE;
        LanePicturesActivity lanePicturesActivity = this;
        if (s.getWayId() != 0) {
            String street = s.getStreet();
            if (street.length() == 0) {
                street = "this street";
            }
            str = street;
        } else {
            str = null;
        }
        Function0 function0 = new Function0() { // from class: com.starpilot.starview.LanePicturesActivity$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit askFix$lambda$58;
                askFix$lambda$58 = LanePicturesActivity.askFix$lambda$58(LanePicturesActivity.this, s);
                return askFix$lambda$58;
            }
        };
        LaneFixDialog.Result lastFix = lastFix();
        String street2 = s.getStreet();
        if (street2.length() == 0) {
            street2 = "no street";
        }
        laneFixDialog.show(lanePicturesActivity, "Fix lanes", 0, str, 0, false, (r34 & 64) != 0 ? 0 : saved, (r34 & 128) != 0 ? null : function0, (r34 & 256) != 0 ? null : lastFix, (r34 & 512) != 0 ? null : pic, (r34 & 1024) != 0 ? "" : ((Object) street2) + "  ·  " + StringsKt.take(StringsKt.substringBefore(s.getCaption(), "\n", s.getCaption()), 9) + "  ·  pinch to zoom  ·  picture " + (this.i + 1) + " of " + this.cur.size(), (r34 & 2048) != 0 ? CollectionsKt.emptyList() : LaneRamps.INSTANCE.parse(s.getRamps()), (r34 & 4096) != 0 ? null : new Function1() { // from class: com.starpilot.starview.LanePicturesActivity$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit askFix$lambda$60;
                askFix$lambda$60 = LanePicturesActivity.askFix$lambda$60(LanePicturesActivity.this, (String) obj);
                return askFix$lambda$60;
            }
        }, (r34 & 8192) != 0 ? null : null, new Function1() { // from class: com.starpilot.starview.LanePicturesActivity$$ExternalSyntheticLambda7
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit askFix$lambda$61;
                askFix$lambda$61 = LanePicturesActivity.askFix$lambda$61(LanePicturesActivity.this, s, (LaneFixDialog.Result) obj);
                return askFix$lambda$61;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit askFix$lambda$58(LanePicturesActivity this$0, LaneShots.Shot $s) {
        LaneBrain.Live live = LaneBrain.Live.INSTANCE;
        Context applicationContext = this$0.getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
        live.forgetCount(applicationContext, $s.getWayId());
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit askFix$lambda$60(LanePicturesActivity this$0, String why) {
        Intrinsics.checkNotNullParameter(why, "why");
        this$0.mark(0, why);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit askFix$lambda$61(LanePicturesActivity this$0, LaneShots.Shot $s, LaneFixDialog.Result r) {
        Intrinsics.checkNotNullParameter(r, "r");
        this$0.keepLast(r);
        boolean wasDone = this$0.driveDone();
        LaneShots laneShots = this$0.shots;
        if (laneShots == null) {
            Intrinsics.throwUninitializedPropertyAccessException("shots");
            laneShots = null;
        }
        laneShots.markFix($s.getName(), r);
        this$0.marks.put($s.getName(), -1);
        this$0.fixed.put($s.getName(), r.text());
        if (r.getRemember() && $s.getWayId() != 0) {
            LaneBrain.Live live = LaneBrain.Live.INSTANCE;
            Context applicationContext = this$0.getApplicationContext();
            Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
            live.rememberCount(applicationContext, $s.getWayId(), r.getThrough());
        }
        if (this$0.i < this$0.cur.size() - 1) {
            this$0.i++;
        }
        this$0.afterMark(wasDone);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void exportNow() {
        if (this.exporting) {
            return;
        }
        this.exporting = true;
        final AlertDialog dlg = new AlertDialog.Builder(this).setTitle("Export for training").setMessage("Packing…").setCancelable(false).show();
        new Thread(new Runnable() { // from class: com.starpilot.starview.LanePicturesActivity$$ExternalSyntheticLambda16
            @Override // java.lang.Runnable
            public final void run() {
                LanePicturesActivity.exportNow$lambda$65(LanePicturesActivity.this, dlg);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void exportNow$lambda$65(final LanePicturesActivity this$0, final AlertDialog $dlg) {
        final String msg;
        try {
            LaneShots laneShots = this$0.shots;
            if (laneShots == null) {
                Intrinsics.throwUninitializedPropertyAccessException("shots");
                laneShots = null;
            }
            msg = laneShots.export(new Function1() { // from class: com.starpilot.starview.LanePicturesActivity$$ExternalSyntheticLambda14
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    Unit exportNow$lambda$65$lambda$63;
                    exportNow$lambda$65$lambda$63 = LanePicturesActivity.exportNow$lambda$65$lambda$63(this$0, $dlg, (String) obj);
                    return exportNow$lambda$65$lambda$63;
                }
            });
        } catch (Exception e) {
            msg = "Export failed: " + e.getMessage();
        }
        this$0.runOnUiThread(new Runnable() { // from class: com.starpilot.starview.LanePicturesActivity$$ExternalSyntheticLambda15
            @Override // java.lang.Runnable
            public final void run() {
                LanePicturesActivity.exportNow$lambda$65$lambda$64($dlg, this$0, msg);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit exportNow$lambda$65$lambda$63(LanePicturesActivity this$0, final AlertDialog $dlg, final String p) {
        Intrinsics.checkNotNullParameter(p, "p");
        this$0.runOnUiThread(new Runnable() { // from class: com.starpilot.starview.LanePicturesActivity$$ExternalSyntheticLambda27
            @Override // java.lang.Runnable
            public final void run() {
                LanePicturesActivity.exportNow$lambda$65$lambda$63$lambda$62($dlg, p);
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void exportNow$lambda$65$lambda$63$lambda$62(AlertDialog $dlg, String $p) {
        $dlg.setMessage($p);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void exportNow$lambda$65$lambda$64(AlertDialog $dlg, LanePicturesActivity this$0, String $msg) {
        $dlg.dismiss();
        this$0.exporting = false;
        new AlertDialog.Builder(this$0).setTitle("Export for training").setMessage($msg + "\n\nAttach those zip files in the chat (each is under 25 MB), or put them in your StarView Transfer Drive folder to keep. Next time, Export only adds the frames that are new.").setPositiveButton("OK", (DialogInterface.OnClickListener) null).show();
    }

    static /* synthetic */ void mark$default(LanePicturesActivity lanePicturesActivity, int i, String str, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            str = "";
        }
        lanePicturesActivity.mark(i, str);
    }

    private final void mark(int v, String why) {
        if (this.cur.isEmpty()) {
            return;
        }
        boolean wasDone = driveDone();
        LaneShots.Shot s = this.cur.get(this.i);
        LaneShots laneShots = this.shots;
        if (laneShots == null) {
            Intrinsics.throwUninitializedPropertyAccessException("shots");
            laneShots = null;
        }
        laneShots.mark(s.getName(), v, why);
        this.marks.put(s.getName(), Integer.valueOf(v));
        this.fixed.remove(s.getName());
        if (v == 0) {
            HashMap<String, String> hashMap = this.skipWhy;
            String name = s.getName();
            String str = why;
            if (str.length() == 0) {
                str = "skip";
            }
            hashMap.put(name, str);
        }
        if (this.i < this.cur.size() - 1) {
            this.i++;
        }
        afterMark(wasDone);
    }

    private final boolean driveDone() {
        boolean $this$all$ivB = false;
        Iterable $this$all$iv;
        if (this.cur.isEmpty()) {
            return false;
        }
        Iterable $this$all$iv2 = this.cur;
        if (!($this$all$iv2 instanceof Collection) || !((Collection) $this$all$iv2).isEmpty()) {
            Iterator it = $this$all$iv2.iterator();
            while (true) {
                if (it.hasNext()) {
                    Object element$iv = it.next();
                    LaneShots.Shot it2 = (LaneShots.Shot) element$iv;
                    if (!this.marks.containsKey(it2.getName())) {
                        $this$all$ivB = false;
                        break;
                    }
                } else {
                    $this$all$ivB = true;
                    break;
                }
            }
        } else {
            $this$all$ivB = true;
        }
        return $this$all$ivB;
    }

    private final void afterMark(boolean wasDone) {
        int count$iv;
        int count$iv2;
        int count$iv3;
        Object element$iv;
        boolean z;
        getLastPrefs().edit().putLong("marked_at", System.currentTimeMillis()).apply();
        listDrives();
        show();
        if (wasDone || !driveDone()) {
            return;
        }
        Iterable $this$count$iv = this.cur;
        if (($this$count$iv instanceof Collection) && ((Collection) $this$count$iv).isEmpty()) {
            count$iv = 0;
        } else {
            count$iv = 0;
            for (Object element$iv2 : $this$count$iv) {
                LaneShots.Shot it = (LaneShots.Shot) element$iv2;
                Integer num = this.marks.get(it.getName());
                if (((num != null ? num.intValue() : 0) > 0) && (count$iv = count$iv + 1) < 0) {
                    CollectionsKt.throwCountOverflow();
                }
            }
        }
        int right = count$iv;
        Iterable $this$count$iv2 = this.cur;
        if (($this$count$iv2 instanceof Collection) && ((Collection) $this$count$iv2).isEmpty()) {
            count$iv2 = 0;
        } else {
            count$iv2 = 0;
            for (Object element$iv3 : $this$count$iv2) {
                LaneShots.Shot it2 = (LaneShots.Shot) element$iv3;
                Integer num2 = this.marks.get(it2.getName());
                if (((num2 != null ? num2.intValue() : 0) < 0) && (count$iv2 = count$iv2 + 1) < 0) {
                    CollectionsKt.throwCountOverflow();
                }
            }
        }
        int wrong = count$iv2;
        Iterable $this$count$iv3 = this.cur;
        if (($this$count$iv3 instanceof Collection) && ((Collection) $this$count$iv3).isEmpty()) {
            count$iv3 = 0;
        } else {
            count$iv3 = 0;
            for (Object element$iv4 : $this$count$iv3) {
                LaneShots.Shot it3 = (LaneShots.Shot) element$iv4;
                Integer num3 = this.marks.get(it3.getName());
                if ((num3 != null && num3.intValue() == 0) && (count$iv3 = count$iv3 + 1) < 0) {
                    CollectionsKt.throwCountOverflow();
                }
            }
        }
        int skip = count$iv3;
        Iterable $this$firstOrNull$iv = driveNames();
        Iterator it4 = $this$firstOrNull$iv.iterator();
        while (true) {
            if (!it4.hasNext()) {
                element$iv = null;
                break;
            }
            element$iv = it4.next();
            String n = (String) element$iv;
            Iterable $this$any$iv = this.all;
            if (!($this$any$iv instanceof Collection) || !((Collection) $this$any$iv).isEmpty()) {
                Iterator it5 = $this$any$iv.iterator();
                while (true) {
                    if (!it5.hasNext()) {
                        z = false;
                        break;
                    }
                    Object element$iv5 = it5.next();
                    LaneShots.Shot it6 = (LaneShots.Shot) element$iv5;
                    String drive = it6.getDrive();
                    if (drive.length() == 0) {
                        drive = "earlier";
                    }
                    if (Intrinsics.areEqual(drive, n) && !this.marks.containsKey(it6.getName())) {
                        z = true;
                        break;
                    }
                }
            } else {
                z = false;
            }
            if (z) {
                break;
            }
        }
        final String next = (String) element$iv;
        AlertDialog.Builder b = new AlertDialog.Builder(this).setTitle("Done ✓").setMessage("All " + this.cur.size() + " pictures of " + this.drive + " are done: " + right + " right, " + wrong + " wrong, " + skip + " skipped.").setPositiveButton("Export", new DialogInterface.OnClickListener() { // from class: com.starpilot.starview.LanePicturesActivity$$ExternalSyntheticLambda12
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                LanePicturesActivity.this.exportNow();
            }
        }).setNegativeButton("OK", (DialogInterface.OnClickListener) null);
        if (next != null) {
            b.setNeutralButton("Next drive", new DialogInterface.OnClickListener() { // from class: com.starpilot.starview.LanePicturesActivity$$ExternalSyntheticLambda13
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    LanePicturesActivity.this.pick(next);
                }
            });
        }
        b.show();
    }

    private final void askDelete(final String n, List<LaneShots.Shot> list) {
        boolean $this$any$ivB = false;
        Iterable $this$any$iv;
        long exportedAt = getLastPrefs().getLong("exported_at", 0L);
        boolean z = true;
        if (exportedAt != 0) {
            List<LaneShots.Shot> $this$any$iv2 = list;
            if (!($this$any$iv2 instanceof Collection) || !$this$any$iv2.isEmpty()) {
                Iterator it = $this$any$iv2.iterator();
                while (true) {
                    if (it.hasNext()) {
                        Object element$iv = it.next();
                        LaneShots.Shot it2 = (LaneShots.Shot) element$iv;
                        boolean it3 = it2.getAtMs() > exportedAt;
                        if (it3) {
                            $this$any$ivB = true;
                            break;
                        }
                    } else {
                        $this$any$ivB = false;
                        break;
                    }
                }
            } else {
                $this$any$ivB = false;
            }
            if (!$this$any$ivB && getLastPrefs().getLong("marked_at", 0L) <= exportedAt) {
                z = false;
            }
        }
        boolean notExported = z;
        new AlertDialog.Builder(this).setTitle("Delete " + n + "?").setMessage(list.size() + " pictures, their training frames and your marks for them." + (notExported ? "\n\n⚠ Not everything is exported yet - Export first if you want to keep it for training." : "\n\nEverything was exported.")).setPositiveButton("Delete", new DialogInterface.OnClickListener() { // from class: com.starpilot.starview.LanePicturesActivity$$ExternalSyntheticLambda17
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                LanePicturesActivity.askDelete$lambda$78(LanePicturesActivity.this, n, dialogInterface, i);
            }
        }).setNegativeButton("Cancel", (DialogInterface.OnClickListener) null).show();
    }

    /* VF: from Vineflower (jadx: Code restructure failed;incorrect) */
    public static final void askDelete$lambda$78(LanePicturesActivity var0, String var1, DialogInterface var2, int var3) {
        Object var19 = null; // declarations Vineflower left out
        LaneShots var6 = var0.shots;
        Object var5 = null;
        Object var4 = null;
        LaneShots var12 = var6;
        if (var6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("shots");
            var12 = null;
        }

        var3 = var12.deleteDrive(var1);
        var6 = var0.shots;
        LaneShots var13 = var6;
        if (var6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("shots");
            var13 = null;
        }

        var0.all = var13.list();
        var0.marks.clear();
        HashMap var7 = var0.marks;
        var6 = var0.shots;
        LaneShots var14 = var6;
        if (var6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("shots");
            var14 = null;
        }

        var7.putAll(var14.verdicts());
        var0.fixed.clear();
        var7 = var0.fixed;
        var6 = var0.shots;
        LaneShots var15 = var6;
        if (var6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("shots");
            var15 = null;
        }

        var7.putAll(var15.fixes());
        Toast.makeText((Context)var0, "Deleted " + var3 + " pictures", 0).show();
        List var24 = var0.driveNames();
        if (var24.isEmpty()) {
            var0.drive = "";
            var0.cur = CollectionsKt.emptyList();
            ZoomImageView var16 = var0.img;
            ZoomImageView var8 = var16;
            if (var16 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("img");
                var8 = null;
            }

            var8.setImageDrawable(null);
            TextView var17 = var0.caption;
            TextView var9 = var17;
            if (var17 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("caption");
                var9 = null;
            }

            var9.setText("No lane pictures.");
            TextView var10 = var0.info;
            if (var10 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("info");
                var10 = (TextView)var4;
            }

            var10.setText("");
            var0.listDrives();
        } else {
            label41: {
                if (!Intrinsics.areEqual(var1, var0.drive)) {
                    String var18 = var0.drive;
                    var1 = (String)var5;
                    if (var24.contains(var18)) {
                        var1 = var18;
                    }

                    var19 = var1;
                    if (var1 != null) {
                        break label41;
                    }
                }

                var19 = CollectionsKt.first(var24);
            }

            var0.pick(((String) var19));
        }
    }
}
