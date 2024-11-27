package com.example.smartring.controller

import com.example.smartring.MainActivity.Companion.result
import com.example.smartring.MainApplication.Companion.manager
import com.example.smartring.model.HeartRateResponseModel
import com.google.gson.Gson
import kotlinx.coroutines.delay

class HeartBeatController {
    private val gson = Gson()

    suspend fun getData(): HeartRateResponseModel? {
        manager?.cmdGet14()
        delay(2000)
        val result = result["GET,14"]
        return gson.fromJson(result.toString(), HeartRateResponseModel::class.java)
    }
}
