package com.ervin.pokedex.core.domain.repository

import com.ervin.pokedex.core.domain.model.Pokemon

interface DetailRepositoryContract {
    suspend fun setFavoritePokemon(pokemon: Pokemon)
}