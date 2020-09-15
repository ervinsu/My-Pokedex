package com.ervin.pokedex.core.domain.usecase.detail

import com.ervin.pokedex.core.domain.model.Pokemon
import com.ervin.pokedex.core.domain.repository.DetailRepositoryContract

class DetailInteractor(private val detailRepositoryContract: DetailRepositoryContract) : DetailUseCase {
    override suspend fun setFavoritePokemon(pokemon: Pokemon) =
        detailRepositoryContract.setFavoritePokemon(pokemon)
}