package com.joeybasile.knowledgemanagement.data.database.data.repository

class TokenRepositoryImpl(
    private val tokenDao: TokenDao
) : TokenRepository {
    override suspend fun getAccessToken(): String? {
        return tokenDao.getAccessToken()
    }

    override suspend fun getRefreshToken(): String? {
        return tokenDao.getRefreshToken()
    }

    override suspend fun getLocalToken(): String? {
        return tokenDao.getLocalToken()
    }

    override suspend fun getAccessTokenExpiry(): String? {
        return tokenDao.getAccessTokenExpiry()
    }

    override suspend fun getRefreshTokenExpiry(): String? {
        return tokenDao.getRefreshTokenExpiry()
    }

    override suspend fun getLocalTokenExpiry(): String? {
        return tokenDao.getLocalTokenExpiry()
    }
    //Will never be used anywhere.
    override suspend fun insertToken(token: TokenEntity) {
        tokenDao.insertToken(token)
    }

    override suspend fun updateAccessToken(accessToken: String, accExpire: String) {
        tokenDao.updateAccessToken(accessToken, accExpire)
    }

    override suspend fun updateRefreshToken(refreshToken: String, refreshExpire: String) {
        tokenDao.updateRefreshToken(refreshToken, refreshExpire)
    }

    override suspend fun updateLocalToken(localToken: String, localExpire: String) {
        tokenDao.updateLocalToken(localToken, localExpire)
    }

    override suspend fun initializeTokenIfNeeded() {
        if (tokenDao.getTokenCount() == 0) {
            val initialToken = TokenEntity(
                id = 1,
                accessToken = null,
                refreshToken = null,
                localToken = null,
                acc_expire = null,
                refresh_expire = null,
                local_expire = null
            )
            tokenDao.initializeTokenRecord(initialToken)
        }
    }
}