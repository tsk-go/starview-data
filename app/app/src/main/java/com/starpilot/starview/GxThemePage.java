package com.starpilot.starview;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.net.Uri;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.text.InputFilter.LengthFilter;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout.LayoutParams;
import android.widget.SeekBar.OnSeekBarChangeListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Triple;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.internal.ProgressionUtilKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.jvm.internal.Ref.BooleanRef;
import kotlin.jvm.internal.Ref.IntRef;
import kotlin.jvm.internal.Ref.ObjectRef;
import kotlin.ranges.RangesKt;
import kotlin.text.Regex;
import kotlin.text.RegexOption;
import kotlin.text.StringsKt;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.MultipartBody.Builder;
import org.json.JSONArray;
import org.json.JSONObject;

public final class GxThemePage extends GxPage {
   private boolean busy;
   private TextView busyText;
   private LinearLayout checkBox;
   private final LinkedHashMap<String, int[]> colors;
   private LinearLayout colorsBox;
   private String discord;
   private GxPage.Row discordRow;
   private LinearLayout distBox;
   private GxPage.Row dlRow;
   private final HashMap<String, List<String>> downloadable;
   private boolean downloading;
   private final HashMap<String, GxThemeFile> files;
   private final LinkedHashMap<String, Boolean> flags;
   private final ArrayList<GxThemeFile> frames;
   private String framesName;
   private LinearLayout iconsBox;
   private LinearLayout libBar;
   private LinearLayout libBox;
   private int libTab;
   private String loadError;
   private boolean loading;
   private EditText nameEdit;
   private final HashMap<String, String> names;
   private List<? extends JSONObject> rawThemes;
   private LinearLayout soundsBox;
   private final HashMap<String, JSONObject> sources;
   private boolean submitted;
   private String themeName;
   private List<GxThemeEntry> themes;
   private int tsLength;
   private String tsStyle;
   private String tsType;
   private LinearLayout turnBox;
   private LinearLayout wheelBox;

   // $VF: synthetic method
   public static void $r8$lambda$_8xwWQugdprZCl0bsXhua1m9BtA/* $VF was: $r8$lambda$_8xwWQugdprZCl0bsXhua1m9BtA*/(GxThemePage var0, TextView var1, View var2) {
      renderTurn$lambda$65(var0, var1, var2);
   }

   // $VF: synthetic method
   public static void $r8$lambda$_US77TUCNUcFD4MSQ2VnA0eAAOM/* $VF was: $r8$lambda$_US77TUCNUcFD4MSQ2VnA0eAAOM*/(
      String var0, MultipartBody var1, String var2, GxThemePage var3
   ) {
      exportTheme$lambda$102(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static void $r8$lambda$0vP3ylNDcC0FJC436eZh8tCi8RE(List var0, GxThemePage var1, Function1 var2) {
      fetchAssets$lambda$29(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$1F7C3mrId5GqajRZ4tgKA6Ej5d8(
      View var0, GxThemePage var1, ArrayList var2, float[] var3, ArrayList var4, ArrayList var5, BooleanRef var6, EditText var7, boolean var8
   ) {
      return gxThemeColorDialog$lambda$168(var0, var1, var2, var3, var4, var5, var6, var7, var8);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$1iQ1eP1OK7jcwJCm2rV1cvfgGxM(ArrayList var0, GxThemePage var1, String var2, GxThemeEntry var3, String var4, List var5) {
      return loadComponent$lambda$152$lambda$151(var0, var1, var2, var3, var4, var5);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$2_MPFNSSMTLW1KB5MtW0lbEaC9E(GxThemePage var0) {
      return build$lambda$5(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$5ZVOSzZFPP_Fvmk2quV68xUVPpI(GxThemePage var0, String var1, boolean var2, String var3, byte[] var4, String var5) {
      return choose$lambda$83$lambda$82(var0, var1, var2, var3, var4, var5);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$7V_bQJpWssCTuCP5KHtAWKctOlU/* $VF was: $r8$lambda$7V_bQJpWssCTuCP5KHtAWKctOlU*/(GxThemePage var0, String var1) {
      return slotRow$lambda$60(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$7jRcymf_Fe1ablmWyxbAnMta_k4/* $VF was: $r8$lambda$7jRcymf_Fe1ablmWyxbAnMta_k4*/(
      EditText var0, Function1 var1, float[] var2, DialogInterface var3, int var4
   ) {
      gxThemeColorDialog$lambda$170(var0, var1, var2, var3, var4);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$AMEiWcG1Juj10iUDxBE1zz3Fg38(GxThemePage var0, TextView var1, String var2) {
      return renderTurn$lambda$65$lambda$64(var0, var1, var2);
   }

   // $VF: synthetic method
   public static void $r8$lambda$BOadtHily4Ilu3FT5rPp8pPbOho(GxThemePage var0, ObjectRef var1) {
      exportTheme$lambda$102$lambda$101(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$Bmn_WcEApYMomt63XDgCrQT4ALI(GxThemePage var0, String var1, View var2) {
      renderColors$lambda$47$lambda$46(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$Bxr3vVU8txisQ0J_XcPzIotTjjM(GxThemePage var0, GxThemeEntry var1, String var2, String var3) {
      return useComponent$lambda$137$lambda$136(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$EELC_LInFjGJx7428tb9JqF_i00(GxThemePage var0) {
      return build$lambda$8(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$Ep0s7jS1dIYQ_hqPiaa5iU0gqNU/* $VF was: $r8$lambda$Ep0s7jS1dIYQ_hqPiaa5iU0gqNU*/(
      GxThemePage var0, String var1, IntRef var2, String var3, String var4
   ) {
      return fetchDownloadables$lambda$106(var0, var1, var2, var3, var4);
   }

   // $VF: synthetic method
   public static void $r8$lambda$FEiAnqt_rg97A_lMQJHGOM5XXRM/* $VF was: $r8$lambda$FEiAnqt_rg97A_lMQJHGOM5XXRM*/(Function1 var0, List var1) {
      fetchAssets$lambda$29$lambda$28(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$FRH984MGourU5sJNM_TIAQ8iiTA(GxThemePage var0, String var1, GxThemeEntry var2, String var3, String var4, JSONObject var5) {
      return loadComponent$lambda$152(var0, var1, var2, var3, var4, var5);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$GMJRmFjPO3P1lYsHTqmBeRepnQU(GxThemePage var0, String var1, GxThemeEntry var2, String var3, String var4) {
      return useComponent$lambda$137(var0, var1, var2, var3, var4);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$GochSJvkmZZEMx2p2naUJqJOzho(GxThemePage var0, GxThemeEntry var1) {
      return renderAll$lambda$116(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$IINrTDWAkXOZL7_Z32pYo_pgvEc/* $VF was: $r8$lambda$IINrTDWAkXOZL7_Z32pYo_pgvEc*/(GxThemePage var0) {
      return renderColors$lambda$55(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$JATX5x7R0YRhHAtYv9Pko_5lLOg/* $VF was: $r8$lambda$JATX5x7R0YRhHAtYv9Pko_5lLOg*/(GxThemePage var0, String var1, JSONObject var2) {
      return save$lambda$86(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$KFfEGdpP43t806jJEA9kW0my7KU(GxThemePage var0, String var1, GxThemeEntry var2) {
      return renderComp$lambda$119(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$KgoWy8hqoAYKJKEDt4DHhh05tQY(GxThemePage var0) {
      return renderTurn$lambda$68(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$L25zPaX9813iGtnNieRK3z5hwhE(GxThemePage var0, TextView var1) {
      return renderTurn$lambda$66(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$LiVaSR4cDbRhQXda4YXkSNcdeDM(ArrayList var0, GxThemePage var1, List var2) {
      return loadDefault$lambda$43$lambda$42(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$LjxhaGgx_W_aJrRnE37FA5fFF3U/* $VF was: $r8$lambda$LjxhaGgx_W_aJrRnE37FA5fFF3U*/(
      GxThemePage var0, GxThemeEntry var1, String var2, JSONObject var3
   ) {
      return loadToEditor$lambda$135(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$PIw1moCVN7BY7e6HfUGqXZIPJxY(GxThemePage var0, String var1, JSONObject var2) {
      return loadDefault$lambda$43(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$POJqAAQ2lDe0azOGJxkg9iBFx_g/* $VF was: $r8$lambda$POJqAAQ2lDe0azOGJxkg9iBFx_g*/(GxThemePage var0) {
      return apply$lambda$88(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$PTZdIsA5YOrN_JvPz_u3J96W_VQ/* $VF was: $r8$lambda$PTZdIsA5YOrN_JvPz_u3J96W_VQ*/(GxThemePage var0, String var1) {
      return renderColors$lambda$53(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$PsHChxbat8z3rum3DC_A038NdtE(GxThemePage var0, int var1) {
      return renderTurn$lambda$62(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$QUryk3DaBiWn2Rjqg4P0FVsYTHI(GxThemePage var0, String var1, boolean var2) {
      return slotRow$lambda$59(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$Qia33ReGrjIaov_PS1QXte8bmeI(GxThemePage var0, GxThemeEntry var1, String var2) {
      return renderComp$lambda$120(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$R5kAUt0UpST3zdZqfs4FCwWnO3g(GxThemePage var0, boolean var1, String var2, Uri var3) {
      return choose$lambda$83(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$RJuJgpVsJYMNVH4UiGh_SDaqdAk/* $VF was: $r8$lambda$RJuJgpVsJYMNVH4UiGh_SDaqdAk*/(
      GxThemePage var0, GxThemeEntry var1, String var2
   ) {
      return renderComp$lambda$121(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$SARvMRfP94hJVVmDXubU83Klw_0(GxThemePage var0, String var1, JSONObject var2) {
      return removeTheme$lambda$154$lambda$153(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$SLDJW3rimRwdGwGb7Evu_qELcds/* $VF was: $r8$lambda$SLDJW3rimRwdGwGb7Evu_qELcds*/(GxThemePage var0, String var1, JSONObject var2) {
      return refreshThemeList$lambda$103(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$TZle_UmUngJF4z8EBiISypxBSpY/* $VF was: $r8$lambda$TZle_UmUngJF4z8EBiISypxBSpY*/(GxThemePage var0) {
      return build$lambda$3(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$UI9ZfIsZrmfnlIBaWYPFyjlgIWw(GxThemePage var0, int var1) {
      return renderTurn$lambda$72(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$WtYUpG5q0fAF_5hfUn9hd8Pc8gc(GxThemePage var0, String var1, GxThemeFile var2) {
      return loadComponent$lambda$152$slot$140$lambda$139(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$X4J_rkvlHbJJazNL94im2wp6T4E(GxThemePage var0, GxThemeEntry var1) {
      return removeTheme$lambda$154(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$Zct2wzdbjJGQt1uZqKAb4IP0_2c(GxThemePage var0, int var1) {
      return renderTurn$lambda$61(var0, var1);
   }

   // $VF: synthetic method
   public static CharSequence $r8$lambda$ZrX8HXKswztk9rrqd5vSJfRyAfk(String var0) {
      return submit$lambda$91(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$_3bs5JGjqVVhCPcXl5rdu2SUqwU(GxThemePage var0, GxThemeFile var1) {
      return loadToEditor$lambda$135$lambda$132(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$a_tFxvRsG3Mi6F2tu8p_Kkcq5WQ/* $VF was: $r8$lambda$a_tFxvRsG3Mi6F2tu8p_Kkcq5WQ*/(
      GxThemePage var0, String var1, GxThemeEntry var2
   ) {
      return removeComponent$lambda$156(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$c_zH0qopsBs3xi1hSztNQOEpnlI(GxThemePage var0) {
      return submit$lambda$93(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$eFxnnIyIXO_O6GWVeFVJxP6rGps/* $VF was: $r8$lambda$eFxnnIyIXO_O6GWVeFVJxP6rGps*/(GxThemePage var0, String var1, JSONObject var2) {
      return removeComponent$lambda$156$lambda$155(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$eVgDVHC8qEBn3ef5lNmZHV2_d6Y/* $VF was: $r8$lambda$eVgDVHC8qEBn3ef5lNmZHV2_d6Y*/(
      GxThemePage var0, String var1, GxThemeFile var2
   ) {
      return loadComponent$lambda$152$lambda$145$lambda$144(var0, var1, var2);
   }

   // $VF: synthetic method
   public static void $r8$lambda$fKeRGm4OkGBJ82r3_S9J_d2LupY/* $VF was: $r8$lambda$fKeRGm4OkGBJ82r3_S9J_d2LupY*/(GxThemePage var0, int var1, View var2) {
      renderLibrary$lambda$111$lambda$110(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$gyku0ypMnkUdGYNOBV9M1eFj3bQ(GxThemePage var0, int var1) {
      return renderTurn$lambda$71(var0, var1);
   }

   // $VF: synthetic method
   public static CharSequence $r8$lambda$iq5vob7bEL8gqq219cKt4ZCU_BU(GxThemePage var0, String var1) {
      return assetPath$lambda$14(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$j5PYY13VxDZTA2Pr_Tw0egS6B5o/* $VF was: $r8$lambda$j5PYY13VxDZTA2Pr_Tw0egS6B5o*/(GxThemePage var0, String var1, int var2) {
      return editColor$lambda$56(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$kpSoRvzHCMFhrjeBs0Yy_hHhREs(GxThemePage var0, String var1, String var2) {
      return build$lambda$12(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$lh9iNc8VGddP0vuWfwvsaP8xj5Q(GxThemePage var0, GxThemeFile var1) {
      return loadComponent$lambda$152$lambda$149(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$lu8px51WXFuXqxzA9P7KMB_mJkA/* $VF was: $r8$lambda$lu8px51WXFuXqxzA9P7KMB_mJkA*/(GxThemePage var0) {
      return build$lambda$2(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$maiUmKpbGaxFKrME3MuCwbChLZU(GxThemePage var0, GxThemeFile var1) {
      return loadDefault$lambda$43$lambda$40(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$pS7Nh2iRYbMO2tTniv_k2z_gNeY/* $VF was: $r8$lambda$pS7Nh2iRYbMO2tTniv_k2z_gNeY*/(GxThemePage var0) {
      return build$lambda$4(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$qicNoFtgH6_RqjMiDcxmLYvO_x4/* $VF was: $r8$lambda$qicNoFtgH6_RqjMiDcxmLYvO_x4*/(GxThemePage var0, TextView var1) {
      return renderTurn$lambda$67(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$rEZHFkBGDDwG_2mkk9TSc6zsT8I/* $VF was: $r8$lambda$rEZHFkBGDDwG_2mkk9TSc6zsT8I*/(
      GxThemePage var0, String var1, GxThemeFile var2
   ) {
      return loadDefault$lambda$43$slot$lambda$33(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$srlSu01l9zcDfxhU4D0QCAjFOug(GxThemePage var0, GxThemeEntry var1) {
      return renderAll$lambda$115(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$tfYaV25lph5Apn9Uc6dFkPDz0RA(GxThemePage var0, String var1, JSONObject var2) {
      return apply$lambda$88$lambda$87(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$u_2lhPW1FjzTf4H0Dgxt7uQH1K8/* $VF was: $r8$lambda$u_2lhPW1FjzTf4H0Dgxt7uQH1K8*/(GxThemePage var0, String var1, JSONObject var2) {
      return submit$lambda$93$lambda$92(var0, var1, var2);
   }

   // $VF: synthetic method
   public static void $r8$lambda$wRjsTyYsCzMVEcltfeM7g_W_Lss/* $VF was: $r8$lambda$wRjsTyYsCzMVEcltfeM7g_W_Lss*/(
      GxThemePage var0, String var1, boolean var2, View var3
   ) {
      renderChecklist$lambda$75$lambda$74(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$wRzAY6_6VBhO9_PUUgR7v4bQrvE(GxThemePage var0, String var1) {
      return build$lambda$8$lambda$7(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$xeeu7aVsRxBvmYHPYPyEu9KJtTM(ArrayList var0, BooleanRef var1, GxThemePage var2, GxThemeEntry var3, List var4) {
      return loadToEditor$lambda$135$lambda$134(var0, var1, var2, var3, var4);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$yiLsrSjeI5uz02Bfj82M1IUq_Y4(GxThemePage var0) {
      return renderTurn$lambda$69(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$zKOJvghbecKqsF0ecf9PT3zpPMo(GxThemePage var0, String var1, GxThemeFile var2) {
      return loadToEditor$lambda$135$slot$126$lambda$125(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$zWEWAB8Zu5iKYayF_o0f8lPwuU0/* $VF was: $r8$lambda$zWEWAB8Zu5iKYayF_o0f8lPwuU0*/(
      GxThemePage var0, String var1, String var2, String var3, JSONObject var4
   ) {
      return startAssetDownload$lambda$157(var0, var1, var2, var3, var4);
   }

   public GxThemePage(Activity var1, GxHost var2) {
      Intrinsics.checkNotNullParameter(var1, "act");
      Intrinsics.checkNotNullParameter(var2, "gx");
      super(var1, var2);
      this.colors = new LinkedHashMap<>();
      this.files = new HashMap<>();
      this.names = new HashMap<>();
      this.frames = new ArrayList<>();
      this.framesName = "";
      this.flags = new LinkedHashMap<>();
      this.sources = new HashMap<>();
      this.tsStyle = "Static";
      this.tsType = "Single Image";
      this.tsLength = 100;
      this.themeName = "";
      this.discord = "";
      this.rawThemes = CollectionsKt.emptyList();
      this.themes = CollectionsKt.emptyList();
      this.loading = true;
      this.loadError = "";
      this.downloadable = new HashMap<>();
      this.resetColors();
      LinkedHashMap var5 = this.flags;
      Pair var4 = TuplesKt.to("colors", true);
      Boolean var3 = false;
      var5.putAll(
         MapsKt.mapOf(
            new Pair[]{
               var4,
               TuplesKt.to("distance_icons", var3),
               TuplesKt.to("icons", var3),
               TuplesKt.to("sounds", var3),
               TuplesKt.to("steering_wheel", var3),
               TuplesKt.to("turn_signals", var3)
            }
         )
      );
   }

   private final boolean anyChecked() {
      var var3 = GxThemeKt.access$getGXTHEME_COMPS$p();
      boolean var2 = var3 instanceof Collection;
      boolean var1 = false;
      if (!var2 || !((Collection)var3).isEmpty()) {
         Iterator var4 = var3.iterator();

         while (var4.hasNext()) {
            if (this.checked((String)var4.next())) {
               var1 = true;
               break;
            }
         }
      }

      return var1;
   }

   private final void apply() {
      if (!this.busy) {
         if (!this.anyChecked()) {
            this.toast("Select at least one component to apply.");
         } else {
            this.confirm(
               "Apply theme?",
               "This makes the current colors/assets the active theme on device. It does not save them as a named theme.",
               "Apply",
               new GxThemePage$$ExternalSyntheticLambda62(this)
            );
         }
      }
   }

   private static final Unit apply$lambda$88(GxThemePage var0) {
      var0.setBusy(true);
      Pair var1 = var0.form();
      LinkedHashMap var2 = (LinkedHashMap)var1.component1();
      var0.upload(
         "/api/themes/apply", (List<Triple<String, String, Pair<byte[], String>>>)var1.component2(), var2, new GxThemePage$$ExternalSyntheticLambda55(var0)
      );
      return Unit.INSTANCE;
   }

   private static final Unit apply$lambda$88$lambda$87(GxThemePage var0, String var1, JSONObject var2) {
      var0.setBusy(false);
      String var4;
      if (var1 == null) {
         if (var2 != null) {
            var1 = var0.msg(var2);
         } else {
            var1 = null;
         }

         var4 = var1;
         if (var1 == null) {
            var4 = "Theme applied!";
         }
      } else {
         var4 = var1;
      }

      var0.toast(var4);
      return Unit.INSTANCE;
   }

   private final String assetPath(String var1, String var2, String var3) {
      var1 = this.enc(var1);
      var3 = CollectionsKt.joinToString$default(
         StringsKt.split$default(var3, new String[]{"/"}, false, 0, 6, null),
         "/",
         null,
         null,
         0,
         null,
         new GxThemePage$$ExternalSyntheticLambda66(this),
         30,
         null
      );
      var2 = this.typeQs(var2);
      return "/api/themes/asset/" + var1 + "/" + var3 + var2;
   }

   private static final CharSequence assetPath$lambda$14(GxThemePage var0, String var1) {
      Intrinsics.checkNotNullParameter(var1, "it");
      return var0.enc(var1);
   }

   private static final Unit build$lambda$12(GxThemePage var0, String var1, String var2) {
      Intrinsics.checkNotNullParameter(var2, "t");
      if (var1 == null && StringsKt.isBlank(var0.discord)) {
         var0.discord = StringsKt.trim(var2).toString();
         var0.paintDiscord();
      }

      return Unit.INSTANCE;
   }

   private static final Unit build$lambda$2(GxThemePage var0) {
      var0.save();
      return Unit.INSTANCE;
   }

   private static final Unit build$lambda$3(GxThemePage var0) {
      var0.apply();
      return Unit.INSTANCE;
   }

   private static final Unit build$lambda$4(GxThemePage var0) {
      var0.exportTheme();
      return Unit.INSTANCE;
   }

   private static final Unit build$lambda$5(GxThemePage var0) {
      var0.submit();
      return Unit.INSTANCE;
   }

   private static final Unit build$lambda$8(GxThemePage var0) {
      GxPage.prompt$default(
         var0,
         "Discord Username",
         "Discord Username",
         var0.discord,
         0,
         "Please enter your Discord username so we can contact you if needed.",
         new GxThemePage$$ExternalSyntheticLambda21(var0),
         8,
         null
      );
      return Unit.INSTANCE;
   }

   private static final Unit build$lambda$8$lambda$7(GxThemePage var0, String var1) {
      Intrinsics.checkNotNullParameter(var1, "v");
      var0.discord = StringsKt.trim(var1).toString();
      var0.paintDiscord();
      return Unit.INSTANCE;
   }

   private final void changed(String var1) {
      this.submitted = false;
      this.sources.put(var1, null);
   }

   private final boolean checked(String var1) {
      Object var3 = this.flags.get(var1);
      boolean var2 = true;
      if (!Intrinsics.areEqual(var3, true) || !this.hasContent(var1)) {
         var2 = false;
      }

      return var2;
   }

   private final void choose(String var1, boolean var2) {
      GxHost var4 = this.getGx();
      String var3;
      if (var2) {
         var3 = "image/*";
      } else {
         var3 = "audio/*";
      }

      GxThemePage$$ExternalSyntheticLambda44 var5 = new GxThemePage$$ExternalSyntheticLambda44(this, var2, var1);
      var4.pickFile(new String[]{var3}, var5);
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private static final Unit choose$lambda$83(GxThemePage var0, boolean var1, String var2, Uri var3) {
      if (var3 == null) {
         return Unit.INSTANCE;
      } else {
         Object var4;
         label28:
         try {
            var4 = Result.Companion;
            var4 = KtCompat.resultOf(var0.getAct().getContentResolver().getType(var3));
         } catch (Throwable var7) {
            Result.Companion var5 = Result.Companion;
            var4 = KtCompat.resultOf(ResultKt.createFailure(var7));
            break label28;
         }

         Object var9 = var4;
         if (KtCompat.isFailure(var4)) {
            var9 = null;
         }

         var0.readUri(var3, new GxThemePage$$ExternalSyntheticLambda12(var0, (String)var9, var1, var2));
         return Unit.INSTANCE;
      }
   }

   private static final Unit choose$lambda$83$lambda$82(GxThemePage var0, String var1, boolean var2, String var3, byte[] var4, String var5) {
      Intrinsics.checkNotNullParameter(var5, "name");
      if (var4 == null) {
         var0.toast("Could not read that file.");
         return Unit.INSTANCE;
      } else if (var4.length > 5242880) {
         var0.toast("That file is too large! Please upload files under 5MB.");
         return Unit.INSTANCE;
      } else {
         label47: {
            if (var1 != null) {
               CharSequence var6 = var1;
               CharSequence var7 = var6;
               if (StringsKt.isBlank(var6)) {
                  var7 = null;
               }

               String var9 = (String)var7;
               var1 = var9;
               if (var9 != null) {
                  break label47;
               }
            }

            String var10 = var0.guessMime(var5);
            var1 = var10;
            if (var10 == null) {
               var1 = "";
            }
         }

         if (var2 && !StringsKt.startsWith$default(var1, "image/", false, 2, null)) {
            var0.toast("Invalid file type! Please upload an image file.");
            return Unit.INSTANCE;
         } else if (!var2 && !StringsKt.startsWith$default(var1, "audio/", false, 2, null)) {
            var0.toast("Invalid file type! Please upload an audio file.");
            return Unit.INSTANCE;
         } else {
            String var11 = var0.compOf(var3);
            if (Intrinsics.areEqual(var3, "__sequential__")) {
               if (Intrinsics.areEqual(var1, "image/gif")) {
                  var0.toast("GIFs are not supported for sequential turn signals...");
                  return Unit.INSTANCE;
               }

               if (!StringsKt.isBlank(var0.framesName)) {
                  var0.framesName = "";
               }

               var0.frames.add(new GxThemeFile(var0.withExt(var5, var1), var4, var1));
            } else {
               var0.files.put(var3, new GxThemeFile(var0.withExt(var5, var1), var4, var1));
               var0.names.put(var3, var5);
            }

            var0.flags.put(var11, true);
            var0.changed(var11);
            var0.renderEditor();
            return Unit.INSTANCE;
         }
      }
   }

   private final void clearComp(String var1) {
      label52: {
         this.sources.put(var1, null);
         switch (var1.hashCode()) {
            case -1354842768:
               if (var1.equals("colors")) {
                  this.resetColors();
                  var4 = CollectionsKt.emptyList();
                  break label52;
               }
               break;
            case -896509628:
               if (var1.equals("sounds")) {
                  var4 = GxThemeKt.access$getGXTHEME_SOUNDS$p();
                  break label52;
               }
               break;
            case -217663920:
               if (var1.equals("distance_icons")) {
                  Iterable var2 = GxThemeKt.access$getGXTHEME_DISTANCE$p();
                  var var5 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var2, 10));

                  for (String var3 : var2) {
                     var5.add("distanceIcons_" + var3);
                  }

                  var4 = var5;
                  break label52;
               }
               break;
            case 100029210:
               if (var1.equals("icons")) {
                  var4 = CollectionsKt.listOf(new String[]{"homeButton", "settingsButton"});
                  break label52;
               }
               break;
            case 271106313:
               if (var1.equals("turn_signals")) {
                  this.frames.clear();
                  this.framesName = "";
                  var4 = CollectionsKt.listOf(new String[]{"turnSignal", "turnSignalBlindspot"});
                  break label52;
               }
               break;
            case 2119462413:
               if (var1.equals("steering_wheel")) {
                  var4 = CollectionsKt.listOf("steeringWheel");
                  break label52;
               }
         }

         var4 = CollectionsKt.emptyList();
      }

      for (String var6 : var4) {
         this.files.remove(var6);
         this.names.remove(var6);
      }
   }

   private final void clearSlot(String var1) {
      this.changed(this.compOf(var1));
      if (Intrinsics.areEqual(var1, "turnSignal") && Intrinsics.areEqual(this.tsType, "Sequential")) {
         this.frames.clear();
         this.framesName = "";
      }

      this.files.remove(var1);
      this.names.remove(var1);
      this.renderEditor();
   }

   private final JSONObject colorsJson() {
      JSONObject var3 = new JSONObject();

      for (Entry var4 : this.colors.entrySet()) {
         String var1 = (String)var4.getKey();
         int[] var5 = (int[])var4.getValue();
         var3.put(var1, new JSONObject().put("red", var5[0]).put("green", var5[1]).put("blue", var5[2]).put("alpha", var5[3]));
      }

      return var3;
   }

   private final String compOf(String var1) {
      if (StringsKt.startsWith$default(var1, "distanceIcons_", false, 2, null)) {
         var1 = "distance_icons";
      } else if (Intrinsics.areEqual(var1, "homeButton") || Intrinsics.areEqual(var1, "settingsButton")) {
         var1 = "icons";
      } else if (Intrinsics.areEqual(var1, "steeringWheel")) {
         var1 = "steering_wheel";
      } else if (!StringsKt.startsWith$default(var1, "turnSignal", false, 2, null) && !Intrinsics.areEqual(var1, "__sequential__")) {
         var1 = "sounds";
      } else {
         var1 = "turn_signals";
      }

      return var1;
   }

   private final void editColor(String var1) {
      int[] var4 = this.colors.get(var1);
      if (var4 != null) {
         String var3 = (String)GxThemeKt.access$getGXTHEME_COLOR_LABELS$p().get(var1);
         String var2 = var3;
         if (var3 == null) {
            var2 = var1;
         }

         this.gxThemeColorDialog(var2, Color.rgb(var4[0], var4[1], var4[2]), new GxThemePage$$ExternalSyntheticLambda60(this, var1));
      }
   }

   private static final Unit editColor$lambda$56(GxThemePage var0, String var1, int var2) {
      var0.colors.put(var1, new int[]{Color.red(var2), Color.green(var2), Color.blue(var2), 255});
      var0.flags.put("colors", true);
      var0.changed("colors");
      var0.renderEditor();
      return Unit.INSTANCE;
   }

   private final void exportTheme() {
      if (!this.busy) {
         if (StringsKt.isBlank(this.themeName)) {
            this.toast("Enter a theme name before exporting.");
         } else if (!this.anyChecked()) {
            this.toast("Select at least one component to export.");
         } else {
            String var1 = this.getHost();
            if (var1 != null) {
               this.setBusy(true);
               Pair var2 = this.form();
               LinkedHashMap var5 = (LinkedHashMap)var2.component1();
               List var4 = (List)var2.component2();
               CharSequence var8 = StringsKt.trim(this.themeName).toString();
               String var9 = new Regex("\\s+").replace(var8, "_");
               String var10 = var9 + ".zip";
               Builder var3 = new Builder(null, 1, null).setType(MultipartBody.FORM);

               for (Entry var6 : var5.entrySet()) {
                  var3.addFormDataPart((String)var6.getKey(), (String)var6.getValue());
               }

               for (Triple var7 : var4) {
                  String var11 = (String)var7.component1();
                  String var14 = (String)var7.component2();
                  Pair var15 = (Pair)var7.component3();
                  var3.addFormDataPart(
                     var11,
                     var14,
                     RequestBody.Companion.create$default(
                        RequestBody.Companion, (byte[])var15.getFirst(), MediaType.Companion.get((String)var15.getSecond()), 0, 0, 6, null
                     )
                  );
               }

               new Thread(new GxThemePage$$ExternalSyntheticLambda7(var1, var3.build(), var10, this)).start();
            }
         }
      }
   }

   private static final void exportTheme$lambda$102(String param0, MultipartBody param1, String param2, GxThemePage param3) {
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
      // 000: ldc_w "Failed to export theme."
      // 003: astore 5
      // 005: new kotlin/jvm/internal/Ref$ObjectRef
      // 008: dup
      // 009: invokespecial kotlin/jvm/internal/Ref$ObjectRef.<init> ()V
      // 00c: astore 7
      // 00e: getstatic com/starpilot/starview/GxPage.Companion Lcom/starpilot/starview/GxPage$Companion;
      // 011: ldc2_w 300
      // 014: invokevirtual com/starpilot/starview/GxPage$Companion.client (J)Lokhttp3/OkHttpClient;
      // 017: astore 8
      // 019: new okhttp3/Request$Builder
      // 01c: astore 6
      // 01e: aload 6
      // 020: invokespecial okhttp3/Request$Builder.<init> ()V
      // 023: new java/lang/StringBuilder
      // 026: astore 9
      // 028: aload 9
      // 02a: invokespecial java/lang/StringBuilder.<init> ()V
      // 02d: aload 9
      // 02f: ldc_w "http://"
      // 032: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 035: astore 9
      // 037: aload 8
      // 039: aload 6
      // 03b: aload 9
      // 03d: aload 0
      // 03e: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 041: ldc_w ":8082/api/themes/download"
      // 044: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 047: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 04a: invokevirtual okhttp3/Request$Builder.url (Ljava/lang/String;)Lokhttp3/Request$Builder;
      // 04d: aload 1
      // 04e: checkcast okhttp3/RequestBody
      // 051: invokevirtual okhttp3/Request$Builder.post (Lokhttp3/RequestBody;)Lokhttp3/Request$Builder;
      // 054: invokevirtual okhttp3/Request$Builder.build ()Lokhttp3/Request;
      // 057: invokevirtual okhttp3/OkHttpClient.newCall (Lokhttp3/Request;)Lokhttp3/Call;
      // 05a: invokeinterface okhttp3/Call.execute ()Lokhttp3/Response; 1
      // 05f: checkcast java/io/Closeable
      // 062: astore 8
      // 064: aload 8
      // 066: checkcast okhttp3/Response
      // 069: astore 1
      // 06a: aload 1
      // 06b: invokevirtual okhttp3/Response.isSuccessful ()Z
      // 06e: istore 4
      // 070: aconst_null
      // 071: astore 6
      // 073: iload 4
      // 075: ifne 111
      // 078: aload 1
      // 079: invokevirtual okhttp3/Response.body ()Lokhttp3/ResponseBody;
      // 07c: astore 0
      // 07d: aload 0
      // 07e: ifnull 08c
      // 081: aload 0
      // 082: invokevirtual okhttp3/ResponseBody.string ()Ljava/lang/String;
      // 085: astore 1
      // 086: aload 1
      // 087: astore 0
      // 088: aload 1
      // 089: ifnonnull 090
      // 08c: ldc_w ""
      // 08f: astore 0
      // 090: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 093: astore 1
      // 094: new org/json/JSONObject
      // 097: astore 2
      // 098: aload 2
      // 099: aload 0
      // 09a: invokespecial org/json/JSONObject.<init> (Ljava/lang/String;)V
      // 09d: aload 2
      // 09e: ldc_w "message"
      // 0a1: invokevirtual org/json/JSONObject.optString (Ljava/lang/String;)Ljava/lang/String;
      // 0a4: checkcast java/lang/CharSequence
      // 0a7: astore 1
      // 0a8: aload 1
      // 0a9: astore 0
      // 0aa: aload 1
      // 0ab: invokestatic kotlin/text/StringsKt.isBlank (Ljava/lang/CharSequence;)Z
      // 0ae: ifeq 0b9
      // 0b1: aload 2
      // 0b2: ldc_w "error"
      // 0b5: invokevirtual org/json/JSONObject.optString (Ljava/lang/String;)Ljava/lang/String;
      // 0b8: astore 0
      // 0b9: aload 0
      // 0ba: checkcast java/lang/String
      // 0bd: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 0c0: astore 0
      // 0c1: goto 0d1
      // 0c4: astore 0
      // 0c5: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 0c8: astore 1
      // 0c9: aload 0
      // 0ca: invokestatic kotlin/ResultKt.createFailure (Ljava/lang/Throwable;)Ljava/lang/Object;
      // 0cd: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 0d0: astore 0
      // 0d1: aload 0
      // 0d2: astore 1
      // 0d3: aload 0
      // 0d4: invokestatic kotlin/Result.isFailure-impl (Ljava/lang/Object;)Z
      // 0d7: ifeq 0dc
      // 0da: aconst_null
      // 0db: astore 1
      // 0dc: aload 1
      // 0dd: checkcast java/lang/String
      // 0e0: astore 0
      // 0e1: aload 0
      // 0e2: ifnull 102
      // 0e5: aload 0
      // 0e6: checkcast java/lang/CharSequence
      // 0e9: astore 0
      // 0ea: aload 0
      // 0eb: invokestatic kotlin/text/StringsKt.isBlank (Ljava/lang/CharSequence;)Z
      // 0ee: ifeq 0f7
      // 0f1: aload 6
      // 0f3: astore 0
      // 0f4: goto 0f7
      // 0f7: aload 0
      // 0f8: checkcast java/lang/String
      // 0fb: astore 1
      // 0fc: aload 1
      // 0fd: astore 0
      // 0fe: aload 1
      // 0ff: ifnonnull 106
      // 102: ldc_w "Failed to export theme."
      // 105: astore 0
      // 106: new java/lang/IllegalStateException
      // 109: astore 1
      // 10a: aload 1
      // 10b: aload 0
      // 10c: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
      // 10f: aload 1
      // 110: athrow
      // 111: new android/content/ContentValues
      // 114: astore 0
      // 115: aload 0
      // 116: invokespecial android/content/ContentValues.<init> ()V
      // 119: aload 0
      // 11a: ldc_w "_display_name"
      // 11d: aload 2
      // 11e: invokevirtual android/content/ContentValues.put (Ljava/lang/String;Ljava/lang/String;)V
      // 121: aload 0
      // 122: ldc_w "mime_type"
      // 125: ldc_w "application/zip"
      // 128: invokevirtual android/content/ContentValues.put (Ljava/lang/String;Ljava/lang/String;)V
      // 12b: aload 0
      // 12c: ldc_w "relative_path"
      // 12f: ldc_w "Download/StarView"
      // 132: invokevirtual android/content/ContentValues.put (Ljava/lang/String;Ljava/lang/String;)V
      // 135: aload 3
      // 136: invokevirtual com/starpilot/starview/GxThemePage.getAct ()Landroid/app/Activity;
      // 139: invokevirtual android/app/Activity.getContentResolver ()Landroid/content/ContentResolver;
      // 13c: getstatic android/provider/MediaStore$Downloads.EXTERNAL_CONTENT_URI Landroid/net/Uri;
      // 13f: aload 0
      // 140: invokevirtual android/content/ContentResolver.insert (Landroid/net/Uri;Landroid/content/ContentValues;)Landroid/net/Uri;
      // 143: astore 0
      // 144: aload 0
      // 145: ifnull 1b6
      // 148: aload 3
      // 149: invokevirtual com/starpilot/starview/GxThemePage.getAct ()Landroid/app/Activity;
      // 14c: invokevirtual android/app/Activity.getContentResolver ()Landroid/content/ContentResolver;
      // 14f: aload 0
      // 150: invokevirtual android/content/ContentResolver.openOutputStream (Landroid/net/Uri;)Ljava/io/OutputStream;
      // 153: astore 0
      // 154: aload 0
      // 155: ifnull 192
      // 158: aload 0
      // 159: checkcast java/io/Closeable
      // 15c: astore 0
      // 15d: aload 0
      // 15e: checkcast java/io/OutputStream
      // 161: astore 6
      // 163: aload 1
      // 164: invokevirtual okhttp3/Response.body ()Lokhttp3/ResponseBody;
      // 167: astore 1
      // 168: aload 1
      // 169: ifnull 17f
      // 16c: aload 1
      // 16d: invokevirtual okhttp3/ResponseBody.byteStream ()Ljava/io/InputStream;
      // 170: astore 1
      // 171: aload 1
      // 172: ifnull 17f
      // 175: aload 1
      // 176: aload 6
      // 178: bipush 0
      // 179: bipush 2
      // 17a: aconst_null
      // 17b: invokestatic kotlin/io/ByteStreamsKt.copyTo$default (Ljava/io/InputStream;Ljava/io/OutputStream;IILjava/lang/Object;)J
      // 17e: pop2
      // 17f: aload 0
      // 180: aconst_null
      // 181: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 184: goto 192
      // 187: astore 2
      // 188: aload 2
      // 189: athrow
      // 18a: astore 1
      // 18b: aload 0
      // 18c: aload 2
      // 18d: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 190: aload 1
      // 191: athrow
      // 192: aload 8
      // 194: aconst_null
      // 195: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 198: new java/lang/StringBuilder
      // 19b: astore 0
      // 19c: aload 0
      // 19d: invokespecial java/lang/StringBuilder.<init> ()V
      // 1a0: aload 7
      // 1a2: aload 0
      // 1a3: ldc_w "Theme exported as a zip: Downloads/StarView/"
      // 1a6: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 1a9: aload 2
      // 1aa: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 1ad: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 1b0: putfield kotlin/jvm/internal/Ref$ObjectRef.element Ljava/lang/Object;
      // 1b3: goto 1e9
      // 1b6: new java/lang/IllegalStateException
      // 1b9: astore 0
      // 1ba: aload 0
      // 1bb: ldc_w "cannot create file"
      // 1be: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
      // 1c1: aload 0
      // 1c2: athrow
      // 1c3: astore 1
      // 1c4: aload 1
      // 1c5: athrow
      // 1c6: astore 0
      // 1c7: aload 8
      // 1c9: aload 1
      // 1ca: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 1cd: aload 0
      // 1ce: athrow
      // 1cf: astore 0
      // 1d0: goto 1d4
      // 1d3: astore 0
      // 1d4: aload 0
      // 1d5: invokevirtual java/lang/Exception.getMessage ()Ljava/lang/String;
      // 1d8: astore 0
      // 1d9: aload 0
      // 1da: ifnonnull 1e3
      // 1dd: aload 5
      // 1df: astore 0
      // 1e0: goto 1e3
      // 1e3: aload 7
      // 1e5: aload 0
      // 1e6: putfield kotlin/jvm/internal/Ref$ObjectRef.element Ljava/lang/Object;
      // 1e9: aload 3
      // 1ea: invokevirtual com/starpilot/starview/GxThemePage.getMain ()Landroid/os/Handler;
      // 1ed: new com/starpilot/starview/GxThemePage$$ExternalSyntheticLambda53
      // 1f0: dup
      // 1f1: aload 3
      // 1f2: aload 7
      // 1f4: invokespecial com/starpilot/starview/GxThemePage$$ExternalSyntheticLambda53.<init> (Lcom/starpilot/starview/GxThemePage;Lkotlin/jvm/internal/Ref$ObjectRef;)V
      // 1f7: invokevirtual android/os/Handler.post (Ljava/lang/Runnable;)Z
      // 1fa: pop
      // 1fb: return
   }

   private static final void exportTheme$lambda$102$lambda$101(GxThemePage var0, ObjectRef var1) {
      var0.setBusy(false);
      var0.toast((String)var1.element);
   }

   private final void fetchAssets(List<String> var1, Function1<? super List<GxThemeFile>, Unit> var2) {
      if (var1.isEmpty()) {
         var2.invoke(CollectionsKt.emptyList());
      } else {
         Iterable var3 = var1;
         var var4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var3, 10));
         Iterator var6 = var3.iterator();

         while (var6.hasNext()) {
            var4.add(this.url((String)var6.next()));
         }

         var1 = var4;
         new Thread(new GxThemePage$$ExternalSyntheticLambda64(var1, this, var2)).start();
      }
   }

   private static final void fetchAssets$lambda$29(List param0, GxThemePage param1, Function1 param2) {
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
      // 000: aload 0
      // 001: checkcast java/lang/Iterable
      // 004: astore 5
      // 006: bipush 0
      // 007: istore 3
      // 008: new java/util/ArrayList
      // 00b: dup
      // 00c: aload 5
      // 00e: bipush 10
      // 010: invokestatic kotlin/collections/CollectionsKt.collectionSizeOrDefault (Ljava/lang/Iterable;I)I
      // 013: invokespecial java/util/ArrayList.<init> (I)V
      // 016: checkcast java/util/Collection
      // 019: astore 9
      // 01b: aload 5
      // 01d: astore 4
      // 01f: aload 4
      // 021: invokeinterface java/lang/Iterable.iterator ()Ljava/util/Iterator; 1
      // 026: astore 8
      // 028: aload 8
      // 02a: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 02f: ifeq 18f
      // 032: aload 8
      // 034: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 039: checkcast java/lang/String
      // 03c: astore 0
      // 03d: aconst_null
      // 03e: astore 6
      // 040: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 043: astore 7
      // 045: getstatic com/starpilot/starview/GxPage.Companion Lcom/starpilot/starview/GxPage$Companion;
      // 048: ldc2_w 30
      // 04b: invokevirtual com/starpilot/starview/GxPage$Companion.client (J)Lokhttp3/OkHttpClient;
      // 04e: astore 10
      // 050: new okhttp3/Request$Builder
      // 053: astore 7
      // 055: aload 7
      // 057: invokespecial okhttp3/Request$Builder.<init> ()V
      // 05a: aload 10
      // 05c: aload 7
      // 05e: aload 0
      // 05f: invokevirtual okhttp3/Request$Builder.url (Ljava/lang/String;)Lokhttp3/Request$Builder;
      // 062: invokevirtual okhttp3/Request$Builder.build ()Lokhttp3/Request;
      // 065: invokevirtual okhttp3/OkHttpClient.newCall (Lokhttp3/Request;)Lokhttp3/Call;
      // 068: invokeinterface okhttp3/Call.execute ()Lokhttp3/Response; 1
      // 06d: checkcast java/io/Closeable
      // 070: astore 10
      // 072: aload 10
      // 074: checkcast okhttp3/Response
      // 077: astore 7
      // 079: aload 7
      // 07b: invokevirtual okhttp3/Response.isSuccessful ()Z
      // 07e: ifne 086
      // 081: aconst_null
      // 082: astore 0
      // 083: goto 138
      // 086: aload 7
      // 088: invokevirtual okhttp3/Response.body ()Lokhttp3/ResponseBody;
      // 08b: astore 11
      // 08d: aload 11
      // 08f: ifnull 143
      // 092: aload 11
      // 094: invokevirtual okhttp3/ResponseBody.bytes ()[B
      // 097: astore 11
      // 099: aload 11
      // 09b: ifnonnull 0a1
      // 09e: goto 143
      // 0a1: aload 0
      // 0a2: bipush 63
      // 0a4: aconst_null
      // 0a5: bipush 2
      // 0a6: aconst_null
      // 0a7: invokestatic kotlin/text/StringsKt.substringBefore$default (Ljava/lang/String;CLjava/lang/String;ILjava/lang/Object;)Ljava/lang/String;
      // 0aa: astore 0
      // 0ab: aload 0
      // 0ac: bipush 47
      // 0ae: aconst_null
      // 0af: bipush 2
      // 0b0: aconst_null
      // 0b1: invokestatic kotlin/text/StringsKt.substringAfterLast$default (Ljava/lang/String;CLjava/lang/String;ILjava/lang/Object;)Ljava/lang/String;
      // 0b4: ldc_w "UTF-8"
      // 0b7: invokestatic java/net/URLDecoder.decode (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
      // 0ba: astore 12
      // 0bc: aload 7
      // 0be: ldc_w "Content-Type"
      // 0c1: aconst_null
      // 0c2: bipush 2
      // 0c3: aconst_null
      // 0c4: invokestatic okhttp3/Response.header$default (Lokhttp3/Response;Ljava/lang/String;Ljava/lang/String;ILjava/lang/Object;)Ljava/lang/String;
      // 0c7: astore 0
      // 0c8: aload 0
      // 0c9: ifnull 10d
      // 0cc: aload 0
      // 0cd: bipush 59
      // 0cf: aconst_null
      // 0d0: bipush 2
      // 0d1: aconst_null
      // 0d2: invokestatic kotlin/text/StringsKt.substringBefore$default (Ljava/lang/String;CLjava/lang/String;ILjava/lang/Object;)Ljava/lang/String;
      // 0d5: astore 0
      // 0d6: aload 0
      // 0d7: ifnull 10d
      // 0da: aload 0
      // 0db: checkcast java/lang/CharSequence
      // 0de: invokestatic kotlin/text/StringsKt.trim (Ljava/lang/CharSequence;)Ljava/lang/CharSequence;
      // 0e1: invokevirtual java/lang/Object.toString ()Ljava/lang/String;
      // 0e4: astore 0
      // 0e5: aload 0
      // 0e6: ifnull 10d
      // 0e9: aload 0
      // 0ea: checkcast java/lang/CharSequence
      // 0ed: astore 7
      // 0ef: aload 7
      // 0f1: astore 0
      // 0f2: aload 7
      // 0f4: invokestatic kotlin/text/StringsKt.isBlank (Ljava/lang/CharSequence;)Z
      // 0f7: ifeq 0fc
      // 0fa: aconst_null
      // 0fb: astore 0
      // 0fc: aload 0
      // 0fd: checkcast java/lang/String
      // 100: astore 7
      // 102: aload 7
      // 104: astore 0
      // 105: aload 7
      // 107: ifnonnull 126
      // 10a: goto 10d
      // 10d: aload 12
      // 10f: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNull (Ljava/lang/Object;)V
      // 112: aload 1
      // 113: aload 12
      // 115: invokespecial com/starpilot/starview/GxThemePage.guessMime (Ljava/lang/String;)Ljava/lang/String;
      // 118: astore 7
      // 11a: aload 7
      // 11c: astore 0
      // 11d: aload 7
      // 11f: ifnonnull 126
      // 122: ldc_w "application/octet-stream"
      // 125: astore 0
      // 126: aload 12
      // 128: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNull (Ljava/lang/Object;)V
      // 12b: new com/starpilot/starview/GxThemeFile
      // 12e: dup
      // 12f: aload 12
      // 131: aload 11
      // 133: aload 0
      // 134: invokespecial com/starpilot/starview/GxThemeFile.<init> (Ljava/lang/String;[BLjava/lang/String;)V
      // 137: astore 0
      // 138: goto 145
      // 13b: astore 0
      // 13c: goto 154
      // 13f: astore 0
      // 140: goto 154
      // 143: aconst_null
      // 144: astore 0
      // 145: aload 10
      // 147: aconst_null
      // 148: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 14b: aload 0
      // 14c: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 14f: astore 0
      // 150: goto 173
      // 153: astore 0
      // 154: aload 0
      // 155: athrow
      // 156: astore 7
      // 158: aload 10
      // 15a: aload 0
      // 15b: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 15e: aload 7
      // 160: athrow
      // 161: astore 0
      // 162: goto 166
      // 165: astore 0
      // 166: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 169: astore 7
      // 16b: aload 0
      // 16c: invokestatic kotlin/ResultKt.createFailure (Ljava/lang/Throwable;)Ljava/lang/Object;
      // 16f: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 172: astore 0
      // 173: aload 0
      // 174: invokestatic kotlin/Result.isFailure-impl (Ljava/lang/Object;)Z
      // 177: ifeq 180
      // 17a: aload 6
      // 17c: astore 0
      // 17d: goto 180
      // 180: aload 9
      // 182: aload 0
      // 183: checkcast com/starpilot/starview/GxThemeFile
      // 186: invokeinterface java/util/Collection.add (Ljava/lang/Object;)Z 2
      // 18b: pop
      // 18c: goto 028
      // 18f: aload 9
      // 191: checkcast java/util/List
      // 194: astore 0
      // 195: aload 1
      // 196: invokevirtual com/starpilot/starview/GxThemePage.getMain ()Landroid/os/Handler;
      // 199: new com/starpilot/starview/GxThemePage$$ExternalSyntheticLambda38
      // 19c: dup
      // 19d: aload 2
      // 19e: aload 0
      // 19f: invokespecial com/starpilot/starview/GxThemePage$$ExternalSyntheticLambda38.<init> (Lkotlin/jvm/functions/Function1;Ljava/util/List;)V
      // 1a2: invokevirtual android/os/Handler.post (Ljava/lang/Runnable;)Z
      // 1a5: pop
      // 1a6: return
   }

   private static final void fetchAssets$lambda$29$lambda$28(Function1 var0, List var1) {
      var0.invoke(var1);
   }

   private final void fetchDownloadables() {
      IntRef var1 = new IntRef();
      var1.element = GxThemeKt.access$getGXTHEME_BUCKET$p().size();

      for (Entry var4 : GxThemeKt.access$getGXTHEME_BUCKET$p().entrySet()) {
         String var2 = (String)var4.getKey();
         String var5 = (String)var4.getValue();
         this.getText("/api/params?key=" + var5, new GxThemePage$$ExternalSyntheticLambda9(this, var2, var1));
      }
   }

   private static final Unit fetchDownloadables$lambda$106(GxThemePage var0, String var1, IntRef var2, String var3, String var4) {
      Intrinsics.checkNotNullParameter(var4, "t");
      if (var3 == null) {
         Map var8 = var0.downloadable;
         Iterable var6 = StringsKt.split$default(var4, new String[]{","}, false, 0, 6, null);
         var var9 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var6, 10));
         Iterator var13 = var6.iterator();

         while (var13.hasNext()) {
            var9.add(StringsKt.trim((String & CharSequence)var13.next()).toString());
         }

         List var10 = var9;
         var6 = var10;
         var var11 = new ArrayList();

         for (Object var15 : var6) {
            boolean var5;
            if (((String)var15).length() > 0) {
               var5 = true;
            } else {
               var5 = false;
            }

            if (var5) {
               var11.add(var15);
            }
         }

         List var12 = var11;
         var8.put(var1, var12);
      }

      var2.element--;
      if (var2.element == 0) {
         var0.merge();
         var0.renderLibrary();
      }

      return Unit.INSTANCE;
   }

   private final Pair<LinkedHashMap<String, String>, List<Triple<String, String, Pair<byte[], String>>>> form() {
      LinkedHashMap var5 = new LinkedHashMap();
      JSONObject var6 = new JSONObject();

      for (String var4 : GxThemeKt.access$getGXTHEME_COMPS$p()) {
         var6.put(var4, this.checked(var4));
      }

      JSONObject var8 = new JSONObject();

      for (String var9 : GxThemeKt.access$getGXTHEME_COMPS$p()) {
         JSONObject var12 = this.sources.get(var9);
         Object var10 = var12;
         if (var12 == null) {
            var10 = JSONObject.NULL;
         }

         var8.put(var9, var10);
      }

      var5.put("themeName", StringsKt.trim(this.themeName).toString());
      var5.put("saveChecklist", var6.toString());
      var5.put("selectedThemeSources", var8.toString());
      if (!StringsKt.isBlank(this.discord)) {
         var5.put("discordUsername", StringsKt.trim(this.discord).toString());
      }

      if (this.checked("colors")) {
         var5.put("colors", this.colorsJson().toString());
      }

      ArrayList var11 = new ArrayList();
      if (this.checked("turn_signals")) {
         var5.put("turnSignalStyle", this.tsStyle);
         var5.put("turnSignalType", this.tsType);
         var5.put("turnSignalLength", String.valueOf(this.tsLength));
         if (Intrinsics.areEqual(this.tsType, "Sequential")) {
            Iterator var13 = this.frames.iterator();
            int var1 = 0;

            while (var13.hasNext()) {
               int var2 = var1 + 1;
               GxThemeFile var16 = (GxThemeFile)var13.next();
               form$add(var11, "turn_signal_" + (var1 + 1), var16);
               var1 = var2;
            }
         }

         form$add(var11, "turnSignal", this.files.get("turnSignal"));
         form$add(var11, "turnSignalBlindspot", this.files.get("turnSignalBlindspot"));
      }

      if (this.checked("icons")) {
         form$add(var11, "homeButton", this.files.get("homeButton"));
         form$add(var11, "settingsButton", this.files.get("settingsButton"));
      }

      if (this.checked("steering_wheel")) {
         form$add(var11, "steeringWheel", this.files.get("steeringWheel"));
      }

      if (this.checked("distance_icons")) {
         for (String var17 : GxThemeKt.access$getGXTHEME_DISTANCE$p()) {
            form$add(var11, "distanceIcons_" + var17, this.files.get("distanceIcons_" + var17));
         }
      }

      if (this.checked("sounds")) {
         for (String var18 : GxThemeKt.access$getGXTHEME_SOUNDS$p()) {
            form$add(var11, var18, this.files.get(var18));
         }
      }

      return TuplesKt.to(var5, var11);
   }

   private static final void form$add(ArrayList<Triple<String, String, Pair<byte[], String>>> var0, String var1, GxThemeFile var2) {
      if (var2 != null) {
         String var4 = var2.getFileName();
         byte[] var5 = var2.getBytes();
         CharSequence var3 = var2.getMime();
         Object var6 = var3;
         if (StringsKt.isBlank(var3)) {
            var6 = "application/octet-stream";
         }

         var0.add(new Triple<>(var1, var4, TuplesKt.to(var5, var6)));
      }
   }

   private final String guessMime(String var1) {
      MimeTypeMap var2 = MimeTypeMap.getSingleton();
      var1 = StringsKt.substringAfterLast(var1, '.', "").toLowerCase(Locale.ROOT);
      Intrinsics.checkNotNullExpressionValue(var1, "toLowerCase(...)");
      return var2.getMimeTypeFromExtension(var1);
   }

   private final void gxThemeColorDialog(String var1, int var2, Function1<? super Integer, Unit> var3) {
      float[] var10 = new float[3];
      Color.colorToHSV(var2, var10);
      LinearLayout var9 = new LinearLayout((Context)this.getAct());
      var9.setOrientation(1);
      var9.setPadding(this.getUi().dp(24), this.getUi().dp(12), this.getUi().dp(24), this.getUi().dp(4));
      View var12 = new View((Context)this.getAct());
      LayoutParams var6 = new LayoutParams(-1, this.getUi().dp(84));
      var6.bottomMargin = this.getUi().dp(12);
      Unit var7 = Unit.INSTANCE;
      var9.addView(var12, (android.view.ViewGroup.LayoutParams)var6);
      BooleanRef var15 = new BooleanRef();
      EditText var11 = new EditText((Context)this.getAct());
      var11.setTextSize(22.0F);
      var11.setTypeface(Typeface.MONOSPACE);
      var11.setSingleLine(true);
      var11.setFilters((InputFilter[])(new LengthFilter[]{new LengthFilter(7)}));
      var11.setInputType(528385);
      ArrayList var14 = new ArrayList();
      ArrayList var23 = new ArrayList();
      ArrayList var13 = new ArrayList();
      ObjectRef var26 = new ObjectRef();
      Iterator var8 = CollectionsKt.listOf(new String[]{"Hue", "Saturation", "Value"}).iterator();
      var2 = 0;

      while (var8.hasNext()) {
         int var5 = var2 + 1;
         String var18 = (String)var8.next();
         TextView var16 = new TextView((Context)this.getAct());
         var16.setTextSize(17.0F);
         var16.setPadding(0, this.getUi().dp(8), 0, this.getUi().dp(4));
         var9.addView((View)var16);
         var23.add(var16);
         var13.add(gxThemeColorDialog$strip(this, var9));
         SeekBar var17 = new SeekBar((Context)this.getAct());
         short var21;
         if (var2 == 0) {
            var21 = 360;
         } else {
            var21 = 100;
         }

         var17.setMax(var21);
         var17.setMinimumHeight(this.getUi().dp(48));
         var17.setThumbTintList(ColorStateList.valueOf(-1));
         var17.setProgressTintList(ColorStateList.valueOf(0));
         var17.setProgressBackgroundTintList(ColorStateList.valueOf(0));
         var17.setOnSeekBarChangeListener(new OnSeekBarChangeListener(var10, var2, var26) {
            final float[] $hsv;
            final int $i;
            final ObjectRef<Function1<Boolean, Unit>> $paint;

            {
               this.$hsv = var1;
               this.$i = var2x;
               this.$paint = var3x;
            }

            public void onProgressChanged(SeekBar var1, int var2x, boolean var3x) {
               if (var3x) {
                  float[] var6x = this.$hsv;
                  int var5x = this.$i;
                  float var4;
                  if (this.$i == 0) {
                     var4 = RangesKt.coerceAtMost(var2x, 359.9F);
                  } else {
                     var4 = var2x / 100.0F;
                  }

                  var6x[var5x] = var4;
                  Function1 var7x;
                  if (this.$paint.element == null) {
                     Intrinsics.throwUninitializedPropertyAccessException("paint");
                     var7x = null;
                  } else {
                     var7x = this.$paint.element;
                  }

                  var7x.invoke(false);
               }
            }

            public void onStartTrackingTouch(SeekBar var1) {
            }

            public void onStopTrackingTouch(SeekBar var1) {
            }
         });
         var9.addView((View)var17, (android.view.ViewGroup.LayoutParams)(new LayoutParams(-1, this.getUi().dp(48))));
         var14.add(var17);
         var16.setTag(var18);
         var2 = var5;
      }

      TextView var27 = new TextView((Context)this.getAct());
      var27.setText("Hex");
      var27.setTextSize(17.0F);
      var27.setPadding(0, this.getUi().dp(10), 0, 0);
      var9.addView((View)var27);
      var9.addView((View)var11);
      Object var28 = var13.get(0);
      Intrinsics.checkNotNullExpressionValue(var28, "get(...)");
      View var30 = (View)var28;
      var28 = new int[7];

      for (int var22 = 0; var22 < 7; var22++) {
         ((Object[])var28)[var22] = Color.HSVToColor(new float[]{var22 * 60.0F % 360.0F, 1.0F, 1.0F});
      }

      ((Object[])var28)[6] = -65536;
      Unit var31 = Unit.INSTANCE;
      gxThemeColorDialog$grad(this, var30, (int[])var28);
      var26.element = (T)(new GxThemePage$$ExternalSyntheticLambda19(var12, this, var13, var10, var14, var23, var15, var11));
      var11.addTextChangedListener(new TextWatcher(var15, var10, var26) {
         final float[] $hsv;
         final ObjectRef<Function1<Boolean, Unit>> $paint;
         final BooleanRef $updating;

         {
            this.$updating = var1;
            this.$hsv = var2x;
            this.$paint = var3x;
         }

         public void afterTextChanged(Editable var1) {
            if (!this.$updating.element) {
               if (var1 != null) {
                  String var2x = var1.toString();
                  if (var2x != null) {
                     String var3x = StringsKt.trim(var2x).toString();
                     if (var3x != null) {
                        String var4 = StringsKt.removePrefix(var3x, "#");
                        if (var4 != null) {
                           if (!new Regex("[0-9a-fA-F]{6}").matches(var4)) {
                              return;
                           }

                           Color.colorToHSV(Color.parseColor("#" + var4), this.$hsv);
                           Function1 var5;
                           if (this.$paint.element == null) {
                              Intrinsics.throwUninitializedPropertyAccessException("paint");
                              var5 = null;
                           } else {
                              var5 = this.$paint.element;
                           }

                           var5.invoke(true);
                           return;
                        }
                     }
                  }
               }
            }
         }

         public void beforeTextChanged(CharSequence var1, int var2x, int var3x, int var4) {
         }

         public void onTextChanged(CharSequence var1, int var2x, int var3x, int var4) {
         }
      });
      Function1 var24;
      if (var26.element == null) {
         Intrinsics.throwUninitializedPropertyAccessException("paint");
         var24 = null;
      } else {
         var24 = (Function1)var26.element;
      }

      var24.invoke(false);
      android.app.AlertDialog.Builder var25 = new android.app.AlertDialog.Builder((Context)this.getAct()).setTitle(var1);
      ScrollView var19 = new ScrollView((Context)this.getAct());
      var19.addView((View)var9);
      var25.setView((View)var19)
         .setPositiveButton("OK", new GxThemePage$$ExternalSyntheticLambda20(var11, var3, var10))
         .setNegativeButton("Cancel", null)
         .show();
   }

   private static final int gxThemeColorDialog$cur(float[] var0) {
      return Color.HSVToColor(var0);
   }

   private static final void gxThemeColorDialog$grad(GxThemePage var0, View var1, int[] var2) {
      GradientDrawable var3 = new GradientDrawable(Orientation.LEFT_RIGHT, var2);
      var3.setCornerRadius(var0.getUi().dp(8));
      var1.setBackground((Drawable)var3);
   }

   private static final Unit gxThemeColorDialog$lambda$168(
      View var0, GxThemePage var1, ArrayList var2, float[] var3, ArrayList var4, ArrayList var5, BooleanRef var6, EditText var7, boolean var8
   ) {
      int var11 = gxThemeColorDialog$cur(var3);
      var0.setBackground((Drawable)var1.getUi().rounded(var11, 12, Color.rgb(90, 96, 108)));
      SeekBar var13 = (SeekBar)var2.get(1);
      Intrinsics.checkNotNullExpressionValue(var13, "get(...)");
      gxThemeColorDialog$grad(
         var1, (View)var13, new int[]{Color.HSVToColor(new float[]{var3[0], 0.0F, var3[2]}), Color.HSVToColor(new float[]{var3[0], 1.0F, var3[2]})}
      );
      var13 = var2.get(2);
      Intrinsics.checkNotNullExpressionValue(var13, "get(...)");
      gxThemeColorDialog$grad(var1, (View)var13, new int[]{-16777216, Color.HSVToColor(new float[]{var3[0], var3[1], 1.0F})});
      ((SeekBar)var4.get(0)).setProgress(Math.round(var3[0]));
      var13 = (SeekBar)var4.get(1);
      float var10 = var3[1];
      float var9 = 100;
      var13.setProgress(Math.round(var10 * var9));
      ((SeekBar)var4.get(2)).setProgress(Math.round(var3[2] * var9));
      TextView var16 = (TextView)var5.get(0);
      int var12 = Math.round(var3[0]);
      var16.setText("Hue  " + var12 + "°");
      TextView var17 = (TextView)var5.get(1);
      var12 = Math.round(var3[1] * var9);
      var17.setText("Saturation  " + var12 + "%");
      TextView var18 = (TextView)var5.get(2);
      var12 = Math.round(var3[2] * var9);
      var18.setText("Value  " + var12 + "%");
      if (!var8) {
         var6.element = true;
         var7.setText(var1.hexOf(var11));
         var7.setSelection(var7.getText().length());
         var6.element = false;
      }

      return Unit.INSTANCE;
   }

   private static final void gxThemeColorDialog$lambda$170(EditText var0, Function1 var1, float[] var2, DialogInterface var3, int var4) {
      String var5 = StringsKt.removePrefix(StringsKt.trim(var0.getText().toString()).toString(), "#");
      if (new Regex("[0-9a-fA-F]{6}").matches(var5)) {
         var4 = Color.parseColor("#" + var5);
      } else {
         var4 = gxThemeColorDialog$cur(var2);
      }

      var1.invoke(var4);
   }

   private static final View gxThemeColorDialog$strip(GxThemePage var0, LinearLayout var1) {
      View var3 = new View((Context)var0.getAct());
      LayoutParams var2 = new LayoutParams(-1, var0.getUi().dp(16));
      var2.leftMargin = var0.getUi().dp(16);
      var2.rightMargin = var0.getUi().dp(16);
      Unit var4 = Unit.INSTANCE;
      var1.addView(var3, (android.view.ViewGroup.LayoutParams)var2);
      return var3;
   }

   private final boolean has(String var1) {
      Object var5 = this.files.get(var1);
      boolean var4 = true;
      boolean var3 = var4;
      if (var5 == null) {
         CharSequence var6 = this.names.get(var1);
         boolean var2;
         if (var6 != null && !StringsKt.isBlank(var6)) {
            var2 = false;
         } else {
            var2 = true;
         }

         if (!var2) {
            var3 = var4;
         } else {
            var3 = false;
         }
      }

      return var3;
   }

   private final boolean hasContent(String var1) {
      int var2 = var1.hashCode();
      boolean var4 = true;
      switch (var2) {
         case -1354842768:
            if (var1.equals("colors")) {
               return var4;
            }
            break;
         case -896509628:
            if (var1.equals("sounds")) {
               var var8 = GxThemeKt.access$getGXTHEME_SOUNDS$p();
               if (!(var8 instanceof Collection) || !((Collection)var8).isEmpty()) {
                  Iterator var9 = var8.iterator();

                  while (var9.hasNext()) {
                     if (this.has((String)var9.next())) {
                        return var4;
                     }
                  }

                  return false;
               }

               return false;
            }
            break;
         case -217663920:
            if (var1.equals("distance_icons")) {
               var var6 = GxThemeKt.access$getGXTHEME_DISTANCE$p();
               if (!(var6 instanceof Collection) || !((Collection)var6).isEmpty()) {
                  for (String var5 : var6) {
                     if (this.has("distanceIcons_" + var5)) {
                        return var4;
                     }
                  }

                  return false;
               }

               return false;
            }
            break;
         case 100029210:
            if (var1.equals("icons")) {
               if (!this.has("homeButton")) {
                  boolean var15;
                  if (this.has("settingsButton")) {
                     var15 = var4;
                  } else {
                     var15 = false;
                  }

                  return var15;
               }

               return var4;
            }
            break;
         case 271106313:
            if (var1.equals("turn_signals")) {
               boolean var3 = var4;
               if (!this.has("turnSignal")) {
                  var3 = var4;
                  if (!this.has("turnSignalBlindspot")) {
                     if (this.frames.isEmpty()) {
                        if (!StringsKt.isBlank(this.framesName)) {
                           var3 = var4;
                        } else {
                           var3 = false;
                        }

                        return var3;
                     }

                     return var4;
                  }
               }

               return var3;
            }
            break;
         case 2119462413:
            if (var1.equals("steering_wheel")) {
               return this.has("steeringWheel");
            }
      }

      return false;
   }

   private final String hexOf(int var1) {
      StringCompanionObject var2 = StringCompanionObject.INSTANCE;
      String var3 = String.format("#%02x%02x%02x", Arrays.copyOf(new Object[]{Color.red(var1), Color.green(var1), Color.blue(var1)}, 3));
      Intrinsics.checkNotNullExpressionValue(var3, "format(...)");
      return var3;
   }

   private final boolean isDownloadable(String var1, String var2) {
      List var5 = this.downloadable.get(var1);
      List var6 = var5;
      if (var5 == null) {
         var6 = CollectionsKt.emptyList();
      }

      var var7 = var6;
      boolean var4 = var7 instanceof Collection;
      boolean var3 = false;
      if (!var4 || !((Collection)var7).isEmpty()) {
         Iterator var8 = var7.iterator();

         while (var8.hasNext()) {
            if (Intrinsics.areEqual(this.norm((String)var8.next()), this.norm(var2))) {
               var3 = true;
               break;
            }
         }
      }

      return var3;
   }

   private final void loadBuiltinTurnSignals() {
      this.clearComp("turn_signals");
      this.submitted = false;
      this.tsLength = 100;
      this.tsType = "Single Image";
      this.tsStyle = "Traditional";
      this.names.put("turnSignal", "None");
      this.names.remove("turnSignalBlindspot");
      this.sources.put("turn_signals", new JSONObject().put("path", "__stock_none__").put("type", "stock_none"));
      this.flags.put("turn_signals", true);
      this.toast("Loaded stock turn signals.");
      this.renderEditor();
   }

   private final void loadComponent(GxThemeEntry var1, String var2) {
      if (!this.busy) {
         this.setBusy(true);
         this.clearComp(var2);
         String var4 = StringsKt.replace$default(var2, "_", " ", false, 4, null);
         String var3 = this.enc(var1.getPath());
         String var5 = this.typeQs(var1.getType());
         this.getJson("/api/themes/load/" + var3 + var5, new GxThemePage$$ExternalSyntheticLambda0(this, var2, var1, var4));
      }
   }

   private static final Unit loadComponent$lambda$152(GxThemePage var0, String var1, GxThemeEntry var2, String var3, String var4, JSONObject var5) {
      if (var4 == null && var5 != null) {
         JSONObject var7 = var5.optJSONObject("images");
         JSONObject var11 = var7;
         if (var7 == null) {
            var11 = new JSONObject();
         }

         ArrayList var9 = new ArrayList();
         switch (var1.hashCode()) {
            case -1354842768:
               if (var1.equals("colors")) {
                  JSONObject var19 = var5.optJSONObject("colors");
                  if (var19 != null) {
                     var0.setColors(var19);
                  }
               }
               break;
            case -896509628:
               if (var1.equals("sounds")) {
                  JSONObject var18 = var5.optJSONObject("sounds");
                  if (var18 != null) {
                     for (String var26 : GxThemeKt.access$getGXTHEME_SOUNDS$p()) {
                        loadComponent$lambda$152$slot$140(var0, var2, var9, var26, var18.optJSONObject(var26));
                     }
                  }
               }
               break;
            case -217663920:
               if (var1.equals("distance_icons")) {
                  var7 = var11.optJSONObject("distanceIcons");
                  if (var7 != null) {
                     for (String var17 : GxThemeKt.access$getGXTHEME_DISTANCE$p()) {
                        loadComponent$lambda$152$slot$140(var0, var2, var9, "distanceIcons_" + var17, var7.optJSONObject(var17));
                     }
                  }
               }
               break;
            case 100029210:
               if (var1.equals("icons")) {
                  loadComponent$lambda$152$slot$140(var0, var2, var9, "homeButton", var11.optJSONObject("homeButton"));
                  loadComponent$lambda$152$slot$140(var0, var2, var9, "settingsButton", var11.optJSONObject("settingsButton"));
               }
               break;
            case 271106313:
               if (var1.equals("turn_signals")) {
                  var0.tsLength = var5.optInt("turnSignalLength", 100);
                  CharSequence var8 = GxPage.str$default(var0, var5, "turnSignalType", null, 2, null);
                  var7 = var8;
                  if (StringsKt.isBlank(var8)) {
                     var7 = "Single Image";
                  }

                  var0.tsType = (String)var7;
                  var8 = GxPage.str$default(var0, var5, "turnSignalStyle", null, 2, null);
                  var7 = var8;
                  if (StringsKt.isBlank(var8)) {
                     var7 = "Static";
                  }

                  var0.tsStyle = (String)var7;
                  loadComponent$lambda$152$slot$140(var0, var2, var9, "turnSignal", var11.optJSONObject("turnSignal"));
                  loadComponent$lambda$152$slot$140(var0, var2, var9, "turnSignalBlindspot", var11.optJSONObject("turnSignalBlindspot"));
                  JSONArray var14 = var5.optJSONArray("sequentialImages");
                  List var15;
                  if (var14 != null) {
                     var15 = var0.strings(var14);
                  } else {
                     var15 = null;
                  }

                  List var23 = var15;
                  if (var15 == null) {
                     var23 = CollectionsKt.emptyList();
                  }

                  if (!var23.isEmpty()) {
                     var0.framesName = var2.getName();

                     for (String var24 : var23) {
                        var9.add(TuplesKt.to("signals/" + var24, new GxThemePage$$ExternalSyntheticLambda14(var0)));
                     }
                  }
               }
               break;
            case 2119462413:
               if (var1.equals("steering_wheel")) {
                  JSONObject var12 = var11.optJSONObject("steeringWheel");
                  if (var12 != null) {
                     String var28 = GxPage.str$default(var0, var12, "path", null, 2, null);
                     if (!StringsKt.isBlank(var28)) {
                        var0.names.put("steeringWheel", var2.getName());
                        CharSequence var22 = GxPage.str$default(var0, var12, "filename", null, 2, null);
                        Object var13 = var22;
                        if (StringsKt.isBlank(var22)) {
                           var13 = var2.getPath();
                        }

                        var9.add(TuplesKt.to(var28, new GxThemePage$$ExternalSyntheticLambda13(var0, (String)var13)));
                     }
                  }
               }
         }

         Iterable var20 = var9;
         boolean var6 = false;
         var var33 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var20, 10));

         for (Pair var35 : var20) {
            var33.add(var0.assetPath(var2.getPath(), var2.getType(), (String)var35.getFirst()));
         }

         List var21 = var33;
         var0.fetchAssets(var21, new GxThemePage$$ExternalSyntheticLambda15(var9, var0, var1, var2, var3));
         return Unit.INSTANCE;
      } else {
         var0.setBusy(false);
         if (var4 == null) {
            var1 = "Failed to load theme asset.";
         } else {
            var1 = var4;
         }

         var0.toast(var1);
         var0.renderEditor();
         return Unit.INSTANCE;
      }
   }

   private static final Unit loadComponent$lambda$152$lambda$145$lambda$144(GxThemePage var0, String var1, GxThemeFile var2) {
      Intrinsics.checkNotNullParameter(var2, "f");
      var0.files.put("steeringWheel", new GxThemeFile(var1, var2.getBytes(), var2.getMime()));
      return Unit.INSTANCE;
   }

   private static final Unit loadComponent$lambda$152$lambda$149(GxThemePage var0, GxThemeFile var1) {
      Intrinsics.checkNotNullParameter(var1, "f");
      var0.frames.add(var1);
      return Unit.INSTANCE;
   }

   private static final Unit loadComponent$lambda$152$lambda$151(ArrayList var0, GxThemePage var1, String var2, GxThemeEntry var3, String var4, List var5) {
      Intrinsics.checkNotNullParameter(var5, "res");
      boolean var7 = false;
      Iterator var14 = var5.iterator();
      int var6 = 0;

      while (true) {
         int var8 = var6;
         if (!var14.hasNext()) {
            var1.sources.put(var2, new JSONObject().put("path", var3.getPath()).put("type", var3.getType()));
            var1.flags.put(var2, true);
            var1.setBusy(false);
            String var10 = var3.getName();
            StringBuilder var12 = null;
            String var11;
            StringBuilder var13;
            if (var7) {
               var12 = new StringBuilder();
               var13 = var12.append("Failed to load ").append(var4).append(" from \"").append(var10);
               var11 = "\".";
            } else {
               var12 = new StringBuilder();
               var13 = var12.append("Loaded ").append(var4).append(" from \"").append(var10);
               var11 = "\"!";
            }

            var1.toast(var13.append(var11).toString());
            var1.renderEditor();
            return Unit.INSTANCE;
         }

         var6++;
         GxThemeFile var9 = (GxThemeFile)var14.next();
         if (var9 != null) {
            ((Function1)((Pair)var0.get(var8)).getSecond()).invoke(var9);
         } else {
            var7 = true;
         }
      }
   }

   private static final void loadComponent$lambda$152$slot$140(
      GxThemePage var0, GxThemeEntry var1, ArrayList<Pair<String, Function1<GxThemeFile, Unit>>> var2, String var3, JSONObject var4
   ) {
      if (var4 != null) {
         String var6 = GxPage.str$default(var0, var4, "path", null, 2, null);
         if (var6 != null) {
            CharSequence var5 = var6;
            CharSequence var7 = var5;
            if (StringsKt.isBlank(var5)) {
               var7 = null;
            }

            String var8 = (String)var7;
            if (var8 != null) {
               var0.names.put(var3, var1.getName());
               var2.add(TuplesKt.to(var8, new GxThemePage$$ExternalSyntheticLambda61(var0, var3)));
               return;
            }
         }
      }
   }

   private static final Unit loadComponent$lambda$152$slot$140$lambda$139(GxThemePage var0, String var1, GxThemeFile var2) {
      Intrinsics.checkNotNullParameter(var2, "f");
      var0.files.put(var1, var2);
      return Unit.INSTANCE;
   }

   private final void loadDefault() {
      this.getJson("/api/themes/default", new GxThemePage$$ExternalSyntheticLambda56(this));
   }

   private static final Unit loadDefault$lambda$43(GxThemePage var0, String var1, JSONObject var2) {
      if (var1 == null && var2 != null) {
         JSONObject var9 = var2.optJSONObject("colors");
         Collection var5 = null;
         if (var9 != null) {
            boolean var3;
            if (var9.length() > 0) {
               var3 = true;
            } else {
               var3 = false;
            }

            if (!var3) {
               var9 = null;
            }

            if (var9 != null) {
               var0.setColors(var9);
               var0.flags.put("colors", true);
            }
         }

         JSONObject var10 = var2.optJSONObject("theme_names");
         JSONObject var4 = var10;
         if (var10 == null) {
            var4 = new JSONObject();
         }

         JSONObject var6 = var2.optJSONObject("images");
         JSONObject var11 = var6;
         if (var6 == null) {
            var11 = new JSONObject();
         }

         ArrayList var7 = new ArrayList();
         loadDefault$lambda$43$slot(
            var0, var7, "homeButton", GxPage.str$default(var0, var11, "homeButton", null, 2, null), "icons", loadDefault$lambda$43$nm(var0, var4, "icons")
         );
         loadDefault$lambda$43$slot(
            var0,
            var7,
            "settingsButton",
            GxPage.str$default(var0, var11, "settingsButton", null, 2, null),
            "icons",
            loadDefault$lambda$43$nm(var0, var4, "icons")
         );
         loadDefault$lambda$43$slot(
            var0,
            var7,
            "steeringWheel",
            GxPage.str$default(var0, var11, "steeringWheel", null, 2, null),
            "steering_wheel",
            loadDefault$lambda$43$nm(var0, var4, "steeringWheel")
         );
         loadDefault$lambda$43$slot(
            var0,
            var7,
            "turnSignal",
            GxPage.str$default(var0, var11, "turnSignal", null, 2, null),
            "signals",
            loadDefault$lambda$43$nm(var0, var4, "turnSignals")
         );
         loadDefault$lambda$43$slot(
            var0,
            var7,
            "turnSignalBlindspot",
            GxPage.str$default(var0, var11, "turnSignalBlindspot", null, 2, null),
            "signals",
            loadDefault$lambda$43$nm(var0, var4, "turnSignals")
         );
         JSONObject var8 = var11.optJSONObject("distanceIcons");
         if (var8 != null) {
            for (String var27 : GxThemeKt.access$getGXTHEME_DISTANCE$p()) {
               loadDefault$lambda$43$slot(
                  var0,
                  var7,
                  "distanceIcons_" + var27,
                  GxPage.str$default(var0, var8, var27, null, 2, null),
                  "distance_icons",
                  loadDefault$lambda$43$nm(var0, var4, "distanceIcons")
               );
            }
         }

         var6 = var2.optJSONObject("sounds");
         if (var6 != null) {
            for (String var13 : GxThemeKt.access$getGXTHEME_SOUNDS$p()) {
               loadDefault$lambda$43$slot(
                  var0, var7, var13, GxPage.str$default(var0, var6, var13, null, 2, null), "sounds", loadDefault$lambda$43$nm(var0, var4, "sounds")
               );
            }
         }

         if (var2.optInt("turnSignalLength", 0) > 0) {
            var0.tsLength = var2.optInt("turnSignalLength");
         }

         CharSequence var29 = GxPage.str$default(var0, var2, "turnSignalStyle", null, 2, null);
         CharSequence var14 = var29;
         if (StringsKt.isBlank(var29)) {
            var14 = null;
         }

         var1 = (String)var14;
         if (var1 != null) {
            var0.tsStyle = var1;
         }

         CharSequence var30 = GxPage.str$default(var0, var2, "turnSignalType", null, 2, null);
         CharSequence var16 = var30;
         if (StringsKt.isBlank(var30)) {
            var16 = null;
         }

         var1 = (String)var16;
         if (var1 != null) {
            var0.tsType = var1;
         }

         JSONArray var22 = var2.optJSONArray("sequentialImages");
         List var18 = var5;
         if (var22 != null) {
            var18 = var0.strings(var22);
         }

         List var23 = var18;
         if (var18 == null) {
            var23 = CollectionsKt.emptyList();
         }

         if (!var23.isEmpty()) {
            var0.framesName = loadDefault$lambda$43$nm(var0, var4, "turnSignals");
            var0.frames.clear();

            for (String var24 : var23) {
               var7.add(TuplesKt.to("signals/" + var24, new GxThemePage$$ExternalSyntheticLambda58(var0)));
            }
         }

         var0.renderEditor();
         Iterable var20 = var7;
         var5 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var20, 10));
         Iterator var25 = var20.iterator();

         while (var25.hasNext()) {
            var5.add(var0.assetPath("__active__", "active", (String)((Pair)var25.next()).getFirst()));
         }

         List var21 = (List)var5;
         var0.fetchAssets(var21, new GxThemePage$$ExternalSyntheticLambda59(var7, var0));
         return Unit.INSTANCE;
      } else {
         var0.toast("Failed to load default theme.");
         return Unit.INSTANCE;
      }
   }

   private static final Unit loadDefault$lambda$43$lambda$40(GxThemePage var0, GxThemeFile var1) {
      Intrinsics.checkNotNullParameter(var1, "f");
      var0.frames.add(var1);
      return Unit.INSTANCE;
   }

   private static final Unit loadDefault$lambda$43$lambda$42(ArrayList var0, GxThemePage var1, List var2) {
      Intrinsics.checkNotNullParameter(var2, "res");
      Iterator var6 = var2.iterator();
      int var3 = 0;

      while (true) {
         int var4 = var3;
         if (!var6.hasNext()) {
            var1.renderEditor();
            return Unit.INSTANCE;
         }

         int var5 = var3 + 1;
         GxThemeFile var7 = (GxThemeFile)var6.next();
         var3 = var5;
         if (var7 != null) {
            ((Function1)((Pair)var0.get(var4)).getSecond()).invoke(var7);
            var3 = var5;
         }
      }
   }

   private static final String loadDefault$lambda$43$nm(GxThemePage var0, JSONObject var1, String var2) {
      CharSequence var4 = GxPage.str$default(var0, var1, var2, null, 2, null);
      Object var3 = var4;
      if (StringsKt.isBlank(var4)) {
         var3 = "Active";
      }

      return (String)var3;
   }

   private static final void loadDefault$lambda$43$slot(
      GxThemePage var0, ArrayList<Pair<String, Function1<GxThemeFile, Unit>>> var1, String var2, String var3, String var4, String var5
   ) {
      if (!StringsKt.isBlank(var3)) {
         var0.names.put(var2, var5);
         var1.add(TuplesKt.to(var4 + "/" + var3, new GxThemePage$$ExternalSyntheticLambda54(var0, var2)));
      }
   }

   private static final Unit loadDefault$lambda$43$slot$lambda$33(GxThemePage var0, String var1, GxThemeFile var2) {
      Intrinsics.checkNotNullParameter(var2, "f");
      var0.files.put(var1, var2);
      return Unit.INSTANCE;
   }

   private final void loadToEditor(GxThemeEntry var1) {
      if (!this.busy) {
         this.setBusy(true);
         String var3 = this.enc(var1.getPath());
         String var2 = this.typeQs(var1.getType());
         this.getJson("/api/themes/load/" + var3 + var2, new GxThemePage$$ExternalSyntheticLambda6(this, var1));
      }
   }

   private static final Unit loadToEditor$lambda$135(GxThemePage var0, GxThemeEntry var1, String var2, JSONObject var3) {
      if (var2 == null && var3 != null) {
         BooleanRef var9 = new BooleanRef();
         JSONObject var12 = var3.optJSONObject("colors");
         Object var7 = null;
         if (var12 != null) {
            boolean var4;
            if (var12.length() > 0) {
               var4 = true;
            } else {
               var4 = false;
            }

            if (!var4) {
               var12 = null;
            }

            if (var12 != null) {
               var0.setColors(var12);
               var0.flags.put("colors", true);
               var9.element = true;
            }
         }

         JSONObject var13 = var3.optJSONObject("images");
         JSONObject var6 = var13;
         if (var13 == null) {
            var6 = new JSONObject();
         }

         ArrayList var10 = new ArrayList();
         JSONObject var14 = var6.optJSONObject("distanceIcons");
         if (var14 != null) {
            for (String var11 : GxThemeKt.access$getGXTHEME_DISTANCE$p()) {
               loadToEditor$lambda$135$slot$126(var0, var10, "distanceIcons_" + var11, var14.optJSONObject(var11));
            }
         }

         for (String var29 : CollectionsKt.listOf(new String[]{"homeButton", "settingsButton", "steeringWheel", "turnSignal", "turnSignalBlindspot"})) {
            loadToEditor$lambda$135$slot$126(var0, var10, var29, var6.optJSONObject(var29));
         }

         JSONObject var16 = var3.optJSONObject("sounds");
         if (var16 != null) {
            for (String var33 : GxThemeKt.access$getGXTHEME_SOUNDS$p()) {
               loadToEditor$lambda$135$slot$126(var0, var10, var33, var16.optJSONObject(var33));
            }
         }

         CharSequence var31 = GxPage.str$default(var0, var3, "turnSignalStyle", null, 2, null);
         CharSequence var17 = var31;
         if (StringsKt.isBlank(var31)) {
            var17 = null;
         }

         var2 = (String)var17;
         if (var2 != null) {
            var0.tsStyle = var2;
         }

         if (var3.has("turnSignalLength") && !var3.isNull("turnSignalLength")) {
            byte var5 = 100;
            int var25 = var3.optInt("turnSignalLength", 100);
            if (var25 == 0) {
               var25 = var5;
            }

            var0.tsLength = var25;
         }

         JSONArray var23 = var3.optJSONArray("sequentialImages");
         List var19 = (List)var7;
         if (var23 != null) {
            var19 = var0.strings(var23);
         }

         List var24 = var19;
         if (var19 == null) {
            var24 = CollectionsKt.emptyList();
         }

         if (!var24.isEmpty()) {
            var0.tsType = "Sequential";
            var0.frames.clear();
            var0.framesName = "";

            for (String var20 : var24) {
               var10.add(TuplesKt.to("signals/" + var20, new GxThemePage$$ExternalSyntheticLambda4(var0)));
            }
         } else if (var6.optJSONObject("turnSignal") != null) {
            var0.tsType = "Single Image";
         }

         Iterable var21 = var10;
         var var27 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var21, 10));

         for (Pair var32 : var21) {
            var27.add(var0.assetPath(var1.getPath(), var1.getType(), (String)var32.getFirst()));
         }

         List var22 = var27;
         var0.fetchAssets(var22, new GxThemePage$$ExternalSyntheticLambda5(var10, var9, var0, var1));
         return Unit.INSTANCE;
      } else {
         var0.setBusy(false);
         if (var2 == null) {
            var2 = "Failed to load theme.";
         }

         var0.toast(var2);
         return Unit.INSTANCE;
      }
   }

   private static final Unit loadToEditor$lambda$135$lambda$132(GxThemePage var0, GxThemeFile var1) {
      Intrinsics.checkNotNullParameter(var1, "f");
      var0.frames.add(var1);
      var0.flags.put("turn_signals", true);
      return Unit.INSTANCE;
   }

   private static final Unit loadToEditor$lambda$135$lambda$134(ArrayList var0, BooleanRef var1, GxThemePage var2, GxThemeEntry var3, List var4) {
      Intrinsics.checkNotNullParameter(var4, "res");
      Iterator var8 = var4.iterator();
      int var5 = 0;

      while (true) {
         int var7 = var5;
         if (!var8.hasNext()) {
            var2.setBusy(false);
            String var9;
            if (!var1.element) {
               var9 = "That theme has no loadable content.";
            } else {
               String var10 = var3.getName();
               var9 = "Loaded \"" + var10 + "\" into the editor!";
            }

            var2.toast(var9);
            var2.renderEditor();
            return Unit.INSTANCE;
         }

         int var6 = var5 + 1;
         GxThemeFile var11 = (GxThemeFile)var8.next();
         var5 = var6;
         if (var11 != null) {
            ((Function1)((Pair)var0.get(var7)).getSecond()).invoke(var11);
            var1.element = true;
            var5 = var6;
         }
      }
   }

   private static final void loadToEditor$lambda$135$slot$126(
      GxThemePage var0, ArrayList<Pair<String, Function1<GxThemeFile, Unit>>> var1, String var2, JSONObject var3
   ) {
      if (var3 != null) {
         String var5 = GxPage.str$default(var0, var3, "path", null, 2, null);
         if (var5 != null) {
            CharSequence var4 = var5;
            CharSequence var6 = var4;
            if (StringsKt.isBlank(var4)) {
               var6 = null;
            }

            String var7 = (String)var6;
            if (var7 != null) {
               var1.add(TuplesKt.to(var7, new GxThemePage$$ExternalSyntheticLambda17(var0, var2)));
               return;
            }
         }
      }
   }

   private static final Unit loadToEditor$lambda$135$slot$126$lambda$125(GxThemePage var0, String var1, GxThemeFile var2) {
      Intrinsics.checkNotNullParameter(var2, "f");
      var0.files.put(var1, var2);
      var0.names.put(var1, var2.getFileName());
      var0.flags.put(var0.compOf(var1), true);
      return Unit.INSTANCE;
   }

   private final void merge() {
      ArrayList var5 = new ArrayList();
      HashMap var6 = new HashMap();

      for (JSONObject var10 : this.rawThemes) {
         GxThemeEntry var7 = new GxThemeEntry(
            GxPage.str$default(this, var10, "name", null, 2, null),
            GxPage.str$default(this, var10, "path", null, 2, null),
            GxPage.str$default(this, var10, "type", null, 2, null),
            var10.optBoolean("is_user_created"),
            false,
            false,
            48,
            null
         );

         for (String var4 : GxThemeKt.access$getGXTHEME_COMPS$p()) {
            String var3 = (String)GxThemeKt.access$getGXTHEME_COMP_FLAG$p().get(var4);
            String var2 = var3;
            if (var3 == null) {
               var2 = "";
            }

            boolean var1 = var10.optBoolean(var2);
            var7.getHas().put(var4, var1);
            var7.getLocal().put(var4, var1);
         }

         var5.add(var7);
         var6.put(this.norm(var7.getName()), var7);
      }

      for (String var16 : GxThemeKt.access$getGXTHEME_COMPS$p()) {
         List var14 = this.downloadable.get(var16);
         List var12 = var14;
         if (var14 == null) {
            var12 = CollectionsKt.emptyList();
         }

         for (String var19 : var12) {
            String var15 = this.norm(var19);
            GxThemeEntry var17 = (GxThemeEntry)var6.get(var15);
            if (var17 == null) {
               var17 = new GxThemeEntry(var19, var19, "holiday", false, true, false, 32, null);
               var17.getHas().put(var16, true);
               var5.add(var17);
               var6.put(var15, var17);
            } else {
               var17.getHas().put(var16, true);
            }
         }
      }

      this.themes = CollectionsKt.sortedWith(var5, new Comparator(StringsKt.getCASE_INSENSITIVE_ORDER(StringCompanionObject.INSTANCE)) {
         final Comparator $comparator;

         {
            this.$comparator = var1;
         }

         @Override
         public final int compare(T var1, T var2) {
            return this.$comparator.compare(((GxThemeEntry)var1).getName(), ((GxThemeEntry)var2).getName());
         }
      });
   }

   private final String msg(JSONObject var1) {
      CharSequence var2 = GxPage.str$default(this, var1, "message", null, 2, null);
      CharSequence var3 = var2;
      if (StringsKt.isBlank(var2)) {
         var3 = null;
      }

      return (String)var3;
   }

   private final String norm(String var1) {
      var1 = StringsKt.trim(var1).toString().toLowerCase(Locale.ROOT);
      Intrinsics.checkNotNullExpressionValue(var1, "toLowerCase(...)");
      CharSequence var3 = var1;
      CharSequence var4 = new Regex("[^a-z0-9]+").replace(var3, " ");
      return StringsKt.trim(new Regex("\\s+").replace(var4, " ")).toString();
   }

   private final void paintDiscord() {
      if (this.discordRow != null) {
         GxPage.Row var3 = this.discordRow;
         CharSequence var2 = null;
         GxPage.Row var1 = var3;
         if (var3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("discordRow");
            var1 = null;
         }

         CharSequence var6 = this.discord;
         if (!StringsKt.isBlank(var6)) {
            var2 = var6;
         }

         label19: {
            String var4 = (String)var2;
            if (var4 != null) {
               String var7 = var4 + " - used when you submit a theme";
               var2 = var7;
               if (var7 != null) {
                  break label19;
               }
            }

            var2 = "Required to submit a theme, so we can contact you if needed.";
         }

         var1.setDesc(var2);
      }
   }

   private final void refreshThemeList() {
      if (this.themes.isEmpty()) {
         this.loading = true;
         this.renderLibrary();
      }

      this.getJson("/api/themes/list", new GxThemePage$$ExternalSyntheticLambda63(this));
   }

   private static final Unit refreshThemeList$lambda$103(GxThemePage var0, String var1, JSONObject var2) {
      var0.loading = false;
      if (var1 == null && var2 != null) {
         var0.loadError = "";
         JSONArray var3 = var2.optJSONArray("themes");
         List var4;
         if (var3 != null) {
            var4 = var0.objects(var3);
         } else {
            var4 = null;
         }

         List var5 = var4;
         if (var4 == null) {
            var5 = CollectionsKt.emptyList();
         }

         var0.rawThemes = var5;
      } else {
         var0.loadError = "Failed to load themes.";
      }

      var0.merge();
      var0.renderLibrary();
      return Unit.INSTANCE;
   }

   private final void removeComponent(GxThemeEntry var1, String var2) {
      if (!this.busy) {
         String var3 = var1.getName();
         this.confirm(
            "Confirm Delete",
            "Are you sure you want to delete the theme \"" + var3 + "\"?",
            "Delete",
            new GxThemePage$$ExternalSyntheticLambda8(this, var2, var1)
         );
      }
   }

   private static final Unit removeComponent$lambda$156(GxThemePage var0, String var1, GxThemeEntry var2) {
      var0.setBusy(true);
      if (Intrinsics.areEqual(var1, "steering_wheel")) {
         var1 = "";
      } else {
         if (Intrinsics.areEqual(var1, "turn_signals")) {
            var1 = "signals";
         }

         var1 = "&component=" + var1;
      }

      GxPage var3 = var0;
      String var4 = var0.enc(var2.getPath());
      String var6 = var0.enc(var2.getType());
      GxPage.send$default(
         var3, "DELETE", "/api/themes/delete/" + var4 + "?type=" + var6 + var1, null, 0L, new GxThemePage$$ExternalSyntheticLambda37(var0), 12, null
      );
      return Unit.INSTANCE;
   }

   private static final Unit removeComponent$lambda$156$lambda$155(GxThemePage var0, String var1, JSONObject var2) {
      var0.setBusy(false);
      String var4;
      if (var1 == null) {
         if (var2 != null) {
            var1 = var0.msg(var2);
         } else {
            var1 = null;
         }

         var4 = var1;
         if (var1 == null) {
            var4 = "Theme deleted.";
         }
      } else {
         var4 = var1;
      }

      var0.toast(var4);
      var0.refreshThemeList();
      var0.fetchDownloadables();
      return Unit.INSTANCE;
   }

   private final void removeTheme(GxThemeEntry var1) {
      if (!this.busy) {
         String var2 = var1.getType().toLowerCase(Locale.ROOT);
         Intrinsics.checkNotNullExpressionValue(var2, "toLowerCase(...)");
         if (!Intrinsics.areEqual(var2, "holiday")) {
            if (Intrinsics.areEqual(var1.getType(), "steering_wheel")) {
               var2 = "steering wheel";
            } else {
               var2 = "theme";
            }

            String var3 = var1.getName();
            this.confirm(
               "Delete theme?", "Delete " + var2 + " \"" + var3 + "\"? This cannot be undone.", "Delete", new GxThemePage$$ExternalSyntheticLambda3(this, var1)
            );
            return;
         }
      }
   }

   private static final Unit removeTheme$lambda$154(GxThemePage var0, GxThemeEntry var1) {
      var0.setBusy(true);
      String var2;
      if (StringsKt.isBlank(var1.getType())) {
         var2 = "?type=user";
      } else {
         var2 = var0.enc(var1.getType());
         var2 = "?type=" + var2;
      }

      GxPage var3 = var0;
      String var4 = var0.enc(var1.getPath());
      GxPage.send$default(var3, "DELETE", "/api/themes/delete/" + var4 + var2, null, 0L, new GxThemePage$$ExternalSyntheticLambda26(var0), 12, null);
      return Unit.INSTANCE;
   }

   private static final Unit removeTheme$lambda$154$lambda$153(GxThemePage var0, String var1, JSONObject var2) {
      var0.setBusy(false);
      String var4;
      if (var1 == null) {
         if (var2 != null) {
            var1 = var0.msg(var2);
         } else {
            var1 = null;
         }

         var4 = var1;
         if (var1 == null) {
            var4 = "Theme deleted.";
         }
      } else {
         var4 = var1;
      }

      var0.toast(var4);
      var0.refreshThemeList();
      var0.fetchDownloadables();
      return Unit.INSTANCE;
   }

   private final void renderAll() {
      Iterable var3 = this.themes;
      var var2 = new ArrayList();

      for (var var3_it : var3) {

         var3 = var3_it;
         if (!((GxThemeEntry)var3).getPseudo()) {
            var2.add(var3);
         }
      }

      List var17 = var2;
      boolean var1 = var17.isEmpty();
      LinearLayout var9 = null;
      if (var1) {
         LinearLayout var22 = this.libBox;
         if (var22 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("libBox");
         } else {
            var9 = var22;
         }

         var9.addView((View)this.empty("No themes yet. Build one above and hit Save Theme."));
      } else {
         for (GxThemeEntry var6 : var17) {
            ArrayList var7;
            label51: {
               var7 = new ArrayList();
               String var10 = var6.getType();
               String var11;
               if (Intrinsics.areEqual(var10, "holiday")) {
                  var11 = "Holiday";
               } else {
                  if (!Intrinsics.areEqual(var10, "steering_wheel")) {
                     break label51;
                  }

                  var11 = "Wheel";
               }

               var7.add(var11);
            }

            for (String var23 : GxThemeKt.access$getGXTHEME_COMPS$p()) {
               if (Intrinsics.areEqual(var6.getLocal().get(var23), true)) {
                  String var18 = (String)GxThemeKt.access$getGXTHEME_COMP_LABELS$p().get(var23);
                  String var12 = var18;
                  if (var18 == null) {
                     var12 = var23;
                  }

                  var7.add(var12);
               }
            }

            GxPage var26 = this;
            String var24 = this.titleOf(var6);
            CharSequence var19 = CollectionsKt.joinToString$default(var7, " · ", null, null, 0, null, null, 62, null);
            CharSequence var13 = var19;
            if (StringsKt.isBlank(var19)) {
               var13 = null;
            }

            String var25 = (String)var13;
            LinearLayout var20 = this.libBox;
            LinearLayout var14 = var20;
            if (var20 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("libBox");
               var14 = null;
            }

            GxPage.Row var21 = GxPage.row$default(var26, var24, var25, (ViewGroup)var14, 0, 8, null);
            GxPage.Row.button$default(var21, "Load", false, new GxThemePage$$ExternalSyntheticLambda39(this, var6), 2, null);
            String var15 = var6.getType().toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(var15, "toLowerCase(...)");
            if (!Intrinsics.areEqual(var15, "holiday")) {
               GxPage.Row.button$default(var21, "Delete", false, new GxThemePage$$ExternalSyntheticLambda40(this, var6), 2, null);
            }
         }
      }
   }

   private static final Unit renderAll$lambda$115(GxThemePage var0, GxThemeEntry var1) {
      var0.loadToEditor(var1);
      return Unit.INSTANCE;
   }

   private static final Unit renderAll$lambda$116(GxThemePage var0, GxThemeEntry var1) {
      var0.removeTheme(var1);
      return Unit.INSTANCE;
   }

   private final void renderChecklist() {
      LinearLayout var5 = this.checkBox;
      LinearLayout var4 = var5;
      if (var5 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("checkBox");
         var4 = null;
      }

      var4.removeAllViews();
      LinearLayout var6 = new LinearLayout((Context)this.getAct());
      var6.setOrientation(0);
      var6.setPadding(0, this.getUi().dp(6), 0, this.getUi().dp(8));

      for (String var8 : GxThemeKt.access$getGXTHEME_COMPS$p()) {
         if (this.hasContent(var8)) {
            boolean var3 = Intrinsics.areEqual(this.flags.get(var8), true);
            TextView var7 = new TextView((Context)this.getAct());
            Object var11;
            if (var3) {
               var11 = "✓  ";
            } else {
               var11 = "";
            }

            Object var9 = GxThemeKt.access$getGXTHEME_COMP_LABELS$p().get(var8);
            var7.setText(var11 + var9);
            var7.setTextSize(18.0F);
            var7.setGravity(17);
            var7.setMinHeight(this.getUi().dp(56));
            var7.setPadding(this.getUi().dp(18), this.getUi().dp(8), this.getUi().dp(18), this.getUi().dp(8));
            var11 = this.getUi();
            int var1;
            if (var3) {
               var1 = CarUi.Companion.getCARD_ON();
            } else {
               var1 = Color.rgb(16, 18, 24);
            }

            int var2;
            if (var3) {
               var2 = CarUi.Companion.getACCENT();
            } else {
               var2 = Color.rgb(50, 55, 66);
            }

            var7.setBackground((Drawable)((CarUi)var11).rounded(var1, 12, var2));
            var11 = CarUi.Companion;
            if (var3) {
               var1 = var11.getACCENT();
            } else {
               var1 = var11.getTEXT();
            }

            var7.setTextColor(var1);
            Typeface var14;
            if (var3) {
               var14 = Typeface.DEFAULT_BOLD;
            } else {
               var14 = Typeface.DEFAULT;
            }

            var7.setTypeface(var14);
            var7.setOnClickListener(new GxThemePage$$ExternalSyntheticLambda18(this, var8, var3));
            View var21 = (View)var7;
            LayoutParams var20 = new LayoutParams(-2, -2);
            var20.rightMargin = this.getUi().dp(10);
            Unit var15 = Unit.INSTANCE;
            var6.addView(var21, (android.view.ViewGroup.LayoutParams)var20);
         }
      }

      var5 = this.checkBox;
      var4 = var5;
      if (var5 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("checkBox");
         var4 = null;
      }

      HorizontalScrollView var19 = new HorizontalScrollView((Context)this.getAct());
      var19.setHorizontalScrollBarEnabled(false);
      var19.addView((View)var6);
      var4.addView((View)var19);
   }

   private static final void renderChecklist$lambda$75$lambda$74(GxThemePage var0, String var1, boolean var2, View var3) {
      var0.flags.put(var1, var2 ^ true);
      var0.renderChecklist();
   }

   private final void renderColors() {
      LinearLayout var5 = this.colorsBox;
      LinearLayout var4 = var5;
      if (var5 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("colorsBox");
         var4 = null;
      }

      var4.removeAllViews();
      Set var13 = GxThemeKt.access$getGXTHEME_COLOR_LABELS$p().keySet();
      Intrinsics.checkNotNullExpressionValue(var13, "<get-keys>(...)");
      List var14 = CollectionsKt.toList(var13);
      int var2 = var14.size();
      byte var1 = 0;
      int var3 = ProgressionUtilKt.getProgressionLastElement(0, var2 - 1, 2);
      if (var3 <= 0) {
         while (true) {
            LinearLayout var7 = new LinearLayout((Context)this.getAct());
            var7.setOrientation(0);
            LinearLayout var6 = this.colorsBox;
            var5 = var6;
            if (var6 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("colorsBox");
               var5 = null;
            }

            var5.addView((View)var7, (android.view.ViewGroup.LayoutParams)CarUi.lp$default(this.getUi(), 0, 0, 3, null));

            for (int var12 = 0; var12 < 2; var12++) {
               String var8 = CollectionsKt.getOrNull(var14, var1 + var12);
               if (var8 == null) {
                  var7.addView(new View((Context)this.getAct()), (android.view.ViewGroup.LayoutParams)(new LayoutParams(0, 1, 1.0F)));
               } else {
                  int[] var21 = this.colors.get(var8);
                  int[] var18 = var21;
                  if (var21 == null) {
                     var18 = new int[]{0, 0, 0, 255};
                  }

                  var6 = new LinearLayout((Context)this.getAct());
                  var6.setOrientation(0);
                  var6.setGravity(16);
                  var6.setMinimumHeight(this.getUi().dp(76));
                  var6.setPadding(this.getUi().dp(16), this.getUi().dp(12), this.getUi().dp(16), this.getUi().dp(12));
                  var6.setBackground((Drawable)CarUi.rounded$default(this.getUi(), CarUi.Companion.getCARD(), 0, null, 6, null));
                  var6.setOnClickListener(new GxThemePage$$ExternalSyntheticLambda41(this, var8));
                  View var11 = new View((Context)this.getAct());
                  var11.setBackground((Drawable)this.getUi().rounded(Color.argb(var18[3], var18[0], var18[1], var18[2]), 10, Color.rgb(90, 96, 108)));
                  LayoutParams var9 = new LayoutParams(this.getUi().dp(52), this.getUi().dp(52));
                  var9.rightMargin = this.getUi().dp(14);
                  Unit var10 = Unit.INSTANCE;
                  var6.addView(var11, (android.view.ViewGroup.LayoutParams)var9);
                  LinearLayout var26 = new LinearLayout((Context)this.getAct());
                  var26.setOrientation(1);
                  TextView var27 = new TextView((Context)this.getAct());
                  var27.setText((CharSequence)GxThemeKt.access$getGXTHEME_COLOR_LABELS$p().get(var8));
                  var27.setTextColor(CarUi.Companion.getTEXT());
                  var27.setTextSize(21.0F);
                  var26.addView((View)var27);
                  TextView var28 = new TextView((Context)this.getAct());
                  var28.setText(this.hexOf(Color.rgb(var18[0], var18[1], var18[2])));
                  var28.setTextColor(CarUi.Companion.getDIM());
                  var28.setTextSize(15.0F);
                  var28.setTypeface(Typeface.MONOSPACE);
                  var26.addView((View)var28);
                  var6.addView((View)var26, (android.view.ViewGroup.LayoutParams)(new LayoutParams(0, -2, 1.0F)));
                  var6.addView((View)CarUi.button$default(this.getUi(), "Edit", false, new GxThemePage$$ExternalSyntheticLambda42(this, var8), 2, null));
                  View var25 = (View)var6;
                  LayoutParams var23 = new LayoutParams(0, -1, 1.0F);
                  if (var12 == 0) {
                     var23.rightMargin = this.getUi().dp(8);
                  }

                  Unit var19 = Unit.INSTANCE;
                  var7.addView(var25, (android.view.ViewGroup.LayoutParams)var23);
               }
            }

            if (var1 == var3) {
               break;
            }

            var1 += 2;
         }
      }

      GxPage var24 = this;
      Pair var20 = TuplesKt.to("Reset to Stock Green", new GxThemePage$$ExternalSyntheticLambda43(this));
      var4 = this.colorsBox;
      if (var4 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("colorsBox");
         var4 = null;
      }

      ViewGroup var16 = (ViewGroup)var4;
      GxPage.buttons$default(var24, new Pair[]{var20}, var16, false, 4, null);
   }

   private static final void renderColors$lambda$47$lambda$46(GxThemePage var0, String var1, View var2) {
      var0.editColor(var1);
   }

   private static final Unit renderColors$lambda$53(GxThemePage var0, String var1) {
      var0.editColor(var1);
      return Unit.INSTANCE;
   }

   private static final Unit renderColors$lambda$55(GxThemePage var0) {
      var0.resetColors();
      var0.flags.put("colors", true);
      var0.changed("colors");
      var0.renderEditor();
      return Unit.INSTANCE;
   }

   private final void renderComp(String var1) {
      GxThemeEntry var4;
      if (Intrinsics.areEqual(var1, "turn_signals")) {
         var4 = new GxThemeEntry("None (Stock)", "__stock_none__", "stock_none", false, false, true, 16, null);
      } else {
         var4 = new GxThemeEntry("Stock", "__stock__", "stock", false, false, true, 16, null);
      }

      Iterable var6 = this.themes;
      var var5 = new ArrayList();

      for (var var6_it : var6) {

         var6 = var6_it;
         GxThemeEntry var8 = (GxThemeEntry)var6;
         boolean var3;
         if (Intrinsics.areEqual(var1, "steering_wheel")) {
            if (Intrinsics.areEqual(var8.getType(), "steering_wheel")
               || Intrinsics.areEqual(var8.getType(), "holiday") && Intrinsics.areEqual(var8.getHas().get(var1), true)) {
               var3 = true;
            } else {
               var3 = false;
            }
         } else {
            var3 = Intrinsics.areEqual(var8.getHas().get(var1), true);
         }

         if (var3) {
            var5.add(var6);
         }
      }

      List var25 = var5;
      String var23 = (String)GxThemeKt.access$getGXTHEME_PARAM$p().get(var1);
      String var19 = var23;
      if (var23 == null) {
         var19 = "";
      }

      String var24 = this.pStr(var19);

      for (GxThemeEntry var26 : CollectionsKt.plus(CollectionsKt.listOf(var4), var25)) {
         boolean var2;
         if (!var26.getBuiltin() && !Intrinsics.areEqual(var26.getLocal().get(var1), true)) {
            var2 = false;
         } else {
            var2 = true;
         }

         String var9 = this.selectionValue(var26, var1);
         var5 = new ArrayList();
         if (var26.getBuiltin()) {
            var5.add("Built-in");
         }

         label92: {
            String var13 = var26.getType();
            String var14;
            if (Intrinsics.areEqual(var13, "holiday")) {
               if (var26.getPseudo()) {
                  break label92;
               }

               var14 = "Holiday";
            } else {
               if (!Intrinsics.areEqual(var13, "steering_wheel")) {
                  break label92;
               }

               var14 = "Wheel";
            }

            var5.add(var14);
         }

         if (!var2) {
            String var15;
            if (this.isDownloadable(var1, var26.getName())) {
               var15 = "Available to download";
            } else {
               var15 = "Not on this device";
            }

            var5.add(var15);
         }

         if (!StringsKt.isBlank(var24) && Intrinsics.areEqual(var24, var9) && this.pBool("CustomThemes")) {
            var5.add("In use");
         }

         GxPage var28 = this;
         String var10 = this.titleOf(var26);
         var var16 = CollectionsKt.joinToString$default(var5, " · ", null, null, 0, null, null, 62, null);
         boolean var12 = StringsKt.isBlank(var16);
         Object var21 = null;
         if (var12) {
            var16 = null;
         }

         String var11 = var16;
         LinearLayout var17 = this.libBox;
         if (var17 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("libBox");
            var17 = (LinearLayout)var21;
         }

         GxPage.Row var18 = GxPage.row$default(var28, var10, var11, (ViewGroup)var17, 0, 8, null);
         if (!var2 && this.isDownloadable(var1, var26.getName())) {
            var18.button("Download", true, new GxThemePage$$ExternalSyntheticLambda23(this, var1, var26));
         } else {
            GxPage.Row.button$default(var18, "Use", false, new GxThemePage$$ExternalSyntheticLambda24(this, var26, var1), 2, null);
         }

         if (!var26.getBuiltin() && !Intrinsics.areEqual(var26.getType(), "holiday") && Intrinsics.areEqual(var26.getLocal().get(var1), true)) {
            GxPage.Row.button$default(var18, "Delete", false, new GxThemePage$$ExternalSyntheticLambda25(this, var26, var1), 2, null);
         }
      }
   }

   private static final Unit renderComp$lambda$119(GxThemePage var0, String var1, GxThemeEntry var2) {
      var0.startAssetDownload(var1, var2.getName());
      return Unit.INSTANCE;
   }

   private static final Unit renderComp$lambda$120(GxThemePage var0, GxThemeEntry var1, String var2) {
      var0.useComponent(var1, var2);
      return Unit.INSTANCE;
   }

   private static final Unit renderComp$lambda$121(GxThemePage var0, GxThemeEntry var1, String var2) {
      var0.removeComponent(var1, var2);
      return Unit.INSTANCE;
   }

   private final void renderEditor() {
      if (this.colorsBox != null) {
         this.renderColors();
         LinearLayout var4 = this.distBox;
         Object var5 = null;
         LinearLayout var3 = var4;
         if (var4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("distBox");
            var3 = null;
         }

         var3.removeAllViews();
         var4 = this.distBox;
         var3 = var4;
         if (var4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("distBox");
            var3 = null;
         }

         this.note("Recommended size 250x250.", (ViewGroup)var3);

         for (String var10 : GxThemeKt.access$getGXTHEME_DISTANCE$p()) {
            String var8 = "distanceIcons_" + var10;
            boolean var2;
            if (var10.length() > 0) {
               var2 = true;
            } else {
               var2 = false;
            }

            if (var2) {
               StringBuilder var22 = new StringBuilder();
               char var1 = var10.charAt(0);
               String var6 = String.valueOf(var1);
               Intrinsics.checkNotNull(var6, "null cannot be cast to non-null type java.lang.String");
               var6 = var6.toUpperCase(Locale.ROOT);
               Intrinsics.checkNotNullExpressionValue(var6, "toUpperCase(...)");
               StringBuilder var23 = var22.append(var6);
               String var11 = var10.substring(1);
               Intrinsics.checkNotNullExpressionValue(var11, "substring(...)");
               var10 = var23.append(var11).toString();
            }

            LinearLayout var35 = this.distBox;
            var4 = var35;
            if (var35 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("distBox");
               var4 = null;
            }

            this.slotRow(var8, var10, true, (ViewGroup)var4);
         }

         var4 = this.iconsBox;
         var3 = var4;
         if (var4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("iconsBox");
            var3 = null;
         }

         var3.removeAllViews();
         var4 = this.iconsBox;
         var3 = var4;
         if (var4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("iconsBox");
            var3 = null;
         }

         this.note("Home Button: 250x250 · Settings Button: 169x104", (ViewGroup)var3);
         var4 = this.iconsBox;
         var3 = var4;
         if (var4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("iconsBox");
            var3 = null;
         }

         this.slotRow("homeButton", "Home Button", true, (ViewGroup)var3);
         var4 = this.iconsBox;
         var3 = var4;
         if (var4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("iconsBox");
            var3 = null;
         }

         this.slotRow("settingsButton", "Settings Button", true, (ViewGroup)var3);
         var4 = this.soundsBox;
         var3 = var4;
         if (var4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("soundsBox");
            var3 = null;
         }

         var3.removeAllViews();

         for (String var36 : GxThemeKt.access$getGXTHEME_SOUNDS$p()) {
            String var30 = (String)GxThemeKt.access$getGXTHEME_SOUND_LABELS$p().get(var36);
            String var17 = var30;
            if (var30 == null) {
               var17 = var36;
            }

            LinearLayout var37 = this.soundsBox;
            var4 = var37;
            if (var37 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("soundsBox");
               var4 = null;
            }

            this.slotRow(var36, var17, false, (ViewGroup)var4);
         }

         var4 = this.wheelBox;
         var3 = var4;
         if (var4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("wheelBox");
            var3 = null;
         }

         var3.removeAllViews();
         var4 = this.wheelBox;
         var3 = var4;
         if (var4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("wheelBox");
            var3 = null;
         }

         this.note("Recommended size 250x250.", (ViewGroup)var3);
         var3 = this.wheelBox;
         if (var3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("wheelBox");
            var3 = (LinearLayout)var5;
         }

         this.slotRow("steeringWheel", "Steering Wheel Image", true, (ViewGroup)var3);
         this.renderTurn();
         this.renderChecklist();
      }
   }

   private final void renderLibrary() {
      if (this.libBox != null) {
         LinearLayout var7 = this.libBar;
         LinearLayout var6 = var7;
         if (var7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("libBar");
            var6 = null;
         }

         var6.removeAllViews();
         Collection var8 = CollectionsKt.listOf("All");
         Iterable var14 = GxThemeKt.access$getGXTHEME_COMPS$p();
         var var9 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var14, 10));

         for (String var21 : var14) {
            String var15 = (String)GxThemeKt.access$getGXTHEME_COMP_LABELS$p().get(var21);
            if (var15 == null) {
               var15 = var21;
            }

            var9.add(var15);
         }

         List var16 = var9;
         List var17 = CollectionsKt.plus(var8, var16);
         Iterator var32 = var17.iterator();
         int var1 = 0;

         while (true) {
            boolean var5 = var32.hasNext();
            boolean var12 = true;
            if (!var5) {
               var7 = this.libBox;
               var6 = var7;
               if (var7 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("libBox");
                  var6 = null;
               }

               var6.removeAllViews();
               if (this.loading) {
                  var6 = this.libBox;
                  if (var6 == null) {
                     Intrinsics.throwUninitializedPropertyAccessException("libBox");
                     var6 = null;
                  }

                  var6.addView((View)this.empty("Loading themes..."));
                  return;
               }

               if (!StringsKt.isBlank(this.loadError)) {
                  String var31 = this.loadError;
                  var7 = this.libBox;
                  var6 = var7;
                  if (var7 == null) {
                     Intrinsics.throwUninitializedPropertyAccessException("libBox");
                     var6 = null;
                  }

                  this.note(var31, (ViewGroup)var6).setTextColor(CarUi.Companion.getWARN());
                  return;
               }

               if (this.libTab == 0) {
                  this.renderAll();
               } else {
                  this.renderComp((String)GxThemeKt.access$getGXTHEME_COMPS$p().get(this.libTab - 1));
               }

               return;
            }

            int var4 = var1 + 1;
            String var11 = (String)var32.next();
            if (var1 != this.libTab) {
               var12 = false;
            }

            LinearLayout var25 = this.libBar;
            var7 = var25;
            if (var25 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("libBar");
               var7 = null;
            }

            TextView var33 = new TextView((Context)this.getAct());
            var33.setText(var11);
            var33.setTextSize(17.0F);
            var33.setMinHeight(this.getUi().dp(52));
            var33.setGravity(17);
            var33.setPadding(this.getUi().dp(18), this.getUi().dp(8), this.getUi().dp(18), this.getUi().dp(8));
            CarUi var35 = this.getUi();
            CarUi.Companion var26 = CarUi.Companion;
            int var2;
            if (var12) {
               var2 = var26.getCARD_ON();
            } else {
               var2 = var26.getCARD();
            }

            Integer var27;
            if (var12) {
               var27 = CarUi.Companion.getACCENT();
            } else {
               var27 = null;
            }

            var33.setBackground((Drawable)var35.rounded(var2, 12, var27));
            CarUi.Companion var28 = CarUi.Companion;
            if (var12) {
               var2 = var28.getACCENT();
            } else {
               var2 = var28.getTEXT();
            }

            var33.setTextColor(var2);
            Typeface var29;
            if (var12) {
               var29 = Typeface.DEFAULT_BOLD;
            } else {
               var29 = Typeface.DEFAULT;
            }

            var33.setTypeface(var29);
            var33.setOnClickListener(new GxThemePage$$ExternalSyntheticLambda57(this, var1));
            View var36 = (View)var33;
            LayoutParams var30 = new LayoutParams(-2, -2);
            var30.rightMargin = this.getUi().dp(8);
            Unit var34 = Unit.INSTANCE;
            var7.addView(var36, (android.view.ViewGroup.LayoutParams)var30);
            var1 = var4;
         }
      }
   }

   private static final void renderLibrary$lambda$111$lambda$110(GxThemePage var0, int var1, View var2) {
      var0.libTab = var1;
      var0.renderLibrary();
   }

   private final void renderTurn() {
      LinearLayout var7 = this.turnBox;
      Object var6 = null;
      LinearLayout var5 = var7;
      if (var7 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("turnBox");
         var5 = null;
      }

      var5.removeAllViews();
      int var1 = 0;
      List var8 = CollectionsKt.listOf(new String[]{"Static", "Traditional"});
      int var2 = Intrinsics.areEqual(this.tsStyle, "Traditional");
      var7 = this.turnBox;
      var5 = var7;
      if (var7 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("turnBox");
         var5 = null;
      }

      this.choice(
         "Style",
         "Static - The turn signal animation appears next to the current speed.\nTraditional - The turn signal animation moves across the bottom of the screen.",
         var8,
         var2,
         (ViewGroup)var5,
         new GxThemePage$$ExternalSyntheticLambda27(this)
      );
      if (Intrinsics.areEqual(this.tsStyle, "Traditional")) {
         var8 = CollectionsKt.listOf(new String[]{"Sequential", "Single Image"});
         byte var4 = Intrinsics.areEqual(this.tsType, "Sequential");
         var7 = this.turnBox;
         var5 = var7;
         if (var7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("turnBox");
            var5 = null;
         }

         this.choice("Turn Signal Type", null, var8, var4 ^ 1, (ViewGroup)var5, new GxThemePage$$ExternalSyntheticLambda28(this));
      }

      GxPage var36 = this;
      var7 = this.turnBox;
      var5 = var7;
      if (var7 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("turnBox");
         var5 = null;
      }

      GxPage.Row var31 = GxPage.row$default(var36, "Length (25-1000ms)", null, (ViewGroup)var5, 0, 8, null);
      TextView var16 = new TextView((Context)this.getAct());
      var16.setText(String.valueOf(this.tsLength));
      var16.setTextSize(22.0F);
      var16.setTextColor(CarUi.Companion.getTEXT());
      var16.setTypeface(Typeface.DEFAULT_BOLD);
      var16.setMinWidth(this.getUi().dp(100));
      var16.setGravity(17);
      var16.setMinHeight(this.getUi().dp(56));
      var16.setOnClickListener(new GxThemePage$$ExternalSyntheticLambda29(this, var16));
      var31.add((View)this.getUi().stepper("−", new GxThemePage$$ExternalSyntheticLambda30(this, var16)));
      var31.add((View)var16);
      var31.add((View)this.getUi().stepper("+", new GxThemePage$$ExternalSyntheticLambda31(this, var16)));
      var7 = this.turnBox;
      var5 = var7;
      if (var7 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("turnBox");
         var5 = null;
      }

      this.slotRow("turnSignalBlindspot", "Blind Spot", true, (ViewGroup)var5);
      if (Intrinsics.areEqual(this.tsType, "Sequential")) {
         String var18;
         if (!StringsKt.isBlank(this.framesName)) {
            var18 = this.framesName;
         } else if (!this.frames.isEmpty()) {
            var2 = this.frames.size();
            var18 = var2 + " image(s) selected";
         } else {
            var18 = "No file selected";
         }

         GxPage var37 = this;
         var7 = this.turnBox;
         var6 = var7;
         if (var7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("turnBox");
            var6 = null;
         }

         GxPage.Row var19 = GxPage.row$default(var37, "Turn Signals", var18, (ViewGroup)var6, 0, 8, null);
         GxPage.Row.button$default(var19, "Add Frame", false, new GxThemePage$$ExternalSyntheticLambda32(this), 2, null);
         if (!this.frames.isEmpty() || !StringsKt.isBlank(this.framesName)) {
            GxPage.Row.button$default(var19, "Clear", false, new GxThemePage$$ExternalSyntheticLambda34(this), 2, null);
         }

         if (!this.frames.isEmpty()) {
            var6 = this.turnBox;
            var5 = (LinearLayout)var6;
            if (var6 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("turnBox");
               var5 = null;
            }

            this.text("Turn Signal Sequence Order", (ViewGroup)var5, 15.0F, CarUi.Companion.getDIM(), true)
               .setPadding(this.getUi().dp(6), this.getUi().dp(8), 0, this.getUi().dp(6));
            Iterator var34 = this.frames.iterator();

            while (true) {
               var2 = var1;
               if (!var34.hasNext()) {
                  break;
               }

               int var3 = var1 + 1;
               GxThemeFile var38 = (GxThemeFile)var34.next();
               String var9 = "Frame " + (var1 + 1);
               String var10 = var38.getFileName();
               var6 = this.turnBox;
               var5 = (LinearLayout)var6;
               if (var6 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("turnBox");
                  var5 = null;
               }

               GxPage.Row var22 = this.row(var9, var10, (ViewGroup)var5, 1);
               var6 = this.thumbOf(var38);
               if (var6 != null) {
                  var22.add((View)this.thumbView((Bitmap)var6));
               }

               if (var1 > 0) {
                  GxPage.Row.button$default(var22, "Up", false, new GxThemePage$$ExternalSyntheticLambda35(this, var1), 2, null);
               }

               var1 = var3;
               if (var2 < this.frames.size() - 1) {
                  GxPage.Row.button$default(var22, "Down", false, new GxThemePage$$ExternalSyntheticLambda36(this, var2), 2, null);
                  var1 = var3;
               }
            }
         }
      } else {
         var5 = this.turnBox;
         if (var5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("turnBox");
            var5 = (LinearLayout)var6;
         }

         this.slotRow("turnSignal", "Turn Signal", true, (ViewGroup)var5);
      }
   }

   private static final Unit renderTurn$lambda$61(GxThemePage var0, int var1) {
      String var2;
      if (var1 == 1) {
         var2 = "Traditional";
      } else {
         var2 = "Static";
      }

      var0.tsStyle = var2;
      var0.flags.put("turn_signals", true);
      var0.changed("turn_signals");
      var0.renderEditor();
      return Unit.INSTANCE;
   }

   private static final Unit renderTurn$lambda$62(GxThemePage var0, int var1) {
      String var2;
      if (var1 == 0) {
         var2 = "Sequential";
      } else {
         var2 = "Single Image";
      }

      var0.tsType = var2;
      var0.changed("turn_signals");
      var0.frames.clear();
      var0.framesName = "";
      var0.files.remove("turnSignal");
      var0.names.remove("turnSignal");
      var0.renderEditor();
      return Unit.INSTANCE;
   }

   private static final void renderTurn$lambda$65(GxThemePage var0, TextView var1, View var2) {
      GxPage.prompt$default(
         var0, "Length (25-1000ms)", "100", String.valueOf(var0.tsLength), 2, null, new GxThemePage$$ExternalSyntheticLambda22(var0, var1), 16, null
      );
   }

   private static final Unit renderTurn$lambda$65$lambda$64(GxThemePage var0, TextView var1, String var2) {
      Intrinsics.checkNotNullParameter(var2, "s");
      Integer var4 = StringsKt.toIntOrNull(StringsKt.trim(var2).toString());
      int var3;
      if (var4 != null) {
         var3 = var4;
      } else {
         var3 = 100;
      }

      renderTurn$setLen(var0, var1, var3);
      return Unit.INSTANCE;
   }

   private static final Unit renderTurn$lambda$66(GxThemePage var0, TextView var1) {
      renderTurn$setLen(var0, var1, var0.tsLength - 25);
      return Unit.INSTANCE;
   }

   private static final Unit renderTurn$lambda$67(GxThemePage var0, TextView var1) {
      renderTurn$setLen(var0, var1, var0.tsLength + 25);
      return Unit.INSTANCE;
   }

   private static final Unit renderTurn$lambda$68(GxThemePage var0) {
      var0.choose("__sequential__", true);
      return Unit.INSTANCE;
   }

   private static final Unit renderTurn$lambda$69(GxThemePage var0) {
      var0.clearSlot("turnSignal");
      return Unit.INSTANCE;
   }

   private static final Unit renderTurn$lambda$71(GxThemePage var0, int var1) {
      Collections.swap(var0.frames, var1, var1 - 1);
      var0.framesName = "";
      var0.changed("turn_signals");
      var0.renderEditor();
      return Unit.INSTANCE;
   }

   private static final Unit renderTurn$lambda$72(GxThemePage var0, int var1) {
      Collections.swap(var0.frames, var1, var1 + 1);
      var0.framesName = "";
      var0.changed("turn_signals");
      var0.renderEditor();
      return Unit.INSTANCE;
   }

   private static final void renderTurn$setLen(GxThemePage var0, TextView var1, int var2) {
      var0.tsLength = RangesKt.coerceIn(var2, 25, 1000);
      var1.setText(String.valueOf(var0.tsLength));
      var0.flags.put("turn_signals", true);
      var0.changed("turn_signals");
   }

   private final void resetColors() {
      this.colors.clear();

      for (Object var1 : GxThemeKt.access$getGXTHEME_COLOR_LABELS$p().keySet()) {
         Intrinsics.checkNotNullExpressionValue(var1, "next(...)");
         String var4 = (String)var1;
         Map var3 = this.colors;
         if (Intrinsics.areEqual(var4, "PathEdge")) {
            var1 = new int[]{18, 107, 54, 255};
         } else {
            var1 = new int[]{23, 134, 68, 255};
         }

         var3.put(var4, var1);
      }
   }

   private final void save() {
      if (!this.busy) {
         if (!StringsKt.isBlank(this.themeName) && this.anyChecked()) {
            this.setBusy(true);
            Pair var2 = this.form();
            LinkedHashMap var1 = (LinkedHashMap)var2.component1();
            this.upload(
               "/api/themes", (List<Triple<String, String, Pair<byte[], String>>>)var2.component2(), var1, new GxThemePage$$ExternalSyntheticLambda33(this)
            );
         } else {
            this.toast("Enter a theme name and select at least one component.");
         }
      }
   }

   private static final Unit save$lambda$86(GxThemePage var0, String var1, JSONObject var2) {
      var0.setBusy(false);
      if (var1 != null) {
         var0.toast(var1);
         return Unit.INSTANCE;
      } else {
         label18: {
            if (var2 != null) {
               String var5 = var0.msg(var2);
               var1 = var5;
               if (var5 != null) {
                  break label18;
               }
            }

            var1 = "Theme saved!";
         }

         var0.toast(var1);
         var0.themeName = "";
         EditText var6 = var0.nameEdit;
         EditText var4 = var6;
         if (var6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("nameEdit");
            var4 = null;
         }

         var4.setText("");
         var0.refreshThemeList();
         return Unit.INSTANCE;
      }
   }

   private final String selectionValue(GxThemeEntry var1, String var2) {
      String var5 = var1.getType();
      String var4 = "stock";
      String var6;
      if (Intrinsics.areEqual(var5, "stock")) {
         var6 = var4;
      } else if (Intrinsics.areEqual(var1.getType(), "stock_none")) {
         var6 = "none";
      } else {
         if (Intrinsics.areEqual(var2, "steering_wheel")) {
            boolean var3;
            if (var1.getPath().length() > 0) {
               var3 = true;
            } else {
               var3 = false;
            }

            if (var3) {
               CharSequence var7 = var1.getPath();
               String var8 = new Regex("\\.[^.]+$").replace(var7, "");
               return var8;
            }
         }

         var6 = var1.getPath();
      }

      return var6;
   }

   private final void setBusy(boolean var1) {
      this.busy = var1;
      if (this.busyText != null) {
         TextView var4 = this.busyText;
         TextView var3 = var4;
         if (var4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("busyText");
            var3 = null;
         }

         byte var2;
         if (var1) {
            var2 = 0;
         } else {
            var2 = 8;
         }

         var3.setVisibility(var2);
      }
   }

   private final void setColors(JSONObject var1) {
      this.resetColors();

      for (Object var3 : GxThemeKt.access$getGXTHEME_COLOR_LABELS$p().keySet()) {
         Intrinsics.checkNotNullExpressionValue(var3, "next(...)");
         String var4 = (String)var3;
         var3 = var1.optJSONObject(var4);
         if (var3 != null) {
            this.colors.put(var4, new int[]{var3.optInt("red", 0), var3.optInt("green", 0), var3.optInt("blue", 0), var3.optInt("alpha", 255)});
         }
      }
   }

   private final void slotRow(String var1, String var2, boolean var3, ViewGroup var4) {
      GxThemeFile var7;
      GxPage var8;
      String var12;
      label27: {
         var7 = this.files.get(var1);
         var8 = this;
         var12 = this.names.get(var1);
         if (var12 != null) {
            CharSequence var6 = var12;
            CharSequence var11 = var6;
            if (StringsKt.isBlank(var6)) {
               var11 = null;
            }

            String var13 = (String)var11;
            var12 = var13;
            if (var13 != null) {
               break label27;
            }
         }

         var12 = "No file selected";
      }

      GxPage.Row var9 = GxPage.row$default(var8, var2, var12, var4, 0, 8, null);
      if (var3 && var7 != null) {
         Bitmap var10 = this.thumbOf(var7);
         if (var10 != null) {
            var9.add((View)this.thumbView(var10));
         }
      }

      GxPage.Row.button$default(var9, "Choose", false, new GxThemePage$$ExternalSyntheticLambda51(this, var1, var3), 2, null);
      if (this.has(var1)) {
         GxPage.Row.button$default(var9, "Clear", false, new GxThemePage$$ExternalSyntheticLambda52(this, var1), 2, null);
      }
   }

   private static final Unit slotRow$lambda$59(GxThemePage var0, String var1, boolean var2) {
      var0.choose(var1, var2);
      return Unit.INSTANCE;
   }

   private static final Unit slotRow$lambda$60(GxThemePage var0, String var1) {
      var0.clearSlot(var1);
      return Unit.INSTANCE;
   }

   private final void startAssetDownload(String var1, String var2) {
      if (!this.downloading && !this.busy) {
         this.downloading = true;
         String var3 = StringsKt.replace$default(var1, "_", " ", false, 4, null);
         GxPage.send$default(
            this,
            "POST",
            "/api/themes/download_asset",
            new JSONObject().put("component", var1).put("name", var2),
            0L,
            new GxThemePage$$ExternalSyntheticLambda11(this, var3, var2),
            8,
            null
         );
      } else {
         this.toast("Please wait for the current download to finish.");
      }
   }

   private static final Unit startAssetDownload$lambda$157(GxThemePage var0, String var1, String var2, String var3, JSONObject var4) {
      if (var3 != null) {
         var0.downloading = false;
         var0.toast("Unable to start download.");
         return Unit.INSTANCE;
      } else {
         var0.toast("Downloading " + var1 + " for the \"" + var2 + "\" theme...");
         GxPage.Row var5 = var0.dlRow;
         Object var10 = null;
         GxPage.Row var7 = var5;
         if (var5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("dlRow");
            var7 = null;
         }

         var7.setTitle("Downloading " + var1 + ": " + var2);
         var5 = var0.dlRow;
         GxPage.Row var8 = var5;
         if (var5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("dlRow");
            var8 = null;
         }

         var8.setDesc("Downloading...");
         GxPage.Row var9 = var0.dlRow;
         if (var9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("dlRow");
            var9 = (GxPage.Row)var10;
         }

         var9.setVisible(true);
         Runnable var6 = new Anon_startAssetDownload_1_r_1(var0, var1, var2, new IntRef());
         var0.getMain().postDelayed(var6, 1000L);
         return Unit.INSTANCE;
      }
   }

   private final void submit() {
      if (!this.busy) {
         if (this.submitted) {
            this.toast("This theme has already been submitted...");
         } else {
            boolean var4 = this.files.isEmpty();
            boolean var3 = false;
            boolean var2 = var3;
            if (var4) {
               var2 = var3;
               if (this.frames.isEmpty()) {
                  Collection var5 = this.names.values();
                  Intrinsics.checkNotNullExpressionValue(var5, "<get-values>(...)");
                  var var8 = var5;
                  boolean var1;
                  if (((Collection)var8).isEmpty()) {
                     var1 = true;
                  } else {
                     Iterator var9 = var8.iterator();

                     while (true) {
                        if (!var9.hasNext()) {
                           var1 = true;
                           break;
                        }

                        String var6 = (String)var9.next();
                        Intrinsics.checkNotNull(var6);
                        if (!StringsKt.isBlank(var6)) {
                           var1 = false;
                           break;
                        }
                     }
                  }

                  var2 = var3;
                  if (var1) {
                     var2 = var3;
                     if (StringsKt.isBlank(this.framesName)) {
                        var2 = true;
                     }
                  }
               }
            }

            if (var2) {
               this.toast("Cannot submit an empty theme...");
            } else if (StringsKt.isBlank(this.discord)) {
               this.toast("Discord username is required for submission...");
            } else if (StringsKt.isBlank(this.themeName)) {
               this.toast("Please enter a theme name...");
            } else if (!this.anyChecked()) {
               this.toast("Please select at least one component to submit!");
            } else {
               Iterable var13 = GxThemeKt.access$getGXTHEME_COMPS$p();
               var var10 = new ArrayList();

               for (Object var7 : var13) {
                  if (this.checked((String)var7)) {
                     var10.add(var7);
                  }
               }

               List var11 = var10;
               String var12 = CollectionsKt.joinToString$default(var11, ", ", null, null, 0, null, new GxThemePage$$ExternalSyntheticLambda1(), 30, null);
               String var16 = StringsKt.trim(this.themeName).toString();
               String var15 = this.discord;
               this.confirm(
                  "Submit Theme for Community Use",
                  "Submit your theme for everyone to use!\n\nTheme: " + var16 + "\nDiscord: " + var15 + "\nComponents: " + var12,
                  "Submit",
                  new GxThemePage$$ExternalSyntheticLambda2(this)
               );
            }
         }
      }
   }

   private static final CharSequence submit$lambda$91(String var0) {
      Intrinsics.checkNotNullParameter(var0, "it");
      String var1 = (String)GxThemeKt.access$getGXTHEME_COMP_LABELS$p().get(var0);
      CharSequence var2;
      if (var1 != null) {
         var2 = var1;
      } else {
         var2 = var0;
      }

      return var2;
   }

   private static final Unit submit$lambda$93(GxThemePage var0) {
      var0.setBusy(true);
      Pair var1 = var0.form();
      LinkedHashMap var2 = (LinkedHashMap)var1.component1();
      var0.upload(
         "/api/themes/submit", (List<Triple<String, String, Pair<byte[], String>>>)var1.component2(), var2, new GxThemePage$$ExternalSyntheticLambda65(var0)
      );
      return Unit.INSTANCE;
   }

   private static final Unit submit$lambda$93$lambda$92(GxThemePage var0, String var1, JSONObject var2) {
      var0.setBusy(false);
      if (var1 != null) {
         var0.toast(var1);
         return Unit.INSTANCE;
      } else {
         label18: {
            if (var2 != null) {
               String var6 = var0.msg(var2);
               var1 = var6;
               if (var6 != null) {
                  break label18;
               }
            }

            var1 = "Theme submitted successfully!";
         }

         var0.toast(var1);
         var0.submitted = true;
         var0.themeName = "";
         EditText var5 = var0.nameEdit;
         EditText var3 = var5;
         if (var5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("nameEdit");
            var3 = null;
         }

         var3.setText("");
         return Unit.INSTANCE;
      }
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private final Bitmap thumbOf(GxThemeFile var1) {
      if (!var1.getThumbDone()) {
         var1.setThumbDone(true);

         Options var19;
         label116: {
            try {
               Result.Companion var20 = Result.Companion;
               GxThemePage var21 = this;
               var19 = new Options();
               var19.inJustDecodeBounds = true;
               BitmapFactory.decodeByteArray(var1.getBytes(), 0, var1.getBytes().length, var19);
            } catch (Throwable var17) {
               Result.Companion var4 = Result.Companion;
               var19 = (Options)KtCompat.resultOf(ResultKt.createFailure(var17));
               break label116;
            }

            byte var2 = 1;

            while (true) {
               try {
                  if (var19.outWidth / (var2 * 2) < 128 || var19.outHeight / (var2 * 2) < 128) {
                     break;
                  }
               } catch (Throwable var18) {
                  Result.Companion var23 = Result.Companion;
                  var19 = (Options)KtCompat.resultOf(ResultKt.createFailure(var18));
                  break label116;
               }

               var2 *= 2;
            }

            label99:
            try {
               byte[] var5 = var1.getBytes();
               int var3 = var1.getBytes().length;
               var19 = new Options();
               var19.inSampleSize = var2;
               Unit var6 = Unit.INSTANCE;
               var19 = (Options)KtCompat.resultOf(BitmapFactory.decodeByteArray(var5, 0, var3, var19));
            } catch (Throwable var16) {
               Result.Companion var24 = Result.Companion;
               var19 = (Options)KtCompat.resultOf(ResultKt.createFailure(var16));
               break label99;
            }
         }

         Object var26 = var19;
         if (KtCompat.isFailure(var19)) {
            var26 = null;
         }

         var1.setThumb((Bitmap)var26);
      }

      return var1.getThumb();
   }

   private final ImageView thumbView(Bitmap var1) {
      ImageView var2 = new ImageView((Context)this.getAct());
      var2.setImageBitmap(var1);
      var2.setScaleType(ScaleType.FIT_CENTER);
      var2.setBackground((Drawable)CarUi.rounded$default(this.getUi(), Color.rgb(6, 8, 11), 10, null, 4, null));
      var2.setPadding(this.getUi().dp(4), this.getUi().dp(4), this.getUi().dp(4), this.getUi().dp(4));
      LayoutParams var3 = new LayoutParams(this.getUi().dp(64), this.getUi().dp(64));
      var3.rightMargin = this.getUi().dp(10);
      var2.setLayoutParams((android.view.ViewGroup.LayoutParams)var3);
      return var2;
   }

   private final String titleOf(GxThemeEntry var1) {
      String var2 = var1.getName();
      String var3;
      if (var1.getUserCreated()) {
         var3 = "  ★";
      } else {
         var3 = "";
      }

      return var2 + var3;
   }

   private final String typeQs(String var1) {
      if (StringsKt.isBlank(var1)) {
         var1 = "";
      } else {
         var1 = this.enc(var1);
         var1 = "?type=" + var1;
      }

      return var1;
   }

   private final void useComponent(GxThemeEntry var1, String var2) {
      if (!this.busy) {
         String var3 = (String)GxThemeKt.access$getGXTHEME_PARAM$p().get(var2);
         if (var3 != null) {
            this.putParam("CustomThemes", "true", new GxThemePage$$ExternalSyntheticLambda16(this, var3, var1, var2));
         }
      }
   }

   private static final Unit useComponent$lambda$137(GxThemePage var0, String var1, GxThemeEntry var2, String var3, String var4) {
      if (var4 != null) {
         return Unit.INSTANCE;
      } else {
         var0.putParam(var1, var0.selectionValue(var2, var3), new GxThemePage$$ExternalSyntheticLambda10(var0, var2, var3));
         return Unit.INSTANCE;
      }
   }

   private static final Unit useComponent$lambda$137$lambda$136(GxThemePage var0, GxThemeEntry var1, String var2, String var3) {
      if (var3 != null) {
         return Unit.INSTANCE;
      } else {
         var0.renderLibrary();
         if (Intrinsics.areEqual(var1.getType(), "stock_none") && Intrinsics.areEqual(var2, "turn_signals")) {
            var0.loadBuiltinTurnSignals();
         } else {
            var0.loadComponent(var1, var2);
         }

         return Unit.INSTANCE;
      }
   }

   private final String withExt(String var1, String var2) {
      if (StringsKt.contains$default(var1, '.', false, 2, null)) {
         return var1;
      } else {
         var2 = MimeTypeMap.getSingleton().getExtensionFromMimeType(var2);
         return var2 == null ? var1 : var1 + "." + var2;
      }
   }

   @Override
   public void build() {
      GxPage.note$default(
         this, "Customize the on-road colors and assets. Changes apply to the active on-device theme; saving keeps them as a named theme.", null, 2, null
      );
      GxPage.header$default(this, "Colors", null, 2, null);
      this.colorsBox = GxPage.box$default(this, null, 1, null);
      GxPage.header$default(this, "Distance Icons", null, 2, null);
      this.distBox = GxPage.box$default(this, null, 1, null);
      GxPage.header$default(this, "Icons", null, 2, null);
      this.iconsBox = GxPage.box$default(this, null, 1, null);
      GxPage.header$default(this, "Sounds", null, 2, null);
      this.soundsBox = GxPage.box$default(this, null, 1, null);
      GxPage.header$default(this, "Steering Wheel", null, 2, null);
      this.wheelBox = GxPage.box$default(this, null, 1, null);
      GxPage.header$default(this, "Turn Signals", null, 2, null);
      this.turnBox = GxPage.box$default(this, null, 1, null);
      GxPage.header$default(this, "Save / Apply", null, 2, null);
      LinearLayout var3 = GxPage.card$default(this, null, 1, null);
      GxPage.text$default(this, "Theme Name", (ViewGroup)var3, 21.0F, CarUi.Companion.getTEXT(), false, 16, null);
      EditText var1 = new EditText((Context)this.getAct());
      var1.setHint("Enter a name for this theme...");
      var1.setTextSize(20.0F);
      var1.setTextColor(CarUi.Companion.getTEXT());
      var1.setHintTextColor(CarUi.Companion.getDIM());
      var1.setSingleLine(true);
      var1.setInputType(524289);
      var1.setBackground((Drawable)this.getUi().rounded(Color.rgb(14, 16, 21), 10, Color.rgb(60, 66, 78)));
      var1.setPadding(this.getUi().dp(16), this.getUi().dp(12), this.getUi().dp(16), this.getUi().dp(12));
      var1.addTextChangedListener(new TextWatcher() {

         public void afterTextChanged(Editable var1) {
            GxThemePage var3x;
            label11: {
               var3x = GxThemePage.this;
               if (var1 != null) {
                  String var2 = var1.toString();
                  var4 = var2;
                  if (var2 != null) {
                     break label11;
                  }
               }

               var4 = "";
            }

            var3x.themeName = var4;
         }

         public void beforeTextChanged(CharSequence var1, int var2, int var3x, int var4) {
         }

         public void onTextChanged(CharSequence var1, int var2, int var3x, int var4) {
         }
      });
      this.nameEdit = var1;
      EditText var2 = this.nameEdit;
      var1 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("nameEdit");
         var1 = null;
      }

      View var8 = (View)var1;
      LayoutParams var4 = new LayoutParams(-1, -2);
      var4.topMargin = this.getUi().dp(6);
      var4.bottomMargin = this.getUi().dp(12);
      Unit var15 = Unit.INSTANCE;
      var3.addView(var8, (android.view.ViewGroup.LayoutParams)var4);
      GxPage.text$default(this, "Select which components to include:", (ViewGroup)var3, 0.0F, 0, false, 28, null);
      this.checkBox = this.box((ViewGroup)var3);
      Pair var5 = TuplesKt.to("Save Theme", new GxThemePage$$ExternalSyntheticLambda45(this));
      Pair var9 = TuplesKt.to("Apply Theme", new GxThemePage$$ExternalSyntheticLambda46(this));
      Pair var19 = TuplesKt.to("Export (.zip)", new GxThemePage$$ExternalSyntheticLambda47(this));
      Pair var16 = TuplesKt.to("Submit Theme", new GxThemePage$$ExternalSyntheticLambda48(this));
      ViewGroup var6 = (ViewGroup)var3;
      this.buttons(new Pair[]{var5, var9, var19, var16}, var6, true);
      TextView var10 = GxPage.text$default(this, "Working…", (ViewGroup)var3, 15.0F, CarUi.Companion.getACCENT(), false, 16, null);
      var10.setVisibility(8);
      this.busyText = var10;
      this.discordRow = GxPage.row$default(this, "Discord Username", null, null, 0, 12, null);
      GxPage.Row var11 = this.discordRow;
      if (var11 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("discordRow");
         var11 = null;
      }

      GxPage.Row.button$default(var11, "Edit", false, new GxThemePage$$ExternalSyntheticLambda49(this), 2, null);
      this.paintDiscord();
      GxPage.header$default(this, "Theme Library", null, 2, null);
      GxPage.note$default(
         this,
         "All: load a whole theme into the editor. Pick a component to use it on the device right away (and load it into the editor), delete it, or download it.",
         null,
         2,
         null
      );
      LinearLayout var12 = new LinearLayout((Context)this.getAct());
      var12.setOrientation(0);
      var12.setPadding(0, 0, 0, this.getUi().dp(10));
      this.libBar = var12;
      LinearLayout var20 = this.getCol();
      HorizontalScrollView var18 = new HorizontalScrollView((Context)this.getAct());
      var18.setHorizontalScrollBarEnabled(false);
      LinearLayout var17 = this.libBar;
      LinearLayout var13 = var17;
      if (var17 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("libBar");
         var13 = null;
      }

      var18.addView((View)var13);
      var20.addView((View)var18);
      GxPage.Row var14 = GxPage.row$default(this, "Downloading", null, null, 0, 12, null);
      var14.setVisible(false);
      this.dlRow = var14;
      this.libBox = GxPage.box$default(this, null, 1, null);
      this.renderEditor();
      this.renderLibrary();
      this.loadDefault();
      this.getText("/api/params?key=DiscordUsername", new GxThemePage$$ExternalSyntheticLambda50(this));
   }

   @Override
   public void onShow() {
      this.refreshThemeList();
      this.fetchDownloadables();
   }

   static final class Anon_startAssetDownload_1_r_1 implements Runnable {
      Anon_startAssetDownload_1_r_1(GxThemePage var1, String var2x, String var3x, IntRef var4x) {
         this.this$0 = var1;
         this.$what = var2x;
         this.$name = var3x;
         this.$polls = var4x;
      }

            final String $name;
            final IntRef $polls;
            final String $what;
            final GxThemePage this$0;

            // $VF: synthetic method
            public static Unit $r8$lambda$m3BBrL6eZS_krnqH_uIav70_098/* $VF was: $r8$lambda$m3BBrL6eZS_krnqH_uIav70_098*/(
               GxThemePage var0, String var1, String var2, IntRef var3, Anon_startAssetDownload_1_r_1 var4, String var5, String var6
            ) {
               return run$lambda$0(var0, var1, var2, var3, var4, var5, var6);
            }

            private static final Unit run$lambda$0(
               GxThemePage var0x, String var1x, String var2x, IntRef var3x, Anon_startAssetDownload_1_r_1 var4x, String var5x, String var6x
            ) {
               Intrinsics.checkNotNullParameter(var6x, "t");
               String var13 = StringsKt.trim(var6x).toString();
               boolean var7x;
               if (var13.length() > 0) {
                  var7x = true;
               } else {
                  var7x = false;
               }

               Object var18 = null;
               Object var11 = null;
               Object var10x = null;
               if (var7x) {
                  GxPage.Row var12 = var0x.dlRow;
                  GxPage.Row var17 = var12;
                  if (var12 == null) {
                     Intrinsics.throwUninitializedPropertyAccessException("dlRow");
                     var17 = null;
                  }

                  var17.setDesc(var13);
               }

               boolean var8x = new Regex("Downloaded!", RegexOption.IGNORE_CASE).containsMatchIn(var13);
               boolean var9x = new Regex("failed|cancelled", RegexOption.IGNORE_CASE).containsMatchIn(var13);
               if (var8x) {
                  var0x.downloading = false;
                  GxPage.Row var16 = var0x.dlRow;
                  if (var16 == null) {
                     Intrinsics.throwUninitializedPropertyAccessException("dlRow");
                     var16 = (GxPage.Row)var10x;
                  }

                  var16.setVisible(false);
                  var0x.toast("Downloaded " + var1x + " for \"" + var2x + "\".");
                  var0x.refreshThemeList();
                  var0x.fetchDownloadables();
               } else if (var9x) {
                  var0x.downloading = false;
                  GxPage.Row var14 = var0x.dlRow;
                  if (var14 == null) {
                     Intrinsics.throwUninitializedPropertyAccessException("dlRow");
                     var14 = (GxPage.Row)var18;
                  }

                  var14.setVisible(false);
                  var0x.toast("Download " + var13);
               } else {
                  var3x.element++;
                  if (var3x.element > 900) {
                     var0x.downloading = false;
                     GxPage.Row var15 = var0x.dlRow;
                     if (var15 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("dlRow");
                        var15 = (GxPage.Row)var11;
                     }

                     var15.setVisible(false);
                     var0x.toast("Download timed out.");
                  } else {
                     var0x.getMain().postDelayed(var4x, 1000L);
                  }
               }

               return Unit.INSTANCE;
            }

            @Override
            public void run() {
               this.this$0
                  .getText(
                     "/api/params_memory?key=ThemeDownloadProgress",
                     new GxThemePage$startAssetDownload$1$r$1$$ExternalSyntheticLambda0(this.this$0, this.$what, this.$name, this.$polls, this)
                  );
            }
            }
}
