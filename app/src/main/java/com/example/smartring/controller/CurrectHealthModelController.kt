package com.example.smartring.controller

import android.util.Log
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
        // BLE 명령 실행
        manager?.cmdGet17()

        // 데이터 수신 대기 (2초)
        runBlocking {
            launch {
                delay(2000)
            }
        }

        // 결과 가져오기
        val rawResult = result?.get("GET,17") ?: return null

        // JSON 데이터를 CurrectHealthModel로 변환
        return gson.fromJson(gson.toJson(rawResult), CurrectHealthModel::class.java)
    }
}
