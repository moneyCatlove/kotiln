package com.example.smartring.controller

import com.example.smartring.MainApplication.Companion.manager
import com.example.smartring.model.DailySleepFetcher
import com.example.smartring.model.DailySleepModel
import com.example.smartring.model.DailySleepStateFetcher
import com.example.smartring.model.DailySleepStateModel

class DailySleepController {
    fun fetchDailySleepData(onResult: (DailySleepModel?) -> Unit) {
        manager?.let { DailySleepFetcher.fetch(it, onResult) }
    }

    fun fetchDailySleepStateData(onResult: (DailySleepStateModel?) -> Unit) {
        manager?.let { DailySleepStateFetcher.fetch(it, onResult) }
    }
}
