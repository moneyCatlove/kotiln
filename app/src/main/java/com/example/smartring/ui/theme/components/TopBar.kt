package com.example.smartring.ui.theme.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartring.R
import com.example.smartring.ble.BluetoothManager
import com.example.smartring.ui.theme.components.ConnectionStatus

@Composable
fun TopBar(bluetoothManager: BluetoothManager) {
    val isConnected = remember { mutableStateOf(false) }
    bluetoothManager.setConnectionStatusListener { isConnected.value = it }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        IconButton(
            onClick = { /* TODO: Drawer open */ },
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_meun),
                contentDescription = "Menu",
                modifier = Modifier.size(28.dp)
            )
        }

        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Catholic",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
            ConnectionStatus(
                isConnected = isConnected.value, // .value 전달
                batteryLevel = 80
            )
        }
    }
}
