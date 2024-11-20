package com.example.smartring

import android.app.Application
import android.util.Log
import com.smtlink.transferprotocolsdk.ble.BleTransferManager

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        manager = BleTransferManager.initialized(this)
    }

    var isConnectedState: Boolean = false

    companion object {
        var instance: MainApplication? = null
        var manager: BleTransferManager? = null
    }
}