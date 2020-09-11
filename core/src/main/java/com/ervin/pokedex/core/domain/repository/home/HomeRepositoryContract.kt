package com.ervin.pokedex.core.domain.repository.home

import androidx.paging.PagedList
import com.ervin.pokedex.core.data.source.Resource
import com.ervin.pokedex.core.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow

interface HomeRepositoryContract {
    fun getAllLocalPokemon(): Flow<Resource<PagedList<Pokemon>>>
    fun getLocalPokemonSize(): Flow<Int>
    fun maybeGetRemoteElement(): Flow<Resource<Int>>
}