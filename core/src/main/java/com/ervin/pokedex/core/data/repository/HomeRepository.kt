package com.ervin.pokedex.core.data.repository

import com.ervin.pokedex.core.data.source.Resource
import com.ervin.pokedex.core.data.source.local.LocalDataSource
import com.ervin.pokedex.core.data.source.remote.RemoteDataSource
import com.ervin.pokedex.core.data.source.remote.network.ApiResponse
import com.ervin.pokedex.core.domain.model.Pokemon
import com.ervin.pokedex.core.domain.repository.HomeRepositoryContract
import com.ervin.pokedex.core.util.mappingElementApiResponseToLocalResponse
import com.ervin.pokedex.core.util.mappingPokemonEntityToDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class HomeRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : HomeRepositoryContract {

    override fun getAllLocalPokemon(): Flow<Resource<List<Pokemon>>> =
        flow {
            emit(Resource.Loading())
            /**
             * get the local db data and return it to view
             */
            try {
                emitAll(
                    localDataSource
                        .getAllPokemon()
                        .map {
                            Resource.Success(mappingPokemonEntityToDomainModel(it))
                        }
                )
            } catch (e: Exception) {
                emit(Resource.Error("Error on HomeRepository $e"))
            }
        }

    override fun getAllFavoritePokemon(): Flow<Resource<List<Pokemon>>> =
        flow {
            /**
             * get Favorite Pokemon
             */
            try {
                emitAll(
                    localDataSource
                        .getAllFavoritePokemon()
                        .map {
                            Resource.Success(mappingPokemonEntityToDomainModel(it))
                        }
                )
            } catch (e: Exception) {
                emit(Resource.Error("Error on HomeRepository $e"))
            }
        }

    override fun getLocalPokemonSize(): Flow<Int> = localDataSource.getSizeDBPokemon()

    override fun maybeGetRemoteElement() =
        flow {
            emit(Resource.Loading())
            val localElementSize = localDataSource.getSizeDBElement().first()
            if (localElementSize == 0) {
                val remoteElementResponse = remoteDataSource.getAllListElement()
                remoteElementResponse.collect { response ->
                    when (response) {
                        is ApiResponse.Success -> {
                            val mappedElements = response.data
                                .map(::mappingElementApiResponseToLocalResponse)
                            localDataSource.insertAllElement(mappedElements)
                            emit(Resource.Success(mappedElements.size))
                        }
                        is ApiResponse.Empty -> {
                            emit(Resource.Error("Empty Data", 0))
                        }
                        is ApiResponse.Error -> {
                            emit(Resource.Error("Error Fetch ${response.errorMessage}", 0))
                        }
                    }
                }
            } else {
                emit(Resource.Success(localElementSize))
            }
        }.catch {
            emit(Resource.Error("Error ${it.localizedMessage}"))
        }

}