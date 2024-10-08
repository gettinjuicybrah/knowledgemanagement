package com.joeybasile.knowledgemanagement.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.joeybasile.knowledgemanagement.data.database.entity.FolderEntity
import com.joeybasile.knowledgemanagement.data.database.entity.FolderMembersEntity
import com.joeybasile.knowledgemanagement.data.database.entity.NotesEntity
import com.joeybasile.knowledgemanagement.ui.navigation.NavigatorImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class FolderDirectoryViewModel() : ViewModel(), KoinComponent {
    private val navigator: NavigatorImpl by inject()
    private val _state = MutableStateFlow(FolderDirectoryState())
    val state: StateFlow<FolderDirectoryState> = _state.asStateFlow()

    init{
        loadFolders()
    }

    fun handleEvent(event: FolderDirectoryEvent) {
        when (event) {
            FolderDirectoryEvent.LoadFolders -> loadFolders()
            FolderDirectoryEvent.LoadMembers -> TODO()
            FolderDirectoryEvent.NavigateBack -> navigateBack()
            FolderDirectoryEvent.addFolder -> TODO()
            FolderDirectoryEvent.addMember -> TODO()
            FolderDirectoryEvent.collapseFolder -> TODO()
            FolderDirectoryEvent.deleteFolder -> TODO()
            FolderDirectoryEvent.expandFolder -> TODO()
            FolderDirectoryEvent.removeMember -> TODO()
            FolderDirectoryEvent.renameFolder -> TODO()
        }
    }

    private fun loadFolders(){

    }
    private fun navigateBack() {
        navigator.popBackStack()
    }
}

data class FolderDirectoryState(
    val folders: List<FolderEntity> = emptyList(),
    val members: List<FolderMembersEntity> = emptyList()
)

sealed class FolderDirectoryEvent {
    object LoadFolders : FolderDirectoryEvent()
    object LoadMembers : FolderDirectoryEvent()
    object NavigateBack : FolderDirectoryEvent()
    object collapseFolder : FolderDirectoryEvent()
    object expandFolder : FolderDirectoryEvent()
    object deleteFolder : FolderDirectoryEvent()
    object renameFolder : FolderDirectoryEvent()
    object addFolder : FolderDirectoryEvent()
    object addMember : FolderDirectoryEvent()
    object removeMember : FolderDirectoryEvent()
    data class SelectNote(val note: NotesEntity) : ListNotesEvent()
    data class ShowNoteDetails(val note: NotesEntity) : ListNotesEvent()
    data class DeleteNote(val note: NotesEntity) : ListNotesEvent()
}