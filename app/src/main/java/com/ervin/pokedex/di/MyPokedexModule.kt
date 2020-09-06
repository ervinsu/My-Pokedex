package com.ervin.pokedex.di

import com.ervin.pokedex.core.domain.usecase.home.HomeInteractor
import com.ervin.pokedex.core.domain.usecase.home.HomeUseCase
import com.ervin.pokedex.ui.home.HomeViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModules = module {
    factory<HomeUseCase> { HomeInteractor(get()) }
}

val viewModelModules = module {
    viewModel { HomeViewModel(get()) }
}