package com.example.smartring.ui.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
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
fun FeatureList(navController: NavController, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFFF7F7F7))
            .padding(10.dp)
    ) {
        FeatureItem("묵주기도", "묵주기도 단수 기록", R.drawable.ic_vector_3) {
            navController.navigate("rosary_prayer")
        }
        FeatureItem("오늘의 복음", "매일미사 중 복음말씀", R.drawable.ic_vector_3) {
            navController.navigate("TodayGospelScreen")
        }
        FeatureItem("거룩한 독서", "연중시기에 따른 주별 동영상", R.drawable.ic_vector_3) {
            navController.navigate("HolyReadingScreen")
        }
        FeatureItem("삼종기도", "2024년 8월 15일", R.drawable.ic_vector_3) {
            navController.navigate("AngelusPrayerScreen")
        }
        FeatureItem("기도문", "가톨릭 기도문 검색", R.drawable.ic_vector_3) {
            navController.navigate("prayer")
        }
        FeatureItem("성당소식", "성당 소식 및 행사 안내", R.drawable.ic_vector_3) {
            navController.navigate("church_news")
        }
        FeatureItem("가톨릭 추천앱", "가톨릭 대표 추천앱", R.drawable.ic_vector_3) {
            navController.navigate("catholic_apps")

        }
    }
}

@Composable
fun FeatureItem(title: String, subtitle: String, iconId: Int, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .background(Color.White, shape = RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(text = title, fontSize = 20.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = subtitle, fontSize = 14.sp, color = Color.Gray)
        }
        Spacer(modifier = Modifier.width(8.dp))
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = title,
            modifier = Modifier.size(15.dp)
        )
    }
}
