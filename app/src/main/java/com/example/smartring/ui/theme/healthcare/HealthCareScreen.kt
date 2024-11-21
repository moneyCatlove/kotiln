package com.example.smartring.ui.theme.healthcare

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun HealthCareScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F7F7))
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            item {
                SleepCard(navController = navController)
            }
            item {
                HeartRateCard(navController = navController)
            }
            item {
                OxygenLevelCard(navController = navController)
            }
            item {
                StressCard(navController = navController)
            }
            item {
                ActivityCard(navController = navController)
            }
        }
    }
}

@Composable
fun SleepCard(navController: NavController) {
    CardTemplate(
        title = "수면 데이터",
        date = "2024년 8월 19일",
        content = "오늘 수면 시간: 7시간 30분"
    )
}

@Composable
fun HeartRateCard(navController: NavController) {
    CardTemplate(
        title = "심박수 데이터",
        date = "2024년 8월 19일",
        content = "현재 심박수: 72 bpm"
    )
}

@Composable
fun OxygenLevelCard(navController: NavController) {
    CardTemplate(
        title = "혈중 산소 데이터",
        date = "2024년 8월 19일",
        content = "현재 산소 농도: 98%"
    )
}

@Composable
fun StressCard(navController: NavController) {
    CardTemplate(
        title = "스트레스 데이터",
        date = "2024년 8월 19일",
        content = "현재 스트레스 레벨: 낮음"
    )
}

@Composable
fun ActivityCard(navController: NavController) {
    CardTemplate(
        title = "활동 데이터",
        date = "2024년 8월 19일",
        content = "걸음 수: 8,500 걸음"
    )
}

@Composable
fun CardTemplate(title: String, date: String, content: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(16.dp)
        ) {
            Text(text = title, fontSize = 20.sp)
            Text(text = date, fontSize = 14.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = content, fontSize = 16.sp)
        }
    }
}
