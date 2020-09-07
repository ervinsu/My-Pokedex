package com.ervin.pokedex

import android.app.Application
import com.ervin.pokedex.core.di.*
import com.ervin.pokedex.di.useCaseModules
import com.ervin.pokedex.di.viewModelModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.module.Module

class MyPokedexApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@MyPokedexApp)
            val listModules = mutableListOf<Module>()
            listModules.add(localModule)
            listModules.add(remoteModule)
            listModules.add(coreModule)
            listModules.addAll(homeModules)
            listModules.add(detailApiModule)
            listModules.add(useCaseModules)
            listModules.add(viewModelModules)
            modules(
                listModules
            )
        }
    }
}