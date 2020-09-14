package com.ervin.pokedex.di

import android.content.Intent
import com.ervin.favorite_pokemon.ui.FavoritePokemonFragment
import com.ervin.favorite_pokemon.ui.FavoritePokemonViewModel
import com.ervin.feature_detail.ui.DetailActivity
import com.ervin.library_common.navigation.FeatureDetail
import com.ervin.list_pokemon.ui.ListPokemonFragment
import com.ervin.list_pokemon.ui.ListPokemonViewModel
import com.ervin.list_pokemon.ui.adapter.ListPokemonAdapter
import com.ervin.pokedex.core.data.repository.HomeRepository
import com.ervin.pokedex.core.data.source.remote.network.home.HomeApiService
import com.ervin.pokedex.core.domain.repository.HomeRepositoryContract
import com.ervin.pokedex.core.domain.usecase.home.HomeInteractor
import com.ervin.pokedex.core.domain.usecase.home.HomeUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit


val homeApiModule = module {
    single<HomeApiService> {
        val retrofit: Retrofit = get(named("poke"))
        retrofit.create(HomeApiService::class.java)
    }
}

val homeRepositoryModule = module {
    single<HomeRepositoryContract> {
        HomeRepository(get(), get())
    }
}

@ExperimentalCoroutinesApi
val homeActivityModule = module {
    factory<HomeUseCase> { HomeInteractor(get(), androidContext()) }
    factory {
        ListPokemonAdapter()
    }
    viewModel { ListPokemonViewModel(get()) }
    viewModel { FavoritePokemonViewModel(get()) }
}

val listPokemonModule = module {
    scope(named<ListPokemonFragment>()) {
        scoped<FeatureDetail> { (fragment: ListPokemonFragment) ->
            object : FeatureDetail {
                override fun createIntent(): Intent {
                    return Intent(fragment.activity, DetailActivity::class.java)
                }

            }
        }
    }
}

val favoritePokemonModule = module {
    scope(named<FavoritePokemonFragment>()) {
        scoped<FeatureDetail> { (fragment: FavoritePokemonFragment) ->
            object : FeatureDetail {
                override fun createIntent(): Intent {
                    return Intent(fragment.activity, DetailActivity::class.java)
                }

            }
        }
    }
}

@ExperimentalCoroutinesApi
val homeModules = listOf(
    homeApiModule,
    homeRepositoryModule,
    homeActivityModule,
    listPokemonModule,
    favoritePokemonModule
)
