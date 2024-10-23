package com.joeybasile.knowledgemanagement.data.database.data.repository

import com.joeybasile.knowledgemanagement.data.database.dao.FolderDao
import com.joeybasile.knowledgemanagement.data.database.entity.FolderEntity
import com.joeybasile.knowledgemanagement.data.database.entity.TokenEntity
import kotlinx.coroutines.flow.Flow

class FolderRepositoryImpl(
    private val folderDao: FolderDao
): FolderRepository {

    override suspend fun getAllFolders(): Flow<List<FolderEntity>> {
        return folderDao.getAllFolders()
    }

    override suspend fun insertFolder(folder: FolderEntity) {
        folderDao.insertFolder(folder)
    }

    override suspend fun updateFolder(folder: FolderEntity) {
        folderDao.updateFolder(folder)
    }

    override suspend fun deleteFolder(folder: FolderEntity) {
        folderDao.deleteFolder(folder)
    }
    override suspend fun initializeRootFolderIfNeeded() {
        if (folderDao.getFolderCount() == 0) {
            val rootFolder = FolderEntity(
                id = 0,
                title = "root",
                parentFolderId = null,
                isExpanded = false
            )
            folderDao.initializeRootFolderRecord(rootFolder)
        }
    }

    override suspend fun getRootFolder(): FolderEntity{
        return folderDao.getRootFolder()
    }

}