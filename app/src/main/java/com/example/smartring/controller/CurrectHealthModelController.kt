package com.example.smartring.controller

import com.example.smartring.MainActivity.Companion.result
import com.example.smartring.MainApplication.Companion.manager
import com.example.smartring.model.CurrectHealthModel
import com.google.gson.Gson
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class CurrectHealthModelController {
    private val gson = Gson()

    fun getData(): CurrectHealthModel? {
        manager?.cmdGet17()
        runBlocking {
            launch {
                delay(2000) }}

        val rawResult = result?.get("GET,17") ?: return null

        return gson.fromJson(gson.toJson(rawResult), CurrectHealthModel::class.java)
    }
}
