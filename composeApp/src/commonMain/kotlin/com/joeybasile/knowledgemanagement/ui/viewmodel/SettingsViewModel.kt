package com.joeybasile.knowledgemanagement.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joeybasile.knowledgemanagement.data.database.data.repository.TokenRepository
import com.joeybasile.knowledgemanagement.service.TokenService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.joeybasile.knowledgemanagement.ui.navigation.NavigatorImpl
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SettingsViewModel : ViewModel(), KoinComponent {
    //private val tokenRepository: TokenRepository by inject()
    private val tokenService: TokenService by inject()
    private val navigator: NavigatorImpl by inject()

    private val _state = MutableStateFlow(SettingsState())
    val state: StateFlow<SettingsState> = _state.asStateFlow()

    init {
        checkLoginStatus()
        //getUserEmail(userRepository.getUserInfo().userIdA, userRepository.getUserInfo().userIdB)
    }

    fun handleEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.ToggleLogin -> toggleLogin()
            is SettingsEvent.NavigateBack -> navigateBack()
        }
    }

    private fun checkLoginStatus() {
        viewModelScope.launch {
            val isLoggedIn = tokenService.getRefreshToken() != null
            _state.value = _state.value.copy(isLoggedIn = isLoggedIn)
        }
    }

    private fun toggleLogin() {
        if (_state.value.isLoggedIn) {
            // Logout logic
            viewModelScope.launch {
                tokenService.insertRefreshToken("", "") // Clear refresh token
                tokenService.insertAccessToken("", "") // Clear access token
                _state.value = _state.value.copy(isLoggedIn = false, userEmail = "")
                navigator.navToLogin() // Navigate to login screen after logout
            }
        } else {
            // Navigate to login screen
            navigator.navToLogin()
        }
    }

    private fun navigateBack() {
        navigator.popBackStack()
    }
}

data class SettingsState(
    val isLoggedIn: Boolean = false,
    val userEmail: String = ""
)

sealed class SettingsEvent {
    object ToggleLogin : SettingsEvent()
    object NavigateBack : SettingsEvent()
}