package com.joeybasile.knowledgemanagement.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joeybasile.knowledgemanagement.data.database.entity.NotesEntity
import com.joeybasile.knowledgemanagement.ui.viewmodel.ListNotesEvent
import com.joeybasile.knowledgemanagement.ui.viewmodel.ListNotesViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun ListNotesScreen() {
    val viewModel: ListNotesViewModel = koinViewModel()
    //the state we expose to UI. Read-Only
   val state by viewModel.state.collectAsState()
    //val state = viewModel.state.value
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Notes") },
                navigationIcon = {
                    IconButton(onClick = { viewModel.handleEvent(ListNotesEvent.NavigateBack) }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(state.notes) { note ->
                NoteItem(
                    note = note,
                    onNoteClick = { viewModel.handleEvent(ListNotesEvent.NavigateToSelectNote(note)) },
                    onMoreClick = { viewModel.handleEvent(ListNotesEvent.ShowNoteDetails(note)) }
                )
            }
        }

        state.selectedNoteDetails?.let { note ->
            NoteDetailsDialog(
                note = note,
                onDismiss = { viewModel.handleEvent(ListNotesEvent.DismissNoteDetails) },
                onDelete = { viewModel.handleEvent(ListNotesEvent.DeleteNote(note)) }
                //formatDate = viewModel::formatDate
            )
        }
    }
}

@Composable
fun NoteItem(
    note: NotesEntity,
    onNoteClick: () -> Unit,
    onMoreClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onNoteClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = note.title,
            style = MaterialTheme.typography.h6,
            modifier = Modifier.weight(1f)
        )
        IconButton(onClick = onMoreClick) {
            Icon(Icons.Default.MoreVert, contentDescription = "More")
        }
    }
}

@Composable
fun NoteDetailsDialog(
    note: NotesEntity,
    onDismiss: () -> Unit,
    onDelete: () -> Unit
    //formatDate: (Long) -> String
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(note.title) },
        text = {
            Column {
                Text("Created: ")
                Text("Last edited: ")
            }
        },
        confirmButton = {
            TextButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Delete")
                Spacer(Modifier.width(4.dp))
                Text("Delete")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Dismiss")
            }
        }
    )
}