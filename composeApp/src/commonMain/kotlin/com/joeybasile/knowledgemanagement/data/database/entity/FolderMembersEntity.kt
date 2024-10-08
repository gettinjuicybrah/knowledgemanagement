package com.joeybasile.knowledgemanagement.data.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(
    tableName = "foldermembers",
    primaryKeys = ["id"],
    foreignKeys =
        [ForeignKey(entity = FolderEntity::class, parentColumns = ["id"], childColumns = ["id"]),
            ForeignKey(entity = FolderEntity::class, parentColumns = ["id"], childColumns = ["folderMemberId"]),
        ForeignKey(entity = NotesEntity::class, parentColumns = ["idA", "idB"], childColumns = ["noteMemberIdA", "noteMemberIdB"])
        ]
)
data class FolderMembersEntity(
    val id: Int,
    val folderMemberId: Int,
    val noteMemberIdA: Int,
    val noteMemberIdB: Int
)