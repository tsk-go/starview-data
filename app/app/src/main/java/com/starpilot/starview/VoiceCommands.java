package com.starpilot.starview;

import java.util.Locale;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.MatchResult;
import kotlin.text.Regex;
import kotlin.text.StringsKt;

public final class VoiceCommands {
   public static final VoiceCommands INSTANCE = new VoiceCommands();

   private VoiceCommands() {
   }

   private static final boolean parse$has(String var0, String... var1) {
      int var3 = var1.length;
      boolean var4 = false;

      for (int var2 = 0; var2 < var3; var2++) {
         String var5 = var1[var2];
         if (new Regex("\\b" + var5 + "\\b").containsMatchIn(var0)) {
            var4 = true;
            break;
         }
      }

      return var4;
   }

   public final VoiceCommands.Cmd parse(String var1) {
      Intrinsics.checkNotNullParameter(var1, "raw");
      var1 = var1.toLowerCase(Locale.ROOT);
      Intrinsics.checkNotNullExpressionValue(var1, "toLowerCase(...)");
      CharSequence var6 = StringsKt.trim(var1).toString();
      CharSequence var7 = new Regex("[.!?,]").replace(var6, "");
      var1 = new Regex("^(hey )?(starview|star view|ok)\\s+").replace(var7, "");
      boolean var2 = parse$has(var1, "off", "stop", "disable", "close", "hide", "quiet", "mute");
      if (!new Regex("^(bookmark|mark)( (this|it|here|that))?$").containsMatchIn(var1) && !Intrinsics.areEqual(var1, "add a bookmark")) {
         MatchResult var3 = Regex.find$default(new Regex("^(take me|go|drive|navigate|head|bring me|let's go)( back)?( to)? (home|work)$"), var1, 0, 2, null);
         if (var3 != null) {
            return new VoiceCommands.Cmd(VoiceCommands.Kind.NAVIGATE, var3.getGroupValues().get(4));
         } else {
            var3 = Regex.find$default(new Regex("^(navigate|take me|drive|go|directions|route)( me)? to (.+)$"), var1, 0, 2, null);
            if (var3 != null) {
               return new VoiceCommands.Cmd(
                  VoiceCommands.Kind.NAVIGATE,
                  StringsKt.trim(StringsKt.removePrefix(StringsKt.trim(var3.getGroupValues().get(3)).toString(), "my ")).toString()
               );
            } else {
               var3 = Regex.find$default(new Regex("^(take|make) (the )?(next )?(left|right)( turn)?$"), var1, 0, 2, null);
               if (var3 != null) {
                  VoiceCommands.Kind var16 = VoiceCommands.Kind.NUDGE;
                  var3 = var3.getGroupValues().get(4);
                  return new VoiceCommands.Cmd(var16, "turn_" + var3);
               } else if (new Regex("^(move|go|shift|scoot)( over)?( a (bit|little))?( to the)? (left|right)( a (bit|little))?$").containsMatchIn(var1)) {
                  VoiceCommands.Kind var19 = VoiceCommands.Kind.NUDGE;
                  CharSequence var4 = var1;
                  var1 = "left";
                  if (!StringsKt.contains$default(var4, "left", false, 2, null)) {
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

   public static final class Cmd {
      private final String arg;
      private final VoiceCommands.Kind kind;

      public Cmd(VoiceCommands.Kind var1, String var2) {
         Intrinsics.checkNotNullParameter(var1, "kind");
         Intrinsics.checkNotNullParameter(var2, "arg");
         super();
         this.kind = var1;
         this.arg = var2;
      }

      // $VF: synthetic method
      public Cmd(VoiceCommands.Kind var1, String var2, int var3, DefaultConstructorMarker var4) {
         if ((var3 & 2) != 0) {
            var2 = "";
         }

         this(var1, var2);
      }

      public final String getArg() {
         return this.arg;
      }

      public final VoiceCommands.Kind getKind() {
         return this.kind;
      }
   }

   public static enum Kind {
      BOOKMARK,
      CAMERA,
      CLOSER,
      EXP_OFF,
      EXP_ON,
      FARTHER,
      FOCUS_OFF,
      FOCUS_ON,
      FOLLOW,
      KEEP,
      MAP_CLOSE,
      MAP_OPEN,
      NAVIGATE,
      NUDGE,
      SAVER_OFF,
      SAVER_ON,
      SCENE,
      UNKNOWN,
      VOICE_OFF,
      VOICE_ON;
      private static final EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(VoiceCommands.Kind.$VALUES);
      private static final VoiceCommands.Kind[] $VALUES = $values();

      // $VF: synthetic method
      private static final VoiceCommands.Kind[] $values() {
         return new VoiceCommands.Kind[]{
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
            UNKNOWN
         };
      }

      public static EnumEntries<VoiceCommands.Kind> getEntries() {
         return $ENTRIES;
      }
   }
}
