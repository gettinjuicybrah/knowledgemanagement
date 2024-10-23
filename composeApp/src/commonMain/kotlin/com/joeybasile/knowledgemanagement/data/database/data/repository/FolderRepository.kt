package com.joeybasile.knowledgemanagement.data.database.data.repository

import com.joeybasile.knowledgemanagement.data.database.entity.FolderEntity
import kotlinx.coroutines.flow.Flow

interface FolderRepository {
    suspend fun getAllFolders(): Flow<List<FolderEntity>>
    suspend fun insertFolder(folder: FolderEntity)
    suspend fun updateFolder(folder: FolderEntity)
    suspend fun deleteFolder(folder: FolderEntity)
    suspend fun initializeRootFolderIfNeeded()
    suspend fun getRootFolder(): FolderEntity
}