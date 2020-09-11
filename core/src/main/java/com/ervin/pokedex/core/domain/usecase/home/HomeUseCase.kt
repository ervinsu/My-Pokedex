package com.ervin.pokedex.core.domain.usecase.home

import androidx.paging.PagedList
import com.ervin.pokedex.core.data.source.Resource
import com.ervin.pokedex.core.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow

interface HomeUseCase {
    fun getAllLocalPokemon(): Flow<Resource<PagedList<Pokemon>>>
    fun maybeFetchRemotePokemon()
    fun maybeFetchRemoteElement(): Flow<Resource<Int>>
    fun getLocalPokemonSize(): Flow<Int>
}