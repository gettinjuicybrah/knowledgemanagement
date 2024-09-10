package com.joeybasile.knowledgemanagement.di

actual val platformModule = module {
    single { DBFactory(androidContext()) }
    single<KtorHttpClient> { KtorHttpClient(OkHttp.create())}
}
