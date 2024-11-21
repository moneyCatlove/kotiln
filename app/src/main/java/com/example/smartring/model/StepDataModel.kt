package com.example.smartring.model

import android.util.Log
import com.smtlink.transferprotocolsdk.ble.BleTransferManager
import com.smtlink.transferprotocolsdk.ble.AnalyticalDataCallBack
import org.json.JSONObject

data class StepDataModel(
    val array: List<StepData> // 걸음 데이터 리스트
)

data class StepData(
    val date: String, // 날짜
    val step: Int, // 걸음 수
    val distance: Int, // 거리 (미터)
    val calorie: Int // 칼로리
)

object StepDataFetcher {

    fun fetch(manager: BleTransferManager, onResult: (StepDataModel?) -> Unit) {
        manager.setAnalyticalDataCallBack(object : AnalyticalDataCallBack {
            override fun jsonObjectData(cmdKey: String?, jsonObject: JSONObject?) {
                if (cmdKey == "GET10" && jsonObject != null) { // cmdGet10 응답 확인
                    try {
                        val stepDataList = mutableListOf<StepData>()
                        val dataArray = jsonObject.getJSONArray("data") // "data" 배열 추출

                        for (i in 0 until dataArray.length()) {
                            val dataObject = dataArray.getJSONObject(i)
                            val date = dataObject.getString("date") // 날짜
                            val step = dataObject.getInt("step") // 걸음 수
                            val distance = dataObject.getInt("distance") // 거리
                            val calorie = dataObject.getInt("calorie") // 칼로리

                            val stepData = StepData(
                                date = date,
                                step = step,
                                distance = distance,
                                calorie = calorie
                            )
                            stepDataList.add(stepData)
                        }

                        val stepDataModel = StepDataModel(array = stepDataList)

                        // 결과 반환
                        onResult(stepDataModel)
                    } catch (e: Exception) {
                        Log.e("StepDataFetcher", "Error parsing step data: ${e.message}")
                        onResult(null)
                    }
                }
            }

            override fun pushDataProgress(progress: Int, totalProgress: Int) {
                Log.d("StepDataFetcher", "Progress: $progress / $totalProgress")
            }

            override fun pushDataProgressState(code: Int) {
                Log.d("StepDataFetcher", "Progress State Code: $code")
            }

            override fun pushDataNotStartedLowBattery() {
                Log.e("StepDataFetcher", "Low battery, data fetch not started")
            }

            override fun getGpsDataProgress(progress: Int) {
                Log.d("StepDataFetcher", "GPS Data Progress: $progress")
            }
        })

        // BLE 명령 호출
        manager.cmdGet10()
    }
}
