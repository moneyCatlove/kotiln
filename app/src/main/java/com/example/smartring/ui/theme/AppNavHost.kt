package com.example.smartring.ui.theme

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.smartring.ui.theme.MainScreen
import com.example.smartring.ui.theme.components.BottomNavigationBar
import com.example.smartring.ui.theme.components.TopBar
import com.example.smartring.ui.theme.healthcare.ActivityDetailScreen
import com.example.smartring.ui.theme.healthcare.HealthCareScreen
import com.example.smartring.ui.theme.healthcare.HeartRateDetailScreen
import com.example.smartring.ui.theme.healthcare.OxygenLevelDetailScreen
import com.example.smartring.ui.theme.healthcare.SleepDayDetailScreen
import com.example.smartring.ui.theme.healthcare.StressDayDetailScreen
import com.example.smartring.ui.theme.main.AngelusPrayerScreen
import com.example.smartring.ui.theme.main.CatholicAppsScreen
import com.example.smartring.ui.theme.main.ChurchNewsScreen
import com.example.smartring.ui.theme.main.HolyReadingScreen
import com.example.smartring.ui.theme.main.PrayerScreen
import com.example.smartring.ui.theme.main.RosaryPrayerScreen
import com.example.smartring.ui.theme.main.TodayGospelScreen
import com.example.smartring.ui.theme.setting.FindRingScreen
import com.example.smartring.ui.theme.setting.FirmwareUpgradeScreen
import com.example.smartring.ui.theme.setting.HealthMonitoringSettingsScreen
import com.example.smartring.ui.theme.setting.LowBatteryAlertScreen
import com.example.smartring.ui.theme.setting.SettingsScreen
import com.example.smartring.ui.theme.setting.SystemSettingsScreen
import com.example.smartring.ui.theme.setting.UnitSettingsScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    Scaffold(
        topBar = { TopBar() },
        bottomBar = { BottomNavigationBar(navController = navController) },
        containerColor = Color(0xFFF7F7F7)
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            // 이동 라우터들 선언 하는 페이지
            composable("home") { MainScreen(navController = navController) }
            composable("rosary_prayer") { RosaryPrayerScreen(navController = navController) }
            composable("TodayGospelScreen") { TodayGospelScreen(navController = navController) }
            composable("HolyReadingScreen") { HolyReadingScreen(navController = navController) }
            composable("AngelusPrayerScreen") { AngelusPrayerScreen(navController = navController) }
            composable("prayer") { PrayerScreen( navController = navController) }
            composable("church_news") { ChurchNewsScreen(navController = navController) }
            composable("catholic_apps") { CatholicAppsScreen(navController = navController) }
            composable("health_main") { HealthCareScreen(navController = navController) }


            //setting 라우터들
            composable("find_ring_screen") { FindRingScreen(navController = navController) }
            composable("health_monitoring_screen") { HealthMonitoringSettingsScreen(navController = navController) }
            composable("unit_settings_screen") { UnitSettingsScreen(navController = navController) }
            composable("low_battery_screen") { LowBatteryAlertScreen(navController = navController) }
            composable("firmware_upgrade_screen") { FirmwareUpgradeScreen(navController = navController) }
            composable("system_settings_screen") { SystemSettingsScreen(navController = navController) }
            composable("settings_screen") {SettingsScreen(navController = navController)}

            //healthcare 라우터들
            composable("sleep_day_detail_screen") {SleepDayDetailScreen(navController = navController)}
            composable("heart_rate_detail_screen") { HeartRateDetailScreen(navController = navController) }
            composable("oxygen_level_detail_screen") { OxygenLevelDetailScreen(navController = navController) }
            composable("stress_day_detail_screen") { StressDayDetailScreen(navController = navController) }
            composable("activity_detail_screen") { ActivityDetailScreen(navController = navController) }


        }
    }
}
