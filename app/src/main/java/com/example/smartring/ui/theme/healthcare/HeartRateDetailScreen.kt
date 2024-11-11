package com.example.smartring.ui.theme.healthcare

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.example.smartring.R
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

@Composable
fun HeartRateDetailScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F7F7))
            .padding(16.dp)
    ) {
        // 상단 뒤로가기 버튼과 제목
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "Back",
                    tint = Color.Black,
                    modifier = Modifier.size(20.dp)

                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "심박수",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 그래프 표시
        AndroidView(
            factory = { context ->
                LineChart(context).apply {
                    val entries = listOf(
                        Entry(0f, 70f),
                        Entry(2f, 75f),
                        Entry(4f, 80f),
                        Entry(6f, 90f),
                        Entry(8f, 85f)
                    )
                    val dataSet = LineDataSet(entries, "심박수").apply {
                        color = android.graphics.Color.RED
                        valueTextColor = android.graphics.Color.GRAY
                        setDrawCircles(false)
                        lineWidth = 2f
                    }

                    data = LineData(dataSet)

                    xAxis.apply {
                        textColor = android.graphics.Color.GRAY
                        setDrawGridLines(false)
                    }
                    axisLeft.textColor = android.graphics.Color.GRAY
                    axisRight.isEnabled = false
                    description.isEnabled = false
                    legend.isEnabled = false
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 심박수 정보 섹션
        Column {
            InfoRow(label = "평균 심박수", value = "96 bpm")
            InfoRow(label = "최소 심박수", value = "77 bpm")
            InfoRow(label = "최대 심박수", value = "108 bpm")

            Spacer(modifier = Modifier.height(16.dp))

            // 실시간 심박수 측정
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(text = "실시간 심박수 측정", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "100 bpm", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.Red)
                        Text(text = "2024-08-19 16:48", fontSize = 14.sp, color = Color.Gray)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = { /* 측정 시작 로직 */ },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "측정을 시작하려면 클릭하세요")
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 실시간 측정 결과
            Text(
                text = "실시간 측정 결과",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Column {
                listOf("87 bpm", "93 bpm", "100 bpm", "97 bpm", "104 bpm", "87 bpm", "87 bpm").forEachIndexed { index, result ->
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Text(text = result, fontSize = 14.sp)
                        Text(text = "16:${48 - index}", fontSize = 14.sp, color = Color.Gray)
                    }
                }
            }
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(12.dp))
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontSize = 14.sp, color = Color.Gray)
        Text(text = value, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.Black)
    }
}
