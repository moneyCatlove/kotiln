package com.example.smartring.ui.theme.healthcare

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
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.example.smartring.ui.theme.components.InfoRow
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

@Composable
fun SleepDayDetailScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(androidx.compose.ui.graphics.Color(0xFFF7F7F7))
            .padding(16.dp)
    ) {
        // 뒤로가기 버튼
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = "Back",
                tint = androidx.compose.ui.graphics.Color.Black
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // LazyColumn을 사용하여 스크롤 가능하게 설정
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                // 상단 날짜 선택 UI
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "수면",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = androidx.compose.ui.graphics.Color.Black
                    )
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "1일",
                            modifier = Modifier
                                .background(androidx.compose.ui.graphics.Color(0xFFE0E0E0))
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                            color = androidx.compose.ui.graphics.Color.Black
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "1주",
                            modifier = Modifier
                                .background(androidx.compose.ui.graphics.Color(0xFFF7F7F7))
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                            color = androidx.compose.ui.graphics.Color.Gray
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "1개월",
                            modifier = Modifier
                                .background(androidx.compose.ui.graphics.Color(0xFFF7F7F7))
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                            color = androidx.compose.ui.graphics.Color.Gray
                        )
                    }
                }
            }

            item {
                // 취침 시간
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(androidx.compose.ui.graphics.Color.White, shape = RoundedCornerShape(12.dp))
                        .padding(16.dp)
                ) {
                    Column {
                        Text(
                            text = "취침 시간",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = androidx.compose.ui.graphics.Color.Black
                        )
                        Text(
                            text = "7시간 31분",
                            fontSize = 36.sp,
                            fontWeight = FontWeight.Bold,
                            color = androidx.compose.ui.graphics.Color(0xFF00BFA5)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // 그래프
                        AndroidView(
                            factory = { context ->
                                LineChart(context).apply {
                                    val entries = listOf(
                                        Entry(0f, 0f),
                                        Entry(1f, 1f),
                                        Entry(2f, 0.5f),
                                        Entry(3f, 1.5f),
                                        Entry(4f, 1f)
                                    )
                                    val dataSet = LineDataSet(entries, "").apply {
                                        color = android.graphics.Color.parseColor("#00BFA5")
                                        valueTextColor = android.graphics.Color.TRANSPARENT
                                        lineWidth = 2f
                                        setDrawCircles(false)
                                    }

                                    data = LineData(dataSet)

                                    xAxis.position = XAxis.XAxisPosition.BOTTOM
                                    xAxis.setDrawGridLines(false)
                                    axisLeft.setDrawGridLines(false)
                                    axisRight.isEnabled = false
                                    description.isEnabled = false
                                    legend.isEnabled = false
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp)
                        )
                    }
                }
            }

            item {
                // 수면 데이터
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(androidx.compose.ui.graphics.Color.White, shape = RoundedCornerShape(12.dp))
                        .padding(16.dp)
                ) {
                    InfoRow(label = "깊은 수면", value = "2시간 11분", color = androidx.compose.ui.graphics.Color(0xFF00BFA5))
                    InfoRow(label = "코어 수면", value = "2시간 36분", color = androidx.compose.ui.graphics.Color(0xFF64B5F6))
                    InfoRow(label = "REM 수면", value = "0시간 21분", color = androidx.compose.ui.graphics.Color(0xFF42A5F5))
                    InfoRow(label = "비수면", value = "0시간 39분", color = androidx.compose.ui.graphics.Color(0xFFBDBDBD))
                }
            }

            item {
                // 종합 점수 섹션
                SummaryScoreCard()
            }
        }
    }
}

@Composable
fun SummaryScoreCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(androidx.compose.ui.graphics.Color.White, shape = RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // Pie Chart
            AndroidView(
                factory = { context ->
                    PieChart(context).apply {
                        val entries = listOf(
                            PieEntry(80f, ""),
                            PieEntry(20f, "")
                        )
                        val dataSet = PieDataSet(entries, "").apply {
                            colors = listOf(
                                android.graphics.Color.parseColor("#42A5F5"),
                                android.graphics.Color.parseColor("#E0E0E0")
                            )
                            sliceSpace = 2f
                            setDrawValues(false)
                        }

                        val pieData = PieData(dataSet)
                        data = pieData

                        holeRadius = 80f
                        transparentCircleRadius = 85f
                        setDrawEntryLabels(false)
                        description.isEnabled = false
                        legend.isEnabled = false
                        isRotationEnabled = false
                    }
                },
                modifier = Modifier.size(120.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // 중앙 점수
            Text(
                text = "80",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = androidx.compose.ui.graphics.Color(0xFF42A5F5)
            )
            Text(
                text = "매우 좋음",
                fontSize = 14.sp,
                color = androidx.compose.ui.graphics.Color.Gray
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 상태별 막대 그래프
            Column(modifier = Modifier.fillMaxWidth()) {
                ProgressRow(label = "수면시간", value = "좋음", progress = 0.8f, color = androidx.compose.ui.graphics.Color.Green)
                ProgressRow(label = "수면 효율성", value = "보통", progress = 0.6f, color = androidx.compose.ui.graphics.Color.Blue)
                ProgressRow(label = "깊은 수면", value = "좋음", progress = 0.9f, color = androidx.compose.ui.graphics.Color.Cyan)
            }
        }
    }
}

@Composable
fun ProgressRow(label: String, value: String, progress: Float, color: androidx.compose.ui.graphics.Color) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = label, fontSize = 14.sp, color = androidx.compose.ui.graphics.Color.Black)
            Text(text = value, fontSize = 14.sp, color = androidx.compose.ui.graphics.Color.Gray)
        }
        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .background(androidx.compose.ui.graphics.Color(0xFFE0E0E0), shape = RoundedCornerShape(4.dp))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(fraction = progress)
                    .height(8.dp)
                    .background(color, shape = RoundedCornerShape(4.dp))
            )
        }
    }
}
