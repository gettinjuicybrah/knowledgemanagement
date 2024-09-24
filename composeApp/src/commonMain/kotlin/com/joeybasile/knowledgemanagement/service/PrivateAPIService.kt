package com.joeybasile.knowledgemanagement.service

import com.joeybasile.knowledgemanagement.data.database.NotesEntity
import com.joeybasile.knowledgemanagement.network.api.PrivateAPI
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import com.joeybasile.knowledgemanagement.network.request.private.*
import com.joeybasile.knowledgemanagement.network.response.private.*

class PrivateAPIService: KoinComponent {
    private val privateAPI: PrivateAPI by inject()
    private val tokenService: TokenService by inject()

    suspend fun insertNote(
       note: NotesEntity
    ): Result<InsertNoteResponse> {
        val request = InsertNoteRequest(note, tokenService.getAccessToken(), tokenService.getRefreshToken())
        return privateAPI.insertNote(request)
    }

    suspend fun updateNote(note: NotesEntity): Result<UpdateNoteResponse> {
        val request = UpdateNoteRequest(note, tokenService.getAccessToken(), tokenService.getRefreshToken())
        return privateAPI.updateNote(request)
    }
    suspend fun deleteNote(note: NotesEntity): Result<DeleteNoteResponse> {
        val request = DeleteNoteRequest(note, tokenService.getAccessToken(), tokenService.getRefreshToken())
        return privateAPI.deleteNote(request)

    }

    suspend fun getAllNotes(): Result<ListNoteResponse> {
        val request = ListNoteRequest(tokenService.getAccessToken(), tokenService.getRefreshToken())
        return privateAPI.listNote(request)

    }

}