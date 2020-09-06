package com.ervin.pokedex.core.domain.usecase.home

import com.ervin.pokedex.core.data.source.Resource
import com.ervin.pokedex.core.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow

interface HomeUseCase {
    fun getAllPokemon(): Flow<Resource<List<Pokemon>>>
}