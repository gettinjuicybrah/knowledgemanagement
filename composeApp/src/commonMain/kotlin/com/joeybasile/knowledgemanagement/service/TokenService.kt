package com.joeybasile.knowledgemanagement.service

import com.joeybasile.knowledgemanagement.data.database.data.repository.TokenRepository
import com.joeybasile.knowledgemanagement.data.model.AccessToken
import com.joeybasile.knowledgemanagement.data.model.RefreshToken
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class TokenService: KoinComponent {
    private val tokenRepository: TokenRepository by inject()
    private val privateAPIService: PrivateAPIService by inject()
    suspend fun getAccessToken(): String {
        return tokenRepository.getAccessToken()
    }
    suspend fun getRefreshToken(): String {
        return tokenRepository.getRefreshToken()
    }
    suspend fun getAccessExpiry(): Long {
        return tokenRepository.getAccessTokenExpiry()
    }
    suspend fun getRefreshExpiry(): Long {
        return tokenRepository.getRefreshTokenExpiry()

    }

    suspend fun insertAccessToken(token: String, expire: Long) {
        tokenRepository.updateAccessToken(token, expire)
    }

    suspend fun insertRefreshToken(token: String, expire: Long) {
        tokenRepository.updateRefreshToken(token, expire)
    }

    suspend fun initializeTokenIfNeeded(){
        tokenRepository.initializeAccessTokenIfNeeded()
        tokenRepository.initializeRefreshTokenIfNeeded()
    }

    suspend fun validateAccessToken(accessToken: AccessToken) {

        if (checkExpired(accessToken.expiry)) {
            val refreshToken = RefreshToken(tokenRepository.getRefreshToken()!!, tokenRepository.getRefreshTokenExpiry()!!)
            privateAPIService.getAccessToken(refreshToken)
        }
    }

    private suspend fun checkExpired(expiry: Long):Boolean{
        //if 1 minute or more left on token, good to go.

        return expiry < 60000

    }


}