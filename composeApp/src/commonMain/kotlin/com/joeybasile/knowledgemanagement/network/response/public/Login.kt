package com.joeybasile.knowledgemanagement.network.response.public

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val accessToken:String,
    val accessTokenExpiry:String,
    val refreshToken:String,
    val refreshTokenExpiry:String
)