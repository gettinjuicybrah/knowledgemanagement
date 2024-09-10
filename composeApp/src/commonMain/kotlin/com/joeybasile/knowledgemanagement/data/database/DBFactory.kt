package com.joeybasile.knowledgemanagement.data.database

expect class DBFactory {
    fun createDatabase(): LocalDatabase
}