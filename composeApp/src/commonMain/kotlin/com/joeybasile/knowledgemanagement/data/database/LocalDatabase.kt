package com.joeybasile.knowledgemanagement.data.database

@Database(entities = [TokenEntity::class], version = 1)
abstract class LocalDatabase : RoomDatabase(), DB {

    abstract fun getTokenDao(): TokenDao

    override fun clearAllTables() {
        super.clearAllTables()
    }
}