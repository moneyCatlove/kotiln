package com.example.smartring.controller

import android.util.Log
import com.example.smartring.model.DailySleepModel
import com.smtlink.transferprotocolsdk.ble.AnalyticalDataCallBack
import com.smtlink.transferprotocolsdk.ble.BleTransferManager
import com.smtlink.transferprotocolsdk.Protocols
import org.json.JSONObject

class DailySleepController(private val manager: BleTransferManager) {

    fun fetchDailySleepData(callback: (DailySleepModel?) -> Unit) {
        // AnalyticalDataCallBack 인터페이스를 구현한 객체를 전달
        manager.setAnalyticalDataCallBack(object : AnalyticalDataCallBack {
            override fun jsonObjectData(cmdKey: String?, jsonObject: JSONObject?) {
                if (cmdKey == Protocols.GET12 && jsonObject != null) { // cmdGet12 응답 확인
                    try {
                        val sleepDataList = mutableListOf<DailySleepModel.SleepData>()
                        val dataArray = jsonObject.getJSONArray("data") // "data" 배열 추출

                        for (i in 0 until dataArray.length()) {
                            val dataObject = dataArray.getJSONObject(i)
                            val date = dataObject.getString("date") // 날짜
                            val deepSleep = dataObject.getString("deep_sleep") // 깊은 수면 시간
                            val lightSleep = dataObject.getString("light_sleep") // 얕은 수면 시간

                            val sleepData = DailySleepModel.SleepData(
                                date = date,
                                deepSleep = deepSleep,
                                lightSleep = lightSleep
                            )
                            sleepDataList.add(sleepData)
                        }

                        val dailySleepModel = DailySleepModel(array = sleepDataList)

                        // 결과 반환
                        callback(dailySleepModel)
                    } catch (e: Exception) {
                        Log.e("DailySleepController", "Error parsing daily sleep data: ${e.message}")
                        callback(null)
                    }
                }
            }

            override fun pushDataProgress(progress: Int, totalProgress: Int) {
                Log.d("DailySleepController", "Progress: $progress / $totalProgress")
            }

            override fun pushDataProgressState(code: Int) {
                Log.d("DailySleepController", "Progress State Code: $code")
            }

            override fun pushDataNotStartedLowBattery() {
                Log.e("DailySleepController", "Low battery, data fetch not started")
            }

            override fun getGpsDataProgress(progress: Int) {
                Log.d("DailySleepController", "GPS Data Progress: $progress")
            }
        })

        // BLE 장치로부터 데이터를 요청
        manager.cmdGet12()
    }
}
