package com.example.smartring.ui.theme.healthcare

import android.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
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
import com.example.smartring.R
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

@Composable
fun SleepCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(androidx.compose.ui.graphics.Color.White, shape = RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Column {
            // 제목과 날짜
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text(
                        text = "수면",
                        fontSize = 20.sp,
                        color = androidx.compose.ui.graphics.Color(0xFF00BFA5) // 청록색
                    )
                    Text(
                        text = "2024년 8월 15일",
                        fontSize = 14.sp,
                        color = androidx.compose.ui.graphics.Color.Gray
                    )
                }
                Icon(
                    painter = painterResource(id = R.drawable.ic_vector_3),
                    contentDescription = "Go to Details",
                    modifier = Modifier.size(24.dp),
                    tint = androidx.compose.ui.graphics.Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 그래프 (MPAndroidChart PieChart 사용)
            AndroidView(
                factory = { context ->
                    PieChart(context).apply {
                        val entries = listOf(
                            PieEntry(80f, ""), // 80% 수면
                            PieEntry(20f, "") // 나머지 비활성
                        )
                        val dataSet = PieDataSet(entries, "").apply {
                            colors = listOf(
                                Color.parseColor("#00BFA5"), // 활성 영역
                                Color.parseColor("#E0E0E0")  // 비활성 영역
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
                    text = "80",
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    color = androidx.compose.ui.graphics.Color.Black
                )
                Text(
                    text = "좋음",
                    fontSize = 16.sp,
                    color = androidx.compose.ui.graphics.Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "23:48",
                    fontSize = 14.sp,
                    color = androidx.compose.ui.graphics.Color.Gray
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_sleep),
                    contentDescription = "Sleep Icon",
                    modifier = Modifier.size(24.dp),
                    tint = androidx.compose.ui.graphics.Color(0xFF00BFA5)
                )
                Text(
                    text = "05시간 27분",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = androidx.compose.ui.graphics.Color.Black
                )
                Text(
                    text = "05:15",
                    fontSize = 14.sp,
                    color = androidx.compose.ui.graphics.Color.Gray
                )
            }
        }
    }
}
