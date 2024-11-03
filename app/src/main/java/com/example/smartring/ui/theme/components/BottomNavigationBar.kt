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
            .padding(vertical = 8.dp)
            .background(Color(0xFFF7F7F7), shape = RoundedCornerShape(24.dp))

    ) {
        BottomNavItem("가톨릭", R.drawable.ic_catholic) {
            navController.navigate("MainScreen")
        }
        BottomNavItem("헬스케어", R.drawable.ic_healthcare) {
            navController.navigate("health_main")
        }
        BottomNavItem("설정", R.drawable.ic_settings) {
            navController.navigate("settings_screen") // 예시 경로 아직 페이지 개발 안됨
        }
    }
}

@Composable
fun BottomNavItem(label: String, iconId: Int, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(onClick = onClick).padding(8.dp)
    ) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = label,
            modifier = Modifier.size(35.dp),
            tint = Color.Unspecified
        )
        Text(text = label, fontSize = 12.sp)
    }
}
