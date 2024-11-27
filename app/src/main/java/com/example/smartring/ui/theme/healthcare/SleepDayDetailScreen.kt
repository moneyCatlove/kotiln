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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.smartring.controller.DailySleepController
import com.example.smartring.model.DailySleepModel
import com.example.smartring.model.DailySleepStateModel
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
fun SleepDayDetailScreen(navController: NavController, controller: DailySleepController) {
    var totalSleepData by remember { mutableStateOf<DailySleepModel?>(null) }
    var sleepStateData by remember { mutableStateOf<DailySleepStateModel?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    // 데이터 가져오기
    LaunchedEffect(Unit) {
        controller.fetchDailySleepData { data ->
            totalSleepData = data
            isLoading = false
        }
        controller.fetchDailySleepStateData { data ->
            sleepStateData = data
            isLoading = false
        }
    }

    if (isLoading) {
        Text(
            text = "데이터 로딩 중...",
            modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center)
        )
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(androidx.compose.ui.graphics.Color(0xFFF7F7F7))
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 뒤로가기 버튼
            item {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_back),
                        contentDescription = "Back",
                        tint = androidx.compose.ui.graphics.Color.Black,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            // 상단 제목 및 날짜 선택
            item {
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
                    Row {
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

            // 취침 시간과 그래프
            item {
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
                            text = totalSleepData?.array?.firstOrNull()?.deepSleep ?: "0시간 0분",
                            fontSize = 36.sp,
                            fontWeight = FontWeight.Bold,
                            color = androidx.compose.ui.graphics.Color(0xFF00BFA5)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        AndroidView(
                            factory = { context ->
                                LineChart(context).apply {
                                    val entries = sleepStateData?.array?.mapIndexed { index, state ->
                                        Entry(index.toFloat(), state.sleepState.toFloat())
                                    } ?: emptyList()

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

            // 수면 상태
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(androidx.compose.ui.graphics.Color.White, shape = RoundedCornerShape(12.dp))
                        .padding(16.dp)
                ) {
                    sleepStateData?.array?.forEach { state ->
                        InfoRow(
                            label = when (state.sleepState) {
                                0 -> "깨어있음"
                                1 -> "얕은 수면"
                                2 -> "깊은 수면"
                                else -> "알 수 없음"
                            },
                            value = state.sleepTimeLong,
                            color = androidx.compose.ui.graphics.Color(0xFF00BFA5)
                        )
                    }
                }
            }

            // 종합 점수 섹션 만들어야됨
//            item {
//                SummaryScoreCard()
//            }
        }
    }
}
