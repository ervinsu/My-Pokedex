package com.ervin.pokedex.core.data.repository.home

import com.ervin.pokedex.core.data.source.Resource
import com.ervin.pokedex.core.data.source.local.LocalDataSource
import com.ervin.pokedex.core.data.source.remote.RemoteDataSource
import com.ervin.pokedex.core.domain.model.Pokemon
import com.ervin.pokedex.core.domain.repository.home.HomeRepositoryContract
import com.ervin.pokedex.core.util.mappingPokemonEntityToDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
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
            emitAll(
                localDataSource
                    .getAllPokemon()
                    .map {
                        Resource.Success(mappingPokemonEntityToDomainModel(it))
                    }
            )
        }

    override fun getLocalPokemonSize(): Flow<Int> = localDataSource.getSizeDBPokemon()

}