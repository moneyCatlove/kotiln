package com.example.smartring.model

import android.util.Log
import com.smtlink.transferprotocolsdk.ble.BleTransferManager
import com.smtlink.transferprotocolsdk.ble.AnalyticalDataCallBack
import org.json.JSONObject

data class CaloriesPerStepModel(
    val date: String, // 날짜
    val array: List<StepDataForCaloriesPerStepModel> // 10분 간격 기록 배열
)

data class StepDataForCaloriesPerStepModel(
    val time: String, // 시간 (시:분:초)
    val step: Int, // 걸음 수
    val distance: Int, // 거리 (미터)
    val calorie: Int // 칼로리
)

object CaloriesPerStepFetcher {

    fun fetch(manager: BleTransferManager, onResult: (List<CaloriesPerStepModel>?) -> Unit) {
        manager.setAnalyticalDataCallBack(object : AnalyticalDataCallBack {
            override fun jsonObjectData(cmdKey: String?, jsonObject: JSONObject?) {
                if (cmdKey == "GET10" && jsonObject != null) { // cmdGet10 응답 확인
                    try {
                        val caloriesPerStepList = mutableListOf<CaloriesPerStepModel>()
                        val dataArray = jsonObject.getJSONArray("data") // "data" 배열 추출

                        for (i in 0 until dataArray.length()) {
                            val dataObject = dataArray.getJSONObject(i)
                            val date = dataObject.getString("date")
                            val stepDataArray = dataObject.getJSONArray("stepData")

                            val stepDataList = mutableListOf<StepDataForCaloriesPerStepModel>()
                            for (j in 0 until stepDataArray.length()) {
                                val stepDataObject = stepDataArray.getJSONObject(j)
                                val time = stepDataObject.getString("time")
                                val step = stepDataObject.getInt("step")
                                val distance = stepDataObject.getInt("distance")
                                val calorie = stepDataObject.getInt("calorie")

                                val stepData = StepDataForCaloriesPerStepModel(
                                    time = time,
                                    step = step,
                                    distance = distance,
                                    calorie = calorie
                                )
                                stepDataList.add(stepData)
                            }

                            val caloriesPerStepModel = CaloriesPerStepModel(
                                date = date,
                                array = stepDataList
                            )
                            caloriesPerStepList.add(caloriesPerStepModel)
                        }

                        // 결과 반환
                        onResult(caloriesPerStepList)
                    } catch (e: Exception) {
                        Log.e("CaloriesPerStepModel", "Error parsing calories per step data: ${e.message}")
                        onResult(null)
                    }
                }
            }

            override fun pushDataProgress(progress: Int, totalProgress: Int) {
                Log.d("CaloriesPerStepModel", "Progress: $progress / $totalProgress")
            }

            override fun pushDataProgressState(code: Int) {
                Log.d("CaloriesPerStepModel", "Progress State Code: $code")
            }

            override fun pushDataNotStartedLowBattery() {
                Log.e("CaloriesPerStepModel", "Low battery, data fetch not started")
            }

            override fun getGpsDataProgress(progress: Int) {
                Log.d("CaloriesPerStepModel", "GPS Data Progress: $progress")
            }
        })

        // BLE 명령 호출
        manager.cmdGet10()
    }
}
