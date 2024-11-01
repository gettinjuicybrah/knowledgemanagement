package com.joeybasile.knowledgemanagement.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joeybasile.knowledgemanagement.data.database.entity.FolderEntity
import com.joeybasile.knowledgemanagement.data.database.entity.NotesEntity
import com.joeybasile.knowledgemanagement.service.FolderService
import com.joeybasile.knowledgemanagement.service.NoteService
import com.joeybasile.knowledgemanagement.ui.navigation.NavigatorImpl
import com.joeybasile.knowledgemanagement.util.FolderNode
import com.joeybasile.knowledgemanagement.util.SelectedNoteUseCase
import com.joeybasile.knowledgemanagement.util.buildDirectoryTree
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class FolderDirectoryViewModel : ViewModel(), KoinComponent {
    private val folderService: FolderService by inject()
    private val noteService: NoteService by inject()
    private val navigator: NavigatorImpl by inject()
    private val selectedNoteUseCase: SelectedNoteUseCase by inject()


    // Keep track of which folders are expanded
    private val _expandedFolders = mutableSetOf<Int>()

    private val _state = MutableStateFlow(FileDirectoryState())
    val state: StateFlow<FileDirectoryState> = _state.asStateFlow()

    // For managing folder creation
    private val _isDialogOpen = mutableStateOf(false)
    val isDialogOpen: State<Boolean> get() = _isDialogOpen

    private val _isAIDialogOpen = mutableStateOf(false)
    val isAIDialogOpen: State<Boolean> get() = _isAIDialogOpen

    private val _newFolderName = mutableStateOf("")
    val newFolderName: State<String> get() = _newFolderName

    init {
        loadDirectory()
    }

    private fun loadDirectory() {
        viewModelScope.launch {
            val foldersFlow = folderService.getAllFolders()
            //val notesFlow = noteService.getAllNotes()
            val notesFlow = noteService.getAllNotes()
            val rootFolder = buildDirectoryTree(notesFlow, foldersFlow)
            _state.value = _state.value.copy(rootFolder = rootFolder)
        }
    }

    fun handleEvent(event: FileDirectoryEvent) {
        when (event) {
            is FileDirectoryEvent.ToggleFolderExpansion -> toggleFolderExpansion(event.folder)
            is FileDirectoryEvent.CreateFolder -> createFolder(event.folderName)
            is FileDirectoryEvent.OpenCreateFolderDialog -> _isDialogOpen.value = true
            is FileDirectoryEvent.CloseCreateFolderDialog -> _isDialogOpen.value = false
            is FileDirectoryEvent.NavigateBack -> navigateBack()
            is FileDirectoryEvent.SelectParentFolder -> selectParentFolder(event.folder)

            //notes
            is FileDirectoryEvent.NavigateToNote -> navigateToNote(event.note)
            is FileDirectoryEvent.ShowNoteDetails -> showNoteDetails(event.note)
            is FileDirectoryEvent.DeleteNote -> deleteNote(event.note)
            is FileDirectoryEvent.NavigateToNewNote -> navigateToNewNote()
            is FileDirectoryEvent.DismissNoteDetails -> dismissNoteDetails()
            FileDirectoryEvent.CloseAIDialog -> _isAIDialogOpen.value = false
            FileDirectoryEvent.OpenAIDialog -> _isAIDialogOpen.value = true
        }
    }

    private fun toggleFolderExpansion(folder: FolderNode) {
        viewModelScope.launch {
            val updatedFolder = folder.folderEntity.copy(isExpanded = !folder.folderEntity.isExpanded)
            folderService.updateFolder(updatedFolder)
            loadDirectory() // Reload the directory to reflect the changes
        }
        /*if (_expandedFolders.contains(folder.folderEntity.id)) {
            _expandedFolders.remove(folder.folderEntity.id)
        } else {
            folder.folderEntity.id?.let { _expandedFolders.add(it) }
            viewModelScope.launch {
                println("PRINT HERE IN TOOGLE FOLDER EXPANSION")
                println(folderService.getAllFolders().first().toString())
            }
        }
        _state.value = _state.value.copy(expandedFolders = _expandedFolders.toSet())

         */
    }

    private fun selectParentFolder(folder: FolderEntity) {
        _state.value = _state.value.copy(selectedParentFolder = folder)
    }

    private fun navigateToNote(note: NotesEntity) {
        selectedNoteUseCase.apply {
            idA = note.idA!!
            idB = note.idB!!
            noteTitle = note.title
            noteContent = note.content
            createdAt = note.creation_date
            updatedAt = note.last_edit_date
        }
        navigator.navToSeeNote()
    }

    private fun showNoteDetails(note: NotesEntity) {

    }

    private fun deleteNote(note: NotesEntity) {
        viewModelScope.launch {
            noteService.deleteNote(note)
        }
    }

    private fun navigateToNewNote() {
        navigator.navToNewNote()
    }

    private fun dismissNoteDetails() {

    }

    private fun createFolder(folderName: String) {
        viewModelScope.launch {
            val newFolder = FolderEntity( // Generating random ID for now
                title = folderName,
                parentFolderId = 0,
                isExpanded = false
            )
            folderService.insertFolder(newFolder)
            loadDirectory()  // Reload to update UI
            _isDialogOpen.value = false
        }
    }

    fun updateNewFolderName(name: String) {
        _newFolderName.value = name
    }

    private fun navigateBack() {
        navigator.popBackStack()
    }
}

data class FileDirectoryState(
    val rootFolder: FolderNode? = null,
    val expandedFolders: Set<Int> = emptySet(),  // Track expanded folder IDs
    val existingFolders: List<FolderEntity> = emptyList(),
    val selectedParentFolder: FolderEntity? = null
)

sealed class FileDirectoryEvent {
    data class ToggleFolderExpansion(val folder: FolderNode) : FileDirectoryEvent()
    data class CreateFolder(val folderName: String) :
        FileDirectoryEvent()

    object OpenCreateFolderDialog : FileDirectoryEvent()
    object CloseCreateFolderDialog : FileDirectoryEvent()

    object OpenAIDialog : FileDirectoryEvent()
    object CloseAIDialog : FileDirectoryEvent()

    object NavigateBack : FileDirectoryEvent()

    data class SelectParentFolder(val folder: FolderEntity) : FileDirectoryEvent()

    //notes
    data class NavigateToNote(val note: NotesEntity) : FileDirectoryEvent()
    data class NavigateToNewNote(val note: NotesEntity) : FileDirectoryEvent()
    data class ShowNoteDetails(val note: NotesEntity) : FileDirectoryEvent()
    data class DeleteNote(val note: NotesEntity) : FileDirectoryEvent()
    object DismissNoteDetails : FileDirectoryEvent()
}



/*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joeybasile.knowledgemanagement.data.database.data.repository.FolderRepository
import com.joeybasile.knowledgemanagement.data.database.data.repository.NoteRepository
import com.joeybasile.knowledgemanagement.ui.navigation.NavigatorImpl
import com.joeybasile.knowledgemanagement.util.buildDirectoryTree
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import com.joeybasile.knowledgemanagement.data.database.entity.*
import com.joeybasile.knowledgemanagement.service.FolderService
import com.joeybasile.knowledgemanagement.service.NoteService
import com.joeybasile.knowledgemanagement.util.FolderNode
import com.joeybasile.knowledgemanagement.util.SelectedNoteUseCase
import kotlinx.coroutines.flow.first

class FolderDirectoryViewModel : ViewModel(), KoinComponent {
    private val navigator: NavigatorImpl by inject()
    private val folderRepository: FolderRepository by inject()
    private val noteRepository: NoteRepository by inject()
    private val folderService: FolderService by inject()
    private val noteService: NoteService by inject()
    private val selectedNoteUseCase: SelectedNoteUseCase by inject()

    private val _state = MutableStateFlow(FolderDirectoryState())
    val state: StateFlow<FolderDirectoryState> = _state.asStateFlow()

    init {
        println("FolderDirectoryViewModel init called")
        loadFolders()
        fetchFoldersForDialog()
    }

  fun handleEvent(event: FolderDirectoryEvent) {
        when (event) {
            is FolderDirectoryEvent.LoadFolders -> loadFolders()
            is FolderDirectoryEvent.NavigateBack -> navigateBack()

            //Folder events
            is FolderDirectoryEvent.CreateNewFolder -> createNewFolder(event.folderName, event.parentFolderId)
            is FolderDirectoryEvent.ToggleFolder -> toggleFolder(event.folderId)
            is FolderDirectoryEvent.UpdateNewFolderName -> updateNewFolderName(event.folderName)
            is FolderDirectoryEvent.ShowFolderDetails -> showFolderDetails(event.folder)
            is FolderDirectoryEvent.DismissFolderDetails -> dismissFolderDetails()
            is FolderDirectoryEvent.DeleteFolderDetails -> deleteFolderDetails(event.folder)


            //Note events
            is FolderDirectoryEvent.NavigateToSelectNote -> navigateToSelectNote(event.note)
            is FolderDirectoryEvent.ShowNoteDetails -> showNoteDetails(event.note)
            is FolderDirectoryEvent.DeleteNote -> deleteNote(event.note)
            is FolderDirectoryEvent.NavigateToNewNote -> navigateToNewNote()
            is FolderDirectoryEvent.DismissNoteDetails -> dismissNoteDetails()

            is FolderDirectoryEvent.ToggleFolderDialogDropdown -> toggleFolderDialogDropdown()
            is FolderDirectoryEvent.SelectFolderForDialog -> selectFolderForDialog(event.folder)
        }
    }

    private fun loadFolders() {
        viewModelScope.launch {
            val rootFolderNode = buildDirectoryTree(
                noteService.getAllNotes(),
                folderService.getAllFolders()
            )
            // Initialize the expandedFolderIds with the root folder's ID
            val initialExpandedFolderIds = setOf(rootFolderNode?.folderEntity?.id)
            _state.value = FolderDirectoryState(rootFolderNode = rootFolderNode, expandedFolderIds = initialExpandedFolderIds)
        }
    }

    private fun toggleFolder(folderId: Int?) {
        val currentExpandedFolders = _state.value.expandedFolderIds.toMutableSet()
        //Expanding/collapsing a nested folder will not expand/collapse the root folder
        //if (folderId != null && folderId != _state.value.rootFolderNode?.id) {
            if (currentExpandedFolders.contains(folderId)) {
                currentExpandedFolders.remove(folderId)
            } else {
                currentExpandedFolders.add(folderId)
            }
            _state.value = _state.value.copy(expandedFolderIds = currentExpandedFolders)
       // }
    }

    private fun navigateToSelectNote(note: NotesEntity) {
        selectedNoteUseCase.apply {
            idA = note.idA!!
            idB = note.idB!!
            noteTitle = note.title
            noteContent = note.content
            createdAt = note.creation_date
            updatedAt = note.last_edit_date
        }
        navigator.navToSeeNote()
    }

    private fun showFolderDetails(folder: FolderEntity){
        _state.value = _state.value.copy(selectedFolderDetails = folder)
    }
    private fun dismissFolderDetails() {
        _state.value = _state.value.copy(selectedFolderDetails = null)
    }
    private fun showNoteDetails(note: NotesEntity) {
        _state.value = _state.value.copy(selectedNoteDetails = note)
    }
    private fun dismissNoteDetails() {
        _state.value = _state.value.copy(selectedNoteDetails = null)
    }
    private fun deleteFolderDetails(folder: FolderEntity){
        _state.value = _state.value.copy(selectedFolderDetails = null)
        viewModelScope.launch {
            folderService.deleteFolder(folder)
        }
    }


    private fun deleteNote(note: NotesEntity) {
        _state.value = _state.value.copy(selectedNoteDetails = null)
        viewModelScope.launch {
            noteService.deleteNote(note)
        }
    }

    private fun navigateBack() {
        navigator.popBackStack()
    }

    private fun navigateToNewNote(){
        navigator.navToNewNote()
    }

    private fun createNewFolder(folderName: String, parentFolderId: Int) {
        viewModelScope.launch {
            val folder = FolderEntity(
                title = folderName,
                //1 for now.
                parentFolderId = parentFolderId
            )
            folderService.insertFolder(folder)
        }
            //reload the folders to reflect the new folder in the UI
            //loadFolders()
            // Clear the new folder name after creation
            //_state.value = _state.value.copy(newFolderName = "")


    }
    private fun updateNewFolderName(name: String) {
        _state.value = _state.value.copy(newFolderName = name)
    }
    private fun toggleFolderDialogDropdown(){
        _state.value = _state.value.copy(isDropdownExpanded = !_state.value.isDropdownExpanded)
    }
    private fun selectFolderForDialog(folder: FolderEntity) {
        _state.value = _state.value.copy(
            selectedFolderForDialog = folder,
            parentFolderId = folder.id,
            isDropdownExpanded = false
        )
    }
    private fun fetchFoldersForDialog() {
        viewModelScope.launch {
            val folderList = folderService.getAllFolders().first() // Fetch folders from service
            _state.value = _state.value.copy(folderListForDialog = folderList)
        }
    }
}

data class FolderDirectoryState(
    val rootFolderNode: FolderNode? = null,
    val expandedFolderIds: Set<Int?> = emptySet(),
    val selectedNoteDetails: NotesEntity? = null,
    val selectedFolderDetails: FolderEntity? = null,
    val newFolderName: String = "",
    val isDropdownExpanded: Boolean = false,
    val selectedFolderForDialog: FolderEntity? = null,
    val parentFolderId: Int? = 0,
    val folderListForDialog: List<FolderEntity> = emptyList()
)

sealed class FolderDirectoryEvent {
    object LoadFolders : FolderDirectoryEvent()
    object NavigateBack : FolderDirectoryEvent()
    object NavigateToNewNote: FolderDirectoryEvent()
    data class CreateNewFolder(val folderName:String, val parentFolderId: Int): FolderDirectoryEvent()
    data class UpdateNewFolderName(val folderName: String): FolderDirectoryEvent()
    data class ToggleFolder(val folderId: Int?) : FolderDirectoryEvent()
    data class NavigateToSelectNote(val note: NotesEntity) : FolderDirectoryEvent()
    data class ShowNoteDetails(val note: NotesEntity) : FolderDirectoryEvent()
    data class DeleteNote(val note: NotesEntity) : FolderDirectoryEvent()
    object DismissNoteDetails : FolderDirectoryEvent()

    data class ShowFolderDetails(val folder: FolderEntity) : FolderDirectoryEvent()
    object DismissFolderDetails : FolderDirectoryEvent()
    data class DeleteFolderDetails(val folder: FolderEntity): FolderDirectoryEvent()

    object ToggleFolderDialogDropdown: FolderDirectoryEvent()
    data class SelectFolderForDialog(val folder: FolderEntity): FolderDirectoryEvent()
    // Add other events as needed
}

 */