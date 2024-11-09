package com.joeybasile.knowledgemanagement.data.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(
    tableName = "folders",
    foreignKeys = [ForeignKey(entity = FolderEntity::class, parentColumns = ["id"], childColumns = ["parentFolderId"])],
    indices = [Index(value = ["parentFolderId"])]
)
data class FolderEntity(
    @PrimaryKey(autoGenerate = true)
    val id: String,
    val title: String,
    val parentFolderId: String?,
    val isExpanded: Boolean
)