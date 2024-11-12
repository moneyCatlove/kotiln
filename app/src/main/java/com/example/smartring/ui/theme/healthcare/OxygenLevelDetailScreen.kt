package com.example.smartring.ui.theme.healthcare

import android.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
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
import com.example.smartring.ui.theme.components.InfoRow


@Composable
fun OxygenLevelDetailScreen(navController: NavController) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(androidx.compose.ui.graphics.Color(0xFFF7F7F7))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 뒤로가기 및 상단 제목
        item {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "Back",
                    tint = androidx.compose.ui.graphics.Color.Black,
                    modifier = Modifier.size(20.dp)

                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "혈중 산소", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(text = "2024년 5월 12일 (목)", fontSize = 14.sp, color = androidx.compose.ui.graphics.Color.Gray)
        }

        // BarChart와 데이터
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(androidx.compose.ui.graphics.Color.White, shape = RoundedCornerShape(12.dp))
                    .padding(16.dp)
            ) {
                Column {
                    Text(text = "07/18 08:30", fontSize = 14.sp, color = androidx.compose.ui.graphics.Color.Gray)
                    Text(
                        text = "98 %",
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold,
                        color = androidx.compose.ui.graphics.Color(0xFF42A5F5)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // BarChart
                    AndroidView(
                        factory = { context ->
                            BarChart(context).apply {
                                val entries = listOf(
                                    BarEntry(0f, 98f),
                                    BarEntry(1f, 97f),
                                    BarEntry(2f, 98f),
                                    BarEntry(3f, 98f),
                                    BarEntry(4f, 97f)
                                )
                                val dataSet = BarDataSet(entries, "혈중 산소").apply {
                                    color = Color.parseColor("#42A5F5")
                                    valueTextColor = Color.TRANSPARENT
                                    valueTextSize = 10f
                                }

                                val barData = BarData(dataSet)
                                data = barData

                                xAxis.position = XAxis.XAxisPosition.BOTTOM
                                xAxis.textColor = Color.GRAY
                                xAxis.setDrawGridLines(false)

                                axisLeft.textColor = Color.GRAY
                                axisLeft.axisMinimum = 0f
                                axisLeft.axisMaximum = 100f
                                axisRight.isEnabled = false

                                description.isEnabled = false
                                legend.isEnabled = false
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    )
                }
            }
        }

        // 평균, 최소, 최대 데이터
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(androidx.compose.ui.graphics.Color.White, shape = RoundedCornerShape(12.dp))
                    .padding(16.dp)
            ) {
                InfoRow(label = "평균 혈중 산소", value = "98 %")
                InfoRow(label = "최소 혈중 산소", value = "97 %")
                InfoRow(label = "최대 혈중 산소", value = "99 %")
            }
        }

        // 실시간 혈중 산소 측정
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(androidx.compose.ui.graphics.Color.White, shape = RoundedCornerShape(12.dp))
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "실시간 혈중 산소 측정", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Text(
                        text = "98 %",
                        fontSize = 16.sp,
                        color = androidx.compose.ui.graphics.Color(0xFF42A5F5)
                    )
                }
                Text(text = "2024-08-19 16:48", fontSize = 14.sp, color = androidx.compose.ui.graphics.Color.Gray)

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = { /* 실시간 측정 */ },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                        containerColor = androidx.compose.ui.graphics.Color(0xFFF5F5F5)
                    )
                ) {
                    Text(text = "측정을 시작하려면 클릭하세요", color = androidx.compose.ui.graphics.Color.Gray)
                }
            }
        }

        // 실시간 측정 결과
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(androidx.compose.ui.graphics.Color.White, shape = RoundedCornerShape(12.dp))
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "실시간 측정 결과", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Text(text = "더보기 >", fontSize = 14.sp, color = androidx.compose.ui.graphics.Color.Gray)
                }

                Spacer(modifier = Modifier.height(8.dp))

                repeat(6) { index ->
                    InfoRow(label = "\uD83D\uDCA7 98%", value = "16:48")
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}
