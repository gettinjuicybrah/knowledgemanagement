package com.joeybasile.knowledgemanagement.network.request.public

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val username: String,
    val password: String
)
@Serializable
data class RegisterRequest(
    val username: String,
    val password: String
)