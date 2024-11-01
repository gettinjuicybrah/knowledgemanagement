package com.joeybasile.knowledgemanagement.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.rounded.Create
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joeybasile.knowledgemanagement.data.database.entity.FolderEntity
import com.joeybasile.knowledgemanagement.data.database.entity.NotesEntity
import com.joeybasile.knowledgemanagement.ui.viewmodel.FileDirectoryEvent
import com.joeybasile.knowledgemanagement.ui.viewmodel.FolderDirectoryViewModel
import com.joeybasile.knowledgemanagement.ui.viewmodel.SeeNoteEvent
import com.joeybasile.knowledgemanagement.util.FolderNode
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun FolderDirectoryScreen() {
    val viewModel: FolderDirectoryViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()
    val isDialogOpen by viewModel.isDialogOpen

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("File Directory") },
                navigationIcon = {
                    IconButton(onClick = { viewModel.handleEvent(FileDirectoryEvent.NavigateBack) }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "go back to home")
                    }
                }
                /*,
                actions = {
                    IconButton(onClick = { viewModel.handleEvent(FileDirectoryEvent.OpenAIDialog) }) {
                        Icon(Icons.Rounded.Create, contentDescription = "AI Tasks")
                    }
                }
                 */
            )
        },

        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.handleEvent(FileDirectoryEvent.OpenCreateFolderDialog) }) {
                Icon(Icons.Default.Add, contentDescription = "Add Folder")
            }
        },
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            state.rootFolder?.let { folder ->
                item {
                    FolderItem(folder, viewModel::handleEvent, level = 1)
                }
            }
        }
        if (isDialogOpen) {
            CreateFolderDialog(
                //folderName = newFolderName,
                onFolderNameChange = viewModel::updateNewFolderName,
                onCreateFolder = { folderName -> viewModel.handleEvent(FileDirectoryEvent.CreateFolder(folderName)) },  // Parent folder can be chosen here
                onDismiss = { viewModel.handleEvent(FileDirectoryEvent.CloseCreateFolderDialog) },
                existingFolders = state.existingFolders,
                parentSelected = state.selectedParentFolder,
                viewModel = viewModel
            )

        }

    }
}
@Composable
fun CreateFolderDialog(
    //folderName: String,
    onFolderNameChange: (String) -> Unit,
    onCreateFolder: (String) -> Unit,
    onDismiss: () -> Unit,
    existingFolders: List<FolderEntity>,
    parentSelected: FolderEntity?,
    viewModel: FolderDirectoryViewModel
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedFolder by remember { mutableStateOf<FolderEntity?>(null) }
    var folderName by remember{mutableStateOf("")}
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Create New Folder") },
        text = {
            Column {
                OutlinedTextField(
                    value = folderName,
                    onValueChange = { folderName = it},
                    label = { Text("Folder Name") }
                )
                Box {
                    TextField(
                        value = selectedFolder?.title ?: "Select Parent Folder",
                        onValueChange = {},
                        readOnly = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { expanded = true },
                        label = { Text("Parent Folder") }
                    )
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = {
                            expanded = false }
                    ) {
                        existingFolders.forEach { folder ->
                            DropdownMenuItem(onClick = {
                                selectedFolder = folder
                                viewModel.handleEvent(FileDirectoryEvent.SelectParentFolder(folder))
                                expanded = false
                            }) {
                                Text(folder.title)
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                viewModel.handleEvent(FileDirectoryEvent.CreateFolder(folderName))

            }) {
                Text("Create")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
@Composable
fun FolderItem(
    folderNode: FolderNode,
    //expandedFolders: Set<Int>,  // Pass expanded folder IDs
    onEvent: (FileDirectoryEvent) -> Unit,
    level: Int
) {
    Column {
        // Folder row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onEvent(FileDirectoryEvent.ToggleFolderExpansion(folderNode)) }
                .padding(start = (level * 16).dp, end = 16.dp, top = 8.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = folderNode.folderEntity.title,
                style = MaterialTheme.typography.h6,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = { onEvent(FileDirectoryEvent.ToggleFolderExpansion(folderNode)) }) {
                val icon = if (folderNode.folderEntity.isExpanded) {
                    Icons.Default.KeyboardArrowUp
                } else {
                    Icons.Default.KeyboardArrowDown
                }
                Icon(icon, contentDescription = "Expand/Collapse")
            }
        }

        // Show children if the folder is expanded
        println("test before into if")
        println()
        //println(expandedFolders.contains(folderNode.folderEntity.id))
        println()
        if (folderNode.folderEntity.isExpanded) {
        println("test inside if")

            // List notes within the folder
            folderNode.notes.forEach { note ->
                println("FolderDirectoryScreenNOTE." + note.title)
                NoteItem(note = note, onNoteClick = { onEvent(FileDirectoryEvent.NavigateToNote(note))}, level = level)
            }

            folderNode.children.forEach { childFolder ->
                println("FolderDirectoryScreenFOLDER." + childFolder.folderEntity.title)
                FolderItem(folderNode = childFolder,
                    onEvent = onEvent,
                    level = level + 1)
            }
            println("FOLDER CHILDREN " + folderNode.children.size)


        }
    }
}

@Composable
fun NoteItem(
    note: NotesEntity,
    onNoteClick: () -> Unit,
    level: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onNoteClick)
            .padding(start = ((level + 1) * 16).dp, end = 16.dp, top = 4.dp, bottom = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = note.title,
            style = MaterialTheme.typography.body1,
            modifier = Modifier.weight(1f)
        )
    }
}

/*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.joeybasile.knowledgemanagement.data.database.entity.FolderEntity
import com.joeybasile.knowledgemanagement.data.database.entity.NotesEntity
import com.joeybasile.knowledgemanagement.ui.viewmodel.FolderDirectoryEvent
import com.joeybasile.knowledgemanagement.ui.viewmodel.FolderDirectoryViewModel
import com.joeybasile.knowledgemanagement.ui.viewmodel.ListNotesEvent
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import com.joeybasile.knowledgemanagement.ui.screen.ListNotesScreen
import com.joeybasile.knowledgemanagement.ui.viewmodel.NewNoteEvent
import com.joeybasile.knowledgemanagement.util.FolderNode

@OptIn(KoinExperimentalAPI::class)
@Preview
@Composable
fun FolderDirectoryScreen() {
    val viewModel: FolderDirectoryViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()

    var showNewFolderDialog by remember {mutableStateOf(false)}
    var newFolderName by remember {mutableStateOf("")}

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
        Box(modifier = Modifier.padding(padding).fillMaxSize()) {
            LazyColumn(modifier = Modifier.padding(padding)) {
                /*
            The ?. is a safe call operator. It checks if state.rootFolder is not null. If it is null, the block inside let will not execute.
If          state.rootFolder is not null, then the let block is executed, and rootFolder will represent the non-null state.rootFolder inside the block.
             */
                state.rootFolderNode?.let { rootFolderNode ->
                    item {
                        FolderNodeItem(
                            folder = state.rootFolderNode!!,
                            expanded = state.expandedFolderIds.contains(rootFolderNode.folderEntity.id),
                            onToggle = {
                                viewModel.handleEvent(
                                    FolderDirectoryEvent.ToggleFolder(
                                        rootFolderNode.folderEntity.id
                                    )
                                )

                            },
                            onNoteClick = { note ->
                                viewModel.handleEvent(
                                    FolderDirectoryEvent.NavigateToSelectNote(note)
                                )
                            },
                            onFolderLongPress = { viewModel.handleEvent(FolderDirectoryEvent.ShowFolderDetails(rootFolderNode.folderEntity)) },
                            onNoteLongPress = { note -> viewModel.handleEvent(FolderDirectoryEvent.ShowNoteDetails(note))},
                            level = 0,
                            viewModel = viewModel
                        )
                    }
                }


            }
        }

        //Column to hold floating action buttons
        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier.fillMaxSize().padding(16.dp)
        ) {
            ExtendedFloatingActionButton(
                onClick = { showNewFolderDialog = true },
                icon =
                {
                    Icon(Icons.Filled.Edit, "New Folder Button")
                },
                text = { Text(text = "New Folder") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            // AlertDialog for creating a new folder

            if (showNewFolderDialog) {
                AlertDialog(
                    onDismissRequest = { showNewFolderDialog = false },
                    title = { Text("Create New Folder") },
                    text = {
                        Column {

                            Text("Please enter a name for the new folder:")
                            TextField(
                                value = newFolderName,
                                onValueChange = { newFolderName = it },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 8.dp)
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Box {
                                OutlinedButton(onClick = { viewModel.handleEvent(FolderDirectoryEvent.ToggleFolderDialogDropdown) }) {
                                    Text(state.selectedFolderForDialog?.title ?: "Select a folder")
                                }
                                DropdownMenu(
                                    expanded = state.isDropdownExpanded,
                                    onDismissRequest = { viewModel.handleEvent(FolderDirectoryEvent.ToggleFolderDialogDropdown) }
                                ) {
                                    state.folderListForDialog.forEach { folder ->
                                        DropdownMenuItem(onClick = {
                                            viewModel.handleEvent(FolderDirectoryEvent.SelectFolderForDialog(folder))
                                        }) {
                                            Text(folder.title)
                                        }
                                    }
                                }
                            }
                            // Show selected folder
                            state.selectedFolderForDialog?.let {
                                Text("Selected Folder: $it.title", modifier = Modifier.padding(top = 8.dp))
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                        }
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                // Pass the folder name to the ViewModel via event
                                state.selectedFolderForDialog?.parentFolderId?.let {
                                    FolderDirectoryEvent.CreateNewFolder(newFolderName,
                                        it
                                    )
                                }?.let { viewModel.handleEvent(it) }
                                showNewFolderDialog = false // Close the dialog
                            }
                        ) {
                            Text("Confirm")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showNewFolderDialog = false }) {
                            Text("Cancel")
                        }
                    }
                )
            }

            ExtendedFloatingActionButton(
                onClick = { viewModel.handleEvent(FolderDirectoryEvent.NavigateToNewNote) },
                icon =
                {
                    Icon(Icons.Filled.Edit, "New Note Button")
                },
                text = { Text(text = "New Note") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

        }
        state.selectedFolderDetails?.let { folder ->
            FolderDetailsDialog(
                folder = folder,
                onDismiss = { viewModel.handleEvent(FolderDirectoryEvent.DismissFolderDetails) },
                onDelete = { viewModel.handleEvent(FolderDirectoryEvent.DeleteFolderDetails(folder)) },
                viewModel = viewModel

                //formatDate = viewModel::formatDate
            )
        }
        state.selectedNoteDetails?.let { note ->
            com.joeybasile.knowledgemanagement.ui.screen.NoteDetailsDialog(
                note = note,
                onDismiss = { viewModel.handleEvent(FolderDirectoryEvent.DismissNoteDetails) },
                onDelete = { viewModel.handleEvent(FolderDirectoryEvent.DeleteNote(note)) }
                //formatDate = viewModel::formatDate
            )
        }

    }
}

@Composable
fun FolderNodeItem(
    folder: FolderNode,
    expanded: Boolean,
    onToggle: () -> Unit,
    onNoteClick: (NotesEntity) -> Unit,
    onFolderLongPress: () -> Unit,
    onNoteLongPress: (NotesEntity) -> Unit,
    level: Int,
    viewModel: FolderDirectoryViewModel
) {
    Column(modifier = Modifier.padding(start = (level * 16).dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onToggle)
                .padding(start = (level * 16).dp, end = 16.dp, top = 8.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = if (expanded) Icons.Default.ArrowDropDown else Icons.Default.KeyboardArrowUp,
                contentDescription = if (expanded) "Collapse" else "Expand"
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = folder.folderEntity.title)
        }

        if (expanded) {
            folder.notes.forEach { note ->
                NoteItem(note = note, onClick = { onNoteClick(note) }, onLongPress = { onNoteLongPress(note) }, level = level + 1)
            }
            folder.children.forEach { childFolder ->
                FolderNodeItem(
                    folder = childFolder,
                    expanded = viewModel.state.value.expandedFolderIds.contains(childFolder.folderEntity.id),
                    onToggle = { viewModel.handleEvent(FolderDirectoryEvent.ToggleFolder(childFolder.folderEntity.id)) },
                    onNoteClick = onNoteClick,
                    onFolderLongPress = onFolderLongPress,
                    onNoteLongPress = onNoteLongPress,
                    level = level + 1,
                    viewModel = viewModel
                )
            }
        }

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteItem(note: NotesEntity, onClick: () -> Unit, onLongPress: () -> Unit, level: Int) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .combinedClickable(onClick = onClick, onLongClick = onLongPress)
                .padding(start = ((level + 1) * 16).dp, end = 16.dp, top = 4.dp, bottom = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = note.title)
        }

}



@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FolderDetailsDialog(
    folder: FolderEntity,
    onDismiss: () -> Unit,
    onDelete: () -> Unit,
    viewModel: FolderDirectoryViewModel,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(folder.title) },
            text = {
                Column {
                    //Text("Created: ${formatDate(note.createdAt)}")
                    //Text("Last edited: ${formatDate(note.updatedAt)}")
                    // Dropdown for Folder Selection
                    Spacer(modifier = Modifier.height(16.dp))
                    Box {
                        OutlinedButton(onClick = { viewModel.handleEvent(FolderDirectoryEvent.ToggleFolderDialogDropdown) }) {
                            Text(viewModel.state.value.selectedFolderForDialog?.title ?: "Select a folder")
                        }
                       DropdownMenu(
                             expanded = viewModel.state.value.isDropdownExpanded,
                            onDismissRequest = { viewModel.handleEvent(FolderDirectoryEvent.ToggleFolderDialogDropdown) }
                        ) {
                            viewModel.state.value.folderListForDialog.forEach { folder ->
                                DropdownMenuItem(onClick = {
                                    viewModel.handleEvent(FolderDirectoryEvent.SelectFolderForDialog(folder))
                                }) {
                                    Text(folder.title)
                                }
                            }
                        }
                    }
                    // Show selected folder
                    viewModel.state.value.selectedFolderForDialog?.let {
                        Text("Selected Folder: $it.title", modifier = Modifier.padding(top = 8.dp))
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }
            },
            confirmButton = {
                TextButton(onClick = { onDelete() }) {
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
}

 */