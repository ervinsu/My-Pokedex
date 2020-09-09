package com.ervin.pokedex.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ervin.pokedex.core.data.source.local.entity.ElementEntity
import com.ervin.pokedex.core.data.source.local.entity.PokemonEntity
import com.ervin.pokedex.core.data.source.local.entity.foreignkey.PokemonElementEntity
import com.ervin.pokedex.core.data.source.local.response.PokemonLocalResponse
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPokemon(listPokemon: List<PokemonEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllElement(listElement: List<ElementEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPokemonElement(listPokemonElement: List<PokemonElementEntity>)

    @Query("SELECT * FROM pokemon")
    fun getAllPokemon(): Flow<List<PokemonLocalResponse>>

    @Query("SELECT COUNT(pokemon_id) FROM POKEMON")
    fun getSizePokemon(): Flow<Int>

    @Query("SELECT COUNT(element_id) FROM ELEMENT")
    fun getSizeElement(): Flow<Int>

}