package com.example.smartring

import android.Manifest
import android.app.AlertDialog
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.smartring.ble.BLEManager
import com.example.smartring.ui.theme.AppNavHost
import com.example.smartring.ui.theme.SmartRingTheme

class MainActivity : ComponentActivity() {

    private lateinit var bleManager: BLEManager
    private var currentGatt: BluetoothGatt? = null // 현재 연결된 GATT 세션 저장

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // BLEManager 초기화
        bleManager = BLEManager(this)

        // BLE 권한 요청
        requestBLEPermissions()

        // BLEManager 콜백 설정
        bleManager.setOnConnectionStateChangedListener { connected ->
            Log.d("SmartRing", if (connected) "Device connected" else "Device disconnected")
        }

        bleManager.setOnDataReceivedListener { data ->
            Log.d("SmartRing", "Received data: $data")
        }

        // Compose UI 초기화
        setContent {
            SmartRingTheme {
                AppNavHost()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (!bleManager.isBluetoothEnabled()) {
            bleManager.enableBluetooth(this)
        }

        // BLE 기기 검색 시작
        bleManager.startScanning { devices ->
            if (devices.isEmpty()) {
                Log.d("SmartRing", "No BLE devices found")
            } else {
                showDeviceSelectionDialog(devices)
            }
        }
    }

    private fun showDeviceSelectionDialog(devices: List<BluetoothDevice>) {
        // 권한 체크
        if (!hasPermission(Manifest.permission.BLUETOOTH_CONNECT)) {
            Log.e("SmartRing", "Missing BLUETOOTH_CONNECT permission")
            requestBLEPermissions()
            return
        }

        // 기기 이름 리스트 생성
        val deviceNames = devices.map { device ->
            // 권한 확인
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.BLUETOOTH_CONNECT
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                // 권한이 있을 때 기기 이름과 주소를 반환
                "${device.name ?: "Unknown"} (${device.address})"
            } else {
                // 권한이 없을 때 이름 대신 "Permission Required"로 표시
                Log.w("SmartRing", "Missing BLUETOOTH_CONNECT permission for device: ${device.address}")
                "Permission Required (${device.address})"
            }
        }.toTypedArray()


        AlertDialog.Builder(this)
            .setTitle("Select a BLE Device")
            .setItems(deviceNames) { _, which ->
                val selectedDevice = devices[which]
                currentGatt = bleManager.connectToDevice(selectedDevice.address)
                if (currentGatt == null) {
                    Log.e("SmartRing", "Failed to connect to device: ${selectedDevice.address}")
                }
            }
            .show()
    }

    private fun requestBLEPermissions() {
        val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            arrayOf(
                Manifest.permission.BLUETOOTH_SCAN,
                Manifest.permission.BLUETOOTH_CONNECT
            )
        } else {
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
        }

        // 권한 요청시키는 페이지
        if (permissions.any {
                ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
            }) {
            ActivityCompat.requestPermissions(this, permissions, 100)
        }
    }

    private fun hasPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
    }

    override fun onDestroy() {
        super.onDestroy()
        currentGatt?.let {
            bleManager.disconnectDevice(it) // GATT 세션 종료
        }
    }
}
