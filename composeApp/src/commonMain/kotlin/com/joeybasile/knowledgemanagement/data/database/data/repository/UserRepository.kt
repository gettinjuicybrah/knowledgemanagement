package com.joeybasile.knowledgemanagement.data.database.data.repository

import com.joeybasile.knowledgemanagement.data.database.entity.UserEntity

interface UserRepository {
    suspend fun updateTheme(theme: String)
    suspend fun getTheme(): String?
    suspend fun initializeUserRecordIfNeeded(user: UserEntity)
    suspend fun updateUser(user: UserEntity)
    suspend fun getUserCount(): Int
}