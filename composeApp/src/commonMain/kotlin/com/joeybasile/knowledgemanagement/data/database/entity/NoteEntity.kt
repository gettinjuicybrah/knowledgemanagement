package com.joeybasile.knowledgemanagement.data.database.entity

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
    val creation_date: String,
    val last_edit_date: String,
    var version: Int
)