package com.ervin.pokedex.core.data.repository

import com.ervin.pokedex.core.data.source.local.LocalDataSource
import com.ervin.pokedex.core.domain.model.Pokemon
import com.ervin.pokedex.core.domain.repository.DetailRepositoryContract
import com.ervin.pokedex.core.util.mappingPokemonDomainModelToEntity

class DetailRepository(private val localDataSource: LocalDataSource) : DetailRepositoryContract {
    override suspend fun setFavoritePokemon(pokemon: Pokemon) {
        localDataSource.updateFavoritePokemon(pokemon.mappingPokemonDomainModelToEntity())
    }
}