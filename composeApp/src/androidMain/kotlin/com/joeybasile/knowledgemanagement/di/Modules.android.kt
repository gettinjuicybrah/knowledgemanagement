package com.joeybasile.knowledgemanagement.di

import com.joeybasile.knowledgemanagement.data.database.DBFactory
import com.joeybasile.knowledgemanagement.network.KtorHttpClient
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual val platformModule = module {
    single { DBFactory(androidContext()) }
    single<KtorHttpClient> { KtorHttpClient(OkHttp.create())}
}
