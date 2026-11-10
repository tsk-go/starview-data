package com.starpilot.starview;

import java.util.Locale;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.MatchResult;
import kotlin.text.Regex;
import kotlin.text.StringsKt;

/* compiled from: VoiceCommands.kt */
public final class VoiceCommands {
    public static final VoiceCommands INSTANCE = new VoiceCommands();

    /* compiled from: VoiceCommands.kt */
    public enum Kind {
        BOOKMARK,
        EXP_ON,
        EXP_OFF,
        MAP_OPEN,
        MAP_CLOSE,
        FOCUS_ON,
        FOCUS_OFF,
        VOICE_ON,
        VOICE_OFF,
        SAVER_ON,
        SAVER_OFF,
        KEEP,
        FOLLOW,
        FARTHER,
        CLOSER,
        NAVIGATE,
        SCENE,
        CAMERA,
        NUDGE,
        UNKNOWN;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries($VALUES);

        public static EnumEntries<Kind> getEntries() {
            return $ENTRIES;
        }
    }

    private VoiceCommands() {
    }

    /* compiled from: VoiceCommands.kt */
    public static final class Cmd {
        private final String arg;
        private final Kind kind;

        public Cmd(Kind kind, String arg) {
            Intrinsics.checkNotNullParameter(kind, "kind");
            Intrinsics.checkNotNullParameter(arg, "arg");
            this.kind = kind;
            this.arg = arg;
        }

        public /* synthetic */ Cmd(Kind kind, String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(kind, (i & 2) != 0 ? "" : str);
        }

        public final String getArg() {
            return this.arg;
        }

        public final Kind getKind() {
            return this.kind;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final Cmd parse(String raw) {
        Intrinsics.checkNotNullParameter(raw, "raw");
        String lowerCase = raw.toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
        String replace = new Regex("^(hey )?(starview|star view|ok)\\s+").replace(new Regex("[.!?,]").replace(StringsKt.trim((CharSequence) lowerCase).toString(), ""), "");
        int i = 2;
        boolean parse$has = parse$has(replace, "off", "stop", "disable", "close", "hide", "quiet", "mute");
        String str = null;
        Object[] objArr = 0;
        Object[] objArr2 = 0;
        Object[] objArr3 = 0;
        Object[] objArr4 = 0;
        Object[] objArr5 = 0;
        Object[] objArr6 = 0;
        Object[] objArr7 = 0;
        Object[] objArr8 = 0;
        Object[] objArr9 = 0;
        Object[] objArr10 = 0;
        Object[] objArr11 = 0;
        Object[] objArr12 = 0;
        Object[] objArr13 = 0;
        Object[] objArr14 = 0;
        Object[] objArr15 = 0;
        Object[] objArr16 = 0;
        Object[] objArr17 = 0;
        Object[] objArr18 = 0;
        Object[] objArr19 = 0;
        Object[] objArr20 = 0;
        Object[] objArr21 = 0;
        Object[] objArr22 = 0;
        Object[] objArr23 = 0;
        Object[] objArr24 = 0;
        Object[] objArr25 = 0;
        if (new Regex("^(bookmark|mark)( (this|it|here|that))?$").containsMatchIn(replace) || Intrinsics.areEqual(replace, "add a bookmark")) {
            return new Cmd(Kind.BOOKMARK, objArr2 == true ? 1 : 0, i, objArr == true ? 1 : 0);
        }
        MatchResult find$default = Regex.find$default(new Regex("^(take me|go|drive|navigate|head|bring me|let's go)( back)?( to)? (home|work)$"), replace, 0, 2, null);
        if (find$default != null) {
            return new Cmd(Kind.NAVIGATE, find$default.getGroupValues().get(4));
        }
        MatchResult find$default2 = Regex.find$default(new Regex("^(navigate|take me|drive|go|directions|route)( me)? to (.+)$"), replace, 0, 2, null);
        if (find$default2 != null) {
            return new Cmd(Kind.NAVIGATE, StringsKt.trim((CharSequence) StringsKt.removePrefix(StringsKt.trim((CharSequence) find$default2.getGroupValues().get(3)).toString(), (CharSequence) "my ")).toString());
        }
        MatchResult find$default3 = Regex.find$default(new Regex("^(take|make) (the )?(next )?(left|right)( turn)?$"), replace, 0, 2, null);
        if (find$default3 != null) {
            return new Cmd(Kind.NUDGE, "turn_" + ((Object) find$default3.getGroupValues().get(4)));
        }
        if (new Regex("^(move|go|shift|scoot)( over)?( a (bit|little))?( to the)? (left|right)( a (bit|little))?$").containsMatchIn(replace)) {
            return new Cmd(Kind.NUDGE, StringsKt.contains$default((CharSequence) replace, (CharSequence) "left", false, 2, (Object) null) ? "left" : "right");
        }
        if (new Regex("^(back to the )?(center|centre|middle)( of the lane)?$").containsMatchIn(replace)) {
            return new Cmd(Kind.NUDGE, "center");
        }
        if (new Regex("(more room|stay behind|give (it|them|him|her) (some )?room|back off)").containsMatchIn(replace)) {
            return new Cmd(Kind.NUDGE, "room");
        }
        if (parse$has(replace, "battery", "saver", "power saving", "power save")) {
            return new Cmd(parse$has ? Kind.SAVER_OFF : Kind.SAVER_ON, str, i, objArr25 == true ? 1 : 0);
        }
        if (parse$has(replace, "experimental")) {
            return new Cmd(parse$has ? Kind.EXP_OFF : Kind.EXP_ON, objArr24 == true ? 1 : 0, i, objArr23 == true ? 1 : 0);
        }
        if (parse$has(replace, "chill")) {
            return new Cmd(Kind.EXP_OFF, objArr22 == true ? 1 : 0, i, objArr21 == true ? 1 : 0);
        }
        if (parse$has(replace, "map")) {
            return new Cmd(parse$has ? Kind.MAP_CLOSE : Kind.MAP_OPEN, objArr20 == true ? 1 : 0, i, objArr19 == true ? 1 : 0);
        }
        if (parse$has(replace, "focus")) {
            return new Cmd(parse$has ? Kind.FOCUS_OFF : Kind.FOCUS_ON, objArr18 == true ? 1 : 0, i, objArr17 == true ? 1 : 0);
        }
        if (parse$has(replace, "voice", "talk", "speak")) {
            return new Cmd(parse$has ? Kind.VOICE_OFF : Kind.VOICE_ON, objArr16 == true ? 1 : 0, i, objArr15 == true ? 1 : 0);
        }
        if (parse$has(replace, "keep", "save") && parse$has(replace, "drive", "this", "recording", "clip", "video")) {
            return new Cmd(Kind.KEEP, objArr14 == true ? 1 : 0, i, objArr13 == true ? 1 : 0);
        }
        if (parse$has(replace, "aggressive")) {
            return new Cmd(Kind.FOLLOW, "0");
        }
        if (parse$has(replace, "standard", "normal")) {
            return new Cmd(Kind.FOLLOW, "1");
        }
        if (parse$has(replace, "relaxed")) {
            return new Cmd(Kind.FOLLOW, "2");
        }
        if (parse$has(replace, "farther", "further", "more room", "back off")) {
            return new Cmd(Kind.FARTHER, objArr12 == true ? 1 : 0, i, objArr11 == true ? 1 : 0);
        }
        if (parse$has(replace, "closer", "tighter")) {
            return new Cmd(Kind.CLOSER, objArr10 == true ? 1 : 0, i, objArr9 == true ? 1 : 0);
        }
        if (parse$has(replace, "scene", "3d")) {
            return new Cmd(Kind.SCENE, objArr8 == true ? 1 : 0, i, objArr7 == true ? 1 : 0);
        }
        if (parse$has(replace, "camera", "video")) {
            return new Cmd(Kind.CAMERA, objArr6 == true ? 1 : 0, i, objArr5 == true ? 1 : 0);
        }
        return new Cmd(Kind.UNKNOWN, objArr4 == true ? 1 : 0, i, objArr3 == true ? 1 : 0);
    }

    private static final boolean parse$has(String t, String... w) {
        for (String str : w) {
            if (new Regex("\\b" + str + "\\b").containsMatchIn(t)) {
                return true;
            }
        }
        return false;
    }
}
