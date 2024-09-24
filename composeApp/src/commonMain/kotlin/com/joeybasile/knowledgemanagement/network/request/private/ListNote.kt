package com.joeybasile.knowledgemanagement.network.request.private

import kotlinx.serialization.Serializable

@Serializable
data class ListNoteRequest(
    val accessToken: String?,
    val refreshToken: String?
)