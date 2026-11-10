package com.starpilot.starview;

import com.starpilot.starview.GxPage;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.text.Regex;
import kotlin.text.RegexOption;
import kotlin.text.StringsKt;

/* compiled from: GxTheme.kt */
public final class GxThemePage$startAssetDownload$1$r$1 implements Runnable {
    final /* synthetic */ String $name;
    final /* synthetic */ Ref.IntRef $polls;
    final /* synthetic */ String $what;
    final /* synthetic */ GxThemePage this$0;

    GxThemePage$startAssetDownload$1$r$1(GxThemePage $receiver, String $what, String $name, Ref.IntRef $polls) {
        this.this$0 = $receiver;
        this.$what = $what;
        this.$name = $name;
        this.$polls = $polls;
    }

    @Override // java.lang.Runnable
    public void run() {
        GxThemePage gxThemePage = this.this$0;
        final GxThemePage gxThemePage2 = this.this$0;
        final String str = this.$what;
        final String str2 = this.$name;
        final Ref.IntRef intRef = this.$polls;
        gxThemePage.getText("/api/params_memory?key=ThemeDownloadProgress", new Function2() { // from class: com.starpilot.starview.GxThemePage$startAssetDownload$1$r$1$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit run$lambda$0;
                run$lambda$0 = GxThemePage$startAssetDownload$1$r$1.run$lambda$0(gxThemePage2, str, str2, intRef, GxThemePage$startAssetDownload$1$r$1.this, (String) obj, (String) obj2);
                return run$lambda$0;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit run$lambda$0(GxThemePage this$0, String $what, String $name, Ref.IntRef $polls, GxThemePage$startAssetDownload$1$r$1 this$1, String str, String t) {
        GxPage.Row row;
        GxPage.Row row2;
        GxPage.Row row3;
        GxPage.Row row4;
        Intrinsics.checkNotNullParameter(t, "t");
        String txt = StringsKt.trim((CharSequence) t).toString();
        GxPage.Row row5 = null;
        if (txt.length() > 0) {
            row4 = this$0.dlRow;
            if (row4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("dlRow");
                row4 = null;
            }
            row4.setDesc(txt);
        }
        boolean done = new Regex("Downloaded!", RegexOption.IGNORE_CASE).containsMatchIn(txt);
        boolean bad = new Regex("failed|cancelled", RegexOption.IGNORE_CASE).containsMatchIn(txt);
        if (done) {
            this$0.downloading = false;
            row3 = this$0.dlRow;
            if (row3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("dlRow");
            } else {
                row5 = row3;
            }
            row5.setVisible(false);
            this$0.toast("Downloaded " + $what + " for \"" + $name + "\".");
            this$0.refreshThemeList();
            this$0.fetchDownloadables();
        } else if (bad) {
            this$0.downloading = false;
            row2 = this$0.dlRow;
            if (row2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("dlRow");
            } else {
                row5 = row2;
            }
            row5.setVisible(false);
            this$0.toast("Download " + txt);
        } else {
            $polls.element++;
            if ($polls.element > 900) {
                this$0.downloading = false;
                row = this$0.dlRow;
                if (row == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("dlRow");
                } else {
                    row5 = row;
                }
                row5.setVisible(false);
                this$0.toast("Download timed out.");
            } else {
                this$0.getMain().postDelayed(this$1, 1000L);
            }
        }
        return Unit.INSTANCE;
    }
}
