package com.example.notabene.di.modules

import com.example.notabene.network.services.NoteApiService
import com.example.notabene.repositories.NotesRepository
import com.example.notabene.viewmodel.NotesViewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module


internal val coreModules = module {
    // Inject a singleton for the user repo
    single { NotesRepository(get()) }


    // Inject user view model
    single { NotesViewModel(get()) }

    // Webservices
    single {
        createWebService<NoteApiService>(
            get(
                named(ApiRetrofitClient)
            )
        )
    }
}

// Class representing the configuration to parse from the gradle file
data class JsonConf(
    val baseUrl: String
)