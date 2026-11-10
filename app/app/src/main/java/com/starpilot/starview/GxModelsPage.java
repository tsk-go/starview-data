package com.starpilot.starview;

import android.app.Activity;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.MatchResult;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import org.json.JSONArray;
import org.json.JSONObject;

public final class GxModelsPage extends GxPage {
   private String activeBigModel;
   private String activeSmallModel;
   private String busy;
   private boolean cancelling;
   private String communityFilter;
   private LinearLayout controlsBox;
   private String currentModel;
   private boolean downloadAll;
   private boolean downloading;
   private String error;
   private int generation;
   private String hardwareFilter;
   private boolean isOnroad;
   private String lastSig;
   private LinearLayout listBox;
   private boolean loading;
   private String modelToDownload;
   private List<? extends JSONObject> models;
   private final boolean needsOffroad;
   private boolean polling;
   private final SharedPreferences prefs;
   private String progressText;
   private int sInstalled;
   private int sMissing;
   private int sTotal;
   private boolean selectionUncertain;
   private boolean selectionWriting;
   private String sortMode;
   private LinearLayout statusBox;
   private String userFilter;

   // $VF: synthetic method
   public static Unit $r8$lambda$08JJE96zC94EgViPNRrBElBaYCs(GxModelsPage var0, String var1, String var2, String var3) {
      return runAction$lambda$27(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$2JnYMdIxbZgilpFxEyYa7zhbBX0(GxModelsPage var0, String var1, String var2, String var3) {
      return runAction$lambda$26(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$4Lewee0khCrPjT9kc2FHLYaFmRY(GxModelsPage var0, List var1, int var2) {
      return build$lambda$3(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$4XYvN1Y5iLf0IfacopTSGDQsxiU(GxModelsPage var0) {
      return build$lambda$7(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$6Pwsf1ShRbhcqgbO2SyXDsRp1dI(GxModelsPage var0, boolean var1) {
      return perform$finish$lambda$29(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$DFjHmXO15nQFq_JaH6pw7xciQlA(GxModelsPage var0, List var1, int var2) {
      return build$lambda$5(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$Fe7_KpvusmDl6uYGDOOeVGi3_SQ/* $VF was: $r8$lambda$Fe7_KpvusmDl6uYGDOOeVGi3_SQ*/(
      String var0, GxModelsPage var1, String var2, JSONObject var3
   ) {
      return perform$lambda$31(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$Fod2DpCZg_8dUqmZHRRez3MAAzs/* $VF was: $r8$lambda$Fod2DpCZg_8dUqmZHRRez3MAAzs*/(GxModelsPage var0) {
      return renderControls$lambda$40(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$L4RqzwozwZXa47nQ6RNmGxu6WWo(GxModelsPage var0, int var1) {
      return build$lambda$1(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$Le2hcULwCimHd37Y3FYFdx4Jmj4(GxModelsPage var0) {
      return renderControls$lambda$38(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$LlGm3nc1eHfd_70BzDl0f2oxx6o/* $VF was: $r8$lambda$LlGm3nc1eHfd_70BzDl0f2oxx6o*/(List var0, GxModelsPage var1, int var2) {
      return renderControls$lambda$45$lambda$44(var0, var1, var2);
   }

   // $VF: synthetic method
   public static int $r8$lambda$MWXZLmxBF0ErHH6M4sppQd1wVlg(GxModelsPage var0, Function1 var1, JSONObject var2, JSONObject var3) {
      return sorted$lambda$19(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static String $r8$lambda$NcoNzxOkfL7_0UQYXzjy5QgzZ5Q/* $VF was: $r8$lambda$NcoNzxOkfL7_0UQYXzjy5QgzZ5Q*/(JSONObject var0) {
      return sorted$lambda$18(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$PalgaYNrTqTR9Xxeom5bOS6N8NE(String var0, GxModelsPage var1, String var2, JSONObject var3) {
      return perform$lambda$34(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$QOAAdiSE9p16WRevQfYmjqNN8Jo(GxModelsPage var0, List var1, int var2) {
      return build$lambda$4(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$XhsY7GiLvy8jGVlhz3uLbtKdvhg(GxModelsPage var0, String var1, JSONObject var2) {
      return perform$lambda$36(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$ZH8CsVnRwvYbC8KDRRf_FDM68Ls/* $VF was: $r8$lambda$ZH8CsVnRwvYbC8KDRRf_FDM68Ls*/(
      GxModelsPage var0, String var1, JSONObject var2
   ) {
      return perform$lambda$33(var0, var1, var2);
   }

   // $VF: synthetic method
   public static int $r8$lambda$_lso_MyIVNZ4TGPBZ6UbM0b3Sxw/* $VF was: $r8$lambda$_lso_MyIVNZ4TGPBZ6UbM0b3Sxw*/(Function2 var0, Object var1, Object var2) {
      return sorted$lambda$20(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$aeL_u2dzyjpDWxTMO0i4ybeut2k/* $VF was: $r8$lambda$aeL_u2dzyjpDWxTMO0i4ybeut2k*/(
      GxModelsPage var0, String var1, JSONObject var2
   ) {
      return perform$lambda$32(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$azQ9lKTEx6VrmM_uJnAzoHxxvkk(GxModelsPage var0, boolean var1, JSONObject var2) {
      return renderList$lambda$60(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$cUD9LTX2cAhUApe0QrUVth_Hj64(GxModelsPage var0, boolean var1, JSONObject var2) {
      return renderList$lambda$56(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$eX4oGahgYXPs6l9M7KYxTRkF5Gg(GxModelsPage var0) {
      return renderControls$lambda$39(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$fcSZw4XBtBC_3ywCge3tLykWb3w(List var0, GxModelsPage var1) {
      return renderControls$lambda$45(var0, var1);
   }

   // $VF: synthetic method
   public static CharSequence $r8$lambda$imU59Ukk_N2FO55s9Ok7K4NRwyc/* $VF was: $r8$lambda$imU59Ukk_N2FO55s9Ok7K4NRwyc*/(JSONObject var0) {
      return render$lambda$37(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$ipeiYK0285JEBRDcZcoOU4NR3ek(GxModelsPage var0, JSONObject var1) {
      return renderList$lambda$59(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$mB0oGWa26XaRpbwVr3L8OvuZQcM(GxModelsPage var0, boolean var1) {
      return build$lambda$7$lambda$6(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$mIq3BMnWYoYmaoM0H6tsFwcH0vU(boolean var0, GxModelsPage var1, String var2, JSONObject var3) {
      return perform$lambda$35(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$pNqx2yyyvsDDI_GjNtI_XEH5Zmk/* $VF was: $r8$lambda$pNqx2yyyvsDDI_GjNtI_XEH5Zmk*/(GxModelsPage var0, List var1, int var2) {
      return renderControls$lambda$49$lambda$48(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$pafqmtMQzpyNDTy2xkHL8XQFhpo(GxModelsPage var0, int var1, Function1 var2, String var3, JSONObject var4) {
      return refresh$lambda$8(var0, var1, var2, var3, var4);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$qOfi2W_qJu7qmxI12mseWOBt6zU(GxModelsPage var0, JSONObject var1) {
      return renderList$lambda$55(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$sfPpmRSHsJEcgiT_ccs3neYBMQM/* $VF was: $r8$lambda$sfPpmRSHsJEcgiT_ccs3neYBMQM*/(
      GxModelsPage var0, String var1, String var2, JSONObject var3
   ) {
      return perform$lambda$30(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$srdQnzckkBvcaeT8DdkXDzsM32o(GxModelsPage var0, List var1) {
      return renderControls$lambda$49(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$vGb6rMyNiYFqsPRoFKdciksiatw(GxModelsPage var0, JSONObject var1) {
      return renderList$lambda$57(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$yLFN6Y2KwGe4U9UkZ4g8XE4lnZU(GxModelsPage var0, String var1, String var2, String var3, boolean var4) {
      return runAction$lambda$26$lambda$25(var0, var1, var2, var3, var4);
   }

   public GxModelsPage(Activity var1, GxHost var2) {
      Intrinsics.checkNotNullParameter(var1, "act");
      Intrinsics.checkNotNullParameter(var2, "gx");
      super(var1, var2);
      this.needsOffroad = true;
      this.prefs = var1.getSharedPreferences("starview_gx_models", 0);
      this.loading = true;
      this.error = "";
      this.busy = "";
      this.selectionUncertain = true;
      this.sortMode = "release_date";
      this.userFilter = "all";
      this.communityFilter = "all";
      SharedPreferences var4 = this.prefs;
      String var6 = "both";
      String var3 = var4.getString("hardwareFilter", "both");
      String var5 = var6;
      if (var3 != null) {
         if (SetsKt.setOf(new String[]{"both", "gpu", "comma"}).contains(var3)) {
            var5 = var3;
         } else {
            var5 = null;
         }

         if (var5 == null) {
            var5 = var6;
         }
      }

      this.hardwareFilter = var5;
      this.models = CollectionsKt.emptyList();
      this.currentModel = "";
      this.activeSmallModel = "";
      this.activeBigModel = "";
      this.modelToDownload = "";
      this.progressText = "";
      this.lastSig = "";
   }

   private final boolean allowedOnroad(String var1) {
      boolean var2;
      if (!Intrinsics.areEqual(var1, "refresh") && !Intrinsics.areEqual(var1, "favorite") && !Intrinsics.areEqual(var1, "unfavorite") && this.isOnroad) {
         var2 = false;
      } else {
         var2 = true;
      }

      return var2;
   }

   private static final Unit build$lambda$1(GxModelsPage var0, int var1) {
      String var2;
      if (var1 == 0) {
         var2 = "release_date";
      } else {
         var2 = "alphabetical";
      }

      var0.sortMode = var2;
      var0.render(true);
      return Unit.INSTANCE;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private static final Unit build$lambda$3(GxModelsPage var0, List var1, int var2) {
      var0.hardwareFilter = (String)var1.get(var2);

      label20:
      try {
         Result.Companion var7 = Result.Companion;
         var0.prefs.edit().putString("hardwareFilter", var0.hardwareFilter).apply();
         KtCompat.resultOf(Unit.INSTANCE);
      } catch (Throwable var5) {
         Result.Companion var6 = Result.Companion;
         KtCompat.resultOf(ResultKt.createFailure(var5));
         break label20;
      }

      var0.render(true);
      return Unit.INSTANCE;
   }

   private static final Unit build$lambda$4(GxModelsPage var0, List var1, int var2) {
      var0.userFilter = (String)var1.get(var2);
      var0.render(true);
      return Unit.INSTANCE;
   }

   private static final Unit build$lambda$5(GxModelsPage var0, List var1, int var2) {
      var0.communityFilter = (String)var1.get(var2);
      var0.render(true);
      return Unit.INSTANCE;
   }

   private static final Unit build$lambda$7(GxModelsPage var0) {
      if (!var0.selectionWriting) {
         boolean var1;
         if (var0.busy.length() == 0) {
            var1 = true;
         } else {
            var1 = false;
         }

         if (var1 && !var0.polling) {
            var0.polling = true;
            var0.refresh(new GxModelsPage$$ExternalSyntheticLambda28(var0));
         }
      }

      return Unit.INSTANCE;
   }

   private static final Unit build$lambda$7$lambda$6(GxModelsPage var0, boolean var1) {
      var0.polling = false;
      return Unit.INSTANCE;
   }

   private final String currentLabel() {
      String var1;
      if (StringsKt.isBlank(this.currentModel)) {
         var1 = "none";
      } else {
         String var2 = this.labelOf(this.currentModel);
         var1 = var2;
         if (var2 == null) {
            var1 = this.currentModel;
         }
      }

      return var1;
   }

   private final String downloadTargetLabel() {
      String var1;
      if (this.downloadAll) {
         var1 = "all missing models";
      } else {
         String var2 = this.labelOf(this.modelToDownload);
         var1 = var2;
         if (var2 == null) {
            CharSequence var4 = this.modelToDownload;
            Object var3 = var4;
            if (StringsKt.isBlank(var4)) {
               var3 = "a model";
            }

            var1 = (String)var3;
         }
      }

      return var1;
   }

   private final List<JSONObject> installedBig() {
      Iterable var3 = this.models;
      var var2 = new ArrayList();

      for (Object var4 : var3) {
         JSONObject var6 = (JSONObject)var4;
         boolean var1;
         if (var6.optBoolean("installed") && var6.optBoolean("requiresGpu")) {
            var1 = true;
         } else {
            var1 = false;
         }

         if (var1) {
            var2.add(var4);
         }
      }

      return var2;
   }

   private final List<JSONObject> installedSmall() {
      Iterable var3 = this.models;
      var var2 = new ArrayList();

      for (var var3_it : var3) {

         var3 = var3_it;
         JSONObject var4 = (JSONObject)var3;
         boolean var1;
         if (var4.optBoolean("installed") && !var4.optBoolean("requiresGpu")) {
            var1 = true;
         } else {
            var1 = false;
         }

         if (var1) {
            var2.add(var3);
         }
      }

      return var2;
   }

   private final String labelOf(String var1) {
      Iterator var5 = this.models.iterator();

      Object var3;
      Object var4;
      do {
         boolean var2 = var5.hasNext();
         var4 = null;
         if (!var2) {
            var3 = null;
            break;
         }

         var3 = (String)var5.next();
      } while (!Intrinsics.areEqual(GxModelsKt.gxModelsText$default((JSONObject)var3, "value", null, 4, null), var1));

      JSONObject var9 = (JSONObject)var3;
      var3 = (String)var4;
      if (var9 != null) {
         var4 = GxModelsKt.access$gxModelsText(var9, "label", var1);
         var3 = (String)var4;
         if (StringsKt.isBlank((CharSequence)var4)) {
            var3 = var1;
         }

         var3 = var3;
      }

      return var3;
   }

   private final void perform(String var1, String var2, String var3, boolean var4) {
      label97: {
         label98: {
            switch (var1.hashCode()) {
               case -1717948619:
                  if (!var1.equals("unfavorite")) {
                     break label97;
                  }
                  break;
               case -1657124689:
                  if (!var1.equals("select-big")) {
                     break label97;
                  }
                  break label98;
               case -1367724422:
                  if (var1.equals("cancel")) {
                     GxPage.send$default(this, "POST", "/api/models/cancel", null, 0L, new GxModelsPage$$ExternalSyntheticLambda23(this), 12, null);
                     return;
                  }
                  break label97;
               case -1335458389:
                  if (var1.equals("delete")) {
                     GxPage.send$default(
                        this,
                        "POST",
                        "/api/models/delete",
                        new JSONObject().put("model", var2),
                        0L,
                        new GxModelsPage$$ExternalSyntheticLambda24(var3, this),
                        8,
                        null
                     );
                     return;
                  }
                  break label97;
               case -1211167751:
                  if (var1.equals("downloadAll")) {
                     GxPage.send$default(
                        this,
                        "POST",
                        "/api/models/download_all",
                        new JSONObject().put("allowGpuWithoutGpu", var4),
                        0L,
                        new GxModelsPage$$ExternalSyntheticLambda21(this),
                        8,
                        null
                     );
                     return;
                  }
                  break label97;
               case 951857398:
                  if (!var1.equals("select-small")) {
                     break label97;
                  }
                  break label98;
               case 1050790300:
                  if (!var1.equals("favorite")) {
                     break label97;
                  }
                  break;
               case 1085444827:
                  if (var1.equals("refresh")) {
                     GxPage.send$default(this, "POST", "/api/models/refresh_manifest", null, 120L, new GxModelsPage$$ExternalSyntheticLambda26(this), 4, null);
                     return;
                  }
                  break label97;
               case 1427818632:
                  if (var1.equals("download")) {
                     GxPage.send$default(
                        this,
                        "POST",
                        "/api/models/download",
                        new JSONObject().put("model", var2).put("allowGpuWithoutGpu", var4),
                        0L,
                        new GxModelsPage$$ExternalSyntheticLambda20(var3, this),
                        8,
                        null
                     );
                     return;
                  }
               default:
                  break label97;
            }

            var4 = Intrinsics.areEqual(var1, "favorite");
            JSONArray var10 = new JSONArray();

            for (JSONObject var6 : this.models) {
               var1 = GxModelsKt.gxModelsText$default(var6, "value", null, 4, null);
               if (var6.optBoolean("userFavorite") && !Intrinsics.areEqual(var1, var2)) {
                  boolean var5;
                  if (var1.length() > 0) {
                     var5 = true;
                  } else {
                     var5 = false;
                  }

                  if (var5) {
                     var10.put(var1);
                  }
               }
            }

            if (var4) {
               var10.put(var2);
            }

            GxPage.send$default(
               this,
               "PUT",
               "/api/models/preferences",
               new JSONObject().put("userFavorites", var10),
               0L,
               new GxModelsPage$$ExternalSyntheticLambda25(var4, this),
               8,
               null
            );
            return;
         }

         if (Intrinsics.areEqual(var1, "select-big")) {
            var1 = "big";
         } else {
            var1 = "small";
         }

         this.selectionUncertain = true;
         this.selectionWriting = true;
         this.generation++;
         GxPage.send$default(
            this,
            "PUT",
            "/api/models/active",
            new JSONObject().put("profile", var1).put("model", var2),
            0L,
            new GxModelsPage$$ExternalSyntheticLambda19(this, var3),
            8,
            null
         );
         return;
      }

      this.busy = "";
   }

   private static final void perform$finish(GxModelsPage var0, String var1, JSONObject var2, String var3) {
      if (!var0.getShown()) {
         var0.busy = "";
      } else {
         if (var1 != null) {
            var0.toast(var1);
         } else {
            label23: {
               if (var2 != null) {
                  var1 = var2.optString("message");
                  if (var1 != null) {
                     CharSequence var7 = var1;
                     CharSequence var5 = var7;
                     if (StringsKt.isBlank(var7)) {
                        var5 = null;
                     }

                     String var8 = (String)var5;
                     var1 = var8;
                     if (var8 != null) {
                        break label23;
                     }
                  }
               }

               var1 = var3;
            }

            var0.toast(var1);
         }

         var0.refresh(new GxModelsPage$$ExternalSyntheticLambda0(var0));
      }
   }

   private static final Unit perform$finish$lambda$29(GxModelsPage var0, boolean var1) {
      var0.busy = "";
      var0.render(true);
      return Unit.INSTANCE;
   }

   private static final Unit perform$lambda$30(GxModelsPage var0, String var1, String var2, JSONObject var3) {
      var0.selectionWriting = false;
      perform$finish(var0, var2, var3, "Selected \"" + var1 + "\".");
      return Unit.INSTANCE;
   }

   private static final Unit perform$lambda$31(String var0, GxModelsPage var1, String var2, JSONObject var3) {
      perform$finish(var1, var2, var3, "Downloading \"" + var0 + "\"...");
      return Unit.INSTANCE;
   }

   private static final Unit perform$lambda$32(GxModelsPage var0, String var1, JSONObject var2) {
      perform$finish(var0, var1, var2, "Started downloading all models.");
      return Unit.INSTANCE;
   }

   private static final Unit perform$lambda$33(GxModelsPage var0, String var1, JSONObject var2) {
      perform$finish(var0, var1, var2, "Cancellation requested.");
      return Unit.INSTANCE;
   }

   private static final Unit perform$lambda$34(String var0, GxModelsPage var1, String var2, JSONObject var3) {
      perform$finish(var1, var2, var3, "Deleted files for \"" + var0 + "\".");
      return Unit.INSTANCE;
   }

   private static final Unit perform$lambda$35(boolean var0, GxModelsPage var1, String var2, JSONObject var3) {
      String var4;
      if (var0) {
         var4 = "Model added to your favorites.";
      } else {
         var4 = "Model removed from your favorites.";
      }

      perform$finish(var1, var2, var3, var4);
      return Unit.INSTANCE;
   }

   private static final Unit perform$lambda$36(GxModelsPage var0, String var1, JSONObject var2) {
      perform$finish(var0, var1, var2, "Model manifest refreshed.");
      return Unit.INSTANCE;
   }

   private final void refresh(Function1<? super Boolean, Unit> var1) {
      this.generation++;
      this.getJson("/api/models/status", new GxModelsPage$$ExternalSyntheticLambda18(this, this.generation, var1));
   }

   // $VF: synthetic method
   static void refresh$default(GxModelsPage var0, Function1 var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = null;
      }

      var0.refresh(var1);
   }

   private static final Unit refresh$lambda$8(GxModelsPage var0, int var1, Function1 var2, String var3, JSONObject var4) {
      boolean var5 = var0.getShown();
      Boolean var6 = false;
      if (var5 && var1 == var0.generation) {
         if (var3 == null && var4 != null) {
            var0.models = GxModelsKt.access$gxModelsObjects(var4.optJSONArray("models"));
            var0.currentModel = GxModelsKt.gxModelsText$default(var4, "currentModel", null, 4, null);
            var0.activeSmallModel = GxModelsKt.gxModelsText$default(var4, "activeSmallModel", null, 4, null);
            var0.activeBigModel = GxModelsKt.gxModelsText$default(var4, "activeBigModel", null, 4, null);
            JSONObject var10 = var4.optJSONObject("summary");
            if (var10 != null) {
               var1 = var10.optInt("installed");
            } else {
               var1 = 0;
            }

            var0.sInstalled = var1;
            if (var10 != null) {
               var1 = var10.optInt("missing");
            } else {
               var1 = 0;
            }

            var0.sMissing = var1;
            if (var10 != null) {
               var1 = var10.optInt("total");
            } else {
               var1 = 0;
            }

            var0.sTotal = var1;
            var0.modelToDownload = GxModelsKt.gxModelsText$default(var4, "modelToDownload", null, 4, null);
            var0.downloadAll = var4.optBoolean("downloadAll");
            var0.downloading = var4.optBoolean("downloading");
            var0.cancelling = var4.optBoolean("cancelling");
            var0.progressText = GxModelsKt.gxModelsText$default(var4, "progress", null, 4, null);
            var0.isOnroad = var4.optBoolean("isOnroad");
            var0.error = "";
            var0.selectionUncertain = false;
            var0.loading = false;
            render$default(var0, false, 1, null);
            if (var2 != null) {
               var2.invoke(true);
            }

            return Unit.INSTANCE;
         } else {
            if (var3 == null) {
               var3 = "bad reply";
            }

            var0.error = var3;
            var0.loading = false;
            render$default(var0, false, 1, null);
            if (var2 != null) {
               var2.invoke(var6);
            }

            return Unit.INSTANCE;
         }
      } else {
         if (var2 != null) {
            var2.invoke(var6);
         }

         return Unit.INSTANCE;
      }
   }

   private final void render(boolean var1) {
      if (this.listBox != null) {
         boolean var8 = this.loading;
         String var18 = this.error;
         String var12 = this.busy;
         boolean var5 = this.selectionUncertain;
         String var16 = this.sortMode;
         String var19 = this.userFilter;
         String var11 = this.communityFilter;
         String var20 = this.hardwareFilter;
         String var17 = this.currentModel;
         String var15 = this.activeSmallModel;
         String var14 = this.activeBigModel;
         int var3 = this.sInstalled;
         int var2 = this.sMissing;
         int var4 = this.sTotal;
         String var21 = this.modelToDownload;
         boolean var6 = this.downloadAll;
         boolean var10 = this.downloading;
         boolean var9 = this.cancelling;
         String var13 = this.progressText;
         boolean var7 = this.isOnroad;
         var11 = CollectionsKt.joinToString$default(
            CollectionsKt.listOf(
               new Object[]{
                  var8,
                  var18,
                  var12,
                  var5,
                  var16,
                  var19,
                  var11,
                  var20,
                  var17,
                  var15,
                  var14,
                  var3,
                  var2,
                  var4,
                  var21,
                  var6,
                  var10,
                  var9,
                  var13,
                  var7,
                  CollectionsKt.joinToString$default(this.models, "|", null, null, 0, null, new GxModelsPage$$ExternalSyntheticLambda27(), 30, null)
               }
            ),
            "\u0001",
            null,
            null,
            0,
            null,
            null,
            62,
            null
         );
         if (var1 || !Intrinsics.areEqual(var11, this.lastSig)) {
            this.lastSig = var11;
            this.renderStatus();
            this.renderControls();
            this.renderList();
         }
      }
   }

   // $VF: synthetic method
   static void render$default(GxModelsPage var0, boolean var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = false;
      }

      var0.render(var1);
   }

   private static final CharSequence render$lambda$37(JSONObject var0) {
      Intrinsics.checkNotNullParameter(var0, "it");
      String var1 = var0.toString();
      Intrinsics.checkNotNullExpressionValue(var1, "toString(...)");
      return var1;
   }

   private final void renderControls() {
      LinearLayout var5 = this.controlsBox;
      Object var7 = null;
      LinearLayout var4 = var5;
      if (var5 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("controlsBox");
         var4 = null;
      }

      var4.removeAllViews();
      if (!this.loading) {
         int var1 = this.busy.length();
         boolean var3 = true;
         boolean var2;
         if (var1 == 0) {
            var2 = true;
         } else {
            var2 = false;
         }

         GxPage var9 = this;
         String var12;
         if (this.downloading) {
            var12 = "Download in progress";
         } else {
            var12 = "Downloads";
         }

         String var19;
         if (this.downloading) {
            var19 = "Stop the current model download.";
         } else {
            var19 = "Download every missing model in the catalogue, or refresh the model manifest.";
         }

         LinearLayout var8 = this.controlsBox;
         LinearLayout var6 = var8;
         if (var8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("controlsBox");
            var6 = null;
         }

         GxPage.Row var20 = GxPage.row$default(var9, var12, var19, (ViewGroup)var6, 0, 8, null);
         if (this.downloading) {
            GxModelsKt.access$gxModelsEnable(
               (View)GxPage.Row.button$default(var20, "Cancel Download", false, new GxModelsPage$$ExternalSyntheticLambda3(this), 2, null), var2
            );
         } else {
            GxModelsKt.access$gxModelsEnable(
               (View)GxPage.Row.button$default(
                  var20, "Download all missing — entire catalogue", false, new GxModelsPage$$ExternalSyntheticLambda4(this), 2, null
               ),
               var2
            );
         }

         String var13;
         if (Intrinsics.areEqual(this.busy, "refresh:")) {
            var13 = "Refreshing…";
         } else {
            var13 = "Refresh";
         }

         GxModelsKt.access$gxModelsEnable((View)GxPage.Row.button$default(var20, var13, false, new GxModelsPage$$ExternalSyntheticLambda5(this), 2, null), var2);
         if (var2 && !this.selectionUncertain && !this.isOnroad) {
            var2 = var3;
         } else {
            var2 = false;
         }

         List var29 = this.installedSmall();
         List var28 = this.installedBig();
         GxPage var10 = this;
         String var21 = this.labelOf(this.activeSmallModel);
         String var14 = var21;
         if (var21 == null) {
            CharSequence var22 = this.activeSmallModel;
            Object var15 = var22;
            if (StringsKt.isBlank(var22)) {
               var15 = "none";
            }

            var14 = (String)var15;
         }

         var6 = this.controlsBox;
         var5 = var6;
         if (var6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("controlsBox");
            var5 = null;
         }

         View var16 = (View)GxPage.Row.button$default(
            GxPage.row$default(var10, "Active Small", var14, (ViewGroup)var5, 0, 8, null),
            "Change",
            false,
            new GxModelsPage$$ExternalSyntheticLambda6(var29, this),
            2,
            null
         );
         GxModelsKt.access$gxModelsEnable(var16, var2);
         GxPage var27 = this;
         String var17;
         if (StringsKt.isBlank(this.activeBigModel)) {
            var17 = "None — always use Active Small";
         } else {
            String var24 = this.labelOf(this.activeBigModel);
            var17 = var24;
            if (var24 == null) {
               var17 = this.activeBigModel;
            }
         }

         var5 = this.controlsBox;
         if (var5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("controlsBox");
            var5 = (LinearLayout)var7;
         }

         View var18 = (View)GxPage.Row.button$default(
            GxPage.row$default(var27, "Active Big", var17, (ViewGroup)var5, 0, 8, null),
            "Change",
            false,
            new GxModelsPage$$ExternalSyntheticLambda7(this, var28),
            2,
            null
         );
         GxModelsKt.access$gxModelsEnable(var18, var2);
      }
   }

   private static final Unit renderControls$lambda$38(GxModelsPage var0) {
      runAction$default(var0, "cancel", null, 2, null);
      return Unit.INSTANCE;
   }

   private static final Unit renderControls$lambda$39(GxModelsPage var0) {
      runAction$default(var0, "downloadAll", null, 2, null);
      return Unit.INSTANCE;
   }

   private static final Unit renderControls$lambda$40(GxModelsPage var0) {
      runAction$default(var0, "refresh", null, 2, null);
      return Unit.INSTANCE;
   }

   private static final Unit renderControls$lambda$45(List var0, GxModelsPage var1) {
      if (var0.isEmpty()) {
         var1.toast("No installed on-device models.");
         return Unit.INSTANCE;
      } else {
         Iterable var2 = var0;
         var var4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var2, 10));

         for (JSONObject var6 : var2) {
            CharSequence var3 = GxModelsKt.access$gxModelsText(var6, "label", GxModelsKt.gxModelsText$default(var6, "value", null, 4, null));
            var2 = var3;
            if (StringsKt.isBlank(var3)) {
               var2 = GxModelsKt.gxModelsText$default(var6, "value", null, 4, null);
            }

            if (Intrinsics.areEqual(GxModelsKt.gxModelsText$default(var6, "value", null, 4, null), var1.activeSmallModel)) {
               var3 = "  ✓";
            } else {
               var3 = "";
            }

            var4.add(var2 + var3);
         }

         List var8 = var4;
         var1.pick("Active Small", var8, new GxModelsPage$$ExternalSyntheticLambda17(var0, var1));
         return Unit.INSTANCE;
      }
   }

   private static final Unit renderControls$lambda$45$lambda$44(List var0, GxModelsPage var1, int var2) {
      JSONObject var3 = (JSONObject)var0.get(var2);
      if (!Intrinsics.areEqual(GxModelsKt.gxModelsText$default(var3, "value", null, 4, null), var1.activeSmallModel)) {
         var1.runAction("select-small", var3);
      }

      return Unit.INSTANCE;
   }

   private static final Unit renderControls$lambda$49(GxModelsPage var0, List var1) {
      boolean var2 = StringsKt.isBlank(var0.activeBigModel);
      String var4 = "  ✓";
      String var6 = "";
      String var3;
      if (var2) {
         var3 = "  ✓";
      } else {
         var3 = "";
      }

      Collection var8 = CollectionsKt.listOf("None — always use Active Small" + var3);
      Iterable var5 = var1;
      var var10 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var5, 10));
      Iterator var9 = var5.iterator();
      var3 = var6;

      while (var9.hasNext()) {
         JSONObject var11 = (JSONObject)var9.next();
         CharSequence var7 = GxModelsKt.access$gxModelsText(var11, "label", GxModelsKt.gxModelsText$default(var11, "value", null, 4, null));
         var6 = var7;
         if (StringsKt.isBlank(var7)) {
            var6 = GxModelsKt.gxModelsText$default(var11, "value", null, 4, null);
         }

         if (Intrinsics.areEqual(GxModelsKt.gxModelsText$default(var11, "value", null, 4, null), var0.activeBigModel)) {
            var7 = var4;
         } else {
            var7 = var3;
         }

         var10.add(var6 + var7);
      }

      List var13 = var10;
      var0.pick("Active Big", CollectionsKt.plus(var8, var13), new GxModelsPage$$ExternalSyntheticLambda11(var0, var1));
      return Unit.INSTANCE;
   }

   private static final Unit renderControls$lambda$49$lambda$48(GxModelsPage var0, List var1, int var2) {
      if (var2 == 0) {
         if (!StringsKt.isBlank(var0.activeBigModel)) {
            runAction$default(var0, "select-big", null, 2, null);
         }
      } else {
         JSONObject var3 = (JSONObject)var1.get(var2 - 1);
         if (!Intrinsics.areEqual(GxModelsKt.gxModelsText$default(var3, "value", null, 4, null), var0.activeBigModel)) {
            var0.runAction("select-big", var3);
         }
      }

      return Unit.INSTANCE;
   }

   private final void renderList() {
      LinearLayout var5 = this.listBox;
      TextView var4 = null;
      LinearLayout var3 = var5;
      if (var5 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("listBox");
         var3 = null;
      }

      var3.removeAllViews();
      if (!this.loading) {
         List var14 = this.sorted();
         if (var14.isEmpty()) {
            var3 = this.listBox;
            if (var3 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("listBox");
               var3 = var4;
            }

            var3.addView((View)this.empty("No models match these filters. Try Both or clear the favourite filters."));
         } else {
            boolean var1;
            if (this.busy.length() == 0) {
               var1 = true;
            } else {
               var1 = false;
            }

            for (JSONObject var8 : var14) {
               String var30 = GxModelsKt.gxModelsText$default(var8, "value", null, 4, null);
               CharSequence var6 = GxModelsKt.access$gxModelsText(var8, "label", var30);
               var4 = var6;
               if (StringsKt.isBlank(var6)) {
                  var4 = var30;
               }

               String var35 = (String)var4;
               ArrayList var10 = new ArrayList();
               if (var8.optBoolean("userFavorite")) {
                  var10.add("★ Your Favorite");
               }

               if (var8.optBoolean("communityFavorite")) {
                  var10.add("Community Favorite");
               }

               ArrayList var9 = new ArrayList();
               var9.add(var30);
               if (var8.optBoolean("builtin")) {
                  var9.add("Built-in");
               }

               var9.add(GxModelsKt.access$gxModelsHardwareLabel(var8));
               var4 = GxModelsKt.gxModelsText$default(var8, "version", null, 4, null);
               if (StringsKt.isBlank((CharSequence)var4)) {
                  var4 = null;
               }

               if (var4 != null) {
                  var9.add("Version " + var4);
               }

               var4 = GxModelsKt.gxModelsText$default(var8, "released", null, 4, null);
               if (StringsKt.isBlank((CharSequence)var4)) {
                  var4 = null;
               }

               if (var4 != null) {
                  var9.add("Released " + var4);
               }

               if (var8.optBoolean("partial")) {
                  var9.add("Partial Files");
               }

               var4 = GxModelsKt.access$gxModelsFileSizeText(var8);
               var9.add("File size: " + var4);
               if (!var10.isEmpty()) {
                  var4 = CollectionsKt.joinToString$default(var10, " · ", null, null, 0, null, null, 62, null);
                  var4 = var4 + "\n";
               } else {
                  var4 = "";
               }

               String var31 = CollectionsKt.joinToString$default(var9, " · ", null, null, 0, null, null, 62, null);
               String var37 = var4 + var31;
               GxPage var36 = this;
               var5 = this.listBox;
               var4 = var5;
               if (var5 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("listBox");
                  var4 = null;
               }

               label150: {
                  var33 = GxPage.row$default(var36, var35, var37, (ViewGroup)var4, 0, 8, null);
                  var4 = this.rowState(var8);
                  switch (var4.hashCode()) {
                     case -1560195700:
                        if (var4.equals("cancellable")) {
                           GxModelsKt.access$gxModelsEnable(
                              (View)GxPage.Row.button$default(var33, "Cancel", false, new GxModelsPage$$ExternalSyntheticLambda12(this, var8), 2, null), var1
                           );
                           break label150;
                        }
                        break;
                     case -1422950650:
                        if (var4.equals("active")) {
                           var33.value("Active model").setTextColor(CarUi.Companion.getACCENT());
                           break label150;
                        }
                        break;
                     case 3035641:
                        if (var4.equals("busy")) {
                           var33.value("Busy").setTextColor(CarUi.Companion.getDIM());
                           break label150;
                        }
                        break;
                     case 29046650:
                        if (var4.equals("installed")) {
                           boolean var2 = var8.optBoolean("requiresGpu");
                           if (var2) {
                              var4 = "Big";
                           } else {
                              var4 = "Small";
                           }

                           var4 = (View)var33.button("Set Active " + var4, true, new GxModelsPage$$ExternalSyntheticLambda13(this, var2, var8));
                           if (var1 && !this.selectionUncertain) {
                              var2 = true;
                           } else {
                              var2 = false;
                           }

                           GxModelsKt.access$gxModelsEnable((View)var4, var2);
                           if (!var8.optBoolean("builtin")) {
                              var4 = GxPage.Row.button$default(var33, "Delete", false, new GxModelsPage$$ExternalSyntheticLambda14(this, var8), 2, null);
                              var4.setTextColor(CarUi.Companion.getWARN());
                              GxModelsKt.access$gxModelsEnable((View)var4, var1);
                           }
                           break label150;
                        }
                  }

                  View var27 = (View)GxPage.Row.button$default(var33, "Download", false, new GxModelsPage$$ExternalSyntheticLambda15(this, var8), 2, null);
                  boolean var12;
                  if (var1 && !this.isOnroad) {
                     var12 = true;
                  } else {
                     var12 = false;
                  }

                  GxModelsKt.access$gxModelsEnable(var27, var12);
               }

               boolean var13 = var8.optBoolean("userFavorite");
               String var28;
               if (var13) {
                  var28 = "★";
               } else {
                  var28 = "☆";
               }

               TextView var34 = GxPage.Row.button$default(var33, var28, false, new GxModelsPage$$ExternalSyntheticLambda16(this, var13, var8), 2, null);
               var34.setMinWidth(this.getUi().dp(64));
               var34.setTextSize(24.0F);
               String var29;
               if (var13) {
                  var29 = "Remove from your favorites";
               } else {
                  var29 = "Add to your favorites";
               }

               var34.setContentDescription(var29);
               if (var13) {
                  var34.setTextColor(CarUi.Companion.getACCENT());
               }

               GxModelsKt.access$gxModelsEnable((View)var34, var1);
            }
         }
      }
   }

   private static final Unit renderList$lambda$55(GxModelsPage var0, JSONObject var1) {
      var0.runAction("cancel", var1);
      return Unit.INSTANCE;
   }

   private static final Unit renderList$lambda$56(GxModelsPage var0, boolean var1, JSONObject var2) {
      String var3;
      if (var1) {
         var3 = "select-big";
      } else {
         var3 = "select-small";
      }

      var0.runAction(var3, var2);
      return Unit.INSTANCE;
   }

   private static final Unit renderList$lambda$57(GxModelsPage var0, JSONObject var1) {
      var0.runAction("delete", var1);
      return Unit.INSTANCE;
   }

   private static final Unit renderList$lambda$59(GxModelsPage var0, JSONObject var1) {
      var0.runAction("download", var1);
      return Unit.INSTANCE;
   }

   private static final Unit renderList$lambda$60(GxModelsPage var0, boolean var1, JSONObject var2) {
      String var3;
      if (var1) {
         var3 = "unfavorite";
      } else {
         var3 = "favorite";
      }

      var0.runAction(var3, var2);
      return Unit.INSTANCE;
   }

   private final void renderStatus() {
      LinearLayout var9 = this.statusBox;
      Object var7 = null;
      Object var8 = null;
      LinearLayout var6 = var9;
      if (var9 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("statusBox");
         var6 = null;
      }

      var6.removeAllViews();
      if (this.loading) {
         var6 = this.statusBox;
         if (var6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("statusBox");
            var6 = (LinearLayout)var8;
         }

         var6.addView((View)this.empty("Loading models..."));
      } else {
         var8 = this.statusBox;
         var6 = (LinearLayout)var8;
         if (var8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("statusBox");
            var6 = null;
         }

         var6 = this.card((ViewGroup)var6);
         var8 = this;
         int var5 = this.sInstalled;
         int var3 = this.sMissing;
         int var4 = this.sTotal;
         GxPage.text$default(
            (GxPage)var8, var5 + " installed · " + var3 + " missing · " + var4 + " total", (ViewGroup)var6, 21.0F, CarUi.Companion.getTEXT(), false, 16, null
         );
         var8 = this.currentLabel();
         this.text("Selected: " + var8, (ViewGroup)var6, 18.0F, CarUi.Companion.getACCENT(), true);
         boolean var10;
         if (this.error.length() > 0) {
            var10 = true;
         } else {
            var10 = false;
         }

         if (var10) {
            this.text("Status load failed", (ViewGroup)var6, 17.0F, CarUi.Companion.getWARN(), true);
            GxPage.text$default(this, this.error, (ViewGroup)var6, 15.0F, CarUi.Companion.getWARN(), false, 16, null);
         }

         if (this.isOnroad) {
            GxPage.text$default(
               this, "Onroad: switching models and downloads are disabled.", (ViewGroup)var6, 17.0F, CarUi.Companion.getWARN(), false, 16, null
            );
         }

         if (this.downloading) {
            var8 = this.downloadTargetLabel();
            this.text("Downloading " + var8, (ViewGroup)var6, 18.0F, CarUi.Companion.getTEXT(), true);
            if (this.progressText.length() > 0) {
               var10 = true;
            } else {
               var10 = false;
            }

            if (var10) {
               GxPage.text$default(this, this.progressText, (ViewGroup)var6, 0.0F, 0, false, 28, null);
            }

            if (this.cancelling) {
               GxPage.text$default(this, "Cancellation requested.", (ViewGroup)var6, 15.0F, CarUi.Companion.getWARN(), false, 16, null);
            }

            var8 = this.progress((ViewGroup)var6);
            MatchResult var21 = Regex.find$default(new Regex("(\\d+(?:\\.\\d+)?)\\s*%"), this.progressText, 0, 2, null);
            Double var14 = (Double)var7;
            if (var21 != null) {
               List var22 = var21.getGroupValues();
               var14 = (Double)var7;
               if (var22 != null) {
                  String var23 = (String)var22.get(1);
                  var14 = (Double)var7;
                  if (var23 != null) {
                     var14 = StringsKt.toDoubleOrNull(var23);
                  }
               }
            }

            double var1;
            if (var14 != null) {
               var1 = var14;
            } else {
               var1 = -1.0;
            }

            this.setProgress((ProgressBar)var8, var1);
         }
      }
   }

   private final String rowState(JSONObject var1) {
      String var4 = GxModelsKt.gxModelsText$default(var1, "value", null, 4, null);
      int var2 = var4.length();
      boolean var3 = true;
      boolean var7;
      if (var2 > 0) {
         var7 = true;
      } else {
         var7 = false;
      }

      if (var7 && Intrinsics.areEqual(var4, this.currentModel)) {
         return "active";
      } else if (!this.downloading) {
         var4 = "installed";
         String var6;
         if (var1.optBoolean("installed")) {
            var6 = var4;
         } else {
            var6 = "available";
         }

         return var6;
      } else {
         if (!this.downloadAll && Intrinsics.areEqual(this.modelToDownload, var4)) {
            var7 = var3;
         } else {
            var7 = false;
         }

         String var5;
         if (!this.downloadAll && !var7) {
            var5 = "busy";
         } else {
            var5 = "cancellable";
         }

         return var5;
      }
   }

   private final void runAction(String var1, JSONObject var2) {
      boolean var3;
      if (!Intrinsics.areEqual(var1, "select-small") && !Intrinsics.areEqual(var1, "select-big")) {
         var3 = false;
      } else {
         var3 = true;
      }

      if (!var3 || !this.selectionUncertain) {
         if (this.busy.length() > 0) {
            var3 = true;
         } else {
            var3 = false;
         }

         if (var3) {
            this.toast("Please wait for the current action to finish.");
         } else if (!this.allowedOnroad(var1)) {
            this.toast("Actions are blocked while onroad.");
         } else {
            String var8 = GxModelsKt.gxModelsText$default(var2, "value", null, 4, null);
            var var5 = var8;
            boolean var4 = StringsKt.isBlank(var5);
            String var6 = "model";
            if (var4) {
               var5 = "model";
            }

            CharSequence var7 = GxModelsKt.access$gxModelsText(var2, "label", var5);
            var5 = var7;
            if (StringsKt.isBlank(var7)) {
               var5 = var8;
               if (StringsKt.isBlank(var5)) {
                  var5 = var6;
               }

               var5 = var5;
            }

            var5 = var5;
            if (Intrinsics.areEqual(var1, "download")) {
               if (var2 != null && var2.optBoolean("requiresGpu") && !var2.optBoolean("gpuAvailable")) {
                  var3 = true;
               } else {
                  var3 = false;
               }
            } else if (Intrinsics.areEqual(var1, "downloadAll")) {
               var var9 = this.models;
               if (var9 instanceof Collection && ((Collection)var9).isEmpty()) {
                  var3 = false;
               } else {
                  Iterator var10 = var9.iterator();

                  while (true) {
                     if (!var10.hasNext()) {
                        var3 = false;
                        break;
                     }

                     JSONObject var18 = (JSONObject)var10.next();
                     if (!var18.optBoolean("installed") && var18.optBoolean("requiresGpu") && !var18.optBoolean("gpuAvailable")) {
                        var3 = true;
                     } else {
                        var3 = false;
                     }

                     if (var3) {
                        var3 = true;
                        break;
                     }
                  }
               }
            } else {
               var3 = false;
            }

            if (var3) {
               String var11;
               if (Intrinsics.areEqual(var1, "downloadAll")) {
                  var11 = "This download includes models that require an external GPU. You can download them now, but they cannot run until a compatible GPU is connected and detected.";
               } else {
                  var11 = "“"
                     + var5
                     + "” requires an external GPU. You can download it now, but it cannot run until a compatible GPU is connected and detected.";
               }

               this.confirm(
                  "No external GPU detected",
                  var11 + "\n\nGPU model files can be large. Downloading does not activate a model or change the model currently in use.",
                  "Download anyway",
                  new GxModelsPage$$ExternalSyntheticLambda1(this, var1, var8, var5)
               );
            } else if (Intrinsics.areEqual(var1, "delete")) {
               this.confirm(
                  "Delete model", "Delete local files for \"" + var5 + "\"?", "Delete", new GxModelsPage$$ExternalSyntheticLambda2(this, var1, var8, var5)
               );
            } else {
               this.busy = var1 + ":" + var8;
               render$default(this, false, 1, null);
               this.perform(var1, var8, var5, false);
            }
         }
      }
   }

   // $VF: synthetic method
   static void runAction$default(GxModelsPage var0, String var1, JSONObject var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = null;
      }

      var0.runAction(var1, var2);
   }

   private static final Unit runAction$lambda$26(GxModelsPage var0, String var1, String var2, String var3) {
      boolean var4;
      if (var0.busy.length() > 0) {
         var4 = true;
      } else {
         var4 = false;
      }

      if (var4) {
         var0.toast("Please wait for the current action to finish.");
         return Unit.INSTANCE;
      } else {
         var0.busy = var1 + ":" + var2;
         render$default(var0, false, 1, null);
         var0.refresh(new GxModelsPage$$ExternalSyntheticLambda22(var0, var1, var2, var3));
         return Unit.INSTANCE;
      }
   }

   private static final Unit runAction$lambda$26$lambda$25(GxModelsPage var0, String var1, String var2, String var3, boolean var4) {
      if (!var0.getShown()) {
         var0.busy = "";
         return Unit.INSTANCE;
      } else {
         if (var4) {
            boolean var5;
            if (var0.error.length() > 0) {
               var5 = true;
            } else {
               var5 = false;
            }

            if (!var5 && var0.allowedOnroad(var1) && !var0.downloading) {
               var0.perform(var1, var2, var3, true);
               return Unit.INSTANCE;
            }
         }

         var0.toast("Download not started. Check the device status and try again while parked.");
         var0.busy = "";
         render$default(var0, false, 1, null);
         return Unit.INSTANCE;
      }
   }

   private static final Unit runAction$lambda$27(GxModelsPage var0, String var1, String var2, String var3) {
      boolean var4;
      if (var0.busy.length() > 0) {
         var4 = true;
      } else {
         var4 = false;
      }

      if (var4) {
         var0.toast("Please wait for the current action to finish.");
         return Unit.INSTANCE;
      } else {
         var0.busy = var1 + ":" + var2;
         render$default(var0, false, 1, null);
         var0.perform(var1, var2, var3, false);
         return Unit.INSTANCE;
      }
   }

   private final List<JSONObject> sorted() {
      Iterable var4 = this.models;
      var var3 = new ArrayList();

      for (Object var5 : var4) {
         if (GxModelsKt.access$gxModelsMatchesHardware((JSONObject)var5, this.hardwareFilter)) {
            var3.add(var5);
         }
      }

      List var8 = var3;
      var4 = var8;
      var3 = new ArrayList();
      Iterator var19 = var4.iterator();

      while (true) {
         boolean var2 = var19.hasNext();
         boolean var1 = false;
         if (!var2) {
            List var10 = var3;
            var4 = var10;
            var3 = new ArrayList();

            for (var var4_it : var4) {

               var4 = var4_it;
               JSONObject var21 = (JSONObject)var4;
               if (!Intrinsics.areEqual(this.communityFilter, "all")
                  && var21.optBoolean("communityFavorite") != Intrinsics.areEqual(this.communityFilter, "yes")) {
                  var1 = false;
               } else {
                  var1 = true;
               }

               if (var1) {
                  var3.add(var4);
               }
            }

            List var18 = var3;
            GxModelsPage$$ExternalSyntheticLambda8 var12 = new GxModelsPage$$ExternalSyntheticLambda8();
            return CollectionsKt.sortedWith(var18, new GxModelsPage$$ExternalSyntheticLambda10(new GxModelsPage$$ExternalSyntheticLambda9(this, var12)));
         }

         var4 = (Iterable)var19.next();
         JSONObject var6 = (JSONObject)var4;
         if (Intrinsics.areEqual(this.userFilter, "all") || var6.optBoolean("userFavorite") == Intrinsics.areEqual(this.userFilter, "yes")) {
            var1 = true;
         }

         if (var1) {
            var3.add(var4);
         }
      }
   }

   private static final String sorted$lambda$18(JSONObject var0) {
      Intrinsics.checkNotNullParameter(var0, "m");
      return GxModelsKt.access$gxModelsText(var0, "label", GxModelsKt.gxModelsText$default(var0, "value", null, 4, null));
   }

   private static final int sorted$lambda$19(GxModelsPage var0, Function1 var1, JSONObject var2, JSONObject var3) {
      if (Intrinsics.areEqual(var0.sortMode, "release_date")) {
         int var4 = Intrinsics.compare(
            GxModelsKt.access$gxModelsReleasedTs(GxModelsKt.gxModelsText$default(var3, "released", null, 4, null)),
            GxModelsKt.access$gxModelsReleasedTs(GxModelsKt.gxModelsText$default(var2, "released", null, 4, null))
         );
         if (var4 != 0) {
            return var4;
         }
      }

      Intrinsics.checkNotNull(var2);
      String var5 = (String)var1.invoke(var2);
      Intrinsics.checkNotNull(var3);
      return GxModelsKt.access$gxModelsNatCompare(var5, (String)var1.invoke(var3));
   }

   private static final int sorted$lambda$20(Function2 var0, Object var1, Object var2) {
      return ((Number)var0.invoke(var1, var2)).intValue();
   }

   @Override
   public void build() {
      GxPage.header$default(this, "Model Manager", null, 2, null);
      this.statusBox = GxPage.box$default(this, null, 1, null);
      GxPage.header$default(this, "Controls", null, 2, null);
      this.controlsBox = GxPage.box$default(this, null, 1, null);
      GxPage.choice$default(
         this,
         "Sort",
         null,
         CollectionsKt.listOf(new String[]{"Release Date", "Alphabetical"}),
         Intrinsics.areEqual(this.sortMode, "release_date") ^ 1,
         null,
         new GxModelsPage$$ExternalSyntheticLambda29(this),
         16,
         null
      );
      List var1 = CollectionsKt.listOf(new String[]{"both", "gpu", "comma"});
      GxPage.choice$default(
         this,
         "Model hardware",
         null,
         CollectionsKt.listOf(new String[]{"Both", "GPU models only", "Comma models only"}),
         RangesKt.coerceAtLeast(var1.indexOf(this.hardwareFilter), 0),
         null,
         new GxModelsPage$$ExternalSyntheticLambda30(this, var1),
         16,
         null
      );
      var1 = CollectionsKt.listOf(new String[]{"all", "yes", "no"});
      GxPage.choice$default(
         this,
         "Your Favorite",
         null,
         CollectionsKt.listOf(new String[]{"All", "Yes", "No"}),
         0,
         null,
         new GxModelsPage$$ExternalSyntheticLambda31(this, var1),
         16,
         null
      );
      GxPage.choice$default(
         this,
         "Community Favorite",
         null,
         CollectionsKt.listOf(new String[]{"All", "Yes", "No"}),
         0,
         null,
         new GxModelsPage$$ExternalSyntheticLambda32(this, var1),
         16,
         null
      );
      GxPage.header$default(this, "Models", null, 2, null);
      this.listBox = GxPage.box$default(this, null, 1, null);
      this.render(true);
      this.every(2000L, new GxModelsPage$$ExternalSyntheticLambda33(this));
   }

   @Override
   public boolean getNeedsOffroad() {
      return this.needsOffroad;
   }

   @Override
   public void onHide() {
      this.generation++;
      this.polling = false;
   }
}
