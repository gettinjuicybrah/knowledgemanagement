package com.joeybasile.knowledgemanagement.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joeybasile.knowledgemanagement.data.database.entity.FolderEntity
import com.joeybasile.knowledgemanagement.service.NoteService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.joeybasile.knowledgemanagement.ui.navigation.NavigatorImpl
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import com.joeybasile.knowledgemanagement.data.database.entity.NotesEntity
import com.joeybasile.knowledgemanagement.service.FolderService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.first
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class NewNoteViewModel : ViewModel(), KoinComponent {
    private val noteService: NoteService by inject()
    private val folderService: FolderService by inject()
    //private val privateService: PrivateService by inject()
    //private val tokenRepository: TokenRepository by inject()
    private val navigator: NavigatorImpl by inject()

    private val _state = MutableStateFlow(NewNoteState())
    val state: StateFlow<NewNoteState> = _state.asStateFlow()

    init {
        fetchFolders()
    }

    fun handleEvent(event: NewNoteEvent) {
        when (event) {
            is NewNoteEvent.UpdateTitle -> updateTitle(event.title)
            is NewNoteEvent.UpdateContent -> updateContent(event.content)
            is NewNoteEvent.InsertNote -> insertNote()
            is NewNoteEvent.NavigateBack -> navigateBack()
            is NewNoteEvent.SelectFolder -> selectFolder(event.folder)
            is NewNoteEvent.ToggleDropdown -> toggleDropdown()
        }
    }

    private fun updateTitle(title: String) {
        _state.value = _state.value.copy(title = title)
    }

    private fun updateContent(content: String) {
        _state.value = _state.value.copy(content = content)
    }

    @OptIn(ExperimentalUuidApi::class)
    private fun insertNote() {
        viewModelScope.launch {
            val note = NotesEntity(
                id = Uuid.random().toString(),
                parentFolderId = _state.value.parentFolderId,
                title = _state.value.title,
                content = _state.value.content,
                creation_date = "",
                last_edit_date = "",
                version = 1
            )
            noteService.insertNote(note)
            navigateBack()
        }
    }
    private fun navigateBack() {
        navigator.popBackStack()
    }
    private fun fetchFolders() {
        viewModelScope.launch {
            val folderList = folderService.getAllFolders().first() // Fetch folders from service
            _state.value = _state.value.copy(folderList = folderList)
        }
    }

    private fun selectFolder(folder: FolderEntity) {
        _state.value = _state.value.copy(
            selectedFolder = folder,
            parentFolderId = folder.id,
            isDropdownExpanded = false
        )
    }
    private fun toggleDropdown() {
        _state.value = _state.value.copy(isDropdownExpanded = !_state.value.isDropdownExpanded)
    }
}

data class NewNoteState(
    val title: String = "",
    val content: String = "",
    val error: String? = null,
    val version: Int? = 0,
    val folderList: List<FolderEntity> = emptyList(),
    val selectedFolder: FolderEntity? = null,
    val parentFolderId: String? = "",
    val isDropdownExpanded: Boolean = false
)

sealed class NewNoteEvent {
    data class UpdateTitle(val title: String) : NewNoteEvent()
    data class UpdateContent(val content: String) : NewNoteEvent()
    data class SelectFolder(val folder: FolderEntity): NewNoteEvent()
    object InsertNote : NewNoteEvent()
    object NavigateBack : NewNoteEvent()
    object ToggleDropdown: NewNoteEvent()
}