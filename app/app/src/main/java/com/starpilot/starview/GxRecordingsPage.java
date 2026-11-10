package com.starpilot.starview;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.LruCache;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;
import android.widget.ImageView.ScaleType;
import androidx.core.widget.NestedScrollView;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.ArrayDeque;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.comparisons.ComparisonsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.jvm.internal.Ref.ObjectRef;
import kotlin.sequences.SequencesKt;
import kotlin.text.MatchResult;
import kotlin.text.Regex;
import kotlin.text.RegexOption;
import kotlin.text.StringsKt;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.Request.Builder;
import org.json.JSONArray;
import org.json.JSONObject;

public final class GxRecordingsPage extends GxPage {
   private boolean preservedOnly;
   private boolean renderPending;
   private Call routeCall;
   private final ArrayList<GxRecordingsRoute> routes;
   private LinearLayout routesDeleteBox;
   private String routesError;
   private int routesGen;
   private LinearLayout routesList;
   private boolean routesLoaded;
   private boolean routesLoading;
   private int routesProgress;
   private TextView routesStats;
   private TextView routesStatus;
   private int routesTotal;
   private Call screenCall;
   private TextView screenCount;
   private LinearLayout screenDeleteBox;
   private String screenError;
   private int screenGen;
   private LinearLayout screenList;
   private boolean screenLoaded;
   private boolean screenLoading;
   private final ArrayList<GxRecordingsScreen> screens;
   private String search;
   private int sortOrder;
   private int tab;
   private int thumbActive;
   private final LruCache<String, Bitmap> thumbCache;
   private final HashSet<String> thumbFailed;
   private final ArrayDeque<GxRecordingsPage.ThumbJob> thumbQueue;

   // $VF: synthetic method
   public static Unit $r8$lambda$_sjguvFmoWZPwtz0ttX_AFy_sro/* $VF was: $r8$lambda$_sjguvFmoWZPwtz0ttX_AFy_sro*/(
      GxRecordingsPage var0, GxRecordingsRoute var1, String var2, JSONObject var3
   ) {
      return openLogs$lambda$82(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$0fleQ5A89asOrpRurebQlSAALCE(GxRecordingsPage var0, JSONObject var1, String var2, String var3) {
      return openLogs$lambda$82$lambda$81(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$1AHRY8nV_05u7eDef3VdLmM6Wj0/* $VF was: $r8$lambda$1AHRY8nV_05u7eDef3VdLmM6Wj0*/(GxRecordingsPage var0, GxRecordingsScreen var1) {
      return renderScreens$lambda$97(var0, var1);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$2Al2x9nq7lc7r3vpX1kdPFFUp48(GxRecordingsScreen var0, GxRecordingsScreen var1) {
      return deleteScreen$lambda$106$lambda$105$lambda$104(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$4XPJceAKu37k__SNpcGZc_3unVo/* $VF was: $r8$lambda$4XPJceAKu37k__SNpcGZc_3unVo*/(
      Call var0, GxRecordingsPage var1, Function1 var2, Function1 var3
   ) {
      stream$lambda$135(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static void $r8$lambda$4iL5ENlxFJYGf4cboHaaQ6GiIlU(Function1 var0, int var1) {
      head$lambda$130$lambda$129(var0, var1);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$5e0jCrvn5Aqhm6NQgPE1KtwtV4o(int var0, GxRecordingsPage var1) {
      return routeRow$lambda$53(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$5tOQtJ6TBJp7Zs99Q__pXRcgjns/* $VF was: $r8$lambda$5tOQtJ6TBJp7Zs99Q__pXRcgjns*/(Call var0, Function1 var1, JSONObject var2) {
      stream$lambda$135$lambda$133$flush$lambda$132(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$6b6_59qq968o2ypapgVMQJQ0DDs(GxRecordingsPage var0) {
      return buildScreens$lambda$89(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$6m2ESMWYtu672IulC2iqeiK2_q8(GxRecordingsPage var0, GxRecordingsRoute var1, List var2) {
      return openLogs$lambda$82$lambda$79(var0, var1, var2);
   }

   // $VF: synthetic method
   public static void $r8$lambda$7_8ypLPMjGEAD7FnB_I_SZrKC8s/* $VF was: $r8$lambda$7_8ypLPMjGEAD7FnB_I_SZrKC8s*/(
      GxRecordingsPage var0, GxRecordingsScreen var1, View var2
   ) {
      renderScreens$lambda$101(var0, var1, var2);
   }

   // $VF: synthetic method
   public static void $r8$lambda$8E33RzxN7smUTCSakw0ZsfHBiZc(GxRecordingsPage var0, GxRecordingsRoute var1, Function0 var2, DialogInterface var3, int var4) {
      renameRoute$lambda$72(var0, var1, var2, var3, var4);
   }

   // $VF: synthetic method
   public static int $r8$lambda$8TD9ufWS8ZwKsEtVOXeeYfLie54(Function2 var0, Object var1, Object var2) {
      return sorted$lambda$46(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$9Hng3IvBzxyXHxveJ7zKKoHAFzk(GxRecordingsPage var0, int var1) {
      return buildRoutes$lambda$10(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$9Y7tnOcDBgdIg_7aMM5agkcsV5o(GxRecordingsPage var0, ObjectRef var1, String var2) {
      return loadRoutes$lambda$16(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$CtXIbl22OOBHIKZcDfqJvlg29L4(
      GxRecordingsPage.Sheet var0, GxRecordingsPage var1, GxRecordingsRoute var2, long var3, JSONObject var5, int var6, int var7
   ) {
      return openLogs$lambda$82$lambda$79$lambda$78$lambda$77(var0, var1, var2, var3, var5, var6, var7);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$Dv_fjLaGCkn_8i547k9JXboc8hU(GxRecordingsPage var0, int var1, LinearLayout var2) {
      return build$lambda$1(var0, var1, var2);
   }

   // $VF: synthetic method
   public static void $r8$lambda$GQfaunGSPM5kK5eHGXmxVdYdij4(GxRecordingsPage var0, String var1, Function1 var2) {
      fetchThumb$lambda$126(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$JYjxMzt3f3v4Tu9QV7DKpSSye88(GxRecordingsPage var0, String var1, JSONObject var2) {
      return deleteAllRoutes$lambda$64$lambda$63(var0, var1, var2);
   }

   // $VF: synthetic method
   public static void $r8$lambda$Jksf2YOzqwPAxAyPHSmTF_s2Jx8(GxRecordingsPage var0, String var1, Function1 var2) {
      head$lambda$130(var0, var1, var2);
   }

   // $VF: synthetic method
   public static void $r8$lambda$JstYc5LyejBN6KrXUnIZnYR7QxQ(Call var0, Function1 var1, ObjectRef var2) {
      stream$lambda$135$lambda$134(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$L_g7S4SiKzqibis0sQlZb4UnznE(GxRecordingsPage var0, HashSet var1, JSONObject var2) {
      return loadScreens$lambda$91(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$MFJIG7LD2ZcD1Lp_JmLyWfJdDz0(GxRecordingsPage var0) {
      return deleteAllScreens$lambda$109(var0);
   }

   // $VF: synthetic method
   public static int $r8$lambda$MOEGdrxFKyS0CcV4PDNMefAbuQA(Function2 var0, Object var1, Object var2) {
      return sorted$lambda$44(var0, var1, var2);
   }

   // $VF: synthetic method
   public static int $r8$lambda$OSR_0OIjLmVYUgZ2OjNALswYs5Q/* $VF was: $r8$lambda$OSR_0OIjLmVYUgZ2OjNALswYs5Q*/(
      int var0, GxRecordingsRoute var1, GxRecordingsRoute var2
   ) {
      return sorted$lambda$43(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$OUh0tF5IF_Yu9bvHDT2vH4DGyMY/* $VF was: $r8$lambda$OUh0tF5IF_Yu9bvHDT2vH4DGyMY*/(GxRecordingsPage var0, GxRecordingsRoute var1) {
      return routeRow$lambda$56(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$OqKJcvNIdxOpFuZLWG6pzdOPCYI(String var0, GxRecordingsPage var1, GxRecordingsScreen var2, Function0 var3, String var4) {
      return renameScreen$lambda$103(var0, var1, var2, var3, var4);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$Pe6gl3QcFN_mRYV6IIroEQEjKh0(GxRecordingsPage var0, GxRecordingsRoute var1) {
      return routeRow$lambda$54(var0, var1);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$PyrhuFZce9oID0_VYQdMgpnwbZs/* $VF was: $r8$lambda$PyrhuFZce9oID0_VYQdMgpnwbZs*/(int var0, GxRecordingsPage var1) {
      return renderScreens$lambda$96(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$SQdaNghJmkKyeY1GrzgStR4_N7k/* $VF was: $r8$lambda$SQdaNghJmkKyeY1GrzgStR4_N7k*/(GxRecordingsPage var0, GxRecordingsScreen var1) {
      return renderScreens$lambda$100(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$SdsirzY7nwZ4LDnq4YXHqqhhPsE(GxRecordingsPage var0, HashSet var1, JSONObject var2) {
      return loadRoutes$lambda$15(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$Sxc0XQF58qDHAqQGn_xrjd0cJdc/* $VF was: $r8$lambda$Sxc0XQF58qDHAqQGn_xrjd0cJdc*/(GxRecordingsPage var0) {
      return buildRoutes$lambda$6(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$VT_CmzkBb1lJeiVLhlNltLCg2Yo/* $VF was: $r8$lambda$VT_CmzkBb1lJeiVLhlNltLCg2Yo*/(Function1 var0, Bitmap var1) {
      fetchThumb$lambda$126$lambda$125(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$XrQP9klPJd66i6BeSVvNcNAxpKc(GxRecordingsPage var0, int var1) {
      return buildRoutes$lambda$11(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$ZAHPfdOFmp2mU_ybheHVpg8gEok/* $VF was: $r8$lambda$ZAHPfdOFmp2mU_ybheHVpg8gEok*/(
      EditText var0, GxRecordingsRoute var1, GxRecordingsPage var2, Function0 var3, DialogInterface var4, int var5
   ) {
      renameRoute$lambda$69(var0, var1, var2, var3, var4, var5);
   }

   // $VF: synthetic method
   public static void $r8$lambda$ZKJECSfwf_rTRSLFWSatTjHhGQk/* $VF was: $r8$lambda$ZKJECSfwf_rTRSLFWSatTjHhGQk*/(
      Function2 var0, int var1, Function1 var2, View var3
   ) {
      chipBar$lambda$113$lambda$112(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$_IeQ8EYqZPNQ8MrpBDkYeI6ZMZU(GxRecordingsPage var0, GxRecordingsScreen var1, Function0 var2) {
      return deleteScreen$lambda$106(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$a0I0jB7ueJ71ESZtnBhZNntSH1k(GxRecordingsPage var0, GxRecordingsRoute var1) {
      return openLogs$lambda$82$lambda$73(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$al91w_ku0Sauvj0AK79wMra90_Q/* $VF was: $r8$lambda$al91w_ku0Sauvj0AK79wMra90_Q*/(
      GxRecordingsPage var0, GxRecordingsRoute var1, Function0 var2, String var3, JSONObject var4
   ) {
      return deleteRoute$lambda$61$lambda$60(var0, var1, var2, var3, var4);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$cX9Qc7IvJ3cjzjgf77hlqYpykbY(GxRecordingsPage var0) {
      return buildRoutes$lambda$12(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$cb0N9MaJL59XluRsVBDoWAZLY8k(GxRecordingsPage var0, String var1, JSONObject var2) {
      return deleteAllScreens$lambda$109$lambda$108(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$cbCUwHJDOXLA9PD9BPHYocUASDY(GxRecordingsPage var0, GxRecordingsRoute var1, Function0 var2, String var3, JSONObject var4) {
      return renameRoute$lambda$72$lambda$71(var0, var1, var2, var3, var4);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$d9M51dbuG6RyPiXwLwTvTWpLQsY(GxRecordingsPage var0, GxRecordingsScreen var1) {
      return renderScreens$lambda$99(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$fKEbHPJuJ5IwXSi4e1yE0SxYjg4(GxRecordingsPage var0) {
      return buildScreens$lambda$88(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$gT_4yWuObw5GVqpn62cKnFKKAnQ(GxRecordingsPage var0, GxRecordingsRoute var1, Function0 var2, String var3, JSONObject var4) {
      return togglePreserved$lambda$59(var0, var1, var2, var3, var4);
   }

   // $VF: synthetic method
   public static void $r8$lambda$gqRYp_IZSxUiLpEDDqPOMd1jKII/* $VF was: $r8$lambda$gqRYp_IZSxUiLpEDDqPOMd1jKII*/(GxRecordingsPage var0) {
      buildRoutes$lambda$8(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$iETb94LK6P8lPHlGXyFj3S_bSZg/* $VF was: $r8$lambda$iETb94LK6P8lPHlGXyFj3S_bSZg*/(
      long var0, List var2, GxRecordingsPage var3, GxRecordingsPage.Sheet var4, GxRecordingsRoute var5, long var6
   ) {
      return openLogs$lambda$82$lambda$79$lambda$78(var0, var2, var3, var4, var5, var6);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$iudkM3bvocJ2rPdjO2Raxbwlklg(ArrayList var0, GxRecordingsPage var1, int var2) {
      return chipBar$lambda$111(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$k2Gzpvarx7zurvnLbFr4xV_euD0(GxRecordingsPage var0, int var1) {
      return build$lambda$0(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$mE8_PCdGsv7MJbCmQzOMN69yhD4(GxRecordingsPage var0, ObjectRef var1, String var2) {
      return loadScreens$lambda$92(var0, var1, var2);
   }

   // $VF: synthetic method
   public static void $r8$lambda$mEWxP4LQVPEaG6y3dfF_K9aC6fU(GxRecordingsPage var0) {
      scheduleRender$lambda$17(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$muTJZD_uNA3FMFoNexdDcTzv1N8(GxRecordingsPage var0, GxRecordingsPage.ThumbJob var1, Bitmap var2) {
      return pumpThumbs$lambda$119(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$p5Lghz8yl9wHCFwCdQH8GopfXvc(GxRecordingsPage var0, Function0 var1, GxRecordingsScreen var2, String var3, JSONObject var4) {
      return deleteScreen$lambda$106$lambda$105(var0, var1, var2, var3, var4);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$phbHIaDF_2TA7b4XVGCVYlpc_TE(GxRecordingsPage var0) {
      return buildRoutes$lambda$13(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$qO3irIXn2YjM3GKoU_NTuVnIHxo(GxRecordingsPage var0, Function0 var1, String var2, JSONObject var3) {
      return renameScreen$lambda$103$lambda$102(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$qmfx2R0j4rAfwFezM73u0_TWCT0/* $VF was: $r8$lambda$qmfx2R0j4rAfwFezM73u0_TWCT0*/(
      GxRecordingsPage var0, GxRecordingsRoute var1, Function0 var2
   ) {
      return deleteRoute$lambda$61(var0, var1, var2);
   }

   // $VF: synthetic method
   public static int $r8$lambda$qrvZ_lp7_Zj6wfE6mPQqzwr91Zo/* $VF was: $r8$lambda$qrvZ_lp7_Zj6wfE6mPQqzwr91Zo*/(
      int var0, GxRecordingsRoute var1, GxRecordingsRoute var2
   ) {
      return sorted$lambda$45(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$rYpP0IgCMOOmDOZIh2gurN5fX0o(GxRecordingsPage var0, GxRecordingsScreen var1) {
      return renderScreens$lambda$98(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$t_me2YneH7ena1Jjy036xmlB4q0/* $VF was: $r8$lambda$t_me2YneH7ena1Jjy036xmlB4q0*/(GxRecordingsPage var0, boolean var1) {
      return deleteAllRoutes$lambda$64(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$vdZm7AexWycBvMXCrNi7gLyVUZ4(GxRecordingsPage var0, GxRecordingsRoute var1, View var2) {
      routeRow$lambda$58(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$yiag0wDRImYGF9qIehEll56yLSw(GxRecordingsPage var0, GxRecordingsRoute var1) {
      return routeRow$lambda$57(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$zfl2mrKYIhwzTNrHQnCxoEny3Qs(
      GxRecordingsPage var0, GxRecordingsRoute var1, String var2, Function0 var3, String var4, JSONObject var5
   ) {
      return renameRoute$lambda$69$lambda$68(var0, var1, var2, var3, var4, var5);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$zpL6PULhjv4bmkjr1JyIP6EGolA(GxRecordingsPage var0, GxRecordingsRoute var1) {
      return routeRow$lambda$55(var0, var1);
   }

   public GxRecordingsPage(Activity var1, GxHost var2) {
      Intrinsics.checkNotNullParameter(var1, "act");
      Intrinsics.checkNotNullParameter(var2, "gx");
      super(var1, var2);
      this.routes = new ArrayList<>();
      this.routesError = "";
      this.search = "";
      this.screens = new ArrayList<>();
      this.screenError = "";
      this.thumbCache = new LruCache<String, Bitmap>(25165824) {
         protected int sizeOf(String var1, Bitmap var2x) {
            Intrinsics.checkNotNullParameter(var1, "key");
            Intrinsics.checkNotNullParameter(var2x, "value");
            return var2x.getByteCount();
         }
      };
      this.thumbFailed = new HashSet<>();
      this.thumbQueue = new ArrayDeque<>();
   }

   private static final Unit build$lambda$0(GxRecordingsPage var0, int var1) {
      var0.tab = var1;
      if (var1 == 1 && !var0.screenLoaded && !var0.screenLoading) {
         var0.loadScreens();
      }

      return Unit.INSTANCE;
   }

   private static final Unit build$lambda$1(GxRecordingsPage var0, int var1, LinearLayout var2) {
      Intrinsics.checkNotNullParameter(var2, "b");
      if (var1 == 0) {
         var0.buildRoutes(var2);
      } else {
         var0.buildScreens(var2);
      }

      return Unit.INSTANCE;
   }

   private final void buildRoutes(LinearLayout var1) {
      LinearLayout var2 = this.card((ViewGroup)var1);
      LinearLayout var3 = new LinearLayout((Context)this.getAct());
      var3.setOrientation(0);
      var3.setGravity(16);
      LinearLayout var4 = new LinearLayout((Context)this.getAct());
      var4.setOrientation(1);
      TextView var5 = new TextView((Context)this.getAct());
      var5.setText("Dashcam Routes");
      var5.setTextColor(CarUi.Companion.getTEXT());
      var5.setTextSize(21.0F);
      var4.addView((View)var5);
      var5 = new TextView((Context)this.getAct());
      var5.setTextColor(CarUi.Companion.getDIM());
      var5.setTextSize(15.0F);
      var5.setPadding(0, this.getUi().dp(4), 0, 0);
      this.routesStats = var5;
      var4.addView((View)this.routesStats);
      var3.addView((View)var4, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(0, -2, 1.0F)));
      var3.addView((View)CarUi.button$default(this.getUi(), "Refresh", false, new GxRecordingsPage$$ExternalSyntheticLambda6(this), 2, null));
      var2.addView((View)var3);
      EditText var8 = new EditText((Context)this.getAct());
      var8.setHint("Search routes, dates, or IDs...");
      var8.setHintTextColor(CarUi.Companion.getDIM());
      var8.setTextColor(CarUi.Companion.getTEXT());
      var8.setTextSize(19.0F);
      var8.setSingleLine(true);
      var8.setInputType(1);
      var8.setBackground((Drawable)this.getUi().rounded(CarUi.Companion.getCARD_ON(), 12, Color.rgb(60, 66, 78)));
      var8.setPadding(this.getUi().dp(16), this.getUi().dp(12), this.getUi().dp(16), this.getUi().dp(12));
      var8.setMinHeight(this.getUi().dp(56));
      var8.addTextChangedListener(new TextWatcher(this, new GxRecordingsPage$$ExternalSyntheticLambda7(this)) {
         final Runnable $searchRun;
         final GxRecordingsPage this$0;

         {
            this.this$0 = var1;
            this.$searchRun = var2x;
         }

         public void afterTextChanged(Editable var1) {
            GxRecordingsPage var3x;
            label11: {
               var3x = this.this$0;
               if (var1 != null) {
                  String var2x = var1.toString();
                  var4 = var2x;
                  if (var2x != null) {
                     break label11;
                  }
               }

               var4 = "";
            }

            var3x.search = var4;
            this.this$0.getMain().removeCallbacks(this.$searchRun);
            this.this$0.getMain().postDelayed(this.$searchRun, 250L);
         }

         public void beforeTextChanged(CharSequence var1, int var2x, int var3x, int var4x) {
         }

         public void onTextChanged(CharSequence var1, int var2x, int var3x, int var4x) {
         }
      });
      View var9 = (View)var8;
      android.widget.LinearLayout.LayoutParams var10 = new android.widget.LinearLayout.LayoutParams(-1, -2);
      var10.topMargin = this.getUi().dp(12);
      Unit var12 = Unit.INSTANCE;
      var2.addView(var9, (LayoutParams)var10);
      this.choice(
         "Sort",
         null,
         CollectionsKt.listOf(new String[]{"Newest first", "Oldest first", "Longest duration", "Shortest duration"}),
         this.sortOrder,
         (ViewGroup)var1,
         new GxRecordingsPage$$ExternalSyntheticLambda8(this)
      );
      this.choice(
         "Show",
         null,
         CollectionsKt.listOf(new String[]{"All", "Preserved"}),
         this.preservedOnly,
         (ViewGroup)var1,
         new GxRecordingsPage$$ExternalSyntheticLambda9(this)
      );
      this.routesStatus = this.note("", (ViewGroup)var1);
      this.routesList = this.box((ViewGroup)var1);
      var1 = this.box((ViewGroup)var1);
      this.routesDeleteBox = var1;
      this.header("Delete local routes", (ViewGroup)var1);
      GxPage.Row var7 = GxPage.row$default(this, "Delete local routes", "This action cannot be undone.", (ViewGroup)var1, 0, 8, null);
      GxPage.Row.button$default(var7, "Delete Non-Preserved", false, new GxRecordingsPage$$ExternalSyntheticLambda10(this), 2, null);
      GxPage.Row.button$default(var7, "Delete All Including Preserved", false, new GxRecordingsPage$$ExternalSyntheticLambda12(this), 2, null)
         .setTextColor(GxRecordingsKt.access$getGxRecordingsDanger$p());
      this.renderRoutes();
   }

   private static final Unit buildRoutes$lambda$10(GxRecordingsPage var0, int var1) {
      var0.sortOrder = var1;
      var0.renderRoutes();
      return Unit.INSTANCE;
   }

   private static final Unit buildRoutes$lambda$11(GxRecordingsPage var0, int var1) {
      boolean var2 = true;
      if (var1 != 1) {
         var2 = false;
      }

      var0.preservedOnly = var2;
      var0.renderRoutes();
      return Unit.INSTANCE;
   }

   private static final Unit buildRoutes$lambda$12(GxRecordingsPage var0) {
      var0.deleteAllRoutes(false);
      return Unit.INSTANCE;
   }

   private static final Unit buildRoutes$lambda$13(GxRecordingsPage var0) {
      var0.deleteAllRoutes(true);
      return Unit.INSTANCE;
   }

   private static final Unit buildRoutes$lambda$6(GxRecordingsPage var0) {
      var0.loadRoutes();
      return Unit.INSTANCE;
   }

   private static final void buildRoutes$lambda$8(GxRecordingsPage var0) {
      var0.renderRoutes();
   }

   private final void buildScreens(LinearLayout var1) {
      LinearLayout var3 = this.card((ViewGroup)var1);
      LinearLayout var2 = new LinearLayout((Context)this.getAct());
      var2.setOrientation(0);
      var2.setGravity(16);
      LinearLayout var4 = new LinearLayout((Context)this.getAct());
      var4.setOrientation(1);
      TextView var5 = new TextView((Context)this.getAct());
      var5.setText("Screen Recordings");
      var5.setTextColor(CarUi.Companion.getTEXT());
      var5.setTextSize(21.0F);
      var4.addView((View)var5);
      var5 = new TextView((Context)this.getAct());
      var5.setTextColor(CarUi.Companion.getDIM());
      var5.setTextSize(15.0F);
      var5.setPadding(0, this.getUi().dp(4), 0, 0);
      this.screenCount = var5;
      var4.addView((View)this.screenCount);
      var2.addView((View)var4, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(0, -2, 1.0F)));
      var2.addView((View)CarUi.button$default(this.getUi(), "Refresh", false, new GxRecordingsPage$$ExternalSyntheticLambda49(this), 2, null));
      var3.addView((View)var2);
      this.screenList = this.box((ViewGroup)var1);
      var1 = this.box((ViewGroup)var1);
      this.screenDeleteBox = var1;
      this.header("Delete recordings", (ViewGroup)var1);
      GxPage.Row.button$default(
            GxPage.row$default(this, "Delete recordings", "This permanently deletes every screen recording.", (ViewGroup)var1, 0, 8, null),
            "Delete All Recordings",
            false,
            new GxRecordingsPage$$ExternalSyntheticLambda50(this),
            2,
            null
         )
         .setTextColor(GxRecordingsKt.access$getGxRecordingsDanger$p());
      this.renderScreens();
   }

   private static final Unit buildScreens$lambda$88(GxRecordingsPage var0) {
      var0.loadScreens();
      return Unit.INSTANCE;
   }

   private static final Unit buildScreens$lambda$89(GxRecordingsPage var0) {
      var0.deleteAllScreens();
      return Unit.INSTANCE;
   }

   private final Pair<LinearLayout, Function1<Integer, Unit>> chipBar(
      List<String> var1, Function2<? super Integer, ? super Function1<? super Integer, Unit>, Unit> var2
   ) {
      LinearLayout var6 = new LinearLayout((Context)this.getAct());
      int var3 = 0;
      var6.setOrientation(0);
      ArrayList var5 = new ArrayList();
      GxRecordingsPage$$ExternalSyntheticLambda31 var7 = new GxRecordingsPage$$ExternalSyntheticLambda31(var5, this);
      Iterator var11 = var1.iterator();

      while (var11.hasNext()) {
         int var4 = var3 + 1;
         String var9 = (String)var11.next();
         TextView var8 = new TextView((Context)this.getAct());
         var8.setText(var9);
         var8.setTextSize(17.0F);
         var8.setGravity(17);
         var8.setMinHeight(this.getUi().dp(56));
         var8.setMinWidth(this.getUi().dp(96));
         var8.setPadding(this.getUi().dp(16), this.getUi().dp(8), this.getUi().dp(16), this.getUi().dp(8));
         var8.setOnClickListener(new GxRecordingsPage$$ExternalSyntheticLambda32(var2, var3, var7));
         var5.add(var8);
         View var10 = (View)var8;
         android.widget.LinearLayout.LayoutParams var13 = new android.widget.LinearLayout.LayoutParams(-2, -2);
         var13.rightMargin = this.getUi().dp(8);
         Unit var12 = Unit.INSTANCE;
         var6.addView(var10, (LayoutParams)var13);
         var3 = var4;
      }

      return TuplesKt.to(var6, var7);
   }

   private static final Unit chipBar$lambda$111(ArrayList var0, GxRecordingsPage var1, int var2) {
      Iterator var8 = var0.iterator();
      int var3 = 0;

      while (var8.hasNext()) {
         boolean var13 = true;
         int var6 = var3 + 1;
         TextView var7 = (TextView)var8.next();
         if (var3 != var2) {
            var13 = false;
         }

         CarUi var10 = var1.getUi();
         CarUi.Companion var9 = CarUi.Companion;
         int var14;
         if (var13) {
            var14 = var9.getCARD_ON();
         } else {
            var14 = var9.getCARD();
         }

         int var5;
         if (var13) {
            var5 = CarUi.Companion.getACCENT();
         } else {
            var5 = Color.rgb(50, 55, 66);
         }

         var7.setBackground((Drawable)var10.rounded(var14, 12, var5));
         CarUi.Companion var11 = CarUi.Companion;
         if (var13) {
            var14 = var11.getACCENT();
         } else {
            var14 = var11.getTEXT();
         }

         var7.setTextColor(var14);
         Typeface var12;
         if (var13) {
            var12 = Typeface.DEFAULT_BOLD;
         } else {
            var12 = Typeface.DEFAULT;
         }

         var7.setTypeface(var12);
         var3 = var6;
      }

      return Unit.INSTANCE;
   }

   private static final void chipBar$lambda$113$lambda$112(Function2 var0, int var1, Function1 var2, View var3) {
      var0.invoke(var1, var2);
   }

   private final void deleteAllRoutes(boolean var1) {
      String var2;
      if (var1) {
         var2 = "Delete all routes, including preserved?";
      } else {
         var2 = "Delete all non-preserved routes?";
      }

      String var3;
      if (var1) {
         var3 = "Delete Everything";
      } else {
         var3 = "Delete Non-Preserved";
      }

      this.confirm(var2, "This action cannot be undone.", var3, new GxRecordingsPage$$ExternalSyntheticLambda60(this, var1));
   }

   private static final Unit deleteAllRoutes$lambda$64(GxRecordingsPage var0, boolean var1) {
      var0.toast("Deleting routes…");
      GxPage.send$default(
         var0, "DELETE", "/api/routes/delete_all?include_preserved=" + var1, null, 180L, new GxRecordingsPage$$ExternalSyntheticLambda59(var0), 4, null
      );
      return Unit.INSTANCE;
   }

   private static final Unit deleteAllRoutes$lambda$64$lambda$63(GxRecordingsPage var0, String var1, JSONObject var2) {
      if (var1 != null) {
         if (Intrinsics.areEqual(var1, "bad reply")) {
            var1 = "Failed to delete routes.";
         }

         var0.toast(var1);
      } else {
         label25: {
            if (var2 != null) {
               var1 = GxPage.str$default(var0, var2, "message", null, 2, null);
               if (var1 != null) {
                  CharSequence var6 = var1;
                  CharSequence var4 = var6;
                  if (StringsKt.isBlank(var6)) {
                     var4 = null;
                  }

                  var1 = (String)var4;
                  if (var1 != null) {
                     break label25;
                  }
               }
            }

            var1 = "Routes deleted!";
         }

         var0.toast(var1);
         var0.loadRoutes();
      }

      return Unit.INSTANCE;
   }

   private final void deleteAllScreens() {
      this.confirm(
         "Delete all recordings?", "This permanently deletes every screen recording.", "Delete All", new GxRecordingsPage$$ExternalSyntheticLambda22(this)
      );
   }

   private static final Unit deleteAllScreens$lambda$109(GxRecordingsPage var0) {
      GxPage.send$default(var0, "DELETE", "/api/screen_recordings/delete_all", null, 120L, new GxRecordingsPage$$ExternalSyntheticLambda28(var0), 4, null);
      return Unit.INSTANCE;
   }

   private static final Unit deleteAllScreens$lambda$109$lambda$108(GxRecordingsPage var0, String var1, JSONObject var2) {
      if (var1 != null) {
         if (Intrinsics.areEqual(var1, "bad reply")) {
            var1 = "Delete failed.";
         }

         var0.toast(var1);
      } else {
         label25: {
            var0.screens.clear();
            var0.renderScreens();
            if (var2 != null) {
               var1 = GxPage.str$default(var0, var2, "message", null, 2, null);
               if (var1 != null) {
                  CharSequence var6 = var1;
                  CharSequence var4 = var6;
                  if (StringsKt.isBlank(var6)) {
                     var4 = null;
                  }

                  var1 = (String)var4;
                  if (var1 != null) {
                     break label25;
                  }
               }
            }

            var1 = "All screen recordings deleted!";
         }

         var0.toast(var1);
      }

      return Unit.INSTANCE;
   }

   private final void deleteRoute(GxRecordingsRoute var1, Function0<Unit> var2) {
      String var3 = var1.getDisplayName();
      this.confirm("Delete route?", "Delete “" + var3 + "”?", "Delete", new GxRecordingsPage$$ExternalSyntheticLambda20(this, var1, var2));
   }

   // $VF: synthetic method
   static void deleteRoute$default(GxRecordingsPage var0, GxRecordingsRoute var1, Function0 var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = null;
      }

      var0.deleteRoute(var1, var2);
   }

   private static final Unit deleteRoute$lambda$61(GxRecordingsPage var0, GxRecordingsRoute var1, Function0 var2) {
      GxPage var4 = var0;
      String var3 = var0.enc(var1.getName());
      GxPage.send$default(var4, "DELETE", "/api/routes/" + var3, null, 0L, new GxRecordingsPage$$ExternalSyntheticLambda18(var0, var1, var2), 12, null);
      return Unit.INSTANCE;
   }

   private static final Unit deleteRoute$lambda$61$lambda$60(GxRecordingsPage var0, GxRecordingsRoute var1, Function0 var2, String var3, JSONObject var4) {
      if (var3 != null) {
         var0.toast("Delete failed.");
      } else {
         var0.routes.remove(var1);
         var0.renderRoutes();
         var0.toast("Route deleted!");
         if (var2 != null) {
            var2.invoke();
         }
      }

      return Unit.INSTANCE;
   }

   private final void deleteScreen(GxRecordingsScreen var1, Function0<Unit> var2) {
      String var3 = var1.getFilename();
      this.confirm("Delete recording?", "Delete “" + var3 + "”?", "Delete", new GxRecordingsPage$$ExternalSyntheticLambda61(this, var1, var2));
   }

   // $VF: synthetic method
   static void deleteScreen$default(GxRecordingsPage var0, GxRecordingsScreen var1, Function0 var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = null;
      }

      var0.deleteScreen(var1, var2);
   }

   private static final Unit deleteScreen$lambda$106(GxRecordingsPage var0, GxRecordingsScreen var1, Function0 var2) {
      GxPage var3 = var0;
      String var4 = var0.enc(var1.getFilename());
      GxPage.send$default(
         var3, "DELETE", "/api/screen_recordings/delete/" + var4, null, 0L, new GxRecordingsPage$$ExternalSyntheticLambda26(var0, var2, var1), 12, null
      );
      return Unit.INSTANCE;
   }

   private static final Unit deleteScreen$lambda$106$lambda$105(GxRecordingsPage var0, Function0 var1, GxRecordingsScreen var2, String var3, JSONObject var4) {
      if (var3 != null) {
         var0.toast("Delete failed.");
      } else {
         CollectionsKt.removeAll(var0.screens, new GxRecordingsPage$$ExternalSyntheticLambda13(var2));
         var0.renderScreens();
         var0.toast("Recording deleted!");
         if (var1 != null) {
            var1.invoke();
         }
      }

      return Unit.INSTANCE;
   }

   private static final boolean deleteScreen$lambda$106$lambda$105$lambda$104(GxRecordingsScreen var0, GxRecordingsScreen var1) {
      Intrinsics.checkNotNullParameter(var1, "it");
      return Intrinsics.areEqual(var1.getFilename(), var0.getFilename());
   }

   private final void downloadScreen(GxRecordingsScreen var1) {
      this.download(this.screenVideoPath(var1), var1.getFilename(), "video/mp4");
   }

   private final void fetchThumb(String var1, Function1<? super Bitmap, Unit> var2) {
      new Thread(new GxRecordingsPage$$ExternalSyntheticLambda47(this, this.url(var1), var2)).start();
   }

   private static final void fetchThumb$lambda$126(GxRecordingsPage param0, String param1, Function1 param2) {
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
      // 001: astore 5
      // 003: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 006: astore 6
      // 008: getstatic com/starpilot/starview/GxPage.Companion Lcom/starpilot/starview/GxPage$Companion;
      // 00b: ldc2_w 30
      // 00e: invokevirtual com/starpilot/starview/GxPage$Companion.client (J)Lokhttp3/OkHttpClient;
      // 011: astore 6
      // 013: new okhttp3/Request$Builder
      // 016: astore 7
      // 018: aload 7
      // 01a: invokespecial okhttp3/Request$Builder.<init> ()V
      // 01d: aload 6
      // 01f: aload 7
      // 021: aload 1
      // 022: invokevirtual okhttp3/Request$Builder.url (Ljava/lang/String;)Lokhttp3/Request$Builder;
      // 025: invokevirtual okhttp3/Request$Builder.build ()Lokhttp3/Request;
      // 028: invokevirtual okhttp3/OkHttpClient.newCall (Lokhttp3/Request;)Lokhttp3/Call;
      // 02b: invokeinterface okhttp3/Call.execute ()Lokhttp3/Response; 1
      // 030: checkcast java/io/Closeable
      // 033: astore 6
      // 035: aload 6
      // 037: checkcast okhttp3/Response
      // 03a: astore 1
      // 03b: aload 1
      // 03c: invokevirtual okhttp3/Response.isSuccessful ()Z
      // 03f: ifne 047
      // 042: aconst_null
      // 043: astore 1
      // 044: goto 0ae
      // 047: aload 1
      // 048: invokevirtual okhttp3/Response.body ()Lokhttp3/ResponseBody;
      // 04b: astore 1
      // 04c: aload 1
      // 04d: ifnull 042
      // 050: aload 1
      // 051: invokevirtual okhttp3/ResponseBody.bytes ()[B
      // 054: astore 1
      // 055: aload 1
      // 056: ifnull 042
      // 059: new android/graphics/BitmapFactory$Options
      // 05c: astore 7
      // 05e: aload 7
      // 060: invokespecial android/graphics/BitmapFactory$Options.<init> ()V
      // 063: aload 7
      // 065: bipush 1
      // 066: putfield android/graphics/BitmapFactory$Options.inJustDecodeBounds Z
      // 069: aload 1
      // 06a: bipush 0
      // 06b: aload 1
      // 06c: arraylength
      // 06d: aload 7
      // 06f: invokestatic android/graphics/BitmapFactory.decodeByteArray ([BIILandroid/graphics/BitmapFactory$Options;)Landroid/graphics/Bitmap;
      // 072: pop
      // 073: bipush 1
      // 074: istore 3
      // 075: aload 7
      // 077: getfield android/graphics/BitmapFactory$Options.outWidth I
      // 07a: iload 3
      // 07b: bipush 2
      // 07c: imul
      // 07d: idiv
      // 07e: sipush 400
      // 081: if_icmplt 08b
      // 084: iload 3
      // 085: bipush 2
      // 086: imul
      // 087: istore 3
      // 088: goto 075
      // 08b: aload 1
      // 08c: arraylength
      // 08d: istore 4
      // 08f: new android/graphics/BitmapFactory$Options
      // 092: astore 7
      // 094: aload 7
      // 096: invokespecial android/graphics/BitmapFactory$Options.<init> ()V
      // 099: aload 7
      // 09b: iload 3
      // 09c: putfield android/graphics/BitmapFactory$Options.inSampleSize I
      // 09f: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 0a2: astore 8
      // 0a4: aload 1
      // 0a5: bipush 0
      // 0a6: iload 4
      // 0a8: aload 7
      // 0aa: invokestatic android/graphics/BitmapFactory.decodeByteArray ([BIILandroid/graphics/BitmapFactory$Options;)Landroid/graphics/Bitmap;
      // 0ad: astore 1
      // 0ae: aload 6
      // 0b0: aconst_null
      // 0b1: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 0b4: aload 1
      // 0b5: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 0b8: astore 1
      // 0b9: goto 0dd
      // 0bc: astore 7
      // 0be: aload 7
      // 0c0: athrow
      // 0c1: astore 1
      // 0c2: aload 6
      // 0c4: aload 7
      // 0c6: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 0c9: aload 1
      // 0ca: athrow
      // 0cb: astore 1
      // 0cc: goto 0d0
      // 0cf: astore 1
      // 0d0: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 0d3: astore 6
      // 0d5: aload 1
      // 0d6: invokestatic kotlin/ResultKt.createFailure (Ljava/lang/Throwable;)Ljava/lang/Object;
      // 0d9: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 0dc: astore 1
      // 0dd: aload 1
      // 0de: invokestatic kotlin/Result.isFailure-impl (Ljava/lang/Object;)Z
      // 0e1: ifeq 0ea
      // 0e4: aload 5
      // 0e6: astore 1
      // 0e7: goto 0ea
      // 0ea: aload 1
      // 0eb: checkcast android/graphics/Bitmap
      // 0ee: astore 1
      // 0ef: aload 0
      // 0f0: invokevirtual com/starpilot/starview/GxRecordingsPage.getMain ()Landroid/os/Handler;
      // 0f3: new com/starpilot/starview/GxRecordingsPage$$ExternalSyntheticLambda43
      // 0f6: dup
      // 0f7: aload 2
      // 0f8: aload 1
      // 0f9: invokespecial com/starpilot/starview/GxRecordingsPage$$ExternalSyntheticLambda43.<init> (Lkotlin/jvm/functions/Function1;Landroid/graphics/Bitmap;)V
      // 0fc: invokevirtual android/os/Handler.post (Ljava/lang/Runnable;)Z
      // 0ff: pop
      // 100: return
   }

   private static final void fetchThumb$lambda$126$lambda$125(Function1 var0, Bitmap var1) {
      var0.invoke(var1);
   }

   private final void head(String var1, Function1<? super Integer, Unit> var2) {
      new Thread(new GxRecordingsPage$$ExternalSyntheticLambda21(this, var1, var2)).start();
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private static final void head$lambda$130(GxRecordingsPage var0, String var1, Function1 var2) {
      label134: {
         Response var44;
         try {
            Result.Companion var4 = Result.Companion;
            OkHttpClient var5 = GxPage.Companion.client(30L);
            Builder var41 = new Builder();
            var44 = var5.newCall(var41.url(var1).head().build()).execute();
         } catch (Throwable var35) {
            Result.Companion var36 = Result.Companion;
            var37 = KtCompat.resultOf(ResultKt.createFailure(var35));
            break label134;
         }

         int var3;
         try {
            var3 = var44.code();
         } catch (Throwable var34) {
            Throwable var42 = var34;

            try {
               throw var42;
            } finally {
               try {
                  CloseableKt.closeFinally(var44, var42);
               } catch (Throwable var32) {
                  Result.Companion var38 = Result.Companion;
                  var37 = KtCompat.resultOf(ResultKt.createFailure(var32));
                  break label134;
               }
            }
         }

         label122:
         try {
            CloseableKt.closeFinally(var44, null);
            var37 = KtCompat.resultOf(var3);
         } catch (Throwable var31) {
            Result.Companion var39 = Result.Companion;
            var37 = KtCompat.resultOf(ResultKt.createFailure(var31));
            break label122;
         }
      }

      Object var43 = var37;
      if (KtCompat.isFailure(var37)) {
         var43 = -1;
      }

      int var40 = ((Number)var43).intValue();
      var0.getMain().post(new GxRecordingsPage$$ExternalSyntheticLambda11(var2, var40));
   }

   private static final void head$lambda$130$lambda$129(Function1 var0, int var1) {
      var0.invoke(var1);
   }

   private final void loadRoutes() {
      if (this.routesList != null && this.getHost() != null) {
         Call var1 = this.routeCall;
         if (var1 != null) {
            var1.cancel();
         }

         this.routes.clear();
         this.routesLoading = true;
         this.routesError = "";
         this.routesProgress = 0;
         this.routesTotal = 0;
         this.renderRoutes();
         HashSet var2 = new HashSet();
         ObjectRef var3 = new ObjectRef();
         var3.element = (T)this.stream(
            "/api/routes", new GxRecordingsPage$$ExternalSyntheticLambda51(this, var2), new GxRecordingsPage$$ExternalSyntheticLambda52(this, var3)
         );
         this.routeCall = (Call)var3.element;
      }
   }

   private static final Unit loadRoutes$lambda$15(GxRecordingsPage var0, HashSet var1, JSONObject var2) {
      Intrinsics.checkNotNullParameter(var2, "o");
      if (var2.has("progress")) {
         var0.routesProgress = var2.optInt("progress");
      }

      if (var2.has("total")) {
         var0.routesTotal = var2.optInt("total");
      }

      JSONArray var5 = var2.optJSONArray("routes");
      if (var5 != null) {
         List var6 = var0.objects(var5);
         if (var6 != null) {
            for (JSONObject var4 : var6) {
               String var7 = GxPage.str$default(var0, var4, "name", null, 2, null);
               if (!StringsKt.isBlank(var7) && var1.add(var7)) {
                  var0.routes.add(var0.parseRoute(var4));
               }
            }
         }
      }

      var0.scheduleRender();
      return Unit.INSTANCE;
   }

   private static final Unit loadRoutes$lambda$16(GxRecordingsPage var0, ObjectRef var1, String var2) {
      if (var0.routeCall != var1.element) {
         return Unit.INSTANCE;
      } else {
         var0.routeCall = null;
         var0.routesLoading = false;
         var0.routesLoaded = true;
         if (var2 != null) {
            var0.routesError = "Couldn't load routes. Try refreshing.";
         }

         var0.renderRoutes();
         return Unit.INSTANCE;
      }
   }

   private final void loadScreens() {
      if (this.screenList != null && this.getHost() != null) {
         Call var1 = this.screenCall;
         if (var1 != null) {
            var1.cancel();
         }

         this.screens.clear();
         this.screenLoading = true;
         this.screenError = "";
         this.renderScreens();
         HashSet var3 = new HashSet();
         ObjectRef var2 = new ObjectRef();
         var2.element = (T)this.stream(
            "/api/screen_recordings/list",
            new GxRecordingsPage$$ExternalSyntheticLambda24(this, var3),
            new GxRecordingsPage$$ExternalSyntheticLambda25(this, var2)
         );
         this.screenCall = (Call)var2.element;
      }
   }

   private static final Unit loadScreens$lambda$91(GxRecordingsPage var0, HashSet var1, JSONObject var2) {
      label32: {
         Intrinsics.checkNotNullParameter(var2, "o");
         JSONArray var7 = var2.optJSONArray("recordings");
         if (var7 != null) {
            List var4 = var0.objects(var7);
            var8 = var4;
            if (var4 != null) {
               break label32;
            }
         }

         var8 = CollectionsKt.emptyList();
      }

      boolean var3 = false;

      for (JSONObject var10 : var8) {
         String var9 = GxPage.str$default(var0, var10, "filename", null, 2, null);
         if (!StringsKt.isBlank(var9) && var1.add(var9)) {
            var0.screens
               .add(
                  new GxRecordingsScreen(
                     var9,
                     GxPage.str$default(var0, var10, "png", null, 2, null),
                     GxPage.str$default(var0, var10, "timestamp", null, 2, null),
                     var10.optBoolean("is_custom_name")
                  )
               );
            var3 = true;
         }
      }

      if (var3) {
         List var6 = var0.screens;
         if (var6.size() > 1) {
            CollectionsKt.sortWith(var6, new Comparator() {
               @Override
               public final int compare(T var1, T var2x) {
                  return ComparisonsKt.compareValues(((GxRecordingsScreen)var2x).getTimestamp(), ((GxRecordingsScreen)var1).getTimestamp());
               }
            });
         }

         var0.renderScreens();
      }

      return Unit.INSTANCE;
   }

   private static final Unit loadScreens$lambda$92(GxRecordingsPage var0, ObjectRef var1, String var2) {
      if (var0.screenCall != var1.element) {
         return Unit.INSTANCE;
      } else {
         var0.screenCall = null;
         var0.screenLoading = false;
         var0.screenLoaded = true;
         if (var2 != null) {
            var0.screenError = "Couldn't load recordings.";
         }

         var0.renderScreens();
         return Unit.INSTANCE;
      }
   }

   private final boolean matches(GxRecordingsRoute var1, String var2) {
      String var10 = StringsKt.trim(var2).toString();
      var2 = GxRecordingsKt.access$gxRecordingsNorm(var2);
      CharSequence var9 = var2;
      boolean var8 = false;
      Iterable var11 = StringsKt.split$default(var9, new String[]{" "}, false, 0, 6, null);
      var var33 = new ArrayList();

      for (Object var12 : var11) {
         boolean var3;
         if (((String)var12).length() > 0) {
            var3 = true;
         } else {
            var3 = false;
         }

         if (var3) {
            var33.add(var12);
         }
      }

      List var34 = var33;
      if (var34.isEmpty()) {
         return true;
      } else {
         var11 = var34;
         boolean var19;
         if (var11 instanceof Collection && ((Collection)var11).isEmpty()) {
            var19 = 0;
         } else {
            Iterator var45 = var11.iterator();

            while (true) {
               if (!var45.hasNext()) {
                  var19 = 0;
                  break;
               }

               label268: {
                  String var40 = (String)var45.next();
                  if (var40.length() >= 3) {
                     var var13 = GxRecordingsKt.access$getGxRecordingsMonths$p();
                     if (var13 instanceof Collection && ((Collection)var13).isEmpty()) {
                        var19 = (boolean)0;
                     } else {
                        Iterator var53 = var13.iterator();

                        while (true) {
                           if (!var53.hasNext()) {
                              var19 = (boolean)0;
                              break;
                           }

                           if (StringsKt.startsWith$default((String)var53.next(), var40, false, 2, null)) {
                              var19 = (boolean)1;
                              break;
                           }
                        }
                     }

                     if (var19) {
                        var19 = (boolean)1;
                        break label268;
                     }
                  }

                  var19 = (boolean)0;
               }

               if (var19) {
                  var19 = 1;
                  break;
               }
            }
         }

         boolean var4;
         if (!var19
            && !new Regex("\\d\\s*[/-]\\s*\\d").containsMatchIn(var10)
            && !new Regex("\\d+(?:st|nd|rd|th)\\b", RegexOption.IGNORE_CASE).containsMatchIn(var10)
            && !new Regex("^\\d{4}$").matches(var2)) {
            var4 = false;
         } else {
            var4 = true;
         }

         boolean var5;
         label243: {
            if (!new Regex("^\\d{1,2}$").matches(var2) && !new Regex("\\d\\s*:\\s*\\d").containsMatchIn(var10)) {
               var11 = var34;
               if (var11 instanceof Collection && ((Collection)var11).isEmpty()) {
                  var19 = (boolean)0;
               } else {
                  Iterator var46 = var11.iterator();

                  while (true) {
                     if (!var46.hasNext()) {
                        var19 = (boolean)0;
                        break;
                     }

                     String var42 = (String)var46.next();
                     if (!Intrinsics.areEqual(var42, "am") && !Intrinsics.areEqual(var42, "pm")) {
                        var19 = (boolean)0;
                     } else {
                        var19 = (boolean)1;
                     }

                     if (var19) {
                        var19 = (boolean)1;
                        break;
                     }
                  }
               }

               if (!var19) {
                  var5 = false;
                  break label243;
               }
            }

            var5 = true;
         }

         char var31;
         label221: {
            label220: {
               String var43 = StringsKt.replace$default(var2, " ", "", false, 4, null);
               boolean var7 = new Regex("^[0-9a-f]+$").matches(var43);
               if (!StringsKt.contains$default(var10, "--", false, 2, null)) {
                  if (!var7) {
                     break label220;
                  }

                  if (var43.length() < 8) {
                     if (var43.length() < 4) {
                        break label220;
                     }

                     CharSequence var35 = var43;
                     var19 = 0;

                     while (true) {
                        if (var19 >= var35.length()) {
                           var23 = false;
                           break;
                        }

                        if (Character.isDigit(var35.charAt(var19))) {
                           var23 = true;
                           break;
                        }

                        var19++;
                     }

                     if (!var23) {
                        break label220;
                     }

                     CharSequence var36 = var43;
                     var19 = 0;

                     while (true) {
                        if (var19 >= var36.length()) {
                           var25 = false;
                           break;
                        }

                        var31 = var36.charAt(var19);
                        boolean var30;
                        if ('a' <= var31 && var31 < 'g') {
                           var30 = true;
                        } else {
                           var30 = false;
                        }

                        if (var30) {
                           var25 = true;
                           break;
                        }

                        var19++;
                     }

                     if (!var25) {
                        break label220;
                     }
                  }
               }

               var31 = '\u0001';
               break label221;
            }

            var31 = '\u0000';
         }

         Regex var37 = new Regex("^\\d{1,2}$");
         Regex var44 = new Regex("^\\d{4}$");
         var var47 = var1.getTitles();
         boolean var26;
         if (var47 instanceof Collection && ((Collection)var47).isEmpty()) {
            var26 = false;
         } else {
            Iterator var48 = var47.iterator();

            while (true) {
               if (!var48.hasNext()) {
                  var26 = false;
                  break;
               }

               if (matches$m$default(var2, var34, var37, var44, (String)var48.next(), false, 32, null)) {
                  var26 = true;
                  break;
               }
            }
         }

         if (!var26) {
            if (var4) {
               var47 = var1.getDates();
               if (var47 instanceof Collection && ((Collection)var47).isEmpty()) {
                  var26 = false;
               } else {
                  Iterator var50 = var47.iterator();

                  while (true) {
                     if (!var50.hasNext()) {
                        var26 = false;
                        break;
                     }

                     if (matches$m(var2, var34, var37, var44, (String)var50.next(), true)) {
                        var26 = true;
                        break;
                     }
                  }
               }

               if (var26) {
                  return true;
               }
            }

            if (var5) {
               var47 = var1.getTimes();
               if (var47 instanceof Collection && ((Collection)var47).isEmpty()) {
                  var26 = false;
               } else {
                  Iterator var52 = var47.iterator();

                  while (true) {
                     if (!var52.hasNext()) {
                        var26 = false;
                        break;
                     }

                     if (matches$m$default(var2, var34, var37, var44, (String)var52.next(), false, 32, null)) {
                        var26 = true;
                        break;
                     }
                  }
               }

               if (var26) {
                  return true;
               }
            }

            if (!var31) {
               return var8;
            }

            var var14 = var1.getIds();
            if (var14 instanceof Collection && ((Collection)var14).isEmpty()) {
               var26 = false;
            } else {
               Iterator var15 = var14.iterator();

               while (true) {
                  if (!var15.hasNext()) {
                     var26 = false;
                     break;
                  }

                  if (matches$m$default(var2, var34, var37, var44, (String)var15.next(), false, 32, null)) {
                     var26 = true;
                     break;
                  }
               }
            }

            if (!var26) {
               return var8;
            }
         }

         return true;
      }
   }

   private static final boolean matches$m(String var0, List<String> var1, Regex var2, Regex var3, String var4, boolean var5) {
      CharSequence var11 = var4;
      CharSequence var13 = var0;
      boolean var8 = false;
      byte var7 = 2;
      boolean var10 = StringsKt.contains$default(var11, var13, false, 2, null);
      boolean var9 = true;
      if (var10) {
         return true;
      } else {
         Iterable var18 = StringsKt.split$default(var4, new String[]{" "}, false, 0, 6, null);
         var var14 = new ArrayList();

         for (Object var19 : var18) {
            boolean var6;
            if (((String)var19).length() > 0) {
               var6 = true;
            } else {
               var6 = false;
            }

            if (var6) {
               var14.add(var19);
            }
         }

         List var15 = var14;
         var var16 = var1;
         if (var16 instanceof Collection && ((Collection)var16).isEmpty()) {
            var8 = var9;
         } else {
            Iterator var17 = var16.iterator();

            label64:
            do {
               if (!var17.hasNext()) {
                  var8 = true;
                  break;
               }

               var4 = (String)var17.next();
               var var28 = var15;
               if (var28 instanceof Collection && ((Collection)var28).isEmpty()) {
                  var9 = var8;
               } else {
                  for (String var12 : var28) {
                     if (var5 && var2.matches(var4) && var3.matches(var12)) {
                        var8 = false;
                     } else {
                        var8 = StringsKt.startsWith$default(var12, var4, false, 2, null);
                     }

                     var9 = false;
                     byte var21 = 2;
                     if (var8) {
                        var10 = true;
                        var8 = var9;
                        var9 = var10;
                        continue label64;
                     }

                     var21 = 2;
                     var8 = false;
                  }

                  var9 = var8;
               }
            } while (var9);
         }

         return var8;
      }
   }

   // $VF: synthetic method
   static boolean matches$m$default(String var0, List var1, Regex var2, Regex var3, String var4, boolean var5, int var6, Object var7) {
      if ((var6 & 32) != 0) {
         var5 = false;
      }

      return matches$m(var0, var1, var2, var3, var4, var5);
   }

   private final void normalize(GxRecordingsRoute var1) {
      ZonedDateTime var6 = this.parseStarted(var1.getTimestamp());
      ZonedDateTime var4 = this.parseStarted(var1.getStartedAt());
      ZonedDateTime var5 = var4;
      if (var4 == null) {
         var5 = var6;
      }

      label141: {
         if (var5 != null) {
            String var7 = var5.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));
            String var14 = var5.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT));
            var7 = var7 + " at " + var14;
            var15 = var7;
            if (var7 != null) {
               break label141;
            }
         }

         var15 = "Unknown date";
      }

      var1.setDisplayDate(var15);
      boolean var3;
      if (var1.isCustomName() || var1.getTimestamp() != null && var6 == null) {
         var3 = true;
      } else {
         var3 = false;
      }

      var1.setCustomName(var3);
      var3 = var1.isCustomName();
      String var39 = "";
      String var16;
      if (var3) {
         String var50 = var1.getTimestamp();
         var16 = var50;
         if (var50 == null) {
            var16 = "";
         }
      } else {
         var16 = var1.getDisplayDate();
      }

      label126: {
         var1.setDisplayName(var16);
         if (var5 != null) {
            Instant var17 = var5.toInstant();
            if (var17 != null) {
               var18 = var17.toEpochMilli();
               break label126;
            }
         }

         var18 = null;
      }

      var1.setStartedAtMs(var18);
      Iterable var51 = CollectionsKt.listOf(GxRecordingsKt.access$gxRecordingsNorm(var1.getName()));
      var var19 = new ArrayList();

      for (Object var8 : var51) {
         boolean var2;
         if (((String)var8).length() > 0) {
            var2 = true;
         } else {
            var2 = false;
         }

         if (var2) {
            var19.add(var8);
         }
      }

      List var20 = var19;
      var1.setIds(var20);
      String var21 = var39;
      if (var1.isCustomName()) {
         var21 = var1.getDisplayName();
      }

      Iterable var40 = CollectionsKt.listOf(GxRecordingsKt.access$gxRecordingsNorm(var21));
      var var22 = new ArrayList();

      for (Object var53 : var40) {
         boolean var10;
         if (((String)var53).length() > 0) {
            var10 = true;
         } else {
            var10 = false;
         }

         if (var10) {
            var22.add(var53);
         }
      }

      List var23 = var22;
      var1.setTitles(var23);
      List var24;
      if (var5 == null) {
         var24 = CollectionsKt.emptyList();
      } else {
         String var9 = var5.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));
         String var54 = var5.format(DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.US));
         String var56 = var5.format(DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.US));
         String var25 = var5.format(DateTimeFormatter.ofPattern("M/d/yyyy", Locale.US));
         String var42 = var5.format(DateTimeFormatter.ofPattern("yyyy-M-d", Locale.US));
         Iterable var43 = CollectionsKt.listOf(new String[]{var9, var54, var56, var25, var42});
         var var26 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var43, 10));
         Iterator var44 = var43.iterator();

         while (var44.hasNext()) {
            var26.add(GxRecordingsKt.access$gxRecordingsNorm((String)var44.next()));
         }

         List var27 = var26;
         Iterable var45 = var27;
         var var28 = new ArrayList();

         for (Object var46 : var45) {
            boolean var11;
            if (((String)var46).length() > 0) {
               var11 = true;
            } else {
               var11 = false;
            }

            if (var11) {
               var28.add(var46);
            }
         }

         var24 = var28;
      }

      var1.setDates(var24);
      List var29;
      if (var5 == null) {
         var29 = CollectionsKt.emptyList();
      } else {
         String var30 = var5.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT));
         String var47 = var5.format(DateTimeFormatter.ofPattern("h:mm a", Locale.US));
         String var34 = var5.format(DateTimeFormatter.ofPattern("HH:mm", Locale.US));
         Iterable var35 = CollectionsKt.listOf(new String[]{var30, var47, var34});
         var var31 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var35, 10));
         Iterator var36 = var35.iterator();

         while (var36.hasNext()) {
            var31.add(GxRecordingsKt.access$gxRecordingsNorm((String)var36.next()));
         }

         List var32 = var31;
         Iterable var37 = var32;
         var var33 = new ArrayList();

         for (Object var38 : var37) {
            boolean var12;
            if (((String)var38).length() > 0) {
               var12 = true;
            } else {
               var12 = false;
            }

            if (var12) {
               var33.add(var38);
            }
         }

         var29 = var33;
      }

      var1.setTimes(var29);
   }

   private final void openLogs(GxRecordingsRoute var1) {
      String var2 = this.enc(var1.getName());
      this.getJson("/api/routes/" + var2 + "/logs", new GxRecordingsPage$$ExternalSyntheticLambda48(this, var1));
   }

   private static final Unit openLogs$lambda$82(GxRecordingsPage var0, GxRecordingsRoute var1, String var2, JSONObject var3) {
      if (var2 == null && var3 != null) {
         label37: {
            JSONArray var10 = var3.optJSONArray("segments");
            if (var10 != null) {
               List var5 = var0.objects(var10);
               var11 = var5;
               if (var5 != null) {
                  break label37;
               }
            }

            var11 = CollectionsKt.emptyList();
         }

         String var21 = var1.getDisplayName();
         GxRecordingsPage.Sheet var6 = var0.new Sheet(var0, (boolean)(var21 + " Logs"), true);
         GxPage var7 = var0;
         int var4 = var11.size();
         if (var11.size() == 1) {
            var21 = "";
         } else {
            var21 = "s";
         }

         var21 = var4 + " segment" + var21;
         String var15 = GxRecordingsKt.access$gxRecordingsBytes(var3.optDouble("totalBytes", 0.0));
         GxPage.Row var16 = GxPage.row$default(var7, var21, var15 + " total download", (ViewGroup)var6.getBody(), 0, 8, null);
         var16.button("Download all (.tar)", true, new GxRecordingsPage$$ExternalSyntheticLambda33(var0, var1));
         GxPage.Row.button$default(var16, "Find on map", false, new GxRecordingsPage$$ExternalSyntheticLambda44(var0, var1, var11), 2, null);

         for (JSONObject var25 : var11) {
            var21 = GxPage.str$default(var0, var25, "filename", null, 2, null);
            GxPage var17 = var0;
            var4 = var25.optInt("segmentNum");
            String var9 = "Segment " + var4;
            var2 = GxRecordingsKt.access$gxRecordingsBytes(var25.optDouble("bytes", 0.0));
            GxPage.Row var26 = GxPage.row$default(var17, var9, var21 + " · " + var2, (ViewGroup)var6.getBody(), 0, 8, null);
            CharSequence var18 = GxPage.str$default(var0, var25, "segment", null, 2, null);
            Object var13 = var18;
            if (StringsKt.isBlank(var18)) {
               var13 = var1.getName();
               var4 = var25.optInt("segmentNum");
               var13 = var13 + "--" + var4;
            }

            GxPage.Row.button$default(var26, "Download", false, new GxRecordingsPage$$ExternalSyntheticLambda55(var0, var25, (String)var13, var21), 2, null);
         }

         var6.getDialog().show();
         return Unit.INSTANCE;
      } else {
         if (var2 == null || StringsKt.startsWith$default(var2, "HTTP", false, 2, null) || Intrinsics.areEqual(var2, "bad reply")) {
            var2 = "Could not read logs.";
         }

         var0.toast(var2);
         return Unit.INSTANCE;
      }
   }

   private static final Unit openLogs$lambda$82$lambda$73(GxRecordingsPage var0, GxRecordingsRoute var1) {
      String var2 = var0.enc(var1.getName());
      var2 = "/api/routes/" + var2 + "/logs/download";
      String var3 = var1.getName();
      var0.download(var2, var3 + "-logs.tar", "application/x-tar");
      return Unit.INSTANCE;
   }

   private static final Unit openLogs$lambda$82$lambda$79(GxRecordingsPage var0, GxRecordingsRoute var1, List var2) {
      List var5 = var0.routeTrack(var1);
      if (var5 == null) {
         return Unit.INSTANCE;
      } else {
         String var6 = var1.getDisplayName();
         GxRecordingsPage.Sheet var8 = var0.new Sheet(var0, (boolean)(var6 + " - tap where"), false);
         Long var7 = var1.getStartedAtMs();
         Intrinsics.checkNotNull(var7);
         long var3 = var7;
         var8.getBody()
            .addView(
               new TrackMapView((Context)var0.getAct(), var5, var3, new GxRecordingsPage$$ExternalSyntheticLambda45(var3, var2, var0, var8, var1)),
               (LayoutParams)(new android.widget.LinearLayout.LayoutParams(-1, 0, 1.0F))
            );
         var8.getDialog().show();
         return Unit.INSTANCE;
      }
   }

   private static final Unit openLogs$lambda$82$lambda$79$lambda$78(
      long var0, List var2, GxRecordingsPage var3, GxRecordingsPage.Sheet var4, GxRecordingsRoute var5, long var6
   ) {
      int var9 = (int)((var6 - var0) / 60000L);
      Iterator var10 = var2.iterator();

      boolean var8;
      do {
         if (!var10.hasNext()) {
            var12 = null;
            break;
         }

         var12 = var10.next();
         if (((JSONObject)var12).optInt("segmentNum") == var9) {
            var8 = true;
         } else {
            var8 = false;
         }
      } while (!var8);

      JSONObject var14 = (JSONObject)var12;
      var2 = CollectionsKt.mutableListOf(new String[]{"Play from here"});
      if (var14 != null) {
         var2.add("Download segment " + var9 + " logs");
      }

      var0 = (var6 - var0) % 60000L / 1000;
      var3.pick("Segment " + var9 + ", " + var0 + " s in", var2, new GxRecordingsPage$$ExternalSyntheticLambda29(var4, var3, var5, var6, var14, var9));
      return Unit.INSTANCE;
   }

   private static final Unit openLogs$lambda$82$lambda$79$lambda$78$lambda$77(
      GxRecordingsPage.Sheet var0, GxRecordingsPage var1, GxRecordingsRoute var2, long var3, JSONObject var5, int var6, int var7
   ) {
      if (var7 == 0) {
         var0.getDialog().dismiss();
         var1.new RoutePlayer(var1, var2, var3);
      } else if (var5 != null) {
         GxPage var9 = var1;
         String var8 = GxPage.str$default(var1, var5, "url", null, 2, null);
         Object var10 = GxPage.str$default(var1, var5, "segment", null, 2, null);
         if (StringsKt.isBlank((CharSequence)var10)) {
            var10 = var2.getName();
            var10 = var10 + "--" + var6;
         }

         String var13 = GxPage.str$default(var1, var5, "filename", null, 2, null);
         GxPage.download$default(var9, var8, var10 + "-" + var13, null, 4, null);
         var10 = Unit.INSTANCE;
      }

      return Unit.INSTANCE;
   }

   private static final Unit openLogs$lambda$82$lambda$81(GxRecordingsPage var0, JSONObject var1, String var2, String var3) {
      GxPage.download$default(var0, GxPage.str$default(var0, var1, "url", null, 2, null), var2 + "-" + var3, null, 4, null);
      return Unit.INSTANCE;
   }

   private final GxRecordingsRoute parseRoute(JSONObject var1) {
      String var6 = GxPage.str$default(this, var1, "name", null, 2, null);
      boolean var2 = var1.has("timestamp");
      CharSequence var4 = null;
      String var3;
      if (var2 && !var1.isNull("timestamp")) {
         var3 = var1.optString("timestamp");
      } else {
         var3 = null;
      }

      CharSequence var5 = GxPage.str$default(this, var1, "startedAt", null, 2, null);
      if (!StringsKt.isBlank(var5)) {
         var4 = var5;
      }

      GxRecordingsRoute var7 = new GxRecordingsRoute(
         var6,
         var3,
         (String)var4,
         var1.optBoolean("isCustomName"),
         var1.optBoolean("is_preserved"),
         var1.optInt("segmentCount"),
         var1.optInt("approxDurationSeconds"),
         GxPage.str$default(this, var1, "png", null, 2, null)
      );
      this.normalize(var7);
      return var7;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private final ZonedDateTime parseStarted(String var1) {
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
         ZoneId var6 = ZoneId.systemDefault();

         label147:
         try {
            Result.Companion var23 = Result.Companion;
            GxRecordingsPage var24 = this;
            var3 = (CharSequence)KtCompat.resultOf(Instant.parse(var1).atZone(var6));
         } catch (Throwable var18) {
            Result.Companion var21 = Result.Companion;
            var3 = (CharSequence)KtCompat.resultOf(ResultKt.createFailure(var18));
            break label147;
         }

         Object var4 = var3;
         if (KtCompat.isFailure(var3)) {
            var4 = null;
         }

         var4 = (ZonedDateTime)var4;
         ZonedDateTime var25 = (ZonedDateTime)var4;
         if (var4 == null) {
            label141:
            try {
               Result.Companion var27 = Result.Companion;
               GxRecordingsPage var28 = this;
               var3 = (CharSequence)KtCompat.resultOf(OffsetDateTime.parse(var1).atZoneSameInstant(var6));
            } catch (Throwable var17) {
               var4 = Result.Companion;
               var3 = (CharSequence)KtCompat.resultOf(ResultKt.createFailure(var17));
               break label141;
            }

            var4 = var3;
            if (KtCompat.isFailure(var3)) {
               var4 = null;
            }

            var4 = (ZonedDateTime)var4;
            var25 = (ZonedDateTime)var4;
            if (var4 == null) {
               label136:
               try {
                  Result.Companion var29 = Result.Companion;
                  GxRecordingsPage var30 = this;
                  var20 = KtCompat.resultOf(LocalDateTime.parse(var1).atZone(var6));
               } catch (Throwable var16) {
                  Result.Companion var19 = Result.Companion;
                  var20 = KtCompat.resultOf(ResultKt.createFailure(var16));
                  break label136;
               }

               if (KtCompat.isFailure(var20)) {
                  var20 = var5;
               }

               var25 = (ZonedDateTime)var20;
            }
         }

         return var25;
      }
   }

   private final void pumpThumbs() {
      while (this.thumbActive < 2 && !this.thumbQueue.isEmpty()) {
         GxRecordingsPage.ThumbJob var1 = this.thumbQueue.removeFirst();
         if (var1.getValid().invoke() && this.getHost() != null) {
            Bitmap var2 = (Bitmap)this.thumbCache.get(var1.getPath());
            if (var2 != null) {
               var1.getIv().setImageBitmap(var2);
               var1.getPh().setVisibility(8);
            } else {
               this.thumbActive++;
               this.fetchThumb(var1.getPath(), new GxRecordingsPage$$ExternalSyntheticLambda30(this, var1));
            }
         }
      }
   }

   private static final Unit pumpThumbs$lambda$119(GxRecordingsPage var0, GxRecordingsPage.ThumbJob var1, Bitmap var2) {
      var0.thumbActive--;
      if (var2 != null) {
         var0.thumbCache.put(var1.getPath(), var2);
         var1.getIv().setImageBitmap(var2);
         var1.getPh().setVisibility(8);
      } else {
         var0.thumbFailed.add(var1.getPath());
      }

      var0.pumpThumbs();
      return Unit.INSTANCE;
   }

   private final void renameRoute(GxRecordingsRoute var1, Function0<Unit> var2) {
      EditText var5 = new EditText((Context)this.getAct());
      var5.setText(var1.getDisplayName());
      var5.setTextSize(20.0F);
      var5.setInputType(1);
      var5.setSelection(var5.getText().length());
      FrameLayout var6 = new FrameLayout((Context)this.getAct());
      var6.setPadding(this.getUi().dp(22), this.getUi().dp(8), this.getUi().dp(22), 0);
      var6.addView((View)var5);
      android.app.AlertDialog.Builder var4 = new android.app.AlertDialog.Builder((Context)this.getAct()).setTitle("Rename route");
      String var3 = var1.getDisplayName();
      android.app.AlertDialog.Builder var7 = var4.setMessage("Rename “" + var3 + "”")
         .setView((View)var6)
         .setPositiveButton("Save", new GxRecordingsPage$$ExternalSyntheticLambda53(var5, var1, this, var2))
         .setNegativeButton("Cancel", null);
      if (var1.isCustomName()) {
         var7.setNeutralButton("Reset name", new GxRecordingsPage$$ExternalSyntheticLambda54(this, var1, var2));
      }

      var7.show();
   }

   // $VF: synthetic method
   static void renameRoute$default(GxRecordingsPage var0, GxRecordingsRoute var1, Function0 var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = null;
      }

      var0.renameRoute(var1, var2);
   }

   private static final void renameRoute$lambda$69(EditText var0, GxRecordingsRoute var1, GxRecordingsPage var2, Function0 var3, DialogInterface var4, int var5) {
      String var6 = StringsKt.trim(var0.getText().toString()).toString();
      boolean var7;
      if (var6.length() == 0) {
         var7 = true;
      } else {
         var7 = false;
      }

      if (!var7 && !Intrinsics.areEqual(var6, var1.getDisplayName())) {
         GxPage.send$default(
            var2,
            "POST",
            "/api/routes/rename",
            new JSONObject().put("old", var1.getName()).put("new", var6),
            0L,
            new GxRecordingsPage$$ExternalSyntheticLambda23(var2, var1, var6, var3),
            8,
            null
         );
      }
   }

   private static final Unit renameRoute$lambda$69$lambda$68(
      GxRecordingsPage var0, GxRecordingsRoute var1, String var2, Function0 var3, String var4, JSONObject var5
   ) {
      if (var4 != null) {
         var0.toast("Rename failed.");
      } else {
         if (var5 != null) {
            var4 = GxPage.str$default(var0, var5, "name", null, 2, null);
            if (var4 != null) {
               CharSequence var9 = var4;
               CharSequence var7 = var9;
               if (StringsKt.isBlank(var9)) {
                  var7 = null;
               }

               var4 = (String)var7;
               if (var4 != null) {
                  var2 = var4;
               }
            }
         }

         var1.setTimestamp(var2);
         var1.setCustomName(true);
         var0.normalize(var1);
         var0.renderRoutes();
         var0.toast("Route renamed!");
         if (var3 != null) {
            var3.invoke();
         }
      }

      return Unit.INSTANCE;
   }

   private static final void renameRoute$lambda$72(GxRecordingsPage var0, GxRecordingsRoute var1, Function0 var2, DialogInterface var3, int var4) {
      GxPage.send$default(
         var0,
         "POST",
         "/api/routes/reset_name",
         new JSONObject().put("name", var1.getName()),
         0L,
         new GxRecordingsPage$$ExternalSyntheticLambda57(var0, var1, var2),
         8,
         null
      );
   }

   private static final Unit renameRoute$lambda$72$lambda$71(GxRecordingsPage var0, GxRecordingsRoute var1, Function0 var2, String var3, JSONObject var4) {
      if (var3 != null) {
         var0.toast("Resetting name failed...");
      } else {
         Object var6 = null;
         Object var5 = null;
         var3 = (String)var6;
         if (var4 != null) {
            String var9 = GxPage.str$default(var0, var4, "timestamp", null, 2, null);
            var3 = (String)var6;
            if (var9 != null) {
               var3 = var9;
               if (StringsKt.isBlank(var3)) {
                  var3 = (String)var5;
               }

               var3 = var3;
            }
         }

         var1.setTimestamp(var3);
         var1.setCustomName(false);
         var0.normalize(var1);
         var0.renderRoutes();
         var0.toast("Route name reset!");
         if (var2 != null) {
            var2.invoke();
         }
      }

      return Unit.INSTANCE;
   }

   private final void renameScreen(GxRecordingsScreen var1, Function0<Unit> var2) {
      CharSequence var3 = var1.getFilename();
      String var4 = new Regex("\\.mp4$", RegexOption.IGNORE_CASE).replace(var3, "");
      GxPage.prompt$default(this, "Rename recording:", null, var4, 0, null, new GxRecordingsPage$$ExternalSyntheticLambda19(var4, this, var1, var2), 26, null);
   }

   // $VF: synthetic method
   static void renameScreen$default(GxRecordingsPage var0, GxRecordingsScreen var1, Function0 var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = null;
      }

      var0.renameScreen(var1, var2);
   }

   private static final Unit renameScreen$lambda$103(String var0, GxRecordingsPage var1, GxRecordingsScreen var2, Function0 var3, String var4) {
      Intrinsics.checkNotNullParameter(var4, "v");
      var4 = StringsKt.trim(var4).toString();
      boolean var5;
      if (var4.length() == 0) {
         var5 = true;
      } else {
         var5 = false;
      }

      if (!var5 && !Intrinsics.areEqual(var4, var0)) {
         GxPage.send$default(
            var1,
            "POST",
            "/api/screen_recordings/rename",
            new JSONObject().put("old", var2.getFilename()).put("new", var4 + ".mp4"),
            0L,
            new GxRecordingsPage$$ExternalSyntheticLambda56(var1, var3),
            8,
            null
         );
         return Unit.INSTANCE;
      } else {
         return Unit.INSTANCE;
      }
   }

   private static final Unit renameScreen$lambda$103$lambda$102(GxRecordingsPage var0, Function0 var1, String var2, JSONObject var3) {
      if (var2 != null) {
         var0.toast("Rename failed.");
      } else {
         var0.toast("Recording renamed!");
         if (var1 != null) {
            var1.invoke();
         }

         var0.loadScreens();
      }

      return Unit.INSTANCE;
   }

   private final void renderRoutes() {
      LinearLayout var7 = this.routesList;
      if (var7 != null) {
         this.routesGen++;
         int var4 = this.routesGen;
         var7.removeAllViews();
         TextView var8 = this.routesStats;
         byte var2 = 0;
         if (var8 != null) {
            int var5 = this.routes.size();
            Iterator var9 = this.routes.iterator();
            int var1 = 0;

            while (var9.hasNext()) {
               GxRecordingsRoute var6 = (GxRecordingsRoute)var9.next();
               var1 += var6.getApproxDurationSeconds();
            }

            String var30 = GxRecordingsKt.access$gxRecordingsDuration(var1);
            var var18 = this.routes;
            if (var18 instanceof Collection && var18.isEmpty()) {
               var1 = 0;
            } else {
               var1 = 0;
               Iterator var19 = var18.iterator();

               while (var19.hasNext()) {
                  if (((GxRecordingsRoute)var19.next()).isPreserved()) {
                     int var3 = var1 + 1;
                     var1 = var3;
                     if (var3 < 0) {
                        CollectionsKt.throwCountOverflow();
                        var1 = var3;
                     }
                  }
               }
            }

            String var20;
            if (var1 > 0) {
               var20 = " · " + var1 + " preserved";
            } else {
               var20 = "";
            }

            var8.setText(var5 + " drives · " + var30 + var20);
         }

         var8 = this.routesStatus;
         if (var8 != null) {
            if (this.routesLoading) {
               String var21;
               if (this.routesTotal > 0) {
                  int var12 = this.routesProgress;
                  int var17 = this.routesTotal;
                  var21 = "Finding local routes... (" + var12 + " of " + var17 + ")";
               } else {
                  var21 = "Finding local routes...";
               }

               var8.setText(var21);
               var8.setTextColor(CarUi.Companion.getDIM());
               var8.setVisibility(0);
            } else {
               boolean var13;
               if (this.routesError.length() > 0) {
                  var13 = true;
               } else {
                  var13 = false;
               }

               if (var13) {
                  var8.setText(this.routesError);
                  var8.setTextColor(CarUi.Companion.getWARN());
                  var8.setVisibility(0);
               } else {
                  var8.setVisibility(8);
               }
            }
         }

         Iterable var27 = this.routes;
         var var22 = new ArrayList();

         for (Object var28 : var27) {
            GxRecordingsRoute var31 = (GxRecordingsRoute)var28;
            boolean var14;
            if ((!this.preservedOnly || var31.isPreserved()) && this.matches(var31, this.search)) {
               var14 = true;
            } else {
               var14 = false;
            }

            if (var14) {
               var22.add(var28);
            }
         }

         List var23 = var22;
         List var24 = this.sorted(var23);
         if (var24.isEmpty() && !this.routesLoading) {
            var7.addView((View)this.empty("No routes found."));
         }

         Iterator var29 = CollectionsKt.take(var24, 250).iterator();

         while (var29.hasNext()) {
            this.routeRow((GxRecordingsRoute)var29.next(), var7, var4);
         }

         if (var24.size() > 250) {
            int var15 = var24.size();
            this.note("Showing the first 250 of " + var15 + " matching routes. Search to narrow the list.", (ViewGroup)var7);
         }

         LinearLayout var25 = this.routesDeleteBox;
         if (var25 != null) {
            byte var16;
            if (!this.routes.isEmpty()) {
               var16 = var2;
            } else {
               var16 = 8;
            }

            var25.setVisibility(var16);
         }
      }
   }

   private final void renderScreens() {
      LinearLayout var6 = this.screenList;
      if (var6 != null) {
         this.screenGen++;
         int var3 = this.screenGen;
         var6.removeAllViews();
         TextView var7 = this.screenCount;
         if (var7 != null) {
            int var1 = this.screens.size();
            String var5;
            if (this.screens.size() == 1) {
               var5 = "";
            } else {
               var5 = "s";
            }

            var7.setText(var1 + " recording" + var5);
         }

         boolean var4 = this.screenLoading;
         byte var2 = 0;
         if (var4 && this.screens.isEmpty()) {
            var6.addView((View)this.empty("Loading screen recordings..."));
         } else {
            boolean var9;
            if (this.screenError.length() > 0) {
               var9 = true;
            } else {
               var9 = false;
            }

            if (var9 && this.screens.isEmpty()) {
               TextView var11 = this.empty(this.screenError);
               var11.setTextColor(CarUi.Companion.getWARN());
               var6.addView((View)var11);
            } else if (this.screens.isEmpty()) {
               var6.addView((View)this.empty("No screen recordings found."));
            }
         }

         Iterator var12 = this.screens.iterator();
         Intrinsics.checkNotNullExpressionValue(var12, "iterator(...)");

         while (var12.hasNext()) {
            Object var14 = var12.next();
            Intrinsics.checkNotNullExpressionValue(var14, "next(...)");
            var14 = (GxRecordingsScreen)var14;
            GxPage.Row var8 = GxPage.row$default(
               this, this.screenName((GxRecordingsScreen)var14), ((GxRecordingsScreen)var14).getFilename(), (ViewGroup)var6, 0, 8, null
            );
            this.thumbInto(var8, ((GxRecordingsScreen)var14).getPng(), 150, 76, new GxRecordingsPage$$ExternalSyntheticLambda37(var3, this));
            var8.button("Play", true, new GxRecordingsPage$$ExternalSyntheticLambda38(this, (GxRecordingsScreen)var14));
            GxPage.Row.button$default(var8, "Rename", false, new GxRecordingsPage$$ExternalSyntheticLambda39(this, (GxRecordingsScreen)var14), 2, null);
            GxPage.Row.button$default(var8, "Download", false, new GxRecordingsPage$$ExternalSyntheticLambda40(this, (GxRecordingsScreen)var14), 2, null);
            GxPage.Row.button$default(var8, "Delete", false, new GxRecordingsPage$$ExternalSyntheticLambda41(this, (GxRecordingsScreen)var14), 2, null)
               .setTextColor(GxRecordingsKt.access$getGxRecordingsDanger$p());
            var8.getView().setOnClickListener(new GxRecordingsPage$$ExternalSyntheticLambda42(this, (GxRecordingsScreen)var14));
         }

         LinearLayout var13 = this.screenDeleteBox;
         if (var13 != null) {
            byte var10;
            if (!this.screens.isEmpty()) {
               var10 = var2;
            } else {
               var10 = 8;
            }

            var13.setVisibility(var10);
         }
      }
   }

   private static final Unit renderScreens$lambda$100(GxRecordingsPage var0, GxRecordingsScreen var1) {
      deleteScreen$default(var0, var1, null, 2, null);
      return Unit.INSTANCE;
   }

   private static final void renderScreens$lambda$101(GxRecordingsPage var0, GxRecordingsScreen var1, View var2) {
      var0.new ScreenPlayer(var0, var1);
   }

   private static final boolean renderScreens$lambda$96(int var0, GxRecordingsPage var1) {
      boolean var2;
      if (var0 == var1.screenGen) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   private static final Unit renderScreens$lambda$97(GxRecordingsPage var0, GxRecordingsScreen var1) {
      var0.new ScreenPlayer(var0, var1);
      return Unit.INSTANCE;
   }

   private static final Unit renderScreens$lambda$98(GxRecordingsPage var0, GxRecordingsScreen var1) {
      renameScreen$default(var0, var1, null, 2, null);
      return Unit.INSTANCE;
   }

   private static final Unit renderScreens$lambda$99(GxRecordingsPage var0, GxRecordingsScreen var1) {
      var0.downloadScreen(var1);
      return Unit.INSTANCE;
   }

   private final String routeDesc(GxRecordingsRoute var1) {
      boolean var3 = var1.isPreserved();
      String var5 = "";
      String var4;
      if (var3) {
         var4 = "Preserved · ";
      } else {
         var4 = "";
      }

      if (var1.isCustomName()) {
         var5 = var1.getDisplayDate();
         var5 = var5 + " · ";
      }

      String var6 = GxRecordingsKt.access$gxRecordingsDuration(var1.getApproxDurationSeconds());
      int var2 = var1.getSegmentCount();
      return var4 + var5 + var6 + " · " + var2 + " segments";
   }

   private final void routeRow(GxRecordingsRoute var1, LinearLayout var2, int var3) {
      GxPage var6 = this;
      CharSequence var5 = var1.getDisplayName();
      Object var4 = var5;
      if (StringsKt.isBlank(var5)) {
         var4 = var1.getName();
      }

      var4 = GxPage.row$default(var6, (String)var4, this.routeDesc(var1), (ViewGroup)var2, 0, 8, null);
      if (var1.isPreserved()) {
         ((GxPage.Row)var4).getTitleView().setTextColor(CarUi.Companion.getACCENT());
      }

      this.thumbInto((GxPage.Row)var4, var1.getPng(), 150, 84, new GxRecordingsPage$$ExternalSyntheticLambda62(var3, this));
      String var7;
      if (var1.isPreserved()) {
         var7 = "Unpreserve";
      } else {
         var7 = "Preserve";
      }

      GxPage.Row.button$default((GxPage.Row)var4, var7, false, new GxRecordingsPage$$ExternalSyntheticLambda1(this, var1), 2, null);
      GxPage.Row.button$default((GxPage.Row)var4, "Logs", false, new GxRecordingsPage$$ExternalSyntheticLambda2(this, var1), 2, null);
      GxPage.Row.button$default((GxPage.Row)var4, "Rename", false, new GxRecordingsPage$$ExternalSyntheticLambda3(this, var1), 2, null);
      GxPage.Row.button$default((GxPage.Row)var4, "Delete", false, new GxRecordingsPage$$ExternalSyntheticLambda4(this, var1), 2, null)
         .setTextColor(GxRecordingsKt.access$getGxRecordingsDanger$p());
      ((GxPage.Row)var4).getView().setOnClickListener(new GxRecordingsPage$$ExternalSyntheticLambda5(this, var1));
   }

   private static final boolean routeRow$lambda$53(int var0, GxRecordingsPage var1) {
      boolean var2;
      if (var0 == var1.routesGen) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   private static final Unit routeRow$lambda$54(GxRecordingsPage var0, GxRecordingsRoute var1) {
      togglePreserved$default(var0, var1, null, 2, null);
      return Unit.INSTANCE;
   }

   private static final Unit routeRow$lambda$55(GxRecordingsPage var0, GxRecordingsRoute var1) {
      var0.openLogs(var1);
      return Unit.INSTANCE;
   }

   private static final Unit routeRow$lambda$56(GxRecordingsPage var0, GxRecordingsRoute var1) {
      renameRoute$default(var0, var1, null, 2, null);
      return Unit.INSTANCE;
   }

   private static final Unit routeRow$lambda$57(GxRecordingsPage var0, GxRecordingsRoute var1) {
      deleteRoute$default(var0, var1, null, 2, null);
      return Unit.INSTANCE;
   }

   private static final void routeRow$lambda$58(GxRecordingsPage var0, GxRecordingsRoute var1, View var2) {
      var0.new RoutePlayer(var0, var1, null, 2, null);
   }

   private final List<DriveTrack.Pt> routeTrack(GxRecordingsRoute var1) {
      Long var6 = var1.getStartedAtMs();
      if (var6 != null) {
         long var2 = var6;
         long var4 = Math.max(var1.getSegmentCount(), 1) + 1;
         List var7 = DriveTrack.INSTANCE.between((Context)this.getAct(), var2 - 30000L, var4 * 60000L + var2);
         if (var7.size() < 2) {
            this.toast("No path for this drive: the tablet keeps one for drives from StarView 2.70 on, while it is connected.");
            return null;
         } else {
            return var7;
         }
      } else {
         this.toast("This drive has no start time - can't find it on the map.");
         return null;
      }
   }

   private final void scheduleRender() {
      if (!this.renderPending) {
         this.renderPending = true;
         this.getMain().postDelayed(new GxRecordingsPage$$ExternalSyntheticLambda58(this), 400L);
      }
   }

   private static final void scheduleRender$lambda$17(GxRecordingsPage var0) {
      var0.renderPending = false;
      var0.renderRoutes();
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private final String screenName(GxRecordingsScreen var1) {
      if (var1.isCustomName()) {
         CharSequence var13 = var1.getFilename();
         return StringsKt.replace$default(new Regex("\\.mp4$", RegexOption.IGNORE_CASE).replace(var13, ""), "_", " ", false, 4, null);
      } else {
         Object var16;
         label78:
         try {
            var16 = Result.Companion;
            var16 = this;
            var16 = (LocalDateTime)KtCompat.resultOf(LocalDateTime.parse(var1.getTimestamp()));
         } catch (Throwable var10) {
            var16 = Result.Companion;
            var16 = (LocalDateTime)KtCompat.resultOf(ResultKt.createFailure(var10));
            break label78;
         }

         Object var7 = var16;
         if (KtCompat.isFailure(var16)) {
            var7 = null;
         }

         var16 = (LocalDateTime)var7;
         if (var16 == null) {
            CharSequence var21 = var1.getTimestamp();
            Object var12 = var21;
            if (StringsKt.isBlank(var21)) {
               var12 = "Unknown date";
            }

            return (String)var12;
         } else {
            int var4 = var16.getDayOfMonth();
            int var5 = var4 % 100;
            int var3 = 0;
            int var2 = var3;
            if (11 <= var5) {
               var2 = var3;
               if (var5 < 14) {
                  var2 = 1;
               }
            }

            String var11 = "th";
            if (!var2) {
               switch (var4 % 10) {
                  case 1:
                     var11 = "st";
                     break;
                  case 2:
                     var11 = "nd";
                     break;
                  case 3:
                     var11 = "rd";
               }
            }

            if (var16.getHour() % 12 == 0) {
               var2 = 12;
            } else {
               var2 = var16.getHour() % 12;
            }

            var7 = var16.format(DateTimeFormatter.ofPattern("MMMM", Locale.US));
            var3 = var16.getYear();
            StringCompanionObject var8 = StringCompanionObject.INSTANCE;
            String var23 = String.format(Locale.US, "%02d", Arrays.copyOf(new Object[]{var16.getMinute()}, 1));
            Intrinsics.checkNotNullExpressionValue(var23, "format(...)");
            String var20;
            if (var16.getHour() >= 12) {
               var20 = "pm";
            } else {
               var20 = "am";
            }

            return var7 + " " + var4 + var11 + ", " + var3 + " - " + var2 + ":" + var23 + var20;
         }
      }
   }

   private final String screenVideoPath(GxRecordingsScreen var1) {
      String var2 = this.enc(var1.getFilename());
      return "/api/screen_recordings/download/" + var2;
   }

   private final List<GxRecordingsRoute> sorted(List<GxRecordingsRoute> var1) {
      int var3 = this.sortOrder;
      byte var2 = -1;
      switch (var3) {
         case 2:
         case 3:
            if (this.sortOrder != 2) {
               var2 = 1;
            }

            var1 = CollectionsKt.sortedWith(var1, new GxRecordingsPage$$ExternalSyntheticLambda15(new GxRecordingsPage$$ExternalSyntheticLambda14(var2)));
            break;
         default:
            if (this.sortOrder == 1) {
               var2 = 1;
            }

            var1 = CollectionsKt.sortedWith(var1, new GxRecordingsPage$$ExternalSyntheticLambda17(new GxRecordingsPage$$ExternalSyntheticLambda16(var2)));
      }

      return var1;
   }

   private static final int sorted$lambda$43(int var0, GxRecordingsRoute var1, GxRecordingsRoute var2) {
      if (var1.getApproxDurationSeconds() != var2.getApproxDurationSeconds()) {
         var0 = (var1.getApproxDurationSeconds() - var2.getApproxDurationSeconds()) * var0;
      } else {
         var0 = var1.getName().compareTo(var2.getName());
      }

      return var0;
   }

   private static final int sorted$lambda$44(Function2 var0, Object var1, Object var2) {
      return ((Number)var0.invoke(var1, var2)).intValue();
   }

   private static final int sorted$lambda$45(int var0, GxRecordingsRoute var1, GxRecordingsRoute var2) {
      Long var4 = var1.getStartedAtMs();
      Long var3 = var2.getStartedAtMs();
      if (var4 == null && var3 == null) {
         var0 = var1.getName().compareTo(var2.getName());
      } else if (var4 == null) {
         var0 = 1;
      } else if (var3 == null) {
         var0 = -1;
      } else if (!Intrinsics.areEqual(var4, var3)) {
         var0 = Intrinsics.compare(var4, var3) * var0;
      } else {
         var0 = var1.getName().compareTo(var2.getName()) * -var0;
      }

      return var0;
   }

   private static final int sorted$lambda$46(Function2 var0, Object var1, Object var2) {
      return ((Number)var0.invoke(var1, var2)).intValue();
   }

   private final Call stream(String var1, Function1<? super JSONObject, Unit> var2, Function1<? super String, Unit> var3) {
      if (this.getHost() == null) {
         var3.invoke("Not connected");
         return null;
      } else {
         Call var4 = GxPage.Companion.client(120L).newCall(new Builder().url(this.url(var1)).header("Accept", "text/event-stream").build());
         new Thread(new GxRecordingsPage$$ExternalSyntheticLambda0(var4, this, var2, var3)).start();
         return var4;
      }
   }

   private static final void stream$lambda$135(Call param0, GxRecordingsPage param1, Function1 param2, Function1 param3) {
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
      // 007: astore 7
      // 009: aload 0
      // 00a: invokeinterface okhttp3/Call.execute ()Lokhttp3/Response; 1
      // 00f: checkcast java/io/Closeable
      // 012: astore 6
      // 014: aload 6
      // 016: checkcast okhttp3/Response
      // 019: astore 8
      // 01b: aload 8
      // 01d: invokevirtual okhttp3/Response.isSuccessful ()Z
      // 020: ifeq 0f8
      // 023: aload 8
      // 025: invokevirtual okhttp3/Response.body ()Lokhttp3/ResponseBody;
      // 028: astore 8
      // 02a: aload 8
      // 02c: ifnull 0eb
      // 02f: aload 8
      // 031: invokevirtual okhttp3/ResponseBody.source ()Lokio/BufferedSource;
      // 034: astore 8
      // 036: aload 8
      // 038: ifnull 0eb
      // 03b: new java/lang/StringBuilder
      // 03e: astore 9
      // 040: aload 9
      // 042: invokespecial java/lang/StringBuilder.<init> ()V
      // 045: aload 8
      // 047: invokeinterface okio/BufferedSource.readUtf8Line ()Ljava/lang/String; 1
      // 04c: astore 10
      // 04e: aload 10
      // 050: ifnonnull 068
      // 053: aload 9
      // 055: aload 1
      // 056: aload 0
      // 057: aload 2
      // 058: invokestatic com/starpilot/starview/GxRecordingsPage.stream$lambda$135$lambda$133$flush (Ljava/lang/StringBuilder;Lcom/starpilot/starview/GxRecordingsPage;Lokhttp3/Call;Lkotlin/jvm/functions/Function1;)V
      // 05b: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 05e: astore 2
      // 05f: aload 6
      // 061: aconst_null
      // 062: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 065: goto 14e
      // 068: aload 10
      // 06a: checkcast java/lang/CharSequence
      // 06d: invokeinterface java/lang/CharSequence.length ()I 1
      // 072: istore 4
      // 074: bipush 1
      // 075: istore 5
      // 077: iload 4
      // 079: ifne 082
      // 07c: bipush 1
      // 07d: istore 4
      // 07f: goto 085
      // 082: bipush 0
      // 083: istore 4
      // 085: iload 4
      // 087: ifeq 095
      // 08a: aload 9
      // 08c: aload 1
      // 08d: aload 0
      // 08e: aload 2
      // 08f: invokestatic com/starpilot/starview/GxRecordingsPage.stream$lambda$135$lambda$133$flush (Ljava/lang/StringBuilder;Lcom/starpilot/starview/GxRecordingsPage;Lokhttp3/Call;Lkotlin/jvm/functions/Function1;)V
      // 092: goto 045
      // 095: aload 10
      // 097: ldc_w "data:"
      // 09a: bipush 0
      // 09b: bipush 2
      // 09c: aconst_null
      // 09d: invokestatic kotlin/text/StringsKt.startsWith$default (Ljava/lang/String;Ljava/lang/String;ZILjava/lang/Object;)Z
      // 0a0: ifeq 045
      // 0a3: aload 9
      // 0a5: checkcast java/lang/CharSequence
      // 0a8: invokeinterface java/lang/CharSequence.length ()I 1
      // 0ad: ifle 0b7
      // 0b0: iload 5
      // 0b2: istore 4
      // 0b4: goto 0ba
      // 0b7: bipush 0
      // 0b8: istore 4
      // 0ba: iload 4
      // 0bc: ifeq 0c7
      // 0bf: aload 9
      // 0c1: bipush 10
      // 0c3: invokevirtual java/lang/StringBuilder.append (C)Ljava/lang/StringBuilder;
      // 0c6: pop
      // 0c7: aload 10
      // 0c9: bipush 5
      // 0ca: invokevirtual java/lang/String.substring (I)Ljava/lang/String;
      // 0cd: astore 10
      // 0cf: aload 10
      // 0d1: ldc_w "substring(...)"
      // 0d4: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 0d7: aload 9
      // 0d9: aload 10
      // 0db: checkcast java/lang/CharSequence
      // 0de: invokestatic kotlin/text/StringsKt.trimStart (Ljava/lang/CharSequence;)Ljava/lang/CharSequence;
      // 0e1: invokevirtual java/lang/Object.toString ()Ljava/lang/String;
      // 0e4: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 0e7: pop
      // 0e8: goto 045
      // 0eb: new java/lang/IllegalStateException
      // 0ee: astore 2
      // 0ef: aload 2
      // 0f0: ldc_w "empty reply"
      // 0f3: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
      // 0f6: aload 2
      // 0f7: athrow
      // 0f8: new java/lang/IllegalStateException
      // 0fb: astore 2
      // 0fc: aload 8
      // 0fe: invokevirtual okhttp3/Response.code ()I
      // 101: istore 4
      // 103: new java/lang/StringBuilder
      // 106: astore 8
      // 108: aload 8
      // 10a: invokespecial java/lang/StringBuilder.<init> ()V
      // 10d: aload 2
      // 10e: aload 8
      // 110: ldc_w "HTTP "
      // 113: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 116: iload 4
      // 118: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
      // 11b: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 11e: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
      // 121: aload 2
      // 122: athrow
      // 123: astore 2
      // 124: aload 2
      // 125: athrow
      // 126: astore 8
      // 128: aload 6
      // 12a: aload 2
      // 12b: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 12e: aload 8
      // 130: athrow
      // 131: astore 8
      // 133: aload 8
      // 135: invokevirtual java/lang/Exception.getMessage ()Ljava/lang/String;
      // 138: astore 6
      // 13a: aload 6
      // 13c: astore 2
      // 13d: aload 6
      // 13f: ifnonnull 148
      // 142: aload 8
      // 144: invokevirtual java/lang/Exception.toString ()Ljava/lang/String;
      // 147: astore 2
      // 148: aload 7
      // 14a: aload 2
      // 14b: putfield kotlin/jvm/internal/Ref$ObjectRef.element Ljava/lang/Object;
      // 14e: aload 1
      // 14f: invokevirtual com/starpilot/starview/GxRecordingsPage.getMain ()Landroid/os/Handler;
      // 152: new com/starpilot/starview/GxRecordingsPage$$ExternalSyntheticLambda27
      // 155: dup
      // 156: aload 0
      // 157: aload 3
      // 158: aload 7
      // 15a: invokespecial com/starpilot/starview/GxRecordingsPage$$ExternalSyntheticLambda27.<init> (Lokhttp3/Call;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/internal/Ref$ObjectRef;)V
      // 15d: invokevirtual android/os/Handler.post (Ljava/lang/Runnable;)Z
      // 160: pop
      // 161: return
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private static final void stream$lambda$135$lambda$133$flush(StringBuilder var0, GxRecordingsPage var1, Call var2, Function1<? super JSONObject, Unit> var3) {
      boolean var4;
      if (var0.length() == 0) {
         var4 = true;
      } else {
         var4 = false;
      }

      if (!var4) {
         Object var9;
         label40:
         try {
            var9 = Result.Companion;
            var9 = new JSONObject(var0.toString());
            var9 = KtCompat.resultOf(var9);
         } catch (Throwable var8) {
            var9 = Result.Companion;
            var9 = KtCompat.resultOf(ResultKt.createFailure(var8));
            break label40;
         }

         Object var6 = var9;
         if (KtCompat.isFailure(var9)) {
            var6 = null;
         }

         var9 = (JSONObject)var6;
         var0.setLength(0);
         if (var9 != null) {
            var1.getMain().post(new GxRecordingsPage$$ExternalSyntheticLambda36(var2, var3, (JSONObject)var9));
         }
      }
   }

   private static final void stream$lambda$135$lambda$133$flush$lambda$132(Call var0, Function1 var1, JSONObject var2) {
      if (!var0.isCanceled()) {
         var1.invoke(var2);
      }
   }

   private static final void stream$lambda$135$lambda$134(Call var0, Function1 var1, ObjectRef var2) {
      if (!var0.isCanceled()) {
         var1.invoke(var2.element);
      }
   }

   private final void thumbInto(GxPage.Row var1, String var2, int var3, int var4, Function0<Boolean> var5) {
      FrameLayout var8 = new FrameLayout((Context)this.getAct());
      var8.setBackground((Drawable)CarUi.rounded$default(this.getUi(), Color.rgb(6, 8, 11), 10, null, 4, null));
      var8.setClipToOutline(true);
      TextView var6 = new TextView((Context)this.getAct());
      var6.setText("Preview unavailable");
      var6.setTextColor(CarUi.Companion.getDIM());
      var6.setTextSize(12.0F);
      var6.setGravity(17);
      ImageView var7 = new ImageView((Context)this.getAct());
      var7.setScaleType(ScaleType.CENTER_CROP);
      var8.addView((View)var6, (LayoutParams)(new android.widget.FrameLayout.LayoutParams(-1, -1)));
      var8.addView((View)var7, (LayoutParams)(new android.widget.FrameLayout.LayoutParams(-1, -1)));
      LinearLayout var11 = var1.getView();
      View var10 = (View)var8;
      android.widget.LinearLayout.LayoutParams var13 = new android.widget.LinearLayout.LayoutParams(this.getUi().dp(var3), this.getUi().dp(var4));
      var13.rightMargin = this.getUi().dp(16);
      Unit var9 = Unit.INSTANCE;
      var11.addView(var10, 0, (LayoutParams)var13);
      if (!StringsKt.isBlank(var2)) {
         Bitmap var12 = (Bitmap)this.thumbCache.get(var2);
         if (var12 != null) {
            var7.setImageBitmap(var12);
            var6.setVisibility(8);
         } else if (!this.thumbFailed.contains(var2)) {
            this.thumbQueue.addLast(new GxRecordingsPage.ThumbJob(var2, var7, (View)var6, var5));
            this.pumpThumbs();
         }
      }
   }

   private final void togglePreserved(GxRecordingsRoute var1, Function0<Unit> var2) {
      GxPage var4 = this;
      String var3;
      if (var1.isPreserved()) {
         var3 = "DELETE";
      } else {
         var3 = "POST";
      }

      String var5 = this.enc(var1.getName());
      GxPage.send$default(
         var4, var3, "/api/routes/" + var5 + "/preserve", null, 0L, new GxRecordingsPage$$ExternalSyntheticLambda46(this, var1, var2), 12, null
      );
   }

   // $VF: synthetic method
   static void togglePreserved$default(GxRecordingsPage var0, GxRecordingsRoute var1, Function0 var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = null;
      }

      var0.togglePreserved(var1, var2);
   }

   private static final Unit togglePreserved$lambda$59(GxRecordingsPage var0, GxRecordingsRoute var1, Function0 var2, String var3, JSONObject var4) {
      if (var3 != null) {
         String var5;
         if (!StringsKt.startsWith$default(var3, "HTTP", false, 2, null) && !Intrinsics.areEqual(var3, "bad reply")) {
            var5 = var3;
         } else {
            var5 = "Failed to update preserved state.";
         }

         var0.toast(var5);
      } else {
         var1.setPreserved(var1.isPreserved() ^ true);
         var0.renderRoutes();
         if (var2 != null) {
            var2.invoke();
         }
      }

      return Unit.INSTANCE;
   }

   @Override
   public void build() {
      GxPage.tabs$default(
         this,
         CollectionsKt.listOf(new String[]{"Dashcam Routes", "Screen Recordings"}),
         0,
         new GxRecordingsPage$$ExternalSyntheticLambda34(this),
         new GxRecordingsPage$$ExternalSyntheticLambda35(this),
         2,
         null
      );
   }

   @Override
   public void onHide() {
      Call var1 = this.routeCall;
      if (var1 != null) {
         var1.cancel();
      }

      this.routeCall = null;
      var1 = this.screenCall;
      if (var1 != null) {
         var1.cancel();
      }

      this.screenCall = null;
      if (this.routesLoading) {
         this.routesLoading = false;
         this.renderRoutes();
      }

      if (this.screenLoading) {
         this.screenLoading = false;
         this.renderScreens();
      }
   }

   @Override
   public void onShow() {
      this.loadRoutes();
      if (this.tab == 1 || this.screenLoaded) {
         this.loadScreens();
      }
   }

   private final class RoutePlayer {
      private String cam;
      private final LinearLayout camBox;
      private List<String> cameras;
      private final LinearLayout controls;
      private int current;
      private final FrameLayout frame;
      private Long jumpTo;
      private TrackMapView mapView;
      private final MediaController mc;
      private final LinearLayout mid;
      private final TextView next;
      private Function1<? super Integer, Unit> paintQual;
      private int pendingSeekMs;
      private TextView preserveBtn;
      private final TextView prev;
      private boolean preview;
      private final LinearLayout qualBox;
      private final GxRecordingsRoute r;
      private final LinearLayout segBar;
      private final TextView segLabel;
      private List<String> segments;
      private final GxRecordingsPage.Sheet sheet;
      private final TextView status;
      final GxRecordingsPage this$0;
      private final Runnable tickPos;
      private int token;
      private final VideoView vv;

      // $VF: synthetic method
      public static void $r8$lambda$_L5hC99yDvGC7UozyUr66SPppyU/* $VF was: $r8$lambda$_L5hC99yDvGC7UozyUr66SPppyU*/(
         GxRecordingsPage.RoutePlayer var0, MediaPlayer var1
      ) {
         _init_$lambda$11(var0, var1);
      }

      // $VF: synthetic method
      public static Unit $r8$lambda$_i95tBLSVpDqoz1w_lU4xpsUhxY/* $VF was: $r8$lambda$_i95tBLSVpDqoz1w_lU4xpsUhxY*/(
         GxRecordingsPage.RoutePlayer var0, int var1, Function1 var2
      ) {
         return fetch$lambda$48$lambda$47(var0, var1, var2);
      }

      // $VF: synthetic method
      public static void $r8$lambda$_w8ZdedDU0u3J9G0lQRLbFQkH5I/* $VF was: $r8$lambda$_w8ZdedDU0u3J9G0lQRLbFQkH5I*/(
         GxRecordingsPage.RoutePlayer var0, GxRecordingsPage var1, DialogInterface var2
      ) {
         _init_$lambda$31(var0, var1, var2);
      }

      // $VF: synthetic method
      public static Unit $r8$lambda$507pdKMbwfBCF0JAFtpxgsZaAcM(GxRecordingsPage var0, GxRecordingsPage.RoutePlayer var1) {
         return _init_$lambda$25(var0, var1);
      }

      // $VF: synthetic method
      public static Unit $r8$lambda$9KESusoUMOORlTxRBvFZVhTVj1A(GxRecordingsPage.RoutePlayer var0, int var1, Function1 var2) {
         return fetch$lambda$48$lambda$46(var0, var1, var2);
      }

      // $VF: synthetic method
      public static Unit $r8$lambda$BiPuRl2_KP8Suwa8qNObV3yMVJY/* $VF was: $r8$lambda$BiPuRl2_KP8Suwa8qNObV3yMVJY*/(
         GxRecordingsPage.RoutePlayer var0, GxRecordingsPage var1, int var2, String var3, String var4
      ) {
         return fetch$lambda$48(var0, var1, var2, var3, var4);
      }

      // $VF: synthetic method
      public static Unit $r8$lambda$HlGwxMYNd_C41NPb7zJ6PTtqNCg/* $VF was: $r8$lambda$HlGwxMYNd_C41NPb7zJ6PTtqNCg*/(
         GxRecordingsPage var0, GxRecordingsPage.RoutePlayer var1
      ) {
         return _init_$lambda$20(var0, var1);
      }

      // $VF: synthetic method
      public static Unit $r8$lambda$IcYXL7LXSPXhx8kszDvTcqP9Y8k(
         int var0, GxRecordingsPage.RoutePlayer var1, String var2, int var3, GxRecordingsPage var4, int var5
      ) {
         return probe$lambda$51(var0, var1, var2, var3, var4, var5);
      }

      // $VF: synthetic method
      public static Unit $r8$lambda$QnYbGbERk_iC9h_MSZZ5FQNYdV4(GxRecordingsPage var0, GxRecordingsPage.RoutePlayer var1) {
         return _init_$lambda$27(var0, var1);
      }

      // $VF: synthetic method
      public static Unit $r8$lambda$ROxQhdxp626CISxn9LkghCeLetQ(GxRecordingsPage var0, GxRecordingsPage.RoutePlayer var1) {
         return _init_$lambda$23(var0, var1);
      }

      // $VF: synthetic method
      public static boolean $r8$lambda$S77DCdzZEvVutk2TQmfLmrvXR4Y(GxRecordingsPage.RoutePlayer var0, MediaPlayer var1, int var2, int var3) {
         return _init_$lambda$10(var0, var1, var2, var3);
      }

      // $VF: synthetic method
      public static Unit $r8$lambda$VHa7HZqDhw98KNgqERf4cOiJuy8(GxRecordingsPage var0, GxRecordingsPage.RoutePlayer var1) {
         return _init_$lambda$19(var0, var1);
      }

      // $VF: synthetic method
      public static Unit $r8$lambda$W2dMIkKI_3nvpckZATfpurMYDAA/* $VF was: $r8$lambda$W2dMIkKI_3nvpckZATfpurMYDAA*/(
         GxRecordingsPage.RoutePlayer var0, long var1
      ) {
         return toggleMap$lambda$35(var0, var1);
      }

      // $VF: synthetic method
      public static Unit $r8$lambda$WXUqiZTMMePLg1oR31y4yKT6iYA(GxRecordingsPage var0, GxRecordingsPage.RoutePlayer var1) {
         return _init_$lambda$15(var0, var1);
      }

      // $VF: synthetic method
      public static Unit $r8$lambda$XFPOWNqDugkyYZW7BvaaUgA0h1w(GxRecordingsPage.RoutePlayer var0) {
         return _init_$lambda$16(var0);
      }

      // $VF: synthetic method
      public static void $r8$lambda$dHsihJRYxPX2MyId1K850N1AVhE(int var0, GxRecordingsPage.RoutePlayer var1, String var2, int var3) {
         probe$lambda$51$lambda$50(var0, var1, var2, var3);
      }

      // $VF: synthetic method
      public static void $r8$lambda$eJtRl9OOOXSJ1EkrungDvEWvylU(GxRecordingsPage.RoutePlayer var0, MediaPlayer var1) {
         _init_$lambda$9(var0, var1);
      }

      // $VF: synthetic method
      public static Unit $r8$lambda$ecJmboVyaNVtvLkSdO6cr7Iv6hc(GxRecordingsPage.RoutePlayer var0) {
         return _init_$lambda$21(var0);
      }

      // $VF: synthetic method
      public static Unit $r8$lambda$nm_G8DVqThNfzJUoTGMyYEB0bkk/* $VF was: $r8$lambda$nm_G8DVqThNfzJUoTGMyYEB0bkk*/(int var0) {
         return paintQual$lambda$7(var0);
      }

      // $VF: synthetic method
      public static Unit $r8$lambda$pkGFZaPukwCfaOTcJK51eLHJOK8(GxRecordingsPage.RoutePlayer var0) {
         return _init_$lambda$12(var0);
      }

      public RoutePlayer(GxRecordingsRoute var1, Long var2, Long var3) {
         Intrinsics.checkNotNullParameter(var2, "r");
         this.this$0 = var1;
         super();
         this.r = var2;
         this.sheet = this.this$0.new Sheet(this.this$0, (boolean)this.r.getDisplayName(), false);
         FrameLayout var5 = new FrameLayout((Context)this.this$0.getAct());
         var5.setBackgroundColor(-16777216);
         this.frame = var5;
         LinearLayout var6 = new LinearLayout((Context)this.this$0.getAct());
         var6.setOrientation(0);
         this.mid = var6;
         this.jumpTo = var3;
         this.tickPos = new Runnable(this, this.this$0) {
            final GxRecordingsPage.RoutePlayer this$0;
            final GxRecordingsPage this$1;

            {
               this.this$0 = var1;
               this.this$1 = var2x;
            }

            @Override
            public void run() {
               TrackMapView var4 = this.this$0.getMapView();
               if (var4 != null) {
                  Long var3x = this.this$0.getR().getStartedAtMs();
                  if (var3x != null) {
                     long var1x = var3x;
                     if (this.this$0.getSheet().getDialog().isShowing()) {
                        if (this.this$0.getVv().isPlaying()) {
                           var4.setNowMs(this.this$0.segNum(this.this$0.getCurrent()) * 60000L + var1x + this.this$0.getVv().getCurrentPosition());
                        }

                        this.this$1.getMain().postDelayed(this, 500L);
                     }
                  }
               }
            }
         };
         this.vv = new VideoView((Context)this.this$0.getAct());
         this.mc = new MediaController((Context)this.this$0.getAct());
         TextView var19 = new TextView((Context)this.this$0.getAct());
         var1 = this.this$0;
         var19.setTextColor(CarUi.Companion.getTEXT());
         var19.setTextSize(19.0F);
         var19.setGravity(17);
         var19.setPadding(var1.getUi().dp(20), var1.getUi().dp(12), var1.getUi().dp(20), var1.getUi().dp(12));
         var19.setBackground((Drawable)CarUi.rounded$default(var1.getUi(), Color.argb(190, 0, 0, 0), 12, null, 4, null));
         this.status = var19;
         LinearLayout var8 = new LinearLayout((Context)this.this$0.getAct());
         var8.setOrientation(1);
         var8.setVisibility(8);
         this.controls = var8;
         LinearLayout var9 = new LinearLayout((Context)this.this$0.getAct());
         var9.setOrientation(0);
         var9.setGravity(16);
         this.segBar = var9;
         LinearLayout var10 = new LinearLayout((Context)this.this$0.getAct());
         var10.setOrientation(0);
         var10.setGravity(16);
         this.camBox = var10;
         LinearLayout var11 = new LinearLayout((Context)this.this$0.getAct());
         var11.setOrientation(0);
         var11.setGravity(16);
         this.qualBox = var11;
         this.paintQual = new GxRecordingsPage$RoutePlayer$$ExternalSyntheticLambda18();
         this.segments = CollectionsKt.emptyList();
         this.cameras = CollectionsKt.emptyList();
         this.cam = "forward";
         this.frame.addView((View)this.vv, (LayoutParams)(new android.widget.FrameLayout.LayoutParams(-1, -1, 17)));
         this.frame.addView((View)this.status, (LayoutParams)(new android.widget.FrameLayout.LayoutParams(-2, -2, 17)));
         this.mid.addView((View)this.frame, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(0, -1, 1.0F)));
         LinearLayout var20 = this.sheet.getBody();
         View var24 = (View)this.mid;
         android.widget.LinearLayout.LayoutParams var12 = new android.widget.LinearLayout.LayoutParams(-1, 0, 1.0F);
         var12.bottomMargin = this.this$0.getUi().dp(10);
         Unit var4 = Unit.INSTANCE;
         var20.addView(var24, (LayoutParams)var12);
         this.mc.setAnchorView((View)this.frame);
         this.vv.setMediaController(this.mc);
         this.vv.setOnPreparedListener(new GxRecordingsPage$RoutePlayer$$ExternalSyntheticLambda23(this));
         this.vv.setOnErrorListener(new GxRecordingsPage$RoutePlayer$$ExternalSyntheticLambda1(this));
         this.vv.setOnCompletionListener(new GxRecordingsPage$RoutePlayer$$ExternalSyntheticLambda2(this));
         this.prev = CarUi.button$default(this.this$0.getUi(), "‹ Prev", false, new GxRecordingsPage$RoutePlayer$$ExternalSyntheticLambda3(this), 2, null);
         this.segLabel = CarUi.button$default(
            this.this$0.getUi(), "Segment", false, new GxRecordingsPage$RoutePlayer$$ExternalSyntheticLambda4(this.this$0, this), 2, null
         );
         this.next = CarUi.button$default(this.this$0.getUi(), "Next ›", false, new GxRecordingsPage$RoutePlayer$$ExternalSyntheticLambda5(this), 2, null);

         for (TextView var13 : CollectionsKt.listOf(new TextView[]{this.prev, this.segLabel, this.next})) {
            this.segBar.addView((View)var13, (LayoutParams)this.gap());
         }

         this.segBar
            .addView(new View((Context)this.this$0.getAct()), (LayoutParams)(new android.widget.LinearLayout.LayoutParams(this.this$0.getUi().dp(14), 1)));
         this.segBar.addView((View)this.camBox);
         this.segBar
            .addView(new View((Context)this.this$0.getAct()), (LayoutParams)(new android.widget.LinearLayout.LayoutParams(this.this$0.getUi().dp(14), 1)));
         this.segBar.addView((View)this.qualBox);
         LinearLayout var14 = this.controls;
         HorizontalScrollView var22 = new HorizontalScrollView((Context)this.this$0.getAct());
         var22.setHorizontalScrollBarEnabled(false);
         var22.addView((View)this.segBar);
         var14.addView((View)var22);
         LinearLayout var23 = new LinearLayout((Context)this.this$0.getAct());
         var1 = this.this$0;
         var23.setOrientation(0);
         var23.setPadding(0, var1.getUi().dp(8), 0, 0);
         var23.addView(
            (View)this.this$0.getUi().button("Download", true, new GxRecordingsPage$RoutePlayer$$ExternalSyntheticLambda6(this.this$0, this)),
            (LayoutParams)this.gap()
         );
         var23.addView(
            (View)CarUi.button$default(
               this.this$0.getUi(), "Logs", false, new GxRecordingsPage$RoutePlayer$$ExternalSyntheticLambda7(this.this$0, this), 2, null
            ),
            (LayoutParams)this.gap()
         );
         var23.addView(
            (View)CarUi.button$default(this.this$0.getUi(), "Map", false, new GxRecordingsPage$RoutePlayer$$ExternalSyntheticLambda8(this), 2, null),
            (LayoutParams)this.gap()
         );
         CarUi var25 = this.this$0.getUi();
         String var16;
         if (this.r.isPreserved()) {
            var16 = "Unpreserve";
         } else {
            var16 = "Preserve";
         }

         this.preserveBtn = CarUi.button$default(var25, var16, false, new GxRecordingsPage$RoutePlayer$$ExternalSyntheticLambda19(this.this$0, this), 2, null);
         var23.addView((View)this.preserveBtn, (LayoutParams)this.gap());
         var23.addView(
            (View)CarUi.button$default(
               this.this$0.getUi(), "Rename", false, new GxRecordingsPage$RoutePlayer$$ExternalSyntheticLambda20(this.this$0, this), 2, null
            ),
            (LayoutParams)this.gap()
         );
         TextView var17 = CarUi.button$default(
            this.this$0.getUi(), "Delete", false, new GxRecordingsPage$RoutePlayer$$ExternalSyntheticLambda21(this.this$0, this), 2, null
         );
         var17.setTextColor(GxRecordingsKt.access$getGxRecordingsDanger$p());
         var23.addView((View)var17, (LayoutParams)this.gap());
         LinearLayout var18 = this.controls;
         HorizontalScrollView var26 = new HorizontalScrollView((Context)this.this$0.getAct());
         var26.setHorizontalScrollBarEnabled(false);
         var26.addView((View)var23);
         var18.addView((View)var26);
         this.sheet.getBody().addView((View)this.controls);
         this.sheet.getDialog().setOnDismissListener(new GxRecordingsPage$RoutePlayer$$ExternalSyntheticLambda22(this, this.this$0));
         this.sheet.getDialog().show();
         this.fetch();
      }

      // $VF: synthetic method
      public RoutePlayer(GxRecordingsPage var1, GxRecordingsRoute var2, Long var3, int var4, DefaultConstructorMarker var5) {
         if ((var4 & 2) != 0) {
            var3 = null;
         }

         this(var1, var2, var3);
      }

      private static final boolean _init_$lambda$10(GxRecordingsPage.RoutePlayer var0, MediaPlayer var1, int var2, int var3) {
         if (var0.preview) {
            var0.preview = false;
            var0.paintQual.invoke(0);
            var0.play();
         } else {
            var0.setStatus("This segment could not be played.");
         }

         return true;
      }

      private static final void _init_$lambda$11(GxRecordingsPage.RoutePlayer var0, MediaPlayer var1) {
         if (var0.current < var0.segments.size() - 1) {
            var0.current++;
            var0.play();
         }
      }

      private static final Unit _init_$lambda$12(GxRecordingsPage.RoutePlayer var0) {
         if (var0.current > 0) {
            var0.current--;
            var0.play();
         }

         return Unit.INSTANCE;
      }

      private static final Unit _init_$lambda$15(GxRecordingsPage var0, GxRecordingsPage.RoutePlayer var1) {
         Iterable var5 = CollectionsKt.getIndices(var1.segments);
         var var4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var5, 10));
         Iterator var7 = var5.iterator();

         while (var7.hasNext()) {
            int var2 = ((IntIterator)var7).nextInt();
            int var3 = var1.segments.size();
            var4.add("Segment " + (var2 + 1) + " of " + var3);
         }

         List var6 = var4;
         var0.pick("Video segment", var6, new GxRecordingsPage$RoutePlayer$$ExternalSyntheticLambda12(var1));
         return Unit.INSTANCE;
      }

      private static final Unit _init_$lambda$16(GxRecordingsPage.RoutePlayer var0) {
         if (var0.current < var0.segments.size() - 1) {
            var0.current++;
            var0.play();
         }

         return Unit.INSTANCE;
      }

      private static final Unit _init_$lambda$19(GxRecordingsPage var0, GxRecordingsPage.RoutePlayer var1) {
         String var3 = var0.enc(var1.r.getName());
         String var2 = var0.enc(var1.cam);
         var2 = "/video/" + var3 + "/combined?camera=" + var2;
         var3 = var1.r.getDisplayName();
         String var4 = var1.cam;
         String var5 = GxRecordingsKt.access$gxRecordingsSafeName(var3 + "-" + var4);
         var0.download(var2, var5 + ".mp4", "video/mp4");
         return Unit.INSTANCE;
      }

      private static final Unit _init_$lambda$20(GxRecordingsPage var0, GxRecordingsPage.RoutePlayer var1) {
         var0.openLogs(var1.r);
         return Unit.INSTANCE;
      }

      private static final Unit _init_$lambda$21(GxRecordingsPage.RoutePlayer var0) {
         var0.toggleMap();
         return Unit.INSTANCE;
      }

      private static final Unit _init_$lambda$23(GxRecordingsPage var0, GxRecordingsPage.RoutePlayer var1) {
         var0.togglePreserved(var1.r, new GxRecordingsPage$RoutePlayer$$ExternalSyntheticLambda17(var1));
         return Unit.INSTANCE;
      }

      private static final Unit _init_$lambda$25(GxRecordingsPage var0, GxRecordingsPage.RoutePlayer var1) {
         var0.renameRoute(var1.r, new GxRecordingsPage$RoutePlayer$$ExternalSyntheticLambda15(var1));
         return Unit.INSTANCE;
      }

      private static final Unit _init_$lambda$27(GxRecordingsPage var0, GxRecordingsPage.RoutePlayer var1) {
         var0.deleteRoute(var1.r, new GxRecordingsPage$RoutePlayer$$ExternalSyntheticLambda11(var1));
         return Unit.INSTANCE;
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      private static final void _init_$lambda$31(GxRecordingsPage.RoutePlayer var0, GxRecordingsPage var1, DialogInterface var2) {
         var0.token++;
         var1.getMain().removeCallbacks(var0.tickPos);

         try {
            Result.Companion var6 = Result.Companion;
            var0.mc.hide();
            var0.vv.stopPlayback();
            KtCompat.resultOf(Unit.INSTANCE);
         } catch (Throwable var4) {
            Result.Companion var5 = Result.Companion;
            KtCompat.resultOf(ResultKt.createFailure(var4));
            return;
         }
      }

      private static final void _init_$lambda$9(GxRecordingsPage.RoutePlayer var0, MediaPlayer var1) {
         var0.status.setVisibility(8);
         if (var0.pendingSeekMs > 0) {
            var0.vv.seekTo(var0.pendingSeekMs);
            var0.pendingSeekMs = 0;
         }

         var0.vv.start();
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      private static final Unit fetch$lambda$48(GxRecordingsPage.RoutePlayer var0, GxRecordingsPage var1, int var2, String var3, String var4) {
         Intrinsics.checkNotNullParameter(var3, "text");
         if (!var0.sheet.getDialog().isShowing()) {
            return Unit.INSTANCE;
         } else if (var4 != null) {
            String var15;
            if (var2 == 404) {
               var15 = "This route is no longer available on this device. Its local video segments may have been deleted or moved.";
            } else {
               CharSequence var34 = var4;
               Object var16 = var34;
               if (StringsKt.isBlank(var34)) {
                  var16 = "Could not load route details (" + var2 + ").";
               }

               var15 = (String)var16;
            }

            var0.setStatus(var15);
            return Unit.INSTANCE;
         } else {
            label173:
            try {
               Result.Companion var35 = Result.Companion;
               JSONObject var36 = new JSONObject(var3);
               var20 = KtCompat.resultOf(var36);
            } catch (Throwable var11) {
               Result.Companion var19 = Result.Companion;
               var20 = KtCompat.resultOf(ResultKt.createFailure(var11));
               break label173;
            }

            JSONObject var37 = (JSONObject)var20;
            if (KtCompat.isFailure(var20)) {
               var37 = null;
            }

            var37 = var37;
            if (var37 == null) {
               var0.setStatus("Could not load route.");
               return Unit.INSTANCE;
            } else {
               label167: {
                  JSONArray var21 = var37.optJSONArray("segment_urls");
                  if (var21 != null) {
                     List var22 = var1.strings(var21);
                     if (var22 != null) {
                        Iterable var6 = var22;
                        var var24 = new ArrayList();

                        for (var var6_it : var6) {

                           var6 = var6_it;
                           if (!StringsKt.isBlank((String & CharSequence)var6)) {
                              var24.add(var6);
                           }
                        }

                        var23 = var24;
                        break label167;
                     }
                  }

                  var23 = CollectionsKt.emptyList();
               }

               label156: {
                  var0.segments = var23;
                  JSONArray var25 = var37.optJSONArray("available_cameras");
                  if (var25 != null) {
                     List var44 = var1.strings(var25);
                     var26 = var44;
                     if (var44 != null) {
                        break label156;
                     }
                  }

                  var26 = CollectionsKt.emptyList();
               }

               Iterable var47 = CollectionsKt.listOf(new String[]{"forward", "wide", "driver"});
               var var45 = new ArrayList();

               for (var var47_it : var47) {

                  var47 = var47_it;
                  if (var26.contains((String)var47)) {
                     var45.add(var47);
                  }
               }

               List var27 = var45;
               var0.cameras = var27;
               if (var0.segments.isEmpty()) {
                  var0.setStatus("No video segments for this route.");
                  return Unit.INSTANCE;
               } else if (var0.cameras.isEmpty()) {
                  var0.setStatus("No camera video for this route.");
                  return Unit.INSTANCE;
               } else {
                  if (var0.cameras.contains("forward")) {
                     var3 = "forward";
                  } else {
                     var3 = var0.cameras.get(0);
                  }

                  var0.cam = var3;
                  var0.current = 0;
                  Iterable var29 = var0.cameras;
                  var45 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var29, 10));

                  for (String var39 : var29) {
                     boolean var17;
                     if (var39.length() > 0) {
                        var17 = true;
                     } else {
                        var17 = false;
                     }

                     if (var17) {
                        StringBuilder var50 = new StringBuilder();
                        char var5 = var39.charAt(0);
                        String var9 = String.valueOf(var5);
                        Intrinsics.checkNotNull(var9, "null cannot be cast to non-null type java.lang.String");
                        var9 = var9.toUpperCase(Locale.ROOT);
                        Intrinsics.checkNotNullExpressionValue(var9, "toUpperCase(...)");
                        var50 = var50.append(var9);
                        String var40 = var39.substring(1);
                        Intrinsics.checkNotNullExpressionValue(var40, "substring(...)");
                        var39 = var50.append(var40).toString();
                     }

                     var45.add(var39);
                  }

                  List var30 = var45;
                  Pair var41 = var1.chipBar(var30, new GxRecordingsPage$RoutePlayer$$ExternalSyntheticLambda13(var0));
                  LinearLayout var31 = (LinearLayout)var41.component1();
                  Function1 var42 = (Function1)var41.component2();
                  var0.camBox.removeAllViews();
                  var0.camBox.addView((View)var31);
                  var42.invoke(var0.cameras.indexOf(var0.cam));
                  Pair var32 = var1.chipBar(
                     CollectionsKt.listOf(new String[]{"Full quality", "Preview"}), new GxRecordingsPage$RoutePlayer$$ExternalSyntheticLambda14(var0)
                  );
                  LinearLayout var12 = (LinearLayout)var32.component1();
                  Function1 var33 = (Function1)var32.component2();
                  var0.paintQual = var33;
                  var0.qualBox.removeAllViews();
                  var0.qualBox.addView((View)var12);
                  var33.invoke(0);
                  LinearLayout var13 = var0.qualBox;
                  byte var18;
                  if (Intrinsics.areEqual(var0.cam, "forward")) {
                     var18 = 0;
                  } else {
                     var18 = 8;
                  }

                  var13.setVisibility(var18);
                  var0.controls.setVisibility(0);
                  Long var14 = var0.jumpTo;
                  if (var14 != null) {
                     var0.jumpTo = null;
                     var0.jump(var14);
                     var0.toggleMap();
                  } else {
                     var0.play();
                  }

                  return Unit.INSTANCE;
               }
            }
         }
      }

      private static final Unit fetch$lambda$48$lambda$46(GxRecordingsPage.RoutePlayer var0, int var1, Function1 var2) {
         Intrinsics.checkNotNullParameter(var2, "p");
         var0.cam = var0.cameras.get(var1);
         var2.invoke(var1);
         LinearLayout var4 = var0.qualBox;
         byte var3;
         if (Intrinsics.areEqual(var0.cam, "forward")) {
            var3 = 0;
         } else {
            var3 = 8;
         }

         var4.setVisibility(var3);
         var0.play();
         return Unit.INSTANCE;
      }

      private static final Unit fetch$lambda$48$lambda$47(GxRecordingsPage.RoutePlayer var0, int var1, Function1 var2) {
         Intrinsics.checkNotNullParameter(var2, "p");
         boolean var3 = true;
         if (var1 != 1) {
            var3 = false;
         }

         var0.preview = var3;
         var2.invoke(var1);
         var0.play();
         return Unit.INSTANCE;
      }

      static final Unit lambda$15$lambda$14(GxRecordingsPage.RoutePlayer var0, int var1) {
         if (var1 != var0.current) {
            var0.current = var1;
            var0.play();
         }

         return Unit.INSTANCE;
      }

      static final Unit lambda$23$lambda$22(GxRecordingsPage.RoutePlayer var0) {
         TextView var1 = var0.preserveBtn;
         if (var1 != null) {
            String var2;
            if (var0.r.isPreserved()) {
               var2 = "Unpreserve";
            } else {
               var2 = "Preserve";
            }

            var1.setText(var2);
         }

         return Unit.INSTANCE;
      }

      static final Unit lambda$25$lambda$24(GxRecordingsPage.RoutePlayer var0) {
         var0.sheet.getTitle().setText(var0.r.getDisplayName());
         return Unit.INSTANCE;
      }

      static final Unit lambda$27$lambda$26(GxRecordingsPage.RoutePlayer var0) {
         var0.sheet.getDialog().dismiss();
         return Unit.INSTANCE;
      }

      private static final Unit paintQual$lambda$7(int var0) {
         return Unit.INSTANCE;
      }

      private static final Unit probe$lambda$51(int var0, GxRecordingsPage.RoutePlayer var1, String var2, int var3, GxRecordingsPage var4, int var5) {
         if (var0 == var1.token && var1.sheet.getDialog().isShowing()) {
            boolean var6;
            if (200 <= var5 && var5 < 300) {
               var6 = true;
            } else {
               var6 = false;
            }

            if (var6 || var5 == -1 || var5 == 405) {
               var1.vv.setVideoURI(Uri.parse(var2));
            } else if (var5 == 503 && var3 < 40) {
               var1.setStatus("Preparing video...");
               var4.getMain().postDelayed(new GxRecordingsPage$RoutePlayer$$ExternalSyntheticLambda9(var0, var1, var2, var3), 1500L);
            } else if (var1.preview) {
               var1.preview = false;
               var1.paintQual.invoke(0);
               var1.play();
            } else {
               var1.setStatus("This segment could not be played.");
            }

            return Unit.INSTANCE;
         } else {
            return Unit.INSTANCE;
         }
      }

      private static final void probe$lambda$51$lambda$50(int var0, GxRecordingsPage.RoutePlayer var1, String var2, int var3) {
         if (var0 == var1.token && var1.sheet.getDialog().isShowing()) {
            var1.probe(var2, var0, var3 + 1);
         }
      }

      private static final Unit toggleMap$lambda$35(GxRecordingsPage.RoutePlayer var0, long var1) {
         var0.jump(var1);
         return Unit.INSTANCE;
      }

      public final void fetch() {
         this.setStatus("Loading video...");
         GxPage var2 = this.this$0;
         String var1 = this.this$0.enc(this.r.getName());
         GxPage.call$default(
            var2, "GET", "/api/routes/" + var1, null, 0L, new GxRecordingsPage$RoutePlayer$$ExternalSyntheticLambda0(this, this.this$0), 12, null
         );
      }

      public final android.widget.LinearLayout.LayoutParams gap() {
         android.widget.LinearLayout.LayoutParams var1 = new android.widget.LinearLayout.LayoutParams(-2, -2);
         var1.rightMargin = this.this$0.getUi().dp(10);
         return var1;
      }

      public final String getCam() {
         return this.cam;
      }

      public final LinearLayout getCamBox() {
         return this.camBox;
      }

      public final List<String> getCameras() {
         return this.cameras;
      }

      public final LinearLayout getControls() {
         return this.controls;
      }

      public final int getCurrent() {
         return this.current;
      }

      public final FrameLayout getFrame() {
         return this.frame;
      }

      public final Long getJumpTo() {
         return this.jumpTo;
      }

      public final TrackMapView getMapView() {
         return this.mapView;
      }

      public final MediaController getMc() {
         return this.mc;
      }

      public final LinearLayout getMid() {
         return this.mid;
      }

      public final TextView getNext() {
         return this.next;
      }

      public final Function1<Integer, Unit> getPaintQual() {
         return this.paintQual;
      }

      public final int getPendingSeekMs() {
         return this.pendingSeekMs;
      }

      public final TextView getPreserveBtn() {
         return this.preserveBtn;
      }

      public final TextView getPrev() {
         return this.prev;
      }

      public final boolean getPreview() {
         return this.preview;
      }

      public final LinearLayout getQualBox() {
         return this.qualBox;
      }

      public final GxRecordingsRoute getR() {
         return this.r;
      }

      public final LinearLayout getSegBar() {
         return this.segBar;
      }

      public final TextView getSegLabel() {
         return this.segLabel;
      }

      public final List<String> getSegments() {
         return this.segments;
      }

      public final GxRecordingsPage.Sheet getSheet() {
         return this.sheet;
      }

      public final TextView getStatus() {
         return this.status;
      }

      public final Runnable getTickPos() {
         return this.tickPos;
      }

      public final int getToken() {
         return this.token;
      }

      public final VideoView getVv() {
         return this.vv;
      }

      public final void jump(long var1) {
         Long var11 = this.r.getStartedAtMs();
         if (var11 != null) {
            long var8 = var11;
            int var7 = (int)((var1 - var8) / 60000L);
            Iterator var13 = CollectionsKt.getIndices(this.segments).iterator();

            boolean var3;
            byte var5;
            Object var12;
            do {
               boolean var10 = var13.hasNext();
               var5 = 0;
               var12 = null;
               if (!var10) {
                  var18 = null;
                  break;
               }

               var18 = var13.next();
               if (this.segNum(((Number)var18).intValue()) == var7) {
                  var3 = 1;
               } else {
                  var3 = 0;
               }
            } while (!var3);

            Integer var19 = (Integer)var18;
            if (var19 != null) {
               var3 = var19;
            } else {
               Iterator var14 = CollectionsKt.getIndices(this.segments).iterator();
               Object var20;
               if (!var14.hasNext()) {
                  var20 = (Integer)var12;
               } else {
                  var20 = (Integer)var14.next();
                  if (var14.hasNext()) {
                     var3 = Math.abs(this.segNum(var20.intValue()) - var7);
                     var12 = var20;

                     do {
                        Object var23 = var14.next();
                        int var6 = Math.abs(this.segNum(((Number)var23).intValue()) - var7);
                        int var4 = var3;
                        var20 = (Integer)var12;
                        if (var3 > var6) {
                           var20 = (Integer)var23;
                           var4 = var6;
                        }

                        var3 = var4;
                        var12 = var20;
                     } while (var14.hasNext());
                  }
               }

               var20 = var20;
               if (var20 == null) {
                  return;
               }

               var3 = var20;
            }

            int var17 = var5;
            if (this.segNum(var3) == var7) {
               var17 = (int)((var1 - var8) % 60000L);
            }

            this.pendingSeekMs = var17;
            this.current = var3;
            this.play();
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      public final void play() {
         String var6 = CollectionsKt.getOrNull(this.segments, this.current);
         if (var6 != null) {
            this.token++;
            int var3 = this.token;
            TextView var7 = this.segLabel;
            int var4 = this.current;
            int var5 = this.segments.size();
            var7.setText("Segment " + (var4 + 1) + " of " + var5);
            var7 = this.prev;
            var4 = this.current;
            float var2 = 1.0F;
            float var1;
            if (var4 > 0) {
               var1 = 1.0F;
            } else {
               var1 = 0.4F;
            }

            var7.setAlpha(var1);
            var7 = this.next;
            if (this.current < this.segments.size() - 1) {
               var1 = var2;
            } else {
               var1 = 0.4F;
            }

            var7.setAlpha(var1);

            label38:
            try {
               Result.Companion var15 = Result.Companion;
               this.vv.stopPlayback();
               KtCompat.resultOf(Unit.INSTANCE);
            } catch (Throwable var10) {
               Result.Companion var8 = Result.Companion;
               KtCompat.resultOf(ResultKt.createFailure(var10));
               break label38;
            }

            this.setStatus("Loading video...");
            this.probe(this.this$0.url(this.videoPath(var6)), var3, 0);
         }
      }

      public final void probe(String var1, int var2, int var3) {
         Intrinsics.checkNotNullParameter(var1, "u");
         this.this$0.head(var1, new GxRecordingsPage$RoutePlayer$$ExternalSyntheticLambda10(var2, this, var1, var3, this.this$0));
      }

      public final int segNum(int var1) {
         String var3 = CollectionsKt.getOrNull(this.segments, var1);
         if (var3 != null) {
            Regex var2 = new Regex("--(\\d+)(?=[/?]|$)");
            CharSequence var4 = var3;
            var3 = null;
            MatchResult var7 = SequencesKt.lastOrNull(Regex.findAll$default(var2, var4, 0, 2, null));
            Integer var5 = var3;
            if (var7 != null) {
               List var8 = var7.getGroupValues();
               var5 = var3;
               if (var8 != null) {
                  String var9 = (String)var8.get(1);
                  var5 = var3;
                  if (var9 != null) {
                     var5 = StringsKt.toIntOrNull(var9);
                  }
               }
            }

            if (var5 != null) {
               var1 = var5;
            }
         }

         return var1;
      }

      public final void setCam(String var1) {
         Intrinsics.checkNotNullParameter(var1, "<set-?>");
         this.cam = var1;
      }

      public final void setCameras(List<String> var1) {
         Intrinsics.checkNotNullParameter(var1, "<set-?>");
         this.cameras = var1;
      }

      public final void setCurrent(int var1) {
         this.current = var1;
      }

      public final void setJumpTo(Long var1) {
         this.jumpTo = var1;
      }

      public final void setMapView(TrackMapView var1) {
         this.mapView = var1;
      }

      public final void setPaintQual(Function1<? super Integer, Unit> var1) {
         Intrinsics.checkNotNullParameter(var1, "<set-?>");
         this.paintQual = var1;
      }

      public final void setPendingSeekMs(int var1) {
         this.pendingSeekMs = var1;
      }

      public final void setPreserveBtn(TextView var1) {
         this.preserveBtn = var1;
      }

      public final void setPreview(boolean var1) {
         this.preview = var1;
      }

      public final void setSegments(List<String> var1) {
         Intrinsics.checkNotNullParameter(var1, "<set-?>");
         this.segments = var1;
      }

      public final void setStatus(String var1) {
         Intrinsics.checkNotNullParameter(var1, "s");
         this.status.setText(var1);
         this.status.setVisibility(0);
      }

      public final void setToken(int var1) {
         this.token = var1;
      }

      public final void toggleMap() {
         TrackMapView var3 = this.mapView;
         if (var3 != null) {
            this.mid.removeView(var3);
            this.mapView = null;
         } else {
            List var4 = this.this$0.routeTrack(this.r);
            if (var4 != null) {
               Long var7 = this.r.getStartedAtMs();
               if (var7 != null) {
                  long var1 = var7;
                  TrackMapView var9 = new TrackMapView(
                     (Context)this.this$0.getAct(), var4, var1, new GxRecordingsPage$RoutePlayer$$ExternalSyntheticLambda16(this)
                  );
                  this.mapView = var9;
                  LinearLayout var8 = this.mid;
                  View var10 = var9;
                  android.widget.LinearLayout.LayoutParams var5 = new android.widget.LinearLayout.LayoutParams(0, -1, 1.0F);
                  var5.leftMargin = this.this$0.getUi().dp(8);
                  Unit var6 = Unit.INSTANCE;
                  var8.addView(var10, (LayoutParams)var5);
                  this.this$0.getMain().removeCallbacks(this.tickPos);
                  this.this$0.getMain().post(this.tickPos);
               }
            }
         }
      }

      public final String videoPath(String var1) {
         Intrinsics.checkNotNullParameter(var1, "seg");
         ArrayList var3 = new ArrayList();
         if (!Intrinsics.areEqual(this.cam, "forward")) {
            String var2 = this.this$0.enc(this.cam);
            var3.add("camera=" + var2);
         }

         if (this.preview && Intrinsics.areEqual(this.cam, "forward")) {
            var3.add("quality=low");
         }

         if (var3.isEmpty()) {
            return var1;
         } else {
            CharSequence var4 = var1;
            String var5 = "?";
            if (StringsKt.contains$default(var4, "?", false, 2, null)) {
               var5 = "&";
            }

            String var6 = CollectionsKt.joinToString$default(var3, "&", null, null, 0, null, null, 62, null);
            return var1 + var5 + var6;
         }
      }
   }

   private final class ScreenPlayer {
      private final FrameLayout frame;
      private final MediaController mc;
      private GxRecordingsScreen rec;
      private final GxRecordingsPage.Sheet sheet;
      private final TextView status;
      final GxRecordingsPage this$0;
      private final VideoView vv;

      // $VF: synthetic method
      public static Unit $r8$lambda$6ZQzFot1YNj1XcBA3qdzWDRy7cM(GxRecordingsPage var0, GxRecordingsPage.ScreenPlayer var1) {
         return _init_$lambda$10(var0, var1);
      }

      // $VF: synthetic method
      public static android.widget.LinearLayout.LayoutParams $r8$lambda$OMqEHYRSYkLZ0DngzeU5_49NtxU/* $VF was: $r8$lambda$OMqEHYRSYkLZ0DngzeU5_49NtxU*/(
         GxRecordingsPage var0
      ) {
         return _init_$lambda$7(var0);
      }

      // $VF: synthetic method
      public static void $r8$lambda$b5RzStEu_xFOagPfp495ISIjjdA(GxRecordingsPage.ScreenPlayer var0, MediaPlayer var1) {
         _init_$lambda$3(var0, var1);
      }

      // $VF: synthetic method
      public static Unit $r8$lambda$cfz6RziSXe66Ih81KSAyiAzillo(GxRecordingsPage var0, GxRecordingsPage.ScreenPlayer var1) {
         return _init_$lambda$8(var0, var1);
      }

      // $VF: synthetic method
      public static boolean $r8$lambda$j31uUbm8TCKiru4MRhMvLyc5nZI(GxRecordingsPage.ScreenPlayer var0, MediaPlayer var1, int var2, int var3) {
         return _init_$lambda$4(var0, var1, var2, var3);
      }

      // $VF: synthetic method
      public static Unit $r8$lambda$wWmAYV_0y53y1Aqrre43dSkBErQ(GxRecordingsPage var0, GxRecordingsPage.ScreenPlayer var1) {
         return _init_$lambda$12(var0, var1);
      }

      // $VF: synthetic method
      public static void $r8$lambda$w_Q9T6JE94H9zJM92vbflBx3Wk0(GxRecordingsPage.ScreenPlayer var0, DialogInterface var1) {
         _init_$lambda$16(var0, var1);
      }

      public ScreenPlayer(GxRecordingsScreen var1, GxRecordingsScreen var2) {
         Intrinsics.checkNotNullParameter(var2, "rec");
         this.this$0 = var1;
         super();
         this.rec = var2;
         this.sheet = this.this$0.new Sheet(this.this$0, (boolean)this.this$0.screenName(this.rec), false);
         FrameLayout var5 = new FrameLayout((Context)this.this$0.getAct());
         var5.setBackgroundColor(-16777216);
         this.frame = var5;
         this.vv = new VideoView((Context)this.this$0.getAct());
         this.mc = new MediaController((Context)this.this$0.getAct());
         TextView var6 = new TextView((Context)this.this$0.getAct());
         GxRecordingsPage var9 = this.this$0;
         var6.setTextColor(CarUi.Companion.getTEXT());
         var6.setTextSize(19.0F);
         var6.setGravity(17);
         var6.setPadding(var9.getUi().dp(20), var9.getUi().dp(12), var9.getUi().dp(20), var9.getUi().dp(12));
         var6.setBackground((Drawable)CarUi.rounded$default(var9.getUi(), Color.argb(190, 0, 0, 0), 12, null, 4, null));
         var6.setText("Loading video...");
         this.status = var6;
         this.frame.addView((View)this.vv, (LayoutParams)(new android.widget.FrameLayout.LayoutParams(-1, -1, 17)));
         this.frame.addView((View)this.status, (LayoutParams)(new android.widget.FrameLayout.LayoutParams(-2, -2, 17)));
         LinearLayout var10 = this.sheet.getBody();
         View var3 = (View)this.frame;
         android.widget.LinearLayout.LayoutParams var7 = new android.widget.LinearLayout.LayoutParams(-1, 0, 1.0F);
         var7.bottomMargin = this.this$0.getUi().dp(10);
         Unit var4 = Unit.INSTANCE;
         var10.addView(var3, (LayoutParams)var7);
         this.mc.setAnchorView((View)this.frame);
         this.vv.setMediaController(this.mc);
         this.vv.setOnPreparedListener(new GxRecordingsPage$ScreenPlayer$$ExternalSyntheticLambda1(this));
         this.vv.setOnErrorListener(new GxRecordingsPage$ScreenPlayer$$ExternalSyntheticLambda2(this));
         LinearLayout var8 = new LinearLayout((Context)this.this$0.getAct());
         var8.setOrientation(0);
         GxRecordingsPage$ScreenPlayer$$ExternalSyntheticLambda3 var11 = new GxRecordingsPage$ScreenPlayer$$ExternalSyntheticLambda3(this.this$0);
         var8.addView(
            (View)this.this$0.getUi().button("Download", true, new GxRecordingsPage$ScreenPlayer$$ExternalSyntheticLambda4(this.this$0, this)),
            (LayoutParams)var11.invoke()
         );
         var8.addView(
            (View)CarUi.button$default(
               this.this$0.getUi(), "Rename", false, new GxRecordingsPage$ScreenPlayer$$ExternalSyntheticLambda5(this.this$0, this), 2, null
            ),
            (LayoutParams)var11.invoke()
         );
         TextView var13 = CarUi.button$default(
            this.this$0.getUi(), "Delete", false, new GxRecordingsPage$ScreenPlayer$$ExternalSyntheticLambda6(this.this$0, this), 2, null
         );
         var13.setTextColor(GxRecordingsKt.access$getGxRecordingsDanger$p());
         var8.addView((View)var13, (LayoutParams)var11.invoke());
         LinearLayout var12 = this.sheet.getBody();
         HorizontalScrollView var14 = new HorizontalScrollView((Context)this.this$0.getAct());
         var14.setHorizontalScrollBarEnabled(false);
         var14.addView((View)var8);
         var12.addView((View)var14);
         this.sheet.getDialog().setOnDismissListener(new GxRecordingsPage$ScreenPlayer$$ExternalSyntheticLambda7(this));
         this.sheet.getDialog().show();
         this.vv.setVideoURI(Uri.parse(this.this$0.url(this.this$0.screenVideoPath(this.rec))));
      }

      private static final Unit _init_$lambda$10(GxRecordingsPage var0, GxRecordingsPage.ScreenPlayer var1) {
         var0.renameScreen(var1.rec, new GxRecordingsPage$ScreenPlayer$$ExternalSyntheticLambda0(var1));
         return Unit.INSTANCE;
      }

      private static final Unit _init_$lambda$12(GxRecordingsPage var0, GxRecordingsPage.ScreenPlayer var1) {
         var0.deleteScreen(var1.rec, new GxRecordingsPage$ScreenPlayer$$ExternalSyntheticLambda8(var1));
         return Unit.INSTANCE;
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      private static final void _init_$lambda$16(GxRecordingsPage.ScreenPlayer var0, DialogInterface var1) {
         try {
            Result.Companion var5 = Result.Companion;
            var0.mc.hide();
            var0.vv.stopPlayback();
            KtCompat.resultOf(Unit.INSTANCE);
         } catch (Throwable var3) {
            Result.Companion var4 = Result.Companion;
            KtCompat.resultOf(ResultKt.createFailure(var3));
            return;
         }
      }

      private static final void _init_$lambda$3(GxRecordingsPage.ScreenPlayer var0, MediaPlayer var1) {
         var0.status.setVisibility(8);
         var0.vv.start();
      }

      private static final boolean _init_$lambda$4(GxRecordingsPage.ScreenPlayer var0, MediaPlayer var1, int var2, int var3) {
         var0.status.setText("This recording could not be played.");
         var0.status.setVisibility(0);
         return true;
      }

      private static final android.widget.LinearLayout.LayoutParams _init_$lambda$7(GxRecordingsPage var0) {
         android.widget.LinearLayout.LayoutParams var1 = new android.widget.LinearLayout.LayoutParams(-2, -2);
         var1.rightMargin = var0.getUi().dp(10);
         return var1;
      }

      private static final Unit _init_$lambda$8(GxRecordingsPage var0, GxRecordingsPage.ScreenPlayer var1) {
         var0.downloadScreen(var1.rec);
         return Unit.INSTANCE;
      }

      static final Unit lambda$10$lambda$9(GxRecordingsPage.ScreenPlayer var0) {
         var0.sheet.getDialog().dismiss();
         return Unit.INSTANCE;
      }

      static final Unit lambda$12$lambda$11(GxRecordingsPage.ScreenPlayer var0) {
         var0.sheet.getDialog().dismiss();
         return Unit.INSTANCE;
      }

      public final FrameLayout getFrame() {
         return this.frame;
      }

      public final MediaController getMc() {
         return this.mc;
      }

      public final GxRecordingsScreen getRec() {
         return this.rec;
      }

      public final GxRecordingsPage.Sheet getSheet() {
         return this.sheet;
      }

      public final TextView getStatus() {
         return this.status;
      }

      public final VideoView getVv() {
         return this.vv;
      }

      public final void setRec(GxRecordingsScreen var1) {
         Intrinsics.checkNotNullParameter(var1, "<set-?>");
         this.rec = var1;
      }
   }

   private final class Sheet {
      private final LinearLayout body;
      private final Dialog dialog;
      final GxRecordingsPage this$0;
      private final TextView title;

      // $VF: synthetic method
      public static Unit $r8$lambda$Ac_SuyAFxcvOstkdqntpQW97ykA/* $VF was: $r8$lambda$Ac_SuyAFxcvOstkdqntpQW97ykA*/(GxRecordingsPage.Sheet var0) {
         return _init_$lambda$4(var0);
      }

      public Sheet(String var1, boolean var2, boolean var3) {
         Intrinsics.checkNotNullParameter(var2, "titleText");
         this.this$0 = var1;
         super();
         this.dialog = new Dialog((Context)this.this$0.getAct(), 16973834);
         LinearLayout var6 = new LinearLayout((Context)this.this$0.getAct());
         var6.setOrientation(1);
         this.body = var6;
         LinearLayout var7 = new LinearLayout((Context)this.this$0.getAct());
         GxRecordingsPage var4 = this.this$0;
         var7.setOrientation(1);
         var7.setBackgroundColor(CarUi.Companion.getBG());
         var7.setPadding(var4.getUi().dp(20), var4.getUi().dp(14), var4.getUi().dp(20), var4.getUi().dp(16));
         LinearLayout var9 = new LinearLayout((Context)this.this$0.getAct());
         GxRecordingsPage var5 = this.this$0;
         var9.setOrientation(0);
         var9.setGravity(16);
         var9.setPadding(0, 0, 0, var5.getUi().dp(12));
         TextView var10 = new TextView((Context)this.this$0.getAct());
         var10.setText(var2);
         var10.setTextColor(CarUi.Companion.getTEXT());
         var10.setTextSize(24.0F);
         var10.setTypeface(Typeface.DEFAULT_BOLD);
         var10.setMaxLines(2);
         this.title = var10;
         var9.addView((View)this.title, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(0, -2, 1.0F)));
         var9.addView((View)CarUi.button$default(this.this$0.getUi(), "Close", false, new GxRecordingsPage$Sheet$$ExternalSyntheticLambda0(this), 2, null));
         var7.addView((View)var9);
         if (var3) {
            NestedScrollView var8 = new NestedScrollView((Context)this.this$0.getAct());
            var8.addView((View)this.body, new LayoutParams(-1, -2));
            var7.addView((View)var8, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(-1, 0, 1.0F)));
         } else {
            var7.addView((View)this.body, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(-1, 0, 1.0F)));
         }

         this.dialog.setContentView((View)var7);
      }

      private static final Unit _init_$lambda$4(GxRecordingsPage.Sheet var0) {
         var0.dialog.dismiss();
         return Unit.INSTANCE;
      }

      public final LinearLayout getBody() {
         return this.body;
      }

      public final Dialog getDialog() {
         return this.dialog;
      }

      public final TextView getTitle() {
         return this.title;
      }
   }

   private static final class ThumbJob {
      private final ImageView iv;
      private final String path;
      private final View ph;
      private final Function0<Boolean> valid;

      public ThumbJob(String var1, ImageView var2, View var3, Function0<Boolean> var4) {
         Intrinsics.checkNotNullParameter(var1, "path");
         Intrinsics.checkNotNullParameter(var2, "iv");
         Intrinsics.checkNotNullParameter(var3, "ph");
         Intrinsics.checkNotNullParameter(var4, "valid");
         super();
         this.path = var1;
         this.iv = var2;
         this.ph = var3;
         this.valid = var4;
      }

      public final ImageView getIv() {
         return this.iv;
      }

      public final String getPath() {
         return this.path;
      }

      public final View getPh() {
         return this.ph;
      }

      public final Function0<Boolean> getValid() {
         return this.valid;
      }
   }
}
