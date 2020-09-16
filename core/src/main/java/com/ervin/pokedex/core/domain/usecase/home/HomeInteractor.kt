package com.ervin.pokedex.core.domain.usecase.home

import android.content.Context
import android.content.Intent
import android.os.Build
import com.ervin.pokedex.core.data.source.Resource
import com.ervin.pokedex.core.domain.backgroundservice.HomeFirstLaunchService
import com.ervin.pokedex.core.domain.model.Pokemon
import com.ervin.pokedex.core.domain.repository.HomeRepositoryContract
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class HomeInteractor(
    private val homeRepository: HomeRepositoryContract,
    private val context: Context
) : HomeUseCase {
    override fun getAllLocalPokemon(): Flow<Resource<List<Pokemon>>> =
        homeRepository.getAllLocalPokemon()

    override fun getAllFavoritePokemon(): Flow<Resource<List<Pokemon>>> =
        homeRepository.getAllFavoritePokemon()

    override suspend fun getSearchedPokemon(input: String): List<Pokemon> =
        homeRepository.getSearchedPokemon(input)


    override fun maybeFetchRemotePokemon() {
        CoroutineScope(Dispatchers.Default).launch {
            getLocalPokemonSize().collect {
                if (it == 0) {
                    val intent = Intent(context, HomeFirstLaunchService::class.java)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        context.startForegroundService(intent)
                    } else {
                        context.startService(intent)
                    }
                }
            }
        }
    }

    override fun maybeFetchRemoteElement(): Flow<Resource<Int>> =
        homeRepository.maybeGetRemoteElement()

    override fun getLocalPokemonSize(): Flow<Int> = homeRepository.getLocalPokemonSize()
}