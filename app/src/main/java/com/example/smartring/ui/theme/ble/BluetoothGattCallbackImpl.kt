package com.example.smartring.ble

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothProfile
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class BluetoothGattCallbackImpl(
    private val context: Context,
    private val onConnectionStatusChange: (Boolean) -> Unit
) : BluetoothGattCallback() {

    override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
        if (newState == BluetoothProfile.STATE_CONNECTED) {
            onConnectionStatusChange(true)
            println("Connected to GATT server.")

            if (hasBluetoothPermission()) {
                try {
                    gatt?.discoverServices()
                } catch (e: SecurityException) {
                    println("SecurityException: ${e.message}")
                }
            } else {
                requestBluetoothPermission(context as? Activity)
            }
        } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
            onConnectionStatusChange(false)
            println("Disconnected from GATT server.")
        }
    }

    override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
        if (status == BluetoothGatt.GATT_SUCCESS) {
            println("Services discovered: ${gatt?.services}")
        } else {
            println("Failed to discover services, status: $status")
        }
    }

    private fun hasBluetoothPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.BLUETOOTH_CONNECT
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestBluetoothPermission(activity: Activity?) {
        activity?.let {
            ActivityCompat.requestPermissions(
                it,
                arrayOf(Manifest.permission.BLUETOOTH_CONNECT),
                1
            )
        }
    }
}
