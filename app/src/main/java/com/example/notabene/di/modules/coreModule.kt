package com.example.notabene.di.modules

import com.example.notabene.network.services.LoginApiService
import com.example.notabene.network.services.NoteApiService
import com.example.notabene.repositories.LoginRepository
import com.example.notabene.repositories.NotesRepository
import com.example.notabene.viewmodel.LoginViewModel
import com.example.notabene.viewmodel.NotesViewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module


internal val coreModules = module {
    // Inject a singleton for the user repo
    single { NotesRepository(get()) }
    single { LoginRepository(get()) }


    // Inject user view model
    single { NotesViewModel(get()) }
    single { LoginViewModel(get()) }

    // Webservices
    single {
        createWebService<NoteApiService>(
            get(
                named(ApiRetrofitClient)
            )
        )
    }
    single {
        createWebService<LoginApiService>(
            get(named(ApiRetrofitClient)
            )
        )
    }

}

// Class representing the configuration to parse from the gradle file
data class JsonConf(
    val baseUrl: String
)