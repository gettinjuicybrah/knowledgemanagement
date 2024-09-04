package com.joeybasile.knowledgemanagement.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.joeybasile.knowledgemanagement.ui.viewmodel.SplashViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SplashScreen() {
    val viewModel: SplashViewModel = koinViewModel()
    LaunchedEffect(Unit) {
        viewModel.checkAuthState()
    }

    // Show a loading indicator or splash image
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}