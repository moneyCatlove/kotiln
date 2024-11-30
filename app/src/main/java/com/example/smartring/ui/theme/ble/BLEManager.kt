package com.example.smartring.ble

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.*
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class BLEManager(private val context: Context) {

    private val bluetoothAdapter: BluetoothAdapter? by lazy {
        val bluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as? android.bluetooth.BluetoothManager
        bluetoothManager?.adapter
    }

    private var bluetoothGatt: BluetoothGatt? = null
    private val _connectionState = MutableLiveData<Boolean>()
    val connectionState: LiveData<Boolean> get() = _connectionState

    private var onDataReceived: ((String) -> Unit)? = null

    // BLE 권한 확인
    private fun hasPermissions(): Boolean {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_SCAN) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED
    }

    // BLE 디바이스 스캔
    @SuppressLint("MissingPermission")
    fun startScan(onDeviceFound: (BluetoothDevice) -> Unit) {
        if (!hasPermissions()) {
            Toast.makeText(context, "BLE 권한이 필요합니다.", Toast.LENGTH_SHORT).show()
            return
        }
        bluetoothAdapter?.bluetoothLeScanner?.startScan(object : ScanCallback() {
            override fun onScanResult(callbackType: Int, result: ScanResult?) {
                result?.device?.let { onDeviceFound(it) }
            }
            override fun onScanFailed(errorCode: Int) {
                Log.e("BLEManager", "BLE 스캔 실패: $errorCode")
            }
        })
    }

    // BLE 디바이스 연결
    @SuppressLint("MissingPermission")
    fun connectDevice(device: BluetoothDevice) {
        if (!hasPermissions()) {
            Toast.makeText(context, "BLE 권한이 필요합니다.", Toast.LENGTH_SHORT).show()
            return
        }

        bluetoothGatt = device.connectGatt(context, false, object : BluetoothGattCallback() {
            override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
                when (newState) {
                    BluetoothProfile.STATE_CONNECTED -> {
                        Log.d("BLEManager", "BLE 기기 연결 성공")
                        _connectionState.postValue(true)
                        gatt?.discoverServices()
                    }
                    BluetoothProfile.STATE_DISCONNECTED -> {
                        Log.d("BLEManager", "BLE 기기 연결 끊김")
                        _connectionState.postValue(false)
                    }
                }
            }

            override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
                if (status == BluetoothGatt.GATT_SUCCESS) {
                    Log.d("BLEManager", "BLE 서비스 발견")
                } else {
                    Log.e("BLEManager", "BLE 서비스 발견 실패: $status")
                }
            }

            override fun onCharacteristicChanged(gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic?) {
                characteristic?.value?.toString(Charsets.UTF_8)?.let { onDataReceived?.invoke(it) }
            }
        })
    }

    // 데이터 수신 리스너 설정
    fun setOnDataReceivedListener(listener: (String) -> Unit) {
        onDataReceived = listener
    }

    // BLE 연결 해제
    @SuppressLint("MissingPermission")
    fun disconnectDevice() {
        if (!hasPermissions()) {
            Toast.makeText(context, "BLE 권한이 필요합니다.", Toast.LENGTH_SHORT).show()
            return
        }
        bluetoothGatt?.disconnect()
        bluetoothGatt?.close()
        bluetoothGatt = null
        _connectionState.postValue(false)
    }
}
