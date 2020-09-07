package com.ervin.pokedex.core.di

import androidx.room.Room
import com.ervin.pokedex.core.data.source.local.LocalDataSource
import com.ervin.pokedex.core.data.source.local.room.PokemonDatabase
import com.ervin.pokedex.core.data.source.remote.RemoteDataSource
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val localModule = module {
    factory { get<PokemonDatabase>().pokemonDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            PokemonDatabase::class.java, "Pokedex.db"
        ).fallbackToDestructiveMigration().build()
    }
}

val remoteModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(ChuckInterceptor(androidContext()))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }
    single(named("poke")) {
        Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
    }
}

val coreModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get(), get()) }
}