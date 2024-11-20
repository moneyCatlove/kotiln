package com.example.smartring.model

data class HeartRateResponseModel(
    val array: List<HeartRateData>, // 심박수 데이터 리스트
)

data class HeartRateData(
    val date: String, // 날짜
    val time: String, // 시간 (정각, 시:분)
    val heart_rate: Int, // 심박수 값
)
