package com.joeybasile.knowledgemanagement.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.joeybasile.knowledgemanagement.data.database.data.repository.TokenRepository
import com.joeybasile.knowledgemanagement.ui.navigation.NavigatorImpl
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SplashViewModel: ViewModel(), KoinComponent {
    private val tokenService: TokenRepository by inject()
    private val navigator: NavigatorImpl by inject()
    suspend fun checkAuthState() {
        tokenService.initializeTokenIfNeeded()
        val hasRefreshToken = tokenService.getRefreshToken() != null

        if (hasRefreshToken) {
            navigator.navToHome()
        } else {
            navigator.navToLogin()
        }
    }
}