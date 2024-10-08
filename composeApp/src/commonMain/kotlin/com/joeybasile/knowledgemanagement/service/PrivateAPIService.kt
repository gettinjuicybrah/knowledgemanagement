package com.joeybasile.knowledgemanagement.service

import com.joeybasile.knowledgemanagement.data.database.entity.NotesEntity
import com.joeybasile.knowledgemanagement.network.api.PrivateAPI
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import com.joeybasile.knowledgemanagement.network.request.private.*

class PrivateAPIService: KoinComponent {
    private val privateAPI: PrivateAPI by inject()
    private val tokenService: TokenService by inject()

    suspend fun insertNote(
       note: NotesEntity
    ){
        val request = InsertNoteRequest(note, tokenService.getAccessToken(), tokenService.getRefreshToken())
        val response = privateAPI.insertNote(request)
    }

    suspend fun updateNote(note: NotesEntity){
        val request = UpdateNoteRequest(note, tokenService.getAccessToken(), tokenService.getRefreshToken())
        val response = privateAPI.updateNote(request)
    }
    suspend fun deleteNote(note: NotesEntity){
        val request = DeleteNoteRequest(note, tokenService.getAccessToken(), tokenService.getRefreshToken())
        val response = privateAPI.deleteNote(request)

    }

}