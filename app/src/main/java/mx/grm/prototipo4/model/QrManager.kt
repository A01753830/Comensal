package mx.grm.prototipo4.model

import android.app.Activity
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning

/**
 * QR Code Scanner Manager
 *
 * This object provides functionality for scanning QR codes using Google's ML Kit Vision Library.
 * It offers a method to start a QR code scanner and provides callbacks for successful scanning results
 * and error handling.
 *
 * @property GmsBarcodeScannerOptions Configuration options for the barcode scanner.
 * @property GmsBarcodeScanning The barcode scanning client.
 * @constructor Private constructor to prevent object instantiation.
 * @sample com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
 * @sample com.google.mlkit.vision.codescanner.GmsBarcodeScanning
 * @see com.google.mlkit.vision.barcode.common.Barcode
 * @see com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions.Builder
 * @see com.google.mlkit.vision.codescanner.GmsBarcodeScanning.getClient
 *
 * @param activity The current activity where the scanner will be started.
 * @param resultCallback The callback to handle the successful result (QR code data).
 * @param errorCallback The callback to handle errors that occur during scanning.
 * @return Unit
 * @sample resultCallback("QR_CODE_DATA") for a successful scan result.
 * @sample errorCallback(exception) for error handling.
 * @throws Exception if an error occurs during scanning.
 *
 * @authors Héctor González Sánchez
 * @authors Alfredo Azamar López
 */

object QrManager {

    /**
     * Starts the QR code scanner using Google's ML Kit Vision Library.
     *
     * @param activity The current activity where the scanner will be started.
     * @param resultCallback The callback to handle the successful result (QR code data).
     * @param errorCallback The callback to handle errors that occur during scanning.
     */
    fun startQrCodeScanner(activity: Activity, resultCallback: (String) -> Unit, errorCallback: (Exception) -> Unit) {
        // Configuration options for the barcode scanner
        val options = GmsBarcodeScannerOptions.Builder()
            .enableAutoZoom()
            .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
            .build()

        // The barcode scanning client
        val scanner = GmsBarcodeScanning.getClient(activity, options)

        // Start scanning
        scanner.startScan()
            .addOnSuccessListener { barcode -> resultCallback(barcode.rawValue.toString()) }
            .addOnFailureListener { e -> errorCallback(e) }
    }
}