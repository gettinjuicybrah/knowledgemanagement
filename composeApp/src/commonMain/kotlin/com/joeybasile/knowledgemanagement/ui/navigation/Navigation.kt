package com.joeybasile.knowledgemanagement.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.koin.compose.koinInject
import com.joeybasile.knowledgemanagement.ui.screen.*


@Composable
fun NavHostController() {
    val navController = rememberNavController()
    val navigator: NavigatorImpl = koinInject()

    LaunchedEffect(navController) {
        navigator.initialize(navController)
    }
    NavHost(navController = navController, startDestination = Screen.SplashScreen.route) {
        composable(route = Screen.HomeScreen.route) {
            HomeScreen()
        }
        composable(route = Screen.ListNotesScreen.route) {
            ListNotesScreen()
        }
        composable(route = Screen.FolderDirectoryScreen.route) {
            FolderDirectoryScreen()
        }
        composable(route = Screen.NewNoteScreen.route) {
            NewNoteScreen()
        }
        composable(route = Screen.SeeNoteScreen.route) {
            SeeNoteScreen()
        }
        composable(route = Screen.SettingsScreen.route) {
            SettingsScreen()
        }
        composable(route = Screen.LoginScreen.route) {
            LoginScreen()
        }
        composable(route = Screen.RegisterScreen.route) {
            RegisterScreen()
        }
        composable(route = Screen.SplashScreen.route) {
            SplashScreen()
        }
    }
}

sealed class Screen(val route: String) {
    object HomeScreen : Screen("home_screen")
    object ListNotesScreen : Screen("listnotes_screen")
    object FolderDirectoryScreen : Screen("folderdirectory_screen")
    object NewNoteScreen : Screen("newnote_screen")
    object SeeNoteScreen : Screen("seenote_screen")
    object SettingsScreen : Screen("settings_screen")
    object LoginScreen : Screen("login_screen")
    object RegisterScreen : Screen("register_screen")
    object SplashScreen : Screen("splash_screen")
}