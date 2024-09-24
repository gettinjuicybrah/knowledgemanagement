package com.joeybasile.knowledgemanagement.network.service

import com.joeybasile.knowledgemanagement.data.database.data.repository.TokenRepository
import com.joeybasile.knowledgemanagement.network.api.PublicAPI
import com.joeybasile.knowledgemanagement.network.request.public.LoginRequest
import com.joeybasile.knowledgemanagement.network.request.public.RegisterRequest
import com.joeybasile.knowledgemanagement.network.response.public.RegisterResponse
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PublicService : KoinComponent {
    private val publicAPI: PublicAPI by inject()
    private val tokenRepository: TokenRepository by inject()
    suspend fun login(email: String, password: String): Result<Unit> {
        val loginRequest = LoginRequest(email, password)
        return publicAPI.login(loginRequest).map { response ->
            tokenRepository.updateAccessToken(response.accessToken, response.accessTokenExpiry)
            tokenRepository.updateRefreshToken(response.refreshToken, response.refreshTokenExpiry)
        }
    }

    suspend fun register(email: String, password: String): Result<RegisterResponse> {
        val registerRequest = RegisterRequest(email, password)
        return publicAPI.register(registerRequest)
    }
}