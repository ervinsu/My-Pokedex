package com.ervin.pokedex.core.domain.repository.home

import com.ervin.pokedex.core.data.source.Resource
import com.ervin.pokedex.core.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow

interface HomeRepositoryContract {
    fun getAllLocalPokemon(): Flow<Resource<List<Pokemon>>>
    fun getLocalPokemonSize(): Flow<Int>
}