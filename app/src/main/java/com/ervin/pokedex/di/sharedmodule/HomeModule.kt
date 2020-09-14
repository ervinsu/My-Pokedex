package com.ervin.pokedex.di.sharedmodule

import android.content.Intent
import com.ervin.feature_detail.ui.DetailActivity
import com.ervin.library_common.navigation.FeatureDetail
import com.ervin.list_pokemon.ui.ListPokemonFragment
import com.ervin.list_pokemon.ui.adapter.ListPokemonAdapter
import com.ervin.pokedex.core.data.repository.home.HomeRepository
import com.ervin.pokedex.core.data.source.remote.network.home.HomeApiService
import com.ervin.pokedex.core.domain.repository.home.HomeRepositoryContract
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

val homeModules = listOf(
    homeApiModule,
    homeRepositoryModule,
    listPokemonModule
)
