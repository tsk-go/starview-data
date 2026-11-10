package com.starpilot.starview;

import android.view.View;
import android.widget.TextView;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: CatalogPanel.kt */
public final class CatalogPanelKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final void txt(TextView $this$txt, CharSequence s) {
        if (Intrinsics.areEqual($this$txt.getText().toString(), s.toString())) {
            return;
        }
        $this$txt.setText(s);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void vis(View $this$vis, boolean on) {
        int v = on ? 0 : 8;
        if ($this$vis.getVisibility() != v) {
            $this$vis.setVisibility(v);
        }
    }
}
