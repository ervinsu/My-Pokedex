package com.ervin.pokedex.di.sharedmodule

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

val homeModules = listOf(
    homeApiModule,
    homeRepositoryModule
)
