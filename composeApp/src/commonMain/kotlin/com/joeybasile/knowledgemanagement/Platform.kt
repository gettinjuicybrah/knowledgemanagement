package com.joeybasile.knowledgemanagement

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform