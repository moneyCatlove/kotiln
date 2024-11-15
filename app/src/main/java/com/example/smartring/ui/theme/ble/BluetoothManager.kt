package com.example.smartring.ble

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothProfile
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class BluetoothManager(private val context: Context) {
    private val bluetoothAdapter: BluetoothAdapter? by lazy {
        val bluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as? BluetoothManager
        bluetoothManager?.adapter
    }

    private var bluetoothGatt: BluetoothGatt? = null
    private var onConnectionStatusChange: ((Boolean) -> Unit)? = null

    fun setConnectionStatusListener(listener: (Boolean) -> Unit) {
        onConnectionStatusChange = listener
    }

    fun connectToDevice(macAddress: String) {
        if (!hasBluetoothPermission()) {
            requestBluetoothPermission(context as? Activity)
            return
        }

        val device = bluetoothAdapter?.getRemoteDevice(macAddress)
        if (device == null) {
            showToast("Device not found with MAC address: $macAddress")
            return
        }

        try {
            bluetoothGatt = device.connectGatt(context, false, object : BluetoothGattCallback() {
                override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
                    if (newState == BluetoothProfile.STATE_CONNECTED) {
                        showToast("Connected to device.")
                        onConnectionStatusChange?.invoke(true)

                        if (hasBluetoothPermission()) {
                            try {
                                gatt?.discoverServices()
                            } catch (e: SecurityException) {
                                println("SecurityException during service discovery: ${e.message}")
                            }
                        } else {
                            println("BLUETOOTH_CONNECT permission not granted.")
                            requestBluetoothPermission(context as? Activity)
                        }
                    } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                        showToast("Disconnected from device.")
                        onConnectionStatusChange?.invoke(false)
                    }
                }
            })
        } catch (e: SecurityException) {
            println("SecurityException during connection: ${e.message}")
        }
    }

    fun disconnectDevice() {
        if (hasBluetoothPermission()) {
            bluetoothGatt?.disconnect()
            bluetoothGatt = null
            onConnectionStatusChange?.invoke(false)
            showToast("Disconnected from device.")
        } else {
            requestBluetoothPermission(context as? Activity)
        }
    }

    private fun hasBluetoothPermission(): Boolean {
        return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }

    private fun requestBluetoothPermission(activity: Activity?) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            activity?.let {
                ActivityCompat.requestPermissions(
                    it,
                    arrayOf(Manifest.permission.BLUETOOTH_CONNECT),
                    1
                )
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
