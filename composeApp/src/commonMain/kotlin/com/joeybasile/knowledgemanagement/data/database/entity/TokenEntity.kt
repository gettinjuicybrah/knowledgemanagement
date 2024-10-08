package com.joeybasile.knowledgemanagement.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tokens")
data class TokenEntity(
    @PrimaryKey val id: Int = 1, // We'll only ever have one row
    val accessToken: String?,
    val accessTokenExpiry: String?,
    val refreshToken: String?,
    val refreshTokenExpiry: String?
)