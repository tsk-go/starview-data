package com.starpilot.starview;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PairScanner.kt */
public final class PairScanner {
    public static final PairScanner INSTANCE = new PairScanner();
    private static final long REPROMPT_MS = 300000;
    private static volatile long lastPromptMs;
    private static volatile boolean prompting;

    private PairScanner() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void scan$default(PairScanner pairScanner, Activity activity, Function0 function0, int i, Object obj) {
        if ((i & 2) != 0) {
            function0 = new Function0() { // from class: com.starpilot.starview.PairScanner$$ExternalSyntheticLambda5
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit unit;
                    unit = Unit.INSTANCE;
                    return unit;
                }
            };
        }
        pairScanner.scan(activity, function0);
    }

    public final void scan(final Activity act, final Function0<Unit> onPaired) {
        Intrinsics.checkNotNullParameter(act, "act");
        Intrinsics.checkNotNullParameter(onPaired, "onPaired");
        GmsBarcodeScannerOptions options = new GmsBarcodeScannerOptions.Builder().setBarcodeFormats(256, new int[0]).build();
        Intrinsics.checkNotNullExpressionValue(options, "build(...)");
        Task<Barcode> startScan = GmsBarcodeScanning.getClient(act, options).startScan();
        final Function1 function1 = new Function1() { // from class: com.starpilot.starview.PairScanner$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit scan$lambda$1;
                scan$lambda$1 = PairScanner.scan$lambda$1(act, onPaired, (Barcode) obj);
                return scan$lambda$1;
            }
        };
        startScan.addOnSuccessListener(new OnSuccessListener() { // from class: com.starpilot.starview.PairScanner$$ExternalSyntheticLambda1
            @Override // com.google.android.gms.tasks.OnSuccessListener
            public final void onSuccess(Object obj) {
                Function1.this.invoke(obj);
            }
        }).addOnFailureListener(new OnFailureListener() { // from class: com.starpilot.starview.PairScanner$$ExternalSyntheticLambda2
            @Override // com.google.android.gms.tasks.OnFailureListener
            public final void onFailure(Exception exc) {
                PairScanner.scan$lambda$3(act, exc);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit scan$lambda$1(Activity $act, Function0 $onPaired, Barcode code) {
        Link link = Link.INSTANCE;
        Context applicationContext = $act.getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
        String rawValue = code.getRawValue();
        if (rawValue == null) {
            rawValue = "";
        }
        if (link.pairFromUri(applicationContext, rawValue)) {
            Toast.makeText($act, "Paired with the comma", 0).show();
            $onPaired.invoke();
        } else {
            Toast.makeText($act, "That isn't a StarView pairing code. On the comma: Settings › starview › show qr", 1).show();
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void scan$lambda$3(Activity $act, Exception e) {
        Intrinsics.checkNotNullParameter(e, "e");
        Toast.makeText($act, "Scanner unavailable: " + e.getMessage(), 1).show();
    }

    public final void promptIfNeeded(final Activity act, final Function0<Unit> onPaired) {
        Intrinsics.checkNotNullParameter(act, "act");
        Intrinsics.checkNotNullParameter(onPaired, "onPaired");
        long now = System.currentTimeMillis();
        if (!Link.INSTANCE.getPairingRequired() || prompting || act.isFinishing() || now - lastPromptMs < 300000) {
            return;
        }
        prompting = true;
        lastPromptMs = now;
        new AlertDialog.Builder(act).setTitle("Pair with your comma").setMessage("On the comma, open Settings › starview › show qr, then tap Scan.").setPositiveButton("Scan", new DialogInterface.OnClickListener() { // from class: com.starpilot.starview.PairScanner$$ExternalSyntheticLambda3
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                PairScanner.promptIfNeeded$lambda$4(act, onPaired, dialogInterface, i);
            }
        }).setNegativeButton("Later", (DialogInterface.OnClickListener) null).setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.starpilot.starview.PairScanner$$ExternalSyntheticLambda4
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                PairScanner.prompting = false;
            }
        }).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void promptIfNeeded$lambda$4(Activity $act, Function0 $onPaired, DialogInterface dialogInterface, int i) {
        INSTANCE.scan($act, $onPaired);
    }
}
