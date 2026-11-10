package com.starpilot.starview;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SensorsActivity.kt */
public final class SensorsActivity extends AppCompatActivity {
    private SensorsView view;

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(128);
        float d = getResources().getDisplayMetrics().density;
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(1);
        root.setBackgroundColor(Color.rgb(11, 13, 18));
        LinearLayout bar = new LinearLayout(this);
        bar.setOrientation(0);
        bar.setGravity(16);
        float f = 16;
        bar.setPadding((int) (f * d), (int) (10 * d), (int) (f * d), (int) (6 * d));
        TextView $this$onCreate_u24lambda_u242 = new TextView(this);
        $this$onCreate_u24lambda_u242.setText("GPS & sensors");
        $this$onCreate_u24lambda_u242.setTextColor(-1);
        $this$onCreate_u24lambda_u242.setTextSize(20.0f);
        SensorsView sensorsView = null;
        $this$onCreate_u24lambda_u242.setTypeface(null, 1);
        bar.addView($this$onCreate_u24lambda_u242, new LinearLayout.LayoutParams(0, -2, 1.0f));
        this.view = new SensorsView(this);
        Button $this$onCreate_u24lambda_u244 = new Button(this);
        $this$onCreate_u24lambda_u244.setText("Reset");
        $this$onCreate_u24lambda_u244.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.SensorsActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SensorsActivity.onCreate$lambda$4$lambda$3(SensorsActivity.this, view);
            }
        });
        bar.addView($this$onCreate_u24lambda_u244);
        Button $this$onCreate_u24lambda_u246 = new Button(this);
        $this$onCreate_u24lambda_u246.setText("Close");
        $this$onCreate_u24lambda_u246.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.SensorsActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SensorsActivity.this.finish();
            }
        });
        bar.addView($this$onCreate_u24lambda_u246);
        root.addView(bar, new LinearLayout.LayoutParams(-1, -2));
        SensorsView sensorsView2 = this.view;
        if (sensorsView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("view");
        } else {
            sensorsView = sensorsView2;
        }
        root.addView(sensorsView, new LinearLayout.LayoutParams(-1, 0, 1.0f));
        setContentView(root);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$4$lambda$3(SensorsActivity this$0, View it) {
        SensorsView sensorsView = this$0.view;
        if (sensorsView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("view");
            sensorsView = null;
        }
        sensorsView.reset();
    }
}
