package com.starpilot.starview;

import android.net.Uri;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public interface GxHost {
   GalaxyApi getGxApi();

   String getGxHost();

   void openPage(String var1);

   void pickFile(String[] var1, Function1<? super Uri, Unit> var2);
}
