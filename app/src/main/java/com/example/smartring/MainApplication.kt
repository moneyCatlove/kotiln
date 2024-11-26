package com.example.smartring

import android.app.Application
import com.smtlink.transferprotocolsdk.ble.BleTransferManager
import kotlinx.coroutines.flow.MutableStateFlow

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        manager = BleTransferManager.initialized(this)
    }

    var isConnectedState = MutableStateFlow(false)

    companion object {
        var instance: MainApplication? = null
        var manager: BleTransferManager? = null
    }
}
