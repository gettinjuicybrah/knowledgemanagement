package com.joeybasile.knowledgemanagement.service

import com.joeybasile.knowledgemanagement.data.database.entity.FolderEntity
import com.joeybasile.knowledgemanagement.data.database.entity.NotesEntity
import com.joeybasile.knowledgemanagement.data.database.entity.UserEntity
import com.joeybasile.knowledgemanagement.data.model.Folder
import com.joeybasile.knowledgemanagement.data.model.Note
import com.joeybasile.knowledgemanagement.data.model.RefreshToken
import com.joeybasile.knowledgemanagement.data.model.User
import com.joeybasile.knowledgemanagement.network.api.PrivateAPI
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import com.joeybasile.knowledgemanagement.network.request.private.*
import com.joeybasile.knowledgemanagement.network.response.private.AccessTokenResponse

class PrivateAPIService : KoinComponent {
    private val privateAPI: PrivateAPI by inject()

    suspend fun getAccessToken(refreshToken: RefreshToken)/*:AccessTokenResponse*/ {
        val request = AccessTokenRequest(refreshToken)
        try {
            val response = privateAPI.getAccessToken(request)
            return response.fold(
                onSuccess = { response ->
                    response
                },
                onFailure = { exception ->
                    throw exception
                }
            )
        } catch (e: Error) {

        }
    }

    suspend fun updateUITheme(user: User) {
        val request = UpdateUIThemeRequest(user)
        try {
            val response = privateAPI.updateUITheme(request)
            return response.fold(
                onSuccess = { response ->
                    response
                },
                onFailure = { exception ->
                    throw exception
                }
            )
        } catch (e: Error) {

        }
    }

    suspend fun insertNote(
        user: User,
        note: Note
    ) {
        val request = InsertNoteRequest(user, note)
        try {
            val response = privateAPI.insertNote(request)
            return response.fold(
                onSuccess = { response ->
                    response
                },
                onFailure = { exception ->
                    throw exception
                }
            )
        } catch (e: Error) {

        }
    }

    suspend fun updateNote(user: User, note: Note) {
        val request = UpdateNoteRequest(user, note)
        try {
            val response = privateAPI.updateNote(request)
            return response.fold(
                onSuccess = { response ->
                    response
                },
                onFailure = { exception ->
                    throw exception
                }
            )
        } catch (e: Error) {
        }
    }

    suspend fun deleteNote(user: User, note: Note) {
        val request = UpdateNoteRequest(user, note)
        try {
            val response = privateAPI.updateNote(request)
            return response.fold(
                onSuccess = { response ->
                    response
                },
                onFailure = { exception ->
                    throw exception
                }
            )
        } catch (e: Error) {
        }
    }

    suspend fun insertFolder(
        user: User,
        note: Folder
    ) {
        val request = InsertFolderRequest(user, note)
        try {
            val response = privateAPI.insertFolder(request)
            return response.fold(
                onSuccess = { response ->
                    response
                },
                onFailure = { exception ->
                    throw exception
                }
            )
        } catch (e: Error) {
        }
    }

    suspend fun updateFolder(
        user: User,
        note: Folder
    ) {
        val request = UpdateFolderRequest(user, note)
        try {
            val response = privateAPI.updateFolder(request)
            return response.fold(
                onSuccess = { response ->
                    response
                },
                onFailure = { exception ->
                    throw exception
                }
            )
        } catch (e: Error) {
        }
    }

    suspend fun deleteFolder(
        user: User,
        note: Folder
    ) {
        val request = DeleteFolderRequest(user, note)
        try {
            val response = privateAPI.deleteFolder(request)
            return response.fold(
                onSuccess = { response ->
                    response
                },
                onFailure = { exception ->
                    throw exception
                }
            )
        } catch (e: Error) {
        }
    }

}