package com.ervin.featurefavorite.di

import com.ervin.featurefavorite.ui.FavoritePokemonViewModel
import com.ervin.featurefavorite.ui.adapter.FavoritePokemonAdapter
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoritePokemonModule = module {
    single {
        FavoritePokemonAdapter()
    }
    viewModel { FavoritePokemonViewModel(get()) }
}