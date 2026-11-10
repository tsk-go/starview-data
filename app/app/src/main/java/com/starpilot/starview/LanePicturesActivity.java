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

/* compiled from: LanePicturesActivity.kt */
public final class LanePicturesActivity extends AppCompatActivity {
    private TextView caption;
    private LinearLayout drives;
    private boolean exporting;
    private int i;
    private ZoomImageView img;
    private TextView info;

    /* renamed from: lastPrefs$delegate, reason: from kotlin metadata */
    private final Lazy lastPrefs;
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
        this.lastPrefs = LazyKt.lazy(new Function0() { // from class: com.starpilot.starview.LanePicturesActivity$$ExternalSyntheticLambda4
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
                    LanePicturesActivity.pickModel$lambda$23$lambda$22(LanePicturesActivity.this, uri);
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
                LanePicturesActivity.pickModel$lambda$23$lambda$22$lambda$21(LanePicturesActivity.this, err);
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
                Function0.this.invoke();
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

    /* JADX WARN: Removed duplicated region for block: B:74:0x0261  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0297  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x02a4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final void listDrives() {
        int count$iv;
        String str;
        String str2;
        int i;
        int count$iv2;
        int count$iv3;
        Iterator<String> it;
        String str3;
        String str4;
        boolean z;
        boolean done;
        int i2;
        int i3;
        int i4;
        int rgb;
        boolean z2;
        LinearLayout linearLayout = this.drives;
        String str5 = "drives";
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("drives");
            linearLayout = null;
        }
        linearLayout.removeAllViews();
        Iterator<String> it2 = driveNames().iterator();
        while (true) {
            if (!it2.hasNext()) {
                break;
            }
            final String n = it2.next();
            Iterable $this$filter$iv = this.all;
            Collection destination$iv$iv = new ArrayList();
            for (Object element$iv$iv : $this$filter$iv) {
                LaneShots.Shot it3 = (LaneShots.Shot) element$iv$iv;
                String drive = it3.getDrive();
                if (drive.length() == 0) {
                    drive = "earlier";
                }
                if (Intrinsics.areEqual(drive, n)) {
                    destination$iv$iv.add(element$iv$iv);
                }
            }
            final List list = (List) destination$iv$iv;
            List $this$filter$iv2 = list;
            Collection destination$iv$iv2 = new ArrayList();
            for (Object element$iv$iv2 : $this$filter$iv2) {
                LaneShots.Shot it4 = (LaneShots.Shot) element$iv$iv2;
                Integer num = this.marks.get(it4.getName());
                if ((num != null ? num.intValue() : 0) != 0) {
                    destination$iv$iv2.add(element$iv$iv2);
                }
            }
            List m = (List) destination$iv$iv2;
            List $this$count$iv = list;
            if (($this$count$iv instanceof Collection) && $this$count$iv.isEmpty()) {
                count$iv2 = 0;
            } else {
                count$iv2 = 0;
                for (Object element$iv : $this$count$iv) {
                    LaneShots.Shot it5 = (LaneShots.Shot) element$iv;
                    Integer num2 = this.marks.get(it5.getName());
                    if ((num2 != null && num2.intValue() == 0) && (count$iv2 = count$iv2 + 1) < 0) {
                        CollectionsKt.throwCountOverflow();
                    }
                }
            }
            int skipped = count$iv2;
            List $this$count$iv2 = m;
            if (($this$count$iv2 instanceof Collection) && $this$count$iv2.isEmpty()) {
                it = it2;
                count$iv3 = 0;
            } else {
                count$iv3 = 0;
                for (Object element$iv2 : $this$count$iv2) {
                    LaneShots.Shot it6 = (LaneShots.Shot) element$iv2;
                    Iterator<String> it7 = it2;
                    Integer num3 = this.marks.get(it6.getName());
                    Intrinsics.checkNotNull(num3);
                    if (num3.intValue() > 0) {
                        count$iv3++;
                        if (count$iv3 < 0) {
                            CollectionsKt.throwCountOverflow();
                        }
                        it2 = it7;
                    } else {
                        it2 = it7;
                    }
                }
                it = it2;
            }
            int ok = count$iv3;
            LinearLayout linearLayout2 = this.drives;
            if (linearLayout2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException(str5);
                linearLayout2 = null;
            }
            TextView $this$listDrives_u24lambda_u2443 = new TextView(this);
            int size = list.size();
            int size2 = m.size();
            if (skipped > 0) {
                str3 = str5;
                str4 = " · " + skipped + " skipped";
            } else {
                str3 = str5;
                str4 = "";
            }
            $this$listDrives_u24lambda_u2443.setText(n + "\n" + size + " pictures · " + size2 + " marked" + str4 + (m.isEmpty() ? "" : " · " + ((ok * 100) / m.size()) + "% right"));
            $this$listDrives_u24lambda_u2443.setTextSize(15.0f);
            $this$listDrives_u24lambda_u2443.setTextColor(Intrinsics.areEqual(n, this.drive) ? -16777216 : -1);
            $this$listDrives_u24lambda_u2443.setPadding(dp(12), dp(10), dp(12), dp(10));
            if (!list.isEmpty()) {
                List $this$all$iv = list;
                if (!($this$all$iv instanceof Collection) || !$this$all$iv.isEmpty()) {
                    Iterator it8 = $this$all$iv.iterator();
                    while (true) {
                        if (!it8.hasNext()) {
                            z2 = true;
                            break;
                        }
                        Object element$iv3 = it8.next();
                        LaneShots.Shot it9 = (LaneShots.Shot) element$iv3;
                        if (!this.marks.containsKey(it9.getName())) {
                            z2 = false;
                            break;
                        }
                    }
                } else {
                    z2 = true;
                }
                if (z2) {
                    z = true;
                    done = z;
                    if (done) {
                        $this$listDrives_u24lambda_u2443.setText("✓ " + ((Object) $this$listDrives_u24lambda_u2443.getText()));
                    }
                    GradientDrawable $this$listDrives_u24lambda_u2443_u24lambda_u2440 = new GradientDrawable();
                    $this$listDrives_u24lambda_u2443_u24lambda_u2440.setCornerRadius(10 * getD());
                    if (Intrinsics.areEqual(n, this.drive)) {
                        if (done) {
                            i2 = 64;
                            i3 = 42;
                            i4 = 22;
                        } else {
                            i2 = 34;
                            i3 = 44;
                            i4 = 30;
                        }
                        rgb = Color.rgb(i4, i2, i3);
                    } else {
                        rgb = Color.rgb(111, 227, 161);
                    }
                    $this$listDrives_u24lambda_u2443_u24lambda_u2440.setColor(rgb);
                    $this$listDrives_u24lambda_u2443.setBackground($this$listDrives_u24lambda_u2443_u24lambda_u2440);
                    $this$listDrives_u24lambda_u2443.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.LanePicturesActivity$$ExternalSyntheticLambda9
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            LanePicturesActivity.this.pick(n);
                        }
                    });
                    $this$listDrives_u24lambda_u2443.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.starpilot.starview.LanePicturesActivity$$ExternalSyntheticLambda10
                        @Override // android.view.View.OnLongClickListener
                        public final boolean onLongClick(View view) {
                            boolean listDrives$lambda$43$lambda$42;
                            listDrives$lambda$43$lambda$42 = LanePicturesActivity.listDrives$lambda$43$lambda$42(LanePicturesActivity.this, n, list, view);
                            return listDrives$lambda$43$lambda$42;
                        }
                    });
                    LinearLayout.LayoutParams $this$listDrives_u24lambda_u2444 = new LinearLayout.LayoutParams(-1, -2);
                    $this$listDrives_u24lambda_u2444.bottomMargin = dp(8);
                    Unit unit = Unit.INSTANCE;
                    linearLayout2.addView($this$listDrives_u24lambda_u2443, $this$listDrives_u24lambda_u2444);
                    it2 = it;
                    str5 = str3;
                }
            }
            z = false;
            done = z;
            if (done) {
            }
            GradientDrawable $this$listDrives_u24lambda_u2443_u24lambda_u24402 = new GradientDrawable();
            $this$listDrives_u24lambda_u2443_u24lambda_u24402.setCornerRadius(10 * getD());
            if (Intrinsics.areEqual(n, this.drive)) {
            }
            $this$listDrives_u24lambda_u2443_u24lambda_u24402.setColor(rgb);
            $this$listDrives_u24lambda_u2443.setBackground($this$listDrives_u24lambda_u2443_u24lambda_u24402);
            $this$listDrives_u24lambda_u2443.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.LanePicturesActivity$$ExternalSyntheticLambda9
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    LanePicturesActivity.this.pick(n);
                }
            });
            $this$listDrives_u24lambda_u2443.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.starpilot.starview.LanePicturesActivity$$ExternalSyntheticLambda10
                @Override // android.view.View.OnLongClickListener
                public final boolean onLongClick(View view) {
                    boolean listDrives$lambda$43$lambda$42;
                    listDrives$lambda$43$lambda$42 = LanePicturesActivity.listDrives$lambda$43$lambda$42(LanePicturesActivity.this, n, list, view);
                    return listDrives$lambda$43$lambda$42;
                }
            });
            LinearLayout.LayoutParams $this$listDrives_u24lambda_u24442 = new LinearLayout.LayoutParams(-1, -2);
            $this$listDrives_u24lambda_u24442.bottomMargin = dp(8);
            Unit unit2 = Unit.INSTANCE;
            linearLayout2.addView($this$listDrives_u24lambda_u2443, $this$listDrives_u24lambda_u24442);
            it2 = it;
            str5 = str3;
        }
        Iterable $this$filter$iv3 = this.all;
        Collection destination$iv$iv3 = new ArrayList();
        for (Object element$iv$iv3 : $this$filter$iv3) {
            LaneShots.Shot it10 = (LaneShots.Shot) element$iv$iv3;
            Integer num4 = this.marks.get(it10.getName());
            if ((num4 != null ? num4.intValue() : 0) != 0) {
                destination$iv$iv3.add(element$iv$iv3);
            }
        }
        List marked = (List) destination$iv$iv3;
        List $this$filter$iv4 = marked;
        Collection destination$iv$iv4 = new ArrayList();
        for (Object element$iv$iv4 : $this$filter$iv4) {
            LaneShots.Shot it11 = (LaneShots.Shot) element$iv$iv4;
            if (Intrinsics.areEqual(it11.getWhy(), "EVERY_30S")) {
                destination$iv$iv4.add(element$iv$iv4);
            }
        }
        List fair = (List) destination$iv$iv4;
        TextView textView = this.score;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("score");
            textView = null;
        }
        if (!marked.isEmpty()) {
            List $this$count$iv3 = marked;
            if (($this$count$iv3 instanceof Collection) && $this$count$iv3.isEmpty()) {
                count$iv = 0;
            } else {
                count$iv = 0;
                for (Object element$iv4 : $this$count$iv3) {
                    LaneShots.Shot it12 = (LaneShots.Shot) element$iv4;
                    Integer num5 = this.marks.get(it12.getName());
                    Intrinsics.checkNotNull(num5);
                    if ((num5.intValue() > 0) && (count$iv = count$iv + 1) < 0) {
                        CollectionsKt.throwCountOverflow();
                    }
                }
            }
            int size3 = marked.size();
            if (!fair.isEmpty()) {
                List $this$count$iv4 = fair;
                if (($this$count$iv4 instanceof Collection) && $this$count$iv4.isEmpty()) {
                    i = 0;
                } else {
                    int count$iv4 = 0;
                    for (Object element$iv5 : $this$count$iv4) {
                        LaneShots.Shot it13 = (LaneShots.Shot) element$iv5;
                        Integer num6 = this.marks.get(it13.getName());
                        Intrinsics.checkNotNull(num6);
                        if ((num6.intValue() > 0) && (count$iv4 = count$iv4 + 1) < 0) {
                            CollectionsKt.throwCountOverflow();
                        }
                    }
                    i = count$iv4;
                }
                str = "  ·  every-30-s: " + ((100 * i) / fair.size()) + "% of " + fair.size();
            }
            str2 = "all drives: " + count$iv + " of " + size3 + " right" + str;
        }
        textView.setText(str2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean listDrives$lambda$43$lambda$42(LanePicturesActivity this$0, String $n, List $list, View it) {
        this$0.askDelete($n, $list);
        return true;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
     */
    private final void show() {
        Bitmap bitmap;
        String str;
        String str2;
        String str3;
        if (this.cur.isEmpty()) {
            return;
        }
        LaneShots.Shot s = this.cur.get(this.i);
        try {
            InputStream openInputStream = getContentResolver().openInputStream(Uri.parse(s.getUri()));
            if (openInputStream != null) {
                InputStream it = openInputStream;
                try {
                    bitmap = BitmapFactory.decodeStream(it);
                    CloseableKt.closeFinally(it, null);
                } finally {
                }
            } else {
                bitmap = null;
            }
        } catch (Exception e) {
            bitmap = null;
        }
        Bitmap bmp = bitmap;
        ZoomImageView zoomImageView = this.img;
        if (bmp != null) {
            if (zoomImageView == null) {
                Intrinsics.throwUninitializedPropertyAccessException("img");
                zoomImageView = null;
            }
            zoomImageView.setImageBitmap(bmp);
        } else {
            if (zoomImageView == null) {
                Intrinsics.throwUninitializedPropertyAccessException("img");
                zoomImageView = null;
            }
            zoomImageView.setImageDrawable(null);
        }
        TextView textView = this.caption;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("caption");
            textView = null;
        }
        textView.setText(s.getCaption());
        Integer mk = this.marks.get(s.getName());
        TextView textView2 = this.info;
        if (textView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("info");
            textView2 = null;
        }
        int i = this.i + 1;
        int size = this.cur.size();
        String why = s.getWhy();
        String str4 = Intrinsics.areEqual(why, "USER") ? "you tapped" : Intrinsics.areEqual(why, "EVERY_30S") ? "every 30 s" : "lane event";
        if (mk == null) {
            str = "  ·  not marked";
        } else if (mk.intValue() > 0) {
            str = "  ·  marked RIGHT";
        } else if (mk.intValue() == 0) {
            String str5 = this.skipWhy.get(s.getName());
            if (str5 != null) {
                switch (str5.hashCode()) {
                    case 169749129:
                        if (str5.equals("intersection")) {
                            str2 = " (intersection)";
                            break;
                        }
                        break;
                    case 1641332067:
                        if (str5.equals("lane_change")) {
                            str2 = " (changing lanes)";
                            break;
                        }
                        break;
                }
                str = "  ·  skipped" + str2;
            }
            str2 = "";
            str = "  ·  skipped" + str2;
        } else {
            str = "  ·  marked WRONG";
        }
        String str6 = s.getRamps().length() > 0 ? "  ·  map: " + CollectionsKt.joinToString$default(LaneRamps.INSTANCE.parse(s.getRamps()), ", ", null, null, 0, null, new Function1() { // from class: com.starpilot.starview.LanePicturesActivity$$ExternalSyntheticLambda18
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                CharSequence show$lambda$50;
                show$lambda$50 = LanePicturesActivity.show$lambda$50((LaneRamps.Ramp) obj);
                return show$lambda$50;
            }
        }, 30, null) : "";
        String it2 = this.fixed.get(s.getName());
        if (it2 == null || (str3 = "  ·  you fixed it: " + it2) == null) {
            str3 = "";
        }
        textView2.setText(i + " of " + size + "  ·  " + str4 + str + str6 + str3 + (bmp == null ? "  ·  picture deleted" : ""));
        TextView textView3 = this.info;
        if (textView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("info");
            textView3 = null;
        }
        textView3.setVisibility(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CharSequence show$lambda$50(LaneRamps.Ramp it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return it.text();
    }

    private final SharedPreferences getLastPrefs() {
        return (SharedPreferences) this.lastPrefs.getValue();
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
        laneFixDialog.show(lanePicturesActivity, "Fix lanes", 0, str, 0, false, (r34 & 64) != 0 ? 0 : saved, (r34 & 128) != 0 ? null : function0, (r34 & 256) != 0 ? null : lastFix, (r34 & 512) != 0 ? null : pic, (r34 & 1024) != 0 ? "" : ((Object) street2) + "  ·  " + StringsKt.take(StringsKt.substringBefore$default(s.getCaption(), "\n", (String) null, 2, (Object) null), 9) + "  ·  pinch to zoom  ·  picture " + (this.i + 1) + " of " + this.cur.size(), (r34 & 2048) != 0 ? CollectionsKt.emptyList() : LaneRamps.INSTANCE.parse(s.getRamps()), (r34 & 4096) != 0 ? null : new Function1() { // from class: com.starpilot.starview.LanePicturesActivity$$ExternalSyntheticLambda6
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
                    exportNow$lambda$65$lambda$63 = LanePicturesActivity.exportNow$lambda$65$lambda$63(LanePicturesActivity.this, $dlg, (String) obj);
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
                        $this$all$iv = null;
                        break;
                    }
                } else {
                    $this$all$iv = 1;
                    break;
                }
            }
        } else {
            $this$all$iv = 1;
        }
        return $this$all$iv != null;
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
                        LaneShots.Shot it3 = it2.getAtMs() > exportedAt ? 1 : null;
                        if (it3 != null) {
                            $this$any$iv = 1;
                            break;
                        }
                    } else {
                        $this$any$iv = null;
                        break;
                    }
                }
            } else {
                $this$any$iv = null;
            }
            if ($this$any$iv == null && getLastPrefs().getLong("marked_at", 0L) <= exportedAt) {
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

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x00cf, code lost:
    
        if (r0 == null) goto L29;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void askDelete$lambda$78(LanePicturesActivity lanePicturesActivity, String str, DialogInterface dialogInterface, int i) {
        String str2;
        LaneShots laneShots = lanePicturesActivity.shots;
        TextView textView = null;
        if (laneShots == null) {
            Intrinsics.throwUninitializedPropertyAccessException("shots");
            laneShots = null;
        }
        int deleteDrive = laneShots.deleteDrive(str);
        LaneShots laneShots2 = lanePicturesActivity.shots;
        if (laneShots2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("shots");
            laneShots2 = null;
        }
        lanePicturesActivity.all = laneShots2.list();
        lanePicturesActivity.marks.clear();
        HashMap<String, Integer> hashMap = lanePicturesActivity.marks;
        LaneShots laneShots3 = lanePicturesActivity.shots;
        if (laneShots3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("shots");
            laneShots3 = null;
        }
        hashMap.putAll(laneShots3.verdicts());
        lanePicturesActivity.fixed.clear();
        HashMap<String, String> hashMap2 = lanePicturesActivity.fixed;
        LaneShots laneShots4 = lanePicturesActivity.shots;
        if (laneShots4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("shots");
            laneShots4 = null;
        }
        hashMap2.putAll(laneShots4.fixes());
        Toast.makeText(lanePicturesActivity, "Deleted " + deleteDrive + " pictures", 0).show();
        List<String> driveNames = lanePicturesActivity.driveNames();
        if (!driveNames.isEmpty()) {
            if (!Intrinsics.areEqual(str, lanePicturesActivity.drive)) {
                String str3 = lanePicturesActivity.drive;
                str2 = driveNames.contains(str3) ? str3 : null;
            }
            str2 = (String) CollectionsKt.first((List) driveNames);
            lanePicturesActivity.pick(str2);
            return;
        }
        lanePicturesActivity.drive = "";
        lanePicturesActivity.cur = CollectionsKt.emptyList();
        ZoomImageView zoomImageView = lanePicturesActivity.img;
        if (zoomImageView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("img");
            zoomImageView = null;
        }
        zoomImageView.setImageDrawable(null);
        TextView textView2 = lanePicturesActivity.caption;
        if (textView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("caption");
            textView2 = null;
        }
        textView2.setText("No lane pictures.");
        TextView textView3 = lanePicturesActivity.info;
        if (textView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("info");
        } else {
            textView = textView3;
        }
        textView.setText("");
        lanePicturesActivity.listDrives();
    }
}
