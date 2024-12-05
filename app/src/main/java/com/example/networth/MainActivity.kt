package com.example.networth

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.networth.ui.theme.NetWorthTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.viewinterop.AndroidView
import com.example.networth.ui.theme.Screen.AboutScreen
import com.example.networth.ui.theme.Screen.GoalsScreen
import com.example.networth.ui.theme.Screen.ItemDisplayScreen
import com.example.networth.ui.theme.Screen.NetWorthMainScreen
import com.example.networth.ui.theme.Screen.ProfileScreen
import com.example.networth.ui.theme.Screen.SplashScreen

import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NetWorthTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NetWorthAppNavHost()
                }
            }
        }
    }
}

@Composable
fun NetWorthAppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "splash",
    sharedViewModel: NetWorthViewModel = hiltViewModel()
) {
    NavHost(
        modifier = modifier, navController = navController, startDestination = startDestination
    ) {
        composable("splash") { SplashScreen(navController) }
        composable("networth") { NetWorthMainScreen(navController, sharedViewModel) }
        composable("profile") { ProfileScreen(navController, modifier, sharedViewModel) }
        composable("goals") { GoalsScreen(navController, modifier, sharedViewModel) }
        composable("assets") { ItemDisplayScreen("assets", navController, modifier, sharedViewModel) }
        composable("liabilities") { ItemDisplayScreen("liabilities", navController, modifier, sharedViewModel) }
        composable("savings") { ItemDisplayScreen("savings", navController, modifier, sharedViewModel) }
        composable("about") { AboutScreen(navController) } // Added "About" screen
    }
}










