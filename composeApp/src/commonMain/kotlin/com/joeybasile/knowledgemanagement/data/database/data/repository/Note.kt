package com.joeybasile.knowledgemanagement.data.database.data.repository

import kotlinx.serialization.Serializable

@Serializable
data class Note(
    val idA: String,
    val idB: String,
    val title: String,
    val content: String,
    val creation_date: Long,
    val last_edit_date: Long,
    var version: Int
)
