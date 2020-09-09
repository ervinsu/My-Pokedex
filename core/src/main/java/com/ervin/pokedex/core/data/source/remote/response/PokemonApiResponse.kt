package com.ervin.pokedex.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class PokemonResponse(

    @field:SerializedName("id")
    val pokemonId: Int,

    @field:SerializedName("name")
    val pokemonName: String,

    @field:SerializedName("height")
    val pokemonHeight: Int,

    @field:SerializedName("weight")
    val pokemonWeight: Int,

    @field:SerializedName("sprites")
    val pokemonSprites: SpriteResponse,

    @field:SerializedName("stats")
    val pokemonBaseStats: List<BaseStatResponse>,

    @field:SerializedName("types")
    val pokemonTypes: List<Type>
)

data class Type(
    @field:SerializedName("type")
    val type: Type
) {
    data class Type(
        @field:SerializedName("name")
        val name: String,
        @field:SerializedName("url")
        val url: String
    )
}

data class SpriteResponse(
    @field:SerializedName("front_default")
    val pokemonFrontSpriteUrl: String
)

data class BaseStatResponse(
    @field:SerializedName("base_stat")
    val statValue: Int,
    val stat: Stat
) {
    data class Stat(
        @field:SerializedName("name")
        val statName: String
    )
}