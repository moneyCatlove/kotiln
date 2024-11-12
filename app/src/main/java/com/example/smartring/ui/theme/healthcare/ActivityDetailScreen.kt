package com.example.smartring.ui.theme.healthcare

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.smartring.R

@Composable
fun ActivityDetailScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // 뒤로가기 버튼
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = "Back",
                tint = Color.Black,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // 제목과 날짜
        Text(text = "활동", fontSize = 20.sp, color = Color(0xFFE57373))
        Text(text = "2024년 5월 12일", fontSize = 14.sp, color = Color.Gray)

        Spacer(modifier = Modifier.height(16.dp))

        // 목표 및 그래프
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, shape = RoundedCornerShape(12.dp))
                .padding(16.dp)
        ) {
            Column {
                Text(text = "활동 목표", fontSize = 16.sp, color = Color.Black)
                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_walk),
                            contentDescription = "걸음 수",
                            tint = Color(0xFFE57373),
                            modifier = Modifier.size(24.dp)
                        )
                        Text(text = "92 / 1000 걸음", fontSize = 14.sp, color = Color.Gray)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_location),
                            contentDescription = "거리",
                            tint = Color(0xFFE57373),
                            modifier = Modifier.size(24.dp)
                        )
                        Text(text = "0.89 / 5.0 Km", fontSize = 14.sp, color = Color.Gray)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_fire),
                            contentDescription = "칼로리",
                            tint = Color(0xFFE57373),
                            modifier = Modifier.size(24.dp)
                        )
                        Text(text = "100 / 300 Kcal", fontSize = 14.sp, color = Color.Gray)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 실시간 측정 데이터
        Text(text = "종합점수", fontSize = 16.sp, color = Color.Black)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, shape = RoundedCornerShape(12.dp))
                .padding(16.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "80", fontSize = 48.sp, color = Color(0xFFE57373))
                Text(text = "매우 좋음", fontSize = 16.sp, color = Color.Gray)
                Spacer(modifier = Modifier.height(16.dp))
                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                    InfoRow(label = "활동 시간", value = "좋음")
                    InfoRow(label = "운동 빈도", value = "매우 좋음")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 수정 버튼
        Button(
            onClick = { /* TODO: 목표 수정 로직 */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "일일 활동 목표 수정")
        }
    }
}
