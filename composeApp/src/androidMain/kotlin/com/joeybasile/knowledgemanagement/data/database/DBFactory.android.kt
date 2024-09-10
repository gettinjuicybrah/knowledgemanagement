package com.joeybasile.knowledgemanagement.data.database

import android.content.Context
import kotlinx.coroutines.Dispatchers

actual class DBFactory(private val context: Context) {
    actual fun createDatabase(): LocalDatabase {
        val dbFile = context.getDatabasePath(dbFileName)
        return Room.databaseBuilder<LocalDatabase>(context, dbFile.absolutePath)
            .setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO)
            .build()
    }
}