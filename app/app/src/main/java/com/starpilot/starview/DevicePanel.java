package com.starpilot.starview;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.Bitmap.Config;
import android.os.Handler;
import android.os.Looper;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.SurfaceHolder.Callback;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.qrcode.QRCodeWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.Result.Companion;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import org.json.JSONArray;
import org.json.JSONObject;

public final class DevicePanel extends ScrollView {
   private final Activity act;
   private boolean autoCan;
   private Boolean autoDl;
   private final LinearLayout col;
   private final float d;
   private final int dim;
   private final String e2eDesc;
   private final Handler fastHandler;
   private final Anon_fastPoll_1 fastPoll;
   private boolean fastPolling;
   private JSONObject fastStatus;
   private final String kind;
   private int lastGen;
   private int meteredSel;
   private final LinearLayout netList;
   private final TextView netStatus;
   private JSONObject offline;
   private final String personalityDesc;
   private int pollTick;
   private boolean scanning;
   private Boolean seven;
   private boolean syncing;
   private Switch teth;
   private final ArrayList<Function0<Unit>> updaters;
   private JSONObject wifi;

   // $VF: synthetic method
   public static boolean $r8$lambda$_7NHvO9L0uYPb0ifSQ8XzoF4d2Q/* $VF was: $r8$lambda$_7NHvO9L0uYPb0ifSQ8XzoF4d2Q*/(DevicePanel var0) {
      return buildToggles$lambda$52(var0);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$_IqnNBeDEvCa055w1k5nzylGWQ8/* $VF was: $r8$lambda$_IqnNBeDEvCa055w1k5nzylGWQ8*/(DevicePanel var0) {
      return buildDeveloper$lambda$194(var0);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$_KSvnp00V0kTSHkGMwQz4PwsooQ/* $VF was: $r8$lambda$_KSvnp00V0kTSHkGMwQz4PwsooQ*/(DevicePanel var0) {
      return buildToggles$lambda$68(var0);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$_NAo_FScSB9bk9wUaGqOBX6UB0k/* $VF was: $r8$lambda$_NAo_FScSB9bk9wUaGqOBX6UB0k*/(DevicePanel var0) {
      return buildToggles$lambda$77(var0);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$_S4UZXHQAsPAsD8pI6GFz3CivMs/* $VF was: $r8$lambda$_S4UZXHQAsPAsD8pI6GFz3CivMs*/(DevicePanel var0) {
      return buildToggles$lambda$64(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$_oomh2uKinl96fJxhVoxvz_3SAE/* $VF was: $r8$lambda$_oomh2uKinl96fJxhVoxvz_3SAE*/(
      Button var0,
      Function0 var1,
      Function0 var2,
      Function0 var3,
      TextView var4,
      LinearLayout var5,
      Function0 var6,
      Function0 var7,
      LinearLayout var8,
      DevicePanel var9
   ) {
      return button$lambda$20(var0, var1, var2, var3, var4, var5, var6, var7, var8, var9);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$0_AbOMhLXV7JXfz4JBpQBp38wQc/* $VF was: $r8$lambda$0_AbOMhLXV7JXfz4JBpQBp38wQc*/(DevicePanel var0) {
      return buildDevice$lambda$106(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$0JX_LimElAXCe2dKy6QCInwrcB0/* $VF was: $r8$lambda$0JX_LimElAXCe2dKy6QCInwrcB0*/(int var0, int var1) {
      return showDriverCamera$lambda$113(var0, var1);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$0lUI58VcwT9GIT_sYtDJFHrkjHY/* $VF was: $r8$lambda$0lUI58VcwT9GIT_sYtDJFHrkjHY*/(DevicePanel var0) {
      return buildDevice$lambda$107(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$23G0ERTcSfIVFECPenGSzqr0ik8(DevicePanel var0, String var1, boolean var2, JSONObject var3) {
      return connectWifi$go$lambda$129(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$2m8qBc0x1R4hGPddPy0uT3rz0C8(DevicePanel var0) {
      return buildDeveloper$lambda$192(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$3V0hM38jmu_spQcznEiujEMKDiU/* $VF was: $r8$lambda$3V0hM38jmu_spQcznEiujEMKDiU*/(Function1 var0, int var1, View var2) {
      multi$lambda$29$lambda$28$lambda$27(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$3WMZaMln_CCuxAYU6t5iFtS3_ck(DevicePanel var0, JSONObject var1) {
      return buildSoftware$lambda$226$lambda$225(var0, var1);
   }

   // $VF: synthetic method
   public static String $r8$lambda$3sK8usxuiCu7MAwrdA8qB8UzShA() {
      return buildDevice$lambda$100();
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$4HEyP1222atIn7GoU7WlVJN1_fg() {
      return multi$lambda$23();
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$58ENACizH31K_vaMkdS7rgR6VG8/* $VF was: $r8$lambda$58ENACizH31K_vaMkdS7rgR6VG8*/(DevicePanel var0, JSONObject var1) {
      return scan$lambda$127(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$5IxpK__N6amtlPo6Y62FPsXhUgU/* $VF was: $r8$lambda$5IxpK__N6amtlPo6Y62FPsXhUgU*/(DevicePanel var0) {
      return buildDevice$lambda$104$lambda$103(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$5U_9NjvhvS0XTVuuE_dQlFZTcGs/* $VF was: $r8$lambda$5U_9NjvhvS0XTVuuE_dQlFZTcGs*/(Function0 var0, View var1) {
      dual$lambda$36$lambda$35(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$5kmcNzoNcKq7JBIvf68j0S8aN7o(DevicePanel var0, boolean var1, Switch var2) {
      return buildToggles$lambda$75(var0, var1, var2);
   }

   // $VF: synthetic method
   public static String $r8$lambda$6VOxNHGa5FjBPfHxghi2XL4zW9Q(DevicePanel var0) {
      return buildDevice$lambda$80(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$6mGfGlAwjmApsxsRwGZzgSu1_8U(DevicePanel var0, String var1, boolean var2, boolean var3, View var4) {
      scan$lambda$127$lambda$125$lambda$124(var0, var1, var2, var3, var4);
   }

   // $VF: synthetic method
   public static void $r8$lambda$780JSxq7iMF9V7m6ROTx5kn2yTI(EditText var0, int var1, DevicePanel var2, Function1 var3, DialogInterface var4, int var5) {
      input$lambda$45(var0, var1, var2, var3, var4, var5);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$7VXFsP1PYdQfgUeeGHYxuQAUiRg(DevicePanel var0) {
      return buildDevice$lambda$94(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$7cXgxzgjUFKYKmk_ZR1Hn2NsxCY(DevicePanel var0, boolean var1, Switch var2) {
      return buildToggles$lambda$72(var0, var1, var2);
   }

   // $VF: synthetic method
   public static String $r8$lambda$8RcR20Z8HkB8gfmPR5YFEI2ARoU(DevicePanel var0) {
      return buildSoftware$lambda$207(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$8TwDnorplNMylya9fO7al_I5icU(DevicePanel var0, JSONObject var1, String var2) {
      return buildSoftware$lambda$222$lambda$221(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$9__JBoIm_vPzfRWlitdOs7wFs14/* $VF was: $r8$lambda$9__JBoIm_vPzfRWlitdOs7wFs14*/(DevicePanel var0, String var1) {
      return buildDeveloper$lambda$190$lambda$189(var0, var1);
   }

   // $VF: synthetic method
   public static String $r8$lambda$9CSKphZDPhjYP_OWe0r11cIPCks/* $VF was: $r8$lambda$9CSKphZDPhjYP_OWe0r11cIPCks*/(DevicePanel var0) {
      return buildNetwork$lambda$149(var0);
   }

   // $VF: synthetic method
   public static String $r8$lambda$9HNqskNn7RHHVhKJE1__Rsd3UvE/* $VF was: $r8$lambda$9HNqskNn7RHHVhKJE1__Rsd3UvE*/(DevicePanel var0) {
      return buildSoftware$lambda$214(var0);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$9a0jmYHw8TY88QmuH1Zf5rLyiqQ(DevicePanel var0) {
      return buildDeveloper$lambda$195(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$9hci_yeLxbG8BUhVHhfaO3LdttM/* $VF was: $r8$lambda$9hci_yeLxbG8BUhVHhfaO3LdttM*/(
      DevicePanel var0, String var1, LinearLayout var2
   ) {
      loadForkFixes$lambda$238(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$ADOMuCb5oWhLVbyQGjLD0_U_KXw/* $VF was: $r8$lambda$ADOMuCb5oWhLVbyQGjLD0_U_KXw*/(DevicePanel var0, boolean var1, Switch var2) {
      return buildNetwork$lambda$139(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$AUOg2YhMyqLg4kwDZIQGhtk0t7U(DevicePanel var0, boolean var1, Switch var2) {
      return buildToggles$lambda$78(var0, var1, var2);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$Acrykt_T7qSrhU55PWsKXkXU7So(DevicePanel var0) {
      return buildDeveloper$lambda$181(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$AiygPeEfTL2PwgzYGR3ERBGLEo4(DevicePanel var0, boolean var1, Switch var2) {
      return buildNetwork$lambda$170(var0, var1, var2);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$AvxJV991DosNxnLD2kYxuYHMZd4(DevicePanel var0) {
      return buildToggles$lambda$55(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$AxOKzyLWPaWlDxJ4czqRuNG0nDY(Function0 var0, DialogInterface var1, int var2) {
      confirm$lambda$43(var0, var1, var2);
   }

   // $VF: synthetic method
   public static String $r8$lambda$Baul8GwkhiSRoUGHYBGKwKakvMQ() {
      return buildSoftware$lambda$227();
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$Bq2Kf_83iVEcR9_5ZUQDBmQ71vg/* $VF was: $r8$lambda$Bq2Kf_83iVEcR9_5ZUQDBmQ71vg*/(DevicePanel var0) {
      return buildNetwork$lambda$151(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$C9DxPN7f43KpaWGY8G4IHE3Rmu8(DevicePanel var0, String var1, boolean var2, String var3) {
      return connectWifi$go$lambda$129$lambda$128(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static void $r8$lambda$CO3xgVdUwrcnGYqcnktCrTzg5gc(Function0 var0, View var1) {
      button$lambda$18$lambda$17(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$C_0aHNX2oFLaPLyzSQaHF_qtiU0(DevicePanel var0, JSONObject var1) {
      return buildNetwork$lambda$180$lambda$179$lambda$178$lambda$177(var0, var1);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$Clw_yMs7hBjDpkHlS3YgrAblWLI(DevicePanel var0) {
      return buildToggles$lambda$65(var0);
   }

   // $VF: synthetic method
   public static String $r8$lambda$Cq323ly0S3WDCIlqHri2pMvK8AM(DevicePanel var0) {
      return buildDevice$lambda$82(var0);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$CyDHfyutDilo3E9XJOssDsuUI0A() {
      return button$lambda$15();
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$CypdD__EBlffFnI6uPWcUaKT01k/* $VF was: $r8$lambda$CypdD__EBlffFnI6uPWcUaKT01k*/(DevicePanel var0, String var1) {
      return buildNetwork$lambda$180$lambda$179(var0, var1);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$E3V_iMnKB6ctt2__nTQHo1pXSEs(DevicePanel var0) {
      return buildSoftware$lambda$212(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$ELVdlVqy1ghp0LJToCjpKXIlS9Y(DevicePanel var0, JSONObject var1) {
      return onTick$lambda$51(var0, var1);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$EPvtFhBbW7xH_eju28D7OXc1mq0/* $VF was: $r8$lambda$EPvtFhBbW7xH_eju28D7OXc1mq0*/() {
      return dual$lambda$33();
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$EQ_KYSAbAtYQ7rev538LALPTtJY/* $VF was: $r8$lambda$EQ_KYSAbAtYQ7rev538LALPTtJY*/(DevicePanel var0, JSONObject var1) {
      return buildNetwork$lambda$159$lambda$158(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$G5yNB1igBIWGLO__GvT9BgUiVe4/* $VF was: $r8$lambda$G5yNB1igBIWGLO__GvT9BgUiVe4*/(Function0 var0, Function0 var1, List var2) {
      return multi$lambda$32(var0, var1, var2);
   }

   // $VF: synthetic method
   public static String $r8$lambda$GTLRvQ9FClQGAZA85gy5dhHTSgI(DevicePanel var0) {
      return buildDeveloper$lambda$187(var0);
   }

   // $VF: synthetic method
   public static String $r8$lambda$Gi93sSDj5SyLnX_f4ldH8DnFkIk() {
      return buildNetwork$lambda$176();
   }

   // $VF: synthetic method
   public static String $r8$lambda$HgpIZ458G1DH19HHjb8O7TxPCLg(DevicePanel var0) {
      return buildDevice$lambda$97(var0);
   }

   // $VF: synthetic method
   public static String $r8$lambda$IoTPvSCLJr_M8PbihgWDRaReoVY/* $VF was: $r8$lambda$IoTPvSCLJr_M8PbihgWDRaReoVY*/() {
      return buildNetwork$lambda$156();
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$J8YDq6Ud3Y7TcLwx7M_4XRQlaP0/* $VF was: $r8$lambda$J8YDq6Ud3Y7TcLwx7M_4XRQlaP0*/(DevicePanel var0) {
      return buildNetwork$lambda$159(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$JW3RM8QKB6yl9NzjhLfge5vMj9E(DevicePanel var0, JSONObject var1) {
      return buildNetwork$lambda$151$lambda$150(var0, var1);
   }

   // $VF: synthetic method
   public static int $r8$lambda$JwQqbkcNnFXrSnog9XbIyNNsL4A(DevicePanel var0) {
      return buildNetwork$lambda$171(var0);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$JyIBlqD1vdmhoscpNxUM2H3uR7M(DevicePanel var0) {
      return buildNetwork$lambda$147(var0);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$K2fgvqwZ7QdXuNbln9NV5HIjeyA() {
      return toggle$lambda$9();
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$KTZ3ztQoehskF1hMTLkILFJPBww(DevicePanel var0) {
      return buildNetwork$lambda$136(var0);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$KobigtuOUi9ITGDfxI6U9XCDYnM(DevicePanel var0) {
      return buildToggles$lambda$74(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$L9ft_O6asT9ywqbu91Bh2aEiev4(DevicePanel var0, String var1) {
      return buildNetwork$lambda$159$lambda$158$lambda$157(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$LAn2JUdm41yD7OCsrklankOvi34(DevicePanel var0, boolean var1, Switch var2) {
      return buildDeveloper$lambda$200(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$LrNd9sWwIKWBenoJ0oxc1ftLPBg(DevicePanel var0) {
      return buildSoftware$lambda$217$lambda$216(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$MNsMwzlm8WvVHaZNSFmt4U0cv5M(DevicePanel var0, int var1) {
      return buildToggles$lambda$63(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$MV7_QIE_4lSp41aE0JbNuSrNgcc/* $VF was: $r8$lambda$MV7_QIE_4lSp41aE0JbNuSrNgcc*/(DevicePanel var0, JSONObject var1) {
      return onTick$lambda$50(var0, var1);
   }

   // $VF: synthetic method
   public static String $r8$lambda$Mhn6g1SwOP4ysTjxxcd0q6MU8iI() {
      return button$lambda$13();
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$MyyuJrO4qi0mjyJm_f_zzWODckg(DevicePanel var0) {
      return buildNetwork$lambda$168(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$N8V9x2rY0WDlvPEzvak42zfM2nc(DevicePanel var0, boolean var1, Switch var2) {
      return buildNetwork$lambda$163(var0, var1, var2);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$NVxHIPReeptv25OAHnmfOHSnsjI(DevicePanel var0) {
      return buildToggles$lambda$62(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$O6lvVHMfE80DrtA13MbZlE__Gyk(DevicePanel var0, JSONObject var1) {
      return buildDeveloper$lambda$190$lambda$189$lambda$188(var0, var1);
   }

   // $VF: synthetic method
   public static int $r8$lambda$O7_f2opNDE23_6m7A8VZ5NeoZ40(DevicePanel var0) {
      return buildToggles$lambda$61(var0);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$OCj4y_hED4DjpN94G63dNSylYoY(DevicePanel var0) {
      return buildDevice$lambda$96(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$OT2GjQg216hyytGpI1kuC3PUiKA(DevicePanel var0, JSONObject var1) {
      return buildNetwork$lambda$140(var0, var1);
   }

   // $VF: synthetic method
   public static String $r8$lambda$OYKyNgsJL7q_qwHrs_eUcSpxxCo/* $VF was: $r8$lambda$OYKyNgsJL7q_qwHrs_eUcSpxxCo*/() {
      return buildDevice$lambda$89();
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$P6ymuLorQOMWAm5otlYJUkY0HMg(DevicePanel var0) {
      return buildNetwork$lambda$142(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$P7YaJwqct8S4NUCmmVOD43El0U4(DevicePanel var0, JSONObject var1) {
      return buildNetwork$lambda$145$lambda$144(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$PCEnKbPKLYA3KtvzQL77jKrxi1A(DevicePanel var0) {
      return buildDevice$lambda$102(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$POticVTv9Q4NjSgjF6Wc64Yfkyw(DevicePanel var0, View var1) {
      buildNetwork$lambda$134$lambda$133(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$PPR7ORHAjFobuoKk1D4ZafXWU7E(DevicePanel var0, Function2 var1, Switch var2, CompoundButton var3, boolean var4) {
      toggle$lambda$11(var0, var1, var2, var3, var4);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$PdC27UcUFOmOoPu8KeFGfm9opbo() {
      return toggle$lambda$8();
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$QBUh6mxJafz2QW3fzYfNjgiJn4w(DevicePanel var0) {
      return buildToggles$lambda$58(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$Q_LMX0fqkGw3Gg3oTd_QQ0ZHziI/* $VF was: $r8$lambda$Q_LMX0fqkGw3Gg3oTd_QQ0ZHziI*/(Button var0, Function0 var1) {
      return dual$lambda$40(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$S3YxzrekJuoxrgILCXw_hc4KB_w/* $VF was: $r8$lambda$S3YxzrekJuoxrgILCXw_hc4KB_w*/(DevicePanel var0, boolean var1, Switch var2) {
      return buildToggles$lambda$66(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$S9Pts83TK1CcWb8csDrRuf8XRL0(DevicePanel var0, JSONObject var1) {
      return buildNetwork$lambda$175$lambda$174(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$SXeBq0hm_cOvMeZ5ZMBHw79wHc0/* $VF was: $r8$lambda$SXeBq0hm_cOvMeZ5ZMBHw79wHc0*/(DevicePanel var0, boolean var1, Switch var2) {
      return buildToggles$lambda$54(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$SlZakOH3uTItex8BzL1V0cSndb0(DevicePanel var0, boolean var1, Switch var2) {
      return buildDeveloper$lambda$183(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$Sp4o7vljrzFrF_CNoG82YFCmTc4(DevicePanel var0) {
      return buildDevice$lambda$104(var0);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$TJTJoaLoMBBTzVtRPT3E2cjpLS0(DevicePanel var0) {
      return buildToggles$lambda$53(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$Te4pVDRozVpekuGNn32ONqHZgk0(DevicePanel var0) {
      return buildDevice$lambda$106$lambda$105(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$Tks4Ma4Re1PrayqFQiHeZ3b1sEI(DevicePanel var0) {
      return buildSoftware$lambda$217(var0);
   }

   // $VF: synthetic method
   public static String $r8$lambda$TpMVH8wpmBrV6PM8ZKHJWDAbAkI(DevicePanel var0) {
      return buildSoftware$lambda$211(var0);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$Tr1P43FtKZI8mhobKjkwKGWahJk(DevicePanel var0) {
      return buildDeveloper$lambda$199(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$UKW_i24gkYcd7NW7W9iQpa9ZJHA(DevicePanel var0, boolean var1, Switch var2) {
      return buildNetwork$lambda$145(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$Ud6X6F47jSh95AIQMiasgiRY5s0(DevicePanel var0, boolean var1, Switch var2) {
      return buildNetwork$lambda$155(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$UqSwoG4QJhoJwOfbbJG4xPyqlrQ(DevicePanel var0) {
      return buildDevice$lambda$99(var0);
   }

   // $VF: synthetic method
   public static String $r8$lambda$Vspwlp7Pqby_YuyvfsbNxheTgn8/* $VF was: $r8$lambda$Vspwlp7Pqby_YuyvfsbNxheTgn8*/() {
      return buildDevice$lambda$91();
   }

   // $VF: synthetic method
   public static void $r8$lambda$W_GJusxyeKxKB_gyGAUQETqO26g/* $VF was: $r8$lambda$W_GJusxyeKxKB_gyGAUQETqO26g*/(VideoClient var0, DialogInterface var1) {
      showDriverCamera$lambda$114(var0, var1);
   }

   // $VF: synthetic method
   public static String $r8$lambda$WfLdtQE3m9CyOg5GayXj3iwZ_SI/* $VF was: $r8$lambda$WfLdtQE3m9CyOg5GayXj3iwZ_SI*/(DevicePanel var0) {
      return buildSoftware$lambda$209(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$WymqgQw9gdVk43NqJ4mlQr309j4(DevicePanel var0, boolean var1, Switch var2) {
      return buildDeveloper$lambda$193(var0, var1, var2);
   }

   // $VF: synthetic method
   public static String $r8$lambda$Xt6WA0k25bl0OPfK7gW7WjXc2VY() {
      return buildNetwork$lambda$146();
   }

   // $VF: synthetic method
   public static String $r8$lambda$Y4JkxHQ4DS_pJThpKFFptLotFhw(DevicePanel var0) {
      return buildNetwork$lambda$143(var0);
   }

   // $VF: synthetic method
   public static String $r8$lambda$Y9yKFrnVYuT9T6FHMeCy_JDT4ZE/* $VF was: $r8$lambda$Y9yKFrnVYuT9T6FHMeCy_JDT4ZE*/(DevicePanel var0) {
      return buildNetwork$lambda$166(var0);
   }

   // $VF: synthetic method
   public static String $r8$lambda$YbKipCRIGgPv3LOd7U82rDuArAk() {
      return buildDevice$lambda$83();
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$Zi5hrj5tBE_axtLy73_do6FeprI/* $VF was: $r8$lambda$Zi5hrj5tBE_axtLy73_do6FeprI*/(DevicePanel var0) {
      return buildDevice$lambda$88(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$_Wq5ZZ3FjRxDyfTfpJLJbld1M7w(Function1 var0, List var1, DialogInterface var2, int var3) {
      choose$lambda$46(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$_dEVUjcIVjMf7XfBPorvmI0OETg(DevicePanel var0, TextView var1, String var2) {
      return showDriverCamera$lambda$112(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$a489LFgmBp1vRiYsokIMMpmsZGA(DevicePanel var0) {
      return buildSoftware$lambda$226(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$aXKnPueUgdsnHpVb8apPMVkYwjQ(DevicePanel var0, boolean var1, Switch var2) {
      return buildSoftware$lambda$222(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$af3bv3a4XU1AVYwOVgHqImKRKa0(DevicePanel var0, JSONObject var1) {
      return scan$lambda$127$lambda$123$lambda$122$lambda$121$lambda$120(var0, var1);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$af7pVHJakkPseM66aYEIzQlJ17Q(DevicePanel var0) {
      return buildNetwork$lambda$172(var0);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$bJvh2ocJvHexciTtmc1pfrkA9Lg(DevicePanel var0) {
      return buildNetwork$lambda$141(var0);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$bwaMKEnMkRwzu5RgxQkHtsUko5E(DevicePanel var0) {
      return buildNetwork$lambda$162(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$cCRXh1zbWxcLMjzA2chQUqEmPS4(DevicePanel var0) {
      return buildNetwork$lambda$180(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$ckR_847j7IzlcqwTKq7W_f0Kfp8/* $VF was: $r8$lambda$ckR_847j7IzlcqwTKq7W_f0Kfp8*/(Function0 var0, View var1) {
      dual$lambda$38$lambda$37(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$csGCFHqRUAJOTkiNI1r_yZsEKWI/* $VF was: $r8$lambda$csGCFHqRUAJOTkiNI1r_yZsEKWI*/(DevicePanel var0, String var1, String var2) {
      return buildNetwork$lambda$180$lambda$179$lambda$178(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$cyzOHXnyj_ZrlVgnpkMFp0ea8fo/* $VF was: $r8$lambda$cyzOHXnyj_ZrlVgnpkMFp0ea8fo*/(DevicePanel var0, JSONObject var1) {
      return buildNetwork$lambda$139$lambda$138(var0, var1);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$dqxKZJKGLhgrcNQnflB1yEjDZic(DevicePanel var0) {
      return buildDeveloper$lambda$182(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$e1SMYZLgb9EEYvcb9Ev35GrNyRQ(TextView var0, String var1) {
      showDriverCamera$lambda$112$lambda$111(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$eF2dOGiPUSUtcb1E1zl4LYMPy1o(DevicePanel var0, boolean var1, Switch var2) {
      return buildDeveloper$lambda$185(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$ejSJoIunAdnzn9HunftfUzgZSX8(DevicePanel var0) {
      return buildSoftware$lambda$228(var0);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$fBMUeuH1KxRmeT1LCPpYvL8eCpM(DevicePanel var0) {
      return buildSoftware$lambda$219(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$gKnuLkxBfBl0I_BCn_I8G38qE2U/* $VF was: $r8$lambda$gKnuLkxBfBl0I_BCn_I8G38qE2U*/(DevicePanel var0) {
      return buildDevice$lambda$86(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$h2SoI5A2RKQdblaqipCc1MiRla4(Function1 var0, DevicePanel var1, JSONObject var2) {
      return call$lambda$42(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$hSv1360KrbdReiVzHnM7o_MFV2k/* $VF was: $r8$lambda$hSv1360KrbdReiVzHnM7o_MFV2k*/(DevicePanel var0, String var1) {
      return buildNetwork$lambda$168$lambda$167(var0, var1);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$hakSuAiphwNLQwHTeo6_6qadDtA/* $VF was: $r8$lambda$hakSuAiphwNLQwHTeo6_6qadDtA*/(DevicePanel var0) {
      return buildDeveloper$lambda$196(var0);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$hlseWoCjjeg3PGGHh4e1zunNras(DevicePanel var0) {
      return buildNetwork$lambda$152(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$hrSLKtQ17YKq8iZxFTdAtXrwp7g(DevicePanel var0, Function1 var1, JSONObject var2) {
      return setParam$lambda$41(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$iB_dR_Kt_DL_0PKHDfb8kox89Xw/* $VF was: $r8$lambda$iB_dR_Kt_DL_0PKHDfb8kox89Xw*/(DevicePanel var0, JSONObject var1) {
      return buildNetwork$lambda$175$lambda$174$lambda$173(var0, var1);
   }

   // $VF: synthetic method
   public static String $r8$lambda$j7_ILxiAKrPlEWuAhV3dMSOywAs(DevicePanel var0) {
      return buildNetwork$lambda$137(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$jNUDBCgrUyeW56HgExCLyQ70fi0(DevicePanel var0, boolean var1, Switch var2) {
      return buildDeveloper$lambda$198(var0, var1, var2);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$jUy85sJKVE6ft2zZR8mBrySlWKc(DevicePanel var0) {
      return buildNetwork$lambda$169(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$jhgmeT2YjhmylUp_DMwEOOPEFWE(DevicePanel var0, boolean var1, Switch var2) {
      return buildToggles$lambda$57(var0, var1, var2);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$jopW6qAc_4_jEObFku1Zx9iSHwQ/* $VF was: $r8$lambda$jopW6qAc_4_jEObFku1Zx9iSHwQ*/(DevicePanel var0) {
      return buildNetwork$lambda$135(var0);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$jrvWh3D50kxumrEncyLC1V8V7Fw(DevicePanel var0) {
      return buildToggles$lambda$56(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$jsmFt7siLCBirm1AlXQoeKRARbo(DevicePanel var0) {
      return buildDeveloper$lambda$198$lambda$197(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$k8Vh7cbjzb2LTdSE8WaPTItA6AY(DevicePanel var0, JSONObject var1) {
      return buildNetwork$lambda$155$lambda$154$lambda$153(var0, var1);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$kRErHtUHQuRjGTAujflM0_gbdm4/* $VF was: $r8$lambda$kRErHtUHQuRjGTAujflM0_gbdm4*/(DevicePanel var0) {
      return buildToggles$lambda$59(var0);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$kjYjOoQZR3YCTR7yHD_7f_k_rSE/* $VF was: $r8$lambda$kjYjOoQZR3YCTR7yHD_7f_k_rSE*/(DevicePanel var0) {
      return buildToggles$lambda$70(var0);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$l95S_YVsJ3Pr9JbzM9purJ3L7a4/* $VF was: $r8$lambda$l95S_YVsJ3Pr9JbzM9purJ3L7a4*/(DevicePanel var0) {
      return buildDevice$lambda$84(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$lMjq5boeWTYuNAKN_VCi6DDCPfI/* $VF was: $r8$lambda$lMjq5boeWTYuNAKN_VCi6DDCPfI*/(TextView var0, Function0 var1) {
      return text$lambda$22(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$lZxjWQedp1RxfiZMmwoG_lCc5WQ/* $VF was: $r8$lambda$lZxjWQedp1RxfiZMmwoG_lCc5WQ*/(DevicePanel var0, String var1, View var2) {
      scan$lambda$127$lambda$123$lambda$122(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$mod0QhrJ5m_qYbGWJVtDARG2Xi0/* $VF was: $r8$lambda$mod0QhrJ5m_qYbGWJVtDARG2Xi0*/(DevicePanel var0, String var1, JSONObject var2) {
      return startFastUpdate$lambda$205(var0, var1, var2);
   }

   // $VF: synthetic method
   public static String $r8$lambda$oVYFjJOowsJzDAhBNRHSB95Rlww(DevicePanel var0) {
      return buildNetwork$lambda$161(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$orO1M8Tu9Yy8f4_OkJ0YrO98Gc0/* $VF was: $r8$lambda$orO1M8Tu9Yy8f4_OkJ0YrO98Gc0*/(DevicePanel var0, int var1) {
      return buildNetwork$lambda$175(var0, var1);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$p_XOUC5kP2bLPwUZVyufLkB3i_w/* $VF was: $r8$lambda$p_XOUC5kP2bLPwUZVyufLkB3i_w*/(DevicePanel var0) {
      return buildSoftware$lambda$218(var0);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$pZQancLbnIhIjwfpHcbJdmEz1A4(DevicePanel var0) {
      return buildToggles$lambda$76(var0);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$q4z4rJWtHopryFnDTV8T1bPC_OI/* $VF was: $r8$lambda$q4z4rJWtHopryFnDTV8T1bPC_OI*/(DevicePanel var0) {
      return buildDeveloper$lambda$191(var0);
   }

   // $VF: synthetic method
   public static String $r8$lambda$qIwvUrgkjvIEOsb917I6Ctmw7k0() {
      return buildDevice$lambda$87();
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$qXnrU1qsbh4qPV7bWe_KBPWoI_o(DevicePanel var0) {
      return buildDevice$lambda$90(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$qw2n_DJ6twU8RzwuZcom7KKWQLg(DevicePanel var0, String var1, boolean var2, String var3) {
      return connectWifi$lambda$130(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$rLDkFjsAJHrJq1kLDzDTvVQ8NRE(DevicePanel var0) {
      return buildToggles$lambda$73(var0);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$rZCJ9FRbeog3SwJYTqY1mwy1vNM() {
      return button$lambda$14();
   }

   // $VF: synthetic method
   public static String $r8$lambda$sE2ltcoBDwTgdpZBcXs8H4H_RNs() {
      return buildDevice$lambda$95();
   }

   // $VF: synthetic method
   public static String $r8$lambda$sMnIEztgdHwyvF__AgDN7s6lRCg() {
      return buildNetwork$lambda$164();
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$tM8HHXdfAMnT4fXeCS5gvn4nKAI(DevicePanel var0) {
      return buildDevice$lambda$92(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$tWc_vYMaMwivBFGVEz0043mi3Z4/* $VF was: $r8$lambda$tWc_vYMaMwivBFGVEz0043mi3Z4*/(
      LinearLayout var0, List var1, DevicePanel var2, String var3
   ) {
      loadForkFixes$lambda$238$lambda$237(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$tcBugrU0T7sEUuq7p7QUa_yjOq4(DevicePanel var0) {
      return buildDeveloper$lambda$190(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$tq1yme6dOzklPAdU8xXVcUEBgww(
      DevicePanel var0, Switch var1, Function0 var2, Function0 var3, LinearLayout var4, Function0 var5, Function0 var6, LinearLayout var7
   ) {
      return toggle$lambda$12(var0, var1, var2, var3, var4, var5, var6, var7);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$vA6IJvQUFTa_sxcr6h2ekM1zZmQ/* $VF was: $r8$lambda$vA6IJvQUFTa_sxcr6h2ekM1zZmQ*/(DevicePanel var0) {
      return buildToggles$lambda$67(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$w3V2ok3cFcY_uIsiihHnre7JJyY/* $VF was: $r8$lambda$w3V2ok3cFcY_uIsiihHnre7JJyY*/(DevicePanel var0, JSONObject var1) {
      return buildDevice$lambda$86$lambda$85(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$w8kzwOCN7p9UWM1eVBPbYHA9Jaw(DevicePanel var0, JSONObject var1, String var2) {
      return loadAuto$lambda$202(var0, var1, var2);
   }

   // $VF: synthetic method
   public static String $r8$lambda$wDMbmhUPEbGSjHhQcNtv2uyaadQ(DevicePanel var0) {
      return buildDeveloper$lambda$186(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$wErAPPLfv219gxKUqB76eNVJSX4(DevicePanel var0, JSONObject var1) {
      return buildNetwork$lambda$155$lambda$154(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$xgD1YE_AKtMuHDmsS3zcsMXMxT8/* $VF was: $r8$lambda$xgD1YE_AKtMuHDmsS3zcsMXMxT8*/(DevicePanel var0, String var1) {
      return scan$lambda$127$lambda$123$lambda$122$lambda$121(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$xiJM89XMkyo2EfHGqX7ZuUG9Ras(DevicePanel var0) {
      return buildDevice$lambda$99$lambda$98(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$xvR5b_Vpfeg6ktGHKNJX7pkiw4E(DevicePanel var0, boolean var1, Switch var2) {
      return buildToggles$lambda$60(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$y7esPAyp2tx8QGhTWv7WqZW0JYI(DevicePanel var0, JSONObject var1) {
      return startFastUpdate$lambda$203(var0, var1);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$yKMrCCmN7BWziq6_ZqGxK_zerqk/* $VF was: $r8$lambda$yKMrCCmN7BWziq6_ZqGxK_zerqk*/(DevicePanel var0) {
      return buildDeveloper$lambda$184(var0);
   }

   // $VF: synthetic method
   public static CharSequence $r8$lambda$yU4D2wfKcC2JJK75UAFOM8fkNss(JSONArray var0, int var1) {
      return buildNetwork$lambda$149$lambda$148(var0, var1);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$ynrT0iWHZIwrMb8KjeEoolD4ONw(DevicePanel var0) {
      return buildToggles$lambda$71(var0);
   }

   // $VF: synthetic method
   public static String $r8$lambda$z3EuUAwpSlKVo0g7mhPYf0kTsoM() {
      return buildSoftware$lambda$223();
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$zeoeuyS9h_gP8xNNV_d20NhbfkM/* $VF was: $r8$lambda$zeoeuyS9h_gP8xNNV_d20NhbfkM*/(DevicePanel var0) {
      return buildDevice$lambda$94$lambda$93(var0);
   }

   // $VF: synthetic method
   public static String $r8$lambda$zgzqLdwf_W6KOe4C5CCxCNHo21w/* $VF was: $r8$lambda$zgzqLdwf_W6KOe4C5CCxCNHo21w*/(DevicePanel var0) {
      return buildSoftware$lambda$210(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$zlQf189jyNRV0v0RA3ejbHCt1V8(DevicePanel var0, boolean var1, Switch var2) {
      return buildToggles$lambda$69(var0, var1, var2);
   }

   public DevicePanel(Activity var1, String var2) {
      Intrinsics.checkNotNullParameter(var1, "act");
      Intrinsics.checkNotNullParameter(var2, "kind");
      super((Context)var1);
      this.act = var1;
      this.kind = var2;
      this.d = this.act.getResources().getDisplayMetrics().density;
      LinearLayout var3 = new LinearLayout((Context)this.act);
      var3.setOrientation(1);
      var3.setPadding(this.dp(8), this.dp(4), this.dp(20), this.dp(40));
      this.col = var3;
      this.updaters = new ArrayList<>();
      this.lastGen = -1;
      this.dim = Color.rgb(143, 163, 199);
      this.personalityDesc = "Standard is recommended. In aggressive mode, openpilot will follow lead cars closer and be more aggressive with the gas and brake. In relaxed mode openpilot will stay further away from lead cars. On supported cars, you can cycle through these personalities with your steering wheel distance button.";
      this.e2eDesc = "openpilot defaults to driving in chill mode. Experimental mode enables alpha-level features that aren't ready for chill mode: end-to-end longitudinal control (the driving model controls gas and brakes, stopping for red lights and stop signs; the set speed is only an upper bound) and the new driving visualization. This is an alpha quality feature; mistakes should be expected.";
      LinearLayout var4 = new LinearLayout((Context)this.act);
      var4.setOrientation(1);
      this.netList = var4;
      TextView var5 = new TextView((Context)this.act);
      var5.setTextColor(this.dim);
      var5.setTextSize(14.0F);
      var5.setPadding(this.dp(4), this.dp(4), this.dp(4), this.dp(8));
      this.netStatus = var5;
      this.wifi = new JSONObject();
      this.offline = new JSONObject();
      this.fastHandler = new Handler(Looper.getMainLooper());
      this.fastPoll = new Anon_fastPoll_1(this);
      this.addView((View)this.col, new LayoutParams(-1, -2));
      String var6 = this.kind;
      switch (var6.hashCode()) {
         case -1335157162:
            if (var6.equals("device")) {
               this.buildDevice();
            }
            break;
         case -1147621473:
            if (var6.equals("toggles")) {
               this.buildToggles();
            }
            break;
         case -80681014:
            if (var6.equals("developer")) {
               this.buildDeveloper();
            }
            break;
         case 1319330215:
            if (var6.equals("software")) {
               this.buildSoftware();
            }
            break;
         case 1843485230:
            if (var6.equals("network")) {
               this.buildNetwork();
            }
      }

      this.refresh(true);
   }

   private final void ackToast(JSONObject var1) {
      if (!var1.optBoolean("ok")) {
         String var2 = var1.optString("error", "rejected");
         Intrinsics.checkNotNullExpressionValue(var2, "optString(...)");
         this.toast(var2);
      }
   }

   private final void add(View var1) {
      android.widget.LinearLayout.LayoutParams var2 = new android.widget.LinearLayout.LayoutParams(-1, -2);
      var2.bottomMargin = this.dp(6);
      this.col.addView(var1, (LayoutParams)var2);
   }

   private final void alert(String var1, CharSequence var2) {
      new Builder((Context)this.act).setTitle(var1).setMessage(var2).setPositiveButton("OK", null).show();
   }

   private final void applyWifiStatus(JSONObject var1) {
      this.wifi = var1;
      String var7 = var1.optString("ssid");
      String var5 = var1.optString("ip");
      TextView var6 = this.netStatus;
      boolean var4 = var1.optBoolean("tethering");
      byte var3 = 0;
      CharSequence var10;
      if (var4) {
         var7 = var1.optString("tether_ssid");
         var10 = "Tethering active (" + var7 + ") · " + var5;
      } else if (var1.optBoolean("connecting")) {
         var10 = "Connecting…";
      } else {
         Intrinsics.checkNotNull(var7);
         boolean var2;
         if (var7.length() > 0) {
            var2 = true;
         } else {
            var2 = false;
         }

         if (var2) {
            var10 = "Connected to " + var7 + " · " + var5;
         } else {
            var10 = "Not connected to Wi-Fi";
         }
      }

      var6.setText(var10);
      this.syncing = true;
      Switch var11 = this.teth;
      if (var11 != null) {
         var11.setChecked(var1.optBoolean("tethering"));
      }

      Switch var12 = this.teth;
      if (var12 != null) {
         var12.setEnabled(true);
      }

      this.syncing = false;
      String var8 = var1.optString("metered");
      byte var9;
      if (Intrinsics.areEqual(var8, "yes")) {
         var9 = 1;
      } else {
         var9 = var3;
         if (Intrinsics.areEqual(var8, "no")) {
            var9 = 2;
         }
      }

      this.meteredSel = var9;
      this.refresh(true);
   }

   private final void buildDeveloper() {
      toggle$default(
         this,
         "Enable ADB",
         "ADB (Android Debug Bridge) allows connecting to your device over USB or over the network. See https://docs.comma.ai/how-to/connect-to-comma for more info.",
         new DevicePanel$$ExternalSyntheticLambda51(this),
         new DevicePanel$$ExternalSyntheticLambda60(this),
         null,
         null,
         new DevicePanel$$ExternalSyntheticLambda61(this),
         48,
         null
      );
      toggle$default(
         this,
         "Enable SSH",
         null,
         new DevicePanel$$ExternalSyntheticLambda62(this),
         null,
         null,
         null,
         new DevicePanel$$ExternalSyntheticLambda63(this),
         56,
         null
      );
      DevicePanel$$ExternalSyntheticLambda64 var1 = new DevicePanel$$ExternalSyntheticLambda64(this);
      button$default(
         this,
         "SSH Keys",
         var1,
         "Warning: This grants SSH access to all public keys in your GitHub settings. Never enter a GitHub username other than your own. A comma employee will NEVER ask you to add their GitHub username.",
         new DevicePanel$$ExternalSyntheticLambda65(this),
         null,
         null,
         null,
         new DevicePanel$$ExternalSyntheticLambda67(this),
         112,
         null
      );
      toggle$default(
         this,
         "Joystick Debug Mode",
         null,
         new DevicePanel$$ExternalSyntheticLambda68(this),
         new DevicePanel$$ExternalSyntheticLambda69(this),
         null,
         null,
         new DevicePanel$$ExternalSyntheticLambda52(this),
         48,
         null
      );
      toggle$default(
         this,
         "openpilot Longitudinal Control (Alpha)",
         "WARNING: openpilot longitudinal control is in alpha for this car and will disable Automatic Emergency Braking (AEB). On this car, openpilot defaults to the car's built-in ACC instead of openpilot's longitudinal control. Enable this to switch to openpilot longitudinal control. Enabling Experimental mode is recommended when enabling openpilot longitudinal control alpha. Changing this setting will restart openpilot if the car is powered on.",
         new DevicePanel$$ExternalSyntheticLambda53(this),
         new DevicePanel$$ExternalSyntheticLambda54(this),
         new DevicePanel$$ExternalSyntheticLambda56(this),
         null,
         new DevicePanel$$ExternalSyntheticLambda57(this),
         32,
         null
      );
      toggle$default(
         this,
         "UI Debug Mode",
         "Shows touches and FPS on the comma's own screen.",
         new DevicePanel$$ExternalSyntheticLambda58(this),
         null,
         null,
         null,
         new DevicePanel$$ExternalSyntheticLambda59(this),
         56,
         null
      );
   }

   private static final boolean buildDeveloper$lambda$181(DevicePanel var0) {
      return var0.p("AdbEnabled");
   }

   private static final boolean buildDeveloper$lambda$182(DevicePanel var0) {
      return var0.onroad() ^ true;
   }

   private static final Unit buildDeveloper$lambda$183(DevicePanel var0, boolean var1, Switch var2) {
      Intrinsics.checkNotNullParameter(var2, "<unused var>");
      setParam$default(var0, "AdbEnabled", var1, false, null, 12, null);
      return Unit.INSTANCE;
   }

   private static final boolean buildDeveloper$lambda$184(DevicePanel var0) {
      return var0.p("SshEnabled");
   }

   private static final Unit buildDeveloper$lambda$185(DevicePanel var0, boolean var1, Switch var2) {
      Intrinsics.checkNotNullParameter(var2, "<unused var>");
      setParam$default(var0, "SshEnabled", var1, false, null, 12, null);
      return Unit.INSTANCE;
   }

   private static final String buildDeveloper$lambda$186(DevicePanel var0) {
      String var1;
      if (var0.i("has_ssh_keys")) {
         var1 = "REMOVE";
      } else {
         var1 = "ADD";
      }

      return var1;
   }

   private static final String buildDeveloper$lambda$187(DevicePanel var0) {
      return var0.pStr("GithubUsername");
   }

   private static final Unit buildDeveloper$lambda$190(DevicePanel var0) {
      if (var0.i("has_ssh_keys")) {
         call$default(var0, "ssh_remove", null, null, 6, null);
      } else {
         input$default(var0, "Enter your GitHub username", "username", null, false, 0, new DevicePanel$$ExternalSyntheticLambda80(var0), 28, null);
      }

      return Unit.INSTANCE;
   }

   private static final Unit buildDeveloper$lambda$190$lambda$189(DevicePanel var0, String var1) {
      Intrinsics.checkNotNullParameter(var1, "u");
      var0.toast("Fetching keys…");
      var0.call("ssh_add", MapsKt.mapOf(TuplesKt.to("username", var1)), new DevicePanel$$ExternalSyntheticLambda165(var0));
      return Unit.INSTANCE;
   }

   private static final Unit buildDeveloper$lambda$190$lambda$189$lambda$188(DevicePanel var0, JSONObject var1) {
      Intrinsics.checkNotNullParameter(var1, "r");
      if (var1.optBoolean("ok")) {
         int var2 = var1.optInt("keys");
         String var3 = var1.optString("username");
         var0.toast("Added " + var2 + " key(s) for " + var3);
      } else {
         var0.ackToast(var1);
      }

      return Unit.INSTANCE;
   }

   private static final boolean buildDeveloper$lambda$191(DevicePanel var0) {
      return var0.p("JoystickDebugMode");
   }

   private static final boolean buildDeveloper$lambda$192(DevicePanel var0) {
      return var0.onroad() ^ true;
   }

   private static final Unit buildDeveloper$lambda$193(DevicePanel var0, boolean var1, Switch var2) {
      Intrinsics.checkNotNullParameter(var2, "<unused var>");
      setParam$default(var0, "JoystickDebugMode", var1, false, null, 12, null);
      return Unit.INSTANCE;
   }

   private static final boolean buildDeveloper$lambda$194(DevicePanel var0) {
      return var0.p("AlphaLongitudinalEnabled");
   }

   private static final boolean buildDeveloper$lambda$195(DevicePanel var0) {
      return var0.engaged() ^ true;
   }

   private static final boolean buildDeveloper$lambda$196(DevicePanel var0) {
      return var0.i("alpha_long_available");
   }

   private static final Unit buildDeveloper$lambda$198(DevicePanel var0, boolean var1, Switch var2) {
      Intrinsics.checkNotNullParameter(var2, "sw");
      if (var1) {
         var0.syncing = true;
         var2.setChecked(false);
         var0.syncing = false;
         confirm$default(
            var0,
            "Enable openpilot longitudinal control (alpha)? This disables AEB on this car.",
            "Enable",
            false,
            new DevicePanel$$ExternalSyntheticLambda47(var0),
            4,
            null
         );
      } else {
         setParam$default(var0, "AlphaLongitudinalEnabled", false, false, null, 12, null);
      }

      return Unit.INSTANCE;
   }

   private static final Unit buildDeveloper$lambda$198$lambda$197(DevicePanel var0) {
      setParam$default(var0, "AlphaLongitudinalEnabled", true, false, null, 12, null);
      return Unit.INSTANCE;
   }

   private static final boolean buildDeveloper$lambda$199(DevicePanel var0) {
      return var0.p("ShowDebugInfo");
   }

   private static final Unit buildDeveloper$lambda$200(DevicePanel var0, boolean var1, Switch var2) {
      Intrinsics.checkNotNullParameter(var2, "<unused var>");
      setParam$default(var0, "ShowDebugInfo", var1, false, null, 12, null);
      return Unit.INSTANCE;
   }

   private final void buildDevice() {
      this.text("Dongle ID", new DevicePanel$$ExternalSyntheticLambda22(this));
      this.text("Serial", new DevicePanel$$ExternalSyntheticLambda90(this));
      button$default(
         this,
         "Pair Device",
         new DevicePanel$$ExternalSyntheticLambda92(),
         "Pair your device with comma connect (connect.comma.ai) and claim your comma prime offer.",
         null,
         null,
         new DevicePanel$$ExternalSyntheticLambda93(this),
         null,
         new DevicePanel$$ExternalSyntheticLambda94(this),
         88,
         null
      );
      DevicePanel$$ExternalSyntheticLambda95 var1 = new DevicePanel$$ExternalSyntheticLambda95();
      button$default(
         this,
         "Pair this tablet",
         var1,
         "Scan the StarView QR code on the comma (Settings › starview › show qr) so this tablet also works over Wi-Fi and after the comma's \"usb needs key\" is turned on.",
         null,
         null,
         null,
         null,
         new DevicePanel$$ExternalSyntheticLambda96(this),
         120,
         null
      );
      button$default(
         this,
         "Driver Camera",
         new DevicePanel$$ExternalSyntheticLambda97(),
         "Preview the driver facing camera to ensure that driver monitoring has good visibility.",
         null,
         null,
         null,
         null,
         new DevicePanel$$ExternalSyntheticLambda99(this),
         120,
         null
      );
      button$default(
         this,
         "Reset Driver Monitoring",
         new DevicePanel$$ExternalSyntheticLambda100(),
         "Clears the saved driver monitoring wheel-side calibration and any manual right-hand-driving override.",
         null,
         new DevicePanel$$ExternalSyntheticLambda33(this),
         null,
         null,
         new DevicePanel$$ExternalSyntheticLambda44(this),
         104,
         null
      );
      button$default(
         this,
         "Reset Calibration",
         new DevicePanel$$ExternalSyntheticLambda55(),
         null,
         null,
         new DevicePanel$$ExternalSyntheticLambda66(this),
         null,
         new DevicePanel$$ExternalSyntheticLambda77(this),
         new DevicePanel$$ExternalSyntheticLambda84(this),
         44,
         null
      );
      button$default(
         this,
         "Regulatory",
         new DevicePanel$$ExternalSyntheticLambda85(),
         null,
         null,
         null,
         null,
         null,
         new DevicePanel$$ExternalSyntheticLambda86(this),
         124,
         null
      );
      this.dual(
         "Reboot",
         "Power Off",
         new DevicePanel$$ExternalSyntheticLambda88(this),
         new DevicePanel$$ExternalSyntheticLambda89(this),
         new DevicePanel$$ExternalSyntheticLambda91(this)
      );
      this.note("Review Training Guide and Change Language only exist on the comma's own screen.");
   }

   private static final String buildDevice$lambda$100() {
      return "VIEW";
   }

   private static final Unit buildDevice$lambda$102(DevicePanel var0) {
      String var1 = var0.getHost();
      if (var1 == null) {
         return Unit.INSTANCE;
      } else {
         WebView var2 = new WebView((Context)var0.act);
         var2.getSettings().setJavaScriptEnabled(false);
         var2.loadUrl("http://" + var1 + ":8090/regulatory");
         new Builder((Context)var0.act).setView((View)var2).setPositiveButton("Close", null).show();
         return Unit.INSTANCE;
      }
   }

   private static final Unit buildDevice$lambda$104(DevicePanel var0) {
      confirm$default(var0, "Are you sure you want to reboot?", "Reboot", false, new DevicePanel$$ExternalSyntheticLambda50(var0), 4, null);
      return Unit.INSTANCE;
   }

   private static final Unit buildDevice$lambda$104$lambda$103(DevicePanel var0) {
      call$default(var0, "reboot", null, null, 6, null);
      return Unit.INSTANCE;
   }

   private static final Unit buildDevice$lambda$106(DevicePanel var0) {
      confirm$default(var0, "Are you sure you want to power off?", "Power Off", false, new DevicePanel$$ExternalSyntheticLambda172(var0), 4, null);
      return Unit.INSTANCE;
   }

   private static final Unit buildDevice$lambda$106$lambda$105(DevicePanel var0) {
      call$default(var0, "power_off", null, null, 6, null);
      return Unit.INSTANCE;
   }

   private static final boolean buildDevice$lambda$107(DevicePanel var0) {
      return var0.onroad() ^ true;
   }

   private static final String buildDevice$lambda$80(DevicePanel var0) {
      var var2 = var0.pStr("DongleId");
      boolean var1;
      if (var2.length() == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      if (var1) {
         var2 = "N/A";
      }

      return var2;
   }

   private static final String buildDevice$lambda$82(DevicePanel var0) {
      var var2 = var0.pStr("HardwareSerial");
      boolean var1;
      if (var2.length() == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      if (var1) {
         var2 = "N/A";
      }

      return var2;
   }

   private static final String buildDevice$lambda$83() {
      return "PAIR";
   }

   private static final boolean buildDevice$lambda$84(DevicePanel var0) {
      return var0.i("comma_paired") ^ true;
   }

   private static final Unit buildDevice$lambda$86(DevicePanel var0) {
      call$default(var0, "comma_pair_url", null, new DevicePanel$$ExternalSyntheticLambda138(var0), 2, null);
      return Unit.INSTANCE;
   }

   private static final Unit buildDevice$lambda$86$lambda$85(DevicePanel var0, JSONObject var1) {
      Intrinsics.checkNotNullParameter(var1, "r");
      if (var1.optBoolean("ok")) {
         String var2 = var1.optString("url");
         Intrinsics.checkNotNullExpressionValue(var2, "optString(...)");
         var0.showQr("Scan with the comma connect app", var2);
      } else {
         var0.ackToast(var1);
      }

      return Unit.INSTANCE;
   }

   private static final String buildDevice$lambda$87() {
      boolean var0;
      if (Link.INSTANCE.getTermKey().length() > 0) {
         var0 = true;
      } else {
         var0 = false;
      }

      String var1;
      if (var0) {
         var1 = "RE-PAIR";
      } else {
         var1 = "PAIR";
      }

      return var1;
   }

   private static final Unit buildDevice$lambda$88(DevicePanel var0) {
      PairScanner.scan$default(PairScanner.INSTANCE, var0.act, null, 2, null);
      return Unit.INSTANCE;
   }

   private static final String buildDevice$lambda$89() {
      return "PREVIEW";
   }

   private static final Unit buildDevice$lambda$90(DevicePanel var0) {
      var0.showDriverCamera();
      return Unit.INSTANCE;
   }

   private static final String buildDevice$lambda$91() {
      return "RESET";
   }

   private static final boolean buildDevice$lambda$92(DevicePanel var0) {
      return var0.engaged() ^ true;
   }

   private static final Unit buildDevice$lambda$94(DevicePanel var0) {
      confirm$default(
         var0, "Are you sure you want to reset driver monitoring calibration?", "Reset", false, new DevicePanel$$ExternalSyntheticLambda153(var0), 4, null
      );
      return Unit.INSTANCE;
   }

   private static final Unit buildDevice$lambda$94$lambda$93(DevicePanel var0) {
      call$default(var0, "reset_dm", null, null, 6, null);
      return Unit.INSTANCE;
   }

   private static final String buildDevice$lambda$95() {
      return "RESET";
   }

   private static final boolean buildDevice$lambda$96(DevicePanel var0) {
      return var0.engaged() ^ true;
   }

   private static final String buildDevice$lambda$97(DevicePanel var0) {
      Telemetry var2 = var0.getT();
      if (var2 != null) {
         String var1 = Telemetry.iStr$default(var2, "calib", null, 2, null);
         if (var1 != null) {
            return "openpilot requires the device to be mounted within 4° left or right and within 5° up or 9° down. "
               + var1
               + "\n\nopenpilot is continuously calibrating, resetting is rarely required. Resetting calibration will restart openpilot if the car is powered on.";
         }
      }

      String var3 = "";
      return "openpilot requires the device to be mounted within 4° left or right and within 5° up or 9° down. "
         + var3
         + "\n\nopenpilot is continuously calibrating, resetting is rarely required. Resetting calibration will restart openpilot if the car is powered on.";
   }

   private static final Unit buildDevice$lambda$99(DevicePanel var0) {
      confirm$default(var0, "Are you sure you want to reset calibration?", "Reset", false, new DevicePanel$$ExternalSyntheticLambda157(var0), 4, null);
      return Unit.INSTANCE;
   }

   private static final Unit buildDevice$lambda$99$lambda$98(DevicePanel var0) {
      call$default(var0, "reset_calibration", null, null, 6, null);
      return Unit.INSTANCE;
   }

   private final void buildNetwork() {
      this.col.addView((View)this.netStatus);
      LinearLayout var1 = new LinearLayout((Context)this.act);
      var1.setOrientation(0);
      var1.setGravity(16);
      TextView var2 = new TextView((Context)this.act);
      var2.setText("WI-FI NETWORKS");
      var2.setTextColor(this.dim);
      var2.setTextSize(13.0F);
      var2.setLetterSpacing(0.1F);
      var2.setPadding(this.dp(4), 0, 0, 0);
      var1.addView((View)var2, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(0, -2, 1.0F)));
      Button var6 = new Button((Context)this.act);
      var6.setText("RESCAN");
      var6.setTextSize(12.0F);
      var6.setOnClickListener(new DevicePanel$$ExternalSyntheticLambda104(this));
      var1.addView((View)var6);
      this.col.addView((View)var1);
      this.col.addView((View)this.netList);
      this.header("Privacy");
      toggle$default(
         this,
         "Comma offline",
         null,
         new DevicePanel$$ExternalSyntheticLambda116(this),
         new DevicePanel$$ExternalSyntheticLambda128(this),
         null,
         new DevicePanel$$ExternalSyntheticLambda130(this),
         new DevicePanel$$ExternalSyntheticLambda132(this),
         16,
         null
      );
      call$default(this, "offline", null, new DevicePanel$$ExternalSyntheticLambda133(this), 2, null);
      DevicePanel$$ExternalSyntheticLambda134 var3 = new DevicePanel$$ExternalSyntheticLambda134(this);
      toggle$default(
         this,
         "Close after downloads",
         null,
         new DevicePanel$$ExternalSyntheticLambda135(this),
         null,
         var3,
         new DevicePanel$$ExternalSyntheticLambda136(this),
         new DevicePanel$$ExternalSyntheticLambda137(this),
         8,
         null
      );
      button$default(
         this,
         "Download door",
         new DevicePanel$$ExternalSyntheticLambda105(),
         null,
         null,
         new DevicePanel$$ExternalSyntheticLambda106(this),
         var3,
         new DevicePanel$$ExternalSyntheticLambda107(this),
         new DevicePanel$$ExternalSyntheticLambda108(this),
         12,
         null
      );
      this.header("Advanced");
      var1 = toggle$default(
         this,
         "Enable Tethering",
         null,
         new DevicePanel$$ExternalSyntheticLambda110(this),
         null,
         null,
         null,
         new DevicePanel$$ExternalSyntheticLambda111(this),
         56,
         null
      );
      View var5 = var1.getChildAt(var1.getChildCount() - 1);
      Intrinsics.checkNotNull(var5, "null cannot be cast to non-null type android.widget.Switch");
      this.teth = (Switch)var5;
      button$default(
         this,
         "Tethering Password",
         new DevicePanel$$ExternalSyntheticLambda112(),
         null,
         null,
         null,
         null,
         null,
         new DevicePanel$$ExternalSyntheticLambda113(this),
         124,
         null
      );
      this.text("IP Address", new DevicePanel$$ExternalSyntheticLambda114(this));
      toggle$default(
         this,
         "Enable Roaming",
         null,
         new DevicePanel$$ExternalSyntheticLambda115(this),
         null,
         null,
         null,
         new DevicePanel$$ExternalSyntheticLambda117(this),
         56,
         null
      );
      button$default(
         this,
         "APN Setting",
         new DevicePanel$$ExternalSyntheticLambda118(),
         null,
         new DevicePanel$$ExternalSyntheticLambda119(this),
         null,
         null,
         null,
         new DevicePanel$$ExternalSyntheticLambda121(this),
         116,
         null
      );
      toggle$default(
         this,
         "Cellular Metered",
         "Prevent large data uploads when on a metered cellular connection",
         new DevicePanel$$ExternalSyntheticLambda122(this),
         null,
         null,
         null,
         new DevicePanel$$ExternalSyntheticLambda123(this),
         56,
         null
      );
      this.multi(
         "Wi-Fi Network Metered",
         "Prevent large data uploads when on a metered Wi-Fi connection",
         CollectionsKt.listOf(new String[]{"default", "metered", "unmetered"}),
         new DevicePanel$$ExternalSyntheticLambda124(this),
         new DevicePanel$$ExternalSyntheticLambda125(this),
         new DevicePanel$$ExternalSyntheticLambda126(this)
      );
      button$default(
         this,
         "Hidden Network",
         new DevicePanel$$ExternalSyntheticLambda127(),
         null,
         null,
         null,
         null,
         null,
         new DevicePanel$$ExternalSyntheticLambda129(this),
         124,
         null
      );
      this.note("The tablet's own USB link is not affected by these settings.");
      if (this.connected()) {
         this.netStatus.setText("Scanning…");
         this.scan(false);
      } else {
         this.netStatus.setText("Not connected to the comma.");
      }
   }

   private static final void buildNetwork$lambda$134$lambda$133(DevicePanel var0, View var1) {
      var0.netStatus.setText("Scanning…");
      var0.scan(true);
   }

   private static final boolean buildNetwork$lambda$135(DevicePanel var0) {
      return var0.offline.optBoolean("on");
   }

   private static final boolean buildNetwork$lambda$136(DevicePanel var0) {
      return var0.offline.optBoolean("ok");
   }

   private static final String buildNetwork$lambda$137(DevicePanel var0) {
      String var1;
      if (!var0.offline.has("ok")) {
         var1 = "Checking…";
      } else if (!var0.offline.optBoolean("ok")) {
         String var2 = var0.offline.optString("error");
         var1 = "Needs offline.py and the comma update for this switch (" + var2 + ")";
      } else if (var0.offline.optBoolean("on")) {
         String var3;
         if (var0.offline.optBoolean("boot")) {
            var3 = " Stays on after a restart.";
         } else {
            var3 = " Boot service missing: it won't come back after a restart.";
         }

         var1 = "On: the comma reaches only the tablet, the phone, GitHub (updates) and Mapbox (maps, traffic). No uploads, no comma connect, no telemetry."
            + var3;
      } else {
         var1 = "Off: the comma uses the internet normally. Turn on to block everything except the tablet, the phone and GitHub updates.";
      }

      return var1;
   }

   private static final Unit buildNetwork$lambda$139(DevicePanel var0, boolean var1, Switch var2) {
      Intrinsics.checkNotNullParameter(var2, "sw");
      var2.setEnabled(false);
      var0.call("offline", MapsKt.mapOf(TuplesKt.to("on", var1)), new DevicePanel$$ExternalSyntheticLambda109(var0));
      return Unit.INSTANCE;
   }

   private static final Unit buildNetwork$lambda$139$lambda$138(DevicePanel var0, JSONObject var1) {
      Intrinsics.checkNotNullParameter(var1, "r");
      if (var1.optBoolean("ok")) {
         var0.offline = var1;
         String var2;
         if (var1.optBoolean("on")) {
            var2 = "Comma offline";
         } else {
            var2 = "Comma online";
         }

         var0.toast(var2);
      } else {
         var0.ackToast(var1);
      }

      return Unit.INSTANCE;
   }

   private static final Unit buildNetwork$lambda$140(DevicePanel var0, JSONObject var1) {
      Intrinsics.checkNotNullParameter(var1, "r");
      var0.offline = var1;
      return Unit.INSTANCE;
   }

   private static final boolean buildNetwork$lambda$141(DevicePanel var0) {
      boolean var1;
      if (var0.offline.optBoolean("ok") && var0.offline.optBoolean("on") && var0.door() != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private static final boolean buildNetwork$lambda$142(DevicePanel var0) {
      JSONObject var2 = var0.door();
      boolean var1 = true;
      if (var2 != null) {
         var1 = var2.optBoolean("autoClose", true);
      }

      return var1;
   }

   private static final String buildNetwork$lambda$143(DevicePanel var0) {
      JSONObject var3 = var0.door();
      boolean var2 = false;
      boolean var1 = var2;
      if (var3 != null) {
         var1 = var2;
         if (!var3.optBoolean("autoClose", true)) {
            var1 = true;
         }
      }

      String var4;
      if (!var1) {
         var4 = "On: Hugging Face is let through only while a model or sound download runs, then shut again.";
      } else {
         var4 = "Off: after a download Hugging Face stays reachable until you tap Close now (or an hour passes).";
      }

      return var4;
   }

   private static final Unit buildNetwork$lambda$145(DevicePanel var0, boolean var1, Switch var2) {
      Intrinsics.checkNotNullParameter(var2, "sw");
      var2.setEnabled(false);
      var0.call("offline_door", MapsKt.mapOf(TuplesKt.to("autoClose", var1)), new DevicePanel$$ExternalSyntheticLambda45(var0));
      return Unit.INSTANCE;
   }

   private static final Unit buildNetwork$lambda$145$lambda$144(DevicePanel var0, JSONObject var1) {
      Intrinsics.checkNotNullParameter(var1, "r");
      if (var1.optBoolean("ok")) {
         var0.offline.put("door", var1.optJSONObject("door"));
      } else {
         var0.ackToast(var1);
      }

      return Unit.INSTANCE;
   }

   private static final String buildNetwork$lambda$146() {
      return "CLOSE NOW";
   }

   private static final boolean buildNetwork$lambda$147(DevicePanel var0) {
      JSONObject var3 = var0.door();
      boolean var2 = false;
      boolean var1 = var2;
      if (var3 != null) {
         var1 = var2;
         if (var3.optBoolean("open")) {
            var1 = true;
         }
      }

      return var1;
   }

   private static final String buildNetwork$lambda$149(DevicePanel var0) {
      JSONObject var3 = var0.door();
      JSONArray var4;
      if (var3 != null) {
         var4 = var3.optJSONArray("downloads");
      } else {
         var4 = null;
      }

      int var1 = 0;
      String var2 = "";
      String var5;
      if (var4 != null && var4.length() > 0) {
         var5 = CollectionsKt.joinToString$default(
            RangesKt.until(0, var4.length()), " and ", null, null, 0, null, new DevicePanel$$ExternalSyntheticLambda167(var4), 30, null
         );
      } else {
         var5 = "";
      }

      String var6;
      if (var3 == null) {
         var6 = var2;
      } else if (!var3.optBoolean("open")) {
         var6 = "Closed. It opens by itself when you refresh or download models or sounds.";
      } else {
         if (var5.length() > 0) {
            var1 = 1;
         }

         if (var1) {
            var6 = "Open: downloading " + var5 + " (Hugging Face only).";
         } else if (!var3.isNull("closesIn")) {
            var1 = (var3.optInt("closesIn") + 59) / 60;
            var6 = "Open (Hugging Face only), closes by itself in " + var1 + " min.";
         } else {
            var6 = "Open (Hugging Face only).";
         }
      }

      return var6;
   }

   private static final CharSequence buildNetwork$lambda$149$lambda$148(JSONArray var0, int var1) {
      String var2 = var0.optString(var1);
      Intrinsics.checkNotNullExpressionValue(var2, "optString(...)");
      return var2;
   }

   private static final Unit buildNetwork$lambda$151(DevicePanel var0) {
      var0.call("offline_door", MapsKt.mapOf(TuplesKt.to("close", true)), new DevicePanel$$ExternalSyntheticLambda101(var0));
      return Unit.INSTANCE;
   }

   private static final Unit buildNetwork$lambda$151$lambda$150(DevicePanel var0, JSONObject var1) {
      Intrinsics.checkNotNullParameter(var1, "r");
      if (var1.optBoolean("ok")) {
         var0.offline.put("door", var1.optJSONObject("door"));
         var0.toast("Download door closed");
      } else {
         var0.ackToast(var1);
      }

      return Unit.INSTANCE;
   }

   private static final boolean buildNetwork$lambda$152(DevicePanel var0) {
      return var0.wifi.optBoolean("tethering");
   }

   private static final Unit buildNetwork$lambda$155(DevicePanel var0, boolean var1, Switch var2) {
      Intrinsics.checkNotNullParameter(var2, "sw");
      var2.setEnabled(false);
      var0.call("tether", MapsKt.mapOf(TuplesKt.to("on", var1)), new DevicePanel$$ExternalSyntheticLambda98(var0));
      return Unit.INSTANCE;
   }

   private static final Unit buildNetwork$lambda$155$lambda$154(DevicePanel var0, JSONObject var1) {
      Intrinsics.checkNotNullParameter(var1, "r");
      var0.ackToast(var1);
      call$default(var0, "wifi_status", null, new DevicePanel$$ExternalSyntheticLambda46(var0), 2, null);
      return Unit.INSTANCE;
   }

   private static final Unit buildNetwork$lambda$155$lambda$154$lambda$153(DevicePanel var0, JSONObject var1) {
      Intrinsics.checkNotNullParameter(var1, "it");
      var0.applyWifiStatus(var1);
      return Unit.INSTANCE;
   }

   private static final String buildNetwork$lambda$156() {
      return "EDIT";
   }

   private static final Unit buildNetwork$lambda$159(DevicePanel var0) {
      call$default(var0, "tether_password", null, new DevicePanel$$ExternalSyntheticLambda103(var0), 2, null);
      return Unit.INSTANCE;
   }

   private static final Unit buildNetwork$lambda$159$lambda$158(DevicePanel var0, JSONObject var1) {
      Intrinsics.checkNotNullParameter(var1, "r");
      String var2 = var1.optString("password");
      Intrinsics.checkNotNullExpressionValue(var2, "optString(...)");
      input$default(var0, "Enter new tethering password", "min 8 characters", var2, false, 8, new DevicePanel$$ExternalSyntheticLambda159(var0), 8, null);
      return Unit.INSTANCE;
   }

   private static final Unit buildNetwork$lambda$159$lambda$158$lambda$157(DevicePanel var0, String var1) {
      Intrinsics.checkNotNullParameter(var1, "pw");
      call$default(var0, "tether_password", MapsKt.mapOf(TuplesKt.to("password", var1)), null, 4, null);
      return Unit.INSTANCE;
   }

   private static final String buildNetwork$lambda$161(DevicePanel var0) {
      var var2 = var0.wifi.optString("ip");
      boolean var1;
      if (var2.length() == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      if (var1) {
         var2 = "—";
      }

      return var2;
   }

   private static final boolean buildNetwork$lambda$162(DevicePanel var0) {
      return var0.p("GsmRoaming");
   }

   private static final Unit buildNetwork$lambda$163(DevicePanel var0, boolean var1, Switch var2) {
      Intrinsics.checkNotNullParameter(var2, "<unused var>");
      setParam$default(var0, "GsmRoaming", var1, false, null, 12, null);
      return Unit.INSTANCE;
   }

   private static final String buildNetwork$lambda$164() {
      return "EDIT";
   }

   private static final String buildNetwork$lambda$166(DevicePanel var0) {
      var var2 = var0.pStr("GsmApn");
      boolean var1;
      if (var2.length() == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      if (var1) {
         var2 = "automatic";
      }

      return var2;
   }

   private static final Unit buildNetwork$lambda$168(DevicePanel var0) {
      input$default(
         var0,
         "Enter APN",
         "leave blank for automatic configuration",
         var0.pStr("GsmApn"),
         false,
         0,
         new DevicePanel$$ExternalSyntheticLambda42(var0),
         24,
         null
      );
      return Unit.INSTANCE;
   }

   private static final Unit buildNetwork$lambda$168$lambda$167(DevicePanel var0, String var1) {
      Intrinsics.checkNotNullParameter(var1, "apn");
      setParam$default(var0, "GsmApn", var1, false, null, 12, null);
      return Unit.INSTANCE;
   }

   private static final boolean buildNetwork$lambda$169(DevicePanel var0) {
      return var0.p("GsmMetered");
   }

   private static final Unit buildNetwork$lambda$170(DevicePanel var0, boolean var1, Switch var2) {
      Intrinsics.checkNotNullParameter(var2, "<unused var>");
      setParam$default(var0, "GsmMetered", var1, false, null, 12, null);
      return Unit.INSTANCE;
   }

   private static final int buildNetwork$lambda$171(DevicePanel var0) {
      return var0.meteredSel;
   }

   private static final boolean buildNetwork$lambda$172(DevicePanel var0) {
      String var3 = var0.wifi.optString("ssid");
      Intrinsics.checkNotNullExpressionValue(var3, "optString(...)");
      int var1 = var3.length();
      boolean var2 = true;
      boolean var4;
      if (var1 > 0) {
         var4 = true;
      } else {
         var4 = false;
      }

      if (!var4 || var0.wifi.optBoolean("tethering")) {
         var2 = false;
      }

      return var2;
   }

   private static final Unit buildNetwork$lambda$175(DevicePanel var0, int var1) {
      var0.call(
         "wifi_metered",
         MapsKt.mapOf(TuplesKt.to("mode", CollectionsKt.listOf(new String[]{"unknown", "yes", "no"}).get(var1))),
         new DevicePanel$$ExternalSyntheticLambda168(var0)
      );
      return Unit.INSTANCE;
   }

   private static final Unit buildNetwork$lambda$175$lambda$174(DevicePanel var0, JSONObject var1) {
      Intrinsics.checkNotNullParameter(var1, "r");
      var0.ackToast(var1);
      call$default(var0, "wifi_status", null, new DevicePanel$$ExternalSyntheticLambda1(var0), 2, null);
      return Unit.INSTANCE;
   }

   private static final Unit buildNetwork$lambda$175$lambda$174$lambda$173(DevicePanel var0, JSONObject var1) {
      Intrinsics.checkNotNullParameter(var1, "it");
      var0.applyWifiStatus(var1);
      return Unit.INSTANCE;
   }

   private static final String buildNetwork$lambda$176() {
      return "CONNECT";
   }

   private static final Unit buildNetwork$lambda$180(DevicePanel var0) {
      input$default(var0, "Enter SSID", "network name", null, false, 0, new DevicePanel$$ExternalSyntheticLambda102(var0), 28, null);
      return Unit.INSTANCE;
   }

   private static final Unit buildNetwork$lambda$180$lambda$179(DevicePanel var0, String var1) {
      Intrinsics.checkNotNullParameter(var1, "ssid");
      input$default(var0, "Enter password", "leave blank for an open network", null, true, 0, new DevicePanel$$ExternalSyntheticLambda161(var0, var1), 20, null);
      return Unit.INSTANCE;
   }

   private static final Unit buildNetwork$lambda$180$lambda$179$lambda$178(DevicePanel var0, String var1, String var2) {
      Intrinsics.checkNotNullParameter(var2, "pw");
      var0.netStatus.setText("Connecting to " + var1 + "…");
      var0.call(
         "wifi_connect",
         MapsKt.mapOf(new Pair[]{TuplesKt.to("ssid", var1), TuplesKt.to("password", var2), TuplesKt.to("hidden", true)}),
         new DevicePanel$$ExternalSyntheticLambda160(var0)
      );
      return Unit.INSTANCE;
   }

   private static final Unit buildNetwork$lambda$180$lambda$179$lambda$178$lambda$177(DevicePanel var0, JSONObject var1) {
      Intrinsics.checkNotNullParameter(var1, "r");
      var0.ackToast(var1);
      var0.scan(false);
      return Unit.INSTANCE;
   }

   private final void buildSoftware() {
      this.text("Current Version", new DevicePanel$$ExternalSyntheticLambda139(this));
      this.text("Branch", new DevicePanel$$ExternalSyntheticLambda145(this));
      button$default(
         this,
         "Update",
         new DevicePanel$$ExternalSyntheticLambda146(this),
         null,
         new DevicePanel$$ExternalSyntheticLambda147(this),
         new DevicePanel$$ExternalSyntheticLambda148(this),
         null,
         new DevicePanel$$ExternalSyntheticLambda149(this),
         new DevicePanel$$ExternalSyntheticLambda150(this),
         36,
         null
      );
      toggle$default(
         this,
         "Automatically Install Updates",
         "Download and install updates by themselves while parked with an internet connection.",
         new DevicePanel$$ExternalSyntheticLambda151(this),
         new DevicePanel$$ExternalSyntheticLambda152(this),
         null,
         null,
         new DevicePanel$$ExternalSyntheticLambda154(this),
         48,
         null
      );
      button$default(
         this,
         "Error Log",
         new DevicePanel$$ExternalSyntheticLambda140(),
         null,
         null,
         null,
         null,
         null,
         new DevicePanel$$ExternalSyntheticLambda141(this),
         124,
         null
      );
      button$default(
         this,
         "Branches, versions & backup",
         new DevicePanel$$ExternalSyntheticLambda143(),
         "Galaxy's System Tools: switch branch, go back to an earlier version, back up or restore all StarPilot settings.",
         null,
         null,
         null,
         null,
         new DevicePanel$$ExternalSyntheticLambda144(this),
         120,
         null
      );
      this.note("Uninstall / factory reset stay on the comma's own screen on purpose.");
      this.header("Fork fixes waiting on upstream");
      LinearLayout var1 = new LinearLayout((Context)this.act);
      var1.setOrientation(1);
      this.col.addView((View)var1);
      this.loadForkFixes(var1);
   }

   private static final String buildSoftware$lambda$207(DevicePanel var0) {
      var var2 = var0.pStr("UpdaterCurrentDescription");
      boolean var1;
      if (var2.length() == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      if (var1) {
         var2 = "—";
      }

      return var2;
   }

   private static final String buildSoftware$lambda$209(DevicePanel var0) {
      var var2 = var0.pStr("GitBranch");
      boolean var1;
      if (var2.length() == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      if (var1) {
         var2 = "—";
      }

      return var2;
   }

   private static final String buildSoftware$lambda$210(DevicePanel var0) {
      String var1;
      if (FastUpdate.INSTANCE.running(var0.fastStatus)) {
         var1 = "UPDATING";
      } else {
         var1 = "UPDATE";
      }

      return var1;
   }

   private static final String buildSoftware$lambda$211(DevicePanel var0) {
      return FastUpdate.INSTANCE.summary(var0.fastStatus);
   }

   private static final boolean buildSoftware$lambda$212(DevicePanel var0) {
      boolean var1;
      if (!var0.onroad() && !FastUpdate.INSTANCE.running(var0.fastStatus)) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private static final String buildSoftware$lambda$214(DevicePanel var0) {
      Object var2 = var0.pStr("GitBranch");
      boolean var1;
      if (var2.length() == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      if (var1) {
         var2 = "branch";
      }

      return "Fast update, the same as Galaxy: gets the newest " + var2 + " and reboots. Anything applied only on the comma is replaced.";
   }

   private static final Unit buildSoftware$lambda$217(DevicePanel var0) {
      Object var2 = var0.pStr("GitBranch");
      boolean var1;
      if (var2.length() == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      if (var1) {
         var2 = "this branch";
      }

      confirm$default(
         var0,
         "Update now? The comma downloads the newest version of " + var2 + " and reboots.",
         "Update",
         false,
         new DevicePanel$$ExternalSyntheticLambda38(var0),
         4,
         null
      );
      return Unit.INSTANCE;
   }

   private static final Unit buildSoftware$lambda$217$lambda$216(DevicePanel var0) {
      var0.startFastUpdate();
      return Unit.INSTANCE;
   }

   private static final boolean buildSoftware$lambda$218(DevicePanel var0) {
      return Intrinsics.areEqual(var0.autoDl, true);
   }

   private static final boolean buildSoftware$lambda$219(DevicePanel var0) {
      boolean var1;
      if (var0.autoCan && var0.autoDl != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private static final Unit buildSoftware$lambda$222(DevicePanel var0, boolean var1, Switch var2) {
      Intrinsics.checkNotNullParameter(var2, "<unused var>");
      Galaxy7 var4 = Galaxy7.Companion.current();
      if (var4 == null) {
         var0.toast("Not connected");
         return Unit.INSTANCE;
      } else {
         JSONObject var5 = new JSONObject().put("action", "preferences").put("automaticDownloads", var1);
         Boolean var3 = var0.autoDl;
         Object var6 = var3;
         if (var3 == null) {
            var6 = JSONObject.NULL;
         }

         var6 = var5.put("expectedAutomaticDownloads", var6);
         Intrinsics.checkNotNullExpressionValue(var6, "put(...)");
         var4.post("/api/software/action", (JSONObject)var6, new DevicePanel$$ExternalSyntheticLambda162(var0));
         return Unit.INSTANCE;
      }
   }

   private static final Unit buildSoftware$lambda$222$lambda$221(DevicePanel var0, JSONObject var1, String var2) {
      if (var2 != null) {
         var0.toast(var2);
      }

      var0.loadAuto();
      return Unit.INSTANCE;
   }

   private static final String buildSoftware$lambda$223() {
      return "VIEW";
   }

   private static final Unit buildSoftware$lambda$226(DevicePanel var0) {
      call$default(var0, "error_log", null, new DevicePanel$$ExternalSyntheticLambda81(var0), 2, null);
      return Unit.INSTANCE;
   }

   private static final Unit buildSoftware$lambda$226$lambda$225(DevicePanel var0, JSONObject var1) {
      Intrinsics.checkNotNullParameter(var1, "r");
      Object var3 = var1.optString("text");
      boolean var2;
      if (var3.length() == 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      if (var2) {
         var3 = "No error log found.";
      }

      Intrinsics.checkNotNullExpressionValue(var3, "ifEmpty(...)");
      var0.alert("Error log", (CharSequence)var3);
      return Unit.INSTANCE;
   }

   private static final String buildSoftware$lambda$227() {
      return "OPEN";
   }

   private static final Unit buildSoftware$lambda$228(DevicePanel var0) {
      Activity var1 = var0.act;
      SettingsActivity var2;
      if (var1 instanceof SettingsActivity) {
         var2 = (SettingsActivity)var1;
      } else {
         var2 = null;
      }

      if (var2 != null) {
         var2.openGalaxyWeb("/system", "Branches, versions & backup");
      }

      return Unit.INSTANCE;
   }

   private final void buildToggles() {
      this.note("Same toggles as the comma's own Toggles panel. Toggles that restart openpilot are disabled while engaged.");
      toggle$default(
         this,
         "Enable openpilot",
         buildToggles$restartDesc(
            "Use the openpilot system for adaptive cruise control and lane keep driver assistance. Your attention is required at all times to use this feature."
         ),
         new DevicePanel$$ExternalSyntheticLambda8(this),
         new DevicePanel$$ExternalSyntheticLambda20(this),
         null,
         null,
         new DevicePanel$$ExternalSyntheticLambda29(this),
         48,
         null
      );
      toggle$default(
         this,
         "Safe Mode",
         buildToggles$restartDesc(
            "Temporarily force driving-affecting StarPilot settings back to safe defaults, stock tuning, and the branch default model until disabled."
         ),
         new DevicePanel$$ExternalSyntheticLambda30(this),
         new DevicePanel$$ExternalSyntheticLambda31(this),
         null,
         null,
         new DevicePanel$$ExternalSyntheticLambda32(this),
         48,
         null
      );
      toggle$default(
         this,
         "Disengage on Accelerator Pedal",
         "When enabled, pressing the accelerator pedal will disengage openpilot.",
         new DevicePanel$$ExternalSyntheticLambda34(this),
         new DevicePanel$$ExternalSyntheticLambda35(this),
         null,
         null,
         new DevicePanel$$ExternalSyntheticLambda36(this),
         48,
         null
      );
      this.multi(
         "Driving Personality",
         this.personalityDesc,
         CollectionsKt.listOf(new String[]{"Aggressive", "Standard", "Relaxed"}),
         new DevicePanel$$ExternalSyntheticLambda37(this),
         new DevicePanel$$ExternalSyntheticLambda9(this),
         new DevicePanel$$ExternalSyntheticLambda10(this)
      );
      toggle$default(
         this,
         "Enable Lane Departure Warnings",
         "Receive alerts to steer back into the lane when your vehicle drifts over a detected lane line without a turn signal activated while driving over 31 mph (50 km/h).",
         new DevicePanel$$ExternalSyntheticLambda12(this),
         new DevicePanel$$ExternalSyntheticLambda13(this),
         null,
         null,
         new DevicePanel$$ExternalSyntheticLambda14(this),
         48,
         null
      );
      toggle$default(
         this,
         "Always-On Driver Monitoring",
         "Enable driver monitoring even when openpilot is not engaged.",
         new DevicePanel$$ExternalSyntheticLambda15(this),
         new DevicePanel$$ExternalSyntheticLambda16(this),
         null,
         null,
         new DevicePanel$$ExternalSyntheticLambda17(this),
         48,
         null
      );
      toggle$default(
         this,
         "Record and Upload Driver Camera",
         buildToggles$restartDesc("Upload data from the driver facing camera and help improve the driver monitoring algorithm."),
         new DevicePanel$$ExternalSyntheticLambda18(this),
         new DevicePanel$$ExternalSyntheticLambda19(this),
         null,
         null,
         new DevicePanel$$ExternalSyntheticLambda21(this),
         48,
         null
      );
      toggle$default(
         this,
         "Record and Upload Microphone Audio",
         buildToggles$restartDesc("Record and store microphone audio while driving. The audio will be included in the dashcam video in comma connect."),
         new DevicePanel$$ExternalSyntheticLambda23(this),
         new DevicePanel$$ExternalSyntheticLambda24(this),
         null,
         null,
         new DevicePanel$$ExternalSyntheticLambda25(this),
         48,
         null
      );
      toggle$default(
         this,
         "Use Metric System",
         "Display speed in km/h instead of mph.",
         new DevicePanel$$ExternalSyntheticLambda26(this),
         new DevicePanel$$ExternalSyntheticLambda27(this),
         null,
         null,
         new DevicePanel$$ExternalSyntheticLambda28(this),
         48,
         null
      );
   }

   private static final boolean buildToggles$lambda$52(DevicePanel var0) {
      return var0.p("OpenpilotEnabledToggle");
   }

   private static final boolean buildToggles$lambda$53(DevicePanel var0) {
      boolean var1;
      if (!var0.engaged() && !var0.locked("OpenpilotEnabledToggle")) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private static final Unit buildToggles$lambda$54(DevicePanel var0, boolean var1, Switch var2) {
      Intrinsics.checkNotNullParameter(var2, "<unused var>");
      setParam$default(var0, "OpenpilotEnabledToggle", var1, false, null, 12, null);
      return Unit.INSTANCE;
   }

   private static final boolean buildToggles$lambda$55(DevicePanel var0) {
      return var0.p("SafeMode");
   }

   private static final boolean buildToggles$lambda$56(DevicePanel var0) {
      boolean var1;
      if (!var0.engaged() && !var0.locked("SafeMode")) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private static final Unit buildToggles$lambda$57(DevicePanel var0, boolean var1, Switch var2) {
      Intrinsics.checkNotNullParameter(var2, "<unused var>");
      setParam$default(var0, "SafeMode", var1, false, null, 12, null);
      return Unit.INSTANCE;
   }

   private static final boolean buildToggles$lambda$58(DevicePanel var0) {
      return var0.p("DisengageOnAccelerator");
   }

   private static final boolean buildToggles$lambda$59(DevicePanel var0) {
      return var0.locked("DisengageOnAccelerator") ^ true;
   }

   private static final Unit buildToggles$lambda$60(DevicePanel var0, boolean var1, Switch var2) {
      Intrinsics.checkNotNullParameter(var2, "<unused var>");
      setParam$default(var0, "DisengageOnAccelerator", var1, false, null, 12, null);
      return Unit.INSTANCE;
   }

   private static final int buildToggles$lambda$61(DevicePanel var0) {
      Telemetry var2 = var0.getT();
      int var1 = 1;
      if (var2 != null) {
         var1 = var2.pInt("LongitudinalPersonality", 1);
      }

      return var1;
   }

   private static final boolean buildToggles$lambda$62(DevicePanel var0) {
      boolean var1;
      if (!var0.p("SafeMode") && var0.i("has_longitudinal_control")) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private static final Unit buildToggles$lambda$63(DevicePanel var0, int var1) {
      setParam$default(var0, "LongitudinalPersonality", var1, false, null, 12, null);
      return Unit.INSTANCE;
   }

   private static final boolean buildToggles$lambda$64(DevicePanel var0) {
      return var0.p("IsLdwEnabled");
   }

   private static final boolean buildToggles$lambda$65(DevicePanel var0) {
      return var0.locked("IsLdwEnabled") ^ true;
   }

   private static final Unit buildToggles$lambda$66(DevicePanel var0, boolean var1, Switch var2) {
      Intrinsics.checkNotNullParameter(var2, "<unused var>");
      setParam$default(var0, "IsLdwEnabled", var1, false, null, 12, null);
      return Unit.INSTANCE;
   }

   private static final boolean buildToggles$lambda$67(DevicePanel var0) {
      return var0.p("AlwaysOnDM");
   }

   private static final boolean buildToggles$lambda$68(DevicePanel var0) {
      return var0.locked("AlwaysOnDM") ^ true;
   }

   private static final Unit buildToggles$lambda$69(DevicePanel var0, boolean var1, Switch var2) {
      Intrinsics.checkNotNullParameter(var2, "<unused var>");
      setParam$default(var0, "AlwaysOnDM", var1, false, null, 12, null);
      return Unit.INSTANCE;
   }

   private static final boolean buildToggles$lambda$70(DevicePanel var0) {
      return var0.p("RecordFront");
   }

   private static final boolean buildToggles$lambda$71(DevicePanel var0) {
      boolean var1;
      if (!var0.engaged() && !var0.locked("RecordFront")) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private static final Unit buildToggles$lambda$72(DevicePanel var0, boolean var1, Switch var2) {
      Intrinsics.checkNotNullParameter(var2, "<unused var>");
      setParam$default(var0, "RecordFront", var1, false, null, 12, null);
      return Unit.INSTANCE;
   }

   private static final boolean buildToggles$lambda$73(DevicePanel var0) {
      return var0.p("RecordAudio");
   }

   private static final boolean buildToggles$lambda$74(DevicePanel var0) {
      boolean var1;
      if (!var0.engaged() && !var0.locked("RecordAudio")) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private static final Unit buildToggles$lambda$75(DevicePanel var0, boolean var1, Switch var2) {
      Intrinsics.checkNotNullParameter(var2, "<unused var>");
      setParam$default(var0, "RecordAudio", var1, false, null, 12, null);
      return Unit.INSTANCE;
   }

   private static final boolean buildToggles$lambda$76(DevicePanel var0) {
      return var0.p("IsMetric");
   }

   private static final boolean buildToggles$lambda$77(DevicePanel var0) {
      return var0.locked("IsMetric") ^ true;
   }

   private static final Unit buildToggles$lambda$78(DevicePanel var0, boolean var1, Switch var2) {
      Intrinsics.checkNotNullParameter(var2, "<unused var>");
      setParam$default(var0, "IsMetric", var1, false, null, 12, null);
      return Unit.INSTANCE;
   }

   private static final String buildToggles$restartDesc(String var0) {
      return var0 + " Changing this setting will restart openpilot if the car is powered on.";
   }

   private final LinearLayout button(
      String var1,
      Function0<String> var2,
      String var3,
      Function0<String> var4,
      Function0<Boolean> var5,
      Function0<Boolean> var6,
      Function0<String> var7,
      Function0<Unit> var8
   ) {
      LinearLayout var9 = this.card();
      if (var3 == null) {
         if (var7 != null) {
            var3 = (String)var7.invoke();
         } else {
            var3 = null;
         }
      }

      LinearLayout var12 = this.titleBlock(var1, var3);
      var9.addView(this.chip(var1), (LayoutParams)this.chipLp());
      var9.addView((View)var12, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(0, -2, 1.0F)));
      TextView var11 = new TextView((Context)this.act);
      var11.setTextColor(this.dim);
      var11.setTextSize(17.0F);
      var11.setPadding(this.dp(8), 0, this.dp(12), 0);
      var11.setGravity(8388613);
      var9.addView((View)var11, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(0, -2, 0.8F)));
      Button var10 = new Button((Context)this.act);
      var10.setTextSize(17.0F);
      var10.setOnClickListener(new DevicePanel$$ExternalSyntheticLambda39(var8));
      var10.setMinWidth(this.dp(130));
      var10.setMinHeight(this.dp(56));
      var9.addView((View)var10);
      this.add((View)var9);
      this.updaters.add(new DevicePanel$$ExternalSyntheticLambda40(var10, var2, var5, var4, var11, var9, var6, var7, var12, this));
      return var9;
   }

   // $VF: synthetic method
   static LinearLayout button$default(
      DevicePanel var0,
      String var1,
      Function0 var2,
      String var3,
      Function0 var4,
      Function0 var5,
      Function0 var6,
      Function0 var7,
      Function0 var8,
      int var9,
      Object var10
   ) {
      if ((var9 & 4) != 0) {
         var3 = null;
      }

      if ((var9 & 8) != 0) {
         var4 = new DevicePanel$$ExternalSyntheticLambda70();
      }

      if ((var9 & 16) != 0) {
         var5 = new DevicePanel$$ExternalSyntheticLambda71();
      }

      if ((var9 & 32) != 0) {
         var6 = new DevicePanel$$ExternalSyntheticLambda72();
      }

      if ((var9 & 64) != 0) {
         var7 = null;
      }

      return var0.button(var1, var2, var3, (Function0<String>)var4, (Function0<Boolean>)var5, (Function0<Boolean>)var6, var7, var8);
   }

   private static final String button$lambda$13() {
      return "";
   }

   private static final boolean button$lambda$14() {
      return true;
   }

   private static final boolean button$lambda$15() {
      return true;
   }

   private static final void button$lambda$18$lambda$17(Function0 var0, View var1) {
      var0.invoke();
   }

   private static final Unit button$lambda$20(
      Button var0,
      Function0 var1,
      Function0 var2,
      Function0 var3,
      TextView var4,
      LinearLayout var5,
      Function0 var6,
      Function0 var7,
      LinearLayout var8,
      DevicePanel var9
   ) {
      var0.setText((CharSequence)var1.invoke());
      var0.setEnabled((Boolean)var2.invoke());
      float var10;
      if (var0.isEnabled()) {
         var10 = 1.0F;
      } else {
         var10 = 0.5F;
      }

      var0.setAlpha(var10);
      String var13 = (String)var3.invoke();
      var4.setText(var13);
      int var11 = var13.length();
      byte var12 = 0;
      boolean var16;
      if (var11 == 0) {
         var16 = 1;
      } else {
         var16 = 0;
      }

      if (var16) {
         var16 = 8;
      } else {
         var16 = 0;
      }

      var4.setVisibility(var16);
      if ((Boolean)var6.invoke()) {
         var16 = var12;
      } else {
         var16 = 8;
      }

      var5.setVisibility(var16);
      if (var7 != null) {
         if (var8.getChildCount() > 1) {
            View var14 = var8.getChildAt(1);
            Intrinsics.checkNotNull(var14, "null cannot be cast to non-null type android.widget.TextView");
            ((TextView)var14).setText((CharSequence)var7.invoke());
         } else {
            TextView var15 = new TextView((Context)var9.act);
            var15.setText((CharSequence)var7.invoke());
            var15.setTextColor(var9.dim);
            var15.setTextSize(15.0F);
            var8.addView((View)var15);
         }
      }

      return Unit.INSTANCE;
   }

   private final void call(String var1, Map<String, ? extends Object> var2, Function1<? super JSONObject, Unit> var3) {
      DataClient var4 = this.getData();
      if (var4 != null) {
         var4.request(var1, var2, new DevicePanel$$ExternalSyntheticLambda163(var3, this));
      } else {
         this.toast("Not connected");
      }
   }

   // $VF: synthetic method
   static void call$default(DevicePanel var0, String var1, Map var2, Function1 var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = MapsKt.emptyMap();
      }

      if ((var4 & 4) != 0) {
         var3 = null;
      }

      var0.call(var1, var2, var3);
   }

   private static final Unit call$lambda$42(Function1 var0, DevicePanel var1, JSONObject var2) {
      Intrinsics.checkNotNullParameter(var2, "ack");
      if (var0 == null) {
         var1.ackToast(var2);
      }

      if (var0 != null) {
         var0.invoke(var2);
      }

      var1.refresh(true);
      return Unit.INSTANCE;
   }

   private final LinearLayout card() {
      LinearLayout var1 = new LinearLayout((Context)this.act);
      var1.setOrientation(0);
      var1.setGravity(16);
      var1.setMinimumHeight(this.dp(76));
      var1.setPadding(this.dp(20), this.dp(14), this.dp(18), this.dp(14));
      var1.setBackgroundResource(R.drawable.card_bg);
      return var1;
   }

   private final View chip(String var1) {
      SettingIcons.Icon var2 = SettingIcons.INSTANCE.forLabel(var1, "device");
      return SvIcon.chip$default(SvIcon.INSTANCE, (Context)this.act, var2.getName(), var2.getGroup(), 46, null, 16, null);
   }

   private final android.widget.LinearLayout.LayoutParams chipLp() {
      android.widget.LinearLayout.LayoutParams var1 = new android.widget.LinearLayout.LayoutParams(this.dp(46), this.dp(46));
      var1.rightMargin = this.dp(16);
      return var1;
   }

   private final void choose(String var1, List<String> var2, String var3, Function1<? super String, Unit> var4) {
      int var5 = var2.indexOf(var3);
      new Builder((Context)this.act)
         .setTitle(var1)
         .setSingleChoiceItems(var2.toArray(new String[0]), var5, new DevicePanel$$ExternalSyntheticLambda142(var4, var2))
         .setNegativeButton("Cancel", null)
         .show();
   }

   private static final void choose$lambda$46(Function1 var0, List var1, DialogInterface var2, int var3) {
      var2.dismiss();
      var0.invoke(var1.get(var3));
   }

   private final void confirm(String var1, String var2, boolean var3, Function0<Unit> var4) {
      new Builder((Context)this.act)
         .setMessage(var1)
         .setPositiveButton(var2, new DevicePanel$$ExternalSyntheticLambda173(var4))
         .setNegativeButton("Cancel", null)
         .show();
   }

   // $VF: synthetic method
   static void confirm$default(DevicePanel var0, String var1, String var2, boolean var3, Function0 var4, int var5, Object var6) {
      if ((var5 & 4) != 0) {
         var3 = false;
      }

      var0.confirm(var1, var2, var3, var4);
   }

   private static final void confirm$lambda$43(Function0 var0, DialogInterface var1, int var2) {
      var0.invoke();
   }

   private final void connectWifi(String var1, boolean var2, boolean var3) {
      if (var2) {
         input$default(this, "Enter password", "for \"" + var1 + "\"", null, true, 8, new DevicePanel$$ExternalSyntheticLambda169(this, var1, var3), 4, null);
      } else {
         connectWifi$go(this, var1, var3, "");
      }
   }

   private static final void connectWifi$go(DevicePanel var0, String var1, boolean var2, String var3) {
      var0.netStatus.setText("Connecting to " + var1 + "…");
      var0.call(
         "wifi_connect",
         MapsKt.mapOf(new Pair[]{TuplesKt.to("ssid", var1), TuplesKt.to("password", var3), TuplesKt.to("hidden", var2)}),
         new DevicePanel$$ExternalSyntheticLambda5(var0, var1, var2)
      );
   }

   private static final Unit connectWifi$go$lambda$129(DevicePanel var0, String var1, boolean var2, JSONObject var3) {
      Intrinsics.checkNotNullParameter(var3, "r");
      if (var3.optBoolean("ok")) {
         var0.toast("Connected to " + var1);
      } else if (var3.optBoolean("need_auth")) {
         input$default(var0, "Enter password", "for \"" + var1 + "\"", null, true, 8, new DevicePanel$$ExternalSyntheticLambda0(var0, var1, var2), 4, null);
      } else {
         var0.ackToast(var3);
      }

      var0.scan(false);
      return Unit.INSTANCE;
   }

   private static final Unit connectWifi$go$lambda$129$lambda$128(DevicePanel var0, String var1, boolean var2, String var3) {
      Intrinsics.checkNotNullParameter(var3, "it");
      connectWifi$go(var0, var1, var2, var3);
      return Unit.INSTANCE;
   }

   private static final Unit connectWifi$lambda$130(DevicePanel var0, String var1, boolean var2, String var3) {
      Intrinsics.checkNotNullParameter(var3, "it");
      connectWifi$go(var0, var1, var2, var3);
      return Unit.INSTANCE;
   }

   private final boolean connected() {
      DataClient var3 = this.getData();
      boolean var2 = false;
      boolean var1 = var2;
      if (var3 != null) {
         var1 = var2;
         if (var3.getConnected()) {
            var1 = true;
         }
      }

      return var1;
   }

   private final JSONObject door() {
      return this.offline.optJSONObject("door");
   }

   private final int dp(int var1) {
      return (int)(var1 * this.d);
   }

   private final void dual(String var1, String var2, Function0<Unit> var3, Function0<Unit> var4, Function0<Boolean> var5) {
      LinearLayout var6 = new LinearLayout((Context)this.act);
      var6.setOrientation(0);
      var6.setMinimumHeight(this.dp(76));
      var6.setPadding(this.dp(20), this.dp(14), this.dp(18), this.dp(14));
      var6.setBackgroundResource(R.drawable.card_bg);
      Button var7 = new Button((Context)this.act);
      var7.setText(var1);
      var7.setOnClickListener(new DevicePanel$$ExternalSyntheticLambda76(var3));
      Button var8 = new Button((Context)this.act);
      var8.setText(var2);
      var8.setOnClickListener(new DevicePanel$$ExternalSyntheticLambda78(var4));
      var8.setTextColor(Color.rgb(255, 120, 120));
      View var10 = (View)var7;
      android.widget.LinearLayout.LayoutParams var11 = new android.widget.LinearLayout.LayoutParams(0, -2, 1.0F);
      var11.rightMargin = this.dp(8);
      Unit var9 = Unit.INSTANCE;
      var6.addView(var10, (LayoutParams)var11);
      var6.addView((View)var8, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(0, -2, 1.0F)));
      this.add((View)var6);
      this.updaters.add(new DevicePanel$$ExternalSyntheticLambda79(var8, var5));
   }

   // $VF: synthetic method
   static void dual$default(DevicePanel var0, String var1, String var2, Function0 var3, Function0 var4, Function0 var5, int var6, Object var7) {
      if ((var6 & 16) != 0) {
         var5 = new DevicePanel$$ExternalSyntheticLambda49();
      }

      var0.dual(var1, var2, var3, var4, (Function0<Boolean>)var5);
   }

   private static final boolean dual$lambda$33() {
      return true;
   }

   private static final void dual$lambda$36$lambda$35(Function0 var0, View var1) {
      var0.invoke();
   }

   private static final void dual$lambda$38$lambda$37(Function0 var0, View var1) {
      var0.invoke();
   }

   private static final Unit dual$lambda$40(Button var0, Function0 var1) {
      byte var2;
      if ((Boolean)var1.invoke()) {
         var2 = 0;
      } else {
         var2 = 4;
      }

      var0.setVisibility(var2);
      return Unit.INSTANCE;
   }

   private final boolean engaged() {
      return this.i("engaged");
   }

   private final DataClient getData() {
      return Link.INSTANCE.getData();
   }

   private final String getHost() {
      return Link.INSTANCE.getHost();
   }

   private final Telemetry getT() {
      return Link.INSTANCE.getTelemetry();
   }

   private final void header(String var1) {
      LinearLayout var2 = this.col;
      TextView var3 = new TextView((Context)this.act);
      var1 = var1.toUpperCase(Locale.ROOT);
      Intrinsics.checkNotNullExpressionValue(var1, "toUpperCase(...)");
      var3.setText(var1);
      var3.setTextColor(this.dim);
      var3.setTextSize(14.0F);
      var3.setLetterSpacing(0.1F);
      var3.setPadding(this.dp(6), this.dp(22), 0, this.dp(8));
      var2.addView((View)var3);
   }

   private final boolean i(String var1) {
      Telemetry var3 = this.getT();
      boolean var2 = false;
      if (var3 != null) {
         var2 = var3.iBool(var1, false);
      }

      return var2;
   }

   private final void input(String var1, String var2, String var3, boolean var4, int var5, Function1<? super String, Unit> var6) {
      EditText var8 = new EditText((Context)this.act);
      var8.setText(var3);
      var8.setHint(var2);
      short var7;
      if (var4) {
         var7 = 145;
      } else {
         var7 = 1;
      }

      var8.setInputType(var7);
      var8.setPadding(this.dp(20), this.dp(16), this.dp(20), this.dp(16));
      new Builder((Context)this.act)
         .setTitle(var1)
         .setView((View)var8)
         .setPositiveButton("OK", new DevicePanel$$ExternalSyntheticLambda166(var8, var5, this, var6))
         .setNegativeButton("Cancel", null)
         .show();
   }

   // $VF: synthetic method
   static void input$default(DevicePanel var0, String var1, String var2, String var3, boolean var4, int var5, Function1 var6, int var7, Object var8) {
      if ((var7 & 4) != 0) {
         var3 = "";
      }

      if ((var7 & 8) != 0) {
         var4 = false;
      }

      if ((var7 & 16) != 0) {
         var5 = 0;
      }

      var0.input(var1, var2, var3, var4, var5, var6);
   }

   private static final void input$lambda$45(EditText var0, int var1, DevicePanel var2, Function1 var3, DialogInterface var4, int var5) {
      String var6 = var0.getText().toString();
      if (var6.length() < var1) {
         var2.toast("Must be at least " + var1 + " characters");
      } else {
         var3.invoke(var6);
      }
   }

   private final void loadAuto() {
      Galaxy7 var1 = Galaxy7.Companion.current();
      if (var1 != null) {
         var1.get("/api/software/status?history=0", new DevicePanel$$ExternalSyntheticLambda73(this));
      }
   }

   private static final Unit loadAuto$lambda$202(DevicePanel var0, JSONObject var1, String var2) {
      if (var1 != null) {
         JSONObject var4 = var1.optJSONObject("operations");
         if (var4 != null) {
            Boolean var3;
            if (var4.isNull("automaticDownloads")) {
               var3 = null;
            } else {
               var3 = var4.optBoolean("automaticDownloads");
            }

            var0.autoDl = var3;
            var0.autoCan = var4.optBoolean("canConfigure");
         }
      }

      var0.refresh(true);
      return Unit.INSTANCE;
   }

   private final void loadForkFixes(LinearLayout var1) {
      String var2 = this.getHost();
      if (var2 != null) {
         new Thread(new DevicePanel$$ExternalSyntheticLambda48(this, var2, var1)).start();
      }
   }

   private static final void loadForkFixes$lambda$238(DevicePanel param0, String param1, LinearLayout param2) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:385)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:551)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1057)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:572)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
      //
      // Bytecode:
      // 000: aconst_null
      // 001: astore 3
      // 002: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 005: astore 4
      // 007: new okhttp3/OkHttpClient$Builder
      // 00a: astore 4
      // 00c: aload 4
      // 00e: invokespecial okhttp3/OkHttpClient$Builder.<init> ()V
      // 011: aload 4
      // 013: getstatic com/starpilot/starview/Link.INSTANCE Lcom/starpilot/starview/Link;
      // 016: invokevirtual com/starpilot/starview/Link.getAuthInterceptor ()Lokhttp3/Interceptor;
      // 019: invokevirtual okhttp3/OkHttpClient$Builder.addInterceptor (Lokhttp3/Interceptor;)Lokhttp3/OkHttpClient$Builder;
      // 01c: ldc2_w 4
      // 01f: getstatic java/util/concurrent/TimeUnit.SECONDS Ljava/util/concurrent/TimeUnit;
      // 022: invokevirtual okhttp3/OkHttpClient$Builder.connectTimeout (JLjava/util/concurrent/TimeUnit;)Lokhttp3/OkHttpClient$Builder;
      // 025: ldc2_w 4
      // 028: getstatic java/util/concurrent/TimeUnit.SECONDS Ljava/util/concurrent/TimeUnit;
      // 02b: invokevirtual okhttp3/OkHttpClient$Builder.readTimeout (JLjava/util/concurrent/TimeUnit;)Lokhttp3/OkHttpClient$Builder;
      // 02e: invokevirtual okhttp3/OkHttpClient$Builder.build ()Lokhttp3/OkHttpClient;
      // 031: astore 5
      // 033: new okhttp3/Request$Builder
      // 036: astore 7
      // 038: aload 7
      // 03a: invokespecial okhttp3/Request$Builder.<init> ()V
      // 03d: ldc_w "/data/openpilot/CLAUDE.md"
      // 040: invokestatic android/net/Uri.encode (Ljava/lang/String;)Ljava/lang/String;
      // 043: astore 4
      // 045: new java/lang/StringBuilder
      // 048: astore 6
      // 04a: aload 6
      // 04c: invokespecial java/lang/StringBuilder.<init> ()V
      // 04f: aload 5
      // 051: aload 7
      // 053: aload 6
      // 055: ldc_w "http://"
      // 058: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 05b: aload 1
      // 05c: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 05f: ldc_w ":8090/fs/get?path="
      // 062: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 065: aload 4
      // 067: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 06a: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 06d: invokevirtual okhttp3/Request$Builder.url (Ljava/lang/String;)Lokhttp3/Request$Builder;
      // 070: invokevirtual okhttp3/Request$Builder.build ()Lokhttp3/Request;
      // 073: invokevirtual okhttp3/OkHttpClient.newCall (Lokhttp3/Request;)Lokhttp3/Call;
      // 076: invokeinterface okhttp3/Call.execute ()Lokhttp3/Response; 1
      // 07b: checkcast java/io/Closeable
      // 07e: astore 4
      // 080: aload 4
      // 082: checkcast okhttp3/Response
      // 085: astore 1
      // 086: aload 1
      // 087: invokevirtual okhttp3/Response.isSuccessful ()Z
      // 08a: ifeq 09e
      // 08d: aload 1
      // 08e: invokevirtual okhttp3/Response.body ()Lokhttp3/ResponseBody;
      // 091: astore 1
      // 092: aload 1
      // 093: ifnull 09e
      // 096: aload 1
      // 097: invokevirtual okhttp3/ResponseBody.string ()Ljava/lang/String;
      // 09a: astore 1
      // 09b: goto 0a0
      // 09e: aconst_null
      // 09f: astore 1
      // 0a0: aload 4
      // 0a2: aconst_null
      // 0a3: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 0a6: aload 1
      // 0a7: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 0aa: astore 1
      // 0ab: goto 0ca
      // 0ae: astore 1
      // 0af: aload 1
      // 0b0: athrow
      // 0b1: astore 5
      // 0b3: aload 4
      // 0b5: aload 1
      // 0b6: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 0b9: aload 5
      // 0bb: athrow
      // 0bc: astore 1
      // 0bd: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 0c0: astore 4
      // 0c2: aload 1
      // 0c3: invokestatic kotlin/ResultKt.createFailure (Ljava/lang/Throwable;)Ljava/lang/Object;
      // 0c6: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 0c9: astore 1
      // 0ca: aload 1
      // 0cb: invokestatic kotlin/Result.isFailure-impl (Ljava/lang/Object;)Z
      // 0ce: ifeq 0d6
      // 0d1: aload 3
      // 0d2: astore 1
      // 0d3: goto 0d6
      // 0d6: aload 1
      // 0d7: checkcast java/lang/String
      // 0da: astore 4
      // 0dc: aload 4
      // 0de: ifnull 0f0
      // 0e1: getstatic com/starpilot/starview/ForkFixes.INSTANCE Lcom/starpilot/starview/ForkFixes;
      // 0e4: aload 4
      // 0e6: invokevirtual com/starpilot/starview/ForkFixes.parse (Ljava/lang/String;)Ljava/util/List;
      // 0e9: astore 3
      // 0ea: aload 3
      // 0eb: astore 1
      // 0ec: aload 3
      // 0ed: ifnonnull 0f4
      // 0f0: invokestatic kotlin/collections/CollectionsKt.emptyList ()Ljava/util/List;
      // 0f3: astore 1
      // 0f4: aload 0
      // 0f5: getfield com/starpilot/starview/DevicePanel.act Landroid/app/Activity;
      // 0f8: new com/starpilot/starview/DevicePanel$$ExternalSyntheticLambda158
      // 0fb: dup
      // 0fc: aload 2
      // 0fd: aload 1
      // 0fe: aload 0
      // 0ff: aload 4
      // 101: invokespecial com/starpilot/starview/DevicePanel$$ExternalSyntheticLambda158.<init> (Landroid/widget/LinearLayout;Ljava/util/List;Lcom/starpilot/starview/DevicePanel;Ljava/lang/String;)V
      // 104: invokevirtual android/app/Activity.runOnUiThread (Ljava/lang/Runnable;)V
      // 107: return
   }

   private static final void loadForkFixes$lambda$238$lambda$237(LinearLayout var0, List var1, DevicePanel var2, String var3) {
      var0.removeAllViews();
      if (var1.isEmpty()) {
         if (var3 == null) {
            var3 = "Couldn't read the fork's notes from the comma.";
         } else {
            var3 = "None: everything this fork fixed is fixed upstream.";
         }

         var0.addView((View)var2.noteView(var3));
      }

      Iterator var14 = var1.iterator();
      int var4 = 0;

      while (var14.hasNext()) {
         int var5 = var4 + 1;
         String var6 = (String)var14.next();
         LinearLayout var8 = var2.card();
         View var7 = var2.chip(var6);
         SvIcon.Chip var10;
         if (var7 instanceof SvIcon.Chip) {
            var10 = (SvIcon.Chip)var7;
         } else {
            var10 = null;
         }

         if (var10 != null) {
            SvIcon.Chip.set$default(var10, "wrench", null, null, 6, null);
         }

         android.widget.LinearLayout.LayoutParams var11 = new android.widget.LinearLayout.LayoutParams(var2.dp(46), var2.dp(46));
         var11.rightMargin = var2.dp(16);
         Unit var9 = Unit.INSTANCE;
         var8.addView(var7, (LayoutParams)var11);
         var8.addView(
            (View)var2.titleBlock(var4 + 1 + ". " + var6, "Carried by this fork until upstream fixes it; dropped then."),
            (LayoutParams)(new android.widget.LinearLayout.LayoutParams(0, -2, 1.0F))
         );
         View var15 = (View)var8;
         android.widget.LinearLayout.LayoutParams var12 = new android.widget.LinearLayout.LayoutParams(-1, -2);
         var12.bottomMargin = var2.dp(6);
         Unit var16 = Unit.INSTANCE;
         var0.addView(var15, (LayoutParams)var12);
         var4 = var5;
      }
   }

   private final boolean locked(String var1) {
      JSONArray var5;
      Telemetry var7;
      label31: {
         var7 = this.getT();
         var5 = null;
         if (var7 != null) {
            Map var6 = var7.getInfo();
            if (var6 != null) {
               var7 = (Telemetry)var6.get("lock");
               break label31;
            }
         }

         var7 = null;
      }

      if (var7 instanceof JSONArray) {
         var5 = (JSONArray)var7;
      }

      if (var5 == null) {
         return false;
      } else {
         int var2 = 0;

         for (int var3 = var5.length(); var2 < var3; var2++) {
            if (Intrinsics.areEqual(var5.optString(var2), var1)) {
               return true;
            }
         }

         return false;
      }
   }

   private final void multi(
      String var1, String var2, List<String> var3, Function0<Integer> var4, Function0<Boolean> var5, Function1<? super Integer, Unit> var6
   ) {
      LinearLayout var8 = new LinearLayout((Context)this.act);
      var8.setOrientation(1);
      var8.setMinimumHeight(this.dp(76));
      var8.setPadding(this.dp(20), this.dp(14), this.dp(18), this.dp(14));
      var8.setBackgroundResource(R.drawable.card_bg);
      LinearLayout var9 = new LinearLayout((Context)this.act);
      var9.setOrientation(0);
      var9.setGravity(16);
      var9.addView(this.chip(var1), (LayoutParams)this.chipLp());
      var9.addView((View)this.titleBlock(var1, var2), (LayoutParams)(new android.widget.LinearLayout.LayoutParams(0, -2, 1.0F)));
      var8.addView((View)var9);
      LinearLayout var14 = new LinearLayout((Context)this.act);
      var14.setOrientation(0);
      var14.setPadding(0, this.dp(8), 0, 0);
      Iterable var12 = var3;
      var var18 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var12, 10));
      int var7 = 0;

      for (Button var10 : var12) {
         if (var7 < 0) {
            CollectionsKt.throwIndexOverflow();
         }

         String var11 = (String)var10;
         var10 = new Button((Context)this.act);
         var10.setText(var11);
         var10.setTextSize(17.0F);
         var10.setMinHeight(this.dp(56));
         var10.setOnClickListener(new DevicePanel$$ExternalSyntheticLambda170(var6, var7));
         var18.add(var10);
         var7++;
      }

      List var13 = var18;

      for (View var17 : var13) {
         android.widget.LinearLayout.LayoutParams var16 = new android.widget.LinearLayout.LayoutParams(0, -2, 1.0F);
         var16.rightMargin = this.dp(6);
         Unit var19 = Unit.INSTANCE;
         var14.addView(var17, (LayoutParams)var16);
      }

      var8.addView((View)var14);
      this.add((View)var8);
      this.updaters.add(new DevicePanel$$ExternalSyntheticLambda171(var4, var5, var13));
   }

   // $VF: synthetic method
   static void multi$default(DevicePanel var0, String var1, String var2, List var3, Function0 var4, Function0 var5, Function1 var6, int var7, Object var8) {
      if ((var7 & 16) != 0) {
         var5 = new DevicePanel$$ExternalSyntheticLambda164();
      }

      var0.multi(var1, var2, var3, var4, (Function0<Boolean>)var5, var6);
   }

   private static final boolean multi$lambda$23() {
      return true;
   }

   private static final void multi$lambda$29$lambda$28$lambda$27(Function1 var0, int var1, View var2) {
      var0.invoke(var1);
   }

   private static final Unit multi$lambda$32(Function0 var0, Function0 var1, List var2) {
      int var6 = ((Number)var0.invoke()).intValue();
      boolean var7 = (Boolean)var1.invoke();
      Iterable var8 = var2;
      int var4 = 0;

      for (Object var10 : var8) {
         if (var4 < 0) {
            CollectionsKt.throwIndexOverflow();
         }

         Button var11 = (Button)var10;
         float var3;
         if (!var7) {
            var3 = 0.4F;
         } else if (var4 == var6) {
            var3 = 1.0F;
         } else {
            var3 = 0.55F;
         }

         var11.setAlpha(var3);
         var11.setEnabled(var7);
         int var5;
         if (var4 == var6) {
            var5 = Color.rgb(111, 227, 161);
         } else {
            var5 = -1;
         }

         var11.setTextColor(var5);
         var4++;
      }

      return Unit.INSTANCE;
   }

   private final void note(String var1) {
      this.col.addView((View)this.noteView(var1));
   }

   private final TextView noteView(String var1) {
      TextView var2 = new TextView((Context)this.act);
      var2.setText(var1);
      var2.setTextColor(this.dim);
      var2.setTextSize(15.0F);
      var2.setPadding(this.dp(4), this.dp(4), this.dp(4), this.dp(10));
      return var2;
   }

   private static final Unit onTick$lambda$50(DevicePanel var0, JSONObject var1) {
      Intrinsics.checkNotNullParameter(var1, "st");
      var0.applyWifiStatus(var1);
      return Unit.INSTANCE;
   }

   private static final Unit onTick$lambda$51(DevicePanel var0, JSONObject var1) {
      Intrinsics.checkNotNullParameter(var1, "r");
      if (var1.optBoolean("ok")) {
         var0.offline = var1;
      }

      return Unit.INSTANCE;
   }

   private final boolean onroad() {
      return this.i("onroad");
   }

   private final boolean p(String var1) {
      Telemetry var3 = this.getT();
      boolean var2 = false;
      if (var3 != null) {
         var2 = var3.pBool(var1, false);
      }

      return var2;
   }

   private final String pStr(String var1) {
      Telemetry var2 = this.getT();
      if (var2 != null) {
         String var4 = Telemetry.pStr$default(var2, var1, null, 2, null);
         if (var4 != null) {
            return var4;
         }
      }

      return "";
   }

   // $VF: synthetic method
   public static void refresh$default(DevicePanel var0, boolean var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = false;
      }

      var0.refresh(var1);
   }

   private final void scan(boolean var1) {
      if (!this.scanning) {
         this.scanning = true;
         this.call("wifi_scan", MapsKt.mapOf(TuplesKt.to("rescan", var1)), new DevicePanel$$ExternalSyntheticLambda174(this));
      }
   }

   private static final Unit scan$lambda$127(DevicePanel var0, JSONObject var1) {
      Intrinsics.checkNotNullParameter(var1, "r");
      var0.scanning = false;
      if (!var1.optBoolean("ok")) {
         var0.ackToast(var1);
         return Unit.INSTANCE;
      } else {
         var0.applyWifiStatus(var1);
         var0.netList.removeAllViews();
         JSONArray var13 = var1.optJSONArray("networks");
         if (var13 == null) {
            return Unit.INSTANCE;
         } else {
            int var4 = 0;

            for (int var3 = var13.length(); var4 < var3; var4++) {
               var1 = var13.getJSONObject(var4);
               String var14 = var1.optString("ssid");
               String var11 = "connected";
               boolean var7 = var1.optBoolean("connected");
               String var9 = "saved";
               boolean var6 = var1.optBoolean("saved");
               boolean var8 = var1.optBoolean("secured");
               LinearLayout var12 = var0.card();
               int var2 = var1.optInt("strength");
               boolean var5 = true;
               byte var26;
               if (var2 > 75) {
                  var26 = 4;
               } else if (var2 > 50) {
                  var26 = 3;
               } else if (var2 > 25) {
                  var26 = 2;
               } else {
                  var26 = 1;
               }

               String var15 = "▂▄▆█".substring(0, var26);
               Intrinsics.checkNotNullExpressionValue(var15, "substring(...)");
               String var18;
               if (var8) {
                  var18 = "\ud83d\udd12 ";
               } else {
                  var18 = "";
               }

               String var16 = var18 + var14;
               Object var10 = null;
               String var19;
               if (var7) {
                  var19 = var11;
               } else {
                  var19 = null;
               }

               if (!var6 || var7) {
                  var9 = null;
               }

               var var20 = CollectionsKt.joinToString$default(
                  CollectionsKt.listOfNotNull(new String[]{var19, var9}), " · ", null, null, 0, null, null, 62, null
               );
               boolean var27;
               if (var20.length() == 0) {
                  var27 = var5;
               } else {
                  var27 = 0;
               }

               if (var27) {
                  var20 = (String)var10;
               }

               var12.addView((View)var0.titleBlock(var16, var20), (LayoutParams)(new android.widget.LinearLayout.LayoutParams(0, -2, 1.0F)));
               TextView var21 = new TextView((Context)var0.act);
               var21.setText(var15);
               if (var7) {
                  var27 = Color.rgb(111, 227, 161);
               } else {
                  var27 = var0.dim;
               }

               var21.setTextColor(var27);
               var21.setTextSize(16.0F);
               var21.setPadding(0, 0, var0.dp(14), 0);
               var12.addView((View)var21);
               if (var6 || var7) {
                  Button var22 = new Button((Context)var0.act);
                  var22.setText("FORGET");
                  var22.setTextSize(12.0F);
                  var22.setOnClickListener(new DevicePanel$$ExternalSyntheticLambda6(var0, var14));
                  var12.addView((View)var22);
               }

               if (!var7) {
                  Button var23 = new Button((Context)var0.act);
                  var23.setText("CONNECT");
                  var23.setTextSize(12.0F);
                  var23.setOnClickListener(new DevicePanel$$ExternalSyntheticLambda7(var0, var14, var8, var6));
                  var10 = (View)var23;
                  android.widget.LinearLayout.LayoutParams var24 = new android.widget.LinearLayout.LayoutParams(-2, -2);
                  var24.leftMargin = var0.dp(6);
                  Unit var29 = Unit.INSTANCE;
                  var12.addView((View)var10, (LayoutParams)var24);
               }

               android.widget.LinearLayout.LayoutParams var25 = new android.widget.LinearLayout.LayoutParams(-1, -2);
               var25.bottomMargin = var0.dp(6);
               var0.netList.addView((View)var12, (LayoutParams)var25);
            }

            if (var13.length() == 0) {
               var0.netList.addView((View)var0.noteView("No networks found."));
            }

            return Unit.INSTANCE;
         }
      }
   }

   private static final void scan$lambda$127$lambda$123$lambda$122(DevicePanel var0, String var1, View var2) {
      confirm$default(var0, "Forget Wi-Fi network \"" + var1 + "\"?", "Forget", false, new DevicePanel$$ExternalSyntheticLambda156(var0, var1), 4, null);
   }

   private static final Unit scan$lambda$127$lambda$123$lambda$122$lambda$121(DevicePanel var0, String var1) {
      var0.call("wifi_forget", MapsKt.mapOf(TuplesKt.to("ssid", var1)), new DevicePanel$$ExternalSyntheticLambda41(var0));
      return Unit.INSTANCE;
   }

   private static final Unit scan$lambda$127$lambda$123$lambda$122$lambda$121$lambda$120(DevicePanel var0, JSONObject var1) {
      Intrinsics.checkNotNullParameter(var1, "it");
      var0.scan(false);
      return Unit.INSTANCE;
   }

   private static final void scan$lambda$127$lambda$125$lambda$124(DevicePanel var0, String var1, boolean var2, boolean var3, View var4) {
      Intrinsics.checkNotNull(var1);
      if (var2 && !var3) {
         var2 = true;
      } else {
         var2 = false;
      }

      var0.connectWifi(var1, var2, false);
   }

   private final void setParam(String var1, Object var2, boolean var3, Function1<? super JSONObject, Unit> var4) {
      DataClient var5 = this.getData();
      if (var5 != null) {
         var5.request(
            "param",
            MapsKt.mapOf(new Pair[]{TuplesKt.to("key", var1), TuplesKt.to("value", var2), TuplesKt.to("memory", var3)}),
            new DevicePanel$$ExternalSyntheticLambda43(this, var4)
         );
      } else {
         this.toast("Not connected");
      }
   }

   // $VF: synthetic method
   static void setParam$default(DevicePanel var0, String var1, Object var2, boolean var3, Function1 var4, int var5, Object var6) {
      if ((var5 & 4) != 0) {
         var3 = false;
      }

      if ((var5 & 8) != 0) {
         var4 = null;
      }

      var0.setParam(var1, var2, var3, var4);
   }

   private static final Unit setParam$lambda$41(DevicePanel var0, Function1 var1, JSONObject var2) {
      Intrinsics.checkNotNullParameter(var2, "ack");
      var0.ackToast(var2);
      if (var1 != null) {
         var1.invoke(var2);
      }

      var0.refresh(true);
      return Unit.INSTANCE;
   }

   private final void showDriverCamera() {
      String var2 = this.getHost();
      if (var2 == null) {
         this.toast("Not connected");
      } else {
         SurfaceView var1 = new SurfaceView((Context)this.act);
         TextView var4 = new TextView((Context)this.act);
         var4.setText("connecting to driver camera…");
         var4.setTextColor(this.dim);
         var4.setPadding(this.dp(12), this.dp(8), this.dp(12), this.dp(8));
         LinearLayout var3 = new LinearLayout((Context)this.act);
         var3.setOrientation(1);
         var3.addView((View)var1, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(this.dp(640), this.dp(400))));
         var3.addView((View)var4);
         VideoClient var5 = new VideoClient(new DevicePanel$$ExternalSyntheticLambda2(this, var4), new DevicePanel$$ExternalSyntheticLambda3());
         var1.getHolder().addCallback(new Callback(var5, var2) {
            final String $h;
            final VideoClient $vc;

            {
               this.$vc = var1;
               this.$h = var2x;
            }

            public void surfaceChanged(SurfaceHolder var1, int var2x, int var3x, int var4x) {
               Intrinsics.checkNotNullParameter(var1, "holder");
            }

            public void surfaceCreated(SurfaceHolder var1) {
               Intrinsics.checkNotNullParameter(var1, "holder");
               this.$vc.connect(this.$h, 8090, "driver", var1.getSurface());
            }

            public void surfaceDestroyed(SurfaceHolder var1) {
               Intrinsics.checkNotNullParameter(var1, "holder");
               this.$vc.close();
            }
         });
         new Builder((Context)this.act)
            .setTitle("Driver camera")
            .setView((View)var3)
            .setPositiveButton("Close", null)
            .setOnDismissListener(new DevicePanel$$ExternalSyntheticLambda4(var5))
            .show();
      }
   }

   private static final Unit showDriverCamera$lambda$112(DevicePanel var0, TextView var1, String var2) {
      Intrinsics.checkNotNullParameter(var2, "s");
      var0.act.runOnUiThread(new DevicePanel$$ExternalSyntheticLambda87(var1, var2));
      return Unit.INSTANCE;
   }

   private static final void showDriverCamera$lambda$112$lambda$111(TextView var0, String var1) {
      var0.setText(var1);
   }

   private static final Unit showDriverCamera$lambda$113(int var0, int var1) {
      return Unit.INSTANCE;
   }

   private static final void showDriverCamera$lambda$114(VideoClient var0, DialogInterface var1) {
      var0.close();
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   private final void showQr(String var1, String var2) {
      int var6 = this.dp(320);

      Bitmap var7;
      label54: {
         try {
            QRCodeWriter var14 = new QRCodeWriter();
            var15 = var14.encode(var2, BarcodeFormat.QR_CODE, var6, var6);
         } catch (Exception var13) {
            var7 = null;
            break label54;
         }

         int var5 = var6 * var6;

         int[] var8;
         try {
            var8 = new int[var5];
         } catch (Exception var12) {
            var7 = null;
            break label54;
         }

         int var3 = 0;

         while (true) {
            if (var3 >= var5) {
               try {
                  var7 = Bitmap.createBitmap(var8, var6, var6, Config.RGB_565);
               } catch (Exception var10) {
                  var7 = null;
               }
               break;
            }

            int var4;
            label39: {
               label38: {
                  try {
                     if (var15.get(var3 % var6, var3 / var6)) {
                        break label38;
                     }
                  } catch (Exception var11) {
                     var7 = null;
                     break;
                  }

                  var4 = -1;
                  break label39;
               }

               var4 = -16777216;
            }

            var8[var3] = var4;
            var3++;
         }
      }

      LinearLayout var17 = new LinearLayout((Context)this.act);
      var17.setOrientation(1);
      var17.setGravity(17);
      var17.setPadding(this.dp(20), this.dp(20), this.dp(20), this.dp(10));
      if (var7 != null) {
         ImageView var9 = new ImageView((Context)this.act);
         var9.setImageBitmap(var7);
         var17.addView((View)var9, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(var6, var6)));
      }

      TextView var16 = new TextView((Context)this.act);
      var16.setText(var2);
      var16.setTextSize(13.0F);
      var16.setPadding(0, this.dp(12), 0, 0);
      var16.setGravity(17);
      var16.setTextIsSelectable(true);
      var17.addView((View)var16);
      new Builder((Context)this.act).setTitle(var1).setView((View)var17).setPositiveButton("Close", null).show();
   }

   private final void startFastUpdate() {
      if (Intrinsics.areEqual(this.seven, true)) {
         call$default(this, "software_fast", null, new DevicePanel$$ExternalSyntheticLambda74(this), 2, null);
      } else {
         String var1 = this.getHost();
         if (var1 == null) {
            this.toast("Not connected");
         } else {
            GalaxyApi.post$default(
               GalaxyApi.Companion.get((Context)this.act, var1), "/api/update/fast", null, new DevicePanel$$ExternalSyntheticLambda75(this), 2, null
            );
         }
      }
   }

   private static final Unit startFastUpdate$lambda$203(DevicePanel var0, JSONObject var1) {
      Intrinsics.checkNotNullParameter(var1, "r");
      String var2;
      if (var1.optBoolean("ok")) {
         var2 = "Updating - the comma reboots when it's done";
      } else {
         var2 = var1.optString("error", "Update refused");
         Intrinsics.checkNotNullExpressionValue(var2, "optString(...)");
      }

      var0.toast(var2);
      var0.fastHandler.removeCallbacks(var0.fastPoll);
      if (var0.fastPolling) {
         var0.fastHandler.post(var0.fastPoll);
      }

      return Unit.INSTANCE;
   }

   private static final Unit startFastUpdate$lambda$205(DevicePanel var0, String var1, JSONObject var2) {
      if (var1 != null) {
         var0.toast(var1);
      } else {
         var0.toast("Updating - the comma reboots when it's done");
      }

      var0.fastHandler.removeCallbacks(var0.fastPoll);
      if (var0.fastPolling) {
         var0.fastHandler.post(var0.fastPoll);
      }

      return Unit.INSTANCE;
   }

   private final void text(String var1, Function0<String> var2) {
      LinearLayout var3 = this.card();
      var3.addView(this.chip(var1), (LayoutParams)this.chipLp());
      var3.addView((View)this.titleBlock(var1, null), (LayoutParams)(new android.widget.LinearLayout.LayoutParams(0, -2, 1.0F)));
      TextView var4 = new TextView((Context)this.act);
      var4.setTextColor(this.dim);
      var4.setTextSize(19.0F);
      var4.setTypeface(Typeface.MONOSPACE);
      var3.addView((View)var4);
      this.add((View)var3);
      this.updaters.add(new DevicePanel$$ExternalSyntheticLambda155(var4, var2));
   }

   private static final Unit text$lambda$22(TextView var0, Function0 var1) {
      var0.setText((CharSequence)var1.invoke());
      return Unit.INSTANCE;
   }

   private final LinearLayout titleBlock(String var1, String var2) {
      LinearLayout var5 = new LinearLayout((Context)this.act);
      boolean var4 = true;
      var5.setOrientation(1);
      TextView var6 = new TextView((Context)this.act);
      var6.setText(var1);
      var6.setTextColor(-1);
      var6.setTextSize(21.0F);
      var5.addView((View)var6);
      CharSequence var7 = var2;
      boolean var3 = var4;
      if (var7 != null) {
         if (var7.length() == 0) {
            var3 = var4;
         } else {
            var3 = false;
         }
      }

      if (!var3) {
         TextView var8 = new TextView((Context)this.act);
         var8.setText(var2);
         var8.setTextColor(this.dim);
         var8.setTextSize(15.0F);
         var8.setPadding(0, this.dp(4), 0, 0);
         var5.addView((View)var8);
      }

      return var5;
   }

   private final void toast(String var1) {
      Toast.makeText((Context)this.act, var1, 0).show();
   }

   private final LinearLayout toggle(
      String var1,
      String var2,
      Function0<Boolean> var3,
      Function0<Boolean> var4,
      Function0<Boolean> var5,
      Function0<String> var6,
      Function2<? super Boolean, ? super Switch, Unit> var7
   ) {
      LinearLayout var8 = this.card();
      if (var2 == null) {
         if (var6 != null) {
            var2 = (String)var6.invoke();
         } else {
            var2 = null;
         }
      }

      LinearLayout var10 = this.titleBlock(var1, var2);
      var8.addView(this.chip(var1), (LayoutParams)this.chipLp());
      var8.addView((View)var10, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(0, -2, 1.0F)));
      Switch var9 = new Switch((Context)this.act);
      var9.setScaleX(1.45F);
      var9.setScaleY(1.45F);
      var9.setPadding(this.dp(14), 0, this.dp(14), 0);
      var9.setOnCheckedChangeListener(new DevicePanel$$ExternalSyntheticLambda175(this, var7, var9));
      var8.addView((View)var9);
      this.add((View)var8);
      this.updaters.add(new DevicePanel$$ExternalSyntheticLambda11(this, var9, var3, var4, var8, var5, var6, var10));
      return var8;
   }

   // $VF: synthetic method
   static LinearLayout toggle$default(
      DevicePanel var0, String var1, String var2, Function0 var3, Function0 var4, Function0 var5, Function0 var6, Function2 var7, int var8, Object var9
   ) {
      if ((var8 & 8) != 0) {
         var4 = new DevicePanel$$ExternalSyntheticLambda82();
      }

      if ((var8 & 16) != 0) {
         var5 = new DevicePanel$$ExternalSyntheticLambda83();
      }

      if ((var8 & 32) != 0) {
         var6 = null;
      }

      return var0.toggle(var1, var2, var3, (Function0<Boolean>)var4, (Function0<Boolean>)var5, var6, var7);
   }

   private static final void toggle$lambda$11(DevicePanel var0, Function2 var1, Switch var2, CompoundButton var3, boolean var4) {
      if (!var0.syncing) {
         var1.invoke(var4, var2);
      }
   }

   private static final Unit toggle$lambda$12(
      DevicePanel var0, Switch var1, Function0 var2, Function0 var3, LinearLayout var4, Function0 var5, Function0 var6, LinearLayout var7
   ) {
      var0.syncing = true;
      var1.setChecked((Boolean)var2.invoke());
      var1.setEnabled((Boolean)var3.invoke());
      float var8;
      if (var1.isEnabled()) {
         var8 = 1.0F;
      } else {
         var8 = 0.5F;
      }

      var4.setAlpha(var8);
      byte var9;
      if ((Boolean)var5.invoke()) {
         var9 = 0;
      } else {
         var9 = 8;
      }

      var4.setVisibility(var9);
      if (var6 != null && var7.getChildCount() > 1) {
         View var10 = var7.getChildAt(1);
         Intrinsics.checkNotNull(var10, "null cannot be cast to non-null type android.widget.TextView");
         ((TextView)var10).setText((CharSequence)var6.invoke());
      }

      var0.syncing = false;
      return Unit.INSTANCE;
   }

   private static final boolean toggle$lambda$8() {
      return true;
   }

   private static final boolean toggle$lambda$9() {
      return true;
   }

   public final String getKind() {
      return this.kind;
   }

   protected void onAttachedToWindow() {
      super.onAttachedToWindow();
      if (Intrinsics.areEqual(this.kind, "software") && !this.fastPolling) {
         this.fastPolling = true;
         this.fastHandler.post(this.fastPoll);
         this.loadAuto();
      }
   }

   protected void onDetachedFromWindow() {
      this.fastPolling = false;
      this.fastHandler.removeCallbacks(this.fastPoll);
      super.onDetachedFromWindow();
   }

   public final void onTick() {
      refresh$default(this, false, 1, null);
      if (Intrinsics.areEqual(this.kind, "network") && this.connected()) {
         this.pollTick++;
         if (this.pollTick % 6 == 1) {
            call$default(this, "wifi_status", null, new DevicePanel$$ExternalSyntheticLambda120(this), 2, null);
         }

         if (this.pollTick % 10 == 5 && this.offline.optBoolean("on")) {
            call$default(this, "offline", null, new DevicePanel$$ExternalSyntheticLambda131(this), 2, null);
         }

         if (this.pollTick % 20 == 2) {
            this.scan(false);
         }
      }
   }

   public final void refresh(boolean var1) {
      Telemetry var3 = this.getT();
      int var2;
      if (var3 != null) {
         var2 = var3.getParamsGeneration();
      } else {
         var2 = -1;
      }

      if (var1 || var2 != this.lastGen) {
         this.lastGen = var2;
         Iterator var5 = this.updaters.iterator();
         Intrinsics.checkNotNullExpressionValue(var5, "iterator(...)");

         while (var5.hasNext()) {
            Object var4 = var5.next();
            Intrinsics.checkNotNullExpressionValue(var4, "next(...)");
            ((Function0)var4).invoke();
         }
      }
   }

   static final class Anon_fastPoll_1 implements Runnable {
      Anon_fastPoll_1(DevicePanel var1) {
         this.this$0 = var1;
      }

         final DevicePanel this$0;

         // $VF: synthetic method
         public static void $r8$lambda$_DcWJydzE5hCTNALEL7j7g3cwnI/* $VF was: $r8$lambda$_DcWJydzE5hCTNALEL7j7g3cwnI*/(
            JSONObject var0, DevicePanel var1, Anon_fastPoll_1 var2
         ) {
            run$lambda$4$lambda$3(var0, var1, var2);
         }

         // $VF: synthetic method
         public static Unit $r8$lambda$CDLnPzx5OfRkNPAj8TiN_SkFpuw(DevicePanel var0, Anon_fastPoll_1 var1, JSONObject var2) {
            return run$lambda$1(var0, var1, var2);
         }

         // $VF: synthetic method
         public static void $r8$lambda$qA4SgnuQtbHCZcFW2gTtZDoCvAs(Anon_fastPoll_1 var0, DevicePanel var1, String var2) {
            run$lambda$4(var0, var1, var2);
         }

         // $VF: synthetic method
         public static void $r8$lambda$zlHn8c5GMKfRQjoOJSpN1b3T1Ec(JSONObject var0, DevicePanel var1, Anon_fastPoll_1 var2) {
            run$lambda$1$lambda$0(var0, var1, var2);
         }

         private static final Unit run$lambda$1(DevicePanel var0, Anon_fastPoll_1 var1x, JSONObject var2x) {
            Intrinsics.checkNotNullParameter(var2x, "r");
            var0.fastHandler.post(new DevicePanel$fastPoll$1$$ExternalSyntheticLambda3(var2x, var0, var1x));
            return Unit.INSTANCE;
         }

         private static final void run$lambda$1$lambda$0(JSONObject var0, DevicePanel var1x, Anon_fastPoll_1 var2x) {
            boolean var5x = var0.optBoolean("ok");
            Object var6 = null;
            if (var5x) {
               var1x.seven = true;
               var1x.fastStatus = FastUpdate.INSTANCE.from70(var0);
            } else {
               String var8 = var0.optString("error");
               Intrinsics.checkNotNullExpressionValue(var8, "optString(...)");
               if (StringsKt.startsWith$default(var8, "unknown action", false, 2, null)) {
                  var1x.seven = false;
                  var1x.fastHandler.post(var2x);
                  return;
               }
            }

            var1x.refresh(true);
            if (var1x.fastPolling) {
               long var3x;
               Handler var7;
               label25: {
                  var7 = var1x.fastHandler;
                  var11 = var2x;
                  if (!FastUpdate.INSTANCE.running(var1x.fastStatus)) {
                     JSONObject var10 = var1x.fastStatus;
                     String var9 = (String)var6;
                     if (var10 != null) {
                        var9 = var10.optString("stage");
                     }

                     if (!Intrinsics.areEqual(var9, "rebooting")) {
                        var3x = 10000L;
                        break label25;
                     }
                  }

                  var3x = 1000L;
               }

               var7.postDelayed(var11, var3x);
            }
         }

         // $VF: Could not inline inconsistent finally blocks
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         private static final void run$lambda$4(Anon_fastPoll_1 var0, DevicePanel var1x, String var2x) {
            label24:
            try {
               Companion var3x = Result.Companion;
               JSONObject var9 = new JSONObject(GalaxyApi.Companion.get((Context)var1x.act, var2x).get("/api/update/fast/status"));
               var7 = KtCompat.resultOf(var9);
            } catch (Throwable var5) {
               Companion var6 = Result.Companion;
               var7 = KtCompat.resultOf(ResultKt.createFailure(var5));
               break label24;
            }

            Object var10 = var7;
            if (KtCompat.isFailure(var7)) {
               var10 = null;
            }

            JSONObject var8 = (JSONObject)var10;
            var1x.fastHandler.post(new DevicePanel$fastPoll$1$$ExternalSyntheticLambda2(var8, var1x, var0));
         }

         private static final void run$lambda$4$lambda$3(JSONObject var0, DevicePanel var1x, Anon_fastPoll_1 var2x) {
            if (var0 != null) {
               var1x.fastStatus = var0;
            }

            var1x.refresh(true);
            if (var1x.fastPolling) {
               Handler var5x = var1x.fastHandler;
               Runnable var6 = var2x;
               long var3x;
               if (FastUpdate.INSTANCE.running(var1x.fastStatus)) {
                  var3x = 1000L;
               } else {
                  var3x = 60000L;
               }

               var5x.postDelayed(var6, var3x);
            }
         }

         @Override
         public void run() {
            if (this.this$0.fastPolling) {
               DataClient var1x = this.this$0.getData();
               if (!Intrinsics.areEqual(this.this$0.seven, false) && var1x != null && var1x.getConnected()) {
                  DataClient.request$default(var1x, "software_status", null, new DevicePanel$fastPoll$1$$ExternalSyntheticLambda0(this.this$0, this), 2, null);
               } else {
                  String var2x = this.this$0.getHost();
                  if (var2x == null) {
                     this.this$0.fastHandler.postDelayed(this, 5000L);
                  } else {
                     new Thread(new DevicePanel$fastPoll$1$$ExternalSyntheticLambda1(this, this.this$0, var2x)).start();
                  }
               }
            }
         }
         }
}
