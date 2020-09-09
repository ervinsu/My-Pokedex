package com.ervin.pokedex.core.data.source.remote.network.home

import com.ervin.pokedex.core.data.source.remote.response.ListElementApiResponse
import com.ervin.pokedex.core.data.source.remote.response.ListPokemonApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeApiService {
    @GET("pokemon")
    suspend fun getListPokemon(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): ListPokemonApiResponse

    @GET("type")
    suspend fun getListPokemonElement(): ListElementApiResponse
}