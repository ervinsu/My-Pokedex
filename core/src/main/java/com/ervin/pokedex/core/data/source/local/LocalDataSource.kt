package com.ervin.pokedex.core.data.source.local

import com.ervin.pokedex.core.data.source.local.entity.ElementEntity
import com.ervin.pokedex.core.data.source.local.entity.PokemonEntity
import com.ervin.pokedex.core.data.source.local.room.PokemonDao

class LocalDataSource(private val dao: PokemonDao) {

    fun getAllPokemon() = dao.getAllPokemon()

    fun getSizeDBPokemon() = dao.getSizePokemon()

    suspend fun insertAllPokemon(listPokemon: List<PokemonEntity>) =
        dao.insertAllPokemon(listPokemon)

    suspend fun insertAllElement(listElement: List<ElementEntity>) =
        dao.insertAllElement(listElement)
}