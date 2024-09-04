package com.joeybasile.knowledgemanagement.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joeybasile.knowledgemanagement.ui.viewmodel.SettingsEvent
import com.joeybasile.knowledgemanagement.ui.viewmodel.SettingsViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SettingsScreen() {
    val viewModel: SettingsViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = { viewModel.handleEvent(SettingsEvent.NavigateBack) }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "User Email: ${state.userEmail}",
                style = MaterialTheme.typography.body1
            )

            Button(
                onClick = { viewModel.handleEvent(SettingsEvent.ToggleLogin) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (state.isLoggedIn) "Log Out" else "Log In")
            }
        }
    }
}