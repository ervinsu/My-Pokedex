package com.ervin.pokedex.di

import android.content.Intent
import com.ervin.about.ui.AboutMeViewModel
import com.ervin.feature_detail.ui.DetailActivity
import com.ervin.library_common.navigation.FeatureDetail
import com.ervin.listpokemon.ui.ListPokemonFragment
import com.ervin.listpokemon.ui.ListPokemonViewModel
import com.ervin.listpokemon.ui.adapter.ListPokemonAdapter
import com.ervin.pokedex.core.data.repository.HomeRepository
import com.ervin.pokedex.core.data.source.remote.network.home.HomeApiService
import com.ervin.pokedex.core.domain.repository.HomeRepositoryContract
import com.ervin.pokedex.core.domain.usecase.home.HomeInteractor
import com.ervin.pokedex.core.domain.usecase.home.HomeUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
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

@FlowPreview
@ExperimentalCoroutinesApi
val homeActivityModule = module {
    factory<HomeUseCase>(named("homeUseCase")) { HomeInteractor(get(), androidContext()) }
    viewModel { ListPokemonViewModel(get(named("homeUseCase"))) }
    viewModel { AboutMeViewModel() }
}

@ExperimentalCoroutinesApi
@FlowPreview
val listPokemonModule = module {
    scope(named<ListPokemonFragment>()) {
        scoped<FeatureDetail> { (fragment: ListPokemonFragment) ->
            object : FeatureDetail {
                override fun createIntent(): Intent {
                    return Intent(fragment.activity, DetailActivity::class.java)
                }

            }
        }
        scoped {
            ListPokemonAdapter()
        }
    }
}

@FlowPreview
@ExperimentalCoroutinesApi
val homeModules = listOf(
    homeApiModule,
    homeRepositoryModule,
    homeActivityModule,
    listPokemonModule
)
