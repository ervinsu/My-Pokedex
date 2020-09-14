package com.ervin.pokedex.core.data.source.local

import com.ervin.pokedex.core.data.source.local.entity.ElementEntity
import com.ervin.pokedex.core.data.source.local.entity.PokemonEntity
import com.ervin.pokedex.core.data.source.local.entity.foreignkey.PokemonElementEntity
import com.ervin.pokedex.core.data.source.local.response.PokemonLocalResponse
import com.ervin.pokedex.core.data.source.local.room.PokemonDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val dao: PokemonDao) {

    fun getAllPokemon(): Flow<List<PokemonLocalResponse>> = dao.getAllPokemon()

    fun getSizeDBPokemon() = dao.getSizePokemon()

    fun getSizeDBElement() = dao.getSizeElement()

    suspend fun updateFavoritePokemon(pokemon: PokemonEntity) =
        dao.updatePokemon(pokemon)

    suspend fun insertAllPokemon(listPokemon: List<PokemonEntity>) =
        dao.insertAllPokemon(listPokemon)

    suspend fun insertAllElement(listElement: List<ElementEntity>) =
        dao.insertAllElement(listElement)

    suspend fun insertAllPokemonElementFK(listPokemonElementEntity: List<PokemonElementEntity>) =
        dao.insertAllPokemonElement(listPokemonElementEntity)

}