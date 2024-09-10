package com.joeybasile.knowledgemanagement.di

expect val platformModule: Module
val databaseModule = module {
    single<NoteDatabase> {
        val dbFactory: DBFactory = get()
        dbFactory.createDatabase()
    }
    single<NoteDao> { get<NoteDatabase>().getNoteDao() }
    single<TokenDao> { get<NoteDatabase>().getTokenDao() }
    single<UserDao> { get<NoteDatabase>().getUserDao() }
}

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


}