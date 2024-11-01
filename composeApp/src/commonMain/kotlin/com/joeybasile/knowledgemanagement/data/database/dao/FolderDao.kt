package com.joeybasile.knowledgemanagement.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.joeybasile.knowledgemanagement.data.database.entity.FolderEntity
import com.joeybasile.knowledgemanagement.data.database.entity.NotesEntity
import com.joeybasile.knowledgemanagement.data.database.entity.TokenEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FolderDao {
    @Insert
    suspend fun insertFolder(folder: FolderEntity)

    @Update
    suspend fun updateFolder(folder: FolderEntity)

    @Delete
    suspend fun deleteFolder(folder: FolderEntity)

    @Query("SELECT * FROM folders")
    fun getAllFolders(): Flow<List<FolderEntity>>

    @Query("SELECT COUNT(*) FROM folders")
    suspend fun getFolderCount(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun initializeRootFolderRecord(folder: FolderEntity)

    @Query("SELECT * FROM folders WHERE parentFolderId = null")
    suspend fun getRootFolder(): FolderEntity
}