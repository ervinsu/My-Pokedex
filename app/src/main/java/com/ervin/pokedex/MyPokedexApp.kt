package com.ervin.pokedex

import android.app.Application
import com.ervin.pokedex.di.detailModule
import com.ervin.pokedex.di.homeModules
import com.ervin.pokedex.di.sharedmodule.coreModule
import com.ervin.pokedex.di.sharedmodule.localModule
import com.ervin.pokedex.di.sharedmodule.remoteModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.module.Module

class MyPokedexApp : Application() {
    @FlowPreview
    @ExperimentalCoroutinesApi
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@MyPokedexApp)
            val listModules = mutableListOf<Module>()
            listModules.add(localModule)
            listModules.add(remoteModule)
            listModules.addAll(coreModule)
            listModules.addAll(homeModules)
            listModules.addAll(detailModule)
            modules(
                listModules
            )
        }
    }
}