package com.joeybasile.knowledgemanagement.ui.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.joeybasile.knowledgemanagement.ui.viewmodel.FolderDirectoryEvent
import com.joeybasile.knowledgemanagement.ui.viewmodel.FolderDirectoryViewModel
import com.joeybasile.knowledgemanagement.ui.viewmodel.ListNotesEvent
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun FolderDirectoryScreen() {
    val viewModel: FolderDirectoryViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Folder Directory") },
                navigationIcon = {
                    IconButton(onClick = { viewModel.handleEvent(FolderDirectoryEvent.NavigateBack) }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {

        }
    }
}