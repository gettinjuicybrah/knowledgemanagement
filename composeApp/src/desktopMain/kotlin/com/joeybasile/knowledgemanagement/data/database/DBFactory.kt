package com.joeybasile.knowledgemanagement.data.database

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import java.io.File

actual class DBFactory {
    actual fun createDatabase(): LocalDatabase {
        val dbFile = File(System.getProperty("java.io.tmpdir"), dbFileName)

        val db = Room.databaseBuilder<LocalDatabase>(dbFile.absolutePath)
            .setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO)
            .build()

        return db

    }
}