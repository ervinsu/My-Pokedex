package com.ervin.pokedex.core.data.repository.home

import com.ervin.pokedex.core.data.source.Resource
import com.ervin.pokedex.core.data.source.remote.RemoteDataSource
import com.ervin.pokedex.core.data.source.remote.network.ApiResponse
import com.ervin.pokedex.core.data.source.remote.response.PokemonResponse
import com.ervin.pokedex.core.domain.model.Pokemon
import com.ervin.pokedex.core.domain.repository.home.HomeRepositoryContract
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class HomeRepository(
    private val remoteDataSource: RemoteDataSource
) : HomeRepositoryContract {
    override fun getAllPokemon(): Flow<Resource<List<Pokemon>>> =
        flow {
            emit(Resource.Loading())
            when (val remoteCall = remoteDataSource.getAllListPokemon().first()) {
                is ApiResponse.Success -> {
                    val result = remoteCall.data
                        .map(::convertToDomainModel)
                    emit(Resource.Success(result))
                }
                is ApiResponse.Empty -> {
                    emit(Resource.Error(message = "empty"))
                }
                is ApiResponse.Error -> {
                    emit(Resource.Error(message = "error"))
                }
            }
        }


    private fun convertToDomainModel(pokemon: PokemonResponse) =
        Pokemon(pokemon.pokemonId, pokemon.pokemonName)

}