package com.example.smartring.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.smartring.ui.theme.components.ConnectionStatus
import com.example.smartring.ui.theme.components.FeatureList

@Composable
fun MainScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {


        Spacer(modifier = Modifier.height(16.dp))

        FeatureList(
            navController = navController,
            modifier = Modifier
                .weight(1f)
                .offset(y = (-20).dp)
        )
    }
}
