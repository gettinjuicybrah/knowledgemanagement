package com.joeybasile.knowledgemanagement.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joeybasile.knowledgemanagement.util.SelectedNoteUseCase
import data.database.root.NotesEntity
import domain.repository.local.NoteRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.joeybasile.knowledgemanagement.ui.navigation.NavigatorImpl
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.text.SimpleDateFormat
import java.util.*

class ListNotesViewModel : ViewModel(), KoinComponent {
    private val noteRepositoryImpl: NoteRepositoryImpl by inject()
    private val navigator: NavigatorImpl by inject()
    private val selectedNoteUseCase: SelectedNoteUseCase by inject()

    private val _state = MutableStateFlow(ListNotesState())
    val state: StateFlow<ListNotesState> = _state.asStateFlow()

    init {
        loadNotes()
    }

    fun handleEvent(event: ListNotesEvent) {
        when (event) {
            is ListNotesEvent.SelectNote -> selectNote(event.note)
            is ListNotesEvent.ShowNoteDetails -> showNoteDetails(event.note)
            is ListNotesEvent.DeleteNote -> deleteNote(event.note)
            /*
            The presence or absence of is depends on whether we're checking
            a type (with potential associated data) or matching against a specific object instance.
             */
            ListNotesEvent.NavigateBack -> navigateBack()
            ListNotesEvent.DismissNoteDetails -> dismissNoteDetails()
        }
    }

    private fun loadNotes() {
        viewModelScope.launch {
            noteRepositoryImpl.getAllNotes().collect { notes ->
                println("Received updated notes: ${notes.size}")
                _state.value = _state.value.copy(notes = notes)
            }
        }
    }

    private fun selectNote(note: NotesEntity) {
        selectedNoteUseCase.apply {
            idA = note.idA!!
            idB = note.idB!!
            noteTitle = note.title
            noteContent = note.content
            createdAt = note.createdAt
            updatedAt = note.updatedAt
        }
        navigator.navToSeeNote()
    }

    private fun showNoteDetails(note: NotesEntity) {
        _state.value = _state.value.copy(selectedNoteDetails = note)
    }

    private fun deleteNote(note: NotesEntity) {
        viewModelScope.launch {
            noteRepositoryImpl.deleteNote(note)
            dismissNoteDetails()
        }
    }

    private fun navigateBack() {
        navigator.popBackStack()
    }

    private fun dismissNoteDetails() {
        _state.value = _state.value.copy(selectedNoteDetails = null)
    }

    fun formatDate(timestamp: Long): String {
        val sdf = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault())
        return sdf.format(Date(timestamp))
    }
}

data class ListNotesState(
    val notes: List<NotesEntity> = emptyList(),
    val selectedNoteDetails: NotesEntity? = null
)

sealed class ListNotesEvent {
    data class SelectNote(val note: NotesEntity) : ListNotesEvent()
    data class ShowNoteDetails(val note: NotesEntity) : ListNotesEvent()
    data class DeleteNote(val note: NotesEntity) : ListNotesEvent()
    object NavigateBack : ListNotesEvent()
    object DismissNoteDetails : ListNotesEvent()
}