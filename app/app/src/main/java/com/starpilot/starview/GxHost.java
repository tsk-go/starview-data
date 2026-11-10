package com.starpilot.starview;

import android.net.Uri;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* compiled from: GxKit.kt */
public interface GxHost {
    GalaxyApi getGxApi();

    String getGxHost();

    void openPage(String page);

    void pickFile(String[] mimes, Function1<? super Uri, Unit> then);
}
