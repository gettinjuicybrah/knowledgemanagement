package com.joeybasile.knowledgemanagement.util

import com.joeybasile.knowledgemanagement.data.database.entity.FolderEntity
import com.joeybasile.knowledgemanagement.data.database.entity.NotesEntity
import com.joeybasile.knowledgemanagement.data.database.entity.UserEntity
import com.joeybasile.knowledgemanagement.data.model.Folder
import com.joeybasile.knowledgemanagement.data.model.Note
import com.joeybasile.knowledgemanagement.data.model.User
import com.joeybasile.knowledgemanagement.data.model.UserAuth

fun userEntityToUser(user: UserEntity): User {
    return User(user.id, user.username, user.theme)
}

fun noteEntityToNote(note: NotesEntity): Note {
    return Note(
        id = note.id,
        parentFolderId = note.parentFolderId!!,
        title = note.title,
        content = note.content,
        createdAt = note.creation_date,
        updatedAt = note.last_edit_date,
        version = note.version
    )
}

fun folderEntityToNote(folder: FolderEntity): Folder {
    return Folder(
        id = folder.id,
        parentFolderId = folder.parentFolderId!!,
        title = folder.title,
        isExpanded = folder.isExpanded
    )
}