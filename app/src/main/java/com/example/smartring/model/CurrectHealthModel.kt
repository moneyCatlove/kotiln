package com.example.smartring.model

import android.util.Log
import com.smtlink.transferprotocolsdk.ble.BleTransferManager
import com.smtlink.transferprotocolsdk.ble.AnalyticalDataCallBack
import org.json.JSONObject

data class CurrectHealthModel(
    val heartRate: Int, // 현재 심박수
    val sleepState: Int, // 현재 수면 상태 (0=깨어있음, 1=얕은 수면, 2=깊은 수면)
    val stepCount: Int // 현재 걸음 수
)

object CurrectHealthFetcher {

    fun fetch(manager: BleTransferManager, onResult: (CurrectHealthModel?) -> Unit) {
        manager.setAnalyticalDataCallBack(object : AnalyticalDataCallBack {
            override fun jsonObjectData(cmdKey: String?, jsonObject: JSONObject?) {
                if (cmdKey == "GET17" && jsonObject != null) { // cmdGet17 응답 확인
                    try {
                        val heartRate = jsonObject.getInt("heart_rate") // 심박수
                        val sleepState = jsonObject.getInt("sleep_state") // 수면 상태
                        val stepCount = jsonObject.getInt("step_count") // 걸음 수

                        val currectHealthData = CurrectHealthModel(
                            heartRate = heartRate,
                            sleepState = sleepState,
                            stepCount = stepCount
                        )

                        // 결과 반환
                        onResult(currectHealthData)
                    } catch (e: Exception) {
                        Log.e("CurrectHealthModel", "Error parsing current health data: ${e.message}")
                        onResult(null)
                    }
                }
            }

            override fun pushDataProgress(progress: Int, totalProgress: Int) {
                Log.d("CurrectHealthModel", "Progress: $progress / $totalProgress")
            }

            override fun pushDataProgressState(code: Int) {
                Log.d("CurrectHealthModel", "Progress State Code: $code")
            }

            override fun pushDataNotStartedLowBattery() {
                Log.e("CurrectHealthModel", "Low battery, data fetch not started")
            }

            override fun getGpsDataProgress(progress: Int) {
                Log.d("CurrectHealthModel", "GPS Data Progress: $progress")
            }
        })

        // BLE 명령 호출
        manager.cmdGet17() // cmdGet17() 호출로 변경
    }
}
