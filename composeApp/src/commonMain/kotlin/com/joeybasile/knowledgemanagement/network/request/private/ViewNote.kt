package com.joeybasile.knowledgemanagement.network.request.private

import kotlinx.serialization.Serializable

@Serializable
data class ViewNoteRequest(
    val accessToken: String?,
    val refreshToken: String?
)