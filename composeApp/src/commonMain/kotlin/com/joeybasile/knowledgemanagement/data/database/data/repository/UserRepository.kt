package com.joeybasile.knowledgemanagement.data.database.data.repository

import com.joeybasile.knowledgemanagement.data.database.entity.UserEntity

interface UserRepository {
    suspend fun updateTheme(theme: Boolean)
    suspend fun setUsername(username: String)
    suspend fun getTheme(): Boolean
    suspend fun initializeUserRecordIfNeeded(user: UserEntity)
    suspend fun updateUser(user: UserEntity)
    suspend fun getUserCount(): Int
    suspend fun getUser(): UserEntity
}