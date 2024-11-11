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
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry

@Composable
fun OxygenLevelDetailScreen(navController: NavController) {
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
                    tint = Color.Black ,
                    modifier = Modifier.size(20.dp)

                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "혈중 산소",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 날짜 표시
        Text(
            text = "2024년 5월 12일",
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 그래프 표시
        AndroidView(
            factory = { context ->
                BarChart(context).apply {
                    val entries = listOf(
                        BarEntry(0f, 98f),
                        BarEntry(1f, 97f),
                        BarEntry(2f, 98f),
                        BarEntry(3f, 97f),
                        BarEntry(4f, 98f),
                        BarEntry(5f, 98f)
                    )
                    val dataSet = BarDataSet(entries, "혈중 산소").apply {
                        color = android.graphics.Color.parseColor("#42A5F5")
                        valueTextColor = android.graphics.Color.GRAY
                        valueTextSize = 10f
                    }

                    val barData = BarData(dataSet)
                    data = barData

                    xAxis.apply {
                        position = XAxis.XAxisPosition.BOTTOM
                        textColor = android.graphics.Color.GRAY
                        granularity = 1f
                        setDrawGridLines(false)
                    }

                    axisLeft.apply {
                        textColor = android.graphics.Color.GRAY
                        axisMinimum = 0f
                        axisMaximum = 100f
                    }
                    axisRight.isEnabled = false

                    description.isEnabled = false
                    legend.isEnabled = false
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 상세 정보 표시
        InfoRow(label = "평균 혈중 산소", value = "98 %")
        InfoRow(label = "최소 혈중 산소", value = "97 %")
        InfoRow(label = "최대 혈중 산소", value = "99 %")

        Spacer(modifier = Modifier.height(16.dp))

        // 실시간 혈중 산소 측정
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
                Text(text = "실시간 혈중 산소 측정", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "98 %",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF42A5F5)
                    )
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
            listOf("98 %", "98 %", "97 %", "98 %", "99 %", "98 %").forEachIndexed { index, result ->
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

