package com.example.smartring.model

import android.util.Log
import com.smtlink.transferprotocolsdk.ble.BleTransferManager
import com.smtlink.transferprotocolsdk.ble.AnalyticalDataCallBack
import org.json.JSONObject

data class DailySleepModel(
    val array: List<SleepData> // 하루 동안의 수면 데이터 리스트
)

data class SleepData(
    val date: String, // 날짜
    val deepSleep: String, // 깊은 수면 시간 (시:분)
    val lightSleep: String // 얕은 수면 시간 (시:분)
)

object DailySleepFetcher {

    fun fetch(manager: BleTransferManager, onResult: (DailySleepModel?) -> Unit) {
        manager.setAnalyticalDataCallBack(object : AnalyticalDataCallBack {
            override fun jsonObjectData(cmdKey: String?, jsonObject: JSONObject?) {
                if (cmdKey == "GET12" && jsonObject != null) { // cmdGet12 응답 확인
                    try {
                        val sleepDataList = mutableListOf<SleepData>()
                        val dataArray = jsonObject.getJSONArray("data") // "data" 배열 추출

                        for (i in 0 until dataArray.length()) {
                            val dataObject = dataArray.getJSONObject(i)
                            val date = dataObject.getString("date") // 날짜
                            val deepSleep = dataObject.getString("deep_sleep") // 깊은 수면 시간
                            val lightSleep = dataObject.getString("light_sleep") // 얕은 수면 시간

                            val sleepData = SleepData(
                                date = date,
                                deepSleep = deepSleep,
                                lightSleep = lightSleep
                            )
                            sleepDataList.add(sleepData)
                        }

                        val dailySleepModel = DailySleepModel(array = sleepDataList)

                        // 결과 반환
                        onResult(dailySleepModel)
                    } catch (e: Exception) {
                        Log.e("DailySleepModel", "Error parsing daily sleep data: ${e.message}")
                        onResult(null)
                    }
                }
            }

            override fun pushDataProgress(progress: Int, totalProgress: Int) {
                Log.d("DailySleepModel", "Progress: $progress / $totalProgress")
            }

            override fun pushDataProgressState(code: Int) {
                Log.d("DailySleepModel", "Progress State Code: $code")
            }

            override fun pushDataNotStartedLowBattery() {
                Log.e("DailySleepModel", "Low battery, data fetch not started")
            }

            override fun getGpsDataProgress(progress: Int) {
                Log.d("DailySleepModel", "GPS Data Progress: $progress")
            }
        })

        // BLE 명령 호출
        manager.cmdGet12()
    }
}
