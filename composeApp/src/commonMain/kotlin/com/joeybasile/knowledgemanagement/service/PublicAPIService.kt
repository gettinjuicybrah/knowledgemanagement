package com.joeybasile.knowledgemanagement.service

import com.joeybasile.knowledgemanagement.network.api.PublicAPI
import com.joeybasile.knowledgemanagement.network.request.public.LoginRequest
import com.joeybasile.knowledgemanagement.network.request.public.RegisterRequest
import com.joeybasile.knowledgemanagement.network.response.public.LoginResponse
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PublicAPIService: KoinComponent {
    private val publicAPI: PublicAPI by inject()

    suspend fun login(username: String, password: String): Result<LoginResponse> {
        val loginRequest = LoginRequest(username, password)
        return publicAPI.login(loginRequest)
    }
    suspend fun register(username: String, password: String){
        val registerRequest = RegisterRequest(username, password)
        publicAPI.register(registerRequest)

    }

}