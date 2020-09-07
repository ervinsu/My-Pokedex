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
    val pokemonTypes: List<TypeElementResponse>
)
