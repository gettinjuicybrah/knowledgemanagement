package com.joeybasile.knowledgemanagement.network.service

import com.joeybasile.knowledgemanagement.network.api.PrivateAPI
import com.joeybasile.knowledgemanagement.network.request.private.InsertNoteRequest
import com.joeybasile.knowledgemanagement.network.request.private.ListNoteRequest
import com.joeybasile.knowledgemanagement.network.request.private.UpdateNoteRequest
import com.joeybasile.knowledgemanagement.network.request.private.ViewNoteRequest
import com.joeybasile.knowledgemanagement.network.response.private.InsertNoteResponse
import com.joeybasile.knowledgemanagement.network.response.private.ListNoteResponse
import com.joeybasile.knowledgemanagement.network.response.private.UpdateNoteResponse
import com.joeybasile.knowledgemanagement.network.response.private.ViewNoteResponse
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PrivateService : KoinComponent {
    private val privateAPI: PrivateAPI by inject()

    suspend fun insertNote(
        accessToken: String?,
        refreshToken: String?,
        title: String,
        content: String
    ): Result<InsertNoteResponse> {
        val request = InsertNoteRequest(accessToken, refreshToken, title, content)
        return privateAPI.insertNote(request)
    }

    suspend fun listNote(accessToken: String, refreshToken: String): Result<ListNoteResponse> {
        val request = ListNoteRequest(accessToken, refreshToken)
        return privateAPI.listNote(request)
    }

    suspend fun viewNote(accessToken: String, refreshToken: String, id: String): Result<ViewNoteResponse> {
        val request = ViewNoteRequest(accessToken, refreshToken, id)
        return privateAPI.viewNote(request)
    }
    suspend fun updateNote(
        accessToken: String,
        refreshToken: String,
        title: String,
        content: String,
        id: String): Result<UpdateNoteResponse> {
            val request = UpdateNoteRequest(accessToken, refreshToken, title, content, id)
            return privateAPI.updateNote(request)
    }
}