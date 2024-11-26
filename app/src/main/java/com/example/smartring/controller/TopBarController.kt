package com.example.smartring.controller

import com.example.smartring.MainActivity.Companion.result
import com.example.smartring.MainApplication.Companion.manager
import com.example.smartring.model.DeviceDataModel
import com.google.gson.Gson
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class TopBarController {
    private val gson = Gson()

    fun getInfoDevice(): DeviceDataModel {
        manager?.cmdGet0()
        runBlocking { launch { runBlocking { delay(1500) } } }
        val objectInList = result["GET,0"]
        return gson.fromJson(gson.toJson(objectInList), DeviceDataModel::class.java)
    }
}
