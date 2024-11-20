import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.os.Handler
import android.os.Looper
import android.util.Log

// 스캔 시작 함수
fun startBLEScan(onDeviceFound: (device: android.bluetooth.BluetoothDevice) -> Unit) {
    val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    val bluetoothLeScanner = bluetoothAdapter.bluetoothLeScanner

    val scanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            onDeviceFound(result.device)
        }

        override fun onScanFailed(errorCode: Int) {
            Log.e("BLEScan", "BLE Scan Failed: $errorCode")
        }
    }

    val scanSettings = ScanSettings.Builder()
        .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
        .build()

    bluetoothLeScanner.startScan(null, scanSettings, scanCallback)

    Handler(Looper.getMainLooper()).postDelayed({
        bluetoothLeScanner.stopScan(scanCallback)
        Log.d("BLEScan", "BLE Scan stopped")
    }, 10000) // 10초 후 스캔 중지
}
