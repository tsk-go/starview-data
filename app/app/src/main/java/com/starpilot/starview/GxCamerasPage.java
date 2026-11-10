package com.starpilot.starview;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.jvm.internal.Ref.DoubleRef;
import kotlin.jvm.internal.Ref.ObjectRef;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import org.json.JSONArray;
import org.json.JSONObject;

public final class GxCamerasPage extends GxPage {
   private TextView pArmNote;
   private TextView pCaption;
   private JSONObject pConfig;
   private boolean pConfigExists;
   private boolean pConfigSaved;
   private TextView pDeleteBtn;
   private TextView pLeftBtn;
   private boolean pLoading;
   private TextView pMsg;
   private TextView pRightBtn;
   private TextView pSaveBtn;
   private TextView pSnapBtn;
   private boolean pSnapLoading;
   private GxCamerasPipView pView;
   private Function1<? super Double, Unit> pZoomSet;
   private LinearLayout sCfgBox;
   private TextView sDelBtn;
   private boolean sDeleteBusy;
   private JSONObject sEvent;
   private LinearLayout sEventBox;
   private String sEventSig;
   private LinearLayout sHistBox;
   private TextView sHistBtn;
   private List<? extends JSONObject> sHistory;
   private boolean sHistoryBusy;
   private String sHistorySig;
   private boolean sHistoryVisible;
   private LinearLayout sLiveBox;
   private TextView sLiveBtn;
   private boolean sLiveBusy;
   private boolean sLoaded;
   private TextView sNotifyBtn;
   private boolean sPushBusy;
   private TextView sState;
   private boolean sStatusFailed;
   private TextView sTestBtn;
   private boolean sTestBusy;
   private int tab;
   private TextView vClearLeftBtn;
   private TextView vClearRightBtn;
   private JSONObject vConfig;
   private boolean vConfigExists;
   private boolean vConfigSaved;
   private TextView vCounts;
   private TextView vDeleteBtn;
   private GxPage.Row vEnabledRow;
   private TextView vFinishBtn;
   private TextView vHint;
   private TextView vLeftBtn;
   private TextView vMsg;
   private TextView vRightBtn;
   private TextView vSaveBtn;
   private boolean vSaving;
   private TextView vSnapBtn;
   private boolean vSnapLoading;
   private final HashMap<String, TextView> vStat;
   private TextView vUndoBtn;
   private GxCamerasRegionView vView;

   // $VF: synthetic method
   public static Unit $r8$lambda$_oV6UBP8S2Yvw_EvHOHaoUFLOnk/* $VF was: $r8$lambda$_oV6UBP8S2Yvw_EvHOHaoUFLOnk*/(GxCamerasPage var0, String var1, Object var2) {
      return viewLive$lambda$51(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$0PPXDOiUeHEXcE_7gpZPwlZySDE/* $VF was: $r8$lambda$0PPXDOiUeHEXcE_7gpZPwlZySDE*/(GxCamerasPage var0) {
      return buildSentry$lambda$5(var0);
   }

   // $VF: synthetic method
   public static String $r8$lambda$0oQZvMa4feksdxtr_bItpH2HZsw(double var0) {
      return buildPip$lambda$121(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$14OPT2_7Jv_e_5NGwWBgBkhCVGc/* $VF was: $r8$lambda$14OPT2_7Jv_e_5NGwWBgBkhCVGc*/(
      GxCamerasPage var0, String var1, JSONObject var2
   ) {
      return loadHistory$lambda$55(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$2NdqMSOIsvtwLTvza8PIgtd0Hlk(GxCamerasPage var0, String var1, String var2, JSONObject var3) {
      return deleteEvent$lambda$63$lambda$62(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$2qWPJ1SxJ3xycU0SJW3Q9GdWYHI(
      DoubleRef var0, double var1, int var3, double var4, double var6, Function1 var8, TextView var9, String var10
   ) {
      return numRow$lambda$24(var0, var1, var3, var4, var6, var8, var9, var10);
   }

   // $VF: synthetic method
   public static void $r8$lambda$6SSJ_S3XxSFAVkCjWDXBUOaFqcs/* $VF was: $r8$lambda$6SSJ_S3XxSFAVkCjWDXBUOaFqcs*/(Dialog var0, View var1) {
      openImage$lambda$46(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$6X0Ljkzkf7R1FMTxzOQdbLsamoM(GxCamerasPage var0, String var1) {
      return deleteEvent$lambda$63(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$6e6nmyIrx78RV2Pv3dkIjk0HW_g/* $VF was: $r8$lambda$6e6nmyIrx78RV2Pv3dkIjk0HW_g*/(GxCamerasPage var0) {
      return buildPip$lambda$112(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$6gjyiiUe49xf36wW9eBhupfc020(GxCamerasPage var0, String var1, Object var2) {
      return loadVasmConfig$lambda$102(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$6ueZSOunOY2RZu_6TjnAJn60EIM/* $VF was: $r8$lambda$6ueZSOunOY2RZu_6TjnAJn60EIM*/(
      GxCamerasPage var0, String var1, JSONObject var2
   ) {
      return vSave$lambda$97(var0, var1, var2);
   }

   // $VF: synthetic method
   public static void $r8$lambda$BPfbjyJYvP6q157sAkD1XJsCxQQ(GxCamerasPage var0, String var1, String var2, int var3, View var4) {
      imageStrip$lambda$39(var0, var1, var2, var3, var4);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$CEvPl_UqAjp7xHzPRl8320FopVg(GxCamerasPage var0, boolean var1) {
      return loadSentryParams$lambda$9(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$CGJPUIJe3xdUx4G9GXmbEIfU5Es(GxCamerasPage var0) {
      return buildVasm$lambda$85(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$GZlpKILeviiQpERSaYRhHtmwU_0/* $VF was: $r8$lambda$GZlpKILeviiQpERSaYRhHtmwU_0*/(GxCamerasPage var0) {
      return vDelete$lambda$101(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$Iacv56SNOah0iIPvFu0a6VvjD5o(GxCamerasPage var0, int var1, LinearLayout var2) {
      return build$lambda$1(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$JA0lzqCZEOvH5MpP3ux80_pEpJU(GxCamerasPage var0) {
      return buildPip$lambda$109(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$JFpO7t07j1q8XmtWCDv5ad9lJE8(GxCamerasPage var0, String var1, JSONObject var2) {
      return loadPipConfig$lambda$132(var0, var1, var2);
   }

   // $VF: synthetic method
   public static void $r8$lambda$JLPuiuOpNv5_McurnuRyBxDK19Q(String var0, String var1, GxCamerasPage var2, Function2 var3) {
      gxCamLoadBitmap$lambda$73(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$KKPYBon9ajUrda_MxylIfmJLukQ(
      GxCamerasPage var0, int var1, DoubleRef var2, double var3, double var5, Function1 var7, TextView var8, String var9, String var10
   ) {
      return numRow$lambda$22$lambda$21(var0, var1, var2, var3, var5, var7, var8, var9, var10);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$LJ67K7Zd9CzHrGhJDgG0IeRAUpg(GxCamerasPage var0, String var1) {
      return buildPip$lambda$118(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$Mebid13ucyX_2HbpuvQUu8mFHgY/* $VF was: $r8$lambda$Mebid13ucyX_2HbpuvQUu8mFHgY*/(GxCamerasPage var0) {
      return buildPip$lambda$116(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$N0a5jB8MmtInLGYPVVuYYwJP2EQ(GxCamerasPage var0, String var1, String var2, String var3) {
      return pollVasm$lambda$108(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$N5JL_ijuSnmhCZZb2zq182ZrsgM/* $VF was: $r8$lambda$N5JL_ijuSnmhCZZb2zq182ZrsgM*/(
      GxCamerasPage var0, String var1, JSONObject var2
   ) {
      return loadSentryStatus$lambda$26(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$Nza9JB5AUHFrKUJxm0Klnr_AXPk/* $VF was: $r8$lambda$Nza9JB5AUHFrKUJxm0Klnr_AXPk*/(GxCamerasPage var0, double var1) {
      return buildPip$lambda$122(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$NzgBydDiBc6S6VKS_u9mcvOqN4A(GxCamerasPage var0) {
      return buildPip$lambda$114(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$OKLhH31sc9qqz8IKVOCihc6O1dw(GxCamerasPage var0) {
      return pDelete$lambda$129(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$QKo3ueU_oBXcdiXYsvs3eILIp5o(GxCamerasPage var0, String var1, Bitmap var2) {
      return loadVasmSnapshot$lambda$105(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$QY3r___BOCudpBQbHyGP6w4SlzE/* $VF was: $r8$lambda$QY3r___BOCudpBQbHyGP6w4SlzE*/(GxCamerasPage var0, double var1) {
      return fillSentryConfig$lambda$18(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$QzgRqWa0s_8vCOzMb3FsHyK0_XY(GxCamerasPage var0) {
      return buildPip$lambda$117(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$Qzsl2shFUiBog8I2VvuzQ74OBf0(GxCamerasPage var0, Function0 var1, String var2) {
      return saveSentryParam$lambda$10(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$S18i6CJj1N1rozCcq0VISDFTN_E(GxCamerasPage var0) {
      return buildSentry$lambda$8(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$T2R97mFstpF3OecliuezlPovSxI(GxCamerasPage var0) {
      return buildVasm$lambda$87(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$UH8oM4az7uIlZbr3IH2Ax97ap9Q(Function0 var0) {
      return kindLine$lambda$34(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$Ui61pyiLlv0otkJfiTx9y4WBk4s(GxCamerasPage var0, boolean var1) {
      return fillSentryConfig$lambda$11(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$Ve5lNKoJn7WK_FiQTXuZNI4A894(GxCamerasPage var0) {
      return buildVasm$lambda$76(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$Xhn3EWvF8dibSyii7y2ljIrDhKU(GxCamerasPage var0, double var1) {
      return fillSentryConfig$lambda$17(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$Xx4h8lcxmVItbcB7o1cc_583fHI/* $VF was: $r8$lambda$Xx4h8lcxmVItbcB7o1cc_583fHI*/(GxCamerasPage var0) {
      return buildPip$lambda$113(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$YOffpQETBOI9NJbVO2JRA0od_J0/* $VF was: $r8$lambda$YOffpQETBOI9NJbVO2JRA0od_J0*/(GxCamerasPage var0) {
      sendTestEvent$lambda$54$lambda$53(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$ZAMF0DIyzN_C_bJX5ehN_2L48_0/* $VF was: $r8$lambda$ZAMF0DIyzN_C_bJX5ehN_2L48_0*/(GxCamerasPage var0, String var1, Bitmap var2) {
      return loadPipSnapshot$lambda$136(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$aV3w8WGC9_S5ohwm4n9WauT_Iys(GxCamerasPage var0) {
      return buildVasm$lambda$82(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$angKi7h9c5_qU_a5117STcOw6xI/* $VF was: $r8$lambda$angKi7h9c5_qU_a5117STcOw6xI*/(GxCamerasPage var0, int var1) {
      return build$lambda$0(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$ciSt7nYFUh26OM2xMQ76TdnEPW4(GxCamerasPage var0) {
      return fillSentryConfig$urlRow$lambda$14$lambda$13$lambda$12(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$cmhm9Nx0UQ_zAzqys_wlLcastMc/* $VF was: $r8$lambda$cmhm9Nx0UQ_zAzqys_wlLcastMc*/(
      GxCamerasPage var0,
      String var1,
      double var2,
      double var4,
      DoubleRef var6,
      String var7,
      int var8,
      Function1 var9,
      TextView var10,
      String var11,
      View var12
   ) {
      numRow$lambda$22(var0, var1, var2, var4, var6, var7, var8, var9, var10, var11, var12);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$dWna0WBnUt4WAoRAa8fZgwwK6sg(GxCamerasPage var0, String var1, JSONObject var2) {
      return pSave$lambda$125(var0, var1, var2);
   }

   // $VF: synthetic method
   public static void $r8$lambda$eiRGx9YSC0Xvy_ATl8iUrwbmoZ0(Function2 var0, ObjectRef var1, ObjectRef var2) {
      gxCamLoadBitmap$lambda$73$lambda$72(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$fpmX_9IGFvZE0_gofIG7tqXx2as/* $VF was: $r8$lambda$fpmX_9IGFvZE0_gofIG7tqXx2as*/(GxCamerasPage var0) {
      return buildSentry$lambda$6(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$hZ0_kt6bcV4rEuesR7_jCdircEw(GxCamerasPage var0, Bitmap var1) {
      return openImage$lambda$47(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$iOxZLD9sTpDZIcdsMuctjYznQcI(GxCamerasPage var0) {
      return buildSentry$lambda$7(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$jRGOoWJVkOl8wKhuE8M8Y1I8Msw(GxCamerasPage var0) {
      return buildVasm$lambda$80(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$jwIYmMqogPM1JtNDd4nYzPxzyBA(GxCamerasPage var0, String var1, JSONObject var2) {
      return sendTestNotification$lambda$49(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$ksKwwctI_HgVSY4pjkggFIpKUQc/* $VF was: $r8$lambda$ksKwwctI_HgVSY4pjkggFIpKUQc*/(GxCamerasPage var0) {
      return build$lambda$3(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$lLYdSLu4R59U_njOOeGAcOeW5AE/* $VF was: $r8$lambda$lLYdSLu4R59U_njOOeGAcOeW5AE*/(GxCamerasPage var0) {
      return buildVasm$lambda$77(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$lMpFoWqsMMi6DT_VIfAeGsSpyD0/* $VF was: $r8$lambda$lMpFoWqsMMi6DT_VIfAeGsSpyD0*/(GxCamerasPage var0) {
      return buildPip$lambda$111(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$nhysf7xoNY_GlWTEUrvQj0QyDrE/* $VF was: $r8$lambda$nhysf7xoNY_GlWTEUrvQj0QyDrE*/(GxCamerasPage var0, String var1) {
      return renderHistory$lambda$58(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$pC2e63uYXJzhHW4olYynzQgVD28(GxCamerasPage var0) {
      return buildVasm$lambda$81(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$rBTnQohLfuti0r1JP6IMOvkSmK4(
      DoubleRef var0, double var1, int var3, double var4, double var6, Function1 var8, TextView var9, String var10
   ) {
      return numRow$lambda$23(var0, var1, var3, var4, var6, var8, var9, var10);
   }

   // $VF: synthetic method
   public static CharSequence $r8$lambda$rQUHjW23MNEkQ8K3NMbc9neLQts(GxCamerasPage var0, JSONObject var1) {
      return renderHistory$lambda$56(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$r_nBZFAKp3BWuiUeznr83NJuMqg(GxCamerasPage var0, String var1, JSONObject var2) {
      return vDelete$lambda$101$lambda$100(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$rw6y0OaMYVel_xBTcFa1X_wDLf4/* $VF was: $r8$lambda$rw6y0OaMYVel_xBTcFa1X_wDLf4*/(GxCamerasPage var0) {
      return fillSentryConfig$urlRow$lambda$16$lambda$15(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$ryJbGkVIFu2pwnYoq3lU3ju2CHQ(Dialog var0) {
      return openImage$lambda$45(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$snBX_7BhTVSjrhIKJlozQRKiMCw/* $VF was: $r8$lambda$snBX_7BhTVSjrhIKJlozQRKiMCw*/(
      GxCamerasPage var0, String var1, String var2, String var3, String var4
   ) {
      return fillSentryConfig$urlRow$lambda$14(var0, var1, var2, var3, var4);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$vm5KGOu_lGybdo_PCvy0jzfCv9A/* $VF was: $r8$lambda$vm5KGOu_lGybdo_PCvy0jzfCv9A*/(GxCamerasPage var0) {
      return build$lambda$2(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$vmWuvrDjuYvXLDHvtW7vGERS2_8(GxCamerasPage var0, String var1, JSONObject var2) {
      return pDelete$lambda$129$lambda$128(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$vu9YNnpM0HvdG9skRxM_tQ8n8nQ(GxCamerasPage var0) {
      return buildVasm$lambda$79(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$w1UkB1KMw8_8sb6rBnBI4MK97Bk(GxCamerasPage var0) {
      return buildVasm$lambda$75(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$wWEaZZgzNgKE_mHBJJJwHpSj8UY(GxCamerasPage var0, String var1) {
      return fillSentryConfig$urlRow$lambda$16(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$wc06v_mv0whtSKBsVm9e4aUYEiw/* $VF was: $r8$lambda$wc06v_mv0whtSKBsVm9e4aUYEiw*/(GxCamerasPage var0) {
      return buildPip$lambda$110(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$wo_vmKLw2jfCsMaqsCQ5uy66xDQ(GxCamerasPage var0, String var1, JSONObject var2) {
      return sendTestEvent$lambda$54(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$yFkYtFKWO17_0M09OCjBptn7Rq0/* $VF was: $r8$lambda$yFkYtFKWO17_0M09OCjBptn7Rq0*/(GxCamerasPage var0) {
      return buildVasm$lambda$90(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$ypsdjgGkI2wVh_A9IkyJm3dMZ0A(GxCamerasPage var0) {
      return buildVasm$lambda$91(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$z3c9_2plqktuepKSIYx8LfPmr7o/* $VF was: $r8$lambda$z3c9_2plqktuepKSIYx8LfPmr7o*/(GxCamerasPage var0) {
      return buildSentry$lambda$4(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$zC6Su6dTi_6FjOqEToDNyq5_kbw/* $VF was: $r8$lambda$zC6Su6dTi_6FjOqEToDNyq5_kbw*/(GxCamerasPage var0) {
      return buildVasm$lambda$78(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$zJok_0C0Lj64gCiVjLLe_4x223U/* $VF was: $r8$lambda$zJok_0C0Lj64gCiVjLLe_4x223U*/(GxCamerasPage var0, String var1, String var2) {
      return fillSentryConfig$urlRow$lambda$14$lambda$13(var0, var1, var2);
   }

   public GxCamerasPage(Activity var1, GxHost var2) {
      Intrinsics.checkNotNullParameter(var1, "act");
      Intrinsics.checkNotNullParameter(var2, "gx");
      super(var1, var2);
      this.sEvent = new JSONObject();
      this.sEventSig = "?";
      this.sHistory = CollectionsKt.emptyList();
      this.sHistorySig = "?";
      this.vStat = new HashMap<>();
   }

   private static final Unit build$lambda$0(GxCamerasPage var0, int var1) {
      var0.tab = var1;
      if (var0.getShown()) {
         loadTab$default(var0, var1, false, 2, null);
      }

      return Unit.INSTANCE;
   }

   private static final Unit build$lambda$1(GxCamerasPage var0, int var1, LinearLayout var2) {
      Intrinsics.checkNotNullParameter(var2, "box");
      switch (var1) {
         case 0:
            var0.buildSentry(var2);
            break;
         case 1:
            var0.buildVasm(var2);
            break;
         default:
            var0.buildPip(var2);
      }

      return Unit.INSTANCE;
   }

   private static final Unit build$lambda$2(GxCamerasPage var0) {
      if (var0.tab == 0) {
         var0.loadSentryStatus();
      }

      return Unit.INSTANCE;
   }

   private static final Unit build$lambda$3(GxCamerasPage var0) {
      if (var0.tab == 1) {
         var0.pollVasm();
      }

      return Unit.INSTANCE;
   }

   private final void buildPip(LinearLayout var1) {
      this.pMsg = this.msgView(var1);
      GxPage var4 = this;
      Pair var5 = TuplesKt.to("Set Left Side of Vehicle - Left Here", new GxCamerasPage$$ExternalSyntheticLambda53(this));
      Pair var3 = TuplesKt.to("Set Right Side of Vehicle - Right Here", new GxCamerasPage$$ExternalSyntheticLambda56(this));
      ViewGroup var2 = (ViewGroup)var1;
      LinearLayout var23 = GxPage.buttons$default(var4, new Pair[]{var5, var3}, var2, false, 4, null);
      View var7 = var23.getChildAt(0);
      Intrinsics.checkNotNull(var7, "null cannot be cast to non-null type android.widget.TextView");
      this.pLeftBtn = (TextView)var7;
      View var8 = var23.getChildAt(1);
      Intrinsics.checkNotNull(var8, "null cannot be cast to non-null type android.widget.TextView");
      this.pRightBtn = (TextView)var8;
      var3 = TuplesKt.to("Save Mask", new GxCamerasPage$$ExternalSyntheticLambda57(this));
      Pair var9 = TuplesKt.to("Delete Mask", new GxCamerasPage$$ExternalSyntheticLambda58(this));
      Pair var26 = TuplesKt.to("Clear All", new GxCamerasPage$$ExternalSyntheticLambda59(this));
      Pair var6 = TuplesKt.to("Get a new Snapshot", new GxCamerasPage$$ExternalSyntheticLambda60(this));
      ViewGroup var34 = (ViewGroup)var1;
      LinearLayout var35 = this.buttons(new Pair[]{var3, var9, var26, var6}, var34, true);
      View var10 = var35.getChildAt(0);
      Intrinsics.checkNotNull(var10, "null cannot be cast to non-null type android.widget.TextView");
      this.pSaveBtn = (TextView)var10;
      View var11 = var35.getChildAt(1);
      Intrinsics.checkNotNull(var11, "null cannot be cast to non-null type android.widget.TextView");
      this.pDeleteBtn = (TextView)var11;
      TextView var27 = this.pDeleteBtn;
      var3 = null;
      TextView var12 = var27;
      if (var27 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("pDeleteBtn");
         var12 = null;
      }

      var12.setTextColor(GxCamerasKt.access$getGX_CAM_RED$p());
      View var13 = var35.getChildAt(3);
      Intrinsics.checkNotNull(var13, "null cannot be cast to non-null type android.widget.TextView");
      this.pSnapBtn = (TextView)var13;
      TextView var14 = new TextView((Context)this.getAct());
      var14.setTextSize(17.0F);
      var14.setPadding(this.getUi().dp(18), this.getUi().dp(12), this.getUi().dp(18), this.getUi().dp(12));
      var14.setVisibility(8);
      this.pArmNote = var14;
      TextView var28 = this.pArmNote;
      TextView var15 = var28;
      if (var28 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("pArmNote");
         var15 = null;
      }

      var1.addView((View)var15, (LayoutParams)CarUi.lp$default(this.getUi(), 0, 0, 3, null));
      this.pView = new GxCamerasPipView((Context)this.getAct());
      GxCamerasPipView var29 = this.pView;
      GxCamerasPipView var16 = var29;
      if (var29 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("pView");
         var16 = null;
      }

      var16.setOnChanged(new GxCamerasPage$$ExternalSyntheticLambda61(this));
      GxCamerasPipView var30 = this.pView;
      GxCamerasPipView var17 = var30;
      if (var30 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("pView");
         var17 = null;
      }

      var17.setOnPlaced(new GxCamerasPage$$ExternalSyntheticLambda62(this));
      GxCamerasPipView var31 = this.pView;
      GxCamerasPipView var18 = var31;
      if (var31 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("pView");
         var18 = null;
      }

      var18.setOnWrongHalf(new GxCamerasPage$$ExternalSyntheticLambda63(this));
      GxCamerasPipView var32 = this.pView;
      GxCamerasPipView var19 = var32;
      if (var32 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("pView");
         var19 = null;
      }

      View var20 = var19;
      android.widget.LinearLayout.LayoutParams var36 = new android.widget.LinearLayout.LayoutParams(-1, -2);
      var36.bottomMargin = this.getUi().dp(4);
      Unit var33 = Unit.INSTANCE;
      var1.addView(var20, (LayoutParams)var36);
      TextView var21 = new TextView((Context)this.getAct());
      var21.setTextSize(15.0F);
      var21.setTextColor(CarUi.Companion.getDIM());
      var21.setGravity(17);
      var21.setPadding(0, this.getUi().dp(2), 0, this.getUi().dp(10));
      this.pCaption = var21;
      TextView var22 = this.pCaption;
      if (var22 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("pCaption");
         var22 = var3;
      }

      var1.addView((View)var22);
      this.pZoomSet = this.stepper(
         "Zoom",
         "Applied to both windows so the preview stays consistent. Lower is wider, higher is closer.",
         240.0,
         60.0,
         640.0,
         5.0,
         new GxCamerasPage$$ExternalSyntheticLambda64(),
         (ViewGroup)var1,
         new GxCamerasPage$$ExternalSyntheticLambda54(this)
      );
      this.note(
         "This preview is mirrored to match the normal on-road driver-camera view: the LEFT side of the vehicle is always on the LEFT here. Place a center point on each window (drag a point to move it); at least one window center is required to enable the preview. Enable \"PiP Side Preview\" in Toggles -> Visual (Display & UI) -> Driving Screen Widgets.",
         (ViewGroup)var1
      );
      this.pPaint();
   }

   private static final Unit buildPip$lambda$109(GxCamerasPage var0) {
      var0.pArm("left");
      return Unit.INSTANCE;
   }

   private static final Unit buildPip$lambda$110(GxCamerasPage var0) {
      var0.pArm("right");
      return Unit.INSTANCE;
   }

   private static final Unit buildPip$lambda$111(GxCamerasPage var0) {
      var0.pSave();
      return Unit.INSTANCE;
   }

   private static final Unit buildPip$lambda$112(GxCamerasPage var0) {
      var0.pDelete();
      return Unit.INSTANCE;
   }

   private static final Unit buildPip$lambda$113(GxCamerasPage var0) {
      var0.pClearAll();
      return Unit.INSTANCE;
   }

   private static final Unit buildPip$lambda$114(GxCamerasPage var0) {
      var0.loadPipSnapshot();
      return Unit.INSTANCE;
   }

   private static final Unit buildPip$lambda$116(GxCamerasPage var0) {
      var0.pPaint();
      return Unit.INSTANCE;
   }

   private static final Unit buildPip$lambda$117(GxCamerasPage var0) {
      TextView var2 = var0.pMsg;
      TextView var1 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("pMsg");
         var1 = null;
      }

      var0.msg(var1, "", false);
      var0.pPaint();
      return Unit.INSTANCE;
   }

   private static final Unit buildPip$lambda$118(GxCamerasPage var0, String var1) {
      Intrinsics.checkNotNullParameter(var1, "side");
      TextView var3 = var0.pMsg;
      TextView var2 = var3;
      if (var3 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("pMsg");
         var2 = null;
      }

      if (Intrinsics.areEqual(var1, "left")) {
         var1 = "LEFT SIDE OF VEHICLE is on the LEFT. Tap the left half of the preview.";
      } else {
         var1 = "RIGHT SIDE OF VEHICLE is on the RIGHT. Tap the right half of the preview.";
      }

      var0.msg(var2, var1, true);
      return Unit.INSTANCE;
   }

   private static final String buildPip$lambda$121(double var0) {
      int var2 = GxCamerasKt.access$gxCamRound(var0);
      return var2 + " px";
   }

   private static final Unit buildPip$lambda$122(GxCamerasPage var0, double var1) {
      GxCamerasPipView var5 = var0.pView;
      Object var4 = null;
      GxCamerasPipView var3 = var5;
      if (var5 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("pView");
         var3 = null;
      }

      var3.setZoom((float)var1);
      GxCamerasPipView var6 = var0.pView;
      if (var6 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("pView");
         var6 = (GxCamerasPipView)var4;
      }

      var6.invalidate();
      return Unit.INSTANCE;
   }

   private final void buildSentry(LinearLayout var1) {
      this.sState = GxPage.row$default(this, "Sentry Mode", "Monitor the parked vehicle and review movement captures.", (ViewGroup)var1, 0, 8, null)
         .value("unknown");
      this.header("Configuration", (ViewGroup)var1);
      this.sCfgBox = this.box((ViewGroup)var1);
      LinearLayout var4 = this.sCfgBox;
      Object var3 = null;
      LinearLayout var2 = var4;
      if (var4 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("sCfgBox");
         var2 = null;
      }

      var2.addView((View)this.empty("Loading Sentry settings..."));
      GxPage var5 = this;
      Pair var17 = TuplesKt.to("Send test notification", new GxCamerasPage$$ExternalSyntheticLambda9(this));
      ViewGroup var9 = (ViewGroup)var1;
      View var10 = GxPage.buttons$default(var5, new Pair[]{var17}, var9, false, 4, null).getChildAt(0);
      Intrinsics.checkNotNull(var10, "null cannot be cast to non-null type android.widget.TextView");
      this.sNotifyBtn = (TextView)var10;
      this.note(
         "The test notification uses every configured channel: browser Web Push, ntfy, and webhook. Browser notifications are enabled from Galaxy in a phone browser.",
         (ViewGroup)var1
      );
      this.header("Live view", (ViewGroup)var1);
      this.sLiveBtn = GxPage.Row.button$default(
         GxPage.row$default(this, "View live", "Capture one still from both cameras while parked.", (ViewGroup)var1, 0, 8, null),
         "View live",
         false,
         new GxCamerasPage$$ExternalSyntheticLambda10(this),
         2,
         null
      );
      this.sLiveBox = this.box((ViewGroup)var1);
      var4 = this.sLiveBox;
      var2 = var4;
      if (var4 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("sLiveBox");
         var2 = null;
      }

      var2.addView((View)this.empty("No live snapshot captured yet."));
      this.header("Latest Event", (ViewGroup)var1);
      GxPage var7 = this;
      Pair var12 = TuplesKt.to("View history", new GxCamerasPage$$ExternalSyntheticLambda12(this));
      Pair var19 = TuplesKt.to("Send test capture", new GxCamerasPage$$ExternalSyntheticLambda13(this));
      Pair var6 = TuplesKt.to("Delete event", new GxCamerasPage$$ExternalSyntheticLambda14(this));
      ViewGroup var24 = (ViewGroup)var1;
      var2 = GxPage.buttons$default(var7, new Pair[]{var12, var19, var6}, var24, false, 4, null);
      View var20 = var2.getChildAt(0);
      Intrinsics.checkNotNull(var20, "null cannot be cast to non-null type android.widget.TextView");
      this.sHistBtn = (TextView)var20;
      View var21 = var2.getChildAt(1);
      Intrinsics.checkNotNull(var21, "null cannot be cast to non-null type android.widget.TextView");
      this.sTestBtn = (TextView)var21;
      View var14 = var2.getChildAt(2);
      Intrinsics.checkNotNull(var14, "null cannot be cast to non-null type android.widget.TextView");
      this.sDelBtn = (TextView)var14;
      TextView var22 = this.sDelBtn;
      TextView var15 = var22;
      if (var22 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("sDelBtn");
         var15 = null;
      }

      var15.setTextColor(GxCamerasKt.access$getGX_CAM_RED$p());
      TextView var23 = this.sDelBtn;
      TextView var16 = var23;
      if (var23 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("sDelBtn");
         var16 = null;
      }

      var16.setVisibility(8);
      this.note("Events refresh automatically every five seconds.", (ViewGroup)var1);
      this.sEventBox = this.box((ViewGroup)var1);
      this.sHistBox = this.box((ViewGroup)var1);
      var1 = this.sHistBox;
      if (var1 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("sHistBox");
         var1 = (LinearLayout)var3;
      }

      var1.setVisibility(8);
   }

   private static final Unit buildSentry$lambda$4(GxCamerasPage var0) {
      var0.sendTestNotification();
      return Unit.INSTANCE;
   }

   private static final Unit buildSentry$lambda$5(GxCamerasPage var0) {
      var0.viewLive();
      return Unit.INSTANCE;
   }

   private static final Unit buildSentry$lambda$6(GxCamerasPage var0) {
      var0.toggleHistory();
      return Unit.INSTANCE;
   }

   private static final Unit buildSentry$lambda$7(GxCamerasPage var0) {
      var0.sendTestEvent();
      return Unit.INSTANCE;
   }

   private static final Unit buildSentry$lambda$8(GxCamerasPage var0) {
      String var1 = GxPage.str$default(var0, var0.sEvent, "eventId", null, 2, null);
      if (!StringsKt.isBlank(var1)) {
         var0.deleteEvent(var1);
      }

      return Unit.INSTANCE;
   }

   private final void buildVasm(LinearLayout var1) {
      this.vMsg = this.msgView(var1);
      this.vEnabledRow = GxPage.row$default(
         this, "Annotate Windows", "Trace the visible side-window glass the driver camera sees so V-ASM knows where to look.", (ViewGroup)var1, 0, 8, null
      );
      GxPage.Row var3 = this.vEnabledRow;
      GxPage.Row var2 = var3;
      if (var3 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("vEnabledRow");
         var2 = null;
      }

      var2.value("V-ASM enabled").setTextColor(CarUi.Companion.getACCENT());
      GxPage var8 = this;
      Pair var28 = TuplesKt.to("Annotate Left Side of Vehicle - Left Here", new GxCamerasPage$$ExternalSyntheticLambda66(this));
      Pair var4 = TuplesKt.to("Annotate Right Side of Vehicle - Right Here", new GxCamerasPage$$ExternalSyntheticLambda73(this));
      Pair var5 = TuplesKt.to("Finish", new GxCamerasPage$$ExternalSyntheticLambda1(this));
      Pair var7 = TuplesKt.to("Undo last point", new GxCamerasPage$$ExternalSyntheticLambda2(this));
      ViewGroup var6 = (ViewGroup)var1;
      LinearLayout var9 = GxPage.buttons$default(var8, new Pair[]{var28, var4, var5, var7}, var6, false, 4, null);
      View var29 = var9.getChildAt(0);
      Intrinsics.checkNotNull(var29, "null cannot be cast to non-null type android.widget.TextView");
      this.vLeftBtn = (TextView)var29;
      View var30 = var9.getChildAt(1);
      Intrinsics.checkNotNull(var30, "null cannot be cast to non-null type android.widget.TextView");
      this.vRightBtn = (TextView)var30;
      View var31 = var9.getChildAt(2);
      Intrinsics.checkNotNull(var31, "null cannot be cast to non-null type android.widget.TextView");
      this.vFinishBtn = (TextView)var31;
      View var10 = var9.getChildAt(3);
      Intrinsics.checkNotNull(var10, "null cannot be cast to non-null type android.widget.TextView");
      this.vUndoBtn = (TextView)var10;
      var5 = TuplesKt.to("Save Config", new GxCamerasPage$$ExternalSyntheticLambda3(this));
      Pair var51 = TuplesKt.to("Delete Config", new GxCamerasPage$$ExternalSyntheticLambda4(this));
      Pair var32 = TuplesKt.to("Clear All", new GxCamerasPage$$ExternalSyntheticLambda5(this));
      Pair var11 = TuplesKt.to("Get a new Snapshot", new GxCamerasPage$$ExternalSyntheticLambda6(this));
      ViewGroup var43 = (ViewGroup)var1;
      LinearLayout var44 = this.buttons(new Pair[]{var5, var51, var32, var11}, var43, true);
      View var12 = var44.getChildAt(0);
      Intrinsics.checkNotNull(var12, "null cannot be cast to non-null type android.widget.TextView");
      this.vSaveBtn = (TextView)var12;
      View var13 = var44.getChildAt(1);
      Intrinsics.checkNotNull(var13, "null cannot be cast to non-null type android.widget.TextView");
      this.vDeleteBtn = (TextView)var13;
      TextView var33 = this.vDeleteBtn;
      TextView var14 = var33;
      if (var33 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("vDeleteBtn");
         var14 = null;
      }

      var14.setTextColor(GxCamerasKt.access$getGX_CAM_RED$p());
      View var15 = var44.getChildAt(3);
      Intrinsics.checkNotNull(var15, "null cannot be cast to non-null type android.widget.TextView");
      this.vSnapBtn = (TextView)var15;
      LinearLayout var45 = new LinearLayout((Context)this.getAct());
      var45.setOrientation(0);
      var45.setGravity(16);
      var45.setPadding(this.getUi().dp(6), 0, 0, this.getUi().dp(6));
      TextView var16 = new TextView((Context)this.getAct());
      var16.setTextSize(18.0F);
      var16.setTextColor(CarUi.Companion.getTEXT());
      this.vCounts = var16;
      TextView var34 = this.vCounts;
      TextView var17 = var34;
      if (var34 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("vCounts");
         var17 = null;
      }

      var45.addView((View)var17, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(0, -2, 1.0F)));
      TextView var18 = CarUi.button$default(this.getUi(), "Clear Left", false, new GxCamerasPage$$ExternalSyntheticLambda7(this), 2, null);
      this.tint(var18, GxCamerasKt.access$getGX_CAM_BLUE$p(), false);
      this.vClearLeftBtn = var18;
      TextView var19 = CarUi.button$default(this.getUi(), "Clear Right", false, new GxCamerasPage$$ExternalSyntheticLambda8(this), 2, null);
      this.tint(var19, GxCamerasKt.access$getGX_CAM_ORANGE$p(), false);
      this.vClearRightBtn = var19;
      TextView var35 = this.vClearLeftBtn;
      TextView var20 = var35;
      if (var35 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("vClearLeftBtn");
         var20 = null;
      }

      View var21 = (View)var20;
      android.widget.LinearLayout.LayoutParams var36 = new android.widget.LinearLayout.LayoutParams(-2, -2);
      var36.rightMargin = this.getUi().dp(10);
      Unit var50 = Unit.INSTANCE;
      var45.addView(var21, (LayoutParams)var36);
      TextView var37 = this.vClearRightBtn;
      TextView var22 = var37;
      if (var37 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("vClearRightBtn");
         var22 = null;
      }

      var45.addView((View)var22);
      var1.addView((View)var45);
      this.vHint = this.note("Loading camera snapshot... The snapshot will load automatically.", (ViewGroup)var1);
      this.vView = new GxCamerasRegionView((Context)this.getAct());
      GxCamerasRegionView var38 = this.vView;
      GxCamerasRegionView var23 = var38;
      if (var38 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("vView");
         var23 = null;
      }

      var23.setOnChanged(new GxCamerasPage$$ExternalSyntheticLambda71(this));
      GxCamerasRegionView var39 = this.vView;
      GxCamerasRegionView var24 = var39;
      if (var39 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("vView");
         var24 = null;
      }

      var24.setOnClose(new GxCamerasPage$$ExternalSyntheticLambda72(this));
      GxCamerasRegionView var40 = this.vView;
      GxCamerasRegionView var25 = var40;
      if (var40 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("vView");
         var25 = null;
      }

      View var26 = var25;
      android.widget.LinearLayout.LayoutParams var41 = new android.widget.LinearLayout.LayoutParams(-1, -2);
      var41.bottomMargin = this.getUi().dp(8);
      Unit var46 = Unit.INSTANCE;
      var1.addView(var26, (LayoutParams)var41);
      this.note(
         "The preview is mirrored: the LEFT side of the vehicle is always on the LEFT here; the RIGHT side is always on the RIGHT. Trace the visible glass (front and rear side windows), exclude A-pillars, door frames, and interior. Drag a point to move it.",
         (ViewGroup)var1
      );
      this.header("Current Status", (ViewGroup)var1);

      for (Pair var47 : CollectionsKt.listOf(
         new Pair[]{
            TuplesKt.to("VASMLeftActive", "Left Active"),
            TuplesKt.to("VASMRightActive", "Right Active"),
            TuplesKt.to("VASMLeftConfidence", "Left Confidence"),
            TuplesKt.to("VASMRightConfidence", "Right Confidence")
         }
      )) {
         String var42 = (String)var47.component1();
         String var48 = (String)var47.component2();
         this.vStat.put(var42, GxPage.row$default(this, var48, null, (ViewGroup)var1, 0, 8, null).value("-"));
      }

      this.vPaint();
   }

   private static final Unit buildVasm$lambda$75(GxCamerasPage var0) {
      var0.vStart("left");
      return Unit.INSTANCE;
   }

   private static final Unit buildVasm$lambda$76(GxCamerasPage var0) {
      var0.vStart("right");
      return Unit.INSTANCE;
   }

   private static final Unit buildVasm$lambda$77(GxCamerasPage var0) {
      var0.vFinish();
      return Unit.INSTANCE;
   }

   private static final Unit buildVasm$lambda$78(GxCamerasPage var0) {
      var0.vUndo();
      return Unit.INSTANCE;
   }

   private static final Unit buildVasm$lambda$79(GxCamerasPage var0) {
      var0.vSave();
      return Unit.INSTANCE;
   }

   private static final Unit buildVasm$lambda$80(GxCamerasPage var0) {
      var0.vDelete();
      return Unit.INSTANCE;
   }

   private static final Unit buildVasm$lambda$81(GxCamerasPage var0) {
      var0.vClearAll();
      return Unit.INSTANCE;
   }

   private static final Unit buildVasm$lambda$82(GxCamerasPage var0) {
      var0.loadVasmSnapshot();
      return Unit.INSTANCE;
   }

   private static final Unit buildVasm$lambda$85(GxCamerasPage var0) {
      var0.vClearSide("left");
      return Unit.INSTANCE;
   }

   private static final Unit buildVasm$lambda$87(GxCamerasPage var0) {
      var0.vClearSide("right");
      return Unit.INSTANCE;
   }

   private static final Unit buildVasm$lambda$90(GxCamerasPage var0) {
      var0.vPaint();
      return Unit.INSTANCE;
   }

   private static final Unit buildVasm$lambda$91(GxCamerasPage var0) {
      var0.vFinish();
      return Unit.INSTANCE;
   }

   private final void busy(TextView var1, boolean var2, String var3, String var4) {
      CharSequence var6;
      if (var2) {
         var6 = var4;
      } else {
         var6 = var3;
      }

      var1.setText(var6);
      var1.setEnabled(var2 ^ true);
      float var5;
      if (var2) {
         var5 = 0.55F;
      } else {
         var5 = 1.0F;
      }

      var1.setAlpha(var5);
   }

   private final void deleteEvent(String var1) {
      if (!StringsKt.isBlank(var1) && !this.sDeleteBusy) {
         this.confirm(
            "Delete Sentry event?",
            "Delete this Sentry event and its camera images? This cannot be undone.",
            "Delete",
            new GxCamerasPage$$ExternalSyntheticLambda68(this, var1)
         );
      }
   }

   private static final Unit deleteEvent$lambda$63(GxCamerasPage var0, String var1) {
      var0.sDeleteBusy = true;
      GxPage var2 = var0;
      String var3 = var0.enc(var1);
      GxPage.send$default(var2, "DELETE", "/api/sentry/events/" + var3, null, 0L, new GxCamerasPage$$ExternalSyntheticLambda15(var0, var1), 12, null);
      return Unit.INSTANCE;
   }

   private static final Unit deleteEvent$lambda$63$lambda$62(GxCamerasPage var0, String var1, String var2, JSONObject var3) {
      var0.sDeleteBusy = false;
      if (var2 != null) {
         CharSequence var9 = var2;
         Object var6 = var9;
         if (StringsKt.isBlank(var9)) {
            var6 = "Sentry event deletion failed.";
         }

         var0.toast((String)var6);
         return Unit.INSTANCE;
      } else {
         Iterable var10 = var0.sHistory;
         var var7 = new ArrayList();

         for (Object var11 : var10) {
            JSONObject var4 = (JSONObject)var11;
            if (!Intrinsics.areEqual(GxPage.str$default(var0, var4, "eventId", null, 2, null), var1)) {
               var7.add(var11);
            }
         }

         List var8 = var7;
         var0.sHistory = var8;
         if (var0.sHistoryVisible) {
            renderHistory$default(var0, false, 1, null);
         }

         if (Intrinsics.areEqual(GxPage.str$default(var0, var0.sEvent, "eventId", null, 2, null), var1)) {
            var0.sEvent = new JSONObject();
            renderLatest$default(var0, false, 1, null);
            var0.loadSentryStatus();
         }

         var0.toast("Sentry event deleted.");
         return Unit.INSTANCE;
      }
   }

   private final String eventSig(JSONObject var1) {
      String var4;
      String var7;
      label13: {
         var4 = GxPage.str$default(this, var1, "eventId", null, 2, null);
         JSONArray var2 = var1.optJSONArray("imageUrls");
         if (var2 != null) {
            List var6 = this.strings(var2);
            if (var6 != null) {
               String var3 = CollectionsKt.joinToString$default(var6, ",", null, null, 0, null, null, 62, null);
               var7 = var3;
               if (var3 != null) {
                  break label13;
               }
            }
         }

         var7 = "";
      }

      String var5 = GxPage.str$default(this, var1, "message", null, 2, null);
      return var4 + "|" + var7 + "|" + var5;
   }

   private final void fillSentryConfig() {
      if (this.sCfgBox != null) {
         LinearLayout var6 = this.sCfgBox;
         Object var5 = null;
         LinearLayout var4 = var6;
         if (var6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sCfgBox");
            var4 = null;
         }

         var4.removeAllViews();
         boolean var3 = this.pBool("SentryModeEnabled");
         var6 = this.sCfgBox;
         var4 = var6;
         if (var6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sCfgBox");
            var4 = null;
         }

         this.toggle(
            "Enable Sentry Mode",
            "Detect sustained movement while the vehicle is parked.",
            var3,
            (ViewGroup)var4,
            new GxCamerasPage$$ExternalSyntheticLambda48(this)
         );
         fillSentryConfig$urlRow(this, "SentryModeWebhook", "Webhook URL", "Optional Discord-compatible or custom webhook.", "https://...");
         fillSentryConfig$urlRow(this, "SentryModeNtfyUrl", "ntfy URL", "Optional ntfy topic URL for phone notifications.", "https://ntfy.sh/...");
         var6 = this.sCfgBox;
         var4 = var6;
         if (var6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sCfgBox");
            var4 = null;
         }

         ViewGroup var16 = (ViewGroup)var4;
         Double var10 = this.pNum("SentryModeSensitivity");
         double var1;
         if (var10 != null) {
            var1 = var10;
         } else {
            var1 = 0.04;
         }

         this.numRow(
            var16,
            "Motion sensitivity",
            "Lower values detect smaller acceleration changes. Default: 0.04.",
            var1,
            0.005,
            1.0,
            0.005,
            3,
            "",
            new GxCamerasPage$$ExternalSyntheticLambda49(this)
         );
         var4 = this.sCfgBox;
         if (var4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sCfgBox");
            var4 = (LinearLayout)var5;
         }

         var5 = (ViewGroup)var4;
         Double var12 = this.pNum("SentryModeWarningTime");
         if (var12 != null) {
            var1 = var12;
         } else {
            var1 = 1.0;
         }

         this.numRow(
            (ViewGroup)var5,
            "Warning persistence",
            "How long movement must continue before the first alert. Default: 1 second.",
            var1,
            0.1,
            10.0,
            0.1,
            1,
            " seconds",
            new GxCamerasPage$$ExternalSyntheticLambda50(this)
         );
      }
   }

   private static final Unit fillSentryConfig$lambda$11(GxCamerasPage var0, boolean var1) {
      saveSentryParam$default(var0, "SentryModeEnabled", var1, null, 4, null);
      return Unit.INSTANCE;
   }

   private static final Unit fillSentryConfig$lambda$17(GxCamerasPage var0, double var1) {
      saveSentryParam$default(var0, "SentryModeSensitivity", var1, null, 4, null);
      return Unit.INSTANCE;
   }

   private static final Unit fillSentryConfig$lambda$18(GxCamerasPage var0, double var1) {
      saveSentryParam$default(var0, "SentryModeWarningTime", var1, null, 4, null);
      return Unit.INSTANCE;
   }

   private static final void fillSentryConfig$urlRow(GxCamerasPage var0, String var1, String var2, String var3, String var4) {
      String var6 = StringsKt.trim(var0.pStr(var1)).toString();
      GxPage var8 = var0;
      String var5;
      if (StringsKt.isBlank(var6)) {
         var5 = "Not set";
      } else {
         var5 = var6;
      }

      String var9 = var5 + "\n" + var3;
      LinearLayout var7 = var0.sCfgBox;
      LinearLayout var10 = var7;
      if (var7 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("sCfgBox");
         var10 = null;
      }

      GxPage.Row var11 = GxPage.row$default(var8, var2, var9, (ViewGroup)var10, 0, 8, null);
      GxPage.Row.button$default(var11, "Edit", false, new GxCamerasPage$$ExternalSyntheticLambda24(var0, var2, var4, var1, var3), 2, null);
      if (!StringsKt.isBlank(var6)) {
         GxPage.Row.button$default(var11, "Clear", false, new GxCamerasPage$$ExternalSyntheticLambda25(var0, var1), 2, null);
      }
   }

   private static final Unit fillSentryConfig$urlRow$lambda$14(GxCamerasPage var0, String var1, String var2, String var3, String var4) {
      var0.prompt(var1, var2, StringsKt.trim(var0.pStr(var3)).toString(), 17, var4, new GxCamerasPage$$ExternalSyntheticLambda51(var0, var3));
      return Unit.INSTANCE;
   }

   private static final Unit fillSentryConfig$urlRow$lambda$14$lambda$13(GxCamerasPage var0, String var1, String var2) {
      Intrinsics.checkNotNullParameter(var2, "v");
      var0.saveSentryParam(var1, StringsKt.trim(var2).toString(), new GxCamerasPage$$ExternalSyntheticLambda69(var0));
      return Unit.INSTANCE;
   }

   private static final Unit fillSentryConfig$urlRow$lambda$14$lambda$13$lambda$12(GxCamerasPage var0) {
      var0.fillSentryConfig();
      return Unit.INSTANCE;
   }

   private static final Unit fillSentryConfig$urlRow$lambda$16(GxCamerasPage var0, String var1) {
      var0.saveSentryParam(var1, "", new GxCamerasPage$$ExternalSyntheticLambda55(var0));
      return Unit.INSTANCE;
   }

   private static final Unit fillSentryConfig$urlRow$lambda$16$lambda$15(GxCamerasPage var0) {
      var0.fillSentryConfig();
      return Unit.INSTANCE;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private final String fmtTime(String var1) {
      Object var6;
      label30:
      try {
         var6 = Result.Companion;
         var6 = this;
         var6 = (String)KtCompat.resultOf(
            OffsetDateTime.parse(var1).atZoneSameInstant(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("MMM d, yyyy h:mm:ss a", Locale.US))
         );
      } catch (Throwable var5) {
         var6 = Result.Companion;
         var6 = (String)KtCompat.resultOf(ResultKt.createFailure(var5));
         break label30;
      }

      Object var3 = var6;
      if (KtCompat.isFailure(var6)) {
         var3 = null;
      }

      var3 = (String)var3;
      var6 = (String)var3;
      if (var3 == null) {
         var6 = var1;
      }

      return var6;
   }

   private final void gxCamLoadBitmap(String var1, Function2<? super String, ? super Bitmap, Unit> var2) {
      String var3 = this.getHost();
      if (var3 == null) {
         GxCamerasPage var4 = this;
         var2.invoke("Not connected", null);
      } else {
         new Thread(new GxCamerasPage$$ExternalSyntheticLambda35(var3, var1, this, var2)).start();
      }
   }

   private static final void gxCamLoadBitmap$lambda$73(String param0, String param1, GxCamerasPage param2, Function2 param3) {
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
      // 007: astore 8
      // 009: new kotlin/jvm/internal/Ref$ObjectRef
      // 00c: dup
      // 00d: invokespecial kotlin/jvm/internal/Ref$ObjectRef.<init> ()V
      // 010: astore 9
      // 012: getstatic com/starpilot/starview/GxPage.Companion Lcom/starpilot/starview/GxPage$Companion;
      // 015: ldc2_w 30
      // 018: invokevirtual com/starpilot/starview/GxPage$Companion.client (J)Lokhttp3/OkHttpClient;
      // 01b: astore 6
      // 01d: new okhttp3/Request$Builder
      // 020: astore 7
      // 022: aload 7
      // 024: invokespecial okhttp3/Request$Builder.<init> ()V
      // 027: new java/lang/StringBuilder
      // 02a: astore 10
      // 02c: aload 10
      // 02e: invokespecial java/lang/StringBuilder.<init> ()V
      // 031: aload 10
      // 033: ldc_w "http://"
      // 036: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 039: astore 10
      // 03b: aload 10
      // 03d: aload 0
      // 03e: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 041: ldc_w ":8082"
      // 044: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 047: astore 0
      // 048: aload 6
      // 04a: aload 7
      // 04c: aload 0
      // 04d: aload 1
      // 04e: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 051: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 054: invokevirtual okhttp3/Request$Builder.url (Ljava/lang/String;)Lokhttp3/Request$Builder;
      // 057: ldc_w "Cache-Control"
      // 05a: ldc_w "no-store"
      // 05d: invokevirtual okhttp3/Request$Builder.header (Ljava/lang/String;Ljava/lang/String;)Lokhttp3/Request$Builder;
      // 060: invokevirtual okhttp3/Request$Builder.build ()Lokhttp3/Request;
      // 063: invokevirtual okhttp3/OkHttpClient.newCall (Lokhttp3/Request;)Lokhttp3/Call;
      // 066: invokeinterface okhttp3/Call.execute ()Lokhttp3/Response; 1
      // 06b: checkcast java/io/Closeable
      // 06e: astore 10
      // 070: aload 10
      // 072: checkcast okhttp3/Response
      // 075: astore 11
      // 077: aload 11
      // 079: ldc_w "Content-Type"
      // 07c: aconst_null
      // 07d: bipush 2
      // 07e: aconst_null
      // 07f: invokestatic okhttp3/Response.header$default (Lokhttp3/Response;Ljava/lang/String;Ljava/lang/String;ILjava/lang/Object;)Ljava/lang/String;
      // 082: astore 0
      // 083: ldc_w ""
      // 086: astore 6
      // 088: aload 0
      // 089: astore 1
      // 08a: aload 0
      // 08b: ifnonnull 092
      // 08e: ldc_w ""
      // 091: astore 1
      // 092: aload 11
      // 094: invokevirtual okhttp3/Response.body ()Lokhttp3/ResponseBody;
      // 097: astore 0
      // 098: aload 0
      // 099: ifnull 0aa
      // 09c: aload 0
      // 09d: invokevirtual okhttp3/ResponseBody.bytes ()[B
      // 0a0: astore 7
      // 0a2: aload 7
      // 0a4: astore 0
      // 0a5: aload 7
      // 0a7: ifnonnull 0ae
      // 0aa: bipush 0
      // 0ab: newarray 8
      // 0ad: astore 0
      // 0ae: aload 11
      // 0b0: invokevirtual okhttp3/Response.isSuccessful ()Z
      // 0b3: istore 5
      // 0b5: iload 5
      // 0b7: ifne 162
      // 0ba: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 0bd: astore 1
      // 0be: new org/json/JSONObject
      // 0c1: astore 6
      // 0c3: new java/lang/String
      // 0c6: astore 1
      // 0c7: aload 1
      // 0c8: aload 0
      // 0c9: getstatic kotlin/text/Charsets.UTF_8 Ljava/nio/charset/Charset;
      // 0cc: invokespecial java/lang/String.<init> ([BLjava/nio/charset/Charset;)V
      // 0cf: aload 6
      // 0d1: aload 1
      // 0d2: invokespecial org/json/JSONObject.<init> (Ljava/lang/String;)V
      // 0d5: aload 6
      // 0d7: ldc_w "error"
      // 0da: invokevirtual org/json/JSONObject.optString (Ljava/lang/String;)Ljava/lang/String;
      // 0dd: checkcast java/lang/CharSequence
      // 0e0: astore 1
      // 0e1: aload 1
      // 0e2: astore 0
      // 0e3: aload 1
      // 0e4: invokestatic kotlin/text/StringsKt.isBlank (Ljava/lang/CharSequence;)Z
      // 0e7: ifeq 0f3
      // 0ea: aload 6
      // 0ec: ldc_w "message"
      // 0ef: invokevirtual org/json/JSONObject.optString (Ljava/lang/String;)Ljava/lang/String;
      // 0f2: astore 0
      // 0f3: aload 0
      // 0f4: checkcast java/lang/String
      // 0f7: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 0fa: astore 0
      // 0fb: goto 10b
      // 0fe: astore 0
      // 0ff: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 102: astore 1
      // 103: aload 0
      // 104: invokestatic kotlin/ResultKt.createFailure (Ljava/lang/Throwable;)Ljava/lang/Object;
      // 107: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 10a: astore 0
      // 10b: aload 0
      // 10c: astore 1
      // 10d: aload 0
      // 10e: invokestatic kotlin/Result.isFailure-impl (Ljava/lang/Object;)Z
      // 111: ifeq 116
      // 114: aconst_null
      // 115: astore 1
      // 116: aload 1
      // 117: checkcast java/lang/String
      // 11a: astore 0
      // 11b: aload 0
      // 11c: ifnull 13a
      // 11f: aload 0
      // 120: checkcast java/lang/CharSequence
      // 123: astore 1
      // 124: aload 1
      // 125: astore 0
      // 126: aload 1
      // 127: invokestatic kotlin/text/StringsKt.isBlank (Ljava/lang/CharSequence;)Z
      // 12a: ifeq 12f
      // 12d: aconst_null
      // 12e: astore 0
      // 12f: aload 0
      // 130: checkcast java/lang/String
      // 133: astore 1
      // 134: aload 1
      // 135: astore 0
      // 136: aload 1
      // 137: ifnonnull 159
      // 13a: aload 11
      // 13c: invokevirtual okhttp3/Response.code ()I
      // 13f: istore 4
      // 141: new java/lang/StringBuilder
      // 144: astore 0
      // 145: aload 0
      // 146: invokespecial java/lang/StringBuilder.<init> ()V
      // 149: aload 0
      // 14a: ldc_w "HTTP "
      // 14d: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 150: iload 4
      // 152: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
      // 155: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 158: astore 0
      // 159: aload 8
      // 15b: aload 0
      // 15c: putfield kotlin/jvm/internal/Ref$ObjectRef.element Ljava/lang/Object;
      // 15f: goto 200
      // 162: aload 1
      // 163: checkcast java/lang/CharSequence
      // 166: ldc_w "json"
      // 169: checkcast java/lang/CharSequence
      // 16c: bipush 0
      // 16d: bipush 2
      // 16e: aconst_null
      // 16f: invokestatic kotlin/text/StringsKt.contains$default (Ljava/lang/CharSequence;Ljava/lang/CharSequence;ZILjava/lang/Object;)Z
      // 172: istore 5
      // 174: iload 5
      // 176: ifeq 1f4
      // 179: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 17c: astore 1
      // 17d: new org/json/JSONObject
      // 180: astore 7
      // 182: new java/lang/String
      // 185: astore 1
      // 186: aload 1
      // 187: aload 0
      // 188: getstatic kotlin/text/Charsets.UTF_8 Ljava/nio/charset/Charset;
      // 18b: invokespecial java/lang/String.<init> ([BLjava/nio/charset/Charset;)V
      // 18e: aload 7
      // 190: aload 1
      // 191: invokespecial org/json/JSONObject.<init> (Ljava/lang/String;)V
      // 194: aload 7
      // 196: ldc_w "jpeg"
      // 199: invokevirtual org/json/JSONObject.optString (Ljava/lang/String;)Ljava/lang/String;
      // 19c: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 19f: astore 0
      // 1a0: goto 1b0
      // 1a3: astore 1
      // 1a4: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 1a7: astore 0
      // 1a8: aload 1
      // 1a9: invokestatic kotlin/ResultKt.createFailure (Ljava/lang/Throwable;)Ljava/lang/Object;
      // 1ac: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 1af: astore 0
      // 1b0: aload 0
      // 1b1: astore 1
      // 1b2: aload 0
      // 1b3: invokestatic kotlin/Result.isFailure-impl (Ljava/lang/Object;)Z
      // 1b6: ifeq 1bb
      // 1b9: aconst_null
      // 1ba: astore 1
      // 1bb: aload 1
      // 1bc: checkcast java/lang/String
      // 1bf: astore 0
      // 1c0: aload 0
      // 1c1: ifnonnull 1ca
      // 1c4: aload 6
      // 1c6: astore 0
      // 1c7: goto 1ca
      // 1ca: aload 0
      // 1cb: checkcast java/lang/CharSequence
      // 1ce: invokestatic kotlin/text/StringsKt.isBlank (Ljava/lang/CharSequence;)Z
      // 1d1: ifeq 1df
      // 1d4: aload 8
      // 1d6: ldc_w "Snapshot missing image data"
      // 1d9: putfield kotlin/jvm/internal/Ref$ObjectRef.element Ljava/lang/Object;
      // 1dc: goto 200
      // 1df: aload 0
      // 1e0: bipush 0
      // 1e1: invokestatic android/util/Base64.decode (Ljava/lang/String;I)[B
      // 1e4: astore 0
      // 1e5: aload 9
      // 1e7: aload 0
      // 1e8: bipush 0
      // 1e9: aload 0
      // 1ea: arraylength
      // 1eb: invokestatic android/graphics/BitmapFactory.decodeByteArray ([BII)Landroid/graphics/Bitmap;
      // 1ee: putfield kotlin/jvm/internal/Ref$ObjectRef.element Ljava/lang/Object;
      // 1f1: goto 200
      // 1f4: aload 9
      // 1f6: aload 0
      // 1f7: bipush 0
      // 1f8: aload 0
      // 1f9: arraylength
      // 1fa: invokestatic android/graphics/BitmapFactory.decodeByteArray ([BII)Landroid/graphics/Bitmap;
      // 1fd: putfield kotlin/jvm/internal/Ref$ObjectRef.element Ljava/lang/Object;
      // 200: aload 8
      // 202: getfield kotlin/jvm/internal/Ref$ObjectRef.element Ljava/lang/Object;
      // 205: ifnonnull 218
      // 208: aload 9
      // 20a: getfield kotlin/jvm/internal/Ref$ObjectRef.element Ljava/lang/Object;
      // 20d: ifnonnull 218
      // 210: aload 8
      // 212: ldc_w "Failed to decode image"
      // 215: putfield kotlin/jvm/internal/Ref$ObjectRef.element Ljava/lang/Object;
      // 218: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 21b: astore 0
      // 21c: aload 10
      // 21e: aconst_null
      // 21f: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 222: goto 253
      // 225: astore 1
      // 226: aload 1
      // 227: athrow
      // 228: astore 0
      // 229: aload 10
      // 22b: aload 1
      // 22c: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 22f: aload 0
      // 230: athrow
      // 231: astore 0
      // 232: goto 23a
      // 235: astore 0
      // 236: goto 23a
      // 239: astore 0
      // 23a: aload 0
      // 23b: invokevirtual java/lang/Exception.getMessage ()Ljava/lang/String;
      // 23e: astore 6
      // 240: aload 6
      // 242: astore 1
      // 243: aload 6
      // 245: ifnonnull 24d
      // 248: aload 0
      // 249: invokevirtual java/lang/Exception.toString ()Ljava/lang/String;
      // 24c: astore 1
      // 24d: aload 8
      // 24f: aload 1
      // 250: putfield kotlin/jvm/internal/Ref$ObjectRef.element Ljava/lang/Object;
      // 253: aload 2
      // 254: invokevirtual com/starpilot/starview/GxCamerasPage.getMain ()Landroid/os/Handler;
      // 257: new com/starpilot/starview/GxCamerasPage$$ExternalSyntheticLambda65
      // 25a: dup
      // 25b: aload 3
      // 25c: aload 8
      // 25e: aload 9
      // 260: invokespecial com/starpilot/starview/GxCamerasPage$$ExternalSyntheticLambda65.<init> (Lkotlin/jvm/functions/Function2;Lkotlin/jvm/internal/Ref$ObjectRef;Lkotlin/jvm/internal/Ref$ObjectRef;)V
      // 263: invokevirtual android/os/Handler.post (Ljava/lang/Runnable;)Z
      // 266: pop
      // 267: return
   }

   private static final void gxCamLoadBitmap$lambda$73$lambda$72(Function2 var0, ObjectRef var1, ObjectRef var2) {
      var0.invoke(var1.element, var2.element);
   }

   private final void imageStrip(LinearLayout var1, List<String> var2, String var3) {
      LinearLayout var9 = new LinearLayout((Context)this.getAct());
      var9.setOrientation(0);
      var9.setPadding(0, this.getUi().dp(8), 0, this.getUi().dp(4));
      Iterator var10 = var2.iterator();
      int var4 = 0;

      while (var10.hasNext()) {
         int var5 = var4 + 1;
         String var6 = (String)var10.next();
         ImageView var8 = new ImageView((Context)this.getAct());
         var8.setScaleType(ScaleType.FIT_CENTER);
         var8.setBackground((Drawable)CarUi.rounded$default(this.getUi(), Color.rgb(6, 8, 11), 12, null, 4, null));
         var8.setContentDescription(var3 + " " + (var4 + 1));
         var8.setOnClickListener(new GxCamerasPage$$ExternalSyntheticLambda32(this, var6, var3, var4));
         View var11 = (View)var8;
         android.widget.LinearLayout.LayoutParams var12 = new android.widget.LinearLayout.LayoutParams(0, this.getUi().dp(220), 1.0F);
         if (var4 < var2.size() - 1) {
            var12.rightMargin = this.getUi().dp(10);
         }

         Unit var7 = Unit.INSTANCE;
         var9.addView(var11, (LayoutParams)var12);
         GxPage.loadImage$default(this, var6, var8, null, 4, null);
         var4 = var5;
      }

      var1.addView((View)var9);
   }

   private static final void imageStrip$lambda$39(GxCamerasPage var0, String var1, String var2, int var3, View var4) {
      var0.openImage(var1, var2 + " " + (var3 + 1));
   }

   private final void kindLine(LinearLayout var1, JSONObject var2, String var3, Function0<Unit> var4) {
      LinearLayout var7 = new LinearLayout((Context)this.getAct());
      var7.setOrientation(0);
      var7.setGravity(16);
      var7.setPadding(0, 0, 0, this.getUi().dp(6));
      CharSequence var6 = GxPage.str$default(this, var2, "kind", null, 2, null);
      TextView var10 = var6;
      if (StringsKt.isBlank(var6)) {
         var10 = "event";
      }

      int var5;
      String var8;
      label35: {
         var8 = (String)var10;
         switch (var8.hashCode()) {
            case -906020504:
               if (var8.equals("selfie")) {
                  var5 = CarUi.Companion.getACCENT();
                  break label35;
               }
               break;
            case 92895825:
               if (var8.equals("alarm")) {
                  var5 = GxCamerasKt.access$getGX_CAM_RED$p();
                  break label35;
               }
               break;
            case 1124446108:
               if (var8.equals("warning")) {
                  var5 = CarUi.Companion.getWARN();
                  break label35;
               }
         }

         var5 = CarUi.Companion.getDIM();
      }

      var10 = new TextView((Context)this.getAct());
      Locale var17 = Locale.US;
      Intrinsics.checkNotNullExpressionValue(var17, "US");
      String var18 = var8.toUpperCase(var17);
      Intrinsics.checkNotNullExpressionValue(var18, "toUpperCase(...)");
      var10.setText(var18);
      var10.setTextSize(14.0F);
      var10.setTypeface(Typeface.DEFAULT_BOLD);
      var10.setTextColor(var5);
      var10.setBackground((Drawable)this.getUi().rounded(CarUi.Companion.getCARD_ON(), 10, var5));
      var10.setPadding(this.getUi().dp(12), this.getUi().dp(5), this.getUi().dp(12), this.getUi().dp(5));
      TextView var19 = new TextView((Context)this.getAct());
      var19.setText(var3);
      var19.setTextSize(17.0F);
      var19.setTextColor(CarUi.Companion.getTEXT());
      var19.setPadding(this.getUi().dp(12), 0, 0, 0);
      if (var4 != null) {
         var19.setTypeface(Typeface.DEFAULT_BOLD);
         View var14 = (View)var19;
         android.widget.LinearLayout.LayoutParams var9 = new android.widget.LinearLayout.LayoutParams(0, -2, 1.0F);
         Unit var20 = Unit.INSTANCE;
         var7.addView(var14, (LayoutParams)var9);
         var19.setPadding(0, 0, this.getUi().dp(12), 0);
         var7.addView((View)var10);
      } else {
         var7.addView((View)var10);
         var7.addView((View)var19, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(0, -2, 1.0F)));
      }

      if (var4 != null) {
         var10 = CarUi.button$default(this.getUi(), "Delete", false, new GxCamerasPage$$ExternalSyntheticLambda70(var4), 2, null);
         var10.setTextColor(GxCamerasKt.access$getGX_CAM_RED$p());
         View var15 = (View)var10;
         android.widget.LinearLayout.LayoutParams var16 = new android.widget.LinearLayout.LayoutParams(-2, -2);
         var16.leftMargin = this.getUi().dp(12);
         Unit var13 = Unit.INSTANCE;
         var7.addView(var15, (LayoutParams)var16);
      }

      var1.addView((View)var7);
   }

   // $VF: synthetic method
   static void kindLine$default(GxCamerasPage var0, LinearLayout var1, JSONObject var2, String var3, Function0 var4, int var5, Object var6) {
      if ((var5 & 8) != 0) {
         var4 = null;
      }

      var0.kindLine(var1, var2, var3, var4);
   }

   private static final Unit kindLine$lambda$34(Function0 var0) {
      var0.invoke();
      return Unit.INSTANCE;
   }

   private final void loadHistory() {
      this.sHistoryBusy = true;
      if (this.sHistoryVisible) {
         TextView var2 = this.sHistBtn;
         TextView var1 = var2;
         if (var2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sHistBtn");
            var1 = null;
         }

         this.busy(var1, true, "Hide history", "Loading...");
      }

      this.getJson("/api/sentry/events", new GxCamerasPage$$ExternalSyntheticLambda52(this));
   }

   private static final Unit loadHistory$lambda$55(GxCamerasPage var0, String var1, JSONObject var2) {
      var0.sHistoryBusy = false;
      TextView var4 = var0.sHistBtn;
      TextView var3 = var4;
      if (var4 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("sHistBtn");
         var3 = null;
      }

      String var8;
      if (var0.sHistoryVisible) {
         var8 = "Hide history";
      } else {
         var8 = "View history";
      }

      var0.busy(var3, false, var8, "Loading...");
      if (var1 == null && var2 != null) {
         label21: {
            JSONArray var5 = var2.optJSONArray("events");
            if (var5 != null) {
               List var7 = var0.objects(var5);
               var6 = var7;
               if (var7 != null) {
                  break label21;
               }
            }

            var6 = CollectionsKt.emptyList();
         }

         var0.sHistory = var6;
         renderHistory$default(var0, false, 1, null);
         return Unit.INSTANCE;
      } else {
         var0.toast("Failed to load Sentry history.");
         return Unit.INSTANCE;
      }
   }

   private final void loadPipConfig() {
      if (this.pView != null) {
         this.getJson("/api/pip_preview/config", new GxCamerasPage$$ExternalSyntheticLambda39(this));
      }
   }

   private static final Unit loadPipConfig$lambda$132(GxCamerasPage var0, String var1, JSONObject var2) {
      String var4 = null;
      Object var3 = null;
      if (var1 == null && var2 != null) {
         var4 = GxPage.str$default(var0, var2, "device_type", null, 2, null);
         CharSequence var5 = (CharSequence)var4;
         if (StringsKt.isBlank((CharSequence)var4)) {
            var5 = null;
         }

         var4 = (String)var5;
         if (var4 != null) {
            GxCamerasPipView var6 = var0.pView;
            if (var6 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("pView");
               var6 = (GxCamerasPipView)var3;
            }

            var3 = Locale.US;
            Intrinsics.checkNotNullExpressionValue(var3, "US");
            var3 = var4.toLowerCase((Locale)var3);
            Intrinsics.checkNotNullExpressionValue(var3, "toLowerCase(...)");
            var6.setC4(Intrinsics.areEqual(var3, "mici"));
         }

         var0.pConfig = var2.optJSONObject("mask");
         var0.pApplyConfig();
         return Unit.INSTANCE;
      } else {
         if (var1 != null) {
            var3 = var0.pMsg;
            TextView var7 = (TextView)var3;
            if (var3 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("pMsg");
               var7 = null;
            }

            if (var7.getVisibility() != 0) {
               TextView var8 = var0.pMsg;
               if (var8 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("pMsg");
                  var8 = var4;
               }

               var0.msg(var8, var1, true);
            }
         }

         return Unit.INSTANCE;
      }
   }

   private final void loadPipSnapshot() {
      if (this.pView != null && !this.pSnapLoading) {
         TextView var2 = this.pMsg;
         TextView var1 = var2;
         if (var2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pMsg");
            var1 = null;
         }

         this.msg(var1, "", false);
         this.pSnapLoading = true;
         GxCamerasPipView var5 = this.pView;
         GxCamerasPipView var3 = var5;
         if (var5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pView");
            var3 = null;
         }

         var3.setPlaceholder("Loading camera snapshot...");
         GxCamerasPipView var6 = this.pView;
         GxCamerasPipView var4 = var6;
         if (var6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pView");
            var4 = null;
         }

         var4.setBmp(null);
         this.pPaint();
         this.gxCamLoadBitmap("/api/pip_preview/snapshot", new GxCamerasPage$$ExternalSyntheticLambda42(this));
      }
   }

   private static final Unit loadPipSnapshot$lambda$136(GxCamerasPage var0, String var1, Bitmap var2) {
      var0.pSnapLoading = false;
      Object var3 = null;
      Object var4 = null;
      if (var2 == null) {
         var3 = var0.pView;
         GxCamerasPipView var8 = (GxCamerasPipView)var3;
         if (var3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pView");
            var8 = null;
         }

         String var5 = "Failed to load snapshot";
         if (var1 == null) {
            var3 = "Failed to load snapshot";
         } else {
            var3 = var1;
         }

         var8.setPlaceholder((String)var3);
         TextView var9 = var0.pMsg;
         if (var9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pMsg");
            var9 = (TextView)var4;
         }

         if (var1 == null) {
            var1 = var5;
         }

         var0.msg(var9, var1, true);
         var0.pPaint();
         return Unit.INSTANCE;
      } else {
         var4 = var0.pView;
         GxCamerasPipView var6 = (GxCamerasPipView)var4;
         if (var4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pView");
            var6 = null;
         }

         var6.setBmp(var2);
         TextView var7 = var0.pMsg;
         if (var7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pMsg");
            var7 = (TextView)var3;
         }

         var0.msg(var7, "Camera snapshot loaded. Place a center point on each window, then adjust the zoom.", false);
         var0.pApplyConfig();
         return Unit.INSTANCE;
      }
   }

   private final void loadSentryParams() {
      GalaxyApi var1 = this.getApi();
      if (var1 != null) {
         var1.refreshValues(new GxCamerasPage$$ExternalSyntheticLambda26(this));
      }
   }

   private static final Unit loadSentryParams$lambda$9(GxCamerasPage var0, boolean var1) {
      if (!var1 && !var0.sLoaded) {
         var0.toast("Failed to load Sentry settings.");
      }

      var0.sLoaded = true;
      var0.fillSentryConfig();
      return Unit.INSTANCE;
   }

   private final void loadSentryStatus() {
      if (this.sState != null) {
         this.getJson("/api/sentry/status", new GxCamerasPage$$ExternalSyntheticLambda16(this));
      }
   }

   private static final Unit loadSentryStatus$lambda$26(GxCamerasPage var0, String var1, JSONObject var2) {
      if (var1 == null && var2 != null) {
         var0.sStatusFailed = false;
         JSONObject var5 = var2.optJSONObject("status");
         JSONObject var3 = var5;
         if (var5 == null) {
            var3 = new JSONObject();
         }

         TextView var4 = var0.sState;
         TextView var6 = var4;
         if (var4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sState");
            var6 = null;
         }

         String var10 = "unknown";
         var3 = var0.str(var3, "state", "unknown");
         if (StringsKt.isBlank(var3)) {
            var3 = var10;
         }

         var6.setText((CharSequence)var3);
         var2 = var2.optJSONObject("lastEvent");
         JSONObject var7 = var2;
         if (var2 == null) {
            var7 = new JSONObject();
         }

         var0.sEvent = var7;
         renderLatest$default(var0, false, 1, null);
         if (var0.sHistoryVisible && !var0.sHistoryBusy) {
            var0.loadHistory();
         }

         return Unit.INSTANCE;
      } else {
         if (var0.sLoaded && !var0.sStatusFailed) {
            var0.toast("Failed to load Sentry status.");
         }

         var0.sStatusFailed = true;
         return Unit.INSTANCE;
      }
   }

   private final void loadTab(int var1, boolean var2) {
      switch (var1) {
         case 0:
            this.loadSentryParams();
            if (!var2) {
               this.loadSentryStatus();
            }
            break;
         case 1:
            this.loadVasmConfig();
            this.loadVasmSnapshot();
            break;
         default:
            this.loadPipConfig();
            this.loadPipSnapshot();
      }
   }

   // $VF: synthetic method
   static void loadTab$default(GxCamerasPage var0, int var1, boolean var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = false;
      }

      var0.loadTab(var1, var2);
   }

   private final void loadVasmConfig() {
      if (this.vView != null) {
         GxPage.json$default(this, "GET", "/api/v_asm/config", null, 0L, new GxCamerasPage$$ExternalSyntheticLambda30(this), 12, null);
      }
   }

   private static final Unit loadVasmConfig$lambda$102(GxCamerasPage var0, String var1, Object var2) {
      Object var4 = null;
      JSONObject var3 = (JSONObject)var4;
      if (var1 == null) {
         var3 = (JSONObject)var4;
         if (var2 instanceof JSONObject) {
            var3 = (JSONObject)var2;
         }
      }

      var0.vConfig = var3;
      var0.vApplyConfig();
      return Unit.INSTANCE;
   }

   private final void loadVasmSnapshot() {
      if (this.vView != null && !this.vSnapLoading) {
         TextView var2 = this.vMsg;
         TextView var1 = var2;
         if (var2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vMsg");
            var1 = null;
         }

         this.msg(var1, "", false);
         this.vSnapLoading = true;
         GxCamerasRegionView var5 = this.vView;
         GxCamerasRegionView var3 = var5;
         if (var5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vView");
            var3 = null;
         }

         var3.setPlaceholder("Loading camera snapshot...");
         GxCamerasRegionView var6 = this.vView;
         GxCamerasRegionView var4 = var6;
         if (var6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vView");
            var4 = null;
         }

         var4.setBmp(null);
         this.vPaint();
         this.gxCamLoadBitmap("/api/v_asm/snapshot", new GxCamerasPage$$ExternalSyntheticLambda31(this));
      }
   }

   private static final Unit loadVasmSnapshot$lambda$105(GxCamerasPage var0, String var1, Bitmap var2) {
      var0.vSnapLoading = false;
      Object var3 = null;
      Object var4 = null;
      if (var2 == null) {
         GxCamerasRegionView var6 = var0.vView;
         if (var6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vView");
            var6 = (GxCamerasRegionView)var4;
         }

         var4 = "Failed to load snapshot";
         if (var1 == null) {
            var3 = "Failed to load snapshot";
         } else {
            var3 = var1;
         }

         var6.setPlaceholder((String)var3);
         if (var1 == null) {
            var1 = (String)var4;
         }

         var0.vError(var1);
         var0.vPaint();
         return Unit.INSTANCE;
      } else {
         GxCamerasRegionView var5 = var0.vView;
         if (var5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vView");
            var5 = (GxCamerasRegionView)var3;
         }

         var5.setBmp(var2);
         var0.vSuccess("Camera snapshot loaded. Tap a window button above to start annotating.");
         var0.vApplyConfig();
         return Unit.INSTANCE;
      }
   }

   private final void msg(TextView var1, String var2, boolean var3) {
      var1.setText(var2);
      int var4;
      if (var3) {
         var4 = GxCamerasKt.access$getGX_CAM_RED$p();
      } else {
         var4 = CarUi.Companion.getTEXT();
      }

      var1.setTextColor(var4);
      CarUi var6 = this.getUi();
      int var5 = CarUi.Companion.getCARD();
      if (var3) {
         var4 = GxCamerasKt.access$getGX_CAM_RED$p();
      } else {
         var4 = CarUi.Companion.getACCENT();
      }

      var1.setBackground((Drawable)var6.rounded(var5, 12, var4));
      byte var8;
      if (StringsKt.isBlank(var2)) {
         var8 = 8;
      } else {
         var8 = 0;
      }

      var1.setVisibility(var8);
   }

   private final TextView msgView(LinearLayout var1) {
      TextView var2 = new TextView((Context)this.getAct());
      var2.setTextSize(17.0F);
      var2.setPadding(this.getUi().dp(18), this.getUi().dp(12), this.getUi().dp(18), this.getUi().dp(12));
      var2.setVisibility(8);
      var1.addView((View)var2, (LayoutParams)CarUi.lp$default(this.getUi(), 0, 0, 3, null));
      return var2;
   }

   private final void numRow(
      ViewGroup var1,
      String var2,
      String var3,
      double var4,
      double var6,
      double var8,
      double var10,
      int var12,
      String var13,
      Function1<? super Double, Unit> var14
   ) {
      GxPage.Row var17 = GxPage.row$default(this, var2, var3, var1, 0, 8, null);
      DoubleRef var16 = new DoubleRef();
      var16.element = var4;
      TextView var15 = new TextView((Context)this.getAct());
      var15.setTextSize(22.0F);
      var15.setTextColor(CarUi.Companion.getTEXT());
      var15.setTypeface(Typeface.DEFAULT_BOLD);
      var15.setMinWidth(this.getUi().dp(110));
      var15.setGravity(17);
      var15.setPadding(this.getUi().dp(6), this.getUi().dp(10), this.getUi().dp(6), this.getUi().dp(10));
      var15.setOnClickListener(new GxCamerasPage$$ExternalSyntheticLambda22(this, var2, var6, var8, var16, var3, var12, var14, var15, var13));
      var17.add((View)this.getUi().stepper("−", new GxCamerasPage$$ExternalSyntheticLambda33(var16, var10, var12, var6, var8, var14, var15, var13)));
      var17.add((View)var15);
      var17.add((View)this.getUi().stepper("+", new GxCamerasPage$$ExternalSyntheticLambda44(var16, var10, var12, var6, var8, var14, var15, var13)));
      numRow$paint(var15, var16, var13, var12);
   }

   private static final String numRow$fmt(int var0, double var1) {
      StringCompanionObject var3 = StringCompanionObject.INSTANCE;
      String var4 = String.format(Locale.US, "%." + var0 + "f", Arrays.copyOf(new Object[]{var1}, 1));
      Intrinsics.checkNotNullExpressionValue(var4, "format(...)");
      return var4;
   }

   private static final void numRow$lambda$22(
      GxCamerasPage var0,
      String var1,
      double var2,
      double var4,
      DoubleRef var6,
      String var7,
      int var8,
      Function1 var9,
      TextView var10,
      String var11,
      View var12
   ) {
      String var14 = numRow$fmt(var8, var2);
      String var13 = numRow$fmt(var8, var4);
      var0.prompt(
         var1,
         var14 + " – " + var13,
         numRow$fmt(var8, var6.element),
         8194,
         var7,
         new GxCamerasPage$$ExternalSyntheticLambda37(var0, var8, var6, var2, var4, var9, var10, var11)
      );
   }

   private static final Unit numRow$lambda$22$lambda$21(
      GxCamerasPage var0, int var1, DoubleRef var2, double var3, double var5, Function1 var7, TextView var8, String var9, String var10
   ) {
      Intrinsics.checkNotNullParameter(var10, "s");
      Double var11 = StringsKt.toDoubleOrNull(StringsKt.trim(var10).toString());
      if (var11 != null) {
         numRow$write(var1, var2, var3, var5, var7, var8, var9, var11.doubleValue());
      } else {
         var0.toast("Not a number");
      }

      return Unit.INSTANCE;
   }

   private static final Unit numRow$lambda$23(DoubleRef var0, double var1, int var3, double var4, double var6, Function1 var8, TextView var9, String var10) {
      numRow$write(var3, var0, var4, var6, var8, var9, var10, var0.element - var1);
      return Unit.INSTANCE;
   }

   private static final Unit numRow$lambda$24(DoubleRef var0, double var1, int var3, double var4, double var6, Function1 var8, TextView var9, String var10) {
      numRow$write(var3, var0, var4, var6, var8, var9, var10, var0.element + var1);
      return Unit.INSTANCE;
   }

   private static final void numRow$paint(TextView var0, DoubleRef var1, String var2, int var3) {
      String var4 = numRow$fmt(var3, var1.element);
      var0.setText(var4 + var2);
   }

   private static final void numRow$write(
      int var0, DoubleRef var1, double var2, double var4, Function1<? super Double, Unit> var6, TextView var7, String var8, double var9
   ) {
      double var11 = Math.pow(10.0, var0);
      var1.element = RangesKt.coerceIn(Math.round(RangesKt.coerceIn(var9, var2, var4) * var11) / var11, var2, var4);
      numRow$paint(var7, var1, var8, var0);
      var6.invoke(var1.element);
   }

   private final void openImage(String var1, String var2) {
      Dialog var3 = new Dialog((Context)this.getAct(), 16973834);
      FrameLayout var6 = new FrameLayout((Context)this.getAct());
      var6.setBackgroundColor(-16777216);
      ImageView var7 = new ImageView((Context)this.getAct());
      var7.setScaleType(ScaleType.FIT_CENTER);
      var6.addView((View)var7, (LayoutParams)(new android.widget.FrameLayout.LayoutParams(-1, -1)));
      LinearLayout var4 = new LinearLayout((Context)this.getAct());
      var4.setOrientation(0);
      var4.setGravity(16);
      var4.setPadding(this.getUi().dp(20), this.getUi().dp(14), this.getUi().dp(20), this.getUi().dp(14));
      var4.setBackgroundColor(Color.argb(160, 0, 0, 0));
      TextView var5 = new TextView((Context)this.getAct());
      var5.setText(var2);
      var5.setTextSize(20.0F);
      var5.setTextColor(-1);
      var5.setTypeface(Typeface.DEFAULT_BOLD);
      var4.addView((View)var5, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(0, -2, 1.0F)));
      var4.addView((View)CarUi.button$default(this.getUi(), "Close", false, new GxCamerasPage$$ExternalSyntheticLambda43(var3), 2, null));
      var6.addView((View)var4, (LayoutParams)(new android.widget.FrameLayout.LayoutParams(-1, -2, 48)));
      var7.setOnClickListener(new GxCamerasPage$$ExternalSyntheticLambda45(var3));
      var3.setContentView((View)var6);
      var3.show();
      this.loadImage(var1, var7, new GxCamerasPage$$ExternalSyntheticLambda46(this));
   }

   private static final Unit openImage$lambda$45(Dialog var0) {
      var0.dismiss();
      return Unit.INSTANCE;
   }

   private static final void openImage$lambda$46(Dialog var0, View var1) {
      var0.dismiss();
   }

   private static final Unit openImage$lambda$47(GxCamerasPage var0, Bitmap var1) {
      if (var1 == null) {
         var0.toast("Could not load the image.");
      }

      return Unit.INSTANCE;
   }

   private final void pApplyConfig() {
      GxCamerasPipView var10 = this.pView;
      GxCamerasPipView var9 = var10;
      if (var10 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("pView");
         var9 = null;
      }

      JSONObject var14 = this.pConfig;
      boolean var8 = false;
      if (var14 == null) {
         var9.setLeftC(null);
         var9.setRightC(null);
         this.pConfigExists = false;
         this.pPaint();
      } else {
         Pair var11 = this.pNative();
         float var6 = ((Number)var11.component1()).floatValue();
         float var3 = ((Number)var11.component2()).floatValue();
         float var4 = var9.getCw();
         float var5 = var9.getCh();
         var9.setLeftC(pApplyConfig$toCanvas$133(var4, var6, var5, var3, var14.optJSONArray("center_right")));
         var9.setRightC(pApplyConfig$toCanvas$133(var4, var6, var5, var3, var14.optJSONArray("center_left")));
         if (var14.has("crop_size")) {
            Double var15 = var14.optDouble("crop_size");
            double var1 = var15.doubleValue();
            boolean var7;
            if (!Double.isInfinite(var1) && !Double.isNaN(var1)) {
               var7 = true;
            } else {
               var7 = false;
            }

            if (!var7) {
               var15 = null;
            }

            if (var15 != null) {
               var1 = var15.doubleValue();
               var1 = GxCamerasKt.access$gxCamRound(var4 * var1 / var6);
               var9.setZoom((float)var1);
               Function1 var16 = this.pZoomSet;
               if (var16 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("pZoomSet");
                  var16 = null;
               }

               var16.invoke(var1);
            }
         }

         if (var9.getLeftC() != null || var9.getRightC() != null) {
            var8 = true;
         }

         this.pConfigExists = var8;
         this.pPaint();
      }
   }

   private static final float[] pApplyConfig$toCanvas$133(float var0, float var1, float var2, float var3, JSONArray var4) {
      float[] var6;
      if (var4 != null && var4.length() >= 2) {
         float[] var5 = new float[]{
            GxCamerasKt.access$gxCamRound(var0 - var4.optDouble(0) * var0 / var1), GxCamerasKt.access$gxCamRound(var4.optDouble(1) * var2 / var3)
         };
         var6 = var5;
      } else {
         var6 = null;
      }

      return var6;
   }

   private final void pArm(String var1) {
      GxCamerasPipView var4 = this.pView;
      Object var3 = null;
      GxCamerasPipView var2 = var4;
      if (var4 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("pView");
         var2 = null;
      }

      var2.setArmSide(var1);
      TextView var5 = this.pMsg;
      if (var5 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("pMsg");
         var5 = (TextView)var3;
      }

      this.msg(var5, "", false);
      this.pPaint();
   }

   private final void pClearAll() {
      GxCamerasPipView var2 = this.pView;
      GxCamerasPipView var1 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("pView");
         var1 = null;
      }

      var1.setLeftC(null);
      var2 = this.pView;
      var1 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("pView");
         var1 = null;
      }

      var1.setRightC(null);
      var2 = this.pView;
      var1 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("pView");
         var1 = null;
      }

      var1.setArmSide(null);
      this.pConfigSaved = false;
      this.pPaint();
   }

   private final void pDelete() {
      this.confirm(
         "Confirm Delete", "Are you sure you want to delete the PiP Side Camera mask?", "Yes, Delete", new GxCamerasPage$$ExternalSyntheticLambda36(this)
      );
   }

   private static final Unit pDelete$lambda$129(GxCamerasPage var0) {
      var0.pLoading = true;
      TextView var2 = var0.pMsg;
      TextView var1 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("pMsg");
         var1 = null;
      }

      var0.msg(var1, "", false);
      var0.pPaint();
      GxPage.send$default(var0, "DELETE", "/api/pip_preview/config", null, 0L, new GxCamerasPage$$ExternalSyntheticLambda23(var0), 12, null);
      return Unit.INSTANCE;
   }

   private static final Unit pDelete$lambda$129$lambda$128(GxCamerasPage var0, String var1, JSONObject var2) {
      var0.pLoading = false;
      Object var10 = null;
      Object var3 = null;
      if (var1 != null) {
         var10 = var0.pMsg;
         if (var10 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pMsg");
            var10 = var3;
         }

         CharSequence var4 = var1;
         var3 = var4;
         if (StringsKt.isBlank(var4)) {
            var3 = "Failed to delete";
         }

         var0.msg((TextView)var10, (String)var3, true);
         var10 = var1;
         Object var9 = var10;
         if (StringsKt.isBlank((CharSequence)var10)) {
            var9 = "Delete failed...";
         }

         var0.toast((String)var9);
         var0.pPaint();
         return Unit.INSTANCE;
      } else {
         var3 = var0.pView;
         GxCamerasPipView var5 = (GxCamerasPipView)var3;
         if (var3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pView");
            var5 = null;
         }

         var5.setLeftC(null);
         var3 = var0.pView;
         GxCamerasPipView var6 = (GxCamerasPipView)var3;
         if (var3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pView");
            var6 = null;
         }

         var6.setRightC(null);
         var3 = var0.pView;
         GxCamerasPipView var7 = (GxCamerasPipView)var3;
         if (var3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pView");
            var7 = null;
         }

         var7.setArmSide(null);
         var0.pConfigSaved = false;
         var0.pConfigExists = false;
         var0.pConfig = null;
         TextView var8 = var0.pMsg;
         if (var8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pMsg");
            var8 = (TextView)var10;
         }

         var0.msg(var8, "PiP Preview mask cleared.", false);
         var0.toast("PiP Preview mask cleared.");
         var0.pPaint();
         return Unit.INSTANCE;
      }
   }

   private final Pair<Float, Float> pNative() {
      GxCamerasPipView var5 = this.pView;
      Object var4 = null;
      GxCamerasPipView var3 = var5;
      if (var5 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("pView");
         var3 = null;
      }

      Bitmap var7 = var3.getBmp();
      int var2;
      if (var7 != null) {
         var2 = var7.getWidth();
      } else {
         var2 = 1920;
      }

      float var1 = var2;
      var3 = this.pView;
      if (var3 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("pView");
         var3 = (GxCamerasPipView)var4;
      }

      Bitmap var9 = var3.getBmp();
      if (var9 != null) {
         var2 = var9.getHeight();
      } else {
         var2 = 1080;
      }

      return TuplesKt.to(var1, (float)var2);
   }

   private final void pPaint() {
      if (this.pView != null) {
         GxCamerasPipView var5 = this.pView;
         Object var6 = null;
         GxCamerasPipView var4 = var5;
         if (var5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pView");
            var4 = null;
         }

         String var8 = var4.getArmSide();
         TextView var29 = this.pLeftBtn;
         TextView var10 = var29;
         if (var29 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pLeftBtn");
            var10 = null;
         }

         this.tint(var10, GxCamerasKt.access$getGX_CAM_BLUE$p(), Intrinsics.areEqual(var8, "left"));
         TextView var30 = this.pRightBtn;
         TextView var11 = var30;
         if (var30 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pRightBtn");
            var11 = null;
         }

         this.tint(var11, GxCamerasKt.access$getGX_CAM_ORANGE$p(), Intrinsics.areEqual(var8, "right"));
         TextView var31 = this.pLeftBtn;
         TextView var12 = var31;
         if (var31 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pLeftBtn");
            var12 = null;
         }

         GxCamerasPipView var7 = this.pView;
         var5 = var7;
         if (var7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pView");
            var5 = null;
         }

         String var33;
         if (var5.getLeftC() != null) {
            var33 = "Move Left Side of Vehicle - Left Here";
         } else {
            var33 = "Set Left Side of Vehicle - Left Here";
         }

         var12.setText(var33);
         TextView var34 = this.pRightBtn;
         TextView var13 = var34;
         if (var34 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pRightBtn");
            var13 = null;
         }

         var7 = this.pView;
         var5 = var7;
         if (var7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pView");
            var5 = null;
         }

         String var36;
         if (var5.getRightC() != null) {
            var36 = "Move Right Side of Vehicle - Right Here";
         } else {
            var36 = "Set Right Side of Vehicle - Right Here";
         }

         var13.setText(var36);
         var5 = this.pView;
         var4 = var5;
         if (var5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pView");
            var4 = null;
         }

         boolean var2;
         label145: {
            if (var4.getLeftC() == null) {
               var5 = this.pView;
               var4 = var5;
               if (var5 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("pView");
                  var4 = null;
               }

               if (var4.getRightC() == null) {
                  var2 = 0;
                  break label145;
               }
            }

            var2 = 1;
         }

         TextView var39 = this.pSaveBtn;
         TextView var16 = var39;
         if (var39 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pSaveBtn");
            var16 = null;
         }

         String var40;
         if (this.pLoading) {
            var40 = "Saving...";
         } else {
            var40 = "Save Mask";
         }

         var16.setText(var40);
         TextView var41 = this.pSaveBtn;
         TextView var17 = var41;
         if (var41 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pSaveBtn");
            var17 = null;
         }

         boolean var3;
         if (!this.pLoading && var2) {
            var3 = true;
         } else {
            var3 = false;
         }

         var17.setEnabled(var3);
         TextView var42 = this.pSaveBtn;
         TextView var18 = var42;
         if (var42 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pSaveBtn");
            var18 = null;
         }

         TextView var56 = this.pSaveBtn;
         TextView var43 = var56;
         if (var56 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pSaveBtn");
            var43 = null;
         }

         float var1;
         if (var43.isEnabled()) {
            var1 = 1.0F;
         } else {
            var1 = 0.45F;
         }

         var18.setAlpha(var1);
         TextView var44 = this.pDeleteBtn;
         TextView var19 = var44;
         if (var44 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pDeleteBtn");
            var19 = null;
         }

         this.showIf((View)var19, this.pConfigExists);
         TextView var45 = this.pDeleteBtn;
         TextView var20 = var45;
         if (var45 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pDeleteBtn");
            var20 = null;
         }

         var20.setEnabled(true ^ this.pLoading);
         TextView var46 = this.pSnapBtn;
         TextView var21 = var46;
         if (var46 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pSnapBtn");
            var21 = null;
         }

         this.busy(var21, this.pSnapLoading, "Get a new Snapshot", "Loading...");
         if (var8 != null) {
            if (Intrinsics.areEqual(var8, "left")) {
               var2 = GxCamerasKt.access$getGX_CAM_BLUE$p();
            } else {
               var2 = GxCamerasKt.access$getGX_CAM_ORANGE$p();
            }

            TextView var47 = this.pArmNote;
            TextView var22 = var47;
            if (var47 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("pArmNote");
               var22 = null;
            }

            String var48;
            if (Intrinsics.areEqual(var8, "left")) {
               var48 = "LEFT SIDE OF VEHICLE - LEFT HERE";
            } else {
               var48 = "RIGHT SIDE OF VEHICLE - RIGHT HERE";
            }

            var22.setText(var48 + "\nTap the matching side of the mirrored preview");
            TextView var49 = this.pArmNote;
            TextView var23 = var49;
            if (var49 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("pArmNote");
               var23 = null;
            }

            var23.setTextColor(var2);
            TextView var50 = this.pArmNote;
            TextView var24 = var50;
            if (var50 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("pArmNote");
               var24 = null;
            }

            var24.setBackground((Drawable)this.getUi().rounded(CarUi.Companion.getCARD(), 12, var2));
            TextView var51 = this.pArmNote;
            TextView var25 = var51;
            if (var51 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("pArmNote");
               var25 = null;
            }

            var25.setVisibility(0);
         } else {
            TextView var52 = this.pArmNote;
            TextView var26 = var52;
            if (var52 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("pArmNote");
               var26 = null;
            }

            var26.setVisibility(8);
         }

         TextView var53 = this.pCaption;
         TextView var27 = var53;
         if (var53 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pCaption");
            var27 = null;
         }

         String var54;
         if (var8 != null) {
            var54 = "Tap the window to place its center point.";
         } else {
            var54 = "Place a center point on each window, then use the zoom control below.";
         }

         var27.setText(var54);
         var4 = this.pView;
         if (var4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pView");
            var4 = (GxCamerasPipView)var6;
         }

         var4.invalidate();
      }
   }

   private final void pSave() {
      GxCamerasPipView var8 = this.pView;
      Object var6 = null;
      Object var7 = null;
      GxCamerasPipView var5 = var8;
      if (var8 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("pView");
         var5 = null;
      }

      if (var5.getLeftC() == null && var5.getRightC() == null) {
         TextView var10 = this.pMsg;
         if (var10 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pMsg");
            var10 = (TextView)var7;
         }

         this.msg(var10, "Place at least one center point.", true);
      } else {
         var7 = this.pNative();
         float var1 = ((Number)var7.component1()).floatValue();
         float var2 = ((Number)var7.component2()).floatValue();
         float var4 = var5.getCw();
         float var3 = var5.getCh();
         var7 = new JSONObject()
            .put("width", GxCamerasKt.access$gxCamRound(var1))
            .put("height", GxCamerasKt.access$gxCamRound(var2))
            .put("center_left", pSave$toNative(var4, var1, var2, var3, var5.getRightC()))
            .put("center_right", pSave$toNative(var4, var1, var2, var3, var5.getLeftC()))
            .put("crop_size", GxCamerasKt.access$gxCamRound(var5.getZoom() * var1 / var4));
         this.pLoading = true;
         TextView var9 = this.pMsg;
         if (var9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pMsg");
            var9 = (TextView)var6;
         }

         this.msg(var9, "", false);
         this.pPaint();
         GxPage.send$default(this, "POST", "/api/pip_preview/config", (JSONObject)var7, 0L, new GxCamerasPage$$ExternalSyntheticLambda67(this), 8, null);
      }
   }

   private static final Unit pSave$lambda$125(GxCamerasPage var0, String var1, JSONObject var2) {
      var0.pLoading = false;
      Object var3 = null;
      TextView var7 = null;
      if (var1 != null) {
         var3 = var0.pMsg;
         if (var3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pMsg");
         } else {
            var7 = (TextView)var3;
         }

         CharSequence var4 = var1;
         var3 = var4;
         if (StringsKt.isBlank(var4)) {
            var3 = "Failed to save";
         }

         var0.msg(var7, (String)var3, true);
         CharSequence var8 = var1;
         Object var6 = var8;
         if (StringsKt.isBlank(var8)) {
            var6 = "Save failed...";
         }

         var0.toast((String)var6);
         var0.pPaint();
         return Unit.INSTANCE;
      } else {
         var0.pConfigSaved = true;
         var0.pConfigExists = true;
         TextView var5 = var0.pMsg;
         if (var5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pMsg");
            var5 = (TextView)var3;
         }

         var0.msg(var5, "PiP Preview mask saved!", false);
         var0.toast("PiP Preview mask saved!");
         var0.loadPipConfig();
         var0.pPaint();
         return Unit.INSTANCE;
      }
   }

   private static final JSONArray pSave$toNative(float var0, float var1, float var2, float var3, float[] var4) {
      JSONArray var5 = null;
      JSONArray var6;
      if (var4 == null) {
         var5 = new JSONArray();
         var6 = var5;
      } else {
         var5 = new JSONArray();
         var6 = var5.put(GxCamerasKt.access$gxCamRound((var0 - var4[0]) * var1 / var0)).put(GxCamerasKt.access$gxCamRound(var4[1] * var2 / var3));
         Intrinsics.checkNotNullExpressionValue(var6, "put(...)");
      }

      return var6;
   }

   private final void pollVasm() {
      if (!this.vStat.isEmpty()) {
         for (Object var2 : this.vStat.keySet()) {
            Intrinsics.checkNotNullExpressionValue(var2, "next(...)");
            var2 = (String)var2;
            String var3 = this.enc((String)var2);
            this.getText("/api/params_memory?key=" + var3, new GxCamerasPage$$ExternalSyntheticLambda11(this, (String)var2));
         }
      }
   }

   private static final Unit pollVasm$lambda$108(GxCamerasPage var0, String var1, String var2, String var3) {
      Intrinsics.checkNotNullParameter(var3, "t");
      if (var2 != null) {
         return Unit.INSTANCE;
      } else {
         TextView var15 = var0.vStat.get(var1);
         if (var15 == null) {
            return Unit.INSTANCE;
         } else {
            boolean var7 = false;
            Double var9 = null;
            if (StringsKt.endsWith$default(var1, "Active", false, 2, null)) {
               boolean var8 = Intrinsics.areEqual(StringsKt.trim(var3).toString(), "1");
               String var10;
               if (var8) {
                  var10 = "YES";
               } else {
                  var10 = "no";
               }

               var15.setText(var10);
               int var6;
               if (!var8) {
                  var6 = CarUi.Companion.getDIM();
               } else if (Intrinsics.areEqual(var1, "VASMLeftActive")) {
                  var6 = CarUi.Companion.getACCENT();
               } else {
                  var6 = GxCamerasKt.access$getGX_CAM_ORANGE$p();
               }

               var15.setTextColor(var6);
            } else {
               label45: {
                  Double var14 = StringsKt.toDoubleOrNull(StringsKt.trim(var3).toString());
                  if (var14 != null) {
                     double var4 = var14.doubleValue();
                     boolean var17 = var7;
                     if (!Double.isInfinite(var4)) {
                        var17 = var7;
                        if (!Double.isNaN(var4)) {
                           var17 = true;
                        }
                     }

                     if (var17) {
                        var9 = var14;
                     }

                     if (var9 != null) {
                        var4 = var9.doubleValue();
                        StringCompanionObject var11 = StringCompanionObject.INSTANCE;
                        String var12 = String.format(Locale.US, "%.3f", Arrays.copyOf(new Object[]{var4}, 1));
                        Intrinsics.checkNotNullExpressionValue(var12, "format(...)");
                        if (var12 != null) {
                           var13 = var12;
                           break label45;
                        }
                     }
                  }

                  var13 = "-";
               }

               var15.setText(var13);
            }

            return Unit.INSTANCE;
         }
      }
   }

   private final void renderHistory(boolean var1) {
      String var3 = CollectionsKt.joinToString$default(this.sHistory, ";", null, null, 0, null, new GxCamerasPage$$ExternalSyntheticLambda27(this), 30, null);
      if (var1 || !Intrinsics.areEqual(var3, this.sHistorySig)) {
         this.sHistorySig = var3;
         LinearLayout var5 = this.sHistBox;
         Object var4 = null;
         LinearLayout var8 = var5;
         if (var5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sHistBox");
            var8 = null;
         }

         var8.removeAllViews();
         if (this.sHistory.isEmpty()) {
            LinearLayout var17 = this.sHistBox;
            if (var17 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("sHistBox");
               var17 = (LinearLayout)var4;
            }

            var17.addView((View)this.empty("No retained Sentry events."));
         } else {
            int var2 = this.sHistory.size();
            if (this.sHistory.size() == 1) {
               var3 = "";
            } else {
               var3 = "s";
            }

            String var23 = var2 + " event" + var3 + " retained. Events stay here until you delete them.";
            var4 = this.sHistBox;
            LinearLayout var10 = (LinearLayout)var4;
            if (var4 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("sHistBox");
               var10 = null;
            }

            this.note(var23, (ViewGroup)var10);

            for (JSONObject var7 : this.sHistory) {
               var4 = this.sHistBox;
               LinearLayout var11 = (LinearLayout)var4;
               if (var4 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("sHistBox");
                  var11 = null;
               }

               LinearLayout var6 = this.card((ViewGroup)var11);
               var4 = GxPage.str$default(this, var7, "eventId", null, 2, null);
               var3 = GxPage.str$default(this, var7, "detectedAt", null, 2, null);
               if (StringsKt.isBlank(var3)) {
                  var3 = "Sentry event";
               } else {
                  var3 = this.fmtTime(var3);
               }

               this.kindLine(var6, var7, var3, new GxCamerasPage$$ExternalSyntheticLambda28(this, (String)var4));
               var4 = GxPage.str$default(this, var7, "message", null, 2, null);
               var3 = (String)var4;
               if (StringsKt.isBlank((CharSequence)var4)) {
                  var3 = "Movement detected while parked.";
               }

               label59: {
                  this.text(var3, (ViewGroup)var6, 21.0F, CarUi.Companion.getTEXT(), true);
                  JSONArray var15 = var7.optJSONArray("imageUrls");
                  if (var15 != null) {
                     var4 = this.strings(var15);
                     var16 = (List)var4;
                     if (var4 != null) {
                        break label59;
                     }
                  }

                  var16 = CollectionsKt.emptyList();
               }

               if (!var16.isEmpty()) {
                  this.imageStrip(var6, var16, "Sentry capture");
               } else {
                  GxPage.text$default(this, "No camera images were available for this event.", (ViewGroup)var6, 0.0F, 0, false, 28, null);
               }
            }
         }
      }
   }

   // $VF: synthetic method
   static void renderHistory$default(GxCamerasPage var0, boolean var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = false;
      }

      var0.renderHistory(var1);
   }

   private static final CharSequence renderHistory$lambda$56(GxCamerasPage var0, JSONObject var1) {
      Intrinsics.checkNotNullParameter(var1, "it");
      return var0.eventSig(var1);
   }

   private static final Unit renderHistory$lambda$58(GxCamerasPage var0, String var1) {
      var0.deleteEvent(var1);
      return Unit.INSTANCE;
   }

   private final void renderLatest(boolean var1) {
      boolean var3 = StringsKt.isBlank(GxPage.str$default(this, this.sEvent, "eventId", null, 2, null)) ^ true;
      TextView var7 = this.sDelBtn;
      Object var5 = null;
      Object var6 = null;
      TextView var4 = var7;
      if (var7 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("sDelBtn");
         var4 = null;
      }

      byte var2;
      if (var3) {
         var2 = 0;
      } else {
         var2 = 8;
      }

      var4.setVisibility(var2);
      String var8;
      if (var3) {
         var8 = this.eventSig(this.sEvent);
      } else {
         var8 = "";
      }

      if (var1 || !Intrinsics.areEqual(var8, this.sEventSig)) {
         this.sEventSig = var8;
         LinearLayout var21 = this.sEventBox;
         LinearLayout var9 = var21;
         if (var21 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sEventBox");
            var9 = null;
         }

         var9.removeAllViews();
         if (!var3) {
            LinearLayout var16 = this.sEventBox;
            if (var16 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("sEventBox");
               var16 = (LinearLayout)var6;
            }

            var16.addView((View)this.empty("No Sentry events recorded yet."));
         } else {
            LinearLayout var10 = this.sEventBox;
            if (var10 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("sEventBox");
               var10 = (LinearLayout)var5;
            }

            var6 = this.card((ViewGroup)var10);
            var5 = this.sEvent;
            String var11 = GxPage.str$default(this, this.sEvent, "detectedAt", null, 2, null);
            kindLine$default(this, (LinearLayout)var6, (JSONObject)var5, this.fmtTime(var11), null, 8, null);
            var5 = GxPage.str$default(this, this.sEvent, "message", null, 2, null);
            Object var12 = var5;
            if (StringsKt.isBlank((CharSequence)var5)) {
               var12 = "Movement detected while parked.";
            }

            label50: {
               this.text((String)var12, (ViewGroup)var6, 21.0F, CarUi.Companion.getTEXT(), true);
               var12 = this.sEvent.optJSONArray("imageUrls");
               if (var12 != null) {
                  var5 = this.strings((JSONArray)var12);
                  var12 = var5;
                  if (var5 != null) {
                     break label50;
                  }
               }

               var12 = CollectionsKt.emptyList();
            }

            if (!((Collection)var12).isEmpty()) {
               this.imageStrip((LinearLayout)var6, (List<String>)var12, "Sentry capture");
               var12 = Unit.INSTANCE;
            } else if (Intrinsics.areEqual(GxPage.str$default(this, this.sEvent, "kind", null, 2, null), "power_off")) {
               GxPage.text$default(
                  this, "Power-off alerts do not include camera captures because the device is shutting down.", (ViewGroup)var6, 0.0F, 0, false, 28, null
               );
            } else {
               GxPage.text$default(this, "No camera images were available for this event.", (ViewGroup)var6, 0.0F, 0, false, 28, null);
            }
         }
      }
   }

   // $VF: synthetic method
   static void renderLatest$default(GxCamerasPage var0, boolean var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = false;
      }

      var0.renderLatest(var1);
   }

   private final void saveSentryParam(String var1, Object var2, Function0<Unit> var3) {
      this.putParam(var1, var2, new GxCamerasPage$$ExternalSyntheticLambda21(this, var3));
   }

   // $VF: synthetic method
   static void saveSentryParam$default(GxCamerasPage var0, String var1, Object var2, Function0 var3, int var4, Object var5) {
      if ((var4 & 4) != 0) {
         var3 = null;
      }

      var0.saveSentryParam(var1, var2, var3);
   }

   private static final Unit saveSentryParam$lambda$10(GxCamerasPage var0, Function0 var1, String var2) {
      if (var2 == null) {
         var0.toast("Sentry setting saved.");
      }

      if (var1 != null) {
         var1.invoke();
      }

      return Unit.INSTANCE;
   }

   private final void sendTestEvent() {
      if (!this.sTestBusy) {
         this.sTestBusy = true;
         TextView var2 = this.sTestBtn;
         TextView var1 = var2;
         if (var2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sTestBtn");
            var1 = null;
         }

         this.busy(var1, true, "Send test capture", "Capturing...");
         GxPage.send$default(this, "POST", "/api/sentry/test", null, 0L, new GxCamerasPage$$ExternalSyntheticLambda41(this), 12, null);
      }
   }

   private static final Unit sendTestEvent$lambda$54(GxCamerasPage var0, String var1, JSONObject var2) {
      var0.sTestBusy = false;
      TextView var3 = var0.sTestBtn;
      TextView var5 = var3;
      if (var3 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("sTestBtn");
         var5 = null;
      }

      var0.busy(var5, false, "Send test capture", "Capturing...");
      if (var1 != null) {
         CharSequence var6 = var1;
         Object var4 = var6;
         if (StringsKt.isBlank(var6)) {
            var4 = "Sentry test failed.";
         }

         var0.toast((String)var4);
      } else {
         var0.toast("Test capture started. The images will appear here shortly.");
         var0.getMain().postDelayed(new GxCamerasPage$$ExternalSyntheticLambda34(var0), 2500L);
      }

      return Unit.INSTANCE;
   }

   private static final void sendTestEvent$lambda$54$lambda$53(GxCamerasPage var0) {
      if (var0.getShown() && var0.tab == 0) {
         var0.loadSentryStatus();
      }
   }

   private final void sendTestNotification() {
      if (!this.sPushBusy) {
         this.sPushBusy = true;
         TextView var2 = this.sNotifyBtn;
         TextView var1 = var2;
         if (var2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sNotifyBtn");
            var1 = null;
         }

         this.busy(var1, true, "Send test notification", "Sending...");
         GxPage.send$default(this, "POST", "/api/sentry/test-notification", null, 0L, new GxCamerasPage$$ExternalSyntheticLambda38(this), 12, null);
      }
   }

   private static final Unit sendTestNotification$lambda$49(GxCamerasPage var0, String var1, JSONObject var2) {
      var0.sPushBusy = false;
      TextView var5 = var0.sNotifyBtn;
      ArrayList var4 = null;
      TextView var3 = var5;
      if (var5 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("sNotifyBtn");
         var3 = null;
      }

      var0.busy(var3, false, "Send test notification", "Sending...");
      if (var1 != null) {
         CharSequence var11 = var1;
         Object var9 = var11;
         if (StringsKt.isBlank(var11)) {
            var9 = "Could not send the test notification.";
         }

         var0.toast((String)var9);
         return Unit.INSTANCE;
      } else {
         JSONObject var6 = var4;
         if (var2 != null) {
            var6 = var2.optJSONObject("channels");
         }

         var4 = new ArrayList();
         if (var6 != null) {
            Iterator var12 = var6.keys();
            Intrinsics.checkNotNullExpressionValue(var12, "keys(...)");

            while (var12.hasNext()) {
               String var10 = (String)var12.next();
               if (var6.optBoolean(var10)) {
                  if (Intrinsics.areEqual(var10, "webPush")) {
                     var10 = "browser";
                  }

                  var4.add(var10);
               }
            }
         }

         if (!var4.isEmpty()) {
            var1 = CollectionsKt.joinToString$default(var4, ", ", null, null, 0, null, null, 62, null);
            var1 = "Test notification sent through " + var1 + ".";
         } else {
            var1 = "Test notification sent.";
         }

         var0.toast(var1);
         return Unit.INSTANCE;
      }
   }

   private final void showIf(View var1, boolean var2) {
      byte var3;
      if (var2) {
         var3 = 0;
      } else {
         var3 = 8;
      }

      var1.setVisibility(var3);
   }

   private final void tint(TextView var1, int var2, boolean var3) {
      CarUi var5 = this.getUi();
      int var4;
      if (var3) {
         var4 = var2;
      } else {
         var4 = CarUi.Companion.getCARD_ON();
      }

      var1.setBackground((Drawable)var5.rounded(var4, 12, var2));
      if (var3) {
         var2 = -1;
      }

      var1.setTextColor(var2);
   }

   private final void toggleHistory() {
      this.sHistoryVisible ^= true;
      TextView var3 = this.sHistBtn;
      Object var4 = null;
      TextView var2 = var3;
      if (var3 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("sHistBtn");
         var2 = null;
      }

      String var8;
      if (this.sHistoryVisible) {
         var8 = "Hide history";
      } else {
         var8 = "View history";
      }

      var2.setText(var8);
      LinearLayout var9 = this.sHistBox;
      LinearLayout var5 = var9;
      if (var9 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("sHistBox");
         var5 = null;
      }

      byte var1;
      if (this.sHistoryVisible) {
         var1 = 0;
      } else {
         var1 = 8;
      }

      var5.setVisibility(var1);
      if (this.sHistoryVisible) {
         this.sHistorySig = "?";
         LinearLayout var10 = this.sHistBox;
         LinearLayout var6 = var10;
         if (var10 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sHistBox");
            var6 = null;
         }

         var6.removeAllViews();
         LinearLayout var7 = this.sHistBox;
         if (var7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sHistBox");
            var7 = (LinearLayout)var4;
         }

         var7.addView((View)this.empty("Loading Sentry history..."));
         this.loadHistory();
      }
   }

   private final void vApplyConfig() {
      GxCamerasRegionView var8 = this.vView;
      Object var9 = null;
      GxCamerasRegionView var7 = var8;
      if (var8 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("vView");
         var7 = null;
      }

      JSONObject var11 = this.vConfig;
      var7.getLeft().clear();
      var7.getRight().clear();
      boolean var6 = false;
      if (var11 == null) {
         this.vConfigExists = false;
         this.vPaint();
      } else {
         float var3 = var7.getCw();
         float var4 = var7.getCh();
         Bitmap var15 = var7.getBmp();
         int var5;
         if (var15 != null) {
            var5 = var15.getWidth();
         } else {
            Integer var16 = var11.optInt("width", 1920);
            boolean var12;
            if (var16.intValue() > 0) {
               var12 = true;
            } else {
               var12 = false;
            }

            if (!var12) {
               var16 = null;
            }

            if (var16 != null) {
               var5 = var16;
            } else {
               var5 = 1920;
            }
         }

         float var1 = var5;
         Bitmap var17 = var7.getBmp();
         if (var17 != null) {
            var5 = var17.getHeight();
         } else {
            Integer var10 = var11.optInt("height", 1080);
            boolean var14;
            if (var10.intValue() > 0) {
               var14 = true;
            } else {
               var14 = false;
            }

            Integer var18 = (Integer)var9;
            if (var14) {
               var18 = var10;
            }

            if (var18 != null) {
               var5 = var18;
            } else {
               var5 = 1080;
            }
         }

         float var2 = var5;
         vApplyConfig$toCanvas(var3, var1, var4, var2, var11.optJSONArray("poly_right"), var7.getLeft());
         vApplyConfig$toCanvas(var3, var1, var4, var2, var11.optJSONArray("poly_left"), var7.getRight());
         if (!var7.getLeft().isEmpty() || !var7.getRight().isEmpty()) {
            var6 = true;
         }

         this.vConfigExists = var6;
         this.vPaint();
      }
   }

   private static final void vApplyConfig$toCanvas(float var0, float var1, float var2, float var3, JSONArray var4, List<float[]> var5) {
      if (var4 != null && var4.length() >= 3) {
         int var6 = 0;

         for (int var7 = var4.length(); var6 < var7; var6++) {
            JSONArray var8 = var4.optJSONArray(var6);
            if (var8 != null) {
               var5.add(
                  new float[]{
                     GxCamerasKt.access$gxCamRound(var0 - var8.optDouble(0) * var0 / var1), GxCamerasKt.access$gxCamRound(var8.optDouble(1) * var2 / var3)
                  }
               );
            }
         }
      }
   }

   private final void vClearAll() {
      GxCamerasRegionView var3 = this.vView;
      Object var2 = null;
      GxCamerasRegionView var1 = var3;
      if (var3 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("vView");
         var1 = null;
      }

      var1.getLeft().clear();
      var3 = this.vView;
      var1 = var3;
      if (var3 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("vView");
         var1 = null;
      }

      var1.getRight().clear();
      var3 = this.vView;
      var1 = var3;
      if (var3 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("vView");
         var1 = null;
      }

      var1.setAnnotating(null);
      this.vConfigSaved = false;
      TextView var6 = this.vMsg;
      if (var6 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("vMsg");
         var6 = (TextView)var2;
      }

      this.msg(var6, "", false);
      this.vPaint();
   }

   private final void vClearSide(String var1) {
      ArrayList var4;
      if (Intrinsics.areEqual(var1, "left")) {
         GxCamerasRegionView var2 = this.vView;
         GxCamerasRegionView var3 = var2;
         if (var2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vView");
            var3 = null;
         }

         var4 = var3.getLeft();
      } else {
         GxCamerasRegionView var7 = this.vView;
         GxCamerasRegionView var5 = var7;
         if (var7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vView");
            var5 = null;
         }

         var4 = var5.getRight();
      }

      var4.clear();
      GxCamerasRegionView var8 = this.vView;
      GxCamerasRegionView var6 = var8;
      if (var8 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("vView");
         var6 = null;
      }

      var6.setAnnotating(null);
      this.vPaint();
   }

   private final void vDelete() {
      this.confirm(
         "Delete V-ASM Config?", "This clears your window annotations and disables V-ASM.", "Delete Config", new GxCamerasPage$$ExternalSyntheticLambda29(this)
      );
   }

   private static final Unit vDelete$lambda$101(GxCamerasPage var0) {
      var0.vSaving = true;
      TextView var2 = var0.vMsg;
      TextView var1 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("vMsg");
         var1 = null;
      }

      var0.msg(var1, "", false);
      var0.vPaint();
      GxPage.send$default(var0, "DELETE", "/api/v_asm/config", null, 0L, new GxCamerasPage$$ExternalSyntheticLambda47(var0), 12, null);
      return Unit.INSTANCE;
   }

   private static final Unit vDelete$lambda$101$lambda$100(GxCamerasPage var0, String var1, JSONObject var2) {
      var0.vSaving = false;
      if (var1 != null) {
         CharSequence var13 = var1;
         Object var11 = var13;
         if (StringsKt.isBlank(var13)) {
            var11 = "Failed to delete";
         }

         var0.vError((String)var11);
         var0.vPaint();
         return Unit.INSTANCE;
      } else {
         GxCamerasRegionView var4 = var0.vView;
         Object var3 = null;
         GxCamerasRegionView var5 = var4;
         if (var4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vView");
            var5 = null;
         }

         var5.getLeft().clear();
         var4 = var0.vView;
         GxCamerasRegionView var6 = var4;
         if (var4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vView");
            var6 = null;
         }

         var6.getRight().clear();
         var4 = var0.vView;
         GxCamerasRegionView var7 = var4;
         if (var4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vView");
            var7 = null;
         }

         label31: {
            var7.setAnnotating(null);
            var0.vConfigSaved = false;
            var0.vConfigExists = false;
            var0.vConfig = null;
            if (var2 != null) {
               var1 = var2.optString("message");
               if (var1 != null) {
                  var1 = var1;
                  if (StringsKt.isBlank(var1)) {
                     var1 = (String)var3;
                  }

                  String var12 = var1;
                  var1 = var12;
                  if (var12 != null) {
                     break label31;
                  }
               }
            }

            var1 = "Annotation config cleared.";
         }

         var0.vSuccess(var1);
         var0.toast("Annotation config cleared.");
         var0.vPaint();
         return Unit.INSTANCE;
      }
   }

   private final void vError(String var1) {
      TextView var3 = this.vMsg;
      TextView var2 = var3;
      if (var3 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("vMsg");
         var2 = null;
      }

      this.msg(var2, var1, true);
      CharSequence var5 = var1;
      Object var4 = var5;
      if (StringsKt.isBlank(var5)) {
         var4 = "Something went wrong.";
      }

      this.toast((String)var4);
   }

   private final void vFinish() {
      GxCamerasRegionView var3 = this.vView;
      GxCamerasRegionView var2 = var3;
      if (var3 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("vView");
         var2 = null;
      }

      String var5 = var2.getAnnotating();
      if (var5 != null) {
         ArrayList var7;
         if (Intrinsics.areEqual(var5, "left")) {
            var3 = this.vView;
            var2 = var3;
            if (var3 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("vView");
               var2 = null;
            }

            var7 = var2.getLeft();
         } else {
            var3 = this.vView;
            var2 = var3;
            if (var3 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("vView");
               var2 = null;
            }

            var7 = var2.getRight();
         }

         if (var7.size() < 3) {
            this.vError("Need at least 3 points to define a region.");
         } else {
            GxCamerasRegionView var4 = this.vView;
            var3 = var4;
            if (var4 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("vView");
               var3 = null;
            }

            var3.setAnnotating(null);
            String var12;
            if (Intrinsics.areEqual(var5, "left")) {
               var12 = "Left";
            } else {
               var12 = "Right";
            }

            int var1 = var7.size();
            this.vSuccess(var12 + " window annotated (" + var1 + " points)!");
            this.vPaint();
         }
      }
   }

   private final void vPaint() {
      if (this.vView != null) {
         GxCamerasRegionView var7 = this.vView;
         GxCamerasRegionView var6 = var7;
         if (var7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vView");
            var6 = null;
         }

         String var11 = var6.getAnnotating();
         var7 = this.vView;
         var6 = var7;
         if (var7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vView");
            var6 = null;
         }

         int var3 = var6.getLeft().size();
         var7 = this.vView;
         var6 = var7;
         if (var7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vView");
            var6 = null;
         }

         int var4 = var6.getRight().size();
         TextView var51 = this.vLeftBtn;
         TextView var20 = var51;
         if (var51 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vLeftBtn");
            var20 = null;
         }

         this.tint(var20, GxCamerasKt.access$getGX_CAM_BLUE$p(), Intrinsics.areEqual(var11, "left"));
         TextView var52 = this.vRightBtn;
         TextView var21 = var52;
         if (var52 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vRightBtn");
            var21 = null;
         }

         this.tint(var21, GxCamerasKt.access$getGX_CAM_ORANGE$p(), Intrinsics.areEqual(var11, "right"));
         TextView var53 = this.vLeftBtn;
         TextView var22 = var53;
         if (var53 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vLeftBtn");
            var22 = null;
         }

         String var54;
         if (Intrinsics.areEqual(var11, "left")) {
            var54 = "Annotating Left Side of Vehicle - Left Here...";
         } else {
            var54 = "Annotate Left Side of Vehicle - Left Here";
         }

         var22.setText(var54);
         TextView var55 = this.vRightBtn;
         TextView var23 = var55;
         if (var55 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vRightBtn");
            var23 = null;
         }

         String var56;
         if (Intrinsics.areEqual(var11, "right")) {
            var56 = "Annotating Right Side of Vehicle - Right Here...";
         } else {
            var56 = "Annotate Right Side of Vehicle - Right Here";
         }

         var23.setText(var56);
         TextView var57 = this.vFinishBtn;
         TextView var24 = var57;
         if (var57 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vFinishBtn");
            var24 = null;
         }

         View var25 = (View)var24;
         boolean var5;
         if (var11 != null) {
            var5 = true;
         } else {
            var5 = false;
         }

         this.showIf(var25, var5);
         TextView var58 = this.vUndoBtn;
         TextView var26 = var58;
         if (var58 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vUndoBtn");
            var26 = null;
         }

         View var27 = (View)var26;
         if (var11 != null) {
            var5 = true;
         } else {
            var5 = false;
         }

         this.showIf(var27, var5);
         TextView var59 = this.vFinishBtn;
         TextView var28 = var59;
         if (var59 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vFinishBtn");
            var28 = null;
         }

         String var60;
         if (Intrinsics.areEqual(var11, "left")) {
            var60 = "Left";
         } else {
            var60 = "Right";
         }

         var28.setText("Finish " + var60);
         boolean var2;
         if (var3 < 3 && var4 < 3) {
            var2 = 0;
         } else {
            var2 = 1;
         }

         TextView var61 = this.vSaveBtn;
         TextView var29 = var61;
         if (var61 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vSaveBtn");
            var29 = null;
         }

         String var62;
         if (this.vSaving) {
            var62 = "Saving...";
         } else {
            var62 = "Save Config";
         }

         var29.setText(var62);
         TextView var63 = this.vSaveBtn;
         TextView var30 = var63;
         if (var63 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vSaveBtn");
            var30 = null;
         }

         if (!this.vSaving && var2) {
            var5 = true;
         } else {
            var5 = false;
         }

         var30.setEnabled(var5);
         TextView var64 = this.vSaveBtn;
         TextView var31 = var64;
         if (var64 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vSaveBtn");
            var31 = null;
         }

         TextView var8 = this.vSaveBtn;
         TextView var65 = var8;
         if (var8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vSaveBtn");
            var65 = null;
         }

         float var1;
         if (var65.isEnabled()) {
            var1 = 1.0F;
         } else {
            var1 = 0.45F;
         }

         var31.setAlpha(var1);
         TextView var66 = this.vDeleteBtn;
         TextView var32 = var66;
         if (var66 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vDeleteBtn");
            var32 = null;
         }

         this.showIf((View)var32, this.vConfigExists);
         TextView var67 = this.vDeleteBtn;
         TextView var33 = var67;
         if (var67 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vDeleteBtn");
            var33 = null;
         }

         var33.setEnabled(this.vSaving ^ true);
         TextView var68 = this.vSnapBtn;
         TextView var34 = var68;
         if (var68 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vSnapBtn");
            var34 = null;
         }

         this.busy(var34, this.vSnapLoading, "Get a new Snapshot", "Loading...");
         GxPage.Row var69 = this.vEnabledRow;
         GxPage.Row var35 = var69;
         if (var69 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vEnabledRow");
            var35 = null;
         }

         LinearLayout var78 = var35.getView();
         GxPage.Row var70 = this.vEnabledRow;
         GxPage.Row var36 = var70;
         if (var70 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vEnabledRow");
            var36 = null;
         }

         View var37 = var78.getChildAt(var36.getView().getChildCount() - 1);
         if (!this.vConfigSaved && !this.vConfigExists) {
            var2 = (byte)4;
         } else {
            var2 = (byte)0;
         }

         var37.setVisibility(var2);
         TextView var71 = this.vCounts;
         TextView var38 = var71;
         if (var71 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vCounts");
            var38 = null;
         }

         String var9 = "s";
         String var10 = "";
         String var72;
         if (var3 != 1) {
            var72 = "s";
         } else {
            var72 = "";
         }

         String var79;
         if (var3 >= 3) {
            var79 = " ✓";
         } else {
            var79 = "";
         }

         if (var4 == 1) {
            var9 = "";
         }

         if (var4 >= 3) {
            var10 = " ✓";
         }

         var38.setText("Left: " + var3 + " pt" + var72 + var79 + "     Right: " + var4 + " pt" + var9 + var10);
         TextView var73 = this.vClearLeftBtn;
         TextView var39 = var73;
         if (var73 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vClearLeftBtn");
            var39 = null;
         }

         View var40 = (View)var39;
         if (var3 > 0) {
            var5 = true;
         } else {
            var5 = false;
         }

         this.showIf(var40, var5);
         TextView var74 = this.vClearRightBtn;
         TextView var41 = var74;
         if (var74 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vClearRightBtn");
            var41 = null;
         }

         View var42 = (View)var41;
         if (var4 > 0) {
            var5 = true;
         } else {
            var5 = false;
         }

         this.showIf(var42, var5);
         TextView var75 = this.vHint;
         TextView var43 = var75;
         if (var75 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vHint");
            var43 = null;
         }

         if (Intrinsics.areEqual(var11, "left")) {
            var2 = GxCamerasKt.access$getGX_CAM_BLUE$p();
         } else if (Intrinsics.areEqual(var11, "right")) {
            var2 = GxCamerasKt.access$getGX_CAM_ORANGE$p();
         } else {
            var2 = CarUi.Companion.getDIM();
         }

         var43.setTextColor(var2);
         TextView var44 = this.vHint;
         TextView var76 = var44;
         if (var44 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vHint");
            var76 = null;
         }

         CharSequence var46;
         if (var11 != null) {
            String var45;
            if (Intrinsics.areEqual(var11, "left")) {
               var45 = "LEFT SIDE OF VEHICLE - LEFT HERE";
            } else {
               var45 = "RIGHT SIDE OF VEHICLE - RIGHT HERE";
            }

            var46 = var45
               + " — Tap the picture to place points around the visible glass. Tap near the first point to close the polygon. Use Undo to remove the last point.";
         } else {
            GxCamerasRegionView var80 = this.vView;
            var6 = var80;
            if (var80 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("vView");
               var6 = null;
            }

            if (var6.getBmp() == null) {
               var46 = "Loading camera snapshot... The snapshot will load automatically.";
            } else {
               var46 = "Idle — tap a window button above to configure your active canvas boundaries.";
            }
         }

         var76.setText(var46);
         var7 = this.vView;
         var6 = var7;
         if (var7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vView");
            var6 = null;
         }

         var6.invalidate();
      }
   }

   private final void vSave() {
      GxCamerasRegionView var6 = this.vView;
      Object var7 = null;
      GxCamerasRegionView var5 = var6;
      if (var6 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("vView");
         var5 = null;
      }

      if (var5.getLeft().size() < 3 && var5.getRight().size() < 3) {
         this.vError("Annotate at least one window with 3+ points.");
      } else {
         Bitmap var11 = var5.getBmp();
         int var3;
         if (var11 != null) {
            var3 = var11.getWidth();
         } else {
            var3 = 1920;
         }

         Bitmap var12 = var5.getBmp();
         int var4;
         if (var12 != null) {
            var4 = var12.getHeight();
         } else {
            var4 = 1080;
         }

         float var1 = var5.getCw();
         float var2 = var5.getCh();
         JSONObject var8 = new JSONObject().put("width", var3).put("height", var4);
         JSONArray var13;
         if (var5.getLeft().size() >= 3) {
            var13 = vSave$scale(var1, var3, var4, var2, var5.getLeft());
         } else {
            var13 = new JSONArray();
         }

         JSONObject var14 = var8.put("poly_right", var13);
         JSONArray var9;
         if (var5.getRight().size() >= 3) {
            var9 = vSave$scale(var1, var3, var4, var2, var5.getRight());
         } else {
            var9 = new JSONArray();
         }

         JSONObject var15 = var14.put("poly_left", var9);
         this.vSaving = true;
         TextView var10 = this.vMsg;
         if (var10 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vMsg");
            var10 = (TextView)var7;
         }

         this.msg(var10, "", false);
         this.vPaint();
         GxPage.send$default(this, "POST", "/api/v_asm/config", var15, 0L, new GxCamerasPage$$ExternalSyntheticLambda0(this), 8, null);
      }
   }

   private static final Unit vSave$lambda$97(GxCamerasPage var0, String var1, JSONObject var2) {
      var0.vSaving = false;
      if (var1 != null) {
         CharSequence var9 = var1;
         Object var6 = var9;
         if (StringsKt.isBlank(var9)) {
            var6 = "Failed to save";
         }

         var0.vError((String)var6);
         var0.vPaint();
         return Unit.INSTANCE;
      } else {
         label24: {
            var0.vConfigSaved = true;
            var0.vConfigExists = true;
            if (var2 != null) {
               var1 = var2.optString("message");
               if (var1 != null) {
                  CharSequence var7 = var1;
                  CharSequence var4 = var7;
                  if (StringsKt.isBlank(var7)) {
                     var4 = null;
                  }

                  String var8 = (String)var4;
                  var1 = var8;
                  if (var8 != null) {
                     break label24;
                  }
               }
            }

            var1 = "Annotation config saved! V-ASM is now enabled.";
         }

         var0.vSuccess(var1);
         var0.toast("Annotation config saved! V-ASM is now enabled.");
         var0.loadVasmConfig();
         var0.vPaint();
         return Unit.INSTANCE;
      }
   }

   private static final JSONArray vSave$scale(float var0, int var1, int var2, float var3, List<float[]> var4) {
      JSONArray var5 = new JSONArray();

      for (float[] var7 : var4) {
         var5.put(new JSONArray().put(GxCamerasKt.access$gxCamRound((var0 - var7[0]) * var1 / var0)).put(GxCamerasKt.access$gxCamRound(var7[1] * var2 / var3)));
      }

      return var5;
   }

   private final void vStart(String var1) {
      GxCamerasRegionView var4 = this.vView;
      Object var3 = null;
      GxCamerasRegionView var2 = var4;
      if (var4 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("vView");
         var2 = null;
      }

      var2.setAnnotating(var1);
      TextView var5 = this.vMsg;
      if (var5 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("vMsg");
         var5 = (TextView)var3;
      }

      this.msg(var5, "", false);
      this.vPaint();
   }

   private final void vSuccess(String var1) {
      TextView var3 = this.vMsg;
      TextView var2 = var3;
      if (var3 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("vMsg");
         var2 = null;
      }

      this.msg(var2, var1, false);
   }

   private final void vUndo() {
      GxCamerasRegionView var4 = this.vView;
      Object var3 = null;
      Object var2 = null;
      GxCamerasRegionView var1 = var4;
      if (var4 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("vView");
         var1 = null;
      }

      String var5 = var1.getAnnotating();
      if (var5 != null) {
         ArrayList var7;
         if (Intrinsics.areEqual(var5, "left")) {
            var1 = this.vView;
            if (var1 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("vView");
               var1 = (GxCamerasRegionView)var2;
            }

            var7 = var1.getLeft();
         } else {
            var1 = this.vView;
            if (var1 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("vView");
               var1 = (GxCamerasRegionView)var3;
            }

            var7 = var1.getRight();
         }

         if (!var7.isEmpty()) {
            var7.remove(var7.size() - 1);
         }

         this.vPaint();
      }
   }

   private final void viewLive() {
      if (!this.sLiveBusy) {
         this.sLiveBusy = true;
         TextView var2 = this.sLiveBtn;
         TextView var1 = var2;
         if (var2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sLiveBtn");
            var1 = null;
         }

         this.busy(var1, true, "View live", "Capturing...");
         GxPage.json$default(this, "GET", "/api/sentry/live", null, 60L, new GxCamerasPage$$ExternalSyntheticLambda40(this), 4, null);
      }
   }

   private static final Unit viewLive$lambda$51(GxCamerasPage var0, String var1, Object var2) {
      var0.sLiveBusy = false;
      TextView var6 = var0.sLiveBtn;
      Object var4 = null;
      Collection var5 = null;
      TextView var3 = var6;
      if (var6 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("sLiveBtn");
         var3 = null;
      }

      var0.busy(var3, false, "View live", "Capturing...");
      if (var2 instanceof JSONObject) {
         var2 = var2;
      } else {
         var2 = null;
      }

      if (var1 == null && var2 != null) {
         var0.toast("Live camera snapshot captured.");
         LinearLayout var15 = var0.sLiveBox;
         LinearLayout var7 = var15;
         if (var15 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sLiveBox");
            var7 = null;
         }

         label57: {
            var7.removeAllViews();
            JSONArray var8 = var2.optJSONArray("imageUrls");
            if (var8 != null) {
               List var16 = var0.strings(var8);
               var9 = var16;
               if (var16 != null) {
                  break label57;
               }
            }

            var9 = CollectionsKt.emptyList();
         }

         if (var9.isEmpty()) {
            LinearLayout var13 = var0.sLiveBox;
            if (var13 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("sLiveBox");
               var13 = var5;
            }

            var13.addView((View)var0.empty("No live snapshot captured yet."));
            return Unit.INSTANCE;
         } else {
            var5 = GxPage.str$default(var0, var2, "capturedAt", null, 2, null);
            LinearLayout var17 = var0.sLiveBox;
            if (var17 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("sLiveBox");
               var17 = (LinearLayout)var4;
            }

            var4 = var0.card((ViewGroup)var17);
            GxPage var23 = var0;
            String var18;
            if (StringsKt.isBlank((CharSequence)var5)) {
               var18 = "just now";
            } else {
               var18 = var0.fmtTime((String)var5);
            }

            GxPage.text$default(var23, "Captured " + var18 + ".", (ViewGroup)var4, 0.0F, 0, false, 28, null);
            String var19 = var0.enc((String)var5);
            Iterable var10 = var9;
            var5 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var10, 10));

            for (String var11 : var10) {
               if (!StringsKt.isBlank(var19)) {
                  var11 = var11 + "?t=" + var19;
               }

               var5.add(var11);
            }

            List var12 = (List)var5;
            var0.imageStrip((LinearLayout)var4, var12, "Live Sentry camera");
            return Unit.INSTANCE;
         }
      } else {
         if (var1 == null) {
            var1 = "Live camera capture failed.";
         }

         var0.toast(var1);
         return Unit.INSTANCE;
      }
   }

   @Override
   public void build() {
      GxPage.header$default(this, "Cameras & Monitoring", null, 2, null);
      this.tabs(
         CollectionsKt.listOf(new String[]{"Sentry Mode", "V-ASM Spot Monitor", "PiP Side Camera"}),
         this.tab,
         new GxCamerasPage$$ExternalSyntheticLambda17(this),
         new GxCamerasPage$$ExternalSyntheticLambda18(this)
      );
      this.every(5000L, new GxCamerasPage$$ExternalSyntheticLambda19(this));
      this.every(3000L, new GxCamerasPage$$ExternalSyntheticLambda20(this));
   }

   @Override
   public void onShow() {
      this.loadTab(this.tab, true);
   }
}
