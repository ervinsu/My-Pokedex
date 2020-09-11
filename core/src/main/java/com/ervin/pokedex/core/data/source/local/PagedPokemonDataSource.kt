package com.ervin.pokedex.core.data.source.local

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.ervin.pokedex.core.data.source.local.response.PokemonLocalResponse
import com.ervin.pokedex.core.domain.model.Pokemon
import com.ervin.pokedex.core.util.mappingPokemonEntityToDomainModel

class PagedPokemonDataSource(
    private val listPokemon: List<PokemonLocalResponse>
) : PageKeyedDataSource<Int, Pokemon>() {
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Pokemon>
    ) {
        Log.d("hmm", "aaaa")
//        flow {
        Log.d("hmm", "aaaab")

        val convertedResult = mappingPokemonEntityToDomainModel(listPokemon)
        Log.d("hmm", "bbb")
        callback.onResult(convertedResult, null, params.requestedLoadSize)
//            emit(true)
//        }
        Log.d("hmm", "ccc")

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Pokemon>) {
        Log.d("hmm", "aaaa")

//        flow {
        Log.d("hmm", "aaaab")
        val convertedResult = mappingPokemonEntityToDomainModel(listPokemon)
        Log.d("hmm", "bbb")

        callback.onResult(convertedResult, params.requestedLoadSize)
//            emit(true)
//        }
        Log.d("hmm", "ccc")

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Pokemon>) {
        // do nothing
    }

}