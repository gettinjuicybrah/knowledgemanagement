package com.joeybasile.knowledgemanagement.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.joeybasile.knowledgemanagement.data.database.dao.FolderDao
import com.joeybasile.knowledgemanagement.data.database.dao.FolderMembersDao
import com.joeybasile.knowledgemanagement.data.database.dao.NoteDao
import com.joeybasile.knowledgemanagement.data.database.dao.TokenDao
import com.joeybasile.knowledgemanagement.data.database.entity.FolderEntity
import com.joeybasile.knowledgemanagement.data.database.entity.FolderMembersEntity
import com.joeybasile.knowledgemanagement.data.database.entity.NotesEntity
import com.joeybasile.knowledgemanagement.data.database.entity.TokenEntity

@Database(entities = [NotesEntity::class, TokenEntity::class, FolderEntity::class, FolderMembersEntity::class], version = 1)
abstract class LocalDatabase : RoomDatabase(), DB {

    abstract fun getTokenDao(): TokenDao
    abstract fun getNoteDao(): NoteDao
    abstract fun getFolderDao(): FolderDao
    abstract fun getFolderMembersDao(): FolderMembersDao

    override fun clearAllTables() {
        super.clearAllTables()
    }
}

internal const val dbFileName = "local.db"

interface DB {
    fun clearAllTables() {}
}
