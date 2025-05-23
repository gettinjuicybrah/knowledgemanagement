package com.joeybasile.knowledgemanagement.data.database.data.repository

import com.joeybasile.knowledgemanagement.data.database.entity.NotesEntity
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    suspend fun getAllNotes(): Flow<List<NotesEntity>>
    suspend fun getNoteById(id: String): NotesEntity?
    suspend fun insertNote(note: NotesEntity)
    suspend fun updateNote(note: NotesEntity)
    suspend fun deleteNote(note: NotesEntity)
}