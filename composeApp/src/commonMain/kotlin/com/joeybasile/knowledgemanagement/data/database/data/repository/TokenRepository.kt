package com.joeybasile.knowledgemanagement.data.database.data.repository

import com.joeybasile.knowledgemanagement.data.database.entity.TokenEntity

interface TokenRepository {
    suspend fun getAccessToken(): String?
    suspend fun getRefreshToken(): String?
    suspend fun getAccessTokenExpiry(): String?
    suspend fun getRefreshTokenExpiry(): String?
    suspend fun insertToken(token: TokenEntity)
    suspend fun updateAccessToken(accessToken: String, accExpire: String)
    suspend fun updateRefreshToken(refreshToken: String, refreshExpire: String)
    suspend fun initializeTokenIfNeeded()
}