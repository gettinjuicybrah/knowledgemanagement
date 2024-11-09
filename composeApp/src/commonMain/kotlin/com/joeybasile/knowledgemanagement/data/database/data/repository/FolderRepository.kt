package com.joeybasile.knowledgemanagement.data.database.data.repository

import com.joeybasile.knowledgemanagement.data.database.entity.FolderEntity
import kotlinx.coroutines.flow.Flow

interface FolderRepository {
    suspend fun getAllFolders(): Flow<List<FolderEntity>>
    suspend fun insertFolder(folder: FolderEntity)
    suspend fun updateFolder(folder: FolderEntity)
    suspend fun deleteFolder(folder: FolderEntity)
    suspend fun initializeRootFolder()
    suspend fun getRootFolder(): FolderEntity
    suspend fun checkInitialize(): Boolean
}