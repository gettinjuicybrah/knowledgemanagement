package com.joeybasile.knowledgemanagement.data.database.data.repository

import com.joeybasile.knowledgemanagement.data.database.NotesEntity
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getAllNotes(): Flow<List<NotesEntity>>
    suspend fun getNoteById(idA: String, idB: String): NotesEntity?
    suspend fun insertNote(note: NotesEntity)
    suspend fun updateNote(note: NotesEntity)
    suspend fun deleteNote(note: NotesEntity)
}