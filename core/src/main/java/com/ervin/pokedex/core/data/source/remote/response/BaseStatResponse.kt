package com.ervin.pokedex.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

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