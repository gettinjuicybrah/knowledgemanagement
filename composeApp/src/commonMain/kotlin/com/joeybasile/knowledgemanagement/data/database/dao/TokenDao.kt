package com.joeybasile.knowledgemanagement.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.joeybasile.knowledgemanagement.data.database.entity.TokenEntity

@Dao
interface TokenDao {
    @Query("SELECT accessToken FROM tokens WHERE id = 1")
    suspend fun getAccessToken(): String?
    @Query("SELECT refreshToken FROM tokens WHERE id = 1")
    suspend fun getRefreshToken(): String?

    @Query("SELECT accessTokenExpiry FROM tokens WHERE id = 1")
    suspend fun getAccessTokenExpiry(): String?

    @Query("SELECT refreshTokenExpiry FROM tokens WHERE id = 1")
    suspend fun getRefreshTokenExpiry(): String?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToken(token: TokenEntity)

    // If you need to update individual fields, you can use Update or custom queries
    @Query("UPDATE tokens SET accessToken = :accessToken, accessTokenExpiry = :accExpire WHERE id = 1")
    suspend fun updateAccessToken(accessToken: String, accExpire: String)

    @Query("UPDATE tokens SET refreshToken = :refreshToken, refreshTokenExpiry = :refreshExpire WHERE id = 1")
    suspend fun updateRefreshToken(refreshToken: String, refreshExpire: String)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun initializeTokenRecord(token: TokenEntity)

    @Query("SELECT COUNT(*) FROM tokens")
    suspend fun getTokenCount(): Int
}