package com.joeybasile.knowledgemanagement.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.joeybasile.knowledgemanagement.data.database.entity.AccessTokenEntity
import com.joeybasile.knowledgemanagement.data.database.entity.RefreshTokenEntity

@Dao
interface TokenDao {
    @Query("SELECT token FROM accessToken WHERE id = 1")
    suspend fun getAccessToken(): String
    @Query("SELECT token FROM refreshToken WHERE id = 1")
    suspend fun getRefreshToken(): String

    @Query("SELECT expiry FROM accessToken WHERE id = 1")
    suspend fun getAccessTokenExpiry(): Long

    @Query("SELECT expiry FROM refreshToken WHERE id = 1")
    suspend fun getRefreshTokenExpiry(): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAccessToken(token: AccessTokenEntity)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRefreshToken(token: RefreshTokenEntity)

    @Query("SELECT 1 FROM refreshToken")
    suspend fun getRefreshTokenRecord()

    // If you need to update individual fields, you can use Update or custom queries
    @Query("UPDATE accessToken SET token = :token, expiry = :expiry WHERE id = 1")
    suspend fun updateAccessToken(token: String, expiry: Long)

    @Query("UPDATE refreshToken SET token = :token, expiry = :expiry WHERE id = 1")
    suspend fun updateRefreshToken(token: String, expiry: Long)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun initializeAccessTokenRecord(token: AccessTokenEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun initializeRefreshTokenRecord(token: RefreshTokenEntity)

    @Query("SELECT COUNT(*) FROM accessToken")
    suspend fun getAccessTokenCount(): Int

    @Query("SELECT COUNT(*) FROM refreshToken")
    suspend fun getRefreshTokenCount(): Int
}