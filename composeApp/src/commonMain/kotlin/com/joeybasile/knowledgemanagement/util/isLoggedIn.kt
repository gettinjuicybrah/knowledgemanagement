package com.joeybasile.knowledgemanagement.util

import com.joeybasile.knowledgemanagement.service.TokenService
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

suspend fun isLoggedIn(refreshExpiry: Long):Boolean{
    return refreshExpiry > 0
}