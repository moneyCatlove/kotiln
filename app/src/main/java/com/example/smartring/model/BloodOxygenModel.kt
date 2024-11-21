package com.example.smartring.model

import android.util.Log
import com.smtlink.transferprotocolsdk.ble.BleTransferManager
import com.smtlink.transferprotocolsdk.ble.AnalyticalDataCallBack
import org.json.JSONObject

data class BloodOxygenModel(
    val date: String, // 날짜
    val time: String, // 시간
    val bOxygen: Int  // 혈중 산소 값
) {
    companion object {

        fun fetch(
            manager: BleTransferManager,
            onResult: (List<BloodOxygenModel>?) -> Unit
        ) {
            manager.setAnalyticalDataCallBack(object : AnalyticalDataCallBack {
                override fun jsonObjectData(cmdKey: String?, jsonObject: JSONObject?) {
                    if (cmdKey == "GET23" && jsonObject != null) { // cmdGet23 응답 확인
                        try {
                            val bloodOxygenList = mutableListOf<BloodOxygenModel>()
                            val dataArray = jsonObject.getJSONArray("data") // "data" 배열 추출

                            for (i in 0 until dataArray.length()) {
                                val dataObject = dataArray.getJSONObject(i)
                                val date = dataObject.getString("date")
                                val time = dataObject.getString("time")
                                val bOxygen = dataObject.getInt("bOxygen")

                                val bloodOxygenData = BloodOxygenModel(date, time, bOxygen)
                                bloodOxygenList.add(bloodOxygenData)
                            }

                            // 데이터 리스트 반환
                            onResult(bloodOxygenList)
                        } catch (e: Exception) {
                            Log.e("BloodOxygenModel", "Error parsing blood oxygen data: ${e.message}")
                            onResult(null)
                        }
                    }
                }

                override fun pushDataProgress(progress: Int, totalProgress: Int) {
                    Log.d("BloodOxygenModel", "Progress: $progress / $totalProgress")
                }

                override fun pushDataProgressState(code: Int) {
                    Log.d("BloodOxygenModel", "Progress State Code: $code")
                }

                override fun pushDataNotStartedLowBattery() {
                    Log.e("BloodOxygenModel", "Low battery, data fetch not started")
                }

                override fun getGpsDataProgress(progress: Int) {
                    Log.d("BloodOxygenModel", "GPS Data Progress: $progress")
                }
            })

            // BLE 명령 호출
            manager.cmdGet23()
        }
    }
}
