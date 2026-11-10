package com.starpilot.starview;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.starpilot.starview.Bookmarks;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import org.json.JSONArray;
import org.json.JSONObject;
import android.graphics.drawable.Drawable;

/* compiled from: Bookmarks.kt */
public final class BookmarkCard extends LinearLayout {
    private final Function0<Unit> askMic;
    private final TextView close;
    private final float d;
    private final Runnable hide;
    private Bookmarks.Item item;
    private final Handler main;
    private boolean recording;
    private final Function0<Boolean> stopped;
    private final TextView sub;
    private int takes;
    private final TextView talk;
    private final TextView title;
    private final TextView type;
    private VoiceNote voice;

    /* VF: from Vineflower (jadx: ) */
    public BookmarkCard(Context var1, Function0<Boolean> var2, Function0<Unit> var3) {
        super(var1);
        Intrinsics.checkNotNullParameter(var1, "ctx");
        Intrinsics.checkNotNullParameter(var2, "stopped");
        Intrinsics.checkNotNullParameter(var3, "askMic");
        this.stopped = var2;
        this.askMic = var3;
        this.d = this.getResources().getDisplayMetrics().density;
        this.main = new Handler(Looper.getMainLooper());
        TextView var5 = new TextView(var1);
        var5.setTextColor(-1);
        var5.setTextSize(20.0F);
        var5.setTypeface(Typeface.DEFAULT_BOLD);
        this.title = var5;
        TextView var6 = new TextView(var1);
        var6.setTextColor(Color.rgb(200, 208, 222));
        var6.setTextSize(15.0F);
        this.sub = var6;
        this.talk = new TextView(var1);
        this.type = new TextView(var1);
        this.close = new TextView(var1);
        this.hide = new BookmarkCard$$ExternalSyntheticLambda1(this);
        this.setOrientation(1);
        this.setVisibility(8);
        this.setPadding(this.dp(18), this.dp(14), this.dp(18), this.dp(14));
        GradientDrawable var7 = new GradientDrawable();
        var7.setCornerRadius(this.dp(18));
        var7.setColor(Color.argb(225, 18, 20, 26));
        var7.setStroke(this.dp(2), Color.rgb(255, 196, 0));
        this.setBackground((Drawable)var7);
        this.addView((View)this.title);
        this.addView((View)this.sub);
        LinearLayout var4 = new LinearLayout(var1);
        var4.setOrientation(0);
        var4.setPadding(0, this.dp(10), 0, 0);
        _init_$pill(this, var4, this.talk, "\ud83c\udfa4  Hold to talk", Color.rgb(52, 92, 170));
        _init_$pill(this, var4, this.type, "✎  Type", Color.rgb(70, 76, 90));
        _init_$pill(this, var4, this.close, "✕", Color.rgb(70, 76, 90));
        this.addView((View)var4);
        this.talk.setOnTouchListener(new BookmarkCard$$ExternalSyntheticLambda2(this));
        this.type.setOnClickListener(new BookmarkCard$$ExternalSyntheticLambda3(this));
        this.close.setOnClickListener(new BookmarkCard$$ExternalSyntheticLambda4(this));
    }

    private final int dp(int v) {
        return (int) (v * this.d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void hide$lambda$2(BookmarkCard this$0) {
        if (this$0.recording) {
            return;
        }
        this$0.setVisibility(8);
    }

    private static final void _init_$pill(BookmarkCard this$0, LinearLayout row, TextView t, String label, int col) {
        t.setText(label);
        t.setTextColor(-1);
        t.setTextSize(18.0f);
        t.setTypeface(Typeface.DEFAULT_BOLD);
        t.setGravity(17);
        t.setPadding(this$0.dp(18), this$0.dp(12), this$0.dp(18), this$0.dp(12));
        GradientDrawable $this$pill_u24lambda_u245 = new GradientDrawable();
        $this$pill_u24lambda_u245.setCornerRadius(this$0.dp(24));
        $this$pill_u24lambda_u245.setColor(col);
        t.setBackground($this$pill_u24lambda_u245);
        LinearLayout.LayoutParams $this$pill_u24lambda_u246 = new LinearLayout.LayoutParams(-2, -2);
        $this$pill_u24lambda_u246.setMarginEnd(this$0.dp(10));
        Unit unit = Unit.INSTANCE;
        row.addView(t, $this$pill_u24lambda_u246);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean _init_$lambda$7(BookmarkCard this$0, View view, MotionEvent e) {
        switch (e.getActionMasked()) {
            case 0:
                this$0.startTalk();
                break;
            case 1:
            case 3:
                this$0.stopTalk();
                break;
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void _init_$lambda$9(BookmarkCard this$0, View it) {
        this$0.main.removeCallbacks(this$0.hide);
        this$0.setVisibility(8);
    }

    public final void show(Bookmarks.Item it) {
        Intrinsics.checkNotNullParameter(it, "it");
        this.item = it;
        this.takes = 0;
        this.title.setText("🔖  Bookmarked");
        TextView textView = this.sub;
        Iterable $this$filter$iv = CollectionsKt.listOf((Object[]) new String[]{it.getTitle(), it.getPlace()});
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            String s = (String) element$iv$iv;
            Intrinsics.checkNotNull(s);
            if (s.length() > 0) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        String joinToString$default = CollectionsKt.joinToString((List) destination$iv$iv, "  ·  ", "", "", -1, "...", null);
        if (joinToString$default.length() == 0) {
            joinToString$default = "saved";
        }
        textView.setText(joinToString$default);
        this.talk.setText("🎤  Hold to talk");
        this.type.setAlpha(this.stopped.invoke().booleanValue() ? 1.0f : 0.4f);
        setVisibility(0);
        bringToFront();
        this.main.removeCallbacks(this.hide);
        this.main.postDelayed(this.hide, 10000L);
    }

    private final void startTalk() {
        final String name;
        final Bookmarks.Item it = this.item;
        if (it == null) {
            return;
        }
        if (getContext().checkSelfPermission("android.permission.RECORD_AUDIO") != 0) {
            this.askMic.invoke();
            return;
        }
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        VoiceNote v = new VoiceNote(context);
        this.voice = v;
        this.takes++;
        if (this.takes == 1) {
            name = "voice.wav";
        } else {
            name = "voice" + this.takes + ".wav";
        }
        String err = v.start(new File(it.getDir(), name), new Function1() { // from class: com.starpilot.starview.BookmarkCard$$ExternalSyntheticLambda7
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit startTalk$lambda$16;
                startTalk$lambda$16 = BookmarkCard.startTalk$lambda$16(it, this, name, (String) obj);
                return startTalk$lambda$16;
            }
        });
        if (err != null) {
            this.sub.setText("Couldn't record: " + err);
            return;
        }
        this.recording = true;
        this.talk.setText("●  Recording… let go to stop");
        this.main.removeCallbacks(this.hide);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit startTalk$lambda$16(Bookmarks.Item $it, BookmarkCard this$0, final String $name, final String text) {
        Intrinsics.checkNotNullParameter(text, "text");
        Bookmarks.INSTANCE.update($it, new Function1() { // from class: com.starpilot.starview.BookmarkCard$$ExternalSyntheticLambda8
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit startTalk$lambda$16$lambda$15;
                startTalk$lambda$16$lambda$15 = BookmarkCard.startTalk$lambda$16$lambda$15($name, text, (JSONObject) obj);
                return startTalk$lambda$16$lambda$15;
            }
        });
        this$0.sub.setText(text.length() > 0 ? "“" + text + "”" : "voice note saved (no transcript on this tablet)");
        if (!this$0.recording) {
            this$0.main.removeCallbacks(this$0.hide);
            this$0.main.postDelayed(this$0.hide, 6000L);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit startTalk$lambda$16$lambda$15(String $name, String $text, JSONObject j) {
        String s;
        Intrinsics.checkNotNullParameter(j, "j");
        JSONArray x = j.optJSONArray("voices");
        if (x == null) {
            x = new JSONArray();
            j.put("voices", x);
        }
        JSONArray a = x;
        a.put(new JSONObject().put("file", $name).put("text", $text));
        Iterable $this$mapNotNull$iv = RangesKt.until(0, a.length());
        Collection destination$iv$iv = new ArrayList();
        Iterator<Integer> it = $this$mapNotNull$iv.iterator();
        while (it.hasNext()) {
            int element$iv$iv$iv = ((IntIterator) it).nextInt();
            Iterable $this$mapNotNull$iv2 = $this$mapNotNull$iv;
            JSONObject optJSONObject = a.optJSONObject(element$iv$iv$iv);
            String str = null;
            if (optJSONObject != null && (s = optJSONObject.optString("text")) != null) {
                if (s.length() > 0) {
                    str = s;
                }
            }
            if (str != null) {
                destination$iv$iv.add(str);
            }
            $this$mapNotNull$iv = $this$mapNotNull$iv2;
        }
        j.put("voice_text", CollectionsKt.joinToString((List) destination$iv$iv, " / ", "", "", -1, "...", null));
        return Unit.INSTANCE;
    }

    private final void stopTalk() {
        if (this.recording) {
            this.recording = false;
            VoiceNote voiceNote = this.voice;
            if (voiceNote != null) {
                voiceNote.stop();
            }
            this.talk.setText("🎤  Hold to add more");
            this.sub.setText("saving…");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void typeNote() {
        final Bookmarks.Item it = this.item;
        if (it == null) {
            return;
        }
        if (!this.stopped.invoke().booleanValue()) {
            this.sub.setText("Typing only when stopped - hold 🎤 to talk instead");
            return;
        }
        this.main.removeCallbacks(this.hide);
        final EditText $this$typeNote_u24lambda_u2417 = new EditText(getContext());
        $this$typeNote_u24lambda_u2417.setText(it.getNote());
        $this$typeNote_u24lambda_u2417.setHint("What happened here?");
        $this$typeNote_u24lambda_u2417.setSingleLine(false);
        $this$typeNote_u24lambda_u2417.setMinLines(2);
        new AlertDialog.Builder(getContext()).setTitle("Bookmark note").setView($this$typeNote_u24lambda_u2417).setPositiveButton("Save", new DialogInterface.OnClickListener() { // from class: com.starpilot.starview.BookmarkCard$$ExternalSyntheticLambda5
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                BookmarkCard.typeNote$lambda$19($this$typeNote_u24lambda_u2417, it, this, dialogInterface, i);
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() { // from class: com.starpilot.starview.BookmarkCard$$ExternalSyntheticLambda6
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                BookmarkCard.typeNote$lambda$20(BookmarkCard.this, dialogInterface, i);
            }
        }).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void typeNote$lambda$19(EditText $et, Bookmarks.Item $it, BookmarkCard this$0, DialogInterface dialogInterface, int i) {
        final String t = StringsKt.trim((CharSequence) $et.getText().toString()).toString();
        Bookmarks.INSTANCE.update($it, new Function1() { // from class: com.starpilot.starview.BookmarkCard$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit typeNote$lambda$19$lambda$18;
                typeNote$lambda$19$lambda$18 = BookmarkCard.typeNote$lambda$19$lambda$18(t, (JSONObject) obj);
                return typeNote$lambda$19$lambda$18;
            }
        });
        this$0.sub.setText(t.length() == 0 ? "saved" : "“" + t + "”");
        this$0.main.postDelayed(this$0.hide, 4000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit typeNote$lambda$19$lambda$18(String $t, JSONObject j) {
        Intrinsics.checkNotNullParameter(j, "j");
        j.put("note", $t);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void typeNote$lambda$20(BookmarkCard this$0, DialogInterface dialogInterface, int i) {
        this$0.main.postDelayed(this$0.hide, 4000L);
    }
}
