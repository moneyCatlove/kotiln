import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.content.Context
import android.content.pm.PackageManager
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat

// BLE 스캔 시작 함수
fun startBLEScan(
    context: Context, // Context를 매개변수로 받음
    onDeviceFound: (device: BluetoothDevice) -> Unit
) {
    val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

    // BluetoothAdapter가 null일 경우 처리
    if (bluetoothAdapter == null) {
        Log.e("BLEScan", "Bluetooth is not supported on this device.")
        return
    }

    val bluetoothLeScanner = bluetoothAdapter.bluetoothLeScanner

    // Bluetooth LE Scanner가 null일 경우 처리
    if (bluetoothLeScanner == null) {
        Log.e("BLEScan", "BLE Scanner is not available.")
        return
    }

    val scanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.BLUETOOTH_CONNECT
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            Log.d("BLEScan", "Device found: ${result.device.name ?: "Unnamed"} (${result.device.address})")
            onDeviceFound(result.device)
        }

        override fun onScanFailed(errorCode: Int) {
            Log.e("BLEScan", "BLE Scan Failed: $errorCode")
        }
    }

    val scanSettings = ScanSettings.Builder()
        .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
        .build()

    // 권한 확인
    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
        Log.e("BLEScan", "Bluetooth scan permission is not granted.")
        // 권한 요청을 호출하는 코드를 추가해야 함
        return
    }

    // BLE 스캔 시작
    bluetoothLeScanner.startScan(null, scanSettings, scanCallback)
    Log.d("BLEScan", "BLE Scan started")

    // 10초 후 BLE 스캔 중지
    Handler(Looper.getMainLooper()).postDelayed({
        bluetoothLeScanner.stopScan(scanCallback)
        Log.d("BLEScan", "BLE Scan stopped")
    }, 10000) // 10초 후 스캔 종료
}
