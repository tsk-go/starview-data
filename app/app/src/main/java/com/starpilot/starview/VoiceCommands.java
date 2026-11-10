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

    /* VF: from Vineflower (jadx: ) */
    public final VoiceCommands.Cmd parse(String var1) {
        Intrinsics.checkNotNullParameter(var1, "raw");
        var1 = var1.toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(var1, "toLowerCase(...)");
        CharSequence var6 = StringsKt.trim(var1).toString();
        CharSequence var7 = new Regex("[.!?,]").replace(var6, "");
        var1 = new Regex("^(hey )?(starview|star view|ok)\\s+").replace(var7, "");
        boolean var2 = parse$has(var1, "off", "stop", "disable", "close", "hide", "quiet", "mute");
        if (!new Regex("^(bookmark|mark)( (this|it|here|that))?$").containsMatchIn(var1) && !Intrinsics.areEqual(var1, "add a bookmark")) {
            MatchResult var3 = (new Regex("^(take me|go|drive|navigate|head|bring me|let's go)( back)?( to)? (home|work)$")).find(var1, 0);
            if (var3 != null) {
                return new VoiceCommands.Cmd(VoiceCommands.Kind.NAVIGATE, var3.getGroupValues().get(4));
            } else {
                var3 = (new Regex("^(navigate|take me|drive|go|directions|route)( me)? to (.+)$")).find(var1, 0);
                if (var3 != null) {
                    return new VoiceCommands.Cmd(
                        VoiceCommands.Kind.NAVIGATE,
                        StringsKt.trim(StringsKt.removePrefix(StringsKt.trim(var3.getGroupValues().get(3)).toString(), "my ")).toString()
                    );
                } else {
                    var3 = (new Regex("^(take|make) (the )?(next )?(left|right)( turn)?$")).find(var1, 0);
                    if (var3 != null) {
                        VoiceCommands.Kind var16 = VoiceCommands.Kind.NUDGE;
                        var3 = var3.getGroupValues().get(4);
                        return new VoiceCommands.Cmd(var16, "turn_" + var3);
                    } else if (new Regex("^(move|go|shift|scoot)( over)?( a (bit|little))?( to the)? (left|right)( a (bit|little))?$").containsMatchIn(var1)) {
                        VoiceCommands.Kind var19 = VoiceCommands.Kind.NUDGE;
                        CharSequence var4 = var1;
                        var1 = "left";
                        if (!StringsKt.contains(var4, "left", false)) {
                            var1 = "right";
                        }

                        return new VoiceCommands.Cmd(var19, var1);
                    } else if (new Regex("^(back to the )?(center|centre|middle)( of the lane)?$").containsMatchIn(var1)) {
                        return new VoiceCommands.Cmd(VoiceCommands.Kind.NUDGE, "center");
                    } else if (new Regex("(more room|stay behind|give (it|them|him|her) (some )?room|back off)").containsMatchIn(var1)) {
                        return new VoiceCommands.Cmd(VoiceCommands.Kind.NUDGE, "room");
                    } else {
                        VoiceCommands.Cmd var10;
                        if (parse$has(var1, "battery", "saver", "power saving", "power save")) {
                            VoiceCommands.Kind var9;
                            if (var2) {
                                var9 = VoiceCommands.Kind.SAVER_OFF;
                            } else {
                                var9 = VoiceCommands.Kind.SAVER_ON;
                            }

                            var10 = new VoiceCommands.Cmd(var9, null, 2, null);
                        } else if (parse$has(var1, "experimental")) {
                            VoiceCommands.Kind var11;
                            if (var2) {
                                var11 = VoiceCommands.Kind.EXP_OFF;
                            } else {
                                var11 = VoiceCommands.Kind.EXP_ON;
                            }

                            var10 = new VoiceCommands.Cmd(var11, null, 2, null);
                        } else if (parse$has(var1, "chill")) {
                            var10 = new VoiceCommands.Cmd(VoiceCommands.Kind.EXP_OFF, null, 2, null);
                        } else if (parse$has(var1, "map")) {
                            VoiceCommands.Kind var12;
                            if (var2) {
                                var12 = VoiceCommands.Kind.MAP_CLOSE;
                            } else {
                                var12 = VoiceCommands.Kind.MAP_OPEN;
                            }

                            var10 = new VoiceCommands.Cmd(var12, null, 2, null);
                        } else if (parse$has(var1, "focus")) {
                            VoiceCommands.Kind var13;
                            if (var2) {
                                var13 = VoiceCommands.Kind.FOCUS_OFF;
                            } else {
                                var13 = VoiceCommands.Kind.FOCUS_ON;
                            }

                            var10 = new VoiceCommands.Cmd(var13, null, 2, null);
                        } else if (parse$has(var1, "voice", "talk", "speak")) {
                            VoiceCommands.Kind var14;
                            if (var2) {
                                var14 = VoiceCommands.Kind.VOICE_OFF;
                            } else {
                                var14 = VoiceCommands.Kind.VOICE_ON;
                            }

                            var10 = new VoiceCommands.Cmd(var14, null, 2, null);
                        } else if (parse$has(var1, "keep", "save") && parse$has(var1, "drive", "this", "recording", "clip", "video")) {
                            var10 = new VoiceCommands.Cmd(VoiceCommands.Kind.KEEP, null, 2, null);
                        } else if (parse$has(var1, "aggressive")) {
                            var10 = new VoiceCommands.Cmd(VoiceCommands.Kind.FOLLOW, "0");
                        } else if (parse$has(var1, "standard", "normal")) {
                            var10 = new VoiceCommands.Cmd(VoiceCommands.Kind.FOLLOW, "1");
                        } else if (parse$has(var1, "relaxed")) {
                            var10 = new VoiceCommands.Cmd(VoiceCommands.Kind.FOLLOW, "2");
                        } else if (parse$has(var1, "farther", "further", "more room", "back off")) {
                            var10 = new VoiceCommands.Cmd(VoiceCommands.Kind.FARTHER, null, 2, null);
                        } else if (parse$has(var1, "closer", "tighter")) {
                            var10 = new VoiceCommands.Cmd(VoiceCommands.Kind.CLOSER, null, 2, null);
                        } else if (parse$has(var1, "scene", "3d")) {
                            var10 = new VoiceCommands.Cmd(VoiceCommands.Kind.SCENE, null, 2, null);
                        } else if (parse$has(var1, "camera", "video")) {
                            var10 = new VoiceCommands.Cmd(VoiceCommands.Kind.CAMERA, null, 2, null);
                        } else {
                            var10 = new VoiceCommands.Cmd(VoiceCommands.Kind.UNKNOWN, null, 2, null);
                        }

                        return var10;
                    }
                }
            }
        } else {
            return new VoiceCommands.Cmd(VoiceCommands.Kind.BOOKMARK, null, 2, null);
        }
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
