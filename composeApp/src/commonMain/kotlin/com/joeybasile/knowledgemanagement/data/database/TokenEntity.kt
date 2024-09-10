package com.joeybasile.knowledgemanagement.data.database

@Entity(tableName = "tokens")
data class TokenEntity(
    @PrimaryKey val id: Int = 1, // We'll only ever have one row
    val accessToken: String?,
    val accessTokenExpiry: String?,
    val refreshToken: String?,
    val refreshTokenExpiry: String?,
    val localToken: String?,
    val localTokenExpiry: String?
)