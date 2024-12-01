package com.example.smartring.ble

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.*
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class BLEManager(private val context: Context) {

    private val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
    private var onConnectionStateChanged: ((Boolean) -> Unit)? = null
    private var onDataReceived: ((String) -> Unit)? = null

    // 블루투스 활성화 확인
    fun isBluetoothEnabled(): Boolean {
        return bluetoothAdapter?.isEnabled ?: false
    }

    // 블루투스 활성화 요청
    fun enableBluetooth(activity: Activity) {
        if (!hasPermission(Manifest.permission.BLUETOOTH_CONNECT)) {
            requestPermission(activity, Manifest.permission.BLUETOOTH_CONNECT)
            return
        }

        try {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            activity.startActivityForResult(enableBtIntent, 100)
        } catch (e: SecurityException) {
            Log.e("BLEManager", "Error enabling Bluetooth: ${e.message}")
        }
    }

    // BLE 스캔 시작
    @SuppressLint("MissingPermission")
    fun startScanning(onDevicesFound: (List<BluetoothDevice>) -> Unit) {
        if (!hasPermission(Manifest.permission.BLUETOOTH_SCAN)) {
            Log.e("BLEManager", "Missing BLUETOOTH_SCAN permission")
            return
        }

        val scanner = bluetoothAdapter?.bluetoothLeScanner
        val devices = mutableListOf<BluetoothDevice>()

        val scanCallback = object : ScanCallback() {
            override fun onScanResult(callbackType: Int, result: ScanResult) {
                result.device?.let {
                    if (!devices.contains(it)) {
                        devices.add(it)
                        Log.d("BLEManager", "Device found: ${it.name ?: "Unknown"} - ${it.address}")
                    }
                }
            }

            override fun onScanFailed(errorCode: Int) {
                Log.e("BLEManager", "Scan failed with error: $errorCode")
            }
        }

        scanner?.startScan(scanCallback)

        Handler(Looper.getMainLooper()).postDelayed({
            scanner?.stopScan(scanCallback)
            onDevicesFound(devices)
        }, 10000)
    }

    // BLE 기기 연결
    @SuppressLint("MissingPermission")
    fun connectToDevice(deviceAddress: String): BluetoothGatt? {
        if (!hasPermission(Manifest.permission.BLUETOOTH_CONNECT)) {
            Log.e("BLEManager", "Missing BLUETOOTH_CONNECT permission")
            return null
        }

        val device = bluetoothAdapter?.getRemoteDevice(deviceAddress)
        return device?.connectGatt(context, false, object : BluetoothGattCallback() {
            override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {
                if (newState == BluetoothProfile.STATE_CONNECTED) {
                    Log.d("BLEManager", "Connected to $deviceAddress")
                    onConnectionStateChanged?.invoke(true)
                } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                    Log.d("BLEManager", "Disconnected from $deviceAddress")
                    onConnectionStateChanged?.invoke(false)
                }
            }

            override fun onCharacteristicChanged(
                gatt: BluetoothGatt,
                characteristic: BluetoothGattCharacteristic
            ) {
                val data = characteristic.getStringValue(0) ?: "Unknown Data"
                onDataReceived?.invoke(data)
            }
        })
    }

    // BLE 기기 연결 해제
    @SuppressLint("MissingPermission")
    fun disconnectDevice(gatt: BluetoothGatt?) {
        if (!hasPermission(Manifest.permission.BLUETOOTH_CONNECT)) {
            Log.e("BLEManager", "Missing BLUETOOTH_CONNECT permission")
            return
        }

        gatt?.disconnect()
        gatt?.close()
    }

    // 연결 상태 변경 콜백 설정
    fun setOnConnectionStateChangedListener(listener: (Boolean) -> Unit) {
        onConnectionStateChanged = listener
    }

    // 데이터 수신 콜백 설정
    fun setOnDataReceivedListener(listener: (String) -> Unit) {
        onDataReceived = listener
    }

    // 권한 요청
    private fun requestPermission(activity: Activity, permission: String) {
        ActivityCompat.requestPermissions(activity, arrayOf(permission), 100)
    }

    // 권한 확인
    private fun hasPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
    }
}
