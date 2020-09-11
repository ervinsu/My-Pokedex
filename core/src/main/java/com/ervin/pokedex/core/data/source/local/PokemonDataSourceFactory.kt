package com.ervin.pokedex.core.data.source.local

import androidx.paging.DataSource
import com.ervin.pokedex.core.data.source.local.response.PokemonLocalResponse
import com.ervin.pokedex.core.domain.model.Pokemon

class PokemonDataSourceFactory(private val listPokemon: List<PokemonLocalResponse>) :
    DataSource.Factory<Int, Pokemon>() {
    override fun create(): DataSource<Int, Pokemon> =
        PagedPokemonDataSource(listPokemon)
}