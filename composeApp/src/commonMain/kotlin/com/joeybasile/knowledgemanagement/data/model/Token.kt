package com.joeybasile.knowledgemanagement.data.model

data class AccessToken(
    val JWTToken: String
)
data class RefreshToken(
    val JWTToken: String
)