package com.joeybasile.knowledgemanagement.data.database.data.repository

import com.joeybasile.knowledgemanagement.data.database.dao.TokenDao
import com.joeybasile.knowledgemanagement.data.database.entity.AccessTokenEntity
import com.joeybasile.knowledgemanagement.data.database.entity.RefreshTokenEntity

class TokenRepositoryImpl(
    private val tokenDao: TokenDao
) : TokenRepository {
    override suspend fun getRefreshTokenRecord() {
        return tokenDao.getRefreshTokenRecord()
    }
    override suspend fun getAccessToken(): String {
        return tokenDao.getAccessToken()
    }

    override suspend fun getRefreshToken(): String {
        return tokenDao.getRefreshToken()
    }

    override suspend fun getAccessTokenExpiry(): Long {
        return tokenDao.getAccessTokenExpiry()
    }

    override suspend fun getRefreshTokenExpiry(): Long {
        return tokenDao.getRefreshTokenExpiry()
    }

    //Will never be used anywhere.
    override suspend fun insertAccessToken(token: AccessTokenEntity) {
        tokenDao.insertAccessToken(token)
    }
    override suspend fun insertRefreshToken(token: RefreshTokenEntity) {
        tokenDao.insertRefreshToken(token)
    }

    override suspend fun updateAccessToken(token: String, expiry: Long) {
        tokenDao.updateAccessToken(token, expiry)
    }

    override suspend fun updateRefreshToken(token: String, expiry: Long) {
        tokenDao.updateRefreshToken(token, expiry)
    }

    override suspend fun initializeAccessTokenIfNeeded() {
        if (tokenDao.getAccessTokenCount() == 0) {
            val initialToken = AccessTokenEntity(
                id = 1,
                token = "",
                expiry = 0)
            tokenDao.initializeAccessTokenRecord(initialToken)
        }
    }

    override suspend fun initializeRefreshTokenIfNeeded() {
        if (tokenDao.getRefreshTokenCount() == 0) {
            val initialToken = RefreshTokenEntity(
                id = 1,
                token = "",
                expiry = 0)
            tokenDao.initializeRefreshTokenRecord(initialToken)
        }
    }
}