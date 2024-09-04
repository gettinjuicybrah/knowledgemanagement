package com.joeybasile.knowledgemanagement.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.joeybasile.knowledgemanagement.ui.viewmodel.LoginEvent
import com.joeybasile.knowledgemanagement.ui.LoginViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LoginScreen() {
    val viewModel: LoginViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = state.username,
            onValueChange = { viewModel.handleEvent(LoginEvent.UpdateUsername(it)) },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        TextField(
            value = state.password,
            onValueChange = { viewModel.handleEvent(LoginEvent.UpdatePassword(it)) },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(16.dp))
        Button(
            onClick = { viewModel.handleEvent(LoginEvent.AttemptLogin) },
            modifier = Modifier.fillMaxWidth(),
            enabled = !state.isLoading
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = MaterialTheme.colors.onPrimary
                )
            } else {
                Text("Login")
            }
        }
        Spacer(Modifier.height(8.dp))
        Button(
            onClick = { viewModel.handleEvent(LoginEvent.Register) },
            modifier = Modifier.fillMaxWidth(),
            enabled = !state.isLoading
        ) {
            Text("Register")
        }
        Spacer(Modifier.height(8.dp))
        Button(
            onClick = { viewModel.handleEvent(LoginEvent.ContinueWithoutLogin) },
            modifier = Modifier.fillMaxWidth(),
            enabled = !state.isLoading
        ) {
            Text("Continue Without Login")
        }
    }

    // Error popup
    if (state.error != null) {
        AlertDialog(
            onDismissRequest = { viewModel.handleEvent(LoginEvent.DismissError) },
            title = { Text("Error") },
            text = { Text(state.error!!) },
            confirmButton = {
                Button(onClick = { viewModel.handleEvent(LoginEvent.DismissError) }) {
                    Text("OK")
                }
            }
        )
    }
}