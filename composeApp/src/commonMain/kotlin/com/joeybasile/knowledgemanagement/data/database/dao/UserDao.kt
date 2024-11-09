package com.joeybasile.knowledgemanagement.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.OnConflictStrategy
import com.joeybasile.knowledgemanagement.data.database.entity.UserEntity

@Dao
interface UserDao {
    @Query("Update users SET theme = :theme")
    suspend fun updateTheme(theme: Boolean)

    @Query("Update users SET username = :username")
    suspend fun setUsername(username: String)

    @Query("SELECT theme FROM users WHERE id = 1")
    suspend fun getTheme(): String?
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun initializeUserRecordIfNeeded(user: UserEntity)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUser(user: UserEntity)
    @Query("SELECT COUNT(*) FROM users")
    suspend fun getUserCount(): Int

    @Query("SELECT 1 FROM users")
    suspend fun getUser(): UserEntity
}