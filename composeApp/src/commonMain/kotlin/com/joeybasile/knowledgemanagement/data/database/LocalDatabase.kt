package com.joeybasile.knowledgemanagement.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.joeybasile.knowledgemanagement.data.database.TokenDao
import com.joeybasile.knowledgemanagement.data.database.TokenEntity

@Database(entities = [NotesEntity::class, TokenEntity::class], version = 1)
abstract class LocalDatabase : RoomDatabase(), DB {

    abstract fun getTokenDao(): TokenDao
    abstract fun getNoteDao(): NoteDao
    override fun clearAllTables() {
        super.clearAllTables()
    }
}

internal const val dbFileName = "local.db"

interface DB {
    fun clearAllTables() {}
}
