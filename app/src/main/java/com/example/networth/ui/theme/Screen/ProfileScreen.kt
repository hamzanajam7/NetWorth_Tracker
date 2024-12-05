package com.example.networth.ui.theme.Screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.networth.NetWorthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: NetWorthViewModel
) {
    var name by remember { mutableStateOf(viewModel.getName()) }
    var birthYear by remember { mutableStateOf(viewModel.getBirthYear().toString()) }
    var birthMonth by remember { mutableStateOf(viewModel.getBirthMonth()) }
    var birthDay by remember { mutableStateOf(viewModel.getBirthDay().toString()) }
    var retirementAge by remember { mutableStateOf(viewModel.getRetirementAge().toString()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile", color = MaterialTheme.colorScheme.tertiary) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("networth") }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = MaterialTheme.colorScheme.onPrimaryContainer)
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Edit Profile", style = MaterialTheme.typography.titleLarge)

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = birthYear,
                    onValueChange = { birthYear = it },
                    label = { Text("Year") },
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = birthMonth,
                    onValueChange = { birthMonth = it },
                    label = { Text("Month") },
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = birthDay,
                    onValueChange = { birthDay = it },
                    label = { Text("Day") },
                    modifier = Modifier.weight(1f)
                )
            }

            OutlinedTextField(
                value = retirementAge,
                onValueChange = { retirementAge = it },
                label = { Text("Retirement Age") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    // Update the ViewModel with the new profile information
                    viewModel.setName(name)
                    viewModel.setBirthInfo(
                        year = birthYear.toIntOrNull() ?: viewModel.getBirthYear(),
                        month = birthMonth,
                        day = birthDay.toIntOrNull() ?: viewModel.getBirthDay()
                    )
                    viewModel.setRetirementAge(retirementAge.toIntOrNull() ?: viewModel.getRetirementAge())
                    navController.navigate("networth") // Navigate back
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Changes")
            }
        }
    }
}
