package com.joeybasile.knowledgemanagement.network.api

import com.joeybasile.knowledgemanagement.network.request.private.DeleteNoteRequest
import com.joeybasile.knowledgemanagement.network.request.private.InsertNoteRequest
import com.joeybasile.knowledgemanagement.network.request.private.UpdateNoteRequest
import com.joeybasile.knowledgemanagement.network.response.private.DeleteNoteResponse
import com.joeybasile.knowledgemanagement.network.response.private.InsertNoteResponse
import com.joeybasile.knowledgemanagement.network.response.private.UpdateNoteResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import org.koin.core.component.KoinComponent

class PrivateAPI(private val httpClient: HttpClient): KoinComponent {

    suspend fun insertNote(insertNoteRequest: InsertNoteRequest): Result<InsertNoteResponse> {
        val response = httpClient.post("/private/PlaceNote") {
            setBody(insertNoteRequest)
        }
        return Result.success(response.body())
    }

    suspend fun updateNote(updateNoteRequest: UpdateNoteRequest): Result<UpdateNoteResponse> {
        val response = httpClient.post("/private/UpdateNote") {
            setBody(updateNoteRequest)
        }
        return Result.success(response.body())

    }

    suspend fun deleteNote(deleteNoteRequest: DeleteNoteRequest): Result<DeleteNoteResponse> {
        val response = httpClient.post("/private/DeleteNote") {
            setBody(deleteNoteRequest)
        }
        return Result.success(response.body())
    }

}