package com.example.smartring.model

import android.util.Log
import com.smtlink.transferprotocolsdk.ble.BleTransferManager
import com.smtlink.transferprotocolsdk.ble.AnalyticalDataCallBack
import org.json.JSONObject

data class HeartRateResponseModel(
    val array: List<HeartRateData> // 심박수 데이터 리스트
)

data class HeartRateData(
    val date: String, // 날짜
    val time: String, // 시간 (정각, 시:분)
    val heartRate: Int // 심박수 값
)

object HeartRateFetcher {

    fun fetch(manager: BleTransferManager, onResult: (HeartRateResponseModel?) -> Unit) {
        manager.setAnalyticalDataCallBack(object : AnalyticalDataCallBack {
            override fun jsonObjectData(cmdKey: String?, jsonObject: JSONObject?) {
                if (cmdKey == "GET14" && jsonObject != null) { // cmdGet14 응답 확인
                    try {
                        val heartRateList = mutableListOf<HeartRateData>()
                        val dataArray = jsonObject.getJSONArray("data") // "data" 배열 추출

                        for (i in 0 until dataArray.length()) {
                            val dataObject = dataArray.getJSONObject(i)
                            val date = dataObject.getString("date") // 날짜
                            val time = dataObject.getString("time") // 시간
                            val heartRate = dataObject.getInt("heart_rate") // 심박수 값

                            val heartRateData = HeartRateData(
                                date = date,
                                time = time,
                                heartRate = heartRate
                            )
                            heartRateList.add(heartRateData)
                        }

                        val heartRateResponseModel = HeartRateResponseModel(array = heartRateList)

                        // 결과 반환
                        onResult(heartRateResponseModel)
                    } catch (e: Exception) {
                        Log.e("HeartRateFetcher", "Error parsing heart rate data: ${e.message}")
                        onResult(null)
                    }
                }
            }

            override fun pushDataProgress(progress: Int, totalProgress: Int) {
                Log.d("HeartRateFetcher", "Progress: $progress / $totalProgress")
            }

            override fun pushDataProgressState(code: Int) {
                Log.d("HeartRateFetcher", "Progress State Code: $code")
            }

            override fun pushDataNotStartedLowBattery() {
                Log.e("HeartRateFetcher", "Low battery, data fetch not started")
            }

            override fun getGpsDataProgress(progress: Int) {
                Log.d("HeartRateFetcher", "GPS Data Progress: $progress")
            }
        })

        // BLE 명령 호출
        manager.cmdGet14()
    }
}
