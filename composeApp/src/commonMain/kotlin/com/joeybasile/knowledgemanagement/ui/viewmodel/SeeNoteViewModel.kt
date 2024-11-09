package com.joeybasile.knowledgemanagement.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joeybasile.knowledgemanagement.data.database.entity.FolderEntity
import com.joeybasile.knowledgemanagement.data.database.entity.NotesEntity
import com.joeybasile.knowledgemanagement.service.FolderService
import com.joeybasile.knowledgemanagement.service.NoteService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.joeybasile.knowledgemanagement.ui.navigation.NavigatorImpl
import com.joeybasile.knowledgemanagement.util.SelectedNoteUseCase
import kotlinx.coroutines.flow.first
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SeeNoteViewModel : ViewModel(), KoinComponent {
    private val selectedNoteUseCase: SelectedNoteUseCase by inject()
    private val noteService: NoteService by inject()
    private val folderService: FolderService by inject()
    private val navigator: NavigatorImpl by inject()

    private val _state = MutableStateFlow(SeeNoteState())
    val state: StateFlow<SeeNoteState> = _state.asStateFlow()

    init {
        fetchFolders()
        loadSelectedNote()
    }

    fun handleEvent(event: SeeNoteEvent) {
        when (event) {
            is SeeNoteEvent.UpdateTitle -> updateTitle(event.title)
            is SeeNoteEvent.UpdateContent -> updateContent(event.content)
            is SeeNoteEvent.UpdateFolder -> updateFolder(event.folder)
            is SeeNoteEvent.SaveAndNavigateBack -> saveAndNavigateBack()
            is SeeNoteEvent.ToggleDropdown -> toggleDropdown()
        }
    }
    private fun loadSelectedNote() {
        _state.value = _state.value.copy(
            id = selectedNoteUseCase.id,
            parentFolderId = selectedNoteUseCase.parentFolderId,
            title = selectedNoteUseCase.noteTitle,
            content = selectedNoteUseCase.noteContent,
            creation_date = selectedNoteUseCase.createdAt,
            last_edit_date = selectedNoteUseCase.updatedAt,
            version = selectedNoteUseCase.version,

        )
    }
    /*
    _state.value gives you the current state object.
.copy() creates a new instance of the state object.
title = title or content = content updates only the specified field in the new copy.
The new state object is then assigned back to _state.value
     */
    private fun updateTitle(title: String) {
        _state.value = _state.value.copy(title = title)
    }

    private fun updateContent(content: String) {
        _state.value = _state.value.copy(content = content)
    }

    private fun saveAndNavigateBack() {
        viewModelScope.launch {
            val currentState = _state.value
            val updatedNote = NotesEntity(
                id = currentState.id,
                parentFolderId = currentState.selectedFolder?.id,
                title = currentState.title,
                content = currentState.content,
                creation_date = "",
                last_edit_date = "",
                version = currentState.version + 1
            )


            noteService.updateNote(updatedNote)


            navigator.popBackStack()
        }
    }
    private fun fetchFolders() {
        viewModelScope.launch {
            val folderList = folderService.getAllFolders().first() // Fetch folders from service
            _state.value = _state.value.copy(folderList = folderList)
        }
    }

    private fun updateFolder(folder: FolderEntity) {
        _state.value = _state.value.copy(
            selectedFolder = folder,
            //it's a note. the parent id is just the folder's id.
            parentFolderId = folder.id,
            isDropdownExpanded = false
        )
    }
    private fun toggleDropdown() {
        _state.value = _state.value.copy(isDropdownExpanded = !_state.value.isDropdownExpanded)
    }

}

data class SeeNoteState(
    val id: String = "",
    val parentFolderId: String? = "",
    val title: String = "",
    val content: String = "",
    val creation_date: String = "",
    val last_edit_date: String = "",
    val version: Int = 0,
    val folderList: List<FolderEntity> = emptyList(),
    val selectedFolder: FolderEntity? = null,
    val isDropdownExpanded: Boolean = false
)

sealed class SeeNoteEvent {
    data class UpdateTitle(val title: String) : SeeNoteEvent()
    data class UpdateContent(val content: String) : SeeNoteEvent()
    data class UpdateFolder(val folder: FolderEntity): SeeNoteEvent()
    object SaveAndNavigateBack : SeeNoteEvent()
    object ToggleDropdown: SeeNoteEvent()
}
