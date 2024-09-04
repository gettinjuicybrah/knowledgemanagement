package com.joeybasile.knowledgemanagement.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joeybasile.knowledgemanagement.domain.repository.local.TokenRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.joeybasile.knowledgemanagement.ui.navigation.NavigatorImpl
import com.joeybasile.knowledgemanagement.network.api.services.PublicService
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.UUID

class LoginViewModel() : ViewModel(), KoinComponent {

    private val tokenRepository: TokenRepository by inject()
    private val publicService: PublicService by inject()
    private val navigator: NavigatorImpl by inject()
    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    fun handleEvent(event: LoginEvent){
        when (event){
            is LoginEvent.UpdateUsername -> updateUsername(event.username)
            is LoginEvent.UpdatePassword -> updatePassword(event.password)
            is LoginEvent.AttemptLogin -> attemptLogin()
            is LoginEvent.Register -> register()
            is LoginEvent.ContinueWithoutLogin -> continueWithoutLogin()
            is LoginEvent.DismissError -> dismissError()
        }
    }
    private fun updateUsername(username: String) {
        _state.value = _state.value.copy(username = username)
    }

    private fun updatePassword(password: String) {
        _state.value = _state.value.copy(password = password)
    }

    private fun attemptLogin() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            val result = publicService.login(_state.value.username, _state.value.password)
            result.fold(
                onSuccess = {
                    _state.value = _state.value.copy(isLoading = false)
                    navigator.navToHome()
                },
                onFailure = { exception ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = exception.message ?: "An unknown error occurred"
                    )
                }
            )
        }
    }
    private fun dismissError() {
        _state.value = _state.value.copy(error = null)
    }
    private fun register() {
        navigator.navToRegister()
    }

    private fun continueWithoutLogin() {
        viewModelScope.launch {
            val localToken = generateLocalToken()
            val expiration = System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000 // 7 days
            tokenRepository.updateLocalToken(localToken, expiration.toString())
            navigator.navToHome()
        }
    }

    private fun generateLocalToken(): String {
        return UUID.randomUUID().toString()
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
    object ContinueWithoutLogin : LoginEvent()
    object DismissError : LoginEvent()
}