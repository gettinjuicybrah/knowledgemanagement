package com.joeybasile.knowledgemanagement.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joeybasile.knowledgemanagement.ui.viewmodel.NewNoteEvent
import com.joeybasile.knowledgemanagement.ui.viewmodel.NewNoteViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun NewNoteScreen() {
    val viewModel: NewNoteViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("New Note") },
                navigationIcon = {
                    IconButton(onClick = { viewModel.handleEvent(NewNoteEvent.NavigateBack) }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    TextButton(onClick = { viewModel.handleEvent(NewNoteEvent.SaveNote) }) {
                        Text("Save", color = MaterialTheme.colors.onPrimary)
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            TextField(
                value = state.title,
                onValueChange = { viewModel.handleEvent(NewNoteEvent.UpdateTitle(it)) },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = state.content,
                onValueChange = { viewModel.handleEvent(NewNoteEvent.UpdateContent(it)) },
                label = { Text("Content") },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                maxLines = Int.MAX_VALUE
            )

            if (state.error != null) {
                Text(
                    text = state.error!!,
                    color = MaterialTheme.colors.error,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}