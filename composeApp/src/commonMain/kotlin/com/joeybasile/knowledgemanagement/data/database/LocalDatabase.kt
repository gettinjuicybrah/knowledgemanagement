package com.joeybasile.knowledgemanagement.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import com.joeybasile.knowledgemanagement.data.database.dao.FolderDao
import com.joeybasile.knowledgemanagement.data.database.dao.NoteDao
import com.joeybasile.knowledgemanagement.data.database.dao.TokenDao
import com.joeybasile.knowledgemanagement.data.database.dao.UserDao
import com.joeybasile.knowledgemanagement.data.database.entity.FolderEntity
import com.joeybasile.knowledgemanagement.data.database.entity.NotesEntity
import com.joeybasile.knowledgemanagement.data.database.entity.TokenEntity
import com.joeybasile.knowledgemanagement.data.database.entity.UserEntity

@Database(entities = [NotesEntity::class, TokenEntity::class, FolderEntity::class, UserEntity::class], version = 1)
@ConstructedBy (LocalDatabaseConstructor::class)
abstract class LocalDatabase : RoomDatabase(){

    abstract fun getTokenDao(): TokenDao
    abstract fun getNoteDao(): NoteDao
    abstract fun getFolderDao(): FolderDao
    abstract fun getUserDao(): UserDao
}

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object LocalDatabaseConstructor:RoomDatabaseConstructor<LocalDatabase>{
    override fun initialize(): LocalDatabase
}

internal const val dbFileName = "local_db.db"
