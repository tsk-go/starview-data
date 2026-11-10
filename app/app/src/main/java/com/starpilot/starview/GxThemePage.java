package com.starpilot.starview;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import com.starpilot.starview.CarUi;
import com.starpilot.starview.GxPage;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Triple;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.internal.ProgressionUtilKt;
import kotlin.io.ByteStreamsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.ranges.RangesKt;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: GxTheme.kt */
public final class GxThemePage extends GxPage {
    private boolean busy;
    private TextView busyText;
    private LinearLayout checkBox;
    private final LinkedHashMap<String, int[]> colors;
    private LinearLayout colorsBox;
    private String discord;
    private GxPage.Row discordRow;
    private LinearLayout distBox;
    private GxPage.Row dlRow;
    private final HashMap<String, List<String>> downloadable;
    private boolean downloading;
    private final HashMap<String, GxThemeFile> files;
    private final LinkedHashMap<String, Boolean> flags;
    private final ArrayList<GxThemeFile> frames;
    private String framesName;
    private LinearLayout iconsBox;
    private LinearLayout libBar;
    private LinearLayout libBox;
    private int libTab;
    private String loadError;
    private boolean loading;
    private EditText nameEdit;
    private final HashMap<String, String> names;
    private List<? extends JSONObject> rawThemes;
    private LinearLayout soundsBox;
    private final HashMap<String, JSONObject> sources;
    private boolean submitted;
    private String themeName;
    private List<GxThemeEntry> themes;
    private int tsLength;
    private String tsStyle;
    private String tsType;
    private LinearLayout turnBox;
    private LinearLayout wheelBox;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GxThemePage(Activity act, GxHost gx) {
        super(act, gx);
        Intrinsics.checkNotNullParameter(act, "act");
        Intrinsics.checkNotNullParameter(gx, "gx");
        this.colors = new LinkedHashMap<>();
        this.files = new HashMap<>();
        this.names = new HashMap<>();
        this.frames = new ArrayList<>();
        this.framesName = "";
        this.flags = new LinkedHashMap<>();
        this.sources = new HashMap<>();
        this.tsStyle = "Static";
        this.tsType = "Single Image";
        this.tsLength = 100;
        this.themeName = "";
        this.discord = "";
        this.rawThemes = CollectionsKt.emptyList();
        this.themes = CollectionsKt.emptyList();
        this.loading = true;
        this.loadError = "";
        this.downloadable = new HashMap<>();
        resetColors();
        this.flags.putAll(MapsKt.mapOf(TuplesKt.to("colors", true), TuplesKt.to("distance_icons", false), TuplesKt.to("icons", false), TuplesKt.to("sounds", false), TuplesKt.to("steering_wheel", false), TuplesKt.to("turn_signals", false)));
    }

    private final void setBusy(boolean v) {
        this.busy = v;
        if (this.busyText != null) {
            TextView textView = this.busyText;
            if (textView == null) {
                Intrinsics.throwUninitializedPropertyAccessException("busyText");
                textView = null;
            }
            textView.setVisibility(v ? 0 : 8);
        }
    }

    @Override // com.starpilot.starview.GxPage
    public void build() {
        GxPage.Row row;
        GxPage.note$default(this, "Customize the on-road colors and assets. Changes apply to the active on-device theme; saving keeps them as a named theme.", null, 2, null);
        GxPage.header$default(this, "Colors", null, 2, null);
        this.colorsBox = GxPage.box$default(this, null, 1, null);
        GxPage.header$default(this, "Distance Icons", null, 2, null);
        this.distBox = GxPage.box$default(this, null, 1, null);
        GxPage.header$default(this, "Icons", null, 2, null);
        this.iconsBox = GxPage.box$default(this, null, 1, null);
        GxPage.header$default(this, "Sounds", null, 2, null);
        this.soundsBox = GxPage.box$default(this, null, 1, null);
        GxPage.header$default(this, "Steering Wheel", null, 2, null);
        this.wheelBox = GxPage.box$default(this, null, 1, null);
        GxPage.header$default(this, "Turn Signals", null, 2, null);
        this.turnBox = GxPage.box$default(this, null, 1, null);
        GxPage.header$default(this, "Save / Apply", null, 2, null);
        LinearLayout c = GxPage.card$default(this, null, 1, null);
        GxPage.text$default(this, "Theme Name", c, 21.0f, CarUi.Companion.getTEXT(), false, 16, null);
        EditText $this$build_u24lambda_u240 = new EditText(getAct());
        $this$build_u24lambda_u240.setHint("Enter a name for this theme...");
        $this$build_u24lambda_u240.setTextSize(20.0f);
        $this$build_u24lambda_u240.setTextColor(CarUi.Companion.getTEXT());
        $this$build_u24lambda_u240.setHintTextColor(CarUi.Companion.getDIM());
        $this$build_u24lambda_u240.setSingleLine(true);
        $this$build_u24lambda_u240.setInputType(524289);
        $this$build_u24lambda_u240.setBackground(getUi().rounded(Color.rgb(14, 16, 21), 10, Integer.valueOf(Color.rgb(60, 66, 78))));
        $this$build_u24lambda_u240.setPadding(getUi().dp(16), getUi().dp(12), getUi().dp(16), getUi().dp(12));
        $this$build_u24lambda_u240.addTextChangedListener(new TextWatcher() { // from class: com.starpilot.starview.GxThemePage$build$1$1
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int a, int b, int d) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int a, int b, int d) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s) {
                String str;
                GxThemePage gxThemePage = GxThemePage.this;
                if (s == null || (str = s.toString()) == null) {
                    str = "";
                }
                gxThemePage.themeName = str;
            }
        });
        this.nameEdit = $this$build_u24lambda_u240;
        EditText editText = this.nameEdit;
        if (editText == null) {
            Intrinsics.throwUninitializedPropertyAccessException("nameEdit");
            editText = null;
        }
        LinearLayout.LayoutParams $this$build_u24lambda_u241 = new LinearLayout.LayoutParams(-1, -2);
        $this$build_u24lambda_u241.topMargin = getUi().dp(6);
        $this$build_u24lambda_u241.bottomMargin = getUi().dp(12);
        Unit unit = Unit.INSTANCE;
        c.addView(editText, $this$build_u24lambda_u241);
        GxPage.text$default(this, "Select which components to include:", c, 0.0f, 0, false, 28, null);
        this.checkBox = box(c);
        buttons(new Pair[]{TuplesKt.to("Save Theme", new Function0() { // from class: com.starpilot.starview.GxThemePage$$ExternalSyntheticLambda45
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit build$lambda$2;
                build$lambda$2 = GxThemePage.build$lambda$2(GxThemePage.this);
                return build$lambda$2;
            }
        }), TuplesKt.to("Apply Theme", new Function0() { // from class: com.starpilot.starview.GxThemePage$$ExternalSyntheticLambda46
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit build$lambda$3;
                build$lambda$3 = GxThemePage.build$lambda$3(GxThemePage.this);
                return build$lambda$3;
            }
        }), TuplesKt.to("Export (.zip)", new Function0() { // from class: com.starpilot.starview.GxThemePage$$ExternalSyntheticLambda47
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit build$lambda$4;
                build$lambda$4 = GxThemePage.build$lambda$4(GxThemePage.this);
                return build$lambda$4;
            }
        }), TuplesKt.to("Submit Theme", new Function0() { // from class: com.starpilot.starview.GxThemePage$$ExternalSyntheticLambda48
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit build$lambda$5;
                build$lambda$5 = GxThemePage.build$lambda$5(GxThemePage.this);
                return build$lambda$5;
            }
        })}, c, true);
        TextView $this$build_u24lambda_u246 = GxPage.text$default(this, "Working…", c, 15.0f, CarUi.Companion.getACCENT(), false, 16, null);
        $this$build_u24lambda_u246.setVisibility(8);
        this.busyText = $this$build_u24lambda_u246;
        this.discordRow = GxPage.row$default(this, "Discord Username", null, null, 0, 12, null);
        GxPage.Row row2 = this.discordRow;
        if (row2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("discordRow");
            row = null;
        } else {
            row = row2;
        }
        GxPage.Row.button$default(row, "Edit", false, new Function0() { // from class: com.starpilot.starview.GxThemePage$$ExternalSyntheticLambda49
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit build$lambda$8;
                build$lambda$8 = GxThemePage.build$lambda$8(GxThemePage.this);
                return build$lambda$8;
            }
        }, 2, null);
        paintDiscord();
        GxPage.header$default(this, "Theme Library", null, 2, null);
        GxPage.note$default(this, "All: load a whole theme into the editor. Pick a component to use it on the device right away (and load it into the editor), delete it, or download it.", null, 2, null);
        LinearLayout $this$build_u24lambda_u249 = new LinearLayout(getAct());
        $this$build_u24lambda_u249.setOrientation(0);
        $this$build_u24lambda_u249.setPadding(0, 0, 0, getUi().dp(10));
        this.libBar = $this$build_u24lambda_u249;
        LinearLayout col = getCol();
        HorizontalScrollView $this$build_u24lambda_u2410 = new HorizontalScrollView(getAct());
        $this$build_u24lambda_u2410.setHorizontalScrollBarEnabled(false);
        LinearLayout linearLayout = this.libBar;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("libBar");
            linearLayout = null;
        }
        $this$build_u24lambda_u2410.addView(linearLayout);
        col.addView($this$build_u24lambda_u2410);
        GxPage.Row $this$build_u24lambda_u2411 = GxPage.row$default(this, "Downloading", null, null, 0, 12, null);
        $this$build_u24lambda_u2411.setVisible(false);
        this.dlRow = $this$build_u24lambda_u2411;
        this.libBox = GxPage.box$default(this, null, 1, null);
        renderEditor();
        renderLibrary();
        loadDefault();
        getText("/api/params?key=DiscordUsername", new Function2() { // from class: com.starpilot.starview.GxThemePage$$ExternalSyntheticLambda50
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit build$lambda$12;
                build$lambda$12 = GxThemePage.build$lambda$12(GxThemePage.this, (String) obj, (String) obj2);
                return build$lambda$12;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit build$lambda$2(GxThemePage this$0) {
        this$0.save();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit build$lambda$3(GxThemePage this$0) {
        this$0.apply();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit build$lambda$4(GxThemePage this$0) {
        this$0.exportTheme();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit build$lambda$5(GxThemePage this$0) {
        this$0.submit();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit build$lambda$8(final GxThemePage this$0) {
        GxPage.prompt$default(this$0, "Discord Username", "Discord Username", this$0.discord, 0, "Please enter your Discord username so we can contact you if needed.", new Function1() { // from class: com.starpilot.starview.GxThemePage$$ExternalSyntheticLambda21
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit build$lambda$8$lambda$7;
                build$lambda$8$lambda$7 = GxThemePage.build$lambda$8$lambda$7(GxThemePage.this, (String) obj);
                return build$lambda$8$lambda$7;
            }
        }, 8, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit build$lambda$8$lambda$7(GxThemePage this$0, String v) {
        Intrinsics.checkNotNullParameter(v, "v");
        this$0.discord = StringsKt.trim((CharSequence) v).toString();
        this$0.paintDiscord();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit build$lambda$12(GxThemePage this$0, String e, String t) {
        Intrinsics.checkNotNullParameter(t, "t");
        if (e == null && StringsKt.isBlank(this$0.discord)) {
            this$0.discord = StringsKt.trim((CharSequence) t).toString();
            this$0.paintDiscord();
        }
        return Unit.INSTANCE;
    }

    @Override // com.starpilot.starview.GxPage
    public void onShow() {
        refreshThemeList();
        fetchDownloadables();
    }

    private final String msg(JSONObject $this$msg) {
        String str$default = GxPage.str$default(this, $this$msg, "message", null, 2, null);
        if (StringsKt.isBlank(str$default)) {
            str$default = null;
        }
        return str$default;
    }

    private final String typeQs(String type) {
        if (StringsKt.isBlank(type)) {
            return "";
        }
        return "?type=" + enc(type);
    }

    private final String assetPath(String theme, String type, String rel) {
        return "/api/themes/asset/" + enc(theme) + "/" + CollectionsKt.joinToString$default(StringsKt.split$default((CharSequence) rel, new String[]{"/"}, false, 0, 6, (Object) null), "/", null, null, 0, null, new Function1() { // from class: com.starpilot.starview.GxThemePage$$ExternalSyntheticLambda66
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                CharSequence assetPath$lambda$14;
                assetPath$lambda$14 = GxThemePage.assetPath$lambda$14(GxThemePage.this, (String) obj);
                return assetPath$lambda$14;
            }
        }, 30, null) + typeQs(type);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CharSequence assetPath$lambda$14(GxThemePage this$0, String it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return this$0.enc(it);
    }

    private final String norm(String s) {
        String lowerCase = StringsKt.trim((CharSequence) s).toString().toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
        return StringsKt.trim((CharSequence) new Regex("\\s+").replace(new Regex("[^a-z0-9]+").replace(lowerCase, " "), " ")).toString();
    }

    private final String compOf(String slot) {
        if (StringsKt.startsWith$default(slot, "distanceIcons_", false, 2, (Object) null)) {
            return "distance_icons";
        }
        if (Intrinsics.areEqual(slot, "homeButton") || Intrinsics.areEqual(slot, "settingsButton")) {
            return "icons";
        }
        if (Intrinsics.areEqual(slot, "steeringWheel")) {
            return "steering_wheel";
        }
        if (StringsKt.startsWith$default(slot, "turnSignal", false, 2, (Object) null) || Intrinsics.areEqual(slot, "__sequential__")) {
            return "turn_signals";
        }
        return "sounds";
    }

    private final String guessMime(String name) {
        MimeTypeMap singleton = MimeTypeMap.getSingleton();
        String lowerCase = StringsKt.substringAfterLast(name, '.', "").toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
        return singleton.getMimeTypeFromExtension(lowerCase);
    }

    private final String withExt(String name, String mime) {
        String ext;
        return (StringsKt.contains$default((CharSequence) name, '.', false, 2, (Object) null) || (ext = MimeTypeMap.getSingleton().getExtensionFromMimeType(mime)) == null) ? name : name + "." + ext;
    }

    private final boolean has(String slot) {
        if (this.files.get(slot) != null) {
            return true;
        }
        String str = this.names.get(slot);
        return !(str == null || StringsKt.isBlank(str));
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    private final boolean hasContent(String comp) {
        Iterable iterable;
        Iterable iterable2;
        switch (comp.hashCode()) {
            case -1354842768:
                if (comp.equals("colors")) {
                    return true;
                }
                break;
            case -896509628:
                if (comp.equals("sounds")) {
                    iterable = GxThemeKt.GXTHEME_SOUNDS;
                    Iterable $this$any$iv = iterable;
                    if (($this$any$iv instanceof Collection) && ((Collection) $this$any$iv).isEmpty()) {
                        return false;
                    }
                    for (Object element$iv : $this$any$iv) {
                        String it = (String) element$iv;
                        if (has(it)) {
                            return true;
                        }
                    }
                    return false;
                }
                break;
            case -217663920:
                if (comp.equals("distance_icons")) {
                    iterable2 = GxThemeKt.GXTHEME_DISTANCE;
                    Iterable $this$any$iv2 = iterable2;
                    if (($this$any$iv2 instanceof Collection) && ((Collection) $this$any$iv2).isEmpty()) {
                        return false;
                    }
                    for (Object element$iv2 : $this$any$iv2) {
                        String it2 = (String) element$iv2;
                        if (has("distanceIcons_" + it2)) {
                            return true;
                        }
                    }
                    return false;
                }
                break;
            case 100029210:
                if (comp.equals("icons")) {
                    return has("homeButton") || has("settingsButton");
                }
                break;
            case 271106313:
                if (comp.equals("turn_signals")) {
                    return has("turnSignal") || has("turnSignalBlindspot") || !this.frames.isEmpty() || !StringsKt.isBlank(this.framesName);
                }
                break;
            case 2119462413:
                if (comp.equals("steering_wheel")) {
                    return has("steeringWheel");
                }
                break;
        }
        return false;
    }

    private final boolean checked(String comp) {
        return Intrinsics.areEqual((Object) this.flags.get(comp), (Object) true) && hasContent(comp);
    }

    private final boolean anyChecked() {
        Iterable iterable;
        iterable = GxThemeKt.GXTHEME_COMPS;
        Iterable $this$any$iv = iterable;
        if (($this$any$iv instanceof Collection) && ((Collection) $this$any$iv).isEmpty()) {
            return false;
        }
        for (Object element$iv : $this$any$iv) {
            String it = (String) element$iv;
            if (checked(it)) {
                return true;
            }
        }
        return false;
    }

    private final void changed(String comp) {
        this.submitted = false;
        this.sources.put(comp, null);
    }

    private final void resetColors() {
        LinkedHashMap linkedHashMap;
        this.colors.clear();
        linkedHashMap = GxThemeKt.GXTHEME_COLOR_LABELS;
        for (Object obj : linkedHashMap.keySet()) {
            Intrinsics.checkNotNullExpressionValue(obj, "next(...)");
            String k = (String) obj;
            this.colors.put(k, Intrinsics.areEqual(k, "PathEdge") ? new int[]{18, 107, 54, 255} : new int[]{23, 134, 68, 255});
        }
    }

    private final void setColors(JSONObject o) {
        LinkedHashMap linkedHashMap;
        resetColors();
        linkedHashMap = GxThemeKt.GXTHEME_COLOR_LABELS;
        for (Object obj : linkedHashMap.keySet()) {
            Intrinsics.checkNotNullExpressionValue(obj, "next(...)");
            String k = (String) obj;
            JSONObject s = o.optJSONObject(k);
            if (s != null) {
                this.colors.put(k, new int[]{s.optInt("red", 0), s.optInt("green", 0), s.optInt("blue", 0), s.optInt("alpha", 255)});
            }
        }
    }

    private final JSONObject colorsJson() {
        JSONObject o = new JSONObject();
        for (Map.Entry<String, int[]> entry : this.colors.entrySet()) {
            String k = entry.getKey();
            int[] v = entry.getValue();
            o.put(k, new JSONObject().put("red", v[0]).put("green", v[1]).put("blue", v[2]).put("alpha", v[3]));
        }
        return o;
    }

    private final String hexOf(int c) {
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        String format = String.format("#%02x%02x%02x", Arrays.copyOf(new Object[]{Integer.valueOf(Color.red(c)), Integer.valueOf(Color.green(c)), Integer.valueOf(Color.blue(c))}, 3));
        Intrinsics.checkNotNullExpressionValue(format, "format(...)");
        return format;
    }

    private final Bitmap thumbOf(GxThemeFile f) {
        Object m1191constructorimpl;
        if (!f.getThumbDone()) {
            f.setThumbDone(true);
            try {
                Result.Companion companion = Result.Companion;
                GxThemePage gxThemePage = this;
                BitmapFactory.Options $this$thumbOf_u24lambda_u2420_u24lambda_u2418 = new BitmapFactory.Options();
                $this$thumbOf_u24lambda_u2420_u24lambda_u2418.inJustDecodeBounds = true;
                BitmapFactory.decodeByteArray(f.getBytes(), 0, f.getBytes().length, $this$thumbOf_u24lambda_u2420_u24lambda_u2418);
                int s = 1;
                while ($this$thumbOf_u24lambda_u2420_u24lambda_u2418.outWidth / (s * 2) >= 128 && $this$thumbOf_u24lambda_u2420_u24lambda_u2418.outHeight / (s * 2) >= 128) {
                    s *= 2;
                }
                byte[] bytes = f.getBytes();
                int length = f.getBytes().length;
                BitmapFactory.Options $this$thumbOf_u24lambda_u2420_u24lambda_u2419 = new BitmapFactory.Options();
                $this$thumbOf_u24lambda_u2420_u24lambda_u2419.inSampleSize = s;
                Unit unit = Unit.INSTANCE;
                m1191constructorimpl = Result.m1191constructorimpl(BitmapFactory.decodeByteArray(bytes, 0, length, $this$thumbOf_u24lambda_u2420_u24lambda_u2419));
            } catch (Throwable th) {
                Result.Companion companion2 = Result.Companion;
                m1191constructorimpl = Result.m1191constructorimpl(ResultKt.createFailure(th));
            }
            if (Result.m1197isFailureimpl(m1191constructorimpl)) {
                m1191constructorimpl = null;
            }
            f.setThumb((Bitmap) m1191constructorimpl);
        }
        return f.getThumb();
    }

    private final ImageView thumbView(Bitmap b) {
        ImageView $this$thumbView_u24lambda_u2422 = new ImageView(getAct());
        $this$thumbView_u24lambda_u2422.setImageBitmap(b);
        $this$thumbView_u24lambda_u2422.setScaleType(ImageView.ScaleType.FIT_CENTER);
        $this$thumbView_u24lambda_u2422.setBackground(CarUi.rounded$default(getUi(), Color.rgb(6, 8, 11), 10, null, 4, null));
        $this$thumbView_u24lambda_u2422.setPadding(getUi().dp(4), getUi().dp(4), getUi().dp(4), getUi().dp(4));
        LinearLayout.LayoutParams $this$thumbView_u24lambda_u2422_u24lambda_u2421 = new LinearLayout.LayoutParams(getUi().dp(64), getUi().dp(64));
        $this$thumbView_u24lambda_u2422_u24lambda_u2421.rightMargin = getUi().dp(10);
        $this$thumbView_u24lambda_u2422.setLayoutParams($this$thumbView_u24lambda_u2422_u24lambda_u2421);
        return $this$thumbView_u24lambda_u2422;
    }

    private final void fetchAssets(List<String> paths, final Function1<? super List<GxThemeFile>, Unit> then) {
        if (paths.isEmpty()) {
            then.invoke(CollectionsKt.emptyList());
            return;
        }
        List<String> $this$map$iv = paths;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        for (Object item$iv$iv : $this$map$iv) {
            String it = (String) item$iv$iv;
            destination$iv$iv.add(url(it));
        }
        final List urls = (List) destination$iv$iv;
        new Thread(new Runnable() { // from class: com.starpilot.starview.GxThemePage$$ExternalSyntheticLambda64
            @Override // java.lang.Runnable
            public final void run() {
                GxThemePage.fetchAssets$lambda$29(urls, this, then);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0117  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0118 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void fetchAssets$lambda$29(List $urls, GxThemePage this$0, final Function1 $then) {
        Iterable $this$map$iv;
        int $i$f$map;
        Iterable $this$mapTo$iv$iv;
        Object m1191constructorimpl;
        Throwable th;
        GxThemeFile gxThemeFile;
        String mime;
        String substringBefore$default;
        String obj;
        List $this$map$iv2 = $urls;
        int $i$f$map2 = 0;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv2, 10));
        Iterable $this$mapTo$iv$iv2 = $this$map$iv2;
        for (Object item$iv$iv : $this$mapTo$iv$iv2) {
            String u = (String) item$iv$iv;
            Object obj2 = null;
            try {
                Result.Companion companion = Result.Companion;
                Response execute = GxPage.Companion.client(30L).newCall(new Request.Builder().url(u).build()).execute();
                try {
                    Response r = execute;
                    if (r.isSuccessful()) {
                        ResponseBody body = r.body();
                        if (body != null) {
                            byte[] bytes = body.bytes();
                            if (bytes == null) {
                                $this$map$iv = $this$map$iv2;
                                $i$f$map = $i$f$map2;
                                $this$mapTo$iv$iv = $this$mapTo$iv$iv2;
                            } else {
                                $this$map$iv = $this$map$iv2;
                                $i$f$map = $i$f$map2;
                                try {
                                    $this$mapTo$iv$iv = $this$mapTo$iv$iv2;
                                    try {
                                        String name = URLDecoder.decode(StringsKt.substringAfterLast$default(StringsKt.substringBefore$default(u, '?', (String) null, 2, (Object) null), '/', (String) null, 2, (Object) null), "UTF-8");
                                        String header$default = Response.header$default(r, "Content-Type", null, 2, null);
                                        if (header$default != null && (substringBefore$default = StringsKt.substringBefore$default(header$default, ';', (String) null, 2, (Object) null)) != null && (obj = StringsKt.trim((CharSequence) substringBefore$default).toString()) != null) {
                                            String str = obj;
                                            if (StringsKt.isBlank(str)) {
                                                str = null;
                                            }
                                            mime = str;
                                            if (mime == null) {
                                            }
                                            Intrinsics.checkNotNull(name);
                                            gxThemeFile = new GxThemeFile(name, bytes, mime);
                                        }
                                        Intrinsics.checkNotNull(name);
                                        mime = this$0.guessMime(name);
                                        if (mime == null) {
                                            mime = "application/octet-stream";
                                        }
                                        Intrinsics.checkNotNull(name);
                                        gxThemeFile = new GxThemeFile(name, bytes, mime);
                                    } catch (Throwable th2) {
                                        th = th2;
                                        try {
                                            throw th;
                                        } catch (Throwable th3) {
                                            CloseableKt.closeFinally(execute, th);
                                            throw th3;
                                        }
                                    }
                                } catch (Throwable th4) {
                                    $this$mapTo$iv$iv = $this$mapTo$iv$iv2;
                                    th = th4;
                                }
                            }
                        } else {
                            $this$map$iv = $this$map$iv2;
                            $i$f$map = $i$f$map2;
                            $this$mapTo$iv$iv = $this$mapTo$iv$iv2;
                        }
                        gxThemeFile = null;
                    } else {
                        $this$map$iv = $this$map$iv2;
                        $i$f$map = $i$f$map2;
                        $this$mapTo$iv$iv = $this$mapTo$iv$iv2;
                        gxThemeFile = null;
                    }
                    try {
                        CloseableKt.closeFinally(execute, null);
                        m1191constructorimpl = Result.m1191constructorimpl(gxThemeFile);
                    } catch (Throwable th5) {
                        th = th5;
                        Result.Companion companion2 = Result.Companion;
                        m1191constructorimpl = Result.m1191constructorimpl(ResultKt.createFailure(th));
                        if (Result.m1197isFailureimpl(m1191constructorimpl)) {
                        }
                        destination$iv$iv.add((GxThemeFile) obj2);
                        $this$map$iv2 = $this$map$iv;
                        $i$f$map2 = $i$f$map;
                        $this$mapTo$iv$iv2 = $this$mapTo$iv$iv;
                    }
                } catch (Throwable th6) {
                    $this$map$iv = $this$map$iv2;
                    $i$f$map = $i$f$map2;
                    $this$mapTo$iv$iv = $this$mapTo$iv$iv2;
                    th = th6;
                }
            } catch (Throwable th7) {
                th = th7;
                $this$map$iv = $this$map$iv2;
                $i$f$map = $i$f$map2;
                $this$mapTo$iv$iv = $this$mapTo$iv$iv2;
            }
            if (Result.m1197isFailureimpl(m1191constructorimpl)) {
                obj2 = m1191constructorimpl;
            }
            destination$iv$iv.add((GxThemeFile) obj2);
            $this$map$iv2 = $this$map$iv;
            $i$f$map2 = $i$f$map;
            $this$mapTo$iv$iv2 = $this$mapTo$iv$iv;
        }
        final List out = (List) destination$iv$iv;
        this$0.getMain().post(new Runnable() { // from class: com.starpilot.starview.GxThemePage$$ExternalSyntheticLambda38
            @Override // java.lang.Runnable
            public final void run() {
                Function1.this.invoke(out);
            }
        });
    }

    private final void loadDefault() {
        getJson("/api/themes/default", new Function2() { // from class: com.starpilot.starview.GxThemePage$$ExternalSyntheticLambda56
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit loadDefault$lambda$43;
                loadDefault$lambda$43 = GxThemePage.loadDefault$lambda$43(GxThemePage.this, (String) obj, (JSONObject) obj2);
                return loadDefault$lambda$43;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit loadDefault$lambda$43(final GxThemePage this$0, String e, JSONObject d) {
        List<String> list;
        List<String> list2;
        if (e != null || d == null) {
            this$0.toast("Failed to load default theme.");
            return Unit.INSTANCE;
        }
        JSONObject it = d.optJSONObject("colors");
        if (it != null) {
            if ((it.length() > 0 ? 1 : null) == null) {
                it = null;
            }
            if (it != null) {
                this$0.setColors(it);
                this$0.flags.put("colors", true);
            }
        }
        JSONObject optJSONObject = d.optJSONObject("theme_names");
        if (optJSONObject == null) {
            optJSONObject = new JSONObject();
        }
        JSONObject tn = optJSONObject;
        JSONObject optJSONObject2 = d.optJSONObject("images");
        if (optJSONObject2 == null) {
            optJSONObject2 = new JSONObject();
        }
        JSONObject imgs = optJSONObject2;
        final ArrayList jobs = new ArrayList();
        loadDefault$lambda$43$slot(this$0, jobs, "homeButton", GxPage.str$default(this$0, imgs, "homeButton", null, 2, null), "icons", loadDefault$lambda$43$nm(this$0, tn, "icons"));
        loadDefault$lambda$43$slot(this$0, jobs, "settingsButton", GxPage.str$default(this$0, imgs, "settingsButton", null, 2, null), "icons", loadDefault$lambda$43$nm(this$0, tn, "icons"));
        loadDefault$lambda$43$slot(this$0, jobs, "steeringWheel", GxPage.str$default(this$0, imgs, "steeringWheel", null, 2, null), "steering_wheel", loadDefault$lambda$43$nm(this$0, tn, "steeringWheel"));
        loadDefault$lambda$43$slot(this$0, jobs, "turnSignal", GxPage.str$default(this$0, imgs, "turnSignal", null, 2, null), "signals", loadDefault$lambda$43$nm(this$0, tn, "turnSignals"));
        loadDefault$lambda$43$slot(this$0, jobs, "turnSignalBlindspot", GxPage.str$default(this$0, imgs, "turnSignalBlindspot", null, 2, null), "signals", loadDefault$lambda$43$nm(this$0, tn, "turnSignals"));
        JSONObject di = imgs.optJSONObject("distanceIcons");
        if (di != null) {
            list2 = GxThemeKt.GXTHEME_DISTANCE;
            for (String k : list2) {
                loadDefault$lambda$43$slot(this$0, jobs, "distanceIcons_" + k, GxPage.str$default(this$0, di, k, null, 2, null), "distance_icons", loadDefault$lambda$43$nm(this$0, tn, "distanceIcons"));
            }
        }
        JSONObject so = d.optJSONObject("sounds");
        if (so != null) {
            list = GxThemeKt.GXTHEME_SOUNDS;
            for (String k2 : list) {
                loadDefault$lambda$43$slot(this$0, jobs, k2, GxPage.str$default(this$0, so, k2, null, 2, null), "sounds", loadDefault$lambda$43$nm(this$0, tn, "sounds"));
            }
        }
        if (d.optInt("turnSignalLength", 0) > 0) {
            this$0.tsLength = d.optInt("turnSignalLength");
        }
        String str$default = GxPage.str$default(this$0, d, "turnSignalStyle", null, 2, null);
        if (StringsKt.isBlank(str$default)) {
            str$default = null;
        }
        String it2 = str$default;
        if (it2 != null) {
            this$0.tsStyle = it2;
        }
        String str$default2 = GxPage.str$default(this$0, d, "turnSignalType", null, 2, null);
        if (StringsKt.isBlank(str$default2)) {
            str$default2 = null;
        }
        String it3 = str$default2;
        if (it3 != null) {
            this$0.tsType = it3;
        }
        JSONArray optJSONArray = d.optJSONArray("sequentialImages");
        List strings = optJSONArray != null ? this$0.strings(optJSONArray) : null;
        if (strings == null) {
            strings = CollectionsKt.emptyList();
        }
        List seq = strings;
        if (!seq.isEmpty()) {
            this$0.framesName = loadDefault$lambda$43$nm(this$0, tn, "turnSignals");
            this$0.frames.clear();
            for (String img : seq) {
                jobs.add(TuplesKt.to("signals/" + img, new Function1() { // from class: com.starpilot.starview.GxThemePage$$ExternalSyntheticLambda58
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        Unit loadDefault$lambda$43$lambda$40;
                        loadDefault$lambda$43$lambda$40 = GxThemePage.loadDefault$lambda$43$lambda$40(GxThemePage.this, (GxThemeFile) obj);
                        return loadDefault$lambda$43$lambda$40;
                    }
                }));
            }
        }
        this$0.renderEditor();
        ArrayList $this$map$iv = jobs;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        for (Object item$iv$iv : $this$map$iv) {
            destination$iv$iv.add(this$0.assetPath("__active__", "active", (String) ((Pair) item$iv$iv).getFirst()));
            seq = seq;
            $this$map$iv = $this$map$iv;
        }
        this$0.fetchAssets((List) destination$iv$iv, new Function1() { // from class: com.starpilot.starview.GxThemePage$$ExternalSyntheticLambda59
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit loadDefault$lambda$43$lambda$42;
                loadDefault$lambda$43$lambda$42 = GxThemePage.loadDefault$lambda$43$lambda$42(jobs, this$0, (List) obj);
                return loadDefault$lambda$43$lambda$42;
            }
        });
        return Unit.INSTANCE;
    }

    private static final String loadDefault$lambda$43$nm(GxThemePage this$0, JSONObject tn, String k) {
        String str$default = GxPage.str$default(this$0, tn, k, null, 2, null);
        if (StringsKt.isBlank(str$default)) {
            str$default = "Active";
        }
        return str$default;
    }

    private static final void loadDefault$lambda$43$slot(final GxThemePage this$0, ArrayList<Pair<String, Function1<GxThemeFile, Unit>>> arrayList, final String slot, String file, String dir, String display) {
        if (StringsKt.isBlank(file)) {
            return;
        }
        this$0.names.put(slot, display);
        arrayList.add(TuplesKt.to(dir + "/" + file, new Function1() { // from class: com.starpilot.starview.GxThemePage$$ExternalSyntheticLambda54
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit loadDefault$lambda$43$slot$lambda$33;
                loadDefault$lambda$43$slot$lambda$33 = GxThemePage.loadDefault$lambda$43$slot$lambda$33(GxThemePage.this, slot, (GxThemeFile) obj);
                return loadDefault$lambda$43$slot$lambda$33;
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit loadDefault$lambda$43$slot$lambda$33(GxThemePage this$0, String $slot, GxThemeFile f) {
        Intrinsics.checkNotNullParameter(f, "f");
        this$0.files.put($slot, f);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit loadDefault$lambda$43$lambda$40(GxThemePage this$0, GxThemeFile f) {
        Intrinsics.checkNotNullParameter(f, "f");
        this$0.frames.add(f);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit loadDefault$lambda$43$lambda$42(ArrayList $jobs, GxThemePage this$0, List res) {
        Intrinsics.checkNotNullParameter(res, "res");
        Iterator it = res.iterator();
        int i = 0;
        while (it.hasNext()) {
            int i2 = i;
            i++;
            GxThemeFile f = (GxThemeFile) it.next();
            if (f != null) {
                ((Function1) ((Pair) $jobs.get(i2)).getSecond()).invoke(f);
            }
        }
        this$0.renderEditor();
        return Unit.INSTANCE;
    }

    private final void renderEditor() {
        List<String> list;
        List<String> list2;
        Map map;
        String str;
        if (this.colorsBox == null) {
            return;
        }
        renderColors();
        LinearLayout linearLayout = this.distBox;
        LinearLayout linearLayout2 = null;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("distBox");
            linearLayout = null;
        }
        linearLayout.removeAllViews();
        LinearLayout linearLayout3 = this.distBox;
        if (linearLayout3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("distBox");
            linearLayout3 = null;
        }
        note("Recommended size 250x250.", linearLayout3);
        list = GxThemeKt.GXTHEME_DISTANCE;
        for (String k : list) {
            String str2 = "distanceIcons_" + k;
            if (k.length() > 0) {
                StringBuilder sb = new StringBuilder();
                char it = k.charAt(0);
                String valueOf = String.valueOf(it);
                Intrinsics.checkNotNull(valueOf, "null cannot be cast to non-null type java.lang.String");
                String upperCase = valueOf.toUpperCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue(upperCase, "toUpperCase(...)");
                StringBuilder append = sb.append((Object) upperCase);
                String substring = k.substring(1);
                Intrinsics.checkNotNullExpressionValue(substring, "substring(...)");
                str = append.append(substring).toString();
            } else {
                str = k;
            }
            LinearLayout linearLayout4 = this.distBox;
            if (linearLayout4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("distBox");
                linearLayout4 = null;
            }
            slotRow(str2, str, true, linearLayout4);
        }
        LinearLayout linearLayout5 = this.iconsBox;
        if (linearLayout5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("iconsBox");
            linearLayout5 = null;
        }
        linearLayout5.removeAllViews();
        LinearLayout linearLayout6 = this.iconsBox;
        if (linearLayout6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("iconsBox");
            linearLayout6 = null;
        }
        note("Home Button: 250x250 · Settings Button: 169x104", linearLayout6);
        LinearLayout linearLayout7 = this.iconsBox;
        if (linearLayout7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("iconsBox");
            linearLayout7 = null;
        }
        slotRow("homeButton", "Home Button", true, linearLayout7);
        LinearLayout linearLayout8 = this.iconsBox;
        if (linearLayout8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("iconsBox");
            linearLayout8 = null;
        }
        slotRow("settingsButton", "Settings Button", true, linearLayout8);
        LinearLayout linearLayout9 = this.soundsBox;
        if (linearLayout9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("soundsBox");
            linearLayout9 = null;
        }
        linearLayout9.removeAllViews();
        list2 = GxThemeKt.GXTHEME_SOUNDS;
        for (String k2 : list2) {
            map = GxThemeKt.GXTHEME_SOUND_LABELS;
            String str3 = (String) map.get(k2);
            if (str3 == null) {
                str3 = k2;
            }
            LinearLayout linearLayout10 = this.soundsBox;
            if (linearLayout10 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("soundsBox");
                linearLayout10 = null;
            }
            slotRow(k2, str3, false, linearLayout10);
        }
        LinearLayout linearLayout11 = this.wheelBox;
        if (linearLayout11 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("wheelBox");
            linearLayout11 = null;
        }
        linearLayout11.removeAllViews();
        LinearLayout linearLayout12 = this.wheelBox;
        if (linearLayout12 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("wheelBox");
            linearLayout12 = null;
        }
        note("Recommended size 250x250.", linearLayout12);
        LinearLayout linearLayout13 = this.wheelBox;
        if (linearLayout13 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("wheelBox");
        } else {
            linearLayout2 = linearLayout13;
        }
        slotRow("steeringWheel", "Steering Wheel Image", true, linearLayout2);
        renderTurn();
        renderChecklist();
    }

    private final void renderColors() {
        LinkedHashMap linkedHashMap;
        LinearLayout linearLayout;
        List keys;
        LinkedHashMap linkedHashMap2;
        LinearLayout linearLayout2 = this.colorsBox;
        LinearLayout linearLayout3 = null;
        if (linearLayout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("colorsBox");
            linearLayout2 = null;
        }
        linearLayout2.removeAllViews();
        linkedHashMap = GxThemeKt.GXTHEME_COLOR_LABELS;
        Set keySet = linkedHashMap.keySet();
        Intrinsics.checkNotNullExpressionValue(keySet, "<get-keys>(...)");
        List keys2 = CollectionsKt.toList(keySet);
        int i = 0;
        int i2 = 0;
        int i3 = 2;
        int progressionLastElement = ProgressionUtilKt.getProgressionLastElement(0, keys2.size() - 1, 2);
        int i4 = 1;
        if (0 <= progressionLastElement) {
            while (true) {
                LinearLayout line = new LinearLayout(getAct());
                line.setOrientation(i2);
                LinearLayout $this$renderColors_u24lambda_u2445 = this.colorsBox;
                if ($this$renderColors_u24lambda_u2445 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("colorsBox");
                    $this$renderColors_u24lambda_u2445 = linearLayout3;
                }
                char c = 3;
                $this$renderColors_u24lambda_u2445.addView(line, CarUi.lp$default(getUi(), i2, i2, 3, linearLayout3));
                int j = 0;
                while (j < i3) {
                    final String k = (String) CollectionsKt.getOrNull(keys2, i + j);
                    if (k == null) {
                        line.addView(new View(getAct()), new LinearLayout.LayoutParams(i2, i4, 1.0f));
                        keys = keys2;
                    } else {
                        int[] v = this.colors.get(k);
                        if (v == null) {
                            v = new int[4];
                            v[i2] = i2;
                            v[i4] = i2;
                            v[i3] = i2;
                            v[c] = 255;
                        }
                        LinearLayout $this$renderColors_u24lambda_u2447 = new LinearLayout(getAct());
                        $this$renderColors_u24lambda_u2447.setOrientation(i2);
                        $this$renderColors_u24lambda_u2447.setGravity(16);
                        $this$renderColors_u24lambda_u2447.setMinimumHeight(getUi().dp(76));
                        $this$renderColors_u24lambda_u2447.setPadding(getUi().dp(16), getUi().dp(12), getUi().dp(16), getUi().dp(12));
                        $this$renderColors_u24lambda_u2447.setBackground(CarUi.rounded$default(getUi(), CarUi.Companion.getCARD(), 0, null, 6, null));
                        $this$renderColors_u24lambda_u2447.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.GxThemePage$$ExternalSyntheticLambda41
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                GxThemePage.this.editColor(k);
                            }
                        });
                        View $this$renderColors_u24lambda_u2448 = new View(getAct());
                        keys = keys2;
                        $this$renderColors_u24lambda_u2448.setBackground(getUi().rounded(Color.argb(v[3], v[0], v[1], v[2]), 10, Integer.valueOf(Color.rgb(90, 96, 108))));
                        LinearLayout.LayoutParams $this$renderColors_u24lambda_u2449 = new LinearLayout.LayoutParams(getUi().dp(52), getUi().dp(52));
                        $this$renderColors_u24lambda_u2449.rightMargin = getUi().dp(14);
                        Unit unit = Unit.INSTANCE;
                        $this$renderColors_u24lambda_u2447.addView($this$renderColors_u24lambda_u2448, $this$renderColors_u24lambda_u2449);
                        LinearLayout tb = new LinearLayout(getAct());
                        tb.setOrientation(1);
                        TextView $this$renderColors_u24lambda_u2451 = new TextView(getAct());
                        linkedHashMap2 = GxThemeKt.GXTHEME_COLOR_LABELS;
                        $this$renderColors_u24lambda_u2451.setText((CharSequence) linkedHashMap2.get(k));
                        $this$renderColors_u24lambda_u2451.setTextColor(CarUi.Companion.getTEXT());
                        $this$renderColors_u24lambda_u2451.setTextSize(21.0f);
                        tb.addView($this$renderColors_u24lambda_u2451);
                        TextView $this$renderColors_u24lambda_u2452 = new TextView(getAct());
                        $this$renderColors_u24lambda_u2452.setText(hexOf(Color.rgb(v[0], v[1], v[2])));
                        $this$renderColors_u24lambda_u2452.setTextColor(CarUi.Companion.getDIM());
                        $this$renderColors_u24lambda_u2452.setTextSize(15.0f);
                        $this$renderColors_u24lambda_u2452.setTypeface(Typeface.MONOSPACE);
                        tb.addView($this$renderColors_u24lambda_u2452);
                        $this$renderColors_u24lambda_u2447.addView(tb, new LinearLayout.LayoutParams(0, -2, 1.0f));
                        $this$renderColors_u24lambda_u2447.addView(CarUi.button$default(getUi(), "Edit", false, new Function0() { // from class: com.starpilot.starview.GxThemePage$$ExternalSyntheticLambda42
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                Unit renderColors$lambda$53;
                                renderColors$lambda$53 = GxThemePage.renderColors$lambda$53(GxThemePage.this, k);
                                return renderColors$lambda$53;
                            }
                        }, 2, null));
                        LinearLayout linearLayout4 = $this$renderColors_u24lambda_u2447;
                        LinearLayout.LayoutParams $this$renderColors_u24lambda_u2454 = new LinearLayout.LayoutParams(0, -1, 1.0f);
                        if (j == 0) {
                            $this$renderColors_u24lambda_u2454.rightMargin = getUi().dp(8);
                        }
                        Unit unit2 = Unit.INSTANCE;
                        line.addView(linearLayout4, $this$renderColors_u24lambda_u2454);
                    }
                    j++;
                    keys2 = keys;
                    i2 = 0;
                    i3 = 2;
                    i4 = 1;
                    c = 3;
                }
                List keys3 = keys2;
                if (i == progressionLastElement) {
                    break;
                }
                i += 2;
                keys2 = keys3;
                linearLayout3 = null;
                i2 = 0;
                i3 = 2;
                i4 = 1;
            }
        }
        GxThemePage gxThemePage = this;
        Pair[] pairArr = {TuplesKt.to("Reset to Stock Green", new Function0() { // from class: com.starpilot.starview.GxThemePage$$ExternalSyntheticLambda43
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit renderColors$lambda$55;
                renderColors$lambda$55 = GxThemePage.renderColors$lambda$55(GxThemePage.this);
                return renderColors$lambda$55;
            }
        })};
        LinearLayout linearLayout5 = this.colorsBox;
        if (linearLayout5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("colorsBox");
            linearLayout = null;
        } else {
            linearLayout = linearLayout5;
        }
        GxPage.buttons$default(gxThemePage, pairArr, linearLayout, false, 4, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderColors$lambda$53(GxThemePage this$0, String $k) {
        this$0.editColor($k);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderColors$lambda$55(GxThemePage this$0) {
        this$0.resetColors();
        this$0.flags.put("colors", true);
        this$0.changed("colors");
        this$0.renderEditor();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void editColor(final String k) {
        LinkedHashMap linkedHashMap;
        int[] v = this.colors.get(k);
        if (v == null) {
            return;
        }
        linkedHashMap = GxThemeKt.GXTHEME_COLOR_LABELS;
        String str = (String) linkedHashMap.get(k);
        if (str == null) {
            str = k;
        }
        gxThemeColorDialog(str, Color.rgb(v[0], v[1], v[2]), new Function1() { // from class: com.starpilot.starview.GxThemePage$$ExternalSyntheticLambda60
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit editColor$lambda$56;
                editColor$lambda$56 = GxThemePage.editColor$lambda$56(GxThemePage.this, k, ((Integer) obj).intValue());
                return editColor$lambda$56;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit editColor$lambda$56(GxThemePage this$0, String $k, int c) {
        this$0.colors.put($k, new int[]{Color.red(c), Color.green(c), Color.blue(c), 255});
        this$0.flags.put("colors", true);
        this$0.changed("colors");
        this$0.renderEditor();
        return Unit.INSTANCE;
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x0028, code lost:
    
        if (r5 == null) goto L9;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final void slotRow(final String slot, String label, final boolean image, ViewGroup into) {
        String str;
        Bitmap it;
        GxThemeFile f = this.files.get(slot);
        GxThemePage gxThemePage = this;
        String str2 = this.names.get(slot);
        if (str2 != null) {
            String str3 = str2;
            if (StringsKt.isBlank(str3)) {
                str3 = null;
            }
            str = str3;
        }
        str = "No file selected";
        GxPage.Row r = GxPage.row$default(gxThemePage, label, str, into, 0, 8, null);
        if (image && f != null && (it = thumbOf(f)) != null) {
            r.add(thumbView(it));
        }
        GxPage.Row.button$default(r, "Choose", false, new Function0() { // from class: com.starpilot.starview.GxThemePage$$ExternalSyntheticLambda51
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit slotRow$lambda$59;
                slotRow$lambda$59 = GxThemePage.slotRow$lambda$59(GxThemePage.this, slot, image);
                return slotRow$lambda$59;
            }
        }, 2, null);
        if (has(slot)) {
            GxPage.Row.button$default(r, "Clear", false, new Function0() { // from class: com.starpilot.starview.GxThemePage$$ExternalSyntheticLambda52
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit slotRow$lambda$60;
                    slotRow$lambda$60 = GxThemePage.slotRow$lambda$60(GxThemePage.this, slot);
                    return slotRow$lambda$60;
                }
            }, 2, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit slotRow$lambda$59(GxThemePage this$0, String $slot, boolean $image) {
        this$0.choose($slot, $image);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit slotRow$lambda$60(GxThemePage this$0, String $slot) {
        this$0.clearSlot($slot);
        return Unit.INSTANCE;
    }

    private final void renderTurn() {
        LinearLayout linearLayout = this.turnBox;
        LinearLayout linearLayout2 = null;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("turnBox");
            linearLayout = null;
        }
        linearLayout.removeAllViews();
        int i = 0;
        List<String> listOf = CollectionsKt.listOf((Object[]) new String[]{"Static", "Traditional"});
        boolean areEqual = Intrinsics.areEqual(this.tsStyle, "Traditional");
        LinearLayout linearLayout3 = this.turnBox;
        if (linearLayout3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("turnBox");
            linearLayout3 = null;
        }
        choice("Style", "Static - The turn signal animation appears next to the current speed.\nTraditional - The turn signal animation moves across the bottom of the screen.", listOf, areEqual ? 1 : 0, linearLayout3, new Function1() { // from class: com.starpilot.starview.GxThemePage$$ExternalSyntheticLambda27
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit renderTurn$lambda$61;
                renderTurn$lambda$61 = GxThemePage.renderTurn$lambda$61(GxThemePage.this, ((Integer) obj).intValue());
                return renderTurn$lambda$61;
            }
        });
        if (Intrinsics.areEqual(this.tsStyle, "Traditional")) {
            List<String> listOf2 = CollectionsKt.listOf((Object[]) new String[]{"Sequential", "Single Image"});
            int i2 = !Intrinsics.areEqual(this.tsType, "Sequential") ? 1 : 0;
            LinearLayout linearLayout4 = this.turnBox;
            if (linearLayout4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("turnBox");
                linearLayout4 = null;
            }
            choice("Turn Signal Type", null, listOf2, i2, linearLayout4, new Function1() { // from class: com.starpilot.starview.GxThemePage$$ExternalSyntheticLambda28
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    Unit renderTurn$lambda$62;
                    renderTurn$lambda$62 = GxThemePage.renderTurn$lambda$62(GxThemePage.this, ((Integer) obj).intValue());
                    return renderTurn$lambda$62;
                }
            });
        }
        GxThemePage gxThemePage = this;
        LinearLayout linearLayout5 = this.turnBox;
        if (linearLayout5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("turnBox");
            linearLayout5 = null;
        }
        GxPage.Row row$default = GxPage.row$default(gxThemePage, "Length (25-1000ms)", null, linearLayout5, 0, 8, null);
        final TextView textView = new TextView(getAct());
        textView.setText(String.valueOf(this.tsLength));
        textView.setTextSize(22.0f);
        textView.setTextColor(CarUi.Companion.getTEXT());
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        textView.setMinWidth(getUi().dp(100));
        textView.setGravity(17);
        textView.setMinHeight(getUi().dp(56));
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.GxThemePage$$ExternalSyntheticLambda29
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                GxThemePage.renderTurn$lambda$65(GxThemePage.this, textView, view);
            }
        });
        row$default.add(getUi().stepper("−", new Function0() { // from class: com.starpilot.starview.GxThemePage$$ExternalSyntheticLambda30
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit renderTurn$lambda$66;
                renderTurn$lambda$66 = GxThemePage.renderTurn$lambda$66(GxThemePage.this, textView);
                return renderTurn$lambda$66;
            }
        }));
        row$default.add(textView);
        row$default.add(getUi().stepper("+", new Function0() { // from class: com.starpilot.starview.GxThemePage$$ExternalSyntheticLambda31
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit renderTurn$lambda$67;
                renderTurn$lambda$67 = GxThemePage.renderTurn$lambda$67(GxThemePage.this, textView);
                return renderTurn$lambda$67;
            }
        }));
        LinearLayout linearLayout6 = this.turnBox;
        if (linearLayout6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("turnBox");
            linearLayout6 = null;
        }
        slotRow("turnSignalBlindspot", "Blind Spot", true, linearLayout6);
        if (Intrinsics.areEqual(this.tsType, "Sequential")) {
            String str = !StringsKt.isBlank(this.framesName) ? this.framesName : !this.frames.isEmpty() ? this.frames.size() + " image(s) selected" : "No file selected";
            GxThemePage gxThemePage2 = this;
            LinearLayout linearLayout7 = this.turnBox;
            if (linearLayout7 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("turnBox");
                linearLayout7 = null;
            }
            GxPage.Row row$default2 = GxPage.row$default(gxThemePage2, "Turn Signals", str, linearLayout7, 0, 8, null);
            GxPage.Row.button$default(row$default2, "Add Frame", false, new Function0() { // from class: com.starpilot.starview.GxThemePage$$ExternalSyntheticLambda32
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit renderTurn$lambda$68;
                    renderTurn$lambda$68 = GxThemePage.renderTurn$lambda$68(GxThemePage.this);
                    return renderTurn$lambda$68;
                }
            }, 2, null);
            if (!this.frames.isEmpty() || !StringsKt.isBlank(this.framesName)) {
                GxPage.Row.button$default(row$default2, "Clear", false, new Function0() { // from class: com.starpilot.starview.GxThemePage$$ExternalSyntheticLambda34
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        Unit renderTurn$lambda$69;
                        renderTurn$lambda$69 = GxThemePage.renderTurn$lambda$69(GxThemePage.this);
                        return renderTurn$lambda$69;
                    }
                }, 2, null);
            }
            if (!this.frames.isEmpty()) {
                LinearLayout linearLayout8 = this.turnBox;
                if (linearLayout8 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("turnBox");
                    linearLayout8 = null;
                }
                text("Turn Signal Sequence Order", linearLayout8, 15.0f, CarUi.Companion.getDIM(), true).setPadding(getUi().dp(6), getUi().dp(8), 0, getUi().dp(6));
                for (GxThemeFile gxThemeFile : this.frames) {
                    final int i3 = i;
                    i++;
                    String str2 = "Frame " + (i3 + 1);
                    String fileName = gxThemeFile.getFileName();
                    LinearLayout linearLayout9 = this.turnBox;
                    if (linearLayout9 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("turnBox");
                        linearLayout9 = null;
                    }
                    GxPage.Row row = row(str2, fileName, linearLayout9, 1);
                    Bitmap thumbOf = thumbOf(gxThemeFile);
                    if (thumbOf != null) {
                        row.add(thumbView(thumbOf));
                    }
                    if (i3 > 0) {
                        GxPage.Row.button$default(row, "Up", false, new Function0() { // from class: com.starpilot.starview.GxThemePage$$ExternalSyntheticLambda35
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                Unit renderTurn$lambda$71;
                                renderTurn$lambda$71 = GxThemePage.renderTurn$lambda$71(GxThemePage.this, i3);
                                return renderTurn$lambda$71;
                            }
                        }, 2, null);
                    }
                    if (i3 < this.frames.size() - 1) {
                        GxPage.Row.button$default(row, "Down", false, new Function0() { // from class: com.starpilot.starview.GxThemePage$$ExternalSyntheticLambda36
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                Unit renderTurn$lambda$72;
                                renderTurn$lambda$72 = GxThemePage.renderTurn$lambda$72(GxThemePage.this, i3);
                                return renderTurn$lambda$72;
                            }
                        }, 2, null);
                    }
                }
                return;
            }
            return;
        }
        LinearLayout linearLayout10 = this.turnBox;
        if (linearLayout10 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("turnBox");
        } else {
            linearLayout2 = linearLayout10;
        }
        slotRow("turnSignal", "Turn Signal", true, linearLayout2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderTurn$lambda$61(GxThemePage this$0, int i) {
        this$0.tsStyle = i == 1 ? "Traditional" : "Static";
        this$0.flags.put("turn_signals", true);
        this$0.changed("turn_signals");
        this$0.renderEditor();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderTurn$lambda$62(GxThemePage this$0, int i) {
        this$0.tsType = i == 0 ? "Sequential" : "Single Image";
        this$0.changed("turn_signals");
        this$0.frames.clear();
        this$0.framesName = "";
        this$0.files.remove("turnSignal");
        this$0.names.remove("turnSignal");
        this$0.renderEditor();
        return Unit.INSTANCE;
    }

    private static final void renderTurn$setLen(GxThemePage this$0, TextView tv, int v) {
        this$0.tsLength = RangesKt.coerceIn(v, 25, 1000);
        tv.setText(String.valueOf(this$0.tsLength));
        this$0.flags.put("turn_signals", true);
        this$0.changed("turn_signals");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void renderTurn$lambda$65(final GxThemePage this$0, final TextView $tv, View it) {
        GxPage.prompt$default(this$0, "Length (25-1000ms)", "100", String.valueOf(this$0.tsLength), 2, null, new Function1() { // from class: com.starpilot.starview.GxThemePage$$ExternalSyntheticLambda22
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit renderTurn$lambda$65$lambda$64;
                renderTurn$lambda$65$lambda$64 = GxThemePage.renderTurn$lambda$65$lambda$64(GxThemePage.this, $tv, (String) obj);
                return renderTurn$lambda$65$lambda$64;
            }
        }, 16, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderTurn$lambda$65$lambda$64(GxThemePage this$0, TextView $tv, String s) {
        Intrinsics.checkNotNullParameter(s, "s");
        Integer intOrNull = StringsKt.toIntOrNull(StringsKt.trim((CharSequence) s).toString());
        renderTurn$setLen(this$0, $tv, intOrNull != null ? intOrNull.intValue() : 100);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderTurn$lambda$66(GxThemePage this$0, TextView $tv) {
        renderTurn$setLen(this$0, $tv, this$0.tsLength - 25);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderTurn$lambda$67(GxThemePage this$0, TextView $tv) {
        renderTurn$setLen(this$0, $tv, this$0.tsLength + 25);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderTurn$lambda$68(GxThemePage this$0) {
        this$0.choose("__sequential__", true);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderTurn$lambda$69(GxThemePage this$0) {
        this$0.clearSlot("turnSignal");
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderTurn$lambda$71(GxThemePage this$0, int $i) {
        Collections.swap(this$0.frames, $i, $i - 1);
        this$0.framesName = "";
        this$0.changed("turn_signals");
        this$0.renderEditor();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderTurn$lambda$72(GxThemePage this$0, int $i) {
        Collections.swap(this$0.frames, $i, $i + 1);
        this$0.framesName = "";
        this$0.changed("turn_signals");
        this$0.renderEditor();
        return Unit.INSTANCE;
    }

    private final void renderChecklist() {
        List<String> list;
        Map map;
        LinearLayout linearLayout = this.checkBox;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("checkBox");
            linearLayout = null;
        }
        linearLayout.removeAllViews();
        LinearLayout bar = new LinearLayout(getAct());
        bar.setOrientation(0);
        bar.setPadding(0, getUi().dp(6), 0, getUi().dp(8));
        list = GxThemeKt.GXTHEME_COMPS;
        for (final String c : list) {
            if (hasContent(c)) {
                final boolean on = Intrinsics.areEqual((Object) this.flags.get(c), (Object) true);
                TextView $this$renderChecklist_u24lambda_u2475 = new TextView(getAct());
                String str = on ? "✓  " : "";
                map = GxThemeKt.GXTHEME_COMP_LABELS;
                $this$renderChecklist_u24lambda_u2475.setText(str + map.get(c));
                $this$renderChecklist_u24lambda_u2475.setTextSize(18.0f);
                $this$renderChecklist_u24lambda_u2475.setGravity(17);
                $this$renderChecklist_u24lambda_u2475.setMinHeight(getUi().dp(56));
                $this$renderChecklist_u24lambda_u2475.setPadding(getUi().dp(18), getUi().dp(8), getUi().dp(18), getUi().dp(8));
                $this$renderChecklist_u24lambda_u2475.setBackground(getUi().rounded(on ? CarUi.Companion.getCARD_ON() : Color.rgb(16, 18, 24), 12, Integer.valueOf(on ? CarUi.Companion.getACCENT() : Color.rgb(50, 55, 66))));
                CarUi.Companion companion = CarUi.Companion;
                $this$renderChecklist_u24lambda_u2475.setTextColor(on ? companion.getACCENT() : companion.getTEXT());
                $this$renderChecklist_u24lambda_u2475.setTypeface(on ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
                $this$renderChecklist_u24lambda_u2475.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.GxThemePage$$ExternalSyntheticLambda18
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        GxThemePage.renderChecklist$lambda$75$lambda$74(GxThemePage.this, c, on, view);
                    }
                });
                LinearLayout.LayoutParams $this$renderChecklist_u24lambda_u2476 = new LinearLayout.LayoutParams(-2, -2);
                $this$renderChecklist_u24lambda_u2476.rightMargin = getUi().dp(10);
                Unit unit = Unit.INSTANCE;
                bar.addView($this$renderChecklist_u24lambda_u2475, $this$renderChecklist_u24lambda_u2476);
            }
        }
        LinearLayout linearLayout2 = this.checkBox;
        if (linearLayout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("checkBox");
            linearLayout2 = null;
        }
        HorizontalScrollView $this$renderChecklist_u24lambda_u2477 = new HorizontalScrollView(getAct());
        $this$renderChecklist_u24lambda_u2477.setHorizontalScrollBarEnabled(false);
        $this$renderChecklist_u24lambda_u2477.addView(bar);
        linearLayout2.addView($this$renderChecklist_u24lambda_u2477);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void renderChecklist$lambda$75$lambda$74(GxThemePage this$0, String $c, boolean $on, View it) {
        this$0.flags.put($c, Boolean.valueOf(!$on));
        this$0.renderChecklist();
    }

    private final void paintDiscord() {
        String it;
        if (this.discordRow != null) {
            GxPage.Row row = this.discordRow;
            String str = null;
            if (row == null) {
                Intrinsics.throwUninitializedPropertyAccessException("discordRow");
                row = null;
            }
            String str2 = this.discord;
            if (!StringsKt.isBlank(str2)) {
                str = str2;
            }
            String it2 = str;
            if (it2 == null || (it = it2 + " - used when you submit a theme") == null) {
                it = "Required to submit a theme, so we can contact you if needed.";
            }
            row.setDesc(it);
        }
    }

    private final void choose(final String slot, final boolean image) {
        GxHost gx = getGx();
        String[] strArr = new String[1];
        strArr[0] = image ? "image/*" : "audio/*";
        gx.pickFile(strArr, new Function1() { // from class: com.starpilot.starview.GxThemePage$$ExternalSyntheticLambda44
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit choose$lambda$83;
                choose$lambda$83 = GxThemePage.choose$lambda$83(GxThemePage.this, image, slot, (Uri) obj);
                return choose$lambda$83;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit choose$lambda$83(final GxThemePage this$0, final boolean $image, final String $slot, Uri uri) {
        Object m1191constructorimpl;
        if (uri == null) {
            return Unit.INSTANCE;
        }
        try {
            Result.Companion companion = Result.Companion;
            m1191constructorimpl = Result.m1191constructorimpl(this$0.getAct().getContentResolver().getType(uri));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            m1191constructorimpl = Result.m1191constructorimpl(ResultKt.createFailure(th));
        }
        if (Result.m1197isFailureimpl(m1191constructorimpl)) {
            m1191constructorimpl = null;
        }
        final String typed = (String) m1191constructorimpl;
        this$0.readUri(uri, new Function2() { // from class: com.starpilot.starview.GxThemePage$$ExternalSyntheticLambda12
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit choose$lambda$83$lambda$82;
                choose$lambda$83$lambda$82 = GxThemePage.choose$lambda$83$lambda$82(GxThemePage.this, typed, $image, $slot, (byte[]) obj, (String) obj2);
                return choose$lambda$83$lambda$82;
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x002e, code lost:
    
        if (r2 == null) goto L17;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Unit choose$lambda$83$lambda$82(GxThemePage this$0, String $typed, boolean $image, String $slot, byte[] bytes, String name) {
        String mime;
        Intrinsics.checkNotNullParameter(name, "name");
        if (bytes == null) {
            this$0.toast("Could not read that file.");
            return Unit.INSTANCE;
        }
        if (bytes.length > 5242880) {
            this$0.toast("That file is too large! Please upload files under 5MB.");
            return Unit.INSTANCE;
        }
        if ($typed != null) {
            String str = $typed;
            if (StringsKt.isBlank(str)) {
                str = null;
            }
            mime = str;
        }
        mime = this$0.guessMime(name);
        if (mime == null) {
            mime = "";
        }
        if ($image && !StringsKt.startsWith$default(mime, "image/", false, 2, (Object) null)) {
            this$0.toast("Invalid file type! Please upload an image file.");
            return Unit.INSTANCE;
        }
        if (!$image && !StringsKt.startsWith$default(mime, "audio/", false, 2, (Object) null)) {
            this$0.toast("Invalid file type! Please upload an audio file.");
            return Unit.INSTANCE;
        }
        String comp = this$0.compOf($slot);
        if (Intrinsics.areEqual($slot, "__sequential__")) {
            if (Intrinsics.areEqual(mime, "image/gif")) {
                this$0.toast("GIFs are not supported for sequential turn signals...");
                return Unit.INSTANCE;
            }
            if (!StringsKt.isBlank(this$0.framesName)) {
                this$0.framesName = "";
            }
            this$0.frames.add(new GxThemeFile(this$0.withExt(name, mime), bytes, mime));
        } else {
            this$0.files.put($slot, new GxThemeFile(this$0.withExt(name, mime), bytes, mime));
            this$0.names.put($slot, name);
        }
        this$0.flags.put(comp, true);
        this$0.changed(comp);
        this$0.renderEditor();
        return Unit.INSTANCE;
    }

    private final void clearSlot(String slot) {
        changed(compOf(slot));
        if (Intrinsics.areEqual(slot, "turnSignal") && Intrinsics.areEqual(this.tsType, "Sequential")) {
            this.frames.clear();
            this.framesName = "";
        }
        this.files.remove(slot);
        this.names.remove(slot);
        renderEditor();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private final void clearComp(String comp) {
        List<String> slots;
        Iterable iterable;
        this.sources.put(comp, null);
        switch (comp.hashCode()) {
            case -1354842768:
                if (comp.equals("colors")) {
                    resetColors();
                    slots = CollectionsKt.emptyList();
                    break;
                }
                slots = CollectionsKt.emptyList();
                break;
            case -896509628:
                if (comp.equals("sounds")) {
                    slots = GxThemeKt.GXTHEME_SOUNDS;
                    break;
                }
                slots = CollectionsKt.emptyList();
                break;
            case -217663920:
                if (comp.equals("distance_icons")) {
                    iterable = GxThemeKt.GXTHEME_DISTANCE;
                    Iterable $this$map$iv = iterable;
                    Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                    for (Object item$iv$iv : $this$map$iv) {
                        String it = (String) item$iv$iv;
                        destination$iv$iv.add("distanceIcons_" + it);
                    }
                    slots = (List) destination$iv$iv;
                    break;
                }
                slots = CollectionsKt.emptyList();
                break;
            case 100029210:
                if (comp.equals("icons")) {
                    slots = CollectionsKt.listOf((Object[]) new String[]{"homeButton", "settingsButton"});
                    break;
                }
                slots = CollectionsKt.emptyList();
                break;
            case 271106313:
                if (comp.equals("turn_signals")) {
                    this.frames.clear();
                    this.framesName = "";
                    slots = CollectionsKt.listOf((Object[]) new String[]{"turnSignal", "turnSignalBlindspot"});
                    break;
                }
                slots = CollectionsKt.emptyList();
                break;
            case 2119462413:
                if (comp.equals("steering_wheel")) {
                    slots = CollectionsKt.listOf("steeringWheel");
                    break;
                }
                slots = CollectionsKt.emptyList();
                break;
            default:
                slots = CollectionsKt.emptyList();
                break;
        }
        for (String s : slots) {
            this.files.remove(s);
            this.names.remove(s);
        }
    }

    private final Pair<LinkedHashMap<String, String>, List<Triple<String, String, Pair<byte[], String>>>> form() {
        List<String> list;
        List<String> list2;
        List<String> list3;
        List<String> list4;
        LinkedHashMap f = new LinkedHashMap();
        JSONObject list5 = new JSONObject();
        list = GxThemeKt.GXTHEME_COMPS;
        for (String c : list) {
            list5.put(c, checked(c));
        }
        JSONObject src = new JSONObject();
        list2 = GxThemeKt.GXTHEME_COMPS;
        for (String c2 : list2) {
            JSONObject jSONObject = this.sources.get(c2);
            if (jSONObject == null) {
                jSONObject = JSONObject.NULL;
            }
            src.put(c2, jSONObject);
        }
        f.put("themeName", StringsKt.trim((CharSequence) this.themeName).toString());
        f.put("saveChecklist", list5.toString());
        f.put("selectedThemeSources", src.toString());
        if (!StringsKt.isBlank(this.discord)) {
            f.put("discordUsername", StringsKt.trim((CharSequence) this.discord).toString());
        }
        if (checked("colors")) {
            f.put("colors", colorsJson().toString());
        }
        ArrayList parts = new ArrayList();
        if (checked("turn_signals")) {
            f.put("turnSignalStyle", this.tsStyle);
            f.put("turnSignalType", this.tsType);
            f.put("turnSignalLength", String.valueOf(this.tsLength));
            if (Intrinsics.areEqual(this.tsType, "Sequential")) {
                int i = 0;
                for (GxThemeFile fr : this.frames) {
                    int i2 = i;
                    i++;
                    form$add(parts, "turn_signal_" + (i2 + 1), fr);
                }
            }
            form$add(parts, "turnSignal", this.files.get("turnSignal"));
            form$add(parts, "turnSignalBlindspot", this.files.get("turnSignalBlindspot"));
        }
        if (checked("icons")) {
            form$add(parts, "homeButton", this.files.get("homeButton"));
            form$add(parts, "settingsButton", this.files.get("settingsButton"));
        }
        if (checked("steering_wheel")) {
            form$add(parts, "steeringWheel", this.files.get("steeringWheel"));
        }
        if (checked("distance_icons")) {
            list4 = GxThemeKt.GXTHEME_DISTANCE;
            for (String k : list4) {
                form$add(parts, "distanceIcons_" + k, this.files.get("distanceIcons_" + k));
            }
        }
        if (checked("sounds")) {
            list3 = GxThemeKt.GXTHEME_SOUNDS;
            for (String k2 : list3) {
                form$add(parts, k2, this.files.get(k2));
            }
        }
        return TuplesKt.to(f, parts);
    }

    private static final void form$add(ArrayList<Triple<String, String, Pair<byte[], String>>> arrayList, String field, GxThemeFile file) {
        if (file != null) {
            String fileName = file.getFileName();
            byte[] bytes = file.getBytes();
            String mime = file.getMime();
            if (StringsKt.isBlank(mime)) {
                mime = "application/octet-stream";
            }
            arrayList.add(new Triple<>(field, fileName, TuplesKt.to(bytes, mime)));
        }
    }

    private final void save() {
        if (this.busy) {
            return;
        }
        if (StringsKt.isBlank(this.themeName) || !anyChecked()) {
            toast("Enter a theme name and select at least one component.");
            return;
        }
        setBusy(true);
        Pair<LinkedHashMap<String, String>, List<Triple<String, String, Pair<byte[], String>>>> form = form();
        LinkedHashMap f = form.component1();
        List p = form.component2();
        upload("/api/themes", p, f, new Function2() { // from class: com.starpilot.starview.GxThemePage$$ExternalSyntheticLambda33
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit save$lambda$86;
                save$lambda$86 = GxThemePage.save$lambda$86(GxThemePage.this, (String) obj, (JSONObject) obj2);
                return save$lambda$86;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit save$lambda$86(GxThemePage this$0, String e, JSONObject o) {
        String str;
        this$0.setBusy(false);
        if (e != null) {
            this$0.toast(e);
            return Unit.INSTANCE;
        }
        if (o == null || (str = this$0.msg(o)) == null) {
            str = "Theme saved!";
        }
        this$0.toast(str);
        this$0.themeName = "";
        EditText editText = this$0.nameEdit;
        if (editText == null) {
            Intrinsics.throwUninitializedPropertyAccessException("nameEdit");
            editText = null;
        }
        editText.setText("");
        this$0.refreshThemeList();
        return Unit.INSTANCE;
    }

    private final void apply() {
        if (this.busy) {
            return;
        }
        if (anyChecked()) {
            confirm("Apply theme?", "This makes the current colors/assets the active theme on device. It does not save them as a named theme.", "Apply", new Function0() { // from class: com.starpilot.starview.GxThemePage$$ExternalSyntheticLambda62
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit apply$lambda$88;
                    apply$lambda$88 = GxThemePage.apply$lambda$88(GxThemePage.this);
                    return apply$lambda$88;
                }
            });
        } else {
            toast("Select at least one component to apply.");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit apply$lambda$88(final GxThemePage this$0) {
        this$0.setBusy(true);
        Pair<LinkedHashMap<String, String>, List<Triple<String, String, Pair<byte[], String>>>> form = this$0.form();
        LinkedHashMap f = form.component1();
        List p = form.component2();
        this$0.upload("/api/themes/apply", p, f, new Function2() { // from class: com.starpilot.starview.GxThemePage$$ExternalSyntheticLambda55
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit apply$lambda$88$lambda$87;
                apply$lambda$88$lambda$87 = GxThemePage.apply$lambda$88$lambda$87(GxThemePage.this, (String) obj, (JSONObject) obj2);
                return apply$lambda$88$lambda$87;
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit apply$lambda$88$lambda$87(GxThemePage this$0, String e, JSONObject o) {
        String str;
        this$0.setBusy(false);
        if (e == null) {
            str = o != null ? this$0.msg(o) : null;
            if (str == null) {
                str = "Theme applied!";
            }
        } else {
            str = e;
        }
        this$0.toast(str);
        return Unit.INSTANCE;
    }

    private final void submit() {
        Iterable iterable;
        Iterable $this$all$iv;
        if (this.busy) {
            return;
        }
        if (this.submitted) {
            toast("This theme has already been submitted...");
            return;
        }
        boolean z = false;
        if (this.files.isEmpty() && this.frames.isEmpty()) {
            Iterable values = this.names.values();
            Intrinsics.checkNotNullExpressionValue(values, "<get-values>(...)");
            Iterable $this$all$iv2 = values;
            if (!((Collection) $this$all$iv2).isEmpty()) {
                Iterator it = $this$all$iv2.iterator();
                while (true) {
                    if (it.hasNext()) {
                        Object element$iv = it.next();
                        String it2 = (String) element$iv;
                        Intrinsics.checkNotNull(it2);
                        if (!StringsKt.isBlank(it2)) {
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
            if ($this$all$iv != null && StringsKt.isBlank(this.framesName)) {
                z = true;
            }
        }
        boolean empty = z;
        if (empty) {
            toast("Cannot submit an empty theme...");
            return;
        }
        if (StringsKt.isBlank(this.discord)) {
            toast("Discord username is required for submission...");
            return;
        }
        if (StringsKt.isBlank(this.themeName)) {
            toast("Please enter a theme name...");
            return;
        }
        if (!anyChecked()) {
            toast("Please select at least one component to submit!");
            return;
        }
        iterable = GxThemeKt.GXTHEME_COMPS;
        Iterable $this$filter$iv = iterable;
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            if (checked((String) element$iv$iv)) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        String comps = CollectionsKt.joinToString$default((List) destination$iv$iv, ", ", null, null, 0, null, new Function1() { // from class: com.starpilot.starview.GxThemePage$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                CharSequence submit$lambda$91;
                submit$lambda$91 = GxThemePage.submit$lambda$91((String) obj);
                return submit$lambda$91;
            }
        }, 30, null);
        confirm("Submit Theme for Community Use", "Submit your theme for everyone to use!\n\nTheme: " + StringsKt.trim((CharSequence) this.themeName).toString() + "\nDiscord: " + this.discord + "\nComponents: " + comps, "Submit", new Function0() { // from class: com.starpilot.starview.GxThemePage$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit submit$lambda$93;
                submit$lambda$93 = GxThemePage.submit$lambda$93(GxThemePage.this);
                return submit$lambda$93;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CharSequence submit$lambda$91(String it) {
        Map map;
        Intrinsics.checkNotNullParameter(it, "it");
        map = GxThemeKt.GXTHEME_COMP_LABELS;
        String str = (String) map.get(it);
        return str != null ? str : it;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit submit$lambda$93(final GxThemePage this$0) {
        this$0.setBusy(true);
        Pair<LinkedHashMap<String, String>, List<Triple<String, String, Pair<byte[], String>>>> form = this$0.form();
        LinkedHashMap f = form.component1();
        List p = form.component2();
        this$0.upload("/api/themes/submit", p, f, new Function2() { // from class: com.starpilot.starview.GxThemePage$$ExternalSyntheticLambda65
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit submit$lambda$93$lambda$92;
                submit$lambda$93$lambda$92 = GxThemePage.submit$lambda$93$lambda$92(GxThemePage.this, (String) obj, (JSONObject) obj2);
                return submit$lambda$93$lambda$92;
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit submit$lambda$93$lambda$92(GxThemePage this$0, String e, JSONObject o) {
        String str;
        this$0.setBusy(false);
        if (e != null) {
            this$0.toast(e);
            return Unit.INSTANCE;
        }
        if (o == null || (str = this$0.msg(o)) == null) {
            str = "Theme submitted successfully!";
        }
        this$0.toast(str);
        this$0.submitted = true;
        this$0.themeName = "";
        EditText editText = this$0.nameEdit;
        if (editText == null) {
            Intrinsics.throwUninitializedPropertyAccessException("nameEdit");
            editText = null;
        }
        editText.setText("");
        return Unit.INSTANCE;
    }

    private final void exportTheme() {
        if (this.busy) {
            return;
        }
        if (StringsKt.isBlank(this.themeName)) {
            toast("Enter a theme name before exporting.");
            return;
        }
        if (!anyChecked()) {
            toast("Select at least one component to export.");
            return;
        }
        final String h = getHost();
        if (h == null) {
            return;
        }
        setBusy(true);
        Pair<LinkedHashMap<String, String>, List<Triple<String, String, Pair<byte[], String>>>> form = form();
        LinkedHashMap fields = form.component1();
        List parts = form.component2();
        final String fileName = new Regex("\\s+").replace(StringsKt.trim((CharSequence) this.themeName).toString(), "_") + ".zip";
        MultipartBody.Builder b = new MultipartBody.Builder(null, 1, null).setType(MultipartBody.FORM);
        for (Map.Entry<String, String> entry : fields.entrySet()) {
            String k = entry.getKey();
            String v = entry.getValue();
            b.addFormDataPart(k, v);
        }
        for (Triple<String, String, Pair<byte[], String>> triple : parts) {
            String field = triple.component1();
            String name = triple.component2();
            Pair data = triple.component3();
            b.addFormDataPart(field, name, RequestBody.Companion.create$default(RequestBody.Companion, data.getFirst(), MediaType.Companion.get(data.getSecond()), 0, 0, 6, (Object) null));
        }
        final MultipartBody body = b.build();
        new Thread(new Runnable() { // from class: com.starpilot.starview.GxThemePage$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                GxThemePage.exportTheme$lambda$102(h, body, fileName, this);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x00b5, code lost:
    
        if (r8 == null) goto L32;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v25, types: [T, java.lang.String] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void exportTheme$lambda$102(String str, MultipartBody multipartBody, String str2, final GxThemePage gxThemePage) {
        Response execute;
        Response response;
        String str3;
        InputStream byteStream;
        String str4;
        Object m1191constructorimpl;
        String str5;
        String str6 = "Failed to export theme.";
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        try {
        } catch (Exception e) {
            e = e;
        }
        try {
            execute = GxPage.Companion.client(300L).newCall(new Request.Builder().url("http://" + str + ":8082/api/themes/download").post(multipartBody).build()).execute();
            try {
                response = execute;
                str3 = null;
            } finally {
            }
        } catch (Exception e2) {
            e = e2;
            String message = e.getMessage();
            T t = str6;
            if (message != null) {
                t = message;
            }
            objectRef.element = t;
            gxThemePage.getMain().post(new Runnable() { // from class: com.starpilot.starview.GxThemePage$$ExternalSyntheticLambda53
                @Override // java.lang.Runnable
                public final void run() {
                    GxThemePage.exportTheme$lambda$102$lambda$101(GxThemePage.this, objectRef);
                }
            });
            return;
        }
        if (response.isSuccessful()) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("_display_name", str2);
            contentValues.put("mime_type", "application/zip");
            contentValues.put("relative_path", "Download/StarView");
            Uri insert = gxThemePage.getAct().getContentResolver().insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues);
            if (insert == null) {
                throw new IllegalStateException("cannot create file");
            }
            OutputStream openOutputStream = gxThemePage.getAct().getContentResolver().openOutputStream(insert);
            if (openOutputStream != null) {
                OutputStream outputStream = openOutputStream;
                try {
                    OutputStream outputStream2 = outputStream;
                    ResponseBody body = response.body();
                    if (body != null && (byteStream = body.byteStream()) != null) {
                        Long.valueOf(ByteStreamsKt.copyTo$default(byteStream, outputStream2, 0, 2, null));
                    }
                    CloseableKt.closeFinally(outputStream, null);
                } finally {
                }
            }
            CloseableKt.closeFinally(execute, null);
            objectRef.element = "Theme exported as a zip: Downloads/StarView/" + str2;
            gxThemePage.getMain().post(new Runnable() { // from class: com.starpilot.starview.GxThemePage$$ExternalSyntheticLambda53
                @Override // java.lang.Runnable
                public final void run() {
                    GxThemePage.exportTheme$lambda$102$lambda$101(GxThemePage.this, objectRef);
                }
            });
            return;
        }
        ResponseBody body2 = response.body();
        if (body2 == null || (str4 = body2.string()) == null) {
            str4 = "";
        }
        String str7 = str4;
        try {
            Result.Companion companion = Result.Companion;
            JSONObject jSONObject = new JSONObject(str7);
            String optString = jSONObject.optString("message");
            if (StringsKt.isBlank(optString)) {
                optString = jSONObject.optString("error");
            }
            m1191constructorimpl = Result.m1191constructorimpl(optString);
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            m1191constructorimpl = Result.m1191constructorimpl(ResultKt.createFailure(th));
        }
        if (Result.m1197isFailureimpl(m1191constructorimpl)) {
            m1191constructorimpl = null;
        }
        String str8 = (String) m1191constructorimpl;
        if (str8 != null) {
            String str9 = str8;
            if (!StringsKt.isBlank(str9)) {
                str3 = str9;
            }
            str5 = str3;
        }
        str5 = "Failed to export theme.";
        throw new IllegalStateException(str5);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static final void exportTheme$lambda$102$lambda$101(GxThemePage this$0, Ref.ObjectRef $msg) {
        this$0.setBusy(false);
        this$0.toast((String) $msg.element);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void refreshThemeList() {
        if (this.themes.isEmpty()) {
            this.loading = true;
            renderLibrary();
        }
        getJson("/api/themes/list", new Function2() { // from class: com.starpilot.starview.GxThemePage$$ExternalSyntheticLambda63
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit refreshThemeList$lambda$103;
                refreshThemeList$lambda$103 = GxThemePage.refreshThemeList$lambda$103(GxThemePage.this, (String) obj, (JSONObject) obj2);
                return refreshThemeList$lambda$103;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit refreshThemeList$lambda$103(GxThemePage this$0, String e, JSONObject o) {
        this$0.loading = false;
        if (e != null || o == null) {
            this$0.loadError = "Failed to load themes.";
        } else {
            this$0.loadError = "";
            JSONArray optJSONArray = o.optJSONArray("themes");
            List<JSONObject> objects = optJSONArray != null ? this$0.objects(optJSONArray) : null;
            if (objects == null) {
                objects = CollectionsKt.emptyList();
            }
            this$0.rawThemes = objects;
        }
        this$0.merge();
        this$0.renderLibrary();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void fetchDownloadables() {
        Map map;
        Map map2;
        final Ref.IntRef left = new Ref.IntRef();
        map = GxThemeKt.GXTHEME_BUCKET;
        left.element = map.size();
        map2 = GxThemeKt.GXTHEME_BUCKET;
        for (Map.Entry entry : map2.entrySet()) {
            final String c = (String) entry.getKey();
            String key = (String) entry.getValue();
            getText("/api/params?key=" + key, new Function2() { // from class: com.starpilot.starview.GxThemePage$$ExternalSyntheticLambda9
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    Unit fetchDownloadables$lambda$106;
                    fetchDownloadables$lambda$106 = GxThemePage.fetchDownloadables$lambda$106(GxThemePage.this, c, left, (String) obj, (String) obj2);
                    return fetchDownloadables$lambda$106;
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit fetchDownloadables$lambda$106(GxThemePage this$0, String $c, Ref.IntRef $left, String e, String t) {
        Intrinsics.checkNotNullParameter(t, "t");
        if (e == null) {
            HashMap<String, List<String>> hashMap = this$0.downloadable;
            Iterable $this$map$iv = StringsKt.split$default((CharSequence) t, new String[]{","}, false, 0, 6, (Object) null);
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            for (Object item$iv$iv : $this$map$iv) {
                String it = (String) item$iv$iv;
                destination$iv$iv.add(StringsKt.trim((CharSequence) it).toString());
            }
            Iterable $this$filter$iv = (List) destination$iv$iv;
            Collection destination$iv$iv2 = new ArrayList();
            for (Object element$iv$iv : $this$filter$iv) {
                String it2 = (String) element$iv$iv;
                if (it2.length() > 0) {
                    destination$iv$iv2.add(element$iv$iv);
                }
            }
            hashMap.put($c, (List) destination$iv$iv2);
        }
        $left.element--;
        if ($left.element == 0) {
            this$0.merge();
            this$0.renderLibrary();
        }
        return Unit.INSTANCE;
    }

    private final void merge() {
        List list;
        List<String> list2;
        Map map;
        ArrayList list3 = new ArrayList();
        HashMap byName = new HashMap();
        for (JSONObject o : this.rawThemes) {
            GxThemeEntry t = new GxThemeEntry(GxPage.str$default(this, o, "name", null, 2, null), GxPage.str$default(this, o, "path", null, 2, null), GxPage.str$default(this, o, "type", null, 2, null), o.optBoolean("is_user_created"), false, false, 48, null);
            list2 = GxThemeKt.GXTHEME_COMPS;
            for (String c : list2) {
                map = GxThemeKt.GXTHEME_COMP_FLAG;
                String str = (String) map.get(c);
                if (str == null) {
                    str = "";
                }
                boolean v = o.optBoolean(str);
                t.getHas().put(c, Boolean.valueOf(v));
                t.getLocal().put(c, Boolean.valueOf(v));
            }
            list3.add(t);
            byName.put(norm(t.getName()), t);
        }
        list = GxThemeKt.GXTHEME_COMPS;
        Iterator it = list.iterator();
        while (it.hasNext()) {
            String c2 = (String) it.next();
            List<String> list4 = this.downloadable.get(c2);
            if (list4 == null) {
                list4 = CollectionsKt.emptyList();
            }
            for (String n : list4) {
                String k = norm(n);
                GxThemeEntry t2 = (GxThemeEntry) byName.get(k);
                if (t2 == null) {
                    GxThemeEntry nt = new GxThemeEntry(n, n, "holiday", false, true, false, 32, null);
                    nt.getHas().put(c2, true);
                    list3.add(nt);
                    byName.put(k, nt);
                    it = it;
                } else {
                    t2.getHas().put(c2, true);
                    it = it;
                }
            }
        }
        final Comparator<String> case_insensitive_order = StringsKt.getCASE_INSENSITIVE_ORDER(StringCompanionObject.INSTANCE);
        this.themes = CollectionsKt.sortedWith(list3, new Comparator() { // from class: com.starpilot.starview.GxThemePage$merge$$inlined$compareBy$1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.Comparator
            public final int compare(T t3, T t4) {
                GxThemeEntry it2 = (GxThemeEntry) t3;
                GxThemeEntry it3 = (GxThemeEntry) t4;
                return case_insensitive_order.compare(it2.getName(), it3.getName());
            }
        });
    }

    private final boolean isDownloadable(String comp, String name) {
        Iterable iterable = (List) this.downloadable.get(comp);
        if (iterable == null) {
            iterable = CollectionsKt.emptyList();
        }
        Iterable $this$any$iv = iterable;
        if (($this$any$iv instanceof Collection) && ((Collection) $this$any$iv).isEmpty()) {
            return false;
        }
        for (Object element$iv : $this$any$iv) {
            String it = (String) element$iv;
            if (Intrinsics.areEqual(norm(it), norm(name))) {
                return true;
            }
        }
        return false;
    }

    private final void renderLibrary() {
        Iterable iterable;
        List list;
        LinearLayout linearLayout;
        Map map;
        if (this.libBox == null) {
            return;
        }
        LinearLayout linearLayout2 = this.libBar;
        if (linearLayout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("libBar");
            linearLayout2 = null;
        }
        linearLayout2.removeAllViews();
        List listOf = CollectionsKt.listOf("All");
        iterable = GxThemeKt.GXTHEME_COMPS;
        Iterable $this$map$iv = iterable;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        for (Object item$iv$iv : $this$map$iv) {
            String it = (String) item$iv$iv;
            map = GxThemeKt.GXTHEME_COMP_LABELS;
            String str = (String) map.get(it);
            if (str != null) {
                it = str;
            }
            destination$iv$iv.add(it);
        }
        List labels = CollectionsKt.plus((Collection) listOf, destination$iv$iv);
        Iterator it2 = labels.iterator();
        int i = 0;
        while (true) {
            if (!it2.hasNext()) {
                break;
            }
            final int i2 = i;
            i++;
            String l = (String) it2.next();
            boolean on = i2 == this.libTab;
            LinearLayout linearLayout3 = this.libBar;
            if (linearLayout3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("libBar");
                linearLayout3 = null;
            }
            TextView $this$renderLibrary_u24lambda_u24111 = new TextView(getAct());
            $this$renderLibrary_u24lambda_u24111.setText(l);
            $this$renderLibrary_u24lambda_u24111.setTextSize(17.0f);
            $this$renderLibrary_u24lambda_u24111.setMinHeight(getUi().dp(52));
            $this$renderLibrary_u24lambda_u24111.setGravity(17);
            List labels2 = labels;
            $this$renderLibrary_u24lambda_u24111.setPadding(getUi().dp(18), getUi().dp(8), getUi().dp(18), getUi().dp(8));
            CarUi ui = getUi();
            CarUi.Companion companion = CarUi.Companion;
            $this$renderLibrary_u24lambda_u24111.setBackground(ui.rounded(on ? companion.getCARD_ON() : companion.getCARD(), 12, on ? Integer.valueOf(CarUi.Companion.getACCENT()) : null));
            CarUi.Companion companion2 = CarUi.Companion;
            $this$renderLibrary_u24lambda_u24111.setTextColor(on ? companion2.getACCENT() : companion2.getTEXT());
            $this$renderLibrary_u24lambda_u24111.setTypeface(on ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
            $this$renderLibrary_u24lambda_u24111.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.GxThemePage$$ExternalSyntheticLambda57
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    GxThemePage.renderLibrary$lambda$111$lambda$110(GxThemePage.this, i2, view);
                }
            });
            LinearLayout.LayoutParams $this$renderLibrary_u24lambda_u24112 = new LinearLayout.LayoutParams(-2, -2);
            $this$renderLibrary_u24lambda_u24112.rightMargin = getUi().dp(8);
            Unit unit = Unit.INSTANCE;
            linearLayout3.addView($this$renderLibrary_u24lambda_u24111, $this$renderLibrary_u24lambda_u24112);
            labels = labels2;
        }
        LinearLayout linearLayout4 = this.libBox;
        if (linearLayout4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("libBox");
            linearLayout4 = null;
        }
        linearLayout4.removeAllViews();
        if (this.loading) {
            LinearLayout linearLayout5 = this.libBox;
            if (linearLayout5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("libBox");
                linearLayout = null;
            } else {
                linearLayout = linearLayout5;
            }
            linearLayout.addView(empty("Loading themes..."));
            return;
        }
        if (!StringsKt.isBlank(this.loadError)) {
            String str2 = this.loadError;
            LinearLayout linearLayout6 = this.libBox;
            if (linearLayout6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("libBox");
                linearLayout6 = null;
            }
            note(str2, linearLayout6).setTextColor(CarUi.Companion.getWARN());
            return;
        }
        if (this.libTab != 0) {
            list = GxThemeKt.GXTHEME_COMPS;
            renderComp((String) list.get(this.libTab - 1));
        } else {
            renderAll();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void renderLibrary$lambda$111$lambda$110(GxThemePage this$0, int $i, View it) {
        this$0.libTab = $i;
        this$0.renderLibrary();
    }

    private final String titleOf(GxThemeEntry t) {
        return t.getName() + (t.getUserCreated() ? "  ★" : "");
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x008f  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00e1  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00ea  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0123 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0053 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final void renderAll() {
        List<String> list;
        String joinToString$default;
        LinearLayout linearLayout;
        String lowerCase;
        Map map;
        String str;
        Iterable $this$filter$iv = this.themes;
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            GxThemeEntry it = (GxThemeEntry) element$iv$iv;
            if (!it.getPseudo()) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        List<GxThemeEntry> list2 = (List) destination$iv$iv;
        LinearLayout linearLayout2 = null;
        if (list2.isEmpty()) {
            LinearLayout linearLayout3 = this.libBox;
            if (linearLayout3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("libBox");
            } else {
                linearLayout2 = linearLayout3;
            }
            linearLayout2.addView(empty("No themes yet. Build one above and hit Save Theme."));
            return;
        }
        for (final GxThemeEntry t : list2) {
            ArrayList chips = new ArrayList();
            String type = t.getType();
            if (!Intrinsics.areEqual(type, "holiday")) {
                str = Intrinsics.areEqual(type, "steering_wheel") ? "Wheel" : "Holiday";
                list = GxThemeKt.GXTHEME_COMPS;
                for (String c : list) {
                    if (Intrinsics.areEqual((Object) t.getLocal().get(c), (Object) true)) {
                        map = GxThemeKt.GXTHEME_COMP_LABELS;
                        String str2 = (String) map.get(c);
                        if (str2 == null) {
                            str2 = c;
                        }
                        chips.add(str2);
                    }
                }
                GxThemePage gxThemePage = this;
                String titleOf = titleOf(t);
                joinToString$default = CollectionsKt.joinToString$default(chips, " · ", null, null, 0, null, null, 62, null);
                if (StringsKt.isBlank(joinToString$default)) {
                    joinToString$default = null;
                }
                String str3 = joinToString$default;
                linearLayout = this.libBox;
                if (linearLayout == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("libBox");
                    linearLayout = null;
                }
                GxPage.Row r = GxPage.row$default(gxThemePage, titleOf, str3, linearLayout, 0, 8, null);
                GxPage.Row.button$default(r, "Load", false, new Function0() { // from class: com.starpilot.starview.GxThemePage$$ExternalSyntheticLambda39
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        Unit renderAll$lambda$115;
                        renderAll$lambda$115 = GxThemePage.renderAll$lambda$115(GxThemePage.this, t);
                        return renderAll$lambda$115;
                    }
                }, 2, null);
                lowerCase = t.getType().toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
                if (Intrinsics.areEqual(lowerCase, "holiday")) {
                    GxPage.Row.button$default(r, "Delete", false, new Function0() { // from class: com.starpilot.starview.GxThemePage$$ExternalSyntheticLambda40
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            Unit renderAll$lambda$116;
                            renderAll$lambda$116 = GxThemePage.renderAll$lambda$116(GxThemePage.this, t);
                            return renderAll$lambda$116;
                        }
                    }, 2, null);
                }
            }
            chips.add(str);
            list = GxThemeKt.GXTHEME_COMPS;
            while (r7.hasNext()) {
            }
            GxThemePage gxThemePage2 = this;
            String titleOf2 = titleOf(t);
            joinToString$default = CollectionsKt.joinToString$default(chips, " · ", null, null, 0, null, null, 62, null);
            if (StringsKt.isBlank(joinToString$default)) {
            }
            String str32 = joinToString$default;
            linearLayout = this.libBox;
            if (linearLayout == null) {
            }
            GxPage.Row r2 = GxPage.row$default(gxThemePage2, titleOf2, str32, linearLayout, 0, 8, null);
            GxPage.Row.button$default(r2, "Load", false, new Function0() { // from class: com.starpilot.starview.GxThemePage$$ExternalSyntheticLambda39
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit renderAll$lambda$115;
                    renderAll$lambda$115 = GxThemePage.renderAll$lambda$115(GxThemePage.this, t);
                    return renderAll$lambda$115;
                }
            }, 2, null);
            lowerCase = t.getType().toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
            if (Intrinsics.areEqual(lowerCase, "holiday")) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderAll$lambda$115(GxThemePage this$0, GxThemeEntry $t) {
        this$0.loadToEditor($t);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderAll$lambda$116(GxThemePage this$0, GxThemeEntry $t) {
        this$0.removeTheme($t);
        return Unit.INSTANCE;
    }

    /* JADX WARN: Removed duplicated region for block: B:46:0x0125  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0180  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x018a  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x0190  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final void renderComp(final String comp) {
        Map map;
        String str;
        String joinToString$default;
        LinearLayout linearLayout;
        GxPage.Row r;
        GxThemeEntry builtin = Intrinsics.areEqual(comp, "turn_signals") ? new GxThemeEntry("None (Stock)", "__stock_none__", "stock_none", false, false, true, 16, null) : new GxThemeEntry("Stock", "__stock__", "stock", false, false, true, 16, null);
        Iterable $this$filter$iv = this.themes;
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            GxThemeEntry t = (GxThemeEntry) element$iv$iv;
            if (Intrinsics.areEqual(comp, "steering_wheel") ? Intrinsics.areEqual(t.getType(), "steering_wheel") || (Intrinsics.areEqual(t.getType(), "holiday") && Intrinsics.areEqual((Object) t.getHas().get(comp), (Object) true)) : Intrinsics.areEqual((Object) t.getHas().get(comp), (Object) true)) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        List visible = (List) destination$iv$iv;
        map = GxThemeKt.GXTHEME_PARAM;
        String str2 = (String) map.get(comp);
        if (str2 == null) {
            str2 = "";
        }
        String cur = pStr(str2);
        for (final GxThemeEntry t2 : CollectionsKt.plus((Collection) CollectionsKt.listOf(builtin), (Iterable) visible)) {
            boolean local = t2.getBuiltin() || Intrinsics.areEqual((Object) t2.getLocal().get(comp), (Object) true);
            String value = selectionValue(t2, comp);
            ArrayList bits = new ArrayList();
            if (t2.getBuiltin()) {
                bits.add("Built-in");
            }
            String type = t2.getType();
            if (Intrinsics.areEqual(type, "holiday")) {
                if (!t2.getPseudo()) {
                    str = "Holiday";
                    bits.add(str);
                }
                if (!local) {
                    bits.add(isDownloadable(comp, t2.getName()) ? "Available to download" : "Not on this device");
                }
                if (!StringsKt.isBlank(cur) && Intrinsics.areEqual(cur, value) && pBool("CustomThemes")) {
                    bits.add("In use");
                }
                GxThemePage gxThemePage = this;
                String titleOf = titleOf(t2);
                joinToString$default = CollectionsKt.joinToString$default(bits, " · ", null, null, 0, null, null, 62, null);
                LinearLayout linearLayout2 = null;
                if (StringsKt.isBlank(joinToString$default)) {
                    joinToString$default = null;
                }
                String str3 = joinToString$default;
                linearLayout = this.libBox;
                if (linearLayout != null) {
                    Intrinsics.throwUninitializedPropertyAccessException("libBox");
                } else {
                    linearLayout2 = linearLayout;
                }
                r = GxPage.row$default(gxThemePage, titleOf, str3, linearLayout2, 0, 8, null);
                if (!local || !isDownloadable(comp, t2.getName())) {
                    GxPage.Row.button$default(r, "Use", false, new Function0() { // from class: com.starpilot.starview.GxThemePage$$ExternalSyntheticLambda24
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            Unit renderComp$lambda$120;
                            renderComp$lambda$120 = GxThemePage.renderComp$lambda$120(GxThemePage.this, t2, comp);
                            return renderComp$lambda$120;
                        }
                    }, 2, null);
                } else {
                    r.button("Download", true, new Function0() { // from class: com.starpilot.starview.GxThemePage$$ExternalSyntheticLambda23
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            Unit renderComp$lambda$119;
                            renderComp$lambda$119 = GxThemePage.renderComp$lambda$119(GxThemePage.this, comp, t2);
                            return renderComp$lambda$119;
                        }
                    });
                }
                if (!t2.getBuiltin() && !Intrinsics.areEqual(t2.getType(), "holiday") && Intrinsics.areEqual((Object) t2.getLocal().get(comp), (Object) true)) {
                    GxPage.Row.button$default(r, "Delete", false, new Function0() { // from class: com.starpilot.starview.GxThemePage$$ExternalSyntheticLambda25
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            Unit renderComp$lambda$121;
                            renderComp$lambda$121 = GxThemePage.renderComp$lambda$121(GxThemePage.this, t2, comp);
                            return renderComp$lambda$121;
                        }
                    }, 2, null);
                }
            } else {
                if (Intrinsics.areEqual(type, "steering_wheel")) {
                    str = "Wheel";
                    bits.add(str);
                }
                if (!local) {
                }
                if (!StringsKt.isBlank(cur)) {
                    bits.add("In use");
                }
                GxThemePage gxThemePage2 = this;
                String titleOf2 = titleOf(t2);
                joinToString$default = CollectionsKt.joinToString$default(bits, " · ", null, null, 0, null, null, 62, null);
                LinearLayout linearLayout22 = null;
                if (StringsKt.isBlank(joinToString$default)) {
                }
                String str32 = joinToString$default;
                linearLayout = this.libBox;
                if (linearLayout != null) {
                }
                r = GxPage.row$default(gxThemePage2, titleOf2, str32, linearLayout22, 0, 8, null);
                if (!local) {
                }
                GxPage.Row.button$default(r, "Use", false, new Function0() { // from class: com.starpilot.starview.GxThemePage$$ExternalSyntheticLambda24
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        Unit renderComp$lambda$120;
                        renderComp$lambda$120 = GxThemePage.renderComp$lambda$120(GxThemePage.this, t2, comp);
                        return renderComp$lambda$120;
                    }
                }, 2, null);
                if (!t2.getBuiltin()) {
                    GxPage.Row.button$default(r, "Delete", false, new Function0() { // from class: com.starpilot.starview.GxThemePage$$ExternalSyntheticLambda25
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            Unit renderComp$lambda$121;
                            renderComp$lambda$121 = GxThemePage.renderComp$lambda$121(GxThemePage.this, t2, comp);
                            return renderComp$lambda$121;
                        }
                    }, 2, null);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderComp$lambda$119(GxThemePage this$0, String $comp, GxThemeEntry $t) {
        this$0.startAssetDownload($comp, $t.getName());
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderComp$lambda$120(GxThemePage this$0, GxThemeEntry $t, String $comp) {
        this$0.useComponent($t, $comp);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderComp$lambda$121(GxThemePage this$0, GxThemeEntry $t, String $comp) {
        this$0.removeComponent($t, $comp);
        return Unit.INSTANCE;
    }

    private final String selectionValue(GxThemeEntry t, String comp) {
        if (Intrinsics.areEqual(t.getType(), "stock")) {
            return "stock";
        }
        if (Intrinsics.areEqual(t.getType(), "stock_none")) {
            return "none";
        }
        if (Intrinsics.areEqual(comp, "steering_wheel")) {
            if (t.getPath().length() > 0) {
                return new Regex("\\.[^.]+$").replace(t.getPath(), "");
            }
        }
        return t.getPath();
    }

    private final void loadToEditor(final GxThemeEntry t) {
        if (this.busy) {
            return;
        }
        setBusy(true);
        getJson("/api/themes/load/" + enc(t.getPath()) + typeQs(t.getType()), new Function2() { // from class: com.starpilot.starview.GxThemePage$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit loadToEditor$lambda$135;
                loadToEditor$lambda$135 = GxThemePage.loadToEditor$lambda$135(GxThemePage.this, t, (String) obj, (JSONObject) obj2);
                return loadToEditor$lambda$135;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit loadToEditor$lambda$135(final GxThemePage this$0, final GxThemeEntry $t, String e, JSONObject data) {
        List<String> list;
        List<String> list2;
        if (e == null && data != null) {
            final Ref.BooleanRef loadedAny = new Ref.BooleanRef();
            JSONObject it = data.optJSONObject("colors");
            if (it != null) {
                if ((it.length() > 0 ? 1 : null) == null) {
                    it = null;
                }
                if (it != null) {
                    this$0.setColors(it);
                    this$0.flags.put("colors", true);
                    loadedAny.element = true;
                }
            }
            JSONObject optJSONObject = data.optJSONObject("images");
            if (optJSONObject == null) {
                optJSONObject = new JSONObject();
            }
            JSONObject imgs = optJSONObject;
            final ArrayList jobs = new ArrayList();
            JSONObject di = imgs.optJSONObject("distanceIcons");
            if (di != null) {
                list2 = GxThemeKt.GXTHEME_DISTANCE;
                for (String k : list2) {
                    loadToEditor$lambda$135$slot$126(this$0, jobs, "distanceIcons_" + k, di.optJSONObject(k));
                }
            }
            for (String s : CollectionsKt.listOf((Object[]) new String[]{"homeButton", "settingsButton", "steeringWheel", "turnSignal", "turnSignalBlindspot"})) {
                loadToEditor$lambda$135$slot$126(this$0, jobs, s, imgs.optJSONObject(s));
            }
            JSONObject so = data.optJSONObject("sounds");
            if (so != null) {
                list = GxThemeKt.GXTHEME_SOUNDS;
                for (String k2 : list) {
                    loadToEditor$lambda$135$slot$126(this$0, jobs, k2, so.optJSONObject(k2));
                }
            }
            String str$default = GxPage.str$default(this$0, data, "turnSignalStyle", null, 2, null);
            if (StringsKt.isBlank(str$default)) {
                str$default = null;
            }
            String it2 = str$default;
            if (it2 != null) {
                this$0.tsStyle = it2;
            }
            if (data.has("turnSignalLength") && !data.isNull("turnSignalLength")) {
                int it3 = data.optInt("turnSignalLength", 100);
                this$0.tsLength = it3 != 0 ? it3 : 100;
            }
            JSONArray optJSONArray = data.optJSONArray("sequentialImages");
            List strings = optJSONArray != null ? this$0.strings(optJSONArray) : null;
            if (strings == null) {
                strings = CollectionsKt.emptyList();
            }
            List seq = strings;
            if (!seq.isEmpty()) {
                this$0.tsType = "Sequential";
                this$0.frames.clear();
                this$0.framesName = "";
                for (String img : seq) {
                    jobs.add(TuplesKt.to("signals/" + img, new Function1() { // from class: com.starpilot.starview.GxThemePage$$ExternalSyntheticLambda4
                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj) {
                            Unit loadToEditor$lambda$135$lambda$132;
                            loadToEditor$lambda$135$lambda$132 = GxThemePage.loadToEditor$lambda$135$lambda$132(GxThemePage.this, (GxThemeFile) obj);
                            return loadToEditor$lambda$135$lambda$132;
                        }
                    }));
                }
            } else if (imgs.optJSONObject("turnSignal") != null) {
                this$0.tsType = "Single Image";
            }
            ArrayList $this$map$iv = jobs;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            for (Object item$iv$iv : $this$map$iv) {
                destination$iv$iv.add(this$0.assetPath($t.getPath(), $t.getType(), (String) ((Pair) item$iv$iv).getFirst()));
                seq = seq;
                $this$map$iv = $this$map$iv;
            }
            this$0.fetchAssets((List) destination$iv$iv, new Function1() { // from class: com.starpilot.starview.GxThemePage$$ExternalSyntheticLambda5
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    Unit loadToEditor$lambda$135$lambda$134;
                    loadToEditor$lambda$135$lambda$134 = GxThemePage.loadToEditor$lambda$135$lambda$134(jobs, loadedAny, this$0, $t, (List) obj);
                    return loadToEditor$lambda$135$lambda$134;
                }
            });
            return Unit.INSTANCE;
        }
        this$0.setBusy(false);
        this$0.toast(e == null ? "Failed to load theme." : e);
        return Unit.INSTANCE;
    }

    private static final void loadToEditor$lambda$135$slot$126(final GxThemePage this$0, ArrayList<Pair<String, Function1<GxThemeFile, Unit>>> arrayList, final String slot, JSONObject asset) {
        String str$default;
        if (asset == null || (str$default = GxPage.str$default(this$0, asset, "path", null, 2, null)) == null) {
            return;
        }
        String str = str$default;
        if (StringsKt.isBlank(str)) {
            str = null;
        }
        String p = str;
        if (p == null) {
            return;
        }
        arrayList.add(TuplesKt.to(p, new Function1() { // from class: com.starpilot.starview.GxThemePage$$ExternalSyntheticLambda17
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit loadToEditor$lambda$135$slot$126$lambda$125;
                loadToEditor$lambda$135$slot$126$lambda$125 = GxThemePage.loadToEditor$lambda$135$slot$126$lambda$125(GxThemePage.this, slot, (GxThemeFile) obj);
                return loadToEditor$lambda$135$slot$126$lambda$125;
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit loadToEditor$lambda$135$slot$126$lambda$125(GxThemePage this$0, String $slot, GxThemeFile f) {
        Intrinsics.checkNotNullParameter(f, "f");
        this$0.files.put($slot, f);
        this$0.names.put($slot, f.getFileName());
        this$0.flags.put(this$0.compOf($slot), true);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit loadToEditor$lambda$135$lambda$132(GxThemePage this$0, GxThemeFile f) {
        Intrinsics.checkNotNullParameter(f, "f");
        this$0.frames.add(f);
        this$0.flags.put("turn_signals", true);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit loadToEditor$lambda$135$lambda$134(ArrayList $jobs, Ref.BooleanRef $loadedAny, GxThemePage this$0, GxThemeEntry $t, List res) {
        String str;
        Intrinsics.checkNotNullParameter(res, "res");
        Iterator it = res.iterator();
        int i = 0;
        while (it.hasNext()) {
            int i2 = i;
            i++;
            GxThemeFile f = (GxThemeFile) it.next();
            if (f != null) {
                ((Function1) ((Pair) $jobs.get(i2)).getSecond()).invoke(f);
                $loadedAny.element = true;
            }
        }
        this$0.setBusy(false);
        if ($loadedAny.element) {
            str = "Loaded \"" + $t.getName() + "\" into the editor!";
        } else {
            str = "That theme has no loadable content.";
        }
        this$0.toast(str);
        this$0.renderEditor();
        return Unit.INSTANCE;
    }

    private final void useComponent(final GxThemeEntry t, final String comp) {
        Map map;
        if (this.busy) {
            return;
        }
        map = GxThemeKt.GXTHEME_PARAM;
        final String key = (String) map.get(comp);
        if (key == null) {
            return;
        }
        putParam("CustomThemes", "true", new Function1() { // from class: com.starpilot.starview.GxThemePage$$ExternalSyntheticLambda16
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit useComponent$lambda$137;
                useComponent$lambda$137 = GxThemePage.useComponent$lambda$137(GxThemePage.this, key, t, comp, (String) obj);
                return useComponent$lambda$137;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit useComponent$lambda$137(final GxThemePage this$0, String $key, final GxThemeEntry $t, final String $comp, String e) {
        if (e != null) {
            return Unit.INSTANCE;
        }
        this$0.putParam($key, this$0.selectionValue($t, $comp), new Function1() { // from class: com.starpilot.starview.GxThemePage$$ExternalSyntheticLambda10
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit useComponent$lambda$137$lambda$136;
                useComponent$lambda$137$lambda$136 = GxThemePage.useComponent$lambda$137$lambda$136(GxThemePage.this, $t, $comp, (String) obj);
                return useComponent$lambda$137$lambda$136;
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit useComponent$lambda$137$lambda$136(GxThemePage this$0, GxThemeEntry $t, String $comp, String e2) {
        if (e2 != null) {
            return Unit.INSTANCE;
        }
        this$0.renderLibrary();
        if (Intrinsics.areEqual($t.getType(), "stock_none") && Intrinsics.areEqual($comp, "turn_signals")) {
            this$0.loadBuiltinTurnSignals();
        } else {
            this$0.loadComponent($t, $comp);
        }
        return Unit.INSTANCE;
    }

    private final void loadBuiltinTurnSignals() {
        clearComp("turn_signals");
        this.submitted = false;
        this.tsLength = 100;
        this.tsType = "Single Image";
        this.tsStyle = "Traditional";
        this.names.put("turnSignal", "None");
        this.names.remove("turnSignalBlindspot");
        this.sources.put("turn_signals", new JSONObject().put("path", "__stock_none__").put("type", "stock_none"));
        this.flags.put("turn_signals", true);
        toast("Loaded stock turn signals.");
        renderEditor();
    }

    private final void loadComponent(final GxThemeEntry t, final String comp) {
        if (this.busy) {
            return;
        }
        setBusy(true);
        clearComp(comp);
        final String what = StringsKt.replace$default(comp, "_", " ", false, 4, (Object) null);
        getJson("/api/themes/load/" + enc(t.getPath()) + typeQs(t.getType()), new Function2() { // from class: com.starpilot.starview.GxThemePage$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit loadComponent$lambda$152;
                loadComponent$lambda$152 = GxThemePage.loadComponent$lambda$152(GxThemePage.this, comp, t, what, (String) obj, (JSONObject) obj2);
                return loadComponent$lambda$152;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public static final Unit loadComponent$lambda$152(final GxThemePage this$0, final String $comp, final GxThemeEntry $t, final String $what, String e, JSONObject data) {
        JSONObject it;
        JSONObject so;
        List<String> list;
        JSONObject di;
        List<String> list2;
        JSONObject sw;
        if (e != null || data == null) {
            this$0.setBusy(false);
            this$0.toast(e == null ? "Failed to load theme asset." : e);
            this$0.renderEditor();
            return Unit.INSTANCE;
        }
        JSONObject optJSONObject = data.optJSONObject("images");
        if (optJSONObject == null) {
            optJSONObject = new JSONObject();
        }
        JSONObject imgs = optJSONObject;
        final ArrayList jobs = new ArrayList();
        switch ($comp.hashCode()) {
            case -1354842768:
                if ($comp.equals("colors") && (it = data.optJSONObject("colors")) != null) {
                    this$0.setColors(it);
                    break;
                }
                break;
            case -896509628:
                if ($comp.equals("sounds") && (so = data.optJSONObject("sounds")) != null) {
                    list = GxThemeKt.GXTHEME_SOUNDS;
                    for (String k : list) {
                        loadComponent$lambda$152$slot$140(this$0, $t, jobs, k, so.optJSONObject(k));
                    }
                    break;
                }
                break;
            case -217663920:
                if ($comp.equals("distance_icons") && (di = imgs.optJSONObject("distanceIcons")) != null) {
                    list2 = GxThemeKt.GXTHEME_DISTANCE;
                    for (String k2 : list2) {
                        loadComponent$lambda$152$slot$140(this$0, $t, jobs, "distanceIcons_" + k2, di.optJSONObject(k2));
                    }
                    break;
                }
                break;
            case 100029210:
                if ($comp.equals("icons")) {
                    loadComponent$lambda$152$slot$140(this$0, $t, jobs, "homeButton", imgs.optJSONObject("homeButton"));
                    loadComponent$lambda$152$slot$140(this$0, $t, jobs, "settingsButton", imgs.optJSONObject("settingsButton"));
                    break;
                }
                break;
            case 271106313:
                if ($comp.equals("turn_signals")) {
                    this$0.tsLength = data.optInt("turnSignalLength", 100);
                    String str$default = GxPage.str$default(this$0, data, "turnSignalType", null, 2, null);
                    if (StringsKt.isBlank(str$default)) {
                        str$default = "Single Image";
                    }
                    this$0.tsType = str$default;
                    String str$default2 = GxPage.str$default(this$0, data, "turnSignalStyle", null, 2, null);
                    if (StringsKt.isBlank(str$default2)) {
                        str$default2 = "Static";
                    }
                    this$0.tsStyle = str$default2;
                    loadComponent$lambda$152$slot$140(this$0, $t, jobs, "turnSignal", imgs.optJSONObject("turnSignal"));
                    loadComponent$lambda$152$slot$140(this$0, $t, jobs, "turnSignalBlindspot", imgs.optJSONObject("turnSignalBlindspot"));
                    JSONArray optJSONArray = data.optJSONArray("sequentialImages");
                    List seq = optJSONArray != null ? this$0.strings(optJSONArray) : null;
                    if (seq == null) {
                        seq = CollectionsKt.emptyList();
                    }
                    if (!seq.isEmpty()) {
                        this$0.framesName = $t.getName();
                        for (String img : seq) {
                            jobs.add(TuplesKt.to("signals/" + img, new Function1() { // from class: com.starpilot.starview.GxThemePage$$ExternalSyntheticLambda14
                                @Override // kotlin.jvm.functions.Function1
                                public final Object invoke(Object obj) {
                                    Unit loadComponent$lambda$152$lambda$149;
                                    loadComponent$lambda$152$lambda$149 = GxThemePage.loadComponent$lambda$152$lambda$149(GxThemePage.this, (GxThemeFile) obj);
                                    return loadComponent$lambda$152$lambda$149;
                                }
                            }));
                        }
                        break;
                    }
                }
                break;
            case 2119462413:
                if ($comp.equals("steering_wheel") && (sw = imgs.optJSONObject("steeringWheel")) != null) {
                    String p = GxPage.str$default(this$0, sw, "path", null, 2, null);
                    if (!StringsKt.isBlank(p)) {
                        this$0.names.put("steeringWheel", $t.getName());
                        String str$default3 = GxPage.str$default(this$0, sw, "filename", null, 2, null);
                        if (StringsKt.isBlank(str$default3)) {
                            str$default3 = $t.getPath();
                        }
                        final String fn = str$default3;
                        jobs.add(TuplesKt.to(p, new Function1() { // from class: com.starpilot.starview.GxThemePage$$ExternalSyntheticLambda13
                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj) {
                                Unit loadComponent$lambda$152$lambda$145$lambda$144;
                                loadComponent$lambda$152$lambda$145$lambda$144 = GxThemePage.loadComponent$lambda$152$lambda$145$lambda$144(GxThemePage.this, fn, (GxThemeFile) obj);
                                return loadComponent$lambda$152$lambda$145$lambda$144;
                            }
                        }));
                        break;
                    }
                }
                break;
        }
        ArrayList $this$map$iv = jobs;
        int $i$f$map = 0;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        for (Object item$iv$iv : $this$map$iv) {
            destination$iv$iv.add(this$0.assetPath($t.getPath(), $t.getType(), (String) ((Pair) item$iv$iv).getFirst()));
            $this$map$iv = $this$map$iv;
            $i$f$map = $i$f$map;
        }
        this$0.fetchAssets((List) destination$iv$iv, new Function1() { // from class: com.starpilot.starview.GxThemePage$$ExternalSyntheticLambda15
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit loadComponent$lambda$152$lambda$151;
                loadComponent$lambda$152$lambda$151 = GxThemePage.loadComponent$lambda$152$lambda$151(jobs, this$0, $comp, $t, $what, (List) obj);
                return loadComponent$lambda$152$lambda$151;
            }
        });
        return Unit.INSTANCE;
    }

    private static final void loadComponent$lambda$152$slot$140(final GxThemePage this$0, GxThemeEntry $t, ArrayList<Pair<String, Function1<GxThemeFile, Unit>>> arrayList, final String slot, JSONObject asset) {
        String str$default;
        if (asset == null || (str$default = GxPage.str$default(this$0, asset, "path", null, 2, null)) == null) {
            return;
        }
        String str = str$default;
        if (StringsKt.isBlank(str)) {
            str = null;
        }
        String p = str;
        if (p == null) {
            return;
        }
        this$0.names.put(slot, $t.getName());
        arrayList.add(TuplesKt.to(p, new Function1() { // from class: com.starpilot.starview.GxThemePage$$ExternalSyntheticLambda61
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit loadComponent$lambda$152$slot$140$lambda$139;
                loadComponent$lambda$152$slot$140$lambda$139 = GxThemePage.loadComponent$lambda$152$slot$140$lambda$139(GxThemePage.this, slot, (GxThemeFile) obj);
                return loadComponent$lambda$152$slot$140$lambda$139;
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit loadComponent$lambda$152$slot$140$lambda$139(GxThemePage this$0, String $slot, GxThemeFile f) {
        Intrinsics.checkNotNullParameter(f, "f");
        this$0.files.put($slot, f);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit loadComponent$lambda$152$lambda$145$lambda$144(GxThemePage this$0, String $fn, GxThemeFile f) {
        Intrinsics.checkNotNullParameter(f, "f");
        this$0.files.put("steeringWheel", new GxThemeFile($fn, f.getBytes(), f.getMime()));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit loadComponent$lambda$152$lambda$149(GxThemePage this$0, GxThemeFile f) {
        Intrinsics.checkNotNullParameter(f, "f");
        this$0.frames.add(f);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit loadComponent$lambda$152$lambda$151(ArrayList $jobs, GxThemePage this$0, String $comp, GxThemeEntry $t, String $what, List res) {
        StringBuilder sb;
        StringBuilder append;
        String str;
        Intrinsics.checkNotNullParameter(res, "res");
        boolean failed = false;
        Iterator it = res.iterator();
        int i = 0;
        while (it.hasNext()) {
            int i2 = i;
            i++;
            GxThemeFile f = (GxThemeFile) it.next();
            if (f != null) {
                ((Function1) ((Pair) $jobs.get(i2)).getSecond()).invoke(f);
            } else {
                failed = true;
            }
        }
        this$0.sources.put($comp, new JSONObject().put("path", $t.getPath()).put("type", $t.getType()));
        this$0.flags.put($comp, true);
        this$0.setBusy(false);
        String name = $t.getName();
        if (failed) {
            sb = new StringBuilder();
            append = sb.append("Failed to load ").append($what).append(" from \"").append(name);
            str = "\".";
        } else {
            sb = new StringBuilder();
            append = sb.append("Loaded ").append($what).append(" from \"").append(name);
            str = "\"!";
        }
        this$0.toast(append.append(str).toString());
        this$0.renderEditor();
        return Unit.INSTANCE;
    }

    private final void removeTheme(final GxThemeEntry t) {
        if (this.busy) {
            return;
        }
        String lowerCase = t.getType().toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
        if (Intrinsics.areEqual(lowerCase, "holiday")) {
            return;
        }
        String kind = Intrinsics.areEqual(t.getType(), "steering_wheel") ? "steering wheel" : "theme";
        confirm("Delete theme?", "Delete " + kind + " \"" + t.getName() + "\"? This cannot be undone.", "Delete", new Function0() { // from class: com.starpilot.starview.GxThemePage$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit removeTheme$lambda$154;
                removeTheme$lambda$154 = GxThemePage.removeTheme$lambda$154(GxThemePage.this, t);
                return removeTheme$lambda$154;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit removeTheme$lambda$154(final GxThemePage this$0, GxThemeEntry $t) {
        String qs;
        this$0.setBusy(true);
        if (StringsKt.isBlank($t.getType())) {
            qs = "?type=user";
        } else {
            qs = "?type=" + this$0.enc($t.getType());
        }
        GxPage.send$default(this$0, "DELETE", "/api/themes/delete/" + this$0.enc($t.getPath()) + qs, null, 0L, new Function2() { // from class: com.starpilot.starview.GxThemePage$$ExternalSyntheticLambda26
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit removeTheme$lambda$154$lambda$153;
                removeTheme$lambda$154$lambda$153 = GxThemePage.removeTheme$lambda$154$lambda$153(GxThemePage.this, (String) obj, (JSONObject) obj2);
                return removeTheme$lambda$154$lambda$153;
            }
        }, 12, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit removeTheme$lambda$154$lambda$153(GxThemePage this$0, String e, JSONObject o) {
        String str;
        this$0.setBusy(false);
        if (e == null) {
            str = o != null ? this$0.msg(o) : null;
            if (str == null) {
                str = "Theme deleted.";
            }
        } else {
            str = e;
        }
        this$0.toast(str);
        this$0.refreshThemeList();
        this$0.fetchDownloadables();
        return Unit.INSTANCE;
    }

    private final void removeComponent(final GxThemeEntry t, final String comp) {
        if (this.busy) {
            return;
        }
        confirm("Confirm Delete", "Are you sure you want to delete the theme \"" + t.getName() + "\"?", "Delete", new Function0() { // from class: com.starpilot.starview.GxThemePage$$ExternalSyntheticLambda8
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit removeComponent$lambda$156;
                removeComponent$lambda$156 = GxThemePage.removeComponent$lambda$156(GxThemePage.this, comp, t);
                return removeComponent$lambda$156;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit removeComponent$lambda$156(final GxThemePage this$0, String $comp, GxThemeEntry $t) {
        String component;
        this$0.setBusy(true);
        if (Intrinsics.areEqual($comp, "steering_wheel")) {
            component = "";
        } else {
            component = "&component=" + (Intrinsics.areEqual($comp, "turn_signals") ? "signals" : $comp);
        }
        GxPage.send$default(this$0, "DELETE", "/api/themes/delete/" + this$0.enc($t.getPath()) + "?type=" + this$0.enc($t.getType()) + component, null, 0L, new Function2() { // from class: com.starpilot.starview.GxThemePage$$ExternalSyntheticLambda37
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit removeComponent$lambda$156$lambda$155;
                removeComponent$lambda$156$lambda$155 = GxThemePage.removeComponent$lambda$156$lambda$155(GxThemePage.this, (String) obj, (JSONObject) obj2);
                return removeComponent$lambda$156$lambda$155;
            }
        }, 12, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit removeComponent$lambda$156$lambda$155(GxThemePage this$0, String e, JSONObject o) {
        String str;
        this$0.setBusy(false);
        if (e == null) {
            str = o != null ? this$0.msg(o) : null;
            if (str == null) {
                str = "Theme deleted.";
            }
        } else {
            str = e;
        }
        this$0.toast(str);
        this$0.refreshThemeList();
        this$0.fetchDownloadables();
        return Unit.INSTANCE;
    }

    private final void startAssetDownload(String comp, final String name) {
        if (this.downloading || this.busy) {
            toast("Please wait for the current download to finish.");
            return;
        }
        this.downloading = true;
        final String what = StringsKt.replace$default(comp, "_", " ", false, 4, (Object) null);
        GxPage.send$default(this, "POST", "/api/themes/download_asset", new JSONObject().put("component", comp).put("name", name), 0L, new Function2() { // from class: com.starpilot.starview.GxThemePage$$ExternalSyntheticLambda11
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit startAssetDownload$lambda$157;
                startAssetDownload$lambda$157 = GxThemePage.startAssetDownload$lambda$157(GxThemePage.this, what, name, (String) obj, (JSONObject) obj2);
                return startAssetDownload$lambda$157;
            }
        }, 8, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit startAssetDownload$lambda$157(GxThemePage this$0, String $what, String $name, String e, JSONObject jSONObject) {
        if (e != null) {
            this$0.downloading = false;
            this$0.toast("Unable to start download.");
            return Unit.INSTANCE;
        }
        this$0.toast("Downloading " + $what + " for the \"" + $name + "\" theme...");
        GxPage.Row row = this$0.dlRow;
        GxPage.Row row2 = null;
        if (row == null) {
            Intrinsics.throwUninitializedPropertyAccessException("dlRow");
            row = null;
        }
        row.setTitle("Downloading " + $what + ": " + $name);
        GxPage.Row row3 = this$0.dlRow;
        if (row3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("dlRow");
            row3 = null;
        }
        row3.setDesc("Downloading...");
        GxPage.Row row4 = this$0.dlRow;
        if (row4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("dlRow");
        } else {
            row2 = row4;
        }
        row2.setVisible(true);
        Ref.IntRef polls = new Ref.IntRef();
        GxThemePage$startAssetDownload$1$r$1 r = new GxThemePage$startAssetDownload$1$r$1(this$0, $what, $name, polls);
        this$0.getMain().postDelayed(r, 1000L);
        return Unit.INSTANCE;
    }

    /* JADX WARN: Type inference failed for: r11v3, types: [T, com.starpilot.starview.GxThemePage$$ExternalSyntheticLambda19] */
    private final void gxThemeColorDialog(String title, int initial, final Function1<? super Integer, Unit> then) {
        Function1 function1;
        final float[] hsv = new float[3];
        Color.colorToHSV(initial, hsv);
        LinearLayout $this$gxThemeColorDialog_u24lambda_u24158 = new LinearLayout(getAct());
        $this$gxThemeColorDialog_u24lambda_u24158.setOrientation(1);
        $this$gxThemeColorDialog_u24lambda_u24158.setPadding(getUi().dp(24), getUi().dp(12), getUi().dp(24), getUi().dp(4));
        final View swatch = new View(getAct());
        LinearLayout.LayoutParams $this$gxThemeColorDialog_u24lambda_u24159 = new LinearLayout.LayoutParams(-1, getUi().dp(84));
        $this$gxThemeColorDialog_u24lambda_u24159.bottomMargin = getUi().dp(12);
        Unit unit = Unit.INSTANCE;
        $this$gxThemeColorDialog_u24lambda_u24158.addView(swatch, $this$gxThemeColorDialog_u24lambda_u24159);
        final Ref.BooleanRef updating = new Ref.BooleanRef();
        final EditText $this$gxThemeColorDialog_u24lambda_u24160 = new EditText(getAct());
        $this$gxThemeColorDialog_u24lambda_u24160.setTextSize(22.0f);
        $this$gxThemeColorDialog_u24lambda_u24160.setTypeface(Typeface.MONOSPACE);
        $this$gxThemeColorDialog_u24lambda_u24160.setSingleLine(true);
        $this$gxThemeColorDialog_u24lambda_u24160.setFilters(new InputFilter.LengthFilter[]{new InputFilter.LengthFilter(7)});
        $this$gxThemeColorDialog_u24lambda_u24160.setInputType(528385);
        final ArrayList bars = new ArrayList();
        ArrayList labels = new ArrayList();
        final ArrayList strips = new ArrayList();
        final Ref.ObjectRef paint = new Ref.ObjectRef();
        Iterator it = CollectionsKt.listOf((Object[]) new String[]{"Hue", "Saturation", "Value"}).iterator();
        int i = 0;
        while (it.hasNext()) {
            final int i2 = i;
            i++;
            String name = (String) it.next();
            TextView $this$gxThemeColorDialog_u24lambda_u24164 = new TextView(getAct());
            $this$gxThemeColorDialog_u24lambda_u24164.setTextSize(17.0f);
            Iterator it2 = it;
            $this$gxThemeColorDialog_u24lambda_u24164.setPadding(0, getUi().dp(8), 0, getUi().dp(4));
            $this$gxThemeColorDialog_u24lambda_u24158.addView($this$gxThemeColorDialog_u24lambda_u24164);
            labels.add($this$gxThemeColorDialog_u24lambda_u24164);
            strips.add(gxThemeColorDialog$strip(this, $this$gxThemeColorDialog_u24lambda_u24158));
            SeekBar sb = new SeekBar(getAct());
            sb.setMax(i2 == 0 ? 360 : 100);
            sb.setMinimumHeight(getUi().dp(48));
            sb.setThumbTintList(ColorStateList.valueOf(-1));
            sb.setProgressTintList(ColorStateList.valueOf(0));
            sb.setProgressBackgroundTintList(ColorStateList.valueOf(0));
            sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.starpilot.starview.GxThemePage$gxThemeColorDialog$sb$1$1
                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onProgressChanged(SeekBar s, int p, boolean fromUser) {
                    Function1<Boolean, Unit> function12;
                    if (fromUser) {
                        hsv[i2] = i2 == 0 ? RangesKt.coerceAtMost(p, 359.9f) : p / 100.0f;
                        if (paint.element == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("paint");
                            function12 = null;
                        } else {
                            function12 = paint.element;
                        }
                        function12.invoke(false);
                    }
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStartTrackingTouch(SeekBar s) {
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStopTrackingTouch(SeekBar s) {
                }
            });
            $this$gxThemeColorDialog_u24lambda_u24158.addView(sb, new LinearLayout.LayoutParams(-1, getUi().dp(48)));
            bars.add(sb);
            $this$gxThemeColorDialog_u24lambda_u24164.setTag(name);
            paint = paint;
            labels = labels;
            it = it2;
        }
        final Ref.ObjectRef paint2 = paint;
        final ArrayList labels2 = labels;
        TextView $this$gxThemeColorDialog_u24lambda_u24166 = new TextView(getAct());
        $this$gxThemeColorDialog_u24lambda_u24166.setText("Hex");
        $this$gxThemeColorDialog_u24lambda_u24166.setTextSize(17.0f);
        $this$gxThemeColorDialog_u24lambda_u24166.setPadding(0, getUi().dp(10), 0, 0);
        $this$gxThemeColorDialog_u24lambda_u24158.addView($this$gxThemeColorDialog_u24lambda_u24166);
        $this$gxThemeColorDialog_u24lambda_u24158.addView($this$gxThemeColorDialog_u24lambda_u24160);
        Object obj = strips.get(0);
        Intrinsics.checkNotNullExpressionValue(obj, "get(...)");
        View view = (View) obj;
        int[] it3 = new int[7];
        for (int i3 = 0; i3 < 7; i3++) {
            it3[i3] = Color.HSVToColor(new float[]{(i3 * 60.0f) % 360.0f, 1.0f, 1.0f});
        }
        it3[6] = -65536;
        Unit unit2 = Unit.INSTANCE;
        gxThemeColorDialog$grad(this, view, it3);
        paint2.element = new Function1() { // from class: com.starpilot.starview.GxThemePage$$ExternalSyntheticLambda19
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj2) {
                Unit gxThemeColorDialog$lambda$168;
                gxThemeColorDialog$lambda$168 = GxThemePage.gxThemeColorDialog$lambda$168(swatch, this, strips, hsv, bars, labels2, updating, $this$gxThemeColorDialog_u24lambda_u24160, ((Boolean) obj2).booleanValue());
                return gxThemeColorDialog$lambda$168;
            }
        };
        $this$gxThemeColorDialog_u24lambda_u24160.addTextChangedListener(new TextWatcher() { // from class: com.starpilot.starview.GxThemePage$gxThemeColorDialog$6
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int a, int b, int d) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int a, int b, int d) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s) {
                String obj2;
                String obj3;
                String t;
                Function1<Boolean, Unit> function12;
                if (Ref.BooleanRef.this.element || s == null || (obj2 = s.toString()) == null || (obj3 = StringsKt.trim((CharSequence) obj2).toString()) == null || (t = StringsKt.removePrefix(obj3, (CharSequence) "#")) == null || !new Regex("[0-9a-fA-F]{6}").matches(t)) {
                    return;
                }
                Color.colorToHSV(Color.parseColor("#" + t), hsv);
                if (paint2.element == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("paint");
                    function12 = null;
                } else {
                    function12 = paint2.element;
                }
                function12.invoke(true);
            }
        });
        if (paint2.element == 0) {
            Intrinsics.throwUninitializedPropertyAccessException("paint");
            function1 = null;
        } else {
            function1 = (Function1) paint2.element;
        }
        function1.invoke(false);
        AlertDialog.Builder title2 = new AlertDialog.Builder(getAct()).setTitle(title);
        ScrollView $this$gxThemeColorDialog_u24lambda_u24169 = new ScrollView(getAct());
        $this$gxThemeColorDialog_u24lambda_u24169.addView($this$gxThemeColorDialog_u24lambda_u24158);
        title2.setView($this$gxThemeColorDialog_u24lambda_u24169).setPositiveButton("OK", new DialogInterface.OnClickListener() { // from class: com.starpilot.starview.GxThemePage$$ExternalSyntheticLambda20
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i4) {
                GxThemePage.gxThemeColorDialog$lambda$170($this$gxThemeColorDialog_u24lambda_u24160, then, hsv, dialogInterface, i4);
            }
        }).setNegativeButton("Cancel", (DialogInterface.OnClickListener) null).show();
    }

    private static final View gxThemeColorDialog$strip(GxThemePage this$0, LinearLayout wrap) {
        View it = new View(this$0.getAct());
        LinearLayout.LayoutParams $this$gxThemeColorDialog_u24strip_u24lambda_u24162_u24lambda_u24161 = new LinearLayout.LayoutParams(-1, this$0.getUi().dp(16));
        $this$gxThemeColorDialog_u24strip_u24lambda_u24162_u24lambda_u24161.leftMargin = this$0.getUi().dp(16);
        $this$gxThemeColorDialog_u24strip_u24lambda_u24162_u24lambda_u24161.rightMargin = this$0.getUi().dp(16);
        Unit unit = Unit.INSTANCE;
        wrap.addView(it, $this$gxThemeColorDialog_u24strip_u24lambda_u24162_u24lambda_u24161);
        return it;
    }

    private static final void gxThemeColorDialog$grad(GxThemePage this$0, View v, int[] cs) {
        GradientDrawable $this$gxThemeColorDialog_u24grad_u24lambda_u24163 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, cs);
        $this$gxThemeColorDialog_u24grad_u24lambda_u24163.setCornerRadius(this$0.getUi().dp(8));
        v.setBackground($this$gxThemeColorDialog_u24grad_u24lambda_u24163);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit gxThemeColorDialog$lambda$168(View $swatch, GxThemePage this$0, ArrayList $strips, float[] $hsv, ArrayList $bars, ArrayList $labels, Ref.BooleanRef $updating, EditText $hex, boolean fromHex) {
        int c = Color.HSVToColor($hsv);
        $swatch.setBackground(this$0.getUi().rounded(c, 12, Integer.valueOf(Color.rgb(90, 96, 108))));
        Object obj = $strips.get(1);
        Intrinsics.checkNotNullExpressionValue(obj, "get(...)");
        gxThemeColorDialog$grad(this$0, (View) obj, new int[]{Color.HSVToColor(new float[]{$hsv[0], 0.0f, $hsv[2]}), Color.HSVToColor(new float[]{$hsv[0], 1.0f, $hsv[2]})});
        Object obj2 = $strips.get(2);
        Intrinsics.checkNotNullExpressionValue(obj2, "get(...)");
        gxThemeColorDialog$grad(this$0, (View) obj2, new int[]{-16777216, Color.HSVToColor(new float[]{$hsv[0], $hsv[1], 1.0f})});
        ((SeekBar) $bars.get(0)).setProgress(Math.round($hsv[0]));
        float f = 100;
        ((SeekBar) $bars.get(1)).setProgress(Math.round($hsv[1] * f));
        ((SeekBar) $bars.get(2)).setProgress(Math.round($hsv[2] * f));
        ((TextView) $labels.get(0)).setText("Hue  " + Math.round($hsv[0]) + "°");
        ((TextView) $labels.get(1)).setText("Saturation  " + Math.round($hsv[1] * f) + "%");
        ((TextView) $labels.get(2)).setText("Value  " + Math.round($hsv[2] * f) + "%");
        if (!fromHex) {
            $updating.element = true;
            $hex.setText(this$0.hexOf(c));
            $hex.setSelection($hex.getText().length());
            $updating.element = false;
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void gxThemeColorDialog$lambda$170(EditText $hex, Function1 $then, float[] $hsv, DialogInterface dialogInterface, int i) {
        String t = StringsKt.removePrefix(StringsKt.trim((CharSequence) $hex.getText().toString()).toString(), (CharSequence) "#");
        int c = new Regex("[0-9a-fA-F]{6}").matches(t) ? Color.parseColor("#" + t) : Color.HSVToColor($hsv);
        $then.invoke(Integer.valueOf(c));
    }
}
