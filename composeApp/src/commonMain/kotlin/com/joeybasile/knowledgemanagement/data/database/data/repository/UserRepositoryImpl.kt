package com.joeybasile.knowledgemanagement.data.database.data.repository

import com.joeybasile.knowledgemanagement.data.database.dao.UserDao
import com.joeybasile.knowledgemanagement.data.database.entity.UserEntity

class UserRepositoryImpl(private val userDao: UserDao): UserRepository {
    override suspend fun updateTheme(theme: Boolean) {
        userDao.updateTheme(theme)
    }

    override suspend fun getTheme(): String? {
        return userDao.getTheme()
    }

    override suspend fun initializeUserRecordIfNeeded(user: UserEntity) {
        userDao.initializeUserRecordIfNeeded(user)
    }

    override suspend fun updateUser(user: UserEntity) {
        userDao.updateUser(user)
    }

    override suspend fun getUserCount(): Int {
        return userDao.getUserCount()
    }

    override suspend fun getUser(): UserEntity {
        return userDao.getUser()
    }
    override suspend fun setUsername(username: String){
        userDao.setUsername(username)
    }

}