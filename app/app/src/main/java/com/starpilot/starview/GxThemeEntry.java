package com.starpilot.starview;

import java.util.HashMap;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: GxTheme.kt */
final class GxThemeEntry {
    private final boolean builtin;
    private final HashMap<String, Boolean> has;
    private final HashMap<String, Boolean> local;
    private final String name;
    private final String path;
    private final boolean pseudo;
    private final String type;
    private final boolean userCreated;

    public GxThemeEntry(String name, String path, String type, boolean userCreated, boolean pseudo, boolean builtin) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(path, "path");
        Intrinsics.checkNotNullParameter(type, "type");
        this.name = name;
        this.path = path;
        this.type = type;
        this.userCreated = userCreated;
        this.pseudo = pseudo;
        this.builtin = builtin;
        this.has = new HashMap<>();
        this.local = new HashMap<>();
    }

    public /* synthetic */ GxThemeEntry(String str, String str2, String str3, boolean z, boolean z2, boolean z3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, str3, z, (i & 16) != 0 ? false : z2, (i & 32) != 0 ? false : z3);
    }

    public final boolean getBuiltin() {
        return this.builtin;
    }

    public final String getName() {
        return this.name;
    }

    public final String getPath() {
        return this.path;
    }

    public final boolean getPseudo() {
        return this.pseudo;
    }

    public final String getType() {
        return this.type;
    }

    public final boolean getUserCreated() {
        return this.userCreated;
    }

    public final HashMap<String, Boolean> getHas() {
        return this.has;
    }

    public final HashMap<String, Boolean> getLocal() {
        return this.local;
    }
}
