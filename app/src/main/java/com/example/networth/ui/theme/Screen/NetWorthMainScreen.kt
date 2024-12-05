package com.example.networth.ui.theme.Screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.networth.NetWorthViewModel
import com.example.networth.R
import com.example.networth.getBarData
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

@SuppressLint("DefaultLocale")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NetWorthMainScreen(
    navController: NavHostController,
    viewModel: NetWorthViewModel = hiltViewModel()
) {
    // Directly observe the state from the ViewModel
    val netWorth = viewModel.netWorth
    val assetAmount = viewModel.netAssets
    val liabilityAmount = viewModel.netLiability
    val savingsAmount = viewModel.netSavings

    // Generate dynamic colors based on the theme
    val barColors = listOf(
        MaterialTheme.colorScheme.primary.toArgb(),
        MaterialTheme.colorScheme.secondary.toArgb(),
        MaterialTheme.colorScheme.tertiary.toArgb()
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Home", color = MaterialTheme.colorScheme.tertiary) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                actions = {
                    IconButton(onClick = { navController.navigate("profile") }) {
                        Icon(Icons.Filled.AccountCircle, null, tint = MaterialTheme.colorScheme.onPrimaryContainer)
                    }
                    IconButton(onClick = { navController.navigate("goals") }) {
                        Icon(Icons.Filled.Star, null, tint = MaterialTheme.colorScheme.onPrimaryContainer)
                    }
                }
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.SpaceBetween, // Ensure About button is at the bottom
                horizontalAlignment = Alignment.CenterHorizontally // Center all items horizontally
            ) {
                // Main content
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Add logo image at the top
                    Image(
                        painter = painterResource(id = R.drawable.walleth_logo), // Replace with your logo resource ID
                        contentDescription = "Logo",
                        modifier = Modifier
                            .padding(bottom = 16.dp)
                            .height(120.dp) // Adjust the height as needed
                            .fillMaxWidth(0.5f), // Adjust width to center the logo
                    )

                    // Dynamically updating net worth
                    Text(
                        text = "You are worth $${String.format("%.2f", netWorth)}",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    // Bar Chart for visualization
                    AndroidView(
                        factory = { context ->
                            BarChart(context).apply {
                                // Set data to the bar chart
                                val data = getBarData(assetAmount, liabilityAmount, savingsAmount, barColors)
                                this.data = data

                                // Customize the bar chart appearance
                                description.isEnabled = false  // Disable chart description
                                setDrawGridBackground(false)  // Remove background grid
                                axisLeft.apply {
                                    granularity = 1f  // Control step size for y-axis
                                    axisMinimum = 0f  // Set minimum value for y-axis
                                    textColor = android.graphics.Color.BLACK
                                }
                                axisRight.isEnabled = false  // Disable the right axis
                                xAxis.apply {
                                    granularity = 1f  // Control step size for x-axis
                                    textColor = android.graphics.Color.BLACK
                                    valueFormatter = IndexAxisValueFormatter(listOf("Assets", "Liabilities", "Savings"))
                                    position = XAxis.XAxisPosition.BOTTOM  // Position x-axis at the bottom
                                }
                                legend.isEnabled = true  // Enable chart legend
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(400.dp)
                            .padding(16.dp)
                    )

                    // Navigation buttons for asset, liability, and savings management
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Button(
                            onClick = { navController.navigate("assets") },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.secondary,
                                contentColor = MaterialTheme.colorScheme.onSecondary
                            )
                        ) {
                            Text("Manage Assets")
                        }

                        Button(
                            onClick = { navController.navigate("liabilities") },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.secondary,
                                contentColor = MaterialTheme.colorScheme.onSecondary
                            )
                        ) {
                            Text("Manage Liabilities")
                        }

                        Button(
                            onClick = { navController.navigate("savings") },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.secondary,
                                contentColor = MaterialTheme.colorScheme.onSecondary
                            )
                        ) {
                            Text("Manage Savings")
                        }
                    }
                }

                // About button at the bottom
                Button(
                    onClick = { navController.navigate("about") }, // Navigate to About screen
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .padding(bottom = 16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text("About")
                }
            }
        }
    )
}

