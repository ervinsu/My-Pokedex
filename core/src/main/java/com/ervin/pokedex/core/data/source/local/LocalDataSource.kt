package com.ervin.pokedex.core.data.source.local

import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.ervin.pokedex.core.data.source.local.entity.ElementEntity
import com.ervin.pokedex.core.data.source.local.entity.PokemonEntity
import com.ervin.pokedex.core.data.source.local.entity.foreignkey.PokemonElementEntity
import com.ervin.pokedex.core.data.source.local.room.PokemonDao
import com.ervin.pokedex.core.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class LocalDataSource(private val dao: PokemonDao) {

    fun getAllPokemon(): Flow<PagedList<Pokemon>?> =
        flow {
            dao.getAllPokemon().collect {
                val convertedListPokemon = PokemonDataSourceFactory(it)

                emit(
                    LivePagedListBuilder(convertedListPokemon, getPagedListConfig())
                        .build()
                        .value
                )
            }
        }

    private fun getPagedListConfig() =
        PagedList.Config.Builder()
            .setInitialLoadSizeHint(SIZE_PER_PAGE)
            .setEnablePlaceholders(false)
            .setPageSize(SIZE_PER_PAGE)
            .setPrefetchDistance(SIZE_PREFETCH_DISTANCE)
            .build()

    fun getSizeDBPokemon() = dao.getSizePokemon()

    fun getSizeDBElement() = dao.getSizeElement()

    suspend fun insertAllPokemon(listPokemon: List<PokemonEntity>) =
        dao.insertAllPokemon(listPokemon)

    suspend fun insertAllElement(listElement: List<ElementEntity>) =
        dao.insertAllElement(listElement)

    suspend fun insertAllPokemonElementFK(listPokemonElementEntity: List<PokemonElementEntity>) =
        dao.insertAllPokemonElement(listPokemonElementEntity)

    companion object {
        const val SIZE_PER_PAGE = 10
        const val SIZE_PREFETCH_DISTANCE = 30
    }
}