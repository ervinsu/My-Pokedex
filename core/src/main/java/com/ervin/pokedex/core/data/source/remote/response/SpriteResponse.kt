package com.ervin.pokedex.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class SpriteResponse(
    @field:SerializedName("front_default")
    val pokemonFrontSpriteUrl: String
)