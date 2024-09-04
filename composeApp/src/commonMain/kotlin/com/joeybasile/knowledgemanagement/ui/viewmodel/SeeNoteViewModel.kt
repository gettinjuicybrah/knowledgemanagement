package com.joeybasile.knowledgemanagement.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joeybasile.knowledgemanagement.data.database.root.NotesEntity
import com.joeybasile.knowledgemanagement.domain.repository.local.NoteRepositoryImpl
import com.joeybasile.knowledgemanagement.util.SelectedNoteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.joeybasile.knowledgemanagement.ui.navigation.NavigatorImpl
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SeeNoteViewModel : ViewModel(), KoinComponent {
    private val noteRepositoryImpl: NoteRepositoryImpl by inject()
    private val navigator: NavigatorImpl by inject()
    private val selectedNoteUseCase: SelectedNoteUseCase by inject()

    private val _state = MutableStateFlow(SeeNoteState())
    val state: StateFlow<SeeNoteState> = _state.asStateFlow()

    init {
        loadSelectedNote()
    }

    fun handleEvent(event: SeeNoteEvent) {
        when (event) {
            is SeeNoteEvent.UpdateTitle -> updateTitle(event.title)
            is SeeNoteEvent.UpdateContent -> updateContent(event.content)
            is SeeNoteEvent.SaveAndNavigateBack -> saveAndNavigateBack()
        }
    }

    private fun loadSelectedNote() {
        _state.value = _state.value.copy(
            idA = selectedNoteUseCase.idA,
            idB = selectedNoteUseCase.idB,
            title = selectedNoteUseCase.noteTitle,
            content = selectedNoteUseCase.noteContent,
            createdAt = selectedNoteUseCase.createdAt
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
            val updatedNote = Note(
                idA = currentState.idA,
                idB = currentState.idB,
                title = currentState.title,
                content = currentState.content,
                updatedAt = System.currentTimeMillis(),
                createdAt = currentState.createdAt
                // Assuming you want to keep the original createdAt
                // If you don't have it in the state, you might need to fetch it from the repository
            )


            noteRepositoryImpl.updateNote(updatedNote.toEntity())


            navigator.popBackStack()
        }
    }
    private fun Note.toEntity(): NotesEntity =
        NotesEntity(
            idA = this.idA,
            idB = this.idB,
            title = this.title,
            content = this.content,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt
        )
}

data class SeeNoteState(
    val idA: String = "",
    val idB: String = "",
    val title: String = "",
    val content: String = "",
    val createdAt: Long = -1,
    val updatedAt: Long = -1
)

sealed class SeeNoteEvent {
    data class UpdateTitle(val title: String) : SeeNoteEvent()
    data class UpdateContent(val content: String) : SeeNoteEvent()
    object SaveAndNavigateBack : SeeNoteEvent()
}
data class Note(
    val idA: String,
    val idB: String,
    val title: String,
    val content: String,
    val updatedAt: Long,
    val createdAt: Long
)