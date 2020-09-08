package com.ervin.pokedex.core.data.repository.home

import android.util.Log
import com.ervin.pokedex.core.data.source.Resource
import com.ervin.pokedex.core.data.source.local.LocalDataSource
import com.ervin.pokedex.core.data.source.remote.RemoteDataSource
import com.ervin.pokedex.core.data.source.remote.network.ApiResponse
import com.ervin.pokedex.core.data.source.remote.response.PokemonResponse
import com.ervin.pokedex.core.domain.model.Pokemon
import com.ervin.pokedex.core.domain.repository.home.HomeRepositoryContract
import com.ervin.pokedex.core.util.mappingPokemonApiResponseToLocalResponse
import com.ervin.pokedex.core.util.mappingPokemonEntityToDomainModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.flow.*

class HomeRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : HomeRepositoryContract {
    @ExperimentalCoroutinesApi
    override fun getAllRemotePokemon() {
        Log.d("tes", "here")
        val scope = SupervisorJob() + Dispatchers.Main
        CoroutineScope(scope).launch {
            /**
             * check if the db is empty, then we should fetch api to get the pokemon
             * else do nothing, since data already provided
             */
            if (getLocalPokemonSize().first() == 0) {
                Log.d("tes", "here2")
                /**
                 * fetch up to 151 pokemon (Pokemon at Kanto Region)
                 */
                var count = 0
                val maxOffset = 151
                val currLimitPerFetch = 10
                val lastLimitFetch = maxOffset % currLimitPerFetch

                val fetchPokemonChannel = produce(CoroutineName("channelPokemon")) {
                    while (count + currLimitPerFetch < maxOffset) {
                        send(LimitOffsetPokemon(count, currLimitPerFetch))
                        count += currLimitPerFetch
                    }
                    //add to channel for last limit
                    send(LimitOffsetPokemon(count, lastLimitFetch))
                }

                try {
                    /**
                     * create 10 channel to run parallel
                     */
                    withContext(Dispatchers.IO) {
                        launch(CoroutineName("launcher-1")) {
                            getQueueRemotePokemon(
                                fetchPokemonChannel
                            )
                        }
                        launch(CoroutineName("launcher-2")) {
                            getQueueRemotePokemon(
                                fetchPokemonChannel
                            )
                        }
                        launch(CoroutineName("launcher-3")) {
                            getQueueRemotePokemon(
                                fetchPokemonChannel
                            )
                        }
                        launch(CoroutineName("launcher-4")) {
                            getQueueRemotePokemon(
                                fetchPokemonChannel
                            )
                        }
                        launch(CoroutineName("launcher-5")) {
                            getQueueRemotePokemon(
                                fetchPokemonChannel
                            )
                        }
                        launch(CoroutineName("launcher-6")) {
                            getQueueRemotePokemon(
                                fetchPokemonChannel
                            )
                        }
                        launch(CoroutineName("launcher-7")) {
                            getQueueRemotePokemon(
                                fetchPokemonChannel
                            )
                        }
                        launch(CoroutineName("launcher-8")) {
                            getQueueRemotePokemon(
                                fetchPokemonChannel
                            )
                        }
                        launch(CoroutineName("launcher-9")) {
                            getQueueRemotePokemon(
                                fetchPokemonChannel
                            )
                        }
                        launch(CoroutineName("launcher-10")) {
                            getQueueRemotePokemon(
                                fetchPokemonChannel
                            )
                        }
                    }
                } catch (e: CancellationException) {
                    scope.cancel(CancellationException("failed to fetch remote pokemon ${e.localizedMessage}"))
                }

            }
        }
    }

    data class LimitOffsetPokemon(val offset: Int, val limit: Int)

    private suspend fun getQueueRemotePokemon(receiveQueuePokemons: ReceiveChannel<LimitOffsetPokemon>) {
        receiveQueuePokemons.consumeAsFlow().onEach { limitOffset ->
            /**
             * convert the remote call and save it to local db
             */

            remoteDataSource.getAllListPokemon(limitOffset.offset, limitOffset.limit).collect {
                when (it) {
                    is ApiResponse.Success -> {
                        val convertedApiResult = it.data
                            .map(::mappingPokemonApiResponseToLocalResponse)
                        localDataSource.insertAllPokemon(convertedApiResult)
                    }
                    is ApiResponse.Empty -> {
                        //do nothing
                    }
                    is ApiResponse.Error -> {
                        throw CancellationException(it.errorMessage)
                    }
                }
            }

        }.catch {
            throw CancellationException(it.localizedMessage)

        }.launchIn(CoroutineScope(Dispatchers.IO))
    }

    override fun getAllLocalPokemon(): Flow<Resource<List<Pokemon>>> =
        flow {
            emit(Resource.Loading())
            /**
             * get the local db data and return it to view
             */
            emitAll(
                localDataSource
                    .getAllPokemon()
                    .map {
                        Resource.Success(mappingPokemonEntityToDomainModel(it))
                    }
            )
        }

    override fun getLocalPokemonSize(): Flow<Int> = localDataSource.getSizeDBPokemon()


    private fun convertToDomainModel(pokemon: PokemonResponse) =
        Pokemon(pokemon.pokemonId, pokemon.pokemonName)

}