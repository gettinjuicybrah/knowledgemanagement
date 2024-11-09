package com.joeybasile.knowledgemanagement.service

import com.joeybasile.knowledgemanagement.data.database.entity.NotesEntity
import com.joeybasile.knowledgemanagement.data.database.data.repository.NoteRepository
import com.joeybasile.knowledgemanagement.data.model.User
import com.joeybasile.knowledgemanagement.util.noteEntityToNote
import com.joeybasile.knowledgemanagement.util.userEntityToUser
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class NoteService : KoinComponent {
    private val noteRepository: NoteRepository by inject()
    private val privateAPIService: PrivateAPIService by inject()
    private val userService: UserService by inject()

    suspend fun insertNote(note: NotesEntity) {
        noteRepository.insertNote(note)
        val userEntity = userService.getUser()
        val user = userEntityToUser(userEntity)
        val note = noteEntityToNote(note)
        privateAPIService.insertNote(user, note)

    }

    suspend fun updateNote(note: NotesEntity) {
        note.version += 1
        noteRepository.updateNote(note)
        val userEntity = userService.getUser()
        val user = userEntityToUser(userEntity)
        val note = noteEntityToNote(note)
        privateAPIService.updateNote(user, note)
    }

    suspend fun deleteNote(note: NotesEntity) {
        noteRepository.deleteNote(note)
        val userEntity = userService.getUser()
        val user = userEntityToUser(userEntity)
        val note = noteEntityToNote(note)
        privateAPIService.deleteNote(user, note)
    }

    suspend fun getAllNotes(): Flow<List<NotesEntity>> {
        return noteRepository.getAllNotes()
    }

}