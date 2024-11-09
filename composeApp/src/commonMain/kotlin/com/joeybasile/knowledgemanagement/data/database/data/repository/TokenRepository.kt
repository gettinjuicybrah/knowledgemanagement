package com.joeybasile.knowledgemanagement.data.database.data.repository

import com.joeybasile.knowledgemanagement.data.database.entity.AccessTokenEntity
import com.joeybasile.knowledgemanagement.data.database.entity.RefreshTokenEntity

interface TokenRepository {
    suspend fun getAccessToken(): String
    suspend fun getRefreshToken(): String
    suspend fun getAccessTokenExpiry(): Long
    suspend fun getRefreshTokenExpiry(): Long
    suspend fun insertAccessToken(token: AccessTokenEntity)
    suspend fun insertRefreshToken(token: RefreshTokenEntity)
    suspend fun updateAccessToken(accessToken: String, expiry: Long)
    suspend fun updateRefreshToken(refreshToken: String, expiry: Long)
    suspend fun initializeAccessTokenIfNeeded()
    suspend fun initializeRefreshTokenIfNeeded()
    suspend fun getRefreshTokenRecord()
}