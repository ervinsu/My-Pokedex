package com.ervin.pokedex.core.data.source.remote

import com.ervin.pokedex.core.data.source.remote.network.ApiResponse
import com.ervin.pokedex.core.data.source.remote.network.detail.DetailApiService
import com.ervin.pokedex.core.data.source.remote.network.home.HomeApiService
import com.ervin.pokedex.core.data.source.remote.response.ListElementApiResponse
import com.ervin.pokedex.core.data.source.remote.response.PokemonResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
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
            val response = homeApiService.getListPokemon(limit, offset)
                .result
                .map {
                    /**
                     * remove the last url character because when we call 'https://pokeapi.co/api/v2/pokemon36/'
                     * it return 404 not found
                     * should be 'https://pokeapi.co/api/v2/pokemon36' instead
                     */
                    detailApiService.getPokemonByUrl(it.url.substring(0, it.url.length - 1))
                }
            if (response.isNullOrEmpty()) {
                emit(ApiResponse.Empty)
            } else {
                emit(ApiResponse.Success(response))
            }
        }.flowOn(Dispatchers.IO)
            .catch {
                emit(ApiResponse.Error("Error fetch API ${it.localizedMessage}"))
            }

    suspend fun getAllListElement(): Flow<ApiResponse<List<ListElementApiResponse.Result>>> =
        flow {
            try {
                val response = homeApiService.getListPokemonElement()
                    .result
                if (response.isNullOrEmpty()) {
                    emit(ApiResponse.Empty)
                } else {
                    emit(ApiResponse.Success(response))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }
}