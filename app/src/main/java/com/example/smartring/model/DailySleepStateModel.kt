package com.example.smartring.model

import android.util.Log
import com.smtlink.transferprotocolsdk.ble.BleTransferManager
import com.smtlink.transferprotocolsdk.ble.AnalyticalDataCallBack
import org.json.JSONObject

data class DailySleepStateModel(
    val array: List<SleepStateData> // 수면 상태 데이터 리스트
)

data class SleepStateData(
    val date: String, // 날짜
    val sleepState: Int, // 수면 상태 (0=깨어있음, 1=얕은 수면, 2=깊은 수면)
    val time: String, // 시간 (시:분)
    val sleepTimeLong: String // 수면 지속 시간 (시:분)
)

object DailySleepStateFetcher {

    fun fetch(manager: BleTransferManager, onResult: (DailySleepStateModel?) -> Unit) {
        manager.setAnalyticalDataCallBack(object : AnalyticalDataCallBack {
            override fun jsonObjectData(cmdKey: String?, jsonObject: JSONObject?) {
                if (cmdKey == "GET13" && jsonObject != null) { // cmdGet13 응답 확인
                    try {
                        val sleepStateDataList = mutableListOf<SleepStateData>()
                        val dataArray = jsonObject.getJSONArray("data") // "data" 배열 추출

                        for (i in 0 until dataArray.length()) {
                            val dataObject = dataArray.getJSONObject(i)
                            val date = dataObject.getString("date") // 날짜
                            val sleepState = dataObject.getInt("sleep_state") // 수면 상태
                            val time = dataObject.getString("time") // 시간
                            val sleepTimeLong = dataObject.getString("sleep_time_long") // 수면 지속 시간

                            val sleepStateData = SleepStateData(
                                date = date,
                                sleepState = sleepState,
                                time = time,
                                sleepTimeLong = sleepTimeLong
                            )
                            sleepStateDataList.add(sleepStateData)
                        }

                        val dailySleepStateModel = DailySleepStateModel(array = sleepStateDataList)

                        // 결과 반환
                        onResult(dailySleepStateModel)
                    } catch (e: Exception) {
                        Log.e("DailySleepStateModel", "Error parsing daily sleep state data: ${e.message}")
                        onResult(null)
                    }
                }
            }

            override fun pushDataProgress(progress: Int, totalProgress: Int) {
                Log.d("DailySleepStateModel", "Progress: $progress / $totalProgress")
            }

            override fun pushDataProgressState(code: Int) {
                Log.d("DailySleepStateModel", "Progress State Code: $code")
            }

            override fun pushDataNotStartedLowBattery() {
                Log.e("DailySleepStateModel", "Low battery, data fetch not started")
            }

            override fun getGpsDataProgress(progress: Int) {
                Log.d("DailySleepStateModel", "GPS Data Progress: $progress")
            }
        })

        // BLE 명령 호출
        manager.cmdGet13()
    }
}
