package com.joeybasile.knowledgemanagement.data.database

import androidx.room.Entity
import kotlinx.serialization.Serializable

@Serializable
@Entity(
    tableName = "notes",
    primaryKeys = ["idA", "idB"]
)
data class NotesEntity(
    val idA: String,
    val idB: String,
    val title: String,
    val content: String,
    val creation_date: Long,
    val last_edit_date: Long,
    var version: Int
)