package com.joeybasile.knowledgemanagement.data.database.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(
    tableName = "notes",
    indices = [Index(value = ["parentFolderId"])],
    foreignKeys = [androidx.room.ForeignKey(
        entity = FolderEntity::class,
        parentColumns = ["id"],
        childColumns = ["parentFolderId"]
    )]
)
data class NotesEntity(
    @PrimaryKey
    val id: String,
    //this should never be null.
    val parentFolderId: String?,
    val title: String,
    val content: String,
    val creation_date: String,
    val last_edit_date: String,
    var version: Int
)