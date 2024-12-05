package com.example.networth.ui.theme.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.example.networth.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {
    // Launch an effect that will navigate after 3 seconds
    LaunchedEffect(true) {
        delay(3000)  // Wait for 3 seconds
        navController.navigate("networth") // Navigate to shopping list screen
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primaryContainer), // Set background color from theme
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // Display the logo image
            Image(
                painter = painterResource(id = R.drawable.walleth_logo_text), // Your custom logo or image
                contentDescription = "Logo",
                modifier = Modifier.fillMaxSize(0.5f)  // Adjust size of the logo if needed
            )
        }
    }
}