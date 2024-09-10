package com.joeybasile.knowledgemanagement.data.database

@Dao
interface TokenDao {
    @Query("SELECT accessToken FROM tokens WHERE id = 1")
    suspend fun getAccessToken(): String?
    @Query("SELECT refreshToken FROM tokens WHERE id = 1")
    suspend fun getRefreshToken(): String?
    @Query("SELECT localToken FROM tokens WHERE id = 1")
    suspend fun getLocalToken(): String?

    @Query("SELECT acc_expire FROM tokens WHERE id = 1")
    suspend fun getAccessTokenExpiry(): String?

    @Query("SELECT refresh_expire FROM tokens WHERE id = 1")
    suspend fun getRefreshTokenExpiry(): String?

    @Query("SELECT local_expire FROM tokens WHERE id = 1")
    suspend fun getLocalTokenExpiry(): String?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToken(token: TokenEntity)

    // If you need to update individual fields, you can use Update or custom queries
    @Query("UPDATE tokens SET accessToken = :accessToken, acc_expire = :accExpire WHERE id = 1")
    suspend fun updateAccessToken(accessToken: String, accExpire: String)

    @Query("UPDATE tokens SET refreshToken = :refreshToken, refresh_expire = :refreshExpire WHERE id = 1")
    suspend fun updateRefreshToken(refreshToken: String, refreshExpire: String)

    @Query("UPDATE tokens SET localToken = :localToken, local_expire = :localExpire WHERE id = 1")
    suspend fun updateLocalToken(localToken: String, localExpire: String)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun initializeTokenRecord(token: TokenEntity)

    @Query("SELECT COUNT(*) FROM tokens")
    suspend fun getTokenCount(): Int
}