package com.example.smartring.model

data class DailySleepModel(
    val array: List<SleepData>
) {
    data class SleepData(
        val deepSleep: String,
        val lightSleep: String,
        val date: String
    )
}
