package com.joeybasile.knowledgemanagement.di

import com.joeybasile.knowledgemanagement.data.database.DBFactory
import com.joeybasile.knowledgemanagement.data.database.LocalDatabase
import com.joeybasile.knowledgemanagement.data.database.dao.*
import com.joeybasile.knowledgemanagement.data.database.data.repository.*
import com.joeybasile.knowledgemanagement.network.KtorHttpClient
import com.joeybasile.knowledgemanagement.ui.navigation.NavigatorImpl
import io.ktor.client.HttpClient
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

import com.joeybasile.knowledgemanagement.service.*
import com.joeybasile.knowledgemanagement.network.api.*
import com.joeybasile.knowledgemanagement.ui.viewmodel.*

import com.joeybasile.knowledgemanagement.util.*

expect val platformModule: Module

val sharedModule = module {
    single<LocalDatabase> {
        val dbFactory: DBFactory = get()
        dbFactory.createDatabase()
    }

    single<TokenDao> {
        get<LocalDatabase>().getTokenDao()
    }
    single<NoteDao> {
        get<LocalDatabase>().getNoteDao()
    }
    single<FolderDao> {
        get<LocalDatabase>().getFolderDao()
    }
    single<FolderMembersDao> {
        get<LocalDatabase>().getFolderMembersDao()
    }
    single<HttpClient> {
        val ktorHttpClient: KtorHttpClient = get()
        ktorHttpClient.create()
    }

    single<NavigatorImpl>{
        NavigatorImpl()
    }

    singleOf(::TokenRepositoryImpl).bind<TokenRepository>()
    singleOf(::NoteRepositoryImpl).bind<NoteRepository>()
    singleOf(::FolderRepositoryImpl).bind<FolderRepository>()
    singleOf(::SelectedNoteUseCase)
    singleOf(::PublicAPI)
    singleOf(::PublicAPIService)
    singleOf(::PrivateAPI)
    singleOf(::PrivateAPIService)

    singleOf(::TokenService)
    singleOf(::NoteService)
    singleOf(::AuthService)

    viewModelOf(::ListNotesViewModel)
    viewModelOf(::SeeNoteViewModel)
    viewModelOf(::NewNoteViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::SplashViewModel)
    viewModelOf(::RegisterViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::SettingsViewModel)
}

/*
val databaseModule = module {
    single<NoteDatabase> {
        val dbFactory: DBFactory = get()
        dbFactory.createDatabase()
    }
    single<NoteDao> { get<NoteDatabase>().getNoteDao() }
    single<TokenDao> { get<NoteDatabase>().getTokenDao() }
    single<UserDao> { get<NoteDatabase>().getUserDao() }
}
 */
/*
val networkModule = module {
    single<HttpClient> {
        //the targets will implement their engines. get() creates
        //a KtorHttpClient object with the specific engine... so, get() uses to params specified.
        val ktorHttpClient: KtorHttpClient = get()
        ktorHttpClient.create() }
}
val navigationModule = module {
    single { NavigatorImpl() }
}
 */
/*
val sharedModule = module {
    includes(databaseModule)
    includes(networkModule)
    includes(navigationModule)

    //single<NoteRepository> { NoteRepositoryImpl(get()) }
    singleOf(::NoteRepositoryImpl).bind<NoteRepository>()
    singleOf(::TokenRepositoryImpl).bind<TokenRepository>()
    singleOf(::UserRepositoryImpl).bind<UserRepository>()

    singleOf(::CommonDBRepository)

    singleOf(::PublicAPI)
    singleOf(::PrivateAPI)
    single { PublicService() }
    single { PrivateService() }

    viewModelOf(::ListNotesViewModel)
    viewModelOf(::SeeNoteViewModel)
    viewModelOf(::NewNoteViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::SplashViewModel)
    viewModelOf(::RegisterViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::SettingsViewModel)
    single { SelectedNoteUseCase() }

    /*
    viewModel { ListNotesViewModel(get()) }
    viewModel { SeeNoteViewModel(get()) }
    viewModel { NewNoteViewModel(get()) }
    */


}*/