package com.starpilot.starview;

import java.util.Map;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import kotlin.text.StringsKt;
import org.json.JSONObject;

public final class FastUpdate {
   public static final FastUpdate INSTANCE = new FastUpdate();
   private static final Map<String, Integer> STAGE_PCT;
   public static final String START = "/api/update/fast";
   public static final String STATUS = "/api/update/fast/status";

   static {
      Pair var3 = TuplesKt.to("preparing", 5);
      Pair var5 = TuplesKt.to("fetching", 25);
      Pair var0 = TuplesKt.to("validating", 50);
      Pair var1 = TuplesKt.to("applying", 70);
      Pair var4 = TuplesKt.to("submodules", 85);
      Integer var6 = 100;
      Pair var2 = TuplesKt.to("rebooting", var6);
      STAGE_PCT = MapsKt.mapOf(new Pair[]{var3, var5, var0, var1, var4, var2, TuplesKt.to("complete", var6)});
   }

   private FastUpdate() {
   }

   private final String str(JSONObject var1, String var2) {
      String var3;
      if (var1.has(var2) && !var1.isNull(var2)) {
         String var4 = var1.optString(var2);
         Intrinsics.checkNotNullExpressionValue(var4, "optString(...)");
         var3 = StringsKt.trim(var4).toString();
      } else {
         var3 = "";
      }

      return var3;
   }

   public final JSONObject from70(JSONObject var1) {
      Intrinsics.checkNotNullParameter(var1, "r");
      JSONObject var13 = var1.optJSONObject("installed");
      JSONObject var11 = var1.optJSONObject("updater");
      JSONObject var12 = var1.optJSONObject("operations");
      if (var11 != null) {
         var1 = var11.optJSONObject("fast");
      } else {
         var1 = null;
      }

      String var5;
      String var9;
      label196: {
         var9 = "";
         if (var1 != null) {
            String var6 = INSTANCE.str(var1, "stage");
            var5 = var6;
            if (var6 != null) {
               break label196;
            }
         }

         var5 = "";
      }

      String var31;
      label191: {
         if (var1 != null) {
            String var16 = INSTANCE.str(var1, "detail");
            var31 = var16;
            if (var16 != null) {
               break label191;
            }
         }

         var31 = "";
      }

      JSONObject var7;
      if (var12 != null) {
         var7 = var12.optJSONObject("request");
      } else {
         var7 = null;
      }

      boolean var2;
      if (var7 != null && Intrinsics.areEqual(this.str(var7, "state"), "pending")) {
         var2 = 1;
      } else {
         var2 = 0;
      }

      boolean var3;
      if (var11 != null && Intrinsics.areEqual(this.str(var11, "state"), "updating...")) {
         var3 = true;
      } else {
         var3 = false;
      }

      JSONObject var14 = new JSONObject();
      boolean var4;
      if ((var3 || var2) && !Intrinsics.areEqual(var5, "error") && !Intrinsics.areEqual(var5, "rebooting")) {
         var4 = true;
      } else {
         var4 = false;
      }

      var14.put("running", var4);
      var14.put("stage", var5);
      label166:
      if (Intrinsics.areEqual(var5, "preparing")) {
         label164: {
            if (var11 != null) {
               String var8 = INSTANCE.str(var11, "state");
               var17 = var8;
               if (var8 != null) {
                  break label164;
               }
            }

            var17 = "";
         }

         switch (var17.hashCode()) {
            case 1268693460:
               if (var17.equals("downloading...")) {
                  var18 = TuplesKt.to("Full update: downloading", 20);
                  break label166;
               }
               break;
            case 1392539220:
               if (var17.equals("checking...")) {
                  var18 = TuplesKt.to("Full update: checking", 5);
                  break label166;
               }
               break;
            case 1941549184:
               if (var17.equals("finalizing update...")) {
                  var18 = TuplesKt.to("Full update: finishing (copying it into place)", 70);
                  break label166;
               }
         }

         var18 = null;
      } else {
         var18 = null;
      }

      String var32;
      label201: {
         if (var18 != null) {
            String var10 = (String)var18.getFirst();
            var32 = var10;
            if (var10 != null) {
               break label201;
            }
         }

         var32 = var31;
         if (var32.length() == 0) {
            var2 = (boolean)1;
         } else {
            var2 = (boolean)0;
         }

         if (var2) {
            var32 = var5;
            if (var32.length() == 0) {
               var2 = (boolean)1;
            } else {
               var2 = (boolean)0;
            }

            if (var2) {
               var32 = "starting";
            }

            var32 = var32;
         }

         var32 = var32;
      }

      var14.put("progressLabel", StringsKt.take(var32, 60));
      if (var18 != null) {
         var2 = ((Number)var18.getSecond()).intValue();
      } else {
         Integer var19 = STAGE_PCT.get(var5);
         if (var19 != null) {
            var2 = var19;
         } else {
            var2 = 0;
         }
      }

      label146: {
         var14.put("progressPercent", var2);
         if (var13 != null) {
            var32 = INSTANCE.str(var13, "commit");
            var20 = var32;
            if (var32 != null) {
               break label146;
            }
         }

         var20 = "";
      }

      var14.put("localCommit", var20);
      String var22;
      if (Intrinsics.areEqual(var5, "error")) {
         var var21 = var31;
         boolean var26;
         if (var21.length() == 0) {
            var26 = true;
         } else {
            var26 = false;
         }

         if (var26) {
            var21 = "update failed";
         }

         var22 = var21;
      } else {
         label140: {
            if (var7 != null) {
               boolean var27;
               if (INSTANCE.str(var7, "error").length() > 0) {
                  var27 = true;
               } else {
                  var27 = false;
               }

               if (var27) {
                  var22 = INSTANCE.str(var7, "error");
                  break label140;
               }
            }

            var22 = var9;
         }
      }

      boolean var28;
      if (var22.length() > 0) {
         var28 = true;
      } else {
         var28 = false;
      }

      if (var28) {
         var14.put("lastError", var22);
      }

      if (var11 != null && var11.optBoolean("targetChangeFound")) {
         var28 = true;
      } else {
         var28 = false;
      }

      if (var28) {
         var14.put("updateAvailable", true);
      }

      if (var12 != null && !var12.optBoolean("canFastUpdate", true)) {
         if (INSTANCE.str(var12, "reason").length() > 0) {
            var28 = true;
         } else {
            var28 = false;
         }

         if (var28) {
            var14.put("reason", INSTANCE.str(var12, "reason"));
         }
      }

      return var14;
   }

   public final boolean running(JSONObject var1) {
      boolean var3 = false;
      boolean var2 = var3;
      if (var1 != null) {
         var2 = var3;
         if (var1.optBoolean("running")) {
            var2 = true;
         }
      }

      return var2;
   }

   public final String summary(JSONObject var1) {
      if (var1 == null) {
         return "checking…";
      } else {
         String var8 = StringsKt.take(this.str(var1, "localCommit"), 7);
         String var9 = StringsKt.take(this.str(var1, "remoteCommit"), 7);
         boolean var6 = this.running(var1);
         boolean var5 = true;
         boolean var4 = true;
         boolean var3 = true;
         int var2 = 1;
         String var11;
         if (var6) {
            Object var7 = this.str(var1, "progressLabel");
            if (var7.length() != 0) {
               var2 = 0;
            }

            if (var2) {
               var7 = "updating";
            }

            var2 = MathKt.roundToInt(var1.optDouble("progressPercent", 0.0));
            var11 = var7 + "  " + var2 + "%";
         } else if (Intrinsics.areEqual(this.str(var1, "stage"), "rebooting")) {
            var11 = "rebooting…";
         } else {
            boolean var15;
            if (this.str(var1, "lastError").length() > 0) {
               var15 = true;
            } else {
               var15 = false;
            }

            if (var15) {
               String var12 = StringsKt.take(this.str(var1, "lastError"), 80);
               var11 = "failed: " + var12;
            } else {
               if (this.str(var1, "reason").length() > 0) {
                  var15 = true;
               } else {
                  var15 = false;
               }

               String var23 = "";
               if (var15) {
                  String var13 = StringsKt.take(this.str(var1, "reason"), 60);
                  if (var8.length() > 0) {
                     var15 = var5;
                  } else {
                     var15 = false;
                  }

                  if (var15) {
                     var23 = "  (" + var8 + ")";
                  }

                  var11 = "not now: " + var13 + var23;
               } else {
                  String var10 = this.str(var1, "versionPin");
                  if (var10.length() > 0) {
                     var15 = true;
                  } else {
                     var15 = false;
                  }

                  if (var15 && !Intrinsics.areEqual(var10, "false") && !Intrinsics.areEqual(var10, "{}")) {
                     var15 = true;
                  } else {
                     var15 = false;
                  }

                  if (var15) {
                     var11 = "an older version is pinned - choose Latest in Galaxy";
                  } else {
                     if (this.str(var1, "remoteError").length() > 0) {
                        var15 = true;
                     } else {
                        var15 = false;
                     }

                     if (var15) {
                        if (var8.length() > 0) {
                           var15 = var4;
                        } else {
                           var15 = false;
                        }

                        if (var15) {
                           var23 = "  (" + var8 + ")";
                        }

                        var11 = "can't reach GitHub" + var23;
                     } else if (var1.optBoolean("updateAvailable")) {
                        var11 = "update available  " + var8 + " → " + var9;
                     } else {
                        if (var8.length() > 0) {
                           var15 = var3;
                        } else {
                           var15 = false;
                        }

                        if (var15) {
                           var11 = "up to date  " + var8;
                        } else {
                           var11 = "";
                        }
                     }
                  }
               }
            }
         }

         return var11;
      }
   }
}
