package com.joeybasile.knowledgemanagement.service

import com.joeybasile.knowledgemanagement.data.database.entity.NotesEntity
import com.joeybasile.knowledgemanagement.data.database.data.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class NoteService: KoinComponent {
    private val noteRepository: NoteRepository by inject()
    private val privateAPIService: PrivateAPIService by inject()

    suspend fun insertNote(note: NotesEntity){
        noteRepository.insertNote(note)
        privateAPIService.insertNote(note)
    }

    suspend fun updateNote(note: NotesEntity){
        note.version += 1
        noteRepository.updateNote(note)
        privateAPIService.updateNote(note)
    }

    suspend fun deleteNote(note: NotesEntity){
        noteRepository.deleteNote(note)
        privateAPIService.deleteNote(note)
    }

    suspend fun seeNote(note: NotesEntity){

    }

    //filter the list down for caterment
    fun getAllNotes(): Flow<List<NotesEntity>> {
        return noteRepository.getAllNotes()
    }
}