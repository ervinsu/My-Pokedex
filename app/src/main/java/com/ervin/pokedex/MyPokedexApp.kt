package com.ervin.pokedex

import android.app.Application
import com.ervin.pokedex.core.di.coreModule
import com.ervin.pokedex.core.di.detailApiModule
import com.ervin.pokedex.core.di.homeModules
import com.ervin.pokedex.core.di.remoteModule
import com.ervin.pokedex.di.useCaseModules
import com.ervin.pokedex.di.viewModelModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyPokedexApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@MyPokedexApp)
            val listModules = mutableListOf(
                remoteModule
            )
            listModules.addAll(homeModules)
            listModules.add(detailApiModule)
            listModules.add(coreModule)
            listModules.add(useCaseModules)
            listModules.add(viewModelModules)
            modules(
                listModules
            )
        }
    }
}