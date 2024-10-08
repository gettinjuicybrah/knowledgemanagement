package com.joeybasile.knowledgemanagement.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joeybasile.knowledgemanagement.data.database.data.repository.TokenRepository
import com.joeybasile.knowledgemanagement.service.AuthService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.joeybasile.knowledgemanagement.ui.navigation.NavigatorImpl
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class LoginViewModel() : ViewModel(), KoinComponent {

    private val authService: AuthService by inject()
    private val navigator: NavigatorImpl by inject()
    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    fun handleEvent(event: LoginEvent){
        when (event){
            is LoginEvent.UpdateUsername -> updateUsername(event.username)
            is LoginEvent.UpdatePassword -> updatePassword(event.password)
            is LoginEvent.AttemptLogin -> attemptLogin()
            is LoginEvent.Register -> register()
            is LoginEvent.DismissError -> dismissError()
        }
    }
    private fun updateUsername(username: String) {
        _state.value = _state.value.copy(username = username)
    }

    private fun updatePassword(password: String) {
        _state.value = _state.value.copy(password = password)
    }
    //assuming success
    private fun attemptLogin() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            val result = authService.login(_state.value.username, _state.value.password)
            _state.value = _state.value.copy(isLoading = false)
            navigator.navToHome()
        }
    }
    private fun dismissError() {
        _state.value = _state.value.copy(error = null)
    }
    private fun register() {
        navigator.navToRegister()
    }


}

data class LoginState(
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed class LoginEvent {
    data class UpdateUsername(val username: String) : LoginEvent()
    data class UpdatePassword(val password: String) : LoginEvent()
    object AttemptLogin : LoginEvent()
    object Register : LoginEvent()
    object DismissError : LoginEvent()
}