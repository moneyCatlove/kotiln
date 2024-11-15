package com.example.smartring.ui.theme.setting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.smartring.ble.BluetoothManager

@Composable
fun FindRingScreen(navController: NavController, bluetoothManager: BluetoothManager) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "반지 찾기 화면입니다.")

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                try {
                    bluetoothManager.connectToDevice("40:27:24:50:00:20") // 실제 MAC 주소 입력
                } catch (e: Exception) {
                    println("Error connecting to device: ${e.message}")
                }
            }
        ) {
            Text(text = "Connect to Bluetooth Device")
        }
    }
}