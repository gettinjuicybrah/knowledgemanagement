package com.joeybasile.knowledgemanagement.util

import org.koin.core.component.KoinComponent

data class SelectedNoteUseCase(
    var id: String = "",
    var parentFolderId: String = "",
    var noteTitle: String = "",
    var noteContent: String = "",
    var createdAt: String = "",
    var updatedAt: String = "",
    var version: Int = 0


): KoinComponent