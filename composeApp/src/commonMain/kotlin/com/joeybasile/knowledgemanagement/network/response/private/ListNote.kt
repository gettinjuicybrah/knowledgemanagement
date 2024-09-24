package com.joeybasile.knowledgemanagement.network.response.private

import kotlinx.serialization.Serializable

@Serializable
data class ListNoteResponse(
    val list: List<Note>
)
@Serializable
data class Note(
    val id: String,
    val title: String
)
