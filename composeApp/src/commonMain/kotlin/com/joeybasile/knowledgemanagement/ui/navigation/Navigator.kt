package com.joeybasile.knowledgemanagement.ui.navigation

interface Navigator {
    fun navToHome()
    fun navToLogin()
    fun navToRegister()
    fun navToSettings()
    fun navToSplash()
    fun navToListNotes()
    fun navToSeeNote()
    fun navToNewNote()
    fun popBackStack()
}
