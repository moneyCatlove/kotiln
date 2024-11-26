package com.example.smartring.controller

import com.example.smartring.MainActivity.Companion.result
import com.example.smartring.MainApplication.Companion.manager
import com.example.smartring.model.HeartRateResponseModel
import com.google.gson.Gson
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class HeartBeatController {
    private val gson = Gson()

    fun getData(): HeartRateResponseModel {
        manager?.cmdGet14()
        runBlocking { launch { runBlocking { delay(2000) } } }
        val result = result["GET,14"]
        return gson.fromJson(gson.toJson(result), HeartRateResponseModel::class.java)
    }
}
