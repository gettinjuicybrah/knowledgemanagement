package com.joeybasile.knowledgemanagement.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joeybasile.knowledgemanagement.data.database.data.repository.TokenRepository
import com.joeybasile.knowledgemanagement.data.database.entity.UserEntity
import com.joeybasile.knowledgemanagement.data.model.UserAuth
import com.joeybasile.knowledgemanagement.network.response.public.LoginResponse
import com.joeybasile.knowledgemanagement.service.PrivateAPIService
import com.joeybasile.knowledgemanagement.service.PublicAPIService
import com.joeybasile.knowledgemanagement.service.TokenService
import com.joeybasile.knowledgemanagement.service.UserService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.joeybasile.knowledgemanagement.ui.navigation.NavigatorImpl
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class LoginViewModel() : ViewModel(), KoinComponent {

    private val publicAPIService: PublicAPIService by inject()
    private val tokenService: TokenService by inject()
    private val userService: UserService by inject()
    private val navigator: NavigatorImpl by inject()
    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    fun handleEvent(event: LoginEvent){
        when (event){
            is LoginEvent.UpdateUsername -> updateUsername(event.username)
            is LoginEvent.UpdatePassword -> updatePassword(event.password)
            is LoginEvent.AttemptLogin -> attemptLogin()
            is LoginEvent.Register -> navigator.navToRegister()
            is LoginEvent.ContinueWithoutLogin -> navigator.navToHome()
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
            val userAuth = UserAuth(_state.value.username, _state.value.password)
            val response = publicAPIService.login(userAuth)
            when (response) {
                is LoginResponse.Fail -> {
                    println("login failed")
                    _state.value = _state.value.copy(isLoading = false)
                }
                is LoginResponse.Success -> {
                    val accessToken = response.accessToken
                    val refreshToken = response.refreshToken
                    val userId = response.userId
                    val theme = response.userTheme
                    userService.setUsername(_state.value.username)
                    userService.updateUser(UserEntity(id = userId, username = _state.value.username, theme = theme))
                    tokenService.insertAccessToken(accessToken.JWTToken, accessToken.expiry)
                    tokenService.insertRefreshToken(refreshToken.JWTToken, refreshToken.expiry)
                    _state.value = _state.value.copy(isLoading = false)
                    navigator.navToHome()
                }

            }
        }
            _state.value = _state.value.copy(isLoading = false)
            navigator.navToHome()
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
    object ContinueWithoutLogin: LoginEvent()

}