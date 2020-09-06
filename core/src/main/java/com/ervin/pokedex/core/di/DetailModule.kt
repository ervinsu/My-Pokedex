package com.ervin.pokedex.core.di

import com.ervin.pokedex.core.data.source.remote.network.detail.DetailApiService
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val detailApiModule = module {
    single<DetailApiService> {
        val retrofit: Retrofit = get(named("poke"))
        retrofit.create(DetailApiService::class.java)
    }
}
