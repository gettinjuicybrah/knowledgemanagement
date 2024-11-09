package com.joeybasile.knowledgemanagement.data.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    //primary key
    val id: String,
    val username: String,
    val theme: Boolean
)
data class UserAuth(
    val username: String,
    val password: String
)