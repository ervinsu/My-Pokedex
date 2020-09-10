package com.ervin.pokedex.di

import com.ervin.feature_home.ui.HomeViewModel
import com.ervin.pokedex.core.data.source.local.LocalDataSource
import com.ervin.pokedex.core.data.source.remote.RemoteDataSource
import com.ervin.pokedex.core.domain.backgroundservice.HomeFirstLaunchService
import com.ervin.pokedex.core.domain.usecase.home.HomeInteractor
import com.ervin.pokedex.core.domain.usecase.home.HomeUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

@ExperimentalCoroutinesApi
val useCaseModules = module {
    factory<HomeUseCase> { HomeInteractor(get(), androidContext()) }
}

val viewModelModules = module {
    viewModel { HomeViewModel(get()) }
}

@ExperimentalCoroutinesApi
val firstLaunchServiceModule = module {
    scope(named<HomeFirstLaunchService>()) {
        scoped<LocalDataSource> {
            get()
        }
        scoped<RemoteDataSource> {
            get()
        }
    }
}