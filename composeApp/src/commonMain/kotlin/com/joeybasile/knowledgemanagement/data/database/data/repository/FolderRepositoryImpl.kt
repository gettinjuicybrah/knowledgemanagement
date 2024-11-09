package com.joeybasile.knowledgemanagement.data.database.data.repository

import com.joeybasile.knowledgemanagement.data.database.dao.FolderDao
import com.joeybasile.knowledgemanagement.data.database.entity.FolderEntity
import com.joeybasile.knowledgemanagement.util.generateUUID
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
    override suspend fun initializeRootFolder() {

            val rootFolder = FolderEntity(
                id = generateUUID(),
                title = "root",
                parentFolderId = null,
                isExpanded = false
            )
            folderDao.initializeRootFolderRecord(rootFolder)

    }
    override suspend fun checkInitialize():Boolean{
        return (folderDao.getFolderCount() == 0)
    }
    override suspend fun getRootFolder(): FolderEntity{
        return folderDao.getRootFolder()
    }

}