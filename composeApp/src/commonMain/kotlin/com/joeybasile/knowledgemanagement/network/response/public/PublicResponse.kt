package com.joeybasile.knowledgemanagement.network.response.public

import com.joeybasile.knowledgemanagement.data.model.AccessToken
import com.joeybasile.knowledgemanagement.data.model.RefreshToken
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponse(
    val result: Boolean
)

sealed class LoginResponse{
    @Serializable
    data class Success(
        @Contextual
        val accessToken:AccessToken,
        @Contextual
        val refreshToken:RefreshToken,
        val userId:String,
        val userTheme: Boolean
    ):LoginResponse()
    @Serializable
    data class Fail(
        val fail:Boolean
    ):LoginResponse()
}