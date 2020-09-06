package com.ervin.pokedex.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class TypeElementResponse(
    @field:SerializedName("types")
    val types: List<Type>
) {
    data class Type(
        @field:SerializedName("name")
        val name: String,
        @field:SerializedName("url")
        val url: String
    )
}