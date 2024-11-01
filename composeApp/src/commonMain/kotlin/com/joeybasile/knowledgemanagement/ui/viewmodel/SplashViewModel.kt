package com.joeybasile.knowledgemanagement.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.joeybasile.knowledgemanagement.data.database.data.repository.FolderRepository
import com.joeybasile.knowledgemanagement.data.database.data.repository.TokenRepository
import com.joeybasile.knowledgemanagement.data.database.data.repository.UserRepository
import com.joeybasile.knowledgemanagement.service.FolderService
import com.joeybasile.knowledgemanagement.service.TokenService
import com.joeybasile.knowledgemanagement.service.UserService
import com.joeybasile.knowledgemanagement.ui.navigation.NavigatorImpl
import com.joeybasile.knowledgemanagement.ui.theme.ThemeManager
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SplashViewModel: ViewModel(), KoinComponent {
    private val tokenService: TokenService by inject()
    private val navigator: NavigatorImpl by inject()
    private val folderService: FolderService by inject()
    private val userService: UserService by inject()

    suspend fun checkAuthState() {
        tokenService.initializeTokenIfNeeded()
        folderService.initializeRootFolderIfNeeded()
        userService.initializeUserRecordIfNeeded()

        val theme = userService.getTheme()
        val isDark = theme == "dark"
        ThemeManager.isDarkTheme = isDark

        val hasRefreshToken = tokenService.getRefreshToken() != null

        if (hasRefreshToken) {
            navigator.navToHome()
        } else {
            navigator.navToLogin()
        }
    }
}