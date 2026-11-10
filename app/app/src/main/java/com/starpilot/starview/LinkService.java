package com.starpilot.starview;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.Notification.Builder;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class LinkService extends Service {
   private static final String CHANNEL = "starview_link";
   public static final LinkService.Companion Companion = new LinkService.Companion(null);
   private static final int ID = 7;
   private WakeLock wake;

   public IBinder onBind(Intent var1) {
      return null;
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   public void onDestroy() {
      label18: {
         WakeLock var1;
         try {
            var1 = this.wake;
         } catch (Exception var3) {
            break label18;
         }

         if (var1 != null) {
            try {
               var1.release();
            } catch (Exception var2) {
            }
         }
      }

      this.wake = null;
      super.onDestroy();
   }

   public int onStartCommand(Intent var1, int var2, int var3) {
      NotificationManager var4 = (NotificationManager)this.getSystemService(NotificationManager.class);
      NotificationChannel var6 = new NotificationChannel("starview_link", "StarView running", 2);
      var6.setDescription("Keeps the comma link, turn signals and lane prompts working while StarView isn't on screen");
      var6.setShowBadge(false);
      var4.createNotificationChannel(var6);
      Context var12 = (Context)this;
      var1 = new Intent((Context)this, MainActivity.class).addFlags(536870912);
      PendingIntent var8 = PendingIntent.getActivity(var12, 0, var1, 201326592);
      Notification var9 = new Builder((Context)this, "starview_link")
         .setSmallIcon(R.mipmap.ic_launcher)
         .setContentTitle("StarView is running")
         .setContentText("Turn signals, lane prompts and the comma link keep working in the background")
         .setOngoing(true)
         .setContentIntent(var8)
         .build();
      Intrinsics.checkNotNullExpressionValue(var9, "build(...)");

      try {
         this.startForeground(7, var9, 16);
      } catch (Exception var5) {
         this.stopSelf();
         return 2;
      }

      if (this.wake == null) {
         WakeLock var10 = (WakeLock)this.getSystemService("power");
         Intrinsics.checkNotNull(var10, "null cannot be cast to non-null type android.os.PowerManager");
         var10 = ((PowerManager)var10).newWakeLock(1, "StarView:link");
         var10.setReferenceCounted(false);
         var10.acquire();
         this.wake = var10;
      }

      return 2;
   }

   public void onTaskRemoved(Intent var1) {
      this.stopSelf();
      super.onTaskRemoved(var1);
   }

   public static final class Companion {
      private Companion() {
      }

      // $VF: synthetic method
      public Companion(DefaultConstructorMarker var1) {
         this();
      }

      public final void start(Context var1) {
         Intrinsics.checkNotNullParameter(var1, "ctx");

         try {
            Intent var2 = new Intent(var1, LinkService.class);
            var1.startForegroundService(var2);
         } catch (Exception var3) {
         }
      }

      public final void stop(Context var1) {
         Intrinsics.checkNotNullParameter(var1, "ctx");

         try {
            Intent var2 = new Intent(var1, LinkService.class);
            var1.stopService(var2);
         } catch (Exception var3) {
         }
      }
   }
}
