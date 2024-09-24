package com.joeybasile.knowledgemanagement.network.request.public

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val email: String,
    val password: String
)