package com.joeybasile.knowledgemanagement

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.joeybasile.knowledgemanagement.service.UserService
import com.joeybasile.knowledgemanagement.ui.navigation.NavHostController
import com.joeybasile.knowledgemanagement.ui.theme.AppTheme
import com.joeybasile.knowledgemanagement.ui.theme.ThemeManager
import com.joeybasile.knowledgemanagement.ui.viewmodel.SettingsViewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import knowledgemanagement.composeapp.generated.resources.Res
import knowledgemanagement.composeapp.generated.resources.compose_multiplatform
import org.koin.core.context.KoinContext
import org.koin.compose.KoinContext
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    AppContent()

}
/*
Need to add some interface between the two to remove the coupling or redesign to avoid
this shit
 */
@Composable
fun AppContent() {
    // Use ViewModel to manage the state; it keeps the app's theme logic
    val settingsViewModel: SettingsViewModel = koinViewModel()
    val isDarkTheme by remember { mutableStateOf(ThemeManager.isDarkTheme) }

    // Observe changes to theme state
    LaunchedEffect(settingsViewModel.state.collectAsState().value.isDarkTheme) {
        // This will trigger recomposition when theme changes
    }

    // Apply theme
    AppTheme() {
        NavHostController()
    }
}
