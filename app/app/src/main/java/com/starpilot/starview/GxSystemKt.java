package com.starpilot.starview;

import android.graphics.Color;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.text.Regex;

/* compiled from: GxSystem.kt */
public final class GxSystemKt {
    private static final int GxSystem_DANGER = Color.rgb(255, 105, 105);
    private static final Regex GxSystem_SHA = new Regex("^[a-f0-9]{40}$");
    private static final List<String> GxSystem_CORE = CollectionsKt.listOf((Object[]) new String[]{"StarPilot", "Dom"});
}
