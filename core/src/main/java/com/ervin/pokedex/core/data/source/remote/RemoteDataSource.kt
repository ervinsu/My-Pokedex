package com.ervin.pokedex.core.data.source.remote

import com.ervin.pokedex.core.data.source.remote.network.ApiResponse
import com.ervin.pokedex.core.data.source.remote.network.detail.DetailApiService
import com.ervin.pokedex.core.data.source.remote.network.home.HomeApiService
import com.ervin.pokedex.core.data.source.remote.response.PokemonResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(
    private val homeApiService: HomeApiService,
    private val detailApiService: DetailApiService
) {

    suspend fun getAllListPokemon(
        limit: Int,
        offset: Int
    ): Flow<ApiResponse<List<PokemonResponse>>> =
        flow {
            try {
                val response = homeApiService.getListPokemon(limit, offset)
                    .result
                    .map {
                        detailApiService.getPokemonByUrl(it.url)
                    }
                if (response.isNullOrEmpty()) {
                    emit(ApiResponse.Empty)
                } else {
                    emit(ApiResponse.Success(response))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
}