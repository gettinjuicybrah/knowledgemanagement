package com.joeybasile.knowledgemanagement

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.joeybasile.knowledgemanagement.di.initKoin

fun main() = application {
    initKoin()
    Window(
        onCloseRequest = ::exitApplication,
        title = "knowledgemanagement",
    ) {
        App()
    }
}