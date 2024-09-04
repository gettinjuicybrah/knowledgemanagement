package com.joeybasile.knowledgemanagement.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joeybasile.knowledgemanagement.domain.repository.local.TokenRepositoryImpl
import com.joeybasile.knowledgemanagement.domain.repository.local.UserRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.joeybasile.knowledgemanagement.ui.navigation.NavigatorImpl
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SettingsViewModel : ViewModel(), KoinComponent {
    private val tokenRepository: TokenRepositoryImpl by inject()
    private val userRepository: UserRepositoryImpl by inject()
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
            val isLoggedIn = tokenRepository.getRefreshToken() != null
            _state.value = _state.value.copy(isLoggedIn = isLoggedIn)
        }
    }

    private fun getUserEmail(userIdA: String, userIdB: String) {
        viewModelScope.launch {
            val userInfo = userRepository.getUserInfo(userIdA, userIdB) // Assuming user ID is 1 for simplicity
            _state.value = _state.value.copy(userEmail = userInfo?.email ?: "")
        }
    }

    private fun toggleLogin() {
        if (_state.value.isLoggedIn) {
            // Logout logic
            viewModelScope.launch {
                tokenRepository.updateRefreshToken("", "") // Clear refresh token
                tokenRepository.updateAccessToken("", "") // Clear access token
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