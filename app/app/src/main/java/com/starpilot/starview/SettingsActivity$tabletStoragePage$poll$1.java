package com.starpilot.starview;

import android.os.Handler;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import kotlin.jvm.internal.Ref;
import org.json.JSONObject;

/* compiled from: SettingsActivity.kt */
public final class SettingsActivity$tabletStoragePage$poll$1 implements Runnable {
    final /* synthetic */ Ref.BooleanRef $copyingNow;
    final /* synthetic */ LinearLayout $list;
    final /* synthetic */ Ref.BooleanRef $seen;
    final /* synthetic */ ScrollView $sv;
    final /* synthetic */ SettingsActivity this$0;

    SettingsActivity$tabletStoragePage$poll$1(ScrollView $sv, Ref.BooleanRef $seen, SettingsActivity $receiver, Ref.BooleanRef $copyingNow, LinearLayout $list) {
        this.$sv = $sv;
        this.$seen = $seen;
        this.this$0 = $receiver;
        this.$copyingNow = $copyingNow;
        this.$list = $list;
    }

    @Override // java.lang.Runnable
    public void run() {
        String h;
        Handler handler;
        Handler handler2;
        if (this.$sv.isAttachedToWindow()) {
            this.$seen.element = true;
        } else if (this.$seen.element) {
            return;
        }
        h = this.this$0.host;
        if (h == null) {
            h = Link.INSTANCE.getHost();
        }
        if (h == null) {
            handler2 = this.this$0.handler;
            handler2.postDelayed(this, 3000L);
            return;
        }
        final SettingsActivity settingsActivity = this.this$0;
        final ScrollView scrollView = this.$sv;
        final Ref.BooleanRef booleanRef = this.$seen;
        final LinearLayout linearLayout = this.$list;
        final Ref.BooleanRef booleanRef2 = this.$copyingNow;
        final String str = h;
        new Thread(new Runnable() { // from class: com.starpilot.starview.SettingsActivity$tabletStoragePage$poll$1$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                SettingsActivity$tabletStoragePage$poll$1.run$lambda$1(str, settingsActivity, scrollView, booleanRef, linearLayout, booleanRef2);
            }
        }).start();
        handler = this.this$0.handler;
        handler.postDelayed(this, this.$copyingNow.element ? 3000L : 10000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void run$lambda$1(String $h, final SettingsActivity this$0, final ScrollView $sv, final Ref.BooleanRef $seen, final LinearLayout $list, final Ref.BooleanRef $copyingNow) {
        Handler handler;
        final JSONObject j = StorageInfo.fetch$default(StorageInfo.INSTANCE, $h, 0, 2, null);
        handler = this$0.handler;
        handler.post(new Runnable() { // from class: com.starpilot.starview.SettingsActivity$tabletStoragePage$poll$1$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                SettingsActivity$tabletStoragePage$poll$1.run$lambda$1$lambda$0($sv, $seen, j, $list, $copyingNow, this$0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void run$lambda$1$lambda$0(ScrollView $sv, Ref.BooleanRef $seen, JSONObject $j, LinearLayout $list, Ref.BooleanRef $copyingNow, SettingsActivity this$0) {
        if ($sv.isAttachedToWindow() || !$seen.element) {
            SettingsActivity.tabletStoragePage$render($list, $copyingNow, this$0, $j);
        }
    }
}
