package com.joeybasile.knowledgemanagement.data.database

import androidx.room.RoomDatabaseConstructor

expect class DBFactory {
    fun createDatabase(): LocalDatabase

}
