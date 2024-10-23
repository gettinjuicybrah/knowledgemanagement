package com.joeybasile.knowledgemanagement.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joeybasile.knowledgemanagement.ui.viewmodel.NewNoteEvent
import com.joeybasile.knowledgemanagement.ui.viewmodel.SeeNoteEvent
import com.joeybasile.knowledgemanagement.ui.viewmodel.SeeNoteViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun SeeNoteScreen() {
    val viewModel: SeeNoteViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Note") },
                navigationIcon = {
                    IconButton(onClick = { viewModel.handleEvent(SeeNoteEvent.SaveAndNavigateBack) }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Save and go back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {

            // Dropdown for Folder Selection
            Spacer(modifier = Modifier.height(16.dp))
            Box {
                OutlinedButton(onClick = { viewModel.handleEvent(SeeNoteEvent.ToggleDropdown) }) {
                    Text(state.selectedFolder?.title ?: "Select a folder")
                }
                DropdownMenu(
                    expanded = state.isDropdownExpanded,
                    onDismissRequest = { viewModel.handleEvent(SeeNoteEvent.ToggleDropdown) }
                ) {
                    state.folderList.forEach { folder ->
                        DropdownMenuItem(onClick = {
                            viewModel.handleEvent(SeeNoteEvent.UpdateFolder(folder))
                        }) {
                            Text(folder.title)
                        }
                    }
                }
            }
            // Show selected folder
            state.selectedFolder?.let {
                Text("Selected Folder: ${it.title}", modifier = Modifier.padding(top = 8.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = state.title,
                onValueChange = { viewModel.handleEvent(SeeNoteEvent.UpdateTitle(it)) },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = state.content,
                onValueChange = { viewModel.handleEvent(SeeNoteEvent.UpdateContent(it)) },
                label = { Text("Content") },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                maxLines = Int.MAX_VALUE
            )
        }
    }
}