package com.example.smartring

import android.Manifest
import android.bluetooth.BluetoothGatt
import android.content.pm.PackageManager
import android.os.*
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.content.ContextCompat
import com.example.smartring.MainApplication.Companion.manager
import com.example.smartring.ui.theme.AppNavHost
import com.example.smartring.ui.theme.SmartRingTheme
import com.example.smartring.util.JsonInfo
import com.smtlink.transferprotocolsdk.ble.AnalyticalDataCallBack
import com.smtlink.transferprotocolsdk.ble.BTMGattCallBack
import com.smtlink.transferprotocolsdk.utils.BluetoothOpenStateUtil
import org.json.JSONObject

class MainActivity :
    ComponentActivity(),
    BTMGattCallBack,
    AnalyticalDataCallBack {

    private val TAG = "SmartRing"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkPermissions()

        manager?.apply {
            setBTMGattCallBack(this@MainActivity)
            setAnalyticalDataCallBack(this@MainActivity)
        }

        setContent {
            SmartRingTheme {
                AppNavHost()
            }
        }
    }

    private fun checkPermissions() {
        val permissions = mutableListOf(
            Manifest.permission.ACCESS_FINE_LOCATION
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            permissions.addAll(
                listOf(
                    Manifest.permission.BLUETOOTH_SCAN,
                    Manifest.permission.BLUETOOTH_CONNECT
                )
            )
        }

        val missingPermissions = permissions.filter {
            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }

        if (missingPermissions.isNotEmpty()) {
            requestPermissions(missingPermissions.toTypedArray(), 100)
        }
    }

    private val handler = Handler(Looper.getMainLooper()) { msg ->
        when (msg.what) {
            MSG_DISCONNECT -> Log.d(TAG, "Device disconnected.")
            MSG_CONNECTED -> Log.d(TAG, "Device connected.")
            MSG_JSON_DATA -> {
                val jsonInfo = msg.obj as JsonInfo
                Log.d(TAG, "Received JSON data: $jsonInfo")
                result[jsonInfo.cmdKey] = jsonInfo.jsonObject
            }
            MSG_LOW_BATTERY -> Log.d(TAG, "Low battery warning.")
        }
        true
    }

    override fun onStart() {
        super.onStart()
        if (!BluetoothOpenStateUtil.isBluetoothOpen()) {
            BluetoothOpenStateUtil.openBluetooth(this)
        }

        manager?.connectGatt("6F:55:45:34:23:23", false)
    }

    override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
        Log.d(TAG, "Connection state changed: status=$status, newState=$newState")
    }

    override fun onConnected() {
        MainApplication.instance?.isConnectedState?.value = true
        Log.d(TAG, "Successfully connected to device.")
    }

    override fun onDisConnect() {
        MainApplication.instance?.isConnectedState?.value = false
        Log.d(TAG, "Disconnected from device.")
    }

    override fun jsonObjectData(cmdKey: String, jsonObject: JSONObject) {
        result[cmdKey] = jsonObject
        val jsonInfo = JsonInfo().apply {
            this.cmdKey = cmdKey
            this.jsonObject = jsonObject
        }
        val msg = Message.obtain().apply {
            what = MSG_JSON_DATA
            obj = jsonInfo
        }
        handler.sendMessageDelayed(msg, 500)
    }

    override fun pushDataProgress(progress: Int, totalProgress: Int) {
        Log.d(TAG, "Data transfer in progress: $progress/$totalProgress")
    }

    override fun pushDataProgressState(stateCode: Int) {
        Log.d(TAG, "Data transfer state code: $stateCode")
    }

    override fun pushDataNotStartedLowBattery() {
        Log.d(TAG, "Data transfer not started due to low battery.")
    }

    override fun getGpsDataProgress(progress: Int) {
        Log.d(TAG, "GPS data progress: $progress%")
    }

    companion object {
        val result: MutableMap<String, JSONObject> = mutableMapOf()

        private const val MSG_DISCONNECT = 0
        private const val MSG_CONNECTED = 1
        private const val MSG_JSON_DATA = 2
        private const val MSG_LOW_BATTERY = 3
    }
}
