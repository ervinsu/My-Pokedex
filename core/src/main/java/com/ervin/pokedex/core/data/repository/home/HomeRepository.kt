package com.ervin.pokedex.core.data.repository.home

import android.util.Log
import androidx.paging.PagedList
import com.ervin.pokedex.core.data.source.Resource
import com.ervin.pokedex.core.data.source.local.LocalDataSource
import com.ervin.pokedex.core.data.source.remote.RemoteDataSource
import com.ervin.pokedex.core.data.source.remote.network.ApiResponse
import com.ervin.pokedex.core.domain.model.Pokemon
import com.ervin.pokedex.core.domain.repository.home.HomeRepositoryContract
import com.ervin.pokedex.core.util.mappingElementApiResponseToLocalResponse
import kotlinx.coroutines.flow.*

class HomeRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : HomeRepositoryContract {

    override fun getAllLocalPokemon(): Flow<Resource<PagedList<Pokemon>>> =
        flow {
            emit(Resource.Loading())
            /**
             * get the local db data and return it to view
             */
            try {
                val data = localDataSource.getAllPokemon().first()
                if (data.isNullOrEmpty()) {
                    emit(Resource.Error("EMPTY"))
                } else {
                    emit(Resource.Success(data))
                }
            } catch (e: Exception) {
                Log.d("hmm", "$e repository level")

                emit(Resource.Error("$e repository level"))
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