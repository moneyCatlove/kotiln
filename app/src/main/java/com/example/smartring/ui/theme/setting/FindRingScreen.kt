package com.example.smartring.ui.theme.setting

import android.Manifest
import android.bluetooth.BluetoothDevice
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import startBLEScan

@Composable
fun FindRingScreen(navController: NavController, context: Context) {
    var isScanning by remember { mutableStateOf(false) }
    var foundDevices by remember { mutableStateOf(listOf<BluetoothDevice>()) }
    var selectedDevice by remember { mutableStateOf<BluetoothDevice?>(null) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "반지 찾기 화면입니다.")

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (!isScanning) {
                    // Start BLE Scan
                    isScanning = true
                    startBLEScan(context) { device ->
                        // Add the found device to the list
                        if (device !in foundDevices) {
                            foundDevices = foundDevices + device
                        }
                        Log.d("FindRingScreen", "Found device: ${device.name} (${device.address})")
                    }
                }
            }
        ) {
            Text(if (isScanning) "Scanning..." else "Start Scan")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Show found devices
        if (foundDevices.isNotEmpty()) {
            Text(text = "Found Devices:")
            Spacer(modifier = Modifier.height(8.dp))
            foundDevices.forEach { device ->
                Button(
                    onClick = {
                        selectedDevice = device
                        // Connect to selected device
                        if (ActivityCompat.checkSelfPermission(
                                context,
                                Manifest.permission.BLUETOOTH_CONNECT
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return@Button
                        }
                        Log.d("FindRingScreen", "Connecting to ${device.name} (${device.address})")
                        // Replace with your BLEManager's connect function
                        // bleManager.connectToDevice(device.address)
                    },
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    Text(text = "${device.name ?: "Unnamed"} (${device.address})")
                }
            }
        }

        // Show selected device
        if (selectedDevice != null) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Selected Device: ${selectedDevice?.name} (${selectedDevice?.address})")
        }
    }
}
