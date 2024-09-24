package com.joeybasile.knowledgemanagement.data.database.data.repository

data class Note(
    val idA: String,
    val idB: String,
    val title: String,
    val content: String,
    val creation_date: Long,
    val last_edit_date: Long,
    val version: Int
)
