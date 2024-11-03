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
import com.example.smartring.ui.theme.healthcare.HealthCareScreen
import com.example.smartring.ui.theme.main.AngelusPrayerScreen
import com.example.smartring.ui.theme.main.CatholicAppsScreen
import com.example.smartring.ui.theme.main.ChurchNewsScreen
import com.example.smartring.ui.theme.main.HolyReadingScreen
import com.example.smartring.ui.theme.main.PrayerScreen
import com.example.smartring.ui.theme.main.RosaryPrayerScreen
import com.example.smartring.ui.theme.main.TodayGospelScreen

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
            // 이동 라우터들 선언
            composable("home") { MainScreen(navController = navController) }
            composable("rosary_prayer") { RosaryPrayerScreen(navController = navController) }
            composable("TodayGospelScreen") { TodayGospelScreen(navController = navController) }
            composable("HolyReadingScreen") { HolyReadingScreen(navController = navController) }
            composable("AngelusPrayerScreen") { AngelusPrayerScreen(navController = navController) }
            composable("prayer") { PrayerScreen( navController = navController) }
            composable("church_news") { ChurchNewsScreen(navController = navController) }
            composable("catholic_apps") { CatholicAppsScreen(navController = navController) }
            composable("health_main") { HealthCareScreen(navController = navController) }



        }
    }
}
