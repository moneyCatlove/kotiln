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
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.example.smartring.R

@Composable
fun SleepDayDetailScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F7F7))
            .padding(16.dp)
    ) {
        // 상단 바
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
                text = "수면",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 취침 시간
        Text(
            text = "취침 시간",
            fontSize = 14.sp,
            color = Color.Gray
        )
        Text(
            text = "7시간 31분",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 그래프
        AndroidView(
            factory = { context ->
                LineChart(context).apply {
                    val entries = listOf(
                        Entry(0f, 1f),
                        Entry(2f, 2f),
                        Entry(4f, 1.5f),
                        Entry(6f, 2.2f)
                    )
                    val dataSet = LineDataSet(entries, "수면 단계").apply {
                        color = android.graphics.Color.BLUE
                        setDrawCircles(false)
                        lineWidth = 2f
                    }

                    data = LineData(dataSet)

                    xAxis.position = XAxis.XAxisPosition.BOTTOM
                    xAxis.textColor = android.graphics.Color.GRAY
                    axisLeft.textColor = android.graphics.Color.GRAY
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

        // 수면 세부 정보
        SleepDetailsRow("깊은 수면", "2시간 11분", Color(0xFF00BFA5))
        SleepDetailsRow("코어 수면", "2시간 36분", Color(0xFF64B5F6))
        SleepDetailsRow("REM 수면", "0시간 21분", Color(0xFFFFA726))
        SleepDetailsRow("비수면", "0시간 39분", Color(0xFFE57373))
    }
}

@Composable
fun SleepDetailsRow(title: String, duration: String, color: Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(16.dp)
                .background(color = color, shape = RoundedCornerShape(50))
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = title,
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = duration,
            fontSize = 14.sp,
            color = Color.Gray
        )
    }
}
