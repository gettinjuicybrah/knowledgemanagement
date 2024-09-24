package com.joeybasile.knowledgemanagement.service

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AuthService: KoinComponent {
    private val privateAPIService: PrivateAPIService by inject()
    private val publicAPIService: PublicAPIService by inject()
    private val tokenService: TokenService by inject()

    suspend fun login(username: String, password: String){
        publicAPIService.login(username, password)
    }
    suspend fun register(username: String, password: String){

    }
    suspend fun ensureAccessToken(username: String, password: String): Boolean{

    }

}