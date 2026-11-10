package com.starpilot.starview

import android.app.Activity
import android.app.AlertDialog
import android.widget.Toast
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning

/**
 * First-time pairing: scan the QR code the comma shows under Settings › starview › show qr.
 * Uses Google's code scanner (Play services), so the app needs no camera permission.
 */
object PairScanner {
  @Volatile private var prompting = false

  fun scan(act: Activity, onPaired: () -> Unit = {}) {
    val options = GmsBarcodeScannerOptions.Builder().setBarcodeFormats(Barcode.FORMAT_QR_CODE).build()
    GmsBarcodeScanning.getClient(act, options).startScan()
      .addOnSuccessListener { code ->
        if (Link.pairFromUri(act.applicationContext, code.rawValue ?: "")) {
          Toast.makeText(act, "Paired with the comma", Toast.LENGTH_SHORT).show()
          onPaired()
        } else {
          Toast.makeText(act, "That isn't a StarView pairing code. On the comma: Settings › starview › show qr", Toast.LENGTH_LONG).show()
        }
      }
      .addOnFailureListener { e -> Toast.makeText(act, "Scanner unavailable: ${e.message}", Toast.LENGTH_LONG).show() }
  }

  @Volatile private var lastPromptMs = 0L
  private const val REPROMPT_MS = 5 * 60_000L

  /** Shown when the comma refuses us (HTTP 401); after "Later", not again for a few minutes of reconnect attempts. */
  fun promptIfNeeded(act: Activity, onPaired: () -> Unit) {
    val now = System.currentTimeMillis()
    if (!Link.pairingRequired || prompting || act.isFinishing || now - lastPromptMs < REPROMPT_MS) return
    prompting = true
    lastPromptMs = now
    AlertDialog.Builder(act)
      .setTitle("Pair with your comma")
      .setMessage("On the comma, open Settings › starview › show qr, then tap Scan.")
      .setPositiveButton("Scan") { _, _ -> scan(act, onPaired) }
      .setNegativeButton("Later", null)
      .setOnDismissListener { prompting = false }
      .show()
  }
}
