package com.starpilot.starview;

import java.util.HashMap;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

final class GxThemeEntry {
   private final boolean builtin;
   private final HashMap<String, Boolean> has;
   private final HashMap<String, Boolean> local;
   private final String name;
   private final String path;
   private final boolean pseudo;
   private final String type;
   private final boolean userCreated;

   public GxThemeEntry(String var1, String var2, String var3, boolean var4, boolean var5, boolean var6) {
      Intrinsics.checkNotNullParameter(var1, "name");
      Intrinsics.checkNotNullParameter(var2, "path");
      Intrinsics.checkNotNullParameter(var3, "type");
      super();
      this.name = var1;
      this.path = var2;
      this.type = var3;
      this.userCreated = var4;
      this.pseudo = var5;
      this.builtin = var6;
      this.has = new HashMap<>();
      this.local = new HashMap<>();
   }

   // $VF: synthetic method
   public GxThemeEntry(String var1, String var2, String var3, boolean var4, boolean var5, boolean var6, int var7, DefaultConstructorMarker var8) {
      if ((var7 & 16) != 0) {
         var5 = false;
      }

      if ((var7 & 32) != 0) {
         var6 = false;
      }

      this(var1, var2, var3, var4, var5, var6);
   }

   public final boolean getBuiltin() {
      return this.builtin;
   }

   public final HashMap<String, Boolean> getHas() {
      return this.has;
   }

   public final HashMap<String, Boolean> getLocal() {
      return this.local;
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
}
