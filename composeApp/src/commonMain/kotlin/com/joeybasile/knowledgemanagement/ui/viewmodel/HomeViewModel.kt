package com.joeybasile.knowledgemanagement.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.joeybasile.knowledgemanagement.ui.navigation.NavigatorImpl
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class HomeViewModel() : ViewModel(), KoinComponent {
    private val navigator: NavigatorImpl by inject()


    fun handleEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.NavigateToSettings -> navigateToSettings()
            is HomeEvent.NavigateToListNotes -> navigateToListNotes()
            is HomeEvent.NavigateToNewNote -> navigateToNewNote()
        }
    }
    private fun navigateToSettings() {
        navigator.navToSettings()
    }
    private fun navigateToListNotes() {
        navigator.navToListNotes()
    }
    private fun navigateToNewNote() {
        navigator.navToNewNote()
    }
}
sealed class HomeEvent {
    object NavigateToSettings : HomeEvent()
    object NavigateToListNotes : HomeEvent()
    object NavigateToNewNote : HomeEvent()

}