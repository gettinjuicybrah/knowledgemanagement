package com.joeybasile.knowledgemanagement.service

import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import com.joeybasile.knowledgemanagement.data.database.data.repository.*
import com.joeybasile.knowledgemanagement.data.database.entity.FolderEntity

class FolderService: KoinComponent {
    private val folderRepository: FolderRepository by inject()

    suspend fun initializeRootFolderIfNeeded(){
        folderRepository.initializeRootFolderIfNeeded()
    }
    suspend fun updateFolder(folderEntity: FolderEntity){
        folderRepository.updateFolder(folderEntity)
    }
    suspend fun getRootFolder(): FolderEntity {
        return folderRepository.getRootFolder()
    }
    suspend fun getAllFolders():Flow<List<FolderEntity>>{
        return folderRepository.getAllFolders()
    }
    suspend fun insertFolder(folderEntity: FolderEntity){
        folderRepository.insertFolder(folderEntity)
    }
    suspend fun deleteFolder(folderEntity: FolderEntity){
        folderRepository.deleteFolder(folderEntity)
    }


}