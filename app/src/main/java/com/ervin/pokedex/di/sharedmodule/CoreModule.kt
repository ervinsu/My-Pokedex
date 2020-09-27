package com.ervin.pokedex.di.sharedmodule

import androidx.room.Room
import com.ervin.pokedex.core.data.source.local.LocalDataSource
import com.ervin.pokedex.core.data.source.local.room.PokemonDatabase
import com.ervin.pokedex.core.data.source.remote.RemoteDataSource
import com.ervin.pokedex.core.domain.backgroundservice.HomeFirstLaunchService
import com.readystatesoftware.chuck.ChuckInterceptor
import kotlinx.coroutines.ExperimentalCoroutinesApi
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
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
        val passphrase: ByteArray = SQLiteDatabase.getBytes("dicoding".toCharArray())
        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(
            androidContext(),
            PokemonDatabase::class.java, "Pokedex.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }

    single { LocalDataSource(get()) }
}

val remoteModule = module {
    single {
        val hostname = "https://pokeapi.co/api/v2/"
        val certificatePinner = CertificatePinner.Builder()
            .add(hostname, "sha256/FEzVOUp4dF3gI0ZVPRJhFbSJVXR+uQmMH65xhs1glH4=")
            .build()
        OkHttpClient.Builder()
            .addInterceptor(ChuckInterceptor(androidContext()))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
            .build()
    }
    single(named("poke")) {
        Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
    }
    single { RemoteDataSource(get(), get()) }
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

@ExperimentalCoroutinesApi
val coreModule = listOf(
    localModule,
    remoteModule,
    firstLaunchServiceModule
)