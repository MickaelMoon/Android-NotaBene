package com.example.notabene.di

import android.content.Context
import com.example.notabene.BuildConfig
import com.example.notabene.di.modules.JsonConf
import com.example.notabene.di.modules.coreModules
import com.example.notabene.di.modules.remoteModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.error.KoinAppAlreadyStartedException
import org.koin.dsl.module

fun injectModuleDependencies(context: Context) {
    try {
        startKoin {
            androidContext(context)
            modules(modules)
        }
    } catch (alreadyStart: KoinAppAlreadyStartedException) {
        loadKoinModules(modules)
    }
}

fun parseConfigurationAndAddItToInjectionModules() {
    val apiConf = JsonConf(baseUrl = BuildConfig.BASE_URL)
    modules.add(module {
        single { apiConf }
    })
}

private val modules = mutableListOf(coreModules, remoteModule)
