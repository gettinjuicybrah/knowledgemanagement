package com.joeybasile.knowledgemanagement.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joeybasile.knowledgemanagement.data.database.data.repository.TokenRepository
import com.joeybasile.knowledgemanagement.network.service.PrivateService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.joeybasile.knowledgemanagement.ui.navigation.NavigatorImpl
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class NewNoteViewModel : ViewModel(), KoinComponent {
    private val privateService: PrivateService by inject()
    private val tokenRepository: TokenRepository by inject()
    private val navigator: NavigatorImpl by inject()

    private val _state = MutableStateFlow(NewNoteState())
    val state: StateFlow<NewNoteState> = _state.asStateFlow()

    fun handleEvent(event: NewNoteEvent) {
        when (event) {
            is NewNoteEvent.UpdateTitle -> updateTitle(event.title)
            is NewNoteEvent.UpdateContent -> updateContent(event.content)
            is NewNoteEvent.InsertNote -> insertNote()
            is NewNoteEvent.NavigateBack -> navigateBack()
        }
    }

    private fun updateTitle(title: String) {
        _state.value = _state.value.copy(title = title)
    }

    private fun updateContent(content: String) {
        _state.value = _state.value.copy(content = content)
    }

    private fun insertNote() {
        viewModelScope.launch {
            val accessToken = tokenRepository.getAccessToken()
            val refreshToken = tokenRepository.getRefreshToken()
            val title = _state.value.title
            val content = _state.value.content
                privateService.insertNote(accessToken, refreshToken, title, content)
                navigateBack()
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
    object InsertNote : NewNoteEvent()
    object NavigateBack : NewNoteEvent()
}