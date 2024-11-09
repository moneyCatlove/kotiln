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
fun BottomNavigationBar(navController: NavController) {
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp) // 높이를 명시적으로 설정
            .background(Color(0xFFF7F7F7), shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)) // 상단 모서리 둥글게
            .padding(horizontal = 16.dp) // 좌우 패딩 추가
    ) {
        BottomNavItem("가톨릭", R.drawable.ic_catholic) {
            navController.navigate("home") // 카톨릭 메인 화면으로 이동
        }
        BottomNavItem("헬스케어", R.drawable.ic_healthcare) {
            navController.navigate("health_main") // 헬스케어 화면으로 이동
        }
        BottomNavItem("설정", R.drawable.ic_settings) {
            navController.navigate("settings_screen") // 설정 화면으로 이동
        }
    }
}

@Composable
fun BottomNavItem(label: String, iconId: Int, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(4.dp) // 전체 패딩 조정
    ) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = label,
            modifier = Modifier
                .size(32.dp) // 아이콘 크기 조정
                .padding(bottom = 4.dp), // 아이콘과 텍스트 간격
            tint = Color.Unspecified
        )
        Text(
            text = label,
            fontSize = 12.sp,
            color = Color.Black // 텍스트 색상 명시
        )
    }
}
