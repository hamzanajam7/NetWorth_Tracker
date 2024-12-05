package com.example.networth.ui.theme.Screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.networth.Item
import com.example.networth.NetWorthViewModel
import androidx.compose.foundation.lazy.items
import com.example.networth.ui.theme.PrimaryBlue
import com.example.networth.ui.theme.SecondaryYellow
import com.example.networth.ui.theme.TertiaryOrange

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoalsScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    sharedViewModel: NetWorthViewModel,
) {
    val goals = remember { mutableStateListOf<Item>() }

    LaunchedEffect(Unit) {
        goals.clear()
        goals.addAll(sharedViewModel.getCategoryList("goal"))
    }

    var showAddGoalDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Goals and Planning",
                        color = MaterialTheme.colorScheme.tertiary
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                actions = {
                    IconButton(onClick = {
                        navController.navigate("networth")
                    }) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddGoalDialog = true },
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add Goal")
            }
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                Text(
                    text = "Monthly Savings Required for Each Goal:",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally)
                )

                MonthlySavingsBarChart(goals)

                if (goals.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No Goals Set",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                } else {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(goals) { goal ->
                            GoalCard(
                                goal = goal,
                                onDelete = {
                                    sharedViewModel.removeItem(goal)
                                    goals.remove(goal)
                                }
                            )
                        }
                    }
                }
            }
        }
    )

    if (showAddGoalDialog) {
        AddGoalDialog(
            onDismiss = { showAddGoalDialog = false },
            onAddGoal = { newGoal ->
                sharedViewModel.addItem(newGoal)
                goals.add(newGoal)
            }
        )
    }
}


@Composable
fun MonthlySavingsBarChart(goals: List<Item>) {
    if (goals.isEmpty()) return

    val maxAmount = goals.maxOfOrNull { it.amount.toDouble() / (it.secondaryCategory.toDoubleOrNull() ?: 1.0) } ?: 0.0
    val colors = listOf(
        SecondaryYellow, PrimaryBlue, TertiaryOrange
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Savings per Month ($)",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        ) {
            val canvasWidth = size.width
            val canvasHeight = size.height
            val barWidth = canvasWidth / (goals.size * 2) // Adjust bar width

            // Draw Y-axis
            drawLine(
                color = Color.Gray,
                start = Offset(x = 60f, y = 0f),
                end = Offset(x = 60f, y = canvasHeight),
                strokeWidth = 2.dp.toPx()
            )

            // Y-axis labels
            val step = maxAmount / 5
            for (i in 0..5) {
                val labelY = canvasHeight - (canvasHeight / 5 * i)
                val labelValue = step * i
                drawContext.canvas.nativeCanvas.drawText(
                    String.format("%.1f", labelValue),
                    20f,
                    labelY,
                    android.graphics.Paint().apply {
                        color = android.graphics.Color.BLACK
                        textSize = 30f
                    }
                )
            }

            // Draw X-axis
            drawLine(
                color = Color.Gray,
                start = Offset(x = 60f, y = canvasHeight),
                end = Offset(x = canvasWidth, y = canvasHeight),
                strokeWidth = 2.dp.toPx()
            )

            // Bars and X-axis labels
            goals.forEachIndexed { index, goal ->
                val monthlySavings = goal.amount.toDouble() / (goal.secondaryCategory.toDoubleOrNull() ?: 1.0)
                val barHeight = (monthlySavings / maxAmount * canvasHeight).toFloat()
                val barX = 80f + index * barWidth * 1.5f // Increased from 60f to 80f for extra spacing
                val barColor = colors[index % colors.size]

                // Draw the bar
                drawRect(
                    color = barColor,
                    topLeft = Offset(barX, canvasHeight - barHeight),
                    size = androidx.compose.ui.geometry.Size(barWidth, barHeight)
                )

                // Draw X-axis labels
                drawContext.canvas.nativeCanvas.drawText(
                    "${goal.secondaryCategory}m",
                    barX + barWidth / 4,
                    canvasHeight + 40f,
                    android.graphics.Paint().apply {
                        color = android.graphics.Color.BLACK
                        textSize = 30f
                    }
                )
            }
        }
    }
}



@Composable
fun GoalCard(goal: Item, onDelete: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = goal.name, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.secondary)
            Text(
                text = "Amount: $${String.format("%.2f", goal.amount)}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary
            )
            Text(
                text = "Deadline: ${goal.secondaryCategory} months",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary
            )
        }
        IconButton(onClick = onDelete) {
            Icon(Icons.Filled.Delete, contentDescription = "Delete", tint = MaterialTheme.colorScheme.error)
        }
    }
}

@Composable
fun AddGoalDialog(onDismiss: () -> Unit, onAddGoal: (Item) -> Unit) {
    val name = remember { mutableStateOf("") }
    val amount = remember { mutableStateOf("") }
    val secondaryCategory = remember { mutableStateOf("") }

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
                text = "Add New Goal",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            OutlinedTextField(
                value = name.value,
                onValueChange = { name.value = it },
                label = { Text("Goal Name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = amount.value,
                onValueChange = { amount.value = it },
                label = { Text("Amount") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = secondaryCategory.value,
                onValueChange = { secondaryCategory.value = it },
                label = { Text("Time Frame (in months)") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = onDismiss,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Cancel")
                }
                Button(
                    onClick = {
                        val newGoal = Item(
                            name = name.value,
                            description = "",
                            amount = amount.value.toFloatOrNull() ?: 0f,
                            category = "goal",
                            status = true,
                            secondaryCategory = secondaryCategory.value
                        )
                        onAddGoal(newGoal)
                        onDismiss()
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Add Goal")
                }
            }
        }
    }
}
