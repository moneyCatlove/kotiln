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
fun FindRingScreen(navController: NavController, BluetoothManager: BluetoothManager) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "반지 찾기 화면입니다.")

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
            },
        ) {
            Text(text = "Connect to Bluetooth Device")
        }
    }
}
