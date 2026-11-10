package com.starpilot.starview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref.ObjectRef;
import kotlin.ranges.RangesKt;
import kotlin.text.Charsets;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public final class GxSystemPage extends GxPage {
   private LinearLayout advBox;
   private TextView advBtn;
   private boolean advOpen;
   private String agnosBusyFor;
   private final HashMap<String, JSONObject> agnosByBranch;
   private final HashMap<String, String> agnosErrByBranch;
   private boolean autoBusy;
   private GxPage.Row autoRow;
   private Switch autoSwitch;
   private boolean branchBusy;
   private boolean branchListFallback;
   private boolean branchLoading;
   private List<String> branches;
   private String busy;
   private TextView checkBtn;
   private boolean checkedForUpdates;
   private String currentBranch;
   private final DateTimeFormatter dayFmt;
   private final HashMap<String, Boolean> expanded;
   private LinearLayout factoryBox;
   private GxPage.Row fastRow;
   private JSONObject fastStatus;
   private boolean isOnroad;
   private final DateTimeFormatter keyFmt;
   private TextView loadingNote;
   private final boolean needsOffroad;
   private LinearLayout noticeBox;
   private boolean otherOpen;
   private boolean pickerOpen;
   private String profileBusy;
   private LinearLayout profileNotice;
   private List<? extends JSONObject> profiles;
   private LinearLayout profilesBox;
   private boolean rebootOfflineSeen;
   private boolean rebootPending;
   private long rebootStartedAt;
   private boolean reconnectedNotice;
   private TextView recoverBtn;
   private TextView rollbackBtn;
   private String selectedCommit;
   private final DateTimeFormatter stampFmt;
   private LinearLayout statusBox;
   private boolean statusInFlight;
   private boolean statusUnavailable;
   private LinearLayout swWrap;
   private String targetBranch;
   private final DateTimeFormatter timeFmt;
   private LinearLayout tsBox;
   private boolean tsBusy;
   private boolean tsInstalled;
   private boolean tsLoaded;
   private TextView upToDateNote;
   private final ArrayList<JSONObject> versionCommits;
   private String versionError;
   private int versionGen;
   private boolean versionHasMore;
   private String versionHead;
   private boolean versionLoading;
   private String versionMode;
   private String versionNotice;
   private int versionPage;

   // $VF: synthetic method
   public static Unit $r8$lambda$_0YQYXtmMLvpgoR0SE9lWEfVH7w/* $VF was: $r8$lambda$_0YQYXtmMLvpgoR0SE9lWEfVH7w*/(GxSystemPage var0) {
      return build$lambda$11(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$_ZyrdcLv7Ho68lyMxKCiff6HFwY/* $VF was: $r8$lambda$_ZyrdcLv7Ho68lyMxKCiff6HFwY*/(GxSystemPage var0) {
      return uninstallTailscale$lambda$163(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$_d2Ga9I6_wqd2mHXg3tOmJ5E7A8/* $VF was: $r8$lambda$_d2Ga9I6_wqd2mHXg3tOmJ5E7A8*/(Function0 var0) {
      return saveProfile$lambda$128(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$_qSqfL0jb2MUfemZdDNgVDcAR6o/* $VF was: $r8$lambda$_qSqfL0jb2MUfemZdDNgVDcAR6o*/(
      boolean var0, GxSystemPage var1, boolean var2, int var3
   ) {
      return paintAdv$lambda$74$lambda$71(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$1eYG0lq5ndRLUdrFeIqMovvVCs8(GxSystemPage var0) {
      return uninstallTailscale$lambda$163$lambda$162$lambda$161(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$4vdLZEEkS5klDHGYUf42Lt74FzA(GxSystemPage var0, String var1, Object var2) {
      return loadBranches$lambda$42(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$5Nj_p5GSaB_Ywj49nyW0hUoh0rY/* $VF was: $r8$lambda$5Nj_p5GSaB_Ywj49nyW0hUoh0rY*/(GxSystemPage var0, LinearLayout var1) {
      return paintProfiles$lambda$84(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$6PDdSZ2QYTGs7F6anAawCUFXdp0(GxSystemPage var0) {
      return paintTailscale$lambda$97$lambda$93(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$6wIDKgU43Rupo4B3ULunXemr_h4/* $VF was: $r8$lambda$6wIDKgU43Rupo4B3ULunXemr_h4*/(GxSystemPage var0, String var1, String var2) {
      return saveProfile$lambda$127(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$74gH7FKv5SWUci0uEPbArgnCHOg(GxSystemPage var0, Function1 var1, String var2, Object var3) {
      return loadFastStatus$lambda$45(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$7zB59TwIlmplZuF5NBBTvoBYs6o(GxSystemPage var0, List var1, int var2) {
      return paintAdv$lambda$74$lambda$70$lambda$69(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$9B5GKZfIA_ywfpulAayDLpKQHV0(GxSystemPage var0) {
      return build$lambda$8(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$9bk2umaCGVD_olEN0nfS4ATy9PI/* $VF was: $r8$lambda$9bk2umaCGVD_olEN0nfS4ATy9PI*/(
      GxSystemPage var0, String var1, JSONObject var2
   ) {
      return deleteAllRoutes$lambda$152$lambda$151(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$BiwfQi6OsBZBXZ_MZUTjKHxbztM(GxSystemPage var0) {
      return build$lambda$9(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$CWtQ3NhDD1wJlUMihGeoEUwNM94(GxSystemPage var0, GxSystemPage.DayGroup var1, boolean var2, View var3) {
      buildPicker$lambda$82(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$CpJQjZ2AgH6_0CxOyFhXb_oToZw/* $VF was: $r8$lambda$CpJQjZ2AgH6_0CxOyFhXb_oToZw*/(GxSystemPage var0) {
      return deleteAllRoutes$lambda$152(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$DwDrbXJlXRXWF_YHEzoCa3HERPY/* $VF was: $r8$lambda$DwDrbXJlXRXWF_YHEzoCa3HERPY*/(GxSystemPage var0) {
      return paintTailscale$lambda$97$lambda$94(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$Et_x85pxF734wYG1_X7qN1d0dng/* $VF was: $r8$lambda$Et_x85pxF734wYG1_X7qN1d0dng*/(Function0 var0, View var1) {
      kv$lambda$20$lambda$19(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$EyPRjLnSDwxG9rBcw0FOb86p86k(GxSystemPage var0) {
      return build$lambda$6(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$F8_sLADEcADkOPi2aO4JnZZNYK0(GxSystemPage var0) {
      return build$lambda$7(var0);
   }

   // $VF: synthetic method
   public static int $r8$lambda$GBwuE4qk_tJUienPm0t_dU1rz8Y(GxSystemPage var0, JSONObject var1, JSONObject var2) {
      return releaseVersions$lambda$28(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$Ge7dKeSZThHivlDb231S_rfC9GU/* $VF was: $r8$lambda$Ge7dKeSZThHivlDb231S_rfC9GU*/(boolean var0, GxSystemPage var1, int var2) {
      return paintAdv$lambda$74$lambda$67(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$Gp9cu8WwgH60JoXT4EQTkESnJvE(GxSystemPage var0, Function0 var1, String var2, JSONObject var3) {
      return loadTailscale$lambda$47(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$HcxFcpRIxWKqcdu_JrTaLfNeBG4(GxSystemPage var0) {
      return build$lambda$14(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$IC7V2oFpHEv74xoeT16Ofe_hrIc/* $VF was: $r8$lambda$IC7V2oFpHEv74xoeT16Ofe_hrIc*/(GxSystemPage var0) {
      return build$lambda$15(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$JmYRc_u4x9hchsAyjZxTu_OxxZE(GxSystemPage var0, String var1) {
      return loadBranches$lambda$42$lambda$41(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$KcypjjGw1apduHcw0c8P7H0kRWw(GxSystemPage var0, String var1, JSONObject var2) {
      return resetDefault$lambda$148$lambda$147(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$M_8QGY5AEh0MvzW1UBYpUNjr87s/* $VF was: $r8$lambda$M_8QGY5AEh0MvzW1UBYpUNjr87s*/(
      GxSystemPage var0, String var1, int var2, String var3, String var4
   ) {
      return installSelectedVersion$lambda$109(var0, var1, var2, var3, var4);
   }

   // $VF: synthetic method
   public static int $r8$lambda$MMubhW50kxSDXUAZDzEomo7s1P0(GxSystemPage.DayGroup var0, GxSystemPage.DayGroup var1) {
      return groupVersionHistory$lambda$31(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$NucZNqOFwM4sYysWIfpaoa8orZE(boolean var0, String var1, GxSystemPage var2, View var3) {
      buildPicker$versionRow$lambda$81(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static void $r8$lambda$OAbxDwCcyg6EBaXMOOUqoWdC3ks(String var0, String var1, GxSystemPage var2) {
      backupToggles$lambda$140(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$OeEbfv3mkqItV8XHA96m2kY42CQ(
      int var0, GxSystemPage var1, String var2, int var3, String var4, int var5, int var6, int var7, String var8, Object var9
   ) {
      return loadVersions$step$lambda$101(var0, var1, var2, var3, var4, var5, var6, var7, var8, var9);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$RDfVOtER28fK6EHXmxtOBkz6OOE(GxSystemPage var0, LinearLayout var1) {
      return paintTailscale$lambda$97(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$SBV8afmvbEVgOdc4aZvN3dHQby0(GxSystemPage var0, JSONObject var1) {
      return paintProfiles$lambda$89$lambda$88(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$SD76MjpDdhFBBBTUsn7vct9_CIA(GxSystemPage var0) {
      return build$lambda$13(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$Tf_LuPjU6d5wpbIMoX2LBM3AT6I(GxSystemPage var0) {
      return paintAdv$lambda$74$lambda$73(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$UWHPesv5uGCy5wqCpl0ymqlixFA(List var0, GxSystemPage var1) {
      return paintAdv$lambda$74$lambda$70(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$VGmPiQNXus2SK7fKlGTlxVWx52I(GxSystemPage var0, ObjectRef var1) {
      backupToggles$lambda$140$lambda$139(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$VVhrhz_jgG_Gns5az9JUpKybAlM/* $VF was: $r8$lambda$VVhrhz_jgG_Gns5az9JUpKybAlM*/(LinearLayout var0) {
      return paintAdv$lambda$65(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$WipG5dBXfrDTzv5VO0I3CxiXiZA(GxSystemPage var0, String var1, JSONObject var2) {
      return uninstallTailscale$lambda$163$lambda$162(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$WxgXqA3G5WNV_yFAKedwsx5Aelk(GxSystemPage var0, String var1) {
      return paintStatus$lambda$64$lambda$58(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$X0SmErkDph7l1oivcinq3SzuKo0(GxSystemPage var0, boolean var1, LinearLayout var2) {
      return paintAdv$lambda$74(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$Y1zA20U76Y58uqVpoK4kT_mOwww/* $VF was: $r8$lambda$Y1zA20U76Y58uqVpoK4kT_mOwww*/(GxSystemPage var0) {
      return resetDefault$lambda$148(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$__5hHhkBjuGnM8HaWYLm0lzQCIo/* $VF was: $r8$lambda$__5hHhkBjuGnM8HaWYLm0lzQCIo*/(GxSystemPage var0, String var1, String var2) {
      return loadProfile$lambda$133(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$aP9DnVYogiPWlDvjpCp_5uARqxY(GxSystemPage var0, String var1, JSONObject var2) {
      return runUpdate$lambda$119$lambda$118(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$bmgBwb3HRxX_NAWVGUifH8WLDxc/* $VF was: $r8$lambda$bmgBwb3HRxX_NAWVGUifH8WLDxc*/(GxSystemPage var0, LinearLayout var1) {
      return paintProfiles$lambda$89(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$buFxmie9_0sOFCDyGzysBvd94Kg(GxSystemPage var0, String var1, JSONObject var2) {
      return installTailscale$lambda$158(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$cV2Jn6GxPlRbzOfbCI0iJVx_h0g(GxSystemPage var0) {
      return build$lambda$10(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$ce29evJeB3XGfeYNtGpLA22DSvU(GxSystemPage var0, String var1, String var2, JSONObject var3) {
      return saveProfile$lambda$127$lambda$126(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$cyIwKx8Fek8qeFbym3Z8poAXDT4(GxSystemPage var0, String var1) {
      return runUpdate$lambda$119(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$e4kF59mPYbqcIIkWqxKwoxciZMc(GxSystemPage var0, String var1, JSONObject var2) {
      return factoryReset$lambda$122$lambda$121(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$gJGndht0rUMXQBej22TwFF5efYQ(GxSystemPage var0, String var1, JSONObject var2) {
      return loadProfiles$lambda$46(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$gr27BaAmg30yZPh1RVU630oo3ME(boolean var0, GxSystemPage var1) {
      return buildPicker$lambda$79(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$h1z3E3xFYIWgCiC_Jf_hNUCVBEc(GxSystemPage var0, JSONObject var1) {
      return paintProfiles$lambda$89$lambda$87(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$iJbKvtlc12vGxjtj8Y9Zbq0wJgU(GxSystemPage var0, byte[] var1, String var2) {
      return restoreToggles$lambda$146$lambda$145(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$icvTZ_fyGsa2htUo1ZK59d9RW18/* $VF was: $r8$lambda$icvTZ_fyGsa2htUo1ZK59d9RW18*/(GxSystemPage var0, String var1) {
      return paintStatus$lambda$64$lambda$57(var0, var1);
   }

   // $VF: synthetic method
   public static CharSequence $r8$lambda$iy3DT_dhjNuI4LOSppI3zVICihY/* $VF was: $r8$lambda$iy3DT_dhjNuI4LOSppI3zVICihY*/(String var0) {
      return installSelectedVersion$lambda$104(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$jz_kRmjhbHc6RxZU4mCCbd4NDmw/* $VF was: $r8$lambda$jz_kRmjhbHc6RxZU4mCCbd4NDmw*/(GxSystemPage var0) {
      return build$lambda$4(var0);
   }

   // $VF: synthetic method
   public static CharSequence $r8$lambda$kdEECIeTR__I1GW7ad79CPAsjJE(JSONObject var0) {
      return paintProfiles$lambda$85(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$kkqcqGNrgQevr5X39iscCtqzNiM(GxSystemPage var0) {
      return build$lambda$5(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$lwr64RnhQBKKjpIBKKCX2p7VjN8(GxSystemPage var0) {
      return build$lambda$12(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$m_fBvN5Z_2dA8OctlkB_ECWzqkg/* $VF was: $r8$lambda$m_fBvN5Z_2dA8OctlkB_ECWzqkg*/(
      GxSystemPage var0, String var1, String var2, JSONObject var3
   ) {
      return loadProfile$lambda$133$lambda$132(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static int $r8$lambda$nF4_77l1bm3rOKEthbfbilh44_8/* $VF was: $r8$lambda$nF4_77l1bm3rOKEthbfbilh44_8*/(Function2 var0, Object var1, Object var2) {
      return releaseVersions$lambda$29(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$nGNFZBKo74bodd3se2ydQZpS6aY(GxSystemPage var0) {
      return factoryReset$lambda$122(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$nx3SJqTRCypp5l7gVr0C0PiM6uk(boolean var0, JSONObject var1, GxSystemPage var2, LinearLayout var3) {
      return paintFactory$lambda$92(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$oNvZoj5XSVW4Zga0eyAWnQrIDX0(GxSystemPage var0) {
      return paintAdv$lambda$74$lambda$72(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$oXUGsGQqIOkrfvLggvAXowiqksk(GxSystemPage var0, String var1, String var2, Object var3) {
      return maybeLoadAgnos$lambda$50(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$of4kYrtQIFZs_YDE5o_Xf1Qj0pc/* $VF was: $r8$lambda$of4kYrtQIFZs_YDE5o_Xf1Qj0pc*/(
      GxSystemPage var0, String var1, String var2, String var3, JSONObject var4
   ) {
      return installSelectedVersion$lambda$109$lambda$108$lambda$107(var0, var1, var2, var3, var4);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$pG6bf5TbxEfUOe9n3ge7CXTbt_o/* $VF was: $r8$lambda$pG6bf5TbxEfUOe9n3ge7CXTbt_o*/(GxSystemPage var0, Uri var1) {
      return restoreToggles$lambda$146(var0, var1);
   }

   // $VF: synthetic method
   public static int $r8$lambda$pit7xgVMghJ4xag6UAp_xF_Lk0w/* $VF was: $r8$lambda$pit7xgVMghJ4xag6UAp_xF_Lk0w*/(Function2 var0, Object var1, Object var2) {
      return groupVersionHistory$lambda$32(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$qSoF3OSDHU5w4WBy0NhtXzd_UK4(GxSystemPage var0, String var1) {
      return checkUpdates$lambda$112(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$qlbGuVLI31ua0dabqzKTe2k1DQ8(boolean var0, GxSystemPage var1) {
      return buildPicker$lambda$83(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$reAcOTgKvZ39Gw0jjuC2HzI8s_4/* $VF was: $r8$lambda$reAcOTgKvZ39Gw0jjuC2HzI8s_4*/(GxSystemPage var0) {
      return paintTailscale$lambda$97$lambda$96(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$tURx6h_H_fGdEdfRFPwLU8FCcKc/* $VF was: $r8$lambda$tURx6h_H_fGdEdfRFPwLU8FCcKc*/(GxSystemPage var0, LinearLayout var1) {
      return paintNotices$lambda$51(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$v_rIfUkKTluNZqeBSIu1MSBCBug/* $VF was: $r8$lambda$v_rIfUkKTluNZqeBSIu1MSBCBug*/(
      GxSystemPage var0, String var1, int var2, String var3, String var4, String var5
   ) {
      return installSelectedVersion$lambda$109$lambda$108(var0, var1, var2, var3, var4, var5);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$v2WOP7_B632tLLRMxXj5lFsWiwE/* $VF was: $r8$lambda$v2WOP7_B632tLLRMxXj5lFsWiwE*/(
      JSONObject var0, GxSystemPage var1, LinearLayout var2
   ) {
      return paintStatus$lambda$64(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$vRs5F6DoEfnSfc6XIYysRO4fOLg(GxSystemPage var0, boolean var1, boolean var2, String var3) {
      return setAutomaticUpdates$lambda$115(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$v_KJymlNpJcQpeY1gVwnLtkiH3U(GxSystemPage var0, String var1, JSONObject var2) {
      return restoreToggles$lambda$146$lambda$145$lambda$144(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$wFukfWqWkQWXx1DeDmeOGD8y56w(GxSystemPage var0) {
      return installTailscale$lambda$158$lambda$157(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$zYe_iIgX7_mNa_UVRcwvzSWJT0Q/* $VF was: $r8$lambda$zYe_iIgX7_mNa_UVRcwvzSWJT0Q*/(GxSystemPage var0, boolean var1) {
      return build$lambda$3(var0, var1);
   }

   public GxSystemPage(Activity var1, GxHost var2) {
      Intrinsics.checkNotNullParameter(var1, "act");
      Intrinsics.checkNotNullParameter(var2, "gx");
      super(var1, var2);
      this.needsOffroad = true;
      this.branches = CollectionsKt.emptyList();
      this.currentBranch = "";
      this.targetBranch = "";
      this.versionMode = "latest";
      this.selectedCommit = "";
      this.versionCommits = new ArrayList<>();
      this.versionHead = "";
      this.versionError = "";
      this.versionNotice = "";
      this.branchLoading = true;
      this.expanded = new HashMap<>();
      this.busy = "";
      this.agnosByBranch = new HashMap<>();
      this.agnosErrByBranch = new HashMap<>();
      this.agnosBusyFor = "";
      this.profiles = CollectionsKt.emptyList();
      this.profileBusy = "";
      this.dayFmt = DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.getDefault()).withZone(ZoneId.systemDefault());
      this.timeFmt = DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM).withLocale(Locale.getDefault()).withZone(ZoneId.systemDefault());
      this.keyFmt = DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(ZoneId.systemDefault());
      this.stampFmt = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).withLocale(Locale.getDefault()).withZone(ZoneId.systemDefault());
   }

   private final void agnosNote(LinearLayout var1) {
      JSONObject var5 = this.agnosByBranch.get(this.targetBranch);
      String var6 = this.agnosErrByBranch.get(this.targetBranch);
      if (Intrinsics.areEqual(this.agnosBusyFor, this.targetBranch)) {
         String var4 = this.targetBranch;
         this.note("Checking whether " + var4 + " includes an AGNOS firmware update…", (ViewGroup)var1);
      } else {
         boolean var3 = true;
         boolean var2;
         if (var5 != null && var5.optBoolean("available")) {
            var2 = true;
         } else {
            var2 = false;
         }

         if (var2) {
            LinearLayout var20;
            label76: {
               var20 = this.card((ViewGroup)var1);
               this.text("AGNOS firmware update included", (ViewGroup)var20, 21.0F, CarUi.Companion.getWARN(), true);
               JSONArray var8 = var5.optJSONArray("warnings");
               if (var8 != null) {
                  List var9 = this.strings(var8);
                  if (var9 != null) {
                     Iterable var26 = var9;
                     var var11 = new ArrayList();

                     for (Object var7 : var26) {
                        if (!StringsKt.isBlank((String & CharSequence)var7)) {
                           var11.add(var7);
                        }
                     }

                     var10 = var11;
                     break label76;
                  }
               }

               var10 = CollectionsKt.emptyList();
            }

            for (String var28 : var10) {
               GxPage.text$default(this, "• " + var28, (ViewGroup)var20, 15.0F, CarUi.Companion.getTEXT(), false, 16, null);
            }

            label59: {
               JSONArray var13 = var5.optJSONArray("changedPartitions");
               if (var13 != null) {
                  List var14 = this.strings(var13);
                  if (var14 != null) {
                     Iterable var23 = var14;
                     var var16 = new ArrayList();

                     for (Object var29 : var23) {
                        if (!StringsKt.isBlank((String & CharSequence)var29)) {
                           var16.add(var29);
                        }
                     }

                     var15 = var16;
                     break label59;
                  }
               }

               var15 = CollectionsKt.emptyList();
            }

            if (!var15.isEmpty()) {
               GxPage var25 = this;
               String var17 = CollectionsKt.joinToString$default(var15, ", ", null, null, 0, null, null, 62, null);
               GxPage.text$default(var25, "Changed AGNOS partitions: " + var17, (ViewGroup)var20, 0.0F, 0, false, 28, null);
            }
         } else {
            CharSequence var21 = var6;
            if (var21 != null && !StringsKt.isBlank(var21)) {
               var2 = false;
            } else {
               var2 = true;
            }

            if (!var2) {
               this.note("AGNOS update check: " + var6, (ViewGroup)var1);
            } else {
               if (var5 != null && var5.optBoolean("checked")) {
                  var2 = var3;
               } else {
                  var2 = false;
               }

               if (var2) {
                  String var22 = this.targetBranch;
                  this.note("No AGNOS firmware update is needed for " + var22 + ".", (ViewGroup)var1);
               }
            }
         }
      }
   }

   private final void backupToggles() {
      if (this.getHost() == null) {
         this.toast("Not connected");
      } else {
         new Thread(new GxSystemPage$$ExternalSyntheticLambda70(this.url("/api/toggles/backup"), "toggle-backup.json", this)).start();
      }
   }

   private static final void backupToggles$lambda$140(String param0, String param1, GxSystemPage param2) {
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
      // 000: new kotlin/jvm/internal/Ref$ObjectRef
      // 003: dup
      // 004: invokespecial kotlin/jvm/internal/Ref$ObjectRef.<init> ()V
      // 007: astore 5
      // 009: getstatic com/starpilot/starview/GxPage.Companion Lcom/starpilot/starview/GxPage$Companion;
      // 00c: ldc2_w 60
      // 00f: invokevirtual com/starpilot/starview/GxPage$Companion.client (J)Lokhttp3/OkHttpClient;
      // 012: astore 4
      // 014: new okhttp3/Request$Builder
      // 017: astore 6
      // 019: aload 6
      // 01b: invokespecial okhttp3/Request$Builder.<init> ()V
      // 01e: aload 4
      // 020: aload 6
      // 022: aload 0
      // 023: invokevirtual okhttp3/Request$Builder.url (Ljava/lang/String;)Lokhttp3/Request$Builder;
      // 026: getstatic okhttp3/RequestBody.Companion Lokhttp3/RequestBody$Companion;
      // 029: bipush 0
      // 02a: newarray 8
      // 02c: aconst_null
      // 02d: bipush 0
      // 02e: bipush 0
      // 02f: bipush 6
      // 031: aconst_null
      // 032: invokestatic okhttp3/RequestBody$Companion.create$default (Lokhttp3/RequestBody$Companion;[BLokhttp3/MediaType;IIILjava/lang/Object;)Lokhttp3/RequestBody;
      // 035: invokevirtual okhttp3/Request$Builder.post (Lokhttp3/RequestBody;)Lokhttp3/Request$Builder;
      // 038: invokevirtual okhttp3/Request$Builder.build ()Lokhttp3/Request;
      // 03b: invokevirtual okhttp3/OkHttpClient.newCall (Lokhttp3/Request;)Lokhttp3/Call;
      // 03e: invokeinterface okhttp3/Call.execute ()Lokhttp3/Response; 1
      // 043: checkcast java/io/Closeable
      // 046: astore 6
      // 048: aload 6
      // 04a: checkcast okhttp3/Response
      // 04d: astore 7
      // 04f: aload 7
      // 051: invokevirtual okhttp3/Response.body ()Lokhttp3/ResponseBody;
      // 054: astore 0
      // 055: aload 0
      // 056: ifnull 067
      // 059: aload 0
      // 05a: invokevirtual okhttp3/ResponseBody.bytes ()[B
      // 05d: astore 4
      // 05f: aload 4
      // 061: astore 0
      // 062: aload 4
      // 064: ifnonnull 06b
      // 067: bipush 0
      // 068: newarray 8
      // 06a: astore 0
      // 06b: aload 7
      // 06d: invokevirtual okhttp3/Response.isSuccessful ()Z
      // 070: istore 3
      // 071: aconst_null
      // 072: astore 4
      // 074: iload 3
      // 075: ifne 0ef
      // 078: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 07b: astore 1
      // 07c: new org/json/JSONObject
      // 07f: astore 1
      // 080: new java/lang/String
      // 083: astore 7
      // 085: aload 7
      // 087: aload 0
      // 088: getstatic kotlin/text/Charsets.UTF_8 Ljava/nio/charset/Charset;
      // 08b: invokespecial java/lang/String.<init> ([BLjava/nio/charset/Charset;)V
      // 08e: aload 1
      // 08f: aload 7
      // 091: invokespecial org/json/JSONObject.<init> (Ljava/lang/String;)V
      // 094: aload 1
      // 095: ldc_w "message"
      // 098: invokevirtual org/json/JSONObject.optString (Ljava/lang/String;)Ljava/lang/String;
      // 09b: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 09e: astore 0
      // 09f: goto 0af
      // 0a2: astore 0
      // 0a3: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 0a6: astore 1
      // 0a7: aload 0
      // 0a8: invokestatic kotlin/ResultKt.createFailure (Ljava/lang/Throwable;)Ljava/lang/Object;
      // 0ab: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 0ae: astore 0
      // 0af: aload 0
      // 0b0: astore 1
      // 0b1: aload 0
      // 0b2: invokestatic kotlin/Result.isFailure-impl (Ljava/lang/Object;)Z
      // 0b5: ifeq 0ba
      // 0b8: aconst_null
      // 0b9: astore 1
      // 0ba: aload 1
      // 0bb: checkcast java/lang/String
      // 0be: astore 0
      // 0bf: aload 0
      // 0c0: ifnull 0e0
      // 0c3: aload 0
      // 0c4: checkcast java/lang/CharSequence
      // 0c7: astore 0
      // 0c8: aload 0
      // 0c9: invokestatic kotlin/text/StringsKt.isBlank (Ljava/lang/CharSequence;)Z
      // 0cc: ifeq 0d5
      // 0cf: aload 4
      // 0d1: astore 0
      // 0d2: goto 0d5
      // 0d5: aload 0
      // 0d6: checkcast java/lang/String
      // 0d9: astore 1
      // 0da: aload 1
      // 0db: astore 0
      // 0dc: aload 1
      // 0dd: ifnonnull 0e4
      // 0e0: ldc_w "Failed to create toggle backup."
      // 0e3: astore 0
      // 0e4: new java/lang/IllegalStateException
      // 0e7: astore 1
      // 0e8: aload 1
      // 0e9: aload 0
      // 0ea: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
      // 0ed: aload 1
      // 0ee: athrow
      // 0ef: new android/content/ContentValues
      // 0f2: astore 4
      // 0f4: aload 4
      // 0f6: invokespecial android/content/ContentValues.<init> ()V
      // 0f9: aload 4
      // 0fb: ldc_w "_display_name"
      // 0fe: aload 1
      // 0ff: invokevirtual android/content/ContentValues.put (Ljava/lang/String;Ljava/lang/String;)V
      // 102: aload 4
      // 104: ldc_w "mime_type"
      // 107: ldc_w "application/json"
      // 10a: invokevirtual android/content/ContentValues.put (Ljava/lang/String;Ljava/lang/String;)V
      // 10d: aload 4
      // 10f: ldc_w "relative_path"
      // 112: ldc_w "Download/StarView"
      // 115: invokevirtual android/content/ContentValues.put (Ljava/lang/String;Ljava/lang/String;)V
      // 118: aload 2
      // 119: invokevirtual com/starpilot/starview/GxSystemPage.getAct ()Landroid/app/Activity;
      // 11c: invokevirtual android/app/Activity.getContentResolver ()Landroid/content/ContentResolver;
      // 11f: getstatic android/provider/MediaStore$Downloads.EXTERNAL_CONTENT_URI Landroid/net/Uri;
      // 122: aload 4
      // 124: invokevirtual android/content/ContentResolver.insert (Landroid/net/Uri;Landroid/content/ContentValues;)Landroid/net/Uri;
      // 127: astore 4
      // 129: aload 4
      // 12b: ifnull 19c
      // 12e: aload 2
      // 12f: invokevirtual com/starpilot/starview/GxSystemPage.getAct ()Landroid/app/Activity;
      // 132: invokevirtual android/app/Activity.getContentResolver ()Landroid/content/ContentResolver;
      // 135: aload 4
      // 137: invokevirtual android/content/ContentResolver.openOutputStream (Landroid/net/Uri;)Ljava/io/OutputStream;
      // 13a: astore 4
      // 13c: aload 4
      // 13e: ifnull 18f
      // 141: aload 4
      // 143: checkcast java/io/Closeable
      // 146: astore 4
      // 148: aload 4
      // 14a: checkcast java/io/OutputStream
      // 14d: aload 0
      // 14e: invokevirtual java/io/OutputStream.write ([B)V
      // 151: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 154: astore 0
      // 155: aload 4
      // 157: aconst_null
      // 158: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 15b: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 15e: astore 0
      // 15f: aload 6
      // 161: aconst_null
      // 162: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 165: new java/lang/StringBuilder
      // 168: astore 0
      // 169: aload 0
      // 16a: invokespecial java/lang/StringBuilder.<init> ()V
      // 16d: aload 5
      // 16f: aload 0
      // 170: ldc_w "Toggle backup downloaded to Downloads/StarView/"
      // 173: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 176: aload 1
      // 177: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 17a: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 17d: putfield kotlin/jvm/internal/Ref$ObjectRef.element Ljava/lang/Object;
      // 180: goto 1cb
      // 183: astore 1
      // 184: aload 1
      // 185: athrow
      // 186: astore 0
      // 187: aload 4
      // 189: aload 1
      // 18a: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 18d: aload 0
      // 18e: athrow
      // 18f: new java/lang/IllegalStateException
      // 192: astore 0
      // 193: aload 0
      // 194: ldc_w "cannot write file"
      // 197: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
      // 19a: aload 0
      // 19b: athrow
      // 19c: new java/lang/IllegalStateException
      // 19f: astore 0
      // 1a0: aload 0
      // 1a1: ldc_w "cannot create file"
      // 1a4: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
      // 1a7: aload 0
      // 1a8: athrow
      // 1a9: astore 1
      // 1aa: aload 1
      // 1ab: athrow
      // 1ac: astore 0
      // 1ad: aload 6
      // 1af: aload 1
      // 1b0: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 1b3: aload 0
      // 1b4: athrow
      // 1b5: astore 0
      // 1b6: aload 0
      // 1b7: invokevirtual java/lang/Exception.getMessage ()Ljava/lang/String;
      // 1ba: astore 1
      // 1bb: aload 1
      // 1bc: astore 0
      // 1bd: aload 1
      // 1be: ifnonnull 1c5
      // 1c1: ldc_w "Backup failed."
      // 1c4: astore 0
      // 1c5: aload 5
      // 1c7: aload 0
      // 1c8: putfield kotlin/jvm/internal/Ref$ObjectRef.element Ljava/lang/Object;
      // 1cb: aload 2
      // 1cc: invokevirtual com/starpilot/starview/GxSystemPage.getMain ()Landroid/os/Handler;
      // 1cf: new com/starpilot/starview/GxSystemPage$$ExternalSyntheticLambda30
      // 1d2: dup
      // 1d3: aload 2
      // 1d4: aload 5
      // 1d6: invokespecial com/starpilot/starview/GxSystemPage$$ExternalSyntheticLambda30.<init> (Lcom/starpilot/starview/GxSystemPage;Lkotlin/jvm/internal/Ref$ObjectRef;)V
      // 1d9: invokevirtual android/os/Handler.post (Ljava/lang/Runnable;)Z
      // 1dc: pop
      // 1dd: return
   }

   private static final void backupToggles$lambda$140$lambda$139(GxSystemPage var0, ObjectRef var1) {
      var0.toast((String)var1.element);
   }

   private static final Unit build$lambda$10(GxSystemPage var0) {
      var0.restoreToggles();
      return Unit.INSTANCE;
   }

   private static final Unit build$lambda$11(GxSystemPage var0) {
      var0.resetDefault();
      return Unit.INSTANCE;
   }

   private static final Unit build$lambda$12(GxSystemPage var0) {
      var0.deleteAllRoutes();
      return Unit.INSTANCE;
   }

   private static final Unit build$lambda$13(GxSystemPage var0) {
      var0.getGx().openPage("gx:galaxy");
      return Unit.INSTANCE;
   }

   private static final Unit build$lambda$14(GxSystemPage var0) {
      var0.factoryReset();
      return Unit.INSTANCE;
   }

   private static final Unit build$lambda$15(GxSystemPage var0) {
      if (var0.getStatusPollingNeeded() && !var0.statusInFlight) {
         loadFastStatus$default(var0, null, 1, null);
      }

      return Unit.INSTANCE;
   }

   private static final Unit build$lambda$3(GxSystemPage var0, boolean var1) {
      var0.setAutomaticUpdates(var1);
      return Unit.INSTANCE;
   }

   private static final Unit build$lambda$4(GxSystemPage var0) {
      var0.getGx().openPage("native:software");
      return Unit.INSTANCE;
   }

   private static final Unit build$lambda$5(GxSystemPage var0) {
      var0.checkUpdates();
      return Unit.INSTANCE;
   }

   private static final Unit build$lambda$6(GxSystemPage var0) {
      var0.runUpdate("recover");
      return Unit.INSTANCE;
   }

   private static final Unit build$lambda$7(GxSystemPage var0) {
      var0.runUpdate("rollback");
      return Unit.INSTANCE;
   }

   private static final Unit build$lambda$8(GxSystemPage var0) {
      var0.advOpen ^= true;
      TextView var2 = var0.advBtn;
      TextView var1 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("advBtn");
         var1 = null;
      }

      String var3;
      if (var0.advOpen) {
         var3 = "Hide";
      } else {
         var3 = "Show";
      }

      var1.setText(var3);
      var0.forceAdv();
      return Unit.INSTANCE;
   }

   private static final Unit build$lambda$9(GxSystemPage var0) {
      var0.backupToggles();
      return Unit.INSTANCE;
   }

   private final void buildPicker(LinearLayout var1, boolean var2) {
      boolean var7 = Intrinsics.areEqual(this.targetBranch, "StarPilot");
      List var11 = this.versionChoices();
      List var10 = this.groupVersionHistory(var11);
      boolean var5 = this.expanded.isEmpty();
      Boolean var13 = true;
      if (var5 && !var10.isEmpty()) {
         this.expanded.put(((GxSystemPage.DayGroup)var10.get(0)).getKey(), var13);
      }

      Iterator var9 = var11.iterator();

      Object var8;
      JSONObject var12;
      do {
         if (!var9.hasNext()) {
            var8 = null;
            break;
         }

         var8 = var9.next();
         var12 = (JSONObject)var8;
      } while (!Intrinsics.areEqual(GxPage.str$default(this, var12, "sha", null, 2, null), this.selectedCommit));

      var12 = (JSONObject)var8;
      String var50;
      if (var7) {
         int var4 = var11.size();
         if (var11.size() == 1) {
            var8 = "release";
         } else {
            var8 = "releases";
         }

         int var3 = this.versionCommits.size();
         var50 = var4 + " " + var8 + " · " + var3 + " builds checked";
      } else {
         var8 = var10;
         var var14 = new ArrayList();

         for (Object var15 : var8) {
            if (!Intrinsics.areEqual(((GxSystemPage.DayGroup)var15).getKey(), "unknown")) {
               var14.add(var15);
            }
         }

         var8 = var14;
         if (var8.isEmpty()) {
            if (!this.versionCommits.isEmpty()) {
               int var22 = this.versionCommits.size();
               var8 = var22 + " versions loaded";
            } else {
               var8 = "No history loaded yet";
            }

            var50 = (String)var8;
         } else {
            int var23 = this.versionCommits.size();
            String var52 = CollectionsKt.<GxSystemPage.DayGroup>last((List<? extends GxSystemPage.DayGroup>)var8).getLabel();
            if (var8.size() > 1) {
               var8 = ((GxSystemPage.DayGroup)var8.get(0)).getLabel();
               var8 = " – " + var8;
            } else {
               var8 = "";
            }

            var50 = var23 + " versions · " + var52 + var8;
         }
      }

      LinearLayout var66 = this.card((ViewGroup)var1);
      GxPage.text$default(this, "Earlier version", (ViewGroup)var66, 15.0F, CarUi.Companion.getDIM(), false, 16, null);
      if (var12 != null) {
         var8 = this.versionTitle(var12, var7);
      } else {
         var8 = "Select an earlier version";
      }

      this.text((String)var8, (ViewGroup)var66, 21.0F, CarUi.Companion.getTEXT(), true);
      GxPage var69 = this;
      var5 = this.versionHasMore;
      String var65 = "All available history loaded.";
      if (var5) {
         if (var7) {
            var8 = "Open the picker to load older versions.";
         } else {
            var8 = "Open the picker to load older days.";
         }
      } else {
         label226: {
            if (!this.versionCommits.isEmpty()) {
               boolean var24;
               if (this.versionError.length() == 0) {
                  var24 = true;
               } else {
                  var24 = false;
               }

               if (var24) {
                  var8 = "All available history loaded.";
                  break label226;
               }
            }

            var8 = "Open the picker to browse history.";
         }
      }

      GxPage.text$default(var69, var50 + ". " + var8, (ViewGroup)var66, 0.0F, 0, false, 28, null);
      boolean var25;
      if (this.versionNotice.length() > 0) {
         var25 = 1;
      } else {
         var25 = 0;
      }

      if (var25) {
         GxPage.text$default(this, this.versionNotice, (ViewGroup)var66, 0.0F, 0, false, 28, null);
      }

      var69 = this;
      if (this.pickerOpen) {
         var8 = "Hide versions";
      } else {
         var8 = "Browse versions";
      }

      var8 = TuplesKt.to(var8, new GxSystemPage$$ExternalSyntheticLambda46(var2, this));
      ViewGroup var16 = (ViewGroup)var66;
      this.en(GxPage.buttons$default(var69, new Pair[]{(Pair)var8}, var16, false, 4, null).getChildAt(0), var2 ^ true);
      if (this.pickerOpen && !var2) {
         var66 = this.card((ViewGroup)var1);
         if (var7) {
            var8 = "Earlier StarPilot versions";
         } else {
            var8 = "Earlier versions by day";
         }

         this.text((String)var8, (ViewGroup)var66, 21.0F, CarUi.Companion.getTEXT(), true);
         GxPage.text$default(this, var50, (ViewGroup)var66, 0.0F, 0, false, 28, null);
         if (this.versionNotice.length() > 0) {
            var25 = (boolean)1;
         } else {
            var25 = (boolean)0;
         }

         if (var25) {
            GxPage.text$default(this, this.versionNotice, (ViewGroup)var66, 0.0F, 0, false, 28, null);
         }

         GxPage var53 = this;
         if (var7) {
            var8 = "Each version uses its newest build. Build dates are shown in your local time.";
         } else {
            var8 = "Expand a day to choose a build. Build dates are shown in your local time.";
         }

         GxPage.text$default(var53, (String)var8, (ViewGroup)var66, 0.0F, 0, false, 28, null);
         if (var7) {
            var8 = var11.iterator();

            while (var8.hasNext()) {
               buildPicker$versionRow(this, var7, var1, var2, (JSONObject)var8.next(), 0);
            }
         } else {
            for (GxSystemPage.DayGroup var48 : var10) {
               var5 = Intrinsics.areEqual(this.expanded.get(var48.getKey()), var13);
               GxPage var58 = this;
               String var62 = var48.getLabel();
               var25 = var48.getCommits().size();
               String var54;
               if (var48.getCommits().size() == 1) {
                  var54 = "build";
               } else {
                  var54 = "builds";
               }

               GxPage.Row var55 = GxPage.row$default(var58, var62 + "  · " + var25 + " " + var54, null, (ViewGroup)var1, 0, 8, null);
               String var59;
               if (var5) {
                  var59 = "▲";
               } else {
                  var59 = "▼";
               }

               var55.value(var59);
               var55.getView().setOnClickListener(new GxSystemPage$$ExternalSyntheticLambda47(this, var48, var5));
               if (var5) {
                  Iterator var60 = var48.getCommits().iterator();
                  Intrinsics.checkNotNullExpressionValue(var60, "iterator(...)");

                  while (var60.hasNext()) {
                     Object var63 = var60.next();
                     Intrinsics.checkNotNullExpressionValue(var63, "next(...)");
                     buildPicker$versionRow(this, var7, var1, var2, (JSONObject)var63, 1);
                  }
               }
            }
         }

         var8 = this.card((ViewGroup)var1);
         boolean var29;
         if (this.versionError.length() > 0) {
            var29 = true;
         } else {
            var29 = false;
         }

         if (var29) {
            GxPage.text$default(this, this.versionError, (ViewGroup)var8, 15.0F, GxSystemKt.access$getGxSystem_DANGER$p(), false, 16, null);
         }

         if (this.versionLoading) {
            GxPage.text$default(this, "Loading older history…", (ViewGroup)var8, 0.0F, 0, false, 28, null);
         }

         label252: {
            if (!this.versionHasMore) {
               if (this.versionError.length() > 0) {
                  var29 = true;
               } else {
                  var29 = false;
               }

               if (!var29) {
                  if (!this.versionLoading) {
                     GxPage var57 = this;
                     String var20;
                     if (!this.versionCommits.isEmpty()) {
                        var20 = var65;
                     } else {
                        var20 = "No history available.";
                     }

                     GxPage.text$default(var57, var20, (ViewGroup)var8, 0.0F, 0, false, 28, null);
                  }
                  break label252;
               }
            }

            GxPage var56 = this;
            if (this.versionError.length() > 0) {
               var29 = true;
            } else {
               var29 = false;
            }

            String var17;
            if (var29) {
               var17 = "Retry history";
            } else if (var7) {
               var17 = "Load older versions";
            } else {
               var17 = "Load older days";
            }

            Pair var18 = TuplesKt.to(var17, new GxSystemPage$$ExternalSyntheticLambda48(var2, this));
            ViewGroup var61 = (ViewGroup)var8;
            View var19 = GxPage.buttons$default(var56, new Pair[]{var18}, var61, false, 4, null).getChildAt(0);
            if (!this.versionLoading && !var2) {
               var2 = true;
            } else {
               var2 = false;
            }

            this.en(var19, var2);
         }

         var29 = false;
         if (this.versionHasMore) {
            if (this.versionError.length() == 0) {
               var29 = true;
            }

            if (var29) {
               GxPage.text$default(this, "Keep loading to browse further back.", (ViewGroup)var8, 0.0F, 0, false, 28, null);
            }
         }
      } else {
         boolean var26 = false;
         if (this.versionError.length() > 0) {
            var26 = true;
         }

         if (var26) {
            GxPage.text$default(this, this.versionError, (ViewGroup)var66, 15.0F, GxSystemKt.access$getGxSystem_DANGER$p(), false, 16, null);
         }

         if (this.versionLoading) {
            GxPage.text$default(this, "Loading older history…", (ViewGroup)var66, 0.0F, 0, false, 28, null);
         }
      }
   }

   private static final Unit buildPicker$lambda$79(boolean var0, GxSystemPage var1) {
      if (!var0) {
         var1.pickerOpen ^= true;
         var1.forceAdv();
      }

      return Unit.INSTANCE;
   }

   private static final void buildPicker$lambda$82(GxSystemPage var0, GxSystemPage.DayGroup var1, boolean var2, View var3) {
      var0.expanded.put(var1.getKey(), var2 ^ true);
      var0.forceAdv();
   }

   private static final Unit buildPicker$lambda$83(boolean var0, GxSystemPage var1) {
      if (!var0 && !var1.versionLoading) {
         if (!var1.versionCommits.isEmpty() && var1.versionHasMore) {
            var0 = true;
         } else {
            var0 = false;
         }

         var1.loadVersions(var0);
      }

      return Unit.INSTANCE;
   }

   private static final void buildPicker$versionRow(GxSystemPage var0, boolean var1, LinearLayout var2, boolean var3, JSONObject var4, int var5) {
      String var7 = GxPage.str$default(var0, var4, "sha", null, 2, null);
      boolean var6 = Intrinsics.areEqual(var7, var0.selectedCommit);
      GxPage.Row var8 = var0.row(var0.versionTitle(var4, var1), null, (ViewGroup)var2, var5);
      var8.getTitleView().setTextSize(18.0F);
      if (var6) {
         var8.getView().setBackground((Drawable)var0.getUi().rounded(CarUi.Companion.getCARD_ON(), 14, CarUi.Companion.getACCENT()));
         var8.value("✓").setTextColor(CarUi.Companion.getACCENT());
      }

      var8.getView().setOnClickListener(new GxSystemPage$$ExternalSyntheticLambda35(var3, var7, var0));
   }

   private static final void buildPicker$versionRow$lambda$81(boolean var0, String var1, GxSystemPage var2, View var3) {
      if (!var0 && GxSystemKt.access$getGxSystem_SHA$p().matches(var1)) {
         var var7 = var2.versionChoices();
         var0 = var7 instanceof Collection;
         boolean var4 = true;
         if (!var0 || !((Collection)var7).isEmpty()) {
            for (JSONObject var5 : var7) {
               if (Intrinsics.areEqual(GxPage.str$default(var2, var5, "sha", null, 2, null), var1)) {
                  var4 = false;
                  break;
               }
            }
         }

         if (!var4) {
            var2.selectedCommit = var1;
            var2.pickerOpen = false;
            var2.forceAdv();
            return;
         }
      }
   }

   private final void checkUpdates() {
      boolean var1;
      if (this.busy.length() > 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      if (!var1 && !this.getUpdateInProgress()) {
         this.busy = "check";
         this.paint();
         this.loadFastStatus(new GxSystemPage$$ExternalSyntheticLambda38(this));
      }
   }

   private static final Unit checkUpdates$lambda$112(GxSystemPage var0, String var1) {
      var0.busy = "";
      if (var1 != null) {
         var0.toast("Failed to check for updates.");
      } else {
         boolean var2 = true;
         var0.checkedForUpdates = true;
         JSONObject var4 = var0.fastStatus;
         if (var0.getUpdateInProgress()) {
            var0.toast("An update is already running.");
         } else {
            if (var4 == null || !var4.optBoolean("updateAvailable")) {
               var2 = false;
            }

            if (var2) {
               CharSequence var3 = GxPage.str$default(var0, var4, "message", null, 2, null);
               Object var5 = var3;
               if (StringsKt.isBlank(var3)) {
                  var5 = "Update available.";
               }

               var0.toast((String)var5);
            } else {
               label33: {
                  if (var4 != null) {
                     var1 = GxPage.str$default(var0, var4, "message", null, 2, null);
                     if (var1 != null) {
                        CharSequence var9 = var1;
                        CharSequence var7 = var9;
                        if (StringsKt.isBlank(var9)) {
                           var7 = null;
                        }

                        var1 = (String)var7;
                        if (var1 != null) {
                           break label33;
                        }
                     }
                  }

                  var1 = "No update available — you're up to date.";
               }

               var0.toast(var1);
            }
         }
      }

      var0.paint();
      return Unit.INSTANCE;
   }

   private final void clearRebootPending(boolean var1) {
      this.rebootPending = false;
      this.rebootStartedAt = 0L;
      this.rebootOfflineSeen = false;
      String var2 = this.getHost();
      if (var2 != null) {
         Long var3 = GxSystemReboot.INSTANCE.getStartedAt().remove(var2);
      }

      if (var1) {
         this.reconnectedNotice = true;
      }
   }

   // $VF: synthetic method
   static void clearRebootPending$default(GxSystemPage var0, boolean var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = true;
      }

      var0.clearRebootPending(var1);
   }

   private final void deleteAllRoutes() {
      this.confirm(
         "Delete All Driving Routes",
         "This permanently deletes all local routes from standard, high-resolution, and alternate footage storage. It does not reset settings or reboot the device.",
         "Delete Routes",
         new GxSystemPage$$ExternalSyntheticLambda68(this)
      );
   }

   private static final Unit deleteAllRoutes$lambda$152(GxSystemPage var0) {
      var0.send("DELETE", "/api/routes/delete_all?include_preserved=true", null, 120L, new GxSystemPage$$ExternalSyntheticLambda23(var0));
      return Unit.INSTANCE;
   }

   private static final Unit deleteAllRoutes$lambda$152$lambda$151(GxSystemPage var0, String var1, JSONObject var2) {
      if (var1 != null) {
         CharSequence var7 = var1;
         Object var3 = var7;
         if (StringsKt.isBlank(var7)) {
            var3 = "Failed to delete driving routes.";
         }

         var1 = (String)var3;
      } else {
         label24: {
            if (var2 != null) {
               var1 = GxPage.str$default(var0, var2, "message", null, 2, null);
               if (var1 != null) {
                  CharSequence var8 = var1;
                  CharSequence var6 = var8;
                  if (StringsKt.isBlank(var8)) {
                     var6 = null;
                  }

                  var1 = (String)var6;
                  if (var1 != null) {
                     break label24;
                  }
               }
            }

            var1 = "All local driving routes deleted.";
         }
      }

      var0.toast(var1);
      return Unit.INSTANCE;
   }

   private final void en(View var1, boolean var2) {
      if (var1 != null) {
         var1.setEnabled(var2);
         float var3;
         if (var2) {
            var3 = 1.0F;
         } else {
            var3 = 0.4F;
         }

         var1.setAlpha(var3);
      }
   }

   private final void factoryReset() {
      this.confirm(
         "Factory reset (SAVE ME)?",
         "This wipes params, backups, themes, models, maps, and route data, then reboots. This cannot be undone.",
         "Factory Reset",
         new GxSystemPage$$ExternalSyntheticLambda21(this)
      );
   }

   private static final Unit factoryReset$lambda$122(GxSystemPage var0) {
      GxPage.send$default(var0, "POST", "/api/update/factory_reset", null, 0L, new GxSystemPage$$ExternalSyntheticLambda28(var0), 12, null);
      return Unit.INSTANCE;
   }

   private static final Unit factoryReset$lambda$122$lambda$121(GxSystemPage var0, String var1, JSONObject var2) {
      if (var1 != null) {
         CharSequence var4 = var1;
         Object var3 = var4;
         if (StringsKt.isBlank(var4)) {
            var3 = "Factory reset failed.";
         }

         var0.toast((String)var3);
         return Unit.INSTANCE;
      } else {
         var0.markRebootPending();
         var0.toast("SAVE ME initiated — factory resetting...");
         var0.paint();
         loadFastStatus$default(var0, null, 1, null);
         return Unit.INSTANCE;
      }
   }

   private final void fill(LinearLayout var1, String var2, Function1<? super LinearLayout, Unit> var3) {
      if (!Intrinsics.areEqual(var1.getTag(), var2)) {
         var1.setTag(var2);
         var1.removeAllViews();
         var3.invoke(var1);
      }
   }

   private final void forceAdv() {
      LinearLayout var2 = this.advBox;
      LinearLayout var1 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("advBox");
         var1 = null;
      }

      var1.setTag(null);
      this.paint();
   }

   private final boolean getBranchSwitchBlocked() {
      boolean var4 = this.branchLoading;
      boolean var3 = true;
      boolean var2 = var3;
      if (!var4) {
         var2 = var3;
         if (!this.isOnroad) {
            JSONObject var5 = this.fastStatus;
            boolean var1;
            if (var5 != null && var5.optBoolean("isOnroad")) {
               var1 = true;
            } else {
               var1 = false;
            }

            var2 = var3;
            if (!var1) {
               var2 = var3;
               if (!this.getUpdateInProgress()) {
                  if (this.busy.length() > 0) {
                     var1 = true;
                  } else {
                     var1 = false;
                  }

                  if (var1) {
                     var2 = var3;
                  } else {
                     var2 = false;
                  }
               }
            }
         }
      }

      return var2;
   }

   private final boolean getInstallVersionBlocked() {
      boolean var3 = this.getBranchSwitchBlocked();
      boolean var2 = true;
      if (!var3 && !this.branchBusy && this.branches.contains(this.targetBranch)) {
         if (Intrinsics.areEqual(this.versionMode, "earlier")) {
            if (this.versionLoading || !GxSystemKt.access$getGxSystem_SHA$p().matches(this.selectedCommit)) {
               return var2;
            }

            var var4 = this.versionChoices();
            boolean var1;
            if (var4 instanceof Collection && ((Collection)var4).isEmpty()) {
               var1 = true;
            } else {
               Iterator var5 = var4.iterator();

               while (true) {
                  if (!var5.hasNext()) {
                     var1 = true;
                     break;
                  }

                  JSONObject var6 = (JSONObject)var5.next();
                  if (Intrinsics.areEqual(GxPage.str$default(this, var6, "sha", null, 2, null), this.selectedCommit)) {
                     var1 = false;
                     break;
                  }
               }
            }

            if (var1) {
               return var2;
            }
         }

         var2 = false;
      }

      return var2;
   }

   private final List<String> getOtherBranches() {
      Iterable var3 = this.branches;
      var var2 = new ArrayList();

      for (var var3_it : var3) {

         var3 = var3_it;
         String var5 = (String)var3;
         if (!GxSystemKt.access$getGxSystem_CORE$p().contains(var5)) {
            var2.add(var3);
         }
      }

      List var6 = var2;
      var2 = new ArrayList(var6);
      boolean var1;
      if (this.currentBranch.length() > 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      if (var1 && !GxSystemKt.access$getGxSystem_CORE$p().contains(this.currentBranch) && !var2.contains(this.currentBranch)) {
         var2.add(0, this.currentBranch);
      }

      return var2;
   }

   private final boolean getStatusPollingNeeded() {
      boolean var1;
      if (this.fastStatus != null && !this.getUpdateInProgress() && !this.rebootPending) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   private final boolean getStatusRebooting() {
      JSONObject var1 = this.fastStatus;
      if (var1 != null) {
         String var2 = GxPage.str$default(this, var1, "stage", null, 2, null);
         if (var2 != null) {
            String var3 = StringsKt.trim(var2).toString();
            if (var3 != null) {
               String var5 = var3.toLowerCase(Locale.ROOT);
               Intrinsics.checkNotNullExpressionValue(var5, "toLowerCase(...)");
               return Intrinsics.areEqual(var5, "rebooting");
            }
         }
      }

      var1 = null;
      return Intrinsics.areEqual(var1, "rebooting");
   }

   private final boolean getUpdateAvailable() {
      boolean var4 = this.checkedForUpdates;
      boolean var3 = false;
      boolean var2 = var3;
      if (var4) {
         JSONObject var5 = this.fastStatus;
         boolean var1;
         if (var5 != null && var5.optBoolean("updateAvailable")) {
            var1 = true;
         } else {
            var1 = false;
         }

         var2 = var3;
         if (var1) {
            var2 = var3;
            if (!this.getUpdateInProgress()) {
               var2 = true;
            }
         }
      }

      return var2;
   }

   private final boolean getUpdateInProgress() {
      JSONObject var4 = this.fastStatus;
      boolean var3 = true;
      boolean var1;
      if (var4 != null && var4.optBoolean("running")) {
         var1 = true;
      } else {
         var1 = false;
      }

      boolean var2 = var3;
      if (!var1) {
         var2 = var3;
         if (!this.getStatusRebooting()) {
            if (this.rebootPending) {
               var2 = var3;
            } else {
               var2 = false;
            }
         }
      }

      return var2;
   }

   private final String githubRemoteUrl(String var1, String var2) {
      String var4 = StringsKt.trim(var1).toString();
      boolean var3;
      if (var4.length() == 0) {
         var3 = 1;
      } else {
         var3 = 0;
      }

      if (var3) {
         var1 = StringsKt.trim(var2).toString();
         var3 = StringsKt.indexOf$default(var1, "/commits/", 0, false, 6, null);
         if (!StringsKt.startsWith$default(var1, "https://github.com/", false, 2, null) || var3 <= 0) {
            return "";
         }

         var4 = var1.substring(0, var3);
         Intrinsics.checkNotNullExpressionValue(var4, "substring(...)");
      }

      if (StringsKt.startsWith$default(var4, "git@github.com:", false, 2, null)) {
         var1 = StringsKt.substringAfter$default(var4, ":", null, 2, null);
         var1 = "https://github.com/" + var1;
      } else if (StringsKt.startsWith$default(var4, "ssh://git@github.com/", false, 2, null)) {
         var1 = StringsKt.substringAfter$default(var4, "ssh://git@github.com/", null, 2, null);
         var1 = "https://github.com/" + var1;
      } else {
         var1 = var4;
         if (StringsKt.startsWith$default(var4, "http://github.com/", false, 2, null)) {
            var1 = StringsKt.substringAfter$default(var4, "http://github.com/", null, 2, null);
            var1 = "https://github.com/" + var1;
         }
      }

      if (!StringsKt.startsWith$default(var1, "https://github.com/", false, 2, null)) {
         return "";
      } else {
         var1 = StringsKt.trimEnd(var1, new char[]{'/'});
         if (StringsKt.endsWith$default(var1, ".git", false, 2, null)) {
            var1 = StringsKt.dropLast(var1, 4);
         }

         return var1;
      }
   }

   private final List<GxSystemPage.DayGroup> groupVersionHistory(List<? extends JSONObject> var1) {
      LinkedHashMap var3 = new LinkedHashMap();

      for (JSONObject var4 : var1) {
         Instant var7 = this.parseDate(GxPage.str$default(this, var4, "date", null, 2, null));
         String var8;
         if (var7 != null) {
            var8 = this.keyFmt.format(var7);
         } else {
            var8 = "unknown";
         }

         Map var6 = var3;
         Object var2 = var6.get(var8);
         Object var9;
         if (var2 == null) {
            Intrinsics.checkNotNull(var8);
            if (var7 != null) {
               var2 = this.dayFmt.format(var7);
            } else {
               var2 = "Unknown date";
            }

            Intrinsics.checkNotNull(var2);
            var2 = new GxSystemPage.DayGroup(var8, (String)var2, new ArrayList<>());
            var6.put(var8, var2);
            var9 = var2;
         } else {
            var9 = var2;
         }

         ((GxSystemPage.DayGroup)var9).getCommits().add(var4);
      }

      Collection var10 = var3.values();
      Intrinsics.checkNotNullExpressionValue(var10, "<get-values>(...)");
      return CollectionsKt.sortedWith(var10, new GxSystemPage$$ExternalSyntheticLambda57(new GxSystemPage$$ExternalSyntheticLambda56()));
   }

   private static final int groupVersionHistory$lambda$31(GxSystemPage.DayGroup var0, GxSystemPage.DayGroup var1) {
      int var2;
      if (Intrinsics.areEqual(var0.getKey(), "unknown")) {
         var2 = 1;
      } else if (Intrinsics.areEqual(var1.getKey(), "unknown")) {
         var2 = -1;
      } else {
         var2 = var1.getKey().compareTo(var0.getKey());
      }

      return var2;
   }

   private static final int groupVersionHistory$lambda$32(Function2 var0, Object var1, Object var2) {
      return ((Number)var0.invoke(var1, var2)).intValue();
   }

   private final void installSelectedVersion() {
      if (!this.getInstallVersionBlocked()) {
         String var11 = this.targetBranch;
         String var6;
         if (Intrinsics.areEqual(this.versionMode, "latest")) {
            var6 = "latest";
         } else {
            var6 = this.selectedCommit;
         }

         int var3 = this.versionGen;
         Iterator var8 = this.versionCommits.iterator();

         Object var5;
         JSONObject var7;
         Object var10;
         do {
            boolean var4 = var8.hasNext();
            var10 = null;
            if (!var4) {
               var5 = null;
               break;
            }

            var5 = (JSONObject)var8.next();
            var7 = var5;
         } while (!Intrinsics.areEqual(GxPage.str$default(this, var7, "sha", null, 2, null), var6));

         var5 = var5;
         String var22;
         if (Intrinsics.areEqual(var6, "latest")) {
            var22 = "Latest";
         } else {
            var5 = this.versionTitle((JSONObject)var5, Intrinsics.areEqual(var11, "StarPilot"));
            var22 = var5 + "\nCommit: " + var6;
         }

         String var23;
         if (Intrinsics.areEqual(var6, "latest")) {
            var23 = "This uses the normal branch updater, including any required OS update during startup. Your automatic-update setting is unchanged.";
         } else {
            var23 = "Automatic updates will be paused for this earlier version.";
         }

         String var9;
         if (Intrinsics.areEqual(var6, "latest")) {
            var9 = "This replaces the current software. Settings and statistics are kept. Local code changes may be overwritten; standard Rollback saves the previous committed version.";
         } else {
            var9 = "This replaces the current software. Settings and statistics are kept, and local code changes are backed up. Older versions may remove this picker; an SSH recovery copy is saved on the device.";
         }

         if (Intrinsics.areEqual(var6, "latest") && !Intrinsics.areEqual(var11, this.currentBranch)) {
            var5 = this.agnosByBranch.get(var11);
         } else {
            var5 = (JSONObject)var10;
            if (Intrinsics.areEqual(var6, "latest")) {
               JSONObject var12 = this.fastStatus;
               var5 = (JSONObject)var10;
               if (var12 != null) {
                  var5 = var12.optJSONObject("agnosUpdate");
               }
            }
         }

         boolean var2 = false;
         boolean var1 = var2;
         if (var5 != null) {
            var1 = var2;
            if (var5.optBoolean("available")) {
               var1 = true;
            }
         }

         String var21;
         if (var1) {
            label67: {
               JSONArray var16 = var5.optJSONArray("warnings");
               if (var16 != null) {
                  List var17 = this.strings(var16);
                  if (var17 != null) {
                     var10 = var17;
                     var var19 = new ArrayList();

                     for (Object var26 : var10) {
                        if (!StringsKt.isBlank((String & CharSequence)var26)) {
                           var19.add(var26);
                        }
                     }

                     var18 = var19;
                     break label67;
                  }
               }

               var18 = CollectionsKt.emptyList();
            }

            String var20 = CollectionsKt.joinToString$default(var18, "\n", null, null, 0, null, new GxSystemPage$$ExternalSyntheticLambda63(), 30, null);
            var21 = "\n\nAGNOS firmware update included:\n\n" + var20;
         } else {
            var21 = "";
         }

         this.confirm(
            "Install selected version?",
            "Branch: " + var11 + "\nVersion: " + var22 + "\n\n" + var23 + "\n\n" + var9 + var21 + "\n\nYour device will reboot when installation finishes.",
            "Install & Reboot",
            new GxSystemPage$$ExternalSyntheticLambda64(this, var11, var3, var6, var22)
         );
      }
   }

   private static final CharSequence installSelectedVersion$lambda$104(String var0) {
      Intrinsics.checkNotNullParameter(var0, "it");
      return "- " + var0;
   }

   private static final Unit installSelectedVersion$lambda$109(GxSystemPage var0, String var1, int var2, String var3, String var4) {
      if (var0.branchBusy) {
         return Unit.INSTANCE;
      } else {
         var0.branchBusy = true;
         var0.paint();
         var0.loadFastStatus(new GxSystemPage$$ExternalSyntheticLambda41(var0, var1, var2, var3, var4));
         return Unit.INSTANCE;
      }
   }

   private static final Unit installSelectedVersion$lambda$109$lambda$108(GxSystemPage var0, String var1, int var2, String var3, String var4, String var5) {
      if (var5 != null) {
         var0.toast(var5);
         var0.branchBusy = false;
         var0.paint();
         return Unit.INSTANCE;
      } else if (!var0.getBranchSwitchBlocked() && Intrinsics.areEqual(var0.targetBranch, var1) && var2 == var0.versionGen) {
         GxPage.send$default(
            var0,
            "POST",
            "/api/update/version",
            new JSONObject().put("branch", var1).put("commit", var3).put("confirmed", true),
            0L,
            new GxSystemPage$$ExternalSyntheticLambda32(var0, var4, var1),
            8,
            null
         );
         return Unit.INSTANCE;
      } else {
         var0.toast("Installation is unavailable while driving or updating, or the selection has changed.");
         var0.branchBusy = false;
         var0.paint();
         return Unit.INSTANCE;
      }
   }

   private static final Unit installSelectedVersion$lambda$109$lambda$108$lambda$107(GxSystemPage var0, String var1, String var2, String var3, JSONObject var4) {
      var0.branchBusy = false;
      if (var3 != null) {
         CharSequence var8 = var3;
         Object var7 = var8;
         if (StringsKt.isBlank(var8)) {
            var7 = "Installation failed.";
         }

         var0.toast((String)var7);
         var0.paint();
         return Unit.INSTANCE;
      } else {
         label25: {
            var0.markRebootPending();
            if (var4 != null) {
               var3 = GxPage.str$default(var0, var4, "message", null, 2, null);
               if (var3 != null) {
                  CharSequence var12 = var3;
                  CharSequence var10 = var12;
                  if (StringsKt.isBlank(var12)) {
                     var10 = null;
                  }

                  var3 = (String)var10;
                  if (var3 != null) {
                     var1 = var3;
                     break label25;
                  }
               }
            }

            var1 = StringsKt.substringBefore$default(var1, "\n", null, 2, null);
            var1 = "Installing " + var1 + " on " + var2 + "...";
         }

         var0.toast(var1);
         var0.paint();
         loadFastStatus$default(var0, null, 1, null);
         return Unit.INSTANCE;
      }
   }

   private final void installTailscale() {
      if (!this.tsBusy) {
         this.tsBusy = true;
         this.paint();
         this.toast("Install started...");
         this.send("POST", "/api/tailscale/setup", null, 300L, new GxSystemPage$$ExternalSyntheticLambda36(this));
      }
   }

   private static final Unit installTailscale$lambda$158(GxSystemPage var0, String var1, JSONObject var2) {
      if (var1 != null) {
         CharSequence var12 = var1;
         Object var5 = var12;
         if (StringsKt.isBlank(var12)) {
            var5 = "Failed to install Tailscale.";
         }

         var0.toast((String)var5);
      } else {
         Object var3;
         label35: {
            var3 = null;
            if (var2 != null) {
               var1 = GxPage.str$default(var0, var2, "message", null, 2, null);
               if (var1 != null) {
                  CharSequence var4 = var1;
                  CharSequence var7 = var4;
                  if (StringsKt.isBlank(var4)) {
                     var7 = null;
                  }

                  var1 = (String)var7;
                  if (var1 != null) {
                     break label35;
                  }
               }
            }

            var1 = "Tailscale setup started.";
         }

         var0.toast(var1);
         if (var2 != null) {
            var1 = GxPage.str$default(var0, var2, "auth_url", null, 2, null);
            if (var1 != null) {
               var1 = var1;
               if (StringsKt.isBlank(var1)) {
                  var1 = (String)var3;
               }

               var1 = var1;
               if (var1 != null) {
                  var0.openUrl(var1);
               }
            }
         }
      }

      var0.loadTailscale(new GxSystemPage$$ExternalSyntheticLambda77(var0));
      return Unit.INSTANCE;
   }

   private static final Unit installTailscale$lambda$158$lambda$157(GxSystemPage var0) {
      var0.tsBusy = false;
      var0.paint();
      return Unit.INSTANCE;
   }

   private final void kv(ViewGroup var1, String var2, String var3, boolean var4, Function0<Unit> var5) {
      LinearLayout var7 = new LinearLayout((Context)this.getAct());
      var7.setOrientation(0);
      var7.setGravity(16);
      var7.setPadding(0, this.getUi().dp(4), 0, this.getUi().dp(4));
      TextView var8 = new TextView((Context)this.getAct());
      var8.setText(var2);
      var8.setTextSize(17.0F);
      var8.setTextColor(CarUi.Companion.getDIM());
      var7.addView((View)var8, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(0, -2, 1.0F)));
      var8 = new TextView((Context)this.getAct());
      var8.setText(var3);
      var8.setTextSize(17.0F);
      CarUi.Companion var9 = CarUi.Companion;
      int var6;
      if (var5 != null) {
         var6 = var9.getACCENT();
      } else {
         var6 = var9.getTEXT();
      }

      var8.setTextColor(var6);
      Typeface var10;
      if (var4) {
         var10 = Typeface.MONOSPACE;
      } else {
         var10 = Typeface.DEFAULT_BOLD;
      }

      var8.setTypeface(var10);
      if (var5 != null) {
         var8.setPaintFlags(var8.getPaintFlags() | 8);
         var8.setPadding(this.getUi().dp(8), this.getUi().dp(8), 0, this.getUi().dp(8));
         var8.setOnClickListener(new GxSystemPage$$ExternalSyntheticLambda66(var5));
      }

      var7.addView((View)var8);
      var1.addView((View)var7);
   }

   // $VF: synthetic method
   static void kv$default(GxSystemPage var0, ViewGroup var1, String var2, String var3, boolean var4, Function0 var5, int var6, Object var7) {
      if ((var6 & 8) != 0) {
         var4 = false;
      }

      if ((var6 & 16) != 0) {
         var5 = null;
      }

      var0.kv(var1, var2, var3, var4, var5);
   }

   private static final void kv$lambda$20$lambda$19(Function0 var0, View var1) {
      var0.invoke();
   }

   private final void loadBranches() {
      this.json("GET", "/api/update/branches", null, 45L, new GxSystemPage$$ExternalSyntheticLambda67(this));
   }

   private static final Unit loadBranches$lambda$42(GxSystemPage var0, String var1, Object var2) {
      boolean var4 = var2 instanceof JSONObject;
      JSONArray var5 = null;
      if (var4) {
         var2 = var2;
      } else {
         var2 = null;
      }

      if (var2 != null) {
         var5 = var2.optJSONArray("branches");
      }

      label83: {
         var4 = true;
         if (var5 != null) {
            List var13 = var0.strings(var5);
            if (var13 != null) {
               Iterable var6 = var13;
               var var14 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var6, 10));
               Iterator var19 = var6.iterator();

               while (var19.hasNext()) {
                  var14.add(StringsKt.trim((String & CharSequence)var19.next()).toString());
               }

               List var15 = var14;
               var6 = var15;
               var var16 = new ArrayList();

               for (Object var21 : var6) {
                  boolean var3;
                  if (((String)var21).length() > 0) {
                     var3 = true;
                  } else {
                     var3 = false;
                  }

                  if (var3) {
                     var16.add(var21);
                  }
               }

               List var17 = var16;
               List var22 = CollectionsKt.distinct(var17);
               var18 = var22;
               if (var22 != null) {
                  break label83;
               }
            }
         }

         var18 = CollectionsKt.emptyList();
      }

      if (var1 == null && !var18.isEmpty()) {
         var0.branches = var18;
         GxPage var8 = var0;
         Intrinsics.checkNotNull(var2);
         var0.currentBranch = StringsKt.trim(GxPage.str$default(var8, var2, "currentBranch", null, 2, null)).toString();
         var0.branchListFallback = false;
         boolean var10;
         if (var0.targetBranch.length() == 0) {
            var10 = true;
         } else {
            var10 = false;
         }

         if (var10) {
            var0.targetBranch = var0.currentBranch;
            if (var0.targetBranch.length() > 0) {
               var10 = true;
            } else {
               var10 = false;
            }

            if (!var10 || GxSystemKt.access$getGxSystem_CORE$p().contains(var0.targetBranch)) {
               var4 = false;
            }

            var0.otherOpen = var4;
         }

         var0.isOnroad = var2.optBoolean("isOnroad");
         var0.branchLoading = false;
         var0.paint();
      } else if (var0.fastStatus == null) {
         var0.loadFastStatus(new GxSystemPage$$ExternalSyntheticLambda17(var0));
      } else {
         loadBranches$lambda$42$fallback(var0);
      }

      return Unit.INSTANCE;
   }

   private static final void loadBranches$lambda$42$fallback(GxSystemPage var0) {
      String var9;
      label47: {
         JSONObject var3 = var0.fastStatus;
         if (var3 != null) {
            var9 = GxPage.str$default(var0, var3, "branch", null, 2, null);
            if (var9 != null) {
               break label47;
            }
         }

         var9 = var0.currentBranch;
      }

      CharSequence var4 = var9;
      var9 = var4;
      if (StringsKt.isBlank(var4)) {
         var9 = var0.currentBranch;
      }

      var9 = StringsKt.trim(var9).toString();
      var0.currentBranch = var9;
      Iterable var5 = CollectionsKt.plus(CollectionsKt.listOf(var9), GxSystemKt.access$getGxSystem_CORE$p());
      var var13 = new ArrayList();
      Iterator var15 = var5.iterator();

      while (true) {
         boolean var2 = var15.hasNext();
         boolean var1 = false;
         if (!var2) {
            List var14 = var13;
            var0.branches = CollectionsKt.distinct(var14);
            var0.branchListFallback = true;
            if (var0.targetBranch.length() == 0) {
               var1 = true;
            } else {
               var1 = false;
            }

            if (var1) {
               var9 = var9;
               if (var9.length() == 0) {
                  var1 = true;
               } else {
                  var1 = false;
               }

               if (var1) {
                  var9 = "Dom";
               }

               var0.targetBranch = var9;
               var0.otherOpen = GxSystemKt.access$getGxSystem_CORE$p().contains(var0.targetBranch) ^ true;
            }

            var0.toast("Could not refresh the full branch list. Standard branches are still available.");
            var0.branchLoading = false;
            var0.paint();
            return;
         }

         Object var6 = var15.next();
         if (((String)var6).length() > 0) {
            var1 = true;
         }

         if (var1) {
            var13.add(var6);
         }
      }
   }

   private static final Unit loadBranches$lambda$42$lambda$41(GxSystemPage var0, String var1) {
      loadBranches$lambda$42$fallback(var0);
      return Unit.INSTANCE;
   }

   private final void loadFastStatus(Function1<? super String, Unit> var1) {
      this.statusInFlight = true;
      this.json("GET", "/api/update/fast/status", null, 45L, new GxSystemPage$$ExternalSyntheticLambda69(this, var1));
   }

   // $VF: synthetic method
   static void loadFastStatus$default(GxSystemPage var0, Function1 var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = null;
      }

      var0.loadFastStatus(var1);
   }

   private static final Unit loadFastStatus$lambda$45(GxSystemPage var0, Function1 var1, String var2, Object var3) {
      var0.statusInFlight = false;
      if (var3 instanceof JSONObject) {
         var3 = (JSONObject)var3;
      } else {
         var3 = null;
      }

      Object var10 = null;
      if (var2 == null && var3 != null && var3.opt("running") instanceof Boolean) {
         String var11 = StringsKt.trim(GxPage.str$default(var0, var3, "stage", null, 2, null)).toString().toLowerCase(Locale.ROOT);
         Intrinsics.checkNotNullExpressionValue(var11, "toLowerCase(...)");
         if (Intrinsics.areEqual(var11, "rebooting") && !var0.rebootPending) {
            var0.rebootPending = true;
            var0.rebootStartedAt = System.currentTimeMillis();
            var2 = var0.getHost();
            if (var2 != null) {
               GxSystemReboot.INSTANCE.getStartedAt().put(var2, var0.rebootStartedAt);
            }
         }

         long var8 = var0.rebootStartedAt;
         long var6 = 0L;
         if (var8 > 0L) {
            var6 = System.currentTimeMillis() - var0.rebootStartedAt;
         }

         boolean var4;
         if (!var0.rebootPending || var3.optBoolean("running") || Intrinsics.areEqual(var11, "rebooting") || !var0.statusUnavailable && var6 < 30000L) {
            var4 = false;
         } else {
            var4 = true;
         }

         boolean var5;
         if (var0.rebootPending && Intrinsics.areEqual(var11, "error")) {
            var5 = true;
         } else {
            var5 = false;
         }

         var0.fastStatus = var3;
         var0.statusUnavailable = false;
         var0.isOnroad = var3.optBoolean("isOnroad");
         if (var4) {
            clearRebootPending$default(var0, false, 1, null);
            var2 = (String)var10;
         } else {
            var2 = (String)var10;
            if (var5) {
               var0.clearRebootPending(false);
               var2 = (String)var10;
            }
         }
      } else {
         if (var2 == null) {
            label82: {
               if (var3 != null) {
                  var2 = GxPage.str$default(var0, var3, "error", null, 2, null);
                  if (var2 != null) {
                     CharSequence var17 = var2;
                     CharSequence var13 = var17;
                     if (StringsKt.isBlank(var17)) {
                        var13 = null;
                     }

                     var3 = (String)var13;
                     break label82;
                  }
               }

               var3 = null;
            }

            var2 = var3;
            if (var3 == null) {
               var2 = "Update status unavailable.";
            }
         }

         var0.statusUnavailable = true;
         if (var0.rebootPending) {
            var0.rebootOfflineSeen = true;
         } else {
            var0.fastStatus = null;
         }
      }

      var0.paint();
      if (var1 != null) {
         var1.invoke(var2);
      }

      return Unit.INSTANCE;
   }

   private final void loadProfile(JSONObject var1) {
      boolean var2;
      if (this.profileBusy.length() > 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      if (!var2 && !this.isOnroad && var1.optBoolean("saved") && !var1.optBoolean("invalid")) {
         String var3 = GxPage.str$default(this, var1, "slot", null, 2, null);
         CharSequence var4 = GxPage.str$default(this, var1, "label", null, 2, null);
         Object var5 = var4;
         if (StringsKt.isBlank(var4)) {
            var5 = var3;
         }

         var5 = (String)var5;
         this.confirm(
            "Load " + var5 + "?",
            "This applies every saved setting in the slot to the device.",
            "Load Settings",
            new GxSystemPage$$ExternalSyntheticLambda34(this, var3, (String)var5)
         );
      }
   }

   private static final Unit loadProfile$lambda$133(GxSystemPage var0, String var1, String var2) {
      var0.profileBusy = "load-" + var1;
      var0.paint();
      GxPage var3 = var0;
      var1 = var0.enc(var1);
      GxPage.send$default(var3, "POST", "/api/toggles/profiles/" + var1 + "/load", null, 0L, new GxSystemPage$$ExternalSyntheticLambda52(var0, var2), 12, null);
      return Unit.INSTANCE;
   }

   private static final Unit loadProfile$lambda$133$lambda$132(GxSystemPage var0, String var1, String var2, JSONObject var3) {
      var0.profileBusy = "";
      if (var2 != null) {
         CharSequence var7 = var2;
         Object var4 = var7;
         if (StringsKt.isBlank(var7)) {
            var4 = "Failed to load settings profile.";
         }

         var0.toast((String)var4);
      } else {
         label27: {
            if (var3 != null) {
               var2 = GxPage.str$default(var0, var3, "message", null, 2, null);
               if (var2 != null) {
                  CharSequence var11 = var2;
                  CharSequence var9 = var11;
                  if (StringsKt.isBlank(var11)) {
                     var9 = null;
                  }

                  var2 = (String)var9;
                  if (var2 != null) {
                     var1 = var2;
                     break label27;
                  }
               }
            }

            var1 = "Loaded " + var1 + ".";
         }

         var0.toast(var1);
         GalaxyApi var6 = var0.getApi();
         if (var6 != null) {
            GalaxyApi.refreshValues$default(var6, null, 1, null);
         }
      }

      var0.paint();
      return Unit.INSTANCE;
   }

   private final void loadProfiles() {
      this.getJson("/api/toggles/profiles", new GxSystemPage$$ExternalSyntheticLambda18(this));
   }

   private static final Unit loadProfiles$lambda$46(GxSystemPage var0, String var1, JSONObject var2) {
      List var5;
      label21: {
         if (var1 == null && var2 != null) {
            JSONArray var3 = var2.optJSONArray("slots");
            if (var3 != null) {
               List var4 = var0.objects(var3);
               var5 = var4;
               if (var4 != null) {
                  break label21;
               }
            }
         }

         var5 = CollectionsKt.emptyList();
      }

      var0.profiles = var5;
      if (var1 == null && var2 != null) {
         var0.isOnroad = var2.optBoolean("isOnroad");
      }

      var0.paint();
      return Unit.INSTANCE;
   }

   private final void loadTailscale(Function0<Unit> var1) {
      this.getJson("/api/tailscale/installed", new GxSystemPage$$ExternalSyntheticLambda55(this, var1));
   }

   // $VF: synthetic method
   static void loadTailscale$default(GxSystemPage var0, Function0 var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = null;
      }

      var0.loadTailscale(var1);
   }

   private static final Unit loadTailscale$lambda$47(GxSystemPage var0, Function0 var1, String var2, JSONObject var3) {
      boolean var6 = false;
      boolean var5 = var6;
      if (var2 == null) {
         boolean var4;
         if (var3 != null && var3.optBoolean("installed")) {
            var4 = true;
         } else {
            var4 = false;
         }

         var5 = var6;
         if (var4) {
            var5 = true;
         }
      }

      var0.tsInstalled = var5;
      var0.tsLoaded = true;
      var0.paint();
      if (var1 != null) {
         var1.invoke();
      }

      return Unit.INSTANCE;
   }

   private final void loadVersions(boolean var1) {
      int var2 = this.targetBranch.length();
      int var3 = 1;
      boolean var8;
      if (var2 == 0) {
         var8 = 1;
      } else {
         var8 = 0;
      }

      if (!var8
         && this.branches.contains(this.targetBranch)
         && !this.versionLoading
         && Intrinsics.areEqual(this.versionMode, "earlier")
         && (!var1 || this.versionHasMore)) {
         String var7 = this.targetBranch;
         int var4 = this.versionGen;
         int var5 = this.versionChoices().size();
         if (var1 && Intrinsics.areEqual(var7, "StarPilot")) {
            var8 = 4;
         } else {
            var8 = 1;
         }

         this.versionLoading = true;
         this.versionError = "";
         this.forceAdv();
         if (var1) {
            var3 = 1 + this.versionPage;
         }

         String var6;
         if (var1) {
            var6 = this.versionHead;
         } else {
            var6 = "";
         }

         loadVersions$step(this, var7, var4, var5, var8, 0, var3, var6);
      }
   }

   private static final void loadVersions$finish(int var0, GxSystemPage var1) {
      if (var0 == var1.versionGen) {
         var1.versionLoading = false;
         var1.forceAdv();
      }
   }

   private static final void loadVersions$step(GxSystemPage var0, String var1, int var2, int var3, int var4, int var5, int var6, String var7) {
      String var10 = var0.enc(var1);
      boolean var8;
      if (var7.length() > 0) {
         var8 = true;
      } else {
         var8 = false;
      }

      String var9;
      if (var8) {
         var9 = "&head=" + var7;
      } else {
         var9 = "";
      }

      var0.json(
         "GET",
         "/api/update/versions?branch=" + var10 + "&page=" + var6 + var9,
         null,
         60L,
         new GxSystemPage$$ExternalSyntheticLambda39(var2, var0, var1, var6, var7, var3, var5, var4)
      );
   }

   private static final Unit loadVersions$step$lambda$101(
      int var0, GxSystemPage var1, String var2, int var3, String var4, int var5, int var6, int var7, String var8, Object var9
   ) {
      if (var0 != var1.versionGen) {
         return Unit.INSTANCE;
      } else {
         boolean var11 = var9 instanceof JSONObject;
         Collection var12 = null;
         if (var11) {
            var9 = var9;
         } else {
            var9 = null;
         }

         if (var8 != null) {
            CharSequence var22 = var8;
            Object var15 = var22;
            if (StringsKt.isBlank(var22)) {
               var15 = "Failed to load version history. Try again.";
            }

            var1.versionError = (String)var15;
            loadVersions$finish(var0, var1);
            return Unit.INSTANCE;
         } else {
            JSONArray var23 = var12;
            if (var9 != null) {
               var23 = var9.optJSONArray("commits");
            }

            if (var9 != null
               && Intrinsics.areEqual(GxPage.str$default(var1, var9, "branch", null, 2, null), var2)
               && var9.optInt("page", -1) == var3
               && GxSystemKt.access$getGxSystem_SHA$p().matches(GxPage.str$default(var1, var9, "head", null, 2, null))) {
               boolean var10;
               if (var4.length() > 0) {
                  var10 = true;
               } else {
                  var10 = false;
               }

               if ((!var10 || Intrinsics.areEqual(GxPage.str$default(var1, var9, "head", null, 2, null), var4)) && var23 != null) {
                  Iterable var16 = var1.objects(var23);
                  var12 = new ArrayList();

                  for (Object var13 : var16) {
                     JSONObject var14 = (JSONObject)var13;
                     if (GxSystemKt.access$getGxSystem_SHA$p().matches(GxPage.str$default(var1, var14, "sha", null, 2, null))) {
                        var12.add(var13);
                     }
                  }

                  List var25 = (List)var12;
                  if (var3 > 1) {
                     Iterable var30 = var1.versionCommits;
                     var var17 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var30, 10));

                     for (JSONObject var34 : var30) {
                        var17.add(GxPage.str$default(var1, var34, "sha", null, 2, null));
                     }

                     List var18 = var17;
                     HashSet var32 = CollectionsKt.toHashSet(var18);

                     for (JSONObject var35 : var25) {
                        if (var32.add(GxPage.str$default(var1, var35, "sha", null, 2, null))) {
                           var1.versionCommits.add(var35);
                        }
                     }
                  } else {
                     var1.versionCommits.clear();
                     var1.versionCommits.addAll(var25);
                  }

                  var11 = var9.optBoolean("cached");
                  var4 = "";
                  if (var11) {
                     Instant var33 = var1.parseDate(GxPage.str$default(var1, var9, "cachedAt", null, 2, null));
                     if (var33 != null) {
                        var4 = var1.stampFmt.format(var33);
                        var4 = " from " + var4;
                     }

                     var1.versionNotice = "Showing saved history" + var4 + ". Installation still needs an online check.";
                  } else if (var3 == 1) {
                     var1.versionNotice = "";
                  }

                  var1.versionHead = GxPage.str$default(var1, var9, "head", null, 2, null);
                  var1.versionPage = var3;
                  if (var9.optBoolean("hasMore") && !var25.isEmpty()) {
                     var11 = true;
                  } else {
                     var11 = false;
                  }

                  var1.versionHasMore = var11;
                  if (var1.versionCommits.isEmpty()) {
                     var1.versionError = "No versions are available for this branch. Choose Latest or another branch.";
                  }

                  if (var1.versionHasMore && var1.versionChoices().size() <= var5 && var6 + 1 < var7) {
                     loadVersions$step(var1, var2, var0, var5, var7, var6 + 1, var3 + 1, var1.versionHead);
                  } else {
                     loadVersions$finish(var0, var1);
                  }

                  return Unit.INSTANCE;
               }
            }

            var1.versionError = "Version history changed. Choose Latest and try again.";
            loadVersions$finish(var0, var1);
            return Unit.INSTANCE;
         }
      }
   }

   private final void markRebootPending() {
      this.rebootPending = true;
      this.rebootStartedAt = System.currentTimeMillis();
      this.rebootOfflineSeen = false;
      this.reconnectedNotice = false;
      String var1 = this.getHost();
      if (var1 != null) {
         GxSystemReboot.INSTANCE.getStartedAt().put(var1, this.rebootStartedAt);
      }
   }

   private final void maybeLoadAgnos() {
      String var3 = this.targetBranch;
      int var1 = var3.length();
      boolean var2 = true;
      boolean var5;
      if (var1 == 0) {
         var5 = true;
      } else {
         var5 = false;
      }

      if (!var5 && !Intrinsics.areEqual(var3, this.currentBranch) && this.branches.contains(var3) && Intrinsics.areEqual(this.versionMode, "latest")) {
         JSONObject var4 = this.fastStatus;
         if (var4 != null && var4.optBoolean("running")) {
            var5 = var2;
         } else {
            var5 = false;
         }

         if (!var5) {
            if (!this.agnosByBranch.containsKey(var3) && !this.agnosErrByBranch.containsKey(var3) && !Intrinsics.areEqual(this.agnosBusyFor, var3)) {
               this.agnosBusyFor = var3;
               String var7 = this.enc(var3);
               this.json("GET", "/api/update/agnos_status?branch=" + var7, null, 45L, new GxSystemPage$$ExternalSyntheticLambda65(this, var3));
               return;
            }

            return;
         }
      }
   }

   private static final Unit maybeLoadAgnos$lambda$50(GxSystemPage var0, String var1, String var2, Object var3) {
      if (Intrinsics.areEqual(var0.agnosBusyFor, var1)) {
         var0.agnosBusyFor = "";
      }

      boolean var6 = var3 instanceof JSONObject;
      CharSequence var7 = null;
      if (var6) {
         var3 = var3;
      } else {
         var3 = null;
      }

      if (var3 != null) {
         var3 = var3.optJSONObject("agnosUpdate");
      } else {
         var3 = null;
      }

      if (var2 != null) {
         var0.agnosErrByBranch.put(var1, var2);
      } else {
         var0.agnosByBranch.put(var1, var3);
         if (var3 != null) {
            String var8 = GxPage.str$default(var0, var3, "error", null, 2, null);
            if (var8 != null) {
               CharSequence var12 = var8;
               if (!StringsKt.isBlank(var12)) {
                  var7 = var12;
               }

               String var11 = (String)var7;
               if (var11 != null) {
                  var0.agnosErrByBranch.put(var1, var11);
               }
            }
         }
      }

      if (var2 == null) {
         boolean var5 = false;
         boolean var4 = var5;
         if (var3 != null) {
            var4 = var5;
            if (var3.optBoolean("available")) {
               var4 = true;
            }
         }

         if (var4 && Intrinsics.areEqual(var0.targetBranch, var1)) {
            var0.toast("Selected branch includes an AGNOS firmware update.");
         }
      }

      var0.forceAdv();
      return Unit.INSTANCE;
   }

   private final void noticeCard(ViewGroup var1, String var2, String var3, int var4) {
      LinearLayout var5 = this.card(var1);
      if (var3 != null) {
         this.text(var3, (ViewGroup)var5, 21.0F, var4, true);
      }

      GxPage var6 = this;
      ViewGroup var7 = (ViewGroup)var5;
      if (var3 != null) {
         var4 = CarUi.Companion.getTEXT();
      }

      GxPage.text$default(var6, var2, var7, 15.0F, var4, false, 16, null);
   }

   // $VF: synthetic method
   static void noticeCard$default(GxSystemPage var0, ViewGroup var1, String var2, String var3, int var4, int var5, Object var6) {
      if ((var5 & 4) != 0) {
         var3 = null;
      }

      if ((var5 & 8) != 0) {
         var4 = CarUi.Companion.getWARN();
      }

      var0.noticeCard(var1, var2, var3, var4);
   }

   private final void openUrl(String var1) {
      try {
         Activity var2 = this.getAct();
         Intent var3 = new Intent("android.intent.action.VIEW", Uri.parse(var1));
         var2.startActivity(var3);
      } catch (Exception var4) {
         this.toast("No browser available");
      }
   }

   private final void paint() {
      if (this.swWrap != null) {
         TextView var6 = this.loadingNote;
         Object var5 = null;
         TextView var4 = var6;
         if (var6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("loadingNote");
            var4 = null;
         }

         boolean var3 = this.branchLoading;
         byte var2 = 0;
         byte var1;
         if (var3) {
            var1 = 0;
         } else {
            var1 = 8;
         }

         var4.setVisibility(var1);
         LinearLayout var8 = this.swWrap;
         if (var8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("swWrap");
            var8 = (LinearLayout)var5;
         }

         var1 = var2;
         if (this.branchLoading) {
            var1 = 8;
         }

         var8.setVisibility(var1);
         this.paintNotices();
         this.paintStatus();
         this.paintControls();
         this.paintAdv();
         this.paintProfiles();
         this.paintFactory();
      }
   }

   private final void paintAdv() {
      if (!this.advOpen) {
         LinearLayout var24 = this.advBox;
         if (var24 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("advBox");
            var24 = null;
         }

         this.fill(var24, "closed", new GxSystemPage$$ExternalSyntheticLambda78());
      } else {
         int var1;
         boolean var2;
         boolean var3;
         boolean var4;
         boolean var5;
         boolean var6;
         boolean var7;
         boolean var8;
         boolean var9;
         String var12;
         String var13;
         String var14;
         String var15;
         String var16;
         String var17;
         String var18;
         List var19;
         String var22;
         label28: {
            this.maybeLoadAgnos();
            var6 = this.getBranchSwitchBlocked();
            var19 = this.branches;
            var16 = this.currentBranch;
            var14 = this.targetBranch;
            var3 = this.otherOpen;
            var12 = this.versionMode;
            var17 = this.selectedCommit;
            var1 = this.versionCommits.size();
            var2 = this.versionHasMore;
            var5 = this.versionLoading;
            var13 = this.versionError;
            var15 = this.versionNotice;
            var4 = this.branchBusy;
            var9 = this.getInstallVersionBlocked();
            var7 = this.branchListFallback;
            var8 = this.pickerOpen;
            var18 = this.expanded.toString();
            JSONObject var10 = this.fastStatus;
            if (var10 != null) {
               JSONObject var21 = var10.optJSONObject("versionPin");
               if (var21 != null) {
                  var22 = var21.toString();
                  break label28;
               }
            }

            var22 = null;
         }

         JSONObject var11 = this.agnosByBranch.get(this.targetBranch);
         String var25;
         if (var11 != null) {
            var25 = var11.toString();
         } else {
            var25 = null;
         }

         Object var20 = this.agnosErrByBranch.get(this.targetBranch);
         var12 = CollectionsKt.joinToString$default(
            CollectionsKt.listOf(
               new Object[]{
                  var19,
                  var16,
                  var14,
                  var3,
                  var12,
                  var17,
                  var1,
                  var2,
                  var5,
                  var13,
                  var15,
                  var4,
                  var6,
                  var9,
                  var7,
                  var8,
                  var18,
                  var22,
                  var25,
                  var20,
                  this.agnosBusyFor
               }
            ),
            "|",
            null,
            null,
            0,
            null,
            null,
            62,
            null
         );
         LinearLayout var26 = this.advBox;
         LinearLayout var23 = var26;
         if (var26 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("advBox");
            var23 = null;
         }

         this.fill(var23, var12, new GxSystemPage$$ExternalSyntheticLambda79(this, var6));
      }
   }

   private static final Unit paintAdv$lambda$65(LinearLayout var0) {
      Intrinsics.checkNotNullParameter(var0, "it");
      return Unit.INSTANCE;
   }

   private static final Unit paintAdv$lambda$74(GxSystemPage var0, boolean var1, LinearLayout var2) {
      Intrinsics.checkNotNullParameter(var2, "b");
      var0.header("Install a Version", (ViewGroup)var2);
      var0.note(
         "Most people should stay on the latest version. Use this only to install a specific branch or historical version while troubleshooting.",
         (ViewGroup)var2
      );
      CharSequence var10 = var0.currentBranch;
      View var9 = var10;
      if (StringsKt.isBlank(var10)) {
         var9 = "Unknown";
      }

      var0.note("Installed branch: " + var9, (ViewGroup)var2);
      boolean var6 = var0.otherOpen;
      boolean var7 = false;
      byte var5;
      if (var6) {
         var5 = 2;
      } else {
         var9 = var0.targetBranch;
         if (Intrinsics.areEqual(var9, "StarPilot")) {
            var5 = 0;
         } else if (Intrinsics.areEqual(var9, "Dom")) {
            var5 = 1;
         } else {
            var5 = -1;
         }
      }

      var0.choice(
         "Target branch",
         "StarPilot — Stable releases. Recommended for most users.\nDom — Latest features and fixes under development. Updates regularly and may introduce bugs.",
         CollectionsKt.listOf(new String[]{"StarPilot — Release", "Dom — Development", "Other branches…"}),
         var5,
         (ViewGroup)var2,
         new GxSystemPage$$ExternalSyntheticLambda72(var1, var0)
      );
      var9 = var2.getChildAt(var2.getChildCount() - 1);
      float var4 = 0.4F;
      float var3;
      if (!var1 && !var0.branchBusy) {
         var3 = 1.0F;
      } else {
         var3 = 0.4F;
      }

      var9.setAlpha(var3);
      if (var0.otherOpen) {
         List var30 = var0.getOtherBranches();
         GxPage.Row var11 = var0.row("Other branches", "Additional branches from this installation's repository.", (ViewGroup)var2, 1);
         String var23;
         if (var30.contains(var0.targetBranch)) {
            var23 = var0.targetBranch;
         } else if (!var30.isEmpty()) {
            var23 = "Select another branch";
         } else {
            var23 = "No other branches available";
         }

         var9 = (View)GxPage.Row.button$default(var11, var23, false, new GxSystemPage$$ExternalSyntheticLambda73(var30, var0), 2, null);
         if (!var1 && !var0.branchBusy && !var30.isEmpty()) {
            var6 = true;
         } else {
            var6 = false;
         }

         var0.en(var9, var6);
      }

      if (var0.branchListFallback) {
         var0.note(
            "The full branch list is temporarily unavailable. Standard branches are shown; selecting a version still verifies it with the device.",
            (ViewGroup)var2
         );
      }

      boolean var16;
      if (var0.targetBranch.length() > 0) {
         var16 = 1;
      } else {
         var16 = 0;
      }

      if (var16) {
         boolean var8 = var0.branches.contains(var0.targetBranch);
         List var31 = CollectionsKt.listOf(new String[]{"Latest", "Choose earlier…"});
         var16 = Intrinsics.areEqual(var0.versionMode, "earlier");
         ViewGroup var35 = (ViewGroup)var2;
         GxSystemPage$$ExternalSyntheticLambda74 var25 = new GxSystemPage$$ExternalSyntheticLambda74(var1, var0, var8);
         var0.choice("Version", null, var31, var16, var35, var25);
         var9 = var2.getChildAt(var2.getChildCount() - 1);
         var3 = var4;
         if (!var1) {
            var3 = var4;
            if (!var0.branchBusy) {
               if (!var8) {
                  var3 = var4;
               } else {
                  var3 = 1.0F;
               }
            }
         }

         var9.setAlpha(var3);
         if (Intrinsics.areEqual(var0.versionMode, "earlier")) {
            if (!var1 && !var0.branchBusy) {
               var6 = false;
            } else {
               var6 = true;
            }

            var0.buildPicker(var2, var6);
         }

         if (Intrinsics.areEqual(var0.versionMode, "latest") && !Intrinsics.areEqual(var0.targetBranch, var0.currentBranch) && var8) {
            var0.agnosNote(var2);
         }

         if (!var8) {
            var0.note("This installed branch is no longer listed by the repository. Select an available target branch to install a version.", (ViewGroup)var2);
         }

         Pair[] var32 = new Pair[1];
         String var27;
         if (var0.branchBusy) {
            var27 = "Starting installation…";
         } else {
            var27 = "Install selected version";
         }

         var32[0] = TuplesKt.to(var27, new GxSystemPage$$ExternalSyntheticLambda75(var0));
         var0.en(var0.buttons(var32, (ViewGroup)var2, true).getChildAt(0), var0.getInstallVersionBlocked() ^ true);
      }

      JSONObject var28 = var0.fastStatus;
      JSONObject var29;
      if (var28 != null) {
         var29 = var28.optJSONObject("versionPin");
      } else {
         var29 = null;
      }

      if (var29 != null) {
         var2 = var0.card((ViewGroup)var2);
         String var36 = GxPage.str$default(var0, var29, "branch", null, 2, null);
         String var33 = var0.shortCommit(GxPage.str$default(var0, var29, "commit", null, 2, null));
         var0.text("Pinned version: " + var36 + " · " + var33, (ViewGroup)var2, 15.0F, CarUi.Companion.getTEXT(), true);
         GxPage.text$default(var0, "Automatic updates were paused at installation.", (ViewGroup)var2, 0.0F, 0, false, 28, null);
         GxPage var37 = var0;
         Pair var34 = TuplesKt.to("Return to Latest", new GxSystemPage$$ExternalSyntheticLambda76(var0));
         ViewGroup var13 = (ViewGroup)var2;
         View var14 = GxPage.buttons$default(var37, new Pair[]{var34}, var13, false, 4, null).getChildAt(0);
         var6 = var7;
         if (!var1) {
            var6 = var7;
            if (!var0.branchBusy) {
               var6 = var7;
               if (var0.branches.contains(GxPage.str$default(var0, var29, "branch", null, 2, null))) {
                  var6 = true;
               }
            }
         }

         var0.en(var14, var6);
      }

      return Unit.INSTANCE;
   }

   private static final Unit paintAdv$lambda$74$lambda$67(boolean var0, GxSystemPage var1, int var2) {
      if (!var0 && !var1.branchBusy) {
         if (var2 == 2) {
            var0 = true;
         } else {
            var0 = false;
         }

         var1.otherOpen = var0;
         if (var1.otherOpen) {
            var1.targetBranch = "";
            var1.resetVersions();
            var1.forceAdv();
         } else {
            String var3;
            if (var2 == 0) {
               var3 = "StarPilot";
            } else {
               var3 = "Dom";
            }

            var1.selectTargetBranch(var3);
         }

         return Unit.INSTANCE;
      } else {
         var1.forceAdv();
         return Unit.INSTANCE;
      }
   }

   private static final Unit paintAdv$lambda$74$lambda$70(List var0, GxSystemPage var1) {
      if (var0.isEmpty()) {
         return Unit.INSTANCE;
      } else {
         Iterable var2 = var0;
         var var4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var2, 10));

         for (String var3 : var2) {
            String var6 = var3;
            if (Intrinsics.areEqual(var3, var1.currentBranch)) {
               var6 = var3 + " (current)";
            }

            var4.add(var6);
         }

         List var7 = var4;
         var1.pick("Other branches", var7, new GxSystemPage$$ExternalSyntheticLambda44(var1, var0));
         return Unit.INSTANCE;
      }
   }

   private static final Unit paintAdv$lambda$74$lambda$70$lambda$69(GxSystemPage var0, List var1, int var2) {
      var0.selectTargetBranch((String)var1.get(var2));
      return Unit.INSTANCE;
   }

   private static final Unit paintAdv$lambda$74$lambda$71(boolean var0, GxSystemPage var1, boolean var2, int var3) {
      if (!var0 && !var1.branchBusy && var2) {
         String var4;
         if (var3 == 1) {
            var4 = "earlier";
         } else {
            var4 = "latest";
         }

         var1.resetVersions();
         var1.versionMode = var4;
         if (Intrinsics.areEqual(var4, "earlier")) {
            var1.loadVersions(false);
         } else {
            var1.forceAdv();
         }

         return Unit.INSTANCE;
      } else {
         var1.forceAdv();
         return Unit.INSTANCE;
      }
   }

   private static final Unit paintAdv$lambda$74$lambda$72(GxSystemPage var0) {
      var0.installSelectedVersion();
      return Unit.INSTANCE;
   }

   private static final Unit paintAdv$lambda$74$lambda$73(GxSystemPage var0) {
      var0.returnToLatest();
      return Unit.INSTANCE;
   }

   private final void paintControls() {
      JSONObject var8 = this.fastStatus;
      Switch var6 = this.autoSwitch;
      Object var7 = null;
      Switch var5 = var6;
      if (var6 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("autoSwitch");
         var5 = null;
      }

      boolean var4 = true;
      byte var2 = 0;
      boolean var3;
      if (var8 != null && var8.optBoolean("automaticUpdates")) {
         var3 = true;
      } else {
         var3 = false;
      }

      var5.setChecked(var3);
      var6 = this.autoSwitch;
      var5 = var6;
      if (var6 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("autoSwitch");
         var5 = null;
      }

      View var14 = (View)var5;
      if (var8 != null && !this.isOnroad && !this.autoBusy && !this.getUpdateInProgress()) {
         var3 = true;
      } else {
         var3 = false;
      }

      this.en(var14, var3);
      GxPage.Row var24 = this.fastRow;
      GxPage.Row var15 = var24;
      if (var24 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("fastRow");
         var15 = null;
      }

      String var25;
      if (this.getUpdateAvailable()) {
         var25 = "An update is available. Install it with Fast Update on StarView's Software page.";
      } else {
         var25 = "Check for and install the latest commit on this branch. Fast update lives on StarView's Software page.";
      }

      var15.setDesc(var25);
      boolean var1;
      if (this.busy.length() == 0) {
         var1 = 1;
      } else {
         var1 = 0;
      }

      if (var1 && !this.isOnroad && !this.getUpdateInProgress()) {
         var3 = var4;
      } else {
         var3 = false;
      }

      TextView var26 = this.checkBtn;
      TextView var16 = var26;
      if (var26 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("checkBtn");
         var16 = null;
      }

      String var27;
      if (Intrinsics.areEqual(this.busy, "check")) {
         var27 = "Checking...";
      } else {
         var27 = "Check";
      }

      var16.setText(var27);
      TextView var28 = this.recoverBtn;
      TextView var17 = var28;
      if (var28 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("recoverBtn");
         var17 = null;
      }

      var4 = Intrinsics.areEqual(this.busy, "recover");
      String var35 = "Working...";
      CharSequence var29;
      if (var4) {
         var29 = "Working...";
      } else {
         var29 = "Recover";
      }

      var17.setText(var29);
      TextView var30 = this.rollbackBtn;
      TextView var18 = var30;
      if (var30 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("rollbackBtn");
         var18 = null;
      }

      String var31;
      if (Intrinsics.areEqual(this.busy, "rollback")) {
         var31 = var35;
      } else {
         var31 = "Rollback";
      }

      var18.setText(var31);
      TextView var32 = this.checkBtn;
      TextView var19 = var32;
      if (var32 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("checkBtn");
         var19 = null;
      }

      this.en((View)var19, var3);
      TextView var33 = this.recoverBtn;
      TextView var20 = var33;
      if (var33 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("recoverBtn");
         var20 = null;
      }

      this.en((View)var20, var3);
      TextView var34 = this.rollbackBtn;
      TextView var21 = var34;
      if (var34 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("rollbackBtn");
         var21 = null;
      }

      this.en((View)var21, var3);
      TextView var22 = this.upToDateNote;
      if (var22 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("upToDateNote");
         var22 = (TextView)var7;
      }

      if (this.checkedForUpdates && !this.getUpdateAvailable() && !this.getUpdateInProgress()) {
         var1 = var2;
      } else {
         var1 = 8;
      }

      var22.setVisibility(var1);
   }

   private final void paintFactory() {
      JSONObject var4 = this.fastStatus;
      boolean var1;
      if (var4 != null && Intrinsics.areEqual(StringsKt.trim(GxPage.str$default(this, var4, "lastMode", null, 2, null)).toString(), "factory-reset")) {
         var1 = true;
      } else {
         var1 = false;
      }

      LinearLayout var3 = this.factoryBox;
      LinearLayout var2 = var3;
      if (var3 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("factoryBox");
         var2 = null;
      }

      String var5;
      if (!var1) {
         var5 = "none";
      } else {
         var5 = var4.toString();
         Intrinsics.checkNotNullExpressionValue(var5, "toString(...)");
      }

      this.fill(var2, var5, new GxSystemPage$$ExternalSyntheticLambda45(var1, var4, this));
   }

   private static final Unit paintFactory$lambda$92(boolean var0, JSONObject var1, GxSystemPage var2, LinearLayout var3) {
      Intrinsics.checkNotNullParameter(var3, "b");
      if (var0 && var1 != null) {
         var2.header("Factory Reset Status", (ViewGroup)var3);
         LinearLayout var8 = var2.card((ViewGroup)var3);
         boolean var5 = false;
         int var6 = var1.optInt("progressTotalSteps", 0);
         int var4 = var6;
         if (var6 == 0) {
            var4 = 5;
         }

         String var9 = GxPage.str$default(var2, var1, "progressLabel", null, 2, null);
         String var11;
         if (var1.optBoolean("running")) {
            var11 = "Running";
         } else {
            CharSequence var7 = GxPage.str$default(var2, var1, "stage", null, 2, null);
            Object var12 = var7;
            if (StringsKt.isBlank(var7)) {
               var12 = "idle";
            }

            var11 = (String)var12;
         }

         var6 = var1.optInt("progressStep", 0);
         if (var9.length() > 0) {
            var5 = true;
         }

         String var16;
         if (var5) {
            var16 = " · " + var9;
         } else {
            var16 = "";
         }

         var2.text(var11 + " · " + var6 + "/" + var4 + var16, (ViewGroup)var8, 15.0F, CarUi.Companion.getTEXT(), true);
         ProgressBar var13 = var2.progress((ViewGroup)var8);
         var2.setProgress(var13, var2.pct(var1.opt("progressPercent")));
         if (Intrinsics.areEqual(GxPage.str$default(var2, var1, "stage", null, 2, null), "error")) {
            var13.setProgressTintList(ColorStateList.valueOf(GxSystemKt.access$getGxSystem_DANGER$p()));
         }

         if (!StringsKt.isBlank(GxPage.str$default(var2, var1, "message", null, 2, null))) {
            GxPage.text$default(
               var2, GxPage.str$default(var2, var1, "message", null, 2, null), (ViewGroup)var8, 15.0F, CarUi.Companion.getTEXT(), false, 16, null
            );
         }

         if (!StringsKt.isBlank(GxPage.str$default(var2, var1, "progressDetail", null, 2, null))) {
            GxPage.text$default(var2, GxPage.str$default(var2, var1, "progressDetail", null, 2, null), (ViewGroup)var8, 0.0F, 0, false, 28, null);
         }

         if (!StringsKt.isBlank(GxPage.str$default(var2, var1, "lastError", null, 2, null))) {
            GxPage var14 = var2;
            String var10 = GxPage.str$default(var2, var1, "lastError", null, 2, null);
            GxPage.text$default(var14, "Last Error: " + var10, (ViewGroup)var8, 15.0F, GxSystemKt.access$getGxSystem_DANGER$p(), false, 16, null);
         }

         return Unit.INSTANCE;
      } else {
         return Unit.INSTANCE;
      }
   }

   private final void paintNotices() {
      LinearLayout var6 = this.noticeBox;
      LinearLayout var5 = var6;
      if (var6 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("noticeBox");
         var5 = null;
      }

      boolean var3 = this.isOnroad;
      boolean var1 = this.rebootPending;
      boolean var4 = this.statusUnavailable;
      boolean var2 = this.reconnectedNotice;
      this.fill(var5, var3 + "|" + var1 + "|" + var4 + "|" + var2, new GxSystemPage$$ExternalSyntheticLambda40(this));
   }

   private static final Unit paintNotices$lambda$51(GxSystemPage var0, LinearLayout var1) {
      Intrinsics.checkNotNullParameter(var1, "b");
      if (var0.isOnroad) {
         noticeCard$default(var0, (ViewGroup)var1, "Updates and branch switching are only available while offroad.", null, 0, 12, null);
      }

      if (var0.rebootPending) {
         ViewGroup var2 = (ViewGroup)var1;
         String var3;
         if (var0.statusUnavailable) {
            var3 = "The device is temporarily offline. StarView will keep checking until it reconnects.";
         } else {
            var3 = "The update is complete. Waiting for the device to reconnect…";
         }

         var0.noticeCard(var2, var3, "Device rebooting", CarUi.Companion.getACCENT());
      } else if (var0.reconnectedNotice) {
         var0.noticeCard((ViewGroup)var1, "Galaxy is connected again and the update status is current.", "Device reconnected", CarUi.Companion.getACCENT());
      }

      return Unit.INSTANCE;
   }

   private final void paintProfiles() {
      LinearLayout var4 = this.profileNotice;
      Object var3 = null;
      LinearLayout var2 = var4;
      if (var4 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("profileNotice");
         var2 = null;
      }

      this.fill(var2, String.valueOf(this.isOnroad), new GxSystemPage$$ExternalSyntheticLambda59(this));
      var2 = this.profilesBox;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("profilesBox");
         var2 = (LinearLayout)var3;
      }

      var3 = CollectionsKt.joinToString$default(this.profiles, null, null, null, 0, null, new GxSystemPage$$ExternalSyntheticLambda60(), 31, null);
      String var7 = this.profileBusy;
      boolean var1 = this.isOnroad;
      this.fill(var2, var3 + "|" + var7 + "|" + var1, new GxSystemPage$$ExternalSyntheticLambda61(this));
   }

   private static final Unit paintProfiles$lambda$84(GxSystemPage var0, LinearLayout var1) {
      Intrinsics.checkNotNullParameter(var1, "b");
      if (var0.isOnroad) {
         noticeCard$default(var0, (ViewGroup)var1, "Park the vehicle to save or load a profile.", null, 0, 12, null);
      }

      return Unit.INSTANCE;
   }

   private static final CharSequence paintProfiles$lambda$85(JSONObject var0) {
      Intrinsics.checkNotNullParameter(var0, "it");
      String var1 = var0.toString();
      Intrinsics.checkNotNullExpressionValue(var1, "toString(...)");
      return var1;
   }

   private static final Unit paintProfiles$lambda$89(GxSystemPage var0, LinearLayout var1) {
      Intrinsics.checkNotNullParameter(var1, "b");

      for (JSONObject var11 : var0.profiles) {
         String var8 = GxPage.str$default(var0, var11, "slot", null, 2, null);
         CharSequence var9 = GxPage.str$default(var0, var11, "label", null, 2, null);
         Object var7 = var9;
         if (StringsKt.isBlank(var9)) {
            var7 = var8;
         }

         String var22 = (String)var7;
         boolean var5 = var11.optBoolean("saved");
         boolean var6 = var11.optBoolean("invalid");
         GxPage var12 = var0;
         if (var6) {
            var7 = "Damaged";
         } else if (var5) {
            int var2 = var11.optInt("settingsCount");
            var7 = var2 + " settings";
         } else {
            var7 = "Empty";
         }

         GxPage.Row var23 = GxPage.row$default(var12, var22, (String)var7, (ViewGroup)var1, 0, 8, null);
         if (Intrinsics.areEqual(var0.profileBusy, "save-" + var8)) {
            var7 = "Saving...";
         } else if (var5) {
            var7 = "Overwrite";
         } else {
            var7 = "Save Current";
         }

         var7 = (View)GxPage.Row.button$default(var23, (String)var7, false, new GxSystemPage$$ExternalSyntheticLambda11(var0, var11), 2, null);
         int var13 = var0.profileBusy.length();
         boolean var4 = false;
         boolean var14;
         if (var13 == 0) {
            var14 = true;
         } else {
            var14 = false;
         }

         boolean var3;
         if (var14 && !var0.isOnroad) {
            var3 = true;
         } else {
            var3 = false;
         }

         var0.en((View)var7, var3);
         if (Intrinsics.areEqual(var0.profileBusy, "load-" + var8)) {
            var7 = "Loading...";
         } else {
            var7 = "Load";
         }

         var7 = (View)var23.button((String)var7, true, new GxSystemPage$$ExternalSyntheticLambda22(var0, var11));
         if (var0.profileBusy.length() == 0) {
            var14 = true;
         } else {
            var14 = false;
         }

         var3 = var4;
         if (var14) {
            var3 = var4;
            if (!var0.isOnroad) {
               var3 = var4;
               if (var5) {
                  var3 = var4;
                  if (!var6) {
                     var3 = true;
                  }
               }
            }
         }

         var0.en((View)var7, var3);
      }

      return Unit.INSTANCE;
   }

   private static final Unit paintProfiles$lambda$89$lambda$87(GxSystemPage var0, JSONObject var1) {
      var0.saveProfile(var1);
      return Unit.INSTANCE;
   }

   private static final Unit paintProfiles$lambda$89$lambda$88(GxSystemPage var0, JSONObject var1) {
      var0.loadProfile(var1);
      return Unit.INSTANCE;
   }

   private final void paintStatus() {
      JSONObject var7 = this.fastStatus;
      LinearLayout var6 = this.statusBox;
      String var5 = null;
      LinearLayout var4 = var6;
      if (var6 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("statusBox");
         var4 = null;
      }

      if (var7 != null) {
         var5 = var7.toString();
      }

      boolean var1 = this.getUpdateInProgress();
      boolean var3 = this.checkedForUpdates;
      boolean var2 = this.statusUnavailable;
      String var8 = this.currentBranch;
      this.fill(var4, var5 + "|" + var1 + "|" + var3 + "|" + var2 + "|" + var8, new GxSystemPage$$ExternalSyntheticLambda71(var7, this));
   }

   private static final Unit paintStatus$lambda$64(JSONObject var0, GxSystemPage var1, LinearLayout var2) {
      Intrinsics.checkNotNullParameter(var2, "b");
      if (var0 == null) {
         return Unit.INSTANCE;
      } else {
         LinearLayout var10 = var1.card((ViewGroup)var2);
         LinearLayout var9 = new LinearLayout((Context)var1.getAct());
         var9.setOrientation(0);
         var9.setGravity(16);
         TextView var23 = new TextView((Context)var1.getAct());
         var23.setText("Update Status");
         var23.setTextSize(21.0F);
         var23.setTextColor(CarUi.Companion.getTEXT());
         var9.addView((View)var23, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(0, -2, 1.0F)));
         Pair var25;
         if (var1.getUpdateInProgress()) {
            String var24;
            if (!var1.rebootPending && !var1.getStatusRebooting()) {
               long var7 = Math.round(var1.pct(var0.opt("progressPercent")));
               var24 = var7 + "%";
            } else {
               var24 = "Reconnecting…";
            }

            var25 = TuplesKt.to(var24, CarUi.Companion.getACCENT());
         } else if (var1.getUpdateAvailable()) {
            var25 = TuplesKt.to("Update available", CarUi.Companion.getWARN());
         } else if (var1.checkedForUpdates) {
            var25 = TuplesKt.to("Up to date", CarUi.Companion.getDIM());
         } else {
            var25 = TuplesKt.to("Not checked", CarUi.Companion.getDIM());
         }

         String var11 = (String)var25.component1();
         int var5 = ((Number)var25.component2()).intValue();
         TextView var26 = new TextView((Context)var1.getAct());
         var26.setText(var11);
         var26.setTextSize(16.0F);
         var26.setTypeface(Typeface.DEFAULT_BOLD);
         var26.setTextColor(var5);
         var26.setBackground((Drawable)var1.getUi().rounded(CarUi.Companion.getCARD_ON(), 12, var5));
         var26.setPadding(var1.getUi().dp(14), var1.getUi().dp(6), var1.getUi().dp(14), var1.getUi().dp(6));
         var9.addView((View)var26);
         var10.addView((View)var9);
         ViewGroup var57 = (ViewGroup)var10;
         CharSequence var45 = GxPage.str$default(var1, var0, "branch", null, 2, null);
         ProgressBar var27 = var45;
         if (StringsKt.isBlank(var45)) {
            var27 = var1.currentBranch;
         }

         CharSequence var46 = (CharSequence)var27;
         var27 = var46;
         if (StringsKt.isBlank(var46)) {
            var27 = "—";
         }

         kv$default(var1, var57, "Installed branch", (String)var27, false, null, 24, null);
         if (var1.getUpdateInProgress() && !var1.rebootPending) {
            var27 = (ViewGroup)var10;
            String var47 = GxPage.str$default(var1, var0, "stage", null, 2, null);
            var11 = GxPage.str$default(var1, var0, "progressLabel", null, 2, null);
            kv$default(var1, (ViewGroup)var27, "Stage", var47 + " · " + var11, false, null, 24, null);
         }

         kv$default(var1, (ViewGroup)var10, "Local", var1.shortCommit(GxPage.str$default(var1, var0, "localCommit", null, 2, null)), true, null, 16, null);
         var27 = var1.remoteCommitUrl();
         ViewGroup var48 = (ViewGroup)var10;
         var11 = var1.shortCommit(GxPage.str$default(var1, var0, "remoteCommit", null, 2, null));
         boolean var39;
         if (((CharSequence)var27).length() > 0) {
            var39 = 1;
         } else {
            var39 = 0;
         }

         if (var39) {
            var27 = new GxSystemPage$$ExternalSyntheticLambda1(var1, (String)var27);
         } else {
            var27 = null;
         }

         var1.kv(var48, "Remote", var11, true, (Function0<Unit>)var27);
         String var49 = var1.recentCommitsUrl();
         if (var49.length() > 0) {
            var39 = (boolean)1;
         } else {
            var39 = (boolean)0;
         }

         if (var39) {
            var27 = var1;
            Pair var50 = TuplesKt.to("Show recent commits on GitHub", new GxSystemPage$$ExternalSyntheticLambda2(var1, var49));
            ViewGroup var60 = (ViewGroup)var10;
            GxPage.buttons$default((GxPage)var27, new Pair[]{var50}, var60, false, 4, null);
         }

         if (var1.getUpdateInProgress() && !var1.rebootPending) {
            var27 = var1.progress((ViewGroup)var10);
            double var3 = var1.pct(var0.opt("progressPercent"));
            var1.setProgress(var27, var3);
            if (Intrinsics.areEqual(GxPage.str$default(var1, var0, "stage", null, 2, null), "error")) {
               var27.setProgressTintList(ColorStateList.valueOf(GxSystemKt.access$getGxSystem_DANGER$p()));
            }

            int var6 = var0.optInt("progressTotalSteps", 0);
            var39 = var6;
            if (var6 == 0) {
               var39 = 5;
            }

            var6 = var0.optInt("progressStep", 0);
            CharSequence var51 = GxPage.str$default(var1, var0, "progressLabel", null, 2, null);
            var27 = var51;
            if (StringsKt.isBlank(var51)) {
               var27 = GxPage.str$default(var1, var0, "stage", null, 2, null);
            }

            CharSequence var52 = (CharSequence)var27;
            var27 = var52;
            if (StringsKt.isBlank(var52)) {
               var27 = "Updating";
            }

            long var44 = Math.round(var3);
            var1.text("Step " + var6 + "/" + var39 + ": " + var27 + "   " + var44 + "%", (ViewGroup)var10, 15.0F, CarUi.Companion.getTEXT(), true);
            if (!StringsKt.isBlank(GxPage.str$default(var1, var0, "progressDetail", null, 2, null))) {
               GxPage.text$default(var1, GxPage.str$default(var1, var0, "progressDetail", null, 2, null), (ViewGroup)var10, 0.0F, 0, false, 28, null);
            }
         }

         if (!StringsKt.isBlank(GxPage.str$default(var1, var0, "message", null, 2, null))) {
            GxPage.text$default(var1, GxPage.str$default(var1, var0, "message", null, 2, null), (ViewGroup)var10, 0.0F, 0, false, 28, null);
         }

         if (var1.statusUnavailable) {
            GxPage.text$default(
               var1, "Waiting for the device to reconnect. The last update status is being kept on screen.", (ViewGroup)var10, 0.0F, 0, false, 28, null
            );
         }

         if (!StringsKt.isBlank(GxPage.str$default(var1, var0, "warning", null, 2, null)) && (var1.getUpdateInProgress() || var0.optBoolean("updateAvailable"))
            )
          {
            GxPage.text$default(
               var1,
               GxPage.str$default(var1, var0, "warning", null, 2, null),
               (ViewGroup)var10,
               15.0F,
               GxSystemKt.access$getGxSystem_DANGER$p(),
               false,
               16,
               null
            );
         }

         JSONObject var36 = var0.optJSONObject("agnosUpdate");
         boolean var42;
         if (var36 != null && var36.optBoolean("available")) {
            var42 = true;
         } else {
            var42 = false;
         }

         if (var42) {
            label134: {
               JSONArray var12 = var36.optJSONArray("warnings");
               if (var12 != null) {
                  List var13 = var1.strings(var12);
                  if (var13 != null) {
                     Iterable var53 = var13;
                     var var15 = new ArrayList();

                     for (Object var61 : var53) {
                        if (!StringsKt.isBlank((String & CharSequence)var61)) {
                           var15.add(var61);
                        }
                     }

                     var14 = var15;
                     break label134;
                  }
               }

               var14 = CollectionsKt.emptyList();
            }

            for (String var55 : var14) {
               GxPage.text$default(var1, "⚠ " + var55, (ViewGroup)var10, 15.0F, GxSystemKt.access$getGxSystem_DANGER$p(), false, 16, null);
            }

            label117: {
               JSONArray var17 = var36.optJSONArray("changedPartitions");
               if (var17 != null) {
                  List var18 = var1.strings(var17);
                  if (var18 != null) {
                     Iterable var37 = var18;
                     var var20 = new ArrayList();

                     for (Object var38 : var37) {
                        if (!StringsKt.isBlank((String & CharSequence)var38)) {
                           var20.add(var38);
                        }
                     }

                     var19 = var20;
                     break label117;
                  }
               }

               var19 = CollectionsKt.emptyList();
            }

            if (!var19.isEmpty()) {
               GxPage var22 = var1;
               String var21 = CollectionsKt.joinToString$default(var19, ", ", null, null, 0, null, null, 62, null);
               GxPage.text$default(var22, "Changed AGNOS partitions: " + var21, (ViewGroup)var10, 0.0F, 0, false, 28, null);
            }
         }

         return Unit.INSTANCE;
      }
   }

   private static final Unit paintStatus$lambda$64$lambda$57(GxSystemPage var0, String var1) {
      var0.openUrl(var1);
      return Unit.INSTANCE;
   }

   private static final Unit paintStatus$lambda$64$lambda$58(GxSystemPage var0, String var1) {
      var0.openUrl(var1);
      return Unit.INSTANCE;
   }

   private final void paintTailscale() {
      LinearLayout var5 = this.tsBox;
      LinearLayout var4 = var5;
      if (var5 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("tsBox");
         var4 = null;
      }

      boolean var2 = this.tsLoaded;
      boolean var1 = this.tsInstalled;
      boolean var3 = this.tsBusy;
      this.fill(var4, var2 + "|" + var1 + "|" + var3, new GxSystemPage$$ExternalSyntheticLambda25(this));
   }

   private static final Unit paintTailscale$lambda$97(GxSystemPage var0, LinearLayout var1) {
      Intrinsics.checkNotNullParameter(var1, "b");
      if (!var0.tsLoaded) {
         var0.noticeCard((ViewGroup)var1, "Checking Tailscale install status...", null, CarUi.Companion.getDIM());
         return Unit.INSTANCE;
      } else {
         noticeCard$default(var0, (ViewGroup)var1, "Not recommended. Using Galaxy Tunnel is the preferred remote connection method.", null, 0, 12, null);
         if (!var0.tsInstalled) {
            GxPage.Row var3 = GxPage.row$default(
               var0, "Install Tailscale", "Installing opens the Tailscale login page to authenticate this device.", (ViewGroup)var1, 0, 8, null
            );
            String var2;
            if (var0.tsBusy) {
               var2 = "Installing...";
            } else {
               var2 = "Install";
            }

            var0.en((View)var3.button(var2, true, new GxSystemPage$$ExternalSyntheticLambda49(var0)), true ^ var0.tsBusy);
         } else {
            GxPage.Row var6 = GxPage.row$default(
               var0,
               "Uninstall Tailscale",
               "Disconnects the device from your tailnet and removes the Tailscale binaries and state.",
               (ViewGroup)var1,
               0,
               8,
               null
            );
            String var4;
            if (var0.tsBusy) {
               var4 = "Uninstalling...";
            } else {
               var4 = "Uninstall";
            }

            TextView var5 = GxPage.Row.button$default(var6, var4, false, new GxSystemPage$$ExternalSyntheticLambda50(var0), 2, null);
            var5.setTextColor(GxSystemKt.access$getGxSystem_DANGER$p());
            var0.en((View)var5, true ^ var0.tsBusy);
         }

         GxPage.Row.button$default(
            GxPage.row$default(var0, "Download for your other devices", "tailscale.com/download", (ViewGroup)var1, 0, 8, null),
            "Open",
            false,
            new GxSystemPage$$ExternalSyntheticLambda51(var0),
            2,
            null
         );
         return Unit.INSTANCE;
      }
   }

   private static final Unit paintTailscale$lambda$97$lambda$93(GxSystemPage var0) {
      var0.installTailscale();
      return Unit.INSTANCE;
   }

   private static final Unit paintTailscale$lambda$97$lambda$94(GxSystemPage var0) {
      var0.uninstallTailscale();
      return Unit.INSTANCE;
   }

   private static final Unit paintTailscale$lambda$97$lambda$96(GxSystemPage var0) {
      var0.openUrl("https://tailscale.com/download");
      return Unit.INSTANCE;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private final Instant parseDate(String var1) {
      CharSequence var3 = var1;
      boolean var2;
      if (var3 != null && !StringsKt.isBlank(var3)) {
         var2 = false;
      } else {
         var2 = true;
      }

      Object var5 = null;
      if (var2) {
         return null;
      } else {
         label85:
         try {
            Result.Companion var15 = Result.Companion;
            GxSystemPage var16 = this;
            var3 = (CharSequence)KtCompat.resultOf(OffsetDateTime.parse(var1).toInstant());
         } catch (Throwable var11) {
            Result.Companion var13 = Result.Companion;
            var3 = (CharSequence)KtCompat.resultOf(ResultKt.createFailure(var11));
            break label85;
         }

         Object var4 = var3;
         if (KtCompat.isFailure(var3)) {
            var4 = null;
         }

         var4 = (Instant)var4;
         Instant var17 = (Instant)var4;
         if (var4 == null) {
            label79:
            try {
               Result.Companion var19 = Result.Companion;
               GxSystemPage var20 = this;
               var12 = KtCompat.resultOf(Instant.parse(var1));
            } catch (Throwable var10) {
               Result.Companion var18 = Result.Companion;
               var12 = KtCompat.resultOf(ResultKt.createFailure(var10));
               break label79;
            }

            if (KtCompat.isFailure(var12)) {
               var12 = var5;
            }

            var17 = (Instant)var12;
         }

         return var17;
      }
   }

   private final double pct(Object var1) {
      boolean var6 = var1 instanceof Number;
      Object var8 = null;
      Number var7;
      if (var6) {
         var7 = (Number)var1;
      } else {
         var7 = null;
      }

      double var4 = 0.0;
      double var2;
      if (var7 != null) {
         var2 = var7.doubleValue();
      } else {
         Double var11 = (Double)var8;
         if (var1 != null) {
            var1 = var1.toString();
            var11 = (Double)var8;
            if (var1 != null) {
               var11 = StringsKt.toDoubleOrNull(var1);
            }
         }

         if (var11 != null) {
            var2 = var11;
         } else {
            var2 = 0.0;
         }
      }

      if (Double.isNaN(var2)) {
         var2 = var4;
      } else {
         var2 = RangesKt.coerceIn(var2, 0.0, 100.0);
      }

      return var2;
   }

   private final String recentCommitsUrl() {
      JSONObject var3 = this.fastStatus;
      if (var3 == null) {
         return "";
      } else {
         String var4 = StringsKt.trim(GxPage.str$default(this, var3, "commitsUrl", null, 2, null)).toString();
         boolean var2 = false;
         if (StringsKt.startsWith$default(var4, "https://github.com/", false, 2, null) && StringsKt.contains$default(var4, "/commits/", false, 2, null)) {
            return var4;
         } else {
            String var5 = this.githubRemoteUrl(GxPage.str$default(this, var3, "originRemote", null, 2, null), var4);
            CharSequence var10 = GxPage.str$default(this, var3, "branch", null, 2, null);
            var3 = var10;
            if (StringsKt.isBlank(var10)) {
               var3 = this.currentBranch;
            }

            String var8 = StringsKt.trim((String & CharSequence)var3).toString();
            boolean var1;
            if (var5.length() == 0) {
               var1 = true;
            } else {
               var1 = false;
            }

            if (!var1) {
               var1 = var2;
               if (var8.length() == 0) {
                  var1 = true;
               }

               if (!var1) {
                  String var9 = this.enc(var8);
                  return var5 + "/commits/" + var9 + "/";
               }
            }

            return "";
         }
      }
   }

   private final List<JSONObject> releaseVersions(List<? extends JSONObject> var1) {
      LinkedHashMap var2 = new LinkedHashMap();

      for (JSONObject var3 : var1) {
         String var4 = GxPage.str$default(this, var3, "version", null, 2, null);
         if (!var2.containsKey(var4)) {
            var2.put(var4, var3);
         }
      }

      Collection var6 = var2.values();
      Intrinsics.checkNotNullExpressionValue(var6, "<get-values>(...)");
      return CollectionsKt.sortedWith(var6, new GxSystemPage$$ExternalSyntheticLambda20(new GxSystemPage$$ExternalSyntheticLambda19(this)));
   }

   private static final int releaseVersions$lambda$28(GxSystemPage var0, JSONObject var1, JSONObject var2) {
      GxPage var6 = var0;
      Intrinsics.checkNotNull(var1);
      String var9 = GxPage.str$default(var6, var1, "version", null, 2, null);
      GxPage var7 = var0;
      Intrinsics.checkNotNull(var2);
      String var21 = GxPage.str$default(var7, var2, "version", null, 2, null);
      int var3 = var9.length();
      byte var4 = 1;
      Integer var8 = 0;
      boolean var13;
      if (var3 == 0) {
         var13 = 1;
      } else {
         var13 = 0;
      }

      if (var13) {
         boolean var14;
         if (var21.length() > 0) {
            var14 = true;
         } else {
            var14 = false;
         }

         if (var14) {
            var13 = var4;
         } else {
            var13 = 0;
         }
      } else {
         boolean var16;
         if (var21.length() == 0) {
            var16 = 1;
         } else {
            var16 = 0;
         }

         if (var16) {
            var13 = -1;
         } else {
            List var12 = releaseVersions$parts(var9);
            List var22 = releaseVersions$parts(var21);
            var16 = 0;

            while (true) {
               if (var16 >= 3) {
                  var13 = 0;
                  break;
               }

               boolean var18;
               if (var16 >= 0 && var16 < var22.size()) {
                  var18 = 1;
               } else {
                  var18 = 0;
               }

               Object var10;
               if (var18) {
                  var10 = var22.get(var16);
               } else {
                  var10 = var8;
               }

               int var5 = ((Number)var10).intValue();
               if (var16 >= 0 && var16 < var12.size()) {
                  var18 = (boolean)1;
               } else {
                  var18 = (boolean)0;
               }

               if (var18) {
                  var10 = var12.get(var16);
               } else {
                  var10 = var8;
               }

               var18 = var5 - ((Number)var10).intValue();
               if (var18 != 0) {
                  var13 = var18;
                  break;
               }

               var16++;
            }
         }
      }

      return var13;
   }

   private static final int releaseVersions$lambda$29(Function2 var0, Object var1, Object var2) {
      return ((Number)var0.invoke(var1, var2)).intValue();
   }

   private static final List<Integer> releaseVersions$parts(String var0) {
      Iterable var2 = StringsKt.split$default(var0, new String[]{"."}, false, 0, 6, null);
      var var4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var2, 10));
      Iterator var3 = var2.iterator();

      while (var3.hasNext()) {
         Integer var5 = StringsKt.toIntOrNull((String)var3.next());
         int var1;
         if (var5 != null) {
            var1 = var5;
         } else {
            var1 = 0;
         }

         var4.add(var1);
      }

      return var4;
   }

   private final String remoteCommitUrl() {
      JSONObject var4 = this.fastStatus;
      String var2 = "";
      if (var4 == null) {
         return "";
      } else {
         String var3 = StringsKt.trim(GxPage.str$default(this, var4, "remoteCommit", null, 2, null)).toString();
         if (!new Regex("^[a-fA-F0-9]{7,40}$").matches(var3)) {
            return "";
         } else {
            String var5 = this.githubRemoteUrl(
               GxPage.str$default(this, var4, "originRemote", null, 2, null), GxPage.str$default(this, var4, "commitsUrl", null, 2, null)
            );
            boolean var1;
            if (var5.length() > 0) {
               var1 = true;
            } else {
               var1 = false;
            }

            if (var1) {
               var2 = var5 + "/commit/" + var3;
            }

            return var2;
         }
      }
   }

   private final void resetDefault() {
      this.confirm(
         "Reset toggles to default?",
         "This resets all toggles to their default values and reboots.",
         "Reset",
         new GxSystemPage$$ExternalSyntheticLambda33(this)
      );
   }

   private static final Unit resetDefault$lambda$148(GxSystemPage var0) {
      GxPage.send$default(var0, "POST", "/api/toggles/reset_default", null, 0L, new GxSystemPage$$ExternalSyntheticLambda43(var0), 12, null);
      return Unit.INSTANCE;
   }

   private static final Unit resetDefault$lambda$148$lambda$147(GxSystemPage var0, String var1, JSONObject var2) {
      if (var1 != null) {
         var0.toast("Reset failed.");
         return Unit.INSTANCE;
      } else {
         var0.markRebootPending();
         var0.toast("Resetting toggles to default... rebooting.");
         var0.paint();
         return Unit.INSTANCE;
      }
   }

   private final void resetVersions() {
      this.versionGen++;
      this.versionMode = "latest";
      this.selectedCommit = "";
      this.versionCommits.clear();
      this.versionHead = "";
      this.versionPage = 0;
      this.versionHasMore = false;
      this.versionLoading = false;
      this.versionError = "";
      this.versionNotice = "";
      this.pickerOpen = false;
      this.expanded.clear();
   }

   private final void restoreToggles() {
      GxHost var2 = this.getGx();
      GxSystemPage$$ExternalSyntheticLambda42 var1 = new GxSystemPage$$ExternalSyntheticLambda42(this);
      var2.pickFile(new String[]{"application/json", "text/plain", "application/octet-stream", "*/*"}, var1);
   }

   private static final Unit restoreToggles$lambda$146(GxSystemPage var0, Uri var1) {
      if (var1 == null) {
         return Unit.INSTANCE;
      } else {
         var0.readUri(var1, new GxSystemPage$$ExternalSyntheticLambda31(var0));
         return Unit.INSTANCE;
      }
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private static final Unit restoreToggles$lambda$146$lambda$145(GxSystemPage var0, byte[] var1, String var2) {
      Intrinsics.checkNotNullParameter(var2, "<unused var>");
      if (var1 == null) {
         var0.toast("That file is not a valid toggle backup.");
         return Unit.INSTANCE;
      } else if (var1.length > 5000000) {
         var0.toast("That toggle backup file is too large.");
         return Unit.INSTANCE;
      } else {
         label39:
         try {
            Result.Companion var8 = Result.Companion;
            var2 = new String(var1, Charsets.UTF_8);
            JSONTokener var3 = new JSONTokener(var2);
            var6 = KtCompat.resultOf(var3.nextValue());
         } catch (Throwable var5) {
            Result.Companion var7 = Result.Companion;
            var6 = KtCompat.resultOf(ResultKt.createFailure(var5));
            break label39;
         }

         Object var10 = var6;
         if (KtCompat.isFailure(var6)) {
            var10 = null;
         }

         if (!(var10 instanceof JSONObject)) {
            var0.toast("That file is not a valid toggle backup.");
            return Unit.INSTANCE;
         } else {
            var0.send("POST", "/api/toggles/restore", (JSONObject)var10, 60L, new GxSystemPage$$ExternalSyntheticLambda24(var0));
            return Unit.INSTANCE;
         }
      }
   }

   private static final Unit restoreToggles$lambda$146$lambda$145$lambda$144(GxSystemPage var0, String var1, JSONObject var2) {
      if (var1 != null) {
         CharSequence var8 = var1;
         Object var4 = var8;
         if (StringsKt.isBlank(var8)) {
            var4 = "Failed to restore toggles.";
         }

         var0.toast((String)var4);
      } else {
         label27: {
            if (var2 != null) {
               var1 = GxPage.str$default(var0, var2, "message", null, 2, null);
               if (var1 != null) {
                  CharSequence var9 = var1;
                  CharSequence var6 = var9;
                  if (StringsKt.isBlank(var9)) {
                     var6 = null;
                  }

                  var1 = (String)var6;
                  if (var1 != null) {
                     break label27;
                  }
               }
            }

            var1 = "Toggles restored!";
         }

         var0.toast(var1);
         GalaxyApi var3 = var0.getApi();
         if (var3 != null) {
            GalaxyApi.refreshValues$default(var3, null, 1, null);
         }
      }

      return Unit.INSTANCE;
   }

   private final void returnToLatest() {
      JSONObject var1 = this.fastStatus;
      if (var1 != null) {
         var1 = var1.optJSONObject("versionPin");
         if (var1 != null) {
            String var3 = GxPage.str$default(this, var1, "branch", null, 2, null);
            if (var3 != null) {
               if (!this.getBranchSwitchBlocked() && !this.branchBusy && this.branches.contains(var3)) {
                  if (!Intrinsics.areEqual(var3, this.targetBranch)) {
                     this.targetBranch = var3;
                     this.otherOpen = GxSystemKt.access$getGxSystem_CORE$p().contains(var3) ^ true;
                  }

                  this.resetVersions();
                  this.forceAdv();
                  this.installSelectedVersion();
                  return;
               }

               return;
            }
         }
      }
   }

   private final void runUpdate(String var1) {
      boolean var2;
      if (this.busy.length() > 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      if (!var2 && !this.getUpdateInProgress() && !this.isOnroad) {
         if (Intrinsics.areEqual(var1, "rollback")) {
            JSONObject var3 = this.fastStatus;
            if (var3 != null && !var3.optBoolean("rollbackAvailable")) {
               this.toast("No previous installed version is available to roll back to.");
               return;
            }
         }

         String var4;
         if (Intrinsics.areEqual(var1, "recover")) {
            var4 = "Recover the interrupted update?";
         } else {
            var4 = "Roll back to the previous installed version?";
         }

         this.confirm(var4, "Your device will reboot when the operation is done.", "Continue", new GxSystemPage$$ExternalSyntheticLambda37(this, var1));
      }
   }

   private static final Unit runUpdate$lambda$119(GxSystemPage var0, String var1) {
      boolean var2;
      if (var0.busy.length() > 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      if (!var2 && !var0.getUpdateInProgress()) {
         var0.busy = var1;
         var0.paint();
         GxPage var3 = var0;
         if (Intrinsics.areEqual(var1, "recover")) {
            var1 = "/api/update/recover";
         } else {
            var1 = "/api/update/rollback";
         }

         GxPage.send$default(var3, "POST", var1, null, 0L, new GxSystemPage$$ExternalSyntheticLambda26(var0), 12, null);
         return Unit.INSTANCE;
      } else {
         return Unit.INSTANCE;
      }
   }

   private static final Unit runUpdate$lambda$119$lambda$118(GxSystemPage var0, String var1, JSONObject var2) {
      var0.busy = "";
      if (var1 != null) {
         CharSequence var8 = var1;
         Object var6 = var8;
         if (StringsKt.isBlank(var8)) {
            var6 = "Update failed.";
         }

         var0.toast((String)var6);
         var0.paint();
         return Unit.INSTANCE;
      } else {
         label25: {
            var0.markRebootPending();
            if (var2 != null) {
               var1 = GxPage.str$default(var0, var2, "message", null, 2, null);
               if (var1 != null) {
                  CharSequence var7 = var1;
                  CharSequence var4 = var7;
                  if (StringsKt.isBlank(var7)) {
                     var4 = null;
                  }

                  var1 = (String)var4;
                  if (var1 != null) {
                     break label25;
                  }
               }
            }

            var1 = "Update started.";
         }

         var0.toast(var1);
         var0.paint();
         loadFastStatus$default(var0, null, 1, null);
         return Unit.INSTANCE;
      }
   }

   private final void saveProfile(JSONObject var1) {
      boolean var2;
      if (this.profileBusy.length() > 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      if (!var2 && !this.isOnroad) {
         String var4 = GxPage.str$default(this, var1, "slot", null, 2, null);
         CharSequence var5 = GxPage.str$default(this, var1, "label", null, 2, null);
         Object var3 = var5;
         if (StringsKt.isBlank(var5)) {
            var3 = var4;
         }

         var3 = (String)var3;
         GxSystemPage$$ExternalSyntheticLambda53 var7 = new GxSystemPage$$ExternalSyntheticLambda53(this, var4, (String)var3);
         if (var1.optBoolean("saved")) {
            this.confirm(
               "Overwrite " + var3 + "?",
               "This replaces the settings currently stored in this slot.",
               "Overwrite",
               new GxSystemPage$$ExternalSyntheticLambda54(var7)
            );
         } else {
            var7.invoke();
         }
      }
   }

   private static final Unit saveProfile$lambda$127(GxSystemPage var0, String var1, String var2) {
      var0.profileBusy = "save-" + var1;
      var0.paint();
      GxPage var3 = var0;
      var1 = var0.enc(var1);
      GxPage.send$default(var3, "POST", "/api/toggles/profiles/" + var1 + "/save", null, 0L, new GxSystemPage$$ExternalSyntheticLambda29(var0, var2), 12, null);
      return Unit.INSTANCE;
   }

   private static final Unit saveProfile$lambda$127$lambda$126(GxSystemPage var0, String var1, String var2, JSONObject var3) {
      var0.profileBusy = "";
      if (var2 != null) {
         CharSequence var6 = var2;
         Object var4 = var6;
         if (StringsKt.isBlank(var6)) {
            var4 = "Failed to save settings profile.";
         }

         var1 = (String)var4;
      } else {
         label24: {
            if (var3 != null) {
               var2 = GxPage.str$default(var0, var3, "message", null, 2, null);
               if (var2 != null) {
                  CharSequence var10 = var2;
                  CharSequence var8 = var10;
                  if (StringsKt.isBlank(var10)) {
                     var8 = null;
                  }

                  var2 = (String)var8;
                  if (var2 != null) {
                     var1 = var2;
                     break label24;
                  }
               }
            }

            var1 = "Saved " + var1 + ".";
         }
      }

      var0.toast(var1);
      var0.paint();
      var0.loadProfiles();
      return Unit.INSTANCE;
   }

   private static final Unit saveProfile$lambda$128(Function0 var0) {
      var0.invoke();
      return Unit.INSTANCE;
   }

   private final void selectTargetBranch(String var1) {
      boolean var2;
      if (var1.length() == 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      if (var2 || !this.branches.contains(var1) || this.branchBusy || this.getBranchSwitchBlocked()) {
         this.forceAdv();
      } else if (Intrinsics.areEqual(var1, this.targetBranch)) {
         this.otherOpen = GxSystemKt.access$getGxSystem_CORE$p().contains(var1) ^ true;
         this.forceAdv();
      } else {
         this.targetBranch = var1;
         this.otherOpen = GxSystemKt.access$getGxSystem_CORE$p().contains(var1) ^ true;
         this.resetVersions();
         this.forceAdv();
      }
   }

   private final void setAutomaticUpdates(boolean var1) {
      JSONObject var3 = this.fastStatus;
      if (!this.autoBusy && !this.isOnroad && !this.getUpdateInProgress() && var3 != null) {
         GalaxyApi var5 = this.getApi();
         if (var5 == null) {
            GxSystemPage var7 = this;
            var7.toast("Not connected");
            var7.paintControls();
         } else {
            boolean var2 = var3.optBoolean("automaticUpdates");
            this.autoBusy = true;
            var3.put("automaticUpdates", var1);
            LinearLayout var4 = this.statusBox;
            LinearLayout var6 = var4;
            if (var4 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("statusBox");
               var6 = null;
            }

            var6.setTag(null);
            this.paint();
            var5.put(
               "AutomaticUpdates", var1, MapsKt.mapOf(TuplesKt.to("label", "Automatic Updates")), new GxSystemPage$$ExternalSyntheticLambda27(this, var2, var1)
            );
         }
      } else {
         this.paintControls();
      }
   }

   private static final Unit setAutomaticUpdates$lambda$115(GxSystemPage var0, boolean var1, boolean var2, String var3) {
      var0.autoBusy = false;
      if (var3 != null) {
         JSONObject var4 = var0.fastStatus;
         if (var4 != null) {
            var4.put("automaticUpdates", var1);
         }

         CharSequence var8 = var3;
         Object var5 = var8;
         if (StringsKt.isBlank(var8)) {
            var5 = "Failed to update Automatic Updates.";
         }

         var0.toast((String)var5);
      } else {
         if (var2) {
            var3 = "enabled";
         } else {
            var3 = "disabled";
         }

         var0.toast("Automatic updates " + var3 + ".");
      }

      LinearLayout var9 = var0.statusBox;
      LinearLayout var7 = var9;
      if (var9 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("statusBox");
         var7 = null;
      }

      var7.setTag(null);
      var0.paint();
      return Unit.INSTANCE;
   }

   private final String shortCommit(String var1) {
      if (var1 == null) {
         var1 = "";
      }

      var1 = StringsKt.take(var1, 10);
      boolean var2;
      if (var1.length() == 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      if (var2) {
         var1 = "—";
      }

      return var1;
   }

   private final void uninstallTailscale() {
      if (!this.tsBusy) {
         this.confirm(
            "Uninstall Tailscale?",
            "This disconnects the device from your tailnet and removes the Tailscale binaries and state.",
            "Uninstall",
            new GxSystemPage$$ExternalSyntheticLambda58(this)
         );
      }
   }

   private static final Unit uninstallTailscale$lambda$163(GxSystemPage var0) {
      var0.tsBusy = true;
      var0.paint();
      var0.toast("Uninstall started...");
      var0.send("POST", "/api/tailscale/uninstall", null, 120L, new GxSystemPage$$ExternalSyntheticLambda0(var0));
      return Unit.INSTANCE;
   }

   private static final Unit uninstallTailscale$lambda$163$lambda$162(GxSystemPage var0, String var1, JSONObject var2) {
      if (var1 != null) {
         CharSequence var7 = var1;
         Object var3 = var7;
         if (StringsKt.isBlank(var7)) {
            var3 = "Failed to uninstall Tailscale.";
         }

         var1 = (String)var3;
      } else {
         label24: {
            if (var2 != null) {
               var1 = GxPage.str$default(var0, var2, "message", null, 2, null);
               if (var1 != null) {
                  CharSequence var8 = var1;
                  CharSequence var6 = var8;
                  if (StringsKt.isBlank(var8)) {
                     var6 = null;
                  }

                  var1 = (String)var6;
                  if (var1 != null) {
                     break label24;
                  }
               }
            }

            var1 = "Tailscale uninstalled.";
         }
      }

      var0.toast(var1);
      var0.loadTailscale(new GxSystemPage$$ExternalSyntheticLambda62(var0));
      return Unit.INSTANCE;
   }

   private static final Unit uninstallTailscale$lambda$163$lambda$162$lambda$161(GxSystemPage var0) {
      var0.tsBusy = false;
      var0.paint();
      return Unit.INSTANCE;
   }

   private final List<JSONObject> versionChoices() {
      List var1;
      if (Intrinsics.areEqual(this.targetBranch, "StarPilot")) {
         var1 = this.releaseVersions(this.versionCommits);
      } else {
         var1 = this.versionCommits;
      }

      return var1;
   }

   private final String versionTitle(JSONObject var1, boolean var2) {
      CharSequence var4 = null;
      Object var5 = null;
      String var3;
      if (var1 != null) {
         var3 = GxPage.str$default(this, var1, "date", null, 2, null);
      } else {
         var3 = null;
      }

      Instant var6 = this.parseDate(var3);
      if (var6 != null) {
         var3 = this.dayFmt.format(var6);
         String var18 = this.timeFmt.format(var6);
         var3 = var3 + " · " + var18;
      } else {
         var3 = "Unknown date";
      }

      String var10;
      if (var2) {
         label55: {
            if (var1 != null) {
               String var7 = GxPage.str$default(this, var1, "version", null, 2, null);
               if (var7 != null) {
                  var var8 = var7;
                  if (StringsKt.isBlank(var8)) {
                     var8 = (String)var5;
                  }

                  var9 = var8;
                  if (var9 != null) {
                     break label55;
                  }
               }
            }

            var9 = "Unnumbered version";
         }

         var10 = var9 + " · " + var3;
      } else {
         label48: {
            if (var1 != null) {
               var5 = GxPage.str$default(this, var1, "subject", null, 2, null);
               if (var5 != null) {
                  var5 = (CharSequence)var5;
                  if (!StringsKt.isBlank((CharSequence)var5)) {
                     var4 = (CharSequence)var5;
                  }

                  var4 = (String)var4;
                  if (var4 != null) {
                     break label48;
                  }
               }
            }

            var4 = "Untitled change";
         }

         label41: {
            if (var1 != null) {
               var11 = GxPage.str$default(this, var1, "sha", null, 2, null);
               if (var11 != null) {
                  break label41;
               }
            }

            var11 = "";
         }

         String var12 = StringsKt.take(var11, 10);
         var10 = var4 + " · " + var3 + " · " + var12;
      }

      return var10;
   }

   @Override
   public void build() {
      long var1;
      label65: {
         String var4 = this.getHost();
         if (var4 != null) {
            Long var7 = GxSystemReboot.INSTANCE.getStartedAt().get(var4);
            if (var7 != null) {
               var1 = var7;
               break label65;
            }
         }

         var1 = 0L;
      }

      this.rebootStartedAt = var1;
      boolean var3;
      if (this.rebootStartedAt > 0L) {
         var3 = true;
      } else {
         var3 = false;
      }

      this.rebootPending = var3;
      GxPage.header$default(this, "Software & Updates", null, 2, null);
      this.loadingNote = GxPage.note$default(this, "Loading update info...", null, 2, null);
      this.swWrap = GxPage.box$default(this, null, 1, null);
      LinearLayout var5 = this.swWrap;
      LinearLayout var8 = var5;
      if (var5 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("swWrap");
         var8 = null;
      }

      this.noticeBox = this.box((ViewGroup)var8);
      var5 = this.swWrap;
      var8 = var5;
      if (var5 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("swWrap");
         var8 = null;
      }

      this.statusBox = this.box((ViewGroup)var8);
      GxPage var6 = this;
      var5 = this.swWrap;
      var8 = var5;
      if (var5 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("swWrap");
         var8 = null;
      }

      this.autoRow = GxPage.row$default(
         var6, "Automatically Install Updates", "Install updates automatically while parked with an active internet connection.", (ViewGroup)var8, 0, 8, null
      );
      GxPage.Row var23 = this.autoRow;
      GxPage.Row var11 = var23;
      if (var23 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("autoRow");
         var11 = null;
      }

      this.autoSwitch = var11.switch_(false, new GxSystemPage$$ExternalSyntheticLambda3(this));
      var6 = this;
      var5 = this.swWrap;
      var8 = var5;
      if (var5 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("swWrap");
         var8 = null;
      }

      this.fastRow = GxPage.row$default(
         var6,
         "Fast Update",
         "Check for and install the latest commit on this branch. Fast update lives on StarView's Software page.",
         (ViewGroup)var8,
         0,
         8,
         null
      );
      GxPage.Row var25 = this.fastRow;
      GxPage.Row var13 = var25;
      if (var25 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("fastRow");
         var13 = null;
      }

      var13.button("Open", true, new GxSystemPage$$ExternalSyntheticLambda7(this));
      var6 = this;
      var5 = this.swWrap;
      var8 = var5;
      if (var5 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("swWrap");
         var8 = null;
      }

      this.checkBtn = GxPage.Row.button$default(
         GxPage.row$default(
            var6,
            "Check for Updates",
            "Check for Updates scans for a newer commit. Use Fast Update on the Software page to install it.",
            (ViewGroup)var8,
            0,
            8,
            null
         ),
         "Check",
         false,
         new GxSystemPage$$ExternalSyntheticLambda8(this),
         2,
         null
      );
      var5 = this.swWrap;
      var8 = var5;
      if (var5 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("swWrap");
         var8 = null;
      }

      this.upToDateNote = this.note("The device is up to date. Update becomes available only after a check finds a newer commit.", (ViewGroup)var8);
      var6 = this;
      var5 = this.swWrap;
      var8 = var5;
      if (var5 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("swWrap");
         var8 = null;
      }

      this.recoverBtn = GxPage.Row.button$default(
         GxPage.row$default(
            var6, "Recover", "Recover continues an update that was interrupted (for example, by power loss mid-install).", (ViewGroup)var8, 0, 8, null
         ),
         "Recover",
         false,
         new GxSystemPage$$ExternalSyntheticLambda9(this),
         2,
         null
      );
      var6 = this;
      var5 = this.swWrap;
      var8 = var5;
      if (var5 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("swWrap");
         var8 = null;
      }

      this.rollbackBtn = GxPage.Row.button$default(
         GxPage.row$default(
            var6, "Rollback", "Rollback returns the device to the previously installed version if the current one has a problem.", (ViewGroup)var8, 0, 8, null
         ),
         "Rollback",
         false,
         new GxSystemPage$$ExternalSyntheticLambda10(this),
         2,
         null
      );
      var6 = this;
      var5 = this.swWrap;
      var8 = var5;
      if (var5 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("swWrap");
         var8 = null;
      }

      this.advBtn = GxPage.Row.button$default(
         GxPage.row$default(var6, "Advanced update options", "Install a specific branch or historical version.", (ViewGroup)var8, 0, 8, null),
         "Show",
         false,
         new GxSystemPage$$ExternalSyntheticLambda12(this),
         2,
         null
      );
      var5 = this.swWrap;
      var8 = var5;
      if (var5 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("swWrap");
         var8 = null;
      }

      this.advBox = this.box((ViewGroup)var8);
      GxPage.header$default(this, "Backup & Restore", null, 2, null);
      var8 = GxPage.card$default(this, null, 1, null);
      GxPage.text$default(this, "Settings Profiles", (ViewGroup)var8, 21.0F, CarUi.Companion.getTEXT(), false, 16, null);
      GxPage.text$default(
         this,
         "Keep two local configurations for different vehicles, drivers, or troubleshooting. Profiles never include pairing or sensitive device data.",
         (ViewGroup)var8,
         0.0F,
         0,
         false,
         28,
         null
      );
      this.profileNotice = GxPage.box$default(this, null, 1, null);
      this.profilesBox = GxPage.box$default(this, null, 1, null);
      GxPage.Row.button$default(
         GxPage.row$default(this, "Backup Toggles", "Downloads your toggle settings as a JSON file (Downloads/StarView).", null, 0, 12, null),
         "Backup",
         false,
         new GxSystemPage$$ExternalSyntheticLambda13(this),
         2,
         null
      );
      GxPage.Row.button$default(
         GxPage.row$default(this, "Restore Toggles", "Re-applies the toggle settings from a backup file.", null, 0, 12, null),
         "Restore",
         false,
         new GxSystemPage$$ExternalSyntheticLambda14(this),
         2,
         null
      );
      GxPage.Row.button$default(
         GxPage.row$default(this, "Reset to Default", "Clears all toggles back to stock and reboots.", null, 0, 12, null),
         "Reset",
         false,
         new GxSystemPage$$ExternalSyntheticLambda15(this),
         2,
         null
      );
      GxPage.Row.button$default(
         GxPage.row$default(
            this,
            "Delete All Driving Routes",
            "Permanently deletes all local routes from standard, high-resolution, and alternate footage storage.",
            null,
            0,
            12,
            null
         ),
         "Delete",
         false,
         new GxSystemPage$$ExternalSyntheticLambda16(this),
         2,
         null
      );
      GxPage.note$default(
         this,
         "Backup downloads your toggle settings as a JSON file. Restore re-applies one, and Reset to Default clears them back to stock and reboots.",
         null,
         2,
         null
      );
      this.factoryBox = GxPage.box$default(this, null, 1, null);
      GxPage.header$default(this, "Tailscale", null, 2, null);
      this.tsBox = GxPage.box$default(this, null, 1, null);
      GxPage.Row.button$default(
         GxPage.row$default(this, "Tailscale remote access", "Install, sign in and uninstall are on the Galaxy & remote access page.", null, 0, 12, null),
         "Open",
         false,
         new GxSystemPage$$ExternalSyntheticLambda4(this),
         2,
         null
      );
      GxPage.header$default(this, "Danger Zone", null, 2, null);
      GxPage.note$default(
         this,
         "Last resort only. Factory Reset (also known as SAVE ME) wipes params, backups, themes, models, maps, and route data, then reboots the device. This cannot be undone.",
         null,
         2,
         null
      );
      GxPage.Row.button$default(
            GxPage.row$default(
               this, "Factory Reset Device (SAVE ME)", "Wipes params, backups, themes, models, maps, and route data, then reboots.", null, 0, 12, null
            ),
            "Factory Reset",
            false,
            new GxSystemPage$$ExternalSyntheticLambda5(this),
            2,
            null
         )
         .setTextColor(GxSystemKt.access$getGxSystem_DANGER$p());
      this.every(1000L, new GxSystemPage$$ExternalSyntheticLambda6(this));
      this.paint();
   }

   @Override
   public boolean getNeedsOffroad() {
      return this.needsOffroad;
   }

   @Override
   public void onShow() {
      this.loadBranches();
      this.loadProfiles();
      if (this.fastStatus != null && !this.statusInFlight) {
         loadFastStatus$default(this, null, 1, null);
      }
   }

   private static final class DayGroup {
      private final ArrayList<JSONObject> commits;
      private final String key;
      private final String label;

      public DayGroup(String var1, String var2, ArrayList<JSONObject> var3) {
         Intrinsics.checkNotNullParameter(var1, "key");
         Intrinsics.checkNotNullParameter(var2, "label");
         Intrinsics.checkNotNullParameter(var3, "commits");
         super();
         this.key = var1;
         this.label = var2;
         this.commits = var3;
      }

      // $VF: synthetic method
      public static GxSystemPage.DayGroup copy$default(GxSystemPage.DayGroup var0, String var1, String var2, ArrayList var3, int var4, Object var5) {
         if ((var4 & 1) != 0) {
            var1 = var0.key;
         }

         if ((var4 & 2) != 0) {
            var2 = var0.label;
         }

         if ((var4 & 4) != 0) {
            var3 = var0.commits;
         }

         return var0.copy(var1, var2, var3);
      }

      public final String component1() {
         return this.key;
      }

      public final String component2() {
         return this.label;
      }

      public final ArrayList<JSONObject> component3() {
         return this.commits;
      }

      public final GxSystemPage.DayGroup copy(String var1, String var2, ArrayList<JSONObject> var3) {
         Intrinsics.checkNotNullParameter(var1, "key");
         Intrinsics.checkNotNullParameter(var2, "label");
         Intrinsics.checkNotNullParameter(var3, "commits");
         return new GxSystemPage.DayGroup(var1, var2, var3);
      }

      @Override
      public boolean equals(Object var1) {
         if (this == var1) {
            return true;
         } else if (!(var1 instanceof GxSystemPage.DayGroup)) {
            return false;
         } else {
            var1 = var1;
            if (!Intrinsics.areEqual(this.key, var1.key)) {
               return false;
            } else {
               return !Intrinsics.areEqual(this.label, var1.label) ? false : Intrinsics.areEqual(this.commits, var1.commits);
            }
         }
      }

      public final ArrayList<JSONObject> getCommits() {
         return this.commits;
      }

      public final String getKey() {
         return this.key;
      }

      public final String getLabel() {
         return this.label;
      }

      @Override
      public int hashCode() {
         return (this.key.hashCode() * 31 + this.label.hashCode()) * 31 + this.commits.hashCode();
      }

      @Override
      public String toString() {
         String var2 = this.key;
         String var1 = this.label;
         ArrayList var3 = this.commits;
         return "DayGroup(key=" + var2 + ", label=" + var1 + ", commits=" + var3 + ")";
      }
   }
}
