package com.joeybasile.knowledgemanagement.service

import com.joeybasile.knowledgemanagement.data.database.data.repository.TokenRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class TokenService: KoinComponent {
    private val tokenRepository: TokenRepository by inject()

    suspend fun getAccessToken(): String? {
        return tokenRepository.getAccessToken()
    }
    suspend fun getRefreshToken(): String? {
        return tokenRepository.getRefreshToken()
    }
    suspend fun getAccessExpiry(): String? {
        return tokenRepository.getAccessTokenExpiry()
    }
    suspend fun getRefreshExpiry(): String? {
        return tokenRepository.getRefreshTokenExpiry()

    }

}