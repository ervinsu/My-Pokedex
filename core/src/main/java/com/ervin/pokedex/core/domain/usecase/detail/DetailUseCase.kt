package com.ervin.pokedex.core.domain.usecase.detail

import com.ervin.pokedex.core.domain.model.Pokemon

interface DetailUseCase {
    suspend fun setFavoritePokemon(pokemon: Pokemon)
}
