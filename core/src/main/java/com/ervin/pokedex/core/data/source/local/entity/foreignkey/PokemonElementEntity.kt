package com.ervin.pokedex.core.data.source.local.entity.foreignkey

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index

/**
 * this is the bridge for pokemon and types
 */
@Entity(
    tableName = PokemonElementEntity.TABLE_NAME,
    primaryKeys = [
        PokemonElementEntity.POKEMON_ELEMENT_ID_POKEMON,
        PokemonElementEntity.POKEMON_ELEMENT_ID_ELEMENT
    ],
    indices = [Index(
        value = [
            PokemonElementEntity.POKEMON_ELEMENT_ID_POKEMON,
            PokemonElementEntity.POKEMON_ELEMENT_ID_ELEMENT
        ]
    )]
)
data class PokemonElementEntity(
    @ColumnInfo(name = POKEMON_ELEMENT_ID_POKEMON, index = true)
    val ck_pokemonId: Int,
    @ColumnInfo(name = POKEMON_ELEMENT_ID_ELEMENT, index = true)
    val ck_typeId: Int
) {
    companion object {
        const val TABLE_NAME = "pokemon_with_element"
        const val POKEMON_ELEMENT_ID_ELEMENT = "pokemon_element_id_element"
        const val POKEMON_ELEMENT_ID_POKEMON = "pokemon_element_id_pokemon"
    }
}