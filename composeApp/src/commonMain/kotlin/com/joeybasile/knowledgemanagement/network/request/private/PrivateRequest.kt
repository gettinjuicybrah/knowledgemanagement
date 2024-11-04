package com.joeybasile.knowledgemanagement.network.request.private

import com.joeybasile.knowledgemanagement.data.database.entity.FolderEntity
import com.joeybasile.knowledgemanagement.data.database.entity.NotesEntity
import com.joeybasile.knowledgemanagement.data.database.entity.UserEntity
import com.joeybasile.knowledgemanagement.data.model.RefreshToken
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class AccessTokenRequest(
    @Contextual
    val refreshToken: RefreshToken
)

@Serializable
data class UpdateUIThemeRequest(
    val user: UserEntity)


@Serializable
data class InsertNoteRequest(
    val note: NotesEntity,
    val user: UserEntity
)
@Serializable
data class DeleteNoteRequest(
    val note: NotesEntity,
    val user: UserEntity

)@Serializable
data class UpdateNoteRequest(
    val note: NotesEntity,
    val user: UserEntity
)

@Serializable
data class InsertFolderRequest(
    val note: FolderEntity,
    val user: UserEntity
)
@Serializable
data class DeleteFolderRequest(
    val note: FolderEntity,
    val user: UserEntity

)@Serializable
data class UpdateFolderRequest(
    val note: FolderEntity,
    val user: UserEntity
)