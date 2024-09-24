package com.joeybasile.knowledgemanagement.network.response.public

import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponse(
    val message: String
)