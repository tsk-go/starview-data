package com.starpilot.starview;


/* compiled from: IdeasMore.kt */
public final class WantIdeas {
    public static final WantIdeas INSTANCE = new WantIdeas();
    private static volatile boolean schoolBus;
    private static volatile boolean wipers;

    private WantIdeas() {
    }

    public final boolean getSchoolBus() {
        return schoolBus;
    }

    public final void setSchoolBus(boolean z) {
        schoolBus = z;
    }

    public final boolean getWipers() {
        return wipers;
    }

    public final void setWipers(boolean z) {
        wipers = z;
    }
}
