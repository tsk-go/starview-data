package com.starpilot.starview;

import android.os.Handler;

/* compiled from: LaneSuggest.kt */
public final class LaneSuggestView$repeat$1 implements Runnable {
    final /* synthetic */ LaneSuggestView this$0;

    LaneSuggestView$repeat$1(LaneSuggestView $receiver) {
        this.this$0 = $receiver;
    }

    @Override // java.lang.Runnable
    public void run() {
        int i;
        int i2;
        String str;
        Handler handler;
        i = this.this$0.holding;
        if (i != 0) {
            LaneSuggestView laneSuggestView = this.this$0;
            i2 = this.this$0.holding;
            String str2 = i2 < 0 ? "left" : "right";
            str = this.this$0.holdWhy;
            laneSuggestView.send(str2, str);
            handler = this.this$0.main;
            handler.postDelayed(this, 250L);
        }
    }
}
