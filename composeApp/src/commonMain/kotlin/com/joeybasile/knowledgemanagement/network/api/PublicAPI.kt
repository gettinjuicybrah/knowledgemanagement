package com.joeybasile.knowledgemanagement.network.api

import com.joeybasile.knowledgemanagement.network.request.public.LoginRequest
import com.joeybasile.knowledgemanagement.network.request.public.RegisterRequest
import com.joeybasile.knowledgemanagement.network.response.public.LoginResponse
import com.joeybasile.knowledgemanagement.network.response.public.RegisterResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import org.koin.core.component.KoinComponent

class PublicAPI(private val httpClient: HttpClient): KoinComponent {

    suspend fun login(loginRequest: LoginRequest): Result<LoginResponse> {
        val response = httpClient.post("/public/login") {
            setBody(loginRequest)
        }
        return Result.success(response.body())
    }

    suspend fun register(registerRequest: RegisterRequest): Result<RegisterResponse> {
        val response = httpClient.post("/public/register") {
            setBody(registerRequest)
        }
        return Result.success(response.body())
    }

}