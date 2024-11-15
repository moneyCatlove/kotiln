package com.example.smartring.ui.theme.components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BluetoothViewModel : ViewModel() {
    private val _isConnected = MutableStateFlow(false)
    val isConnected: StateFlow<Boolean> = _isConnected

    private val _batteryLevel = MutableStateFlow(0)
    val batteryLevel: StateFlow<Int> = _batteryLevel

    // 연결 상태 변경
    fun updateConnectionStatus(connected: Boolean) {
        viewModelScope.launch {
            _isConnected.emit(connected)
        }
    }

    // 배터리 수준 업데이트
    fun updateBatteryLevel(level: Int) {
        viewModelScope.launch {
            _batteryLevel.emit(level)
        }
    }
}
