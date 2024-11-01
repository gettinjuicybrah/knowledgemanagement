package com.joeybasile.knowledgemanagement.service

import com.joeybasile.knowledgemanagement.data.database.data.repository.UserRepository
import com.joeybasile.knowledgemanagement.data.database.entity.UserEntity
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
class UserService: KoinComponent {
    private val userRepository: UserRepository by inject()

    suspend fun updateTheme(theme: String){
        userRepository.updateTheme(theme)

    }
    suspend fun getTheme(): String? {
        return userRepository.getTheme()
    }
    suspend fun initializeUserRecordIfNeeded(){
        if(userRepository.getUserCount() == 0) {
            val user = UserEntity(1, "light")

            userRepository.initializeUserRecordIfNeeded(user)
        }

    }
    suspend fun updateUser(user: UserEntity) {
        return userRepository.updateUser(user)
    }
    suspend fun getUserCount(): Int{
        return userRepository.getUserCount()
    }

}