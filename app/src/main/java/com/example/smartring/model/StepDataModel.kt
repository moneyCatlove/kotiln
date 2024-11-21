package com.example.smartring.model

data class StepDataModel(
    val array: List<StepData>,
)

data class StepData(
    val date: String, // 날짜
    val step: Int, // 걸음 수
    val distance: Int, // 거리 (미터)
    val calorie: Int, // 칼로리
)
