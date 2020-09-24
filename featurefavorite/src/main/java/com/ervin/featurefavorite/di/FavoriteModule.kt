package com.ervin.featurefavorite.di

import com.ervin.featurefavorite.ui.FavoritePokemonFragment
import com.ervin.featurefavorite.ui.FavoritePokemonViewModel
import com.ervin.featurefavorite.ui.adapter.FavoritePokemonAdapter
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val favoritePokemonModule = module {
    scope(named<FavoritePokemonFragment>()) {

        scoped {
            FavoritePokemonAdapter()
        }
    }
    viewModel { FavoritePokemonViewModel(get(named("homeUseCase"))) }
}