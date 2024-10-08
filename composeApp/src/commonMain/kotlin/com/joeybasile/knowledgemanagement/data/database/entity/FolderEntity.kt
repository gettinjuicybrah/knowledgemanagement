package com.joeybasile.knowledgemanagement.data.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(
    tableName = "folders",
    primaryKeys = ["id"],
    foreignKeys = [ForeignKey(entity = FolderEntity::class, parentColumns = ["id"], childColumns = ["parentFolderId"])]
)
data class FolderEntity(
    val id: Int,
    val title: String,
    val parentFolderId: Int
)