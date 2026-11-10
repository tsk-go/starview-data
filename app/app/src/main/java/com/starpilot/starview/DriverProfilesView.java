package com.starpilot.starview;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.FileFilter;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: JournalPages.kt */
public final class DriverProfilesView extends CardPage {
    /* VF: from Vineflower (jadx: ) */
    public DriverProfilesView(Context var1, CarUi var2) {
        super(var1, var2);
        Intrinsics.checkNotNullParameter(var1, "ctx");
        Intrinsics.checkNotNullParameter(var2, "ui");
    }

    private final File getDir() {
        File $this$_get_dir__u24lambda_u240 = new File(getContext().getFilesDir(), "profiles");
        $this$_get_dir__u24lambda_u240.mkdirs();
        return $this$_get_dir__u24lambda_u240;
    }

    private final void toast(String s) {
        Toast.makeText(getContext(), s, 1).show();
    }

    @Override // com.starpilot.starview.CardPage
    public void refresh() {
        List emptyList;
        Object m1191constructorimpl;
        SimpleDateFormat simpleDateFormat;
        JSONArray optJSONArray;
        getCol().removeAllViews();
        LinearLayout head = card();
        add(head);
        head.addView(title("Driver profiles", "standard", "person"));
        head.addView(CardPage.line$default(this, "Each driver keeps their own feel: following distance, speed offsets, steering, sounds, the tablet's screen. Save how it's set now under a name; apply a profile while parked.", 0, 0.0f, 6, null));
        int i = 1;
        TextView button = getUi().button("Save current settings as a profile…", true, new Function0() { // from class: com.starpilot.starview.DriverProfilesView$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit refresh$lambda$1;
                refresh$lambda$1 = DriverProfilesView.refresh$lambda$1(DriverProfilesView.this);
                return refresh$lambda$1;
            }
        });
        int i2 = 0;
        Charset charset = null;
        LinearLayout.LayoutParams $this$refresh_u24lambda_u242 = CarUi.lp$default(getUi(), 0, 0, 1, null);
        $this$refresh_u24lambda_u242.topMargin = getUi().dp(8);
        Unit unit = Unit.INSTANCE;
        head.addView(button, $this$refresh_u24lambda_u242);
        Object[] $this$sortedBy$iv = getDir().listFiles(new FileFilter() { // from class: com.starpilot.starview.DriverProfilesView$$ExternalSyntheticLambda1
            @Override // java.io.FileFilter
            public final boolean accept(File file) {
                boolean refresh$lambda$3;
                refresh$lambda$3 = DriverProfilesView.refresh$lambda$3(file);
                return refresh$lambda$3;
            }
        });
        if ($this$sortedBy$iv == null || (emptyList = ArraysKt.sortedWith($this$sortedBy$iv, new Comparator() { // from class: com.starpilot.starview.DriverProfilesView$refresh$$inlined$sortedBy$1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.Comparator
            public final int compare(T t, T t2) {
                File it = (File) t;
                String name = it.getName();
                Intrinsics.checkNotNullExpressionValue(name, "getName(...)");
                String lowerCase = name.toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
                File it2 = (File) t2;
                String name2 = it2.getName();
                Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                String lowerCase2 = name2.toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue(lowerCase2, "toLowerCase(...)");
                return ComparisonsKt.compareValues(lowerCase, lowerCase2);
            }
        })) == null) {
            emptyList = CollectionsKt.emptyList();
        }
        List files = emptyList;
        if (files.isEmpty()) {
            LinearLayout $this$refresh_u24lambda_u245 = card();
            $this$refresh_u24lambda_u245.addView(CardPage.line$default(this, "No profiles yet.", 0, 0.0f, 6, null));
            add($this$refresh_u24lambda_u245);
        }
        Iterator it = files.iterator();
        while (it.hasNext()) {
            final File f = (File) it.next();
            try {
                Result.Companion companion = Result.Companion;
                DriverProfilesView driverProfilesView = this;
                Intrinsics.checkNotNull(f);
                m1191constructorimpl = KtCompat.resultOf(new JSONObject(FilesKt.readText(f, kotlin.text.Charsets.UTF_8)));
            } catch (Throwable th) {
                Result.Companion companion2 = Result.Companion;
                m1191constructorimpl = KtCompat.resultOf(ResultKt.createFailure(th));
            }
            if (KtCompat.isFailure(m1191constructorimpl)) {
                m1191constructorimpl = charset;
            }
            final JSONObject o = (JSONObject) m1191constructorimpl;
            if (o != null) {
                LinearLayout c = card();
                add(c);
                JSONObject optJSONObject = o.optJSONObject("galaxy");
                int n = (optJSONObject == null || (optJSONArray = optJSONObject.optJSONArray("settings")) == null) ? i2 : optJSONArray.length();
                final LinearLayout $this$refresh_u24lambda_u2414 = new LinearLayout(getContext());
                $this$refresh_u24lambda_u2414.setOrientation(i2);
                $this$refresh_u24lambda_u2414.setGravity(16);
                SvIcon svIcon = SvIcon.INSTANCE;
                Context context = $this$refresh_u24lambda_u2414.getContext();
                Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
                View chip$default = SvIcon.chip$default(svIcon, context, "standard", "person", 48, null, 16, null);
                LinearLayout.LayoutParams $this$refresh_u24lambda_u2414_u24lambda_u247 = new LinearLayout.LayoutParams(getUi().dp(48), getUi().dp(48));
                LinearLayout head2 = head;
                $this$refresh_u24lambda_u2414_u24lambda_u247.rightMargin = getUi().dp(14);
                Unit unit2 = Unit.INSTANCE;
                $this$refresh_u24lambda_u2414.addView(chip$default, $this$refresh_u24lambda_u2414_u24lambda_u247);
                LinearLayout $this$refresh_u24lambda_u2414_u24lambda_u2410 = new LinearLayout($this$refresh_u24lambda_u2414.getContext());
                $this$refresh_u24lambda_u2414_u24lambda_u2410.setOrientation(1);
                TextView $this$refresh_u24lambda_u2414_u24lambda_u2410_u24lambda_u248 = new TextView($this$refresh_u24lambda_u2414_u24lambda_u2410.getContext());
                List files2 = files;
                $this$refresh_u24lambda_u2414_u24lambda_u2410_u24lambda_u248.setText(o.optString("name"));
                $this$refresh_u24lambda_u2414_u24lambda_u2410_u24lambda_u248.setTextColor(CarUi.Companion.getTEXT());
                $this$refresh_u24lambda_u2414_u24lambda_u2410_u24lambda_u248.setTextSize(21.0f);
                $this$refresh_u24lambda_u2414_u24lambda_u2410_u24lambda_u248.setTypeface(Typeface.DEFAULT_BOLD);
                $this$refresh_u24lambda_u2414_u24lambda_u2410.addView($this$refresh_u24lambda_u2414_u24lambda_u2410_u24lambda_u248);
                TextView $this$refresh_u24lambda_u2414_u24lambda_u2410_u24lambda_u249 = new TextView($this$refresh_u24lambda_u2414_u24lambda_u2410.getContext());
                simpleDateFormat = JournalPagesKt.dayFmt;
                $this$refresh_u24lambda_u2414_u24lambda_u2410_u24lambda_u249.setText("Saved " + simpleDateFormat.format(new Date(o.optLong("savedAt"))) + " · " + n + " StarPilot settings + tablet");
                $this$refresh_u24lambda_u2414_u24lambda_u2410_u24lambda_u249.setTextColor(CarUi.Companion.getDIM());
                $this$refresh_u24lambda_u2414_u24lambda_u2410_u24lambda_u249.setTextSize(15.0f);
                $this$refresh_u24lambda_u2414_u24lambda_u2410.addView($this$refresh_u24lambda_u2414_u24lambda_u2410_u24lambda_u249);
                $this$refresh_u24lambda_u2414.addView($this$refresh_u24lambda_u2414_u24lambda_u2410, new LinearLayout.LayoutParams(0, -2, 1.0f));
                i = 1;
                $this$refresh_u24lambda_u2414.addView(getUi().button("Apply", true, new Function0() { // from class: com.starpilot.starview.DriverProfilesView$$ExternalSyntheticLambda2
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        Unit refresh$lambda$14$lambda$11;
                        refresh$lambda$14$lambda$11 = DriverProfilesView.refresh$lambda$14$lambda$11(DriverProfilesView.this, o);
                        return refresh$lambda$14$lambda$11;
                    }
                }));
                $this$refresh_u24lambda_u2414.addView(new View($this$refresh_u24lambda_u2414.getContext()), new LinearLayout.LayoutParams(getUi().dp(10), 1));
                $this$refresh_u24lambda_u2414.addView(CarUi.button$default(getUi(), "Delete", false, new Function0() { // from class: com.starpilot.starview.DriverProfilesView$$ExternalSyntheticLambda3
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        Unit refresh$lambda$14$lambda$13;
                        refresh$lambda$14$lambda$13 = DriverProfilesView.refresh$lambda$14$lambda$13($this$refresh_u24lambda_u2414, o, f, this);
                        return refresh$lambda$14$lambda$13;
                    }
                }, 2, null));
                c.addView($this$refresh_u24lambda_u2414);
                i2 = 0;
                head = head2;
                files = files2;
                it = it;
                charset = null;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit refresh$lambda$1(DriverProfilesView this$0) {
        this$0.askSave();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean refresh$lambda$3(File f) {
        String name = f.getName();
        Intrinsics.checkNotNullExpressionValue(name, "getName(...)");
        return StringsKt.endsWith(name, ".json", false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit refresh$lambda$14$lambda$11(DriverProfilesView this$0, JSONObject $o) {
        this$0.apply($o);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit refresh$lambda$14$lambda$13(LinearLayout $this_apply, JSONObject $o, final File $f, final DriverProfilesView this$0) {
        new AlertDialog.Builder($this_apply.getContext()).setMessage("Delete the profile \"" + $o.optString("name") + "\"?").setPositiveButton("Delete", new DialogInterface.OnClickListener() { // from class: com.starpilot.starview.DriverProfilesView$$ExternalSyntheticLambda7
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                DriverProfilesView.refresh$lambda$14$lambda$13$lambda$12($f, this$0, dialogInterface, i);
            }
        }).setNegativeButton("Cancel", (DialogInterface.OnClickListener) null).show();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void refresh$lambda$14$lambda$13$lambda$12(File $f, DriverProfilesView this$0, DialogInterface dialogInterface, int i) {
        $f.delete();
        this$0.refresh();
    }

    private final JSONObject feel(JSONObject backup) {
        JSONObject prefs = backup.optJSONObject("prefs");
        if (prefs == null) {
            return backup;
        }
        Iterator<String> keys = prefs.keys();
        Intrinsics.checkNotNullExpressionValue(keys, "keys(...)");
        for (String k : SequencesKt.toList(SequencesKt.asSequence(keys))) {
            if (!SetsKt.setOf((Object[]) new String[]{"starview_ui", "starview_hud", "speech"}).contains(k)) {
                prefs.remove(k);
            }
        }
        return backup;
    }

    private final void askSave() {
        final EditText $this$askSave_u24lambda_u2415 = new EditText(getContext());
        $this$askSave_u24lambda_u2415.setHint("Driver's name");
        new AlertDialog.Builder(getContext()).setTitle("Save profile").setView($this$askSave_u24lambda_u2415).setPositiveButton("Save", new DialogInterface.OnClickListener() { // from class: com.starpilot.starview.DriverProfilesView$$ExternalSyntheticLambda4
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                DriverProfilesView.askSave$lambda$17($this$askSave_u24lambda_u2415, this, dialogInterface, i);
            }
        }).setNegativeButton("Cancel", (DialogInterface.OnClickListener) null).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void askSave$lambda$17(EditText $et, final DriverProfilesView this$0, DialogInterface dialogInterface, int i) {
        final String name = StringsKt.trim((CharSequence) $et.getText().toString()).toString();
        if (name.length() == 0) {
            return;
        }
        Galaxy7 g = Galaxy7.Companion.current();
        if (g == null) {
            this$0.toast("Not connected to the comma");
        } else {
            g.get("/api/settings/backup", new Function2() { // from class: com.starpilot.starview.DriverProfilesView$$ExternalSyntheticLambda5
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    Unit askSave$lambda$17$lambda$16;
                    askSave$lambda$17$lambda$16 = DriverProfilesView.askSave$lambda$17$lambda$16(this$0, name, (JSONObject) obj, (String) obj2);
                    return askSave$lambda$17$lambda$16;
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit askSave$lambda$17$lambda$16(DriverProfilesView this$0, String $name, JSONObject doc, String err) {
        if (doc == null) {
            this$0.toast(err == null ? "Galaxy didn't give its settings" : err);
            return Unit.INSTANCE;
        }
        JSONObject put = new JSONObject().put("name", $name).put("savedAt", System.currentTimeMillis()).put("galaxy", doc);
        SettingsBackup settingsBackup = SettingsBackup.INSTANCE;
        Context context = this$0.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        JSONObject o = put.put("tablet", this$0.feel(settingsBackup.export(context)));
        File file = new File(this$0.getDir(), new Regex("[^A-Za-z0-9 _-]").replace($name, "_") + ".json");
        String jSONObject = o.toString();
        Intrinsics.checkNotNullExpressionValue(jSONObject, "toString(...)");
        FilesKt.writeText(file, jSONObject, kotlin.text.Charsets.UTF_8);
        this$0.toast("Saved \"" + $name + "\"");
        this$0.refresh();
        return Unit.INSTANCE;
    }

    private final void apply(final JSONObject o) {
        new AlertDialog.Builder(getContext()).setTitle("Apply \"" + o.optString("name") + "\"?").setMessage("StarPilot's settings and this tablet's settings change to this profile. The car must be parked (Galaxy only restores while parked).").setPositiveButton("Apply", new DialogInterface.OnClickListener() { // from class: com.starpilot.starview.DriverProfilesView$$ExternalSyntheticLambda6
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                DriverProfilesView.apply$lambda$22(DriverProfilesView.this, o, dialogInterface, i);
            }
        }).setNegativeButton("Cancel", (DialogInterface.OnClickListener) null).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void apply$lambda$22(final DriverProfilesView this$0, final JSONObject $o, DialogInterface dialogInterface, int i) {
        Galaxy7 g = Galaxy7.Companion.current();
        if (g == null) {
            this$0.toast("Not connected to the comma");
            return;
        }
        JSONObject doc = $o.optJSONObject("galaxy");
        if (doc == null) {
            this$0.toast("This profile has no StarPilot settings");
        } else {
            g.post("/api/settings/restore", doc, new Function2() { // from class: com.starpilot.starview.DriverProfilesView$$ExternalSyntheticLambda8
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    Unit apply$lambda$22$lambda$21;
                    apply$lambda$22$lambda$21 = DriverProfilesView.apply$lambda$22$lambda$21(this$0, $o, (JSONObject) obj, (String) obj2);
                    return apply$lambda$22$lambda$21;
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit apply$lambda$22$lambda$21(DriverProfilesView this$0, JSONObject $o, JSONObject r, String err) {
        int tab;
        if (r == null) {
            this$0.toast("StarPilot settings not applied: " + (err == null ? "Galaxy refused" : err));
            return Unit.INSTANCE;
        }
        JSONObject it = $o.optJSONObject("tablet");
        if (it != null) {
            SettingsBackup settingsBackup = SettingsBackup.INSTANCE;
            Context context = this$0.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            tab = settingsBackup.importJson(context, it);
        } else {
            tab = 0;
        }
        String optString = $o.optString("name");
        String[] strArr = new String[2];
        Integer valueOf = Integer.valueOf(r.optInt("restored", -1));
        if ((valueOf.intValue() >= 0 ? 1 : 0) == 0) {
            valueOf = null;
        }
        strArr[0] = valueOf != null ? valueOf.intValue() + " StarPilot settings changed" : null;
        strArr[1] = tab + " tablet settings";
        this$0.toast("Applied \"" + optString + "\": " + CollectionsKt.joinToString(CollectionsKt.listOfNotNull((Object[]) strArr), ", ", "", "", -1, "...", null));
        return Unit.INSTANCE;
    }
}
