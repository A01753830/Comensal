package mx.grm.prototipo4.model

import android.app.Activity
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning

object QrManager {
    fun startQrCodeScanner(activity: Activity, resultCallback: (String) -> Unit, errorCallback: (Exception) -> Unit) {
        val options = GmsBarcodeScannerOptions.Builder()
            .enableAutoZoom()
            .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
            .build()

        val scanner = GmsBarcodeScanning.getClient(activity, options)
        scanner.startScan()
            .addOnSuccessListener { barcode -> resultCallback(barcode.rawValue.toString()) }
            .addOnFailureListener { e -> errorCallback(e) }
    }
}