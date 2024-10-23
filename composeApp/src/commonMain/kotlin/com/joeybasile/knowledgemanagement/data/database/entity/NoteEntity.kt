package com.joeybasile.knowledgemanagement.data.database.entity

import androidx.room.Entity
import androidx.room.Index
import kotlinx.serialization.Serializable

@Serializable
@Entity(
    tableName = "notes",
    primaryKeys = ["idA", "idB"],
    indices = [Index(value = ["parentFolderId"])],
    foreignKeys = [androidx.room.ForeignKey(
        entity = FolderEntity::class,
        parentColumns = ["id"],
        childColumns = ["parentFolderId"]
    )]
)
data class NotesEntity(
    val idA: String,
    val idB: String,
    //this should never be null.
    val parentFolderId: Int?,
    val title: String,
    val content: String,
    val creation_date: String,
    val last_edit_date: String,
    var version: Int
)