package com.ervin.pokedex.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = PokemonEntity.TABLE_NAME,
    indices = [Index(PokemonEntity.POKEMON_ID)]
)
data class PokemonEntity(
    @PrimaryKey
    @ColumnInfo(name = POKEMON_ID)
    val pokemonId: Int,

    @ColumnInfo(name = POKEMON_NAME)
    val pokemonName: String,

    @ColumnInfo(name = POKEMON_SPRITE)
    val pokemonSpritesUrl: String,

    @ColumnInfo(name = POKEMON_SPEED)
    val speed: Int,

    @ColumnInfo(name = POKEMON_DEFENSE)
    val specialDefense: Int,

    @ColumnInfo(name = POKEMON_SP_ATTACK)
    val specialAttack: Int,

    @ColumnInfo(name = POKEMON_SP_DEFENSE)
    val defense: Int,

    @ColumnInfo(name = POKEMON_ATTACK)
    val attack: Int,

    @ColumnInfo(name = POKEMON_HP)
    val hp: Int,

    @ColumnInfo(name = POKEMON_WEIGHT)
    val weight: Int,

    @ColumnInfo(name = POKEMON_HEIGHT)
    val height: Int,

    @ColumnInfo(name = POKEMON_FAVORITE)
    val isFavorite: Boolean = false
) {
    companion object {
        const val TABLE_NAME = "pokemon"
        const val POKEMON_NAME = "pokemon_name"
        const val POKEMON_SPRITE = "pokemon_sprite"
        const val POKEMON_SPEED = "pokemon_speed"
        const val POKEMON_DEFENSE = "pokemon_defense"
        const val POKEMON_ATTACK = "pokemon_attack"
        const val POKEMON_HP = "pokemon_hp"
        const val POKEMON_SP_ATTACK = "pokemon_sp_attack"
        const val POKEMON_SP_DEFENSE = "pokemon_sp_defense"
        const val POKEMON_WEIGHT = "pokemon_weight"
        const val POKEMON_HEIGHT = "pokemon_height"
        const val POKEMON_ID = "pokemon_id"
        const val POKEMON_FAVORITE = "pokemon_favorite"
    }
}