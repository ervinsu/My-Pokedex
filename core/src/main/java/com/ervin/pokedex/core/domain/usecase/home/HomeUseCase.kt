package com.ervin.pokedex.core.domain.usecase.home

import com.ervin.pokedex.core.data.source.Resource
import com.ervin.pokedex.core.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow

interface HomeUseCase {
    fun getAllLocalPokemon(): Flow<Resource<List<Pokemon>>>
    fun getAllFavoritePokemon(): Flow<Resource<List<Pokemon>>>
    fun maybeFetchRemotePokemon()
    fun maybeFetchRemoteElement(): Flow<Resource<Int>>
    fun getLocalPokemonSize(): Flow<Int>
}