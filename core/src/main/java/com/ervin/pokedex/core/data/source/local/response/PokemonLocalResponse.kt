package com.ervin.pokedex.core.data.source.local.response

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.ervin.pokedex.core.data.source.local.entity.ElementEntity
import com.ervin.pokedex.core.data.source.local.entity.PokemonEntity
import com.ervin.pokedex.core.data.source.local.entity.foreignkey.PokemonElementEntity

class PokemonLocalResponse {
    @Embedded
    lateinit var pokemonEntity: PokemonEntity

    @Relation(
        parentColumn = PokemonEntity.POKEMON_ID,
        entity = ElementEntity::class,
        entityColumn = ElementEntity.ELEMENT_ID,
        associateBy = Junction(
            value = PokemonElementEntity::class,
            parentColumn = PokemonElementEntity.POKEMON_ELEMENT_ID_POKEMON,
            entityColumn = PokemonElementEntity.POKEMON_ELEMENT_ID_ELEMENT
        )
    )
    var typeList: List<ElementEntity> = arrayListOf()

}