package com.joeybasile.knowledgemanagement.service

import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import com.joeybasile.knowledgemanagement.data.database.data.repository.*
import com.joeybasile.knowledgemanagement.data.database.entity.FolderEntity
import com.joeybasile.knowledgemanagement.util.folderEntityToNote
import com.joeybasile.knowledgemanagement.util.noteEntityToNote
import com.joeybasile.knowledgemanagement.util.userEntityToUser

class FolderService : KoinComponent {
    private val folderRepository: FolderRepository by inject()
    private val privateAPIService: PrivateAPIService by inject()
    private val userService: UserService by inject()
    suspend fun initializeRootFolderIfNeeded() {
        if (folderRepository.checkInitialize()) {
            folderRepository.initializeRootFolder()
            val userEntity = userService.getUser()
            val user = userEntityToUser(userEntity)
            val folder = folderEntityToNote(folderRepository.getRootFolder())
            privateAPIService.insertFolder(user, folder)
        }

    }

    suspend fun updateFolder(folderEntity: FolderEntity) {
        folderRepository.updateFolder(folderEntity)
        val userEntity = userService.getUser()
        val user = userEntityToUser(userEntity)
        val folder = folderEntityToNote(folderEntity)
        privateAPIService.updateFolder(user, folder)
    }

    suspend fun getRootFolder(): FolderEntity {
        return folderRepository.getRootFolder()
    }

    suspend fun getAllFolders(): Flow<List<FolderEntity>> {
        return folderRepository.getAllFolders()
    }

    suspend fun insertFolder(folderEntity: FolderEntity) {
        folderRepository.insertFolder(folderEntity)
        val userEntity = userService.getUser()
        val user = userEntityToUser(userEntity)
        val folder = folderEntityToNote(folderEntity)
        privateAPIService.updateFolder(user, folder)
    }

    suspend fun deleteFolder(folderEntity: FolderEntity) {
        folderRepository.deleteFolder(folderEntity)
        val userEntity = userService.getUser()
        val user = userEntityToUser(userEntity)
        val folder = folderEntityToNote(folderEntity)
        privateAPIService.deleteFolder(user, folder)
    }


}