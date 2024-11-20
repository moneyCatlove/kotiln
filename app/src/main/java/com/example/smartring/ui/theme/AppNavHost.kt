package com.example.smartring.ui.theme

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.smartring.ble.BluetoothManager
import com.example.smartring.ui.theme.components.BottomNavigationBar
import com.example.smartring.ui.theme.components.TopBar
import com.example.smartring.ui.theme.healthcare.*
import com.example.smartring.ui.theme.main.*
import com.example.smartring.ui.theme.setting.*

@Composable
fun AppNavHost(bluetoothManager: BluetoothManager) {
    // 네비게이션 컨트롤러 초기화
    val navController = rememberNavController()

    // Scaffold를 사용하여 상단바와 하단바 포함
    Scaffold(
        topBar = { TopBar(bluetoothManager = bluetoothManager) },
        bottomBar = { BottomNavigationBar(navController = navController) },
        containerColor = Color(0xFFF7F7F7) // 배경색 설정
    ) { innerPadding ->
        // 네비게이션 호스트
        NavHost(
            navController = navController,
            startDestination = "home", // 초기 화면
            modifier = Modifier.padding(innerPadding) // Scaffold 패딩 추가
        ) {
            // Main Screens
            composable("home") { MainScreen(navController = navController) }
            composable("rosary_prayer") { RosaryPrayerScreen(navController = navController) }
            composable("TodayGospelScreen") { TodayGospelScreen(navController = navController) }
            composable("HolyReadingScreen") { HolyReadingScreen(navController = navController) }
            composable("AngelusPrayerScreen") { AngelusPrayerScreen(navController = navController) }
            composable("prayer") { PrayerScreen(navController = navController) }
            composable("church_news") { ChurchNewsScreen(navController = navController) }
            composable("catholic_apps") { CatholicAppsScreen(navController = navController) }

            // HealthCare Screens
            composable("health_main") { HealthCareScreen(navController = navController) }
            composable("sleep_day_detail_screen") { SleepDayDetailScreen(navController = navController) }
            composable("heart_rate_detail_screen") { HeartRateDetailScreen(navController = navController) }
            composable("oxygen_level_detail_screen") { OxygenLevelDetailScreen(navController = navController) }
            composable("stress_day_detail_screen") { StressDayDetailScreen(navController = navController) }
            composable("activity_detail_screen") { ActivityDetailScreen(navController = navController) }

            // Setting Screens
            composable("find_ring_screen") {
                FindRingScreen(
                    navController = navController,
                    bluetoothManager = bluetoothManager
                )
            }
            composable("health_monitoring_screen") { HealthMonitoringSettingsScreen(navController = navController) }
            composable("unit_settings_screen") { UnitSettingsScreen(navController = navController) }
            composable("low_battery_screen") { LowBatteryAlertScreen(navController = navController) }
            composable("firmware_upgrade_screen") { FirmwareUpgradeScreen(navController = navController) }
            composable("system_settings_screen") { SystemSettingsScreen(navController = navController) }
            composable("settings_screen") { SettingsScreen(navController = navController) }
        }
    }
}
