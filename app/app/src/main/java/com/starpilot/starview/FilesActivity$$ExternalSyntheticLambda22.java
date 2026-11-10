package com.starpilot.starview;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.widget.CheckBox;
import java.util.concurrent.CountDownLatch;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Ref.ObjectRef;

// $VF: synthetic class
public final class FilesActivity$$ExternalSyntheticLambda22 implements OnClickListener {
   public final ObjectRef f$0;
   public final CheckBox f$1;
   public final Function1 f$2;
   public final CountDownLatch f$3;

   // $VF: synthetic method
   public FilesActivity$$ExternalSyntheticLambda22(ObjectRef var1, CheckBox var2, Function1 var3, CountDownLatch var4) {
      this.f$0 = var1;
      this.f$1 = var2;
      this.f$2 = var3;
      this.f$3 = var4;
   }

   public final void onClick(DialogInterface var1, int var2) {
      FilesActivity.$r8$lambda$B71AwJUKmdzGtWn_i06clGOpXCE(this.f$0, this.f$1, this.f$2, this.f$3, var1, var2);
   }
}
