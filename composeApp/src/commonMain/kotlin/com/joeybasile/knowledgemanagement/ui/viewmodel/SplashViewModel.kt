package com.joeybasile.knowledgemanagement.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.joeybasile.knowledgemanagement.data.database.data.repository.TokenRepository
import com.joeybasile.knowledgemanagement.domain.repository.local.TokenRepository
import com.joeybasile.knowledgemanagement.ui.navigation.NavigatorImpl
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SplashViewModel: ViewModel(), KoinComponent {
    private val tokenRepository: TokenRepository by inject()
    private val navigator: NavigatorImpl by inject()
    suspend fun checkAuthState() {
        tokenRepository.initializeTokenIfNeeded()
        val hasLocalToken = tokenRepository.getLocalToken() != null
        val hasRefreshToken = tokenRepository.getRefreshToken() != null

        if (hasLocalToken || hasRefreshToken) {
            navigator.navToHome()
        } else {
            navigator.navToLogin()
        }
    }
}