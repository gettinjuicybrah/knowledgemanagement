package com.joeybasile.knowledgemanagement.util

import com.joeybasile.knowledgemanagement.data.database.entity.FolderEntity
import com.joeybasile.knowledgemanagement.data.database.entity.NotesEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map


data class FolderNode(
    var children: MutableList<FolderNode> = mutableListOf(),
    var notes: MutableList<NotesEntity> = mutableListOf(),
    val folderEntity: FolderEntity
)

suspend fun buildDirectoryTree(
    noteResult: Flow<List<NotesEntity>>,
    folderResult: Flow<List<FolderEntity>>
): FolderNode? {
    //each flow list is just a container of a single list.
    val folderList = folderResult.first()
    val noteList = noteResult.first()

    //each element in folderList is a folderEntity type.
    val folderMap = folderList.associate { folderEntity ->
        //each folderEntity's id is a key, and the value is a FolderNode object, which contains the folderEntity itself as one of its attributes.
        folderEntity.id to FolderNode(
            folderEntity = folderEntity
        )
    }


    //accessing every folderEntity element in the list
    folderList.forEach { folderEntity ->
        //if not root folder, because only folder with parentFolderId == null is root folder
        if (folderEntity.parentFolderId != null) {
            //if the currently considered folderEntity is not the root folder, then we use it's parent folder id as a key and then access
            // the children attribute of the Folder object, and then add the currently considered folderEntity as a child - specifically by
            //having this child attribute existing as a key value pair itself in the map, which was generated above.
            folderMap[folderEntity.parentFolderId]?.children?.add(folderMap[folderEntity.id]!!)
        }
    }

    // Add notes to respective folders
    noteList.forEach { note ->
        folderMap[note.parentFolderId]?.notes?.add(note)

    }

    // Return only root folders (those without a parent), which I can then use
    // to propagate UI.
    val rootFolder = folderMap.values.find{
        it.folderEntity.parentFolderId == null
    }
    return rootFolder
}

// Extension function to get a flattened list of all folders
fun List<FolderNode>.flatten(): List<FolderNode> {
    return flatMap { folder ->
        listOf(folder) + folder.children.flatten()
    }
}

// Extension function to find a folder by its ID
fun List<FolderNode>.findFolderById(id: Int): FolderNode? {
    return flatten().find { it.folderEntity.id == id }
}