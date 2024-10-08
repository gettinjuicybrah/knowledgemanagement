package com.joeybasile.knowledgemanagement.network.request.private

import com.joeybasile.knowledgemanagement.data.database.entity.NotesEntity
import kotlinx.serialization.Serializable

@Serializable
data class DeleteNoteRequest(
    val note: NotesEntity,
    val accessToken: String?,
    val refreshToken: String?

)
