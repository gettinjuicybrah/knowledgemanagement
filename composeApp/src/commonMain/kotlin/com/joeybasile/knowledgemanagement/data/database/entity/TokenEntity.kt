package com.joeybasile.knowledgemanagement.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "accessToken")
data class AccessTokenEntity(
    @PrimaryKey
    val id: Int = 1, // We'll only ever have one row
    val token: String,
    val expiry: Long
)
@Entity(tableName = "refreshToken")
data class RefreshTokenEntity(
    @PrimaryKey
    val id: Int = 1,
    val token: String,
    val expiry: Long
)