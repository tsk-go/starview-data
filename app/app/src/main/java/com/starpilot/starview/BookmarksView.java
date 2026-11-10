package com.starpilot.starview;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.starpilot.starview.Bookmarks;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.json.JSONObject;

/* compiled from: Bookmarks.kt */
public final class BookmarksView extends CardPage {
    private final SimpleDateFormat fmt;
    private MediaPlayer player;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BookmarksView(Context ctx, CarUi ui) {
        super(ctx, ui);
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(ui, "ui");
        this.fmt = new SimpleDateFormat("EEE MMM d, h:mm:ss a", Locale.US);
        paint();
    }

    @Override // com.starpilot.starview.CardPage
    public void refresh() {
        paint();
    }

    private final void paint() {
        char c;
        getCol().removeAllViews();
        Bookmarks bookmarks = Bookmarks.INSTANCE;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        List items = bookmarks.list(context);
        LinearLayout head = card();
        add(head);
        head.addView(title("Bookmarks", "bookmark", "tablet"));
        head.addView(line("Tap 🔖 on the drive screen (or say \"bookmark\") to mark a moment; hold 🎤 on the card that pops up to say what happened. Export them and attach the zip in a chat: Claude reads exactly what the tablet knew at that moment.", CarUi.Companion.getDIM(), 15.0f));
        LinearLayout $this$paint_u24lambda_u240 = new LinearLayout(getContext());
        char c2 = 0;
        $this$paint_u24lambda_u240.setOrientation(0);
        $this$paint_u24lambda_u240.setPadding(0, getUi().dp(10), 0, 0);
        $this$paint_u24lambda_u240.addView(getUi().button("Export new", true, new Function0() { // from class: com.starpilot.starview.BookmarksView$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit paint$lambda$3;
                paint$lambda$3 = BookmarksView.paint$lambda$3(BookmarksView.this);
                return paint$lambda$3;
            }
        }));
        $this$paint_u24lambda_u240.addView(new View(getContext()), new LinearLayout.LayoutParams(getUi().dp(12), 1));
        $this$paint_u24lambda_u240.addView(CarUi.button$default(getUi(), "Export all", false, new Function0() { // from class: com.starpilot.starview.BookmarksView$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit paint$lambda$4;
                paint$lambda$4 = BookmarksView.paint$lambda$4(BookmarksView.this);
                return paint$lambda$4;
            }
        }, 2, null));
        head.addView($this$paint_u24lambda_u240);
        if (items.isEmpty()) {
            head.addView(line("No bookmarks yet.", CarUi.Companion.getTEXT(), 16.0f));
            return;
        }
        for (final Bookmarks.Item it : items) {
            LinearLayout c3 = card();
            add(c3);
            TextView $this$paint_u24lambda_u245 = new TextView(getContext());
            $this$paint_u24lambda_u245.setText(this.fmt.format(new Date(it.getAtMs())) + (it.getJson().optBoolean("exported") ? "  ·  exported" : ""));
            $this$paint_u24lambda_u245.setTextColor(CarUi.Companion.getTEXT());
            $this$paint_u24lambda_u245.setTextSize(17.0f);
            $this$paint_u24lambda_u245.setTypeface(Typeface.DEFAULT_BOLD);
            c3.addView($this$paint_u24lambda_u245);
            String[] strArr = new String[3];
            strArr[c2] = it.getTitle();
            strArr[1] = it.getPlace();
            strArr[2] = it.getJson().optString("speed");
            Iterable $this$filter$iv = CollectionsKt.listOf((Object[]) strArr);
            Collection destination$iv$iv = new ArrayList();
            for (Object element$iv$iv : $this$filter$iv) {
                String s = (String) element$iv$iv;
                Intrinsics.checkNotNull(s);
                if (s.length() > 0) {
                    destination$iv$iv.add(element$iv$iv);
                }
            }
            c3.addView(line(CollectionsKt.joinToString((List) destination$iv$iv, "  ·  ", "", "", -1, "...", null), CarUi.Companion.getDIM(), 15.0f));
            String note = it.getNote();
            Intrinsics.checkNotNullExpressionValue(note, "<get-note>(...)");
            if (note.length() > 0) {
                c3.addView(line("✎ " + it.getNote(), CarUi.Companion.getTEXT(), 16.0f));
            }
            String transcript = it.getTranscript();
            Intrinsics.checkNotNullExpressionValue(transcript, "<get-transcript>(...)");
            if (transcript.length() > 0) {
                c3.addView(line("🎤 “" + it.getTranscript() + "”", CarUi.Companion.getTEXT(), 16.0f));
                c = 0;
            } else if (it.getHasVoice()) {
                c = 0;
                c3.addView(line("🎤 voice note (no transcript)", CarUi.Companion.getDIM(), 15.0f));
            } else {
                c = 0;
            }
            if (it.getHasPicture()) {
                ImageView $this$paint_u24lambda_u248 = new ImageView(getContext());
                $this$paint_u24lambda_u248.setAdjustViewBounds(true);
                $this$paint_u24lambda_u248.setScaleType(ImageView.ScaleType.FIT_START);
                try {
                    String path = new File(it.getDir(), "picture.jpg").getPath();
                    BitmapFactory.Options $this$paint_u24lambda_u248_u24lambda_u247 = new BitmapFactory.Options();
                    $this$paint_u24lambda_u248_u24lambda_u247.inSampleSize = 2;
                    Unit unit = Unit.INSTANCE;
                    $this$paint_u24lambda_u248.setImageBitmap(BitmapFactory.decodeFile(path, $this$paint_u24lambda_u248_u24lambda_u247));
                } catch (Exception e) {
                }
                LinearLayout.LayoutParams $this$paint_u24lambda_u249 = new LinearLayout.LayoutParams(-1, -2);
                $this$paint_u24lambda_u249.topMargin = getUi().dp(6);
                Unit unit2 = Unit.INSTANCE;
                c3.addView($this$paint_u24lambda_u248, $this$paint_u24lambda_u249);
            }
            LinearLayout b = new LinearLayout(getContext());
            c2 = 0;
            b.setOrientation(0);
            b.setPadding(0, getUi().dp(8), 0, 0);
            if (it.getHasVoice()) {
                b.addView(CarUi.button$default(getUi(), "▶ Play", false, new Function0() { // from class: com.starpilot.starview.BookmarksView$$ExternalSyntheticLambda4
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        Unit paint$lambda$11;
                        paint$lambda$11 = BookmarksView.paint$lambda$11(BookmarksView.this, it);
                        return paint$lambda$11;
                    }
                }, 2, null));
                b.addView(new View(getContext()), new LinearLayout.LayoutParams(getUi().dp(10), 1));
            }
            b.addView(CarUi.button$default(getUi(), "✎ Note", false, new Function0() { // from class: com.starpilot.starview.BookmarksView$$ExternalSyntheticLambda5
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit paint$lambda$12;
                    paint$lambda$12 = BookmarksView.paint$lambda$12(BookmarksView.this, it);
                    return paint$lambda$12;
                }
            }, 2, null));
            b.addView(new View(getContext()), new LinearLayout.LayoutParams(getUi().dp(10), 1));
            b.addView(CarUi.button$default(getUi(), "Delete", false, new Function0() { // from class: com.starpilot.starview.BookmarksView$$ExternalSyntheticLambda6
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit paint$lambda$14;
                    paint$lambda$14 = BookmarksView.paint$lambda$14(BookmarksView.this, it);
                    return paint$lambda$14;
                }
            }, 2, null));
            c3.addView(b);
        }
    }

    private static final void paint$exportNow(final BookmarksView this$0, final boolean onlyNew) {
        new Thread(new Runnable() { // from class: com.starpilot.starview.BookmarksView$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                BookmarksView.paint$exportNow$lambda$2(this$0, onlyNew);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void paint$exportNow$lambda$2(final BookmarksView this$0, boolean $onlyNew) {
        Bookmarks bookmarks = Bookmarks.INSTANCE;
        Context context = this$0.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        Pair<String, Integer> export = bookmarks.export(context, $onlyNew);
        final String err = export.component1();
        final int n = export.component2().intValue();
        this$0.getMain().post(new Runnable() { // from class: com.starpilot.starview.BookmarksView$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                BookmarksView.paint$exportNow$lambda$2$lambda$1(this$0, err, n);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void paint$exportNow$lambda$2$lambda$1(BookmarksView this$0, String $err, int $n) {
        String str;
        Context context = this$0.getContext();
        if ($err == null) {
            str = "Exported " + $n + " bookmark" + ($n == 1 ? "" : "s") + " to Download/StarView/Bookmarks";
        } else {
            str = $err;
        }
        Toast.makeText(context, str, 1).show();
        this$0.paint();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paint$lambda$3(BookmarksView this$0) {
        paint$exportNow(this$0, true);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paint$lambda$4(BookmarksView this$0) {
        paint$exportNow(this$0, false);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paint$lambda$11(BookmarksView this$0, Bookmarks.Item $it) {
        this$0.play(new File($it.getDir(), "voice.wav"));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paint$lambda$12(BookmarksView this$0, Bookmarks.Item $it) {
        this$0.edit($it);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paint$lambda$14(final BookmarksView this$0, final Bookmarks.Item $it) {
        new AlertDialog.Builder(this$0.getContext()).setTitle("Delete this bookmark?").setPositiveButton("Delete", new DialogInterface.OnClickListener() { // from class: com.starpilot.starview.BookmarksView$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                BookmarksView.paint$lambda$14$lambda$13($it, this$0, dialogInterface, i);
            }
        }).setNegativeButton("Cancel", (DialogInterface.OnClickListener) null).show();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void paint$lambda$14$lambda$13(Bookmarks.Item $it, BookmarksView this$0, DialogInterface dialogInterface, int i) {
        Bookmarks.INSTANCE.delete($it);
        this$0.paint();
    }

    private final void play(File f) {
        try {
            MediaPlayer mediaPlayer = this.player;
            if (mediaPlayer != null) {
                mediaPlayer.release();
            }
            MediaPlayer $this$play_u24lambda_u2415 = new MediaPlayer();
            $this$play_u24lambda_u2415.setDataSource(f.getPath());
            $this$play_u24lambda_u2415.prepare();
            $this$play_u24lambda_u2415.start();
            this.player = $this$play_u24lambda_u2415;
        } catch (Exception e) {
            Toast.makeText(getContext(), "Can't play: " + e.getMessage(), 0).show();
        }
    }

    private final void edit(final Bookmarks.Item it) {
        final EditText $this$edit_u24lambda_u2416 = new EditText(getContext());
        $this$edit_u24lambda_u2416.setText(it.getNote());
        $this$edit_u24lambda_u2416.setHint("What happened here?");
        $this$edit_u24lambda_u2416.setMinLines(2);
        new AlertDialog.Builder(getContext()).setTitle("Bookmark note").setView($this$edit_u24lambda_u2416).setPositiveButton("Save", new DialogInterface.OnClickListener() { // from class: com.starpilot.starview.BookmarksView$$ExternalSyntheticLambda7
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                BookmarksView.edit$lambda$18(it, this, $this$edit_u24lambda_u2416, dialogInterface, i);
            }
        }).setNegativeButton("Cancel", (DialogInterface.OnClickListener) null).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void edit$lambda$18(Bookmarks.Item $it, BookmarksView this$0, final EditText $et, DialogInterface dialogInterface, int i) {
        Bookmarks.INSTANCE.update($it, new Function1() { // from class: com.starpilot.starview.BookmarksView$$ExternalSyntheticLambda9
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit edit$lambda$18$lambda$17;
                edit$lambda$18$lambda$17 = BookmarksView.edit$lambda$18$lambda$17($et, (JSONObject) obj);
                return edit$lambda$18$lambda$17;
            }
        });
        this$0.paint();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit edit$lambda$18$lambda$17(EditText $et, JSONObject j) {
        Intrinsics.checkNotNullParameter(j, "j");
        j.put("note", StringsKt.trim((CharSequence) $et.getText().toString()).toString());
        return Unit.INSTANCE;
    }

    public final void stop() {
        try {
            MediaPlayer mediaPlayer = this.player;
            if (mediaPlayer != null) {
                mediaPlayer.release();
            }
        } catch (Exception e) {
        }
        this.player = null;
    }
}
