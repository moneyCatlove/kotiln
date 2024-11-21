package com.example.smartring.ui.theme.healthcare

import android.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.example.smartring.R
import com.example.smartring.model.CurrectHealthFetcher
import com.example.smartring.model.CurrectHealthModel
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.smtlink.transferprotocolsdk.ble.BleTransferManager

@Composable
fun ActivityCard(navController: NavController, manager: BleTransferManager) {
    var healthData by remember { mutableStateOf<CurrectHealthModel?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    // Fetch data
    LaunchedEffect(Unit) {
        CurrectHealthFetcher.fetch(manager) { data ->
            healthData = data
            isLoading = false
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(androidx.compose.ui.graphics.Color.White, shape = RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Column {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text(
                        text = "활동",
                        fontSize = 20.sp,
                        color = androidx.compose.ui.graphics.Color(0xFFE57373)
                    )
                    Text(
                        text = healthData?.let { "데이터를 불러왔습니다" } ?: "2024년 8월 15일",
                        fontSize = 14.sp,
                        color = androidx.compose.ui.graphics.Color.Gray
                    )
                }
                IconButton(
                    onClick = {
                        navController.navigate("activity_detail_screen")
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_vector_3),
                        contentDescription = "Go to Details",
                        tint = androidx.compose.ui.graphics.Color.Gray,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 그래프 (MPAndroidChart PieChart 사용)
            AndroidView(
                factory = { context ->
                    PieChart(context).apply {
                        val entries = listOf(
                            PieEntry(healthData?.stepCount?.toFloat() ?: 0f, ""), // 걸음 수
                            PieEntry((10000f - (healthData?.stepCount?.toFloat() ?: 0f)), "") // 목표 대비 남은 값
                        )
                        val dataSet = PieDataSet(entries, "").apply {
                            colors = listOf(
                                Color.parseColor("#FF7043"), // 메인 그래프 색상
                                Color.parseColor("#E0E0E0")  // 비활성 영역 색상
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
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // 중앙 점수 및 상태
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "${healthData?.stepCount ?: 0}",
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    color = androidx.compose.ui.graphics.Color.Black
                )
                Text(
                    text = if (healthData?.stepCount ?: 0 > 5000) "좋음" else "평균",
                    fontSize = 16.sp,
                    color = androidx.compose.ui.graphics.Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 하단 추가 정보
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_fire),
                        contentDescription = "칼로리",
                        tint = androidx.compose.ui.graphics.Color(0xFFE57373),
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${healthData?.heartRate ?: 0} BPM",
                        fontSize = 14.sp,
                        color = androidx.compose.ui.graphics.Color.Gray
                    )
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_walk),
                        contentDescription = "걸음 수",
                        tint = androidx.compose.ui.graphics.Color(0xFFE57373),
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${healthData?.stepCount ?: 0} 걸음",
                        fontSize = 14.sp,
                        color = androidx.compose.ui.graphics.Color.Gray
                    )
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_location),
                        contentDescription = "거리",
                        tint = androidx.compose.ui.graphics.Color(0xFFE57373),
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "3.5 Km",
                        fontSize = 14.sp,
                        color = androidx.compose.ui.graphics.Color.Gray
                    )
                }
            }
        }
    }
}
