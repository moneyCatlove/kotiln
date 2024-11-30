package com.example.smartring.controller

import com.example.smartring.model.DailySleepStateModel
import com.example.smartring.model.SleepStateData
import com.smtlink.transferprotocolsdk.ble.AnalyticalDataCallBack
import com.smtlink.transferprotocolsdk.ble.BleTransferManager
import org.json.JSONObject

class DailySleepStateController(private val manager: BleTransferManager) {

    fun fetchDailySleepStateData(callback: (DailySleepStateModel?) -> Unit) {
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
                        callback(dailySleepStateModel)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        callback(null)
                    }
                }
            }

            override fun pushDataProgress(progress: Int, totalProgress: Int) {}
            override fun pushDataProgressState(code: Int) {}
            override fun pushDataNotStartedLowBattery() {}
            override fun getGpsDataProgress(progress: Int) {}
        })

        // BLE 명령 호출
        manager.cmdGet13()
    }
}
