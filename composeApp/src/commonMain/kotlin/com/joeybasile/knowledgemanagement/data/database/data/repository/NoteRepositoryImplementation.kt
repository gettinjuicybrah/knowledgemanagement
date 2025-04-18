package com.joeybasile.knowledgemanagement.data.database.data.repository

import com.joeybasile.knowledgemanagement.data.database.dao.NoteDao
import com.joeybasile.knowledgemanagement.data.database.entity.NotesEntity
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(
    private val dao: NoteDao
) : NoteRepository {

    override suspend fun getAllNotes(): Flow<List<NotesEntity>> {
        return dao.getAllNotes()
    }

    override suspend fun getNoteById(id: String): NotesEntity? {
        return dao.getNoteById(id)
    }

    override suspend fun insertNote(note: NotesEntity){
        return dao.insertNote(note)
    }

    override suspend fun updateNote(note: NotesEntity) {
        dao.updateNote(note)
    }

    override suspend fun deleteNote(note: NotesEntity) {
        dao.deleteNote(note)
    }


}