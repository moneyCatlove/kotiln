package com.example.smartring.model

import android.util.Log
import com.smtlink.transferprotocolsdk.ble.BleTransferManager
import com.smtlink.transferprotocolsdk.ble.AnalyticalDataCallBack
import org.json.JSONObject

data class DeviceDataModel(
    val name: String, // 이름
    val version: String, // 프로토콜 버전
    val screenType: Int, // 화면 유형
    val screenWidth: Int, // 화면 너비
    val screenHeight: Int, // 화면 높이
    val p_num: Int, // 미업로드된 걸음 수 데이터 수
    val p_delta_num: Int, // 미업로드된 걸음 시간 데이터 수
    val goal: Int, // 걸음 목표
    val sex: Int, // 성별
    val height: Int, // 키
    val weight: Int, // 몸무게
    val age: Int, // 나이
    val s_num: Int, // 미업로드된 수면 데이터 수
    val s_delta_num: Int, // 미업로드된 수면 시간 데이터 수
    val start_time: String, // 시작 시간
    val end_time: String, // 종료 시간
    val SIT: Int, // 오래 앉아 있음 알림 시간
    val heart_rate: Int, // 미업로드된 심박수 데이터 수
    val alarmNum: Int, // 알람 개수
    val battery_capacity: Int, // 배터리 용량
    val bt_address: String, // 블루투스 주소
    val software_version: String, // 소프트웨어 버전
    val sim: Int, // SIM 지원 여부
    val barcode: String?, // 장치 바코드
    val project_code: String?, // 프로젝트 식별 코드
    val raise_hand_screen_bright: String, // 손 들기 화면 밝기 설정
    val screen_stays_on: Int, // 화면 항상 켜짐 설정
    val time_format: Int, // 시간 형식
    val do_not_disturb_mode: Int, // 방해 금지 모드
    val turn_on_vibration: Int // 진동 모드
)

object DeviceDataFetcher {

    fun fetch(manager: BleTransferManager, onResult: (DeviceDataModel?) -> Unit) {
        manager.setAnalyticalDataCallBack(object : AnalyticalDataCallBack {
            override fun jsonObjectData(cmdKey: String?, jsonObject: JSONObject?) {
                if (cmdKey == "GET10" && jsonObject != null) { // cmdGet10 응답 확인
                    try {
                        val deviceData = DeviceDataModel(
                            name = jsonObject.getString("name"),
                            version = jsonObject.getString("version"),
                            screenType = jsonObject.getInt("screenType"),
                            screenWidth = jsonObject.getInt("screenWidth"),
                            screenHeight = jsonObject.getInt("screenHeight"),
                            p_num = jsonObject.getInt("p_num"),
                            p_delta_num = jsonObject.getInt("p_delta_num"),
                            goal = jsonObject.getInt("goal"),
                            sex = jsonObject.getInt("sex"),
                            height = jsonObject.getInt("height"),
                            weight = jsonObject.getInt("weight"),
                            age = jsonObject.getInt("age"),
                            s_num = jsonObject.getInt("s_num"),
                            s_delta_num = jsonObject.getInt("s_delta_num"),
                            start_time = jsonObject.getString("start_time"),
                            end_time = jsonObject.getString("end_time"),
                            SIT = jsonObject.getInt("SIT"),
                            heart_rate = jsonObject.getInt("heart_rate"),
                            alarmNum = jsonObject.getInt("alarmNum"),
                            battery_capacity = jsonObject.getInt("battery_capacity"),
                            bt_address = jsonObject.getString("bt_address"),
                            software_version = jsonObject.getString("software_version"),
                            sim = jsonObject.getInt("sim"),
                            barcode = jsonObject.optString("barcode", null),
                            project_code = jsonObject.optString("project_code", null),
                            raise_hand_screen_bright = jsonObject.getString("raise_hand_screen_bright"),
                            screen_stays_on = jsonObject.getInt("screen_stays_on"),
                            time_format = jsonObject.getInt("time_format"),
                            do_not_disturb_mode = jsonObject.getInt("do_not_disturb_mode"),
                            turn_on_vibration = jsonObject.getInt("turn_on_vibration")
                        )

                        // 결과 반환
                        onResult(deviceData)
                    } catch (e: Exception) {
                        Log.e("DeviceDataFetcher", "Error parsing device data: ${e.message}")
                        onResult(null)
                    }
                }
            }

            override fun pushDataProgress(progress: Int, totalProgress: Int) {
                Log.d("DeviceDataFetcher", "Progress: $progress / $totalProgress")
            }

            override fun pushDataProgressState(code: Int) {
                Log.d("DeviceDataFetcher", "Progress State Code: $code")
            }

            override fun pushDataNotStartedLowBattery() {
                Log.e("DeviceDataFetcher", "Low battery, data fetch not started")
            }

            override fun getGpsDataProgress(progress: Int) {
                Log.d("DeviceDataFetcher", "GPS Data Progress: $progress")
            }
        })

        // BLE 명령 호출
        manager.cmdGet0()
    }
}
