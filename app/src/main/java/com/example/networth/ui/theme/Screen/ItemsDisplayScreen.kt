package com.example.networth.ui.theme.Screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.example.networth.Item
import com.example.networth.NetWorthViewModel
import com.example.networth.generateChartColors
import com.example.networth.getPieData
import com.github.mikephil.charting.charts.PieChart
import com.example.networth.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemDisplayScreen(
    category: String,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: NetWorthViewModel
) {
    val items = remember { mutableStateListOf<Item>() }
    var showDialog by remember { mutableStateOf(false) }

    // Sync the items with the ViewModel
    LaunchedEffect(Unit) {
        items.clear()
        items.addAll(viewModel.getCategoryList(category))
    }

    val totalAmount = items.sumOf { it.amount.toDouble() }

    // Get theme colors
    val themeColors = listOf(
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.secondary,
        MaterialTheme.colorScheme.tertiary
    )

    // Generate chart colors dynamically
    val chartColors = generateChartColors(themeColors, items.size)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically // Align logo and title vertically
                    ) {
                        // Choose logo dynamically based on the category
                        val logoResId = when (category.lowercase()) {
                            "assets" -> R.drawable.asset_logo // Replace with your assets logo resource ID
                            "liabilities" -> R.drawable.liabil_logo // Replace with your liabilities logo resource ID
                            "savings" -> R.drawable.savings_logo // Replace with your savings logo resource ID
                            else -> R.drawable.walleth_logo // Default logo for unexpected categories
                        }

                        // Add logo image
                        Image(
                            painter = painterResource(id = logoResId),
                            contentDescription = "$category Logo",
                            modifier = Modifier
                                .height(40.dp) // Adjust the height as needed
                                .padding(end = 8.dp) // Space between the logo and the title
                        )

                        // Title text
                        Text(
                            text = category.replaceFirstChar { it.uppercase() },
                            color = MaterialTheme.colorScheme.tertiary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("networth") }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = MaterialTheme.colorScheme.onPrimaryContainer)
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog = true },
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add $category")
            }
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {

                Text(
                    text = "Total ${category.replaceFirstChar { it.uppercase() }} Amount: $${String.format("%.2f", totalAmount)}",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally)
                )

                if (items.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No ${category.replaceFirstChar { it.uppercase() }} Available",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                } else {
                    // Add the pie chart here
                    AndroidView(
                        factory = { context ->
                            PieChart(context).apply {
                                description.isEnabled = false
                                isDrawHoleEnabled = true
                                setEntryLabelColor(android.graphics.Color.WHITE)
                                setUsePercentValues(true)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .padding(16.dp),
                        update = { pieChart ->
                            // Update the PieChart data and refresh
                            pieChart.data = getPieData(items, chartColors) // Pass chart colors here
                            pieChart.invalidate() // Force refresh
                        }
                    )

                    // Display individual items
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(items) { item ->
                            ItemCard(
                                item = item,
                                onDelete = {
                                    items.remove(item)
                                    viewModel.removeItem(item)
                                }
                            )
                        }
                    }
                }
            }
        }
    )

    if (showDialog) {
        CustomAddItemDialog(
            category = category,
            onDismiss = { showDialog = false },
            onAddItem = { newItem ->
                viewModel.addItem(newItem)
                items.add(newItem)
            }
        )
    }
}

@Composable
fun CustomAddItemDialog(
    category: String,
    onDismiss: () -> Unit,
    onAddItem: (Item) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
                .fillMaxWidth(0.9f)
        ) {
            Text(
                text = "Add New ${category.replaceFirstChar { it.uppercase() }}",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = amount,
                onValueChange = { amount = it },
                label = { Text("Amount") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        name = ""
                        description = ""
                        amount = ""
                        onDismiss()
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Cancel")
                }
                Button(
                    onClick = {
                        val newItem = Item(
                            name = name,
                            description = description,
                            category = category,
                            amount = amount.toFloatOrNull() ?: 0f,
                            status = false,
                            secondaryCategory = ""
                        )
                        Log.d("NetWorthDebug", "New item created: $newItem")
                        onAddItem(newItem)
                        name = ""
                        description = ""
                        amount = ""
                        onDismiss()
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Add")
                }
            }
        }
    }
}

@Composable
fun ItemCard(item: Item, onDelete: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = item.name, style = MaterialTheme.typography.bodyLarge,color = MaterialTheme.colorScheme.secondary)
            Text(
                text = "Description: ${item.description}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary
            )
            Text(
                text = "Amount: $${String.format("%.2f", item.amount)}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary
            )
        }
        IconButton(onClick = onDelete) {
            Icon(Icons.Filled.Delete, contentDescription = "Delete", tint = MaterialTheme.colorScheme.error)
        }
    }
}


