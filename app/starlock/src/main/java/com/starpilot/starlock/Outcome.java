package com.starpilot.starlock;

import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/* compiled from: Door.kt */
public enum Outcome {
    CONFIRMED,
    SENT,
    FAILED;

    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

    public static EnumEntries<Outcome> getEntries() {
        return $ENTRIES;
    }
}
