package com.joeybasile.knowledgemanagement.network.response.private

import kotlinx.serialization.Serializable

@Serializable
data class ViewNoteResponse(
    val id: String,
    val title: String,
    val content: String
)