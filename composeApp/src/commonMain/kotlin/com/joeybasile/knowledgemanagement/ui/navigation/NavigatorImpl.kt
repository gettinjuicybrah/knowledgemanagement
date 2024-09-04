package com.joeybasile.knowledgemanagement.ui.navigation

import androidx.navigation.NavController
import org.koin.core.component.KoinComponent

class NavigatorImpl() : Navigator, KoinComponent {

    private lateinit var navController: NavController

    fun initialize(navController: NavController) {
        this.navController = navController
    }

    override fun navToHome() {
        navController.navigate(Screen.HomeScreen.route)
    }

    override fun navToLogin() {
        navController.navigate(Screen.LoginScreen.route)
    }

    override fun navToRegister() {
        navController.navigate(Screen.RegisterScreen.route)
    }

    override fun navToSettings() {
        navController.navigate(Screen.SettingsScreen.route)
    }

    override fun navToSplash() {
        navController.navigate(Screen.SplashScreen.route)
    }

    override fun navToListNotes() {
        navController.navigate(Screen.ListNotesScreen.route)
    }

    override fun navToSeeNote() {
        navController.navigate(Screen.SeeNoteScreen.route)
    }

    override fun navToNewNote() {
        navController.navigate(Screen.NewNoteScreen.route)
    }

    override fun popBackStack() {
        navController.popBackStack()
    }
}