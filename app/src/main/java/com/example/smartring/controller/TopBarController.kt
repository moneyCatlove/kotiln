package com.example.smartring.controller

import com.example.smartring.MainActivity.Companion.result
import com.example.smartring.MainApplication.Companion.manager
import com.example.smartring.model.DeviceDataModel
import com.google.gson.Gson

class TopBarController {
    private val gson = Gson()

    fun getInfoDevice(): DeviceDataModel {
        manager?.cmdGet0()
        val objectInList = result.last()
        return gson.fromJson(gson.toJson(objectInList), DeviceDataModel::class.java)
    }
}
