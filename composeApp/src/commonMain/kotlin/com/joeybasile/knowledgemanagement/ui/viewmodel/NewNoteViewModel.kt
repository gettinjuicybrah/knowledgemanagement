package com.joeybasile.knowledgemanagement.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joeybasile.knowledgemanagement.domain.repository.local.NoteRepositoryImpl
import com.joeybasile.knowledgemanagement.data.database.root.NotesEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.joeybasile.knowledgemanagement.ui.navigation.NavigatorImpl
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.UUID

class NewNoteViewModel : ViewModel(), KoinComponent {
    private val noteRepositoryImpl: NoteRepositoryImpl by inject()
    private val navigator: NavigatorImpl by inject()

    private val _state = MutableStateFlow(NewNoteState())
    val state: StateFlow<NewNoteState> = _state.asStateFlow()

    fun handleEvent(event: NewNoteEvent) {
        when (event) {
            is NewNoteEvent.UpdateTitle -> updateTitle(event.title)
            is NewNoteEvent.UpdateContent -> updateContent(event.content)
            is NewNoteEvent.SaveNote -> saveNote()
            is NewNoteEvent.NavigateBack -> navigateBack()
        }
    }

    private fun updateTitle(title: String) {
        _state.value = _state.value.copy(title = title)
    }

    private fun updateContent(content: String) {
        _state.value = _state.value.copy(content = content)
    }

    private fun saveNote() {
        viewModelScope.launch {
            val idA = generateLocalToken()
            val idB = generateLocalToken()
            val note = NotesEntity(
                idA = idA,
                idB = idB,
                title = _state.value.title,
                content = _state.value.content,
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis()
            )
            try {
                noteRepositoryImpl.insertNote(note)
                navigateBack()
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message ?: "Failed to save note")
            }
        }
    }

    private fun navigateBack() {
        navigator.popBackStack()
    }
}

data class NewNoteState(
    val title: String = "",
    val content: String = "",
    val error: String? = null
)

sealed class NewNoteEvent {
    data class UpdateTitle(val title: String) : NewNoteEvent()
    data class UpdateContent(val content: String) : NewNoteEvent()
    object SaveNote : NewNoteEvent()
    object NavigateBack : NewNoteEvent()
}
private fun generateLocalToken(): String {
    return UUID.randomUUID().toString()
}