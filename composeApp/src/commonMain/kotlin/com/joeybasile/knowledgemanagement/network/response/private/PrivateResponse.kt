package com.joeybasile.knowledgemanagement.network.response.private

import com.joeybasile.knowledgemanagement.data.model.AccessToken
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class AccessTokenResponse(
    @Contextual
    val accessToken: AccessToken
)

@Serializable
data class UpdateUIThemeResponse(
    val result:Boolean
)

@Serializable
data class InsertNoteResponse(
    val result:Boolean
)
@Serializable
data class DeleteNoteResponse(
    val result:Boolean

)@Serializable
data class UpdateNoteResponse(
    val result:Boolean
)

@Serializable
data class InsertFolderResponse(
    val result:Boolean
)
@Serializable
data class DeleteFolderResponse(
    val result:Boolean

)@Serializable
data class UpdateFolderResponse(
    val result:Boolean
)