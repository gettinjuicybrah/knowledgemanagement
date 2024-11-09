package com.joeybasile.knowledgemanagement.service

import com.joeybasile.knowledgemanagement.data.model.UserAuth
import com.joeybasile.knowledgemanagement.network.api.PublicAPI
import com.joeybasile.knowledgemanagement.network.request.public.LoginRequest
import com.joeybasile.knowledgemanagement.network.request.public.RegisterRequest
import com.joeybasile.knowledgemanagement.network.response.public.LoginResponse
import com.joeybasile.knowledgemanagement.network.response.public.RegisterResponse
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PublicAPIService: KoinComponent {
    private val publicAPI: PublicAPI by inject()

    suspend fun login(userAuth: UserAuth): LoginResponse {
        val loginRequest = LoginRequest(userAuth.username, userAuth.password)
        val loginResponse = publicAPI.login(loginRequest)
        return loginResponse.fold(
            onSuccess = { response ->
                response
            },
            onFailure = { exception ->
                throw exception
            }
        )
    }
    suspend fun register(userAuth: UserAuth): RegisterResponse{
        val registerRequest = RegisterRequest(userAuth.username, userAuth.password)
        val registerResponse = publicAPI.register(registerRequest)
        return registerResponse.fold(
            onSuccess = { response ->
                response
            },
            onFailure = { exception ->
                throw exception
            }
        )
    }

}