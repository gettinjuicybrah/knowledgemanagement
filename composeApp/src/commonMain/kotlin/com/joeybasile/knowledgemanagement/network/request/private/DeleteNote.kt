package com.joeybasile.knowledgemanagement.network.request.private

import com.joeybasile.knowledgemanagement.data.database.NotesEntity

data class DeleteNoteRequest(
    val note: NotesEntity,
    val accessToken: String?,
    val refreshToken: String?

)
