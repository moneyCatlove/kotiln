package com.example.smartring.ui.theme.healthcare

import androidx.compose.foundation.layout.*
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
fun StressDayDetailScreen(navController: NavController) {
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

        // 제목 및 날짜
        Text(text = "스트레스", fontSize = 20.sp, color = Color(0xFF66BB6A))
        Text(text = "2024년 8월 15일", fontSize = 14.sp, color = Color.Gray)

        Spacer(modifier = Modifier.height(16.dp))

        // 상세 정보
        InfoRow(label = "평균 스트레스", value = "42 %")
        InfoRow(label = "최소 스트레스", value = "30 %")
        InfoRow(label = "최대 스트레스", value = "49 %")

        Spacer(modifier = Modifier.height(16.dp))

        // 실시간 스트레스 측정
        Text(
            text = "실시간 스트레스 측정",
            fontSize = 16.sp,
            color = Color(0xFF66BB6A)
        )
        Text(
            text = "2024-08-19 16:48",
            fontSize = 14.sp,
            color = Color.Gray
        )
        Text(
            text = "45 %",
            fontSize = 24.sp,
            color = Color(0xFF66BB6A),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 실시간 측정 결과 목록
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(text = "실시간 측정 결과", fontSize = 16.sp, color = Color.Black)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "\uD83C\uDF31 44 %", fontSize = 14.sp, color = Color(0xFF66BB6A))
                Text(text = "16:48", fontSize = 14.sp, color = Color.Gray)
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "\uD83C\uDF31 35 %", fontSize = 14.sp, color = Color(0xFF66BB6A))
                Text(text = "16:21", fontSize = 14.sp, color = Color.Gray)
            }
            // 추가 데이터는 동일 형식으로 추가 가능
        }
    }
}