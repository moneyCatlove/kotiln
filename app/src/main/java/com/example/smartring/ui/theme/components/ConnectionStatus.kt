package com.example.smartring.ui.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Text
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.shape.CircleShape

@Composable
fun ConnectionStatus() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .clip(CircleShape)
                .background(Color.Green)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = "연결됨 | 배터리 : 100%",
            fontSize = 16.sp,
        )
    }
}