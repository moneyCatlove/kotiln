package com.example.smartring.model

data class CurrectHealthModel(
    val heart_rate: Int, // 현재 심박수
    val sleep_state: Int, // 현재 수면 상태 (0=깨어있음, 1=얕은 수면, 2=깊은 수면)
    val step_count: Int, // 현재 걸음 수
)
