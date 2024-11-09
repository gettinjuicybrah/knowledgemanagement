package com.joeybasile.knowledgemanagement.data.model

data class AccessToken(
    val JWTToken: String,
    val expiry: Long
)
data class RefreshToken(
    val JWTToken: String,
    val expiry: Long
)