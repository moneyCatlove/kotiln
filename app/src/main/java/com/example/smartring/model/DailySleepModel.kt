package com.example.smartring.model

data class DailySleepModel(
    val array: List<SleepData>, // 하루 동안의 수면 데이터 리스트
)

data class SleepData(
    val date: String, // 날짜
    val deep_sleep: String, // 깊은 수면 시간 (시:분)
    val light_sleep: String, // 얕은 수면 시간 (시:분)
)
