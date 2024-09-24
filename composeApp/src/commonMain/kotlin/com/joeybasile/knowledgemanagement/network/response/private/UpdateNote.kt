package com.joeybasile.knowledgemanagement.network.response.private

import kotlinx.serialization.Serializable

@Serializable
data class UpdateNoteResponse(
    val message: String
)