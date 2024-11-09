package com.joeybasile.knowledgemanagement.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.joeybasile.knowledgemanagement.data.database.entity.NotesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    //This setup ensures that whenever the notes table is updated, the flow emits a new list of NotesEntity.
    @Query("SELECT * FROM notes")
    fun getAllNotes(): Flow<List<NotesEntity>>

    @Query("SELECT * FROM notes WHERE id = id")
    suspend fun getNoteById(id: String): NotesEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NotesEntity)

    @Update
    suspend fun updateNote(note: NotesEntity)

    @Delete
    suspend fun deleteNote(note: NotesEntity)


}