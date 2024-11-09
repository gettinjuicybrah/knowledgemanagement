package com.joeybasile.knowledgemanagement.network.request.private

import com.joeybasile.knowledgemanagement.data.database.entity.FolderEntity
import com.joeybasile.knowledgemanagement.data.database.entity.NotesEntity
import com.joeybasile.knowledgemanagement.data.database.entity.UserEntity
import com.joeybasile.knowledgemanagement.data.model.Folder
import com.joeybasile.knowledgemanagement.data.model.Note
import com.joeybasile.knowledgemanagement.data.model.RefreshToken
import com.joeybasile.knowledgemanagement.data.model.User
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class AccessTokenRequest(
    @Contextual
    val refreshToken: RefreshToken
)

@Serializable
data class UpdateUIThemeRequest(
    val user: User
)


@Serializable
data class InsertNoteRequest(
    val user: User,
    val note: Note
)
@Serializable
data class DeleteNoteRequest(
    val user: User,
    val note: Note

)@Serializable
data class UpdateNoteRequest(
    val user: User,
    val note: Note
)

@Serializable
data class InsertFolderRequest(
    val user: User,
    val folder: Folder
)
@Serializable
data class DeleteFolderRequest(
    val user: User,
    val folder: Folder

)@Serializable
data class UpdateFolderRequest(
    val user: User,
    val folder: Folder
)