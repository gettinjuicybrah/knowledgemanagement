package com.joeybasile.knowledgemanagement.data.database.data.repository

import com.joeybasile.knowledgemanagement.data.database.dao.NoteDao
import com.joeybasile.knowledgemanagement.data.database.entity.NotesEntity
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(
    private val dao: NoteDao
) : NoteRepository {

    override fun getAllNotes(): Flow<List<NotesEntity>> {
        return dao.getAllNotes()
    }

    override suspend fun getNoteById(idA: String, idB: String): NotesEntity? {
        return dao.getNoteById(idA, idB)
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