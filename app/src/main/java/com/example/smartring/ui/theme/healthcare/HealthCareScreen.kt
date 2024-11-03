package com.example.smartring.ui.theme.healthcare

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.smartring.ui.theme.components.ConnectionStatus

@Composable
fun HealthCareScreen(navController: NavController) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F7F7))
            .padding(horizontal = 16.dp)
    ) {
        item {
            TopSection()
        }
        item {
            SleepCard()
        }
        item {
            HeartRateCard()
        }
        item {
            BloodOxygenCard()
        }
        item {
            StressCard()
        }
        item {
            ActivityCard()
        }
    }
}

@Composable
fun TopSection() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        Text("헬스케어", fontSize = 24.sp)
        ConnectionStatus()
    }
}

@Composable
fun SleepCard() {
    // 수면 데이터 그래프
    CardTemplate(
        title = "수면",
        date = "2024년 8월 15일",
        content = "수면 분석 내용 표시"
    )
}

@Composable
fun HeartRateCard() {
    // 심박수 데이터 그래프 표시
    CardTemplate(
        title = "심박수",
        date = "2024년 8월 15일",
        content = "심박수 데이터 표시"
    )
}

@Composable
fun BloodOxygenCard() {
    // 혈중 산소 데이터 그래프 표시
    CardTemplate(
        title = "혈중 산소",
        date = "2024년 8월 15일",
        content = "혈중 산소 데이터 표시"
    )
}

@Composable
fun StressCard() {
    // 스트레스 데이터 그래프
    CardTemplate(
        title = "스트레스",
        date = "2024년 8월 15일",
        content = "스트레스 데이터 표시"
    )
}

@Composable
fun ActivityCard() {
    // 활동 데이터 그래프 표시
    CardTemplate(
        title = "활동",
        date = "2024년 8월 15일",
        content = "활동 데이터 표시"
    )
}

@Composable
fun CardTemplate(title: String, date: String, content: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text(text = title, fontSize = 20.sp)
        Text(text = date, fontSize = 14.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = content, fontSize = 16.sp)
    }
}
