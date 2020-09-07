package com.ervin.pokedex.core.data.repository.home

import com.ervin.pokedex.core.data.source.Resource
import com.ervin.pokedex.core.data.source.local.LocalDataSource
import com.ervin.pokedex.core.data.source.remote.RemoteDataSource
import com.ervin.pokedex.core.data.source.remote.network.ApiResponse
import com.ervin.pokedex.core.data.source.remote.response.PokemonResponse
import com.ervin.pokedex.core.domain.model.Pokemon
import com.ervin.pokedex.core.domain.repository.home.HomeRepositoryContract
import com.ervin.pokedex.core.util.mappingPokemonApiResponseToLocalResponse
import com.ervin.pokedex.core.util.mappingPokemonEntityToDomainModel
import kotlinx.coroutines.flow.*

class HomeRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : HomeRepositoryContract {
    override fun getAllPokemon(): Flow<Resource<List<Pokemon>>> =
        flow {
            emit(Resource.Loading())
            /**
             * check if the db is empty, then we should fetch api to get the pokemon
             * else we show the pokemon instead
             */
            val localSize = localDataSource.getSizeDBPokemon().first()
            if (localSize == 0) {
                when (val remoteCall = remoteDataSource.getAllListPokemon().first()) {
                    is ApiResponse.Success -> {

                        /**
                         * convert the remote call and save it to local db
                         */
                        val convertedApiResult = remoteCall.data
                            .map(::mappingPokemonApiResponseToLocalResponse)
                        localDataSource.insertAllPokemon(convertedApiResult)

                        /**
                         * get the local db data and return it to view
                         */
                        emitAll(getAllLocalPokemon().map {
                            Resource.Success(it)
                        })

                    }
                    is ApiResponse.Empty -> {
                        /**
                         * get the local db data and return it to view
                         */
                        emitAll(getAllLocalPokemon().map {
                            Resource.Success(it)
                        })
                    }
                    is ApiResponse.Error -> {
                        /**
                         * return the error message
                         */
                        emit(Resource.Error(message = "Error failed to fetch ${remoteCall.errorMessage}"))
                    }
                }
            } else {
                /**
                 * get the local db data and return it to view
                 */
                emitAll(getAllLocalPokemon().map {
                    Resource.Success(it)
                })
            }
        }

    private fun getAllLocalPokemon() =
        localDataSource.getAllPokemon().map {
            mappingPokemonEntityToDomainModel(it)
        }


    private fun convertToDomainModel(pokemon: PokemonResponse) =
        Pokemon(pokemon.pokemonId, pokemon.pokemonName)

}