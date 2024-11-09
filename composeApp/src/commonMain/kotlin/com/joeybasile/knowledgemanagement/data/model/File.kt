package com.joeybasile.knowledgemanagement.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Note(
    val id: String,
    val parentFolderId: String,
    val title: String,
    val content: String,
    val createdAt: String,
    val updatedAt: String,
    val version: Int
)
@Serializable
data class Folder(
    val id: String,
    val title: String,
    val parentFolderId: String,
    val isExpanded: Boolean
)
