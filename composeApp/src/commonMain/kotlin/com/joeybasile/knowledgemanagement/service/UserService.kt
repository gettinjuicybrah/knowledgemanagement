package com.joeybasile.knowledgemanagement.service

import com.joeybasile.knowledgemanagement.data.database.data.repository.UserRepository
import com.joeybasile.knowledgemanagement.data.database.entity.UserEntity
import com.joeybasile.knowledgemanagement.util.generateUUID
import com.joeybasile.knowledgemanagement.util.isLoggedIn
import com.joeybasile.knowledgemanagement.util.userEntityToUser
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
class UserService: KoinComponent {
    private val userRepository: UserRepository by inject()
    private val tokenService: TokenService by inject()
    private val privateAPIService: PrivateAPIService by inject()

    suspend fun updateTheme(theme: Boolean){
        userRepository.updateTheme(theme)
        if(isLoggedIn(tokenService.getRefreshExpiry())){
            privateAPIService.updateUITheme(userEntityToUser(getUser()))
        }
    }
    suspend fun getTheme(): Boolean {
        return userRepository.getTheme()
    }
    suspend fun initializeUserRecordIfNeeded(){
        if(userRepository.getUserCount() == 0) {
            val user = UserEntity(generateUUID(), "",false)

            userRepository.initializeUserRecordIfNeeded(user)
        }
    }

    suspend fun getUser(): UserEntity{
        return userRepository.getUser()
    }

    suspend fun updateUser(user: UserEntity) {
        return userRepository.updateUser(user)
    }
    suspend fun getUserCount(): Int{
        return userRepository.getUserCount()
    }
    suspend fun setUsername(username: String){
        userRepository.setUsername(username)
    }

}