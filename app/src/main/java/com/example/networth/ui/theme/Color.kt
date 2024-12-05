package com.example.networth.ui.theme

import androidx.compose.ui.graphics.Color

val PrimaryBlue = Color(0xFF153992)
val SecondaryYellow = Color(0xFFD6BF3E)
val TertiaryOrange = Color(0xFFD6943E)
val StandardWhite = Color(0xFFFFFFFF)

/*val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)*/





///////

//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun GoalsScreen(
//    navController: NavHostController,
//    modifier: Modifier = Modifier,
//    sharedViewModel: NetWorthViewModel,
//) {
//
//    // Initialize states with default values from the ViewModel
//    var goals by remember { mutableStateOf(sharedViewModel.getCategoryList("goals")) }
//    var showAddGoalDialog by remember { mutableStateOf(false) } // Proper initialization for dialog state
//
//    // Calculate the total goal amount
//    val totalGoalAmount = goals.sumOf { it.amount.toDouble() }
//
//
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = {
//                    Text(
//                        text = "Goals and Planning",
//                        color = MaterialTheme.colorScheme.tertiary
//                    )
//                },
//                colors = TopAppBarDefaults.topAppBarColors(
//                    containerColor = MaterialTheme.colorScheme.primaryContainer
//                ),
//                actions = {
//                    IconButton(onClick = {
//                        navController.navigate("networth")
//                    }) {
//                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = MaterialTheme.colorScheme.onPrimaryContainer)
//                    }
//                }
//            )
//        },
//        floatingActionButton = {
//            FloatingActionButton(
//                onClick = { showAddGoalDialog = true },
//                containerColor = MaterialTheme.colorScheme.secondary,
//                contentColor = MaterialTheme.colorScheme.onSecondary
//            ) {
//                Icon(Icons.Filled.Add, contentDescription = "Add Goal")
//            }
//        },
//        content = { innerPadding ->
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(innerPadding)
//            ) {
//
//                Text(
//                    text = "Total Goal Amount: $${String.format("%.2f", totalGoalAmount)}",
//                    style = MaterialTheme.typography.titleLarge,
//                    color = MaterialTheme.colorScheme.secondary,
//                    modifier = Modifier
//                        .padding(16.dp)
//                        .align(Alignment.CenterHorizontally)
//                )
//
//                if (goals.isEmpty()) {
//                    Box(
//                        modifier = Modifier.fillMaxSize(),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        Text(
//                            text = "No Goals Set",
//                            style = MaterialTheme.typography.bodyMedium,
//                            color = MaterialTheme.colorScheme.onBackground
//                        )
//                    }
//                } else {
//                    Column(modifier = Modifier.fillMaxSize()) {
//                        goals.forEach { goal ->
//                            GoalCard(goal = goal, onDelete = { goals.remove(goal) })
//                        }
//                    }
//                }
//            }
//        }
//    )
//
//    if (showAddGoalDialog) {
//        AddGoalDialog(
//            onDismiss = { showAddGoalDialog = false },
//            onAddGoal = { newGoal -> goals.add(newGoal) }
//        )
//    }
//}
//
///////
//
//
//@Composable
//fun GoalCard(goal: Goal, onDelete: () -> Unit) {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(8.dp)
//            .background(MaterialTheme.colorScheme.primaryContainer)
//            .padding(16.dp),
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Column(modifier = Modifier.weight(1f)) {
//            Text(text = goal.name, style = MaterialTheme.typography.bodyLarge)
//            Text(
//                text = "Amount: $${String.format("%.2f", goal.amount)}",
//                style = MaterialTheme.typography.bodySmall,
//                color = MaterialTheme.colorScheme.secondary
//            )
//            Text(
//                text = "Deadline: ${goal.timeFrame}",
//                style = MaterialTheme.typography.bodySmall,
//                color = MaterialTheme.colorScheme.secondary
//            )
//        }
//        IconButton(onClick = onDelete) {
//            Icon(Icons.Filled.Delete, contentDescription = "Delete", tint = MaterialTheme.colorScheme.error)
//        }
//    }
//}
//
//@Composable
//fun AddGoalDialog(onDismiss: () -> Unit, onAddGoal: (Goal) -> Unit) {
//    val name = remember { mutableStateOf("") }
//    val amount = remember { mutableStateOf("") }
//    val timeFrame = remember { mutableStateOf("") }
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.Black.copy(alpha = 0.5f)),
//        contentAlignment = Alignment.Center
//    ) {
//        Column(
//            modifier = Modifier
//                .background(MaterialTheme.colorScheme.background)
//                .padding(16.dp)
//                .fillMaxWidth(0.9f)
//        ) {
//            Text(
//                text = "Add New Goal",
//                style = MaterialTheme.typography.titleLarge,
//                modifier = Modifier.padding(bottom = 16.dp)
//            )
//            OutlinedTextField(
//                value = name.value,
//                onValueChange = { name.value = it },
//                label = { Text("Goal Name") },
//                modifier = Modifier.fillMaxWidth()
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//            OutlinedTextField(
//                value = amount.value,
//                onValueChange = { amount.value = it },
//                label = { Text("Amount") },
//                modifier = Modifier.fillMaxWidth()
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//            OutlinedTextField(
//                value = timeFrame.value,
//                onValueChange = { timeFrame.value = it },
//                label = { Text("Time Frame") },
//                modifier = Modifier.fillMaxWidth()
//            )
//            Spacer(modifier = Modifier.height(16.dp))
//            Row(
//                horizontalArrangement = Arrangement.spacedBy(8.dp),
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Button(
//                    onClick = onDismiss,
//                    modifier = Modifier.weight(1f)
//                ) {
//                    Text("Cancel")
//                }
//                Button(
//                    onClick = {
//                        val newGoal = Goal(
//                            name = name.value,
//                            amount = amount.value.toFloatOrNull() ?: 0f,
//                            timeFrame = timeFrame.value
//
//                        )
//                        onAddGoal(newGoal)
//                        onDismiss()
//                    },
//                    modifier = Modifier.weight(1f)
//                ) {
//                    Text("Add Goal")
//                }
//            }
//        }
//    }
//}
//
//data class Goal(
//    val name: String,
//    val amount: Float,
//    val timeFrame: String
//)
//
//
/////////////
