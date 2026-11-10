package com.starpilot.starview;

import android.graphics.Color;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.text.Regex;

/* compiled from: GxSystem.kt */
public final class GxSystemKt {
    static final int GxSystem_DANGER = Color.rgb(255, 105, 105);
    static final Regex GxSystem_SHA = new Regex("^[a-f0-9]{40}$");
    static final List<String> GxSystem_CORE = CollectionsKt.listOf(new String[]{"StarPilot", "Dom"});
}
