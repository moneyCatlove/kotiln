package com.example.smartring.model

data class CaloriesPerStepModel(
    val date: String, // 날짜
    val array: List<StepDataForCaloriesPerStepModel>, // 10분 간격 기록 배열
)

data class StepDataForCaloriesPerStepModel(
    val time: String, // 시간 (시:분:초)
    val step: Int, // 걸음 수
    val distance: Int, // 거리 (미터)
    val calorie: Int, // 칼로리
)
