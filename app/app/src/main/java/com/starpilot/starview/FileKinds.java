package com.starpilot.starview;

import java.util.Locale;
import java.util.Set;
import kotlin.collections.SetsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.StringsKt;

public final class FileKinds {
   private static final Set<String> ARCHIVE = SetsKt.setOf(new String[]{"zip", "tar", "gz", "zst", "bz2", "xz", "7z", "apk", "tgz"});
   private static final int CODE = -8604417;
   private static final int DOC = -5589304;
   private static final int FOLDER = -11549705;
   private static final Set<String> IMAGE = SetsKt.setOf(new String[]{"jpg", "jpeg", "png", "gif", "webp", "bmp"});
   public static final FileKinds INSTANCE = new FileKinds();
   private static final int LOGC = -9508952;
   private static final int MEDIA = -28728;
   private static final int PACK = -22180;
   private static final Set<String> TEXT = SetsKt.setOf(
      new String[]{
         "txt",
         "log",
         "md",
         "py",
         "sh",
         "conf",
         "cfg",
         "ini",
         "csv",
         "toml",
         "yaml",
         "yml",
         "service",
         "env",
         "kt",
         "js",
         "html",
         "css",
         "c",
         "h",
         "cc",
         "cpp",
         "patch",
         "diff",
         "xml"
      }
   );
   private static final Set<String> VIDEO = SetsKt.setOf(new String[]{"mp4", "hevc", "ts", "mkv", "mov", "264", "265"});

   private FileKinds() {
   }

   public final FileKinds.Kind of(String var1, boolean var2) {
      Intrinsics.checkNotNullParameter(var1, "name");
      if (var2) {
         return new FileKinds.Kind("folder", -11549705, FileKinds.Preview.NONE);
      } else {
         String var4 = var1.toLowerCase(Locale.ROOT);
         Intrinsics.checkNotNullExpressionValue(var4, "toLowerCase(...)");
         var1 = StringsKt.substringAfterLast(var4, '.', "");
         FileKinds.Kind var6;
         if (new Regex("^(rlog|qlog)(\\.(zst|bz2))?$").matches(var4)) {
            var6 = new FileKinds.Kind("console", -9508952, FileKinds.Preview.NONE);
         } else if (Intrinsics.areEqual(var1, "json")) {
            var6 = new FileKinds.Kind("code", -8604417, FileKinds.Preview.JSON);
         } else if (Intrinsics.areEqual(var1, "log")) {
            var6 = new FileKinds.Kind("console", -9508952, FileKinds.Preview.TEXT);
         } else {
            boolean var3 = false;
            if (SetsKt.setOf(new String[]{"py", "sh", "kt", "js", "c", "h", "cc", "cpp", "patch", "diff"}).contains(var1)) {
               var6 = new FileKinds.Kind("code", -8604417, FileKinds.Preview.TEXT);
            } else if (TEXT.contains(var1)) {
               var6 = new FileKinds.Kind("file", -5589304, FileKinds.Preview.TEXT);
            } else if (IMAGE.contains(var1)) {
               var6 = new FileKinds.Kind("image", -28728, FileKinds.Preview.IMAGE);
            } else if (VIDEO.contains(var1) || Intrinsics.areEqual(var4, "fcamera.hevc") || Intrinsics.areEqual(var4, "ecamera.hevc")) {
               var6 = new FileKinds.Kind("film", -28728, FileKinds.Preview.NONE);
            } else if (ARCHIVE.contains(var1)) {
               var6 = new FileKinds.Kind("archive", -22180, FileKinds.Preview.NONE);
            } else {
               if (var1.length() == 0) {
                  var3 = true;
               }

               if (var3) {
                  var6 = new FileKinds.Kind("file", -5589304, FileKinds.Preview.TEXT);
               } else {
                  var6 = new FileKinds.Kind("file", -5589304, FileKinds.Preview.NONE);
               }
            }
         }

         return var6;
      }
   }

   public final boolean showable(byte[] var1) {
      Intrinsics.checkNotNullParameter(var1, "bytes");
      int var6 = Math.min(var1.length, 4096);
      int var3 = 0;
      int var2 = 0;

      while (true) {
         boolean var8 = false;
         int var5 = 0;
         if (var2 >= var6) {
            if (var6 == 0 || var3 * 20 < var6) {
               var8 = true;
            }

            return var8;
         }

         int var7 = var1[var2] & 255;
         if (var7 == 0) {
            return false;
         }

         label34: {
            if (var7 >= 9) {
               boolean var4 = (boolean)var5;
               if (14 <= var7) {
                  var4 = (boolean)var5;
                  if (var7 < 32) {
                     var4 = true;
                  }
               }

               var5 = var3;
               if (!var4) {
                  break label34;
               }
            }

            var5 = var3 + 1;
         }

         var2++;
         var3 = var5;
      }
   }

   public static final class Kind {
      private final int color;
      private final String icon;
      private final FileKinds.Preview preview;

      public Kind(String var1, int var2, FileKinds.Preview var3) {
         Intrinsics.checkNotNullParameter(var1, "icon");
         Intrinsics.checkNotNullParameter(var3, "preview");
         super();
         this.icon = var1;
         this.color = var2;
         this.preview = var3;
      }

      public final int getColor() {
         return this.color;
      }

      public final String getIcon() {
         return this.icon;
      }

      public final FileKinds.Preview getPreview() {
         return this.preview;
      }
   }

   public static enum Preview {
      IMAGE,
      JSON,
      NONE,
      TEXT;
      private static final EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(FileKinds.Preview.$VALUES);
      private static final FileKinds.Preview[] $VALUES = $values();

      // $VF: synthetic method
      private static final FileKinds.Preview[] $values() {
         return new FileKinds.Preview[]{TEXT, JSON, IMAGE, NONE};
      }

      public static EnumEntries<FileKinds.Preview> getEntries() {
         return $ENTRIES;
      }
   }
}
