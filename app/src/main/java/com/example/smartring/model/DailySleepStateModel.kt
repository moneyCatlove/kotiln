package com.example.smartring.model

data class DailySleepStateModel(
    val array: List<SleepStateData>, // 수면 상태 데이터 리스트
)

data class SleepStateData(
    val date: String, // 날짜
    val sleep_state: Int, // 수면 상태 (0=깨어있음, 1=얕은 수면, 2=깊은 수면)
    val time: String, // 시간 (시:분)
    val sleep_time_long: String, // 수면 지속 시간 (시:분)
)
