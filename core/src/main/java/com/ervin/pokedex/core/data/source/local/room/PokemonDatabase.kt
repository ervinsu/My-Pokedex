package com.ervin.pokedex.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ervin.pokedex.core.data.source.local.entity.ElementEntity
import com.ervin.pokedex.core.data.source.local.entity.PokemonEntity
import com.ervin.pokedex.core.data.source.local.entity.foreignkey.PokemonElementEntity

@Database(
    entities = [ElementEntity::class, PokemonEntity::class, PokemonElementEntity::class],
    version = 1,
    exportSchema = false
)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
}