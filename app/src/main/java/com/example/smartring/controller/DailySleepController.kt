package com.example.smartring.controller

import com.example.smartring.model.DailySleepFetcher
import com.example.smartring.model.DailySleepModel
import com.example.smartring.model.DailySleepStateFetcher
import com.example.smartring.model.DailySleepStateModel
import com.smtlink.transferprotocolsdk.ble.BleTransferManager

class DailySleepController(private val manager: BleTransferManager) {

    fun fetchDailySleepData(onResult: (DailySleepModel?) -> Unit) {
        DailySleepFetcher.fetch(manager, onResult)
    }

    fun fetchDailySleepStateData(onResult: (DailySleepStateModel?) -> Unit) {
        DailySleepStateFetcher.fetch(manager, onResult)
    }
}
