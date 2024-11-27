package com.example.smartring.model

import android.util.Log
import org.json.JSONObject

data class CurrectHealthModel(
    val heartRate: Int,   // 현재 심박수
    val sleepState: Int,  // 현재 수면 상태 (0=깨어있음, 1=얕은 수면, 2=깊은 수면)
    val stepCount: Int    // 현재 걸음 수
) {
    companion object {
        fun fromJson(jsonObject: JSONObject): CurrectHealthModel? {
            return try {
                val heartRate = jsonObject.getInt("heart_rate")
                val sleepState = jsonObject.getInt("sleep_state")
                val stepCount = jsonObject.getInt("step_count")

                CurrectHealthModel(
                    heartRate = heartRate,
                    sleepState = sleepState,
                    stepCount = stepCount
                )
            } catch (e: Exception) {
                Log.e("CurrectHealthModel", "Error parsing health data: ${e.message}")
                null
            }
        }
    }
}
