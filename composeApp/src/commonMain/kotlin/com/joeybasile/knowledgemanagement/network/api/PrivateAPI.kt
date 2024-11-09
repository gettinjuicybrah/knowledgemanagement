package com.joeybasile.knowledgemanagement.network.api

import com.joeybasile.knowledgemanagement.data.model.AccessToken
import com.joeybasile.knowledgemanagement.network.request.private.*
import com.joeybasile.knowledgemanagement.network.request.private.InsertNoteRequest
import com.joeybasile.knowledgemanagement.network.request.private.UpdateFolderRequest
import com.joeybasile.knowledgemanagement.network.request.private.UpdateNoteRequest
import com.joeybasile.knowledgemanagement.network.response.private.AccessTokenResponse
import com.joeybasile.knowledgemanagement.network.response.private.DeleteFolderResponse
import com.joeybasile.knowledgemanagement.network.response.private.DeleteNoteResponse
import com.joeybasile.knowledgemanagement.network.response.private.InsertFolderResponse
import com.joeybasile.knowledgemanagement.network.response.private.InsertNoteResponse
import com.joeybasile.knowledgemanagement.network.response.private.UpdateFolderResponse
import com.joeybasile.knowledgemanagement.network.response.private.UpdateNoteResponse
import com.joeybasile.knowledgemanagement.network.response.private.UpdateUIThemeResponse
import com.joeybasile.knowledgemanagement.service.TokenService
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
class PrivateAPI(private val httpClient: HttpClient): KoinComponent {
private val tokenService: TokenService by inject()

    suspend fun getAccessToken(accessTokenRequest: AccessTokenRequest): Result<AccessTokenResponse> {
        val refreshToken = accessTokenRequest.refreshToken.JWTToken
        val response = httpClient.post("/private/accessToken") {
            header("Authorization", "Bearer $refreshToken")
            setBody(accessTokenRequest)
        }
        return Result.success(response.body())
    }

    suspend fun updateUITheme(updateUIThemeRequest: UpdateUIThemeRequest): Result<UpdateUIThemeResponse> {
        val accessToken = tokenService.getAccessToken()
        tokenService.validateAccessToken(AccessToken(accessToken!!, tokenService.getAccessExpiry()!!))
        val response = httpClient.post("/private/uiTheme") {
            header("Authorization", "Bearer $accessToken")
            setBody(updateUIThemeRequest)
        }
        return Result.success(response.body())
    }

    suspend fun insertNote(insertNoteRequest: InsertNoteRequest): Result<InsertNoteResponse> {
        val accessToken = tokenService.getAccessToken()
        tokenService.validateAccessToken(AccessToken(accessToken!!, tokenService.getAccessExpiry()!!))
        val response = httpClient.post("/private/insertNote") {
            header("Authorization", "Bearer $accessToken")
            setBody(insertNoteRequest)
        }
        return Result.success(response.body())
    }

    suspend fun updateNote(updateNoteRequest: UpdateNoteRequest): Result<UpdateNoteResponse> {
        val accessToken = tokenService.getAccessToken()
        tokenService.validateAccessToken(AccessToken(accessToken!!, tokenService.getAccessExpiry()!!))
        val response = httpClient.post("/private/updateNote") {
            header("Authorization", "Bearer $accessToken")
            setBody(updateNoteRequest)
        }
        return Result.success(response.body())

    }

    suspend fun deleteNote(deleteNoteRequest: DeleteNoteRequest): Result<DeleteNoteResponse> {
        val accessToken = tokenService.getAccessToken()
        tokenService.validateAccessToken(AccessToken(accessToken!!, tokenService.getAccessExpiry()!!))
        val response = httpClient.post("/private/deleteNote") {
            header("Authorization", "Bearer $accessToken")
            setBody(deleteNoteRequest)
        }
        return Result.success(response.body())
    }

    suspend fun insertFolder(insertFolderRequest: InsertFolderRequest): Result<InsertFolderResponse> {
        val accessToken = tokenService.getAccessToken()
        tokenService.validateAccessToken(AccessToken(accessToken!!, tokenService.getAccessExpiry()!!))
        val response = httpClient.post("/private/insertFolder") {
            header("Authorization", "Bearer $accessToken")
            setBody(insertFolderRequest)
        }
        return Result.success(response.body())
    }

    suspend fun updateFolder(updateFolderRequest: UpdateFolderRequest): Result<UpdateFolderResponse> {
        val accessToken = tokenService.getAccessToken()
        tokenService.validateAccessToken(AccessToken(accessToken!!, tokenService.getAccessExpiry()!!))
        val response = httpClient.post("/private/updateFolder") {
            header("Authorization", "Bearer $accessToken")
            setBody(updateFolderRequest)
        }
        return Result.success(response.body())

    }

    suspend fun deleteFolder(deleteFolderRequest: DeleteFolderRequest): Result<DeleteFolderResponse> {
        val accessToken = tokenService.getAccessToken()
        tokenService.validateAccessToken(AccessToken(accessToken!!, tokenService.getAccessExpiry()!!))
        val response = httpClient.post("/private/deleteFolder") {
            header("Authorization", "Bearer $accessToken")
            setBody(deleteFolderRequest)
        }
        return Result.success(response.body())
    }

}