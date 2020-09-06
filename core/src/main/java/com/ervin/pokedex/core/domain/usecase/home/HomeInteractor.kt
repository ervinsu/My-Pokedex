package com.ervin.pokedex.core.domain.usecase.home

import com.ervin.pokedex.core.data.source.Resource
import com.ervin.pokedex.core.domain.model.Pokemon
import com.ervin.pokedex.core.domain.repository.home.HomeRepositoryContract
import kotlinx.coroutines.flow.Flow

class HomeInteractor(private val homeRepository: HomeRepositoryContract) : HomeUseCase {
    override fun getAllPokemon(): Flow<Resource<List<Pokemon>>> = homeRepository.getAllPokemon()
}