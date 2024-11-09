package com.joeybasile.knowledgemanagement.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joeybasile.knowledgemanagement.service.PrivateAPIService
import com.joeybasile.knowledgemanagement.service.TokenService
import com.joeybasile.knowledgemanagement.service.UserService
import com.joeybasile.knowledgemanagement.ui.theme.ThemeManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.joeybasile.knowledgemanagement.ui.navigation.NavigatorImpl
import com.joeybasile.knowledgemanagement.util.isLoggedIn
import com.joeybasile.knowledgemanagement.util.userEntityToUser
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SettingsViewModel : ViewModel(), KoinComponent {
    private val tokenService: TokenService by inject()
    private val userService: UserService by inject()
    private val navigator: NavigatorImpl by inject()

    private val _state = MutableStateFlow(SettingsState())
    val state: StateFlow<SettingsState> = _state.asStateFlow()

    init {
        checkLoginStatus()
        observeTheme()
    }

    fun handleEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.ToggleLogin -> toggleLogin()
            is SettingsEvent.NavigateBack -> navigateBack()
            is SettingsEvent.ToggleTheme -> toggleTheme()
        }
    }

    private fun checkLoginStatus() {
        viewModelScope.launch {
            val isLoggedIn = isLoggedIn(tokenService.getRefreshExpiry())
            _state.value = _state.value.copy(isLoggedIn = isLoggedIn)
        }
    }

    private fun observeTheme() {
        viewModelScope.launch {
            val theme = userService.getTheme()
            val isDark = theme == false
            ThemeManager.isDarkTheme = isDark // Initialize ThemeManager with stored preference
            _state.value = _state.value.copy(isDarkTheme = isDark)

        }
    }

    private fun toggleTheme() {
        viewModelScope.launch {
            val newThemeState = ThemeManager.toggleTheme()
            val newTheme = !newThemeState
            userService.updateTheme(newTheme)
            _state.value = _state.value.copy(isDarkTheme = newThemeState)
        }
    }

    private fun toggleLogin() {
        if (_state.value.isLoggedIn) {
            viewModelScope.launch {
                tokenService.insertRefreshToken("", 0)
                tokenService.insertAccessToken("", 0)
                _state.value = _state.value.copy(isLoggedIn = false, userEmail = "")
                navigator.navToLogin()
            }
        } else {
            navigator.navToLogin()
        }
    }

    private fun navigateBack() {
        navigator.popBackStack()
    }
}

data class SettingsState(
    val isLoggedIn: Boolean = false,
    val userEmail: String = "",
    val isDarkTheme: Boolean = false
)

sealed class SettingsEvent {
    object ToggleLogin : SettingsEvent()
    object NavigateBack : SettingsEvent()
    object ToggleTheme : SettingsEvent()
}