package com.starpilot.starview;

import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

// recovered: R8's synthetic lambda class (used by methods taken from Vineflower)
public final class DestinationActivity$$ExternalSyntheticLambda23 implements OnEditorActionListener {
   public final DestinationActivity f$0;
   public final EditText f$1;

   public DestinationActivity$$ExternalSyntheticLambda23(DestinationActivity var1, EditText var2) {
      this.f$0 = var1;
      this.f$1 = var2;
   }

   public final boolean onEditorAction(TextView var1, int var2, KeyEvent var3) {
      return DestinationActivity.onCreate$lambda$7$lambda$6(this.f$0, this.f$1, var1, var2, var3);
   }
}
