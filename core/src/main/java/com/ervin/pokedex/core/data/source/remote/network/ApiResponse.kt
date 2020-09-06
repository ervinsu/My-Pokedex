package com.ervin.pokedex.core.data.source.remote.network

sealed class ApiResponse<out U> {
    data class Success<out T>(val data: T) : ApiResponse<T>()
    data class Error(val errorMessage: String) : ApiResponse<Nothing>()
    object Empty : ApiResponse<Nothing>()
}