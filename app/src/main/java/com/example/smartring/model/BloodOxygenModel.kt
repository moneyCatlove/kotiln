package com.example.smartring.model

data class BloodOxygenModel(
    val date: String, // 날짜
    val time: String, // 시간 (시:분:초)
    val BOxygen: Int, // 혈중 산소 값
)
