package com.starpilot.starview;

import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.content.ContentValues;
import android.content.Context;
import android.content.MutableContextWrapper;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore.Downloads;
import android.util.Base64;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import kotlin.Unit;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.StringsKt;
import org.json.JSONObject;

public final class GalaxyWebPool {
   public static final GalaxyWebPool INSTANCE = new GalaxyWebPool();
   public static final String QUIET = "/tools";
   private static String lastPath;
   private static String loadedHost;
   private static final Handler main = new Handler(Looper.getMainLooper());
   private static String pendingPath;
   private static volatile boolean ready;
   private static final ArrayList<Function0<Unit>> waiting = new ArrayList<>();
   private static WebView web;
   private static MutableContextWrapper wrapper;

   // $VF: synthetic method
   public static void $r8$lambda$ZqhZ6DAdkhS_n49NwmcIyjjdOFs(GalaxyWebPool var0, WebView var1, String var2, String var3, String var4, String var5, long var6) {
      get$lambda$4$lambda$2$lambda$1(var0, var1, var2, var3, var4, var5, var6);
   }

   // $VF: synthetic method
   public static void $r8$lambda$ZwJUsdKXP7alHEIsR0JOXge_d5U(WebView var0, int var1) {
      pollReady$lambda$11$lambda$10(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$_DolRg3an_tbFQ0HE_AFz7zkl6Q/* $VF was: $r8$lambda$_DolRg3an_tbFQ0HE_AFz7zkl6Q*/(int var0, WebView var1, String var2) {
      pollReady$lambda$11(var0, var1, var2);
   }

   private GalaxyWebPool() {
   }

   private final String base(String var1) {
      return "http://" + var1 + ":8082/";
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   private final void download(WebView var1, String var2, String var3, String var4) {
      Context var7 = var1.getContext();
      var3 = URLUtil.guessFileName(var2, var3, var4);
      Intrinsics.checkNotNull(var3);
      if (StringsKt.startsWith$default(var3, "downloadfile", false, 2, null)) {
         long var5;
         label44: {
            var5 = System.currentTimeMillis();
            var3 = MimeTypeMap.getSingleton().getExtensionFromMimeType(var4);
            if (var3 != null) {
               var4 = "." + var3;
               var3 = var4;
               if (var4 != null) {
                  break label44;
               }
            }

            var3 = "";
         }

         var3 = "galaxy-" + var5 + var3;
      }

      if (StringsKt.startsWith$default(var2, "blob:", false, 2, null)) {
         var2 = JSONObject.quote(var2);
         var3 = JSONObject.quote(var3);
         var1.evaluateJavascript(
            "(function(){fetch("
               + var2
               + ").then(r=>r.blob()).then(b=>{var f=new FileReader();f.onload=function(){StarViewSave.save("
               + var3
               + ",f.result.split(',')[1]||'')};f.readAsDataURL(b)})})();",
            null
         );
      } else {
         try {
            Request var14 = new Request(Uri.parse(var2));
            Request var15 = var14.setTitle(var3).setDescription("From Galaxy").setNotificationVisibility(1);
            String var8 = Environment.DIRECTORY_DOWNLOADS;
            StringBuilder var29 = new StringBuilder();
            var16 = var15.setDestinationInExternalPublicDir(var8, var29.append("StarView/").append(var3).toString());
         } catch (Exception var12) {
            String var13 = var12.getMessage();
            Toast.makeText(var7, "Download failed: " + var13, 1).show();
            return;
         }

         try {
            var2 = CookieManager.getInstance().getCookie(var2);
         } catch (Exception var11) {
            String var17 = var11.getMessage();
            Toast.makeText(var7, "Download failed: " + var17, 1).show();
            return;
         }

         if (var2 != null) {
            try {
               var16.addRequestHeader("Cookie", var2);
            } catch (Exception var10) {
               String var18 = var10.getMessage();
               Toast.makeText(var7, "Download failed: " + var18, 1).show();
               return;
            }
         }

         try {
            Object var22 = var7.getSystemService("download");
            Intrinsics.checkNotNull(var22, "null cannot be cast to non-null type android.app.DownloadManager");
            ((DownloadManager)var22).enqueue(var16);
            StringBuilder var20 = new StringBuilder();
            Toast.makeText(var7, var20.append("Saving ").append(var3).append(" to Download/StarView").toString(), 1).show();
         } catch (Exception var9) {
            String var19 = var9.getMessage();
            Toast.makeText(var7, "Download failed: " + var19, 1).show();
         }
      }
   }

   private static final void get$lambda$4$lambda$2$lambda$1(GalaxyWebPool var0, WebView var1, String var2, String var3, String var4, String var5, long var6) {
      Intrinsics.checkNotNull(var2);
      var0.download(var1, var2, var4, var5);
   }

   private final String hex(int var1) {
      StringCompanionObject var2 = StringCompanionObject.INSTANCE;
      String var3 = String.format("#%06X", Arrays.copyOf(new Object[]{16777215 & var1}, 1));
      Intrinsics.checkNotNullExpressionValue(var3, "format(...)");
      return var3;
   }

   private final void inject(WebView var1) {
      String var2 = StringsKt.replace$default(StringsKt.replace$default(this.getCSS(), "\\", "\\\\", false, 4, null), "`", "\\`", false, 4, null);
      var1.evaluateJavascript(
         "(function(){try{localStorage.setItem('galaxy-preview-theme','dark');}catch(e){}\n      var s=document.getElementById('starview-theme');if(!s){s=document.createElement('style');s.id='starview-theme';(document.head||document.documentElement).appendChild(s);}\n      s.textContent=`"
            + var2
            + "`;})();",
         null
      );
   }

   private final void pollReady(WebView var1, int var2) {
      var1.evaluateJavascript(
         "(function(){var b=document.getElementById('galaxy-boot');return !!document.querySelector('.gx-content')&&(!b||!b.isConnected||b.hidden);})()",
         new GalaxyWebPool$$ExternalSyntheticLambda2(var2, var1)
      );
   }

   private static final void pollReady$lambda$11(int var0, WebView var1, String var2) {
      if (!Intrinsics.areEqual(var2, "true") && var0 < 60) {
         main.postDelayed(new GalaxyWebPool$$ExternalSyntheticLambda1(var1, var0), 100L);
      } else {
         ready = true;
         String var3 = pendingPath;
         if (var3 != null) {
            pendingPath = null;
            INSTANCE.go(var3);
         }

         ArrayList var4 = new ArrayList<>(waiting);
         waiting.clear();
         Iterator var5 = var4.iterator();

         while (var5.hasNext()) {
            ((Function0)var5.next()).invoke();
         }
      }
   }

   private static final void pollReady$lambda$11$lambda$10(WebView var0, int var1) {
      INSTANCE.pollReady(var0, var1 + 1);
   }

   public final WebView get(Context var1, String var2) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      Intrinsics.checkNotNullParameter(var2, "host");
      WebView var4 = web;
      WebView var3 = var4;
      if (var4 == null) {
         GalaxyWebPool var7 = this;
         MutableContextWrapper var5 = new MutableContextWrapper(var1);
         wrapper = var5;
         var3 = new WebView((Context)var5);
         var3.setBackgroundColor(CarUi.Companion.getFRAME());
         var3.getSettings().setJavaScriptEnabled(true);
         var3.getSettings().setDomStorageEnabled(true);
         var3.getSettings().setMediaPlaybackRequiresUserGesture(false);
         var3.setWebChromeClient(new WebChromeClient());
         var3.addJavascriptInterface(new GalaxyWebPool.SaveBridge((Context)var5), "StarViewSave");
         var3.setDownloadListener(new GalaxyWebPool$$ExternalSyntheticLambda0(var7, var3));
         var3.setWebViewClient(new WebViewClient(var7) {
            final GalaxyWebPool $this_run;

            {
               this.$this_run = var1;
            }

            public void onPageFinished(WebView var1, String var2x) {
               Intrinsics.checkNotNullParameter(var1, "view");
               this.$this_run.inject(var1);
               this.$this_run.pollReady(var1, 0);
            }

            public void onPageStarted(WebView var1, String var2x, Bitmap var3x) {
               Intrinsics.checkNotNullParameter(var1, "view");
               GalaxyWebPool.ready = false;
               this.$this_run.inject(var1);
            }

            public boolean shouldOverrideUrlLoading(WebView var1, WebResourceRequest var2x) {
               Intrinsics.checkNotNullParameter(var1, "view");
               Intrinsics.checkNotNullParameter(var2x, "request");
               String var4x = var2x.getUrl().toString();
               Intrinsics.checkNotNullExpressionValue(var4x, "toString(...)");
               GalaxyWebPool var3x = this.$this_run;
               String var6 = GalaxyWebPool.loadedHost;
               String var5x = var6;
               if (var6 == null) {
                  var5x = "";
               }

               return StringsKt.startsWith$default(var4x, var3x.base(var5x), false, 2, null) ^ true;
            }
         });
         web = var3;
      }

      MutableContextWrapper var8 = wrapper;
      if (var8 != null) {
         var8.setBaseContext(var1);
      }

      if (!Intrinsics.areEqual(loadedHost, var2)) {
         loadedHost = var2;
         ready = false;
         String var6 = this.base(var2);
         var3.loadUrl(var6 + "#/tools");
      }

      return var3;
   }

   public final String getCSS() {
      String var10 = this.hex(CarUi.Companion.getACCENT());
      String var9 = this.hex(CarUi.Companion.getACCENT());
      String var8 = this.hex(CarUi.Companion.getBG());
      String var7 = this.hex(CarUi.Companion.getACCENT());
      String var6 = this.hex(CarUi.Companion.getACCENT());
      String var1 = this.hex(CarUi.Companion.getWARN());
      String var4 = this.hex(CarUi.Companion.getFRAME());
      String var11 = this.hex(CarUi.Companion.getCARD());
      String var14 = this.hex(CarUi.Companion.getCARD());
      String var12 = this.hex(CarUi.Companion.getCARD_ON());
      String var13 = this.hex(CarUi.Companion.getDIM());
      String var5 = this.hex(CarUi.Companion.getDIM());
      String var2 = this.hex(CarUi.Companion.getCARD());
      String var15 = this.hex(CarUi.Companion.getCARD_ON());
      String var3 = this.hex(CarUi.Companion.getFRAME());
      return StringsKt.trimIndent(
         "\n    :root, [data-theme=\"light\"] { color-scheme: dark;\n      --primary: "
            + var10
            + "; --primary-hover: "
            + var9
            + "; --primary-glow: rgba(111,227,161,.25); --on-primary: "
            + var8
            + ";\n      --primary-container: rgba(111,227,161,.16); --on-primary-container: #d8ffe9;\n      --secondary: "
            + var7
            + "; --secondary-glow: rgba(111,227,161,.2); --success: "
            + var6
            + "; --warning: "
            + var1
            + ";\n      --background: "
            + var4
            + "; --surface: "
            + var11
            + "; --surface-container: "
            + var14
            + "; --surface-container-high: "
            + var12
            + ";\n      --on-surface: #FFFFFF; --on-surface-variant: "
            + var13
            + "; --text-muted: "
            + var5
            + ";\n      --glass-bg: "
            + var2
            + "; --glass-bg-hover: "
            + var15
            + "; --glass-active-bg: rgba(111,227,161,.18); --glass-border: #3C424E;\n      --glass-shadow: none; --glass-blur: none; --glass-text: #FFFFFF; --outline: #323742; --outline-variant: #262A33;\n      --font-body: Roboto, \"Noto Sans\", sans-serif; --radius-sm: 14px; --radius-md: 14px; --radius-lg: 18px;\n      --scrollbar-thumb: #3A404C; --scrollbar-thumb-hover: #4A515E; --scrollbar-border: transparent;\n      --bottomnav-height: 0px; --content-width: 100%; }\n    html, body { background: "
            + var3
            + " !important; font-size: 17px; }\n    #galaxy-bg, .gx-appbar, .gx-drawer, .gx-underlay, .blur-nav, .gx-nav-indicator, #galaxy-boot, .gx-boot { display: none !important; }\n    * { -webkit-backdrop-filter: none !important; backdrop-filter: none !important; }\n    .gx-content { max-width: none !important; margin-top: 0 !important; padding-top: 4px !important; }\n  "
      );
   }

   public final boolean getReady() {
      return ready;
   }

   public final WebView getWeb() {
      return web;
   }

   public final void go(String var1) {
      Intrinsics.checkNotNullParameter(var1, "path");
      WebView var2 = web;
      if (var2 != null) {
         lastPath = var1;
         if (!ready) {
            pendingPath = var1;
         } else {
            var1 = JSONObject.quote(var1);
            var2.evaluateJavascript("(function(){var p=" + var1 + ";if(location.hash.slice(1)!==p){location.hash=p;}})();", null);
         }
      }
   }

   public final void park() {
      WebView var1 = web;
      if (var1 != null) {
         if (!ready) {
            pendingPath = "/tools";
         } else {
            var1.evaluateJavascript("(function(){if(location.hash.slice(1)!=='/tools'){location.hash='/tools';}})();", null);
         }
      }
   }

   public final void reload() {
      ready = false;
      WebView var1 = web;
      if (var1 != null) {
         var1.reload();
      }
   }

   public final void warm(Context var1, String var2) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      Intrinsics.checkNotNullParameter(var2, "host");
      this.get(var1, var2);
   }

   public final void whenReady(Function0<Unit> var1) {
      Intrinsics.checkNotNullParameter(var1, "fn");
      if (ready) {
         var1.invoke();
      } else {
         waiting.add(var1);
      }
   }

   public static final class SaveBridge {
      private final Context ctx;

      // $VF: synthetic method
      public static void $r8$lambda$4noD2yKRPrWmdQGTCvqnL_ubrUs(GalaxyWebPool.SaveBridge var0, Exception var1) {
         save$lambda$3(var0, var1);
      }

      // $VF: synthetic method
      public static void $r8$lambda$IuV52B5GK3kiVx0pjDrOVaH5Zqw(GalaxyWebPool.SaveBridge var0, String var1) {
         save$lambda$2(var0, var1);
      }

      public SaveBridge(Context var1) {
         Intrinsics.checkNotNullParameter(var1, "ctx");
         super();
         this.ctx = var1;
      }

      private static final void save$lambda$2(GalaxyWebPool.SaveBridge var0, String var1) {
         Toast.makeText(var0.ctx, "Saved " + var1 + " to Download/StarView", 1).show();
      }

      private static final void save$lambda$3(GalaxyWebPool.SaveBridge var0, Exception var1) {
         Context var2 = var0.ctx;
         String var3 = var1.getMessage();
         Toast.makeText(var2, "Save failed: " + var3, 1).show();
      }

      // $VF: Duplicated exception handlers to handle obfuscated exceptions
      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @JavascriptInterface
      public final void save(String var1, String var2) {
         Intrinsics.checkNotNullParameter(var1, "name");
         Intrinsics.checkNotNullParameter(var2, "b64");

         ContentValues var5;
         try {
            var37 = Base64.decode(var2, 0);
            var5 = new ContentValues();
            var5.put("_display_name", var1);
            String var3 = Environment.DIRECTORY_DOWNLOADS;
            StringBuilder var4 = new StringBuilder();
            var5.put("relative_path", var4.append(var3).append("/StarView").toString());
         } catch (Exception var35) {
            new Handler(Looper.getMainLooper()).post(new GalaxyWebPool$SaveBridge$$ExternalSyntheticLambda1(this, var35));
            return;
         }

         Uri var41;
         try {
            var41 = this.ctx.getContentResolver().insert(Downloads.EXTERNAL_CONTENT_URI, var5);
         } catch (Exception var34) {
            new Handler(Looper.getMainLooper()).post(new GalaxyWebPool$SaveBridge$$ExternalSyntheticLambda1(this, var34));
            return;
         }

         if (var41 != null) {
            try {
               var42 = this.ctx.getContentResolver().openOutputStream(var41);
            } catch (Exception var32) {
               new Handler(Looper.getMainLooper()).post(new GalaxyWebPool$SaveBridge$$ExternalSyntheticLambda1(this, var32));
               return;
            }

            if (var42 != null) {
               try {
                  var43 = var42;
               } catch (Exception var31) {
                  new Handler(Looper.getMainLooper()).post(new GalaxyWebPool$SaveBridge$$ExternalSyntheticLambda1(this, var31));
                  return;
               }

               try {
                  var43.write(var37);
                  Unit var39 = Unit.INSTANCE;
               } catch (Throwable var30) {
                  Throwable var38 = var30;

                  try {
                     throw var38;
                  } finally {
                     try {
                        CloseableKt.closeFinally(var43, var38);
                     } catch (Exception var27) {
                        new Handler(Looper.getMainLooper()).post(new GalaxyWebPool$SaveBridge$$ExternalSyntheticLambda1(this, var27));
                        return;
                     }
                  }
               }

               try {
                  CloseableKt.closeFinally(var43, null);
               } catch (Exception var29) {
                  new Handler(Looper.getMainLooper()).post(new GalaxyWebPool$SaveBridge$$ExternalSyntheticLambda1(this, var29));
                  return;
               }
            }

            try {
               Handler var40 = new Handler(Looper.getMainLooper());
               GalaxyWebPool$SaveBridge$$ExternalSyntheticLambda0 var44 = new GalaxyWebPool$SaveBridge$$ExternalSyntheticLambda0(this, var1);
               var40.post(var44);
            } catch (Exception var26) {
               new Handler(Looper.getMainLooper()).post(new GalaxyWebPool$SaveBridge$$ExternalSyntheticLambda1(this, var26));
            }
         } else {
            try {
               RuntimeException var36 = new RuntimeException("no storage");
               throw var36;
            } catch (Exception var33) {
               new Handler(Looper.getMainLooper()).post(new GalaxyWebPool$SaveBridge$$ExternalSyntheticLambda1(this, var33));
            }
         }
      }
   }
}
