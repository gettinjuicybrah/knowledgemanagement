package com.joeybasile.knowledgemanagement.network.service

import com.joeybasile.knowledgemanagement.network.api.PrivateAPI
import com.joeybasile.knowledgemanagement.network.request.private.InsertNoteRequest
import com.joeybasile.knowledgemanagement.network.request.private.ListNoteRequest
import com.joeybasile.knowledgemanagement.network.request.private.UpdateNoteRequest
import com.joeybasile.knowledgemanagement.network.request.private.ViewNoteRequest
import com.joeybasile.knowledgemanagement.network.response.private.InsertNoteResponse
import com.joeybasile.knowledgemanagement.network.response.private.ListNoteResponse
import com.joeybasile.knowledgemanagement.network.response.private.Note
import com.joeybasile.knowledgemanagement.network.response.private.UpdateNoteResponse
import com.joeybasile.knowledgemanagement.network.response.private.ViewNoteResponse
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class NoteService : KoinComponent {
    private val privateAPI: PrivateAPI by inject()
    suspend fun insertNote(
        accessToken: String?,
        refreshToken: String?,
        title: String,
        content: String
    ): Result<InsertNoteResponse> {
        val request = InsertNoteRequest(accessToken, refreshToken, title, content)
        val result = privateAPI.insertNote(request)
        return result
    }

    suspend fun listNote(accessToken: String, refreshToken: String): List<Note> {
        val request = ListNoteRequest(accessToken, refreshToken)
        val result = privateAPI.listNote(request)

        return result.fold(
            onSuccess = { listNoteResponse ->
                listNoteResponse.list
            },
            onFailure = { exception ->
                emptyList() // Return an empty list or handle errors appropriately
            }
        )
    }

    suspend fun viewNote(accessToken: String, refreshToken: String, id: String): ViewNoteResponse {
        val request = ViewNoteRequest(accessToken, refreshToken, id)
        val result = privateAPI.viewNote(request)
        return result.getOrElse{
            throw it
        }
    }
    suspend fun updateNote(
        accessToken: String,
        refreshToken: String,
        title: String,
        content: String,
        id: String): Result<UpdateNoteResponse> {
        val request = UpdateNoteRequest(accessToken, refreshToken, title, content, id)
        val result = privateAPI.updateNote(request)
        return result
    }
}