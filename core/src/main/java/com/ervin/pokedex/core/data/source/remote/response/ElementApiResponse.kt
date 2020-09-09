package com.ervin.pokedex.core.data.source.remote.response

import com.google.gson.annotations.SerializedName


data class ListElementApiResponse(
    @field:SerializedName("results")
    val result: List<Result>
) {
    data class Result(
        @field:SerializedName("name")
        val name: String,
        @field:SerializedName("url")
        val url: String
    ) {
        val id: Int
            get() = url.split("/")[6].toInt()
    }
}