package com.example.smartring

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import com.example.smartring.ble.PermissionUtils
import com.example.smartring.ui.theme.AppNavHost
import com.example.smartring.ui.theme.SmartRingTheme

class MainActivity : ComponentActivity() {

    // 권한 요청 런처
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val allGranted = permissions.all { it.value }
            if (!allGranted) {
                // 권한 거부 시 처리
                Toast.makeText(this, "필수 권한이 필요합니다. 앱을 종료합니다.", Toast.LENGTH_LONG).show()
                finish()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 권한 확인 및 요청
        if (!PermissionUtils.checkPermissions(this)) {
            PermissionUtils.requestPermissionsWithLauncher(this, requestPermissionLauncher)
        }

        // Compose 시작
        setContent {
            SmartRingTheme {
                AppNavHost()
            }
        }
    }
}
