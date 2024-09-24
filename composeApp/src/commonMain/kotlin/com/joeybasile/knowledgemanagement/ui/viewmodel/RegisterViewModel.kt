package com.joeybasile.knowledgemanagement.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.joeybasile.knowledgemanagement.ui.navigation.NavigatorImpl
import com.joeybasile.knowledgemanagement.network.api.services.PublicService
import com.joeybasile.knowledgemanagement.network.service.PublicService
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RegisterViewModel() : ViewModel(), KoinComponent {

    private val publicService: PublicService by inject()
    private val navigator: NavigatorImpl by inject()

    private val _state = MutableStateFlow(RegisterState())
    val state: StateFlow<RegisterState> = _state.asStateFlow()

    fun handleEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.UpdateUsername -> updateUsername(event.username)
            is RegisterEvent.UpdatePassword -> updatePassword(event.password)
            is RegisterEvent.AttemptRegister -> attemptRegister()
            is RegisterEvent.GoBack -> goBack()
            is RegisterEvent.DismissError -> dismissError()
        }
    }
    private fun updateUsername(username: String) {
        _state.value = _state.value.copy(username = username)
    }
    private fun updatePassword(password: String) {
        _state.value = _state.value.copy(password = password)
    }
    private fun attemptRegister() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            val result = publicService.register(_state.value.username, _state.value.password)
            result.fold(
                onSuccess = {
                    _state.value = _state.value.copy(isLoading = false)
                    navigator.navToLogin()
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
    private fun goBack() {
        navigator.popBackStack()
    }
    private fun dismissError() {
        _state.value = _state.value.copy(error = null)
    }
}

data class RegisterState(
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed class RegisterEvent {
    data class UpdateUsername(val username: String) : RegisterEvent()
    data class UpdatePassword(val password: String) : RegisterEvent()
    object AttemptRegister : RegisterEvent()
    object GoBack : RegisterEvent()
    object DismissError : RegisterEvent()
}