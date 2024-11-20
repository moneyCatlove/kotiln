package com.example.smartring

import android.Manifest
import android.bluetooth.BluetoothGatt
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.content.ContextCompat
import com.example.smartring.MainApplication.Companion.manager
import com.example.smartring.ui.theme.AppNavHost
import com.example.smartring.ui.theme.SmartRingTheme
import com.smtlink.transferprotocolsdk.ble.AnalyticalDataCallBack
import com.smtlink.transferprotocolsdk.ble.BTMGattCallBack
import com.smtlink.transferprotocolsdk.utils.BluetoothOpenStateUtil
import org.json.JSONObject


class MainActivity : ComponentActivity(), BTMGattCallBack, AnalyticalDataCallBack {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) permissions = arrayOf<String>(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.BLUETOOTH_CONNECT
        )
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.BLUETOOTH_CONNECT
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(permissions, 100)
        }

        manager?.setBTMGattCallBack(this)
        manager?.setAnalyticalDataCallBack(this)

        // Compose 시작
        setContent {
            SmartRingTheme {
                AppNavHost()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (!BluetoothOpenStateUtil.isBluetoothOpen()) { //检查蓝牙开关状态
            BluetoothOpenStateUtil.openBluetooth(this)
        }
        Log.d("umjunsik", MainApplication.instance?.isConnectedState.toString())
        manager?.connectGatt("6F:55:45:34:23:23", false)
        Log.d("umjunsik",manager?.cmdGet66().toString())
    }

    override fun onConnectionStateChange(p0: BluetoothGatt?, p1: Int, p2: Int) {
        Log.d("umjunsik", "변경")
    }

    override fun onConnected() {
        Log.d("umjunsik", "연결됨")
    }

    override fun onDisConnect() {
        Log.d("umjunsik", "연결 끊어짐")    }

    override fun jsonObjectData(p0: String?, p1: JSONObject?) {
        Log.d("umjunsik", "데이터")    }

    override fun pushDataProgress(p0: Int, p1: Int) {
        Log.d("umjunsik", "데이터 넣는중")    }

    override fun pushDataProgressState(p0: Int) {
        Log.d("umjunsik", "데이터 진행 상태")    }

    override fun pushDataNotStartedLowBattery() {
        Log.d("umjunsik", "배터리 없음")    }

    override fun getGpsDataProgress(p0: Int) {
        Log.d("umjunsik", "GPS?")    }
}
