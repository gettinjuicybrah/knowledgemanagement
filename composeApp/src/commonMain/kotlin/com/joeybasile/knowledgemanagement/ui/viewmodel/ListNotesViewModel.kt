package com.joeybasile.knowledgemanagement.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.State
import com.joeybasile.knowledgemanagement.data.database.entity.NotesEntity
import com.joeybasile.knowledgemanagement.service.NoteService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.joeybasile.knowledgemanagement.ui.navigation.NavigatorImpl
import com.joeybasile.knowledgemanagement.util.SelectedNoteUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ListNotesViewModel : ViewModel(), KoinComponent {
    private val navigator: NavigatorImpl by inject()
    private val noteService: NoteService by inject()
    private val selectedNoteUseCase: SelectedNoteUseCase by inject()

    private val _state = MutableStateFlow(ListNotesState())
    val state: StateFlow<ListNotesState> get() = _state.asStateFlow()

    init {
        loadNotes()
    }

    fun handleEvent(event: ListNotesEvent) {
        when (event) {
            is ListNotesEvent.NavigateBack -> navigateBack()

            is ListNotesEvent.NavigateToSelectNote -> navigateToSelectNote(event.note)
            is ListNotesEvent.DeleteNote -> deleteNote(event.note)
            is ListNotesEvent.DismissNoteDetails -> dismissNoteDetails()
            is ListNotesEvent.ShowNoteDetails -> showNoteDetails(event.note)
        }
    }

    private fun loadNotes() {
        viewModelScope.launch {

            noteService.getAllNotes().collect { notes ->
                println("Received updated notes: ${notes.size}")
                _state.value = _state.value.copy(notes = notes)
            }
        }
    }

    private fun navigateToSelectNote(note: NotesEntity) {
        selectedNoteUseCase.apply {
            id = note.id!!
            noteTitle = note.title
            noteContent = note.content
            createdAt = note.creation_date
            updatedAt = note.last_edit_date
        }
        navigator.navToSeeNote()
    }

    private fun showNoteDetails(note: NotesEntity) {
        _state.value = _state.value.copy(selectedNoteDetails = note)
    }

    private fun deleteNote(note: NotesEntity) {
        viewModelScope.launch {
            noteService.deleteNote(note)
            dismissNoteDetails()
        }
    }

    private fun navigateBack() {
        navigator.popBackStack()
    }

    private fun dismissNoteDetails() {
        _state.value = _state.value.copy(selectedNoteDetails = null)
    }

}

data class ListNotesState(
    val notes: List<NotesEntity> = emptyList(),
    val selectedNoteDetails: NotesEntity? = null
)

sealed class ListNotesEvent {
    data class NavigateToSelectNote(val note: NotesEntity) : ListNotesEvent()
    data class ShowNoteDetails(val note: NotesEntity) : ListNotesEvent()
    data class DeleteNote(val note: NotesEntity) : ListNotesEvent()
    object NavigateBack : ListNotesEvent()
    object DismissNoteDetails : ListNotesEvent()
}