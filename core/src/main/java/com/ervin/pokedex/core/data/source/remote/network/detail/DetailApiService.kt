package com.ervin.pokedex.core.data.source.remote.network.detail

import com.ervin.pokedex.core.data.source.remote.response.PokemonResponse
import retrofit2.http.GET
import retrofit2.http.Url

interface DetailApiService {
    @GET
    suspend fun getPokemonByUrl(@Url pokemonUrl: String): PokemonResponse
}